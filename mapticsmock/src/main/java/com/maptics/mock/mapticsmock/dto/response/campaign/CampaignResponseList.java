package com.maptics.mock.mapticsmock.dto.response.campaign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampaignResponseList {
    private ResponseResult responseResult;

    @JsonProperty("data")
    private CampaignResponseListDetail campaignResponseListDetail;
}
