package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import models.Product;
import mycompany.iorder.R;

/**
 * Created by ΧΡΗΣΤΟΣ on 10/1/2015.
 */
public class OrderedItemsAdapter extends ArrayAdapter<Product> {
    Context context;
    ArrayList<Product> product = null;


    public OrderedItemsAdapter(Context context, ArrayList<Product> product) {
        super(context, R.layout.single_ordered_item_adapter, product);
        this.context = context;
        this.product = product;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.single_ordered_item_adapter, parent, false);
        //TextView idText = (TextView) rowView.findViewById(R.id.id);
        TextView titleText = (TextView) rowView.findViewById(R.id.textTitle);
        TextView priceText = (TextView) rowView.findViewById(R.id.textprice);
        TextView quantityText = (TextView) rowView.findViewById(R.id.textQuantity);
        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.eraseButton);
        final Product tempProduct = product.get(position);
        //idText.setText(tempProduct.getProductId());
        titleText.setText(tempProduct.getTitle());
        priceText.setText(tempProduct.getPrice());
        quantityText.setText(tempProduct.getQuantity());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.remove(position);
                OrderedItemsAdapter.this.notifyDataSetChanged();
            }
        });
        return rowView;
    }
}
