package uk.ac.le.co2103.part2.shoppinglistapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.ac.le.co2103.part2.shoppinglistapp.Product;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);
    @Query("SELECT * FROM Product WHERE listId = :listId")
    List<Product> getProductsForList(int listId);

    // Other CRUD operations for Product
}
