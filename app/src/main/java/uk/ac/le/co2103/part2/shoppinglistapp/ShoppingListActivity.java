package uk.ac.le.co2103.part2.shoppinglistapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.database.AppDatabase;
import uk.ac.le.co2103.part2.shoppinglistapp.adapter.ProductListAdapter;

public class ShoppingListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private AppDatabase database;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list); // Make sure you have this layout file


        recyclerView = findViewById(R.id.recyclerview_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductListAdapter();
        recyclerView.setAdapter(adapter);

        database = AppDatabase.getInstance(this);


        // Retrieve the SHOPPING_LIST_ID from the intent
        int shoppingListId = getIntent().getIntExtra("SHOPPING_LIST_ID", -1);
        if (shoppingListId != -1) {
            // Load the products for the given shopping list ID

            loadProducts(shoppingListId);
        } else {
            Toast.makeText(this, "Error: Shopping list ID not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no ID is found
        }


        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });


        adapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Product product) {
            // Display a dialog with options to edit or delete the selected product
            showEditDialog(product);
        }
    });
}

    private void loadProducts(int shoppingListId) {
        database.productDao().getProductsForList(shoppingListId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {
                // Update the cached copy of the products in the adapter.
                adapter.setProducts(products);

            }

        });
    }


    private void showEditDialog(final Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options")
                .setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0: // Edit
                                Intent editIntent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
                                editIntent.putExtra("PRODUCT_ID", product.getId());
                                startActivity(editIntent);
                                break;
                            case 1: // Delete
                                deleteProduct(product);
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void deleteProduct(Product product) {
        database.productDao().deleteProduct(product);
    }
}



