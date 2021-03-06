package com.maptics.mock.mapticsmock.dto.response.place;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlaceResponseList {
    private String code;
    private String message;
    private String request_id;

    @JsonProperty("data")
    private PlaceResponseListDetail placeResponseListDetail;


}
