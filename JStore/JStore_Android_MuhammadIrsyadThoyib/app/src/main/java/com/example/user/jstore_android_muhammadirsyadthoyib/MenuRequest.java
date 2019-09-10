package com.example.user.jstore_android_muhammadirsyadthoyib;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    private static final String Regis_URL = "http://localhost:8080/logincust";
    private Map<String, String> params;
    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();


    public MenuRequest(Response.Listener<String> listener) {
        super(Request.Method.GET, Regis_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject item = jsonResponse.getJSONObject(i);
                        int idItem = item.getInt("int");
                        String nameItem = item.getString("name");
                        int price = item.getInt("price");
                        String category = item.getString("category");
                        String status = item.getString("status");


                        JSONObject supplier = item.getJSONObject("supplier");

                        int id = supplier.getInt("id");
                        String name = supplier.getString("name");
                        String email = supplier.getString("email");
                        String phoneNumber = supplier.getString("phoneNumber");

                        JSONObject location = supplier.getJSONObject("location");

                        String province = supplier.getString("province");
                        String itemName = supplier.getString("name");
                        String city = supplier.getString("city");

                        Location loc = new Location(province, itemName, city);
                        Supplier sup = new Supplier(id, name, email, phoneNumber, loc);
                        Item ite = new Item(idItem, nameItem, price, category, status, sup);

                        listSupplier.add(sup);
                        listItem.add(ite);

                        childMapping.put(listSupplier.get(i), listItem);
                    }
                } catch (JSONException j) {

                }
            }
        };
    }
}
