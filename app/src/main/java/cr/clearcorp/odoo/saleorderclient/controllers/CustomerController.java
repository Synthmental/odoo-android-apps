package cr.clearcorp.odoo.saleorderclient.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import cr.clearcorp.odoo.saleorderclient.models.Customer;

import static java.util.Arrays.asList;

public class CustomerController {

    private static final String MODEL = "res.partner";

    public static ArrayList<Customer> readAllCustomers(String url, String database, Integer uid, String password) {

        ArrayList<HashMap<String, Object>> elements = GenericController.readAll(url, database, uid,
                password, MODEL, asList(asList(asList("customer", "=", true))),
                new HashMap() {{
                    put("fields", asList("id", "name"));
                }});

        ArrayList<Customer> customers = new ArrayList<>();
        for (final HashMap<String, Object> object : elements){
            customers.add(new Customer((Integer)object.get("id"), (String)object.get("name")));
        }

        return customers;
    }
}
