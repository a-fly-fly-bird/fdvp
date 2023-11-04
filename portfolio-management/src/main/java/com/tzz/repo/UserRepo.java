package com.tzz.repo;

import com.tzz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    @Query(value="select * from user where user.name = ?1", nativeQuery=true)
    UserEntity findByUsername(String name);
}
