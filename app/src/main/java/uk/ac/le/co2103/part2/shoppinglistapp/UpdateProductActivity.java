package uk.ac.le.co2103.part2.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.database.AppDatabase;

public class UpdateProductActivity extends AppCompatActivity {

    private TextView productName;
    private EditText editTextQuantity;

    private TextView productUnit;
    private Button buttonSave;
    private int productId;
    private AppDatabase database;
    private Product productToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        database = AppDatabase.getInstance(this);

        productName = findViewById(R.id.productName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        buttonSave = findViewById(R.id.buttonSave);
        productUnit = findViewById(R.id.productUnit);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        if (productId != -1) {
            getProductDetails(productId);
        } else {
            Toast.makeText(this, "Error: Product ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        Button buttonIncrement = findViewById(R.id.buttonIncrement);
        Button buttonDecrement = findViewById(R.id.buttonDecrement);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick(v);
            }
        });

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQuantity();
            }
        });

        buttonDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQuantity();
            }
        });

    }

    private void getProductDetails(int productId) {
        new Thread(() -> {
            final Product product = database.productDao().getProductById(productId);
            runOnUiThread(() -> {
                if (product != null) {
                    productName.setText(product.getName());
                    editTextQuantity.setText(String.valueOf(product.getQuantity()));
                    productUnit.setText((product.getUnit()));
                } else {
                    Toast.makeText(this, "Error: Product not found.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();
    }

    private void incrementQuantity() {
        String quantityStr = editTextQuantity.getText().toString().trim();
        int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr);
        quantity++;
        editTextQuantity.setText(String.valueOf(quantity));
    }

    private void decrementQuantity() {
        String quantityStr = editTextQuantity.getText().toString().trim();
        int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr);
        if (quantity > 0) {
            quantity--;
            editTextQuantity.setText(String.valueOf(quantity));
        }
    }

    // Method to handle Save button click
    public void onSaveButtonClick(View view) {
        String quantityStr = editTextQuantity.getText().toString().trim();
        int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr);

        // Update product details in the database asynchronously
        new Thread(() -> {
            // Access the database
            String productNameStr = productName.getText().toString();
            Product existingProduct = database.productDao().getProductByName(productNameStr);

            if (existingProduct != null) {
                // Update the quantity of the existing product
                existingProduct.setQuantity(quantity);
                // Update the product in the database
                database.productDao().updateProduct(existingProduct);
                // Show a toast message indicating successful update on the UI thread
                runOnUiThread(() -> {
                    Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    // Start ShoppingListActivity
                    Intent intent = new Intent(this, ShoppingListActivity.class);
                    startActivity(intent);
                    finish(); // Finish UpdateProductActivity to return to ShoppingListActivity
                });
            } else {
                // If the product doesn't exist, show a toast message on the UI thread
                runOnUiThread(() -> Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show());
            }
        }).start(); // Start the thread
    }


    private void updateProductAsync(int quantity) {
        // Start a new thread to perform database operations
        new Thread(() -> {
            // Access the database
            String productNameStr = productName.getText().toString();
            Product existingProduct = database.productDao().getProductByName(productNameStr);

            if (existingProduct != null) {
                // Update the quantity of the existing product
                existingProduct.setQuantity(quantity);
                // Update the product in the database
                database.productDao().updateProduct(existingProduct);
                // Show a toast message indicating successful update on the UI thread
                runOnUiThread(() -> {
                    Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                // If the product doesn't exist, show a toast message on the UI thread
                runOnUiThread(() -> Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show());
            }
        }).start(); // Start the thread
    }
}

