package vn.edu.fpt.fa24.Parsers.Deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import vn.edu.fpt.fa24.Models.Cart.CartItemModel;
import vn.edu.fpt.fa24.Models.ProductModel;

public class CartItemDeserializer implements JsonDeserializer<CartItemModel> {
    @Override
    public CartItemModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        CartItemModel cartItem = new CartItemModel();
        cartItem.setCartId(jsonObject.get("cartId").getAsInt());
        cartItem.setProductId(jsonObject.get("productId").getAsInt());
        cartItem.setQuantity(jsonObject.get("quantity").getAsInt());
        cartItem.setUnitPrice(jsonObject.get("unitPrice").getAsBigDecimal());
        cartItem.setTotalPrice(jsonObject.get("totalPrice").getAsDouble());

        // Deserialize the ProductModel
        JsonElement productJson = jsonObject.get("products");
        ProductModel product = context.deserialize(productJson, ProductModel.class);
        cartItem.setProducts(product);

        return cartItem;
    }
}
