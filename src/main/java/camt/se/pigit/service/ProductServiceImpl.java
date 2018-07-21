package camt.se.pigit.service;

import camt.se.pigit.dao.ProductDao;
import camt.se.pigit.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;


    @Override
    public List<Product> getProducts() {
        return productDao.getProducts();
    }

    @Override
    public Product addProduct(Product product) {
        return productDao.addProduct(product);
    }

    @Override
    public Boolean remove(String barcodenumber) {
        return productDao.removeProduct(barcodenumber);
    }

    @Override
    public Product findProductByBarcodenumber(String barcodeNumber) {
        return productDao.findByBarcodenumber(barcodeNumber);
    }


}
