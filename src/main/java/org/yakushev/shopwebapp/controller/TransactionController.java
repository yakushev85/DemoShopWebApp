package org.yakushev.shopwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.dto.TransactionRequest;
import org.yakushev.shopwebapp.model.Transaction;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.service.TransactionService;
import org.yakushev.shopwebapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:4200/"})
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Transaction> getAll(@RequestParam(name="page", defaultValue = "0") Integer page,
                                    @RequestParam(name="size", defaultValue = "10") Integer size,
                                    HttpServletRequest request) {
        if (userService.isAdminRole(request)) {
            return transactionService.getAll(PageRequest.of(page, size));
        } else {
            User user = userService.getUserFromRequest(request);

            return transactionService.getByUserId(user.getId(), PageRequest.of(page, size));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Transaction getItemById(@PathVariable Long id, HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);

        Transaction transaction = transactionService.getById(id);
        if ((!userService.isAdminRole(request) && user.getId() != null && transaction.getUser().getId().equals(user.getId())) ||
                userService.isAdminRole(request)) {

            return transaction;
        } else {
            throw new IllegalArgumentException("Wrong access.");
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public Transaction add(@RequestBody TransactionRequest transactionDto, HttpServletRequest request) {
        if (!userService.isAdminRole(request)) {
            User user = userService.getUserFromRequest(request);

            transactionDto.setUserId(user.getId());

        }
        return transactionService.add(transactionDto);
    }
}
