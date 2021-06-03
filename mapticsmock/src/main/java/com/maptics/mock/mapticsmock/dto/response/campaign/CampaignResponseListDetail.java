package com.maptics.mock.mapticsmock.dto.response.campaign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequest;
import com.maptics.mock.mapticsmock.dto.request.campaign.CampaignRequestDetail;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampaignResponseListDetail {

    @JsonProperty("campaign_detail")
    private List<CampaignRequestDetail> Campaign_detail;

    @JsonProperty("campaign_short")
    private List<CampaignRequestDetail> Campaign_short;
}
