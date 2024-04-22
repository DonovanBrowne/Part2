package uk.ac.le.co2103.part2.shoppinglistapp.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.database.AppDatabase;
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingListActivity;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<ShoppingList> shoppingLists;
    private AppDatabase database;

    public ShoppingListAdapter(AppDatabase database) {
        this.database = database;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);
        holder.bind(shoppingList);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return shoppingLists == null ? 0 : shoppingLists.size();
    }

    public ShoppingList getShoppingListAtPosition(int position) {
        if (position >= 0 && position < shoppingLists.size()) {
            return shoppingLists.get(position);
        } else {
            return null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ShoppingList shoppingList = shoppingLists.get(position);

                        Intent intent = new Intent(v.getContext(), ShoppingListActivity.class);
                        intent.putExtra("SHOPPING_LIST_ID", shoppingList.getListId());
                        v.getContext().startActivity(intent);
                    }
                }
            });

            itemView.setOnCreateContextMenuListener(this); // Set the context menu listener here
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem deleteItem = menu.add(Menu.NONE, 1, 1, "Delete");
            deleteItem.setOnMenuItemClickListener(item -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ShoppingList shoppingList = shoppingLists.get(position);
                    String listName = shoppingList.getName();

                    // Use a background thread to perform database operation asynchronously
                    new Thread(() -> {
                        database.shoppingListDao().deleteWithCascade(shoppingList.getListId());
                        itemView.post(() -> Toast.makeText(itemView.getContext(), "List \"" + listName + "\" successfully deleted", Toast.LENGTH_SHORT).show());
                    }).start();

                    shoppingLists.remove(position);
                    notifyItemRemoved(position);
                }
                return true;
            });
        }

        public void bind(ShoppingList shoppingList) {
            textView.setText(shoppingList.getName()); // Set the text here using the instance of textView
            Picasso.get().load(shoppingList.getImage()).into(imageView);
        }
    }


}
