package com.br.gsanac.gui;

import com.br.gsanac.R;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroLocalInst;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.HidrometroLocalInst.HidrometroLocalInstColunas;
import com.br.gsanac.entidades.HidrometroProtecao;
import com.br.gsanac.entidades.HidrometroProtecao.HidrometroProtecaoColunas;
import com.br.gsanac.entidades.LigacaoAguaSituacao;
import com.br.gsanac.entidades.LigacaoAguaSituacao.LigacaoAguaSituacaos;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao.LigacaoEsgotoSituacaos;
import com.br.gsanac.entidades.MedicaoTipo;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

import android.os.Bundle;
import android.database.Cursor;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class LigacaoAbaActivity extends BaseTabsActivity {
	
	private static Fachada fachada = Fachada.getInstance();
	private Cursor cursor;
	
	private ImovelAtlzCadastral imovelAtlzCadastral;
	private HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad;
	
	/**Componentes da tela**/
    private Spinner spnSituacaoAgua;
    private Spinner spnSituacaoEsgoto;
    private RadioGroup radioGroupHidrAgua;
    private EditText edtNumeroHidrometro;
    private Spinner spnLocalInstalacao;
    private Spinner spnTipoProtecao;
    private RadioGroup radioGroupCavalete;
    private RadioButton radioCavaleteSim;
    private RadioButton radioCavaleteNao;
    private EditText edtLeitura;
    private EditText edtObservacao;
    


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ligacao_aba);
		
		try {
			/** Situacao Agua **/
			spnSituacaoAgua = (Spinner) findViewById(R.id.spnSituacaoAgua);
							
			cursor = fachada.getCursor(LigacaoAguaSituacao.class,
									   LigacaoAguaSituacaos.ID,
									   LigacaoAguaSituacaos.DESCRICAO,
									   new LigacaoAguaSituacao().getNomeTabela());
			
			spnSituacaoAgua.setAdapter(Util.getAdapter(cursor));
			
			/** Situacao Esgoto **/
			spnSituacaoEsgoto = (Spinner) findViewById(R.id.spnSituacaoEsgoto);
							
			cursor = fachada.getCursor(LigacaoEsgotoSituacao.class,
									   LigacaoEsgotoSituacaos.ID,
									   LigacaoEsgotoSituacaos.DESCRICAO,
									   new LigacaoEsgotoSituacao().getNomeTabela());
			
			spnSituacaoEsgoto.setAdapter(Util.getAdapter(cursor));
			
			/** Hidrometro Agua **/
			radioGroupHidrAgua = (RadioGroup) findViewById(R.id.radioGroupHidrAgua);
			
			radioGroupHidrAgua.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					
					if(checkedId == R.id.radioHidrometroAguaSim){
						habilitarTodosCampos(true);
					}else{
						limparCamposHidrometro();
						habilitarTodosCampos(false);
					}				
				}
			});
			
			/** Numero Hidrometro **/
			edtNumeroHidrometro = (EditText) findViewById(R.id.edtNumeroHidrometro);
			edtNumeroHidrometro.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(10), Util.filterReplaceCaracteresEspeciaisEEspaco()});
			
			/** Local de Instalacao **/
			spnLocalInstalacao = (Spinner) findViewById(R.id.spnLocalInstalacao);
			
			cursor = fachada.getCursor(HidrometroLocalInst.class,
									   HidrometroLocalInstColunas.ID,
									   HidrometroLocalInstColunas.DESCRICAO,
									   new HidrometroLocalInst().getNomeTabela());
			
			spnLocalInstalacao.setAdapter(Util.getAdapter(cursor));
			
			/** Tipo de Protecao **/
			spnTipoProtecao = (Spinner) findViewById(R.id.spnTipoProtecao);
			
			cursor = fachada.getCursor(HidrometroProtecao.class,
									   HidrometroProtecaoColunas.ID,
									   HidrometroProtecaoColunas.DESCRICAO,
									   new HidrometroProtecao().getNomeTabela());
			
			spnTipoProtecao.setAdapter(Util.getAdapter(cursor));
			
			/** Cavalete **/
			radioGroupCavalete = (RadioGroup) findViewById(R.id.radioGroupCavalete);
			radioCavaleteSim   = (RadioButton) findViewById(R.id.radioCavaleteSim);
			radioCavaleteNao   = (RadioButton) findViewById(R.id.radioCavaleteNao);
			
			/** Leitura **/
			edtLeitura = (EditText) findViewById(R.id.edtLeitura);
			
			/** Leitura **/
			edtLeitura = (EditText) findViewById(R.id.edtLeitura);
			
			/** Observacao **/
			edtObservacao = (EditText) findViewById(R.id.edtObservacao);
			edtObservacao.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(400), Util.filterReplaceCaracteresEspeciais()});
			
			this.habilitarTodosCampos(false);			
			
			/** Exibe os dados do Imóvel ***/
			imovelAtlzCadastral = TabsActivity.imovel;
			 
			if ( TabsActivity.primeiraVezAbaLigacao ) {
          	   hidrometroInstHistAtlzCad = null;
          	   
          	   //Hidrometro Agua
          	   radioGroupHidrAgua.check(R.id.radioHidrometroAguaNao);
          	   
          	   limparCamposHidrometro();
          	   habilitarTodosCampos(false);
          	   TabsActivity.primeiraVezAbaLigacao = false;
      	    } 
			
			
				
			//Carrega na combobox a Situacao de Agua do Imovel
		    if (imovelAtlzCadastral.getLigAguaSituacao() != null 
				   && imovelAtlzCadastral.getLigAguaSituacao().getId() != null) {
			   Util.selecionarItemCombo(spnSituacaoAgua, imovelAtlzCadastral.getLigAguaSituacao().getId().longValue());
            }
			   
			//Carrega na combobox a Situacao de Esgoto do Imovel
		    if (imovelAtlzCadastral.getLigEsgotoSituacao() != null 
				   && imovelAtlzCadastral.getLigEsgotoSituacao().getId() != null) {
			    Util.selecionarItemCombo(spnSituacaoEsgoto, imovelAtlzCadastral.getLigEsgotoSituacao().getId().longValue());
            }			   
			   
			hidrometroInstHistAtlzCad = TabsActivity.hidrometroInstalacaoHist;
               
			if ( hidrometroInstHistAtlzCad == null ) {
				hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
			}
            	  
			//Caso alguma informacao de hidrometro seja preenchida mantem o indicador de hidrometro ativo.
			if (  hidrometroInstHistAtlzCad != null && (hidrometroInstHistAtlzCad.getNumeroHidrometro() != null || 
					hidrometroInstHistAtlzCad.getNumeroInstHidrometro() != null || 
					( hidrometroInstHistAtlzCad.getHidrometroLocalInst() != null && hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId() != null ) ||
					( hidrometroInstHistAtlzCad.getHidrometroProtecao() != null && hidrometroInstHistAtlzCad.getHidrometroProtecao().getId() != null)
 					 )) {
				//Hidrometro Agua
        	    radioGroupHidrAgua.check(R.id.radioHidrometroAguaSim);
        	    this.habilitarTodosCampos(true);
			}else{
				//Hidrometro Agua
        	    radioGroupHidrAgua.check(R.id.radioHidrometroAguaNao);
			}
			
    	    //Numero Hidrometro
    	    if ( hidrometroInstHistAtlzCad.getNumeroHidrometro() != null ) {
    	    	edtNumeroHidrometro.setText(hidrometroInstHistAtlzCad.getNumeroHidrometro());
    	    }
            //Local Instalacao
            if(hidrometroInstHistAtlzCad.getHidrometroLocalInst() != null){
        	    Util.selecionarItemCombo(spnLocalInstalacao, hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId().longValue());
            }
           
            //Tipo Protecao
            if(hidrometroInstHistAtlzCad.getHidrometroProtecao() != null){
           	    Util.selecionarItemCombo(spnTipoProtecao, hidrometroInstHistAtlzCad.getHidrometroProtecao().getId().longValue());
            }
           
            //Cavalete
            Integer indicadorCavalete = hidrometroInstHistAtlzCad.getIndicadorCavalete();
           
            if(indicadorCavalete != null && indicadorCavalete.equals(1)){
        	   radioGroupCavalete.check(R.id.radioCavaleteSim);
            }else{
        	   radioGroupCavalete.check(R.id.radioCavaleteNao);
            }
           
            //Leitura
            if ( hidrometroInstHistAtlzCad.getNumeroInstHidrometro() != null ) {
            	edtLeitura.setText(hidrometroInstHistAtlzCad.getNumeroInstHidrometro().toString());
            }
       
	        //Carrega a observacao do imovel.
		    if (imovelAtlzCadastral.getObservacao() != null && !imovelAtlzCadastral.getObservacao().equals("") ) {
	            edtObservacao.setText(imovelAtlzCadastral.getObservacao());
	        }
               
		
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}

	}
	
	/**  
	 * [UC1423] - Concluir Manter Dados Imoveis
	 * [SB0004] - Inserir/Atualizar dados aba Ligacao 
	 * 
	 * **/
	@Override
	protected void onPause() {
		super.onPause();
		try {  
	        //Situacao Agua
			if(spnSituacaoAgua.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
				LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
				long _idSituacaoAgua = spnSituacaoAgua.getSelectedItemId();
				ligacaoAguaSituacao.setId(String.valueOf(_idSituacaoAgua));
				
				imovelAtlzCadastral.setLigAguaSituacao(ligacaoAguaSituacao);
			}else{
				imovelAtlzCadastral.setLigAguaSituacao(null);
			}
			
			//Situacao Esgoto
			if(spnSituacaoEsgoto.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
				long _idSituacaoEsgoto = spnSituacaoEsgoto.getSelectedItemId();
				ligacaoEsgotoSituacao.setId(String.valueOf(_idSituacaoEsgoto));
				
				imovelAtlzCadastral.setLigEsgotoSituacao(ligacaoEsgotoSituacao);
			}else{
				imovelAtlzCadastral.setLigEsgotoSituacao(null);
			}

			//verifica se tem hidrometro
			if(radioGroupHidrAgua.getCheckedRadioButtonId() == R.id.radioHidrometroAguaSim){
				
				if(hidrometroInstHistAtlzCad == null){
					hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
				}
				
				//Numero Hidrometro
				hidrometroInstHistAtlzCad.setNumeroHidrometro(edtNumeroHidrometro.getText().toString());
				
				//Medicao Tipo
				MedicaoTipo medicaoTipo = new MedicaoTipo();
				medicaoTipo.setId(1);
				hidrometroInstHistAtlzCad.setMedicaoTipo(medicaoTipo);
				
				//Local Instalacao
				if(spnLocalInstalacao.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
					HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
					long _idHidrometroLocalInst = spnLocalInstalacao.getSelectedItemId();
					hidrometroLocalInst.setId(String.valueOf(_idHidrometroLocalInst));
					
					hidrometroInstHistAtlzCad.setHidrometroLocalInst(hidrometroLocalInst);
				}else{
					hidrometroInstHistAtlzCad.setHidrometroLocalInst(null);
				}
				
				//Tipo Protecao
				if(spnTipoProtecao.getSelectedItemId() != ConstantesSistema.ITEM_INVALIDO){
					HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
					long _idHidrometroProtecao = spnTipoProtecao.getSelectedItemId();
					hidrometroProtecao.setId(String.valueOf(_idHidrometroProtecao));
					
					hidrometroInstHistAtlzCad.setHidrometroProtecao(hidrometroProtecao);
				}else{
					hidrometroInstHistAtlzCad.setHidrometroProtecao(null);
				}
				
				//Cavalete
				if(radioGroupCavalete.getCheckedRadioButtonId() == R.id.radioCavaleteSim){
					hidrometroInstHistAtlzCad.setIndicadorCavalete(1);
				}else if(radioGroupCavalete.getCheckedRadioButtonId() == R.id.radioCavaleteNao){
					hidrometroInstHistAtlzCad.setIndicadorCavalete(2);
				}else{
					hidrometroInstHistAtlzCad.setIndicadorCavalete(null);
				}
				
				//Leitura
				if(!edtLeitura.getText().toString().equals("")){
					hidrometroInstHistAtlzCad.setNumeroInstHidrometro(Integer.valueOf(edtLeitura.getText().toString()));
				}
			}else{
				hidrometroInstHistAtlzCad = null;
			}
			
			//Observacao
			imovelAtlzCadastral.setObservacao(edtObservacao.getText().toString().replace("\n", " "));
			
			TabsActivity.hidrometroInstalacaoHist = hidrometroInstHistAtlzCad;
			TabsActivity.imovel = imovelAtlzCadastral;
			
			if ( TabsActivity.indicadorExibirMensagemErro ) {
				//Validacao
				String mensagemErro;
				mensagemErro = fachada.validarAbaLigacao(imovelAtlzCadastral, hidrometroInstHistAtlzCad);
				
		    	if ( mensagemErro != null && !mensagemErro.equals("") ) {
		    		Util.exibirMensagemErro(LigacaoAbaActivity.this, mensagemErro);           	
		    	}
			}
		} catch (FachadaException e) {
			Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ligacao_aba, menu);
		return true;
	}
	
	/** Caso nao exista hidrometro **/
	private void habilitarTodosCampos(boolean flag){
		edtNumeroHidrometro.setEnabled(flag);
		spnLocalInstalacao.setEnabled(flag);
		spnTipoProtecao.setEnabled(flag);
		radioCavaleteSim.setEnabled(flag);
		radioCavaleteNao.setEnabled(flag);
		edtLeitura.setEnabled(flag);
	}
	
	private void limparCamposHidrometro(){
		edtNumeroHidrometro.setText("");
		spnLocalInstalacao.setSelection(0);
		spnTipoProtecao.setSelection(0);
		radioGroupCavalete.check(-1);
		edtLeitura.setText("");
	}
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
		this.fecharCursor(cursor);
    }
	
}
