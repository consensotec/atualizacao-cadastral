package com.br.gsanac.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.br.gsanac.R;
import com.br.gsanac.excecoes.ActivityException;
import com.br.gsanac.persistencia.conexao.BancoDeDadosHelper;
import com.br.gsanac.tasks.AsyncResponse;
import com.br.gsanac.tasks.TaskVerificarNovaVersao;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * Activity que serve como ponto de entrada do aplicativo.<br>
 * Cria as as pastas utilizadas pelo aplicativo e verifica se existe uma nova
 * versão do mesmo.<br>
 * 
 * @author André Miranda
 * @since 21/05/2015
 */
public class MainActivity extends Activity implements AsyncResponse {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			Utilitarios.criarDiretorios();
			verificarNovaVersao();
		} catch (Exception e) {
			throw new ActivityException(e.getMessage());
		}
	}

	/**
	 * Método responsável pela verificação de novas versões.<br>
	 * Caso existe uma nova versão, o processo de atualização será iniciado,
	 * caso contrário o sistema irá exibir a tela de login.<br>
	 * Observe que a verificação por novas versões só será realizada se não
	 * houver arquivo carregado.
	 * 
	 * @author Jonathan Marcos
	 * @author André Miranda
	 * @since 15/09/2014
	 */
	private void verificarNovaVersao() {
		if (BancoDeDadosHelper.verificarSeExisteBancoDeDados()) {
			Utilitarios.chamarActivitySemParametros(this, LoginActivity.class);
			return;
		}

		final ProgressDialog progressDialog = Utilitarios.montarProgressDialogPadrao(this,
				R.string.string_verificando_informacoes_servidor_online);
		progressDialog.show();

		TaskVerificarNovaVersao task = new TaskVerificarNovaVersao(this, progressDialog);
		task.execute();
	}

	@Override
	public void processTaskResult(Boolean result, Boolean erroTransmissao, String msgErro) {
		if(result) {
			Utilitarios.chamarActivitySemParametros(MainActivity.this, DownloadApkActivity.class);
		} else {
			Utilitarios.chamarActivitySemParametros(MainActivity.this, LoginActivity.class);
		}
	}
}
