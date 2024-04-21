package uk.ac.le.co2103.part2.shoppinglistapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.shoppinglistapp.adapter.ProductListAdapter;

public class ShoppingListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list); // Make sure you have this layout file

        recyclerView = findViewById(R.id.recyclerview_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductListAdapter();
        recyclerView.setAdapter(adapter);

        // Retrieve the SHOPPING_LIST_ID from the intent
        int shoppingListId = getIntent().getIntExtra("SHOPPING_LIST_ID", -1);
        if (shoppingListId != -1) {
            // Load the products for the given shopping list ID
            loadProducts(shoppingListId);
        } else {
            Toast.makeText(this, "Error: Shopping list ID not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no ID is found
        }
    }

    private void loadProducts(int shoppingListId) {
        // Implement the method to load products based on the shopping list ID
        // For example, query the database and set the products to the adapter
    }
}
