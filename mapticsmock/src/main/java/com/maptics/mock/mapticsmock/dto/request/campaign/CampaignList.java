package com.maptics.mock.mapticsmock.dto.request.campaign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CampaignList {



    @JsonProperty("campaign_ids")
    private List<String> campaign_ids;

    @JsonProperty("detail_req")
    private boolean detail_req;

}
