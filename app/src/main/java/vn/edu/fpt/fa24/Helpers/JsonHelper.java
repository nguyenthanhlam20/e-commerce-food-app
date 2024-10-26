package vn.edu.fpt.fa24.Helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.lang.reflect.Type;

import vn.edu.fpt.fa24.Parsers.Deserializers.DateDeserializer;

public final class JsonHelper<T> {
    public T parse(String responseData, Class<T> type) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();
            return gson.fromJson(responseData, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T parseList(String responseData, Type type) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();
            return gson.fromJson(responseData, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
