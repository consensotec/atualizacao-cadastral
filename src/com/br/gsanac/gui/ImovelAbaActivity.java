package com.br.gsanac.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
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
import com.br.gsanac.entidades.Categoria;
import com.br.gsanac.entidades.Categoria.CategoriaColunas;
import com.br.gsanac.entidades.FonteAbastecimento;
import com.br.gsanac.entidades.FonteAbastecimento.FonteAbastecimentos;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelPerfil;
import com.br.gsanac.entidades.ImovelPerfil.ImovelPerfilColunas;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.PavimentoCalcada;
import com.br.gsanac.entidades.PavimentoCalcada.PavimentoCalcadas;
import com.br.gsanac.entidades.PavimentoRua;
import com.br.gsanac.entidades.PavimentoRua.PavimentoRuas;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.entidades.SubCategoria;
import com.br.gsanac.entidades.SubCategoria.SubCategoriaColunas;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

/**
 * [UC 1412] - Manter Dados da Aba Imóvel do Tablet
 * 
 * @author Davi Menezes
 * @date 07/01/2013
 *
 */
public class ImovelAbaActivity extends BaseTabsActivity implements OnLongClickListener {

	private static Fachada fachada = Fachada.getInstance();
	
	private Cursor cursor;
	private Intent intent;
	
	private Spinner spnPerfilImovel;
	
	private RadioGroup  radioGroupTarifaSocial;
	private RadioButton radioTarifaSocialSim;
	private RadioButton radioTarifaSocialNao;
	
	private EditText edtNumMedidorEnergia;
	private EditText edtNumMoradores;
	
	private Spinner spnPavimentoRua;
	private Spinner spnPavimentoCalcada;
	private Spinner spnFonteAbastecimento;
	
	private Button btAdicionarCategoria;
	
	/*** Armazena as linhas do TableLayout Categoria ***/
	private Map<ImovelSubCategAtlzCad, TableRow> tableRowsCategoria;
    
    /*** Tabela com as Categorias selecionados ***/
    private TableLayout tableLayoutCategoria;
	
	private ImovelAtlzCadastral imovelAtlzCadastral;
	
	private ArrayList<ImovelSubCategAtlzCad> colImoveisSubCategoria = new ArrayList<ImovelSubCategAtlzCad>();
	
	/** Called when the activity is first created. */
    int mStackLevel = 1;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.imovel_aba);
    	
		try {
			SistemaParametros sistemaParametros = new SistemaParametros();
			sistemaParametros = (SistemaParametros) fachada.pesquisar(sistemaParametros, null, null);
			
			//Perfil Imóvel
			spnPerfilImovel = (Spinner) findViewById(R.id.spnPerfilImovel);
			cursor = fachada.getCursor(ImovelPerfil.class, 
									   ImovelPerfilColunas.ID, 
									   ImovelPerfilColunas.DESCRICAO, 
									   new ImovelPerfil().getNomeTabela());
			
			spnPerfilImovel.setAdapter(Util.getAdapter(cursor));
			
			Util.selecionarItemCombo(spnPerfilImovel, ImovelPerfil.NORMAL);
			spnPerfilImovel.setEnabled(false);
			
			//Numero Medidor
			edtNumMedidorEnergia = (EditText) findViewById(R.id.edtNumMedidorEnergia);
			edtNumMedidorEnergia.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(10), Util.filterReplaceCaracteresEspeciaisEEspaco()});
			
			//Pavimento Rua
			spnPavimentoRua = (Spinner) findViewById(R.id.spnPavimentoRua);
			cursor = fachada.getCursor(PavimentoRua.class, 
									   PavimentoRuas.ID, 
									   PavimentoRuas.DESCRICAO, 
									   new PavimentoRua().getNomeTabela());
			
			spnPavimentoRua.setAdapter(Util.getAdapter(cursor));
			
			//Pavimento Calçada
			spnPavimentoCalcada = (Spinner) findViewById(R.id.spnPavimentoCalcada);
			cursor = fachada.getCursor(PavimentoCalcada.class, 
									   PavimentoCalcadas.ID, 
									   PavimentoCalcadas.DESCRICAO, 
									   new PavimentoCalcada().getNomeTabela());
			
			spnPavimentoCalcada.setAdapter(Util.getAdapter(cursor));
			
			//Fonte de Abastecimento
			spnFonteAbastecimento = (Spinner) findViewById(R.id.spnFonteAbastecimento);
			cursor = fachada.getCursor(FonteAbastecimento.class, 
									   FonteAbastecimentos.ID, 
									   FonteAbastecimentos.DESCRICAO, 
									   new FonteAbastecimento().getNomeTabela());
			
			//Tarifa Social
			radioTarifaSocialSim   = (RadioButton) findViewById(R.id.radioTarifaSocialSim);
			radioTarifaSocialNao   = (RadioButton) findViewById(R.id.radioTarifaSocialNao);
			radioGroupTarifaSocial = (RadioGroup) findViewById(R.id.radioGroupTarifaSocial);
			
			spnFonteAbastecimento.setAdapter(Util.getAdapter(cursor));
			
			/** Chama a tela de Inserir Categoria **/
			btAdicionarCategoria = (Button) findViewById(R.id.btAdicionarCategoria);
			
			btAdicionarCategoria.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {	
					TabsActivity.indicadorExibirMensagemErro = false;
					
                    intent = new Intent(ImovelAbaActivity.this, CategoriaImovelInserirActivity.class);

                    startActivityForResult(intent, ConstantesSistema.IMOVEL_ABA_REQUEST_CODE);
				}
			});
			
			/*** HashMap de Categoria ***/
			tableRowsCategoria = new HashMap<ImovelSubCategAtlzCad, TableRow>();
			
			/** Table Layout Categoria **/
			tableLayoutCategoria = (TableLayout) findViewById(R.id.tableLayoutCategorias);
			tableLayoutCategoria.setVisibility(View.VISIBLE);
			
			imovelAtlzCadastral = TabsActivity.imovel;
			
			if(TabsActivity.primeiraVezAbaImovel){
	 			   	
	 			   	radioTarifaSocialSim = (RadioButton) findViewById(R.id.radioTarifaSocialSim);
					radioTarifaSocialNao = (RadioButton) findViewById(R.id.radioTarifaSocialNao);
					
					if(imovelAtlzCadastral == null || imovelAtlzCadastral.getImovelId() == null){
						radioTarifaSocialSim.setChecked(false);
						radioTarifaSocialNao.setChecked(true);
						imovelAtlzCadastral.setIndicadorTarifaSocial(2);
					}
					
					TabsActivity.primeiraVezAbaImovel = false;
			}
				
			if(imovelAtlzCadastral.getImovelPerfil() != null){
				//Carrega na combobox o valor do Imóvel Perfil
				Util.selecionarItemCombo(spnPerfilImovel, imovelAtlzCadastral.getImovelPerfil().getId());
				spnPerfilImovel.setEnabled(false);
		   	}
			
			if(imovelAtlzCadastral.getIndicadorTarifaSocial() != null){
				if(imovelAtlzCadastral.getIndicadorTarifaSocial().equals(ConstantesSistema.SIM)){
					radioTarifaSocialSim.setChecked(true);
					radioTarifaSocialNao.setChecked(false);
					imovelAtlzCadastral.setIndicadorTarifaSocial(1);
				}else{
					radioTarifaSocialSim.setChecked(false);
					radioTarifaSocialNao.setChecked(true);
					imovelAtlzCadastral.setIndicadorTarifaSocial(2);
				}
			}
			
			if(imovelAtlzCadastral.getNumeroMedidorEnergia() != null && !imovelAtlzCadastral.getNumeroMedidorEnergia().equals("")){
				edtNumMedidorEnergia.setText(imovelAtlzCadastral.getNumeroMedidorEnergia().toString());
			}
			
			edtNumMoradores = (EditText) findViewById(R.id.edtNumMoradores);
			if(imovelAtlzCadastral.getNumeroMorador() != null && !imovelAtlzCadastral.getNumeroMorador().equals("")){
				edtNumMoradores.setText(imovelAtlzCadastral.getNumeroMorador().toString());
			}
			
			if(imovelAtlzCadastral.getPavimentoRua() != null){
				//Carrega na combobox o valor de Pavimento Rua
				for(int i = 0 ; i < spnPavimentoRua.getCount(); i++){
					long itemIdAtPosition2 = spnPavimentoRua.getItemIdAtPosition(i);
					if(itemIdAtPosition2 == imovelAtlzCadastral.getPavimentoRua().getId() && (itemIdAtPosition2 != 0)){
						spnPavimentoRua.setSelection(i);
						break;
					}
				}
			}
			
			if(imovelAtlzCadastral.getPavimentoCalcada() != null){
				//Carrega na combobox o valor de Pavimento Calçada
				for(int i = 0; i < spnPavimentoCalcada.getCount(); i++){
					long itemIdAtPosition2 = spnPavimentoCalcada.getItemIdAtPosition(i);
					if(itemIdAtPosition2 == imovelAtlzCadastral.getPavimentoCalcada().getId() && (itemIdAtPosition2 != 0)){
						spnPavimentoCalcada.setSelection(i);
						break;
					}
				}
			}
			
			if(imovelAtlzCadastral.getFonteAbastecimento() != null){
				//Carrega na combobox o valor de Fonte de Abastecimento
				for(int i = 0; i < spnFonteAbastecimento.getCount(); i++){
					long itemIdAtPosition2 = spnFonteAbastecimento.getItemIdAtPosition(i);
					if(itemIdAtPosition2 == imovelAtlzCadastral.getFonteAbastecimento().getId() && (itemIdAtPosition2 != 0)){
						spnFonteAbastecimento.setSelection(i);
						break;
					}
				}
			}
            
			//carrega as subCategorias do imovel na tabela de subcategorias
			if(TabsActivity.colImoveisSubCategoria != null){
				//Adicionar uma Linha de Categoria
				for(ImovelSubCategAtlzCad imovelSubCatAtlzCadastral : TabsActivity.colImoveisSubCategoria)
				this.inserirLinhaTabelaCategorias(imovelSubCatAtlzCadastral);
			}
				
			
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	
    	radioTarifaSocialSim = (RadioButton) findViewById(R.id.radioTarifaSocialSim);
    	radioTarifaSocialNao = (RadioButton) findViewById(R.id.radioTarifaSocialNao);
    	
    	edtNumMedidorEnergia = (EditText) findViewById(R.id.edtNumMedidorEnergia);
    	edtNumMedidorEnergia.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(10), Util.filterReplaceCaracteresEspeciaisEEspaco()});
    	edtNumMoradores = (EditText) findViewById(R.id.edtNumMoradores);
    	
    	ImovelPerfil imovelPerfil = new ImovelPerfil();
    	if(spnPerfilImovel.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
    		imovelPerfil.setId((int) spnPerfilImovel.getSelectedItemId());
    	}else{
    		imovelPerfil = null;
    	}
    	PavimentoRua pavimentoRua = new PavimentoRua();
    	
    	if(spnPavimentoRua.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
    		pavimentoRua.setId((int) spnPavimentoRua.getSelectedItemId());
    	}else{
    		pavimentoRua = null;
    	}
    	
    	PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
    	if(spnPavimentoCalcada.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
    		pavimentoCalcada.setId((int) spnPavimentoCalcada.getSelectedItemId());
    	}else{
    		pavimentoCalcada = null;
    	}
    	
    	FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
    	if(spnFonteAbastecimento.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
    		fonteAbastecimento.setId((int) spnFonteAbastecimento.getSelectedItemId());
    	}else{
    		fonteAbastecimento = null;
    	}
    	
    	String numMedidorEnergia = null;
    	if(edtNumMedidorEnergia.getText() != null && !edtNumMedidorEnergia.getText().toString().equals("")){
    		numMedidorEnergia = edtNumMedidorEnergia.getText().toString();
    	}
    	
    	Integer numMoradores = null;
    	if(edtNumMoradores.getText() != null && !edtNumMoradores.getText().toString().equals("")){
    		numMoradores = Integer.parseInt(edtNumMoradores.getText().toString());
    	}
    	
    	imovelAtlzCadastral = TabsActivity.imovel;
    	
    	imovelAtlzCadastral.setImovelPerfil(imovelPerfil);
    	imovelAtlzCadastral.setNumeroMedidorEnergia(numMedidorEnergia);
    	imovelAtlzCadastral.setNumeroMorador(numMoradores);
    	imovelAtlzCadastral.setPavimentoRua(pavimentoRua);
    	imovelAtlzCadastral.setPavimentoCalcada(pavimentoCalcada);
    	imovelAtlzCadastral.setFonteAbastecimento(fonteAbastecimento);
    	
    	if(radioGroupTarifaSocial.getCheckedRadioButtonId() == R.id.radioTarifaSocialSim){
    		imovelAtlzCadastral.setIndicadorTarifaSocial(1);
    	}else{
    		imovelAtlzCadastral.setIndicadorTarifaSocial(2);
    	}
    	
    	TabsActivity.imovel = imovelAtlzCadastral;
    	TabsActivity.colImoveisSubCategoria = colImoveisSubCategoria;
    	
    	if ( TabsActivity.indicadorExibirMensagemErro ) {
	    	try {
				String mensagemErro = fachada.validarAbaImovel(imovelAtlzCadastral);
				
				if(mensagemErro != null && !mensagemErro.equals("")){
					Util.exibirMensagemErro(ImovelAbaActivity.this, mensagemErro);
				}
				
			} catch (FachadaException e) {
				Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
			}
    	}
    }
    
    /**
     * Inserir uma linha na tabela de Categorias
     * 
     * @author Davi Menezes
     * @date 07/01/2013
     */
    private void inserirLinhaTabelaCategorias(ImovelSubCategAtlzCad imovelSubCategoria){
    	
    	if(!colImoveisSubCategoria.contains(imovelSubCategoria)){
    		tableLayoutCategoria = (TableLayout) findViewById(R.id.tableLayoutCategorias);
    		
    		Integer idCategoria = imovelSubCategoria.getCategoria().getId();
    		Integer idSubCategoria = imovelSubCategoria.getSubCategoria().getId();
    		
    		String aux = String.valueOf(idCategoria) + "" + String.valueOf(idSubCategoria);
    		Integer id = Integer.parseInt(aux);
    		
    		//Pesquisar Categoria
            String selection = CategoriaColunas.ID + "=?";
            String[] selectionArgs = new String[] {
                String.valueOf(idCategoria)
            };
            
            //Pesquisar SubCategoria
            String selection2 = SubCategoriaColunas.ID + "=?" + " AND " + SubCategoriaColunas.CATEGORIA_ID + "=?";
            String[] selectionArgs2 = new String[] {
                    String.valueOf(idSubCategoria),
            		String.valueOf(idCategoria)
                };
            
            Categoria categoria = new Categoria();
            SubCategoria subCategoria = new SubCategoria();
            try {
				categoria = (Categoria) fachada.pesquisar(categoria, selection, selectionArgs);
				subCategoria = (SubCategoria) fachada.pesquisar(subCategoria, selection2, selectionArgs2);
			} catch (FachadaException e) {
				Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
			}
  			 
    		TableRow tr = new TableRow(this);
    		tr.setId(id);
    		tr.setGravity(Gravity.CENTER_VERTICAL);
    		tr.setTag(ConstantesSistema.BOTAO_REMOVER_CATEGORIA_ID);
    		tr.setOnLongClickListener(this);
    		
    		tableLayoutCategoria.addView(tr);
    		
    		TextView tvActionSelected = new TextView(this);
            tvActionSelected.setGravity(Gravity.CENTER);
            tvActionSelected.setTextColor(Color.BLACK);
            tvActionSelected.setHeight(50);
    		tvActionSelected.setText(categoria.getDescricao());
    		tvActionSelected.setTextSize(18);
            
            TextView tvActionSelected2 = new TextView(this);
            tvActionSelected2.setId(idSubCategoria);
            tvActionSelected2.setGravity(Gravity.CENTER);
            tvActionSelected2.setTextColor(Color.BLACK);
            tvActionSelected2.setHeight(50);
            tvActionSelected2.setMaxWidth(300);
            tvActionSelected2.setText(subCategoria.getDescricao());
            tvActionSelected2.setTextSize(18);
            
            TextView tvActionSelected3 = new TextView(this);
            tvActionSelected3.setId(idSubCategoria);
            tvActionSelected3.setGravity(Gravity.CENTER);
            tvActionSelected3.setTextColor(Color.BLACK);
            tvActionSelected3.setHeight(50);
            tvActionSelected3.setText(imovelSubCategoria.getQuantidadeEconomia().toString());
            tvActionSelected3.setTextSize(18);
            
            ImageView imgRemoveAction = new ImageView(this);
            imgRemoveAction.setId(id);
            imgRemoveAction.setImageDrawable(getResources().getDrawable(R.drawable.btnremover));
            imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_CATEGORIA_ID);
            
            tr.addView(tvActionSelected);
            tr.addView(tvActionSelected2);
            tr.addView(tvActionSelected3);
            tr.addView(imgRemoveAction);
            
            tr.setBackgroundColor((tableRowsCategoria.size() % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F"));
            
            tableRowsCategoria.put(imovelSubCategoria, tr);
            colImoveisSubCategoria.add(imovelSubCategoria);
            tableLayoutCategoria.invalidate();

    	}else{
    		Util.showMessage(this, getString(R.string.error_categoria_ja_selecionada), Toast.LENGTH_SHORT);
    	}
    }
    
    /**
     * Remove da tabela a Categoria Selecionada
     * 
     * @author Davi Menezes
     * @date 07/01/2013
     */
    private void removerLinhaTabelaCategoria(View v){
    	Integer id = v.getId();
    	
    	Integer idCategoria = Integer.parseInt(id.toString().substring(0, 1));
    	Integer idSubcategoria = Integer.parseInt(id.toString().substring(1));
    	
    	int pos = 3;
    	
    	ImovelSubCategAtlzCad imovelSubCategoria = new ImovelSubCategAtlzCad();
    	if(colImoveisSubCategoria != null && !colImoveisSubCategoria.isEmpty()){
    		for(ImovelSubCategAtlzCad aux : colImoveisSubCategoria){
    			if(aux.getCategoria().getId().equals(idCategoria) && 
    					aux.getSubCategoria().getId().equals(idSubcategoria)){
    				imovelSubCategoria = aux;
    				break;
    			}
    			pos++;
    		}
    		
    		tableRowsCategoria.remove(imovelSubCategoria);
    		colImoveisSubCategoria.remove(imovelSubCategoria);
    		
    		//Verificar Uma forma melhor de fazer
			for(int i = pos; i <= tableLayoutCategoria.getChildCount(); i++){
				TableRow tableRow = (TableRow) tableLayoutCategoria.getChildAt(i);
				
	    		if(tableRow.isPressed()){
	    			tableRow.setVisibility(View.GONE);
	    			break;
	    		}
			}
    		
    		tableLayoutCategoria.invalidate();
    		
    		coloreLinha();
    		
    		Util.showMessage(this, getString(R.string.msg_categoria_removida_sucesso), Toast.LENGTH_SHORT);
    	}
    	
    	/*
    	if(tableRowsCategoria.containsKey(imovelSubCategoria)){
    		tableRowsCategoria.remove(imovelSubCategoria);
    		colImoveisSubCategoria.remove(imovelSubCategoria);
    		
    		Util.showMessage(this, getString(R.string.msg_categoria_removida_sucesso), Toast.LENGTH_SHORT);
    		
    		this.populaTabelaCategorias();
    	}
    	*/
    }
    
    /**
     * Colore as linhas da tabela categoria
     * 
     * @author Anderson Cabral
     * @date 21/01/2013
     */
	private void coloreLinha(){
    	int cont = 1;
    	
		for(int i = 3; i <= tableLayoutCategoria.getChildCount(); i++){
			TableRow tableRow = (TableRow) tableLayoutCategoria.getChildAt(i);
			
			if(tableRow != null && tableRow.getVisibility() != View.GONE){
				cont = cont + 1;
				tableRow.setBackgroundColor((cont % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F"));
			}
		}
    }
	
	@Override
	public boolean onLongClick(View v) {
		if(v != null && v.getTag() != null && v.getTag().equals(ConstantesSistema.BOTAO_REMOVER_CATEGORIA_ID)){
			this.removerLinhaTabelaCategoria(v);
		}
		
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if(resultCode == Activity.RESULT_OK){
			ImovelSubCategAtlzCad imovelSubCategoriaAtlzCadastral = (ImovelSubCategAtlzCad)
					intent.getSerializableExtra(ConstantesSistema.IMOVEL_SUBCATEGORIA_ATLZ_CADASTRAL);
			
			this.inserirLinhaTabelaCategorias(imovelSubCategoriaAtlzCadastral);
		}
		
		TabsActivity.indicadorExibirMensagemErro = true;
	}
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
		this.fecharCursor(cursor);
    }
	
}
