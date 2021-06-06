package com.maptics.mock.mapticsmock.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maptics.mock.mapticsmock.dto.request.user.UserRequestDetail;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseListDetail {

    @JsonProperty("user_detail")
    private List<UserRequestDetail> userResponseDetailList;

    @JsonProperty("user_short")
    private List<String> ids;
}
