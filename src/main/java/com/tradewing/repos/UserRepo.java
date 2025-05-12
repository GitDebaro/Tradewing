package com.tradewing.repos;

import com.tradewing.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email); //Find users by their email for Login
    UserEntity findUserByEmail(String email); //Return a UserEntity
}