package com.erp.greenlight.services;

import com.erp.greenlight.DTOs.ApproveSupplierOrderDTO;
import com.erp.greenlight.DTOs.InvoiceItemDTO;
import com.erp.greenlight.DTOs.SupplierOrderDTO;
import com.erp.greenlight.mappers.SupplierOrderMapper;
import com.erp.greenlight.models.*;
import com.erp.greenlight.repositories.*;
import com.erp.greenlight.utils.AppResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.beans.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     SupplierOrderMapper mapper;
    public List<SupplierOrder> getAllSupplierOrders(){
        return supplierOrderRepo.findAll();
    }
    public Optional<SupplierOrder> getSupplierOrderById( Long id){
        return Optional.of(supplierOrderRepo.findById(id).get());
    }
    public SupplierOrder saveSupplierOrder(SupplierOrderDTO supplierOrderDTO){


        Supplier supplier = supplierRepo.findById(supplierOrderDTO.getSupplier()).orElseThrow();
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
        supplierOrder.setAccount(supplier.getAccount());
        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
        supplierOrder.setNotes(supplierOrderDTO.getNotes());
        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        supplierOrder.setOrderDate(supplierOrder.getOrderDate());
        supplierOrder.setTotalCost(BigDecimal.ZERO);
        supplierOrder.setDiscountValue(BigDecimal.ZERO);
        return supplierOrderRepo.save(supplierOrder);
    }

    public SupplierOrder updateSupplierOrder(SupplierOrder supplierOrder){
//        SupplierOrder supplierOrder = supplierOrderRepo.findById(supplierOrderDTO.getOrderId()).get();
//        supplierOrder.setDocNo(supplierOrderDTO.getDocNo());
//        supplierOrder.setSupplier(new Supplier(supplierOrderDTO.getSupplier()));
//        supplierOrder.setStore(new Store(supplierOrderDTO.getStore()));
//        supplierOrder.setNotes(supplierOrderDTO.getNotes());
//        supplierOrder.setPillType(supplierOrderDTO.getPillType());
        return supplierOrderRepo.save(supplierOrder);
    }
    @Transactional
    public boolean deleteSupplierOrder(Long id){
         for(SupplierOrderDetails supplierOrderDetails: supplierOrderRepo.findById(id).get().getSupplierOrderDetailsItems()){
             supplierOrderDetailsRepo.deleteById(supplierOrderDetails.getId());
         }
         supplierOrderRepo.deleteById(id);
        return true;

    }

    @Transactional
    public boolean checkOrderIsApproved(Long id){
        SupplierOrder supplierOrder=supplierOrderRepo.findById(id).orElseThrow();
       return supplierOrder.getIsApproved();
    }


    @Transactional
    public ResponseEntity<Object> approve(ApproveSupplierOrderDTO request){

        SupplierOrder supplierOrder = supplierOrderRepo.findById(request.getOrderId()).orElseThrow();

        Supplier supplier = supplierOrder.getSupplier();
        Store store = supplierOrder.getStore();

        if(supplierOrder.getIsApproved()){
            return AppResponse.generateResponse("عفوا لايمكن اعتماد فاتورة معتمده من قبل !!", HttpStatus.OK, null , true);
        }

        supplierOrder.setTaxPercent(request.getTaxPercent());
        supplierOrder.setTaxValue(request.getTaxValue());
        supplierOrder.setTotalBeforeDiscount(supplierOrder.getTotalCost());
        supplierOrder.setDiscountType(request.getDiscountType());
        supplierOrder.setDiscountPercent(request.getDiscountPercent());
        supplierOrder.setDiscountValue(request.getDiscountValue());
        supplierOrder.setPillType(request.getPillType());
        supplierOrder.setIsApproved(Boolean.TRUE);
        supplierOrder.setWhatPaid(request.getWhatPaid());
        supplierOrder.setWhatRemain(request.getWhatRemain());
        supplierOrder.setMoneyForAccount(supplierOrder.getTotalCost().multiply(new BigDecimal(-1)) );

        if(request.getPillType() ==1){
            if(!Objects.equals(request.getWhatPaid(), supplierOrder.getTotalCost())){
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!", HttpStatus.OK, null , true);
            }
        }

        if(request.getPillType() ==2){
            if(Objects.equals(request.getWhatPaid(), supplierOrder.getTotalCost())){
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ بالكامل مدفوع في حالة الفاتورة اجل !!", HttpStatus.OK, null , true);
            }
        }

        if(request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0){
            if (request.getWhatPaid().compareTo(supplierOrder.getTotalCost()) > 0) {
                return AppResponse.generateResponse("عفوا يجب ان لايكون المبلغ المدفوع اكبر من اجمالي الفاتورة ", HttpStatus.OK, null , true);
            }

            BigDecimal balance = treasuriesTransactionsRepo.getBalance();
            if (balance.compareTo(request.getWhatPaid()) < 0) {
                return AppResponse.generateResponse("عفوا لاتملتك الان رصيد كافي بخزنة الصرف  لكي تتمكن من اتمام عمليه الصرف ", HttpStatus.OK, null , true);
            }
        }

        supplierOrderRepo.save(supplierOrder);

        if(request.getWhatPaid().compareTo(BigDecimal.ZERO) > 0){
            Treasure treasure = treasureRepo.findById(1L).orElseThrow();
            Account account = supplier.getAccount();

            TreasuryTransaction newTreasureTransaction = new TreasuryTransaction();

            newTreasureTransaction.setMoney(request.getWhatPaid());
            newTreasureTransaction.setTreasure(treasure);
            newTreasureTransaction.setMovType(9);
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

            if(account.getAccountType().getId() == 2L){
                BigDecimal the_net_in_suppliers_with_orders = supplierOrderRepo.getNet(account);
                BigDecimal the_net_in_treasuries_transactions = treasuriesTransactionsRepo.getNet(account);

                BigDecimal finalBalance =account.getStartBalance()
                        .add(the_net_in_treasuries_transactions)
                        .add(the_net_in_suppliers_with_orders);

                supplier.setCurrentBalance(finalBalance);
                supplierRepo.save(supplier);

                account.setCurrentBalance(finalBalance);
                accountRepo.save(account);
            }
        }
        ///////////////////ok

        List<SupplierOrderDetails> items = supplierOrder.getSupplierOrderDetailsItems();

        items.forEach( item->{

            BigDecimal quantityBeforeMove = invItemCardBatchRepo.getQuantityBeforeMove(item.getInvItemCard());
            BigDecimal quantityBeforeMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(item.getInvItemCard(), store);

            InvUom uom = item.getUom();

            BigDecimal quantity = null;
            BigDecimal unitPrice = null;

            if(item.getIsParentUom()){
                quantity = item.getDeliveredQuantity();
                unitPrice = item.getUnitPrice();
            }else{
                quantity = ( item.getDeliveredQuantity().divide(item.getInvItemCard().getRetailUomQuntToParent())) ;
                unitPrice = item.getUnitPrice().multiply(item.getInvItemCard().getRetailUomQuntToParent());
            }


            InvItemCardBatch newInvItemCardBatch  = new InvItemCardBatch();

            newInvItemCardBatch.setStore(store);
            newInvItemCardBatch.setItem(item.getInvItemCard());
            newInvItemCardBatch.setQuantity(quantity);
            newInvItemCardBatch.setUnitCostPrice(unitPrice);
            newInvItemCardBatch.setInvUom(uom);

            List<InvItemCardBatch> oldBatches = invItemCardBatchRepo.findAllByStoreIdAndItemIdAndInvUomIdAndUnitCostPriceAndQuantity(store.getId(), item.getInvItemCard().getId(), uom.getId(), unitPrice,quantity);

            if (!oldBatches.isEmpty()){
                InvItemCardBatch updateInvItemCardBatch  = oldBatches.get(0);
                updateInvItemCardBatch.setQuantity(quantity);
                updateInvItemCardBatch.setTotalCostPrice(updateInvItemCardBatch.getUnitCostPrice().multiply(updateInvItemCardBatch.getQuantity()));

                invItemCardBatchRepo.save(updateInvItemCardBatch);
            }else{
                newInvItemCardBatch.setTotalCostPrice(item.getTotalPrice());
                invItemCardBatchRepo.save(newInvItemCardBatch);
            }


            BigDecimal quantityAfterMove = invItemCardBatchRepo.getQuantityBeforeMove(item.getInvItemCard());
            BigDecimal quantityAfterMoveCurrentStore = invItemCardBatchRepo.getQuantityBeforeMoveCurrentStore(item.getInvItemCard(), store);



            InvItemcardMovement newMovement = new InvItemcardMovement();

            newMovement.setInvItemcardMovementsCategory(new InvItemcardMovementsCategory(1));
            newMovement.setInvItemcardMovementsType(new InvItemcardMovementsType(1));
            newMovement.setItem(item.getInvItemCard());
            newMovement.setByan("نظير مشتريات من المورد " +" " + supplier.getName() + " فاتورة رقم" + " " + supplierOrder.getId());

            newMovement.setQuantityBeforMovement("عدد " + " " + quantityBeforeMove  + " " + uom.getName());
            newMovement.setQuantityAfterMove("عدد " + " " + quantityAfterMove  + " " + uom.getName());


            newMovement.setQuantityBeforMoveStore("عدد " + " " + quantityBeforeMoveCurrentStore  + " " + uom.getName());
            newMovement.setQuantityAfterMoveStore("عدد " + " " + quantityAfterMoveCurrentStore  + " " + uom.getName());
            newMovement.setStore(store);


            invItemcardMovementRepo.save(newMovement);

            InvItemCard updateInvItemCard =item.getInvItemCard();


            //update last Cost price   تحديث اخر سعر شراء للصنف
            if (item.getIsParentUom()) {
                updateInvItemCard.setCostPrice(item.getUnitPrice());
                if (item.getInvItemCard().isDoesHasRetailUnit()) {

                    updateInvItemCard.setCostPriceRetail(item.getUnitPrice().divide(item.getInvItemCard().getRetailUomQuntToParent(), 2, RoundingMode.CEILING));

                } else {
                    updateInvItemCard.setCostPrice(item.getUnitPrice().multiply(item.getInvItemCard().getRetailUomQuntToParent()));
                    updateInvItemCard.setCostPriceRetail(item.getUnitPrice());
                }

                invItemCardRepo.save(updateInvItemCard);

            }
            });




        return AppResponse.generateResponse("تم اعتماد وترحيل الفاتورة بنجاح ", HttpStatus.OK, null , true);
    }

}

