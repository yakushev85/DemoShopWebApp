package org.yakushev.shopwebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yakushev.shopwebapp.model.Product;

public interface ProductService {

	Page<Product> getAll(Pageable pageable);

	Product getById(Long id);

	Product add(Product value);

	Product update(Product value);

}
