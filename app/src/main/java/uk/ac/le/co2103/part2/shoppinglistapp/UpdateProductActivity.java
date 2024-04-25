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

public class UpdateProductActivity extends AppCompatActivity {
    private EditText editTextName, editTextQuantity;
    private Spinner spinnerUnit;
    private Button buttonSave;
    private int productId;
    private AppDatabase database;
    private Product productToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        buttonSave = findViewById(R.id.buttonSave);

        database = AppDatabase.getInstance(this);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        if (productId != -1) {
            getProductDetails(productId);
        } else {
            Toast.makeText(this, "Error: Product ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        Button buttonIncrement = findViewById(R.id.buttonIncrement);
        Button buttonDecrement = findViewById(R.id.buttonDecrement);

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
        productToUpdate = database.productDao().getProductById(productId);

        if (productToUpdate != null) {
            // Update EditText and Spinner with retrieved details
            editTextName.setText(productToUpdate.getName());
            editTextQuantity.setText(String.valueOf(productToUpdate.getQuantity()));
            spinnerUnit.setSelection(getIndex(spinnerUnit, productToUpdate.getUnit()));
        } else {
            Toast.makeText(this, "Error: Product not found.", Toast.LENGTH_SHORT).show();
            finish();
        }
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


    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }

    // Method to handle Save button click
    public void onSaveButtonClick(View view) {
        String name = editTextName.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr);
        String unit = spinnerUnit.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a product name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update product details in the database
        productToUpdate.setName(name);
        productToUpdate.setQuantity(quantity);
        productToUpdate.setUnit(unit);
        database.productDao().updateProduct(productToUpdate);

        // Navigate back to ShoppingListActivity
        finish();
    }
}


