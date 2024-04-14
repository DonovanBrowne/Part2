package uk.ac.le.co2103.part2.shoppinglistapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<ShoppingList> shoppingLists;

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
    }

    @Override
    public int getItemCount() {
        return shoppingLists == null ? 0 : shoppingLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView TextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);

            Picasso.get().load("url_to_image").into(imageView);
        }

        public void bind(ShoppingList shoppingList) {
            TextView.setText(shoppingList.getName());
            // Load image using your preferred method and set it to the ImageView
        }
    }
}

