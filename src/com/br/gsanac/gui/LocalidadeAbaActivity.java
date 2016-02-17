package com.br.gsanac.gui;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.br.gsanac.R;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.Municipio;
import com.br.gsanac.entidades.Quadra;
import com.br.gsanac.entidades.Quadra.QuadraColunas;
import com.br.gsanac.entidades.SetorComercial;
import com.br.gsanac.entidades.SetorComercial.SetorComercialColunas;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

public class LocalidadeAbaActivity  extends BaseTabsActivity  {

	private static Fachada fachada = Fachada.getInstance();
	
	private EditText edtMatricula;
	private EditText edtMunicipio;
	private EditText edtLocalidade;
	private Spinner spnSetorComercial;
	private Spinner spnQuadra;
	private EditText edtLote;
	private EditText edtSublote;
	private EditText edtSetorComercial;
	private EditText edtQuadra;
	
	private ImovelAtlzCadastral imovelAtlzCadastral;
	
	SistemaParametros sistemaParametros;
	
	boolean selecionarQuadra = false;
	
	/** Called when the activity is first created. */
    int mStackLevel = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localidade_aba);
		   		
		try {
		   sistemaParametros = new SistemaParametros();
		   sistemaParametros = (SistemaParametros) fachada.pesquisar(sistemaParametros, null, null);
		   
		   imovelAtlzCadastral = TabsActivity.imovel;
		   
		   Municipio municipio = new Municipio();
	       municipio = (Municipio) fachada.pesquisar(municipio, null, null);
	       
	       imovelAtlzCadastral.setMunicipioId(municipio.getId());
		   imovelAtlzCadastral.setLocalidadeId(Integer.valueOf(sistemaParametros.getIdLocalidade()));
		   
		   //Matricula
		   edtMatricula = (EditText) findViewById(R.id.edtMatricula);
		   
		   //Municipio
		   edtMunicipio = (EditText) findViewById(R.id.edtMunicipio); 
		   edtMunicipio.setText(municipio.getNome());
		   edtMunicipio.setEnabled(false);

		   //Localidade
		   String localidade = sistemaParametros.getIdLocalidade() + " - " + sistemaParametros.getDescricaoLocalidade();
		   edtLocalidade = (EditText) findViewById(R.id.edtLocalidade);
		   edtLocalidade.setText(localidade);
		   edtLocalidade.setEnabled(false);
		   
		   //Setor Comercial
		   spnSetorComercial = (Spinner) findViewById(R.id.spnSetorComercial);
		   
		   //Setor Comercial nao encontrado na lista
		   edtSetorComercial = (EditText) findViewById(R.id.edtSetorComercial);
		   
		   //Quadra
		   spnQuadra = (Spinner) findViewById(R.id.spnQuadra);
 
		   //Quadra nao encontrado na lista
		   edtQuadra = (EditText) findViewById(R.id.edtQuadra);
		   
		   		   
		   Cursor cursorSetor = fachada.getCursorOrderBy(SetorComercial.class,
				   				   SetorComercialColunas.CODIGO,
				   				   SetorComercialColunas.CODIGO,
								   new SetorComercial().getNomeTabela(),
								   SetorComercialColunas.ORDENACAO);
		   	   
		   spnSetorComercial.setAdapter(Util.getAdapter(cursorSetor));

		   
		   spnSetorComercial.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long id) {
					carregarQuadra(id);					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					
				}
			});
		   
		   edtSetorComercial.addTextChangedListener(new TextWatcher() {
			
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {}
				
				@Override
				public void afterTextChanged(Editable s) {
					if(s.length() == 0){
						spnSetorComercial.setEnabled(true);
					}else{
						Util.selecionarItemCombo(spnSetorComercial, 0);
						spnSetorComercial.setEnabled(false);
					}
				}
		   });
		   
		   edtQuadra.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {				
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {}
				
				@Override
				public void afterTextChanged(Editable s) {
					if(s.length() == 0){
						spnQuadra.setEnabled(true);
					}else{
						Util.selecionarItemCombo(spnQuadra, 0);
						spnQuadra.setEnabled(false);
					}	
				}
		   });
		   
		   if (imovelAtlzCadastral != null && imovelAtlzCadastral.getImovelId() != null ) {
			   
			   //Matricula do imóvel
			   edtMatricula.setText(imovelAtlzCadastral.getImovelId().toString());
			   edtMatricula.setEnabled(false);

		   } else {
			   edtMatricula = (EditText) findViewById(R.id.edtMatricula);
			   edtMatricula.setText("NOVO IMÓVEL");
			   edtMatricula.setEnabled(false);
			   
		   }
		   
		   //Setor
		   if(imovelAtlzCadastral.getCodigoSetorComercial() != null){
			   Util.selecionarItemCombo(spnSetorComercial, imovelAtlzCadastral.getCodigoSetorComercial());
			   
			   if(spnSetorComercial.getSelectedItemId() == ConstantesSistema.ITEM_INVALIDO 
					   || spnSetorComercial.getSelectedItemId() == 0){
				   edtSetorComercial.setText(imovelAtlzCadastral.getCodigoSetorComercial().toString());
			   }
			   
			   selecionarQuadra = true;
		   }
		   
		   //Lote
		   if ( imovelAtlzCadastral.getNumeroLote() != null ) {
			   edtLote = (EditText) findViewById(R.id.edtLote);
			   edtLote.setText(String.valueOf(imovelAtlzCadastral.getNumeroLote().toString()));
		   }
		   
		   
		   //Sublote
		   if ( imovelAtlzCadastral.getNumeroSubLote() != null ) {
			   edtSublote = (EditText) findViewById(R.id.edtSublote);
			   edtSublote.setText(String.valueOf(imovelAtlzCadastral.getNumeroSubLote()));
		   }
			
		   
		   imovelAtlzCadastral.setLocalidadeId(Integer.valueOf(sistemaParametros.getIdLocalidade()));
		   imovelAtlzCadastral.setNomeLocalidade(sistemaParametros.getDescricaoLocalidade());
		   imovelAtlzCadastral.setMunicipioId(municipio.getId());
		   imovelAtlzCadastral.setNomeMunicipio(municipio.getNome());

		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		
		edtLote    = (EditText) findViewById(R.id.edtLote);
		edtSublote = (EditText) findViewById(R.id.edtSublote);
		
		//Setor
		if((spnSetorComercial.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO 
				&& spnSetorComercial.getSelectedItemId() != 0)){
			imovelAtlzCadastral.setCodigoSetorComercial((int) spnSetorComercial.getSelectedItemId());
		}else if((edtSetorComercial.getText() != null && !edtSetorComercial.getText().toString().equals(""))){
			imovelAtlzCadastral.setCodigoSetorComercial(Integer.parseInt(edtSetorComercial.getText().toString()));
		}else{
			imovelAtlzCadastral.setCodigoSetorComercial(null);
		}
		
        //Quadra
		if(spnQuadra.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO 
				&& spnQuadra.getSelectedItemId() != 0){
			imovelAtlzCadastral.setNumeroQuadra((int) spnQuadra.getSelectedItemId());
		}else if((edtQuadra.getText() != null && !edtQuadra.getText().toString().equals(""))){
			imovelAtlzCadastral.setNumeroQuadra(Integer.parseInt(edtQuadra.getText().toString()));
		}else{
			imovelAtlzCadastral.setNumeroQuadra(null);
		}
		
		//Lote
        if ( edtLote.getText() != null && !edtLote.getText().toString().equals("") ) {
        	imovelAtlzCadastral.setNumeroLote(Integer.valueOf(edtLote.getText().toString()));
        } else {
        	imovelAtlzCadastral.setNumeroLote(null);
        }
        
        //Sublote
        if ( edtSublote.getText() != null && !edtSublote.getText().toString().equals("") ) {
        	imovelAtlzCadastral.setNumeroSubLote(Integer.valueOf(edtSublote.getText().toString()));
        } else {
        	imovelAtlzCadastral.setNumeroSubLote(null);
        }
                
        TabsActivity.imovel = imovelAtlzCadastral;
        
        if ( TabsActivity.indicadorExibirMensagemErro ) {
	        
        	try {
	        
	        	String mensagemErro = fachada.validarAbaLocalidade(imovelAtlzCadastral);
	        	
	        	if ( mensagemErro != null && !mensagemErro.equals("") ) {
	
	        		Util.exibirMensagemErro(LocalidadeAbaActivity.this, mensagemErro);
	
	        	}
	        	       	
	        } catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}
        }
	}
	
	private void carregarQuadra(long codigoSetor){
	    try {
	    	
	           String where = QuadraColunas.CDSETORCOMERCIAL + " = " + codigoSetor;
	           where += " OR " + QuadraColunas.ID + " = " + ConstantesSistema.ITEM_INVALIDO;
	           
	           
	 		  Cursor cursorQuadra = fachada.getCursorOrderBy(Quadra.class,
	 										QuadraColunas.NUMQUADRA,
	 										QuadraColunas.NUMQUADRA,
	 									   new Quadra().getNomeTabela(),
	 									  where,
	 									 QuadraColunas.ORDENACAO);
	 		   
	 		   spnQuadra.setAdapter(Util.getAdapter(cursorQuadra));
	 		   
	 		   if(selecionarQuadra){
	 			  //Carrega na combobox o valor da quadra do imovel
				   if (imovelAtlzCadastral.getNumeroQuadra() != null) {
					   Util.selecionarItemCombo(spnQuadra, imovelAtlzCadastral.getNumeroQuadra());
					 
					   if(spnQuadra.getSelectedItemId() == ConstantesSistema.ITEM_INVALIDO 
							   || spnQuadra.getSelectedItemId() == 0){
						   edtQuadra.setText(imovelAtlzCadastral.getNumeroQuadra().toString());
					   }
		           }
				   selecionarQuadra = false;
	 		   }
				
			} catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}
	}

    @Override
    protected void onDestroy() {
    	super.onDestroy();
//		this.fecharCursor(cursor);
    }
	
}
