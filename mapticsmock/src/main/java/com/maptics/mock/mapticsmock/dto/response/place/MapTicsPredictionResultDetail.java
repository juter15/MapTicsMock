package com.maptics.mock.mapticsmock.dto.response.place;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MapTicsPredictionResultDetail {

    private int avgCnt;
    private String place_id;

}
