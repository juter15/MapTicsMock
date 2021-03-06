package com.maptics.mock.mapticsmock.dto.response.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

import java.util.List;

@Data
public class PlaceResponse {

    private String code;
    private String message;
    private String request_id;

    @JsonProperty("data")
    List<PlaceResponseDetail> placeResponseData;
}
