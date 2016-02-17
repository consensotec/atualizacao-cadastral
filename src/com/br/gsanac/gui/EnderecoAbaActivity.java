package com.br.gsanac.gui;

import java.awt.font.NumericShaper;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.br.gsanac.R;
import com.br.gsanac.entidades.Bairro;
import com.br.gsanac.entidades.Bairro.Bairros;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.Cep.Ceps;
import com.br.gsanac.entidades.EnderecoReferencia;
import com.br.gsanac.entidades.EnderecoReferencia.EnderecoReferencias;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.Logradouro.Logradouros;
import com.br.gsanac.entidades.LogradouroBairro;
import com.br.gsanac.entidades.LogradouroBairro.LogradouroBairros;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroCep.LogradouroCeps;
import com.br.gsanac.entidades.LogradouroTipo;
import com.br.gsanac.entidades.LogradouroTipo.LogradouroTipoColunas;
import com.br.gsanac.entidades.LogradouroTitulo;
import com.br.gsanac.entidades.LogradouroTitulo.LogradouroTituloColunas;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

public class EnderecoAbaActivity extends BaseTabsActivity {
	
	private static Fachada fachada = Fachada.getInstance();
	private Cursor cursor;
	private Intent intent;
	
	private ImovelAtlzCadastral imovelAtlzCadastral;
	private LogradouroBairro logradouroBairro;
	private LogradouroCep logradouroCep;

	AutoCompleteTextView autoComplete;
	Spinner spnReferenciaNum;
	EditText edtReferenciaNum;
	EditText edtComplemento;
	Spinner spnBairro;
	Spinner spnCep;
	Button btnInserirLogra;
	EditText edtEndereco;
	
	long idLogradouroSelecionado = ConstantesSistema.ITEM_INVALIDO;
	private ArrayList<Logradouro> logradouros = null;
	
	private boolean novoLogra = false;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endereco_aba);
		try {
			
			/** Logradouro **/		
			cursor = fachada.getCursorLogradouro(Logradouro.class, null);
//			startManagingCursor(cursor);
			SimpleCursorAdapter simple = Util.getAdapterAutoCompleteLogradouro(cursor);

			simple.setFilterQueryProvider(new FilterQueryProvider() {
				
	            @Override
	            public Cursor runQuery(CharSequence description) {
	                Cursor cursor = getDescriptions(description);
	                return cursor;
	            }
			});
	
	        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
			autoComplete.setAdapter(simple);
			autoComplete.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
	         
			autoComplete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AutoCompleteTextView autoCom = (AutoCompleteTextView) v;
					if(autoCom.getText() == null || autoCom.getText().toString().equals("")){
						autoCom.showDropDown();
					}
				}
			});
			
			autoComplete.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long id) {
					idLogradouroSelecionado = id;
					
	            	carregarBairro(id);
	            	carregarCEP(id);
					
				}
			});
			
			/** Referencia/Numero **/
			spnReferenciaNum = (Spinner) findViewById(R.id.spnReferenciaNum);
			
			cursor = fachada.getCursor(EnderecoReferencia.class,
									   EnderecoReferencias.ID,
									   EnderecoReferencias.DESCRICAO,
									   new EnderecoReferencia().getNomeTabela());
//			startManagingCursor(cursor);
			spnReferenciaNum.setAdapter(Util.getAdapter(cursor));
			
			edtReferenciaNum = (EditText) findViewById(R.id.edtReferenciaNum);
			edtReferenciaNum.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(5), Util.filtervalidarFiltroNumeroComplemento()});			
			
			edtReferenciaNum.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus) {
						
						String texto = edtReferenciaNum.getText().toString();
						
						if ( texto != null && !texto.equals( "" ) ){	
								if ( Util.ehNumero( texto ) && texto.length() < 5 ){									
									if ( texto != null && !texto.equals( "" ) )
										edtReferenciaNum.setText( Util.completarComZeros(5, texto ) );
								}
						}
					}	
				}
			});
			
			
			/**
			 * Correção referente a RM10115 - Ajuste no campo número - atualização cadastral (tablet)
			 * 
			 * @author Diogo Luiz
			 * @data 06/03/2014
			 */
			spnReferenciaNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        	
	            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	            	if(spnReferenciaNum.getSelectedItemId() == ConstantesSistema.SEM_NUMERO){
	            		edtReferenciaNum.setText("");
	            		edtReferenciaNum.setEnabled(false);
	            	}else{
	            		edtReferenciaNum.setEnabled(true);
	            	}
	            }

	            public void onNothingSelected(AdapterView<?> arg0) {

	            }

	        });
			
			edtComplemento = (EditText) findViewById(R.id.edtComplemento);
			edtComplemento.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(25), Util.filterReplaceCaracteresEspeciaisEEspaco()});
			
			/** Bairro **/
			spnBairro = (Spinner) findViewById(R.id.spnBairro);
			
//			carregarBairro(autoComplete.getSelectedItemId());
			
			/** CEP **/
			spnCep = (Spinner) findViewById(R.id.spnCep);
			
//			carregarCEP(spnLogradouro.getSelectedItemId());
			
			/** Exibe os dados do Imóvel ***/
			imovelAtlzCadastral = TabsActivity.imovel;
			
				   
			//Carrega na combobox o Logradouro do imovel
		   if (imovelAtlzCadastral.getLogradouro() != null 
				   && imovelAtlzCadastral.getLogradouro().getId() != null) {
			   logradouros = (ArrayList<Logradouro>) fachada.pesquisarLista(Logradouro.class, null, null, Logradouros.NOMELOGRADOURO);				   
			   Util.selecionarItemAutoComplete(autoComplete, imovelAtlzCadastral.getLogradouro().getId().longValue(), logradouros);
			   
			   idLogradouroSelecionado = imovelAtlzCadastral.getLogradouro().getId().longValue();
			   
			   carregarBairro(idLogradouroSelecionado);
			   carregarCEP(idLogradouroSelecionado);
           }
		   
			//Carrega na combobox o Referencia Numero do imovel
		   if (imovelAtlzCadastral.getEnderecoReferencia() != null 
				   && imovelAtlzCadastral.getEnderecoReferencia().getId() != null) {
			   Util.selecionarItemCombo(spnReferenciaNum, imovelAtlzCadastral.getEnderecoReferencia().getId().longValue());
           }
		   
		   //Carrega o Numero do imovel
		   if ( imovelAtlzCadastral.getNumeroImovel() != null && !imovelAtlzCadastral.getNumeroImovel().equals("") ){
			   if ( Util.ehNumero( imovelAtlzCadastral.getNumeroImovel() ) )
				   edtReferenciaNum.setText( Util.completarComZeros( 5, imovelAtlzCadastral.getNumeroImovel() ) );
			   else{
				   edtReferenciaNum.setText( imovelAtlzCadastral.getNumeroImovel() );
				   Util.selecionarItemCombo(spnReferenciaNum, ConstantesSistema.SEM_NUMERO );
			   }
		   }		   
		   
		   //Carrega o complemento do endereco
		   edtComplemento.setText(imovelAtlzCadastral.getComplementoEndereco());
		   
		   //Pesquisa Logradouro Bairro
		   if ( imovelAtlzCadastral.getLogradouroBairroId() != null ) {
			   logradouroBairro = new LogradouroBairro();
	           String selection = LogradouroBairros.ID + "=?";
	           int logradouroBairroId  = imovelAtlzCadastral.getLogradouroBairroId().intValue();
	           
	           String[] selectionArgs = new String[] {
	               String.valueOf(logradouroBairroId)
	           };
			   
	           logradouroBairro = (LogradouroBairro) fachada.pesquisar(logradouroBairro, selection, selectionArgs);
	                          
				//Carrega na combobox o Bairro do imovel
			   if (logradouroBairro != null 
					   && logradouroBairro.getBairro() != null) {
				   Util.selecionarItemCombo(spnBairro, logradouroBairro.getBairro().getId().longValue());
	           }
		   }
		   //Pesquisa Logradouro Cep
		   logradouroCep = new LogradouroCep();
		   if ( imovelAtlzCadastral.getLogradouroCEPId() != null ) {
	           String selectionLograCep = LogradouroCeps.ID + "=?";
	           int logradouroCepId  = imovelAtlzCadastral.getLogradouroCEPId().intValue();
	           
	           String[] selectionArgsLograCep = new String[] {
	               String.valueOf(logradouroCepId)
	           };
			   
	           logradouroCep = (LogradouroCep) fachada.pesquisar(logradouroCep, selectionLograCep, selectionArgsLograCep);
	                          
				//Carrega na combobox o CEP do imovel
			   if (logradouroCep != null 
					   && logradouroCep.getCep() != null) {
				   Util.selecionarItemCombo(spnCep, logradouroCep.getCep().getId().longValue());
	           }  
		   }

			
			/** Chama a tela de Inserir Logradouro **/
			btnInserirLogra = (Button) findViewById(R.id.btnInserirLogra);
			
			btnInserirLogra.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {	
					TabsActivity.indicadorExibirMensagemErro = false;
                    intent = new Intent(EnderecoAbaActivity.this, LogradouroInserirActivity.class);

                    startActivityForResult(intent, ConstantesSistema.ENDERECO_ABA_REQUEST_CODE);
				}
			});

		} catch (FachadaException e) {
			Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		   
        try {
			//Logradouro
			if(idLogradouroSelecionado != ConstantesSistema.ITEM_INVALIDO){
			   Logradouro logradouro = new Logradouro();
	           String selectionLogra = Logradouros.ID + "=?";
	           
	           String[] selectionArgsLogra = new String[] {
	               String.valueOf(idLogradouroSelecionado)
	           };
			   
	           logradouro = (Logradouro) fachada.pesquisar(logradouro, selectionLogra, selectionArgsLogra);
				
	           String endereco = "";
				LogradouroTipo logradouroTipo = new LogradouroTipo();
				
				String selectionTipo = LogradouroTipoColunas.ID + "=?";
				
		        String[] selectionArgsTipo = new String[] {
			            String.valueOf(logradouro.getLogradouroTipo().getId())
			    };
		        
		        logradouroTipo = (LogradouroTipo) Fachada.getInstance().pesquisar(logradouroTipo, selectionTipo, selectionArgsTipo);
		        
		        if ( logradouroTipo != null ) {
		        	endereco = logradouroTipo.getDescricao() + " ";
		        }
		        
		        
		        LogradouroTitulo logradouroTitulo = new LogradouroTitulo();
				
				String selectionTitulo = LogradouroTituloColunas.ID + "=?";
				
		        String[] selectionArgsTitulo = new String[] {
			            String.valueOf(logradouro.getLogradouroTitulo().getId())
			    };
		        
		        logradouroTitulo = (LogradouroTitulo) Fachada.getInstance().pesquisar(logradouroTitulo, selectionTitulo, selectionArgsTitulo);
		        
		        if ( logradouroTitulo != null && logradouroTitulo.getDescricao() != null ) {
		        	endereco += logradouroTitulo.getDescricao() + " ";
		        }
	           
		        endereco += logradouro.getNomeLogradouro();
	           
	           if(logradouro != null && autoComplete.getText().toString().equalsIgnoreCase(endereco)){
//					long _idLogradouro = idLogradouroSelecionado;
					logradouro.setId(String.valueOf(idLogradouroSelecionado));
					
					imovelAtlzCadastral.setLogradouro(logradouro);
					
					if ( logradouro.getCodigoUnico() != null ) {
						imovelAtlzCadastral.setCodigoUnicoLogradouro(logradouro.getCodigoUnico());
					}
	           }else{
	        	   imovelAtlzCadastral.setLogradouro(null);
	           }
			}else{
				imovelAtlzCadastral.setLogradouro(null);
			}
			
			//Referencia
			if(spnReferenciaNum.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
				EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
				long _idEnderecoReferencia = spnReferenciaNum.getSelectedItemId();
				enderecoReferencia.setId(String.valueOf(_idEnderecoReferencia));
				
				imovelAtlzCadastral.setEnderecoReferencia(enderecoReferencia);
			}else{
				imovelAtlzCadastral.setEnderecoReferencia(null);
			}

			//Numero
			if(imovelAtlzCadastral.getEnderecoReferencia() != null 
					&& imovelAtlzCadastral.getEnderecoReferencia().getId() == ConstantesSistema.SEM_NUMERO){
				imovelAtlzCadastral.setNumeroImovel("S/N");
			}else{
				imovelAtlzCadastral.setNumeroImovel(edtReferenciaNum.getText().toString());
			}
			
			//Complemento
			imovelAtlzCadastral.setComplementoEndereco(edtComplemento.getText().toString());
			
			//Logradouro Bairro			
			if(spnBairro.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
				long _idBairro = spnBairro.getSelectedItemId();
				
				LogradouroBairro logradouroBairro = new LogradouroBairro();
		        String selection = LogradouroBairros.BAIRRO + "=?";
		        selection = selection + " AND " + LogradouroBairros.LOGRADOURO + "=?";
		        
		        String[] selectionArgs = new String[] {
		            String.valueOf(_idBairro),
		            String.valueOf(idLogradouroSelecionado)
		        };
				
		        logradouroBairro = (LogradouroBairro) fachada.pesquisar(logradouroBairro, selection, selectionArgs);
		        
		        if(logradouroBairro == null || logradouroBairro.getId() == null){
			        selection = LogradouroBairros.BAIRRO + "=?";
			        
			        selectionArgs = new String[] {
			            String.valueOf(_idBairro)
			        };
					
			        logradouroBairro = (LogradouroBairro) fachada.pesquisar(logradouroBairro, selection, selectionArgs);
		        }
		        
		        imovelAtlzCadastral.setLogradouroBairroId(logradouroBairro.getId());
		        if ( logradouroBairro.getBairro() != null && logradouroBairro.getBairro().getId() != null ) {
		        	imovelAtlzCadastral.setIdBairro(logradouroBairro.getBairro().getId());
		        }
			}else{
				imovelAtlzCadastral.setLogradouroBairroId(null);
			}
	        
	        //Logradouro CEP
			if(spnCep.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
				long _idCep= spnCep.getSelectedItemId();
				
				LogradouroCep logradouroCep = new LogradouroCep();
		        String selectionLogrCep = LogradouroCeps.CEP + "=?";
		        selectionLogrCep = selectionLogrCep + " AND " + LogradouroBairros.LOGRADOURO + "=?";
		        
		        String[] selectionArgsLogrCep = new String[] {
		            String.valueOf(_idCep),
		            String.valueOf(idLogradouroSelecionado)
		        };
				
		        logradouroCep = (LogradouroCep) fachada.pesquisar(logradouroCep, selectionLogrCep, selectionArgsLogrCep);
		        
		        if(logradouroCep == null || logradouroCep.getId() == null){
			        selectionLogrCep = LogradouroCeps.CEP + "=?";
			        
			        selectionArgsLogrCep = new String[] {
			            String.valueOf(_idCep)
			        };
					
			        logradouroCep = (LogradouroCep) fachada.pesquisar(logradouroCep, selectionLogrCep, selectionArgsLogrCep);
		        }
		        
		        imovelAtlzCadastral.setLogradouroCEPId(logradouroCep.getId());
		        
		        if(logradouroCep.getCep() != null){
		        	imovelAtlzCadastral.setCodigoCep(logradouroCep.getCep().getCodigo());
		        }
		        
		        Cep cep = new Cep();
		        String selectionCep = Ceps.ID+ "=?";
		        
		        String[] selectionArgsCep = new String[] {
		            String.valueOf(_idCep)
		        };
				
		        cep = (Cep) fachada.pesquisar(cep, selectionCep, selectionArgsCep);

		        imovelAtlzCadastral.setCodigoCep(cep.getCodigo());
			}else{
				imovelAtlzCadastral.setLogradouroCEPId(null);
			}	        
			
			TabsActivity.imovel = imovelAtlzCadastral;
			
			if ( TabsActivity.indicadorExibirMensagemErro ) {
				//Validacao
				String mensagemErro = fachada.validarAbaEndereco(imovelAtlzCadastral);
				
	        	if ( mensagemErro != null && !mensagemErro.equals("") ) {
	        		Util.exibirMensagemErro(EnderecoAbaActivity.this, mensagemErro);           	
	        	}
			}
	
		} catch (FachadaException e) {
			Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.endereco_aba, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if(resultCode == Activity.RESULT_OK){
			
			TabsActivity.indicadorExibirMensagemErro = true;
			try {
				
				cursor = fachada.getCursorLogradouro(Logradouro.class,null);
//				startManagingCursor(cursor);
				SimpleCursorAdapter simple = Util.getAdapterAutoCompleteLogradouro(cursor);
				
				simple.setFilterQueryProvider(new FilterQueryProvider() {
					
				 @Override
				 public Cursor runQuery(CharSequence description) {
				     Cursor cursor = getDescriptions(description);
				     if(!cursor.moveToFirst()){
				    	 idLogradouroSelecionado = ConstantesSistema.ITEM_INVALIDO;
				     }
				     return cursor;
				 }
				});
				
				autoComplete.setAdapter(simple);
	
				Logradouro logradouro = (Logradouro) intent.getSerializableExtra(ConstantesSistema.LOGRADOURO);
				if ( logradouro != null ) {
					TabsActivity.imovel.setLogradouro(logradouro);
				}
				
				idLogradouroSelecionado = logradouro.getId().longValue();

				logradouros = (ArrayList<Logradouro>) fachada.pesquisarLista(Logradouro.class, null, null, Logradouros.NOMELOGRADOURO);
				
				Util.selecionarItemAutoComplete(autoComplete, logradouro.getId().longValue(), logradouros);
				
				novoLogra = true;
				
				carregarBairro(idLogradouroSelecionado);
				carregarCEP(idLogradouroSelecionado);
								
				novoLogra = false;
				
			} catch (FachadaException e) {
				Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
			}
		}
	}
	
	private void carregarBairro(long logradoiroId){
        try {
        		
				String where = "";
				String ids = "";
				String selection = LogradouroBairros.LOGRADOURO + "=?";
			
		        String[] selectionArgs = new String[] {
			            String.valueOf(logradoiroId)
			     };

				ArrayList<LogradouroBairro> listaLogradouroBairro = (ArrayList<LogradouroBairro>) fachada.pesquisarLista(LogradouroBairro.class, selection, selectionArgs, null);

				if(listaLogradouroBairro != null && !listaLogradouroBairro.isEmpty() ){
					where = Bairros.ID + " IN (";
					
					for(LogradouroBairro logradouroBairro : listaLogradouroBairro){
						ids += logradouroBairro.getBairro().getId() + ",";
					}
					
					ids += ConstantesSistema.ITEM_INVALIDO;
					where += ids + ")";
					cursor = fachada.getCursor(Bairro.class,
							   Bairros.ID,
							   Bairros.DESCRICAO,
							   new Bairro().getNomeTabela(),
							   where);
//					startManagingCursor(cursor);
					spnBairro.setAdapter(Util.getAdapter(cursor));

					if(novoLogra){
						String idBairroSelecionar = String.valueOf(ConstantesSistema.ITEM_INVALIDO);
						
						if(ids.split(",").length <= 2){
							idBairroSelecionar = ids.split(",")[0];
						}

						Util.selecionarItemCombo(spnBairro, Long.parseLong(idBairroSelecionar));
					}else if(logradouroBairro != null && logradouroBairro.getBairro() != null){						
						Util.selecionarItemCombo(spnBairro, logradouroBairro.getBairro().getId().longValue());
					}
					
				} else {
					where = null;
					spnBairro.setAdapter(null);
				}
				
			} catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}
	}
	
	private void carregarCEP(long logradoiroId){
        try {
				String where = "";
				String ids = "";
				String selection = LogradouroCeps.LOGRADOURO + "=?";
			
		        String[] selectionArgs = new String[] {
			            String.valueOf(logradoiroId)
			     };

				ArrayList<LogradouroCep> listaLogradouroCep = (ArrayList<LogradouroCep>) fachada.pesquisarLista(LogradouroCep.class, selection, selectionArgs, null);

				if(listaLogradouroCep != null && !listaLogradouroCep.isEmpty() ){
					where = Ceps.ID + " IN (";
					
					for(LogradouroCep logradouroCep : listaLogradouroCep){
						ids += logradouroCep.getCep().getId() + ",";
					}
					ids += ConstantesSistema.ITEM_INVALIDO;
					where += ids + ")";
				
					cursor = fachada.getCursor(Cep.class,
							   Ceps.ID,
							   Ceps.CODIGO,
							   new Cep().getNomeTabela(),
							   where);
//					startManagingCursor(cursor);
					spnCep.setAdapter(Util.getAdapter(cursor));
					
					if(novoLogra){
						String idCepSelecionar = String.valueOf(ConstantesSistema.ITEM_INVALIDO);
						
						if(ids.split(",").length <= 2){
							idCepSelecionar = ids.split(",")[0];
						}

						Util.selecionarItemCombo(spnCep, Long.parseLong(idCepSelecionar));
					}else if(logradouroCep != null && logradouroCep.getCep() != null){
						Util.selecionarItemCombo(spnCep, logradouroCep.getCep().getId().longValue());
					}
				} else {
					where = null;
				}
	
			} catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}
	}
	
    /**********
     *Filtra os logadouros
     *@author Anderson Cabral
     *@since 23/01/2013 
     * 
     *************/
    public Cursor getDescriptions(CharSequence descriptionFragment) {
       	Cursor cursor = null;
    	String where = null;
    	
    	if(descriptionFragment == null || descriptionFragment == ""){
    		where = null;
    	}else{
    		
    		String [] array = descriptionFragment.toString().split(" ");
    		String pesquisa = "";
    		for (int i = 0; i < array.length; i++) {
				pesquisa += array[i] + "%";
			}
    		
    		where = " where " + LogradouroTipoColunas.DESCRICAO + "||" + LogradouroTituloColunas.DESCRICAO + "||" +Logradouros.NOMELOGRADOURO   +  " LIKE '%" + pesquisa + "'"
    				+" or " + LogradouroTipoColunas.DESCRICAO + "||"  +Logradouros.NOMELOGRADOURO   +  " LIKE '%" + pesquisa + "'";
    	}

        try {
        	cursor = fachada.getCursorLogradouro(Logradouro.class, where);
//        	startManagingCursor(cursor);
		} catch (FachadaException e) {
			e.printStackTrace();
		}
		return cursor;
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
		this.fecharCursor(cursor);
    }
    
}
