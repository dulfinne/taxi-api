package com.dulfinne.taxi.paymentservice.service.impl;

import com.dulfinne.taxi.paymentservice.model.Wallet;
import com.dulfinne.taxi.paymentservice.repository.WalletRepository;
import com.dulfinne.taxi.paymentservice.service.SchedulerService;
import com.dulfinne.taxi.paymentservice.service.TransactionService;
import com.dulfinne.taxi.paymentservice.util.DescriptionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final WalletRepository repository;
    private final TransactionService transactionService;

    @Override
    @Scheduled(cron = "${scheduler.debt-repayment.cron}")
    public void scheduleDebtRepayment() {
        List<Wallet> wallets = repository.findAllByDebtGreaterThan(BigDecimal.ZERO);

        for (Wallet wallet : wallets) {
            repayDebt(wallet);
        }
    }

    private void repayDebt(Wallet wallet) {
        BigDecimal debt = wallet.getDebt();
        BigDecimal currentBalance = wallet.getBalance();

        if (currentBalance.compareTo(debt) >= 0) {
            BigDecimal newBalance = currentBalance.subtract(debt);
            wallet.setBalance(newBalance);
            wallet.setDebt(BigDecimal.ZERO);

            repository.save(wallet);
            transactionService.createTransaction(wallet, debt.negate(), DescriptionConstants.DEBT_REPAYMENT);
        }
    }
}
