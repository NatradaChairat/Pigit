package camt.se.pigit.controller;

import camt.se.pigit.entity.Product;
import camt.se.pigit.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController("/product")
public class ProductController {
    Logger LOGGER = LoggerFactory.getLogger(ProductController.class.getName());
    ProductService productService;

    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }
    @GetMapping("product/get/{barcodenumber}")
    public ResponseEntity getProductByBarcodenumber(@PathVariable("barcodenumber")String barcodenumber){
        LOGGER.info("getProductByBarcodenumber(): "+barcodenumber);
        Product product = productService.findProductByBarcodenumber(barcodenumber);
        LOGGER.info("Return: "+product);
        if(product != null){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    @GetMapping("/product")
    public ResponseEntity<?> getProduct(){
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("product/remove/{barcodenumber}")
    public ResponseEntity removeProduct(@PathVariable("barcodenumber")String barcodenumber){
        LOGGER.info("removeProduct(): "+barcodenumber);
        productService.remove(barcodenumber);
        return ResponseEntity.ok(barcodenumber);
    }

    @PostMapping("product/create")
    public ResponseEntity<?> uploadProduct(@RequestBody Product product){
        LOGGER.info("uploadProduct(): "+product);
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
