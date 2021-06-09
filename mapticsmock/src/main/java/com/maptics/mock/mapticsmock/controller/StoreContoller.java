package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.store.StoreRequestList;
import com.maptics.mock.mapticsmock.dto.request.store.StoreRequest;
import com.maptics.mock.mapticsmock.dto.request.store.StoreRequestDetail;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StoreContoller {

    private final ResultService resultService;

    //List<StoreRequestDetail> storeRequestDetailList = new CopyOnWriteArrayList<>();
    Map<String, StoreRequestDetail> storeList = new HashMap<>();

    @PostMapping("/v2/maptics/bizniz/store")
    public ResponseEntity StoreCreate(
            @RequestHeader String Authorization,
            @RequestBody StoreRequest storeRequest
    ) {
        log.info("create storeRequest : {}", storeRequest);

        List<StoreResponseDetail> storeResponseDetailList = new ArrayList<>();


        for (StoreRequestDetail create : storeRequest.getStoreRequestDetailList()) {
            if (!storeList.containsKey(create.getStore_id())) {
                storeList.put(create.getStore_id(), create);

                StoreResponseDetail setResponseDetail = new StoreResponseDetail();
                setResponseDetail.setStore_id(create.getStore_id());
                setResponseDetail.setRet_value(0);

                storeResponseDetailList.add(setResponseDetail);
            } else {
                StoreResponseDetail setResponseDetail = new StoreResponseDetail();
                setResponseDetail.setStore_id(create.getStore_id());
                setResponseDetail.setRet_value(1);

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

    @PostMapping("/v2/maptics/bizniz/store/update")
    public ResponseEntity StoreUpdate(
            @RequestHeader String Authorization,
            @RequestBody StoreRequest storeRequest
    ) {
        log.info("storeRequest : {}", storeRequest);

        List<StoreResponseDetail> setResponseDetail = new ArrayList<>();

        for (StoreRequestDetail update : storeRequest.getStoreRequestDetailList()) {
            if (storeList.containsKey(update.getStore_id())) {
                storeList.replace(update.getStore_id(), update);

                StoreResponseDetail setDetail = new StoreResponseDetail();
                setDetail.setStore_id(update.getStore_id());
                setDetail.setRet_value(0);

                setResponseDetail.add(setDetail);
            } else {
                StoreResponseDetail setDetail = new StoreResponseDetail();
                setDetail.setStore_id(update.getStore_id());
                setDetail.setRet_value(2);

                setResponseDetail.add(setDetail);
            }
        }

        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setResponseResult(resultService.setResult(Authorization, "update"));
        storeResponse.setStoreResponseDetailList(setResponseDetail);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponse);
    }

    @PostMapping("/v2/maptics/bizniz/store/delete")
    public ResponseEntity StoreDelete(
            @RequestHeader String Authorization,
            @RequestBody StoreRequestList storeRequestList
    ) {
        List<StoreResponseDetail> storeResponseDetailList = new ArrayList<>();
        for (String ids : storeRequestList.getIds()) {
            if (storeList.containsKey(ids)) {
                storeList.remove(ids);

                StoreResponseDetail storeResponseDetail = new StoreResponseDetail();
                storeResponseDetail.setStore_id(ids);
                storeResponseDetail.setRet_value(0);
                storeResponseDetailList.add(storeResponseDetail);
            } else {
                StoreResponseDetail storeResponseDetail = new StoreResponseDetail();
                storeResponseDetail.setStore_id(ids);
                storeResponseDetail.setRet_value(2);
                storeResponseDetailList.add(storeResponseDetail);
            }
        }

        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setResponseResult(resultService.setResult(Authorization, "delete"));
        storeResponse.setStoreResponseDetailList(storeResponseDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponse);
    }

    @PostMapping("v1/store/list")
    public ResponseEntity StoreList(
            @RequestHeader String Authorization,
            @RequestBody StoreRequestList storeRequestList
    ) {
        log.info("campaignRequest : {}", storeRequestList);
        StoreResponseList storeResponseList = new StoreResponseList();
        StoreResponseListDetail storeResponseListDetailList = new StoreResponseListDetail();
        if (storeRequestList.isDetail_req()) {
            List<StoreRequestDetail> setResponseDetail = new ArrayList<>();
            for (String ids : storeRequestList.getIds()) {
                if (storeList.containsKey(ids)) {
                    setResponseDetail.add(storeList.get(ids));

                }
            }
            storeResponseListDetailList.setStore_detail(setResponseDetail);

        } else {
                List<String> setResponseIds = new ArrayList<>();
            for (String ids : storeRequestList.getIds()) {
                if (storeList.containsKey(ids)) {
                    setResponseIds.add(ids);
                }
            }
            storeResponseListDetailList.setIds(setResponseIds);
        }
        storeResponseList.setResponseResult(resultService.setResult(Authorization, "list"));
        storeResponseList.setStoreResponseListDetailList(storeResponseListDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(storeResponseList);
    }
}
