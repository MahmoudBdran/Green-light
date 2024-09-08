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
    public SalesInvoiceReturn saveItemInOrder(SalesReturnInvoiceItemDTO request) {

        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(request.getOrderId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(request.getBatch()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(request.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(request.getUom()).orElseThrow();
        Store store = storeRepo.findById(request.getStore()).orElseThrow();

        String customerName = "طياري";

        if(salesInvoiceReturn.getCustomer()!= null){
            Customer customer = salesInvoiceReturn.getCustomer();
            customerName = customer.getName();
        }

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

        salesInvoiceReturnDetailsRepo.save(dataToInsertToInvoiceDetails);

        List<SalesInvoicesReturnDetails> newDetails = salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(salesInvoiceReturn.getId());


        float totalPrice = 0;
        for (SalesInvoicesReturnDetails details : newDetails) {
            totalPrice += details.getTotalPrice().floatValue();
        }
        salesInvoiceReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        salesInvoiceReturn.setTotalBeforeDiscount(BigDecimal.valueOf(totalPrice));
        salesInvoiceReturnRepo.save(salesInvoiceReturn);




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
        dataInsertInvItemCardMovements.setByan( "نظير مبيعات  للعميل " + " " + customerName + " فاتورة رقم"+" " + salesInvoiceReturn.getId());
        dataInsertInvItemCardMovements.setQuantityBeforMovement( "عدد " +" " + ( quantityBeforeMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMove("عدد " + " " + ( quantityAfterMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityBeforMoveStore("عدد " +" "+( quantityBeforeMoveCurrentStore ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMoveStore( "عدد " + " " + ( quantityAfterMoveCurrentStore ) + " " +  invUom.getName());

        dataInsertInvItemCardMovements.setStore(store);
        //التاثير في حركة كارت الصنف
        invItemcardMovementRepo.save(dataInsertInvItemCardMovements);


        invItemCardService.doUpdateItemCardQuantity(invItemCard, batchData);

        return salesInvoiceReturn ;
    }


    @Transactional
    public void deleteItem(Long id) {
        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
        SalesInvoiceReturn salesInvoiceReturn = salesInvoiceReturnRepo.findById(salesInvoicesReturnDetails.getSalesInvoiceReturn().getId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(salesInvoicesReturnDetails.getBatch().getId()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(salesInvoicesReturnDetails.getItem().getId()).orElseThrow();
        InvUom invUom = invUomRepo.findById(salesInvoicesReturnDetails.getUom().getId()).orElseThrow();
        Store store = storeRepo.findById(salesInvoicesReturnDetails.getStore().getId()).orElseThrow();

        String customerName = "طياري";

        if(salesInvoiceReturn.getCustomer()!= null){
            Customer customer = salesInvoiceReturn.getCustomer();
            customerName = customer.getName();
        }

        salesInvoiceReturnDetailsRepo.deleteById(id);

        List<SalesInvoicesReturnDetails> newDetails = salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnId(salesInvoiceReturn.getId());
        float totalPrice=0;
        for(SalesInvoicesReturnDetails details : newDetails){
            totalPrice+=details.getTotalPrice().floatValue();
        }
        salesInvoiceReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        salesInvoiceReturn.setTotalBeforeDiscount(BigDecimal.valueOf(totalPrice));
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
        dataInsertInvItemCardMovements.setByan( "نظير مبيعات  للعميل " + " " + customerName + " فاتورة رقم"+" " + salesInvoiceReturn.getId());
        dataInsertInvItemCardMovements.setQuantityBeforMovement( "عدد " +" " + ( quantityBeforeMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMove("عدد " + " " + ( quantityAfterMove ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityBeforMoveStore("عدد " +" "+( quantityBeforeMoveCurrentStore ) + " " + invUom.getName());
        dataInsertInvItemCardMovements.setQuantityAfterMoveStore( "عدد " + " " + ( quantityAfterMoveCurrentStore ) + " " +  invUom.getName());

        dataInsertInvItemCardMovements.setStore(store);
        //التاثير في حركة كارت الصنف
        invItemcardMovementRepo.save(dataInsertInvItemCardMovements);


        invItemCardService.doUpdateItemCardQuantity(invItemCard, batchData);
    }

    @Transactional
    public SalesInvoiceReturn deleteItemFromSalesInvoiceReturn(Long id) {

        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
        deleteItem(id);
        return salesInvoiceReturnRepo.findById(salesInvoicesReturnDetails.getSalesInvoiceReturn().getId()).orElseThrow();
    }

    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id){
        SalesInvoicesReturnDetails salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findById(id).orElseThrow();
        SalesInvoiceReturn salesInvoiceReturn= salesInvoiceReturnRepo.findById(salesInvoicesReturnDetails.getSalesInvoiceReturn().getId()).orElseThrow();
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

    @Transactional
    public SalesInvoiceReturn overWriteItemInOrder(SalesReturnInvoiceItemDTO request) throws JsonProcessingException {
        Optional<SalesInvoicesReturnDetails> salesInvoicesReturnDetails = salesInvoiceReturnDetailsRepo.findBySalesInvoiceReturnIdAndItemIdAndUomId(request.getOrderId(), request.getInvItemCard(), request.getUom());


        if(salesInvoicesReturnDetails.isPresent()){
            Long oldItemDetailsId = salesInvoicesReturnDetails.get().getId();
            BigDecimal oldItemQuantity = salesInvoicesReturnDetails.get().getQuantity();
            deleteItem(oldItemDetailsId);
            request.setItemQuantity(oldItemQuantity.add(request.getItemQuantity()));

            return saveItemInOrder(request);
        }
        return null;
    }

}

