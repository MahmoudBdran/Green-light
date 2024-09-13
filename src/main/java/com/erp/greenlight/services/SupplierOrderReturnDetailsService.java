package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.ReturnInvoiceItemDTO;
import com.erp.greenlight.DTOs.SalesReturnInvoiceItemDTO;
import com.erp.greenlight.exception.InternalServerErrorException;
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
public class SupplierOrderReturnDetailsService {
    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;
    @Autowired
    SupplierOrderRepo supplierOrderRepo;
    @Autowired
    InvUomRepo invUomRepo;
    @Autowired
    InvItemCardRepo invItemCardRepo;

    @Autowired
    InvItemcardMovementRepo invItemcardMovementRepo;

    @Autowired
    InvItemCardService invItemCardService;

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    public List<SupplierOrderDetails> findByOrderReturnId(Long id) {
        return supplierOrderDetailsRepo.findByOrderId(id);
    }

    @Transactional
    public SupplierOrder saveItemInOrderReturn(ReturnInvoiceItemDTO parsedInvoiceItemDto) {

        SupplierOrder invoiceData = supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(parsedInvoiceItemDto.getBatch()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(parsedInvoiceItemDto.getUom()).orElseThrow();
        Store store = invoiceData.getStore();
        Supplier supplier = invoiceData.getSupplier();

        try{
            if (!invoiceData.getIsApproved()) {



               // if ( invUom.isMaster() && batchData.getQuantity().compareTo(parsedInvoiceItemDto.getItemQuantity()) > 0) {

                    SupplierOrderDetails dataInsertItems = new SupplierOrderDetails();

                    BigDecimal totalPrice = parsedInvoiceItemDto.getItemQuantity().multiply(parsedInvoiceItemDto.getUnitPrice());
                    dataInsertItems.setInvItemCard(invItemCard);
                    dataInsertItems.setUom(invUom);
                    dataInsertItems.setBatch(batchData);
                    dataInsertItems.setDeliveredQuantity(parsedInvoiceItemDto.getItemQuantity());
                    dataInsertItems.setUnitPrice(parsedInvoiceItemDto.getUnitPrice());
                    dataInsertItems.setTotalPrice(totalPrice);
                    dataInsertItems.setIsParentUom(invUom.isMaster());
                    dataInsertItems.setOrder(invoiceData);
                    dataInsertItems.setOrderType(invoiceData.getOrderType());

                    supplierOrderDetailsRepo.save(dataInsertItems);

                    invoiceData.setTotalCost(invoiceData.getTotalCost().add(totalPrice));
                    invoiceData.setTotalBeforeDiscount(invoiceData.getTotalCost());

                    supplierOrderRepo.save(invoiceData);

                    BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);

                    //get Quantity Before any Action  حنجيب كيمة الصنف  بالمخزن المحدد معه   الحالي قبل الحركة
                    BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store);
                    if (invUom.isMaster()) {
                        //حخصم بشكل مباشر لانه بنفس وحده الباتش الاب
                        batchData.setQuantity(batchData.getQuantity().subtract(parsedInvoiceItemDto.getItemQuantity()));
                    } else {
                        //مرجع بالوحده الابن التجزئة فلازم تحولها الي الاب قبل الخصم انتبه !!
                        batchData.setQuantity((batchData.getQuantity().subtract(parsedInvoiceItemDto.getItemQuantity().divide(invItemCard.getRetailUomQuntToParent()))));
                    }

                    batchData.setTotalCostPrice(batchData.getUnitCostPrice().multiply(batchData.getQuantity()));

                    invItemCardBatchRepo.save(batchData);

                    BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(invItemCard);
                    BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(invItemCard, store);

                    InvItemcardMovement dataInsertInvItemCardMovements = new InvItemcardMovement();

                    dataInsertInvItemCardMovements.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(1));
                    dataInsertInvItemCardMovements.setInvItemcardMovementsType(new InvItemcardMovementsType(3));
                    dataInsertInvItemCardMovements.setItem(invItemCard);
                    dataInsertInvItemCardMovements.setByan("نظير مرتجع مشتريات عام الي المورد" + " " + supplier.getName() + " فاتورة رقم" + " " + invoiceData.getId());
                    dataInsertInvItemCardMovements.setQuantityBeforMovement("عدد " + " " + (quantityBeforeMove) + " " + invUom.getName());
                    dataInsertInvItemCardMovements.setQuantityAfterMove("عدد " + " " + (quantityAfterMove) + " " + invUom.getName());
                    dataInsertInvItemCardMovements.setQuantityBeforMoveStore("عدد " + " " + (quantityBeforeMoveCurrentStore) + " " + invUom.getName());
                    dataInsertInvItemCardMovements.setQuantityAfterMoveStore("عدد " + " " + (quantityAfterMoveCurrentStore) + " " + invUom.getName());
                    dataInsertInvItemCardMovements.setStore(store);

                    invItemcardMovementRepo.save(dataInsertInvItemCardMovements);
                    invItemCardService.doUpdateItemCardQuantity(invItemCard, batchData);

                    return invoiceData;

                //}

            }
        }catch (Exception ex){
            throw  new InternalServerErrorException("error");
        }


        throw  new InternalServerErrorException("error");

    }


    @Transactional
    public SupplierOrder deleteItemFromSupplierOrderReturn(Long id) {


        SupplierOrderDetails item = supplierOrderDetailsRepo.findById(id).orElseThrow();
        SupplierOrder parentPillData = item.getOrder();
        Store store = parentPillData.getStore();

        if (parentPillData.getIsApproved()) {
            throw new InternalServerErrorException("عفوا  لايمكن الحذف بتفاصيل فاتورة معتمده ومؤرشفة");
        }

        BigDecimal totalCost = parentPillData.getTotalCost();
        parentPillData.setTotalCost(totalCost.subtract(item.getTotalPrice()));
        parentPillData.setTotalBeforeDiscount(totalCost.subtract(item.getTotalPrice()));

        supplierOrderRepo.save(parentPillData);
        supplierOrderDetailsRepo.deleteById(id);

        InvItemCard itemCardData = item.getInvItemCard();
        InvItemCardBatch batchData = item.getBatch();
        InvUom uom = item.getUom();
        Supplier supplier = parentPillData.getSupplier();


        BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(itemCardData);

        BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(itemCardData, store);

        if (uom.isMaster()) {
            //حخصم بشكل مباشر لانه بنفس وحده الباتش الاب
            batchData.setQuantity(batchData.getQuantity().add(item.getDeliveredQuantity()));
        } else {
            //مرجع بالوحده الابن التجزئة فلازم تحولها الي الاب قبل الخصم انتبه !!
            batchData.setQuantity((batchData.getQuantity().add(item.getDeliveredQuantity().divide(itemCardData.getRetailUomQuntToParent()))));
        }
        batchData.setTotalCostPrice(batchData.getUnitCostPrice().multiply(batchData.getQuantity()));
        invItemCardBatchRepo.save(batchData);


        BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(item.getInvItemCard());
        BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(item.getInvItemCard(), store);

        InvItemcardMovement newMovement = new InvItemcardMovement();

        newMovement.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(1));
        newMovement.setInvItemcardMovementsType(new InvItemcardMovementsType(3));
        newMovement.setItem(item.getInvItemCard());
        newMovement.setByan(" نظير حذف سطر الصنف من فاتورة مرتجع مشتريات عام   الي المورد " + " " + supplier.getName() + " فاتورة رقم" + " " + parentPillData.getId());
        newMovement.setQuantityBeforMovement("عدد " + " " + quantityBeforeMove + " " + uom.getName());
        newMovement.setQuantityAfterMove("عدد " + " " + quantityAfterMove + " " + uom.getName());
        newMovement.setQuantityBeforMoveStore("عدد " + " " + quantityBeforeMoveCurrentStore + " " + uom.getName());
        newMovement.setQuantityAfterMoveStore("عدد " + " " + quantityAfterMoveCurrentStore + " " + uom.getName());
        newMovement.setStore(store);

        invItemcardMovementRepo.save(newMovement);

        invItemCardService.doUpdateItemCardQuantity(itemCardData, batchData);

        return parentPillData;
    }

    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id) {
        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(supplierOrderReturnDetails.getOrder().getId()).orElseThrow();
        return supplierOrderReturn.getIsApproved();
    }

    public boolean checkItemInOrder(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : " + parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : " + parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : " + parsedInvoiceItemDto.getInvItemCard());
        Optional<SupplierOrderDetails> supplierOrderReturnDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCardIdAndUomId(parsedInvoiceItemDto.getOrderId(), parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (supplierOrderReturnDetails.isEmpty()) {
            return false;
        } else {
            System.out.println(supplierOrderReturnDetails.get().getOrder().getId());
            System.out.println(supplierOrderReturnDetails.get().getInvItemCard().getId());
            System.out.println(supplierOrderReturnDetails.get().getUom().getId());
            return true;
        }
    }

    @Transactional
    public SupplierOrder overWriteItemInOrder(ReturnInvoiceItemDTO request) throws JsonProcessingException {
        Optional<SupplierOrderDetails> supplierOrderReturnDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCardIdAndUomId(request.getOrderId(), request.getInvItemCard(), request.getUom());


        if(supplierOrderReturnDetails.isPresent()){
            Long oldItemDetailsId = supplierOrderReturnDetails.get().getId();
            BigDecimal oldItemQuantity = supplierOrderReturnDetails.get().getDeliveredQuantity();
            deleteItemFromSupplierOrderReturn(oldItemDetailsId);
            request.setItemQuantity(oldItemQuantity.add(request.getItemQuantity()));

            return saveItemInOrderReturn(request);
        }
        return null;
    }
}

