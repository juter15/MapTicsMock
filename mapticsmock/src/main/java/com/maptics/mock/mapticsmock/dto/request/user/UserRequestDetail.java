package com.maptics.mock.mapticsmock.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDetail {
    private String user_id;
    private String store_id;
    private String product_type;
}
