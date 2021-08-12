package com.maptics.mock.mapticsmock.dto.request.campaign;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampaignRequestDetail {

    private String campaign_id;
    private String campaign_name;
    private Integer campaign_req_cnt;
    private String campaign_stime;
    private String campaign_etime;

    private String contents_title;
    private String contents_text;
    private String contents_image;

    private String msg_type;
    private String place_id;

    private List<String> test_receiver;

    private Integer target_gender;

    private List<Integer> target_age;

    private String user_id;

    private String campaign_status;

    private Integer send_cnt;

}
