package cr.clearcorp.odoo.saleorderclient;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SaleConfirmationActivity extends AppCompatActivity {

    public TextView textViewSaleOrderNumber;
    private Button buttonNewSaleOrder;
    private TextView textViewTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_confirmation);


        Intent intent = getIntent();
        String orderName = intent.getStringExtra("orderName");

        this.textViewSaleOrderNumber = (TextView) findViewById(R.id.textViewSaleOrderNumber);
        this.textViewSaleOrderNumber.setText(orderName);

        this.buttonNewSaleOrder = (Button) findViewById(R.id.buttonNewSaleOrder);
        this.buttonNewSaleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    }

