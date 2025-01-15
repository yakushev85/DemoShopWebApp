package org.yakushev.shopwebapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yakushev.shopwebapp.model.Product;

public interface PageableProductRepository extends PagingAndSortingRepository<Product, Long> {
}
