package com.br.gsanac.gui;
import java.util.List;

import com.br.gsanac.R;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunas;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

import android.widget.LinearLayout.LayoutParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RelatorioActivity  extends BaseActivity{
	
	private TextView totalImoveis;
	private TextView totalImoveisAtualizados;
	private TextView totalImoveisIncluidos;
	private TextView totalImoveisAvisitar;
	private TextView totalImovelVisitados;
	private TextView percentualImoveisAtualizados;
	private LinearLayout percentualConcluido;
	private LinearLayout percentualNaoConcluido;
	private Button btRelatorioPorOcorrencia;
	private Button btRelatorioPorCadastrador;
	private Button btVoltar;
	private LinearLayout layoutBtCadastrador;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relatorio_activity);
		
		// Barra de porcentagem
		percentualConcluido = (LinearLayout) findViewById(R.id.concluido);
		android.widget.LinearLayout.LayoutParams parametroPercentualConcluido = (LayoutParams) percentualConcluido.getLayoutParams();
		
		percentualNaoConcluido = (LinearLayout) findViewById(R.id.nao_concluido);
		android.widget.LinearLayout.LayoutParams parametroPercentualNaoConcluido = (LayoutParams) percentualNaoConcluido.getLayoutParams();
		
		//Total de Imoveis
		int qtdTotalImovel = 0;
		try {
			qtdTotalImovel = Fachada.getInstance().obterQuantidadeImoveis();
		} catch (FachadaException e1) {
			
			e1.printStackTrace();
		}
		
		// Pega os imoveis que foram atualizados e incluidos
		String selection = ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO + "=?";
		String[] selectionArgs = new String[]{
				String.valueOf(ConstantesSistema.SIM)
		};
		
		List<ImovelAtlzCadastral> listImovelAtualizado = null;
		
		try{
			listImovelAtualizado = (List<ImovelAtlzCadastral>) Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class, selection, selectionArgs, null);
		}catch(FachadaException ex){
			ex.printStackTrace();
		}
		
		int quantidadeImovelAtualizado = 0;
		int quantidadeImovelIncluido = 0;
		int imovelAvisitar = 0;
		
		for(ImovelAtlzCadastral imovel : listImovelAtualizado){
			
			if(imovel.getImovelId() != 0){
				quantidadeImovelAtualizado++;
				
			}else{
				quantidadeImovelIncluido++;
			}
		}
		
		// Total de Imoveis Visitados
		int qtdImovelVisitado = quantidadeImovelAtualizado + quantidadeImovelIncluido;
		
		// Total de Imoveis a Visitar
		imovelAvisitar = qtdTotalImovel - quantidadeImovelAtualizado;
		
		// Bara de Porcentagem
		double percentual = ((float) quantidadeImovelAtualizado / (float) qtdTotalImovel) *100;
			
		totalImoveis = (TextView) findViewById(R.id.totalImoveis);
		totalImoveis.setText(String.valueOf(qtdTotalImovel));
		totalImoveisAtualizados = (TextView) findViewById(R.id.totalImoveisAtualizados);
		totalImoveisAtualizados.setText(String.valueOf(quantidadeImovelAtualizado));
		totalImoveisAvisitar = (TextView) findViewById(R.id.totalImoveisAvisitar);
		totalImoveisAvisitar.setText(String.valueOf(imovelAvisitar));
		totalImoveisIncluidos = (TextView) findViewById(R.id.totalImoveisIncluidos);
		totalImoveisIncluidos.setText(String.valueOf(quantidadeImovelIncluido));
		totalImovelVisitados = (TextView) findViewById(R.id.totalImoveisVisitados);
		totalImovelVisitados.setText(String.valueOf(qtdImovelVisitado));
		
		percentualImoveisAtualizados = (TextView) findViewById(R.id.percentualImoveisAtualizados);
		
		if(qtdImovelVisitado == 0){
			percentualImoveisAtualizados.setText("%0");
			parametroPercentualConcluido.weight = 1f;
			parametroPercentualNaoConcluido.weight = 0f;
			
		}else{
			percentualImoveisAtualizados.setText(String.format("%.1f %s", percentual, "%"));
			double totalConcluido = percentual / 100;
			parametroPercentualConcluido.weight = (float) (1-totalConcluido);
			parametroPercentualNaoConcluido.weight = (float) (totalConcluido);
		}
		
		btRelatorioPorOcorrencia = (Button) findViewById(R.id.btRelatorioPorOcorrencia);
		btRelatorioPorOcorrencia.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent i =  new Intent(RelatorioActivity.this, RelatorioPorOcorrenciaCadastroActivity.class);
				startActivity(i);
			}
		});
		
		SistemaParametros sistemaParametros = new SistemaParametros();
		try {
			sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
		
		} catch (FachadaException e) {
			e.printStackTrace();
		}
		
		btRelatorioPorCadastrador = (Button) findViewById(R.id.btRelatorioPorCadastrador);
		btRelatorioPorCadastrador.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				try {
					List<String> listaLogin = Fachada.getInstance().pesquisarListaLogin();
					
					if(listaLogin == null || listaLogin.size() == 0){						
						Util.exibirMensagemErro(RelatorioActivity.this, "Nenhum Imóvel foi Atualizado");
						
					}else{
						Intent j = new Intent(RelatorioActivity.this, RelatorioPorCadastradorActivity.class);
						startActivity(j);
					}
					
				} catch (FachadaException e) {	
					e.printStackTrace();
				}

				}
			});
		
		layoutBtCadastrador = (LinearLayout) findViewById(R.id.btCadastrador);
		
		if((sistemaParametros.getLogin() == null || sistemaParametros.getLogin().equals(""))
				|| (sistemaParametros.getSenha() == null || sistemaParametros.equals(""))){
				
			layoutBtCadastrador.setVisibility(View.GONE);
			
		}
		
		btVoltar = (Button) findViewById(R.id.btVoltar);

		btVoltar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(RelatorioActivity.this, RoteiroActivity.class);
				startActivity(intent);
				finish();
			}
		});	
	}

	@Override
    public void onBackPressed() { 
	}
}
