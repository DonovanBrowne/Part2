package uk.ac.le.co2103.part2.shoppinglistapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.database.AppDatabase;

import java.io.IOException;

public class CreateListActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;
    private ImageView imageView;
    private Uri imageUri;
    private AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        database = AppDatabase.getInstance(this);


        EditText editTextName = findViewById(R.id.edit_text_name);
        Button createButton = findViewById(R.id.button_create);
        imageView = findViewById(R.id.image_view);
        Button pickImageButton = findViewById(R.id.button_choose_image);

        pickImageButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        createButton.setOnClickListener(view -> {
            String shoppingListName = editTextName.getText().toString();
            // Check if the user has entered a name
            if (shoppingListName.isEmpty()) {
                // Display a popup error message
                Toast.makeText(CreateListActivity.this, "Please enter a name for the shopping list.", Toast.LENGTH_SHORT).show();
            } else {

            // Prepare the result intent and finish the activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("shopping_list_name", shoppingListName);
                if (imageUri != null) {
                resultIntent.putExtra("shopping_list_image", imageUri.toString());
                }
                setResult(RESULT_OK, resultIntent);

                // Create a new thread to insert the shopping list into the database
                new Thread(() -> {
                    ShoppingList shoppingList = new ShoppingList();
                    shoppingList.setName(shoppingListName);
                    if (imageUri != null) {
                        shoppingList.setImage(imageUri.toString());
                    }
                    database.shoppingListDao().insert(shoppingList);
                }).start();

                finish();}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
