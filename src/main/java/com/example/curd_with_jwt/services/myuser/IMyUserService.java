package com.example.curd_with_jwt.services.myuser;

import com.example.curd_with_jwt.dto.MyUserDto;
import com.example.curd_with_jwt.entity.MyUser;
import com.example.curd_with_jwt.request.MyUserRequest;
import com.example.curd_with_jwt.request.UpdateMyUserRequest;

import java.util.List;

public interface IMyUserService {
    MyUser addNewUser(MyUserRequest myUserRequest);
    List<MyUser> getAllUser();
    MyUser findUserById(Long id);
    MyUser updateUser(UpdateMyUserRequest updateMyUserRequest, long
                       id);
    void delete(Long id);
    MyUserDto getConvertedMyUser(MyUser myUser);

    List<MyUserDto> usersConvertedToDto(List<MyUser> user);
}
