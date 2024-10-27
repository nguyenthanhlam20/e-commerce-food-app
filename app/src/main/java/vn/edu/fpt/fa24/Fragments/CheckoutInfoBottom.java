package vn.edu.fpt.fa24.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Callbacks.DatePickerCallback;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Helpers.DateHelper;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Listeners.CartListener;
import vn.edu.fpt.fa24.Models.Cart.CartItemModel;
import vn.edu.fpt.fa24.Models.Cart.CartModel;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.Requests.CartRequest;
import vn.edu.fpt.fa24.Requests.CheckoutRequest;
import vn.edu.fpt.fa24.Services.CheckoutService;

public class CheckoutInfoBottom extends BottomSheetDialogFragment {
    private final Context mContext;
    private final CartModel cart;
    private Button payBtn;
    private TextView totalPrice;
    private EditText requireDate, shipAddress;
    private CheckoutService service;
    private SessionHelper session;
    private DateHelper dateHelper;

    public CheckoutInfoBottom(Context mContext, CartModel cart) {
        this.mContext = mContext;
        this.cart = cart;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkout_info_bottom, container);

        payBtn = view.findViewById(R.id.pay);
        totalPrice = view.findViewById(R.id.total);
        requireDate = view.findViewById(R.id.requireDate);
        shipAddress = view.findViewById(R.id.shipAddress);

        service = new CheckoutService();
        session = new SessionHelper(requireActivity());
        dateHelper = new DateHelper(requireActivity());

        setTotal();
        setListeners();

        return view;
    }

    private void setTotal() {
        String total = "Total: $" + getTotalPrice();
        totalPrice.setText(total);
    }

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemModel item : cart.getCartItems()) {
            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        return total;
    }

    private ArrayList<CartRequest> MapToListCartRequest(List<CartItemModel> items, String userId) {
        ArrayList<CartRequest> results = new ArrayList<>();

        for (CartItemModel item : items) {
            CartRequest result = new CartRequest();
            result.setProductId(item.getProductIdStr());
            result.setUserId(userId);
            result.setQuantity(item.getQuantityStr());
            result.setUnitPrice(item.getUnitPrice().toString());

            results.add(result);
        }
        return results;
    }

    private void setListeners() {
        requireDate.setOnClickListener(v -> dateHelper.showDatePickerDialog(new DatePickerCallback() {
            @Override
            public void setDate(String date) {
                requireDate.setText(date);
            }
        }));

        payBtn.setOnClickListener(v -> {
            String date = dateHelper.formatDate(requireDate.getText().toString());
            String address = shipAddress.getText().toString();

            if (date.isEmpty() || address.isEmpty()) {
                Toast.makeText(requireActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!dateHelper.isValidFutureDate(date)) {
                Toast.makeText(requireActivity(), "Ship date must be in the future", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = session.getUserId();
            ArrayList<CartRequest> cartRequests = MapToListCartRequest(cart.getCartItems(), userId);

            CheckoutRequest request = new CheckoutRequest();
            request.setCarts(cartRequests);
            request.setUserId(userId);
            request.setShipPrice(getTotalPrice().toString());
            request.setRequireDate(date);
            request.setShipAddress(address);

            service.checkout(request, new ResponseCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(mContext, "Payment successfully", Toast.LENGTH_SHORT).show();
                        notifyCartChanged(mContext);
                    });
                }

                @Override
                public void onFailure(String error) {
                    requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show());
                }
            });
        });
    }

    public void notifyCartChanged(Context context) {
        Intent intent = new Intent(CartListener.ACTION_UPDATE_CART);
        context.sendBroadcast(intent);
        dismiss();
    }
}