package com.maptics.mock.mapticsmock.dto.request.place;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class PlaceRequestDetail {
    private String place_id;
    private String place_name;
    private String address;
    private double latitude;
    private double longitude;
    private double radius;

}
