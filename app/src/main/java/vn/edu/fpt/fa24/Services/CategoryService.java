package vn.edu.fpt.fa24.Services;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.CategoriesModel;
import vn.edu.fpt.fa24.Services.Callbacks.ResponseCallBack;

public class CategoryService {
    public void getCategories(ResponseCallBack<ArrayList<CategoriesModel>> categoriesCallback) {
        ClientService clientService = new ClientService("Category");
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                // Create a Type for List<AccountModel>
                Type type = new TypeToken<ArrayList<CategoriesModel>>() {}.getType();

                JsonHelper<ArrayList<CategoriesModel>> jsonHelper = new JsonHelper();
                ArrayList<CategoriesModel> categories = jsonHelper.parseList(response, type);
                categoriesCallback.onSuccess(categories);
            }

            @Override
            public void onFailure(String error) {
                categoriesCallback.onFailure(error);
            }
        });
    }
}
