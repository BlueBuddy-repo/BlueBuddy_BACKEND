package com.soohwang.bluebuddy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "postcard_image")
public class PostcardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postcardImageId;

    @Column(nullable = false)
    private String imagePath;

    @OneToMany(mappedBy = "postcardImage", cascade = CascadeType.REMOVE)
    private List<UserPostcard> userPostcards;
}
