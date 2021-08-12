package com.maptics.mock.mapticsmock.dto.response.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StoreResponseList {

    private String code;
    private String message;
    private String request_id;

    @JsonProperty("data")
    private StoreResponseListDetail storeResponseListDetailList;

}
