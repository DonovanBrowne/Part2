package uk.ac.le.co2103.part2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import uk.ac.le.co2103.part2.shoppinglistapp.Product;
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;
import uk.ac.le.co2103.part2.shoppinglistapp.dao.ProductDao;
import uk.ac.le.co2103.part2.shoppinglistapp.dao.ShoppingListDao;

@Database(entities = {ShoppingList.class, Product.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShoppingListDao shoppingListDao();
    public abstract ProductDao productDao();
    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "shopping-list-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;}
}