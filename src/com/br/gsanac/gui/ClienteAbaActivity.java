package com.br.gsanac.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.ClienteTipo;
import com.br.gsanac.entidades.ClienteTipo.ClienteTipoColunas;
import com.br.gsanac.entidades.FoneTipo;
import com.br.gsanac.entidades.FoneTipo.FoneTipoColunas;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.OrgaoExpedidorRg;
import com.br.gsanac.entidades.OrgaoExpedidorRg.OrgaoExpedidorRgColunas;
import com.br.gsanac.entidades.PessoaSexo;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.entidades.UnidadeFederacao;
import com.br.gsanac.entidades.UnidadeFederacao.UnidadeFederacaoColunas;
import com.br.gsanac.entidades.bean.ClienteFoneHelper;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Mascara;
import com.br.gsanac.util.Util;

/**
 * [UC 1411] - Manter Dados Aba Cliente Tablet
 * 
 * @author Davi Menezes
 * @date 27/12/2012
 *
 */
public class ClienteAbaActivity extends BaseTabsActivity implements OnLongClickListener {

	private static Fachada fachada = Fachada.getInstance();
	private Cursor cursor;
	
	private Spinner spnTipoCliente;
	
	private RadioGroup	radioGroupPessoaTipo;
	private RadioButton radioPessoaFisica;
	private RadioButton radioPessoaJuridica;
	
	private EditText edtCpfCnpj;
	private EditText edtNomeCliente;
	private EditText edtDataNascimento;
	
	private RadioGroup rgOpcoesPessoaSexo;
	private RadioButton radioMasculino;
	private RadioButton radioFeminino;
	
	private EditText edtRg;
	
	private Spinner spnOrgaoExpedidor;
	private Spinner spnUF;
	
	private EditText edtDataEmissaoRg;
	
	private Spinner  spnFoneTipo;
	private EditText edtTelefone;
	private Button	 btAdicionarTelefone;
	
	/*** Armazena as linhas do TableLayout Telefone ***/
    private Map<ClienteFoneHelper, TableRow> tableRowsTelefone;
    
    /*** Tabela com os Telefones selecionados ***/
    private TableLayout tableLayoutTelefone;
	
	private ClienteAtlzCadastral clienteAtualizacaoCadastral;
	private ImovelAtlzCadastral imovelAtlzCadastral;
	
	private Collection<ClienteFoneHelper> colecaoClienteFoneHelper = new ArrayList<ClienteFoneHelper>();
	
	/** Called when the activity is first created. */
    int mStackLevel = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.cliente_aba);
    	
    	try{
    		SistemaParametros sistemaParametros = new SistemaParametros();
    		sistemaParametros = (SistemaParametros) fachada.pesquisar(sistemaParametros, null, null);
    		
    		//Tipo Pessoa
    		radioGroupPessoaTipo = (RadioGroup) findViewById(R.id.radioGroupPessoaTipo);
	        radioPessoaFisica = (RadioButton) findViewById(R.id.radioPessoaFisica);
    		radioPessoaJuridica = (RadioButton) findViewById(R.id.radioPessoaJuridica);
    		radioPessoaFisica.setChecked(true);
    		
    		radioGroupPessoaTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {	
					if(checkedId == R.id.radioPessoaFisica){
						carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);	
					}else if(checkedId == R.id.radioPessoaJuridica){
						carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_JURIDICA);
					}
				}
			});
    		
    		//Tipo Cliente
    		spnTipoCliente = (Spinner) findViewById(R.id.spnTipoCliente);
    		
    		//CPF-CNPJ
    		edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
    		      	
        	edtCpfCnpj.addTextChangedListener(new TextWatcher() {
	   			 boolean isUpdating;
	   			 String old = "";
	   			 String mask;
	   			 
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					 String str = Mascara.unmask(s.toString());
					 String mascara = "";
					 if (isUpdating) {
					 old = str;
					 isUpdating = false;
					 return;
					 }
					 int i = 0;
					 
					 if(radioGroupPessoaTipo.getCheckedRadioButtonId() == R.id.radioPessoaJuridica){
						 mask = "##.###.###/####-##";
					 }else{
						 mask = "###.###.###-##";
					 }
					 
					 for (char m : mask.toCharArray()) {
					 if (m != '#' && str.length() >= old.length()) {
					 mascara += m;
					 continue;
					 }
					 try {
					 mascara += str.charAt(i);
					 } catch (Exception e) {
					 break;
					 }
					 i++;
					 }
					 isUpdating = true;
					 edtCpfCnpj.setText(mascara);
					 edtCpfCnpj.setSelection(mascara.length());					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});
    		
            //Nome Cliente
            edtNomeCliente = (EditText) findViewById(R.id.edtNomeCliente);
            edtNomeCliente.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(100), Util.filterReplaceCaracteresEspeciais()});
            
            //Data de Nascimento
            edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
//            edtDataNascimento.addTextChangedListener(Mascara.insert("", edtDataNascimento));
            
            edtDataNascimento.addTextChangedListener(new TextWatcher() {
	   			 boolean isUpdating;
	   			 String old = "";
	   			 String mask;
	   			 
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					 String str = Mascara.unmask(s.toString());
					 String mascara = "";
					 if (isUpdating) {
					 old = str;
					 isUpdating = false;
					 return;
					 }
					 int i = 0;
					 
				     mask = "##/##/####";
					 
					 for (char m : mask.toCharArray()) {
					 if (m != '#' && str.length() >= old.length()) {
					 mascara += m;
					 continue;
					 }
					 try {
					 mascara += str.charAt(i);
					 } catch (Exception e) {
					 break;
					 }
					 i++;
					 }
					 isUpdating = true;

			         edtDataNascimento.setText(mascara);
			         edtDataNascimento.setSelection(mascara.length());					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});
            
            
    		//Sexo
            rgOpcoesPessoaSexo = (RadioGroup) findViewById(R.id.rgOpcoesPessoaSexo);
    		radioMasculino = (RadioButton) findViewById(R.id.radioMasculino);
    		radioFeminino  = (RadioButton) findViewById(R.id.radioFeminino);
    		
    		//RG
    		edtRg = (EditText) findViewById(R.id.edtRg);
    		
    		//Órgão Expedidor
    		spnOrgaoExpedidor = (Spinner) findViewById(R.id.spnOrgaoExpedidor);
    		cursor = fachada.getCursor(OrgaoExpedidorRg.class, 
    								   OrgaoExpedidorRgColunas.ID, 
    								   OrgaoExpedidorRgColunas.DESCRICAOABREV, 
    								   new OrgaoExpedidorRg().getNomeTabela());
    		
    		spnOrgaoExpedidor.setAdapter(Util.getAdapter(cursor));
    		
    		//UF
    		spnUF = (Spinner) findViewById(R.id.spnUF);
    		cursor = fachada.getCursor(UnidadeFederacao.class, 
    								   UnidadeFederacaoColunas.ID, 
    								   UnidadeFederacaoColunas.DESCRICAO, 
    								   new UnidadeFederacao().getNomeTabela());
    		
    		spnUF.setAdapter(Util.getAdapter(cursor));
    		
			//Data Emissao
        	edtDataEmissaoRg = (EditText) findViewById(R.id.edtDataEmissaoRg);
//        	edtDataEmissaoRg.addTextChangedListener(Mascara.insert("##/##/####", edtDataEmissaoRg));
        	edtDataEmissaoRg.addTextChangedListener(new TextWatcher() {
	   			 boolean isUpdating;
	   			 String old = "";
	   			 String mask;
	   			 
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					 String str = Mascara.unmask(s.toString());
					 String mascara = "";
					 if (isUpdating) {
					 old = str;
					 isUpdating = false;
					 return;
					 }
					 int i = 0;
					 
				     mask = "##/##/####";
					 
					 for (char m : mask.toCharArray()) {
					 if (m != '#' && str.length() >= old.length()) {
					 mascara += m;
					 continue;
					 }
					 try {
					 mascara += str.charAt(i);
					 } catch (Exception e) {
					 break;
					 }
					 i++;
					 }
					 isUpdating = true;

					 edtDataEmissaoRg.setText(mascara);
					 edtDataEmissaoRg.setSelection(mascara.length());					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});
        	
        	
        	
        	
    		//Tipo de Telefone
    		spnFoneTipo = (Spinner) findViewById(R.id.spnFoneTipo);
    		cursor = fachada.getCursor(FoneTipo.class, 
	    			   FoneTipoColunas.ID, 
	    			   FoneTipoColunas.DESCRICAO, 
	    			   new FoneTipo().getNomeTabela());

    		spnFoneTipo.setAdapter(Util.getAdapter(cursor));
    		    		
    		//Telefone
    		edtTelefone = (EditText) findViewById(R.id.edtTelefone);   		
    		edtTelefone.addTextChangedListener(Mascara.insert("(##)####-####", edtTelefone));
    		
    		//Botao Adicionar Telefone
    		btAdicionarTelefone = (Button) findViewById(R.id.btAdicionarTelefone);
    		
    		btAdicionarTelefone.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(validaCamposObrigatoriosTelefone()){
						inserirLinhaTabelaTelefone();
					}
				}
			});
    		
    		// Tabela Telefone
    		tableLayoutTelefone = (TableLayout) findViewById(R.id.tableLayoutTelefones);
    		tableLayoutTelefone.setVisibility(View.VISIBLE);
    		
    		tableRowsTelefone = new HashMap<ClienteFoneHelper, TableRow>();
    		
    		/*** Carrega dados do imovel ****/
    		imovelAtlzCadastral = TabsActivity.imovel;
    		if(TabsActivity.cliente == null){
    			clienteAtualizacaoCadastral = new ClienteAtlzCadastral();
    			
    			if(radioGroupPessoaTipo.getCheckedRadioButtonId() == R.id.radioPessoaFisica){
					carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);	
				}else if(radioGroupPessoaTipo.getCheckedRadioButtonId() == R.id.radioPessoaJuridica){
					carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_JURIDICA);
				}
    		}else{
    			clienteAtualizacaoCadastral = TabsActivity.cliente;
    		}

            if(clienteAtualizacaoCadastral.getClienteTipo() != null && clienteAtualizacaoCadastral.getClienteTipo().getId() != null){
		   		
		   		//Pesquisar Cliente Tipo
		   		String selection = ClienteTipoColunas.ID + "=?";
		   		String[] selectionArgs = new String[] { String.valueOf(clienteAtualizacaoCadastral.getClienteTipo().getId()) };
	            
	            ClienteTipo clienteTipo = new ClienteTipo();
	            clienteTipo = (ClienteTipo) Fachada.getInstance().pesquisar(clienteTipo, selection, selectionArgs);
            	
            	if(clienteTipo.getIndicadorPessoa().equals(ConstantesSistema.PESSOA_FISICA)){
            		radioPessoaFisica.setChecked(true);
            		radioPessoaJuridica.setChecked(false);
            		
            		if(clienteAtualizacaoCadastral.getPessoaSexo() != null){
            			if(clienteAtualizacaoCadastral.getPessoaSexo().getId().equals(PessoaSexo.MASCULINO)){
            				radioMasculino.setChecked(true);
            				radioFeminino.setChecked(false);
            			}else{
            				radioMasculino.setChecked(false);
            				radioFeminino.setChecked(true);
            			}
            		}
            		
            		this.carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);
            		
            	
            	}else{
            		radioPessoaFisica.setChecked(false);
            		radioPessoaJuridica.setChecked(true);
            		radioMasculino.setChecked(false);
    				radioFeminino.setChecked(false);
    				
    				this.carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_JURIDICA);	
    				
    			}
            	
	            //Carrega na combobox o valor do Cliente Tipo
	            Util.selecionarItemCombo(spnTipoCliente, clienteAtualizacaoCadastral.getClienteTipo().getId());
            }else {
        		radioPessoaFisica.setChecked(true);
        		radioPessoaJuridica.setChecked(false);
            	this.carregarTelaParaTipoPessoa(ConstantesSistema.PESSOA_FISICA);
//            	Util.selecionarItemCombo(spnTipoCliente, ConstantesSistema.ITEM_INVALIDO);
            	
        		if(clienteAtualizacaoCadastral.getPessoaSexo() != null){
        			if(clienteAtualizacaoCadastral.getPessoaSexo().getId().equals(PessoaSexo.MASCULINO)){
        				radioMasculino.setChecked(true);
        				radioFeminino.setChecked(false);
        			}else{
        				radioMasculino.setChecked(false);
        				radioFeminino.setChecked(true);
        			}
        		}
            }
            
            if(clienteAtualizacaoCadastral.getNumeroCPFCNPPJ() != null && !clienteAtualizacaoCadastral.getNumeroCPFCNPPJ().equals("")){
            	edtCpfCnpj.setText(clienteAtualizacaoCadastral.getNumeroCPFCNPPJ());
    		}
            
	   		//Nome
            edtNomeCliente.setText(clienteAtualizacaoCadastral.getNomeCliente());
            
            //Data Nascimento
            if(clienteAtualizacaoCadastral.getDataNascimento() != null){
	            edtDataNascimento.setText(Util.convertDateToString(clienteAtualizacaoCadastral.getDataNascimento()));
	        }
            
            //RG
            if(clienteAtualizacaoCadastral.getNumeroRG() != null && !clienteAtualizacaoCadastral.getNumeroRG().equals("")){           	
            	edtRg.setText(clienteAtualizacaoCadastral.getNumeroRG());
            }	
            
        	//Carrega na combobox o valor do órgão expedidor RG
		   	if (clienteAtualizacaoCadastral.getOrgaoExpedidorRg() != null && !clienteAtualizacaoCadastral.getOrgaoExpedidorRg().getId().equals(0)) {
		   		Util.selecionarItemCombo(spnOrgaoExpedidor, clienteAtualizacaoCadastral.getOrgaoExpedidorRg().getId());
		   	}
		   	
		   	//Carrega na combobox o valor da UF do RG
		   	if (clienteAtualizacaoCadastral.getUnidadeFederacao() != null && !clienteAtualizacaoCadastral.getUnidadeFederacao().getId().equals(0)){
		   		Util.selecionarItemCombo(spnUF, clienteAtualizacaoCadastral.getUnidadeFederacao().getId());
		   	}
		   	
		   	if(clienteAtualizacaoCadastral.getDataEmissaoRg() != null){
		   		edtDataEmissaoRg.setText(Util.convertDateToString(clienteAtualizacaoCadastral.getDataEmissaoRg()));
		   	}
            
            Collection<ClienteFoneAtlzCad> colecaoClienteFone = TabsActivity.colecaoClienteFone;
            this.carregarTelefones(colecaoClienteFone);
	            
	        
    		
    	}catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
    }
    
    private void carregarTelaParaTipoPessoa(int tipoPessoa){
		
		try {
			String where = null;			
        	InputFilter[] filterArray = new InputFilter[1];
        				
	    	if(tipoPessoa == ConstantesSistema.PESSOA_FISICA){
	        	where = ClienteTipoColunas.INDICADOR_PESSOA +" != " + ConstantesSistema.PESSOA_JURIDICA; 
	        	filterArray[0] = new InputFilter.LengthFilter(14);	
	        	
	        	edtDataNascimento.setEnabled(true);
	        	radioMasculino.setEnabled(true);
	        	radioFeminino.setEnabled(true);
	        	edtRg.setEnabled(true);
	        	spnOrgaoExpedidor.setEnabled(true);
	        	spnUF.setEnabled(true);
	        	edtDataEmissaoRg.setEnabled(true);

	        	edtCpfCnpj.setText("");
	        	
	    	}else if(tipoPessoa == ConstantesSistema.PESSOA_JURIDICA){
	    		where = ClienteTipoColunas.INDICADOR_PESSOA +" != " + ConstantesSistema.PESSOA_FISICA;
	    		filterArray[0] = new InputFilter.LengthFilter(18);	

	    		edtCpfCnpj.setText("");
	    		edtDataNascimento.setText("");
	        	radioMasculino.setChecked(false);
	        	radioFeminino.setChecked(false);
	        	edtRg.setText("");
	        	spnOrgaoExpedidor.setSelection(0);
	        	spnUF.setSelection(0);
	        	edtDataEmissaoRg.setText("");
	        	
	        	edtDataNascimento.setEnabled(false);
	        	radioMasculino.setEnabled(false);
	        	radioFeminino.setEnabled(false);
	        	edtRg.setEnabled(false);
	        	spnOrgaoExpedidor.setEnabled(false);
	        	spnUF.setEnabled(false);
	        	edtDataEmissaoRg.setEnabled(false);
	        	
	        	rgOpcoesPessoaSexo.clearCheck();
	    	}
	    	
        	edtCpfCnpj.setFilters(filterArray);
        	
			cursor = fachada.getCursor(ClienteTipo.class, 
	    			   ClienteTipoColunas.ID, 
	    			   ClienteTipoColunas.DESCRICAO,
	    			   new ClienteTipo().getNomeTabela(),
	    			   where);

			spnTipoCliente.setAdapter(Util.getAdapter(cursor));
			
			if(tipoPessoa == ConstantesSistema.PESSOA_FISICA){
				Util.selecionarItemCombo(spnTipoCliente, ClienteTipo.PARTICULARES.longValue());
				spnTipoCliente.setEnabled(false);
			}else{
				spnTipoCliente.setEnabled(true);	
			}

		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
    	
    }
    
    @Override
	protected void onPause() {
		super.onPause();
    	
		radioPessoaFisica = (RadioButton) findViewById(R.id.radioPessoaFisica);
		radioPessoaJuridica = (RadioButton) findViewById(R.id.radioPessoaJuridica);
		radioMasculino = (RadioButton) findViewById(R.id.radioMasculino);
		radioFeminino = (RadioButton) findViewById(R.id.radioFeminino);
		
		edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
		edtNomeCliente = (EditText) findViewById(R.id.edtNomeCliente);
		edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
		edtRg = (EditText) findViewById(R.id.edtRg);
		edtDataEmissaoRg = (EditText) findViewById(R.id.edtDataEmissaoRg);
		spnOrgaoExpedidor = (Spinner) findViewById(R.id.spnOrgaoExpedidor);
		spnUF = (Spinner) findViewById(R.id.spnUF);
		
		//ImovelAtlzCadastral
		clienteAtualizacaoCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);
		
		//Orgao Expedidor
		OrgaoExpedidorRg orgaoExpedidor = new OrgaoExpedidorRg();
		orgaoExpedidor.setId((int) spnOrgaoExpedidor.getSelectedItemId());
		
		if(orgaoExpedidor.getId() != ConstantesSistema.ITEM_INVALIDO && !orgaoExpedidor.getId().equals(Integer.valueOf(0))){
			clienteAtualizacaoCadastral.setOrgaoExpedidorRg(orgaoExpedidor);
		}else{
			clienteAtualizacaoCadastral.setOrgaoExpedidorRg(null);
		}
		
		//UF
    	UnidadeFederacao uf = new UnidadeFederacao();
		uf.setId((int) spnUF.getSelectedItemId());
    	
		if(uf.getId() != ConstantesSistema.ITEM_INVALIDO && !uf.getId().equals(Integer.valueOf(0))){
			clienteAtualizacaoCadastral.setUnidadeFederacao(uf);
		}else{
			clienteAtualizacaoCadastral.setUnidadeFederacao(null);
		}
		
		//Setar CPF/CNPJ
		if(edtCpfCnpj.getText() != null && !edtCpfCnpj.getText().toString().equals("")){
			String cpfcnpj = edtCpfCnpj.getText().toString().replace(".", "").replace("-", "").replace("/", "");
			clienteAtualizacaoCadastral.setNumeroCPFCNPPJ(cpfcnpj);
		}else{
			clienteAtualizacaoCadastral.setNumeroCPFCNPPJ(null);
		}
		
		//Setar Nome Cliente
		if(edtNomeCliente.getText() != null && !edtNomeCliente.getText().toString().equals("")){
			clienteAtualizacaoCadastral.setNomeCliente(edtNomeCliente.getText().toString());
		}else{
			clienteAtualizacaoCadastral.setNomeCliente(null);
		}
		
		//Setar Data Nascimento
		if(edtDataNascimento.getText() != null && !edtDataNascimento.getText().toString().equals("")){
			if(!Util.validarData(edtDataNascimento.getText().toString(), "dd/MM/yyyy")){
				TabsActivity.dataNascimentoValida = true;
				clienteAtualizacaoCadastral.setDataNascimento(Util.converteStringParaDate(edtDataNascimento.getText().toString()));
			}else{
				TabsActivity.dataNascimentoValida = false;
				clienteAtualizacaoCadastral.setDataNascimento(null);
			}
		}else{
			TabsActivity.dataNascimentoValida = true;
			clienteAtualizacaoCadastral.setDataNascimento(null);
		}
		
		//Setar RG
		if(edtRg.getText() != null && !edtRg.getText().toString().equals("")){
			clienteAtualizacaoCadastral.setNumeroRG(edtRg.getText().toString());
		}else{
			clienteAtualizacaoCadastral.setNumeroRG(null);
		}
		
		//Setar Data Emissão RG
		if(edtDataEmissaoRg.getText() != null && !edtDataEmissaoRg.getText().toString().equals("")){
			if(!Util.validarData(edtDataEmissaoRg.getText().toString(), "dd/MM/yyyy")){
				TabsActivity.dataEmissaoValida = true;
				clienteAtualizacaoCadastral.setDataEmissaoRg(Util.converteStringParaDate(edtDataEmissaoRg.getText().toString()));
			}else{
				TabsActivity.dataEmissaoValida = false;
				clienteAtualizacaoCadastral.setDataEmissaoRg(null);
			}
		}else{
			TabsActivity.dataEmissaoValida = true;
			clienteAtualizacaoCadastral.setDataEmissaoRg(null);
		}
		
		ClienteTipo clienteTipo = new ClienteTipo();
		clienteTipo.setId((int) spnTipoCliente.getSelectedItemId());
		
		//Verificar se é Pessoa Física ou Jurídica
		if(radioPessoaFisica.isChecked()){
			
			clienteTipo.setIndicadorPessoa(ConstantesSistema.PESSOA_FISICA);
			
			if ( clienteTipo.getId() != null && !clienteTipo.getId().equals(Integer.valueOf(0)) && clienteTipo.getId() != ConstantesSistema.ITEM_INVALIDO ) {
				clienteAtualizacaoCadastral.setClienteTipo(clienteTipo);
			}else{
				clienteAtualizacaoCadastral.setClienteTipo(null);
			}
			
			PessoaSexo pessoaSexo = new PessoaSexo();
			if(radioMasculino.isChecked()){
				pessoaSexo.setId(PessoaSexo.MASCULINO);
				clienteAtualizacaoCadastral.setPessoaSexo(pessoaSexo);
			}else if(radioFeminino.isChecked()){
				pessoaSexo.setId(PessoaSexo.FEMININO);
				clienteAtualizacaoCadastral.setPessoaSexo(pessoaSexo);
			}else{
				clienteAtualizacaoCadastral.setPessoaSexo(null);
			}
		
		}else if(radioPessoaJuridica.isChecked()){
			clienteTipo.setIndicadorPessoa(ConstantesSistema.PESSOA_JURIDICA);
			clienteAtualizacaoCadastral.setClienteTipo(clienteTipo);
			clienteAtualizacaoCadastral.setDataNascimento(null);
			clienteAtualizacaoCadastral.setPessoaSexo(null);
			clienteAtualizacaoCadastral.setNumeroRG(null);
			clienteAtualizacaoCadastral.setOrgaoExpedidorRg(null);
			clienteAtualizacaoCadastral.setUnidadeFederacao(null);
			clienteAtualizacaoCadastral.setDataEmissaoRg(null);
		}
		
		if(colecaoClienteFoneHelper != null && !colecaoClienteFoneHelper.isEmpty()){
			ArrayList<ClienteFoneAtlzCad> colecaoClienteFone = new ArrayList<ClienteFoneAtlzCad>();
			ClienteFoneAtlzCad clienteFone = null;
			FoneTipo foneTipo = null;
			
			for(ClienteFoneHelper helper : colecaoClienteFoneHelper){
				foneTipo = new FoneTipo();
				
				clienteFone = new ClienteFoneAtlzCad();
				clienteFone.setCodigoDDD(Integer.parseInt(helper.getCodigoDDD()));
				clienteFone.setNumeroFone(Integer.parseInt(helper.getNumeroTelefone()));
				clienteFone.setClienteAtlzCadastral(clienteAtualizacaoCadastral);
				
				if(helper.getIdFoneTipo() == FoneTipo.CELULAR){
					foneTipo.setId(FoneTipo.CELULAR);
				}else if(helper.getIdFoneTipo() == FoneTipo.COMERCIAL){
					foneTipo.setId(FoneTipo.COMERCIAL);
				}else if(helper.getIdFoneTipo() == FoneTipo.FAX){
					foneTipo.setId(FoneTipo.FAX);
				}else if(helper.getIdFoneTipo() == FoneTipo.RESIDENCIAL){
					foneTipo.setId(FoneTipo.RESIDENCIAL);
				}
				
				clienteFone.setFoneTipo(foneTipo);
				
				colecaoClienteFone.add(clienteFone);
			}
			
			TabsActivity.colecaoClienteFone = colecaoClienteFone;
		}
		
		TabsActivity.cliente = clienteAtualizacaoCadastral;
		
		Integer idLigacaoAguaSituacao = null;
				
		if(imovelAtlzCadastral.getLigAguaSituacao() != null){
			idLigacaoAguaSituacao = imovelAtlzCadastral.getLigAguaSituacao().getId();
		}
		
		if ( TabsActivity.indicadorExibirMensagemErro ) {
			try{
				String mensagemErro = fachada.validarAbaCliente(clienteAtualizacaoCadastral, idLigacaoAguaSituacao);
				
				if(mensagemErro != null && !mensagemErro.equals("")){
					Util.exibirMensagemErro(ClienteAbaActivity.this, mensagemErro);
				}
				
			}catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}
		}
	}
    
    /**
     * Valida os Campos obrigatórios de adicionar telefone
     * 
     * @author Davi Menezes
     * @date 03/01/20313
     */
    private boolean validaCamposObrigatoriosTelefone(){
    	long idTipoTelefone = spnFoneTipo.getSelectedItemId();
    	String campos = "";
    	
    	if(edtTelefone.getText().toString().length() == 13){
	    	String codigoDDD = edtTelefone.getText().toString().substring(0, 4);
	    	String numTelefone = edtTelefone.getText().toString().substring(4, 13);
	    	String primeiroNum = edtTelefone.getText().toString().substring(4,5);
	    	
	    	if(idTipoTelefone == ConstantesSistema.ITEM_INVALIDO){
	    		campos = campos + "Informe Tipo Telefone \n";
	    	}
	    	
	    	if(codigoDDD == null || codigoDDD.equals("")){
	    		campos = campos + "Informe Código DDD \n";
	    	}else if(!codigoDDD.equals("(81)") && !codigoDDD.equals("(87)")){
	    		campos = campos + "DDD Inválido \n";
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
    		
    		Util.exibirMensagemErro(ClienteAbaActivity.this, campos);
    		
    		return false;
    	}else{
    		return true;
    	}
    }
    
    /**
     * Carrega os telefones do Imovel na tabela de Telefone
     * 
     * @author Anderson Cabral
     * @date 17/01/2012
     */
    private void carregarTelefones(Collection<ClienteFoneAtlzCad> colecaoClienteFone){
    	if(colecaoClienteFone != null){
	    	for(ClienteFoneAtlzCad clienteFone : colecaoClienteFone){ 
	    		
		    	ClienteFoneHelper helper = new ClienteFoneHelper();
		    	
		    	int idFoneTipo = (int) clienteFone.getFoneTipo().getId();
		    	helper.setIdFoneTipo(idFoneTipo);
		    	
		    	if(idFoneTipo == FoneTipo.RESIDENCIAL){
		    		helper.setDescricaoFoneTipo("RESIDENCIAL");
		    	}else if(idFoneTipo == FoneTipo.COMERCIAL){
		    		helper.setDescricaoFoneTipo("COMERCIAL");
		    	}else if(idFoneTipo == FoneTipo.CELULAR){
		    		helper.setDescricaoFoneTipo("CELULAR");
		    	}else if(idFoneTipo == FoneTipo.FAX){
		    		helper.setDescricaoFoneTipo("FAX");
		    	}
		    	
		    	String codigoDDD = clienteFone.getCodigoDDD().toString();
		    	helper.setCodigoDDD(codigoDDD);
		
		    	String numeroTelefone = clienteFone.getNumeroFone().toString();
		    	helper.setNumeroTelefone(numeroTelefone);
		    	
		    	if(!tableRowsTelefone.containsKey(helper)){
		    		tableLayoutTelefone = (TableLayout) findViewById(R.id.tabelaTelefones);
		    		
		//    		String codigoTelefone = codigoDDD + "" + numeroTelefone;
		    		
		    		TableRow tr = new TableRow(this);
		    		tr.setGravity(Gravity.CENTER_VERTICAL);
		    		tr.setId(Integer.valueOf(numeroTelefone));
		    		tr.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
		                    										android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		    		tr.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
		    		tr.setOnLongClickListener(this);
		    		
		    		TextView tvActionSelected = new TextView(this);
		            tvActionSelected.setId(Integer.valueOf(numeroTelefone));
		            tvActionSelected.setGravity(Gravity.CENTER_VERTICAL);
		            tvActionSelected.setTextColor(Color.BLACK);
		            tvActionSelected.setHeight(50);
		            tvActionSelected.setPadding(3, 0, 0, 0);
		            tvActionSelected.setMaxWidth(10);
		            
		            
		            
		            String numeroTelefoneFormatado = "";
		            if ( numeroTelefone.length() > 7 ) {
		            	numeroTelefoneFormatado = numeroTelefone.substring(0, 4) + "-" + numeroTelefone.substring(4, 8);	
		            } else {
		            	numeroTelefoneFormatado = numeroTelefone;
		            }
		            
		            
		            String telefone = "(" + codigoDDD + ")" + " " + numeroTelefoneFormatado + " " + "(" +  helper.getDescricaoFoneTipo() + ")";
		            
		            tvActionSelected.setText(telefone);
		            tvActionSelected.setTextSize(18);
		            tvActionSelected.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
		            tvActionSelected.setOnLongClickListener(this);
		    		
		            ImageView imgRemoveAction = new ImageView(this);
		            imgRemoveAction.setId(Integer.valueOf(numeroTelefone));
		            imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
		            imgRemoveAction.setBackgroundResource(R.drawable.btnremover);
		            imgRemoveAction.setPadding(0, 20, 30, 10);
		            imgRemoveAction.setOnLongClickListener(this);
		    		
		            spnFoneTipo.setSelection(0);
		//          edtFoneDDD.getText().clear();
		            edtTelefone.getText().clear();
		            
		            tr.addView(tvActionSelected);
		            tr.addView(imgRemoveAction);
		            
		            tableRowsTelefone.put(helper, tr);
		            colecaoClienteFoneHelper.add(helper);
		            
		            populaTabelaTelefone();
		        }
	    	}
    	}
    }
    
    /**
     * Inserir uma linha na tabela de Telefone
     * 
     * @author Davi Menezes
     * @date 03/01/2012
     */
    private void inserirLinhaTabelaTelefone(){
    	ClienteFoneHelper helper = new ClienteFoneHelper();
    	
    	int idFoneTipo = (int) spnFoneTipo.getSelectedItemId();
    	helper.setIdFoneTipo(idFoneTipo);
    	
    	if(idFoneTipo == FoneTipo.RESIDENCIAL){
    		helper.setDescricaoFoneTipo("RESIDENCIAL");
    	}else if(idFoneTipo == FoneTipo.COMERCIAL){
    		helper.setDescricaoFoneTipo("COMERCIAL");
    	}else if(idFoneTipo == FoneTipo.CELULAR){
    		helper.setDescricaoFoneTipo("CELULAR");
    	}else if(idFoneTipo == FoneTipo.FAX){
    		helper.setDescricaoFoneTipo("FAX");
    	}
    	
    	String codigoDDD = edtTelefone.getText().toString().substring(0, 4);
    	codigoDDD = codigoDDD.replace("(", "").replace(")", "");
    	helper.setCodigoDDD(codigoDDD);

    	String numeroTelefoneFormatado = edtTelefone.getText().toString().substring(4, 13);
    	String numeroTelefone = numeroTelefoneFormatado.replace("-", "");
    	helper.setNumeroTelefone(numeroTelefone);
    	
    	if(!tableRowsTelefone.containsKey(helper)){
    		tableLayoutTelefone = (TableLayout) findViewById(R.id.tabelaTelefones);
    		
//    		String codigoTelefone = codigoDDD + "" + numeroTelefone;
    		
    		TableRow tr = new TableRow(this);
    		tr.setGravity(Gravity.CENTER_VERTICAL);
    		tr.setId(Integer.valueOf(numeroTelefone));
    		tr.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    										android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    		tr.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
    		tr.setOnLongClickListener(this);
    		
    		TextView tvActionSelected = new TextView(this);
            tvActionSelected.setId(Integer.valueOf(numeroTelefone));
            tvActionSelected.setGravity(Gravity.CENTER_VERTICAL);
            tvActionSelected.setTextColor(Color.BLACK);
            tvActionSelected.setHeight(50);
            tvActionSelected.setPadding(3, 0, 0, 0);
            tvActionSelected.setMaxWidth(10);
            
            String telefone = "(" + codigoDDD + ")" + " " + numeroTelefoneFormatado + " " + "(" +  helper.getDescricaoFoneTipo() + ")";
            
            tvActionSelected.setText(telefone);
            tvActionSelected.setTextSize(18);
            tvActionSelected.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
            tvActionSelected.setOnLongClickListener(this);
    		
            ImageView imgRemoveAction = new ImageView(this);
            imgRemoveAction.setId(Integer.valueOf(numeroTelefone));
            imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID);
            imgRemoveAction.setBackgroundResource(R.drawable.btnremover);
            imgRemoveAction.setPadding(0, 20, 30, 10);
            imgRemoveAction.setOnLongClickListener(this);
    		
            spnFoneTipo.setSelection(0);
//          edtFoneDDD.getText().clear();
            edtTelefone.getText().clear();
            
            tr.addView(tvActionSelected);
            tr.addView(imgRemoveAction);
            
            tableRowsTelefone.put(helper, tr);
            colecaoClienteFoneHelper.add(helper);
            
            populaTabelaTelefone();
        }else{
    		Util.showMessage(this, getString(R.string.error_tel_ja_selecionado), Toast.LENGTH_SHORT);
    	}
    }
    
    /**
     * Remove da tabela o Telefone Selecionado
     * 
     * @author Davi Menezes
     * @date 03/01/2012
     */
    private void removerLinhaTabelaTelefone(View v){
    	Integer id = v.getId();
    	
    	ClienteFoneHelper helper = new ClienteFoneHelper();
    	if(colecaoClienteFoneHelper != null && !colecaoClienteFoneHelper.isEmpty()){
    		for(ClienteFoneHelper aux : colecaoClienteFoneHelper){
    			if(aux.getNumeroTelefone().equals(String.valueOf(id))){
    				helper = aux;
    			}
    		}
    	}
    	
    	if(tableRowsTelefone.containsKey(helper)){
    		tableRowsTelefone.remove(helper);
    		colecaoClienteFoneHelper.remove(helper);
    		
    		Util.showMessage(this, getString(R.string.msg_tel_removido_sucesso), Toast.LENGTH_SHORT);
    		
    		populaTabelaTelefone();
    	}
    }
    
    /**
     * Popula a tabela de Telefone
     * 
     * @author Davi Menezes
     * @date 03/01/2012
     */
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
    		tr.setBackgroundColor((tableLayoutTelefone.getChildCount() % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F"));
    		tableLayoutTelefone.addView(tr, new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                                                                 android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    	}
    }
    
    @Override
	public boolean onLongClick(View v) {
		if(v.getTag().equals(ConstantesSistema.BOTAO_REMOVER_TELEFONE_ID)){
			this.removerLinhaTabelaTelefone(v);
		}
		
		return false;
	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
		this.fecharCursor(cursor);
    }
    
}