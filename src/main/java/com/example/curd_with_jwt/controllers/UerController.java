package com.example.curd_with_jwt.controllers;

import com.example.curd_with_jwt.dto.MyUserDto;
import com.example.curd_with_jwt.entity.MyUser;
import com.example.curd_with_jwt.request.MyUserRequest;
import com.example.curd_with_jwt.request.UpdateMyUserRequest;
import com.example.curd_with_jwt.response.ApiResponse;
import com.example.curd_with_jwt.services.myuser.IMyUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class UerController {

    private final IMyUserService myUserService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewUser(@Validated @RequestBody MyUserRequest request) {
        try {
            MyUser user = myUserService.addNewUser(request);
            MyUserDto myUserDto = myUserService.getConvertedMyUser(user);
            return ResponseEntity.ok().body(new ApiResponse("Added", myUserDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Validated @RequestBody UpdateMyUserRequest request, @PathVariable Long id) {
        try {
            MyUser myUser = myUserService.updateUser(request, id);
            MyUserDto dto = myUserService.getConvertedMyUser(myUser);
            return ResponseEntity.ok().body(new ApiResponse("Successfully update", dto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error on update", e.getMessage()));
        }

    }

}
