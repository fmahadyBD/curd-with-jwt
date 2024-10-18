package com.example.curd_with_jwt.reposioty;

import com.example.curd_with_jwt.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser,Long> {

    Optional<MyUser> findById(Long id);
    Optional<MyUser> findByEmail(String email);
}
