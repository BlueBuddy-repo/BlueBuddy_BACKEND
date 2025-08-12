package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.PostcardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostcardImageRepository extends JpaRepository<PostcardImage, Long> {
}
