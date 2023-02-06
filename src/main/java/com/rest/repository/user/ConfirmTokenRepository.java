package com.rest.repository.user;

import com.rest.model.user.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {
    ConfirmToken findByToken(String token);
}
