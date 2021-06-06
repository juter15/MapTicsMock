package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequest;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.store.StoreList;
import com.maptics.mock.mapticsmock.dto.request.store.StoreRequest;
import com.maptics.mock.mapticsmock.dto.request.store.StoreRequestDetail;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponse;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.storeresponse.StoreResponse;
import com.maptics.mock.mapticsmock.dto.response.storeresponse.StoreResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.storeresponse.StoreResponseList;
import com.maptics.mock.mapticsmock.dto.response.storeresponse.StoreResponseListDetail;
import com.maptics.mock.mapticsmock.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v2/maptics/bizniz/store")
public class StoreContoller {

    private final ResultService resultService;

    List<StoreRequestDetail> storeRequestDetailList = new CopyOnWriteArrayList<>();

    @PostMapping
    public ResponseEntity StoreCreate(
            @RequestHeader String Authorization,
            @RequestBody StoreRequest storeRequest
    ){
            log.info("create storeRequest : {}", storeRequest);
            log.info("storeRequestDetailList : {}", storeRequestDetailList);
            List<StoreResponseDetail> storeResponseDetailList = new ArrayList<>();

            StoreResponseDetail setResponseDetail = new StoreResponseDetail();
            if(storeRequestDetailList.isEmpty()){
                int i = 0;
                for(StoreRequestDetail create : storeRequest.getStoreRequestDetailList()) {
                        storeRequestDetailList.add(create);

                        setResponseDetail.setStore_id(create.getStore_id());
                        setResponseDetail.setRet_value(0);

                        storeResponseDetailList.add(setResponseDetail);

                    i++;
                    }

            }
            else{
                for(StoreRequestDetail list : storeRequestDetailList){
                    for(StoreRequestDetail create : storeRequest.getStoreRequestDetailList()){
                        if(!list.getStore_id().equals(create.getStore_id())){
                            storeRequestDetailList.add(create);
                            setResponseDetail.setStore_id(create.getStore_id());
                            setResponseDetail.setRet_value(0);

                        }
                        else{
                            setResponseDetail.setStore_id(create.getStore_id());
                            setResponseDetail.setRet_value(1);
                        }
                    }
                    storeResponseDetailList.add(setResponseDetail);
                }
            }

            StoreResponse storeResponse = new StoreResponse();
            storeResponse.setResponseResult(resultService.setResult(Authorization, "create"));
            storeResponse.setStoreResponseDetailList(storeResponseDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponse);
    }
    @PostMapping("update")
    public ResponseEntity StoreUpdate(
            @RequestHeader String Authorization,
            @RequestBody StoreRequest storeRequest
    ){
        log.info("storeRequest : {}", storeRequest);
        int i = 0;
        List<StoreResponseDetail> setResponseDetail = new ArrayList<>();

        for(StoreRequestDetail list : storeRequestDetailList){
            for(StoreRequestDetail update : storeRequest.getStoreRequestDetailList()){
                if(list.getStore_id().equals(update.getStore_id())){
                    StoreResponseDetail setDetail = new StoreResponseDetail();
                    storeRequestDetailList.set(i, update);
                    setDetail.setStore_id(update.getStore_id());
                    setDetail.setRet_value(0);
                    setResponseDetail.add(setDetail);
                }
            }
            i++;
        }
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setResponseResult(resultService.setResult(Authorization, "update"));
        storeResponse.setStoreResponseDetailList(setResponseDetail);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponse);
    }
    @PostMapping("delete")
    public ResponseEntity StoreDelete(
            @RequestHeader String Authorization,
            @RequestBody StoreList storeList
    ){
        List<StoreResponseDetail> storeResponseDetailList = new ArrayList<>();
        for(StoreRequestDetail list : storeRequestDetailList){
            for(String ids : storeList.getIds()){
                if(list.getStore_id().equals(ids)){
                    storeRequestDetailList.remove(list);
                    StoreResponseDetail storeResponseDetail = new StoreResponseDetail();
                    storeResponseDetail.setStore_id(ids);
                    storeResponseDetail.setRet_value(0);
                    storeResponseDetailList.add(storeResponseDetail);
                }


            }

        }
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setResponseResult(resultService.setResult(Authorization, "delete"));
        storeResponse.setStoreResponseDetailList(storeResponseDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponse);
    }
    @PostMapping("list")
    public ResponseEntity StoreList(
            @RequestHeader String Authorization,
            @RequestBody StoreList storeList
    ){
        log.info("campaignRequest : {}", storeList);
        StoreResponseList storeResponseList = new StoreResponseList();
        StoreResponseListDetail storeResponseListDetailList = new StoreResponseListDetail();
        if(storeList.isDetail_req()){
            for(StoreRequestDetail list : storeRequestDetailList){
                for(String ids : storeList.getIds()){
                    if(list.getStore_id().equals(ids)){
                        List<StoreRequestDetail> setResponseDetail = new ArrayList<>();
                        setResponseDetail.add(list);
                        storeResponseListDetailList.setStore_detail(setResponseDetail);
                    }
                }
            }

        }
        else{
            for(StoreRequestDetail list : storeRequestDetailList){
                for(String ids : storeList.getIds()){
                    if(list.getStore_id().equals(ids)){
                        List<String> setResponseIds = new ArrayList<>();
                        setResponseIds.add(ids);
                        storeResponseListDetailList.setIds(setResponseIds);
                    }
                }
            }
        }
        storeResponseList.setResponseResult(resultService.setResult(Authorization, "list"));
        storeResponseList.setStoreResponseListDetailList(storeResponseListDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponseList);
    }
}
