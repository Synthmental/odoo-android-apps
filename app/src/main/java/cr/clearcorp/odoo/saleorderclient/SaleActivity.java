package cr.clearcorp.odoo.saleorderclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;

import cr.clearcorp.odoo.saleorderclient.controllers.CustomerController;
import cr.clearcorp.odoo.saleorderclient.controllers.PricelistController;
import cr.clearcorp.odoo.saleorderclient.controllers.WarehouseController;
import cr.clearcorp.odoo.saleorderclient.models.Customer;
import cr.clearcorp.odoo.saleorderclient.models.Pricelist;
import cr.clearcorp.odoo.saleorderclient.models.Warehouse;

public class SaleActivity extends AppCompatActivity {

    private String database;
    private String password;
    private String url;
    private Integer uid;
    private Spinner spinnerCustomer;
    private Spinner spinnerWarehouse;
    private Spinner spinnerPricelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.database = intent.getStringExtra("database");
        password = intent.getStringExtra("password");
        url = intent.getStringExtra("url");
        uid = intent.getIntExtra("uid", 0);
        setContentView(R.layout.activity_sale);
        spinnerCustomer = (Spinner) findViewById(R.id.spinnerCustomer);
        spinnerWarehouse = (Spinner) findViewById(R.id.spinnerWarehouse);
        spinnerPricelist = (Spinner) findViewById(R.id.spinnerPricelist);
    }

    @Override
    protected void onStart(){
        super.onStart();
        RetrieveCustomersIdsTask customerTask = new RetrieveCustomersIdsTask(url, database, uid, password);
        customerTask.execute();
        RetrieveWarehouseIdsTask warehouseTask = new RetrieveWarehouseIdsTask(url, database, uid, password);
        warehouseTask.execute();
        RetrievePricelistIdsTask pricelistTask = new RetrievePricelistIdsTask(url, database, uid, password);
        pricelistTask.execute();

    }

    private void LoadtextViewCustomer(ArrayList<Customer> customers) {
        ArrayAdapter<Customer> adapter;
        adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_spinner_dropdown_item, customers);
        spinnerCustomer.setAdapter(adapter);
    }

    private void LoadtextViewWarehouse(ArrayList<Warehouse> warehouses) {
        ArrayAdapter<Warehouse> adapter;
        adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_spinner_dropdown_item, warehouses);
        spinnerWarehouse.setAdapter(adapter);
    }

    private void LoadtextViewPricelist(ArrayList<Pricelist> pricelists) {
        ArrayAdapter<Pricelist> adapter;
        adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_spinner_dropdown_item, pricelists);
        spinnerPricelist.setAdapter(adapter);
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

    private class RetrieveWarehouseIdsTask extends AsyncTask<Void, Void, ArrayList<Warehouse>> {

        private String database;
        private Integer uid;
        private String password;
        private String url;

        public RetrieveWarehouseIdsTask(String url, String database, Integer uid, String password) {
            this.database = database;
            this.uid = uid;
            this.password = password;
            this.url = url;
        }

        @Override
        protected ArrayList<Warehouse> doInBackground(Void... params) {
            return LoadWarehouses();
        }

        @Override
        protected void onPostExecute(ArrayList<Warehouse> result) {
            LoadtextViewWarehouse(result);
        }

        private ArrayList<Warehouse> LoadWarehouses(){
            return WarehouseController.readAllWarehouses(this.url, this.database, this.uid, this.password);
        }
    }

    private class RetrievePricelistIdsTask extends AsyncTask<Void, Void, ArrayList<Pricelist>> {

        private String database;
        private Integer uid;
        private String password;
        private String url;

        public RetrievePricelistIdsTask(String url, String database, Integer uid, String password) {
            this.database = database;
            this.uid = uid;
            this.password = password;
            this.url = url;
        }

        @Override
        protected ArrayList<Pricelist> doInBackground(Void... params) {
            return LoadPricelists();
        }

        @Override
        protected void onPostExecute(ArrayList<Pricelist> result) {
            LoadtextViewPricelist(result);
        }

        private ArrayList<Pricelist> LoadPricelists(){
            return PricelistController.readAllPricelists(this.url, this.database, this.uid, this.password);
        }
    }
}
