package vn.edu.fpt.fa24.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import vn.edu.fpt.fa24.Models.Order.OrderDetailsModel;
import vn.edu.fpt.fa24.R;

public class OrderHistoryDetailsAdapter extends RecyclerView.Adapter<OrderHistoryDetailsAdapter.OrderDetailsViewHolder> {

    private List<OrderDetailsModel> orderDetailsList;

    public OrderHistoryDetailsAdapter(List<OrderDetailsModel> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_details, parent, false);
        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int position) {
        OrderDetailsModel details = orderDetailsList.get(position);
        holder.tvQuantity.setText("Quantity: " + details.getQuantity());
        holder.tvTotalPrice.setText("Total Price: $" + details.getTotalPrice());
        holder.tvProductName.setText("Product: " + details.getProduct().getProductName());
        holder.tvPrice.setText("Price: $" + details.getProduct().getUnitPrice());
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    static class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuantity, tvPrice, tvTotalPrice, tvProductName;

        OrderDetailsViewHolder(View itemView) {
            super(itemView);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
