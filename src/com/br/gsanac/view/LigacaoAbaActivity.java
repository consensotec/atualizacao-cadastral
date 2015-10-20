package com.br.gsanac.view;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.br.gsanac.R;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.ActivityException;
import com.br.gsanac.excecoes.FachadaException;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.HidrometroInstHistAtlzCad;
import com.br.gsanac.pojos.HidrometroLocalInst;
import com.br.gsanac.pojos.HidrometroLocalInst.HidrometroLocalInstColuna;
import com.br.gsanac.pojos.HidrometroProtecao;
import com.br.gsanac.pojos.HidrometroProtecao.HidrometroProtecaoColuna;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.pojos.LeituraAnormalidade;
import com.br.gsanac.pojos.LeituraAnormalidade.LeituraAnormalidadeColuna;
import com.br.gsanac.pojos.LigacaoAguaSituacao;
import com.br.gsanac.pojos.LigacaoEsgotoSituacao;
import com.br.gsanac.pojos.MedicaoTipo;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

public class LigacaoAbaActivity extends Fragment {
	private Fachada fachada;
	private Context context;
	private View view;

	private ImovelAtlzCadastral imovelAtlzCadastral;
	private HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad;

	/**Componentes da tela**/
    private Spinner spnSituacaoAgua;
    private Spinner spnSituacaoEsgoto;
    private RadioGroup radioGroupHidrAgua;
    private EditText edtNumeroHidrometro;
    private Spinner spnLocalInstalacao;
    private Spinner spnTipoProtecao;
    private Spinner spnAnormalidadeLeitura;
    private EditText edtLeitura;
    private EditText edtObservacao;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			view = inflater.inflate(R.layout.ligacao_aba, container, false);
			context = view.getContext();
			fachada = Fachada.getInstancia(context);

			/** Situacao Agua **/
			spnSituacaoAgua = (Spinner) view.findViewById(R.id.spnSituacaoAgua);
			List<Object> situacoesAgua = fachada.pesquisarListaTodosObjetosGenerico(LigacaoAguaSituacao.class);
			spnSituacaoAgua.setAdapter(Utilitarios.getAdapterSpinner(context, situacoesAgua));

			/** Situacao Esgoto **/
			spnSituacaoEsgoto = (Spinner) view.findViewById(R.id.spnSituacaoEsgoto);
			List<Object> situacoesEsgoto = fachada.pesquisarListaTodosObjetosGenerico(LigacaoEsgotoSituacao.class);
			spnSituacaoEsgoto.setAdapter(Utilitarios.getAdapterSpinner(context, situacoesEsgoto));

			/** Hidrometro Agua **/
			radioGroupHidrAgua = (RadioGroup) view.findViewById(R.id.radioGroupHidrAgua);
			radioGroupHidrAgua.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId == R.id.radioHidrometroAguaSim) {
						habilitarTodosCampos(true);
					} else {
						limparCamposHidrometro();
						habilitarTodosCampos(false);
					}
				}
			});

			/** Numero Hidrometro **/
			edtNumeroHidrometro = (EditText) view.findViewById(R.id.edtNumeroHidrometro);
			edtNumeroHidrometro.setFilters(new InputFilter[]{new InputFilter.AllCaps(),
					new InputFilter.LengthFilter(10),
						Utilitarios.filterReplaceCaracteresEspeciaisEEspaco()});

			/** Local de Instalacao **/
			spnLocalInstalacao = (Spinner) view.findViewById(R.id.spnLocalInstalacao);
			List<Object> locaisInstalacao = fachada.pesquisarListaObjetoGenerico(HidrometroLocalInst.class, null, null, null,
					HidrometroLocalInstColuna.DESCRICAO);
			spnLocalInstalacao.setAdapter(Utilitarios.getAdapterSpinner(context, locaisInstalacao));

			/** Tipo de Protecao **/
			spnTipoProtecao = (Spinner) view.findViewById(R.id.spnTipoProtecao);
			List<Object> tiposProtecao = fachada.pesquisarListaObjetoGenerico(HidrometroProtecao.class, null, null, null,
					HidrometroProtecaoColuna.DESCRICAO);
			spnTipoProtecao.setAdapter(Utilitarios.getAdapterSpinner(context, tiposProtecao));

			/** Leitura **/
			edtLeitura = (EditText) view.findViewById(R.id.edtLeitura);

			/** Observacao **/
			edtObservacao = (EditText) view.findViewById(R.id.edtObservacao);
			edtObservacao.setFilters(new InputFilter[]{new InputFilter.AllCaps(),
	        		new InputFilter.LengthFilter(100),
	    				Utilitarios.filterReplaceCaracteresEspeciais()});
			edtObservacao.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
 			
			/** Anormalidade Leitura (Ocorrencia Hidrômetro) **/
			spnAnormalidadeLeitura = (Spinner) view.findViewById(R.id.spnAnormalidadeLeitura);
			List<Object> anormalidadesLeitura = fachada.pesquisarListaObjetoGenerico(LeituraAnormalidade.class, null, null, null,
					LeituraAnormalidadeColuna.DESCRICAO);
			spnAnormalidadeLeitura.setAdapter(Utilitarios.getAdapterSpinner(context, anormalidadesLeitura));

			this.habilitarTodosCampos(false);

			/** Exibe os dados do Imóvel ***/
			imovelAtlzCadastral = TabsActivity.imovel;

			if (TabsActivity.primeiraVezAbaLigacao) {
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
			   Utilitarios.selecionarItemSpinner(spnSituacaoAgua, imovelAtlzCadastral.getLigAguaSituacao().getId());
            }

			//Carrega na combobox a Situacao de Esgoto do Imovel
		    if (imovelAtlzCadastral.getLigEsgotoSituacao() != null
				   && imovelAtlzCadastral.getLigEsgotoSituacao().getId() != null) {
			    Utilitarios.selecionarItemSpinner(spnSituacaoEsgoto, imovelAtlzCadastral.getLigEsgotoSituacao().getId());
            }

			hidrometroInstHistAtlzCad = TabsActivity.hidrometroInstalacaoHist;

			if (hidrometroInstHistAtlzCad == null) {
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

			// Numero Hidrometro
			if (hidrometroInstHistAtlzCad.getNumeroHidrometro() != null) {
				edtNumeroHidrometro.setText(hidrometroInstHistAtlzCad.getNumeroHidrometro());
			}

			// Local Instalacao
			if (hidrometroInstHistAtlzCad.getHidrometroLocalInst() != null) {
				Utilitarios.selecionarItemSpinner(spnLocalInstalacao, hidrometroInstHistAtlzCad
						.getHidrometroLocalInst().getId());
			}

			// Tipo Protecao
			if (hidrometroInstHistAtlzCad.getHidrometroProtecao() != null) {
				Utilitarios.selecionarItemSpinner(spnTipoProtecao, hidrometroInstHistAtlzCad.getHidrometroProtecao()
						.getId());
			}

			// Leitura
			if (hidrometroInstHistAtlzCad.getNumeroInstHidrometro() != null) {
				edtLeitura.setText(hidrometroInstHistAtlzCad.getNumeroInstHidrometro().toString());
			}

			// Carrega a observacao do imovel.
			if (imovelAtlzCadastral.getObservacao() != null && !imovelAtlzCadastral.getObservacao().equals("")) {
				edtObservacao.setText(imovelAtlzCadastral.getObservacao());
			}

		    //Carrega na combobox a Ocorrência Hidrômetro
		    if (imovelAtlzCadastral.getAnormalidadeLeitura() != null
				   && imovelAtlzCadastral.getAnormalidadeLeitura().getId() != null) {
			   Utilitarios.selecionarItemSpinner(spnAnormalidadeLeitura, imovelAtlzCadastral.getAnormalidadeLeitura().getId());
            }

			return view;
		} catch(Exception e) {
        	throw new ActivityException(e.getMessage() + "");
        }
	}

	/**
	 * [UC1423] Concluir Manter Dados Imoveis
	 * [SB0004] Inserir/Atualizar dados aba Ligacao
	 */
	@Override
	public void onPause() {
		super.onPause();

		try {
	        //Situacao Agua
			if (spnSituacaoAgua.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
				imovelAtlzCadastral.setLigAguaSituacao((LigacaoAguaSituacao) spnSituacaoAgua.getSelectedItem());
			} else {
				imovelAtlzCadastral.setLigAguaSituacao(null);
			}

			//Situacao Esgoto
			if (spnSituacaoEsgoto.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
				imovelAtlzCadastral.setLigEsgotoSituacao((LigacaoEsgotoSituacao) spnSituacaoEsgoto.getSelectedItem());
			} else {
				imovelAtlzCadastral.setLigEsgotoSituacao(null);
			}

			// Verifica se tem hidrometro
			if (radioGroupHidrAgua.getCheckedRadioButtonId() == R.id.radioHidrometroAguaSim) {
				if (hidrometroInstHistAtlzCad == null) {
					hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
				}

				// Numero Hidrometro
				hidrometroInstHistAtlzCad.setNumeroHidrometro(edtNumeroHidrometro.getText().toString());

				// Medicao Tipo
				MedicaoTipo medicaoTipo = new MedicaoTipo();
				medicaoTipo.setId(1);
				hidrometroInstHistAtlzCad.setMedicaoTipo(medicaoTipo);

				// Local Instalacao
				if (spnLocalInstalacao.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
					hidrometroInstHistAtlzCad.setHidrometroLocalInst((HidrometroLocalInst) spnLocalInstalacao
							.getSelectedItem());
				} else {
					hidrometroInstHistAtlzCad.setHidrometroLocalInst(null);
				}

				// Tipo Protecao
				if (spnTipoProtecao.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
					hidrometroInstHistAtlzCad.setHidrometroProtecao((HidrometroProtecao) spnTipoProtecao
							.getSelectedItem());
				} else {
					hidrometroInstHistAtlzCad.setHidrometroProtecao(null);
				}

				// Leitura
				if (!edtLeitura.getText().toString().isEmpty()) {
					hidrometroInstHistAtlzCad.setNumeroInstHidrometro(Integer.valueOf(edtLeitura.getText().toString()));
				} else {
					hidrometroInstHistAtlzCad.setNumeroInstHidrometro(0);
					edtLeitura.setText("0");
				}
			} else {
				hidrometroInstHistAtlzCad = null;
			}

			// Observacao
			imovelAtlzCadastral.setObservacao(edtObservacao.getText().toString().replace("\n", " "));

			// Hidrometro Ocorrencia
			if (spnAnormalidadeLeitura.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
				imovelAtlzCadastral.setAnormalidadeLeitura((LeituraAnormalidade) spnAnormalidadeLeitura
						.getSelectedItem());
			} else {
				imovelAtlzCadastral.setAnormalidadeLeitura(null);
			}

			TabsActivity.hidrometroInstalacaoHist = hidrometroInstHistAtlzCad;
			TabsActivity.imovel = imovelAtlzCadastral;

			if (!TabsActivity.indicadorExibirMensagemErro) {
				return;
			}

			// Validacao
			String mensagemErro;
			mensagemErro = fachada.validarAbaLigacao(imovelAtlzCadastral, hidrometroInstHistAtlzCad);

			if (mensagemErro != null && !mensagemErro.equals("")) {
				Utilitarios.mensagemAlert(context, mensagemErro, MensagemTipo.ERRO);
			}
		} catch (FachadaException e) {
			Log.e(ConstantesSistema.TAG_LOGCAT, e.getMessage() + " - " + e.getCause());
		}
	}

	/** Caso nao exista hidrometro **/
	private void habilitarTodosCampos(boolean flag){
		edtNumeroHidrometro.setEnabled(flag);
		spnLocalInstalacao.setEnabled(flag);
		spnTipoProtecao.setEnabled(flag);
		edtLeitura.setEnabled(flag);
	}

	private void limparCamposHidrometro(){
		edtNumeroHidrometro.setText("");
		edtLeitura.setText("");
		spnLocalInstalacao.setSelection(0);
		spnTipoProtecao.setSelection(0);
	}
}
