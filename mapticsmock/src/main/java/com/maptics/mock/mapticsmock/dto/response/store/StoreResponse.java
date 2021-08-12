package com.maptics.mock.mapticsmock.dto.response.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StoreResponse {

    private String code;
    private String message;
    private String request_id;

    @JsonProperty("data")
    private List<StoreResponseDetail> storeResponseDetailList;
}
