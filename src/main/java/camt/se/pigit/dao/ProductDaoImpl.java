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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

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
        List<Product> products  = new ArrayList<Product>();
        try {
            databaseReference.child("product").orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    LOGGOR.info("Show snapshot: " + snapshot.toString());
                    LOGGOR.info("Show count: " + snapshot.getChildrenCount());

                    for(DataSnapshot data: snapshot.getChildren()){
                        Product product = new Product();
                        LOGGOR.info("Show key: " + data.getKey());
                        product.setBarcodenumber(data.getKey());
                        product.setName((String) data.child("name").getValue());
                        LOGGOR.info("Show name: " + data.child("name").getValue());
                        product.setDescription((String) data.child("description").getValue());
                        LOGGOR.info("Show description: " + data.child("description").getValue());
                        product.setImage((String) data.child("image").getValue());
                        LOGGOR.info("Show image: " + data.child("image").getValue());
                        product.setPrice(data.child("price").getValue(Double.class));
                        LOGGOR.info("Show price: " + data.child("price").getValue());

                        LOGGOR.info("Product: "+product);
                        products.add(product);
                        LOGGOR.info("Products: "+products);

                    }

                    /*for (int i = 0; i<snapshot.getChildrenCount(); i++) {
                        Product product = new Product();
                        LOGGOR.info("Show key: " + snapshot.);
                        product.setBarcodenumber(data.getKey());
                        LOGGOR.info("Show Product: " + product.toString());
                        products.add(product);
                        LOGGOR.info("Show Products: " + products.toString());
                    }*/

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGOR.info("Products: "+products);
        return products;
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

    @Override
    public List<Product> findNameIgnoreCaseContaining(String searchText) {
        List<Product> result = new ArrayList<Product>();
        List<Product> products = getProducts();
        LOGGOR.info("Products "+products);

        for(int i=0;i<products.size();i++) {
            System.out.println(" " + i + " " + products.get(i).getName().contains(searchText));

            if(products.get(i).getName().contains(searchText)) {
                databaseReference.child("product").orderByChild("name").equalTo(products.get(i).getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        LOGGOR.info("Snapshot: " + snapshot);
                        Product product = new Product();

                        String barcodenumber = snapshot.getValue().toString().substring(1, 14);
                        System.out.println("barcode "+barcodenumber);
                        LOGGOR.info("Show key: " + snapshot.child(barcodenumber).getKey());
                        product.setBarcodenumber(snapshot.child(barcodenumber).getKey());
                        product.setName((String) snapshot.child(barcodenumber).child("name").getValue());
                        LOGGOR.info("Show name: " + snapshot.child(barcodenumber).child("name").getValue());
                        product.setDescription((String) snapshot.child(barcodenumber).child("description").getValue());
                        LOGGOR.info("Show description: " + snapshot.child(barcodenumber).child("description").getValue());
                        product.setImage((String) snapshot.child(barcodenumber).child("image").getValue());
                        LOGGOR.info("Show image: " + snapshot.child(barcodenumber).child("image").getValue());
                        product.setPrice(snapshot.child(barcodenumber).child("price").getValue(Double.class));
                        LOGGOR.info("Show price: " + snapshot.child(barcodenumber).child("price").getValue());
                        result.add(product);

                /*LOGGOR.info("findByBarcodenumber(): "+snapshot.toString());
                products.setBarcodenumber(barcodenumber);
                String _name = (String) snapshot.child(barcodenumber).child("name").getValue();
                LOGGOR.info(_name);
                products.setName(_name);
                String _description = (String) snapshot.child(barcodenumber).child("description").getValue();
                LOGGOR.info(_description);
                products.setDescription(_description);
                String _image = (String) snapshot.child(barcodenumber).child("image").getValue();
                LOGGOR.info(_image);
                products.setImage(_image);
                double _price = (Double) snapshot.child(barcodenumber).child("price").getValue();
                LOGGOR.info(String.valueOf(_price));
                products.setPrice(_price);*/

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //LOGGOR.info(products.toString());
            /*if(products.getName()!=null) {
                return products;
            }else return null;*/
            return result;
        }

    }
}
