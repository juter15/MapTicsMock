package com.maptics.mock.mapticsmock.dto.request.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MapTicsPredictionByPlace {
    @JsonProperty("place_id")
    private String placeId;

    private Integer target_gender; // ALL -> 0, M -> 1, F -> 2

    @JsonProperty("target_age")
    private List<Integer> ageGroup; // 10, 20, 30, 40. 50, 60

    @JsonProperty("exp_count_stime")
    private String startDateTime; //yyyymmddhhmmss

    @JsonProperty("exp_count_etime")
    private String endDateTime; //yyyymmddhhmmss
}
