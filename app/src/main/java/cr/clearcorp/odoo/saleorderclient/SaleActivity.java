package cr.clearcorp.odoo.saleorderclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cr.clearcorp.odoo.saleorderclient.controllers.CustomerController;
import cr.clearcorp.odoo.saleorderclient.models.Customer;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;

import static java.util.Arrays.asList;


public class SaleActivity extends AppCompatActivity {

    //private static final String COMMON_URL = "/xmlrpc/2/common";
    private static final String OBJECT_URL = "/xmlrpc/2/object";

    private String database;
    private String password;
    private String url;
    private Integer uid;
    private AutoCompleteTextView textViewCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.database = intent.getStringExtra("database");
        password = intent.getStringExtra("password");
        url = intent.getStringExtra("url");
        uid = intent.getIntExtra("uid", 0);
        setContentView(R.layout.activity_sale);
        textViewCustomer = (AutoCompleteTextView) findViewById(R.id.sale_customer);
    }

    @Override
    protected void onStart(){
        super.onStart();
        RetrieveCustomersIdsTask task = new RetrieveCustomersIdsTask(url, database, uid, password);
        task.execute();
    }

    private void LoadtextViewCustomer(ArrayList<Customer> customers) {
        ArrayAdapter<Customer> adapter;
        adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_expandable_list_item_1, customers);
        textViewCustomer.setAdapter(adapter);
    }

    private class RetrieveCustomersIdsTask extends AsyncTask<Void, Void, ArrayList<Customer>> {

        private String database;
        private Integer uid;
        private String password;
        private String url;

        public RetrieveCustomersIdsTask(String url, String database, Integer uid, String password) {
            this.database = database;
            this.uid = uid;
            this.password = password;
            this.url = url;
        }

        @Override
        protected ArrayList<Customer> doInBackground(Void... params) {
            return LoadCustomers();
        }

        @Override
        protected void onPostExecute(ArrayList<Customer> result) {
            LoadtextViewCustomer(result);
        }

        private ArrayList<Customer> LoadCustomers(){
            return CustomerController.readAllCustomers(this.url, this.database, this.uid, this.password);
        }
    }
}
