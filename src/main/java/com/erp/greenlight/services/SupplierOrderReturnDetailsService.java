package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.ReturnInvoiceItemDTO;
import com.erp.greenlight.enums.SupplierOrderType;
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
    public SupplierOrder saveItemInOrderReturn(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrder invoiceData = supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        InvItemCardBatch batchData = invItemCardBatchRepo.findById(parsedInvoiceItemDto.getBatch()).orElseThrow();
        InvItemCard invItemCard = invItemCardRepo.findById(parsedInvoiceItemDto.getInvItemCard()).orElseThrow();
        InvUom invUom = invUomRepo.findById(parsedInvoiceItemDto.getUom()).orElseThrow();
        Store store = invoiceData.getStore();
        Supplier supplier = invoiceData.getSupplier();


        if (!invoiceData.getIsApproved()) {
            if (batchData.getQuantity().compareTo(parsedInvoiceItemDto.getItemQuantity()) > 0) {

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

                supplierOrderDetailsRepo.save(dataInsertItems);

                invoiceData.setTotalCost(invoiceData.getTotalCost().add(totalPrice));
                invoiceData.setTotalBeforeDiscount(invoiceData.getTotalCost());

                supplierOrderRepo.save(invoiceData);

            }
        }

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
    }

    @Transactional
    public SupplierOrderDetails updateItemBeingInsertedAgain(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {

        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(), parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom()).orElseThrow();
        supplierOrderReturnDetails.setDeliveredQuantity(supplierOrderReturnDetails.getDeliveredQuantity().add(parsedInvoiceItemDto.getItemQuantity()));
        supplierOrderReturnDetails.setTotalPrice(supplierOrderReturnDetails.getUnitPrice().multiply(supplierOrderReturnDetails.getDeliveredQuantity()));
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself

        float totalPrice = 0;
        for (SupplierOrderDetails details : supplierOrderDetailsRepo.findByOrderId(parsedInvoiceItemDto.getOrderId())) {
            System.out.println(details.getTotalPrice());
            totalPrice += details.getTotalPrice().floatValue();
        }

        System.out.println("totalPrice : " + totalPrice);
        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(parsedInvoiceItemDto.getOrderId()).orElseThrow();
        supplierOrderReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        System.out.println("totalPrice in obj : " + supplierOrderReturn.getTotalCost());
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue() == null ? BigDecimal.ZERO : supplierOrderReturn.getTaxValue()));
        System.out.println("totalPrice : " + supplierOrderReturn.getTotalBeforeDiscount());
        System.out.println("totalPrice : " + supplierOrderReturn.getTaxValue());
        supplierOrderRepo.save(supplierOrderReturn);
        totalPrice = 0;
        return supplierOrderDetailsRepo.save(supplierOrderReturnDetails);
    }

    @Transactional
    public List<SupplierOrderDetails> deleteItemFromSupplierOrderReturn(Long id) {
        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        //map from DTO to the Original Entity
        //calculate the total price for the supplier order itself
        supplierOrderDetailsRepo.deleteById(id);
        float totalPrice = 0;
        for (SupplierOrderDetails details : supplierOrderDetailsRepo.findByOrderId(supplierOrderReturnDetails.getOrder().getId())) {
            totalPrice += details.getTotalPrice().floatValue();
        }
        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(supplierOrderReturnDetails.getOrder().getId()).orElseThrow();
        supplierOrderReturn.setTotalCost(BigDecimal.valueOf(totalPrice));
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost().add(supplierOrderReturn.getTaxValue() == null ? BigDecimal.ZERO : supplierOrderReturn.getTaxValue()));
        totalPrice = 0;
        supplierOrderRepo.save(supplierOrderReturn);
        return supplierOrderReturn.getSupplierOrderDetailsItems();
    }

    @Transactional
    public boolean checkOrderDetailsItemIsApproved(Long id) {
        SupplierOrderDetails supplierOrderReturnDetails = supplierOrderDetailsRepo.findById(id).orElseThrow();
        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(supplierOrderReturnDetails.getOrder().getId()).orElseThrow();
        return supplierOrderReturn.getIsApproved();
    }

    public boolean checkItemInOrderOrNot(ReturnInvoiceItemDTO parsedInvoiceItemDto) throws JsonProcessingException {
        System.out.println("entered checkItemInORderOrNot method");
        System.out.println("parsedInvoiceItemDto : " + parsedInvoiceItemDto.getOrderId());
        System.out.println("parsedInvoiceItemDto : " + parsedInvoiceItemDto.getUom());
        System.out.println("parsedInvoiceItemDto : " + parsedInvoiceItemDto.getInvItemCard());
        Optional<SupplierOrderDetails> supplierOrderReturnDetails = supplierOrderDetailsRepo.findByOrderIdAndInvItemCard_IdAndUomId(parsedInvoiceItemDto.getOrderId(), parsedInvoiceItemDto.getInvItemCard(), parsedInvoiceItemDto.getUom());
        if (supplierOrderReturnDetails.isEmpty()) {
            return false;
        } else {
            System.out.println(supplierOrderReturnDetails.get().getOrder().getId());
            System.out.println(supplierOrderReturnDetails.get().getInvItemCard().getId());
            System.out.println(supplierOrderReturnDetails.get().getUom().getId());
            return true;
        }
    }
}

