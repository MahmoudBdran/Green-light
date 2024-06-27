package com.erp.greenlight.services;

import com.erp.greenlight.models.InvItemCard;
import com.erp.greenlight.models.InvItemCardBatch;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.repositories.InvItemCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class InvItemCardService {
    @Autowired
    InvItemCardRepo repo;

    @Autowired
    InvItemCardBatchRepo batchRepo;

    public List<InvItemCard> getAllInvItemCards(){
        return repo.findAll();
    }

    public Optional<InvItemCard> getInvItemCardById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }
    public InvItemCard saveInvItemCard(InvItemCard invItemcard){

        if(invItemcard.getParentInvItemCard().getId() == null){
            invItemcard.setParentInvItemCard(null);
        }
        return repo.save(invItemcard);
    }
    public void deleteInvItemCard( Long id){
        repo.deleteById(id);
    }


    public void doUpdateItemCardQuantity(InvItemCard invItemCard, InvItemCardBatch invItemCardBatch){

        // update itemcard Quantity mirror  تحديث المرآه الرئيسية للصنف
        //حنجيب كمية الصنف من جدول الباتشات
        BigDecimal allQuantityINBatches = batchRepo.allQuantityINBatches(invItemCard);

        //كل كمية الصنف بوحده الاب مباشره بدون اي تحويلات مثال  4شكارة وعلبتين


        invItemCard.setAllQUENTITY(allQuantityINBatches);
        if(invItemCard.isDoesHasRetailUnit()){


            //all quantity in reatails  كل الكمية بوحده التجزئة
            //emaple 21 kilo
            BigDecimal QUENTITY_all_Retails= allQuantityINBatches.multiply(invItemCard.getRetailUomQuntToParent());
            // 21kilo  21/10  -> int 2 شكارة


            invItemCard.setQUENTITY(QUENTITY_all_Retails.divide(invItemCard.getRetailUomQuntToParent()));
            //% modelus  21%10  - 1 علبة
            invItemCard.setQUENTITYRetail(QUENTITY_all_Retails.remainder(invItemCard.getRetailUomQuntToParent()));
            invItemCard.setQUENTITYAllRetails(QUENTITY_all_Retails);
        }else{
            invItemCard.setQUENTITY(allQuantityINBatches);
        }
        repo.save(invItemCard);
    }
}
