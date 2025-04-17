package com.instalab.repositories;

import com.instalab.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByUserId(UUID UserId);
}
