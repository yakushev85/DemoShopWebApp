package org.yakushev.shopwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yakushev.shopwebapp.dto.TransactionRequest;
import org.yakushev.shopwebapp.model.Transaction;
import org.yakushev.shopwebapp.repository.PageableTransactionRepository;
import org.yakushev.shopwebapp.repository.TransactionRepository;
import org.yakushev.shopwebapp.repository.JwtTokenRepository;

import java.lang.reflect.InvocationTargetException;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private PageableTransactionRepository pageableTransactionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Override
	public Page<Transaction> getAll(Pageable pageable) {
		return pageableTransactionRepository.findAll(pageable);
	}

	@Override
	public Transaction getById(Long id) {
		return transactionRepository.findById(id).orElse(null);
	}

	@Override
	public Page<Transaction> getByUserId(Long userId, Pageable pageable) {
		return transactionRepository.findByUserIdOrderByIdDesc(userId, pageable);
	}

	@Override
	@Transactional
	public Transaction add(TransactionRequest value) {
		Transaction transaction = new Transaction();
		transaction.setDescription(value.getDescription());
		transaction.setUser(userService.getById(value.getUserId()));
		transaction.setProduct(productService.getById(value.getProductId()));
		return transactionRepository.save(transaction);
	}

	@Override
	@Transactional
	public Transaction update(Transaction value) {
		Transaction oldValue = getById(value.getId());
		try {
			Transaction updatedValue = (new Transaction()).merge(getById(value.getId()), value);
			return transactionRepository.save(updatedValue);
		} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			return oldValue;
		}
	}

}
