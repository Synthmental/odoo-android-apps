package cr.clearcorp.odoo.saleorderclient.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import cr.clearcorp.odoo.saleorderclient.models.Customer;
import cr.clearcorp.odoo.saleorderclient.models.SaleOrder;
import cr.clearcorp.odoo.saleorderclient.models.SaleOrderLine;

import static java.util.Arrays.asList;

public class SaleOrderController {

    private static final String MODEL = "sale.order";

    public static String readName(String url, String database, Integer uid, String password, Integer id) {

        ArrayList<HashMap<String, Object>> elements = GenericController.readAll(url, database, uid,
                password, MODEL, asList(asList(asList("id", "=", id))),
                new HashMap() {{
                    put("fields", asList("id", "name"));
                }});

        String name = (String) elements.get(0).get("name");
        return name;
    }

    public static Integer createSaleOrder(String url, String database, Integer uid, String password, final SaleOrder saleOrder, Integer type){

        final ArrayList<Object> lines = new ArrayList<>();
        for (final SaleOrderLine line : saleOrder.getLines()) {
            lines.add(asList(0, false, new HashMap() {{
                put("product_id", line.getProduct().getId());
                put("name", line.getProduct().toString());
                put("product_uom_qty", line.getQuantity());
                put("product_uom", line.getUom().getId());
                put("price_unit", line.getPrice());
            }}));
        }

        Integer saleId = GenericController.callMethodInteger(
                url, database, uid, password, MODEL, "create", asList((Object) new HashMap() {{
                    put("partner_id", saleOrder.getCustomer().getId());
                    put("partner_invoice_id", saleOrder.getCustomer().getId());
                    put("partner_shipping_id", saleOrder.getCustomer().getId());
                    put("warehouse_id", 1);
                    put("pricelist_id", saleOrder.getPricelist().getId());
                    put("picking_policy", "one");
                    put("order_policy", "picking");
                    put("payment_term", 1);
                    put("order_line", lines);
                }}));
        return saleId;
    }
}
