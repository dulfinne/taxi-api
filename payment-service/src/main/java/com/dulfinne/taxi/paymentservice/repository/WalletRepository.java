package com.dulfinne.taxi.paymentservice.repository;

import com.dulfinne.taxi.paymentservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
  Optional<Wallet> findByUsername(String username);
  List<Wallet> findAllByDebtGreaterThan(BigDecimal debt);
}
