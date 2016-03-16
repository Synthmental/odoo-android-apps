package cr.clearcorp.odoo.saleorderclient.utils;

/**
 * Created by gsojo on 09/03/16.
 */

import android.content.Context;

import odoo.Odoo;
import odoo.helper.OUser;

public class Service {

    private Odoo OdooService;

    public Service(Context context, String url) {
        this.OdooService = new Odoo(context, url);

    }

    public OUser connect(String username, String password) {
        return this.OdooService.authenticate(username, password, "test2_8");
    }

}
