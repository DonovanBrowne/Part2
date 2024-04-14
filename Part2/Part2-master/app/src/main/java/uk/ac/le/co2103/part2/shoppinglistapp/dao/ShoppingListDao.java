package uk.ac.le.co2103.part2.shoppinglistapp.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;
@Dao
public interface ShoppingListDao {
        @Query("SELECT * FROM ShoppingList")
        List<ShoppingList> getAllShoppingLists();

        // Other CRUD operations for ShoppingList
    }

