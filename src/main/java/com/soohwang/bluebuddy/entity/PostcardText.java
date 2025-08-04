package com.soohwang.bluebuddy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "postcard_text")
public class PostcardText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postcardTextId;

    @Column(nullable = false)
    private String postcardText;

    @OneToMany(mappedBy = "postcardText", cascade = CascadeType.REMOVE)
    private List<UserPostcard> userPostcards;
}

