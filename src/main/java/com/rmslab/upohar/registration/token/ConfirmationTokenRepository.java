package com.rmslab.upohar.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token) ;

    @Transactional
    @Modifying
    @Query("update ConfirmationToken c set c.confirmedEmailAt = :confirmedEmailAt where c.token= :token")
    int updateConfirmedEmailAt(@Param("token")String token, @Param("confirmedEmailAt") LocalDateTime confirmedEmailAt) ;
}
