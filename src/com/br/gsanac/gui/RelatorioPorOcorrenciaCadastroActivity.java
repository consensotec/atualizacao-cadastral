package com.br.gsanac.gui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.br.gsanac.R;
import com.br.gsanac.adapter.ListaRelatoriosOcorrenciaCadastroAdapter;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunas;
import com.br.gsanac.entidades.bean.RelatorioOcorrenciaCadastro;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;


public class RelatorioPorOcorrenciaCadastroActivity extends BaseActivity{
	
	private ListaRelatoriosOcorrenciaCadastroAdapter adapter;
	private ListView lv;	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		if(execute){
		setContentView(R.layout.relatorio_por_ocorrencia_cadastro);
		
		lv = (ListView) findViewById(R.id.listRelatorioPorOcorrencia);
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				
				}

			});

		}
	}
	
	public void onResume(){
		super.onResume();
		
		try{
			
			ArrayList<RelatorioOcorrenciaCadastro> listaRelatorioPorOcorrencia = new ArrayList<RelatorioOcorrenciaCadastro>();
			ArrayList<CadastroOcorrencia> listaOcorrencia = new ArrayList<CadastroOcorrencia>();
			
			listaOcorrencia = (ArrayList<CadastroOcorrencia>) Fachada.getInstance().pesquisarLista(CadastroOcorrencia.class, null, null, CadastroOcorrenciaColunas.ID);
			Integer totalImoveis = Fachada.getInstance().obterQuantidadeImoveis();
		
			for(int i = 0; i < listaOcorrencia.size(); i++){
			
				if(listaOcorrencia.get(i).getId() != ConstantesSistema.ITEM_INVALIDO){
					RelatorioOcorrenciaCadastro relatorioOcorrenciaCadastro = null;
	
					Integer totalImoveisAtualizados = Fachada.getInstance().obterQuantidadeImoveisAtualizadosPorOcorrencia(listaOcorrencia.get(i).getId());
					Integer totalImoveisIncluidos = Fachada.getInstance().obterQuantidadeImoveisIncluidosComPorOcorrencia(listaOcorrencia.get(i).getId());
					Integer totalImoveisVisitados = totalImoveisAtualizados + totalImoveisIncluidos;
					String 	descricaoOcorrencia = Fachada.getInstance().buscarDescricaoOcorrencias(listaOcorrencia.get(i).getId());
					
					relatorioOcorrenciaCadastro = new RelatorioOcorrenciaCadastro( totalImoveis, totalImoveisAtualizados, totalImoveisIncluidos, totalImoveisVisitados, descricaoOcorrencia);						
					listaRelatorioPorOcorrencia.add(relatorioOcorrenciaCadastro);
				}
		}		
		
		adapter = new ListaRelatoriosOcorrenciaCadastroAdapter(RelatorioPorOcorrenciaCadastroActivity.this, listaRelatorioPorOcorrencia);
		lv.setAdapter(adapter);
		
		}catch(FachadaException ex){ 
			ex.printStackTrace();
		}
	}
	
	@Override
    public void onBackPressed() { 
		finish();
	}
}
