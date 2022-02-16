package com.jango.laundrysimple.repository;

import com.jango.laundrysimple.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByAddressId(Long addressId);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
