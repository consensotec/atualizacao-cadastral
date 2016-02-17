package com.br.gsanac.controlador;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.exception.ControladorException;

/**
 * @author Arthur Carvalho
 * @since 06/12/12
 * @param <T extends BaseEntity>
 */
public interface IControladorBase<T extends EntidadeBase> {

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @param context
     */
    public void setContext(Context c);

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @param t
     * @param selection
     * @param selectionArgs
     * @return
     * @throws ControladorException
     */
    public T pesquisar(T t, String selection, String[] selectionArgs) throws ControladorException;

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @param clazz
     * @param selection
     *            is a String to search for in the columns. Only columns with matching string are
     *            returned.
     * @param selectionArgs
     *            is a String[] with arguments for the selection.
     * @param orderBy
     * @return
     * @throws ControladorException
     */
    public List<? extends EntidadeBase> pesquisarLista(Class<? extends EntidadeBase> clazz, String selection, String selectionArgs[],
            String orderBy) throws ControladorException;

    /**
     * @author Arthur Carvalho
     * @since 23/09/2011
     * @param clazz
     * @param idField
     * @param descriptionField
     * @param tablename
     * @return
     * @throws ControladorException
     */
    public Cursor getCursor(Class<? extends EntidadeBase> clazz, String idField, String descriptionField, String tablename)
                                                                                                                         throws ControladorException;

    /**
     * @author Anderson Cabral
     * @since 13/01/2013
     * @param clazz
     * @param idField
     * @param descriptionField
     * @param tablename
     * @param where
     * @return
     * @throws ControladorException
     */
    public Cursor getCursor(Class<? extends EntidadeBase> clazz, String idField, String descriptionField, String tablename, String where)
                                                                                                                         throws ControladorException;

    
    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @param t
     * @throws ControladorException
     */
    public long inserir(T t) throws ControladorException;

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @param t
     * @throws ControladorException
     */
    public void atualizar(T t) throws ControladorException;

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @param t
     * @throws ControladorException
     */
    public void remover(T t) throws ControladorException;
    
    /**
     * @author Anderson Cabral
     * @since 13/01/2013
     * @param clazz
     * @param idField
     * @param descriptionField
     * @param tablename
     * @param where
     * @return
     * @throws ControladorException
     */
    public Cursor getCursorOrderBy(Class<? extends EntidadeBase> clazz, String idField, String descriptionField, String tablename, String orderBy)
                                                                                                                         throws ControladorException;

    /**
     * @author Anderson Cabral
     * @since 13/01/2013
     * @param clazz
     * @param idField
     * @param descriptionField
     * @param tablename
     * @param where
     * @return
     * @throws ControladorException
     */
    public Cursor getCursorLogradouro(Class<? extends EntidadeBase> clazz, String where) throws ControladorException;

}