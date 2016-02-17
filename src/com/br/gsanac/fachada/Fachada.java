package com.br.gsanac.fachada;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.Display;
import android.view.WindowManager;

import com.br.gsanac.controlador.ControladorBase;
import com.br.gsanac.controlador.ControladorUtil;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.Roteiro;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.ControladorException;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.repositorio.RepositorioImovelAtlzCadastral;

/**
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
@SuppressWarnings({
        "rawtypes",
            "unchecked"
})
public class Fachada {

    private static Fachada        instance;

    @SuppressWarnings("unused")
    private ControladorBase       controladorBase;

    
    private ControladorUtil controladorUtil;


    public Fachada() {
        this.controladorBase = ControladorBase.getInstance();
        this.controladorUtil = ControladorUtil.getInstance();
    }

    public static Fachada getInstance() {
        if (instance == null) {
            instance = new Fachada();
        }
        return instance;
    }

    public static void setContext(Context c) {
        ControladorBase.getInstance().setContext(c);
    }
    
    public static Context getContext() {
        return ControladorBase.getContext();
    }

    /**
     * @author Erivan Sousa
     * @since 06/09/2011
     * @param login
     * @param password
     * @return
     */
    public SistemaParametros validateLogin(String login, String password) throws FachadaException {
        try {
            return controladorUtil.validarLogin(login, password);
        } catch (ControladorException e) {
            e.printStackTrace();
            throw new FachadaException(e.getMessage());
        }
    }

    

    /**
     * @author Arthur Carvalho
     * @since 12/09/2011
     * @param clazz
     * @param orderBy
     * @return um array de objetos do tipo t
     * @throws FachadaException
     */
    public List<? extends EntidadeBase> pesquisarLista(Class<? extends EntidadeBase> clazz, String selection, String selectionArgs[],
            String orderBy) throws FachadaException {
        try {
            return ControladorBase.getInstance().pesquisarLista(clazz, selection, selectionArgs, orderBy);
        } catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }

    public Object pesquisar(EntidadeBase entity, String selection, String[] selectionArgs) throws FachadaException {
        try {
            return ControladorBase.getInstance().pesquisar(entity, selection, selectionArgs);
        } catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }

    public long inserir(EntidadeBase entity) throws FachadaException {
        try {
            return ControladorBase.getInstance().inserir(entity);
        } catch (ControladorException e) {
            throw new FachadaException(e.getMessage());
        }
    }

    public void update(EntidadeBase entity) throws FachadaException {
        try {
            ControladorBase.getInstance().atualizar(entity);
        } catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }

    public void remover(EntidadeBase entity) throws FachadaException {
        try {
            ControladorBase.getInstance().remover(entity);
        } catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }

    public Cursor getCursor(Class<? extends EntidadeBase> clazz, String idField, String descriptionField, String tablename)
                                                                                                                         throws FachadaException {
        try {
            return ControladorBase.getInstance().getCursor(clazz, idField, descriptionField, tablename);
        } catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }
    
    public Cursor getCursor(Class<? extends EntidadeBase> clazz, String idField, String descriptionField, String tablename, String where) 
    																													throws FachadaException {
        try {
            return ControladorBase.getInstance().getCursor(clazz, idField, descriptionField, tablename, where);
        } catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }
    
    public Cursor getCursorOrderBy(Class<? extends EntidadeBase> clazz, String idField, String descriptionField, String tablename, String where, String orderBy) 
			throws FachadaException {
		try {
			return ControladorBase.getInstance().getCursorOrderBy(clazz, idField, descriptionField, tablename, where, orderBy);
		} catch (ControladorException ce) {
			throw new FachadaException(ce.getMessage());
		}
	} 
    
    /**
     * Metodo responsavel por validar os campos da aba localidade
     * 
     * @author Arthur Carvalho
     * @date 21/12/2012
     *
     * @param imovelAtlzCadastral
     * @return
     * @throws FachadaException
     */
    public String validarAbaLocalidade(ImovelAtlzCadastral imovelAtlzCadastral ) throws FachadaException {
    	
    	try{
    		return controladorUtil.validarAbaLocalidade(imovelAtlzCadastral);
    	} catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }
    
    /**
     * Metodo responsavel por validar os campos da aba endereco
     * 
     * @author Anderson Cabral
     * @date 26/12/2012
     *
     * @param imovelAtlzCadastral
     * @return Mensagem de Erro
     * @throws FachadaException
     */
    public String validarAbaEndereco(ImovelAtlzCadastral imovelAtlzCadastral ) throws FachadaException {
    	
    	try{
    		return controladorUtil.validarAbaEndereco(imovelAtlzCadastral);
    	} catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }

    /**
     * Método responsável por validar os campos da aba cliente
     * 
     * @author Davi Menezes
     * @date 28/12/2012
     * 
     * @param clienteAtlzCadastral
     * @return Mensagem de Erro
     * @throws FachadaException
     */
    public String validarAbaCliente(ClienteAtlzCadastral clienteAtlzCadastral, Integer idLigacaoAguaSituacao) throws FachadaException {
    	try{
    		return controladorUtil.validarAbaCliente(clienteAtlzCadastral, idLigacaoAguaSituacao);
    	}catch (ControladorException ce) {
            throw new FachadaException(ce.getMessage());
        }
    }
    
    /**
	 * Retorna true se a orientacao default do celular dor LANDSCAPE
	 * @author Amelia Pessoa
	 * @param Contexto
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean isOrientacaoLandscape(Context ctx){
		Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if(display.getOrientation()==1){
        	return true;
        } else {
        	return false;
        }
	}
      
    /**
     * Metodo responsavel por validar os campos da aba ligacao
     * 
     * @author Anderson Cabral
     * @date 03/01/2013
     *
     * @param imovelAtlzCadastral
     * @param hidrometroInstHistAtlzCad
     * @return Mensagem de Erro
     * @throws FachadaException
     */
    public String validarAbaLigacao(ImovelAtlzCadastral imovelAtlzCadastral, HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws FachadaException {
		try{
			return controladorUtil.validarAbaLigacao(imovelAtlzCadastral, hidrometroInstHistAtlzCad);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
	}

    /**
     * Metodo responsavel por validar os campos da aba imovel
     * 
     * @author Davi Menezes
     * @date 07/01/2013
     * 
     * @param imovelAtlzCadastral
     * @return Mensagem de Erro
     * @throws FachadaException
     */
    public String validarAbaImovel(ImovelAtlzCadastral imovelAtlzCadastral) throws FachadaException{
    	try{
    		return controladorUtil.validarAbaImovel(imovelAtlzCadastral);
    	}catch (ControladorException ce) {
			throw new FachadaException(ce.getMessage());
		}
    }
    
    /**
     * Metodo que pesquisa o imovel de acordo com a posicao dele no roteiro.
     * 
     * @author Arthur Carvalho
     * @date 08/01/2013
     *
     * @param posicao
     * @return
     * @throws ControladorException
     */
    public ImovelAtlzCadastral buscarImovelPosicao(Integer posicao) throws FachadaException{
    	try{
			return controladorUtil.buscarImovelPosicao(posicao);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    
    /**
     * Metodo responsavel por validar os campos da aba Fotos
     * 
     * @author Anderson Cabral
     * @date 08/01/2013
     *
     * @param idImovel
     * @return Mensagem de Erro
     * @throws FachadaException
     */
    public String validarAbaFotos(Integer idImovel) throws FachadaException {
		try{
			return controladorUtil.validarAbaFotos(idImovel);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
	}
    
	/****
	 * Retorna o maior id da tabela Imovel
	 * 
	 *@author Anderson Cabral
	 *@since 11/01/2013
	 ****/
	public Integer pesquisarMaiorIdImovel() throws FachadaException{
    	try{
			return controladorUtil.pesquisarMaiorIdImovel();
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    
   
	public String gerarArquivoRetornoImovel(ImovelAtlzCadastral imovelAtlzCadastral) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoImovel(imovelAtlzCadastral);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
	
	public String gerarArquivoRetornoCep(Cep cep) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoCep(cep);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
	
	public String gerarArquivoRetornoLogradouro(Logradouro logradouro, SistemaParametros sistemaParametros) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoLogradouroBairro(logradouro, sistemaParametros);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
	
	public String gerarArquivoRetornoLogradouroCep(LogradouroCep logradouroCep) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoLogradouroCep(logradouroCep);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
	
	/**
     * 
     * @author Arthur Carvalho
     * @date 18/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public Roteiro pesquisarRoteiro(Integer idImovelAtlzCadastral) throws FachadaException{
    	try{
			return controladorUtil.pesquisarRoteiro(idImovelAtlzCadastral);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    /**
     * 
     * @author Arthur Carvalho
     * @date 25/01/2013
     *
     * @param clazz
     * @param idField
     * @param descriptionField
     * @param tablename
     * @param orderBy
     * @return
     * @throws FachadaException
     */
    public Cursor getCursorOrderBy(Class<? extends EntidadeBase> clazz, String idField, String descriptionField, String tablename, String orderBy) 
			throws FachadaException {
    	
    	try {
    			return ControladorBase.getInstance().getCursorOrderBy(clazz, idField, descriptionField, tablename, orderBy);
    	} catch (ControladorException ce) {
    		throw new FachadaException(ce.getMessage());
    	}
	}
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 25/01/2013
     *
     * @param clazz
     * @return
     * @throws FachadaException
     */
    public Cursor getCursorLogradouro(Class<? extends EntidadeBase> clazz, String where) throws FachadaException {
    	
    	try {
    			return ControladorBase.getInstance().getCursorLogradouro(clazz, where);
    	} catch (ControladorException ce) {
    		throw new FachadaException(ce.getMessage());
    	}
	}
    
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    public Cursor getCursorListaLogradouro(Class<? extends EntidadeBase> t) throws FachadaException{
    	
    	try{
    		return ControladorBase.getInstance().getCursorListaLogradouro(t);
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    }
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    
    public Cursor getCursorListaLogradouroCep(Class<? extends EntidadeBase> t) throws FachadaException{
    	
    	try{
    		return ControladorBase.getInstance().getCursorListaLogradouroCep(t);
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    	
    }
    
    /**
     * Metodo responsavel por pesquisar todos os imóveis cadastrados no gsan.
     * 
     * @author Arthur Carvalho
     * @return FileReturn
     * @param idServiceOrder
     * @throws RepositoryException
     */
    public ArrayList<Integer> pesquisarMatriculas() throws FachadaException {
    	
    	ArrayList<Integer> lista = null; 
    	
    	try {
    		lista = controladorUtil.pesquisarMatriculas();
	        	
        } catch (ControladorException e) {
        	throw new FachadaException(e.getMessage());
        }
    	return lista;
    }
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param cliente
     * @return
     * @throws FachadaException
     */
    public String gerarArquivoRetornoCliente(ClienteAtlzCadastral cliente, List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral, String codigoImovelAtlzCadastral) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoCliente(cliente, listaClienteFoneAtlzCadastral, codigoImovelAtlzCadastral);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param cliente
     * @return
     * @throws FachadaException
     */
    public String gerarArquivoRetornoHidrometro(List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad, String codigoImovelAtlzCadastral, Integer idImovel) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoHidrometro(listaHidrometroInstHistAtlzCad, codigoImovelAtlzCadastral, idImovel);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }

    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param cliente
     * @return
     * @throws FachadaException
     */
    public String gerarArquivoRetornoSubategoria(List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad, String codigoImovelAtlzCadastral) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoSubcategoria(listaImovelSubCategAtlzCad, codigoImovelAtlzCadastral);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param cliente
     * @return
     * @throws FachadaException
     */
    public String gerarArquivoRetornoOcorrencia(List<ImovelOcorrencia> listaImovelOcorrencia, String codigoImovelAtlzCadastral) throws FachadaException{
    	try{
			return controladorUtil.gerarArquivoRetornoOcorrencia(listaImovelOcorrencia, codigoImovelAtlzCadastral);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    
    /**
     * @author Erivan Sousa
     * @since 06/09/2011
     * @param login
     * @param password
     * @return
     */
    public SistemaParametros validarLoginCpf(String login) throws FachadaException {
        try {
            return controladorUtil.validarLoginCpf(login);
        } catch (ControladorException e) {
            e.printStackTrace();
            throw new FachadaException(e.getMessage());
        }
    }
    
	/****
	 * Retorna imovel atraves do numero do hidrometro
	 * 
	 *@author Anderson Cabral
	 *@since 17/07/2013
	 ****/
    public List<ImovelAtlzCadastral> pesquisarImovelPeloHidrometro(String numeroHidrometro) throws FachadaException {
    	
    	List<ImovelAtlzCadastral> listaImovelAtlzCadastral = null; 
    	
    	try {
    		listaImovelAtlzCadastral = controladorUtil.pesquisarImovelPeloHidrometro(numeroHidrometro);
	        	
        } catch (ControladorException e) {
        	throw new FachadaException(e.getMessage());
        }
    	return listaImovelAtlzCadastral;
    }
    
	/****
	 * Retorna colecao de imoveis pelo cpf
	 * 
	 *@author Anderson Cabral
	 *@since 17/07/2013
	 ****/
	public List<ImovelAtlzCadastral> pesquisarImovelPeloCPFCNPJ(String numeroCpfCnpj) throws FachadaException{
    	List<ImovelAtlzCadastral> listaImovelAtlzCadastral = null; 
    	
    	try {
    		listaImovelAtlzCadastral = controladorUtil.pesquisarImovelPeloCPFCNPJ(numeroCpfCnpj);
	        	
        } catch (ControladorException e) {
        	throw new FachadaException(e.getMessage());
        }
    	return listaImovelAtlzCadastral;
	}
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 26/06/2013
     *
     * @return
     * @throws FachadaException
     */
    public Integer pesquisarSetorComercialPrincipal() throws FachadaException {
    	
    	Integer codigo = null; 
    	
    	try {
    		codigo = controladorUtil.pesquisarSetorComercialPrincipal();
	        	
        } catch (ControladorException e) {
        	throw new FachadaException(e.getMessage());
        }
    	return codigo;
    }

    
    /**
     * 
     * @author Arthur Carvalho
     * @date 18/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public Date pesquisarArquivoDivididoCarregado(String nomeArquivo) throws FachadaException{
    	try{
			return controladorUtil.pesquisarArquivoDivididoCarregado(nomeArquivo);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 18/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public void inserirArquivoDividido(String nomeArquivo) throws FachadaException{
    	try{
			 controladorUtil.inserirArquivoDividido(nomeArquivo);
		}catch (ControladorException ce) {
	        throw new FachadaException(ce.getMessage());
	    }
    }
    
    /**
     * 
     * @author Flávio Ferreira
     * @date 11/10/2013
     * @return
     * @throws ControladorException
     */
    
    public Integer obterQuantidadeImoveis() throws FachadaException{
    	
    	Integer quantidade = null;
    	try{
    		quantidade =  controladorUtil.obterQuantidadeImoveis();
    		
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    	
    	return quantidade;
    }
    
    /**
     * 
     * @author Flávio Ferreira
     * @date 11/10/2013
     *
     * @return
     * @throws ControladorException
     */
    
    public Integer obterQuantidadeImoveisAtualizadosPorOcorrencia(Integer numeroOcorrencia) throws FachadaException{
    	
    	Integer quantidade = 0;
    	try{
    		quantidade =  controladorUtil.obterQuantidadeImoveisAtualizadosPorOcorrencia(numeroOcorrencia);
    		
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    	
    	return quantidade;
    }
    
    /**
     * 
     * @author Flávio Ferreira
     * @date 11/10/2013
     *
     * @return
     * @throws ControladorException
     */
    
    public Integer obterQuantidadeImoveisIncluidosComPorOcorrencia(Integer numeroOcorrencia) throws FachadaException{
    	
    	Integer quantidade = 0;
    	try{
    		quantidade =  controladorUtil.obterQuantidadeImoveisIncluidosComPorOcorrencia(numeroOcorrencia);
    		
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    	
    	return quantidade;
    }
    
    /**
     * @author Flavio Ferreira
     * @date 10/10/2013
     * @return
     * @throws RepositorioException
     */
    public String buscarDescricaoOcorrencias(Integer idCadastroOcorrencia)  throws FachadaException{
    	
    	String dsOcorrecias = null;
    	
    	try{
    		
    		dsOcorrecias = controladorUtil.buscarDescricaoOcorrencias(idCadastroOcorrencia);
    		
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    	
    	return dsOcorrecias;
    			
    }
    
    
    /**
     * @author Flavio Ferreira
     * @date 14/10/2013
     * @return
     * @throws RepositorioException
     */
    public Integer obterTotalImoveisAtualizados(String login) throws FachadaException{
    	Integer quantidade = 0;
    	
    	try{
    		quantidade = controladorUtil.obterTotalImoveisAtualizados(login);
    		
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    	
    	return quantidade;
    	
    }
    
    /**
     * @author Flavio Ferreira
     * @date 14/10/2013
     * @return
     * @throws RepositorioException
     */
    public Integer obterTotalImoveisIncluidos(String login) throws FachadaException{
    	Integer quantidade = 0;
    	
    	try{
    		quantidade = controladorUtil.obterTotalImoveisIncluidos(login);
    		
    	}catch(ControladorException ex){
    		throw new FachadaException(ex.getMessage());
    	}
    	
    	return quantidade;	
    }
    
    /**
     * @author Flavio Ferreira
     * @date 15/10/2013
     * @return
     * @throws FachadaException 
     * @throws RepositorioException
     */
	
	public List<String> pesquisarListaLogin() throws FachadaException{
		List<String> listaLogin = null;
		
		try{
			
			listaLogin = controladorUtil.pesquisarListaLogin();
		}catch(ControladorException ex){
			throw new FachadaException(ex.getMessage());
		}
		
		return listaLogin; 
				
	}
}