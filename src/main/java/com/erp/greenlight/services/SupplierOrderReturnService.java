package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.ApproveSupplierOrderDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
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
    SupplierOrderReturnRepo supplierOrderReturnRepo;

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
    SupplierOrderReturnDetailsRepo supplierOrderReturnDetailsRepo;

    @Autowired
    SupplierService supplierService;

    @Autowired
    InvItemCardService invItemCardService;
    SupplierOrderMapper mapper;

    public List<SupplierOrderReturn> getAllSupplierOrdersReturns() {
        return supplierOrderReturnRepo.findAll();
    }

    public Optional<SupplierOrderReturn> getSupplierOrderReturnById(Long id) {
        return Optional.of(supplierOrderReturnRepo.findById(id).orElseThrow());
    }

    public SupplierOrderReturn saveSupplierOrderReturn(SupplierOrderDTO supplierOrderDTO) {


        Supplier supplier = supplierRepo.findById(supplierOrderDTO.getSupplier()).orElseThrow();
        SupplierOrderReturn supplierOrderReturn = new SupplierOrderReturn();
        supplierOrderReturn.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
        supplierOrderReturn.setAccount(supplier.getAccount());
        supplierOrderReturn.setStore(new Store(supplierOrderDTO.getStore()));
        supplierOrderReturn.setNotes(supplierOrderDTO.getNotes());
        supplierOrderReturn.setPillType(supplierOrderDTO.getPillType());
        supplierOrderReturn.setOrderDate(supplierOrderReturn.getOrderDate());
        supplierOrderReturn.setTotalCost(BigDecimal.ZERO);
        supplierOrderReturn.setDiscountValue(BigDecimal.ZERO);
        return supplierOrderReturnRepo.save(supplierOrderReturn);
    }

    public SupplierOrderReturn updateSupplierOrderReturn(SupplierOrderReturn supplierOrderReturn) {
//        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(supplierOrderDTO.getOrderId()).get();
//        supplierOrderReturn.setDocNo(supplierOrderDTO.getDocNo());
//        supplierOrderReturn.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
//        supplierOrderReturn.setStore(new Store(supplierOrderDTO.getStore()));
//        supplierOrderReturn.setNotes(supplierOrderDTO.getNotes());
//        supplierOrderReturn.setPillType(supplierOrderDTO.getPillType());
        return supplierOrderReturnRepo.save(supplierOrderReturn);
    }

    @Transactional
    public boolean deleteSupplierOrderReturn(Long id) {
        for (SupplierOrderReturnDetails supplierOrderReturnDetails : supplierOrderReturnRepo.findById(id).get().getSupplierOrderReturnDetailsItems()) {
            supplierOrderReturnDetailsRepo.deleteById(supplierOrderReturnDetails.getId());
        }
        supplierOrderReturnRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderReturnIsApproved(Long id) {
        SupplierOrderReturn supplierOrderReturn = supplierOrderReturnRepo.findById(id).orElseThrow();
        return supplierOrderReturn.getIsApproved();
    }


    @Transactional
    public ResponseEntity<Object> approve(ApproveSupplierOrderDTO request) {

        SupplierOrderReturn supplierOrderReturn = supplierOrderReturnRepo.findById(request.getOrderId()).orElseThrow();

        Supplier supplier = supplierOrderReturn.getSupplier();
        Store store = supplierOrderReturn.getStore();

        if (supplierOrderReturn.getIsApproved()) {
            return AppResponse.generateResponse("عفوا لايمكن اعتماد فاتورة معتمده من قبل !!", HttpStatus.OK, null, true);
        }

        supplierOrderReturn.setTaxPercent(request.getTaxPercent());
        supplierOrderReturn.setTaxValue(request.getTaxValue());
        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost());
        supplierOrderReturn.setDiscountType(request.getDiscountType());
        supplierOrderReturn.setDiscountPercent(request.getDiscountPercent());
        supplierOrderReturn.setDiscountValue(request.getDiscountValue());
        supplierOrderReturn.setPillType(request.getPillType());
        supplierOrderReturn.setIsApproved(Boolean.TRUE);
        supplierOrderReturn.setWhatPaid(request.getWhatPaid());
        supplierOrderReturn.setWhatRemain(request.getWhatRemain());
        supplierOrderReturn.setMoneyForAccount(supplierOrderReturn.getTotalCost().multiply(new BigDecimal(-1)));

        if (request.getPillType() == 1) {
            if (!Objects.equals(request.getWhatPaid(), supplierOrderReturn.getTotalCost())) {
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!", HttpStatus.OK, null, true);
            }
        }

        if (request.getPillType() == 2) {
            if (Objects.equals(request.getWhatPaid(), supplierOrderReturn.getTotalCost())) {
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!", HttpStatus.OK, null, true);
            }
        }

        if (request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0) {
            if (request.getWhatPaid().compareTo(supplierOrderReturn.getTotalCost()) > 0) {
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ المدفوع اكبر من اجمالي الفاتورة ", HttpStatus.OK, null, true);
            }

            BigDecimal balance = treasuriesTransactionsRepo.getBalance();
            if (balance.compareTo(request.getWhatPaid()) < 0) {
                return AppResponse.generateResponse("عفوا لاتملتك الان رصيد كافي بخزنة الصرف  لكي تتمكن من اتمام عمليه الصرف ", HttpStatus.OK, null, true);
            }
        }

        supplierOrderReturnRepo.save(supplierOrderReturn);

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
            newTreasureTransaction.setByan("صرف نظير فاتورة مشتريات  رقم" + supplierOrderReturn.getId());
            newTreasureTransaction.setIsalNumber(1L);
            newTreasureTransaction.setShiftCode(1L);

            treasuriesTransactionsRepo.save(newTreasureTransaction);

            treasure.setLastIsalExchange(newTreasureTransaction.getId());
            treasureRepo.save(treasure);

            supplierService.refreshAccountForSupplier(account, supplier);


        }
        ///////////////////ok

        List<SupplierOrderReturnDetails> items = supplierOrderReturn.getSupplierOrderReturnDetailsItems();

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
            newMovement.setByan("نظير مشتريات من المورد " + " " + supplier.getName() + " فاتورة رقم" + " " + supplierOrderReturn.getId());

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

