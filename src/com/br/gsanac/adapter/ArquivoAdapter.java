package com.br.gsanac.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.gsanac.R;


/**
 * @author Arthur Carvalho
 */
public class ArquivoAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<String>   files;

    private Context        context;

    public ArquivoAdapter(Context context, List<String> files) {
        this.context = context;
        this.files = files;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(R.layout.arquivo_adapter, null);
        }

        TextView tvFilename = (TextView) view.findViewById(R.id.name);
        tvFilename.setText(files.get(position));
        tvFilename.setGravity(Gravity.CENTER_VERTICAL);
        tvFilename.setHeight(50);

        view.setTag(getItem(position));

        return view;
    }

}
