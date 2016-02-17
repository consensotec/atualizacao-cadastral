package com.br.gsanac.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.br.gsanac.R;
import com.br.gsanac.conexao.ComunicacaoWebServer;
import com.br.gsanac.conexao.ConexaoWebServer;
import com.br.gsanac.conexao.PhotoConnection;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.Cep.Ceps;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteAtlzCadastral.ClienteAtlzCadastralColunas;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.ClienteFoneAtlzCad.ClienteFoneAtlzCadColunas;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.Foto.FotoColunas;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad.HidrometroInstHistAtlzCadColunas;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunas;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelOcorrencia.ImovelOcorrenciaColunas;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad.ImovelSubCategAtlzCadColunas;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.Logradouro.Logradouros;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroCep.LogradouroCeps;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;


/**
 * <p>
 * Activity que gerencia o processo de finalização de um arquivo
 * </p>
 * 
 * @author Arthur Carvalho
 * @date 14/01/2013
 */
public class FinalizarRoteiroActivity extends Activity implements OnClickListener {

    private Button                  btnCancel;

    private ProgressBar             prbImovel;

    private ProgressBar             prbFoto;
    
    private ProgressBar             prbCep;
    
    private ProgressBar             prbLogradouro;
    
    private ProgressBar             prbLogradouroCep;

    private FinalizarRoteiroControl rfc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_finalization_activity);
        Fachada.setContext(this);

        prbCep = (ProgressBar) findViewById(R.id.progressByCep);
        prbLogradouro = (ProgressBar) findViewById(R.id.progressByLogradouro);
        prbLogradouroCep = (ProgressBar) findViewById(R.id.progressByLogradouroCep);
        prbImovel = (ProgressBar) findViewById(R.id.progressByImovel);
        prbFoto = (ProgressBar) findViewById(R.id.progressByFoto);

        rfc = new FinalizarRoteiroControl();
        rfc.execute(prbImovel, prbFoto, prbCep, prbLogradouro, prbLogradouroCep);
    }

    @Override
    public void onClick(View arg0) {

        if (arg0 == btnCancel) {
            // Cancelamos a operaÃ§Ã£o
            rfc.abort = true;
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    @Override
    public boolean onSearchRequested() {
        finish();
        return false;
    }

    // Classe responsavel por controlar o processo de envio dos imóveis pra base do GSAN.
    private class FinalizarRoteiroControl extends AsyncTask<ProgressBar, Integer, Integer> {

        private ProgressBar      prbImovel;
        private ProgressBar      prbFoto;
        private ProgressBar      prbCep;
        private ProgressBar      prbLogradouro;
        private ProgressBar      prbLogradouroCep;
        protected boolean        abort                                = false;

        private static final int ERROR_GENERIC                        = 0;
        private static final int ERROR_ABORT_REQUESTED                = 2;
        private static final int ERROR_GENERATING_RETURN_FILE         = 3;
        private static final int ERROR_SEDING_SO_INFORMATION          = 4;
        private static final int ERROR_SERVER_OFFLINE                 = 5;
        private static final int ERROR_FILE_FINISHED                  = 6;
        private static final int ERROR_SENDIG_PHOTO                   = 7;
        private static final int ARQUIVO_FINALIZADO                   = 8;
        

        private static final int SUCESS                               = 1000;

        /**
         * Prepare activity before upload
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(ProgressBar... params) {
            ConexaoWebServer web = new ConexaoWebServer(FinalizarRoteiroActivity.this);
            
                prbImovel = params[0];
                prbFoto = params[1];
                prbCep = params[2];
                prbLogradouro = params[3];
                prbLogradouroCep = params[4];

                int myCepProgress = 0;
                int myLogradouroProgress = 0;
                int myLogradouroCepProgress = 0;
                int myImovelProgress = 0;
                int myFotoProgress = 0;

                // Casp esteja off line
                if (!web.serverOnline()) {
                    return ERROR_SERVER_OFFLINE;
                }
    	        
    	        
    	        try {
    	        	
    	        	SistemaParametros sistemaParametros = new SistemaParametros();
     	            sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
    	        	
    	        	  //Verifica se o arquivo não está finalizado para transmitir os dados
     	            Integer verificador = web.arquivoLiberadoParaTransmissao(sistemaParametros.getIdComando());
     	            
     	            //caso o verificador seja igual 1 - arquivo nao finalizado
     	            //caso o verificador seja igual 2 - arquivo finalizado
     	           //caso o verificador seja igual 3 - sem conexao
     	            
	    		    if ( verificador.equals(Integer.valueOf(1)) )  {
    	        	
	    	        	 /**
	    	             * Montando o arquivo Registro tipo CEP
	    	             */
	    		        String selectionLogCep = LogradouroCeps.INDICADORNOVO+ "=? ";
	    		        
	    		        String[] selectionArgsLogCep = new String[] {
	    		        		String.valueOf(ConstantesSistema.SIM)
	    		        };
	    		        
	    		        List<? extends EntidadeBase> listaLogrCep = Fachada.getInstance().pesquisarLista(LogradouroCep.class, selectionLogCep, selectionArgsLogCep, null);
	    	       	
	    	            if ( listaLogrCep != null ) {
	    	           
	    		           	Iterator<? extends EntidadeBase> iteratorLogradouroCep = listaLogrCep.iterator();
	    		    		
	    		           	while( iteratorLogradouroCep.hasNext() ) {
	    		           		
	    		           		LogradouroCep logradouroCep = (LogradouroCep) iteratorLogradouroCep.next();
	    		           	 
	    		           		
	    		                String selectionCep = Ceps.ID + "=? AND " + Ceps.INDICADOR_TRANSMITIDO + "=?";
	    		    	        
	    		    	        String[] selectionArgsCep = new String[] {
	    		    	            String.valueOf(logradouroCep.getCep().getId()),
	    		    	            String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO)
	    		    	        };
	    		    	        
	    		            	List<? extends EntidadeBase> listaCep = Fachada.getInstance().pesquisarLista(Cep.class,selectionCep, selectionArgsCep, null);
	    		            	
	    		            	//Valida se os ceps ja foram enviados.
	    	    	            if (listaCep == null || listaCep.size() == 0) {
	    	    	            	 //Atualiza a barra de progresso do CEP
	    	    	            	
	    	    	            	myCepProgress = 100;
	    	    	            	myLogradouroProgress = 0;
	    	    	            	myLogradouroCepProgress = 0;
	    	    	            	myImovelProgress = 0;
	    	    	            	myFotoProgress = 0;
	    	    	            	
	    	    	                 publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
	    	    	                 Thread.sleep(500);
	    	    	             } else {
	    	    	            	 
	    	    	            	 int c = 0; 
	    	    	        		 Iterator<? extends EntidadeBase> iteratorCep = listaCep.iterator();
	    	    	        		
	    	    	        		 while( iteratorCep.hasNext() ) {
	    	    	        			 c++;
	    	    	        			 Cep cep = (Cep) iteratorCep.next();
	
	
	     		           		 		if ( cep.getCodigoUnico() == null || cep.getCodigoUnico().equals("") ) {
	     		           		 			Long dateTime = new Date().getTime();
	     		           		 			cep.setCodigoUnico(dateTime.toString());
	     		           		 			Fachada.getInstance().update(cep);
	     		           		 		}
	    	    	        			 try {
	    	    	        				//Tentativa de transmissão do cep pra base do GSAN
	    		                            boolean sucess = web.enviarDadosCep(cep);
	    		
	    		                            if (!sucess) {
	    		                                return ERROR_SEDING_SO_INFORMATION;
	    		                            } else {
	    		                            	//Caso o cep seja transmitido com sucesso atualizada o indicador que ja foi transmitido.
	    		                                cep.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
	    		                                try {
	    		                                    Fachada.getInstance().update(cep);
	    		                                } catch (FachadaException fe) {
	    		                                    fe.printStackTrace();
	    		                                }
	    		                            }
	    		                        } catch (Exception e) {
	    		                            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
	    		                            return ERROR_GENERATING_RETURN_FILE;
	    		                        }
	    	    	        		 }
	
	    	    	        		 myCepProgress = (int) (((double) c / (double) listaCep.size()) * 100);
	
	    	     	            	 myLogradouroProgress = 0;
	    	     	            	 myLogradouroCepProgress = 0;
	    	     	            	 myImovelProgress = 0;
	    	     	            	 myFotoProgress = 0;
	    	     	            	
	    	     	                 publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
	    	    	        		 Thread.sleep(500);
	    	    	             }
	    		           	}
	    	           }
	    	        	
	    	    
	    	        	
		                /**
		                 * Fim da transmissão de CEP
		                 */
	    	           
	    	            /**
	    	             * Inicio da transmissao de LOGRADOURO
//	    	             */
//	        	        String selectionLogradouro = Logradouros.INDICADORNOVO+ "=? AND " + Logradouros.INDICADOR_TRANSMITIDO + "=?";
//	        	        
//	        	        String[] selectionArgsLogradouro = new String[] {
//	        	        		String.valueOf(ConstantesSistema.SIM),
//	            	            String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO)
//	        	        };
	        	        
	        	        Cursor cursor =  Fachada.getInstance().getCursorListaLogradouro(Logradouro.class);
	        	        Logradouro logr = new Logradouro();
	    	        	List<? extends EntidadeBase> listaLogradouro = logr.carregarListaEntidade(cursor);
	    	        	
	    	        	//Valida se os logradouros ja foram enviados.
	    	            if (listaLogradouro == null || listaLogradouro.size() == 0) {
	    	                  
	    	            	//Atualiza a barra de progresso de logradouro
	    	            	myCepProgress = 100;
	    	            	myLogradouroProgress = 100;
	    	            	myLogradouroCepProgress = 0;
	    	            	myImovelProgress = 0;
	    	            	myFotoProgress = 0;
	    	            	
	    	                publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
	    	                Thread.sleep(500);
	    	                
	    	             } else {
	    	            	 
	    	            	 int l = 0; 
	    	        		 Iterator<? extends EntidadeBase> iteratorLogradouro = listaLogradouro.iterator();
	    	        		
	    	        		 while( iteratorLogradouro.hasNext() ) {
	    	        			 l++;
	    	        			 Logradouro logradouro = (Logradouro) iteratorLogradouro.next();
	
	    	        			 try {
	    	        				
	    	        				//Tentativa de transmissão do cep pra base do GSAN
		                            boolean sucess = web.enviarDadosLogradouro(logradouro, sistemaParametros);
		
		                            if (!sucess) {
		                                return ERROR_SEDING_SO_INFORMATION;
		                            } else {
		                            	//Caso o cep seja transmitido com sucesso atualizada o indicador que ja foi transmitido.
		                            	logradouro.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
		                                try {
		                                    Fachada.getInstance().update(logradouro);
		                                } catch (FachadaException fe) {
		                                    fe.printStackTrace();
		                                }
		                            }
		                        } catch (Exception e) {
		                            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
		                            return ERROR_GENERATING_RETURN_FILE;
		                        }
	    	        		 }
	
	    	        		 myLogradouroProgress = (int) (((double) l / (double) listaLogradouro.size()) * 100);
	    	        		 myCepProgress = 100;
	     	            	 myLogradouroCepProgress = 0;
	     	            	 myImovelProgress = 0;
	     	            	 myFotoProgress = 0;
	     	            	
	     	                 publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
	    	        		 Thread.sleep(500);
	    	             }
	                
	    	            /**
		                 * Fim da transmissão de LOGRADOURO
		                 */
	    	            
	    	            /**
	    	             * Inicio da transmissao de LOGRADOURO CEP
//	    	             */
//	        	        String selectionLogradouroCep = LogradouroCeps.INDICADORNOVO+ "=? AND " + LogradouroCeps.INDICADOR_TRANSMITIDO + "=?";
//	        	        
//	        	        String[] selectionArgsLogradouroCep = new String[] {
//	        	        		String.valueOf(ConstantesSistema.SIM),
//	            	            String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO)
//	        	        };
	    	            
	        	        Cursor cursor1 = Fachada.getInstance().getCursorListaLogradouroCep(LogradouroCep.class);
	        	        LogradouroCep logrCep = new LogradouroCep();
	    	        	List<? extends EntidadeBase> listaLogradouroCep = logrCep.carregarListaEntidade(cursor1);
	    	        	
	    	        	//Valida se os logradouros ja foram enviados.
	    	            if (listaLogradouroCep == null || listaLogradouroCep.size() == 0) {
	    	                    //Atualiza a barra de progresso de logradouro
	    	            	myCepProgress = 100;
	    	            	myLogradouroProgress = 100;
	    	            	myLogradouroCepProgress = 100;
	    	            	myImovelProgress = 0;
	    	            	myFotoProgress = 0;
	    	            	
	    	                publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
	    	                    Thread.sleep(500);
	    	             } else {
	    	            	 
	    	            	 int lc = 0; 
	    	        		 Iterator<? extends EntidadeBase> iteratorLogradouroCep = listaLogradouroCep.iterator();
	    	        		
	    	        		 while( iteratorLogradouroCep.hasNext() ) {
	    	        			 lc++;
	    	        			 LogradouroCep logradouroCep = (LogradouroCep) iteratorLogradouroCep.next();
	
	    	        			 try {
	    	        				//Tentativa de transmissão do cep pra base do GSAN
		                            boolean sucess = web.enviarDadosLogradouroCep(logradouroCep);
		
		                            if (!sucess) {
		                                return ERROR_SEDING_SO_INFORMATION;
		                            } else {
		                            	//Caso o cep seja transmitido com sucesso atualizada o indicador que ja foi transmitido.
		                            	logradouroCep.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
		                                try {
		                                    Fachada.getInstance().update(logradouroCep);
		                                } catch (FachadaException fe) {
		                                    fe.printStackTrace();
		                                }
		                            }
		                        } catch (Exception e) {
		                            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
		                            return ERROR_GENERATING_RETURN_FILE;
		                        }
	    	        		 }
	
	    	        		 myLogradouroCepProgress = (int) (((double) lc / (double) listaLogradouroCep.size()) * 100);
	    	        		 myCepProgress = 100;
	     	            	 myLogradouroProgress = 100;
	     	            	 myImovelProgress = 0;
	     	            	 myFotoProgress = 0;
	     	            	
	     	                 publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
	    	        		 Thread.sleep(500);
	    	             }
	    	            /**
	    	             * 	Fim da transmissao de Logradouro CEP
	    	             */
	                
	                
	    	            /**
	    	             * 	Inicio da transmissao de Imovel
	    	             */
	    	            String selectionImovel = ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO+ "=? AND " + ImovelAtlzCadastralColunas.INDICADOR_TRANSMITIDO + "=?";
	        	        
	        	        String[] selectionArgsImovel = new String[] {
	        	        		String.valueOf(ConstantesSistema.FINALIZADO),
	            	            String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO)
	        	        };
	               
	        	        List<? extends EntidadeBase> listaImovel = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class,
																	                		selectionImovel,
																	                		selectionArgsImovel,
	                                                                                       ImovelAtlzCadastralColunas.ID);
	
		                if (listaImovel.size() == 0) {
		
		                    myCepProgress = 100;
			            	myLogradouroProgress = 100;
			            	myLogradouroCepProgress = 100;
			            	myImovelProgress = 100;
			            	myFotoProgress = 0;
			            	
			                publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
		                    Thread.sleep(500);
		                }
		
		                int i = 0;
		                Iterator<? extends EntidadeBase> iteratorListaImovel = listaImovel.iterator();
		                while (iteratorListaImovel.hasNext()) {
		                    i++;
		                    ImovelAtlzCadastral imovelAtlzCadastral = (ImovelAtlzCadastral) iteratorListaImovel.next();
		
		                    try {
		                    	
		                    	//Pesquisa Cliente
		       		         String selection = ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID + "=?";
		       			        
		       		         String[] selectionArgs = new String[] {
		       		        		 imovelAtlzCadastral.getId().toString()
		       		         };
		       		         
		       		         ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();        
		       		         clienteAtlzCadastral =  (ClienteAtlzCadastral) Fachada.getInstance().pesquisar(clienteAtlzCadastral, selection, selectionArgs);
		       	   			
		       		         //CLIENTE FONE
		       		         List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral = null;
		       		         if ( clienteAtlzCadastral != null && clienteAtlzCadastral.getId() != null ) {
		       		        	 
		       		        	 //Pesquisa Logradouro
		       			         String selectionFone = ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID + "=?";
		       			        
		       			         String[] selectionArgsFone = new String[] {
		       			        		clienteAtlzCadastral.getId().toString()
		       			         };
		       		        	
		       		         	listaClienteFoneAtlzCadastral = (List<ClienteFoneAtlzCad>) Fachada.getInstance().pesquisarLista(ClienteFoneAtlzCad.class, selectionFone, selectionArgsFone, null);
		       		         }
		                    	
		       		         //Registro do tipo 08 - Hidrometro Atlz Cadastral
		    		         String selectionHidrometro = HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
		    		        
		    		         String[] selectionArgsHidrometro = new String[] {
		    		        		imovelAtlzCadastral.getId().toString()
		    		         };
		    	        	
		    	        	 List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad = (List<HidrometroInstHistAtlzCad>) Fachada.getInstance().
		    	        			 	pesquisarLista(HidrometroInstHistAtlzCad.class, selectionHidrometro, selectionArgsHidrometro, null);
		    			     
		    	        	  //Registro do tipo 09 - Subcategoria Atlz Cadastral
		    		         String selectionSub = ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
		    		        
		    		         String[] selectionArgsSub = new String[] {
		    		        		imovelAtlzCadastral.getId().toString()
		    		         };
		    	       
		    	        	
		    	        	 List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad = (List<ImovelSubCategAtlzCad>) Fachada.getInstance()
		    	        			 .pesquisarLista(ImovelSubCategAtlzCad.class, selectionSub, selectionArgsSub, null);
		
		    	        	 //Registro do tipo 10 - Imovel ocorrencia
		    			     String selectionOcorrencia = ImovelOcorrenciaColunas.IMOVELATLZCAD_ID + "=?";
		    			        
		    			     String[] selectionArgsOcorrencia = new String[] {
		    			        		imovelAtlzCadastral.getId().toString()
		    			     };
		    		        	
		    		         List<ImovelOcorrencia> listaImovelOcorrencia = (List<ImovelOcorrencia>) Fachada.getInstance()
		    		        		 .pesquisarLista(ImovelOcorrencia.class, selectionOcorrencia, selectionArgsOcorrencia, null);
		    		         
		                        boolean sucess = web.enviarDadosImovel(imovelAtlzCadastral, clienteAtlzCadastral, listaClienteFoneAtlzCadastral, listaHidrometroInstHistAtlzCad,
		                        		listaImovelSubCategAtlzCad, listaImovelOcorrencia);
		
		                        if (!sucess) {
		                            return ERROR_SEDING_SO_INFORMATION;
		                        } else {
		                            imovelAtlzCadastral.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
		                            try {
		                                Fachada.getInstance().update(imovelAtlzCadastral);
		                            } catch (FachadaException fe) {
		                                fe.printStackTrace();
		                            }
		                        }
		                    } catch (Exception e) {
		                        Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
		                        return ERROR_GENERATING_RETURN_FILE;
		                    }
		
		                    myImovelProgress = (int) (((double) i / (double) listaImovel.size()) * 100);
		
		                    
		                    myCepProgress = 100;
			            	myLogradouroProgress = 100;
			            	myLogradouroCepProgress = 100;
			            	
			                publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
		                    Thread.sleep(500);
		                }
	                
	                
		                //Fotos
		                List<? extends EntidadeBase> listaFoto = null;
		
		                String selectionPhoto = FotoColunas.INDICADORTRANSMITIDO + "=?";
		                
		                String[] selectionPhotoArgs = new String[] {
		                        String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO)
		                };
		
		                listaFoto = Fachada.getInstance().pesquisarLista(Foto.class, selectionPhoto, selectionPhotoArgs, FotoColunas.ID);
		
		                int j = 0;
		                Iterator<? extends EntidadeBase> iteratorListaFoto = listaFoto.iterator();
		                while (iteratorListaFoto.hasNext()) {
		                    j++;
		                    myFotoProgress = (int) (((double) j / (double) listaFoto.size()) * 100);
		
		                    Foto foto = (Foto) iteratorListaFoto.next();
		
		                    File imageFile = new File(foto.getFotoPath());
		                    String nomeFoto = imageFile.getName();
		                    ArrayList<String> dados = Util.split_(nomeFoto);
		
		                    PhotoConnection connection = new PhotoConnection();
		                    boolean sucesso = connection.doFileUpload(imageFile, dados.get(0), foto.getFotoTipo(), ConstantesSistema.ACTION, foto);
		//                    connection.execute(imageFile, dados.get(0), foto.getFotoTipo(), foto);
		                    
		                    if (!sucesso) {
		                    	System.out.println("Erro ao enviar foto: " + nomeFoto);
//		                        return ERROR_ABORT_REQUESTED;
		                    }else{
	                            foto.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
	                            try {
	                                Fachada.getInstance().update(foto);
	                            } catch (FachadaException fe) {
	                                fe.printStackTrace();
	                            }
		                    }
		                    
		                    myCepProgress = 100;
			            	myLogradouroProgress = 100;
			            	myLogradouroCepProgress = 100;
			            	
			                publishProgress(myImovelProgress,myFotoProgress, myCepProgress, myLogradouroProgress, myLogradouroCepProgress);
		                    Thread.sleep(500);
		                }
	                
		                Bundle b = new Bundle();
		                b = getIntent().getExtras();
		                String imoveisPendentes = null;
		                
		                if(b != null){
		                	imoveisPendentes = b.getString("imoveisPendentes");
		                }
		                
		                if(imoveisPendentes == null || !imoveisPendentes.equals("SIM")){
		                
			                //verifica se o usuario que ta finalizando é o supervisor.
			                if ( sistemaParametros.getSenha() != null && !sistemaParametros.getSenha().equals("") ) {
			                	ComunicacaoWebServer finalizarFile = new ComunicacaoWebServer(FinalizarRoteiroActivity.this);	
			                	//verifica se o supervisor esta online
			                	if ( finalizarFile.isServerOnline() ) { 
			                	
			                		boolean arquivofinalizado =	finalizarFile.routeInitializationSignal(ConstantesSistema.ARQUIVO_FINALIZADO);
			                		//verifica se o arquivo foi finalizado no gsan
					                if ( arquivofinalizado ) {
					                	return SUCESS;
					                } else {
					                	return ERROR_SERVER_OFFLINE;    	
					                }
			                	} else {
				                	return ERROR_SERVER_OFFLINE;    	
				                }
			            	}
		                }
                
		         } else {
		        	if ( verificador.equals(Integer.valueOf(2)) ){ 
		        		return ARQUIVO_FINALIZADO;
		        	} else {
		            	return ERROR_SERVER_OFFLINE;
		        	}
		         }
                
            } catch (Exception e) {
            	//Qualquer exception, informamos que não conseguimos executar
                Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
                return ERROR_GENERIC;
            }

            return SUCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
        	
            String errorMsg = null;

            switch (result) {

                case ERROR_GENERIC:
                    errorMsg = "Houve um problema desconhecido ao enviar os dados. Favor realizar finalização OFFLINE";
                    break;
                case ERROR_ABORT_REQUESTED:
                    errorMsg = "Operação Cancelada";
                    break;
                case ERROR_GENERATING_RETURN_FILE:
                    errorMsg = "Houve um erro na geração do arquivo de retorno";
                    break;
                case ERROR_SEDING_SO_INFORMATION:
                    errorMsg = "Houve um erro no envio das informações dos Imóveis para o GSAN. Favor repetir a operação.";
                    break;
                case ERROR_SERVER_OFFLINE:
                    errorMsg = "Erro de conexão. Sem acesso ao servidor GSAN.";
                    break;
                case ERROR_FILE_FINISHED:
                    errorMsg = "Erro ao transmitir a finalização do arquivo.";
                    break;
                case ERROR_SENDIG_PHOTO:
                    errorMsg = "Erro no envio das photos ao transmitir a finalização do arquivo.";
                    break;
                case ARQUIVO_FINALIZADO:
                    errorMsg = "O arquivo já foi finalizado.";
                    break;
                case SUCESS:
                	
                Bundle b = new Bundle();
                b = getIntent().getExtras();
                String imoveisPendentes = null;
                
                if(b != null){
                	imoveisPendentes = b.getString("imoveisPendentes");
                }
                
                if(imoveisPendentes == null || !imoveisPendentes.equals("SIM")){
	            	 Intent i = new Intent(FinalizarRoteiroActivity.this, ArquivoFinalizadoActivity.class);
	            	 startActivity(i);
	            	 finish();
            	}else{
            		  new AlertDialog.Builder(FinalizarRoteiroActivity.this)
                              .setMessage("Imóvel Transmitido com Sucesso!")
                              .setIcon(R.drawable.ok)
                              .setCancelable(false)
                              .setNeutralButton(ConstantesSistema.ALERT_OK,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {                                                	                                             		  
	                                   	               	 Intent i = new Intent(FinalizarRoteiroActivity.this, RoteiroActivity.class);
	                                   	            	 startActivity(i);
	                                   	            	 finish();
                                                    }
                                                })
                              .show();

            	}
                	 
            }

            if (errorMsg != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(FinalizarRoteiroActivity.this).setTitle(getString(R.string.problem_complet))
                                                                                                 .setMessage(errorMsg)
                                                                                                 .setIcon(R.drawable.ok)
                                                                                                 .setCancelable(false)
                                                                                                 .setNeutralButton(ConstantesSistema.ALERT_OK,
                                                                                                                   new DialogInterface.OnClickListener() {
                                                                                                                       @Override
                                                                                                                       public void onClick(
                                                                                                                               DialogInterface dialog,
                                                                                                                               int which) {

                                                                                                                       }
                                                                                                                   })
                                                                                                 .show();
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent i = new Intent(FinalizarRoteiroActivity.this,
                                              RoteiroActivity.class);
                        FinalizarRoteiroActivity.this.startActivity(i);
                        finish();
                    }
                });
            }
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            int myImovelProgress = values[0];
            int myfotoProgress = values[1];
            int myCepProgress = values[2];
            int myLogradouroProgress= values[3];
            int myLogradouroCep= values[4];

            prbImovel.setProgress(myImovelProgress);
            prbFoto.setProgress(myfotoProgress);
            prbCep.setProgress(myCepProgress);
            prbLogradouro.setProgress(myLogradouroProgress);
            prbLogradouroCep.setProgress(myLogradouroCep);
        }
    }

}