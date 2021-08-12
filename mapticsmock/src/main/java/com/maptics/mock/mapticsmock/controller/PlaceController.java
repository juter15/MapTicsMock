package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.ResponseResult;
import com.maptics.mock.mapticsmock.dto.request.place.MapTicsPredictionByPlace;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceReqeust;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestList;
import com.maptics.mock.mapticsmock.dto.response.place.*;
import com.maptics.mock.mapticsmock.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v2/maptics/bizniz/place")
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
        //placeResponse.setResponseResult(resultService.setResult(Authorization, "create"));
        ResponseResult responseResult = resultService.setResult(Authorization, "create");
        placeResponse.setCode(responseResult.getCode());
        placeResponse.setMessage(responseResult.getMessage());
        placeResponse.setRequest_id(responseResult.getRequest_id());
        placeResponse.setPlaceResponseData(placeResponseDatalist);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponse);
    }
    @PostMapping("/update")
    public ResponseEntity placeUpdate(
            @RequestHeader String Authorization,
            @Valid
            @RequestBody  PlaceReqeust placeReqeust
    ){
        List<PlaceResponseDetail> placeResponseDatalist = new ArrayList<>();
        log.info("Update Request Data: {}", placeReqeust);
        PlaceResponse placeResponse = new PlaceResponse();

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

        //placeResponse.setResponseResult(resultService.setResult(Authorization, "update"));
        ResponseResult responseResult = resultService.setResult(Authorization, "update");
        placeResponse.setCode(responseResult.getCode());
        placeResponse.setMessage(responseResult.getMessage());
        placeResponse.setRequest_id(responseResult.getRequest_id());
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

        //placeResponse.setResponseResult(resultService.setResult(Authorization, "delete"));
        ResponseResult responseResult = resultService.setResult(Authorization, "delete");
        placeResponse.setCode(responseResult.getCode());
        placeResponse.setMessage(responseResult.getMessage());
        placeResponse.setRequest_id(responseResult.getRequest_id());
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

        PlaceResponseListDetail placeResponseListDetail = new PlaceResponseListDetail();

        if(placeRequestList.isDetail_req()){
            List<PlaceRequestDetail> setplaceList = new ArrayList<>();
            if(placeRequestList.getIds().get(0).equals("ALL")){
                for(String key : placeList.keySet()){
                    setplaceList.add(placeList.get(key));
                }
            }else{
                for (String ids : placeRequestList.getIds()) {
                    if (placeList.containsKey(ids)) {
                        setplaceList.add(placeList.get(ids));

                    }
                }
            }
            placeResponseListDetail.setPlaceRequestDetailList(setplaceList);
        }
        else{
            List<String> setIds = new ArrayList<>();
            if(placeRequestList.getIds().get(0).equals("ALL")){
                setIds.addAll(placeList.keySet());
            }
            else{
                for(String ids : placeRequestList.getIds()){
                    if(placeList.containsKey(ids)){
                        setIds.add(ids);
                    }
                }
            }
            placeResponseListDetail.setIds(setIds);
        }
        //placeResponseList.setResponseResult(resultService.setResult(Authorization, "list"));
        ResponseResult responseResult = resultService.setResult(Authorization, "list");
        placeResponseList.setCode(responseResult.getCode());
        placeResponseList.setMessage(responseResult.getMessage());
        placeResponseList.setRequest_id(responseResult.getRequest_id());
        placeResponseList.setPlaceResponseListDetail(placeResponseListDetail);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeResponseList);
    }
    @PostMapping("/predict-count")
    public ResponseEntity placePredictionStayer(
            @RequestHeader String Authorization,
            @RequestBody MapTicsPredictionByPlace mapTicsPredictionByPlace
    ){
        log.info("mapTicsPredictionByPlace : {}", mapTicsPredictionByPlace);
        Random random = new Random();
        MapTicsPredictionResultDetail mapTicsPredictionResultDetail = new MapTicsPredictionResultDetail();
        mapTicsPredictionResultDetail.setPlace_id(mapTicsPredictionByPlace.getPlaceId());
        mapTicsPredictionResultDetail.setAvgCnt(random.nextInt(100));
        log.info("mapTicsPredictionResultDetail : {}", mapTicsPredictionResultDetail);

        MapTicsPredictionResult mapTicsPredictionResult = new MapTicsPredictionResult();
        mapTicsPredictionResult.setMapTicsPredictionResultDetails(mapTicsPredictionResultDetail);

        ResponseResult responseResult = resultService.setResult(Authorization, "placePredictionStayer");
        mapTicsPredictionResult.setCode(Integer.parseInt(responseResult.getCode()));
        mapTicsPredictionResult.setMessage(responseResult.getMessage());
        mapTicsPredictionResult.setRequest_id(responseResult.getRequest_id());
        log.info("mapTicsPredictionResult : {}", mapTicsPredictionResult);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mapTicsPredictionResult);
    }
}
