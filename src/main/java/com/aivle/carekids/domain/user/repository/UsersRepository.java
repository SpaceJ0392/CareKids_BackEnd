package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.user.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByUsersEmail(String usersEmail);
    boolean existsByUsersNickname(String usersNickname);
}
