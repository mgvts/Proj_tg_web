package com.proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.proj.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET password_sha=crypt(?2, gen_salt('bf')) where id=?1", nativeQuery = true)
    void updatePasswordSha(long id, String password);

//    @Modifying
//    @Query(value = "INSERT INTO users (login, password_sha) VALUES (?2, crypt(?3, gen_salt('bf')))", nativeQuery = true)
//    void register(long id, String login, String password);

    @Query(value = "SELECT * FROM users WHERE login = ?1 AND password_sha = crypt(?2, password_sha)", nativeQuery = true)
    User findByLoginAndPassword(String login, String password);

    List<User> findAllByOrderByIdDesc();

    @Query(value = "SELECT * FROM users WHERE login=?1", nativeQuery = true)
    User findByLogin(String login);

    int countByLogin(String login);

    User findUserByTgId(Long tgId);

    @Query(value = "SELECT * FROM users WHERE tg_id=?1", nativeQuery = true)
    User findByTgId(String tgId);
}
