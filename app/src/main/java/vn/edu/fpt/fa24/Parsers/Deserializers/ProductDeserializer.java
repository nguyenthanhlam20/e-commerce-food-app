package vn.edu.fpt.fa24.Parsers.Deserializers;

import com.google.gson.*;
import java.lang.reflect.Type;

import vn.edu.fpt.fa24.Models.ProductModel;

public class ProductDeserializer implements JsonDeserializer<ProductModel> {
    @Override
    public ProductModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        ProductModel product = new ProductModel();
        product.setProductId(jsonObject.get("productId").getAsInt());
        product.setProductName(jsonObject.get("productName").getAsString());
        product.setProductImage(jsonObject.get("productImage").getAsString());
        product.setDescription(jsonObject.get("description").getAsString());
        product.setBrandName(jsonObject.get("brandName").getAsString());
        product.setUnitPrice(jsonObject.get("unitPrice").getAsDouble());
        product.setNumberOfStock(jsonObject.get("numberOfStock").getAsInt());

        return product;
    }
}
