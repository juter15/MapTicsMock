package com.maptics.mock.mapticsmock.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseDetail {
    private String user_id;
    private Integer ret_value;

}
