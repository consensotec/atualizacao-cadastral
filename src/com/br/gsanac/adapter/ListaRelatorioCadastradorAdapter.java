package com.br.gsanac.adapter;

import java.util.ArrayList;

import com.br.gsanac.R;
import com.br.gsanac.entidades.bean.RelatorioOcorrenciaCadastro;
import com.br.gsanac.entidades.bean.RelatorioPorCadastrador;
import com.br.gsanac.util.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListaRelatorioCadastradorAdapter extends BaseAdapter implements OnClickListener{
	
	private LayoutInflater inflater;
	private ArrayList<RelatorioPorCadastrador> relatorio;
	private Context c;
	
	public ListaRelatorioCadastradorAdapter(Context context, ArrayList<RelatorioPorCadastrador> relatorio){
		c = context;
		this.relatorio = relatorio;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		
		return relatorio.size();
	}

	@Override
	public Object getItem(int position) {
		
		return relatorio.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = inflater.inflate(R.layout.lista_relatorio_cadastrador_adapter, null); 
		}
		
		convertView.setOnClickListener(this);
		
		int totalImoveis = relatorio.get(position).getTotalImoveis().intValue();
		int totalImoveisAtualizados = relatorio.get(position).getTotalImoveisAtualizados().intValue();
		int totalImoveisIncluidos = relatorio.get(position).getTotalImoveisIncluidos().intValue();
		int totalImoveisVisitados = relatorio.get(position).getTotalImoveisVisitados();
		String desCadastrador = relatorio.get(position).getLogin();
		double porcentagem = ((float) totalImoveisAtualizados / (float) totalImoveis) * 100;
		
		TextView imoveisAtualizados = (TextView) convertView.findViewById(R.id.totalImvAtualizado);
		imoveisAtualizados.setText("Total de Imóveis Atualizados: " + totalImoveisAtualizados);
		
		TextView imoveisIncluidos = (TextView) convertView.findViewById(R.id.totalImvIncluido);
		imoveisIncluidos.setText("Total de Imóveis Incluidos:      " + totalImoveisIncluidos);
		
		TextView imoveisVisitados = (TextView) convertView.findViewById(R.id.totalImvIVisitado);
		imoveisVisitados.setText("Total de Imóveis Visitados:     " + totalImoveisVisitados);
		
		TextView percentualPorcadastrador = (TextView) convertView.findViewById(R.id.percentualPorCadastrador);
		percentualPorcadastrador.setText(String.format("%.1f %s", porcentagem, "%" ));
		
		TextView descricaocadastrador = (TextView) convertView.findViewById(R.id.descricaoCadastrador);
		
		if(Util.isLong(desCadastrador)){
			descricaocadastrador.setText(Util.formatarCpf(desCadastrador));
		}else{
			descricaocadastrador.setText(desCadastrador.toUpperCase());
		}
		
		convertView.setTag(getItem(position));
		

		return convertView;
	}

	@Override
	public void onClick(View v) {


		LinearLayout detalheCadastrador= (LinearLayout) v.findViewById(R.id.detalhesCadastrador);
		LinearLayout resumoCadstrador = (LinearLayout) v.findViewById(R.id.resumoPorCadastrador);
		

		if(detalheCadastrador.getVisibility() == View.VISIBLE){
			
			detalheCadastrador.setVisibility(View.GONE);  
			TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 0 );   
			slide.setDuration(1000);   
			slide.setFillAfter(true);   
			detalheCadastrador.startAnimation(slide);
			
			resumoCadstrador.setBackgroundResource(R.drawable.fundocinza_bg);
		
			
		}else{
			
			detalheCadastrador.setVisibility(View.VISIBLE);  
			TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 0 );   
			slide.setDuration(1000);   
			slide.setFillAfter(true);   
			detalheCadastrador.startAnimation(slide);
			
			resumoCadstrador.setBackgroundResource(R.drawable.fundoazulclaro_bg);
			
		}
		
		
	}

}
