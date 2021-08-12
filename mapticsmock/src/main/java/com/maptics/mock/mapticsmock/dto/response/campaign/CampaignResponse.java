package com.maptics.mock.mapticsmock.dto.response.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

import java.util.List;

@Data
public class CampaignResponse {

    private String code;
    private String message;
    private String request_id;

    @JsonProperty("data")
    private List<CampaignResponseDetail> campaignResponseDetail;
}
