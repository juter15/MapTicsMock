package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.ResponseResult;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceReqeust;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v2/maptics/blbiz/place")
public class PlaceController {
    private final ResultService resultService;
    List<PlaceRequestDetail> placeRequestDetailList = new CopyOnWriteArrayList<>();

    @PostMapping
    public ResponseEntity placeCreate(
            @RequestHeader String Authorization,
            @RequestBody PlaceReqeust placeReqeust
    ){
        log.info("Create Request Data: {}", placeReqeust);

        List<PlaceResponseDetail> placeResponseDatalist = new ArrayList<>();
        int i = 0;
        for (PlaceRequestDetail placeRequestDetail : placeReqeust.getPlaceRequestDetailList())
        {
            PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
            if(!placeRequestDetailList.contains(placeRequestDetail)){
                placeRequestDetailList.add(placeReqeust.getPlaceRequestDetailList().get(i));
                placeResponseDetail.setPlace_id(placeRequestDetail.getPlace_id());
                placeResponseDetail.setRet_value(0);
            }
            else {
                placeResponseDetail.setPlace_id(placeRequestDetail.getPlace_id());
                placeResponseDetail.setRet_value(1);
            }
            placeResponseDatalist.add(placeResponseDetail);
            i++;
        }

        log.info("placeRequestDetailList : {}", placeRequestDetailList);
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
        for(PlaceRequestDetail list : placeRequestDetailList){
            for(PlaceRequestDetail update : placeReqeust.getPlaceRequestDetailList()){
                if(list.getPlace_id().equals(update.getPlace_id())){
                    placeRequestDetailList.set(i, update);
                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    placeResponseDetail.setPlace_id(update.getPlace_id());
                    placeResponseDetail.setRet_value(0);
                    placeResponseDatalist.add(placeResponseDetail);
                }

            }
            i++;
        }

        //Response
        PlaceResponse placeResponse = new PlaceResponse();


        log.info("placeRequestDetailList : {}", placeRequestDetailList);
        placeResponse.setResponseResult(resultService.setResult(Authorization, "update"));
        placeResponse.setPlaceResponseData(placeResponseDatalist);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponse);
    }
    @PostMapping("/delete")
    public ResponseEntity placeDelete(
            @RequestHeader String Authorization,
            @RequestBody PlaceList placeList
    ){
        List<PlaceResponseDetail> ResponseDatalist = new ArrayList<>();
        log.info("Delete Request Data: {}", placeList);


        log.info("list : {}", placeRequestDetailList);
        for(PlaceRequestDetail list : placeRequestDetailList){
            for(String ids : placeList.getIds()){
                if(list.getPlace_id().equals(ids)){
                    placeRequestDetailList.remove(list);
                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    placeResponseDetail.setPlace_id(ids);
                    placeResponseDetail.setRet_value(0);
                    ResponseDatalist.add(placeResponseDetail);
                }


            }

        }
        log.info("list : {}", placeRequestDetailList);



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
            @RequestBody PlaceList placeList
            ){
        log.info("Place list : {}", placeList);

        PlaceResponseList placeResponseList = new PlaceResponseList();
        placeResponseList.setResponseResult(resultService.setResult(Authorization, "list"));
        PlaceResponseListDetail placeResponseListDetail = new PlaceResponseListDetail();

        if(placeList.isDetail_req()){
            List<PlaceRequestDetail> setplaceList = new ArrayList<>();
            for(PlaceRequestDetail list : placeRequestDetailList) {
                for (String ids : placeList.getIds()) {
                    if (list.getPlace_id().equals(ids)) {
                        setplaceList.add(list);

                    }
                }
            }
            placeResponseListDetail.setPlaceRequestDetailList(setplaceList);
        }
        else{
            List<String> setIds = new ArrayList<>();
            for(PlaceRequestDetail list : placeRequestDetailList){
                for(String ids : placeList.getIds()){
                    if(list.getPlace_id().equals(ids)){
                        setIds.add(list.getPlace_id());

                    }
                }

            }
            placeResponseListDetail.setIds(setIds);
        }

        placeResponseList.setPlaceResponseListDetail(placeResponseListDetail);

        log.info("placeRequestDetailList : {}", placeRequestDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponseList);
    }
}
