package com.maptics.mock.mapticsmock.dto.request.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StoreRequest {
    @JsonProperty("store_list")
    private List<StoreRequestDetail> storeRequestDetailList;
}
