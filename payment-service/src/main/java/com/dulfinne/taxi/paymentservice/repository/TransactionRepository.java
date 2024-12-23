package com.dulfinne.taxi.paymentservice.repository;

import com.dulfinne.taxi.paymentservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Page<Transaction> findByWalletId(Long walletId, Pageable pageable);
}
