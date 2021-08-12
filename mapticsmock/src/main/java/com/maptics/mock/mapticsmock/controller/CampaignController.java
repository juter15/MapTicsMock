package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.ResponseResult;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestList;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequest;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestDetail;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponse;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseList;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseListDetail;
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

import java.awt.event.WindowFocusListener;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v1/campaign/bizniz")
public class CampaignController {
    private final ResultService resultService;

    //List<CampaignRequestDetail> campaignRequestDetailList = new CopyOnWriteArrayList<>();
    Map<String, CampaignRequestDetail> campaignList = new HashMap<>();
    @PostMapping
    public ResponseEntity CampaignCreate(
            @RequestHeader String Authorization,
            @RequestBody CampaignRequest campaignRequest
    ){
        log.info("campaignRequest : {}", campaignRequest);

        List<CampaignResponseDetail> campaignResponseDetailList = new ArrayList<>();
        int i = 0;
        for(CampaignRequestDetail create : campaignRequest.getCampaignRequestDetail()){
            if(!campaignList.containsKey(create.getCampaign_id())){
                campaignList.put(create.getCampaign_id(), create);
                CampaignResponseDetail campDetail = new CampaignResponseDetail();
                campDetail.setCampaign_id(create.getCampaign_id());
                campDetail.setRet_value(0);

                campaignResponseDetailList.add(campDetail);
            }
            else{
                CampaignResponseDetail campDetail = new CampaignResponseDetail();
                campDetail.setCampaign_id(create.getCampaign_id());
                campDetail.setRet_value(1);

                campaignResponseDetailList.add(campDetail);
            }

        }


        CampaignResponse campaignResponse = new CampaignResponse();
        ResponseResult responseResult = resultService.setResult(Authorization, "create");
        campaignResponse.setCode(responseResult.getCode());
        campaignResponse.setMessage(responseResult.getMessage());
        campaignResponse.setRequest_id(responseResult.getRequest_id());
        campaignResponse.setCampaignResponseDetail(campaignResponseDetailList);

        CampaignTimer();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(campaignResponse);
    }
    @PostMapping("/delete")
    public ResponseEntity campaignDelete(
            @RequestHeader String Authorization,
            @RequestBody CampaignRequestList campaignRequestList
    ){
        log.info("campaignList: {}", campaignRequestList);
            CampaignResponse campaignResponse = new CampaignResponse();
            List<CampaignResponseDetail> setResponseDataList = new ArrayList<>();
            for(String ids : campaignRequestList.getCampaign_ids()){
                if(campaignList.containsKey(ids)){
                    campaignList.remove(ids);
                    CampaignResponseDetail setResponseDate = new CampaignResponseDetail();
                    setResponseDate.setCampaign_id(ids);
                    setResponseDate.setRet_value(0);
                    setResponseDataList.add(setResponseDate);
                }
                else{
                    CampaignResponseDetail setResponseDate = new CampaignResponseDetail();
                    setResponseDate.setCampaign_id(ids);
                    setResponseDate.setRet_value(2);
                    setResponseDataList.add(setResponseDate);
                }

            }

            ResponseResult responseResult = resultService.setResult(Authorization, "delete");
            campaignResponse.setCode(responseResult.getCode());
            campaignResponse.setMessage(responseResult.getMessage());
            campaignResponse.setRequest_id(responseResult.getRequest_id());
            campaignResponse.setCampaignResponseDetail(setResponseDataList);

            log.info("campaignResponse : {}", campaignResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(campaignResponse);

    }
    @PostMapping("/list")
    public ResponseEntity CampaignList(
            @RequestHeader String Authorization,
            @RequestBody CampaignRequestList campaignRequestList
            ){
        log.info("campaignList{}", campaignRequestList);

        CampaignResponseListDetail campaignResponseListDetail = new CampaignResponseListDetail();

        if(campaignRequestList.isDetail_req()){
            List<CampaignRequestDetail> setCampaignList = new ArrayList<>();
            if(campaignRequestList.getCampaign_ids().get(0).equals("ALL")){
                for(String key : campaignList.keySet()){
                    setCampaignList.add(campaignList.get(key));
                }
            }else{

                for (String ids : campaignRequestList.getCampaign_ids()){
                    if(campaignList.containsKey(ids)){
                            setCampaignList.add(campaignList.get(ids));
                    }
                }

            }

            campaignResponseListDetail.setCampaign_detail(setCampaignList);
        }
        else{
            List<CampaignRequestDetail> shortList = new ArrayList<>();
            if(campaignRequestList.getCampaign_ids().get(0).equals("ALL")){
                for (String key : campaignList.keySet()) {
                    CampaignRequestDetail toshort = new CampaignRequestDetail();
                    toshort.setCampaign_id(key);
                    toshort.setCampaign_status(campaignList.get(key).getCampaign_status());
                    toshort.setSend_cnt(campaignList.get(key).getSend_cnt());
                    shortList.add(toshort);
                }
            }else{
                for(String ids : campaignRequestList.getCampaign_ids()){
                    if(campaignList.containsKey(ids)){
                        CampaignRequestDetail toshort = new CampaignRequestDetail();
                        toshort.setCampaign_id(ids);
                        toshort.setCampaign_status(campaignList.get(ids).getCampaign_status());
                        toshort.setSend_cnt(campaignList.get(ids).getSend_cnt());
                        shortList.add(toshort);
                    }
                }
            }
            campaignResponseListDetail.setCampaign_short(shortList);

        }

        CampaignResponseList campaignResponseList = new CampaignResponseList();
        campaignResponseList.setCampaignResponseListDetail(campaignResponseListDetail);

        //campaignResponseListDetail.setResponseResult(resultService.setResult(Authorization, "list"));

        ResponseResult responseResult = resultService.setResult(Authorization, "list");
        campaignResponseList.setCode(responseResult.getCode());
        campaignResponseList.setMessage(responseResult.getMessage());
        campaignResponseList.setRequest_id(responseResult.getRequest_id());

        log.info("조회 : {}", campaignResponseListDetail);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(campaignResponseList);
    }

    /*public void SetResponseResult(String Authorization,String TID){

        String authcode = "maptics";
        CampaignResponse campaignResponse = new CampaignResponse();
        campaignResponse.set
        //Response
        public ResponseResult setResult(String Authorization, String TID){
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

            responseResult.setRequest_id(TID);
            return responseResult;
        }
    }*/
    public void CampaignTimer(){
        Timer timer = new Timer();
        Random random = new Random();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                int index = 0, j = 0;
                campaignList.forEach((ids, value)->{
                    if(value.getCampaign_status() == null && value.getSend_cnt() == null){
                        int r = random.nextInt(10);
                        if(r >= 0){
                            value.setCampaign_status("SUCCESS");
                            value.setSend_cnt(random.nextInt(value.getCampaign_req_cnt()));
                        }
                        else {
                            value.setCampaign_status("FAILED");
                            value.setSend_cnt(0);
                        }
                    }
                });
                log.info("timer campaignList : ", campaignList);
                /*for(CampaignRequestDetail list : campaignRequestDetailList){
                    if(list.getCampaign_status() == null && list.getSend_cnt() == null){
                        index = campaignRequestDetailList.indexOf(list);
                        log.info("list : {} ",list);
                        log.info("index : {}", index );
                        int r = random.nextInt(10);
                        if(r > 2){
                            list.setCampaign_status("DONE");
                            list.setSend_cnt(random.nextInt(100));
                        }
                        else {
                            list.setCampaign_status("FAILED");
                            list.setSend_cnt(0);
                        }
                        campaignRequestDetailList.set(index, list);
                    }

                }
                log.info("campaignRequestDetailList : {} ", campaignRequestDetailList);*/
            }
        };
        timer.schedule(timerTask, 5000);
    }
}
