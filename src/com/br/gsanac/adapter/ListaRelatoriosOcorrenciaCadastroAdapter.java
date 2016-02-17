package com.br.gsanac.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.br.gsanac.R;
import com.br.gsanac.entidades.bean.RelatorioOcorrenciaCadastro;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaRelatoriosOcorrenciaCadastroAdapter extends BaseAdapter implements OnClickListener{
	
	private LayoutInflater inflater;
	private ArrayList<RelatorioOcorrenciaCadastro> relatorios;
	private Context c;
	
	public ListaRelatoriosOcorrenciaCadastroAdapter(Context context, ArrayList<RelatorioOcorrenciaCadastro> relatorios){
		c = context;
		this.relatorios = relatorios;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		
		return relatorios.size();
	}

	@Override
	public Object getItem(int position) {
		
		return relatorios.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = inflater.inflate(R.layout.lista_relatorio_ocorrencia_adapter, null);
		}
		
		convertView.setOnClickListener(this);
		
		int totalImoveis = relatorios.get(position).getTotalImoveis();
		int imoveisAtualizados = relatorios.get(position).getTotalImoveisAtualizados().intValue();
		int imoveisIncluidos = relatorios.get(position).getTotalImoveisIncluidos().intValue();
		int imoveisVisitados = imoveisAtualizados + imoveisIncluidos;
		String desOcorrencia = relatorios.get(position).getDescricao(); 
		double porcentagem = ( (float) imoveisAtualizados / (float) totalImoveis) * 100;
		
		TextView totalImoveisAtualizados = (TextView) convertView.findViewById(R.id.totalImvAtualizados);
		totalImoveisAtualizados.setText("Imóveis Atualizados:  " + imoveisAtualizados);
		
		TextView totalImoveisIncluidos = (TextView) convertView.findViewById(R.id.totalImvIncluidos);
		totalImoveisIncluidos.setText("Imóveis Incluidos:       " + imoveisIncluidos);
		
		TextView totalimoveisVisitados = (TextView) convertView.findViewById(R.id.totalImvIVisitados);
		totalimoveisVisitados.setText("Imóveis Visitados:      " + imoveisVisitados);
		
		TextView percentualPorOcorrencia = (TextView) convertView.findViewById(R.id.percentualPorOcorrencia);
		percentualPorOcorrencia.setText(String.format("%.1f %s", porcentagem, "%" ));
		
		TextView descricaoOcorrencia = (TextView) convertView.findViewById(R.id.descricaoOcorrencia);
		descricaoOcorrencia.setText(desOcorrencia);
		
		convertView.setTag(getItem(position));
		
		
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		
		LinearLayout detalheOcorrencia = (LinearLayout) v.findViewById(R.id.detalhesOcorrencia);
		LinearLayout resumoOcorrencia = (LinearLayout) v.findViewById(R.id.resumoPorOcorrencia);
		
		
		
		if(detalheOcorrencia.getVisibility() == v.VISIBLE){
			
			detalheOcorrencia.setVisibility(View.GONE);  
			TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 0 );   
			slide.setDuration(1000);   
			slide.setFillAfter(true);   
			detalheOcorrencia.startAnimation(slide);
			
			resumoOcorrencia.setBackgroundResource(R.drawable.fundocinza_bg);
		
			
		}else{
			
			detalheOcorrencia.setVisibility(View.VISIBLE);  
			TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 0 );   
			slide.setDuration(1000);   
			slide.setFillAfter(true);   
			detalheOcorrencia.startAnimation(slide);
			
			resumoOcorrencia.setBackgroundResource(R.drawable.fundoazulclaro_bg);
			
		}
		
	}

}
