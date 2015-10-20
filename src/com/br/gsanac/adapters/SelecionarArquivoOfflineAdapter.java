package com.br.gsanac.adapters;

import java.util.List;
import com.br.gsanac.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Jonathan Marcos
 * @since 09/09/2014
 */
public class SelecionarArquivoOfflineAdapter extends BaseAdapter {
	
	// Atributos adapter
	private LayoutInflater layoutInflater;
	private List<String> listaArquivos;
	private Context context;
	
	public SelecionarArquivoOfflineAdapter(Context context,List<String> listaArquivos) {
		this.context = context;
		this.listaArquivos = listaArquivos;
		layoutInflater = (LayoutInflater)this.context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return listaArquivos.size();
	}

	@Override
	public Object getItem(int position) {
		return listaArquivos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.selecionar_arquivo_offline_adapter, null);
		}
		
		TextView textViewNomeArquivo = (TextView) 
				convertView.findViewById(R.id.textViewNomeArquivo);
		
		textViewNomeArquivo.setText(listaArquivos.get(position));
		textViewNomeArquivo.setGravity(Gravity.CENTER_VERTICAL);
		textViewNomeArquivo.setHeight(50);

	    convertView.setTag(getItem(position));
		
		return convertView;
	}
}