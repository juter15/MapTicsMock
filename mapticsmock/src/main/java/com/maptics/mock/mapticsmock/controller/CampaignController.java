package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignList;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequest;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceReqeust;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponse;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseList;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseListDetail;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponseDetail;
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

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v1/campaign/blbiz")
public class CampaignController {
    private final ResultService resultService;

    List<CampaignRequestDetail> campaignRequestDetailList = new CopyOnWriteArrayList<>();

    @PostMapping
    public ResponseEntity CampaignCreate(
            @RequestHeader String Authorization,
            @RequestBody CampaignRequest campaignRequest
    ){
        log.info("campaignRequest : {}", campaignRequest);

        List<CampaignResponseDetail> campaignResponseDetailList = new ArrayList<>();
        int i = 0;
        for (CampaignRequestDetail camp  : campaignRequest.getCampaignRequestDetail())
        {
            //CampaignRequestDetail campaignRequestDetail = new CampaignRequestDetail();
            CampaignResponseDetail campDetail = new CampaignResponseDetail();

            if(campaignRequestDetailList.isEmpty() ||!campaignRequestDetailList.get(i).getCampaign_id().equals(camp.getCampaign_id())){
                campaignRequestDetailList.add(camp);
                campDetail.setCampaign_id(camp.getCampaign_id());
                campDetail.setRet_value(0);
            }
            else {
                campDetail.setCampaign_id(camp.getCampaign_id());
                campDetail.setRet_value(2);
            }
            campaignResponseDetailList.add(campDetail);
            i++;
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
            @RequestBody CampaignList campaignList
    ){
        log.info("campaignList: {}", campaignList);
            CampaignResponse campaignResponse = new CampaignResponse();
            List<CampaignResponseDetail> setResponseDataList = new ArrayList<>();
            for(CampaignRequestDetail list : campaignRequestDetailList){
                for(String ids : campaignList.getCampaign_ids()){
                    if (list.getCampaign_id().equals(ids)){
                        log.info("???");
                        CampaignResponseDetail setResponseDate = new CampaignResponseDetail();
                        setResponseDate.setCampaign_id(list.getCampaign_id());
                        setResponseDate.setRet_value(0);
                        setResponseDataList.add(setResponseDate);
                        campaignRequestDetailList.remove(list);

                    }
                }

                log.info("setResponseDataList : {}", setResponseDataList);
            }

            /*int i = 0;
            for(String ids : campaignList.getCampaign_ids()){
                if( !setResponseDataList.isEmpty() && !setResponseDataList.get(i).getCampaign_id().equals(ids) ){
                    log.info("없음");
                    CampaignResponseDetail setResponseDate = new CampaignResponseDetail();
                    setResponseDate.setCampaign_id(ids);
                    setResponseDate.setRet_value(2);
                    setResponseDataList.add(setResponseDate);
                }
                i++;
            }*/
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
            @RequestBody CampaignList campaignList
            ){
        log.info("campaignList{}", campaignList);

        CampaignResponseListDetail campaignResponseListDetail = new CampaignResponseListDetail();

        if(campaignList.isDetail_req()){
            List<CampaignRequestDetail> setCampaignList = new ArrayList<>();
            for(CampaignRequestDetail list : campaignRequestDetailList){
                for (String ids : campaignList.getCampaign_ids()){
                    if(list.getCampaign_id().equals(ids)){
                        setCampaignList.add(list);

                    }
                }
            }
            campaignResponseListDetail.setCampaign_detail(setCampaignList);
        }
        else{
            List<CampaignRequestDetail> shortList = new ArrayList<>();
            CampaignRequestDetail toshort = new CampaignRequestDetail();
            for (CampaignRequestDetail crq : campaignRequestDetailList){
                for(String ids : campaignList.getCampaign_ids()){
                    if(crq.getCampaign_id().equals(ids)){
                        toshort.setCampaign_id(crq.getCampaign_id());
                        toshort.setCampaign_status(crq.getCampaign_status());
                        toshort.setSend_cnt(crq.getSend_cnt());
                        shortList.add(toshort);

                    }
                }
            }
            campaignResponseListDetail.setCampaign_short(shortList);
            //List<CampaignRequestDetail> toshort = campaignRequestDetail.stream().map(CampaignRequestDetail::toShort).collect(Collectors.toList());
        }
        campaignResponseListDetail.setResponseResult(resultService.setResult(Authorization, "list"));
        log.info("캠페인 목록 : {} ", campaignRequestDetailList);
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
                log.info("campaignRequestDetailList : {} ", campaignRequestDetailList);
                int i = 0, j = 0;
                for(CampaignRequestDetail list : campaignRequestDetailList){
                    if(list.getCampaign_status() == null && list.getSend_cnt() == null){
                        int r = random.nextInt(10);
                        if(r > 2){
                            list.setCampaign_status("DONE");
                            list.setSend_cnt(random.nextInt(100));
                        }
                        else {
                            list.setCampaign_status("FAILED");
                            list.setSend_cnt(0);
                        }
                    }
                    campaignRequestDetailList.set(i, list);
                    i++;
                }
            }
        };
        timer.schedule(timerTask, 5000);
    }
}
