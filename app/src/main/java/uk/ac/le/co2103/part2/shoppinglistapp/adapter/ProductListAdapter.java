package uk.ac.le.co2103.part2.shoppinglistapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.le.co2103.part2.R;
import uk.ac.le.co2103.part2.shoppinglistapp.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<Product> products;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }


    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView quantityTextView;
        private TextView unitTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            quantityTextView = itemView.findViewById(R.id.quantity_text_view);
            unitTextView = itemView.findViewById(R.id.unit_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        Product product = products.get(position);
                        Toast.makeText(v.getContext(), "Product: " + product.getName(), Toast.LENGTH_SHORT).show();
                        listener.onItemClick(products.get(position));
                    }
                }
            });
        }

        public void bind(Product product) {
            nameTextView.setText(product.getName());
            quantityTextView.setText(String.valueOf(product.getQuantity()));
            unitTextView.setText(product.getUnit());
        }
    }
}
