package uk.ac.le.co2103.part2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import uk.ac.le.co2103.part2.shoppinglistapp.Product;
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;
import uk.ac.le.co2103.part2.shoppinglistapp.dao.ProductDao;
import uk.ac.le.co2103.part2.shoppinglistapp.dao.ShoppingListDao;

@Database(entities = {ShoppingList.class, Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShoppingListDao shoppingListDao();
    public abstract ProductDao productDao();
}
