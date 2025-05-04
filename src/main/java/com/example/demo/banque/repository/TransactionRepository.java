package com.example.demo.banque.repository;

import com.example.demo.banque.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByCompteId(int compteId);
}
