package com.erp.greenlight.controllers;


import com.erp.greenlight.DTOs.GetItemBatchDto;
import com.erp.greenlight.DTOs.ItemsBalanceDto;
import com.erp.greenlight.models.InvItemCard;
import com.erp.greenlight.models.InvItemCardBatch;
import com.erp.greenlight.repositories.InvItemCardBatchRepo;
import com.erp.greenlight.repositories.InvUomRepo;
import com.erp.greenlight.services.InvItemCardService;
import com.erp.greenlight.services.StoreService;
import com.erp.greenlight.utils.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/batches")
@CrossOrigin(origins = "http://localhost:4200")

public class BatchesController {

    @Autowired
    InvItemCardBatchRepo invItemCardBatchRepo;

    @Autowired
    StoreService storeService;

    @Autowired
    InvUomRepo invUomRepo;


    @Autowired
    InvItemCardService invItemCardService;
    @GetMapping
    public ResponseEntity<Object> findAll(){

        Map<String, Object> data = new HashMap<>();
        data.put("stores", storeService.findAll());
        data.put("invItems", invItemCardService.getAllInvItemCards());

        List<InvItemCardBatch> batches = invItemCardBatchRepo.findAll();
        List<InvItemCard> items = invItemCardService.getAllInvItemCards();

        List<ItemsBalanceDto> itemsBalanceList= new ArrayList<>();

        items.forEach( i->{
            ItemsBalanceDto row = new ItemsBalanceDto();
            List<InvItemCardBatch> newBatches = invItemCardBatchRepo.findAllByItemId(i.getId());
            row.setItem(i);
            row.setBatches(newBatches);

            itemsBalanceList.add(row);
        });
        data.put("batches", itemsBalanceList);

        return AppResponse.generateResponse("all_batches", HttpStatus.OK, data, true);
    }



    @PostMapping("/getItemBatches")
    public ResponseEntity<Object> getItemBatches(@RequestBody GetItemBatchDto request){


        InvItemCard invItemCard = invItemCardService.getInvItemCardById(request.getInvItemId()).orElseThrow();
        List<InvItemCardBatch> invItemCardBatch = invItemCardBatchRepo.findAllByStoreIdAndItemIdAndInvUomId(request.getStoreId(), invItemCard.getId(),invItemCard.getUom().getId());



        Map<String, Object> data = new HashMap<>();
        data.put("uom", invUomRepo.findById(request.getUomId()));
        data.put("invItemCardBatch",invItemCardBatch);

        return AppResponse.generateResponse("invItemCardBatch", HttpStatus.OK,  data, true);
    }



}
