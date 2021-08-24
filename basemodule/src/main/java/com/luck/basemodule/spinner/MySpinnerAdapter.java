package com.luck.basemodule.spinner;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/21 13:30
 * @描述:
 */
public class MySpinnerAdapter extends ArrayAdapter {
    private final List<String> items;
    private final ArrayList<String> arraylist;

    public MySpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> items) {
        super(context, resource, items);
        this.items = items;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(items);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    public void getContainsFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (String item : arraylist) {
                if (item.toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}
