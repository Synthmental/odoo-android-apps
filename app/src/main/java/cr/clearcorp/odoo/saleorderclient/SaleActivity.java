package cr.clearcorp.odoo.saleorderclient;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import cr.clearcorp.odoo.saleorderclient.controllers.WarehouseController;
import cr.clearcorp.odoo.saleorderclient.models.Product;
import cr.clearcorp.odoo.saleorderclient.models.Warehouse;

public class SaleActivity extends AppCompatActivity implements ProductFragment.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.main_sale_activity) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            SaleOrderLineFragment saleOrderLineFragment = new SaleOrderLineFragment();
            ProductFragment productFragment = new ProductFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            saleOrderLineFragment.setArguments(getIntent().getExtras());
            productFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'main_sale_activity' Layout
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_sale_activity, productFragment);
            transaction.add(R.id.main_sale_activity, saleOrderLineFragment, "SaleOrderLineFragment");
            transaction.commit();
        }
    }

    /*@Override
    protected void onStart(){
        super.onStart();
        RetrieveWarehouseIdsTask warehouseTask = new RetrieveWarehouseIdsTask(url, database, uid, password);
        warehouseTask.execute();
    }

    private void LoadtextViewWarehouse(ArrayList<Warehouse> warehouses) {
        ArrayAdapter<Warehouse> adapter;
        adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_spinner_dropdown_item, warehouses);
        spinnerWarehouse.setAdapter(adapter);
    }*/

    @Override
    public void OnItemClicked(Product product, Double price) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SaleOrderLineFragment fragment = (SaleOrderLineFragment) fragmentManager.findFragmentByTag("SaleOrderLineFragment");
        fragment.LoadNewProduct(product, price);
        Log.d("SaleOrderLineFragment", "Product added");
    }

    /*private class RetrieveWarehouseIdsTask extends AsyncTask<Void, Void, ArrayList<Warehouse>> {

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
    }*/
}
