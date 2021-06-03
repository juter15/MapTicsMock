package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignList;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequest;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceReqeust;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponse;
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

        //campaignRequestDetailList.addAll(campaignRequest.getCampaignRequestDetail());
        CampaignRequestDetail campaignRequestDetail = new CampaignRequestDetail();
        int i = 0;
        for (CampaignRequestDetail camp  : campaignRequest.getCampaignRequestDetail())
        {
            //CampaignRequestDetail campaignRequestDetail = new CampaignRequestDetail();

            if(!campaignRequestDetailList.contains(camp)){
                campaignRequestDetailList.add(camp);

            }
            /*if(!placeRequestDetailList.contains(placeRequestDetail)){
                placeRequestDetailList.add(placeReqeust.getPlaceRequestDetailList().get(i));
                placeResponseDetail.setPlace_id(placeRequestDetail.getPlace_id());
                placeResponseDetail.setRet_value(0);
            }
            else {
                placeResponseDetail.setPlace_id(placeRequestDetail.getPlace_id());
                placeResponseDetail.setRet_value(2);
            }
            placeResponseDatalist.add(placeResponseDetail);
            i++;*/
        }
        CampaignResponse campaignResponse = new CampaignResponse();
        campaignResponse.setResponseResult(resultService.setResult(Authorization, "create"));

        CampaignTimer(campaignRequest.getCampaignRequestDetail());

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
            campaignResponseListDetail.setCampaign_detail(campaignRequestDetailList);
        }
        else{
            List<CampaignRequestDetail> shortList = new ArrayList<>();
            CampaignRequestDetail toshort = new CampaignRequestDetail();
            for (CampaignRequestDetail crq : campaignRequestDetailList){
                toshort.setCampaign_id(crq.getCampaign_id());
                toshort.setCampaign_status(crq.getCampaign_status());
                toshort.setSend_cnt(crq.getSend_cnt());
                shortList.add(toshort);
            }
            //List<CampaignRequestDetail> toshort = campaignRequestDetail.stream().map(CampaignRequestDetail::toShort).collect(Collectors.toList());
            campaignResponseListDetail.setCampaign_short(shortList);
        }
        log.info("캠페인 목록 : {} ", campaignRequestDetailList);
        log.info("조회 : {}", campaignResponseListDetail);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(campaignResponseListDetail);
    }

    public void CampaignTimer(List<CampaignRequestDetail> camp){
        Timer timer = new Timer();
        Random random = new Random();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int i = 0, j = 0;
                for(CampaignRequestDetail list : campaignRequestDetailList){
                    for(CampaignRequestDetail create : camp){
                        if(list.getCampaign_id().equals(create.getCampaign_id())){
                            int r = random.nextInt(10);
                            if(r > 2){
                                create.setCampaign_status("DONE");
                                create.setSend_cnt(random.nextInt(100));
                            }
                            else {
                                create.setCampaign_status("FAILED");
                                create.setSend_cnt(0);
                            }

                        }
                        campaignRequestDetailList.set(i, create);
                    }
                    i++;
                }
            }
        };
        timer.schedule(timerTask, 5000);
    }
}
