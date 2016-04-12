package cr.clearcorp.odoo.saleorderclient;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.DatagramPacket;

import cr.clearcorp.odoo.saleorderclient.models.SaleOrderLine;

public class SaleOrderLineEditFragment extends Fragment {

    private OnActionListener listener;
    private String database;
    private String password;
    private String url;
    private Integer uid;
    private Integer position;
    private Integer product_id;
    private EditText editTextSaleQtyEdit;
    private TextView textViewSaleProductEdit;
    private EditText editTextSalePriceEdit;
    private TextView textViewSalePriceTotalEdit;
    private Button buttonSaleSaveEdit;
    private Button buttonSaleCancelEdit;
    private Button buttonSaleDeleteEdit;

    public SaleOrderLineEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        this.database = bundle.getString("database");
        this.password = bundle.getString("password");
        this.url = bundle.getString("url");
        this.uid = bundle.getInt("uid", 0);
        this.position = bundle.getInt("position", 0);
        View view = inflater.inflate(R.layout.fragment_sale_line_edit, container, false);

        this.textViewSaleProductEdit = (TextView) view.findViewById(R.id.textViewSaleProductEdit);
        this.textViewSaleProductEdit.setText(bundle.getString("product"));
        this.product_id = bundle.getInt("product_id");

        Double qty = bundle.getDouble("qty", 0.0);
        Double price = bundle.getDouble("price", 0.0);
        Double total = qty * price;

        this.editTextSaleQtyEdit = (EditText) view.findViewById(R.id.editTextSaleQtyEdit);
        this.editTextSaleQtyEdit.setText(String.format("%.2f", qty));

        this.editTextSalePriceEdit = (EditText) view.findViewById(R.id.editTextSalePriceEdit);
        this.editTextSalePriceEdit.setText(String.format("%.2f", price));

        this.textViewSalePriceTotalEdit = (TextView) view.findViewById(R.id.textViewSalePriceTotalEdit);
        this.textViewSalePriceTotalEdit.setText(String.format("%.2f", total));

        this.buttonSaleCancelEdit = (Button) view.findViewById(R.id.buttonSaleCancelEdit);
        this.buttonSaleCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnActionCancel();
            }
        });

        this.buttonSaleSaveEdit = (Button) view.findViewById(R.id.buttonSaleSaveEdit);
        this.buttonSaleSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double qty = Double.parseDouble(SaleOrderLineEditFragment.this.editTextSaleQtyEdit.getText().toString());
                    Double price = Double.parseDouble(SaleOrderLineEditFragment.this.editTextSalePriceEdit.getText().toString());
                    listener.OnActionSave(qty, price, 0, SaleOrderLineEditFragment.this.position);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.buttonSaleDeleteEdit = (Button) view.findViewById(R.id.buttonSaleDeleteEdit);
        this.buttonSaleDeleteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.OnActionDelete(SaleOrderLineEditFragment.this.position);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionListener) {
            listener = (OnActionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement SaleOrderLineEditFragment.OnActionListener");
        }
    }

    public interface OnActionListener {
        public void OnActionCancel();
        public void OnActionSave(Double qty, Double price, Integer uomId, Integer position);
        public void OnActionDelete(Integer position);
    }

}
