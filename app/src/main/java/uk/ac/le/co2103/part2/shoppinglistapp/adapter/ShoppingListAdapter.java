package uk.ac.le.co2103.part2.shoppinglistapp.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import uk.ac.le.co2103.part2.shoppinglistapp.ShoppingListActivity;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<ShoppingList> shoppingLists;

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
        }

        public void bind(ShoppingList shoppingList) {
            TextView.setText(shoppingList.getName());
            Picasso.get().load(shoppingList.getImage()).into(imageView);}


    }
}
