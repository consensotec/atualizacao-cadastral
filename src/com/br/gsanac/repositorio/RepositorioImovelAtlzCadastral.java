package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunas;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunasTipo;
import com.br.gsanac.entidades.EnderecoReferencia;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.FonteAbastecimento;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelPerfil;
import com.br.gsanac.entidades.LigacaoAguaSituacao;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.PavimentoCalcada;
import com.br.gsanac.entidades.PavimentoRua;
import com.br.gsanac.entidades.ImovelOcorrencia.ImovelOcorrenciaColunas;
import com.br.gsanac.entidades.Roteiro;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;



/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioImovelAtlzCadastral extends RepositorioBase<ImovelAtlzCadastral> {

    private static RepositorioImovelAtlzCadastral instance;

    public RepositorioImovelAtlzCadastral() {
        super();
    }

    public static RepositorioImovelAtlzCadastral getInstance() {
        if (instance == null) {
            instance = new RepositorioImovelAtlzCadastral();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(ImovelAtlzCadastral entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();

        if (selection == null || selection.trim().equals("")) {
            selection = ImovelAtlzCadastralColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(imovelAtlzCadastral.getNomeTabela(),
            							 ImovelAtlzCadastral.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            imovelAtlzCadastral = imovelAtlzCadastral.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return imovelAtlzCadastral;
	}

	@Override
	public List<ImovelAtlzCadastral> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<ImovelAtlzCadastral> listaImovelAtlzCadastral = null;
        ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
        try {
        	listaImovelAtlzCadastral = new ArrayList<ImovelAtlzCadastral>();

            cursor = super.getDb().query(imovelAtlzCadastral.getNomeTabela(),
            							 ImovelAtlzCadastral.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaImovelAtlzCadastral = imovelAtlzCadastral.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaImovelAtlzCadastral;
	}

	@Override
	public long inserir(ImovelAtlzCadastral imovelAtlzCadastral) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = imovelAtlzCadastral.carregarValues();
       
        try {
           return super.getDb().insert(imovelAtlzCadastral.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(ImovelAtlzCadastral imovelAtlzCadastral) throws RepositorioException {
    	ContentValues values =  imovelAtlzCadastral.carregarValues();

        String _id = String.valueOf(imovelAtlzCadastral.getId());

        String where = ImovelAtlzCadastralColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(imovelAtlzCadastral.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(ImovelAtlzCadastral imovelAtlzCadastral) throws RepositorioException {
        String _id = String.valueOf(imovelAtlzCadastral.getId());

        String where = ImovelAtlzCadastralColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(imovelAtlzCadastral.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
	
	public ImovelAtlzCadastral buscarImovelPosicao(Integer posicao) throws RepositorioException {
		Cursor cursor = null;
		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		try {
			cursor = super.getDb().query(imovelAtlzCadastral.getNomeTabela(), imovelAtlzCadastral.getColunas(), ImovelAtlzCadastralColunas.POSICAO + "=" +posicao, null,
					null, null, null);

			if (cursor.moveToFirst()) {
				List<ImovelAtlzCadastral> colecao = imovelAtlzCadastral.carregarListaEntidade(cursor);
				if(colecao!=null){
					return colecao.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	/****
	 * Retorna o maior id da tabela Imovel
	 * 
	 *@author Anderson Cabral
	 *@since 11/01/2013
	 ****/
	public Integer pesquisarMaiorIdImovel() throws RepositorioException{
		Cursor cursor = null;
		Integer maxId = null;
		try { 
	       StringBuilder sql = new StringBuilder();
	       sql.append("SELECT MAX("+ ImovelAtlzCadastralColunas.ID +") as 'ULTIMO_ID' FROM IMOVEL_ATLZ_CADASTRAL");
	       
	       cursor = super.getDb().rawQuery(sql.toString(), null);
	       
	       if (cursor.moveToFirst()) {
	    	   maxId = cursor.getInt(0);
	       }
	       
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return maxId;
	}
	/**
     * Metodo responsavel por pesquisar o arquivo de retorno
     * 
     * @author Arthur Carvalho
     * @return FileReturn
     * @param idServiceOrder
     * @throws RepositoryException
     */
    public Roteiro pesquisarRoteiro(Integer idImovelAtlzCadastral) throws RepositorioException {

        Cursor cursor = null;
        
        Roteiro roteiro = new Roteiro();
        StringBuilder sql = new StringBuilder();
        sql.append("select imac.imac_id, imac.LOCA_ID, imac.STCM_CDSETORCOMERCIAL, imac.QDRA_NNQUADRA, imac.IMAC_NNLOTE, ");
        sql.append("imac.IMAC_NNSUBLOTE, clac.CLAC_NMCLIENTE, lgtp.LGTP_DSLOGRADOUROTIPO, logr.LOGR_NMLOGRADOURO, imac.IMAC_NNIMOVEL, ");
        sql.append("bair.BAIR_NMBAIRRO, ce.CEP_CDCEP, imac.MUNI_NMMUNICIPIO, imac.imac_posicao, imac.IMAC_ICFINALIZADO, lgtt.LGTT_DSLOGRADOUROTITULO ");
		sql.append("from imovel_atlz_cadastral imac ");
		sql.append("left join cliente_atlz_cadastral clac on clac.imac_id = imac.imac_id ");
		sql.append("left join logradouro logr on logr.logr_id = imac.logr_id ");
		sql.append("left join logradouro_tipo lgtp on LGTP.LGTP_ID = logr.LGTP_ID ");
		sql.append("left join logradouro_titulo lgtt on LGTT.LGTT_ID = logr.LGTT_ID ");
		sql.append("left join logradouro_bairro lgbr on lgbr.lgbr_id = imac.lgbr_id ");
		sql.append("left join bairro bair on bair.bair_id = lgbr.bair_id ");
		sql.append("left join logradouro_cep lgcp on lgcp.lgcp_id = imac.lgcp_id ");
		sql.append("left join cep ce on ce.cep_id = lgcp.cep_id ");
		sql.append("where imac.imac_id = " +idImovelAtlzCadastral );
		

        try {
        	
            cursor = super.getDb().rawQuery(sql.toString(), null);

            if ( cursor.moveToFirst() ) {	
		    	roteiro.setMatriculaImovel( cursor.getInt(0));
		    	roteiro.setIdLocalidade( cursor.getInt(1) );
		    	roteiro.setCodigoSetorComercial( cursor.getInt(2) );
		    	roteiro.setNumeroQuadra( cursor.getInt(3) );
		    	roteiro.setNumeroLote( cursor.getInt(4) );
		    	roteiro.setNumeroSublote( cursor.getInt(5) );
		    	if ( cursor.getString(6) != null ) {
		    		roteiro.setNomeCliente( cursor.getString(6).toString() );	
		    	}
		    	
		    	if ( cursor.getString(7) != null ) {
		    		roteiro.setDescricaoLogradouroTipo( cursor.getString(7).toString() );
		    	}
		    	if ( cursor.getString(8) != null ) {
		    		roteiro.setDescricaoLogradouro( cursor.getString(8).toString() );
		    	}
		    	
		    	if ( cursor.getString(9) != null ) {
		    		roteiro.setNumeroImovel( cursor.getString(9).toString() );
		    	}
		    	if ( cursor.getString(10) != null ) {
		    		roteiro.setDescricaoBairro( cursor.getString(10).toString() );
		    	}
		    	
		    	roteiro.setCodigoCep( cursor.getInt(11) );
		    	
		    	if ( cursor.getString(12) != null  ) {
		    		roteiro.setDescricaoMunicipio( cursor.getString(12).toString() );
		    	}
		    	
		    	roteiro.setPosicao( cursor.getInt(13) );
		    	roteiro.setIndicadorFinalizado( cursor.getInt(14) );
		    	
		    	if ( cursor.getString(15) != null ) {
		    		roteiro.setDescricaoLogradouroTitulo( cursor.getString(15).toString() );
		    	}
            }
            
        } catch (Exception sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(Fachada.getContext().getResources().getString(R.string.db_error));
        } finally {
        	  
        	if (cursor != null) {
        		cursor.close();
	        }

        }
        return roteiro;
    }
    
    
    /**
     * Metodo responsavel por pesquisar todos os imóveis cadastrados no gsan.
     * 
     * @author Arthur Carvalho
     * @return FileReturn
     * @param idServiceOrder
     * @throws RepositoryException
     */
    public ArrayList<Integer> pesquisarMatriculas() throws RepositorioException {

        Cursor cursor = null;
        
        ArrayList<Integer> listaMatriculas = new ArrayList<Integer>();
        StringBuilder sql = new StringBuilder();
        sql.append("select imac.imov_id ");
		sql.append("from imovel_atlz_cadastral imac ");
		

        try {
        	
            cursor = super.getDb().rawQuery(sql.toString(), null);

            if ( cursor.moveToFirst() ) {
	        
            	do {
	            
            		listaMatriculas.add(cursor.getInt(0));
	            	
	            } while (cursor.moveToNext());
            }
            
        } catch (Exception sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(Fachada.getContext().getResources().getString(R.string.db_error));
        } finally {
        	  
        	if (cursor != null) {
        		cursor.close();
	        }

        }
        return listaMatriculas;
    }
    
	/****
	 * Retorna imovel atraves do numero do hidrometro
	 * 
	 *@author Anderson Cabral
	 *@since 17/07/2013
	 ****/
	public List<ImovelAtlzCadastral> pesquisarImovelPeloHidrometro(String numeroHidrometro) throws RepositorioException{
		Cursor cursor = null;
        ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
        List<ImovelAtlzCadastral> listaImovelAtlzCadastral = null;
        
        try {        	
	       StringBuilder sql = new StringBuilder();
	       sql.append("SELECT * FROM imovel_atlz_cadastral imovel ");
	       sql.append("INNER JOIN hidrom_inst_hist_atl_cad hidroInst ON hidroInst.imac_id = imovel.imac_id ");
	       sql.append("WHERE hidroInst.hiac_nnhidrometro = " + "'" + numeroHidrometro + "'");

	       cursor = super.getDb().rawQuery(sql.toString(), null);
	       
	       listaImovelAtlzCadastral = imovelAtlzCadastral.carregarListaEntidade(cursor);
	       
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return listaImovelAtlzCadastral;
	}
	
	/****
	 * Retorna colecao de imoveis pelo cpf
	 * 
	 *@author Anderson Cabral
	 *@since 17/07/2013
	 ****/
	public List<ImovelAtlzCadastral> pesquisarImovelPeloCPFCNPJ(String numeroCpfCnpj) throws RepositorioException{
		Cursor cursor = null;
        ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
        List<ImovelAtlzCadastral> listaImovelAtlzCadastral = null;
        
        try {        	
	       StringBuilder sql = new StringBuilder();
	       sql.append("SELECT * FROM imovel_atlz_cadastral imovel ");
	       sql.append("INNER JOIN cliente_atlz_cadastral cliente ON cliente.imac_id = imovel.imac_id ");
	       sql.append("WHERE cliente.clac_nncpfcnppj = " + "'" + numeroCpfCnpj + "'");

	       cursor = super.getDb().rawQuery(sql.toString(), null);
	       
	       listaImovelAtlzCadastral = imovelAtlzCadastral.carregarListaEntidade(cursor);
	       
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return listaImovelAtlzCadastral;
	}
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 26/06/2013
     *
     * @return
     * @throws RepositorioException
     */
    public Integer pesquisarSetorComercialPrincipal() throws RepositorioException {
    	Integer codigoSetorComercial = null;
    	
        Cursor cursor = null;
        
        StringBuilder sql = new StringBuilder();
        sql.append("select STCM_CDSETORCOMERCIAL, count(distinct(STCM_CDSETORCOMERCIAL)) ");
		sql.append("from imovel_atlz_cadastral ");
		sql.append("group by STCM_CDSETORCOMERCIAL ");
		sql.append("order by 2 desc ");
		

        try {
        	
            cursor = super.getDb().rawQuery(sql.toString(), null);

            if ( cursor.moveToFirst() ) {	
            	codigoSetorComercial = cursor.getInt(0);
            }            
            
        } catch (Exception sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(Fachada.getContext().getResources().getString(R.string.db_error));
        } finally {
        	  
        	if (cursor != null) {
        		cursor.close();
	        }

        }
        return codigoSetorComercial;
    }
    
   
	/**
	 * 
	 * @author Arthur Carvalho
	 * @date 23/07/2013
	 *
	 * @param nomeArquivo
	 * @return
	 * @throws RepositorioException
	 */
    public Date pesquisarArquivoDivididoCarregado(String nomeArquivo) throws RepositorioException {
    	Date dataCarregamento = null;
    	
        Cursor cursor = null;
        
        StringBuilder sql = new StringBuilder();
        sql.append("select ARDI_TMULTIMAALTERACAO ");
		sql.append("from ARQUIVO_DIVIDIDO ");
		sql.append("WHERE ARDI_DSARQUIVO = " + "'"+nomeArquivo+"'");
		

        try {
        	
            cursor = super.getDb().rawQuery(sql.toString(), null);

            if ( cursor.moveToFirst() ) {	
            	dataCarregamento = Util.convertStringToDate(cursor.getString(0));
            }            
            
        } catch (Exception sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(Fachada.getContext().getResources().getString(R.string.db_error));
        } finally {
        	  
        	if (cursor != null) {
        		cursor.close();
	        }

        }
        return dataCarregamento;
    }
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 23/07/2013
     *
     * @param nomeArquivo
     * @return
     * @throws RepositorioException
     */
    public void inserirArquivoDividido(String nomeArquivo) throws RepositorioException {
        ContentValues values = new ContentValues();

		
		values.put("ARDI_DSARQUIVO", nomeArquivo.substring(0, nomeArquivo.length() - 4));
		values.put("ARDI_TMULTIMAALTERACAO", Util.dateFormatDB.format(new Date()));
       
        try {
        	
           super.getDb().insert("ARQUIVO_DIVIDIDO", "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}
    
    
    
    /**
     * @author Flavio Ferreira
     * @date 10/10/2013
     * @param numeroOcorrencia
     * @return
     * @throws RepositorioException
     * 
     */
    
    // Este Metodo obtem a  quantidade de Imoveis por Ocorrencia
    
    public Integer obterQuantidadeImoveis() throws RepositorioException {	
		Cursor cursor = null;
		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		
		try {
			String query = "SELECT COUNT(imac.imac_id) as qnt FROM "+  imovelAtlzCadastral.getNomeTabela() + " imac "
					+" WHERE "+ ImovelAtlzCadastralColunas.IMOVEL_ID + " IS NOT NULL " + " AND "
					+ ImovelAtlzCadastralColunas.IMOVEL_ID + " <> 0 ";
			
			 cursor = super.getDb().rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex("qnt");
				return cursor.getInt(codigo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
    
    
    /**
     * @author Flavio Ferreira
     * @date 10/10/2013
     * @param numeroOcorrencia
     * @return
     * @throws RepositorioException
     */
    
    // Obtem a quantidade de Imoveis atualizados por Ocorrencia
    
    public Integer obterQuantidadeImoveisAtualizadosPorOcorrencia(Integer numeroOcorrencia) throws RepositorioException {	
		Cursor cursor = null;
		ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
		
		try {
			String query = "SELECT COUNT(imoc.imac_id) as qnt FROM "+  imovelOcorrencia.getNomeTabela() + " imoc "
					+" INNER JOIN imovel_atlz_cadastral imac ON imac.imac_id = imoc.imac_id "
					+" WHERE " + ImovelOcorrenciaColunas.CADOCORRENCIA_ID + " = "+ numeroOcorrencia
					+" AND "   + ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO + " = 1 "
					+" AND "   + ImovelAtlzCadastralColunas.IMOVEL_ID + " IS NOT NULL " 
					+" AND "   + ImovelAtlzCadastralColunas.IMOVEL_ID + " <> 0 ";
			
			 cursor = super.getDb().rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex("qnt");
				return cursor.getInt(codigo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
    
    /**
     * @author Flavio Ferreira
     * @date 10/10/2013
     * @param numeroOcorrencia
     * @return
     * @throws RepositorioException
     */
    
    // Obtem a quantidade de Imoveis Incluidos por Ocorrencia
    public Integer obterQuantidadeImoveisIncluidosComPorOcorrencia(Integer numeroOcorrencia) throws RepositorioException {	
		Cursor cursor = null;
		ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		
		try {
			String query = "SELECT COUNT(imoc.imac_id) as qnt "
					+" FROM "+  imovelOcorrencia.getNomeTabela() + " imoc "
					+" INNER JOIN "+ imovelAtlzCadastral.getNomeTabela() +" imac ON imac.imac_id = imoc.imac_id "
					+" WHERE imoc." + ImovelOcorrenciaColunas.CADOCORRENCIA_ID + " = "+ numeroOcorrencia
					+" AND ( imac."+ ImovelAtlzCadastralColunas.IMOVEL_ID +" IS NULL OR imac." + ImovelAtlzCadastralColunas.IMOVEL_ID + " == 0 )";
			
			 cursor = super.getDb().rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex("qnt");
				return cursor.getInt(codigo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
    
    /**
     * @author Flavio Ferreira
     * @date 10/10/2013
     * @return
     * @throws RepositorioException
     */
    
    // Obtem a descrição de cada  Ocorrencia
    public String buscarDescricaoOcorrencias(Integer idCadastroOcorrencia) throws RepositorioException {
		Cursor cursor = null;
		String descricao = null;
		CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia(); 
		try {
			
			String query = "SELECT DISTINCT "+ CadastroOcorrenciaColunas.DESCRICAO + " AS descricao FROM "  +  cadastroOcorrencia.getNomeTabela()
			+ " WHERE " + CadastroOcorrenciaColunas.ID + " = " + idCadastroOcorrencia;
			
			cursor = super.getDb().rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int descricaoindex = cursor.getColumnIndex("descricao");
				descricao = cursor.getString(descricaoindex);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return descricao;
	}
   
	  /**
     * @author Flavio Ferreira 
     * @date 14/10/2013
     * @return
     * @throws RepositorioException
     */
	//Mestodo retorna todos os imoveis atualizados
	public Integer obterTotalImoveisAtualizados(String login) throws RepositorioException {
		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		Cursor cursor = null;
		
		try{
			String query = "SELECT COUNT("+  ImovelAtlzCadastralColunas.IMOVEL_ID +") AS qnt FROM " + imovelAtlzCadastral.getNomeTabela()
					+" WHERE " + ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO + " = 1 "
					+" AND "   + ImovelAtlzCadastralColunas.IMOVEL_ID + " IS NOT NULL " 
					+" AND "   + ImovelAtlzCadastralColunas.IMOVEL_ID + " <> 0 " 
					+" AND "   + ImovelAtlzCadastralColunas.LOGIN + " = '" + login+"'";
			
			cursor = super.getDb().rawQuery(query, null);
			
			if(cursor.moveToFirst()){
				int codigo = cursor.getColumnIndex("qnt");
				return cursor.getInt(codigo);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.LOG_TAG, ex.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_error));
		}finally{
			if(cursor != null){
				cursor.close();
			}
		}
		
		return null;
	}
	
	 /**
     * @author Flavio Ferreira
     * @date 14/10/2013
     * @return
     * @throws RepositorioException
     */
	//Mestodo retorna todos os imoveis Incluidos
	public Integer obterTotalImoveisIncluidos(String login) throws RepositorioException {
		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		Cursor cursor = null;
		
		try{
			String query = "SELECT COUNT(" + ImovelAtlzCadastralColunas.ID  + ") AS qnt FROM " + imovelAtlzCadastral.getNomeTabela() 
					+" WHERE "  + ImovelAtlzCadastralColunas.LOGIN + " = '" + login+"'"
					+" AND (" + ImovelAtlzCadastralColunas.IMOVEL_ID  + " IS NULL OR "+ ImovelAtlzCadastralColunas.IMOVEL_ID  + " = 0 )";
							
			cursor = super.getDb().rawQuery(query, null);
			
			if(cursor.moveToFirst()){
				int codigo = cursor.getColumnIndex("qnt");
				return cursor.getInt(codigo);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.LOG_TAG, ex.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_error));
		}finally{
			if(cursor != null){
				cursor.close();
			}
		}
		return null;
	}
	
	 /**
     * @author Flavio Ferreira
     * @date 15/10/2013
     * @return
     * @throws RepositorioException
     */
	
	public List<String> pesquisarListaLogin() throws RepositorioException{
		Cursor cursor = null;
        ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
        List<String> listaLogin = new ArrayList<String>();
        
        try {        	
        	String query = "SELECT distinct("+  ImovelAtlzCadastralColunas.LOGIN +") AS descricao  FROM " + imovelAtlzCadastral.getNomeTabela() +  " ORDER BY "+  ImovelAtlzCadastralColunas.LOGIN;

	       cursor = super.getDb().rawQuery(query, null);
	       
	       if ( cursor.moveToFirst() ) {
	   		
				do{
					
					int login = cursor.getColumnIndex("descricao");
				
					String loginRetorno = cursor.getString(login);
					if ( loginRetorno != null) {
						listaLogin.add(loginRetorno);
					}
				
				} while (cursor.moveToNext());
			}
			
			cursor.close();
	       
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
			throw new RepositorioException(context.getResources().getString(R.string.db_error));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return listaLogin;
	}
	
}

