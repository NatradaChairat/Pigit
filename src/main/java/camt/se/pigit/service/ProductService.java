package camt.se.pigit.service;

import camt.se.pigit.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product addProduct(Product product);
    Boolean remove(String barcodenumber);
    Product findProductByBarcodenumber(String barcodeNumber);
    List<Product> findNameIgnoreCaseContaining(String searchText);
}
