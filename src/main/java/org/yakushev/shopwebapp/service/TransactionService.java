package org.yakushev.shopwebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yakushev.shopwebapp.dto.TransactionRequest;
import org.yakushev.shopwebapp.model.Transaction;

import java.util.List;

public interface TransactionService {

	Page<Transaction> getAll(Pageable pageable);

	Transaction getById(Long id);

	Page<Transaction> getByUserId(Long userId, Pageable pageable);

	Transaction add(TransactionRequest value);

	Transaction update(Transaction value);

}
