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
        supplierOrder.setOrderType(SupplierOrderType.RETURN_ON_GENERAL);

        return supplierOrderRepo.save(supplierOrder);
    }

    public SupplierOrder updateSupplierOrderReturn(SupplierOrderDTO supplierOrderDTO) {
        Supplier supplier = supplierRepo.findById(supplierOrderDTO.getSupplier()).orElseThrow();

        SupplierOrder supplierOrder = supplierOrderRepo.findById(supplierOrderDTO.getId()).orElseThrow();

        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
        supplierOrder.setAccount(supplier.getAccount());
        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());

        return supplierOrderRepo.save(supplierOrder);
    }

    @Transactional
    public boolean deleteSupplierOrderReturn(Long id) {


            SupplierOrder parentPillData = supplierOrderRepo.findById(id).orElseThrow();
            Store store = parentPillData.getStore();
            Supplier supplier = parentPillData.getSupplier();

            if (parentPillData.getIsApproved()) {
                throw new InternalServerErrorException("عفوا  لايمكن الحذف بتفاصيل فاتورة معتمده ومؤرشفة");
            }

            //حنجيب الاصناف المضافة علي الفاتورة
            List<SupplierOrderDetails> itemsDetails = parentPillData.getSupplierOrderDetailsItems();

            //حنحذف الفاتورة الاب
            supplierOrderRepo.deleteById(id);


            //حنلف علي الاصناف المضافه علي الفاتورة ونطبق عليهم نفس اللي عملناها في حذف تفاصيل عنصر علي الفاتورة

            for (SupplierOrderDetails item : itemsDetails) {

                SupplierOrderDetailsRepo.deleteById(item.getId());

                InvItemCard itemCardData = item.getInvItemCard();
                InvItemCardBatch batchData = item.getBatch();
                InvUom uom = itemCardData.getUom();


                //خصم الكمية من الباتش
                //كمية الصنف بكل المخازن قبل الحركة

                BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(item.getInvItemCard());
                BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(item.getInvItemCard(), store);

                //هنا حنرجع الكمية لحظيا من باتش الصنف
                //update current Batch تحديث علي الباتش القديمة

                if (item.getUom().isMaster()) {
                    batchData.setQuantity(batchData.getQuantity().add(item.getDeliveredQuantity()));
                } else {
                    //مرجع بالوحده الابن التجزئة فلازم تحولها الي الاب قبل الخصم انتبه !!
                    batchData.setQuantity(batchData.getQuantity().add(item.getDeliveredQuantity().divide(itemCardData.getRetailUomQuntToParent())));
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
            }
        return true;
    }

    @Transactional
    public boolean checkOrderReturnIsApproved(Long id) {
        SupplierOrder SupplierOrder = supplierOrderRepo.findById(id).orElseThrow();
        return SupplierOrder.getIsApproved();
    }


    @Transactional
    public ResponseEntity<Object> approve(ApproveSupplierOrderDTO request) {

        SupplierOrder supplierOrderReturn = supplierOrderRepo.findById(request.getOrderId()).orElseThrow();

        Supplier supplier = supplierOrderReturn.getSupplier();
        Store store = supplierOrderReturn.getStore();

        if (supplierOrderReturn.getIsApproved()) {
            return AppResponse.generateResponse("عفوا لايمكن اعتماد فاتورة معتمده من قبل !!", HttpStatus.OK, null, true);
        }

        BigDecimal totalCost = supplierOrderReturn.getTotalCost();
        BigDecimal totalCostAfterDiscount = calculateTotalAfterDiscount(totalCost, request.getDiscountType(), request.getDiscountValue(), request.getDiscountPercent());

        supplierOrderReturn.setTaxPercent(request.getTaxPercent());
        supplierOrderReturn.setTaxValue(request.getTaxValue());

        supplierOrderReturn.setTotalBeforeDiscount(supplierOrderReturn.getTotalCost());
        supplierOrderReturn.setTotalCost(totalCostAfterDiscount);

        supplierOrderReturn.setDiscountType(request.getDiscountType());
        supplierOrderReturn.setDiscountPercent(request.getDiscountPercent());
        supplierOrderReturn.setDiscountValue(request.getDiscountValue());
        supplierOrderReturn.setPillType(request.getPillType());
        supplierOrderReturn.setIsApproved(Boolean.TRUE);
        supplierOrderReturn.setWhatPaid(request.getWhatPaid());
        supplierOrderReturn.setWhatRemain(request.getWhatRemain());
        supplierOrderReturn.setMoneyForAccount(supplierOrderReturn.getTotalCost().multiply(new BigDecimal(-1)));

        if (request.getPillType() == 1) {
            if (request.getWhatPaid().compareTo(totalCostAfterDiscount) != 0) {
               throw new InternalServerErrorException ("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!");
            }
        }

        if (request.getPillType() == 2) {
            if (request.getWhatPaid().compareTo(totalCostAfterDiscount) == 0) {
                throw new InternalServerErrorException ("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!");
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

        supplierOrderRepo.save(supplierOrderReturn);

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


        return AppResponse.generateResponse("تم اعتماد وترحيل الفاتورة بنجاح ", HttpStatus.OK, supplierOrderReturn, true);
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

