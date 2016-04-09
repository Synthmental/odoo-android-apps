package cr.clearcorp.odoo.saleorderclient;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import cr.clearcorp.odoo.saleorderclient.controllers.WarehouseController;
import cr.clearcorp.odoo.saleorderclient.models.Product;
import cr.clearcorp.odoo.saleorderclient.models.Warehouse;

public class SaleActivity extends AppCompatActivity implements ProductFragment.OnItemClickListener {

    private Spinner spinnerWarehouse;

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

            /*spinnerCustomer = (Spinner) findViewById(R.id.spinnerCustomer);
            //spinnerWarehouse = (Spinner) findViewById(R.id.spinnerWarehouse);
            spinnerPricelist = (Spinner) findViewById(R.id.spinnerPricelist);
            productGrid = (GridView) findViewById(R.id.gridViewProduct);
            listViewLines = (ListView) findViewById(R.id.listViewLines);
            adapterLines = new SaleOrderLineAdapter(this, R.layout.sale_line, new ArrayList<SaleOrderLine>());
            this.listViewLines.setAdapter(adapterLines);
            this.listViewLines.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Snackbar.make(findViewById(R.id.SaleCoordinatorLayout), "Editando linea " + String.valueOf(position),
                            Snackbar.LENGTH_SHORT)
                            .show();
                // Create new fragment and transaction
                SaleOrderLineEditFragment newFragment = new SaleOrderLineEditFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.sale_line_fragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                }
            });*/
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        /*RetrieveWarehouseIdsTask warehouseTask = new RetrieveWarehouseIdsTask(url, database, uid, password);
        warehouseTask.execute();*/
    }

    private void LoadtextViewWarehouse(ArrayList<Warehouse> warehouses) {
        ArrayAdapter<Warehouse> adapter;
        adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_spinner_dropdown_item, warehouses);
        spinnerWarehouse.setAdapter(adapter);
    }

    @Override
    public void OnItemClicked(Product product, Double price) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SaleOrderLineFragment fragment = (SaleOrderLineFragment) fragmentManager.findFragmentByTag("SaleOrderLineFragment");
        fragment.LoadNewProduct(product, price);
        Log.d("asdasd", "adsadasd");
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
}
