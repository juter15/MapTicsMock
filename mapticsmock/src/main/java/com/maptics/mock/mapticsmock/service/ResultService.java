package com.maptics.mock.mapticsmock.service;

import com.maptics.mock.mapticsmock.dto.ResponseResult;
import com.maptics.mock.mapticsmock.dto.response.place.PlaceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {

    private static String authcode = "maptics";
    //Response
    public ResponseResult setResult(String Authorization, String TID){
        ResponseResult responseResult = new ResponseResult();
            if(Authorization.isEmpty()){
            responseResult.setCode("4010");
            responseResult.setMessage("NEED_ACCESS_TOKEN");
        }
            else if(!authcode.equals(Authorization)){
            responseResult.setCode("4011");
            responseResult.setMessage("INVALID_ACCESS_TOKEN");
        }
            else{
            responseResult.setCode("2000");
            responseResult.setMessage("OK");
        }

            responseResult.setRequest_id(TID);
        return responseResult;
    }

}
