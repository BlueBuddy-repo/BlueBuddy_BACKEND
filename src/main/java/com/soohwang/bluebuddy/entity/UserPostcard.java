package com.soohwang.bluebuddy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "user_postcard")
public class UserPostcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPostcardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postcard_image_id")
    @JsonBackReference
    private PostcardImage postcardImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postcard_text_id")
    @JsonBackReference
    private PostcardText postcardText;
}


