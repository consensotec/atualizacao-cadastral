package com.br.gsanac.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.ActivityException;
import com.br.gsanac.excecoes.FachadaException;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.Categoria;
import com.br.gsanac.pojos.Categoria.CategoriaColuna;
import com.br.gsanac.pojos.EntidadeBasica;
import com.br.gsanac.pojos.FonteAbastecimento;
import com.br.gsanac.pojos.FonteAbastecimento.FonteAbastecimentoColuna;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.pojos.ImovelSubCategAtlzCad;
import com.br.gsanac.pojos.PavimentoCalcada;
import com.br.gsanac.pojos.PavimentoCalcada.PavimentoCalcadaColuna;
import com.br.gsanac.pojos.PavimentoRua;
import com.br.gsanac.pojos.PavimentoRua.PavimentoRuaColuna;
import com.br.gsanac.pojos.SubCategoria;
import com.br.gsanac.pojos.SubCategoria.SubCategoriaColuna;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * [UC 1412] - Manter Dados da Aba Imóvel do Tablet
 *
 * @author André Miranda
 * @since 05/09/2014
 */
public class ImovelAbaActivity extends Fragment {
	private Intent intent;
	private Context context;
	private Fachada fachada;

	private Button btAdicionarCategoria;

	private Spinner spnPavimentoRua;
	private Spinner spnPavimentoCalcada;

	private EditText edtFonteAbastecimento;
	private EditText edtNumMedidorEnergia;
	private EditText edtNumMoradores;

	private TableLayout tableLayoutCategoria;

	private Map<ImovelSubCategAtlzCad, TableRow> tableRowsCategoria;
	private ArrayList<ImovelSubCategAtlzCad> colImoveisSubCategoria;
	private ImovelAtlzCadastral imovelAtlzCadastral;

	boolean flagInserirNovaCategoria = false;

	/**
	 * @author André Miranda
	 * @date 05/09/2014
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			View view = inflater.inflate(R.layout.imovel_aba, container, false);

	        ArrayList<Object> list;

	        context = view.getContext();
			fachada = Fachada.getInstancia(context);
			tableRowsCategoria = new HashMap<ImovelSubCategAtlzCad, TableRow>();
			colImoveisSubCategoria = new ArrayList<ImovelSubCategAtlzCad>();

			spnPavimentoRua = (Spinner) view.findViewById(R.id.spnPavimentoRua);
			spnPavimentoCalcada = (Spinner) view.findViewById(R.id.spnPavimentoCalcada);
			tableLayoutCategoria = (TableLayout) view.findViewById(R.id.tableLayoutCategorias);

			edtFonteAbastecimento = (EditText) view.findViewById(R.id.edtFonteAbastecimento);
			edtNumMoradores = (EditText) view.findViewById(R.id.edtNumMoradores);

			edtNumMedidorEnergia = (EditText) view.findViewById(R.id.edtNumMedidorEnergia);
			edtNumMedidorEnergia.setFilters(new InputFilter[]{new InputFilter.AllCaps(),
					new InputFilter.LengthFilter(10),
						Utilitarios.filterReplaceCaracteresEspeciaisEEspaco()});

			list = fachada.pesquisarListaObjetoGenerico(PavimentoRua.class, null, null, null,
					PavimentoRuaColuna.DESCRICAO);
			spnPavimentoRua.setAdapter(Utilitarios.getAdapterSpinner(context, list));

			list = fachada.pesquisarListaObjetoGenerico(PavimentoCalcada.class, null, null, null,
					PavimentoCalcadaColuna.DESCRICAO);
			spnPavimentoCalcada.setAdapter(Utilitarios.getAdapterSpinner(context, list));

	        /** Chama a tela de Inserir Categoria **/
			btAdicionarCategoria = (Button) view.findViewById(R.id.btAdicionarCategoria);
			btAdicionarCategoria.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					flagInserirNovaCategoria = true;
	                intent = new Intent(ImovelAbaActivity.this.context, CategoriaImovelInserirActivity.class);
	                startActivityForResult(intent, ConstantesSistema.IMOVEL_ABA_REQUEST_CODE);
				}
			});

			imovelAtlzCadastral = TabsActivity.imovel;
			carregarDadosAba(imovelAtlzCadastral);

			return view;
		} catch(Exception e) {
        	throw new ActivityException(e.getMessage() + "");
        }
	}

	/**
	 * No onPause os dados desta abas
	 * serão gravados na TabsActivity
	 */
    @Override
    public void onPause() {
    	super.onPause();

    	String numMedidorEnergia = null;
    	Integer numMoradores = null;

    	PavimentoRua pavimentoRua = null;
    	PavimentoCalcada pavimentoCalcada = null;

    	if(spnPavimentoRua.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO){
        	pavimentoRua = (PavimentoRua) spnPavimentoRua.getSelectedItem();
    	}

    	if(spnPavimentoCalcada.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO){
        	pavimentoCalcada = (PavimentoCalcada) spnPavimentoCalcada.getSelectedItem();
    	}

    	if(edtNumMedidorEnergia.getText() != null && edtNumMedidorEnergia.getText().length() > 0){
    		numMedidorEnergia = edtNumMedidorEnergia.getText().toString();
    	}

    	if(edtNumMoradores.getText() != null && edtNumMoradores.getText().length() > 0){
    		numMoradores = Integer.parseInt(edtNumMoradores.getText().toString());
    	}

    	imovelAtlzCadastral.setNumeroMedidorEnergia(numMedidorEnergia);
    	imovelAtlzCadastral.setNumeroMorador(numMoradores);
    	imovelAtlzCadastral.setPavimentoRua(pavimentoRua);
    	imovelAtlzCadastral.setPavimentoCalcada(pavimentoCalcada);

    	TabsActivity.colecaoImoveisSubCategoria = colImoveisSubCategoria;

    	// Ao inserir uma nova categoria, não será executada a validação
    	if(flagInserirNovaCategoria) {
    		flagInserirNovaCategoria = false;
    		return;
    	}

		if(!TabsActivity.indicadorExibirMensagemErro) {
			return;
		}

    	// Validação
		try {
			String mensagemErro = fachada.validarAbaImovel(imovelAtlzCadastral);

			if(mensagemErro != null && !mensagemErro.equals("")) {
				Utilitarios.mensagemAlert(context, mensagemErro, MensagemTipo.ERRO);
			}
		} catch (FachadaException e) {
			Log.e(ConstantesSistema.TAG_LOGCAT, e.getMessage() + " - " + e.getCause());
		}
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		if(resultCode == Activity.RESULT_OK){
			ImovelSubCategAtlzCad imovelSubCategoriaAtlzCadastral = (ImovelSubCategAtlzCad)
					intent.getSerializableExtra(ConstantesSistema.IMOVEL_SUBCATEGORIA_ATLZ_CADASTRAL);

			this.inserirLinhaTabelaCategorias(imovelSubCategoriaAtlzCadastral);
		}

		//TabsActivity.indicadorExibirMensagemErro = true;
	}

	/**
	 * Carrega os dados desta aba a partir do que está na TabsActivity
	 *
	 * @param imovel
	 */
	private void carregarDadosAba(ImovelAtlzCadastral imovel) {
		if (imovel.getNumeroMedidorEnergia() != null && !imovel.getNumeroMedidorEnergia().isEmpty()) {
			edtNumMedidorEnergia.setText(imovel.getNumeroMedidorEnergia().toString());
		}

		if (imovel.getNumeroMorador() != null) {
			edtNumMoradores.setText(imovel.getNumeroMorador().toString());
		}

		// Carregar fonte de abastecimento
		if (imovel.getFonteAbastecimento() != null) {
			String descricao = imovel.getFonteAbastecimento().getDescricao();
			if(descricao == null) {
				String select = FonteAbastecimentoColuna.ID + " = ?";
				String[] selectArgs = new String[]{ String.valueOf(imovel.getFonteAbastecimento().getId()) };
				FonteAbastecimento fonte = fachada.pesquisarObjetoGenerico(FonteAbastecimento.class,
						select, selectArgs, null, null);

				if(fonte != null) {
					descricao = fonte.getDescricao();
					imovel.getFonteAbastecimento().setDescricao(descricao);
				}
			}
			edtFonteAbastecimento.setText(descricao);
		}

		// Carrega na combobox o valor de Pavimento Rua
		if (imovel.getPavimentoRua() != null) {
			for (int i = 0; i < spnPavimentoRua.getCount(); i++) {
				long id = ((EntidadeBasica) spnPavimentoRua.getItemAtPosition(i)).getId();
				if (id == imovel.getPavimentoRua().getId()) {
					spnPavimentoRua.setSelection(i);
					break;
				}
			}
		}

		// Carrega na combobox o valor de Pavimento Calçada
		if (imovel.getPavimentoCalcada() != null) {
			for (int i = 0; i < spnPavimentoCalcada.getCount(); i++) {
				long id = ((EntidadeBasica) spnPavimentoCalcada.getItemAtPosition(i)).getId();
				if (id == imovel.getPavimentoCalcada().getId()) {
					spnPavimentoCalcada.setSelection(i);
					break;
				}
			}
		}

		// Carrega as subCategorias do imovel na tabela de subcategorias
		if (TabsActivity.colecaoImoveisSubCategoria != null) {
			for (ImovelSubCategAtlzCad imovelCategoria : TabsActivity.colecaoImoveisSubCategoria) {
				this.inserirLinhaTabelaCategorias(imovelCategoria);
			}
		}
	}

    /**
     * Colore as linhas da tabela categoria
     */
	private void colorirLinhas() {
		int cont = 1;

		// Começa com o child na posição 3 pois existem
		// outros que não são linhas de categorias
		for (int i = 3; i <= tableLayoutCategoria.getChildCount(); i++) {
			TableRow tableRow = (TableRow) tableLayoutCategoria.getChildAt(i);

			if (tableRow == null) continue;

			cont++;
			tableRow.setBackgroundColor((cont % 2 == 0) ? Color.TRANSPARENT
					: Color.parseColor("#5D5F5F"));
		}
	}

	/**
     * Remove da tabela a Categoria Selecionada
     */
    private void removerLinhaTabelaCategoria(View v) {
    	Integer idCategoria = (Integer) v.getTag(R.id.categoria);
    	Integer idSubcategoria = (Integer) v.getTag(R.id.subcategoria);

    	ImovelSubCategAtlzCad imovelSubCategoria = new ImovelSubCategAtlzCad();

    	if(colImoveisSubCategoria == null || colImoveisSubCategoria.isEmpty()){
    		Log.e(ConstantesSistema.TAG_LOGCAT, "colImoveisSubCategoria não inicializado!");
    		return;
    	}

		for(ImovelSubCategAtlzCad aux : colImoveisSubCategoria) {
			if(aux.getCategoria().getId().equals(idCategoria) && aux.getSubCategoria().getId().equals(idSubcategoria)) {
				imovelSubCategoria = aux;
				break;
			}
		}

		tableRowsCategoria.remove(imovelSubCategoria);
		colImoveisSubCategoria.remove(imovelSubCategoria);
		tableLayoutCategoria.removeView(v);

		colorirLinhas();

		Utilitarios.mensagemAlert(context, R.string.msg_categoria_removida_sucesso, MensagemTipo.INFO);
    }

	/**
     * Adiciona na tabela a Categoria Inserida
     */
    private void inserirLinhaTabelaCategorias(ImovelSubCategAtlzCad imovelSubCategoria) {
    	if(colImoveisSubCategoria.contains(imovelSubCategoria)){
    		Utilitarios.mensagemAlert(context, R.string.error_categoria_ja_selecionada, MensagemTipo.ERRO);
    		return;
    	}

        Categoria categoria = imovelSubCategoria.getCategoria();
        SubCategoria subCategoria = imovelSubCategoria.getSubCategoria();

        if(categoria.getId() != null && categoria.getDescricao() == null) {
			String atributosWhere = CategoriaColuna.ID + " = ?";
			String[] valorAtributosWhere = new String[]{categoria.getId() + ""};

			categoria = fachada.pesquisarObjetoGenerico(Categoria.class, atributosWhere, valorAtributosWhere, null, null);
        }

        if(subCategoria.getId() != null && subCategoria.getDescricao() == null) {
			String atributosWhere = SubCategoriaColuna.ID + " = ?";
			String[] valorAtributosWhere = new String[]{subCategoria.getId() + ""};

			subCategoria = fachada.pesquisarObjetoGenerico(SubCategoria.class, atributosWhere, valorAtributosWhere, null, null);
        }

		TableRow tr = new TableRow(context);
		tr.setGravity(Gravity.CENTER_VERTICAL);
        tr.setTag(R.id.categoria, categoria.getId());
        tr.setTag(R.id.subcategoria, subCategoria.getId());
        tr.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				removerLinhaTabelaCategoria(v);
				return true;
			}
		});

		tableLayoutCategoria.addView(tr);

		TextView tvActionSelected = new TextView(context);
        tvActionSelected.setGravity(Gravity.CENTER);
        tvActionSelected.setTextColor(Color.BLACK);
        tvActionSelected.setHeight(50);
		tvActionSelected.setText(categoria.getDescricao());
		tvActionSelected.setTextSize(18);

        TextView tvActionSelected2 = new TextView(context);
        tvActionSelected2.setGravity(Gravity.CENTER);
        tvActionSelected2.setTextColor(Color.BLACK);
        tvActionSelected2.setHeight(50);
        tvActionSelected2.setMaxWidth(300);
        tvActionSelected2.setText(subCategoria.getDescricao());
        tvActionSelected2.setTextSize(18);

        TextView tvActionSelected3 = new TextView(context);
        tvActionSelected3.setGravity(Gravity.CENTER);
        tvActionSelected3.setTextColor(Color.BLACK);
        tvActionSelected3.setHeight(50);
        tvActionSelected3.setText(imovelSubCategoria.getQuantidadeEconomia().toString());
        tvActionSelected3.setTextSize(18);

        ImageView imgRemoveAction = new ImageView(context);
        imgRemoveAction.setImageResource(R.drawable.btnremover);
        imgRemoveAction.setScaleX(1.5f);
        imgRemoveAction.setScaleY(1.5f);
	    imgRemoveAction.setPadding(0, 0, 2, 0);

        tr.addView(tvActionSelected);
        tr.addView(tvActionSelected2);
        tr.addView(tvActionSelected3);
        tr.addView(imgRemoveAction);

        tr.setBackgroundColor((tableRowsCategoria.size() % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F"));

        tableRowsCategoria.put(imovelSubCategoria, tr);
        colImoveisSubCategoria.add(imovelSubCategoria);
        tableLayoutCategoria.invalidate();
    }
}
