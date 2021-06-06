package com.maptics.mock.mapticsmock.dto.response.storeresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.request.store.StoreRequestDetail;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StoreResponseListDetail {
    @JsonProperty("store_detail")
    private List<StoreRequestDetail> store_detail;

    @JsonProperty("store_short")
    private List<String> ids;
}
