package vn.edu.fpt.fa24.Parsers.Deserializers;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Models.Order.OrderHistoryModel;
import vn.edu.fpt.fa24.Models.Order.OrderDetailsModel;

public class OrderHistoryDeserializer implements JsonDeserializer<OrderHistoryModel> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public OrderHistoryModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        OrderHistoryModel order = new OrderHistoryModel();

        order.setOrderID(jsonObject.has("orderID") && !jsonObject.get("orderID").isJsonNull() ?
                jsonObject.get("orderID").getAsInt() : 0);

        order.setOrderDate(jsonObject.has("orderDate") && !jsonObject.get("orderDate").isJsonNull() ?
                jsonObject.get("orderDate").getAsString() : null);

        order.setRequireDate(jsonObject.has("requireDate") && !jsonObject.get("requireDate").isJsonNull() ?
                jsonObject.get("requireDate").getAsString() : null);

        order.setShipAddress(jsonObject.has("shipAddress") && !jsonObject.get("shipAddress").isJsonNull() ?
                jsonObject.get("shipAddress").getAsString() : "");

        order.setShipPrice(jsonObject.has("shipPrice") && !jsonObject.get("shipPrice").isJsonNull() ?
                jsonObject.get("shipPrice").getAsDouble() : 0.0);

        order.setStatus(jsonObject.has("status") && !jsonObject.get("status").isJsonNull() &&
                jsonObject.get("status").getAsBoolean());

        // Deserialize orderDetails list
        if (jsonObject.has("orderDetails") && !jsonObject.get("orderDetails").isJsonNull()) {
            JsonArray detailsArray = jsonObject.getAsJsonArray("orderDetails");
            List<OrderDetailsModel> detailsList = new ArrayList<>();
            for (JsonElement element : detailsArray) {
                OrderDetailsModel detail = context.deserialize(element, OrderDetailsModel.class);
                detailsList.add(detail);
            }
            order.setOrderDetails(detailsList);
        } else {
            order.setOrderDetails(new ArrayList<>());  // Initialize as an empty list if not present
        }

        return order;
    }
}
