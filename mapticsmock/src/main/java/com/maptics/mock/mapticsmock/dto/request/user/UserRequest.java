package com.maptics.mock.mapticsmock.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserRequest {

    @JsonProperty("user_list")
    private List<UserRequestDetail> userRequestDetail;
}
