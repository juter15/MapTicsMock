package com.maptics.mock.mapticsmock.controller;

import com.maptics.mock.mapticsmock.dto.request.store.StoreRequest;
import com.maptics.mock.mapticsmock.dto.request.store.StoreRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.user.UserRequest;
import com.maptics.mock.mapticsmock.dto.request.user.UserRequestDetail;
import com.maptics.mock.mapticsmock.dto.request.user.UserRequestList;
import com.maptics.mock.mapticsmock.dto.response.storeresponse.StoreResponse;
import com.maptics.mock.mapticsmock.dto.response.storeresponse.StoreResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.user.UserResponse;
import com.maptics.mock.mapticsmock.dto.response.user.UserResponseDetail;
import com.maptics.mock.mapticsmock.dto.response.user.UserResponseList;
import com.maptics.mock.mapticsmock.dto.response.user.UserResponseListDetail;
import com.maptics.mock.mapticsmock.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/v2/maptics/bizniz/user")
public class UserController {
    private final ResultService resultService;
    Map<String, UserRequestDetail> userList = new HashMap<>();

    @PostMapping
    public ResponseEntity UserCreate(
            @RequestHeader String Authorization,
            @RequestBody UserRequest userRequest
    ){
            log.info("User Create : {}", userRequest);

            UserResponse userResponse = new UserResponse();
            List<UserResponseDetail> detailList = new ArrayList<>();

            for(UserRequestDetail create : userRequest.getUserRequestDetail()){
                if(!userList.containsKey(create.getUser_id())){
                    //중복 처리
                    userList.put(create.getUser_id(), create);
                    UserResponseDetail setDetail = new UserResponseDetail();
                    setDetail.setUser_id(create.getUser_id());
                    setDetail.setRet_value(0);

                    detailList.add(setDetail);
                }
                else{
                    UserResponseDetail setDetail = new UserResponseDetail();
                    setDetail.setUser_id(create.getUser_id());
                    setDetail.setRet_value(1);

                    detailList.add(setDetail);
                }

            }
            log.info("userList : {}", userList);

        userResponse.setResponseResult(resultService.setResult(Authorization, "create"));
        userResponse.setUserResponseDetailList(detailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @PostMapping("/update")
    public ResponseEntity UserUpdate(
            @RequestHeader String Authorization,
            @RequestBody UserRequest userRequest
    ){
        log.info("User Update : {}", userRequest);

        UserResponse userResponse = new UserResponse();
        List<UserResponseDetail> detailList = new ArrayList<>();

        for(UserRequestDetail update : userRequest.getUserRequestDetail()){
            if(userList.containsKey(update.getUser_id())){
                userList.replace(update.getUser_id(), update);
                UserResponseDetail setDetail = new UserResponseDetail();
                setDetail.setUser_id(update.getUser_id());
                setDetail.setRet_value(0);

                detailList.add(setDetail);
            }
            else{
                //없는 사용자
                UserResponseDetail setDetail = new UserResponseDetail();
                setDetail.setUser_id(update.getUser_id());
                setDetail.setRet_value(2);

                detailList.add(setDetail);
            }

        }
        log.info("userList : {}", userList);

        userResponse.setResponseResult(resultService.setResult(Authorization, "update"));
        userResponse.setUserResponseDetailList(detailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }
    @PostMapping("/delete")
    public ResponseEntity UserDelete(
            @RequestHeader String Authorization,
            @RequestBody UserRequestList userRequestList
    ){
        log.info("User Delete : {}", userRequestList);

        UserResponse userResponse = new UserResponse();
        List<UserResponseDetail> detailList = new ArrayList<>();

        for(String update : userRequestList.getIds()){
            if(userList.containsKey(update)){
                userList.remove(update);
                UserResponseDetail setDetail = new UserResponseDetail();
                setDetail.setUser_id(update);
                setDetail.setRet_value(0);

                detailList.add(setDetail);
            }
            else{
                //없는 사용자
                UserResponseDetail setDetail = new UserResponseDetail();
                setDetail.setUser_id(update);
                setDetail.setRet_value(2);

                detailList.add(setDetail);
            }

        }
        log.info("userList : {}", userList);

        userResponse.setResponseResult(resultService.setResult(Authorization, "delete"));
        userResponse.setUserResponseDetailList(detailList);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @PostMapping("/list")
    public ResponseEntity UserList(
            @RequestHeader String Authorization,
            @RequestBody UserRequestList userRequestList
    ){
        log.info("User list : {}", userRequestList);

        UserResponseList userResponseList = new UserResponseList();

        UserResponseListDetail list = new UserResponseListDetail();
        //List<UserRequestDetail> setDetail = new ArrayList<>();



        if(userRequestList.isDetail_req()){
            //detail
            List<UserRequestDetail> setDetail = new ArrayList<>();
            for(String ids : userRequestList.getIds()) {
                setDetail.addAll(userList.values().stream().filter(li -> li.getUser_id().equals(ids)).collect(Collectors.toList()));
                //setDetail.add(add);
                log.info("list : {}", setDetail );
            }
            list.setUserResponseDetailList(setDetail);
        }
        else{
            List<String> Idlist = new ArrayList<>();
            for(String ids : userRequestList.getIds()) {

                Idlist.addAll(userList.values().stream()
                        .map(UserRequestDetail::getUser_id)
                        .filter(user_id -> user_id.equals(ids))
                        .collect(Collectors.toList()));
            }
            list.setIds(Idlist);
                        //.collect(Collectors.toCollection(ArrayList::new));
            log.info("Idlist : {}", list );
        }

        userResponseList.setUserResponseListDetails(list);
        userResponseList.setUserResponseListDetails(list);
        userResponseList.setResponseResult(resultService.setResult(Authorization, "list"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponseList);
    }
}
