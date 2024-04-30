package uk.ac.le.co2103.part2.shoppinglistapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.database.AppDatabase;

public class AddProductActivity extends AppCompatActivity {
    private EditText editTextName, editTextQuantity;
    private Spinner spinnerUnit;
    private Button buttonAdd;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        buttonAdd = findViewById(R.id.buttonAdd);

        database = AppDatabase.getInstance(this);

        // Set onClickListener for buttonAdd
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClick(view); // Call the onAddButtonClick method
            }
        });
    }

    public void onAddButtonClick(View view) {
        String name = editTextName.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr);
        String unit = spinnerUnit.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a product name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if product with the same name already exists asynchronously
        new Thread(() -> isProductExists(name, quantity, unit)).start();
    }

    private void isProductExists(String name, int quantity, String unit) {
        Product existingProduct = database.productDao().getProductByName(name);

        if (existingProduct != null) {
            runOnUiThread(() -> Toast.makeText(AddProductActivity.this, "Product already exists", Toast.LENGTH_SHORT).show());
        } else {
            Product product = new Product(name, quantity, unit);
            insertProduct(product);
        }
    }

    private void insertProduct(Product product) {
        new Thread(() -> {
            database.productDao().insertProduct(product);

            // Navigate back to ShoppingListActivity
            runOnUiThread(() -> {
                Toast.makeText(AddProductActivity.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

}




