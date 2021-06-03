package com.maptics.mock.mapticsmock.dto.response.place;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.request.place.PlaceRequestDetail;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlaceResponseListDetail {
    @JsonProperty("place_detail")
    private List<PlaceRequestDetail> placeRequestDetailList;

    @JsonProperty("place_short")
    private List<String> ids;

}
