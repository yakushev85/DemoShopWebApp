package org.yakushev.shopwebapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yakushev.shopwebapp.model.User;

public interface PageableUserRepository extends PagingAndSortingRepository<User, Long> {
}
