package cr.clearcorp.odoo.saleorderclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import cr.clearcorp.odoo.saleorderclient.adapters.ProductAdapter;
import cr.clearcorp.odoo.saleorderclient.adapters.SaleOrderLineAdapter;
import cr.clearcorp.odoo.saleorderclient.controllers.CustomerController;
import cr.clearcorp.odoo.saleorderclient.controllers.PricelistController;
import cr.clearcorp.odoo.saleorderclient.controllers.ProductController;
import cr.clearcorp.odoo.saleorderclient.controllers.WarehouseController;
import cr.clearcorp.odoo.saleorderclient.models.Customer;
import cr.clearcorp.odoo.saleorderclient.models.Pricelist;
import cr.clearcorp.odoo.saleorderclient.models.Product;
import cr.clearcorp.odoo.saleorderclient.models.SaleOrder;
import cr.clearcorp.odoo.saleorderclient.models.SaleOrderLine;
import cr.clearcorp.odoo.saleorderclient.models.Warehouse;

public class SaleActivity extends AppCompatActivity {

    private String database;
    private String password;
    private String url;
    private Integer uid;
    private Spinner spinnerCustomer;
    private Spinner spinnerWarehouse;
    private Spinner spinnerPricelist;
    private GridView productGrid;
    private ListView listViewLines;
    private SaleOrderLineAdapter adapterLines;

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
        //spinnerWarehouse = (Spinner) findViewById(R.id.spinnerWarehouse);
        spinnerPricelist = (Spinner) findViewById(R.id.spinnerPricelist);
        productGrid = (GridView) findViewById(R.id.gridViewProduct);
        listViewLines = (ListView) findViewById(R.id.listViewLines);
    }

    @Override
    protected void onStart(){
        super.onStart();
        RetrieveCustomersIdsTask customerTask = new RetrieveCustomersIdsTask(url, database, uid, password);
        customerTask.execute();
        /*RetrieveWarehouseIdsTask warehouseTask = new RetrieveWarehouseIdsTask(url, database, uid, password);
        warehouseTask.execute();*/
        RetrievePricelistIdsTask pricelistTask = new RetrievePricelistIdsTask(url, database, uid, password);
        pricelistTask.execute();
        RetrieveProductIdsTask productTask = new RetrieveProductIdsTask(url, database, uid, password);
        productTask.execute();
        adapterLines = new SaleOrderLineAdapter(this, R.layout.sale_line, new ArrayList<SaleOrderLine>());
        this.listViewLines.setAdapter(adapterLines);
    }

    private void LoadtextViewCustomer(ArrayList<Customer> customers) {
        ArrayAdapter<Customer> adapter;
        customers.add(0, new Customer(0, getResources().getString(R.string.prompt_customer_no_selection)));
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
        pricelists.add(0, new Pricelist(0, getResources().getString(R.string.prompt_pricelist_no_selection)));
        adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_spinner_dropdown_item, pricelists);
        spinnerPricelist.setAdapter(adapter);
    }

    private void addProduct(Product product, Double price){
        boolean flag = false;
        Integer count = 0;
        while (count < this.adapterLines.getCount()) {
            if (this.adapterLines.getItem(count).getProduct().getId() == product.getId()){
                this.adapterLines.getItem(count).setQuantity(this.adapterLines.getItem(count).getQuantity() + 1);
                flag = true;
                break;
            }
            count += 1;
        }

        if (!flag){
            this.adapterLines.add(new SaleOrderLine(1.0, product, product.getUom(), price));
        }
    }

    private void LoadNewProduct(Product product, Double price) {
        addProduct(product, price);
        this.adapterLines.notifyDataSetChanged();
        Log.d("New Product add", product.toString());
    }

    private void LoadViewProducts(ArrayList<Product> products) {
        ProductAdapter adapter;
        adapter = new ProductAdapter(SaleActivity.this, R.layout.product, products);
        productGrid.setAdapter(adapter);
        productGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Customer customer = (Customer) SaleActivity.this.spinnerCustomer.getSelectedItem();
                if (customer.getId() == 0) {
                Snackbar.make(findViewById(R.id.SaleCoordinatorLayout), R.string.error_no_customer,
                        Snackbar.LENGTH_SHORT)
                        .show();
                }
                else {
                    Pricelist pricelist = (Pricelist) SaleActivity.this.spinnerPricelist.getSelectedItem();
                    if (pricelist.getId() == 0) {
                        Snackbar.make(findViewById(R.id.SaleCoordinatorLayout), R.string.error_no_pricelist,
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    else{
                        Product product = (Product) parent.getAdapter().getItem(position);
                        ComputePriceTask priceTask = new ComputePriceTask(SaleActivity.this.url,
                                SaleActivity.this.database, SaleActivity.this.uid,
                                SaleActivity.this.password, product, pricelist.getId(), 1.0);
                        priceTask.execute();
                    }
                }
            }
        });

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

    private class ComputePriceTask extends AsyncTask<Void, Void, Double> {

        private String database;
        private Integer uid;
        private String password;
        private String url;
        Product product;
        Integer pricelistId;
        Double quantity;

        public ComputePriceTask(String url, String database, Integer uid, String password,
                                Product product, Integer pricelistId, Double quantity) {
            this.database = database;
            this.uid = uid;
            this.password = password;
            this.url = url;
            this.product = product;
            this.pricelistId = pricelistId;
            this.quantity = quantity;
        }

        @Override
        protected Double doInBackground(Void... params) {
            return LoadPrice();
        }

        @Override
        protected void onPostExecute(Double result) {
            LoadNewProduct(this.product, result);
        }

        private Double LoadPrice(){
            return PricelistController.getPrice(this.url, this.database, this.uid, this.password,
                    this.product.getId(), this.pricelistId, this.quantity);
        }
    }

    private class RetrieveProductIdsTask extends AsyncTask<Void, Void, ArrayList<Product>> {

        private String database;
        private Integer uid;
        private String password;
        private String url;

        public RetrieveProductIdsTask(String url, String database, Integer uid, String password) {
            this.database = database;
            this.uid = uid;
            this.password = password;
            this.url = url;
        }

        @Override
        protected ArrayList<Product> doInBackground(Void... params) {
            return LoadProducts();
        }

        @Override
        protected void onPostExecute(ArrayList<Product> result) {
            LoadViewProducts(result);
        }

        private ArrayList<Product> LoadProducts(){
            return ProductController.readAllProducts(this.url, this.database, this.uid, this.password);
        }
    }
}
