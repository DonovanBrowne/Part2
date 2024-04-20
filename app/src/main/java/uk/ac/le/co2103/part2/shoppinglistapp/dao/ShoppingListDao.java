package uk.ac.le.co2103.part2.shoppinglistapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;
@Dao
public interface ShoppingListDao {
    @Insert
    void insert(ShoppingList shoppingList);
    @Query("SELECT * FROM ShoppingList")
    LiveData<List<ShoppingList>>getAllShoppingLists();

}


