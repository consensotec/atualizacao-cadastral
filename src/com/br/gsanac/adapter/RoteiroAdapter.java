package com.br.gsanac.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.Roteiro;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

/**
 * @author Arthur Carvalho
 */
public class RoteiroAdapter extends BaseAdapter {

    private LayoutInflater             inflater;

    private Context                    context;

    private List<? extends EntidadeBase> listaImovel;
    
    public RoteiroAdapter(Context context, List<? extends EntidadeBase> list) {
        this.context = context;
        this.listaImovel = list;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	int retorno = 0;
    	if(listaImovel != null){
    		retorno = listaImovel.size();
    	}
    	
        return retorno;
    }

    @Override
    public Object getItem(int position) {

        Object retorno = null;
    	if(listaImovel != null){
    		retorno = listaImovel.get(position);
    	}
    	
        return retorno;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {

    	//Recebe o Imovel
        ImovelAtlzCadastral imovel = (ImovelAtlzCadastral) listaImovel.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.roteiro_adapter, null);
        }
        
        convertView.setBackgroundResource(R.drawable.tegroteiro);
        Roteiro roteiro = null;
        try{
        	roteiro = (Roteiro) Fachada.getInstance().pesquisarRoteiro(imovel.getId());
        } catch (FachadaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        //Exibe o nome do cliente
        TextView tvCliente = (TextView) convertView.findViewById(R.id.cliente);
        tvCliente.setGravity(Gravity.CENTER_VERTICAL);
        tvCliente.setPadding(12, 12, 0, 0);
        String nome = "";
        if ( roteiro.getNomeCliente() != null && !roteiro.getNomeCliente().equals("")) {
	        if ( roteiro.getNomeCliente().length() > 40 ) {
	        	nome = roteiro.getNomeCliente().substring(0,40);
	        } else {
	        	nome  = roteiro.getNomeCliente();
	        }
	        tvCliente.setText(nome);
	        tvCliente.setTextColor(Color.BLACK);
        } else {
        	tvCliente.setText("CLIENTE NULO");
	        tvCliente.setTextColor(Color.BLACK);
        }
        String endereco = "";
        
        if ( roteiro.getDescricaoLogradouroTipo() != null ) {
        
        	endereco = roteiro.getDescricaoLogradouroTipo() + " ";
        }
        
        if ( roteiro.getDescricaoLogradouroTitulo() != null ) {
            
        	endereco += roteiro.getDescricaoLogradouroTitulo() + " ";
        }
        
        endereco += roteiro.getDescricaoLogradouro();
        
        
        if ( roteiro.getNumeroImovel() != null && !roteiro.getNumeroImovel().equals("") ) {
        	endereco += ", n° " + roteiro.getNumeroImovel();
        }
        
        
        if ( roteiro.getDescricaoBairro() != null ) { 
        	
        	endereco += " - " + roteiro.getDescricaoBairro();
        }
        
        //Matricula
        String matricula = imovel.getImovelId().toString();
        
        TextView tvMatriculaLabel = (TextView) convertView.findViewById(R.id.tvMatriculaLabel);
        tvMatriculaLabel.setPadding(12, 0, 0, 0);
        tvMatriculaLabel.setTextColor(Color.BLACK);
        
        TextView tvMatricula = (TextView) convertView.findViewById(R.id.tvMatricula);
        tvMatricula.setText(matricula);
        tvMatricula.setPadding(3, 0, 0, 0);       
        tvMatricula.setTextColor(Color.BLACK);
        
        //Inscricao
        String inscricao = imovel.getLocalidadeId() + "." + imovel.getCodigoSetorComercial() + "." + imovel.getNumeroQuadra() + "." + imovel.getNumeroLote() + "." + imovel.getNumeroSubLote();
        
        TextView tvInscricaoLabel = (TextView) convertView.findViewById(R.id.tvInscricaoLabel);
        tvInscricaoLabel.setText("  -  Inscrição:");
        tvInscricaoLabel.setPadding(0, 0, 0, 0);
        tvInscricaoLabel.setTextColor(Color.BLACK);
        
        TextView tvInscricao = (TextView) convertView.findViewById(R.id.tvInscricao);
        tvInscricao.setText(inscricao);
        tvInscricao.setPadding(3, 0, 0, 0);       
        tvInscricao.setTextColor(Color.BLACK);
        
        //Exibe o endereço
        TextView tvEndereco = (TextView) convertView.findViewById(R.id.endereco);
        tvEndereco.setText(endereco);
        tvEndereco.setPadding(12, 0, 0, 0);
        tvEndereco.setTextColor(Color.BLACK);
        
        //Exibe o endereço complemento
        String enderecoComplemento = "CEP " + Util.formatarCEP(roteiro.getCodigoCep().toString()) + " - " + imovel.getNomeMunicipio() + " - PE ";
        
        TextView tvEnderecoComplemento = (TextView) convertView.findViewById(R.id.enderecoComplemento);
        tvEnderecoComplemento.setText(enderecoComplemento);
        tvEnderecoComplemento.setPadding(12, 0, 0, 0);
        tvEnderecoComplemento.setTextColor(Color.BLACK);
                
        //Exibe a ordem
        TextView tvOrdem = (TextView) convertView.findViewById(R.id.ordem);
        tvOrdem.setText("Ordem: " + (imovel.getPosicao()));
        tvOrdem.setPadding(22, 10, 0, 0);
        tvOrdem.setTextColor(Color.BLACK);
        
        ImageView imgSituation = (ImageView) convertView.findViewById(R.id.imgSituation);
               
    
        if ( imovel.getIndicadorFinalizado() != null ) {
	        switch (imovel.getIndicadorFinalizado()) {
	            case ConstantesSistema.PENDENTE:
	                imgSituation.setImageResource(R.drawable.erro);
	                break;
	            case ConstantesSistema.FINALIZADO:
	                imgSituation.setImageResource(R.drawable.ok);
	                break;
	            default:
	                Log.e(ConstantesSistema.LOG_TAG, "(Error) Imovel ID: " + imovel.getId());
	        }
        }

        convertView.setTag(getItem(position));

        return convertView;
    }
    
}
