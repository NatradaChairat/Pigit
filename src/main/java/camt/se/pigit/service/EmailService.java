package camt.se.pigit.service;

import camt.se.pigit.entity.Product;

public interface EmailService {
    Boolean sendEmail(Product product, int quantity,String name, String email, String addInfo);
}
