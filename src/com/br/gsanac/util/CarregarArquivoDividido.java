package com.br.gsanac.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.util.Log;

import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.Cep.Ceps;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunas;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.Logradouro.Logradouros;
import com.br.gsanac.entidades.LogradouroBairro;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.repositorio.RepositorioCep;
import com.br.gsanac.repositorio.RepositorioClienteAtlzCadastral;
import com.br.gsanac.repositorio.RepositorioClienteFoneAtlzCad;
import com.br.gsanac.repositorio.RepositorioFoto;
import com.br.gsanac.repositorio.RepositorioHidrometroInstHistAtlzCad;
import com.br.gsanac.repositorio.RepositorioImovelAtlzCadastral;
import com.br.gsanac.repositorio.RepositorioImovelOcorrencia;
import com.br.gsanac.repositorio.RepositorioImovelSubCategAtlzCad;
import com.br.gsanac.repositorio.RepositorioLogradouro;
import com.br.gsanac.repositorio.RepositorioLogradouroBairro;
import com.br.gsanac.repositorio.RepositorioLogradouroCep;
import com.br.gsanac.repositorio.RepositorioSistemaParametros;


/**
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class CarregarArquivoDividido {

    private static DBLoader instance;

    private static int      i = 0;
    
    private static long      idImovelAtlzCad = 0;
    
    private static boolean arquivoDivididoComando = true;
    
    private static boolean imovelNaoFinalizado = true;
    
    public static DBLoader getInstance() {
        if (instance == null) {
            instance = new DBLoader();
        }
        return instance;
    }

    public static boolean carregarArquivoFromInputStream(InputStream input, boolean isToDropDB) {
    	
    	boolean retorno = false;
    	try {
    		
            InputStreamReader inputReader = new InputStreamReader(input, "ISO-8859-1");
            BufferedReader reader = new BufferedReader(inputReader);
            String line;
            
            while ((line = reader.readLine()) != null) {
            	if ( !arquivoDivididoComando ) {
            		break;
            	}
                carregarBancoDadosArquivoDividido(line);
            }
            
            if ( arquivoDivididoComando ) {
            	retorno = true;
            } else {
        		retorno = false;
            }
            
            arquivoDivididoComando = true;
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (RepositorioException re) {
            re.printStackTrace();
        }
    	return retorno;
    }

    /**
     * @author Arthur Carvalho
     * @since 09/09/2011
     * @param line
     * @throws RepositorioException
     */
    public static void carregarBancoDadosArquivoDividido(String line) throws RepositorioException {

        List<String> objectArray = Util.split(line);

        String registerType = objectArray.get(0);
        
        //Carrega o arquivo caso seja do mesmo comando do supervisor e o arquivo seja do tipo do subordinado.
        if ( arquivoDivididoComando ) {
        	
	        if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_CEP)){
	        	
	            RepositorioCep.getInstance().inserir(Cep.inserirAtualizarDoArquivoDividido(objectArray));
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_LOGRADOURO)){
	            RepositorioLogradouro.getInstance().inserir(Logradouro.inserirAtualizarDoArquivoDividido(objectArray));
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_LOGRADOURO_BAIRRO)){
	        	
	        	String selectionl = Logradouros.CODIGO_UNICO + "=?";
	           
	            String[] selectionArgsl= new String[] {
	               objectArray.get(1)
	            };
	        	
	        	Logradouro logradouro = new Logradouro();
	        	
	        	try {
					logradouro = (Logradouro) Fachada.getInstance().pesquisar(logradouro, selectionl, selectionArgsl);
				} catch (FachadaException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        	
	            RepositorioLogradouroBairro.getInstance().inserir(LogradouroBairro.inserirAtualizarDoArquivoDividido(objectArray, logradouro));
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_LOGRADOURO_CEP)){
	        	
	        	String selectionl = Logradouros.CODIGO_UNICO + "=?";
	            
	            String[] selectionArgsl= new String[] {
	               objectArray.get(2)
	            };
	        	
	        	Logradouro logradouro = new Logradouro();
	        	
	        	try {
					logradouro = (Logradouro) Fachada.getInstance().pesquisar(logradouro, selectionl, selectionArgsl);
				} catch (FachadaException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        	
	        	
	        	String selectionC = Ceps.CODIGO_UNICO + "=?";
	            
	            String[] selectionArgsC= new String[] {
	               objectArray.get(1)
	            };
	        	
	        	Cep cep = new Cep();
	        	try {
	        		cep = (Cep) Fachada.getInstance().pesquisar(cep, selectionC, selectionArgsC);
				} catch (FachadaException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        	
	        	
	            RepositorioLogradouroCep.getInstance().inserir(LogradouroCep.inserirAtualizarDoArquivoDividido(objectArray, logradouro, cep));
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_IMOVEL_ATLZ_CADASTRAL)){
	
	        	//Imovel ja cadastrado ( indice 2 = imov_id )
	        	Integer posicao = null;
	        	if ( objectArray.get(2) != null && !objectArray.get(2).equals("0") && !objectArray.get(2).equals("")) {
	        		
	        		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
	        		imovelAtlzCadastral.setId(objectArray.get(1));
	        		String selectionl = Logradouros.CODIGO_UNICO + "=?";
	                
	                String[] selectionArgsl= new String[] {
	                   objectArray.get(9)
	                };
	            	
	            	Logradouro logradouro = new Logradouro();
	            	
	            	try {
	            		//pesquisa logradouro novo para o imovel
	            		logradouro = (Logradouro) Fachada.getInstance().pesquisar(logradouro, selectionl, selectionArgsl);
	            		
	            		//caso nao seja logradouro novo pesquisa logradouro ja cadastrado
	            		if ( logradouro == null || logradouro.getId() == null ) {
	            			selectionl = Logradouros.ID + "=?";
	                        
	                        selectionArgsl= new String[] {
	                           objectArray.get(9)
	                        };
	                    	
	                    	logradouro = new Logradouro();
	                    	
	                    	logradouro = (Logradouro) Fachada.getInstance().pesquisar(logradouro, selectionl, selectionArgsl);
	            		}
	            		
	        			imovelAtlzCadastral =  (ImovelAtlzCadastral) Fachada.getInstance().pesquisar(imovelAtlzCadastral, null, null);
	    			} catch (FachadaException e) {
	    				e.printStackTrace();
	    			} catch (Exception e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	        		
	            	if ( imovelAtlzCadastral.getIndicadorFinalizado() != null && 
	            			!imovelAtlzCadastral.getIndicadorFinalizado().toString().equals(String.valueOf(ConstantesSistema.SIM)) ) {
	            	
		        		RepositorioImovelAtlzCadastral.getInstance().atualizar(ImovelAtlzCadastral.inserirAtualizarDoArquivoDividido(objectArray, posicao, imovelAtlzCadastral, logradouro));
		        		
		        		//remover o hidrometro
		        		RepositorioHidrometroInstHistAtlzCad.getInstance().remover(objectArray.get(1));
		        		
		        		//remover imovel subcategoria
		        		RepositorioImovelSubCategAtlzCad.getInstance().remover(objectArray.get(1));
		        		
		        		//remover o cliente 
		        		RepositorioClienteAtlzCadastral.getInstance().remover(objectArray.get(1));
		        		imovelNaoFinalizado = true;
		        		idImovelAtlzCad = Long.valueOf(objectArray.get(1));
	            	} else {
	            		imovelNaoFinalizado = false;
	            	}
	        	} else {
	        		
	        		imovelNaoFinalizado = true;
	        		String selectionl = Logradouros.CODIGO_UNICO + "=?";
	                
	                String[] selectionArgsl= new String[] {
	                   objectArray.get(9)
	                };
	            	
	            	Logradouro logradouro = new Logradouro();
	            	
	            	try {
	    				logradouro = (Logradouro) Fachada.getInstance().pesquisar(logradouro, selectionl, selectionArgsl);
	    				
	    				//caso nao seja logradouro novo pesquisa logradouro ja cadastrado
	            		if ( logradouro == null || logradouro.getId() == null ) {
	            			selectionl = Logradouros.ID + "=?";
	                        
	                        selectionArgsl= new String[] {
	                           objectArray.get(9)
	                        };
	                    	
	                    	logradouro = new Logradouro();
	                    	
	                    	logradouro = (Logradouro) Fachada.getInstance().pesquisar(logradouro, selectionl, selectionArgsl);
	            		}
	    				
	    				
	        			SistemaParametros sistemaParametros = new SistemaParametros();
	        			sistemaParametros  = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
	        			posicao = Integer.valueOf(sistemaParametros.getQuantidadeImovel()) + Integer.valueOf(1)  ;
	        			sistemaParametros.setQuantidadeImovel(String.valueOf( posicao ));
	            		RepositorioSistemaParametros.getInstance().atualizar(sistemaParametros);
	            		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
	            		idImovelAtlzCad = RepositorioImovelAtlzCadastral.getInstance().inserir(ImovelAtlzCadastral.inserirAtualizarDoArquivoDividido(objectArray, posicao, imovelAtlzCadastral, logradouro));		
	            	} catch (FachadaException e) {
	        			e.printStackTrace();
	        		}        		
	        	}
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_CLIENTE_ATLZ_CADASTRAL)){
	        	if ( imovelNaoFinalizado ) {
	        		RepositorioClienteAtlzCadastral.getInstance().inserir(ClienteAtlzCadastral.inserirAtualizarDoArquivoDividido(objectArray, idImovelAtlzCad));	
		        	//remove os telefones do cliente
		        	RepositorioClienteFoneAtlzCad.getInstance().remover(objectArray.get(12));
	        	}
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_CLIENTE_FONE_ATLZ_CADASTRAL)){
	        	if ( imovelNaoFinalizado ) {
	        		RepositorioClienteFoneAtlzCad.getInstance().inserir(ClienteFoneAtlzCad.inserirAtualizarDoArquivoDividido(objectArray));
	        	}
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_HIDROMETRO_ATLZ_CADASTRAL)){
	        	if ( imovelNaoFinalizado ) {
	        		RepositorioHidrometroInstHistAtlzCad.getInstance().inserir(HidrometroInstHistAtlzCad.inserirAtualizarDoArquivoDividido(objectArray, idImovelAtlzCad));
	        	}
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_SUBCATEGORIA_ATLZ_CADASTRAL)){
	        	if ( imovelNaoFinalizado ) {
	        		RepositorioImovelSubCategAtlzCad.getInstance().inserir(ImovelSubCategAtlzCad.inserirAtualizarDoArquivoDividido(objectArray, idImovelAtlzCad));
	        	}
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_OCORRENCIA_ATLZ_CADASTRAL)){
	        	if ( imovelNaoFinalizado ) {
		        	String selectionl = ImovelAtlzCadastralColunas.INTEGRACAO + "=?";
		            
		            String[] selectionArgsl = new String[] {
		               objectArray.get(2)
		            };
		            ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		        	try {
		    			imovelAtlzCadastral =  (ImovelAtlzCadastral) Fachada.getInstance().pesquisar(imovelAtlzCadastral, selectionl, selectionArgsl);
					} catch (FachadaException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
		        	
		        	
		            RepositorioImovelOcorrencia.getInstance().inserir(ImovelOcorrencia.inserirAtualizarDoArquivoDividido(objectArray, imovelAtlzCadastral));
	        	}
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_IMOVEL_TRANSMITIDO)){
	
	        	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
	    		imovelAtlzCadastral.setId(objectArray.get(1));
	    		try {
	    			imovelAtlzCadastral =  (ImovelAtlzCadastral) Fachada.getInstance().pesquisar(imovelAtlzCadastral, null, null);
				} catch (FachadaException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		
	    		imovelAtlzCadastral.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
	    		imovelAtlzCadastral.setIndicadorFinalizado(ConstantesSistema.SIM);
	    		
	    		RepositorioImovelAtlzCadastral.getInstance().atualizar(imovelAtlzCadastral);
	            i++;
	        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_AD_IMOVEL_FOTO)){
	        	if ( imovelNaoFinalizado ) {
		        	String selectionl = ImovelAtlzCadastralColunas.INTEGRACAO + "=?";
		            
		            String[] selectionArgsl = new String[] {
		               objectArray.get(3)
		            };
		            ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		        	try {
		    			imovelAtlzCadastral =  (ImovelAtlzCadastral) Fachada.getInstance().pesquisar(imovelAtlzCadastral, selectionl, selectionArgsl);
					} catch (FachadaException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
		    		
		    		RepositorioFoto.getInstance().inserir(Foto.inserirAtualizarDoArquivoDividido(objectArray, imovelAtlzCadastral));
	        	}
	            i++;
	        } else if(registerType.equals(ConstantesSistema.ARQUIVO_TRANSMITIDO_USUARIO_SEM_LOGIN)){
	
	        	String idComando = objectArray.get(1);
	    		
	        	try {
	    			SistemaParametros sistemaParametros = new SistemaParametros();
	    			sistemaParametros  = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
	    			
	    			if ( !sistemaParametros.getIdComando().toString().equals(idComando) ) {
	 	    			arquivoDivididoComando = false;
	    			}
	        	} catch (FachadaException e) {
	    			e.printStackTrace();
	    		}  
	            i++;
	        } else if(registerType.equals(ConstantesSistema.ARQUIVO_TRANSMITIDO_USUARIO_COM_LOGIN)){
	
	 	    	arquivoDivididoComando = false;
	            i++;
	        }
        
        }
        
        Log.i(ConstantesSistema.LOG_TAG, i + " " + line);

        objectArray.clear();
    }
}