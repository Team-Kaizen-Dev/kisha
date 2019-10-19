package com.teamkaizen.kisha.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
