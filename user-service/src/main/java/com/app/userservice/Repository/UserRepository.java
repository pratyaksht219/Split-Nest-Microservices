package com.app.userservice.Repository;

import com.app.userservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE :groupId MEMBER OF u.groupIds")
    List<User> findUsersByGroupId(@Param("groupId") Long groupId);

    List<User> findAllByGroupIdsContaining(Long groupId);
}
