package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.le.co2103.part2.database.AppDatabase;
import uk.ac.le.co2103.part2.shoppinglistapp.CreateListActivity;
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;
import uk.ac.le.co2103.part2.shoppinglistapp.adapter.ShoppingListAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CREATE_LIST = 1;
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private AppDatabase database;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = AppDatabase.getInstance(this);

        adapter = new ShoppingListAdapter(database);
        recyclerView.setAdapter(adapter);

        // Load shopping lists from database and set them to adapter
        loadShoppingLists();

        Log.d(TAG, "onCreate()");

        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            Log.d(TAG, "Floating action button clicked.");
            Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_LIST, null); // Passing null for the Bundle
        });
    }

    private void loadShoppingLists() {
        database.shoppingListDao().getAllShoppingLists().observe(this, new Observer<List<ShoppingList>>() {
            @Override
            public void onChanged(@Nullable final List<ShoppingList> shoppingLists) {
                // Update the cached copy of the shopping lists in the adapter.
                adapter.setShoppingLists(shoppingLists);
            }
        });
    }
}


