package com.erp.greenlight.services;

import com.erp.greenlight.models.InvItemCard;
import com.erp.greenlight.models.InvItemCardBatch;
import com.erp.greenlight.models.InvItemcardCategory;
import com.erp.greenlight.models.InvUom;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.repositories.InvItemCardCategoryRepo;
import com.erp.greenlight.repositories.InvItemCardRepo;
import com.erp.greenlight.repositories.InvUomRepo;
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

    @Autowired
    InvUomRepo invUomRepo;

    @Autowired
    InvItemCardCategoryRepo invItemCardCategoryRepo;

    public List<InvItemCard> getAllInvItemCards(){
        return repo.findAll();
    }

    public Optional<InvItemCard> getInvItemCardById(@PathVariable Long id){
        return Optional.of(repo.findById(id).get());
    }
    public InvItemCard saveInvItemCard(InvItemCard request){

        InvItemcardCategory invItemcardCategory = invItemCardCategoryRepo.findById(request.getInvItemCategory().getId()).orElseThrow();
        InvUom uom = invUomRepo.findById(request.getUom().getId()).orElseThrow();

        InvItemCard dataToInsert = new InvItemCard();

        dataToInsert.setBarcode(request.getBarcode());
        dataToInsert.setName(request.getName());
        dataToInsert.setItemType(request.getItemType());
        dataToInsert.setInvItemCategory(invItemcardCategory);
        dataToInsert.setUom(uom);
        dataToInsert.setActive(request.isActive());

        dataToInsert.setPrice(request.getPrice());
        dataToInsert.setNosGomlaPrice(request.getNosGomlaPrice());
        dataToInsert.setGomlaPrice(request.getGomlaPrice());
        dataToInsert.setCostPrice(request.getCostPrice());

        dataToInsert.setHasFixcedPrice(request.isHasFixcedPrice());
        dataToInsert.setAllQUENTITY(BigDecimal.ZERO);
        dataToInsert.setAllQUENTITY(BigDecimal.ZERO);
        dataToInsert.setQUENTITY(BigDecimal.ZERO);


        if(request.getParentInvItemCard().getId() == null){
            dataToInsert.setParentInvItemCard(null);
        }
        dataToInsert.setDoesHasRetailUnit(request.isDoesHasRetailUnit());

        if(request.isDoesHasRetailUnit()){
            InvUom retailUom = invUomRepo.findById(request.getRetailUom().getId()).orElseThrow();
            dataToInsert.setRetailUom(retailUom);
            dataToInsert.setRetailUomQuntToParent(request.getRetailUomQuntToParent());

            dataToInsert.setPriceRetail(request.getPriceRetail());
            dataToInsert.setNosGomlaPriceRetail(request.getNosGomlaPriceRetail());
            dataToInsert.setGomlaPriceRetail(request.getGomlaPriceRetail());
            dataToInsert.setCostPriceRetail(request.getCostPriceRetail());

            dataToInsert.setQUENTITY(BigDecimal.ZERO);
            dataToInsert.setQUENTITYAllRetails(BigDecimal.ZERO);
        }

        return repo.save(dataToInsert);

    }
    public void deleteInvItemCard( Long id){
       // repo.deleteById(id);
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
