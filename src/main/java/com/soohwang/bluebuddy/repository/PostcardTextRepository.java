package com.soohwang.bluebuddy.repository;

import com.soohwang.bluebuddy.entity.PostcardText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostcardTextRepository extends JpaRepository<PostcardText, Long> {
}
