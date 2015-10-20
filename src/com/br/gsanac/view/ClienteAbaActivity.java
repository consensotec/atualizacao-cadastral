package com.br.gsanac.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gsanac.R;
import com.br.gsanac.bean.ClienteFoneHelper;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.FachadaException;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.ClienteAtlzCadastral;
import com.br.gsanac.pojos.ClienteFoneAtlzCad;
import com.br.gsanac.pojos.ClienteTipo;
import com.br.gsanac.pojos.ClienteTipo.ClienteTipoColuna;
import com.br.gsanac.pojos.FoneTipo;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.pojos.OrgaoExpedidorRg;
import com.br.gsanac.pojos.OrgaoExpedidorRg.OrgaoExpedidorRgColuna;
import com.br.gsanac.pojos.PessoaSexo;
import com.br.gsanac.pojos.UnidadeFederacao;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Mascara;
import com.br.gsanac.utilitarios.Utilitarios;

public class ClienteAbaActivity extends Fragment {
	private Fachada fachada;
	private Context context;
	private View view;

	private Spinner spnTipoCliente;
	private RadioGroup radioGroupPessoaTipo;
	private RadioButton radioPessoaFisica;
	private RadioButton radioPessoaJuridica;
	private RadioGroup opcaoUsuarioProprietario;
	private RadioButton simUsuarioProprietario;
	private RadioButton naoUsuarioProprietario;
	private RadioGroup opcaoUsuarioResponsavel;
	private RadioButton simUsuarioResponsavel;
	private RadioButton naoUsuarioResponsavel;
	private RadioGroup opcaoApresentouDocumentacao;
	private RadioButton simApresentouDocumentacao;
	private RadioButton naoApresentouDocumentacao;
	private EditText edtCpfCnpj;
	private EditText edtNomeCliente;
	private EditText edtNomeMae;
	private EditText edtDataNascimento;
	private RadioGroup rgOpcoesPessoaSexo;
	private RadioButton radioMasculino;
	private RadioButton radioFeminino;
	private EditText edtRg;
	private Spinner spnOrgaoExpedidor;
	private Spinner spnUF;
	private EditText edtDataEmissaoRg;
	private Spinner spnFoneTipo;
	private EditText edtTelefone;
	private Button btAdicionarTelefone;
	private Map<ClienteFoneHelper, TableRow> tableRowsTelefone;
	private TableLayout tableLayoutTelefone;
	private ClienteAtlzCadastral clienteAtualizacaoCadastral;
	private ImovelAtlzCadastral imovelAtlzCadastral;
	private Collection<ClienteFoneHelper> colecaoClienteFoneHelper;
	private List<String> fonesTabela;
	private Mascara mascaraCpfCnpj;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = inflater.inflate(R.layout.cliente_aba, container, false);

        context = view.getContext();
		fachada = Fachada.getInstancia(context);

		//Tipo Cliente
		spnTipoCliente = (Spinner) view.findViewById(R.id.spnTipoCliente);
		//Nome Cliente
		edtNomeCliente = (EditText) view.findViewById(R.id.edtNomeCliente);
        edtNomeCliente.setFilters(new InputFilter[]{new InputFilter.AllCaps(),
        		new InputFilter.LengthFilter(100),
        			Utilitarios.filterReplaceCaracteresEspeciais()});
		//Nome da Mãe
		edtNomeMae =  (EditText) view.findViewById(R.id.edtNomeMae);
		edtNomeMae.setFilters(new InputFilter[]{new InputFilter.AllCaps(),
        		new InputFilter.LengthFilter(50),
    				Utilitarios.filterReplaceCaracteresEspeciais()});
		//Data de Nascimento
		edtDataNascimento = (EditText) view.findViewById(R.id.edtDataNascimento);
        edtDataNascimento.addTextChangedListener(new Mascara("##/##/####", edtDataNascimento));

		// Tipo Pessoa
		radioGroupPessoaTipo = (RadioGroup) view.findViewById(R.id.radioGroupPessoaTipo);
		radioPessoaFisica = (RadioButton) view.findViewById(R.id.radioPessoaFisica);
		radioPessoaJuridica = (RadioButton) view.findViewById(R.id.radioPessoaJuridica);
		radioPessoaFisica.setChecked(true);
		radioGroupPessoaTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radioPessoaFisica) {
					carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);
				} else if (checkedId == R.id.radioPessoaJuridica) {
					carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_JURIDICA);
				}
			}
		});

		// CPF-CNPJ
		edtCpfCnpj = (EditText) view.findViewById(R.id.edtCpfCnpj);
		mascaraCpfCnpj = new Mascara("###.###.###-##", edtCpfCnpj);
		edtCpfCnpj.addTextChangedListener(mascaraCpfCnpj);

		//Sexo
        rgOpcoesPessoaSexo = (RadioGroup) view.findViewById(R.id.rgOpcoesPessoaSexo);
		radioMasculino = (RadioButton) view.findViewById(R.id.radioMasculino);
		radioFeminino  = (RadioButton) view.findViewById(R.id.radioFeminino);

		//Usuario Proprietario
		opcaoUsuarioProprietario = (RadioGroup) view.findViewById(R.id.opcaoUsuarioProprietario);
		simUsuarioProprietario = (RadioButton) view.findViewById(R.id.simUsuarioProprietario);
		naoUsuarioProprietario = (RadioButton) view.findViewById(R.id.naoUsuarioProprietario);

		//Usuario Responsavel
		opcaoUsuarioResponsavel = (RadioGroup) view.findViewById(R.id.opcaoUsuarioResponsavel);
		simUsuarioResponsavel = (RadioButton) view.findViewById(R.id.simUsuarioResponsavel);
		naoUsuarioResponsavel = (RadioButton) view.findViewById(R.id.naoUsuarioResponsavel);

		//Apresentou Documento
		opcaoApresentouDocumentacao = (RadioGroup) view.findViewById(R.id.opcaoApresentouDocumentacao);
		simApresentouDocumentacao = (RadioButton) view.findViewById(R.id.simApresentouDocumentacao);
		naoApresentouDocumentacao = (RadioButton) view.findViewById(R.id.naoApresentouDocumentacao);

		//RG
		edtRg = (EditText) view.findViewById(R.id.edtRg);

		//Órgão Expedidor
		spnOrgaoExpedidor = (Spinner) view.findViewById(R.id.spnOrgaoExpedidor);
		List<Object> list = fachada.pesquisarListaObjetoGenerico(OrgaoExpedidorRg.class, null, null, null,
				OrgaoExpedidorRgColuna.DESCRICAO);
		spnOrgaoExpedidor.setAdapter(Utilitarios.getAdapterSpinner(context, list));

		//UF
		spnUF = (Spinner) view.findViewById(R.id.spnUF);
		list = fachada.pesquisarListaTodosObjetosGenerico(UnidadeFederacao.class);
		spnUF.setAdapter(Utilitarios.getAdapterSpinner(context, list));

		//Data Emissão Rg
		edtDataEmissaoRg = (EditText) view.findViewById(R.id.edtDataEmissaoRg);
    	edtDataEmissaoRg.addTextChangedListener(new Mascara("##/##/####", edtDataEmissaoRg));

    	//Tipo de Telefone
    	spnFoneTipo = (Spinner) view.findViewById(R.id.spnFoneTipo);
		list = fachada.pesquisarListaObjetoGenerico(FoneTipo.class, null, null, null, null);
    	spnFoneTipo.setAdapter(Utilitarios.getAdapterSpinner(context, list));

    	//Telefone
    	edtTelefone = (EditText) view.findViewById(R.id.edtTelefone);
    	edtTelefone.addTextChangedListener(new Mascara("(##)####-####", edtTelefone));

    	//Botão Adicionar Telefone
    	btAdicionarTelefone = (Button) view.findViewById(R.id.btAdicionarTelefone);
    	btAdicionarTelefone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validaCamposObrigatoriosTelefone()){
					try{
						Utilitarios.removerTeclado(ClienteAbaActivity.this.getActivity());
		    			inserirLinhaTabelaTelefone();
		    		}catch (IllegalArgumentException e) {
		    			Utilitarios.mensagemAlert(context, e.getMessage(), MensagemTipo.ERRO);
					}
				}
			}
		});

    	//Tabela Telefone
    	tableLayoutTelefone = (TableLayout) view.findViewById(R.id.tabelaTelefones);
    	tableLayoutTelefone.setVisibility(View.VISIBLE);
		tableRowsTelefone = new HashMap<ClienteFoneHelper, TableRow>();
		fonesTabela = new ArrayList<String>();
		colecaoClienteFoneHelper = new ArrayList<ClienteFoneHelper>();

		//Carregar dados do imóvel e do cliente
		imovelAtlzCadastral = TabsActivity.imovel;
		clienteAtualizacaoCadastral = TabsActivity.cliente;

		carregarDadosDoImovel();

		return view;
	}

	private void carregarDadosDoImovel() {
		if(clienteAtualizacaoCadastral == null){
			clienteAtualizacaoCadastral = new ClienteAtlzCadastral();

			if(radioGroupPessoaTipo.getCheckedRadioButtonId() == R.id.radioPessoaFisica){
				carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);
			}else if(radioGroupPessoaTipo.getCheckedRadioButtonId() == R.id.radioPessoaJuridica){
				carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_JURIDICA);
			}
		}

        if(clienteAtualizacaoCadastral.getClienteTipo() != null &&
			clienteAtualizacaoCadastral.getClienteTipo().getId() != null){

			//Pesquisar Cliente Tipo: Físico ou Jurídico
			if(clienteAtualizacaoCadastral.getClienteTipo().getIndicadorPessoa() == ConstantesSistema.PESSOA_FISICA){
				radioPessoaFisica.setChecked(true);
				radioPessoaJuridica.setChecked(false);

				if(clienteAtualizacaoCadastral.getPessoaSexo() != null) {
					Integer sexo = clienteAtualizacaoCadastral.getPessoaSexo().getId();
					radioMasculino.setChecked(sexo != null && sexo.equals(PessoaSexo.MASCULINO));
					radioFeminino.setChecked(sexo != null && sexo.equals(PessoaSexo.FEMININO));
				}
				this.carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);
			}else{
				radioPessoaFisica.setChecked(false);
				radioPessoaJuridica.setChecked(true);
				radioMasculino.setChecked(false);
				radioFeminino.setChecked(false);
				this.carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_JURIDICA);
			}

			Utilitarios.selecionarItemSpinner(spnTipoCliente, clienteAtualizacaoCadastral.getClienteTipo().getId());
        }else{
			radioPessoaFisica.setChecked(true);
			radioPessoaJuridica.setChecked(false);
			this.carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);

			if(clienteAtualizacaoCadastral.getPessoaSexo() != null) {
				Integer sexo = clienteAtualizacaoCadastral.getPessoaSexo().getId();
				radioMasculino.setChecked(sexo != null && sexo.equals(PessoaSexo.MASCULINO));
				radioFeminino.setChecked(sexo != null && sexo.equals(PessoaSexo.FEMININO));
			}
		}

        //CPF/CNPJ
		if(clienteAtualizacaoCadastral.getNumeroCPFCNPPJ() != null &&
				!clienteAtualizacaoCadastral.getNumeroCPFCNPPJ().equals("")){
			edtCpfCnpj.setText(clienteAtualizacaoCadastral.getNumeroCPFCNPPJ());
		}

		//Nome
		edtNomeCliente.setText(clienteAtualizacaoCadastral.getNomeCliente());

		//Nome da Mãe
		edtNomeMae.setText(clienteAtualizacaoCadastral.getNomeMae());

		//Data Nascimento
		if(clienteAtualizacaoCadastral.getDataNascimento() != null){
			edtDataNascimento.setText(Utilitarios.converterDataParaString(
					clienteAtualizacaoCadastral.getDataNascimento()));
		}

		//RG
		if(clienteAtualizacaoCadastral.getNumeroRG() != null &&
				!clienteAtualizacaoCadastral.getNumeroRG().equals("")){
			edtRg.setText(clienteAtualizacaoCadastral.getNumeroRG());
		}

		//Carregar na Combobox o valor do órgão expedidor RG
		if(clienteAtualizacaoCadastral.getOrgaoExpedidorRg() != null &&
				!clienteAtualizacaoCadastral.getOrgaoExpedidorRg().getId().equals(0)){
			Utilitarios.selecionarItemSpinner(spnOrgaoExpedidor,
					clienteAtualizacaoCadastral.getOrgaoExpedidorRg().getId());
		}

		//Carregar na Combobox o valor da UF do RG
		if(clienteAtualizacaoCadastral.getUnidadeFederacao() != null &&
				!clienteAtualizacaoCadastral.getUnidadeFederacao().getId().equals(0)){
			Utilitarios.selecionarItemSpinner(spnUF,
					clienteAtualizacaoCadastral.getUnidadeFederacao().getId());
		}

		//Data Emissão RG
		if(clienteAtualizacaoCadastral.getDataEmissaoRg() != null){
			edtDataEmissaoRg.setText(Utilitarios.converterDataParaString(
					clienteAtualizacaoCadastral.getDataEmissaoRg()));
		}

        Collection<ClienteFoneAtlzCad> colecaoClienteFone = TabsActivity.colecaoClienteFone;
        carregarTelefones(colecaoClienteFone);

		// Usuario Proprietario
        if(clienteAtualizacaoCadastral.getIndicadorProprietario() == ConstantesSistema.SIM) {
        	simUsuarioProprietario.setChecked(true);
        } else if(clienteAtualizacaoCadastral.getIndicadorProprietario() == ConstantesSistema.NAO) {
        	naoUsuarioProprietario.setChecked(true);
        }

		// Usuario Responsável
        if(clienteAtualizacaoCadastral.getIndicadorResponsavel() == ConstantesSistema.SIM) {
        	simUsuarioResponsavel.setChecked(true);
        } else if(clienteAtualizacaoCadastral.getIndicadorResponsavel() == ConstantesSistema.NAO) {
        	naoUsuarioResponsavel.setChecked(true);
        }

		// Apresentou Documento
        if(clienteAtualizacaoCadastral.getIndicadorDocumento() == ConstantesSistema.SIM) {
        	simApresentouDocumentacao.setChecked(true);
        } else if(clienteAtualizacaoCadastral.getIndicadorDocumento() == ConstantesSistema.NAO) {
        	naoApresentouDocumentacao.setChecked(true);
        }
	}

    private void inserirLinhaTabelaTelefone() throws IllegalArgumentException {
    	int idFoneTipo = (int) spnFoneTipo.getSelectedItemId();

    	String codigoDDD = edtTelefone.getText().toString().substring(0, 4);
    	codigoDDD = codigoDDD.replace("(", "").replace(")", "");

    	String numeroTelefone = edtTelefone.getText()
    			.toString().substring(4, 13);

    	criarLinhaTabelaTelefone(idFoneTipo, codigoDDD, numeroTelefone, ConstantesSistema.NAO);
    }

	private void criarLinhaTabelaTelefone(int idFoneTipo, String codigoDDD, String numeroTelefone, Integer indicadorFonePadrao)
			throws IllegalArgumentException {
		ClienteFoneHelper helper = new ClienteFoneHelper();

		helper.setIdFoneTipo(idFoneTipo);
		helper.setIndicadorFonePadrao(indicadorFonePadrao);

		if (idFoneTipo == FoneTipo.RESIDENCIAL) {
			helper.setDescricaoFoneTipo("RESIDENCIAL");
		} else if (idFoneTipo == FoneTipo.COMERCIAL) {
			helper.setDescricaoFoneTipo("COMERCIAL");
		} else if (idFoneTipo == FoneTipo.CELULAR) {
			helper.setDescricaoFoneTipo("CELULAR");
		} else if (idFoneTipo == FoneTipo.FAX) {
			helper.setDescricaoFoneTipo("FAX");
		} else {
			throw new IllegalArgumentException("Informe o tipo do telefone");
		}

		codigoDDD = codigoDDD.replace("(", "").replace(")", "");
		helper.setCodigoDDD(codigoDDD);

		numeroTelefone = numeroTelefone.replace("-", "");
		helper.setNumeroTelefone(numeroTelefone);

        String numeroTelefoneFormatado = "";
        if ( numeroTelefone.length() > 7 ) {
        	numeroTelefoneFormatado = numeroTelefone.substring(0, 4) + "-" + numeroTelefone.substring(4, 8);
        } else {
        	numeroTelefoneFormatado = numeroTelefone;
        }

        String telefoneId = codigoDDD + numeroTelefone;

    	if(fonesTabela.contains(telefoneId)) {
    		Toast toast = Toast.makeText(context,
					getString(R.string.error_tel_ja_selecionado),
					Toast.LENGTH_SHORT);
    		toast.show();

    		return;
    	}
    	fonesTabela.add(telefoneId);

		TableRow tr = new TableRow(this.getActivity());
		tr.setGravity(Gravity.CENTER_VERTICAL);
		tr.setId(Integer.valueOf(numeroTelefone));
		tr.setLayoutParams(new TableLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		tr.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
		tr.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				removerLinhaTabelaTelefone(v);
				return true;
			}
		});

		TextView tvActionSelected = new TextView(this.getActivity());
	    tvActionSelected.setId(Integer.valueOf(numeroTelefone));
	    tvActionSelected.setGravity(Gravity.CENTER_VERTICAL);
	    tvActionSelected.setTextColor(Color.BLACK);
	    tvActionSelected.setHeight(50);
	    tvActionSelected.setPadding(3, 0, 0, 0);
	    tvActionSelected.setMaxWidth(10);

	    String telefone = "(" + codigoDDD + ")" + " " + numeroTelefoneFormatado + " "
	    		+ "(" +  helper.getDescricaoFoneTipo() + ")";

	    tvActionSelected.setText(telefone);
	    tvActionSelected.setTextSize(18);
	    tvActionSelected.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);

	    ImageView imgRemoveAction = new ImageView(this.getActivity());
	    imgRemoveAction.setId(Integer.valueOf(numeroTelefone));
	    imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
        imgRemoveAction.setImageResource(R.drawable.btnremover);
        imgRemoveAction.setScaleX(1.5f);
        imgRemoveAction.setScaleY(1.5f);
	    imgRemoveAction.setPadding(0, 0, 8, 0);

	    spnFoneTipo.setSelection(0);
	    edtTelefone.getText().clear();

	    tr.addView(tvActionSelected);
	    tr.addView(imgRemoveAction);

	    tableRowsTelefone.put(helper, tr);
	    colecaoClienteFoneHelper.add(helper);

	    populaTabelaTelefone();
	}

    /**
     * Carrega os telefones do Imovel na tabela de Telefone
     *
     * @author André Miranda
     * @date 16/09/2014
     */
	private void carregarTelefones(Collection<ClienteFoneAtlzCad> colecaoClienteFone) {
		if (colecaoClienteFone != null) {
			for (ClienteFoneAtlzCad clienteFone : colecaoClienteFone) {
				int idFoneTipo = clienteFone.getFoneTipo().getId();
				String codigoDDD = clienteFone.getCodigoDDD().toString();
				String numeroTelefoneFormatado = clienteFone.getNumeroFone().toString();
				Integer indicadorFonePadrao = clienteFone.getIndicadorFonePadrao();

				criarLinhaTabelaTelefone(idFoneTipo, codigoDDD, numeroTelefoneFormatado, indicadorFonePadrao);
			}
		}
	}

    /**
     * Remove da tabela o Telefone Selecionado
     *
     * @author André Miranda
     * @since 16/09/2014
     */
    private void removerLinhaTabelaTelefone(View v) {
    	Integer id = v.getId();

    	ClienteFoneHelper helper = new ClienteFoneHelper();
    	if(colecaoClienteFoneHelper != null && !colecaoClienteFoneHelper.isEmpty()){
    		for(ClienteFoneHelper aux : colecaoClienteFoneHelper){
    			if(aux.getNumeroTelefone().equals(String.valueOf(id))){
    				helper = aux;
    			}
    		}
    	}

    	if(tableRowsTelefone.containsKey(helper)) {
    		tableRowsTelefone.remove(helper);
    		colecaoClienteFoneHelper.remove(helper);

    		String telefoneId = helper.getCodigoDDD() + helper.getNumeroTelefone();
        	fonesTabela.remove(telefoneId);

    		Utilitarios.showMessage(context, getString(R.string.msg_tel_removido_sucesso), Toast.LENGTH_SHORT, true);
    		populaTabelaTelefone();
    	}
    }

    @SuppressWarnings("deprecation")
	private void populaTabelaTelefone(){
    	//Limpa
    	if(tableLayoutTelefone.getChildCount() > 0){
    		tableLayoutTelefone.removeAllViews();
    	}

    	Set<ClienteFoneHelper> keys = tableRowsTelefone.keySet();

    	TableRow tr = null;

    	for(ClienteFoneHelper key : keys){
    		tr = tableRowsTelefone.get(key);
    		tr.setBackgroundColor((tableLayoutTelefone.getChildCount() % 2 == 0) ?
    				Color.TRANSPARENT : Color.parseColor("#5D5F5F"));
    		tableLayoutTelefone.addView(tr, new TableLayout.LayoutParams(
    				android.view.ViewGroup.LayoutParams.FILL_PARENT,
    				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    	}
    }

    private boolean validaCamposObrigatoriosTelefone(){
    	long idTipoTelefone = spnFoneTipo.getSelectedItemId();
    	String campos = "";

    	if(edtTelefone.getText().toString().length() == 13){
	    	String codigoDDD = edtTelefone.getText().toString().substring(0, 4);
	    	String numTelefone = edtTelefone.getText().toString().substring(4, 13);
	    	String primeiroNum = edtTelefone.getText().toString().substring(4,5);

	    	if(idTipoTelefone == -1){
	    		campos = campos + "Informe Tipo Telefone \n";
	    	}

	    	if(codigoDDD == null || codigoDDD.equals("")){
	    		campos = campos + "Informe Código DDD \n";
	    	}

	    	if(numTelefone == null || numTelefone.equals("")){
	    		campos = campos + "Informe Número Telefone \n";
	    	}else if(primeiroNum.equals("0")){
	    		campos = campos + "Telefone Inválido \n";
	    	}
    	}else{
    		campos = "Telefone Inválido \n";
    	}

    	if(campos.length() > 0){
    		campos = campos.substring(0, campos.length() - 2);
    		Utilitarios.mensagemAlert(context, campos, MensagemTipo.ERRO);
    		return false;
    	}else{
    		return true;
    	}
    }

	private void carregarTelaParaTipoPessoa(int tipoPessoa) {
		try {
			String where = ClienteTipoColuna.INDICADOR_PESSOA + " == ";

			if (tipoPessoa == ConstantesSistema.PESSOA_FISICA) {
				where += ConstantesSistema.PESSOA_FISICA;

				edtDataNascimento.setEnabled(true);
				radioMasculino.setEnabled(true);
				edtRg.setEnabled(true);
				spnUF.setEnabled(true);
				radioFeminino.setEnabled(true);
				spnOrgaoExpedidor.setEnabled(true);
				edtDataEmissaoRg.setEnabled(true);
				edtNomeMae.setEnabled(true);

				mascaraCpfCnpj.setMask("###.###.###-##");
			} else if (tipoPessoa == ConstantesSistema.PESSOA_JURIDICA) {
				where += ConstantesSistema.PESSOA_JURIDICA;

				edtDataNascimento.setText("");
				edtRg.setText("");

				spnOrgaoExpedidor.setSelection(0);
				spnUF.setSelection(0);

				radioMasculino.setChecked(false);
				radioFeminino.setChecked(false);
				rgOpcoesPessoaSexo.clearCheck();

				edtDataNascimento.setEnabled(false);
				radioMasculino.setEnabled(false);
				radioFeminino.setEnabled(false);
				edtRg.setEnabled(false);
				spnOrgaoExpedidor.setEnabled(false);
				spnUF.setEnabled(false);
				edtDataEmissaoRg.setEnabled(false);
				edtNomeMae.setEnabled(false);

				mascaraCpfCnpj.setMask("##.###.###/####-##");
			}
			edtCpfCnpj.setText("");
			edtNomeMae.setText("");
			edtNomeCliente.setText("");
			edtDataEmissaoRg.setText("");

			edtCpfCnpj.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
			opcaoApresentouDocumentacao.clearCheck();
			opcaoUsuarioProprietario.clearCheck();
			opcaoUsuarioResponsavel.clearCheck();
			tableRowsTelefone.clear();
			fonesTabela.clear();
			populaTabelaTelefone();

			List<Object> list = fachada.pesquisarListaObjetoGenerico(
					ClienteTipo.class, where, null, null, ClienteTipoColuna.DESCRICAO);
			spnTipoCliente.setAdapter(Utilitarios.getAdapterSpinner(context, list));
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.TAG_LOGCAT,
					fe.getMessage() + " - " + fe.getCause());
		}
	}

	@Override
	public void onPause(){
		super.onPause();
		try{
			inserirDadosCliente();

			if(!TabsActivity.indicadorExibirMensagemErro) {
				return;
			}

			fachada.validarAbaCliente(clienteAtualizacaoCadastral, false);
		}catch(FachadaException e){
			Utilitarios.mensagemAlert(context, e.getMessage(), MensagemTipo.ERRO);
		}
	}

	private void inserirDadosCliente() {
		//verificar se o cliente é novo
		if(clienteAtualizacaoCadastral.getId() == null){
			clienteAtualizacaoCadastral = new ClienteAtlzCadastral();
			TabsActivity.cliente = clienteAtualizacaoCadastral;
		}

		//imovel
		clienteAtualizacaoCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);

		//Orgão Expedidor Rg
		clienteAtualizacaoCadastral.setOrgaoExpedidorRg(null);
		if(spnOrgaoExpedidor.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO){
			clienteAtualizacaoCadastral.setOrgaoExpedidorRg(
					(OrgaoExpedidorRg) spnOrgaoExpedidor.getSelectedItem());
		}

		// UF
		clienteAtualizacaoCadastral.setUnidadeFederacao(null);
		if(spnUF.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO){
			clienteAtualizacaoCadastral.setUnidadeFederacao((UnidadeFederacao) spnUF.getSelectedItem());
		}

		//CPF/CPNJ
		if(edtCpfCnpj.getText() != null){
			String cpfCnpj = edtCpfCnpj.getText().toString().replace(".", "")
					.replace("-", "").replace("/", "");
			clienteAtualizacaoCadastral.setNumeroCPFCNPPJ(cpfCnpj);
		}

		//Nome Cliente
		if(edtNomeCliente.getText() != null) {
			clienteAtualizacaoCadastral.setNomeCliente(edtNomeCliente.getText().toString());
		}

		//Nome da Mãe
		if(edtNomeMae.getText() != null){
			clienteAtualizacaoCadastral.setNomeMae(edtNomeMae.getText().toString());
		}

		// Data de Nascimento
		clienteAtualizacaoCadastral.setDataNascimento(null);

		if(edtDataNascimento.getText() != null) {
			String dtNascimento = edtDataNascimento.getText().toString();

			if(!dtNascimento.isEmpty()){
				clienteAtualizacaoCadastral.setDataNascimento(ConstantesSistema.DATA_INVALIDA);
				if(Utilitarios.validarData(dtNascimento)) {
					clienteAtualizacaoCadastral.setDataNascimento(
							Utilitarios.converteStringParaDate(dtNascimento));
				}
			}
		}

		// RG
		if(edtRg.getText() != null){
			clienteAtualizacaoCadastral.setNumeroRG(edtRg.getText().toString());
		} else {
			clienteAtualizacaoCadastral.setNumeroRG(null);
		}

		// Data Emissao RG
		clienteAtualizacaoCadastral.setDataEmissaoRg(null);

		if(edtDataEmissaoRg.getText() != null){
			String dtEmissaoRg = edtDataEmissaoRg.getText().toString();

			if(!dtEmissaoRg.isEmpty()){
				clienteAtualizacaoCadastral.setDataEmissaoRg(ConstantesSistema.DATA_INVALIDA);
				if(Utilitarios.validarData(dtEmissaoRg)) {
					clienteAtualizacaoCadastral.setDataEmissaoRg(
							Utilitarios.converteStringParaDate(dtEmissaoRg));
				}
			}
		}

		//Cliente Tipo
		ClienteTipo clienteTipo = new ClienteTipo();
		if(spnTipoCliente.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
			clienteTipo = (ClienteTipo) spnTipoCliente.getSelectedItem();
		}

		if(radioPessoaFisica.isChecked()){
			clienteTipo.setIndicadorPessoaJuridica(ConstantesSistema.PESSOA_FISICA);

			if((clienteTipo.getId() != null && !clienteTipo.getId().equals(Integer.valueOf(0))
					&& !clienteTipo.getId().equals(ConstantesSistema.ITEM_INVALIDO)) ||
					clienteTipo.getIndicadorPessoa() != null){
				clienteAtualizacaoCadastral.setClienteTipo(clienteTipo);
			}

			PessoaSexo pessoaSexo = new PessoaSexo();
			if(radioMasculino.isChecked()){
				pessoaSexo.setId(PessoaSexo.MASCULINO);
				clienteAtualizacaoCadastral.setPessoaSexo(pessoaSexo);
			}else if(radioFeminino.isChecked()){
				pessoaSexo.setId(PessoaSexo.FEMININO);
				clienteAtualizacaoCadastral.setPessoaSexo(pessoaSexo);
			} else {
				clienteAtualizacaoCadastral.setPessoaSexo(null);
			}

		}else{
			clienteTipo.setIndicadorPessoaJuridica(ConstantesSistema.PESSOA_JURIDICA);
			clienteAtualizacaoCadastral.setClienteTipo(clienteTipo);
			clienteAtualizacaoCadastral.setDataNascimento(null);
			clienteAtualizacaoCadastral.setPessoaSexo(null);
			clienteAtualizacaoCadastral.setNumeroRG(null);
			clienteAtualizacaoCadastral.setOrgaoExpedidorRg(null);
			clienteAtualizacaoCadastral.setUnidadeFederacao(null);
			clienteAtualizacaoCadastral.setDataEmissaoRg(null);
		}

		//Fone
		if(colecaoClienteFoneHelper != null){
			ArrayList<ClienteFoneAtlzCad> colecaoClienteFone = new ArrayList<ClienteFoneAtlzCad>();

			if(!colecaoClienteFoneHelper.isEmpty()) {
				ClienteFoneAtlzCad clienteFone = null;
				FoneTipo foneTipo = null;

				for(ClienteFoneHelper helper : colecaoClienteFoneHelper){
					foneTipo = new FoneTipo();

					clienteFone = new ClienteFoneAtlzCad();
					clienteFone.setCodigoDDD(Integer.parseInt(helper.getCodigoDDD()));
					clienteFone.setNumeroFone(Integer.parseInt(helper.getNumeroTelefone()));
					clienteFone.setClienteAtlzCadastral(clienteAtualizacaoCadastral);

					if (helper.getIdFoneTipo() == FoneTipo.CELULAR) {
						foneTipo.setId(FoneTipo.CELULAR);
					} else if (helper.getIdFoneTipo() == FoneTipo.COMERCIAL) {
						foneTipo.setId(FoneTipo.COMERCIAL);
					} else if (helper.getIdFoneTipo() == FoneTipo.FAX) {
						foneTipo.setId(FoneTipo.FAX);
					} else if (helper.getIdFoneTipo() == FoneTipo.RESIDENCIAL) {
						foneTipo.setId(FoneTipo.RESIDENCIAL);
					}

					clienteFone.setFoneTipo(foneTipo);
					clienteFone.setIndicadorFonePadrao(helper.getIndicadorFonePadrao());

					colecaoClienteFone.add(clienteFone);
				}
			}

			TabsActivity.colecaoClienteFone = colecaoClienteFone;
		}

		//Usuario Proprietario
		if(simUsuarioProprietario.isChecked()){
			clienteAtualizacaoCadastral.setIndicadorProprietario(ConstantesSistema.SIM);
		}

		if(naoUsuarioProprietario.isChecked()){
			clienteAtualizacaoCadastral.setIndicadorProprietario(ConstantesSistema.NAO);
		}

		//Usuario Responsável
		if(simUsuarioResponsavel.isChecked()){
			clienteAtualizacaoCadastral.setIndicadorResponsavel(ConstantesSistema.SIM);
		}

		if(naoUsuarioResponsavel.isChecked()){
			clienteAtualizacaoCadastral.setIndicadorResponsavel(ConstantesSistema.NAO);
		}

		//Apresentou Documento
		if(simApresentouDocumentacao.isChecked()){
			clienteAtualizacaoCadastral.setIndicadorDocumento(ConstantesSistema.SIM);
		}

		if(naoApresentouDocumentacao.isChecked()){
			clienteAtualizacaoCadastral.setIndicadorDocumento(ConstantesSistema.NAO);
		}
	}
}
