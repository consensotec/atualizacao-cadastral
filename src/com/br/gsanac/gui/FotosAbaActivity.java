package com.br.gsanac.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.br.gsanac.R;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunas;
import com.br.gsanac.entidades.ClienteAtlzCadastral.ClienteAtlzCadastralColunas;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;


public class FotosAbaActivity extends BaseTabsActivity implements OnClickListener, OnLongClickListener {
	
	private static Fachada fachada = Fachada.getInstance();
	private Cursor cursor;
	private ImovelAtlzCadastral imovelAtlzCadastral;	
	private LinearLayout.LayoutParams llParams;
	private Integer fotoTipoId = 0;

	private TextView tvData;
	
	private LinearLayout llFrenteCasa;
	private ImageView 	 imgFrenteCasa;
	
	private LinearLayout llHidrometro;
	private ImageView	 imgHidrometro;
	
	private Button btnExcluirFrenteCasa;
	private Button btnExcluirHidrometro;
	private Spinner spnOcorrencias;
	
    private boolean primeiraVez   = true;
    
    private Integer idFotoFrenteCasa = null;
    private Integer idFotoHidrometro = null;
    
    /*** Armazena as linhas da TableLayout Ocorrencias***/
    private Map<Long, TableRow> tableRowsOcorrencias;
    
    /*** Tabela com os ocorrencias selecionadas ***/
    private TableLayout  tableLayoutOcorrencias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fotos_aba);
		
		try {
			
		    llParams = new LinearLayout.LayoutParams(100, 100);
			
			imovelAtlzCadastral = TabsActivity.imovel;
			
			//Data
			tvData = (TextView) findViewById(R.id.tvData);		
			String data = Util.convertDateToString(new Date());
			tvData.setText(data);
			
			//Fotos
			llFrenteCasa  = (LinearLayout) findViewById(R.id.llFrenteCasa);
			llFrenteCasa.setOnClickListener(this);
			imgFrenteCasa = (ImageView) findViewById(R.id.imgFrenteCasa);
			
			llHidrometro  = (LinearLayout) findViewById(R.id.llHidrometro);
			llHidrometro.setOnClickListener(this);		
			imgHidrometro = (ImageView) findViewById(R.id.imgHidrometro);
			
			//Botoes
			btnExcluirFrenteCasa = (Button) findViewById(R.id.btnExcluirFrenteCasa);
			btnExcluirFrenteCasa.setOnLongClickListener(this);
			btnExcluirHidrometro = (Button) findViewById(R.id.btnExcluirHidrometro);
			btnExcluirHidrometro.setOnLongClickListener(this);
			
			//Fotos
			carregarFoto(TabsActivity.fotoFrenteDaCasa, ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA);
			carregarFoto(TabsActivity.fotoHidrometro, ConstantesSistema.FOTO_TIPO_HIDROMETRO);
			
			if(TabsActivity.fotoFrenteDaCasa != null){
				if(TabsActivity.fotoFrenteDaCasa.getId() != null){
					idFotoFrenteCasa = TabsActivity.fotoFrenteDaCasa.getId();
				}
			}
			
			if(TabsActivity.fotoHidrometro != null){
				if(TabsActivity.fotoHidrometro.getId() != null){
					idFotoHidrometro = TabsActivity.fotoHidrometro.getId();
				}
			}
			
			//Ocorrencias
	        spnOcorrencias = (Spinner) findViewById(R.id.spnOcorrencias);
					
	        cursor = fachada.getCursor(CadastroOcorrencia.class,
	        						   CadastroOcorrenciaColunas.ID,
	        						   CadastroOcorrenciaColunas.DESCRICAO,
									   new CadastroOcorrencia().getNomeTabela());
			
	        spnOcorrencias.setAdapter(Util.getAdapter(cursor));
			
	        spnOcorrencias.setOnItemSelectedListener(new OnItemSelectedListener() {
	
	            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	
	                if (!primeiraVez) {
	                	inserirLinhaTabelaOcorrencias();
	                } else {
	                	primeiraVez = false;
	                }
	            }
	
	            public void onNothingSelected(AdapterView<?> arg0) {
	
	            }
	
	        });
			
	        tableRowsOcorrencias = new HashMap<Long, TableRow>();
	        
			if(TabsActivity.colecaoImovelOcorrencia == null){
				TabsActivity.colecaoImovelOcorrencia = new ArrayList<ImovelOcorrencia>();
			}else{
				carregarTabelaOcorrencias();
			}
      
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
		
	}
		
	private void carregarFoto(Foto foto, int tipo){

		if(foto != null){
			if(tipo == ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA){
				imgFrenteCasa.setImageBitmap(Util.getBitmapByPath(foto.getFotoPath()));
	        	imgFrenteCasa.setLayoutParams(llParams);
			}

			if(tipo == ConstantesSistema.FOTO_TIPO_HIDROMETRO){
				imgHidrometro.setImageBitmap(Util.getBitmapByPath(foto.getFotoPath()));
				imgHidrometro.setLayoutParams(llParams);
			}
		}else{
			if(tipo == ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA){
				imgFrenteCasa.setImageResource(R.drawable.camera);
			}else if(tipo == ConstantesSistema.FOTO_TIPO_HIDROMETRO){
				imgHidrometro.setImageResource(R.drawable.camera);
			}
		}
	}

	//Chama a  itent da camera, para tirar a foto
	@Override
	public void onClick(View v) {
		TabsActivity.indicadorExibirMensagemErro = false;
		
        v.setBackgroundResource(android.R.drawable.list_selector_background);

        switch (v.getId()) {
            case R.id.llFrenteCasa:
                fotoTipoId = ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA;
                break;
            case R.id.llHidrometro:
                fotoTipoId = ConstantesSistema.FOTO_TIPO_HIDROMETRO;
                break;
            default:
                Log.e(ConstantesSistema.LOG_TAG, "FOTO onClick " + getClass().getName() + " " + v.getId());
        }
        
        if (fotoTipoId != null && (fotoTipoId.equals(ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA) || fotoTipoId.equals(ConstantesSistema.FOTO_TIPO_HIDROMETRO))) {
        	TabsActivity.indicadorExibirMensagemErro = false;
        	Intent  intent;
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            startActivityForResult(intent, fotoTipoId);
        }
	}
	
	//Metodo executado, pelos botoes de excluir (foto e ocorrencia)
	@Override
	public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.btnExcluirFrenteCasa:
                fotoTipoId = ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA;
                break;
            case R.id.btnExcluirHidrometro:
                fotoTipoId = ConstantesSistema.FOTO_TIPO_HIDROMETRO;
                break;
            default:
            	fotoTipoId = 0;
        }
        
        if (fotoTipoId != 0) {
        	//Exclui Foto
        	
        	if(fotoTipoId == ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA){
        		if(TabsActivity.fotoFrenteDaCasa != null && Util.photoExistsByPath(TabsActivity.fotoFrenteDaCasa.getFotoPath())){
	        		Util.deletePhotoFileByPath(TabsActivity.fotoFrenteDaCasa.getFotoPath());
	        		this.carregarFoto(null, TabsActivity.fotoFrenteDaCasa.getFotoTipo());
	        		TabsActivity.fotoFrenteDaCasa = null;	        		
        		}
        	}else{
        		if(TabsActivity.fotoHidrometro != null && Util.photoExistsByPath(TabsActivity.fotoHidrometro.getFotoPath())){
	        		Util.deletePhotoFileByPath(TabsActivity.fotoHidrometro.getFotoPath());
	        		this.carregarFoto(null, TabsActivity.fotoHidrometro.getFotoTipo());
	        		TabsActivity.fotoHidrometro = null;
        		}
        	}
        	
//        	Foto foto = Util.pesquisarFotoBanco(imovelAtlzCadastral.getId(), fotoTipoId);
//
//            if (foto != null && Util.photoExistsByPath(foto.getFotoPath())) {
//
//                /** Remove o arquivo da foto **/
//                if (Util.deletePhotoFileByPath(foto.getFotoPath())) {
//                	try {
//						fachada.remover(foto);
//						this.carregarFoto(null, foto.getFotoTipo());
//					} catch (FachadaException e) {
//						 Log.e(ConstantesSistema.LOG_TAG, "onClick " + getClass().getName() + " " + v.getId());
//					}
//                }
//            }
        }else{
        	//Exclui Ocorrencia
        	this.removeLinhaTabelaOcorrencia(v);      	
        }
        
		return true;
	}
	
	
	//Metodo executado, depois de tirada a foto
	 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	 super.onActivityResult(requestCode, resultCode, data);
    	 
         // verifica se é o código de foto
         if (requestCode == ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA || requestCode == ConstantesSistema.FOTO_TIPO_HIDROMETRO) {
        	 fotoTipoId = requestCode;
        	 TabsActivity.indicadorExibirMensagemErro = true;
             // verifica se a foto foi salva
             if (resultCode == RESULT_OK) {
            	 try { 
	                 // pega a foto
	                 Bundle bundle = data.getExtras();
	                 
	                 //inseri o id do imovel, caso ele seja novo
	                 if(imovelAtlzCadastral.getId() == null || imovelAtlzCadastral.getId().equals("")){
	                	 imovelAtlzCadastral.setId(fachada.pesquisarMaiorIdImovel() + 1);
	                 }
	                 
	                 File fotoFile = Util.getPhotoFile(imovelAtlzCadastral.getId(), fotoTipoId);
	                 
	                 FileOutputStream out = new FileOutputStream(fotoFile);
	                 Bitmap bmp = (Bitmap) bundle.get("data");
	
	                 // transforma o arquivo na foto tirada e grava o bmp
	                 bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
	                 
	                 //Caso a foto tenha sido salva no Tablet, salva os meta dados dela, no banco.
	                 if(Util.photoExistsByPath(fotoFile.getAbsolutePath())){
	                	 
	                	 Foto foto = new Foto();
	                	 
	                	 if(fotoTipoId == ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA){
	                		foto.setId(idFotoFrenteCasa);
	                	 }else{
	                		foto.setId(idFotoHidrometro);
	                	 }
		                 
		                 foto.setFotoTipo(fotoTipoId);
		                 foto.setImovelAtlzCadastral(imovelAtlzCadastral);
		                 foto.setUltimaAlteracao(new Date());
		                 foto.setFotoPath(fotoFile.getAbsolutePath());
		                 foto.setIndicadorTransmitido(2);
		                 
		                 if(foto.getFotoTipo() == ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA){
		                	 TabsActivity.fotoFrenteDaCasa = foto;
		                 }else{
		                	 TabsActivity.fotoHidrometro = foto;
		                 }
                                             
                       this.carregarFoto(foto, foto.getFotoTipo());
	                 }

                 } catch (Exception e) {
                	 Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
                 }
             }
             
         }
         TabsActivity.indicadorExibirMensagemErro = true;
    }
	 
   /**
     * Carregar Tabela
     * 
     * @author Anderson Cabral
     * @since 07/01/2013
     */
    private void carregarTabelaOcorrencias() {
    	for(ImovelOcorrencia imovelOcorrencia : TabsActivity.colecaoImovelOcorrencia){
    		
    		if (!tableRowsOcorrencias.containsKey(imovelOcorrencia.getCadastroOcorrencia().getId())) {
	
				tableLayoutOcorrencias= (TableLayout) findViewById(R.id.tabelaOcorrencias);
	
	            int id = (int) imovelOcorrencia.getCadastroOcorrencia().getId();
	
	            /*
	             * 
	             */
	            TableRow tr = new TableRow(this);
	            tr.setGravity(Gravity.CENTER_VERTICAL);
	            tr.setId(id);
	            tr.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
	                                                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	            tr.setTag(ConstantesSistema.BOTAO_REMOVER_OCORRENCIA_ID);
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
	            
				//Pesquisar Cadastro Ocorrencia
	            String 	selection = CadastroOcorrenciaColunas.ID + "=?";
	            String[] selectionArgs = new String[] {
	                String.valueOf(imovelOcorrencia.getCadastroOcorrencia().getId())
	            };
	            
	            CadastroOcorrencia cadOcorrencia = new CadastroOcorrencia();
	            
	            try {
					cadOcorrencia = (CadastroOcorrencia) fachada.pesquisar(cadOcorrencia, selection, selectionArgs);
				} catch (FachadaException fe) {
					Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
				}
	            
	            imovelOcorrencia.setCadastroOcorrencia(cadOcorrencia);
	            
	            tvActionSelected.setText(cadOcorrencia.getDescricao());
	            tvActionSelected.setTextSize(18);
	            tvActionSelected.setTag(ConstantesSistema.BOTAO_REMOVER_OCORRENCIA_ID);
	            tvActionSelected.setOnLongClickListener(this);
	
	            /*
	             * 
	             */
	            ImageView imgRemoveAction = new ImageView(this);
	            imgRemoveAction.setId(id);
	            imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_OCORRENCIA_ID);
	            imgRemoveAction.setImageResource(R.drawable.btnremover);
	            imgRemoveAction.setPadding(0, 20, 30, 10);
//	            imgRemoveAction.setOnLongClickListener(this);
	            
	            /*
	             * 
	             */
	            tr.addView(tvActionSelected);
	            tr.addView(imgRemoveAction);
	
	            tableRowsOcorrencias.put((long) id, tr);
	
	            this.populaTabelaOcorrencias();
	
	        } else {
	            primeiraVez = true;
	        }
    	}
    } 
	 
   /**
     * Inseri uma linha na lista de Ocorrencias
     * 
     * @author Anderson Cabral
     * @since 07/01/2013
     */
    private void inserirLinhaTabelaOcorrencias() {

		if (!tableRowsOcorrencias.containsKey(spnOcorrencias.getSelectedItemId())) {
	
				tableLayoutOcorrencias= (TableLayout) findViewById(R.id.tabelaOcorrencias);
	
	            int id = (int) spnOcorrencias.getSelectedItemId();
	
	            /*
	             * 
	             */
	            TableRow tr = new TableRow(this);
	            tr.setGravity(Gravity.CENTER_VERTICAL);
	            tr.setId(id);
	            tr.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
	                                                            android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	            tr.setTag(ConstantesSistema.BOTAO_REMOVER_OCORRENCIA_ID);
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
	
	            Cursor c = (Cursor) spnOcorrencias.getSelectedItem();
	            String descricao = c.getString(c.getColumnIndex(ConstantesSistema.COLUMN_DESCRIPTION_ALIAS));
	            tvActionSelected.setText(descricao);
	            tvActionSelected.setTextSize(18);
	            tvActionSelected.setTag(ConstantesSistema.BOTAO_REMOVER_OCORRENCIA_ID);
	            tvActionSelected.setOnLongClickListener(this);
	
	            /*
	             * 
	             */
	            ImageView imgRemoveAction = new ImageView(this);
	            imgRemoveAction.setId(id);
	            imgRemoveAction.setTag(ConstantesSistema.BOTAO_REMOVER_OCORRENCIA_ID);
	            imgRemoveAction.setImageResource(R.drawable.btnremover);
	            imgRemoveAction.setPadding(0, 20, 30, 10);
//	            imgRemoveAction.setOnLongClickListener(this);
	
	            spnOcorrencias.setSelection(0);
	            primeiraVez = true;
	            
	            /*
	             * 
	             */
	            tr.addView(tvActionSelected);
	            tr.addView(imgRemoveAction);
	
	            tableRowsOcorrencias.put((long) id, tr);
	
	            this.populaTabelaOcorrencias();
	            
	            //Adiciona o imovelOcorrencia na lista
	            ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
				imovelOcorrencia.setImovelAtlzCadastral(imovelAtlzCadastral);
				
				
		        String selection = CadastroOcorrenciaColunas.ID + "=?";
		        String[] selectionArgs = new String[] {
		        		String.valueOf(id)
		        };
		        
		        CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
		        try {
					cadastroOcorrencia = (CadastroOcorrencia) Fachada.getInstance().pesquisar(cadastroOcorrencia,selection, selectionArgs );
				} catch (FachadaException e) {
					e.printStackTrace();
				}
				
				imovelOcorrencia.setCadastroOcorrencia(cadastroOcorrencia);
	            
	            TabsActivity.colecaoImovelOcorrencia.add(imovelOcorrencia);
	
	        } else {
	            Util.showMessage(this, getString(R.string.error_ocorrencia_ja_selecionado), Toast.LENGTH_SHORT);
	            spnOcorrencias.requestFocus();
	            spnOcorrencias.setSelection(0);
	            primeiraVez = true;
	        }
    }
    
    /**
     * <p>
     * Popula a tabela Ocorrencias
     * </p>
     * 
     * @author Anderson Cabral
     * @since 07/01/2013
     */
    @SuppressWarnings("deprecation")
	private void populaTabelaOcorrencias() {
        // Limpa
        if (tableLayoutOcorrencias.getChildCount() > 0) {
        	tableLayoutOcorrencias.removeAllViews();
        }

        Set<Long> keys = tableRowsOcorrencias.keySet();

        TableRow tr = null;

        for (Long key : keys) {
            tr = tableRowsOcorrencias.get(key);
            tr.setBackgroundColor((tableLayoutOcorrencias.getChildCount() % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F"));
            tableLayoutOcorrencias.addView(tr, new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                                                                 android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
    
    /**
     * Remove da tabela o Ocorrencia selecionado
     * 
     * @author Anderson Cabral
     * @since 07/01/2013
     */
    private void removeLinhaTabelaOcorrencia(View v) {

        long id = v.getId();

        if (tableRowsOcorrencias.containsKey(id)) {

        	tableRowsOcorrencias.remove(id);

            Util.showMessage(this, getString(R.string.msg_ocorrencia_removida_sucesso), Toast.LENGTH_SHORT);

            this.populaTabelaOcorrencias();
            
            //Remove o ImovelOcorrencia da Lista
            ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
			imovelOcorrencia.setImovelAtlzCadastral(imovelAtlzCadastral);
			
			CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
			cadastroOcorrencia.setId(String.valueOf(id));
			
			imovelOcorrencia.setCadastroOcorrencia(cadastroOcorrencia);
            
            TabsActivity.colecaoImovelOcorrencia.remove(imovelOcorrencia);
        }
    }
    
	@Override
	protected void onPause() {
		super.onPause();
		try{
			
			if ( TabsActivity.indicadorExibirMensagemErro ) {
				
				//Validacao
				String mensagemErro = fachada.validarAbaFotos(imovelAtlzCadastral.getId());
				
		    	if ( mensagemErro != null && !mensagemErro.equals("") ) {
		    		Util.exibirMensagemErro(FotosAbaActivity.this, mensagemErro);           	
		    	}
			}
			
		} catch (FachadaException e) {
			Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
		}
	}

	 		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotos_aba, menu);
		return true;
	}
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
		this.fecharCursor(cursor);
    }

}
