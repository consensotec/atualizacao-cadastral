package com.br.gsanac.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.br.gsanac.R;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.FachadaException;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.Bairro;
import com.br.gsanac.pojos.Bairro.BairroColuna;
import com.br.gsanac.pojos.Cep;
import com.br.gsanac.pojos.Cep.CepColuna;
import com.br.gsanac.pojos.EnderecoReferencia;
import com.br.gsanac.pojos.EnderecoReferencia.EnderecoReferenciaColuna;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.pojos.Logradouro;
import com.br.gsanac.pojos.Logradouro.LogradouroColuna;
import com.br.gsanac.pojos.LogradouroBairro;
import com.br.gsanac.pojos.LogradouroBairro.LogradouroBairroColuna;
import com.br.gsanac.pojos.LogradouroCep;
import com.br.gsanac.pojos.LogradouroCep.LogradouroCepColuna;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * [UC 1409] - Manter Dados da Aba Endereco do Tablet
 *
 * @author Bruno Sá Barreto
 * @date 05/09/2014
 *
 */
public class EnderecoAbaActivity extends Fragment {
	private Fachada fachada;
	private Context context;
	private View view;
	private Intent intent;

	private ImovelAtlzCadastral imovelAtlzCadastral;

	// elementos da view
	AutoCompleteTextView autoComplete; // logradouro
	Spinner spnReferenciaNum;
	EditText edtReferenciaNum;
	EditText edtComplemento;
	Spinner spnBairro;
	Spinner spnCep;
	Button btnInserirLogra;
	EditText edtEndereco;

	// utilizado para escapar da validação no onPause()
	private boolean flagTelaInserirLogradouro = false;

	private List<Object> logradouros;
	private Logradouro logradouro;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = inflater.inflate(R.layout.endereco_aba, container, false);

		/** Exibe os dados do Imóvel ***/
		imovelAtlzCadastral = TabsActivity.imovel;

		context = view.getContext();
		fachada = Fachada.getInstancia(context);

		// carregar elementos na pagina

		/** Logradouro **/
		logradouros = fachada
				.pesquisarListaTodosObjetosGenerico(Logradouro.class);
		ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(context,
				android.R.layout.simple_dropdown_item_1line, logradouros);
		autoComplete = (AutoCompleteTextView) view
				.findViewById(R.id.autoComplete); // logradouro
		autoComplete.setAdapter(adapter);

		// caso o elemento seja clicado e esteja vazio
		autoComplete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AutoCompleteTextView autoCom = (AutoCompleteTextView) v;
				if (autoCom.getText() == null
						|| autoCom.getText().toString().equals("")) {
					autoCom.showDropDown();
				}
			}
		});

		// caso um logradouro seja selecionado no auto complete, carregar os
		// campos de cep e bairro
		autoComplete.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				logradouro = (Logradouro) parent.getItemAtPosition(position);
				carregarBairros(logradouro.getId());
				carregarCEPS(logradouro.getId());
				verificarCepDesconhecido(logradouro.getCepDesconhecido());
				
			}
		});

		/** Chama a tela de Inserir Logradouro **/
		btnInserirLogra = (Button) view.findViewById(R.id.btnInserirLogra);

		btnInserirLogra.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flagTelaInserirLogradouro = true;
				intent = new Intent(context, LogradouroInserirActivity.class);
				startActivityForResult(intent,
						ConstantesSistema.ENDERECO_ABA_REQUEST_CODE);
			}

		});

		/** Referencia/Numero **/
		List<Object> enderecoReferencias = fachada
				.pesquisarListaObjetoGenerico(EnderecoReferencia.class, null, null, null, EnderecoReferenciaColuna.DESCRICAO);
		spnReferenciaNum = (Spinner) view.findViewById(R.id.spnReferenciaNum);
		spnReferenciaNum.setAdapter(Utilitarios.getAdapterSpinner(context,
				enderecoReferencias));

		edtReferenciaNum = (EditText) view.findViewById(R.id.edtReferenciaNum);
		edtReferenciaNum.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(5)});

		edtComplemento = (EditText) view.findViewById(R.id.edtComplemento);
		edtComplemento.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		edtComplemento.setFilters(new InputFilter[]{new InputFilter.AllCaps(),
				new InputFilter.LengthFilter(25),
					Utilitarios.filterReplaceCaracteresEspeciaisEEspaco()});

		/** Bairro **/
		spnBairro = (Spinner) view.findViewById(R.id.spnBairro);
//		bairros = fachada.pesquisarListaTodosObjetosGenerico(Bairro.class);
//		spnBairro.setAdapter(Utilitarios.getAdapterSpinner(context, bairros));

		/** CEP **/
		spnCep = (Spinner) view.findViewById(R.id.spnCep);
//		ceps = fachada.pesquisarListaTodosObjetosGenerico(Cep.class);
//		spnCep.setAdapter(Utilitarios.getAdapterSpinner(context, ceps));

		carregarDadosDoImovelNaAba();

		return view;
	}

	private void carregarDadosDoImovelNaAba() {
		Logradouro logradouro = null;

		if (imovelAtlzCadastral.getLogradouro() != null) {
			String[] parametros = { String.valueOf(imovelAtlzCadastral.getLogradouro().getId()) };
			logradouro = fachada.pesquisarObjetoGenerico(Logradouro.class, LogradouroColuna.ID + "=?",
					parametros, null, null);
		}

		if (logradouro != null) { 
			// logradouro
			autoComplete.setText(logradouro.toString());

			// bairros
			carregarBairros(imovelAtlzCadastral.getLogradouro().getId());

			if (imovelAtlzCadastral.getIdBairro() != null) {
				Utilitarios.selecionarItemSpinner(spnBairro, imovelAtlzCadastral.getIdBairro());
			}

			// ceps
			boolean isDesconhecido = verificarCepDesconhecido(logradouro.getCepDesconhecido());
			carregarCEPS(imovelAtlzCadastral.getLogradouro().getId());
			if (!isDesconhecido) {

				String[] parametros = { String.valueOf(imovelAtlzCadastral.getCodigoCep()) };
				Cep cep = fachada.pesquisarObjetoGenerico(Cep.class, CepColuna.CODIGO + "=?", parametros, null, null);

				if (cep != null) {
					Utilitarios.selecionarItemSpinner(spnCep, cep.getId());
				}
			}
			

			this.logradouro = logradouro;
		}

		if (imovelAtlzCadastral.getEnderecoReferencia() != null) {
			Utilitarios.selecionarItemSpinner(spnReferenciaNum, imovelAtlzCadastral.getEnderecoReferencia().getId());
		}

		edtReferenciaNum.setText(imovelAtlzCadastral.getNumeroImovel());
		edtComplemento.setText(imovelAtlzCadastral.getComplementoEndereco());
	}

	private void carregarDadosDaAbaNoImovel() {
    	String selection;
		String[] selectionArgs;
		Fachada fachada = Fachada.getInstancia(context);

		// logradouro
		if(autoComplete.getText().length()>0){
			if (logradouro != null) {
				imovelAtlzCadastral.setLogradouro(logradouro);
				imovelAtlzCadastral.setCodigoUnicoLogradouro(logradouro
						.getCodigoUnico());
			}
		}else{
			imovelAtlzCadastral.setLogradouro(new Logradouro());
			imovelAtlzCadastral.setCodigoUnicoLogradouro(null);
		}
		// bairro
		if (!ConstantesSistema.ITEM_INVALIDO.equals(spnBairro.getSelectedItem())
				&& spnBairro.getSelectedItem() != null) {
			Bairro tmpBairro = (Bairro) spnBairro.getSelectedItem();
			imovelAtlzCadastral.setIdBairro(tmpBairro.getId());

			if (logradouro != null) {
		    	selection = LogradouroBairroColuna.LOGRADOURO + " = ? AND " + LogradouroBairroColuna.BAIRRO + " = ?";
				selectionArgs = new String[]{ String.valueOf(logradouro.getId()), String.valueOf(tmpBairro.getId()) };

				LogradouroBairro logradouroBairro =
						fachada.pesquisarObjetoGenerico(LogradouroBairro.class, selection, selectionArgs, null, null);

				if(logradouroBairro != null) {
					imovelAtlzCadastral.setLogradouroBairroId(logradouroBairro.getId());
				}
			}
		} else {
			imovelAtlzCadastral.setIdBairro(null);
			imovelAtlzCadastral.setLogradouroBairroId(null);
		}
		
		if (!ConstantesSistema.ITEM_INVALIDO.equals(spnCep.getSelectedItem())
				&& spnCep.getSelectedItem() != null) {
			Cep tmpCep = (Cep) spnCep.getSelectedItem();
			imovelAtlzCadastral.setCodigoCep(tmpCep.getCodigo());

			if (logradouro != null) {
		    	selection = LogradouroCepColuna.LOGRADOURO + " = ? AND " + LogradouroCepColuna.CEP + " = ?";
				selectionArgs = new String[]{ String.valueOf(logradouro.getId()), String.valueOf(tmpCep.getId()) };

				LogradouroCep logradouroCep =
						fachada.pesquisarObjetoGenerico(LogradouroCep.class, selection, selectionArgs, null, null);

				if(logradouroCep != null) {
					imovelAtlzCadastral.setLogradouroCEPId(logradouroCep.getId());
				}
			}	
				
		} else if(logradouro != null
					&& ConstantesSistema.SIM.equals(logradouro.getCepDesconhecido())){
			spnCep.setSelection(1);
			Cep tmpCep = (Cep) spnCep.getSelectedItem();
			spnCep.setSelection(0);
			
			imovelAtlzCadastral.setCodigoCep(tmpCep.getCodigo());
			selection = LogradouroCepColuna.LOGRADOURO + " = ? AND " + LogradouroCepColuna.CEP + " = ?";
			selectionArgs = new String[]{ String.valueOf(logradouro.getId()), String.valueOf(tmpCep.getId()) };
			LogradouroCep logradouroCep = fachada.pesquisarObjetoGenerico(LogradouroCep.class, selection, selectionArgs, null, null);
			if(logradouroCep != null) {
				imovelAtlzCadastral.setLogradouroCEPId(logradouroCep.getId());
			}
			
		} else {
			imovelAtlzCadastral.setCodigoCep(null);
			imovelAtlzCadastral.setLogradouroCEPId(null);
		}
			
		// tiporeferencia
		if (spnReferenciaNum.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
			EnderecoReferencia endRef = (EnderecoReferencia) spnReferenciaNum
					.getSelectedItem();
			imovelAtlzCadastral.setEnderecoReferencia(endRef);
		} else {
			imovelAtlzCadastral.setEnderecoReferencia(null);
		}
		
		// numero
		imovelAtlzCadastral.setNumeroImovel(edtReferenciaNum.getText()
				.toString());
		imovelAtlzCadastral.setComplementoEndereco(edtComplemento.getText()
				.toString());
	}

	@Override
	public void onPause() {
		super.onPause();

		if (flagTelaInserirLogradouro) {
			flagTelaInserirLogradouro = false;
			return;
		}

		carregarDadosDaAbaNoImovel();

		if(!TabsActivity.indicadorExibirMensagemErro) {
			return;
		}

		try {
			fachada.validarDadosAbaEndereco(imovelAtlzCadastral);
		} catch (FachadaException e) {
			Utilitarios.mensagemAlert(context, e.getMessage(), MensagemTipo.ERRO);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == Activity.RESULT_OK) {

			Logradouro logradouroCadastrado = (Logradouro) intent
					.getSerializableExtra("logradouroCadastrado");

			if (logradouroCadastrado != null) {
				// logradouro

				String result = "";

		    	if(logradouroCadastrado.getLogradouroTipo()!=null){
		    		imovelAtlzCadastral.getLogradouro().setLogradouroTipo(logradouroCadastrado.getLogradouroTipo());
		    		if(imovelAtlzCadastral.getLogradouro().getLogradouroTipo()!=null){
		    			result+=imovelAtlzCadastral.getLogradouro().getLogradouroTipo().getDescricao()+" ";
		    		}
		    	}

		    	if(logradouroCadastrado.getLogradouroTitulo()!=null){
		    		imovelAtlzCadastral.getLogradouro().setLogradouroTitulo(logradouroCadastrado.getLogradouroTitulo());
		    		if(imovelAtlzCadastral.getLogradouro().getLogradouroTitulo()!=null){
		    			result+=imovelAtlzCadastral.getLogradouro().getLogradouroTitulo().getDescricao()+" ";
		    		}
		    	}

		    	/** Logradouro **/
				logradouros.add(logradouroCadastrado);
				ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(context,
						android.R.layout.simple_dropdown_item_1line, logradouros);
				autoComplete = (AutoCompleteTextView) view
						.findViewById(R.id.autoComplete); // logradouro
				autoComplete.setAdapter(adapter);

				autoComplete.setText(result + logradouroCadastrado.getNomeLogradouro());

				// bairros
				carregarBairros(logradouroCadastrado.getId());
				if (imovelAtlzCadastral.getIdBairro() != null) {
					Utilitarios.selecionarItemSpinner(spnBairro,
							imovelAtlzCadastral.getIdBairro());
				}

				// ceps
				boolean isDesconhecido = verificarCepDesconhecido(logradouroCadastrado.getCepDesconhecido());
				carregarCEPS(logradouroCadastrado.getId());
				if (!isDesconhecido) {
					String[] parametros2 = { String.valueOf(imovelAtlzCadastral
							.getCodigoCep()) };
					
					Cep tmpCep = fachada.pesquisarObjetoGenerico(Cep.class,
							CepColuna.CODIGO + "=?", parametros2, null, null);
					
					if (tmpCep != null) {
						Utilitarios.selecionarItemSpinner(spnCep, tmpCep.getId());
					}
				}

				logradouro = logradouroCadastrado;
			}

		}
	}

	private void carregarBairros(long logradoiroId) {
		String[] parametros = { "" + logradoiroId };
		List<Object> logradouroBairros = fachada.pesquisarListaObjetoGenerico(
				LogradouroBairro.class, LogradouroBairroColuna.LOGRADOURO
						+ "=?", parametros, null, null);
		List<Object> bairrosAssociados = new ArrayList<Object>();
		for (int i = 0; i < logradouroBairros.size(); i++) {
			LogradouroBairro logradouroBairro = (LogradouroBairro) logradouroBairros
					.get(i);
			String[] parametros2 = { String.valueOf(logradouroBairro
					.getBairro().getId()) };
			Bairro tmpBairro = fachada.pesquisarObjetoGenerico(Bairro.class,
					BairroColuna.ID + "=?", parametros2, null, null);
			bairrosAssociados.add(tmpBairro);
		}

		spnBairro = (Spinner) view.findViewById(R.id.spnBairro);
		spnBairro.setAdapter(Utilitarios.getAdapterSpinner(context,
				bairrosAssociados));
	}

	private void carregarCEPS(long logradoiroId) {
		String[] parametros = { "" + logradoiroId };
		List<LogradouroCep> logradouroCeps = fachada.pesquisarListaObjetoGenerico(
				LogradouroCep.class, LogradouroCepColuna.LOGRADOURO + "=?",
				parametros, null, null);

		List<Object> cepsAssociados = new ArrayList<Object>();

		for (LogradouroCep logradouroCep : logradouroCeps) {
			String[] parametros2 = { String.valueOf(logradouroCep.getCep().getId()) };
			Cep cep = fachada.pesquisarObjetoGenerico(Cep.class, CepColuna.ID + "=?", parametros2, null, null);
			cepsAssociados.add(cep);
		}

		spnCep = (Spinner) view.findViewById(R.id.spnCep);
		spnCep.setAdapter(Utilitarios.getAdapterSpinner(context, cepsAssociados));
		spnCep.setSelection(0);
	}

	/**
	 * [UC1409] - Manter Dados da Aba Endereço do Tablet
	 *            
	 * [IT0002] - 1. Caso o logradouro selecionado seja de CEP Desconhecido
	 * 			  (LOGR_ICCEPDESC = 1), o sistema desabilita o campo de CEP;
	          
	 * Deixa inativo a opcao Cep e o listview 
	 *
	 * @author Marcílio de Queiroz
	 * @date 28/01/2015
	 */
	public boolean verificarCepDesconhecido(Integer cepDesconhecido) {
		boolean isDesconhecido = ConstantesSistema.SIM.equals(cepDesconhecido); 

		Spinner tlSpinner = (Spinner) view.findViewById(R.id.spnCep);
		tlSpinner.setEnabled(!isDesconhecido);
		
		if(isDesconhecido){
			tlSpinner.setSelection(0);
		}
		
		return isDesconhecido;
	}
}
