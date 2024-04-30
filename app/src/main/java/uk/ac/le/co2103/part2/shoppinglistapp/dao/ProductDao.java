package uk.ac.le.co2103.part2.shoppinglistapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.ac.le.co2103.part2.shoppinglistapp.Product;
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);
    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProducts();
    @Query("SELECT * FROM Product WHERE id = :productId")
    Product getProductById(int productId);

    @Query("SELECT * FROM product WHERE name = :name LIMIT 1")
    Product getProductByName(String name);

    @Update
    void updateProduct(Product productToUpdate);
    @Delete
    void deleteProduct(Product product);
    @Insert
    void insertProduct(Product product);
}
