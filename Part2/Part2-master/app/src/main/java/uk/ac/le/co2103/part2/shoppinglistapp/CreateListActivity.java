package uk.ac.le.co2103.part2.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.le.co2103.part2.R;

public class CreateListActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_list);

            EditText editTextName = findViewById(R.id.edit_text_name);
            ImageView imageView = findViewById(R.id.image_view);
            Button createButton = findViewById(R.id.button_create);


            createButton.setOnClickListener(view -> {
                String name = editTextName.getText().toString();
                String shoppingListName = editTextName.getText().toString();
                // function to create shopping list
                Intent resultIntent = new Intent();
                resultIntent.putExtra("shopping_list_name", shoppingListName);
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        }
    }


