package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.ResponseResult;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceReqeust;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceList;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponse;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseList;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseListDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/v2/maptics/blbiz/place")
public class PlaceController {
    String create_TID = "create";
    String update_TID = "update";
    String delete_TID = "delete";
    String Search_TID = "search";
    List<PlaceRequestDetail> placeRequestDetailList = new ArrayList<>();
    static String authcode = "maptics";
    @PostMapping
    public ResponseEntity placeCreate(
            @RequestHeader String Authorization,
            @RequestBody PlaceReqeust placeReqeust
    ){
        log.info("Create Request Data: {}", placeReqeust);



        //Response
        PlaceResponse placeResponse = new PlaceResponse();
        ResponseResult responseResult = new ResponseResult();
        if(Authorization.isEmpty()){
            responseResult.setCode("4010");
            responseResult.setMessage("NEED_ACCESS_TOKEN");
        }
        else if(!authcode.equals(Authorization)){
            responseResult.setCode("4011");
            responseResult.setMessage("INVALID_ACCESS_TOKEN");
        }
        else{
            responseResult.setCode("2000");
            responseResult.setMessage("OK");
        }
        responseResult.setRequest_id(create_TID);


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
                placeResponseDetail.setRet_value(2);
            }
            placeResponseDatalist.add(placeResponseDetail);
            i++;
        }
        /*placeReqeust.getPlaceRequestDetailList().forEach(
                item -> {
                    PlaceResponseData placeResponseData = new PlaceResponseData();
                    if(!placeRequestDetailList.contains(item)){
                        placeRequestDetailList.add(placeReqeust.getPlaceRequestDetailList().get(item.i));
                        placeResponseData.setPlace_id(item.getPlace_id());
                        placeResponseData.setRet_value(0);
                    }
                    else {
                        placeResponseData.setPlace_id(item.getPlace_id());
                        placeResponseData.setRet_value(2);
                    }
                    placeResponseDatalist.add(placeResponseData);
                }

        );*/
        log.info("placeRequestDetailList : {}", placeRequestDetailList);
        placeResponse.setResponseResult(responseResult);
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
        for(int i = 0; i < placeRequestDetailList.size(); i++){
            for (int j = 0; j < placeReqeust.getPlaceRequestDetailList().size(); j++){
                if(placeRequestDetailList.get(i).getPlace_id().equals(placeReqeust.getPlaceRequestDetailList().get(j).getPlace_id())){
                    placeRequestDetailList.set(i, placeReqeust.getPlaceRequestDetailList().get(j));

                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    placeResponseDetail.setPlace_id(placeReqeust.getPlaceRequestDetailList().get(j).getPlace_id());
                    placeResponseDetail.setRet_value(0);
                    placeResponseDatalist.add(placeResponseDetail);
                }
            }
        }

        //Response
        PlaceResponse placeResponse = new PlaceResponse();
        ResponseResult responseResult = new ResponseResult();
        if(Authorization.isEmpty()){
            responseResult.setCode("4010");
            responseResult.setMessage("NEED_ACCESS_TOKEN");
        }
        else if(!authcode.equals(Authorization)){
            responseResult.setCode("4011");
            responseResult.setMessage("INVALID_ACCESS_TOKEN");
        }
        else{
            responseResult.setCode("2000");
            responseResult.setMessage("OK");
        }
        responseResult.setRequest_id(update_TID);

        log.info("placeRequestDetailList : {}", placeRequestDetailList);
        placeResponse.setResponseResult(responseResult);
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
        List<PlaceResponseDetail> placeResponseDatalist = new ArrayList<>();
        log.info("Delete Request Data: {}", placeList);
        log.info("placeRequestDetailList : {}", placeRequestDetailList);

        for(int i = 0; i < placeRequestDetailList.size(); i++){
            for (int j = 0; j < placeList.getIds().size(); j++){
                if(placeRequestDetailList.get(i).getPlace_id().equals(placeList.getIds().get(j))){
                    PlaceResponseDetail placeResponseDetail = new PlaceResponseDetail();
                    log.info("getIds : {}", placeList.getIds().get(j));
                    log.info("placeRequestDetailList.get(i).getPlace_id() : {}", placeRequestDetailList.get(i).getPlace_id());
                    placeRequestDetailList.remove(i);
                    placeResponseDetail.setPlace_id(placeList.getIds().get(j));
                    placeResponseDetail.setRet_value(0);
                    placeResponseDatalist.add(placeResponseDetail);
                }
            }
        }

//Response
        PlaceResponse placeResponse = new PlaceResponse();
        ResponseResult responseResult = new ResponseResult();
        if(Authorization.isEmpty()){
            responseResult.setCode("4010");
            responseResult.setMessage("NEED_ACCESS_TOKEN");
        }
        else if(!authcode.equals(Authorization)){
            responseResult.setCode("4011");
            responseResult.setMessage("INVALID_ACCESS_TOKEN");
        }
        else{
            responseResult.setCode("2000");
            responseResult.setMessage("OK");
        }
        responseResult.setRequest_id(delete_TID);

        placeResponse.setResponseResult(responseResult);
        placeResponse.setPlaceResponseData(placeResponseDatalist);
        log.info("placeRequestDetailList : {}", placeRequestDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponse);
    }


    @PostMapping("/list")
    public ResponseEntity placeList(
            @RequestHeader String Authorization,
            @RequestBody PlaceList placeList
            ){
        ResponseResult responseResult = new ResponseResult();
        log.info("List Request Data: {}", placeList);
        if(Authorization.isEmpty()){
            responseResult.setCode("4010");
            responseResult.setMessage("NEED_ACCESS_TOKEN");
        }
        else if(!authcode.equals(Authorization)){
            responseResult.setCode("4011");
            responseResult.setMessage("INVALID_ACCESS_TOKEN");
        }
        else{
            responseResult.setCode("2000");
            responseResult.setMessage("OK");
        }
        responseResult.setRequest_id(Search_TID);


        PlaceResponseList placeResponseList = new PlaceResponseList();
        placeResponseList.setResponseResult(responseResult);
        PlaceResponseListDetail placeResponseListDetail = new PlaceResponseListDetail();
        if(placeList.isDetail_req()){
            placeResponseListDetail.setPlaceRequestDetailList(placeRequestDetailList);
        }
        else{
            placeResponseListDetail.setIds(placeRequestDetailList.stream().map(PlaceRequestDetail::getPlace_id).collect(Collectors.toList()));
        }

        placeResponseList.setPlaceResponseListDetail(placeResponseListDetail);

        log.info("placeRequestDetailList : {}", placeRequestDetailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponseList);
    }
}
