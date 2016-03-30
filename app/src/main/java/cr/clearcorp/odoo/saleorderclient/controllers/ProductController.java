package cr.clearcorp.odoo.saleorderclient.controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import cr.clearcorp.odoo.saleorderclient.models.Product;

import static java.util.Arrays.asList;

public class ProductController {

    private static final String MODEL = "product.product";

    public static ArrayList<Product> readAllProducts(String url, String database, Integer uid, String password) {

        ArrayList<HashMap<String, Object>> elements = GenericController.readAll(url, database, uid,
                password, MODEL, asList(Collections.emptyList()),
                new HashMap() {{
                    put("fields", asList("id", "name", "image_medium"));
                }});

        ArrayList<Product> products = new ArrayList<>();
        for (final HashMap<String, Object> object : elements){
            Integer id = (Integer) object.get("id");
            String name = (String) object.get("name");
            String imageMedium = "";
            try {
                imageMedium = (String) object.get("image_medium");
            }
            catch (Exception e) {
                Log.e("Image","Product has no image");
            }

            products.add(new Product(id, name, imageMedium));
        }

        return products;
    }
}
