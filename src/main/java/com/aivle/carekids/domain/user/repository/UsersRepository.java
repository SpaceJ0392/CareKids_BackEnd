package com.aivle.carekids.domain.user.repository;

import com.aivle.carekids.domain.user.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>, UsersRepositoryCustom {

    boolean existsByUsersEmail(String usersEmail);
    boolean existsByUsersNickname(String usersNickname);

    Users findByUsersEmail(String usersEmail);
    Optional<Users> findByUsersId(Long usersId);
}
