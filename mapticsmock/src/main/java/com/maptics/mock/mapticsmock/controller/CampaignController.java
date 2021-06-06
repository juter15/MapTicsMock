package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestList;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequest;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestDetail;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponse;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseDetail;
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
@RequestMapping("/v1/campaign/blbiz")
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
        campaignResponse.setResponseResult(resultService.setResult(Authorization, "create"));
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

            campaignResponse.setResponseResult(resultService.setResult(Authorization, "delete"));
            campaignResponse.setCampaignResponseDetail(setResponseDataList);
            log.info("campaignResponse : {}", campaignResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(campaignResponse);

    }
    @PostMapping("list")
    public ResponseEntity CampaignList(
            @RequestHeader String Authorization,
            @RequestBody CampaignRequestList campaignRequestList
            ){
        log.info("campaignList{}", campaignRequestList);

        CampaignResponseListDetail campaignResponseListDetail = new CampaignResponseListDetail();

        if(campaignRequestList.isDetail_req()){
            List<CampaignRequestDetail> setCampaignList = new ArrayList<>();
            for (String ids : campaignRequestList.getCampaign_ids()){
                if(campaignList.containsKey(ids)){
                        setCampaignList.add(campaignList.get(ids));
                }
            }

            campaignResponseListDetail.setCampaign_detail(setCampaignList);
        }
        else{
            List<CampaignRequestDetail> shortList = new ArrayList<>();
                for(String ids : campaignRequestList.getCampaign_ids()){
                    if(campaignList.containsKey(ids)){
                        CampaignRequestDetail toshort = new CampaignRequestDetail();
                        toshort.setCampaign_id(ids);
                        toshort.setCampaign_status(campaignList.get(ids).getCampaign_status());
                        toshort.setSend_cnt(campaignList.get(ids).getSend_cnt());
                        shortList.add(toshort);
                    }
                }
            campaignResponseListDetail.setCampaign_short(shortList);

        }
        campaignResponseListDetail.setResponseResult(resultService.setResult(Authorization, "list"));
        log.info("조회 : {}", campaignResponseListDetail);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(campaignResponseListDetail);
    }

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
                        if(r > 2){
                            value.setCampaign_status("DONE");
                            value.setSend_cnt(random.nextInt(100));
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
