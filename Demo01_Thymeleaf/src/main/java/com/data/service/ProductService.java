package com.data.service;

import com.data.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    int delete(int id);
    int save(Product product);
}
