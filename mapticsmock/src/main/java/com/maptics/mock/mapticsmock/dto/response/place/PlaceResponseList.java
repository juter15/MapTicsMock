package com.maptics.mock.mapticsmock.dto.response.place;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlaceResponseList {
    private ResponseResult responseResult;

    @JsonProperty("data")
    private PlaceResponseListDetail placeResponseListDetail;


}
