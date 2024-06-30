package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesInvoiceItemDTO;
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
public class SalesInvoiceDetailsService {
    @Autowired
    SalesInvoiceDetailsRepo salesInvoiceDetailsRepo;
    @Autowired
    SalesInvoiceRepo salesInvoiceRepo;
    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;

    @Autowired
    StoreRepo storeRepo;

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    InvItemcardMovementRepo invItemcardMovementRepo;
    @Autowired
    AccountRepo accountRepo;

    @Autowired
    InvItemCardService invItemCardService;


    public List<SalesInvoiceDetail> findBySalesInvoiceId(Long id){

        return salesInvoiceDetailsRepo.findBySalesInvoiceId(id);
    }

    @Transactional
    public List<SalesInvoiceDetail> saveItemInOrder(SalesInvoiceItemDTO request) throws JsonProcessingException {


        SalesInvoice salesInvoice = salesInvoiceRepo.findById(request.getOrderId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(request.getBatch()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(request.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(request.getUom()).orElseThrow();
        Store store = storeRepo.findById(request.getStore()).orElseThrow();
        Customer customer = salesInvoice.getCustomer();


        SalesInvoiceDetail dataToInsertToInvoiceDetails = new SalesInvoiceDetail();


        dataToInsertToInvoiceDetails.setStore(store);
        dataToInsertToInvoiceDetails.setItem(invItemCard);
        dataToInsertToInvoiceDetails.setUom(invUom);
        dataToInsertToInvoiceDetails.setBatch(batchData);
        dataToInsertToInvoiceDetails.setQuantity(request.getItemQuantity());
        dataToInsertToInvoiceDetails.setUnitPrice(request.getUnitPrice());
        dataToInsertToInvoiceDetails.setTotalPrice(request.getUnitPrice().multiply(request.getItemQuantity()));
        dataToInsertToInvoiceDetails.setIsParentUom(invUom.isMaster());
        dataToInsertToInvoiceDetails.setSalesInvoice(salesInvoice);

        salesInvoice.setTotalCost(salesInvoice.getTotalCost().add(dataToInsertToInvoiceDetails.getTotalPrice()));
        //salesInvoice.setTotalCost(request.getUnitPrice().multiply(request.getItemQuantity()));
        salesInvoiceRepo.save(salesInvoice);


        salesInvoiceDetailsRepo.save(dataToInsertToInvoiceDetails);

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
            dataUpdateOldBatch.setQuantity(batchData.getQuantity().subtract(request.getItemQuantity()));
        } else {
            //مرجع بالوحده الابن التجزئة فلازم تحولها الي الاب قبل الخصم انتبه !!
            dataUpdateOldBatch.setQuantity((batchData.getQuantity().subtract( request.getItemQuantity().divide(invItemCard.getRetailUomQuntToParent()) )));
        }


        dataUpdateOldBatch.setTotalCostPrice(batchData.getUnitCostPrice().multiply(dataUpdateOldBatch.getQuantity()));
        invItemCardBatchRepo.save(dataUpdateOldBatch);



            BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);
            BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store);

            InvItemcardMovement dataInsertInvItemCardMovements = new InvItemcardMovement();

            dataInsertInvItemCardMovements.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(2));
            dataInsertInvItemCardMovements.setInvItemcardMovementsType(new InvItemcardMovementsType(4));
            dataInsertInvItemCardMovements.setItem(new InvItemCard(request.getInvItemCard()));
            dataInsertInvItemCardMovements.setByan( "نظير مبيعات  للعميل " + " " + customer.getName() + " فاتورة رقم"+" " + salesInvoice.getId());
            dataInsertInvItemCardMovements.setQuantityBeforMovement( "عدد " +" " + ( quantityBeforeMove ) + " " + invUom.getName());
            dataInsertInvItemCardMovements.setQuantityAfterMove("عدد " + " " + ( quantityAfterMove ) + " " + invUom.getName());
            dataInsertInvItemCardMovements.setQuantityBeforMoveStore("عدد " +" "+( quantityBeforeMoveCurrentStore ) + " " + invUom.getName());
            dataInsertInvItemCardMovements.setQuantityAfterMoveStore( "عدد " + " " + ( quantityAfterMoveCurrentStore ) + " " +  invUom.getName());

            dataInsertInvItemCardMovements.setStore(store);
            //التاثير في حركة كارت الصنف
            invItemcardMovementRepo.save(dataInsertInvItemCardMovements);


            invItemCardService.doUpdateItemCardQuantity(invItemCard, batchData);

            return  salesInvoice.getSalesInvoiceDetails();

    }

    @Transactional
    public SalesInvoiceDetail updateItemInOrder(SalesInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoiceDetail supplierOrderDetails = salesInvoiceDetailsRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get();
        //map from DTO to the Original Entity
        return mapSalesInvoiceDetailsDtoToSalesInvoiceDetails(parsedInvoiceItemDto, supplierOrderDetails);
        //
    }
    @Transactional
    public SalesInvoiceDetail updateItemBeingInsertedAgain(SalesInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SalesInvoiceDetail salesInvoiceDetail = salesInvoiceDetailsRepo.findBySalesInvoiceIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(),parsedInvoiceItemDto.getUom()).orElseThrow();
        salesInvoiceDetail.setQuantity(salesInvoiceDetail.getQuantity().add(parsedInvoiceItemDto.getItemQuantity()));
        salesInvoiceDetail.setTotalPrice(salesInvoiceDetail.getUnitPrice().multiply(salesInvoiceDetail.getQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice=0;
        for(SalesInvoiceDetail details : salesInvoiceDetailsRepo.findBySalesInvoiceId(parsedInvoiceItemDto.getOrderId())){
            System.out.println(details.getTotalPrice());
            totalPrice+=details.getTotalPrice().floatValue();
        }

        System.out.println("totalPrice : "+totalPrice);
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoice.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : "+salesInvoice.getTotalCost());
        salesInvoice.setTotalBeforeDiscount(salesInvoice.getTotalCost().add(salesInvoice.getTaxValue()==null? BigDecimal.ZERO:salesInvoice.getTaxValue()));
        System.out.println("totalPrice : "+salesInvoice.getTotalBeforeDiscount());
        System.out.println("totalPrice : "+salesInvoice.getTaxValue());
        salesInvoiceRepo.save(salesInvoice);
        totalPrice=0;
        return salesInvoiceDetailsRepo.save(salesInvoiceDetail);
    }

    @Transactional
    public SalesInvoice deleteItemFromSalesInvoice(Long id) {
        SalesInvoiceDetail salesInvoiceDetail = salesInvoiceDetailsRepo.findById(id).orElseThrow();
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(salesInvoiceDetail.getSalesInvoice().getId()).orElseThrow();
        //SalesInvoice salesInvoice = salesInvoiceRepo.findById(request.getOrderId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(salesInvoiceDetail.getBatch().getId()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(salesInvoiceDetail.getItem().getId()).orElseThrow();
        InvUom invUom = invUomRepo.findById(salesInvoiceDetail.getUom().getId()).orElseThrow();
        Store store = storeRepo.findById(salesInvoiceDetail.getStore().getId()).orElseThrow();
        Customer customer = salesInvoice.getCustomer();


        salesInvoiceDetailsRepo.deleteById(id);
        float totalPrice=0;
        for(SalesInvoiceDetail details : salesInvoiceDetailsRepo.findBySalesInvoiceId(salesInvoiceDetail.getSalesInvoice().getId())){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        salesInvoice.setTotalCost(BigDecimal.valueOf(totalPrice));
        salesInvoice.setTotalBeforeDiscount(BigDecimal.valueOf(totalPrice));
        salesInvoiceRepo.save(salesInvoice);

        //خصم الكمية من الباتش
        //كمية الصنف بكل المخازن قبل الحركة
        BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);

        //get Quantity Before any Action  حنجيب كيمة الصنف  بالمخزن المحدد معه   الحالي قبل الحركة
        BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store );

        InvItemCardBatch dataUpdateOldBatch = invItemCardBatchRepo.findById(salesInvoiceDetail.getBatch().getId()).orElseThrow();


        //هنا حخصم الكمية لحظيا من باتش الصنف
        //update current Batch تحديث علي الباتش القديمة
        if (invUom.isMaster()) {
            //حخصم بشكل مباشر لانه بنفس وحده الباتش الاب
            dataUpdateOldBatch.setQuantity(batchData.getQuantity().add(salesInvoiceDetail.getQuantity()));
        } else {
            //مرجع بالوحده الابن التجزئة فلازم تحولها الي الاب قبل الخصم انتبه !!
            dataUpdateOldBatch.setQuantity((batchData.getQuantity().add( salesInvoiceDetail.getQuantity().divide(invItemCard.getRetailUomQuntToParent()) )));
        }


        dataUpdateOldBatch.setTotalCostPrice(batchData.getUnitCostPrice().multiply(dataUpdateOldBatch.getQuantity()));
        invItemCardBatchRepo.save(dataUpdateOldBatch);



        BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);
        BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store);

        InvItemcardMovement dataInsertInvItemCardMovements = new InvItemcardMovement();

        dataInsertInvItemCardMovements.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(2));
        dataInsertInvItemCardMovements.setInvItemcardMovementsType(new InvItemcardMovementsType(4));
        dataInsertInvItemCardMovements.setItem(new InvItemCard(salesInvoiceDetail.getItem().getId()));
        dataInsertInvItemCardMovements.setByan( "نظير مبيعات  للعميل " + " " + customer.getName() + " فاتورة رقم"+" " + salesInvoice.getId());
        dataInsertInvItemCardMovements.setQuantityBeforMovement( "عدد " +" " + ( quantityBeforeMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMove("عدد " + " " + ( quantityAfterMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityBeforMoveStore("عدد " +" "+( quantityBeforeMoveCurrentStore ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMoveStore( "عدد " + " " + ( quantityAfterMoveCurrentStore ) + " " +  invUom.getName());

        dataInsertInvItemCardMovements.setStore(store);
        //التاثير في حركة كارت الصنف
        invItemcardMovementRepo.save(dataInsertInvItemCardMovements);


        invItemCardService.doUpdateItemCardQuantity(invItemCard, batchData);

        return  salesInvoice;
//        SalesInvoiceDetail salesInvoiceDetail = salesInvoiceDetailsRepo.findById(id).orElseThrow();
//        //map from DTO to the Original Entity
//        //calculate the total price for the supplier order itself
//        salesInvoiceDetailsRepo.deleteById(id);
//        float totalPrice=0;
//        for(SalesInvoiceDetail details : salesInvoiceDetailsRepo.findBySalesInvoiceId(salesInvoiceDetail.getSalesInvoice().getId())){
//            totalPrice+=details.getTotalPrice().floatValue();
//        }
//        SalesInvoice salesInvoice = salesInvoiceRepo.findById(salesInvoiceDetail.getSalesInvoice().getId()).orElseThrow();
//        salesInvoice.setTotalCost(BigDecimal.valueOf(totalPrice));
//        salesInvoice.setTotalBeforeDiscount(salesInvoice.getTotalCost().add(salesInvoice.getTaxValue()==null? BigDecimal.ZERO:salesInvoice.getTaxValue()));
//        salesInvoice.setUpdatedBy(new Admin(1));
//        totalPrice=0;
//        return salesInvoiceRepo.save(salesInvoice);
    }
    @Transactional
    public SalesInvoiceDetail mapSalesInvoiceDetailsDtoToSalesInvoiceDetails(SalesInvoiceItemDTO parsedInvoiceItemDto, SalesInvoiceDetail salesInvoiceDetail) {


        salesInvoiceDetail.setSalesInvoice(new SalesInvoice(parsedInvoiceItemDto.getOrderId()));
        salesInvoiceDetail.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoiceDetail.setUom(new InvUom(parsedInvoiceItemDto.getUom()));
        salesInvoiceDetail.setQuantity(parsedInvoiceItemDto.getItemQuantity());
        salesInvoiceDetail.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
        salesInvoiceDetail.setTotalPrice(parsedInvoiceItemDto.getUnitPrice().multiply(parsedInvoiceItemDto.getItemQuantity()==null?BigDecimal.ONE:parsedInvoiceItemDto.getItemQuantity()));
        salesInvoiceDetail.setIsParentUom(invUomRepo.findById(parsedInvoiceItemDto.getUom()).get().isMaster());
        salesInvoiceDetail.setItem(new InvItemCard(parsedInvoiceItemDto.getInvItemCard()));
        salesInvoiceDetail.setSalesItemType((byte) invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).get().getItemType());
        //saving the salesInvoiceDetail in the DB.

        SalesInvoiceDetail savedSalesInvoiceDetails= salesInvoiceDetailsRepo.save(salesInvoiceDetail);

        System.out.println("saved savedSalesInvoiceDetails service method");
        //updating the Order itself with the updates.
        SalesInvoice salesInvoice = salesInvoiceRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        salesInvoice.setTotalCost(salesInvoiceRepo.findById(parsedInvoiceItemDto.getOrderId()).get().getTotalCost().add(salesInvoiceDetail.getTotalPrice()==null?BigDecimal.ZERO:salesInvoiceDetail.getTotalPrice()));
        salesInvoice.setTotalBeforeDiscount(salesInvoice.getTotalCost().add(salesInvoice.getTaxValue()==null? BigDecimal.ZERO:salesInvoice.getTaxValue()));
        salesInvoiceRepo.save(salesInvoice);

        return savedSalesInvoiceDetails;
    }
    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SalesInvoiceDetail salesInvoiceDetail = salesInvoiceDetailsRepo.findById(id).orElseThrow();
        SalesInvoice salesInvoice= salesInvoiceRepo.findById(salesInvoiceDetail.getSalesInvoice().getId()).orElseThrow();
        return salesInvoice.getIsApproved();
    }
    public boolean checkItemInOrderOrNot(SalesInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : "+parsedInvoiceItemDto.getInvItemCard());
        Optional<SalesInvoiceDetail> salesInvoiceDetails= salesInvoiceDetailsRepo.findBySalesInvoiceIdAndItemIdAndUomId(parsedInvoiceItemDto.getOrderId(),parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (salesInvoiceDetails.isEmpty()){
            return false;
        }else{
            System.out.println(salesInvoiceDetails.get().getSalesInvoice().getId());
            System.out.println(salesInvoiceDetails.get().getItem().getId());
            System.out.println(salesInvoiceDetails.get().getUom().getId());
            return true;
        }
    }
}

