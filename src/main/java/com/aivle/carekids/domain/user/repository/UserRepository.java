package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserEmail(String usersEmail);
    boolean existsByUserNickname(String usersNickname);
//    Optional<User> findByUserEmail(String usersEmail);
    User findByUserEmail(String usersEmail);
    Optional<User> findByUserId(Long userId);
}
