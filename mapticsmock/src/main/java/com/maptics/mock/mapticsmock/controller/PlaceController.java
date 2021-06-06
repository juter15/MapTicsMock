package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.place.PlaceReqeust;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestList;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponse;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseList;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseListDetail;
import com.maptics.mock.mapticsmock.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v2/maptics/blbiz/place")
public class PlaceController {
    private final ResultService resultService;
    //List<PlaceRequestDetail> placeRequestDetailList = new CopyOnWriteArrayList<>();
    Map<String,PlaceRequestDetail> placeList = new HashMap<>();
    @PostMapping
    public ResponseEntity placeCreate(
            @RequestHeader String Authorization,
            @RequestBody PlaceReqeust placeReqeust
    ){
        log.info("Create Request Data: {}", placeReqeust);

        List<PlaceResponseDetail> placeResponseDatalist = new ArrayList<>();
        int i = 0;
        for (PlaceRequestDetail create : placeReqeust.getPlaceRequestDetailList())
        {
            if(!placeList.containsKey(create.getPlace_id())){
                placeList.put(create.getPlace_id(), create);

                PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                placeResponseDetail.setPlace_id(create.getPlace_id());
                placeResponseDetail.setRet_value(0);

                placeResponseDatalist.add(placeResponseDetail);
            }
            else {
                PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                placeResponseDetail.setPlace_id(create.getPlace_id());
                placeResponseDetail.setRet_value(1);

                placeResponseDatalist.add(placeResponseDetail);
            }
        }

        PlaceResponse placeResponse = new PlaceResponse();
        placeResponse.setResponseResult(resultService.setResult(Authorization, "create"));
        placeResponse.setPlaceResponseData(placeResponseDatalist);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponse);
    }
    @PostMapping("/update")
    public ResponseEntity placeUpdate(
            @RequestHeader String Authorization,
            @RequestBody PlaceReqeust placeReqeust
    ){
        List<PlaceResponseDetail> placeResponseDatalist = new ArrayList<>();
        log.info("Update Request Data: {}", placeReqeust);

        int i = 0;
            for(PlaceRequestDetail update : placeReqeust.getPlaceRequestDetailList()){
                if(placeList.containsKey(update.getPlace_id())){
                    placeList.replace(update.getPlace_id(), update);
                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    placeResponseDetail.setPlace_id(update.getPlace_id());
                    placeResponseDetail.setRet_value(0);
                    placeResponseDatalist.add(placeResponseDetail);
                }
                else{
                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    placeResponseDetail.setPlace_id(update.getPlace_id());
                    placeResponseDetail.setRet_value(2);
                    placeResponseDatalist.add(placeResponseDetail);
                }
            }

        //Response
        PlaceResponse placeResponse = new PlaceResponse();

        placeResponse.setResponseResult(resultService.setResult(Authorization, "update"));
        placeResponse.setPlaceResponseData(placeResponseDatalist);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponse);
    }
    @PostMapping("/delete")
    public ResponseEntity placeDelete(
            @RequestHeader String Authorization,
            @RequestBody PlaceRequestList placeRequestList
    ){
        List<PlaceResponseDetail> ResponseDatalist = new ArrayList<>();
        log.info("Delete Request Data: {}", placeRequestList);

            for(String ids : placeRequestList.getIds()){
                if(placeList.containsKey(ids)){
                    placeList.remove(ids);
                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    placeResponseDetail.setPlace_id(ids);
                    placeResponseDetail.setRet_value(0);
                    ResponseDatalist.add(placeResponseDetail);
                }
                else{
                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    placeResponseDetail.setPlace_id(ids);
                    placeResponseDetail.setRet_value(2);
                    ResponseDatalist.add(placeResponseDetail);
                }



        }


        //Response
        PlaceResponse placeResponse = new PlaceResponse();

        placeResponse.setResponseResult(resultService.setResult(Authorization, "delete"));
        placeResponse.setPlaceResponseData(ResponseDatalist);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponse);
    }


    @PostMapping("/list")
    public ResponseEntity placeList(
            @RequestHeader String Authorization,
            @RequestBody PlaceRequestList placeRequestList
            ){
        log.info("Place list : {}", placeRequestList);

        PlaceResponseList placeResponseList = new PlaceResponseList();
        placeResponseList.setResponseResult(resultService.setResult(Authorization, "list"));
        PlaceResponseListDetail placeResponseListDetail = new PlaceResponseListDetail();

        if(placeRequestList.isDetail_req()){
            List<PlaceRequestDetail> setplaceList = new ArrayList<>();
            for (String ids : placeRequestList.getIds()) {
                if (placeList.containsKey(ids)) {
                    setplaceList.add(placeList.get(ids));

                }
                placeResponseListDetail.setPlaceRequestDetailList(setplaceList);
            }
        }

        else{
            List<String> setIds = new ArrayList<>();

            for(String ids : placeRequestList.getIds()){
                if(placeList.containsKey(ids)){
                    setIds.add(ids);
                }
            }
            placeResponseListDetail.setIds(setIds);
        }
        placeResponseList.setResponseResult(resultService.setResult(Authorization, "list"));
        placeResponseList.setPlaceResponseListDetail(placeResponseListDetail);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponseList);
    }
}
