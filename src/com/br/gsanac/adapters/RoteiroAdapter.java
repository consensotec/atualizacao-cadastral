package com.br.gsanac.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.bean.RoteiroBean;
import com.br.gsanac.excecoes.FachadaException;
import com.br.gsanac.persistencia.fachada.Fachada;
import com.br.gsanac.pojos.EntidadeBasica;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 10/09/2014
 */
public class RoteiroAdapter extends BaseAdapter {

	// Atributos adapter
	private LayoutInflater layoutInflater;
	private List<? extends EntidadeBasica> listaImoveis;
	private Context context;

	public RoteiroAdapter(Context context,List<? extends EntidadeBasica> listaImoveis) {
		this.context = context;
		this.listaImoveis = listaImoveis;
		layoutInflater = (LayoutInflater)
				this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if(listaImoveis!=null){
			return listaImoveis.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return listaImoveis.get(position).getId();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.roteiro_adapter, null);
		}

		// Background dos Imoveis
		convertView.setBackgroundResource(R.drawable.tegroteiro);

		// Monta o objeto imovelAtlzCadastral
        ImovelAtlzCadastral imovelAtlzCadastral = (ImovelAtlzCadastral) listaImoveis.get(position);
        // Objeto roteiroBean
        RoteiroBean roteiroBean = null;
        try{
        	// Pesquisa o objeto roteiroBean
        	roteiroBean = Fachada.getInstancia(context).
        			pesquisarRoteiroBean(imovelAtlzCadastral.getId());
        } catch (FachadaException e) {
			e.printStackTrace();
		}

        //Exibe o nome do cliente
        TextView tvCliente = (TextView) convertView.findViewById(R.id.cliente);
        tvCliente.setGravity(Gravity.CENTER_VERTICAL);
        tvCliente.setPadding(12, 12, 0, 0);
        tvCliente.setTextColor(Color.BLACK);
        String nome = "";
        if ( roteiroBean.getNomeCliente() != null && !roteiroBean.getNomeCliente().equals("")) {
	        if ( roteiroBean.getNomeCliente().length() > 40 ) {
	        	nome = roteiroBean.getNomeCliente().substring(0,40);
	        } else {
	        	nome  = roteiroBean.getNomeCliente();
	        }
	        tvCliente.setText(nome);
        } else {
        	tvCliente.setText("CLIENTE NÃO INFORMADO");
        }

        // Endereço
        String endereco = "";
        if ( roteiroBean.getDescricaoLogradouroTipo() != null ) {
        	endereco = roteiroBean.getDescricaoLogradouroTipo() + " ";
        }

        if ( roteiroBean.getDescricaoLogradouroTitulo() != null ) {
        	endereco += roteiroBean.getDescricaoLogradouroTitulo() + " ";
        }
        endereco += roteiroBean.getDescricaoLogradouro();

        if ( roteiroBean.getNumeroImovel() != null && !roteiroBean.getNumeroImovel().equals("") ) {
        	endereco += ", n° " + roteiroBean.getNumeroImovel();
        }

        if ( roteiroBean.getDescricaoBairro() != null ) {

        	endereco += " - " + roteiroBean.getDescricaoBairro();
        }

        // Matricula
        String matricula = imovelAtlzCadastral.getImovelId().toString();

        TextView tvMatriculaLabel = (TextView) convertView.findViewById(R.id.tvMatriculaLabel);
        tvMatriculaLabel.setPadding(12, 0, 0, 0);
        tvMatriculaLabel.setTextColor(Color.BLACK);

        TextView tvMatricula = (TextView) convertView.findViewById(R.id.tvMatricula);
        tvMatricula.setText(matricula);
        tvMatricula.setPadding(3, 0, 0, 0);
        tvMatricula.setTextColor(Color.BLACK);

        // Inscricao
        String inscricao = imovelAtlzCadastral.getLocalidadeId() + "."
        				   + imovelAtlzCadastral.getCodigoSetorComercial() + "."
        				   + imovelAtlzCadastral.getNumeroQuadra() + "."
        				   + imovelAtlzCadastral.getNumeroLote() + "."
        				   + imovelAtlzCadastral.getNumeroSubLote();

        TextView tvInscricaoLabel = (TextView) convertView.findViewById(R.id.tvInscricaoLabel);
        tvInscricaoLabel.setText("  -  Inscrição:");
        tvInscricaoLabel.setPadding(0, 0, 0, 0);
        tvInscricaoLabel.setTextColor(Color.BLACK);

        TextView tvInscricao = (TextView) convertView.findViewById(R.id.tvInscricao);
        tvInscricao.setText(inscricao);
        tvInscricao.setPadding(3, 0, 0, 0);
        tvInscricao.setTextColor(Color.BLACK);

        // Exibe o endereço
        TextView tvEndereco = (TextView) convertView.findViewById(R.id.endereco);
        tvEndereco.setText(endereco);
        tvEndereco.setPadding(12, 0, 0, 0);
        tvEndereco.setTextColor(Color.BLACK);

        // Exibe o endereço complemento
        String enderecoComplemento = "CEP "
        							 + Utilitarios.formatarCEP(roteiroBean.getCodigoCep().toString())
        							 + " - "
        							 + imovelAtlzCadastral.getNomeMunicipio()
        							 + " - "+ConstantesSistema.CODIGO_ESTADO;

        TextView tvEnderecoComplemento = (TextView) convertView.findViewById(R.id.enderecoComplemento);
        tvEnderecoComplemento.setText(enderecoComplemento);
        tvEnderecoComplemento.setPadding(12, 0, 0, 0);
        tvEnderecoComplemento.setTextColor(Color.BLACK);

        //Exibe a ordem
        TextView tvOrdem = (TextView) convertView.findViewById(R.id.ordem);
        tvOrdem.setText("Ordem: " + (imovelAtlzCadastral.getPosicao()));
        tvOrdem.setPadding(22, 10, 0, 0);
        tvOrdem.setTextColor(Color.BLACK);

        /*
         *  Icone do imovel indicado
         *  se foi visitado ou não
         */
        Integer icFinalizado = imovelAtlzCadastral.getIndicadorFinalizado();
        Integer icTransmitido = imovelAtlzCadastral.getIndicadorTransmitido();

        if(icTransmitido != null && icTransmitido.equals(ConstantesSistema.SIM)) {
        	tvCliente.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ok, 0);
        } else if(icFinalizado != null && icFinalizado.equals(ConstantesSistema.SIM)) {
        	tvCliente.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.finalizado, 0);
        } else {
        	tvCliente.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.erro, 0);
        }

        convertView.setTag(imovelAtlzCadastral);
		return convertView;
	}
}