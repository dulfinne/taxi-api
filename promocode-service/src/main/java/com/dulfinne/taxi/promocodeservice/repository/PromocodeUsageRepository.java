package com.dulfinne.taxi.promocodeservice.repository;

import com.dulfinne.taxi.promocodeservice.model.PromocodeUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocodeUsageRepository extends MongoRepository<PromocodeUsage, String> {
  Page<PromocodeUsage> findByUsername(String username, Pageable pageable);
  boolean existsByUsernameAndCode(String username, String code);
}
