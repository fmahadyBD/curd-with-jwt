package com.example.curd_with_jwt.services.myuser;

import com.example.curd_with_jwt.dto.MyUserDto;
import com.example.curd_with_jwt.entity.MyUser;
import com.example.curd_with_jwt.exception.ResourceNotFoundException;
import com.example.curd_with_jwt.reposioty.MyUserRepository;
import com.example.curd_with_jwt.request.MyUserRequest;
import com.example.curd_with_jwt.request.UpdateMyUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService implements IMyUserService {

    private final MyUserRepository myUserRepository;

    @Override
    public MyUser addNewUser(MyUserRequest request) {
        return myUserRepository.save(createMyUser(request));
    }

    public MyUser createMyUser(MyUserRequest request) {
        return new MyUser(
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
    }

    @Override
    public List<MyUser> getAllUser() {
        return List.of();
    }

    @Override
    public MyUser findUserById(Long id) {
        return null;
    }

    @Override
    public MyUser updateUser(UpdateMyUserRequest request, long id) {
        return myUserRepository.findById(id)
                .map(existUser -> updateExistingUser(existUser, request))
                .map(myUserRepository::save).orElseThrow(() -> new ResourceNotFoundException("Unable to update the product"));

    }

    public MyUser updateExistingUser(MyUser user, UpdateMyUserRequest request) {
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return user;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public MyUserDto getConvertedMyUser(MyUser myUser) {
        return new MyUserDto(
                myUser.getId(),
                myUser.getEmail(),
                myUser.getRole()

        );
    }
}
