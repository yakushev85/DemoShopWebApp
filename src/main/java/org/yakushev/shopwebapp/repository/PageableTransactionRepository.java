package org.yakushev.shopwebapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yakushev.shopwebapp.model.Transaction;

public interface PageableTransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
}
