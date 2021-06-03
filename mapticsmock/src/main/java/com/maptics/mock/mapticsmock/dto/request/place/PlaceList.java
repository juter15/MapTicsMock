package com.maptics.mock.mapticsmock.dto.request.place;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlaceList {

    @JsonProperty("place_ids")
    private List<String> ids;

    @JsonProperty("detail_req")
    private boolean detail_req;

}
