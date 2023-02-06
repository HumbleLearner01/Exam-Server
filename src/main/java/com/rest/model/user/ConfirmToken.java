package com.rest.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ConfirmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;

    private String token;
    private Instant createdTime;

    /*relationship*/
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    /*end of relationship*/

    public ConfirmToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.createdTime = Instant.now();
    }
}