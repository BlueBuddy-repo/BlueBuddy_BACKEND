package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.UserPostcard;
import com.soohwang.bluebuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostcardRepository extends JpaRepository<UserPostcard, Long> {
    List<UserPostcard> findByUser(User user);
}
