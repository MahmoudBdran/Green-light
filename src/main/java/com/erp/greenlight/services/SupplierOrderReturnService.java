package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.ApproveSupplierOrderDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.enums.SupplierOrderType;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class SupplierOrderReturnService {
    @Autowired
    SupplierOrderRepo supplierOrderRepo;

    @Autowired
    TreasuriesTransactionsRepo treasuriesTransactionsRepo;

    @Autowired
    InvItemCardRepo invItemCardRepo;

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
    SupplierOrderDetailsRepo SupplierOrderDetailsRepo;

    @Autowired
    SupplierService supplierService;

    @Autowired
    InvItemCardService invItemCardService;
    SupplierOrderMapper mapper;

    public List<SupplierOrder> getAllSupplierOrdersReturns() {
        return supplierOrderRepo.findAllByOrderType(SupplierOrderType.RETURN_ON_GENERAL);
    }

    public Optional<SupplierOrder> getSupplierOrderReturnById(Long id) {
        return Optional.of(supplierOrderRepo.findById(id).orElseThrow());
    }

    public SupplierOrder saveSupplierOrderReturn(SupplierOrderDTO supplierOrderDTO) {


        Supplier supplier = supplierRepo.findById(supplierOrderDTO.getSupplier()).orElseThrow();
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
        supplierOrder.setAccount(supplier.getAccount());
        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        supplierOrder.setOrderDate(supplierOrder.getOrderDate());
        supplierOrder.setTotalCost(BigDecimal.ZERO);
        supplierOrder.setDiscountValue(BigDecimal.ZERO);
        supplierOrder.setOrderType(SupplierOrderType.RETURN_ON_SAME_PILL);

        return supplierOrderRepo.save(supplierOrder);
    }

    public SupplierOrder updateSupplierOrderReturn(SupplierOrder SupplierOrder) {
        return supplierOrderRepo.save(SupplierOrder);
    }

    @Transactional
    public boolean deleteSupplierOrderReturn(Long id) {
        for (SupplierOrderDetails SupplierOrderDetails : supplierOrderRepo.findById(id).get().getSupplierOrderDetailsItems()) {
            SupplierOrderDetailsRepo.deleteById(SupplierOrderDetails.getId());
        }
        supplierOrderRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderReturnIsApproved(Long id) {
        SupplierOrder SupplierOrder = supplierOrderRepo.findById(id).orElseThrow();
        return SupplierOrder.getIsApproved();
    }


    @Transactional
    public ResponseEntity<Object> approve(ApproveSupplierOrderDTO request) {

        SupplierOrder SupplierOrder = supplierOrderRepo.findById(request.getOrderId()).orElseThrow();

        Supplier supplier = SupplierOrder.getSupplier();
        Store store = SupplierOrder.getStore();

        if (SupplierOrder.getIsApproved()) {
            return AppResponse.generateResponse("عفوا لايمكن اعتماد فاتورة معتمده من قبل !!", HttpStatus.OK, null, true);
        }

        SupplierOrder.setTaxPercent(request.getTaxPercent());
        SupplierOrder.setTaxValue(request.getTaxValue());
        SupplierOrder.setTotalBeforeDiscount(SupplierOrder.getTotalCost());
        SupplierOrder.setDiscountType(request.getDiscountType());
        SupplierOrder.setDiscountPercent(request.getDiscountPercent());
        SupplierOrder.setDiscountValue(request.getDiscountValue());
        SupplierOrder.setPillType(request.getPillType());
        SupplierOrder.setIsApproved(Boolean.TRUE);
        SupplierOrder.setWhatPaid(request.getWhatPaid());
        SupplierOrder.setWhatRemain(request.getWhatRemain());
        SupplierOrder.setMoneyForAccount(SupplierOrder.getTotalCost().multiply(new BigDecimal(-1)));

        if (request.getPillType() == 1) {
            if (!Objects.equals(request.getWhatPaid(), SupplierOrder.getTotalCost())) {
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!", HttpStatus.OK, null, true);
            }
        }

        if (request.getPillType() == 2) {
            if (Objects.equals(request.getWhatPaid(), SupplierOrder.getTotalCost())) {
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!", HttpStatus.OK, null, true);
            }
        }

        if (request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0) {
            if (request.getWhatPaid().compareTo(SupplierOrder.getTotalCost()) > 0) {
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ المدفوع اكبر من اجمالي الفاتورة ", HttpStatus.OK, null, true);
            }

            BigDecimal balance = treasuriesTransactionsRepo.getBalance();
            if (balance.compareTo(request.getWhatPaid()) < 0) {
                return AppResponse.generateResponse("عفوا لاتملتك الان رصيد كافي بخزنة الصرف  لكي تتمكن من اتمام عمليه الصرف ", HttpStatus.OK, null, true);
            }
        }

        supplierOrderRepo.save(SupplierOrder);

        if (request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0) {
            Treasure treasure = treasureRepo.findById(1L).orElseThrow();
            Account account = supplier.getAccount();

            TreasuryTransaction newTreasureTransaction = new TreasuryTransaction();

            newTreasureTransaction.setMoney(request.getWhatPaid());
            newTreasureTransaction.setTreasure(treasure);
            newTreasureTransaction.setMovType(new MovType(9));
            newTreasureTransaction.setMoveDate(LocalDate.from(LocalDateTime.now()));
            newTreasureTransaction.setAccount(account);
            newTreasureTransaction.setIsAccount(Boolean.TRUE);
            newTreasureTransaction.setIsApproved(Boolean.TRUE);
            newTreasureTransaction.setMoneyForAccount(request.getWhatPaid().multiply(new BigDecimal(-1)));
            newTreasureTransaction.setByan("صرف نظير فاتورة مشتريات  رقم" + SupplierOrder.getId());
            newTreasureTransaction.setIsalNumber(1L);
            newTreasureTransaction.setShiftCode(1L);

            treasuriesTransactionsRepo.save(newTreasureTransaction);

            treasure.setLastIsalExchange(newTreasureTransaction.getId());
            treasureRepo.save(treasure);

            supplierService.refreshAccountForSupplier(account, supplier);


        }
        ///////////////////ok

        List<SupplierOrderDetails> items = SupplierOrder.getSupplierOrderDetailsItems();

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
            newMovement.setByan("نظير مشتريات من المورد " + " " + supplier.getName() + " فاتورة رقم" + " " + SupplierOrder.getId());

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

                } else {
                    updateInvItemCard.setCostPrice(item.getUnitPrice().multiply(item.getInvItemCard().getRetailUomQuntToParent()));
                    updateInvItemCard.setCostPriceRetail(item.getUnitPrice());
                }

                invItemCardService.doUpdateItemCardQuantity(item.getInvItemCard(), null);

                invItemCardRepo.save(updateInvItemCard);


            }
        });


        return AppResponse.generateResponse("تم اعتماد وترحيل الفاتورة بنجاح ", HttpStatus.OK, null, true);
    }

}

