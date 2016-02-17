package com.br.gsanac.gui;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.br.gsanac.R;
import com.br.gsanac.entidades.Bairro;
import com.br.gsanac.entidades.Bairro.Bairros;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.Cep.Ceps;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.LogradouroBairro;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroTipo;
import com.br.gsanac.entidades.LogradouroTipo.LogradouroTipoColunas;
import com.br.gsanac.entidades.LogradouroTitulo;
import com.br.gsanac.entidades.LogradouroTitulo.LogradouroTituloColunas;
import com.br.gsanac.entidades.Municipio;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class LogradouroInserirActivity extends Activity implements OnLongClickListener {
	
	
	private static Fachada fachada = Fachada.getInstance();
	private Cursor cursor;
	private Intent intent;
	
	/**Componentes da tela**/
    private Spinner spnTipoLogradouro;
    private Spinner spnTituloLogradouro;
    private EditText edtNomeLogradouro;
    private EditText edtNomePopLogradouro;
    private EditText edtLoteamento;
    private EditText edtMunicipio;
    private Spinner  spnBairro;
    private EditText edtCep;
    private Button btInserir;
    private Button btCancelar;
    private Button btAdicionarCep;
    
    private Municipio municipio;
    private Cep  cepUnico=  null;
    

    private boolean primeiraVez   = true;
    
    /*** Armazena as linhas da TableLayout Bairro***/
    private Map<Long, TableRow> tableRowsBairros;
    
    /*** Tabela com os bairros selecionados ***/
    private TableLayout  tableLayoutBairros;
    
    
    /*** Armazena as linhas do TableLayout CEP ***/
    private Map<Integer, TableRow> tableRowsCeps;
    
    /*** Tabela com os CEPs selecionados ***/
    private TableLayout tableLayoutCeps;

	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logradouro_inserir);

		try {
			this.fecharCursor(cursor);
			
			/** Tipo Logradouro **/
			spnTipoLogradouro = (Spinner) findViewById(R.id.spnTipoLogradouro);
							
			cursor = fachada.getCursor(LogradouroTipo.class,
									   LogradouroTipoColunas.ID,
									   LogradouroTipoColunas.DESCRICAO,
									   new LogradouroTipo().getNomeTabela());
			
			spnTipoLogradouro.setAdapter(Util.getAdapter(cursor));
			
			/** Titulo Logradouro **/
			spnTituloLogradouro = (Spinner) findViewById(R.id.spnTituloLogradouro);
		
					
			cursor = fachada.getCursor(LogradouroTitulo.class,
									   LogradouroTituloColunas.ID,
									   LogradouroTituloColunas.DESCRICAO,
									   new LogradouroTitulo().getNomeTabela());
			
			spnTituloLogradouro.setAdapter(Util.getAdapter(cursor));
			
			/** Bairro **/
	        spnBairro = (Spinner) findViewById(R.id.spnBairro);
					
	        cursor = fachada.getCursor(Bairro.class,
									   Bairros.ID,
									   Bairros.DESCRICAO,
									   new Bairro().getNomeTabela());
			
			spnBairro.setAdapter(Util.getAdapter(cursor));
			
			spnBairro.setOnItemSelectedListener(new OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (!primeiraVez) {
                    	inserirLinhaTabelaBairro();
                    } else {
                    	primeiraVez = false;
                    }
                }

                public void onNothingSelected(AdapterView<?> arg0) {

                }

            });
			
			tableRowsBairros = new HashMap<Long, TableRow>();
			tableRowsCeps 	 = new HashMap<Integer, TableRow>(); 
			
			/** Nome Logradouro **/
			edtNomeLogradouro 	 = (EditText) findViewById(R.id.edtNomeLogradouro);	
			edtNomeLogradouro.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(40), Util.filterReplaceCaracteresEspeciais()});
			
			/** Nome Popular **/
			edtNomePopLogradouro = (EditText) findViewById(R.id.edtnomePopLogradouro);	
			edtNomePopLogradouro.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(30), Util.filterReplaceCaracteresEspeciais()});
			
	        /** Loteamento **/
			edtLoteamento		 = (EditText) findViewById(R.id.edtLoteamento);
			edtLoteamento.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(30), Util.filterReplaceCaracteresEspeciais()});
			
			/** CEP **/		
			edtCep 				 = (EditText) findViewById(R.id.edtCep);
			
			/** Municipio **/
			edtMunicipio 	 	= (EditText) findViewById(R.id.edtMunicipio);
			
			municipio = new Municipio();
	        municipio = (Municipio) fachada.pesquisar(municipio, null, null);
	        
	        edtMunicipio.setText(municipio.getNome());
	        edtMunicipio.setEnabled(false);
	        
			/**** Botao Adicionar CEP ******/
			
			btAdicionarCep = (Button) findViewById(R.id.btAdicionarCep); 
			
			btAdicionarCep.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {		
					inserirLinhaTabelaCep();
				}
			});
			
	        /** Verifica se o municipio selecionado tem CEP unico ou nao.**/
	        tableLayoutCeps	= (TableLayout) findViewById(R.id.tableLayoutCeps);
	        
			if(municipio != null && municipio.getCodigoCepUnico() == 1){
				cepUnico = new Cep();
				cepUnico = (Cep) fachada.pesquisar(cepUnico, null, null);
		        
				edtCep.setText(cepUnico.getCodigo().toString());
				edtCep.setEnabled(false);
				btAdicionarCep.setVisibility(View.GONE);
				tableLayoutCeps.setVisibility(View.GONE);
				
			}else{
				edtCep.setEnabled(true);
				btAdicionarCep.setVisibility(View.VISIBLE);
				tableLayoutCeps.setVisibility(View.VISIBLE);
			}
			
			/** Botão Inserir **/	
			btInserir = (Button) findViewById(R.id.btInserir);
			
			btInserir.setOnClickListener(new OnClickListener() {
				
				
				@Override
				public void onClick(View v) {										
					if(validarCamposObrigatorios()){
						
						Long dateTime = new Date().getTime();
						
						/** Inseri Logradouro***/
						Logradouro logradouro = new Logradouro();
						
						long idLogradouroTipo 	= spnTipoLogradouro.getSelectedItemId();						
						long idLogradouroTitulo = spnTituloLogradouro.getSelectedItemId();
						Integer idMunicipio 		 = municipio.getId();
						String nomeLogradouro 		 = edtNomeLogradouro.getText().toString();
						String nomePopularLogradouro = edtNomePopLogradouro.getText().toString();
						String loteamento 		 	 = edtLoteamento.getText().toString();
						
//						logradouro.setId(dateTime.toString());
												
						logradouro.setLogradouroTipo(new LogradouroTipo());
						logradouro.getLogradouroTipo().setId(String.valueOf(idLogradouroTipo));
						
						if(idLogradouroTitulo != ConstantesSistema.ITEM_INVALIDO){							
							logradouro.setLogradouroTitulo(new LogradouroTitulo());
							logradouro.getLogradouroTitulo().setId(String.valueOf(idLogradouroTitulo));
						}
						
						logradouro.setMunicipio(new Municipio());
						logradouro.getMunicipio().setId(idMunicipio);
						
						logradouro.setNomeLogradouro(nomeLogradouro);
						logradouro.setNomePopularLogradouro(nomePopularLogradouro);
						logradouro.setNomeLoteamento(loteamento);
						logradouro.setIndicadorNovo(1);
						logradouro.setIndicadorTransmitido(2);
						
						try {
							logradouro.setCodigoUnico(String.valueOf(new Date().getTime()));
							long idLo = fachada.inserir(logradouro);
							logradouro.setId(String.valueOf(idLo));
							
						} catch (FachadaException e) {
							e.printStackTrace();
						}
						
						/** Inseri Logradouros Bairro **/

						LogradouroBairro logradouroBairro = new LogradouroBairro();
						
						Set<Long> idsBairros = tableRowsBairros.keySet();
						
						for(Long idBairro : idsBairros){
//		                    String selection = LogradouroBairros.ID + "=?";
//	
//		                    String[] selectionArgs = new String[] {
//		                        String.valueOf(idBairro)
//		                    };
//		                    
//		                    try {
//		                    	//verifica se ja existe o bairro selecionado na tabela logradouro bairro
//								logradouroBairro = (LogradouroBairro) fachada.pesquisar(logradouroBairro, selection, selectionArgs);
//							} catch (FachadaException e) {
//								e.printStackTrace();
//							}
							
//							if(logradouroBairro == null || logradouroBairro.getId() == null){
								dateTime = new Date().getTime();
								
								logradouroBairro = new LogradouroBairro();
//								logradouroBairro.setId(dateTime.toString());
								logradouroBairro.setLogradouro(logradouro);
								logradouroBairro.setBairro(new Bairro());
								logradouroBairro.getBairro().setId(String.valueOf(idBairro));
								
								try {
									long idLoBa = fachada.inserir(logradouroBairro);
									logradouroBairro.setId(String.valueOf(idLoBa));
								} catch (FachadaException e) {
									e.printStackTrace();
								}
//							}
							
						}
						
						ArrayList<String> listaCep = new ArrayList<String>();
						
						//Caso o municipio nao seja CEP unico, cria um array com os CEPs selecionados
						//Se nao, adiciona o CEP unico no array 
						if(cepUnico == null){
							Collection<TableRow> listaTableRowCep = tableRowsCeps.values();
							for(TableRow tableRowCep : listaTableRowCep){
								TextView tvCep 		= (TextView) tableRowCep.getChildAt(0);
								String codigoCepStr = tvCep.getText().toString();
								
								listaCep.add(codigoCepStr);
								
							}
						}else{
							listaCep.add(edtCep.getText().toString());
						}
						
						
						for(String codigoCepStr : listaCep){
							
							/** Inseri CEP **/

							Integer codigoCep = null;
							Cep _cep = new Cep();
							
							if(!codigoCepStr.equals("")){
								codigoCep = Integer.valueOf(codigoCepStr);

			                    String selectionCep = Ceps.CODIGO + "=?";
		
			                    String[] selectionArgsCep = new String[] {
			                    	codigoCepStr
			                    };
			                    
			                    try {
			                    	//verifica se ja existe o cep selecionado na tabela cep
									_cep = (Cep) fachada.pesquisar(_cep, selectionCep, selectionArgsCep);
								} catch (FachadaException e) {
									e.printStackTrace();
								}
							}
							
							if(_cep == null || _cep.getId() == null){
								_cep = new Cep();
								_cep.setCodigo(codigoCep);
								_cep.setIndicadorNovo(1);
								_cep.setIndicadorTransmitido(2);
								
								try {
									dateTime = new Date().getTime();
//									_cep.setId(dateTime.toString());
									
									_cep.setCodigoUnico(dateTime.toString());
									
									long idCEP = fachada.inserir(_cep);
									_cep.setId(String.valueOf(idCEP));
									
								} catch (FachadaException e) {
									e.printStackTrace();
								}
							}
						
							/** Inseri Logradouro CEP **/
//							Integer idCep = _cep.getId();
//
							LogradouroCep logradouroCep	= new LogradouroCep();
//							
//							if(idCep != null){						
//			                    String selectionLograCep = Ceps.ID + "=?";
//		
//			                    String[] selectionArgsLograCep = new String[] {
//			                        String.valueOf(idCep)
//			                    };
//			                    
//			                    try {
//			                    	//verifica se ja existe o cep selecionado na tabela Logradouro Cep
//			                    	logradouroCep = (LogradouroCep) fachada.pesquisar(logradouroCep, selectionLograCep, selectionArgsLograCep);
//								} catch (FachadaException e) {
//									e.printStackTrace();
//								}
//							}
							
//							if(logradouroCep == null || logradouroCep.getId() == null){
							
								logradouroCep = new LogradouroCep();
								
								logradouroCep.setCep(_cep);
								logradouroCep.setLogradouro(logradouro);
								logradouroCep.setIndicadorNovo(1);
								logradouroCep.setIndicadorTransmitido(2);
								
								try {
									fachada.inserir(logradouroCep);
								} catch (FachadaException e) {
									e.printStackTrace();
								}
//							}
						}
						//Volta para a tela de endereco.
	                    intent = new Intent(LogradouroInserirActivity.this, EnderecoAbaActivity.class);
	                    
	                    intent.putExtra(ConstantesSistema.LOGRADOURO, logradouro);

	                    setResult(Activity.RESULT_OK, intent);
	                    finish();
			
					}	
				}				
			});
			
			/** Botão Cancelar **/	
			btCancelar = (Button) findViewById(R.id.btCancelar);
			
			btCancelar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TabsActivity.indicadorExibirMensagemErro = true;
					//Volta para a tela de endereco.
					setResult(Activity.RESULT_CANCELED);
					finish();
				}
			});
       
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
	}
	
	/**
	 *Valida se o CEP tem 8 dígitos
	 *
	 * @author Anderson Cabral
	 * @Date 20/12/2012
	 ***/
	private boolean validarCEP(){
		String codigoCepStr = edtCep.getText().toString();
		
			
			if(codigoCepStr.length() != 8 || codigoCepStr.length() <= 1 || codigoCepStr.substring(0, 1).equals("0") ){
	            new AlertDialog.Builder(LogradouroInserirActivity.this).setTitle(getString(R.string.cep_invalido))
	            .setMessage(R.string.msg_cep_invalido)
	            .setIcon(R.drawable.erro)
	            .setNeutralButton(ConstantesSistema.ALERT_OK,
	                              new DialogInterface.OnClickListener() {
	                                  @Override
	                                  public void onClick(
	                                          DialogInterface dialog,
	                                          int which) {
	                                  }
	                              })
	            .show();
	            
	            return false;
			}else{
				return true;
			}
	}
	
	/**
	 *Valida os campos obrigatorios
	 *
	 * @author Anderson Cabral
	 * @Date 17/12/2012
	 ***/
	private boolean validarCamposObrigatorios(){
		long idTipoLogradouro = spnTipoLogradouro.getSelectedItemId();
		String nomeLogradouro = edtNomeLogradouro.getText().toString();
//		String nomeLoteamento = edtLoteamento.getText().toString();
		String nomeMunicipio = edtMunicipio.getText().toString();
		String codigoCepUnico = edtCep.getText().toString();
		
		String campos  = "";
		
		if(idTipoLogradouro == ConstantesSistema.ITEM_INVALIDO){
			campos = campos + "Informe Tipo Logradouro \n";			
		}
		
		if(nomeLogradouro == null || nomeLogradouro.trim().equals("")){
			campos = campos + "Informe Nome Logradouro \n";
		}
		
//		if(nomeLoteamento == null || nomeLoteamento.trim().equals("")){
//			campos = campos + "Informe Loteamento \n";
//		}
				
		if(nomeMunicipio == null || nomeMunicipio.trim().equals("")){
			campos = campos + "Informe Município \n";
		}
		
		if(tableRowsBairros.keySet().isEmpty()){
			campos = campos + "Informe Bairro \n";
		}
		
		if(cepUnico != null){ 
			if(codigoCepUnico == null || codigoCepUnico.trim().equals("")){
				campos = campos + "Informe CEP \n";
			}
		}else if(tableRowsCeps.keySet().isEmpty()){
			campos = campos + "Informe CEP \n";
		}
		
		if(campos.length() > 0){
			
			campos = campos.substring(0, campos.length() - 2);

			Util.exibirMensagemErro(LogradouroInserirActivity.this, campos);
        
            return false;
		}else{
			return true;
		}
	}
	
    /**
     * Inseri uma linha na lista de CEPs
     * 
     * @author Anderson Cabral
     * @since 19/12/2012
     */
    private void inserirLinhaTabelaCep() {
    	if(validarCEP()){
    		Integer cepCode = Integer.valueOf(edtCep.getText().toString());
    		if (!tableRowsCeps.containsKey(cepCode)) {
    			
	    		tableLayoutCeps =(TableLayout) findViewById(R.id.tabelaCeps);
	    				
	    		String codigoCepStr = edtCep.getText().toString();
	
	            TableRow tr = new TableRow(this);
	            tr.setGravity(Gravity.CENTER_VERTICAL);
	            tr.setId(Integer.valueOf(codigoCepStr));
	            tr.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
	                                                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	            tr.setTag(ConstantesSistema.BOTAO_REMOVER_CEP_ID);
	            tr.setOnLongClickListener(this);
	
	            
	            TextView tvActionSelected = new TextView(this);
	            tvActionSelected.setId(Integer.valueOf(codigoCepStr));
	            tvActionSelected.setGravity(Gravity.CENTER_VERTICAL);
	            tvActionSelected.setTextColor(Color.BLACK);
	            tvActionSelected.setHeight(50);
	            tvActionSelected.setPadding(3, 0, 0, 0);
	            tvActionSelected.setMaxWidth(10);
	
	            
	            tvActionSelected.setText(codigoCepStr);
	            tvActionSelected.setTextSize(18);
	            tvActionSelected.setTag(ConstantesSistema.BOTAO_REMOVER_CEP_ID);
	            tvActionSelected.setOnLongClickListener(this);
	
	            ImageView imgRemoveAction = new ImageView(this);
	            imgRemoveAction.setId(Integer.valueOf(codigoCepStr));
	            imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_CEP_ID);
	            imgRemoveAction.setBackgroundResource(R.drawable.btnremover);
	            imgRemoveAction.setPadding(0, 20, 30, 10);
	            imgRemoveAction.setOnLongClickListener(this);
	
	            edtCep.getText().clear();
	
	            tr.addView(tvActionSelected);
	            tr.addView(imgRemoveAction);
	
	            tableRowsCeps.put(Integer.valueOf(codigoCepStr), tr);
	
	            this.populaTabelaCep();
    		}else{
    			Util.showMessage(this, getString(R.string.error_cep_ja_selecionado), Toast.LENGTH_SHORT);
    		}
    		
    	}
    }
    
    /**
     * <p>
     * Popula a tabela de CEPs
     * </p>
     * 
     * @author Anderson Cabral
     * @since 27/12/2012
     */
    @SuppressWarnings("deprecation")
	private void populaTabelaCep() {
        // Limpa
        if (tableLayoutCeps.getChildCount() > 0) {
        	tableLayoutCeps.removeAllViews();
        }

        Set<Integer> keys = tableRowsCeps.keySet();

        TableRow tr = null;

        for (Integer key : keys) {
            tr = tableRowsCeps.get(key);
            tr.setBackgroundColor((tableLayoutCeps.getChildCount() % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F"));
            tableLayoutCeps.addView(tr, new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                                                                 android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
    
    /**
     * Remove da tabela o CEP selecionado
     * 
     * @author Anderson Cabral
     * @since 27/12/2012
     */
    private void removeLinhaTabelaCEP(View v) {

        Integer id = v.getId();

        if (tableRowsCeps.containsKey(id)) {

        	tableRowsCeps.remove(id);

            Util.showMessage(this, getString(R.string.msg_cep_removido_sucesso), Toast.LENGTH_SHORT);

            this.populaTabelaCep();
        }
    }
	
	
    /**
     * Inseri uma linha na lista de Bairros
     * 
     * @author Anderson Cabral
     * @since 19/12/2012
     */
    private void inserirLinhaTabelaBairro() {

		if (!tableRowsBairros.containsKey(spnBairro.getSelectedItemId())) {
	
				tableLayoutBairros = (TableLayout) findViewById(R.id.tabelaBairros);
	
	            int id = (int) spnBairro.getSelectedItemId();
	
	            /*
	             * 
	             */
	            TableRow tr = new TableRow(this);
	            tr.setGravity(Gravity.CENTER_VERTICAL);
	            tr.setId(id);
	            tr.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
	                                                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	            tr.setTag(ConstantesSistema.BOTAO_REMOVER_BAIRRO_ID);
	            tr.setOnLongClickListener(this);
	
	            /*
	             * 
	             */
	            TextView tvActionSelected = new TextView(this);
	            tvActionSelected.setId(id);
	            tvActionSelected.setGravity(Gravity.CENTER_VERTICAL);
	            tvActionSelected.setTextColor(Color.BLACK);
	            tvActionSelected.setHeight(50);
	            tvActionSelected.setPadding(3, 0, 0, 0);
	            tvActionSelected.setMaxWidth(10);
	
	            Cursor c = (Cursor) spnBairro.getSelectedItem();
	            tvActionSelected.setText(c.getString(c.getColumnIndex(ConstantesSistema.COLUMN_DESCRIPTION_ALIAS)));
	            tvActionSelected.setTextSize(18);
	            tvActionSelected.setTag(ConstantesSistema.BOTAO_REMOVER_BAIRRO_ID);
	            tvActionSelected.setOnLongClickListener(this);
	
	            /*
	             * 
	             */
	            ImageView imgRemoveAction = new ImageView(this);
	            imgRemoveAction.setId(id);
	            imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_BAIRRO_ID);
	            imgRemoveAction.setBackgroundResource(R.drawable.btnremover);
	            imgRemoveAction.setPadding(0, 20, 30, 10);
	            imgRemoveAction.setOnLongClickListener(this);
	
	            spnBairro.setSelection(0);
	            primeiraVez = true;
	            
	            /*
	             * 
	             */
	            tr.addView(tvActionSelected);
	            tr.addView(imgRemoveAction);
	
	            tableRowsBairros.put((long) id, tr);
	
	            this.populaTabelaBairro();
	
	        } else {
	            Util.showMessage(this, getString(R.string.error_bairro_ja_selecionado), Toast.LENGTH_SHORT);
	            spnBairro.requestFocus();
	            spnBairro.setSelection(0);
	            primeiraVez = true;
	        }
    }

    /**
     * <p>
     * Popula a tabela
     * </p>
     * 
     * @author Anderson Cabral
     * @since 19/12/2012
     */
    @SuppressWarnings("deprecation")
	private void populaTabelaBairro() {
        // Limpa
        if (tableLayoutBairros.getChildCount() > 0) {
        	tableLayoutBairros.removeAllViews();
        }

        Set<Long> keys = tableRowsBairros.keySet();

        TableRow tr = null;

        for (Long key : keys) {
            tr = tableRowsBairros.get(key);
            tr.setBackgroundColor((tableLayoutBairros.getChildCount() % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F"));
            tableLayoutBairros.addView(tr, new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                                                                 android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
    
    /**
     * Remove da tabela o bairro selecionado
     * 
     * @author Anderson Cabral
     * @since 19/12/2012
     */
    private void removeLinhaTabelaBairro(View v) {

        long id = v.getId();

        if (tableRowsBairros.containsKey(id)) {

        	tableRowsBairros.remove(id);

            Util.showMessage(this, getString(R.string.msg_bairro_removido_sucesso), Toast.LENGTH_SHORT);

            this.populaTabelaBairro();
        }
    }


	@Override
	public boolean onLongClick(View v) {
        if (v.getTag().equals(ConstantesSistema.BOTAO_REMOVER_BAIRRO_ID)) {
            this.removeLinhaTabelaBairro(v);
        }else if(v.getTag().equals(ConstantesSistema.BOTAO_REMOVER_CEP_ID)){
        	this.removeLinhaTabelaCEP(v);
        }
        return false;
	}
	
	public void fecharCursor(Cursor cursor){
		if(cursor != null){
			cursor.close();
		}
	}

}
