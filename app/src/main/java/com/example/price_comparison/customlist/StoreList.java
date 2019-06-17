package com.example.price_comparison.customlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.price_comparison.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa stworzona w celu wyświetlenia listy sklepów.
 */
public class StoreList extends ArrayAdapter<SingleStore> {

    private ArrayList<SingleStore> dataSet; /**< \brief Lista  danych*/
    Context mContext; /**< \brief Kontekst */

    /**
     * Klasa stworzona w celu podtrzymania widoku.
     * /static
     */
    private static class ViewHolder {
        TextView storeName; /**< Nazwa sklepu w postaci tekstu */
        TextView storePrice; /**< Cena w sklepie w postaci tekstu */
        ImageView info; /**< Informacje w postaci obrazka*/
    }

    /**
     * \brief Konstruktor parametryczny, tworzący listę sklepów na podstawie danych, oraz kontekstu.
     * Konstruktor parametryczny, tworzący listę sklepów na podstawie danych, oraz kontekstu.
     * @param data dane
     * @param context kontekst
     */
    public StoreList(ArrayList<SingleStore> data, Context context){
        super(context, R.layout.store_list, data);
        this.dataSet = data;
        this.mContext=context;
    }

    /**
     * Metoda zwracająca widok, na którym wyświetlone zostaną informacje takie jak nazwa, cena, info.
     * @param position pozycja
     * @param convertView konwertowany widok
     * @param parent rodzic
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SingleStore dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.store_list, parent, false);
            viewHolder.storeName = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.storePrice = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.imagelist);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.storeName.setText(dataModel.getName());
        viewHolder.storePrice.setText(dataModel.getPrice().toString());
        return convertView;
    }

}
