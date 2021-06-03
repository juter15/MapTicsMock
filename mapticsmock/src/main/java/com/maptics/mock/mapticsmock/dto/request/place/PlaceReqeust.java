package com.maptics.mock.mapticsmock.dto.request.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlaceReqeust {

    @JsonProperty("place_list")
    List<PlaceRequestDetail> placeRequestDetailList;
}
