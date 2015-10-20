package com.br.gsanac.view;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.br.gsanac.R;
import com.br.gsanac.adapters.SelecionarArquivoOfflineAdapter;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.ActivityException;
import com.br.gsanac.persistencia.conexao.BancoDeDadosHelper;
import com.br.gsanac.tasks.AsyncResponse;
import com.br.gsanac.tasks.TaskCarregarArquivoOffline;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 08/09/2014
 */
public class SelecionarArquivoOfflineActivity extends Activity implements AsyncResponse {
	private List<String> listaArquivos;
	private ListView listViewlistaArquivosOffilineCarregamento;
	private SelecionarArquivoOfflineAdapter selecionarArquivoOfflineAdapter;
	private ProgressDialog progressDialogCarregamentoArquivo;
	private String nomeArquivoZip;
	private String nomeArquivoTxt;
	private String cpf;

	/**
	 * Método responsável por gerenciar os eventos
	 * da tela selecionar_arquivo_offline_activity.xml
	 *
	 * @author Jonathan Marcos
	 * @since 08/09/2014
	 * @param Bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecionar_arquivo_offline_activity);

		try {
			//  Cria os diretórios do app caso não exista
			Utilitarios.criarDiretorios();

			// Analisar o valor do atributo cpf
			cpf = analisarCpf(this);

			listViewlistaArquivosOffilineCarregamento = (ListView)
					findViewById(R.id.listViewlistaArquivosOffilineCarregamento);

			 // Obtem lista com os arquivos TXT
			listaArquivos = Utilitarios.obterListaArquivosDiretorio(
					ConstantesSistema.CAMINHO_SDCARD_GSANAC_CARREGAMENTO,
					ConstantesSistema.EXTENSAO_ARQUIVO_ZIP);
			if (listaArquivos == null) {
				Utilitarios.mensagemAlert(this, R.string.string_nao_existe_arquivo_para_carregar, MensagemTipo.ERRO, true);
			} else {
				// Preenche a ListView com os nomes dos arquivos
				selecionarArquivoOfflineAdapter = new SelecionarArquivoOfflineAdapter(this, listaArquivos);
				listViewlistaArquivosOffilineCarregamento.setAdapter(selecionarArquivoOfflineAdapter);

				// Carregar arquivo oa pressionar e segurar num item da lista
				listViewlistaArquivosOffilineCarregamento.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						nomeArquivoZip = (String) view.getTag();
						nomeArquivoTxt = Utilitarios.obterNomeArquivoTxtCarregamento(nomeArquivoZip);

						if (!validarArquivoCarregamentoOffline(nomeArquivoZip)) {
							Utilitarios.mensagemAlert(SelecionarArquivoOfflineActivity.this,
									R.string.string_arquivo_invalido, MensagemTipo.ERRO);
							return false;
						}

						// limpar pasta de mapas antes do carregamento
						Utilitarios.limparPastaMapas();

						// colocar o txt na pasta de carregamento
						// colocar os mapas das quadras na pasta de mapas
						Utilitarios.extrairArquivoZipCarregamento(nomeArquivoZip);

						// depois de extraido, remove o arquivo.
						Utilitarios.deletarArquivoGsan(nomeArquivoZip);

						// Exibir o progressDialog
						progressDialogCarregamentoArquivo = Utilitarios.montarProgressDialogPadrao(
								SelecionarArquivoOfflineActivity.this,
								R.string.string_carregando_arquivo_offline);
						progressDialogCarregamentoArquivo.show();

						// Verifica versão do arquivo TXT antes de carregar o arquivo
						String[] versaoAPPArquivo = null;

						try {
							versaoAPPArquivo = validarVersaoArquivoTexto(nomeArquivoTxt);
						} catch (Exception ex) {
							Utilitarios.mensagemAlert(SelecionarArquivoOfflineActivity.this,
									R.string.string_arquivo_corrompido, MensagemTipo.ERRO, true);
						}

						if (versaoAPPArquivo == null) {
							// Executa a asyncTaskCarregarBancoDeDados
							TaskCarregarArquivoOffline task = new TaskCarregarArquivoOffline(
									SelecionarArquivoOfflineActivity.this, progressDialogCarregamentoArquivo,
									nomeArquivoTxt, cpf);
							task.execute();
						} else {
							progressDialogCarregamentoArquivo.dismiss();
							String mensagem = "Versão do Aplicativo diferente da versão do arquivo:\n\n"
											 +"Versão do Aplicativo: "+versaoAPPArquivo[0]+"\n"
											 +"Versão do Arquivo   : "+versaoAPPArquivo[1];
							Utilitarios.mensagemAlert(SelecionarArquivoOfflineActivity.this, mensagem, MensagemTipo.ERRO, true);
						}

						return false;
					}
				});
			}
		} catch (Exception e) {
			throw new ActivityException(e.getMessage());
		}
	}

	/**
	 * Método responsável por validar a versão do arquivo TXT
	 *
	 * @param nomeArquivoTXT
	 * @return
	 */
	private String[] validarVersaoArquivoTexto(String nomeArquivoTXT) {
		String[] versaoArquivos = null;
		Integer versaoArquivoTexto = Utilitarios.obterVersaoArquivoTextoInteger(nomeArquivoTXT);
		Integer versaoApp = Utilitarios.obterVersaoAPPInteger(SelecionarArquivoOfflineActivity.this);

		if (versaoApp.equals(versaoArquivoTexto)) {
			return versaoArquivos;
		}

		versaoArquivos = new String[] {
				Utilitarios.obterVersaoAPP(SelecionarArquivoOfflineActivity.this),
				Utilitarios.obterVersaoArquivoTexto(nomeArquivoTXT)
		};

		return versaoArquivos;
	}

	/**
	 *
	 * Método responsável por analisar o cpf
	 *
	 * @param context
	 * @return
	 */
	private String analisarCpf(Context context) {
		String cpf = ConstantesSistema.CPF_VAZIO;
		Intent intent = ((Activity)context).getIntent();

		if (intent.getSerializableExtra(ConstantesSistema.CPF_LOGIN) != null) {
			cpf = (String) intent.getSerializableExtra(ConstantesSistema.CPF_LOGIN);
		}

		return cpf;
	}

	/**
	 * Verifica se o arquivo zip contém o txt a ser processado.<br>
	 * Os arquivos map são opcionais, por isso não seram considerados.
	 *
	 * @author Bruno Sá Barreto
	 * @since 10/12/2014
	 */
	public boolean validarArquivoCarregamentoOffline(String pathArquivo) {
		String path = ConstantesSistema.CAMINHO_SDCARD_GSANAC_CARREGAMENTO + "/" + nomeArquivoZip;
		ZipFile zip = null;

		try {
			zip = new ZipFile(new File(path));
			Enumeration<? extends ZipEntry> e = zip.entries();

			while (e.hasMoreElements()) {
				ZipEntry entrada = e.nextElement();
				if (entrada.getName().endsWith(".txt")) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				zip.close();
			} catch (Exception ignorar) {}
		}

		return false;
	}

	public void processTaskResult(Boolean resultado, Boolean erroRecepcao, String msgErro) {
		if(resultado && !erroRecepcao) {
			Utilitarios.showMessage(this, "Arquivo carregado com sucesso", Toast.LENGTH_SHORT, false);
		} else {
			BancoDeDadosHelper.deletarBancoDeDados();
			Utilitarios.mensagemAlert(this, R.string.string_arquivo_corrompido, MensagemTipo.ERRO, true);
		}
	}
}
