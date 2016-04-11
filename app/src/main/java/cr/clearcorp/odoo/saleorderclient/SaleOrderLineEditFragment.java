package cr.clearcorp.odoo.saleorderclient;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SaleOrderLineEditFragment extends Fragment {

    private String database;
    private String password;
    private String url;
    private Integer uid;
    private Integer product_id;
    private EditText editTextSaleQtyEdit;
    private TextView textViewSaleProductEdit;
    private EditText textViewSalePriceEdit;
    private TextView textViewSalePriceTotalEdit;


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
        View view = inflater.inflate(R.layout.fragment_sale_line_edit, container, false);

        this.textViewSaleProductEdit = (TextView) view.findViewById(R.id.textViewSaleProductEdit);
        this.textViewSaleProductEdit.setText(bundle.getString("product"));
        this.product_id = bundle.getInt("product_id");

        Double qty = bundle.getDouble("qty", 0.0);
        Double price = bundle.getDouble("price", 0.0);
        Double total = qty * price;

        this.editTextSaleQtyEdit = (EditText) view.findViewById(R.id.editTextSaleQtyEdit);
        this.editTextSaleQtyEdit.setText(String.format("%.2f", qty));

        this.textViewSalePriceEdit = (EditText) view.findViewById(R.id.textViewSalePriceEdit);
        this.textViewSalePriceEdit.setText(String.format("%.2f", price));

        this.textViewSalePriceTotalEdit = (TextView) view.findViewById(R.id.textViewSalePriceTotalEdit);
        this.textViewSalePriceTotalEdit.setText(String.format("%.2f", total));

        return view;
    }

}
