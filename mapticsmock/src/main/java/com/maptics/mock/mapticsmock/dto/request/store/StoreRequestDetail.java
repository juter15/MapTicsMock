package com.maptics.mock.mapticsmock.dto.request.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class StoreRequestDetail {
    private String store_id;
    private String store_name;
    private String address;
    private String phone;
    private double latitude;
    private double longitude;
    private String business_type;
    private String product_type;
}
