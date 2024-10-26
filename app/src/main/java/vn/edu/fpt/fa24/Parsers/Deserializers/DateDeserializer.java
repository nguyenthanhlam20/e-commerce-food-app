package vn.edu.fpt.fa24.Parsers.Deserializers;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date>, JsonSerializer<Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault());

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateStr = json.getAsString();
        // Add a time zone indicator if it's missing
        if (!dateStr.endsWith("Z") && !dateStr.contains("+") && !dateStr.contains("-")) {
            dateStr += "Z"; // default to UTC
        }
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new JsonParseException("Failed to parse Date: " + dateStr, e);
        }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(dateFormat.format(src));
    }
}

