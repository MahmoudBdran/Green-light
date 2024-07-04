package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.ApproveSupplierOrderDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.enums.SupplierOrderType;
import com.erp.greenlight.exception.InternalServerErrorException;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierOrderService {
    @Autowired
    SupplierOrderRepo supplierOrderRepo;

    @Autowired
    TreasuriesTransactionsRepo treasuriesTransactionsRepo;

    @Autowired
    InvItemCardRepo invItemCardRepo;

    @Autowired
    InvItemCardService invItemCardService;

    @Autowired
    TreasureRepo treasureRepo;

    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    @Autowired
    InvItemcardMovementRepo invItemcardMovementRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    SupplierOrderDetailsRepo supplierOrderDetailsRepo;

    @Autowired
    SupplierService supplierService;

    @Autowired
    StoreRepo storeRepo;
    SupplierOrderMapper mapper;

    public List<SupplierOrder> getAllSupplierOrders() {
        return supplierOrderRepo.findAllByOrderType(SupplierOrderType.BURSHASE);
    }

    public Optional<SupplierOrder> getSupplierOrderById(Long id) {
        return Optional.of(supplierOrderRepo.findById(id).get());
    }

    public SupplierOrder saveSupplierOrder(SupplierOrderDTO supplierOrderDTO) {


        Store store = storeRepo.findById(supplierOrderDTO.getStore()).orElseThrow();
        Supplier supplier = supplierRepo.findById(supplierOrderDTO.getSupplier()).orElseThrow();
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
        supplierOrder.setSupplier(supplier);
        supplierOrder.setAccount(supplier.getAccount());
        supplierOrder.setStore(store);
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        supplierOrder.setOrderDate(supplierOrder.getOrderDate());
        supplierOrder.setTotalCost(BigDecimal.ZERO);
        supplierOrder.setDiscountType((byte) 0);
        supplierOrder.setDiscountValue(BigDecimal.ZERO);
        supplierOrder.setDiscountPercent(BigDecimal.ZERO);
         supplierOrder.setOrderType(SupplierOrderType.BURSHASE);
        return supplierOrderRepo.save(supplierOrder);
    }

    public SupplierOrder updateSupplierOrder(SupplierOrderDTO supplierOrderDTO) {


        Store store = storeRepo.findById(supplierOrderDTO.getStore()).orElseThrow();
        Supplier supplier = supplierRepo.findById(supplierOrderDTO.getSupplier()).orElseThrow();
        SupplierOrder supplierOrder = supplierOrderRepo.findById(supplierOrderDTO.getId()).orElseThrow();

        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
        supplierOrder.setSupplier(supplier);
        supplierOrder.setAccount(supplier.getAccount());
        supplierOrder.setStore(store);
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        supplierOrder.setOrderDate(supplierOrder.getOrderDate());
        supplierOrder.setOrderType(SupplierOrderType.BURSHASE);
        return supplierOrderRepo.save(supplierOrder);
    }

    @Transactional
    public boolean deleteSupplierOrder(Long id) {
        for (SupplierOrderDetails supplierOrderDetails : supplierOrderRepo.findById(id).get().getSupplierOrderDetailsItems()) {
            supplierOrderDetailsRepo.deleteById(supplierOrderDetails.getId());
        }
        supplierOrderRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id) {
        SupplierOrder supplierOrder = supplierOrderRepo.findById(id).orElseThrow();
        return supplierOrder.getIsApproved();
    }


    @Transactional
    public ResponseEntity<Object> approve(ApproveSupplierOrderDTO request) {

        SupplierOrder supplierOrder = supplierOrderRepo.findById(request.getOrderId()).orElseThrow();

        Supplier supplier = supplierOrder.getSupplier();
        Store store = supplierOrder.getStore();

        if (supplierOrder.getIsApproved()) {
            return AppResponse.generateResponse("عفوا لايمكن اعتماد فاتورة معتمده من قبل !!", HttpStatus.OK, null, true);
        }

        BigDecimal totalCost = supplierOrder.getTotalCost();
        BigDecimal totalCostAfterDiscount = calculateTotalAfterDiscount(totalCost, request.getDiscountType(), request.getDiscountValue(), request.getDiscountPercent());


        supplierOrder.setTaxPercent(request.getTaxPercent());
        supplierOrder.setTaxValue(request.getTaxValue());

        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost());
        supplierOrder.setTotalCost(totalCostAfterDiscount);
        supplierOrder.setDiscountType(request.getDiscountType());
        supplierOrder.setDiscountPercent(request.getDiscountPercent());
        supplierOrder.setDiscountValue(request.getDiscountValue());

        supplierOrder.setPillType(request.getPillType());
        supplierOrder.setWhatPaid(request.getWhatPaid());
        supplierOrder.setWhatRemain(request.getWhatRemain());
        supplierOrder.setMoneyForAccount(supplierOrder.getTotalCost().multiply(new BigDecimal(-1)));

        if (request.getPillType() == 1) {
            if (request.getWhatPaid().compareTo(totalCostAfterDiscount) != 0) {

                throw new InternalServerErrorException("عفوا يجب ان يكون المبلغ بالكامل مدفوع في حالة الفاتورة كاش !!");
             }
        }

        if (request.getPillType() == 2) {
            if (request.getWhatPaid().compareTo(totalCostAfterDiscount) == 0) {
                throw new InternalServerErrorException("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !");
            }
        }

        if (request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0) {
            if (request.getWhatPaid().compareTo(totalCostAfterDiscount) > 0) {
                throw new InternalServerErrorException("عفوا يجب ان لايكون المبلغ المدفوع اكبر من اجمالي الفاتورة");
            }

            BigDecimal balance = treasuriesTransactionsRepo.getBalance();
            if (balance.compareTo(request.getWhatPaid()) < 0) {
                throw new InternalServerErrorException("عفوا يجب ان لايكون المبلغ المدفوع اكبر من اجمالي الفاتورة");
            }
        }
        supplierOrder.setIsApproved(Boolean.TRUE);
        supplierOrderRepo.save(supplierOrder);

        if (request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0) {
            Treasure treasure = treasureRepo.findById(1L).orElseThrow();
            Account account = supplier.getAccount();

            TreasuryTransaction newTreasureTransaction = new TreasuryTransaction();

            newTreasureTransaction.setMoney(request.getWhatPaid().multiply(new BigDecimal(-1)));
            newTreasureTransaction.setTreasure(treasure);
            newTreasureTransaction.setMovType(new MovType(9));
            newTreasureTransaction.setMoveDate(LocalDate.from(LocalDateTime.now()));
            newTreasureTransaction.setAccount(account);
            newTreasureTransaction.setIsAccount(Boolean.TRUE);
            newTreasureTransaction.setIsApproved(Boolean.TRUE);
            newTreasureTransaction.setMoneyForAccount(request.getWhatPaid());
            newTreasureTransaction.setByan("صرف نظير فاتورة مشتريات  رقم" + supplierOrder.getId());
            newTreasureTransaction.setIsalNumber(1L);
            newTreasureTransaction.setShiftCode(1L);

            treasuriesTransactionsRepo.save(newTreasureTransaction);

            treasure.setLastIsalExchange(newTreasureTransaction.getId());
            treasureRepo.save(treasure);

            supplierService.refreshAccountForSupplier(account, supplier);
        }
        ///////////////////ok

        List<SupplierOrderDetails> items = supplierOrder.getSupplierOrderDetailsItems();

        items.forEach(item -> {

            BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(item.getInvItemCard());
            BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(item.getInvItemCard(), store);

            InvUom uom = item.getUom();

            BigDecimal quantity = null;
            BigDecimal unitPrice = null;

            if (item.getIsParentUom()) {
                quantity = item.getDeliveredQuantity();
                unitPrice = item.getUnitPrice();
            } else {
                quantity = (item.getDeliveredQuantity().divide(item.getInvItemCard().getRetailUomQuntToParent()));
                unitPrice = item.getUnitPrice().multiply(item.getInvItemCard().getRetailUomQuntToParent());
            }


            InvItemCardBatch newInvItemCardBatch = new InvItemCardBatch();

            newInvItemCardBatch.setStore(store);
            newInvItemCardBatch.setItem(item.getInvItemCard());
            newInvItemCardBatch.setQuantity(quantity);
            newInvItemCardBatch.setUnitCostPrice(unitPrice);
            newInvItemCardBatch.setInvUom(uom);

            List<InvItemCardBatch> oldBatches = invItemCardBatchRepo.findAllByStoreIdAndItemIdAndInvUomIdAndUnitCostPriceAndQuantity(store.getId(), item.getInvItemCard().getId(), uom.getId(), unitPrice, quantity);

            if (!oldBatches.isEmpty()) {
                InvItemCardBatch updateInvItemCardBatch = oldBatches.get(0);
                updateInvItemCardBatch.setQuantity(quantity);
                updateInvItemCardBatch.setTotalCostPrice(updateInvItemCardBatch.getUnitCostPrice().multiply(updateInvItemCardBatch.getQuantity()));

                invItemCardBatchRepo.save(updateInvItemCardBatch);
            } else {
                newInvItemCardBatch.setTotalCostPrice(item.getTotalPrice());
                invItemCardBatchRepo.save(newInvItemCardBatch);
            }


            BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(item.getInvItemCard());
            BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(item.getInvItemCard(), store);


            InvItemcardMovement newMovement = new InvItemcardMovement();

            newMovement.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(1));
            newMovement.setInvItemcardMovementsType(new InvItemcardMovementsType(1));
            newMovement.setItem(item.getInvItemCard());
            newMovement.setByan("نظير مشتريات من المورد " + " " + supplier.getName() + " فاتورة رقم" + " " + supplierOrder.getId());

            newMovement.setQuantityBeforMovement("عدد " + " " + quantityBeforeMove + " " + uom.getName());
            newMovement.setQuantityAfterMove("عدد " + " " + quantityAfterMove + " " + uom.getName());


            newMovement.setQuantityBeforMoveStore("عدد " + " " + quantityBeforeMoveCurrentStore + " " + uom.getName());
            newMovement.setQuantityAfterMoveStore("عدد " + " " + quantityAfterMoveCurrentStore + " " + uom.getName());
            newMovement.setStore(store);


            invItemcardMovementRepo.save(newMovement);

            InvItemCard updateInvItemCard = item.getInvItemCard();


            //update last Cost price   تحديث اخر سعر شراء للصنف
            if (item.getIsParentUom()) {
                updateInvItemCard.setCostPrice(item.getUnitPrice());
                if (item.getInvItemCard().isDoesHasRetailUnit()) {
                    updateInvItemCard.setCostPriceRetail(item.getUnitPrice().divide(item.getInvItemCard().getRetailUomQuntToParent(), 2, RoundingMode.CEILING));
                }
            } else {
                updateInvItemCard.setCostPrice(item.getUnitPrice().multiply(item.getInvItemCard().getRetailUomQuntToParent()));
                updateInvItemCard.setCostPriceRetail(item.getUnitPrice());
            }

            invItemCardRepo.save(updateInvItemCard);

            invItemCardService.doUpdateItemCardQuantity(updateInvItemCard, null);


        });


        return AppResponse.generateResponse("تم اعتماد وترحيل الفاتورة بنجاح ", HttpStatus.OK, supplierOrder, true);
    }


    private BigDecimal calculateTotalAfterDiscount(BigDecimal invoiceTotal, Byte discountType, BigDecimal discountValue, BigDecimal discountPercentage) {
        if (discountType == 0) {
            return invoiceTotal;
        } else if (discountType == 1) {
            return invoiceTotal.subtract(discountPercentage.divide(new BigDecimal(100)).multiply(invoiceTotal));
        } else if (discountType == 2) {
            return invoiceTotal.subtract(discountValue);
        }else {
            return null;
        }
    }

}

