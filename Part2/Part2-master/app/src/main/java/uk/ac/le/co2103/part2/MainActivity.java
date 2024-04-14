package uk.ac.le.co2103.part2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
        adapter = new ShoppingListAdapter();
        recyclerView.setAdapter(adapter);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "shopping-list-db").build();

        // Load shopping lists from database and set them to adapter
        loadShoppingLists();


        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);


        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            Log.d(TAG, "Floating action button clicked.");
            Toast.makeText(getApplicationContext(), "Not implemented yet.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
            startActivityForResult(intent, REQUEST_CREATE_LIST);
        });


    }

    private void loadShoppingLists() {
        new Thread(() -> {
            List<ShoppingList> shoppingLists = database.shoppingListDao().getAllShoppingLists();
            runOnUiThread(() -> adapter.setShoppingLists(shoppingLists));
        }).start();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
            startActivity(intent);
        });


        class CreateListActivity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_create_list);

                EditText editTextName = findViewById(R.id.edit_text_name);
                ImageView imageView = findViewById(R.id.image_view);
                Button createButton = findViewById(R.id.button_create);

                createButton.setOnClickListener(view -> {
                    String name = editTextName.getText().toString();
                    // Retrieve image if needed
                    // Create shopping list
                    // Send data back to MainActivity
                    finish();


                });
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE_LIST && resultCode == RESULT_OK) {
            // Handle data returned from CreateListActivity
            if (data != null && data.hasExtra("shopping_list_name")) {
                String shoppingListName = data.getStringExtra("shopping_list_name");
                // Create the shopping list with the received name{

            }

        }
    }
}

