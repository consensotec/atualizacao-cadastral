package com.br.gsanac.view;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.excecoes.FachadaException;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.pojos.SistemaParametros;
import com.br.gsanac.utilitarios.ConstantesSistema;

public class RelatorioActivity  extends Activity{

	private TextView totalImoveis;
	private TextView totalImoveisAtualizados;
	private TextView totalImoveisIncluidos;
	private TextView totalImoveisAvisitar;
	private TextView totalImovelVisitados;
	private TextView percentualImoveisAtualizados;
	private LinearLayout percentualConcluido;
	private LinearLayout percentualNaoConcluido;
	private Button btRelatorioPorOcorrencia;
	private Button btVoltar;
	private LinearLayout layoutBtCadastrador;

	ArrayList<ImovelAtlzCadastral> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relatorio_activity);

		// Barra de porcentagem
		percentualConcluido = (LinearLayout) findViewById(R.id.concluido);
		android.widget.LinearLayout.LayoutParams parametroPercentualConcluido = (LayoutParams) percentualConcluido.getLayoutParams();

		percentualNaoConcluido = (LinearLayout) findViewById(R.id.nao_concluido);
		android.widget.LinearLayout.LayoutParams parametroPercentualNaoConcluido = (LayoutParams) percentualNaoConcluido.getLayoutParams();

		Fachada fachada = Fachada.getInstancia(this);

		listaImoveis = fachada.pesquisarListaTodosObjetosGenerico(ImovelAtlzCadastral.class);

		//Total de Imoveis
		int qtdTotalImovel = listaImoveis.size();
		int quantidadeImovelAtualizado = 0;
		int quantidadeImovelIncluido = 0;
		int imovelAvisitar = 0;

		for(ImovelAtlzCadastral imovel : listaImoveis){
			//imovel com ocorrência de cadastro
			if(imovel.getImovelId().intValue()>0 && imovel.getIndicadorFinalizado().equals(ConstantesSistema.SIM)){
				quantidadeImovelAtualizado++;
			//imovel novo
			}else if(imovel.getImovelId().intValue()==0 && imovel.getIndicadorFinalizado().equals(ConstantesSistema.SIM)){
				quantidadeImovelIncluido++;
			}
		}

		// Total de Imoveis Visitados
		int qtdImovelVisitado = quantidadeImovelAtualizado + quantidadeImovelIncluido;

		// Total de Imoveis a Visitar
		imovelAvisitar = qtdTotalImovel - qtdImovelVisitado;

		// Bara de Porcentagem
		double percentual = ((float) qtdImovelVisitado / (float) qtdTotalImovel) *100;

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

		if (qtdImovelVisitado == 0) {
			percentualImoveisAtualizados.setText("0%");
			parametroPercentualConcluido.weight = 1f;
			parametroPercentualNaoConcluido.weight = 0f;
		} else {
			percentualImoveisAtualizados.setText(String.format("%.1f%%", percentual));
			double totalConcluido = percentual / 100;
			parametroPercentualConcluido.weight = (float) (1 - totalConcluido);
			parametroPercentualNaoConcluido.weight = (float) (totalConcluido);
		}

		btRelatorioPorOcorrencia = (Button) findViewById(R.id.btRelatorioPorOcorrencia);
		btRelatorioPorOcorrencia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(arg0.getContext(), RelatorioPorOcorrenciaCadastroActivity.class);
				startActivityForResult(i,ConstantesSistema.RELATORIO_ACTIVITY_REQUEST_CODE);
			}
		});

		SistemaParametros sistemaParametros = new SistemaParametros();
		try {
			sistemaParametros = (SistemaParametros) fachada.pesquisarListaTodosObjetosGenerico(SistemaParametros.class).get(0);
		} catch (FachadaException e) {
			e.printStackTrace();
		}

		if ((sistemaParametros.getLogin() == null || sistemaParametros.getLogin().equals(""))
				|| (sistemaParametros.getSenha() == null || sistemaParametros.equals(""))) {

			layoutBtCadastrador.setVisibility(View.GONE);
		}

		btVoltar = (Button) findViewById(R.id.btVoltar);
		btVoltar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
