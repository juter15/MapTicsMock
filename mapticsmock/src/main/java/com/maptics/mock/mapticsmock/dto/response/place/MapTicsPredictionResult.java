package com.maptics.mock.mapticsmock.dto.response.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MapTicsPredictionResult {

    private int code;
    @JsonProperty("data")
    private MapTicsPredictionResultDetail mapTicsPredictionResultDetails;
    private String message;
    private String request_id;

}
