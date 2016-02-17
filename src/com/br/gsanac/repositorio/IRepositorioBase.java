package com.br.gsanac.repositorio;

import java.util.List;

import android.database.Cursor;

import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.exception.RepositorioException;

/**
 * @author Arthur Carvalho
 * @since 06/12/2012
 * @param <Class<? extends BaseEntity>>
 */
public interface IRepositorioBase<T extends EntidadeBase> {

    /**
     * @author Arthur Carvalho
     * @since 06/12/2012
     * @param t
     * @param selection
     * @param selectionArgs
     * @return
     * @throws RepositorioException
     */
    public EntidadeBase pesquisar(T t, String selection, String[] selectionArgs) throws RepositorioException;

   /**
    * 
    * @author Arthur Carvalho
    * @since 06/12/2012
    * @param selection
    * @param selectionArgs
    * @param orderBy
    * @return
    * @throws RepositorioException
    */
    public List<T> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException;

    /**
     * 
     * @author Arthur Carvalho
     * @date 06/12/2012
     *
     * @param idField
     * @param description
     * @param tablename
     * @return
     * @throws RepositorioException
     */
    public Cursor getCursor(String idField, String description, String tablename) throws RepositorioException;
    
    /**
     * 
     * @author Anderson Cabral
     * @date 16/01/2013
     *
     * @param idField
     * @param descriptionField
     * @param tablename
     * @param where
     * @return
     * @throws RepositorioException
     */
    public Cursor getCursor(String idField, String descriptionField, String tablename, String where) throws RepositorioException;

    /**
     * 
     * @author Arthur Carvalho
     * @date 06/12/2012
     *
     * @param t
     * @throws RepositorioException
     */
    public long inserir(T t) throws RepositorioException;

    /**
     * 
     * @author Arthur Carvalho
     * @date 06/12/2012
     *
     * @param t
     * @throws RepositorioException
     */
    public void atualizar(T t) throws RepositorioException;

    /**
     * 
     * @author Arthur Carvalho
     * @date 06/12/2012
     *
     * @param t
     * @throws RepositorioException
     */
    public void remover(T t) throws RepositorioException;
    
    /**
     * 
     * @author Anderson Cabral
     * @date 16/01/2013
     *
     * @param idField
     * @param descriptionField
     * @param tablename
     * @param where
     * @return
     * @throws RepositorioException
     */
    public Cursor getCursorOrderBy(String idField, String descriptionField, String tablename, String orderBy) throws RepositorioException;

    /**
     * 
     * @author Anderson Cabral
     * @date 26/06/2013
     *
     * @param idField
     * @param descriptionField
     * @param tablename
     * @param where
     * @param orderBy
     * @return
     * @throws RepositorioException
     */
    public Cursor getCursorOrderBy(String idField, String descriptionField, String tablename, String where, String orderBy) throws RepositorioException;

    /**
     * 
     * @author Arthur Carvalho
     * @date 06/12/2012
     *
     * @param idField
     * @param description
     * @param tablename
     * @return
     * @throws RepositorioException
     */
    public Cursor getCursorLogradouro(String where) throws RepositorioException;
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    public Cursor getCursorListaLogradouro() throws RepositorioException;
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    
    public Cursor getCursorListaLogradouroCep() throws RepositorioException;
    
}