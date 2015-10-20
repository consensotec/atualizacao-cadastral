package com.br.gsanac.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.br.gsanac.R;
import com.br.gsanac.enums.MensagemTipo;
import com.br.gsanac.excecoes.FachadaException;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.Categoria;
import com.br.gsanac.pojos.Categoria.CategoriaColuna;
import com.br.gsanac.pojos.ImovelSubCategAtlzCad;
import com.br.gsanac.pojos.SubCategoria;
import com.br.gsanac.pojos.SubCategoria.SubCategoriaColuna;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * [UC 1412] - Manter Dados da Aba do Imóvel do Tablet
 *
 * @author André Miranda
 * @since 09/09/2014
 */
public class CategoriaImovelInserirActivity extends Activity {
	private static Fachada fachada;
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

		// TabsActivity.indicadorExibirMensagemErro = false;

		fachada = Fachada.getInstancia(this);

		try {
			spnCategoriaImovel = (Spinner) findViewById(R.id.spnCategoriaImovel);
			spnSubCategoriaImovel = (Spinner) findViewById(R.id.spnSubCategoriaImovel);
			edtQtdEconomias = (EditText) findViewById(R.id.edtQtdEconomias);
			btInserir = (Button) findViewById(R.id.btInserir);
			btCancelar = (Button) findViewById(R.id.btCancelar);

			List<Object> list = fachada.pesquisarListaObjetoGenerico(Categoria.class, null, null, null,
					CategoriaColuna.DESCRICAO);
			spnCategoriaImovel.setAdapter(Utilitarios.getAdapterSpinner(this, list));
			spnCategoriaImovel.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					listarSubCategoria();
				}

				@Override
				public void onNothingSelected(AdapterView<?> a) {}
			});

			btInserir.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!validaCamposObrigatorios()) {
						return;
					}

					ImovelSubCategAtlzCad imovelSubCategoriaAtlzCadastral = new ImovelSubCategAtlzCad();

					String qtdEconomias = edtQtdEconomias.getText().toString();
					Categoria categoria = (Categoria) spnCategoriaImovel.getSelectedItem();
					SubCategoria subCategoria = (SubCategoria) spnSubCategoriaImovel.getSelectedItem();
					subCategoria.setCategoria(categoria);

					imovelSubCategoriaAtlzCadastral.setCategoria(categoria);
					imovelSubCategoriaAtlzCadastral.setSubCategoria(subCategoria);
					imovelSubCategoriaAtlzCadastral.setQuantidadeEconomia(Integer.parseInt(qtdEconomias));

					// Volta para tela de Imóvel
					intent = new Intent(CategoriaImovelInserirActivity.this, ImovelAbaActivity.class);
					intent.putExtra(ConstantesSistema.IMOVEL_SUBCATEGORIA_ATLZ_CADASTRAL, imovelSubCategoriaAtlzCadastral);

					Utilitarios.removerTeclado(CategoriaImovelInserirActivity.this);

					setResult(Activity.RESULT_OK, intent);
					finish();
				}
			});

			btCancelar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//TabsActivity.indicadorExibirMensagemErro = true;

					// Volta para a aba de Imóvel
					setResult(Activity.RESULT_CANCELED);
					finish();
				}
			});

		} catch (FachadaException fe) {
			Utilitarios.gerarLogCat(fe.getMessage() + " - " + fe.getCause());
		}
	}

	/**
	 * Valida os Campos Obrigatórios
	 *
	 * @author André Miranda
	 * @since 09/09/2014
	 */
	private boolean validaCamposObrigatorios(){
		String campos  = "";
		String qtdEconomias = edtQtdEconomias.getText().toString();

		if (spnCategoriaImovel.getSelectedItem() == ConstantesSistema.ITEM_INVALIDO) {
			campos += "Informe Categoria \n";
		}

		if (spnSubCategoriaImovel.getSelectedItem() == ConstantesSistema.ITEM_INVALIDO) {
			campos += "Informe Subcategoria \n";
		}

		if (qtdEconomias == null || qtdEconomias.equals("")) {
			campos += "Informe Quantidade de Economias \n";
		} else if (Integer.parseInt(qtdEconomias) == 0) {
			campos += "Quantidade de Economias deve somente conter números positivos \n";
		}

		if (campos.length() > 0) {
			campos = campos.substring(0, campos.length() - 2);

			Utilitarios.mensagemAlert(this, campos, MensagemTipo.ERRO);

			return false;
		}

		return true;
	}

	/**
	 * Lista as Subcategorias de acordo com a Categoria Selecionada
	 *
	 * @author André Miranda
	 * @since 09/09/2014
	 */
	private void listarSubCategoria() {
		List<Object> list = new ArrayList<Object>();

		if(spnCategoriaImovel.getSelectedItem() != ConstantesSistema.ITEM_INVALIDO) {
			Categoria categoria = (Categoria) spnCategoriaImovel.getSelectedItem();
			String selection = SubCategoriaColuna.CATEGORIA_ID + " = ?";
			String[] selectionArgs = new String[] {
					String.valueOf(categoria.getId())
			};

			try {
				list = fachada.pesquisarListaObjetoGenerico(SubCategoria.class, selection, selectionArgs, null,
						SubCategoriaColuna.DESCRICAO);
			} catch (FachadaException fe) {
				Utilitarios.gerarLogCat(fe.getMessage() + " - " + fe.getCause());
			}
		}

		spnSubCategoriaImovel.setAdapter(Utilitarios.getAdapterSpinner(this, list));
	}
}
