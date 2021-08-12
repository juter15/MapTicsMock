package com.maptics.mock.mapticsmock.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.ResponseResult;
import com.maptics.mock.mapticsmock.dto.request.user.UserRequestDetail;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseList {
    private String code;
    private String message;
    private String request_id;

    @JsonProperty("data")
    private UserResponseListDetail userResponseListDetails;
}
