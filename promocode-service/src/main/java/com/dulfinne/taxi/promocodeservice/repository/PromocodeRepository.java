package com.dulfinne.taxi.promocodeservice.repository;

import com.dulfinne.taxi.promocodeservice.model.Promocode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocodeRepository extends MongoRepository<Promocode, String> {
  Optional<Promocode> findByCode(String code);
}
