package com.br.gsanac.gui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.br.gsanac.R;
import com.br.gsanac.entidades.Categoria;
import com.br.gsanac.entidades.Categoria.CategoriaColunas;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.SubCategoria;
import com.br.gsanac.entidades.SubCategoria.SubCategoriaColunas;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

/**
 * [UC 1412] - Manter Dados da Aba do Imóvel do Tablet
 * 
 * @author Davi Menezes
 * @date 07/01/2013
 *
 */
public class CategoriaImovelInserirActivity extends Activity  {

	private static Fachada fachada = Fachada.getInstance();
	private Cursor cursor;
	private Intent intent;
	
	/** Compontentes da tela */
	private Spinner spnCategoriaImovel;
	private Spinner spnSubCategoriaImovel;
	private EditText edtQtdEconomias;
	
	private Button btCancelar;
	private Button btInserir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categoria_imovel_inserir);
		
		TabsActivity.indicadorExibirMensagemErro = false;
		
		try{
			
			/** Categoria Imovel **/
			spnCategoriaImovel = (Spinner) findViewById(R.id.spnCategoriaImovel);
			cursor = fachada.getCursor(Categoria.class, 
									   CategoriaColunas.ID, 
									   CategoriaColunas.DESCRICAO, 
									   new Categoria().getNomeTabela());
			
			startManagingCursor(cursor);
			
			spnCategoriaImovel.setAdapter(Util.getAdapter(cursor));
			
			spnCategoriaImovel.setOnItemSelectedListener(new OnItemSelectedListener() {
	        	
	            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	                listarSubCategoria(id);
	            }
	
	            public void onNothingSelected(AdapterView<?> arg0) {
	
	            }
	
	        });
	        
			/** SubCategoria Imovel **/
			spnSubCategoriaImovel = (Spinner) findViewById(R.id.spnSubCategoriaImovel);		
			
			/** Quantidade de Economias **/
			edtQtdEconomias = (EditText) findViewById(R.id.edtQtdEconomias);
			
			/** Botão Inserir **/
			btInserir = (Button) findViewById(R.id.btInserir);
			
			btInserir.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(validaCamposObrigatorios()){
						ImovelSubCategAtlzCad imovelSubCategoriaAtlzCadastral = new ImovelSubCategAtlzCad();
						
						int idCategoria = (int) spnCategoriaImovel.getSelectedItemId();
						int idSubCategoria = (int) spnSubCategoriaImovel.getSelectedItemId(); 
						String qtdEconomias = edtQtdEconomias.getText().toString();
						
						Categoria categoria = new Categoria();
						categoria.setId(idCategoria);
						
						SubCategoria subCategoria = new SubCategoria();
						subCategoria.setCategoria(categoria);
						subCategoria.setId(idSubCategoria);
						
						imovelSubCategoriaAtlzCadastral.setCategoria(categoria);
						imovelSubCategoriaAtlzCadastral.setSubCategoria(subCategoria);
						imovelSubCategoriaAtlzCadastral.setQuantidadeEconomia(Integer.parseInt(qtdEconomias));
						
						//Volta para tela de Imovel
						intent = new Intent(CategoriaImovelInserirActivity.this, ImovelAbaActivity.class);
						
						intent.putExtra(ConstantesSistema.IMOVEL_SUBCATEGORIA_ATLZ_CADASTRAL, imovelSubCategoriaAtlzCadastral);

						
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
					//Volta para a aba de Imóvel
					setResult(Activity.RESULT_CANCELED);
					finish();
					
					TabsActivity.indicadorExibirMensagemErro = true;
				}
			});
			
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
	}
	
	/**
	 * Valida os Campos Obrigatórios
	 * 
	 * @author Davi Menezes
	 * @date 07/01/2013
	 */
	private boolean validaCamposObrigatorios(){
		long idCategoria = spnCategoriaImovel.getSelectedItemId();
		long idSubCategoria = spnSubCategoriaImovel.getSelectedItemId(); 
		String qtdEconomias = edtQtdEconomias.getText().toString();
		
		String campos  = "";
		
		if(idCategoria == ConstantesSistema.ITEM_INVALIDO ){
			campos += "Informe Categoria \n";
		}
		
		if(idSubCategoria == ConstantesSistema.ITEM_INVALIDO ){
			campos += "Informe Subcategoria \n";
		}
		
		if(qtdEconomias == null || qtdEconomias.equals("")){
			campos += "Informe Quantidade de Economias \n";
		}else if(qtdEconomias.equals("0") || (Integer.parseInt(qtdEconomias) == 0)){
			campos += "Quantidade de Economias deve somente conter números positivos \n";
		}
		
		if(campos.length() > 0){
			campos = campos.substring(0, campos.length() - 2);

			Util.exibirMensagemErro(CategoriaImovelInserirActivity.this, campos);
			
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Lista as Subcategorias de acordo com a Categoria Selecionada 
	 * @author Anderson
	 * @since 19/01/2013
	 *****/
	private void listarSubCategoria(long categoriaId){
		String where = SubCategoriaColunas.CATEGORIA_ID +" = " + categoriaId + " OR " + SubCategoriaColunas.ID + " = " + ConstantesSistema.ITEM_INVALIDO ;
		
			try {
				cursor = fachada.getCursor(SubCategoria.class, 
						SubCategoriaColunas.ID, 
						SubCategoriaColunas.DESCRICAO, 
						new SubCategoria().getNomeTabela(),
						where);
				
				startManagingCursor(cursor);
				
			} catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}

		spnSubCategoriaImovel.setAdapter(Util.getAdapter(cursor));
	}
	
	public void fecharCursor(Cursor cursor){
		if(cursor != null){
			cursor.close();
		}
	}
}
