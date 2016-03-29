package cr.clearcorp.odoo.saleorderclient.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import cr.clearcorp.odoo.saleorderclient.models.Pricelist;

import static java.util.Arrays.asList;

public class PricelistController {

    private static final String MODEL = "product.pricelist";

    public static ArrayList<Pricelist> readAllPricelists(String url, String database, Integer uid, String password) {

        ArrayList<HashMap<String, Object>> elements = GenericController.readAll(url, database, uid,
                password, MODEL, asList(Collections.emptyList()),
                new HashMap() {{
                    put("fields", asList("id", "name"));
                }});

        ArrayList<Pricelist> pricelists = new ArrayList<>();
        for (final HashMap<String, Object> object : elements){
            pricelists.add(new Pricelist((Integer)object.get("id"), (String)object.get("name")));
        }

        return pricelists;
    }
}
