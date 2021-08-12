package com.maptics.mock.mapticsmock.dto.request.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data

public class PlaceReqeust {

    @Valid
    @JsonProperty("place_list")
    private List<PlaceRequestDetail> placeRequestDetailList;
}
