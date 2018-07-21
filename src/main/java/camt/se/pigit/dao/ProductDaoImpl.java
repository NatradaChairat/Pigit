package camt.se.pigit.dao;

import camt.se.pigit.config.FirebaseConfig;
import camt.se.pigit.entity.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {
    Logger LOGGOR = LoggerFactory.getLogger(ProductDaoImpl.class.getName());
    DatabaseReference databaseReference;

    @Autowired
    public void setDatabaseReference (FirebaseConfig firebaseConfig){
        this.databaseReference = firebaseConfig.firebaseDatabase();
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = null;

            databaseReference.child("product").orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    LOGGOR.info("Show snapshot: " + snapshot.toString());
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Product product = new Product();
                        LOGGOR.info("Show key: " + data.getKey());
                        product.setBarcodenumber(data.getKey());
                        LOGGOR.info("Show Product: " + product.toString());
                        products.add(product);
                        LOGGOR.info("Show Products: " + products.toString());

                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            return null;
    }

    @Override
    public Product addProduct(Product product) {
        LOGGOR.info(product.toString());
        DatabaseReference usersRef = databaseReference.child("product");
        Map<String, Object> productTableMap = new HashMap<>();
        productTableMap.put("name", product.getName());
        productTableMap.put("description",product.getDescription());
        productTableMap.put("price", product.getPrice());
        productTableMap.put("image", product.getImage());
        usersRef.child(product.getBarcodenumber()).setValueAsync(productTableMap);
        return product;
    }

    @Override
    public Boolean removeProduct(String barcodenumber) {
        LOGGOR.info(barcodenumber);
        databaseReference.child("product").child(barcodenumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                LOGGOR.info(snapshot.getValue().toString());
                snapshot.getRef().removeValueAsync();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return null;
    }

    @Override
    public Product findByBarcodenumber(String barcodenumber) {
        Product product = new Product();
        databaseReference.child("product").orderByKey().equalTo(barcodenumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               LOGGOR.info("findByBarcodenumber(): "+snapshot.toString());
               product.setBarcodenumber(barcodenumber);
               String _name = (String) snapshot.child(barcodenumber).child("name").getValue();
               LOGGOR.info(_name);
               product.setName(_name);
               String _description = (String) snapshot.child(barcodenumber).child("description").getValue();
               LOGGOR.info(_description);
               product.setDescription(_description);
               String _image = (String) snapshot.child(barcodenumber).child("image").getValue();
               LOGGOR.info(_image);
               product.setImage(_image);
               double _price = (Double) snapshot.child(barcodenumber).child("price").getValue();
               LOGGOR.info(String.valueOf(_price));
               product.setPrice(_price);


            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            LOGGOR.info(product.toString());
            if(product.getName()!=null) {
                return product;
            }else return null;
        }
    }
}
