package vn.edu.fpt.fa24.Services;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.CategoryModel;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class CategoryService {
    public void getCategories(ResponseCallBack<ArrayList<CategoryModel>> categoriesCallback) {
        ClientService clientService = new ClientService("Category");
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Type type = new TypeToken<ArrayList<CategoryModel>>() {}.getType();

                JsonHelper<ArrayList<CategoryModel>> jsonHelper = new JsonHelper();
                ArrayList<CategoryModel> categories = jsonHelper.parseList(response, type);
                categoriesCallback.onSuccess(categories);
            }

            @Override
            public void onFailure(String error) {
                categoriesCallback.onFailure(error);
            }
        });
    }
}
