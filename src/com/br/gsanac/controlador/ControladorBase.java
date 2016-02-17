package com.br.gsanac.controlador;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.br.gsanac.entidades.Bairro;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.Categoria;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.ClienteTipo;
import com.br.gsanac.entidades.EnderecoReferencia;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.FoneTipo;
import com.br.gsanac.entidades.FonteAbastecimento;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.HidrometroCapacidade;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroLocalInst;
import com.br.gsanac.entidades.HidrometroMarca;
import com.br.gsanac.entidades.HidrometroProtecao;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelPerfil;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.LigacaoAguaSituacao;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.LogradouroBairro;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroTipo;
import com.br.gsanac.entidades.LogradouroTitulo;
import com.br.gsanac.entidades.MedicaoTipo;
import com.br.gsanac.entidades.Municipio;
import com.br.gsanac.entidades.OrgaoExpedidorRg;
import com.br.gsanac.entidades.PavimentoCalcada;
import com.br.gsanac.entidades.PavimentoRua;
import com.br.gsanac.entidades.PessoaSexo;
import com.br.gsanac.entidades.Quadra;
import com.br.gsanac.entidades.SetorComercial;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.entidades.SubCategoria;
import com.br.gsanac.entidades.UnidadeFederacao;
import com.br.gsanac.exception.ControladorException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.repositorio.IRepositorioBase;
import com.br.gsanac.repositorio.RepositorioBairro;
import com.br.gsanac.repositorio.RepositorioBase;
import com.br.gsanac.repositorio.RepositorioCadastroOcorrencia;
import com.br.gsanac.repositorio.RepositorioCategoria;
import com.br.gsanac.repositorio.RepositorioCep;
import com.br.gsanac.repositorio.RepositorioClienteAtlzCadastral;
import com.br.gsanac.repositorio.RepositorioClienteFoneAtlzCad;
import com.br.gsanac.repositorio.RepositorioClienteTipo;
import com.br.gsanac.repositorio.RepositorioEnderecoReferencia;
import com.br.gsanac.repositorio.RepositorioFoneTipo;
import com.br.gsanac.repositorio.RepositorioFonteAbastecimento;
import com.br.gsanac.repositorio.RepositorioFoto;
import com.br.gsanac.repositorio.RepositorioHidrometroCapacidade;
import com.br.gsanac.repositorio.RepositorioHidrometroInstHistAtlzCad;
import com.br.gsanac.repositorio.RepositorioHidrometroLocalInst;
import com.br.gsanac.repositorio.RepositorioHidrometroMarca;
import com.br.gsanac.repositorio.RepositorioHidrometroProtecao;
import com.br.gsanac.repositorio.RepositorioImovelAtlzCadastral;
import com.br.gsanac.repositorio.RepositorioImovelOcorrencia;
import com.br.gsanac.repositorio.RepositorioImovelPerfil;
import com.br.gsanac.repositorio.RepositorioImovelSubCategAtlzCad;
import com.br.gsanac.repositorio.RepositorioLigacaoAguaSituacao;
import com.br.gsanac.repositorio.RepositorioLigacaoEsgotoSituacao;
import com.br.gsanac.repositorio.RepositorioLogradouro;
import com.br.gsanac.repositorio.RepositorioLogradouroBairro;
import com.br.gsanac.repositorio.RepositorioLogradouroCep;
import com.br.gsanac.repositorio.RepositorioLogradouroTipo;
import com.br.gsanac.repositorio.RepositorioLogradouroTitulo;
import com.br.gsanac.repositorio.RepositorioMedicaoTipo;
import com.br.gsanac.repositorio.RepositorioMunicipio;
import com.br.gsanac.repositorio.RepositorioOrgaoExpedidorRg;
import com.br.gsanac.repositorio.RepositorioPavimentoCalcada;
import com.br.gsanac.repositorio.RepositorioPavimentoRua;
import com.br.gsanac.repositorio.RepositorioPessoaSexo;
import com.br.gsanac.repositorio.RepositorioQuadra;
import com.br.gsanac.repositorio.RepositorioSetorComercial;
import com.br.gsanac.repositorio.RepositorioSistemaParametros;
import com.br.gsanac.repositorio.RepositorioSubCategoria;
import com.br.gsanac.repositorio.RepositorioUnidadeFederacao;

/**
 * @author Arthur Carvalho
 * @since 06/12/2012
 * @param <T extends BaseEntity>
 */
@SuppressWarnings({
        "unchecked",
            "rawtypes"
})
public class ControladorBase<T extends EntidadeBase> implements IControladorBase<T> {

    protected static Context       context;

    private static IRepositorioBase iRepositoryBase;

    private static ControladorBase  instance;

    public static ControladorBase getInstance() {

        if (instance == null) {
            instance = new ControladorBase();
        }

        return instance;
    }

    @Override
    public T pesquisar(T t, String selection, String[] selectionArgs) throws ControladorException {
        try {
            t = (T) this.getRepositoryInstance(t.getClass()).pesquisar(t, selection, selectionArgs);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return t;
    }

    @Override
    public List<T> pesquisarLista(Class<? extends EntidadeBase> t, String selection, String selectionArgs[], String orderBy)
                                                                                                                  throws ControladorException {
        List<T> allObjects = null;

        try {
            allObjects = this.getRepositoryInstance(t).pesquisarLista(selection, selectionArgs, orderBy);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return allObjects;
    }

    @Override
    public Cursor getCursor(Class<? extends EntidadeBase> t, String idField, String descriptionField, String tablename)
                                                                                                                     throws ControladorException {
        Cursor cursor = null;

        try {
            cursor = this.getRepositoryInstance(t).getCursor(idField, descriptionField, tablename);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return cursor;
    }
    
    @Override
    public Cursor getCursor(Class<? extends EntidadeBase> t, String idField, String descriptionField, String tablename, String where) throws ControladorException{
        Cursor cursor = null;

        try {
            cursor = this.getRepositoryInstance(t).getCursor(idField, descriptionField, tablename, where);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return cursor;
    }
    
    public Cursor getCursorOrderBy(Class<? extends EntidadeBase> t, String idField, String descriptionField, String tablename, String where, String orderBy) throws ControladorException{
        Cursor cursor = null;

        try {
            cursor = this.getRepositoryInstance(t).getCursorOrderBy(idField, descriptionField, tablename, where, orderBy);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return cursor;
    }

    @Override
    public long inserir(T t) throws ControladorException {
        try {
            return this.getRepositoryInstance(t.getClass()).inserir(t);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }
    }

    @Override
    public void atualizar(T t) throws ControladorException {
        try {
            this.getRepositoryInstance(t.getClass()).atualizar(t);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }
    }

    @Override
    public void remover(T t) throws ControladorException {
        try {
            this.getRepositoryInstance(t.getClass()).remover(t);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }
    }

    @Override
    public Cursor getCursorOrderBy(Class<? extends EntidadeBase> t, String idField, String descriptionField, String tablename, String orderBy) throws ControladorException{
        Cursor cursor = null;

        try {
            cursor = this.getRepositoryInstance(t).getCursorOrderBy(idField, descriptionField, tablename, orderBy);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return cursor;
    }
    
    @Override
    public Cursor getCursorLogradouro(Class<? extends EntidadeBase> t, String where) throws ControladorException{
        Cursor cursor = null;

        try {
            cursor = this.getRepositoryInstance(t).getCursorLogradouro(where);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return cursor;
    }
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    public Cursor getCursorListaLogradouro(Class<? extends EntidadeBase> t) throws ControladorException{
    	Cursor cursor = null;
    	
    	try{
    		cursor = this.getRepositoryInstance(t).getCursorListaLogradouro();
    	}catch(RepositorioException ex){
    		throw new ControladorException(ex.getMessage());
    	}
    	
    	return cursor;
    }
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    
    public Cursor getCursorListaLogradouroCep(Class<? extends EntidadeBase> t) throws ControladorException{
    	Cursor cursor = null;
    	
    	try{
    		cursor = this.getRepositoryInstance(t).getCursorListaLogradouroCep();
    	}catch(RepositorioException ex){
    		throw new ControladorException(ex.getMessage());
    	}
    	
    	return cursor;
    }
    /**
     * @author Arthur Carvalho
     * @since 06/12/2012
     * @param clazz qualquer classe que extende de BaseEntity
     * @return iRepositoryBase, uma instancia especializada do repositorio
     */
    private IRepositorioBase getRepositoryInstance(Class<? extends EntidadeBase> clazz) {

        iRepositoryBase = null;

        if (clazz.equals(PessoaSexo.class)) {
            iRepositoryBase = RepositorioPessoaSexo.getInstance();
       
        }else if(clazz.equals(FoneTipo.class)){
        	iRepositoryBase = RepositorioFoneTipo.getInstance();
        
        }else if(clazz.equals(HidrometroLocalInst.class)){
        	iRepositoryBase = RepositorioHidrometroLocalInst.getInstance();
        
        }else if(clazz.equals(HidrometroProtecao.class)){
        	iRepositoryBase = RepositorioHidrometroProtecao.getInstance();
        
        }else if(clazz.equals(OrgaoExpedidorRg.class)){
        	iRepositoryBase =RepositorioOrgaoExpedidorRg.getInstance();
        
        }else if(clazz.equals(Categoria.class)){
        	iRepositoryBase = RepositorioCategoria.getInstance();
        	
        }else if(clazz.equals(SubCategoria.class)){
        	iRepositoryBase = RepositorioSubCategoria.getInstance();
        	
        }else if(clazz.equals(Bairro.class)){
        	iRepositoryBase = RepositorioBairro.getInstance();
        
        }else if(clazz.equals(Cep.class)){
        	iRepositoryBase = RepositorioCep.getInstance();
        
        }else if(clazz.equals(EnderecoReferencia.class)){
        	iRepositoryBase = RepositorioEnderecoReferencia.getInstance();
        
        }else if(clazz.equals(FonteAbastecimento.class)){
        	iRepositoryBase = RepositorioFonteAbastecimento.getInstance();
        
        }else if(clazz.equals(LigacaoAguaSituacao.class)){
        	iRepositoryBase = RepositorioLigacaoAguaSituacao.getInstance();
        
        }else if(clazz.equals(LigacaoEsgotoSituacao.class)){
        	iRepositoryBase = RepositorioLigacaoEsgotoSituacao.getInstance();
        
        }else if(clazz.equals(Logradouro.class)){
        	iRepositoryBase = RepositorioLogradouro.getInstance();
        
        }else if(clazz.equals(LogradouroBairro.class)){
        	iRepositoryBase = RepositorioLogradouroBairro.getInstance();
        
        }else if(clazz.equals(LogradouroCep.class)){
        	iRepositoryBase = RepositorioLogradouroCep.getInstance();
        
        }else if(clazz.equals(PavimentoCalcada.class)){
        	iRepositoryBase = RepositorioPavimentoCalcada.getInstance();
        
        }else if(clazz.equals(PavimentoRua.class)){
        	iRepositoryBase = RepositorioPavimentoRua.getInstance();
        
        }else if(clazz.equals(SistemaParametros.class)){
        	iRepositoryBase = RepositorioSistemaParametros.getInstance();
        
        }else if(clazz.equals(ImovelSubCategAtlzCad.class)){
        	iRepositoryBase = RepositorioImovelSubCategAtlzCad.getInstance();
        
        }else if(clazz.equals(ClienteFoneAtlzCad.class)){
        	iRepositoryBase = RepositorioClienteFoneAtlzCad.getInstance();
        
        }else if(clazz.equals(ClienteAtlzCadastral.class)){
        	iRepositoryBase = RepositorioClienteAtlzCadastral.getInstance();
        
        }else if(clazz.equals(ImovelAtlzCadastral.class)){
        	iRepositoryBase = RepositorioImovelAtlzCadastral.getInstance();
        
        }else if(clazz.equals(HidrometroInstHistAtlzCad.class)){
        	iRepositoryBase = RepositorioHidrometroInstHistAtlzCad.getInstance();
        
        }else if(clazz.equals(Municipio.class)){
        	iRepositoryBase = RepositorioMunicipio.getInstance();
        
        }else if(clazz.equals(LogradouroTipo.class)){
        	iRepositoryBase = RepositorioLogradouroTipo.getInstance();
        
        }else if(clazz.equals(LogradouroTitulo.class)){
        	iRepositoryBase = RepositorioLogradouroTitulo.getInstance();
        
        }else if(clazz.equals(Quadra.class)){
        	iRepositoryBase = RepositorioQuadra.getInstance();
        
        }else if(clazz.equals(ImovelPerfil.class)){
        	iRepositoryBase = RepositorioImovelPerfil.getInstance();
        
        }else if(clazz.equals(CadastroOcorrencia.class)){
        	iRepositoryBase = RepositorioCadastroOcorrencia.getInstance();
        
        }else if(clazz.equals(UnidadeFederacao.class)){
        	iRepositoryBase = RepositorioUnidadeFederacao.getInstance();
        
        }else if(clazz.equals(ClienteTipo.class)){
        	iRepositoryBase = RepositorioClienteTipo.getInstance();
        
        }else if(clazz.equals(MedicaoTipo.class)){
        	iRepositoryBase = RepositorioMedicaoTipo.getInstance();
        
        }else if(clazz.equals(HidrometroCapacidade.class)){
        	iRepositoryBase = RepositorioHidrometroCapacidade.getInstance();
        
        }else if(clazz.equals(HidrometroMarca.class)){
        	iRepositoryBase = RepositorioHidrometroMarca.getInstance();
        
        }else if(clazz.equals(Foto.class)){
        	iRepositoryBase = RepositorioFoto.getInstance();
        
        }else if(clazz.equals(ImovelOcorrencia.class)){
        	iRepositoryBase = RepositorioImovelOcorrencia.getInstance();
        
        }else if(clazz.equals(SetorComercial.class)){
        	iRepositoryBase = RepositorioSetorComercial.getInstance();
        
        }
 
        return iRepositoryBase;
    }

    @Override
    public void setContext(Context c) {
        context = c;
        RepositorioBase.setContext(c);
    }

    public static Context getContext() {
        return context;
    }
}