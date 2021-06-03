package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignList;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequest;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceReqeust;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseList;
import com.maptics.mock.mapticsmock.dto.response.campaign.CampaignResponseListDetail;
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

@Slf4j
@Controller
@RequestMapping("/v1/campaign/blbiz")
public class CampaignController {
    List<CampaignRequestDetail> campaignRequestDetail = new ArrayList<>();

    @PostMapping
    public ResponseEntity CampaignCreate(
            @RequestHeader String Authorization,
            @RequestBody CampaignRequest campaignRequest
    ){
        log.info("campaignRequest : {}", campaignRequest);
        campaignRequestDetail.addAll(campaignRequest.getCampaignRequestDetail());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PostMapping("list")
    public ResponseEntity CampaignList(
            @RequestHeader String Authorization,
            @RequestBody CampaignList campaignList
            ){
        log.info("campaignList{}", campaignList);
        CampaignResponseListDetail campaignResponseListDetail = new CampaignResponseListDetail();
        if(campaignList.isDetail_req()){
            campaignResponseListDetail.setCampaign_detail(campaignRequestDetail);
        }
        /*else{
            campaignResponseListDetail.
        }*/
        log.info("캠페인 목록 : {} ", campaignRequestDetail);
        log.info("조회 : {}", campaignResponseListDetail);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
