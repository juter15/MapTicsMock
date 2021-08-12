package com.maptics.mock.mapticsmock.dto.request.place;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Valid
public class PlaceRequestDetail {

    @NotEmpty
    private String place_id;
    @NotNull(message = "플레이스 이름는 필수값입니다.")
    private String place_name;
    @NotNull
    private String address;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private double radius;

}
