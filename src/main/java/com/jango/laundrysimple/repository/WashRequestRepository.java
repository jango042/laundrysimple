package com.jango.laundrysimple.repository;

import com.jango.laundrysimple.model.WashRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WashRequestRepository extends JpaRepository<WashRequest, Long> {
    List<WashRequest> findByUserId(Long userId);
    List<WashRequest> findByUserIdAndClothType(Long userId, String clothType);
}
