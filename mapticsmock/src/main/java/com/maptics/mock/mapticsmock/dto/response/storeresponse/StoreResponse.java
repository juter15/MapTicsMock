package com.maptics.mock.mapticsmock.dto.response.storeresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

import java.util.List;

@Data
public class StoreResponse {
    private ResponseResult responseResult;

    @JsonProperty("data")
    private List<StoreResponseDetail> storeResponseDetailList;
}
