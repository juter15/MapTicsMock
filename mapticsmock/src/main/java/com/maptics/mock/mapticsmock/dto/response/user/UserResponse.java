package com.maptics.mock.mapticsmock.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponse {
    private ResponseResult responseResult;
    @JsonProperty("date")
    private List<UserResponseDetail> userResponseDetailList;
}
