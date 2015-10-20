package com.br.gsanac.view;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.api.core.Retorno;
import com.br.gsanac.R;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.ActivityException;
import com.br.gsanac.persistencia.conexao.BancoDeDadosHelper;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.SistemaParametros;
import com.br.gsanac.pojos.SistemaParametros.SistemaParametrosColuna;
import com.br.gsanac.tasks.AsyncResponse;
import com.br.gsanac.tasks.TaskDownloadArquivo;
import com.br.gsanac.tasks.TaskTransmitirArquivoOnline;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Criptografia;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @author Bruno Sá Barreto
 * @author André Miranda
 *
 * @since 02/09/2014
 */
public class LoginActivity extends Activity implements AsyncResponse {
	// Atributos do xml login_activity.xml
	private TextView textViewVersao;
	private EditText editTextLogin;
	private EditText editTextSenha;
	private Button buttonEntrar;
	private Fachada fachada;
	
	private static final String htmlSobre =
		"<html><body style=\" text-align:center; font-size: 20px; \">"
		+ "<p>"
			+ "O projeto Atualiza&ccedil;&atilde;o Cadastral da <a href=\"http://www.gsan.ipad.com.br/\">Consenso Solu&ccedil;&otilde;es em Tecnologia</a> est&aacute; "
			+ "licenciado com uma Licen&ccedil;a Creative Commons:"
			+ "<br /><a href=\"http://creativecommons.org/licenses/by-nc-sa/4.0/\">Atribui&ccedil;&atilde;o-N&atilde;oComercial-CompartilhaIgual 4.0 Internacional</a>"
		+ "</p>"
		+ "<p>"
			+ "Podem estar dispon&iacute;veis autoriza&ccedil;&otilde;es adicionais &agrave;s concedidas no &acirc;mbito desta licen&ccedil;a em: "
			+ "<br /><a href=\"http://www.gsan.ipad.com.br/\">www.gsan.ipad.com.br</a>"
		+ "</p>"
		+ "</body></html>";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_sobre, menu);
		return true;
	}

	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.itemSobre:
				return exibirSobre();

		    case R.id.itemCarregarArquivoOffline:
    			return carregarArquivoOffline();

		    case R.id.itemTransmitirArquivoOnline:
		    	return transmitirArquivoOnline();

		    default:
		        return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		try {
			// Cria os diretórios do app caso não existam
			Utilitarios.criarDiretorios();
	        Utilitarios.removerTeclado(this);

	        fachada = Fachada.getInstancia(this);

			// Seta a versão da aplicação
			textViewVersao = (TextView)findViewById(R.id.textViewVersao);
			textViewVersao.setText(ConstantesSistema.VERSAO + Utilitarios.obterVersaoAPP(LoginActivity.this));
			editTextLogin = (EditText)findViewById(R.id.editTextLogin);
			editTextSenha  = (EditText)findViewById(R.id.editTextSenha);
			buttonEntrar = (Button)findViewById(R.id.buttonEntrar);

			buttonEntrar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Utilitarios.removerTeclado(LoginActivity.this);

					String login = editTextLogin.getText().toString();
					String senha = editTextSenha.getText().toString();

					if(!verificarPreenchimentoCamposLoginSenha(login,senha)){
						return;
					}

					if(Utilitarios.verificarArquivoPendenteTransmissao() != null) {
						Utilitarios.mensagemAlert(LoginActivity.this,
								R.string.string_arquivo_pendente_transmitir, MensagemTipo.ERRO);
						return;
					}

					/*
					 * Verificar a existencia de dados na base de dados
					 * 1. caso exista:
					 *  	validar dados de acesso e chamar a tela de roteiro
					 * 2. caso não exista:
					 * 		iniciar o processo de tentativa de carregamento de arquivo online
					 */
					if (Utilitarios.verificarSeExisteArquivoCarregado(v.getContext())) {
						if (!Utilitarios.validarCarregamentoCompletoArquivoTxt(LoginActivity.this)) {
							Utilitarios.removerInstanciasRepositorios();
							BancoDeDadosHelper.deletarBancoDeDados();
							executarAlertErroCarregamentoArquivo();
						} else if (validarAutenticacaoAcesso(login, senha)) {
							verificarDataParaChamarTelaRoteiro();
						}
					} else {
						carregarArquivoOnline(login, senha);
					}
				}
			});
		} catch (Exception e) {
			throw new ActivityException(e.getMessage());
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem itemTransmitir = menu.findItem(R.id.itemTransmitirArquivoOnline);
		boolean existeArquivo = (Utilitarios.verificarArquivoPendenteTransmissao() != null);
		itemTransmitir.setEnabled(existeArquivo);
		return true;
	}

	/**
	 * Exibe um alert com o erro de falha no carregamento do arquivo
	 * solicitando o recarregamento do mesmo.
	 *
	 * @author Bruno Sá Barreto
	 * @since 13/11/2014
	 *
	 */
	private void executarAlertErroCarregamentoArquivo() {
		AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
		alert.setTitle(getString(R.string.string_erro_inesperado_carregamento_arquivo));
		alert.setMessage(getString(R.string.string_contato_administrador_sistema_erro_carregamento));
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {}
		});
		alert.show();
	}

	 /**
	 * Realiza a verificação da data, caso seja confirmada,
	 * redireciona o usuário para a tela de roteiro.
	 *
	 * @author Bruno Sá Barreto
	 * @since 13/11/2014
	 */
	private void verificarDataParaChamarTelaRoteiro(){
		String data = Utilitarios.converterDataParaString(new Date());
		AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
		alert.setTitle("Atualização Cadastral");
		alert.setMessage("A data atual é " + data.substring(0,10) + ". Confirma? ");
		alert.setPositiveButton(R.string.str_sim, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Utilitarios.chamarActivitySemParametros(LoginActivity.this,
						RoteiroActivity.class);
			}
		});
		alert.setNegativeButton(R.string.str_nao, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
				startActivity(intent);
			}
		});
		alert.show();
	}

	/**
	 * Este método verifica o preenchimento dos campos
	 * login e senha e informa críticas caso algum dos
	 * mesmos não esteja preenchido.
	 *
	 * @author Bruno Sá Barreto
	 * @since 13/11/2014
	 *
	 * @param login
	 * @param senha
	 */
	private boolean verificarPreenchimentoCamposLoginSenha(String login, String senha) {
		if (login.trim().isEmpty()) {
			Utilitarios.mensagemAlert(LoginActivity.this, R.string.string_informe_login, MensagemTipo.ERRO);
			return false;
		}

		if (senha.trim().isEmpty()) {
			Utilitarios.mensagemAlert(LoginActivity.this, R.string.string_informe_senha, MensagemTipo.ERRO);
			return false;
		}

		return true;
	}

	/**
	 * Este método chama a activity responsável pelas validações
	 * e carregamento do arquivo via transmissão de dados online
	 *
	 * @author Bruno Sá Barreto
	 * @since 13/11/2014
	 *
	 * @param login
	 * @param senha
	 */
	private void carregarArquivoOnline(String login, String senha){
		ProgressDialog progressDialog = Utilitarios.montarProgressDialogPadrao(this,
				R.string.string_titulo_carregando_arquivo);
		progressDialog.setIndeterminate(false);
		progressDialog.show();

		TaskDownloadArquivo task = new TaskDownloadArquivo(this, progressDialog);
		task.execute(login, senha);
	}

	/**
	 * Verifica na base de dados se o arquivo carregado está associado
	 * aos dados de acesso fornecidos.
	 *
	 * @author Bruno Sá Barreto
	 * @since 13/11/2014
	 *
	 * @param login
	 * @param senha
	 */
	private boolean validarAutenticacaoAcesso(String login,String senha){
		if (!BancoDeDadosHelper.verificarSeExisteBancoDeDados()) {
			return false;
		}

		if (!Utilitarios.validarCarregamentoCompletoArquivoTxt(LoginActivity.this)) {
			return false;
		}

		String[] atributosWhere = null;
		String[] valorAtributosWhere = null;

		atributosWhere = new String[] { SistemaParametrosColuna.LOGIN,
				SistemaParametrosColuna.SENHA };
		valorAtributosWhere = new String[] { login, Criptografia.encode(senha) };

		SistemaParametros sistemaParametros = fachada.pesquisarObjetoGenerico(SistemaParametros.class,
						Retorno.retornarStringFormatadaPreparedStatement(atributosWhere),
						valorAtributosWhere, null, null);

		if (sistemaParametros != null) {
			return true;
		}

		Utilitarios.mensagemAlert(LoginActivity.this, R.string.string_login_invalido, MensagemTipo.ERRO);
		return false;
	}

	private boolean exibirSobre() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_sobre);
		dialog.setTitle("Sobre");

		// Adicionando componentes do dialog box, imagem, texto e botão
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.creative_commons);

		WebView textoLicencaSobre = (WebView) dialog.findViewById(R.id.dialogTextoDescricao);
		textoLicencaSobre.setBackgroundColor(0x00000000);
		textoLicencaSobre.loadData(htmlSobre, "text/html", null);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		
		return true;
	}

	private boolean carregarArquivoOffline() {
    	if(Utilitarios.verificarSeExisteArquivoCarregado(this)) {
			Utilitarios.mensagemAlert(this, R.string.string_ja_existe_arquivo_carregado, MensagemTipo.ERRO);
			return false;
    	}

    	if(!Utilitarios.verificaExistenciaArquivosParaCarregamento()) {
    		Utilitarios.mensagemAlert(this, R.string.string_nao_existe_arquivo_para_carregar, MensagemTipo.ERRO);
    		return false;
    	}

    	Intent intent = new Intent(LoginActivity.this, SelecionarArquivoOfflineActivity.class);
		startActivity(intent);
		
		return true;
	}

	private boolean transmitirArquivoOnline() {
		ProgressDialog progressDialog = new ProgressDialog(this) {
			@Override
			public void onBackPressed() {}

			@Override
			public boolean onSearchRequested() {
				return false;
			}
		};

		progressDialog.setTitle("Transmitindo arquivo");
		progressDialog.setMessage("Por favor, aguarde enquanto o arquivo é transmitido.");
		progressDialog.setIndeterminate(false);
		progressDialog.setIcon(R.drawable.ok);
		progressDialog.setCancelable(false);
		progressDialog.show();

		File file = Utilitarios.verificarArquivoPendenteTransmissao();

 		TaskTransmitirArquivoOnline task = new TaskTransmitirArquivoOnline(LoginActivity.this, progressDialog, file);
		task.execute();

		return true;
	}

	public void processTaskResult(Boolean resultado, Boolean erroTransmissao, String msgErro) {
		invalidateOptionsMenu();

		if(resultado && !erroTransmissao) {
			Utilitarios.mensagemAlert(this, "Arquivo transmitido com sucesso", MensagemTipo.INFO);
		} else {
			Utilitarios.mensagemAlert(this, msgErro, MensagemTipo.ERRO);
		}
	}
}
