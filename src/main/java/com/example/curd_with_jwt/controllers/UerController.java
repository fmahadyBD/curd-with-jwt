package com.example.curd_with_jwt.controllers;

import com.example.curd_with_jwt.dto.MyUserDto;
import com.example.curd_with_jwt.entity.LoginForm;
import com.example.curd_with_jwt.entity.MyUser;
import com.example.curd_with_jwt.request.MyUserRequest;
import com.example.curd_with_jwt.request.UpdateMyUserRequest;
import com.example.curd_with_jwt.response.ApiResponse;
import com.example.curd_with_jwt.services.myuser.IMyUserService;
import com.example.curd_with_jwt.services.userdetailsservice.JwtService;
import com.example.curd_with_jwt.services.userdetailsservice.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
public class UerController {

    @Autowired
    private  IMyUserService myUserService;
    @Autowired
    private  AuthenticationManager manager;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  MyUserDetailsService myUserDetailsService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUser(){
        try{
           List<MyUser> users =myUserService.getAllUser();
           List<MyUserDto> convertedUserDto = myUserService.usersConvertedToDto(users);
           return ResponseEntity.ok().body(new ApiResponse("All user",convertedUserDto));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id){
        try{
            MyUser myUser = myUserService.findUserById(id);
            MyUserDto myUserDto = myUserService.getConvertedMyUser(myUser);
            return ResponseEntity.ok().body(new ApiResponse("Found!",myUserDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Not found",e.getMessage()));
        }
    }

    @PostMapping("/registration/add")
    public ResponseEntity<ApiResponse> addNewUser(@Validated @RequestBody MyUserRequest request) {
        try {
            MyUser user = myUserService.addNewUser(request);
            MyUserDto myUserDto = myUserService.getConvertedMyUser(user);
            return ResponseEntity.ok().body(new ApiResponse("Added", myUserDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Validated @RequestBody UpdateMyUserRequest request, @PathVariable Long id) {
        try {

            MyUser myUser = myUserService.updateUser(request, id);
            MyUserDto dto = myUserService.getConvertedMyUser(myUser);
            return ResponseEntity.ok().body(new ApiResponse("Successfully update", dto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error on update", e.getMessage()));
        }

    }
    @DeleteMapping("/admin/delete/{id}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        try{
            myUserService.delete(id);
            return ResponseEntity.ok().body(new ApiResponse("Successfully delete user",id));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/authentication")
    public  String athenticationAndGetToken(@RequestBody LoginForm loginForm){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
           loginForm.email(),loginForm.password()
        ));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(myUserDetailsService.loadUserByUsername(loginForm.email()));
        }else{
            throw new UsernameNotFoundException("Invalid");
        }


    }

}
