package org.yakushev.shopwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.model.Product;
import org.yakushev.shopwebapp.service.ProductService;
import org.yakushev.shopwebapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:4200/"})
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Product> getAll(@RequestParam(name="page", defaultValue = "0") Integer page,
                                @RequestParam(name="size", defaultValue = "10") Integer size) {
        return productService.getAll(PageRequest.of(page, size));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getItemById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Product add(@RequestBody Product product, HttpServletRequest request) {
        userService.checkAdminRole(request);
        return productService.add(product);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Product update(@RequestBody Product product, HttpServletRequest request) {
        userService.checkAdminRole(request);
        return productService.update(product);
    }
}
