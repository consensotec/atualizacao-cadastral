package com.br.gsanac.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.br.gsanac.R;
import com.br.gsanac.adapter.RoteiroAdapter;
import com.br.gsanac.conexao.DBConnection;
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
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroCep.LogradouroCeps;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.ExportaBancoDados;
import com.br.gsanac.util.Util;

/**
 * @author Arthur Carvalho
 * @since 18/12/2012
 */
public class RoteiroActivity extends Activity {

	private RoteiroAdapter adapter;

	private Intent intent;

	private ListView listView;

	private Spinner spnBuscaTipo;

	private String[] tiposBusca;

	private ImageButton procurar;
	
	private EditText parametroInformado;
	
	private String tipoBuscaSelecionado;
	
	private int                 aux;
	
	private SistemaParametros sistemaParametros = new SistemaParametros();
	
	ProgressDialog mProgressDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.roteiro);

        //Para o teclado não aparecer ao entrar na tela
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
        Fachada.setContext(this);
        
    	try{
    		sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
    	} catch (FachadaException e) {
			e.printStackTrace();
		}
        
        if ( getIntent().getSerializableExtra(ConstantesSistema.INTEGRACAO_CODIGO_TIPO_FINALIZACAO) != null ) {
			
        	Integer codigoTipoFinalizado = (Integer) getIntent().getSerializableExtra(ConstantesSistema.INTEGRACAO_CODIGO_TIPO_FINALIZACAO);
        	//1 - online
        	//2 - offline
        	//verifica se é para finalizar on line
        	if ( codigoTipoFinalizado != null && codigoTipoFinalizado.intValue() == 1) {
        		
        		Intent i = new Intent(RoteiroActivity.this, FinalizarRoteiroActivity.class);
        		startActivity(i);
        		finish();
        	}//verifica se é para finalizar off line 
        	else if ( codigoTipoFinalizado != null && codigoTipoFinalizado.intValue() == 2) {
        		gerarArquivoOffLine();
        	}
        } else {
        
        listView = (ListView) findViewById(R.id.listaImoveis);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            	
            	//Verifica se o indicador de Mapa esta desativado, caso contrario obriga o usuario selecionar a posicao do imovel no mapa.
            	if ( sistemaParametros.getIndicadorMapa().toString().equals( String.valueOf(ConstantesSistema.NAO) ) ) {
            		
            		if (((ImovelAtlzCadastral) view.getTag()).getIndicadorTransmitido() != ConstantesSistema.INDICADOR_TRANSMITIDO) {
                    	view.setBackgroundResource(android.R.drawable.list_selector_background);

                        
                            intent = new Intent(RoteiroActivity.this, TabsActivity.class);

                            intent.putExtra(ConstantesSistema.IMOVEL, (ImovelAtlzCadastral) view.getTag());
                            Util.removerAtributosTabsActivity();

                            startActivity(intent);
                            finish();
                    	} else {
                    		 Util.showMessage(RoteiroActivity.this,
                                     "O imóvel selecionado já foi transmitido.",
                                     Toast.LENGTH_SHORT);
                    	}
            	} else {
            		  new AlertDialog.Builder(RoteiroActivity.this).setTitle("Mapa obrigatório")
                      .setMessage("É necessario identificar a localização do imóvel através do mapa.")
                      .setIcon(R.drawable.ok)
                      .setCancelable(false)
                      .setNeutralButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                
                                            }
                                        })

                      .show();
            	}
            	
            	
            	
            }
        });
        
        tiposBusca = new String[] { getString(R.string.str_imoveis_spinner_todos),
        		 getString(R.string.str_imoveis_spinner_matricula_imovel),
        		 getString(R.string.str_imoveis_spinner_imoveis_pendentes),
        		 getString(R.string.str_imoveis_spinner_posicao),
        		 getString(R.string.str_imoveis_spinner_numero_hidrometro),
        		 getString(R.string.str_imoveis_spinner_cpfcnpj),
        		 getString(R.string.str_imoveis_spinner_novo_imovel)
        };
    	
        spnBuscaTipo = (Spinner) findViewById(R.id.buscaTipo);
        
        ArrayAdapter<String> tipoBuscaAdapter = new ArrayAdapter<String>(RoteiroActivity.this,
    			android.R.layout.simple_spinner_item,tiposBusca);
    	tipoBuscaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    	spnBuscaTipo.setAdapter(tipoBuscaAdapter);
    	
    	parametroInformado = (EditText) findViewById(R.id.parametroInformado);
    	parametroInformado.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    	
        procurar = (ImageButton) findViewById(R.id.procurar);
         
        
        
        spnBuscaTipo.setOnItemSelectedListener(new OnItemSelectedListener() {

			
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) { 
				
				tipoBuscaSelecionado = (String) parent.getSelectedItem();

				//Todos imóveis
				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_todos))){

					List<? extends EntidadeBase> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

			        try {
			        	
			        	listaImoveis = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class, null, null, ImovelAtlzCadastralColunas.POSICAO);

			        } catch (FachadaException fe) {
			            Log.e(ConstantesSistema.LOG_TAG, fe.getMessage());
			        }
			        
			        System.out.println("TAMANHO DA LISTA"+listaImoveis.size());
			        
			        adapter = new RoteiroAdapter(RoteiroActivity.this,listaImoveis);

			        listView.setAdapter(adapter);
				
			        //Imóveis pendentes
				} else if ( tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_imoveis_pendentes) ) ) {
					
					List<? extends EntidadeBase> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

			        try {
			        	
			            String selection = ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO + "=?";
			            String[] selectionArgs = new String[] {
			                String.valueOf(ConstantesSistema.PENDENTE)
			            };
			        	
			        	listaImoveis = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class, selection, selectionArgs, ImovelAtlzCadastralColunas.POSICAO);

			        } catch (FachadaException fe) {
			            Log.e(ConstantesSistema.LOG_TAG, fe.getMessage());
			        }

			        adapter = new RoteiroAdapter(RoteiroActivity.this,listaImoveis);

			        listView.setAdapter(adapter);
			        
				}//pesquisa novos imoveis
				else if ( tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_novo_imovel) ) ) {
					
					List<? extends EntidadeBase> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

			        try {
			        	
			            String selection = ImovelAtlzCadastralColunas.IMOVEL_ID + " is null or " + ImovelAtlzCadastralColunas.IMOVEL_ID + " = 0 " ;
			        	
			        	listaImoveis = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class, selection, null, ImovelAtlzCadastralColunas.POSICAO);

			        } catch (FachadaException fe) {
			            Log.e(ConstantesSistema.LOG_TAG, fe.getMessage());
			        }

			        adapter = new RoteiroAdapter(RoteiroActivity.this,listaImoveis);

			        listView.setAdapter(adapter);
				}
								
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
         
        procurar.setOnClickListener(new OnClickListener() {
 			
 			
 			public void onClick(View arg0) {

 				
 				
 				String valorBusca = parametroInformado.getText().toString().trim();
 				
 				// Busca por matrícula
 				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_matricula_imovel))){

 					List<? extends EntidadeBase> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

			        try {
			        	
			            String selection = ImovelAtlzCadastralColunas.IMOVEL_ID + "=?";
			            String[] selectionArgs = new String[] {
			            		valorBusca
			            };
			        	
			        	listaImoveis = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class, selection, selectionArgs, ImovelAtlzCadastralColunas.POSICAO);

			        } catch (FachadaException fe) {
			            Log.e(ConstantesSistema.LOG_TAG, fe.getMessage());
			        } 			
			        
			        adapter = new RoteiroAdapter(RoteiroActivity.this,listaImoveis);
			        
			        listView.setAdapter(adapter);
						 				
 				}else // Busca por numero de ordem
 	 				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_posicao))){

 	 					List<? extends EntidadeBase> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

 				        try {
 				        	
 				            String selection = ImovelAtlzCadastralColunas.POSICAO + "=?";
 				            String[] selectionArgs = new String[] {
 				            		valorBusca
 				            };
 				        	
 				        	listaImoveis = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class, selection, selectionArgs, ImovelAtlzCadastralColunas.POSICAO);

 				        } catch (FachadaException fe) {
 				            Log.e(ConstantesSistema.LOG_TAG, fe.getMessage());
 				        } 			
 				        
 				        adapter = new RoteiroAdapter(RoteiroActivity.this,listaImoveis);

 				        listView.setAdapter(adapter);
 				        
 	 			}else // Busca por numero do hidrometro
 	 				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_numero_hidrometro))){
 	 					List<? extends EntidadeBase> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

 				        try {
 				        	
 				        	listaImoveis = Fachada.getInstance().pesquisarImovelPeloHidrometro(valorBusca);

 				        } catch (FachadaException fe) {
 				            Log.e(ConstantesSistema.LOG_TAG, fe.getMessage());
 				        } 			
 				        
 				        adapter = new RoteiroAdapter(RoteiroActivity.this,listaImoveis);

 				        listView.setAdapter(adapter);
 	 			}else // Busca pelo CPF/CNPJ
 	 				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_cpfcnpj))){
 	 					List<? extends EntidadeBase> listaImoveis = new ArrayList<ImovelAtlzCadastral>();

 				        try {
 				        	
 				        	
 				        	listaImoveis = Fachada.getInstance().pesquisarImovelPeloCPFCNPJ(valorBusca.replaceAll("[/.-]", ""));

 				        } catch (FachadaException fe) {
 				            Log.e(ConstantesSistema.LOG_TAG, fe.getMessage());
 				        } 			
 				        
 				        adapter = new RoteiroAdapter(RoteiroActivity.this,listaImoveis);

 				        listView.setAdapter(adapter);
 	 			}
 				
 			}
 		});
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo_imovel:
            	
            	//Verifica se o indicador de Mapa esta desativado, caso contrario obriga o usuario selecionar a posicao do imovel no mapa.
            	if ( sistemaParametros.getIndicadorMapa().toString().equals( String.valueOf(ConstantesSistema.NAO) ) ) {
            		
            		Util.removerAtributosTabsActivity();
            		Intent intent = new Intent(RoteiroActivity.this , TabsActivity.class);
            		intent.putExtra("indicadorNovo", true);
                    startActivity(intent);
                    finish();
                    
            	} else {
            		new AlertDialog.Builder(RoteiroActivity.this).setTitle("Mapa obrigatório")
                    .setMessage("É necessario identificar a localização do imóvel através do mapa.")
                    .setIcon(R.drawable.ok)
                    .setCancelable(false)
                    .setNeutralButton("Ok",
                                      new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(
                                                  DialogInterface dialog,
                                                  int which) {
                                              
                                          }
                                      })

                    .show();
            	}
            	
                return true;
            case R.id.carregar_mapa:
            	
            	try {
            		ArrayList<Integer> listaMatriculas = Fachada.getInstance().pesquisarMatriculas();
            		Integer codigoSetorComercial = Fachada.getInstance().pesquisarSetorComercialPrincipal();
            		
	            	Intent integracaoCompesa = new Intent(Intent.ACTION_MAIN);
	                integracaoCompesa.setClassName("compesa.mobile.mapalocal.activities", "compesa.mobile.mapalocal.activities.MainActivity");
	                integracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_ENVIO_MATRICULAS, listaMatriculas);
	                integracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_ENVIO_LOCALIDADE, sistemaParametros.getIdLocalidade());
	                integracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_ENVIO_SETORCOMERCIAL, String.valueOf(codigoSetorComercial));
	                startActivity(integracaoCompesa);
	                finish();
            	} catch (ActivityNotFoundException e) {
            		//Não existe o sistema de mapas
            		  new AlertDialog.Builder(RoteiroActivity.this).setTitle("Mapa Indisponível")
                      .setMessage("O sistema de mapas não está instalado.")
                      .setIcon(R.drawable.ok)
                      .setCancelable(false)
                      .setNeutralButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                    
                                            	try{
                                            		//desativa a obrigatoriedade de informar a localizacao do imóvel, habilitando selecionar o imóvel pela tela de roteiro.
                                            		sistemaParametros.setIndicadorMapa(ConstantesSistema.NAO);
                                            		
                                            		Fachada.getInstance().update(sistemaParametros);
                                            	} catch (FachadaException e) {
                                					e.printStackTrace();
                                				}
                                            	
                                            	
                                            	
                                            }
                                        })

                      .show();
            	} catch ( FachadaException fe ) {
            		fe.printStackTrace();
            	}
                return true;
            case R.id.menu_finalizar:
           
            	final CharSequence[] items = {
                         "Transmitir o arquivo online",
                         "Gerar o arquivo offline"
                 };

                 AlertDialog.Builder builder = new AlertDialog.Builder(this);
                 builder.setTitle("Finalização de roteiro:");
                 builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int item) {
                         aux = item;
                     }
                 });
                
                 builder.setPositiveButton(getString(R.string.processar), new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface arg0, int arg1) {
                         // Finalizar roteiro on line
                         if (aux == 0) {
                             Intent i = new Intent(RoteiroActivity.this,
                                                   FinalizarRoteiroActivity.class);
                             startActivity(i);
                             finish();
                             // Finalizar roteiro off line
                         } 
                         else if (aux == 1) {
                        	 gerarArquivoOffLine();
                         }
                     }
                 });
                 builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface arg0, int arg1) {
                     }
                 });
                 AlertDialog alert = builder.create();
                 alert.show();
                return true;
                
            case R.id.exportar_banco:
          	
                mProgressDialog = new ProgressDialog(RoteiroActivity.this) {

                    @Override
                    public void onBackPressed() {}

                    @Override
                    public boolean onSearchRequested() { return false;}
                };
                
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setTitle(getString(R.string.exportando_banco));
                mProgressDialog.setMessage(getString(R.string.carregando));
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setMax(100);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setIcon(R.drawable.transfer);
                mProgressDialog.show();
                
                AsyncTask<Object, Object, Object> taskExportaBanco = new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {               
                    	new ExportaBancoDados().exportarBanco();  
                    	mProgressDialog.dismiss();
                    	return true;
                    }
                    
                    @Override
                    protected void onPostExecute(Object result) {
                        mProgressDialog.dismiss();
                        
                        new AlertDialog.Builder(RoteiroActivity.this).setTitle("BANCO EXPORTADO")
                        .setIcon(R.drawable.warning)
                        .setMessage("Banco exportado com sucesso! \nCaminho: " + ConstantesSistema.SDCARD_BANCO_PATH)
                        .setNeutralButton(ConstantesSistema.ALERT_OK,
                                          new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(
                                                      DialogInterface dialog,
                                                      int which) {
                                              }
                                          })
                        .show();
                    }
                };
                
                taskExportaBanco.execute();
     	
            	return true;
            	
            	
            case R.id.carregar_arq_dividido:
                
            	if ( sistemaParametros.getLogin() != null && !sistemaParametros.getLogin().equals("") &&
            			sistemaParametros.getSenha() != null && !sistemaParametros.getSenha().equals("") ) {
            		
            		Intent i = new Intent(RoteiroActivity.this,SelecionarArquivoDivididoActivity.class);
              	  	startActivity(i);
              	  	finish();
            	} else {
            		 Util.showMessage(RoteiroActivity.this, "Não é possivel carregar o arquivo dividido.",Toast.LENGTH_SHORT);
            	}
            	
            	 
                return true;
                
            case R.id.trasmitir_imoveis:
            	String  imoveisPendentes = "SIM"; 
            	Intent intent = new Intent(RoteiroActivity.this, FinalizarRoteiroActivity.class);
            	intent.putExtra("imoveisPendentes", imoveisPendentes);
            	startActivity(intent);
            	finish();
           return true;
           
            case R.id.carregar_relatorio:
        		Intent i = new Intent(RoteiroActivity.this, RelatorioActivity.class);
          	  	startActivity(i);
          	  	finish();
          	  return true;
          	  
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        
        finish();
    }

    @Override
    public boolean onSearchRequested() {
        finish();
        return false;
    }
    
    /**
     * Metodo responsavel por gerar o arquivo e as fotos de retorno.
     * 
     * @author Arthur Carvalho
     */
    private void gerarArquivoOffLine() {
        
    	/*
    	 * EXPORTA O BANCO DE DADOS
    	 * AUTOMATICAMENTE AO FINALIZAR 
    	 * O ROTEIRO  
    	 */
    	exportarBancoDeDadosAutomatico();
    	
    	StringBuilder fileContent = new StringBuilder("");
    	
        try {
            /*
             * MONTA O REGISTRO DO TIPO FINALIZAR ARQUIVO OU DO TIPO  
             * FINALIZADO POR USUARIO SEM LOGIN(SEM PERMISSAO PARA 
             * FINALIZAR O ARQUIVO)
             */
        	fileContent = montarRegistroTipo13UsuarioSemLogin(fileContent);
        
        	/*
             * MONTA O ARQUIVO REGISTRO 01 TIPO CEP
             */
        	fileContent = montandoArquivoRegistroTipo01(fileContent);
        
        	/*
        	 * MONTA O ARQUIVO REGISTRO 02 TIPO LOGRADOURO
        	 */
        	fileContent = montandoArquivoRegistroTipo02(fileContent);
            
        	/*
        	 * MONTA O ARQUIVO REGISTRO 03 TIPO LOGRADOURO CEP
        	 */
        	fileContent = montandoArquivoRegistroTipo03(fileContent);
        	
        	try{
            	
            	//Apaga Arquivo de Retono existente
            	File diretorioArquivoRetorno = new File(ConstantesSistema.SDCARD_GSANAC_RETURN_PATH);
                Util.deletarPastas(diretorioArquivoRetorno);
                
                // Gera nome do arquivo
                String fileName = sistemaParametros.getIdComando() + "_" +  sistemaParametros.getLogin();
                
            	Util.createSystemDirs();

                // Gera o arquivo de retorno
                File file = new File(ConstantesSistema.SDCARD_GSANAC_RETURN_PATH + "/" + fileName + ".txt");
              
                // criar o arquivo zip
				File arquivoZip = new File(ConstantesSistema.SDCARD_GSANAC_RETURN_PATH + "/"+ sistemaParametros.getIdComando().toString()+ "_" +  sistemaParametros.getLogin() + ".zip" );
			    ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( arquivoZip ) );
	            
	            
	            /**
	             * Montando o registro Imovel
	             */
			    
			    String selection1 = ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO + "=? AND " + ImovelAtlzCadastralColunas.INDICADOR_TRANSMITIDO + "=?";
		        
		         String[] selectionArgs1 = new String[] {
		        		 "1",
		        		 "2"
		         };
			    
	            List<? extends EntidadeBase> listaImovel = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class,
	            		selection1,
	            		selectionArgs1,
	                                                                                   ImovelAtlzCadastralColunas.ID);
	            Iterator<? extends EntidadeBase> iteratorListaImovel = listaImovel.iterator();
	           
	            ImovelAtlzCadastral imovelAtlzCadastral = null;
	            ClienteAtlzCadastral clienteAtlzCadastral = null;
	            
	            while (iteratorListaImovel.hasNext()) {
	                
	                imovelAtlzCadastral = (ImovelAtlzCadastral) iteratorListaImovel.next();
	
	                //Verifica se o imóvel ja foi atualizado e se ja foi transmitido.
//	                if (imovelAtlzCadastral.getIndicadorFinalizado().toString().equals(String.valueOf(ConstantesSistema.FINALIZADO))
//	                        && imovelAtlzCadastral.getIndicadorTransmitido().toString().equals(String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO))) {
	                	 //IMOVEL 
	                	 fileContent.append(Fachada.getInstance().gerarArquivoRetornoImovel(imovelAtlzCadastral));
	                	
	                	 //Pesquisa Cliente
        		         String selection = ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID + "=?";
        		        
        		         String[] selectionArgs = new String[] {
        		        		 imovelAtlzCadastral.getId().toString()
        		         };
        		         
        		         clienteAtlzCadastral = new ClienteAtlzCadastral();        
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
        		         
	                	 //CLIENTE | CLIENTE FONE
        		         fileContent.append(Fachada.getInstance().gerarArquivoRetornoCliente(clienteAtlzCadastral, listaClienteFoneAtlzCadastral, imovelAtlzCadastral.getIntegracaoID()));
        		         
        		         //Registro do tipo 08 - Hidrometro Atlz Cadastral
    			         String selectionHidrometro = HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
    			        
    			         String[] selectionArgsHidrometro = new String[] {
    			        		imovelAtlzCadastral.getId().toString()
    			         };
    		        	
    			         List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad = (List<HidrometroInstHistAtlzCad>) Fachada.getInstance().
    		        			 	pesquisarLista(HidrometroInstHistAtlzCad.class, selectionHidrometro, selectionArgsHidrometro, null);
        			     
    		        	 fileContent.append(Fachada.getInstance().gerarArquivoRetornoHidrometro(listaHidrometroInstHistAtlzCad, imovelAtlzCadastral.getIntegracaoID(), imovelAtlzCadastral.getImovelId()));
        		         
        			     //Registro do tipo 09 - Subcategoria Atlz Cadastral
				         String selectionSub = ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
				        
				         String[] selectionArgsSub = new String[] {
				        		imovelAtlzCadastral.getId().toString()
				         };
			       
			        	
				         List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad = (List<ImovelSubCategAtlzCad>) Fachada.getInstance()
			        			 .pesquisarLista(ImovelSubCategAtlzCad.class, selectionSub, selectionArgsSub, null);
    						
			        	 fileContent.append(Fachada.getInstance().gerarArquivoRetornoSubategoria(listaImovelSubCategAtlzCad, imovelAtlzCadastral.getIntegracaoID()));
			             
        			     //Registro do tipo 10 - Imovel ocorrencia
					     String selectionOcorrencia = ImovelOcorrenciaColunas.IMOVELATLZCAD_ID + "=?";
					        
					     String[] selectionArgsOcorrencia = new String[] {
					        		imovelAtlzCadastral.getId().toString()
					     };
				       
				        	
					     List<ImovelOcorrencia> listaImovelOcorrencia = (List<ImovelOcorrencia>) Fachada.getInstance()
				        		 .pesquisarLista(ImovelOcorrencia.class, selectionOcorrencia, selectionArgsOcorrencia, null);
							
				         fileContent.append(Fachada.getInstance().gerarArquivoRetornoOcorrencia(listaImovelOcorrencia, imovelAtlzCadastral.getIntegracaoID()));
				   // }
	                
	                //Pesquisar Foto do imóvel
	                String selectionFoto = FotoColunas.IMOVELATLZCAD_ID + "=? AND " + FotoColunas.INDICADORTRANSMITIDO + "=?";
	                
	                String[] selectionArgsFoto = new String[] {
	                    String.valueOf(imovelAtlzCadastral.getId()),
	                    String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO),
	                };
	        	
	        		try {
	        			
	        			List<? extends EntidadeBase> listaFoto =  Fachada.getInstance().pesquisarLista(Foto.class, selectionFoto, selectionArgsFoto, null);
	        			
	        			Iterator<? extends EntidadeBase> iteratorListaFoto = listaFoto.iterator();
	    	            while (iteratorListaFoto.hasNext()) {
	    	                
	    	            	Foto foto = (Foto) iteratorListaFoto.next();
	    	            	
	    	            	StringBuilder sb = new StringBuilder("");
		    				sb.append("12|");
		    				sb.append(foto.getFotoPath()+"|");
		    				sb.append(foto.getFotoTipo()+"|");
		    				sb.append(imovelAtlzCadastral.getIntegracaoID()+"|");
		    				sb.append("\n");
	    	            	fileContent.append(sb);
	    	            	
	    	            	try {
								Util.addFileToZip("", foto.getFotoPath(), zos);
							
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    	            }
	    	            
	           		} catch (FachadaException e) {
	        			Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
	        		}
	           }
	                  
	            /*
	        	 * MONTA O ARQUIVO REGISTRO 00 TIPO RESPONSAVEL 
	        	 * POR FINALIZAR O ARQUIVO NO GSAN
	        	 */
	            fileContent = montandoArquivoRegistroTipo00(fileContent);
	          
	            // Escreve no arquivo de retorno criado
	            FileOutputStream fileOut = null;
           	
                fileOut = new FileOutputStream(file);
                fileOut.write(fileContent.toString().getBytes());
                
                
                StringBuilder arquivoDeLog = new StringBuilder("");
                Util.adicionarArquivo(zos, file);
         	    
                zos.close();
			    file.delete();
			    
                DBConnection.getInstance(Fachada.getContext()).deleteDatabase();
                Util.removeInstanceRepository();
                
                File diretorio = new File(ConstantesSistema.SDCARD_GSANAC_PHOTOS_PATH);
            	File diretorioArquivo = new File(ConstantesSistema.SDCARD_GSANAC_FILES_PATH);
            	File diretorioArquivoDividido = new File(ConstantesSistema.SDCARD_GSANAC_ARQUIVO_DIVIDIDO_PATH);
                Util.deletarPastas(diretorio);
                Util.deletarPastas(diretorioArquivo);
                Util.deletarPastas(diretorioArquivoDividido);

                // Informamos ao usuário que foi tudo certo
                new AlertDialog.Builder(RoteiroActivity.this).setTitle("Roteiro Encerrado")
                	.setMessage("O arquivo foi gerado no cartão de memoria, localizado na pasta gsanAC/retorno.")
                    .setIcon(R.drawable.ok)
                    .setCancelable(false)
                    .setNeutralButton("Ok",
                    		
                new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
										Intent i = new Intent(
												RoteiroActivity.this,
												LoginActivity.class);
										startActivity(i);
										finish();
									}
								})

						.show();

            } catch (FileNotFoundException e) {
                Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
            } catch (IOException e) {
                Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
            }

        } catch (FachadaException e) {
            e.printStackTrace();
        }

    }
    
    public StringBuilder montarRegistroTipo13UsuarioSemLogin(StringBuilder fileContent){
    	if ( !(sistemaParametros.getLogin() != null && !sistemaParametros.getLogin().equals("") &&
    			sistemaParametros.getSenha() != null && !sistemaParametros.getSenha().equals("")) ) {
    		//Arquivo do tipo usuario sem permissao para finalizar o arquivo.
    		StringBuilder s = new StringBuilder("");
			s.append("13|");
			s.append(Util.stringPipe(sistemaParametros.getIdComando()));
			s.append("\n");
        	fileContent.append(s);
        	
        	//adicionar imoveis que ja foram transmitidos para que no tablet do "supervisor"(usuario com permissao de finalizar arquivo)
        	//seja possivel ver os imoveis ja transmitidos.
        	montarRegistroTipo11AdicionarImoveisQueForamTransmitidos(fileContent);
        	
    	}
    	return fileContent;
    }
    
    public StringBuilder montarRegistroTipo11AdicionarImoveisQueForamTransmitidos(StringBuilder fileContent ){
    	//adicionar imoveis que ja foram transmitidos para que no tablet do "supervisor"(usuario com permissao de finalizar arquivo)
    	//seja possivel ver os imoveis ja transmitidos.
        List<? extends EntidadeBase> listaImovel1;
		try {
			listaImovel1 = Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class,
			                                                                       null,
			                                                                       null,
			                                                                       ImovelAtlzCadastralColunas.ID);
			Iterator<? extends EntidadeBase> iteratorListaImovel1 = listaImovel1.iterator();
	        while (iteratorListaImovel1.hasNext()) {
	            
	            ImovelAtlzCadastral imovelAtlzCadastral = (ImovelAtlzCadastral) iteratorListaImovel1.next();

	            //Verifica se o imóvel ja foi atualizado e se ja foi transmitido e não é imovel novo.
	            if (imovelAtlzCadastral.getIndicadorFinalizado().toString().equals(String.valueOf(ConstantesSistema.FINALIZADO))
	                    && imovelAtlzCadastral.getIndicadorTransmitido().toString().equals(String.valueOf(ConstantesSistema.INDICADOR_TRANSMITIDO))
	                    && (imovelAtlzCadastral.getImovelId() != null && !imovelAtlzCadastral.getImovelId().toString().equals("") 
	                    && !imovelAtlzCadastral.getImovelId().toString().equals("0")) ) {
	            	
	            	StringBuilder sb = new StringBuilder("");
					sb.append("11|");
					sb.append(imovelAtlzCadastral.getId()+"|");
					sb.append("\n");
	            	fileContent.append(sb);
	            }
	        }
		} catch (FachadaException e) {
			e.printStackTrace();
		}
    	return fileContent;
    }
    
    public StringBuilder montandoArquivoRegistroTipo01(StringBuilder fileContent){
    
    	/**
         * Montando o arquivo Registro tipo CEP
         */
        String selectionLogCep = LogradouroCeps.INDICADORNOVO+ "=? ";
        
        String[] selectionArgsLogCep = new String[] {
        		String.valueOf(ConstantesSistema.SIM)
        };
        
        try{
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
	            	
	            	if ( listaCep != null ) {
	            		
	            		Iterator<? extends EntidadeBase> iteratorCep = listaCep.iterator();
	           		 	while( iteratorCep.hasNext() ) {
	           		 		
	           		 		Cep cep = (Cep) iteratorCep.next();
	           		 		
	           		 		if ( cep.getCodigoUnico() == null || cep.getCodigoUnico().equals("") ) {
	           		 			Long dateTime = new Date().getTime();
	           		 			cep.setCodigoUnico(dateTime.toString());
	           		 			Fachada.getInstance().update(cep);
	           		 		}
	           		 		
	           		 		fileContent.append(Fachada.getInstance().gerarArquivoRetornoCep(cep));
	           		 	}
	            	}
	           	}
	       }
        } catch (FachadaException e) {
			e.printStackTrace();
		}
        
    	return fileContent;
    }
    
    public StringBuilder montandoArquivoRegistroTipo02(StringBuilder fileContent){
    	
		
        try {
        	//          String selectionLogradouro = Logradouros.INDICADORNOVO+ "=? AND " + Logradouros.INDICADOR_TRANSMITIDO + "=?";
    		//        
    		//        String[] selectionArgsLogradouro = new String[] {
    		//        		String.valueOf(ConstantesSistema.SIM),
    		//	            String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO)
    		//        };
        	
        	 Cursor cursor = Fachada.getInstance().getCursorListaLogradouro(Logradouro.class);
             Logradouro logr = new Logradouro();
             List<? extends EntidadeBase> listaLogradouro = logr.carregarListaEntidade(cursor);
       		
          	if ( listaLogradouro != null ) {
          	
          		Iterator<? extends EntidadeBase> iteratorLogradouro = listaLogradouro.iterator();
              	while( iteratorLogradouro.hasNext() ) {

              		Logradouro logradouro = (Logradouro) iteratorLogradouro.next();
          			 
              		fileContent.append(Fachada.getInstance().gerarArquivoRetornoLogradouro(logradouro, sistemaParametros));
          		}
          	}      
        } catch (FachadaException e) {
			e.printStackTrace();
		}
    	return fileContent;
    }
    
    public StringBuilder montandoArquivoRegistroTipo03(StringBuilder fileContent){

    	try {
    		//          String selectionLogradouroCep = LogradouroCeps.INDICADORNOVO+ "=? AND " + LogradouroCeps.INDICADOR_TRANSMITIDO + "=?";
    		//        
    		//        String[] selectionArgsLogradouroCep = new String[] {
    		//        		String.valueOf(ConstantesSistema.SIM),
    		//	            String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO)
    		//        };
        	
            Cursor cursor1 = Fachada.getInstance().getCursorListaLogradouroCep(LogradouroCep.class);
            LogradouroCep logrCep = new LogradouroCep();
        	List<? extends EntidadeBase> listaLogradouroCep = logrCep.carregarListaEntidade(cursor1);  
        	
            if ( listaLogradouroCep != null ) {
            
            	Iterator<? extends EntidadeBase> iteratorLogradouroCep = listaLogradouroCep.iterator();
     		
            	while( iteratorLogradouroCep.hasNext() ) {
            		
            		LogradouroCep logradouroCep = (LogradouroCep) iteratorLogradouroCep.next();
            		
            		fileContent.append(Fachada.getInstance().gerarArquivoRetornoLogradouroCep(logradouroCep));
            	}
            }
    	 } catch (FachadaException e) {
 			e.printStackTrace();
 		}
     	return fileContent;
    }
    
    public StringBuilder montandoArquivoRegistroTipo00(StringBuilder fileContent){
    	
    	if ( sistemaParametros.getLogin() != null && !sistemaParametros.getLogin().equals("") &&
    			sistemaParametros.getSenha() != null && !sistemaParametros.getSenha().equals("") ) {

    		//Indicador Responsavel por finalizar o arquivo no gsan.
        	StringBuilder sb = new StringBuilder("");
			sb.append("00|");
			sb.append(Util.stringPipe(sistemaParametros.getIdComando()));
			sb.append(Util.stringPipe(5));
			sb.append("\n");
        	fileContent.append(sb);
    	
        }
    	
    	return fileContent;
    }
  
    public void exportarBancoDeDadosAutomatico(){
    	    	new ExportaBancoDados().exportarBanco();  
    }  
              
}