package com.maptics.mock.mapticsmock.dto.request.campaign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CampaignRequest {
    @JsonProperty("campaign_list")
    private List<CampaignRequestDetail> campaignRequestDetail;

}
