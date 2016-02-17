package com.br.gsanac.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.br.gsanac.R;
import com.br.gsanac.adapter.ListaRelatorioCadastradorAdapter;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunas;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunas;
import com.br.gsanac.entidades.bean.RelatorioPorCadastrador;
import com.br.gsanac.entidades.bean.RelatorioOcorrenciaCadastro;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;

public class RelatorioPorCadastradorActivity extends BaseActivity{
	
	private ListaRelatorioCadastradorAdapter adapter;
	private ListView lv;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		if(execute){
		setContentView(R.layout.relatorio_por_cadastrador);
		
		lv = (ListView) findViewById(R.id.listRelatorioPorCadastrador);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				
				}

			});

		}
			
	}	
	
	public void onResume(){
		super.onResume();
		
		try {
			ArrayList<RelatorioPorCadastrador> listaRelatorioPorCadastrador = new ArrayList<RelatorioPorCadastrador>();

			List<String> listaLogin = null;		
			Integer totalImoveis = 0;
	
			listaLogin = Fachada.getInstance().pesquisarListaLogin();
			
			totalImoveis = Fachada.getInstance().obterQuantidadeImoveis();
	
			if ( listaLogin != null && listaLogin.size() != 0) {
		
				for(int i = 0; i <  listaLogin.size(); i++){
					
					RelatorioPorCadastrador relatorioPorCadastrador = null;
					
					Integer totalImoveisAtualizados = Fachada.getInstance().obterTotalImoveisAtualizados(listaLogin.get(i));
					Integer totalImoveisIncluidos   = Fachada.getInstance().obterTotalImoveisIncluidos(listaLogin.get(i));				 
					Integer totalImoveisVisitados = totalImoveisAtualizados + totalImoveisIncluidos;
					
					relatorioPorCadastrador = new RelatorioPorCadastrador(totalImoveis, totalImoveisAtualizados, totalImoveisIncluidos, totalImoveisVisitados, listaLogin.get(i));		 
					listaRelatorioPorCadastrador.add(relatorioPorCadastrador);
		
				}
			} 
			
			adapter = new ListaRelatorioCadastradorAdapter(RelatorioPorCadastradorActivity.this, listaRelatorioPorCadastrador);
			lv.setAdapter(adapter);
			
		} catch (FachadaException e1) {
			e1.printStackTrace();
		}
		
	}
		
	@Override
    public void onBackPressed() { 
		finish();
	}
}
