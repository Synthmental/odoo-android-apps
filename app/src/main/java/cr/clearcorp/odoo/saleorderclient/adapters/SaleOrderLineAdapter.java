package cr.clearcorp.odoo.saleorderclient.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cr.clearcorp.odoo.saleorderclient.R;
import cr.clearcorp.odoo.saleorderclient.models.Product;
import cr.clearcorp.odoo.saleorderclient.models.SaleOrderLine;

public class SaleOrderLineAdapter extends ArrayAdapter<SaleOrderLine> {

    private int resource;

    public SaleOrderLineAdapter(Context context, int resource, List<SaleOrderLine> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        SaleOrderLine line = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);

        }

        // Lookup view for data population
        EditText textViewSaleQty = (EditText) convertView.findViewById(R.id.textViewSaleQty);
        textViewSaleQty.setText(line.getQuantity().toString());

        TextView textViewSaleProduct = (TextView) convertView.findViewById(R.id.textViewSaleProduct);
        textViewSaleProduct.setText(line.getProduct().toString());

        EditText textViewSalePrice = (EditText) convertView.findViewById(R.id.textViewSalePrice);
        textViewSalePrice.setText(line.getPrice().toString());

        TextView textViewSalePriceTotal = (TextView) convertView.findViewById(R.id.textViewSalePriceTotal);
        textViewSalePriceTotal.setText(String.valueOf(line.getPrice() * line.getQuantity()));

        // Return the completed view to render on screen
        return convertView;

    }

}
