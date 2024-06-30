package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesInvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesReturnInvoiceItemDTO;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SalesInvoiceReturnDetailsService {
    @Autowired
    SalesInvoiceReturnDetailsRepo salesInvoiceReturnDetailsRepo;

    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;
    @Autowired
    private SalesInvoiceReturnRepo salesInvoiceReturnRepo;
    @Autowired
    private InvItemCardBatchRepo invItemCardBatchRepo;
    @Autowired
    private StoreRepo storeRepo;
    @Autowired
    private InvItemcardMovementRepo invItemcardMovementRepo;
    @Autowired
    private InvItemCardService invItemCardService;

    public List<SalesInvoicesReturnDetails> findBySalesInvoiceReturnId(Long id){

        return salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(id);
    }

    @Transactional
    public SalesInvoicesReturnDetails saveItemInOrder(SalesReturnInvoiceItemDTO request) throws JsonProcessingException {

        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(request.getOrderId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(request.getBatch()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(request.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(request.getUom()).orElseThrow();
        Store store = storeRepo.findById(request.getStore()).orElseThrow();
        Customer customer = salesInvoiceReturn.getCustomer();


        SalesInvoicesReturnDetails dataToInsertToInvoiceDetails = new SalesInvoicesReturnDetails();


        dataToInsertToInvoiceDetails.setStore(store);
        dataToInsertToInvoiceDetails.setItem(invItemCard);
        dataToInsertToInvoiceDetails.setUom(invUom);
        dataToInsertToInvoiceDetails.setBatch(batchData);
        dataToInsertToInvoiceDetails.setQuantity(request.getItemQuantity());
        dataToInsertToInvoiceDetails.setUnitPrice(request.getUnitPrice());
        dataToInsertToInvoiceDetails.setUnitCostPrice(request.getUnitCostPrice());
        dataToInsertToInvoiceDetails.setTotalPrice(request.getUnitPrice().multiply(request.getItemQuantity()));
        dataToInsertToInvoiceDetails.setIsparentuom(invUom.isMaster());
        dataToInsertToInvoiceDetails.setSalesInvoiceReturn(salesInvoiceReturn);

        salesInvoiceReturn.setTotalCost(salesInvoiceReturn.getTotalCost().add(dataToInsertToInvoiceDetails.getTotalPrice()));
        salesInvoiceReturn.setTotalBeforeDiscount(salesInvoiceReturn.getTotalCost().add(dataToInsertToInvoiceDetails.getTotalPrice()));
        //salesInvoiceReturn.setTotalCost(request.getUnitPrice().multiply(request.getItemQuantity()));
        salesInvoiceReturnRepo.save(salesInvoiceReturn);


       SalesInvoicesReturnDetails savedSalesInvoicesReturnDetails= salesInvoiceReturnDetailsRepo.save(dataToInsertToInvoiceDetails);

        //خصم الكمية من الباتش
        //كمية الصنف بكل المخازن قبل الحركة
        BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);

        //get Quantity Before any Action  حنجيب كيمة الصنف  بالمخزن المحدد معه   الحالي قبل الحركة
        BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store );

        InvItemCardBatch dataUpdateOldBatch = invItemCardBatchRepo.findById(request.getBatch()).orElseThrow();


        //هنا حخصم الكمية لحظيا من باتش الصنف
        //update current Batch تحديث علي الباتش القديمة
        if (invUom.isMaster()) {
            //حخصم بشكل مباشر لانه بنفس وحده الباتش الاب
            dataUpdateOldBatch.setQuantity(batchData.getQuantity().add(request.getItemQuantity()));
        } else {
            //مرجع بالوحده الابن التجزئة فلازم تحولها الي الاب قبل الخصم انتبه !!
            dataUpdateOldBatch.setQuantity((batchData.getQuantity().add( request.getItemQuantity().divide(invItemCard.getRetailUomQuntToParent()) )));
        }


        dataUpdateOldBatch.setTotalCostPrice(batchData.getUnitCostPrice().multiply(dataUpdateOldBatch.getQuantity()));


        invItemCardBatchRepo.save(dataUpdateOldBatch);



        BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);
        BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store);

        InvItemcardMovement dataInsertInvItemCardMovements = new InvItemcardMovement();

        dataInsertInvItemCardMovements.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(2));
        dataInsertInvItemCardMovements.setInvItemcardMovementsType(new InvItemcardMovementsType(4));
        dataInsertInvItemCardMovements.setItem(new InvItemCard(request.getInvItemCard()));
        dataInsertInvItemCardMovements.setByan( "نظير مبيعات  للعميل " + " " + customer.getName() + " فاتورة رقم"+" " + salesInvoiceReturn.getId());
        dataInsertInvItemCardMovements.setQuantityBeforMovement( "عدد " +" " + ( quantityBeforeMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMove("عدد " + " " + ( quantityAfterMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityBeforMoveStore("عدد " +" "+( quantityBeforeMoveCurrentStore ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMoveStore( "عدد " + " " + ( quantityAfterMoveCurrentStore ) + " " +  invUom.getName());

        dataInsertInvItemCardMovements.setStore(store);
        //التاثير في حركة كارت الصنف
        invItemcardMovementRepo.save(dataInsertInvItemCardMovements);


        invItemCardService.doUpdateItemCardQuantity(invItemCard, batchData);

        return savedSalesInvoicesReturnDetails ;
    }
    @Transactional
    public SalesInvoicesReturnDetails updateItemInOrder(InvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        //map from DTO to the Original Entity
        return mapSalesInvoiceReturnDetailsDtoToSalesInvoiceReturnDetails(parsedInvoiceItemDto, salesInvoicesReturnDetails);
        //
    }
    @Transactional
    public SalesInvoicesReturnDetails updateItemBeingInsertedAgain(SalesReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoicesReturnDetails salesInvoiceDetail = salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(),parsedInvoiceItemDto.getUom()).orElseThrow();
        salesInvoiceDetail.setQuantity(salesInvoiceDetail.getQuantity().add(parsedInvoiceItemDto.getItemQuantity()));
        salesInvoiceDetail.setTotalPrice(salesInvoiceDetail.getUnitPrice().multiply(salesInvoiceDetail.getQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice=0;
        for(SalesInvoicesReturnDetails details : salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(parsedInvoiceItemDto.getOrderId())){
            System.out.println(details.getTotalPrice());
            totalPrice+=details.getTotalPrice().floatValue();
        }

        System.out.println("totalPrice : "+totalPrice);
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoiceReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : "+salesInvoiceReturn.getTotalCost());
        salesInvoiceReturn.setTotalBeforeDiscount(salesInvoiceReturn.getTotalCost().add(salesInvoiceReturn.getTaxValue()==null? BigDecimal.ZERO:salesInvoiceReturn.getTaxValue()));
        System.out.println("totalPrice : "+salesInvoiceReturn.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+salesInvoiceReturn.getTaxValue());
        salesInvoiceReturnRepo.save(salesInvoiceReturn);
        totalPrice=0;
        return salesInvoiceReturnDetailsRepo.save(salesInvoiceDetail);
    }

    @Transactional
    public SalesInvoiceReturn deleteItemFromSalesInvoiceReturn(Long id) {
//        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
//        //map from DTO to the Original Entity
//        //calculate the total price for the supplier order itself
//        salesInvoiceReturnDetailsRepo.deleteById(id);
//        float totalPrice=0;
//        for(SalesInvoicesReturnDetails details : salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(salesInvoicesReturnDetails.getSalesInvoiceReturn().getId())){
//            totalPrice+=details.getTotalPrice().floatValue();
//        }
//        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(salesInvoicesReturnDetails.getSalesInvoiceReturn().getId()).orElseThrow();
//        salesInvoiceReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
//        salesInvoiceReturn.setTotalBeforeDiscount(salesInvoiceReturn.getTotalCost().add(salesInvoiceReturn.getTaxValue()==null? BigDecimal.ZERO:salesInvoiceReturn.getTaxValue()));
//        salesInvoiceReturn.setUpdatedBy(new Admin(1));
//        totalPrice=0;
//        return salesInvoiceReturnRepo.save(salesInvoiceReturn);

        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(salesInvoicesReturnDetails.getSalesInvoiceReturn().getId()).orElseThrow();
        //SalesInvoice salesInvoiceReturn = salesInvoiceRepo.findById(request.getOrderId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(salesInvoicesReturnDetails.getBatch().getId()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(salesInvoicesReturnDetails.getItem().getId()).orElseThrow();
        InvUom invUom = invUomRepo.findById(salesInvoicesReturnDetails.getUom().getId()).orElseThrow();
        Store store = storeRepo.findById(salesInvoicesReturnDetails.getStore().getId()).orElseThrow();
        Customer customer = salesInvoiceReturn.getCustomer();


        salesInvoiceReturnDetailsRepo.deleteById(id);
        float totalPrice=0;
        for(SalesInvoicesReturnDetails details : salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(salesInvoicesReturnDetails.getSalesInvoiceReturn().getId())){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        salesInvoiceReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        salesInvoiceReturnRepo.save(salesInvoiceReturn);

        //خصم الكمية من الباتش
        //كمية الصنف بكل المخازن قبل الحركة
        BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);

        //get Quantity Before any Action  حنجيب كيمة الصنف  بالمخزن المحدد معه   الحالي قبل الحركة
        BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store );

        InvItemCardBatch dataUpdateOldBatch = invItemCardBatchRepo.findById(salesInvoicesReturnDetails.getBatch().getId()).orElseThrow();


        //هنا حخصم الكمية لحظيا من باتش الصنف
        //update current Batch تحديث علي الباتش القديمة
        if (invUom.isMaster()) {
            //حخصم بشكل مباشر لانه بنفس وحده الباتش الاب
            dataUpdateOldBatch.setQuantity(batchData.getQuantity().subtract(salesInvoicesReturnDetails.getQuantity()));
        } else {
            //مرجع بالوحده الابن التجزئة فلازم تحولها الي الاب قبل الخصم انتبه !!
            dataUpdateOldBatch.setQuantity((batchData.getQuantity().subtract( salesInvoicesReturnDetails.getQuantity().divide(invItemCard.getRetailUomQuntToParent()) )));
        }


        dataUpdateOldBatch.setTotalCostPrice(batchData.getUnitCostPrice().multiply(dataUpdateOldBatch.getQuantity()));
        invItemCardBatchRepo.save(dataUpdateOldBatch);



        BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);
        BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store);

        InvItemcardMovement dataInsertInvItemCardMovements = new InvItemcardMovement();

        dataInsertInvItemCardMovements.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(2));
        dataInsertInvItemCardMovements.setInvItemcardMovementsType(new InvItemcardMovementsType(4));
        dataInsertInvItemCardMovements.setItem(new InvItemCard(salesInvoicesReturnDetails.getItem().getId()));
        dataInsertInvItemCardMovements.setByan( "نظير مبيعات  للعميل " + " " + customer.getName() + " فاتورة رقم"+" " + salesInvoiceReturn.getId());
        dataInsertInvItemCardMovements.setQuantityBeforMovement( "عدد " +" " + ( quantityBeforeMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMove("عدد " + " " + ( quantityAfterMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityBeforMoveStore("عدد " +" "+( quantityBeforeMoveCurrentStore ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMoveStore( "عدد " + " " + ( quantityAfterMoveCurrentStore ) + " " +  invUom.getName());

        dataInsertInvItemCardMovements.setStore(store);
        //التاثير في حركة كارت الصنف
        invItemcardMovementRepo.save(dataInsertInvItemCardMovements);


        invItemCardService.doUpdateItemCardQuantity(invItemCard, batchData);

        return  salesInvoiceReturn;


    }
    @Transactional
    public SalesInvoicesReturnDetails mapSalesInvoiceReturnDetailsDtoToSalesInvoiceReturnDetails(InvoiceItemDTO parsedInvoiceItemDto, SalesInvoicesReturnDetails salesInvoicesReturnDetails) {


        salesInvoicesReturnDetails.setSalesInvoiceReturn(new SalesInvoiceReturn(parsedInvoiceItemDto.getOrderId()));
        salesInvoicesReturnDetails.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoicesReturnDetails.setUom(new InvUom(parsedInvoiceItemDto.getUom()));
        salesInvoicesReturnDetails.setQuantity(parsedInvoiceItemDto.getDeliveredQuantity());
        salesInvoicesReturnDetails.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        salesInvoicesReturnDetails.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getDeliveredQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getDeliveredQuantity()));
        salesInvoicesReturnDetails.setIsparentuom(invUomRepo.findById(parsedInvoiceItemDto.getUom()).get().isMaster());
        salesInvoicesReturnDetails.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoicesReturnDetails.setSalesItemType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get().getItemType());
        //saving the salesInvoicesReturnDetails in the DB.

        SalesInvoicesReturnDetails savedSalesInvoiceDetails= salesInvoiceReturnDetailsRepo.save(salesInvoicesReturnDetails);

        System.out.println("saved savedSalesInvoiceDetails service method");
        //updating the Order itself with the updates.
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoiceReturn.setTotalCost(salesInvoiceReturnRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(salesInvoicesReturnDetails.getTotalPrice()==null?BigDecimal.ZERO:salesInvoicesReturnDetails.getTotalPrice()));
        salesInvoiceReturn.setTotalBeforeDiscount(salesInvoiceReturn.getTotalCost().add(salesInvoiceReturn.getTaxValue()==null? BigDecimal.ZERO:salesInvoiceReturn.getTaxValue()));
        salesInvoiceReturnRepo.save(salesInvoiceReturn);

        return savedSalesInvoiceDetails;
    }
    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
        SalesInvoiceReturn salesInvoiceReturn= salesInvoiceReturnRepo.findById(id).orElseThrow();
        return salesInvoiceReturn.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(SalesReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getInvItemCard());
        Optional<SalesInvoicesReturnDetails> salesInvoiceReturnDetails= salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (salesInvoiceReturnDetails.isEmpty()){
            return false;
        }else{
            System.out.println(salesInvoiceReturnDetails.get().getSalesInvoiceReturn().getId());
            System.out.println(salesInvoiceReturnDetails.get().getItem().getId());
            System.out.println(salesInvoiceReturnDetails.get().getUom().getId());
            return true;
        }
    }
}

