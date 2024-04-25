package uk.ac.le.co2103.part2.shoppinglistapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uk.ac.le.co2103.part2.shoppinglistapp.Product;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);
    @Query("SELECT * FROM Product WHERE listId = :listId")
    LiveData<List<Product>>getProductsForList(int listId);
    @Query("SELECT * FROM Product WHERE id = :productId")
    Product getProductById(int productId);

    @Update
    void updateProduct(Product productToUpdate);
    @Delete
    void deleteProduct(Product product);
    @Insert
    void insertProduct(Product product);
}
