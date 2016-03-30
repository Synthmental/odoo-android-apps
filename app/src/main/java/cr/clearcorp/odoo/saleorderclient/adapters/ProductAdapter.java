package cr.clearcorp.odoo.saleorderclient.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cr.clearcorp.odoo.saleorderclient.R;
import cr.clearcorp.odoo.saleorderclient.SaleActivity;
import cr.clearcorp.odoo.saleorderclient.models.Product;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Product product = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product, parent, false);

        }

        // Lookup view for data population

        ImageButton imageButtonProduct = (ImageButton) convertView.findViewById(R.id.imageButtonProduct);
        if (!product.getImageMedium().isEmpty()) {
            byte[] decodedString = Base64.decode(product.getImageMedium(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageButtonProduct.setImageBitmap(decodedByte);
        }
        else {
            imageButtonProduct.setImageResource(R.drawable.placeholder);
        }

        TextView textViewProduct = (TextView) convertView.findViewById(R.id.textViewProduct);
        textViewProduct.setText(product.toString());

        /*TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);

        // Populate the data into the template view using the data object

        tvName.setText(user.name);

        tvHome.setText(user.hometown);*/

        // Return the completed view to render on screen

        return convertView;

    }

}
