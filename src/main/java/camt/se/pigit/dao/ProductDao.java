package camt.se.pigit.dao;

import camt.se.pigit.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts();
    Product addProduct(Product product);
    Boolean removeProduct(String barcodenumber);
    Product findByBarcodenumber(String barcodenumber);

    List<Product> findNameIgnoreCaseContaining(String searchText);

}
