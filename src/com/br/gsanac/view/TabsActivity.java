package com.br.gsanac.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.ActivityException;
import com.br.gsanac.helpers.HelperAtualizarImovel;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.ClienteAtlzCadastral;
import com.br.gsanac.pojos.ClienteAtlzCadastral.ClienteAtlzCadastralColuna;
import com.br.gsanac.pojos.ClienteFoneAtlzCad;
import com.br.gsanac.pojos.ClienteFoneAtlzCad.ClienteFoneAtlzCadColuna;
import com.br.gsanac.pojos.ClienteTipo;
import com.br.gsanac.pojos.ClienteTipo.ClienteTipoColuna;
import com.br.gsanac.pojos.FonteAbastecimento;
import com.br.gsanac.pojos.Foto;
import com.br.gsanac.pojos.HidrometroInstHistAtlzCad;
import com.br.gsanac.pojos.HidrometroInstHistAtlzCad.HidrometroInstHistAtlzCadColuna;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.pojos.ImovelOcorrencia;
import com.br.gsanac.pojos.ImovelOcorrencia.ImovelOcorrenciaColuna;
import com.br.gsanac.pojos.ImovelSubCategAtlzCad;
import com.br.gsanac.pojos.ImovelSubCategAtlzCad.ImovelSubCategAtlzCadColuna;
import com.br.gsanac.pojos.Logradouro;
import com.br.gsanac.pojos.SistemaParametros;
import com.br.gsanac.tasks.AsyncResponse;
import com.br.gsanac.tasks.TaskAtualizarImovel;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

public class TabsActivity extends FragmentActivity implements AsyncResponse {
	private Fachada fachada;
	private SistemaParametros sistemaParametros;

	public static ImovelAtlzCadastral imovel;
	public static ImovelAtlzCadastral imovelInicial;

	public static ClienteAtlzCadastral cliente;

	public static HidrometroInstHistAtlzCad hidrometroInstalacaoHist;
	public static HidrometroInstHistAtlzCad hidrometroInstalacaoHistInicial;

	public static ArrayList<ClienteFoneAtlzCad> colecaoClienteFone;
	public static ArrayList<ImovelSubCategAtlzCad> colecaoImoveisSubCategoria;
	public static ArrayList<ImovelOcorrencia> colecaoImovelOcorrencia;

	public static Foto fotoFrenteDaCasa;
	public static Foto fotoHidrometro;
	public static Foto fotoFrenteDaCasaInicial;
	public static Foto fotoHidrometroInicial;

	public static boolean indicadorImovelNovo = false;
	public static boolean indicadorExibirMensagemErro = true;
	public static boolean primeiraVezAbaLigacao = true;

	// Componentes do layout
	private FragmentTabHost mTabHost;
	private TextView posicao;
	private TextView totalImoveis;
	private Button btnConcluir;
	private Button btnCancelar;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
        	setContentView(R.layout.tabs_activity);
            mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
            mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

            fachada = Fachada.getInstancia(this);

			sistemaParametros = fachada.pesquisarObjetoGenerico(SistemaParametros.class, null, null, null, null);

            posicao = (TextView) findViewById(R.id.posicao);
    		totalImoveis = (TextView) findViewById(R.id.total);
    		btnConcluir = (Button) findViewById(R.id.btnConcluir);
    		btnCancelar = (Button) findViewById(R.id.btnCancelar);

    		btnConcluir.setOnClickListener(new OnClickListener() {
    			@Override
				public void onClick(View v) {
					atualizarImovel();
    			}
    		});

    		btnCancelar.setOnClickListener(new OnClickListener() {
    			@Override
				public void onClick(View v) {
    				onBackPressed();
    			}
    		});

            exibirAbas();
            carregarDadosImovel();
            atualizarPosicoes();
    		Utilitarios.removerTeclado(this);

    		// Ao trocar de abas, esconder o teclado
    		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
    			@Override
    			public void onTabChanged(String tabId) {
    				Utilitarios.removerTeclado(TabsActivity.this);
    			}
    		});

            // Quando usado o teclado físico, as abas "roubam" o foco durante a digitação.
            // Este código pode ser removido se interferir no comportamento da aplicação no tablet.
            // http://stackoverflow.com/q/15669152
            mTabHost.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                @Override
                public void onViewDetachedFromWindow(View v) {}

                @Override
                public void onViewAttachedToWindow(View v) {
                    mTabHost.getViewTreeObserver().removeOnTouchModeChangeListener(mTabHost);
                }
            });
        } catch(Exception e) {
        	throw new ActivityException(e.getMessage() + "");
        }
    }

	@Override
	public void onBackPressed() {
		// Retorna para a tela de roteiro
		indicadorExibirMensagemErro = false;
		Utilitarios.removerTeclado(TabsActivity.this);
		finish();
	}

	private void carregarDadosImovel() throws CloneNotSupportedException {
		String selection;
        String[] selectionArgs;

		limparAtributos();

		// Imóvel recebido da tela de roteiro
		imovel = (ImovelAtlzCadastral) getIntent().getSerializableExtra(ConstantesSistema.IMOVEL);

		if (imovel == null || imovel.getId() == null) {
			// novo imóvel
			imovel = new ImovelAtlzCadastral();
			imovelInicial = imovel.clone();
			cliente = new ClienteAtlzCadastral();
			hidrometroInstalacaoHist = new HidrometroInstHistAtlzCad();
			imovel.setLogradouro(new Logradouro());

			FonteAbastecimento fonte = new FonteAbastecimento();
			fonte.setId(FonteAbastecimento.CAERN);
			imovel.setFonteAbastecimento(fonte);
			indicadorImovelNovo = true;
			return;
		}

		imovelInicial = imovel.clone();

		if(imovel.getImovelId() == null || imovel.getImovelId() < 1) {
			indicadorImovelNovo = true;
		}

		// Pesquisa Cliente
        selection = ClienteAtlzCadastralColuna.IMOVELATLZCAD_ID + "=?";
        selectionArgs = new String[] {
            String.valueOf(imovel.getId())
        };

        cliente = fachada.pesquisarObjetoGenerico(ClienteAtlzCadastral.class, selection, selectionArgs, null, null);

        if(cliente != null && cliente.getId() != null  && !cliente.getId().equals("")){
	        //Pesquisa Cliente Tipo
	   		selection = ClienteTipoColuna.ID + " = ?";
	   		selectionArgs = new String[] { String.valueOf(cliente.getClienteTipo().getId()) };

	   		ClienteTipo clienteTipo = fachada.pesquisarObjetoGenerico(ClienteTipo.class, selection, selectionArgs, null, null);

            cliente.setClienteTipo(clienteTipo);

            //Pesquisa Cliente Fone
            selection = ClienteFoneAtlzCadColuna.CLIENTEATLZCAD_ID + " = ?";
            selectionArgs = new String[] {
                String.valueOf(cliente.getId()),
            };

            colecaoClienteFone = fachada.pesquisarListaObjetoGenerico(ClienteFoneAtlzCad.class, selection, selectionArgs, null, null);
	   }

        // Pesquisa Hidrometro
       selection = HidrometroInstHistAtlzCadColuna.IMOVELATLZCAD_ID + " = ?";
       selection += " AND " + HidrometroInstHistAtlzCadColuna.MEDICAOTIPO_ID + " = ?";

       selectionArgs = new String[] {
           String.valueOf(imovel.getId()),
           "1" //Agua
       };

       hidrometroInstalacaoHist = fachada.pesquisarObjetoGenerico(HidrometroInstHistAtlzCad.class, selection, selectionArgs, null, null);
       if(hidrometroInstalacaoHist != null) {
    	   hidrometroInstalacaoHistInicial = hidrometroInstalacaoHist.clone();
       }

       //Pesquisar Foto Frente da Casa
       fotoFrenteDaCasa = Utilitarios.pesquisarFotoBanco(this, imovel.getId(), ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA);
       if(fotoFrenteDaCasa != null){
    	   fotoFrenteDaCasaInicial = fotoFrenteDaCasa.clone();
       }

       //Pesquisar Foto Hidrometro
       fotoHidrometro = Utilitarios.pesquisarFotoBanco(this, imovel.getId(), ConstantesSistema.FOTO_TIPO_HIDROMETRO);
       if(fotoHidrometro != null){
    	   fotoHidrometroInicial = fotoHidrometro.clone();
       }

		//Pesquisar ImovelOcorrencia Atualização Cadastral
        selection = ImovelOcorrenciaColuna.IMOVELATLZCAD_ID + "=?";
        selectionArgs = new String[] {
            String.valueOf(imovel.getId())
        };

        colecaoImovelOcorrencia = fachada.pesquisarListaObjetoGenerico(ImovelOcorrencia.class,selection, selectionArgs, null, null);
        if(colecaoImovelOcorrencia == null) {
        	colecaoImovelOcorrencia = new ArrayList<ImovelOcorrencia>();
        }

		//Pesquisar SubCategoria Atualização Cadastral
        selection = ImovelSubCategAtlzCadColuna.IMOVELATLZCAD_ID + "=?";
        selectionArgs = new String[] {
            String.valueOf(imovel.getId())
        };

        colecaoImoveisSubCategoria = fachada.pesquisarListaObjetoGenerico(ImovelSubCategAtlzCad.class,selection, selectionArgs, null, null);
        if(colecaoImoveisSubCategoria == null) {
        	colecaoImoveisSubCategoria = new ArrayList<ImovelSubCategAtlzCad>();
        }
	}

	private void exibirAbas() {
		novaAba("localidade", "Localidade", LocalidadeAbaActivity.class);
		novaAba("endereco", "Endereço", EnderecoAbaActivity.class);
		novaAba("cliente", "Cliente", ClienteAbaActivity.class);
		novaAba("imovel", "Imóvel", ImovelAbaActivity.class);
		novaAba("ligacao", "Ligação", LigacaoAbaActivity.class);
		novaAba("fotos", "Fotos", FotosAbaActivity.class);
	}

    @SuppressLint("InflateParams")
	private void novaAba(final String tag, String text, Class<?> classe) {
    	View view = LayoutInflater.from(mTabHost.getContext()).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);

		TabSpec tabSpec = mTabHost.newTabSpec(tag).setIndicator(view);
		mTabHost.addTab(tabSpec, classe, null);
    }

	private void atualizarPosicoes() {
		totalImoveis = (TextView) findViewById(R.id.total);
	    posicao = (TextView) findViewById(R.id.posicao);

	    if(imovel!=null && imovel.getPosicao() != null ){
			posicao.setText(imovel.getPosicao()+"/");
			totalImoveis.setText( sistemaParametros.getQuantidadeImovel() );
		}	else {
			posicao.setText("NOVO IMÓVEL");
			totalImoveis.setText("");
		}
	}

	public void atualizarImovel() {
		Utilitarios.removerTeclado(this);

		// invocar o onPause da aba atual para gravar os dados
		Fragment f = getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
		indicadorExibirMensagemErro = false;
		f.onPause();
		indicadorExibirMensagemErro = true;

		final HelperAtualizarImovel helper = new HelperAtualizarImovel();
		helper.setImovel(imovel);
		helper.setImovelInicial(imovelInicial);
		helper.setCliente(cliente);
		helper.setHidrometro(hidrometroInstalacaoHist);
		helper.setHidrometroInicial(hidrometroInstalacaoHistInicial);
		helper.setListFone(colecaoClienteFone);
		helper.setListSubCategoria(colecaoImoveisSubCategoria);
		helper.setListOcorrencia(colecaoImovelOcorrencia);
		helper.setFotoFrenteCasa(fotoFrenteDaCasa);
		helper.setFotoHidrometro(fotoHidrometro);
		helper.setIdCodigoSetorComercial(sistemaParametros.getIdCodigoSetorComercial());

		// Valida os dados do imóvel
		String mensagemErro = fachada.validarImovel(helper);

		if(!Utilitarios.isVazio(mensagemErro)) {
			Utilitarios.mensagemAlert(this, mensagemErro, MensagemTipo.ERRO);
			return;
		}
		
		// Confirmação
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Atualização?");
        builder.setMessage("Após a confirmação e transmissão, não será mais possível editar os campos deste imóvel.");
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.str_nao), null);
        builder.setPositiveButton(getString(R.string.str_sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
        		ProgressDialog progressDialog = new ProgressDialog(TabsActivity.this) {
        			@Override
        			public void onBackPressed() { }

        			@Override
        			public boolean onSearchRequested() { return false; }
        		};

        		progressDialog.setTitle("Atualizando o Imóvel");
        		progressDialog.setMessage("Por favor, aguarde enquanto enviamos os dados para o GSAN.");
        		progressDialog.setIndeterminate(false);
        		progressDialog.setIcon(R.drawable.ok);
        		progressDialog.setCancelable(false);
        		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        		progressDialog.show();

        		// Insere/atualiza os dados no banco
        		// Também tenta transmitir os dados para o servidor do GSAN
        		new TaskAtualizarImovel(TabsActivity.this, helper, progressDialog).execute();
            }
        });
        builder.show();
	}

	public void processTaskResult(Boolean resultado, Boolean erroRecepcao, String msgErro) {
		if (resultado) {
			setResult(erroRecepcao ? ConstantesSistema.RESULT_ERRO_RECEPCAO : Activity.RESULT_OK);
			finish();
		} else {
			Utilitarios.mensagemAlert(this, msgErro, MensagemTipo.ERRO);
		}
	}

	/**
	 * Limpa todos os atributos estáticos
	 */
    public static void limparAtributos() {
	    imovel = null;
        cliente = null;
        hidrometroInstalacaoHist = null;
        hidrometroInstalacaoHistInicial = null;
        colecaoClienteFone = null;
        colecaoImovelOcorrencia = null;
        colecaoImoveisSubCategoria = null;
        indicadorImovelNovo = false;
        indicadorExibirMensagemErro = true;
        primeiraVezAbaLigacao = true;
        fotoFrenteDaCasa = null;
        fotoFrenteDaCasaInicial = null;
        fotoHidrometro = null;
        fotoHidrometroInicial = null;
    }
}
