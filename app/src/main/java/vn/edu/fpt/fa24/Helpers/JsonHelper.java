package vn.edu.fpt.fa24.Helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Date;

import vn.edu.fpt.fa24.Models.Cart.CartItemModel;
import vn.edu.fpt.fa24.Models.Order.OrderHistoryModel;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.Parsers.Deserializers.CartItemDeserializer;
import vn.edu.fpt.fa24.Parsers.Deserializers.DateDeserializer;
import vn.edu.fpt.fa24.Parsers.Deserializers.OrderHistoryDeserializer;
import vn.edu.fpt.fa24.Parsers.Deserializers.ProductDeserializer;

public final class JsonHelper<T> {

    private final Gson gson;

    public JsonHelper() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(CartItemModel.class, new CartItemDeserializer())
                .registerTypeAdapter(ProductModel.class, new ProductDeserializer())
                .registerTypeAdapter(OrderHistoryModel.class, new OrderHistoryDeserializer())
                .create();
    }

    // Method to parse JSON string to an object
    public T parse(String responseData, Class<T> type) {
        try {
            return gson.fromJson(responseData, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T parseRaw(String responseData, Class<T> type) {
        try {
            Gson gsonRaw = new Gson();
            return gsonRaw.fromJson(responseData, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to parse JSON string to a list
    public T parseList(String responseData, Type type) {
        try {
            return gson.fromJson(responseData, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // New method to convert an object to JSON string
    public String toJson(T object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
