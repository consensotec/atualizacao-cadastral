package com.br.gsanac.gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.br.gsanac.R;
import com.br.gsanac.adapter.ArquivoAdapter;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.CarregarArquivoDividido;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

/**
 * @author Arthur Carvalho
 * @since 09/09/2011
 */
public class SelecionarArquivoDivididoActivity extends Activity {

    private File         dir;

    private List<String> filesList;

    private ArquivoAdapter  fileAdapter;

    private String       path;
    
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.arquivo_dividido_selecionar);

        Fachada.setContext(this);

        Util.createSystemDirs();

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith(".zip");
            }
        };

        filesList = new ArrayList<String>();

        ListView listView = (ListView) findViewById(R.id.listFiles);

        dir = new File(ConstantesSistema.SDCARD_GSANAC_ARQUIVO_DIVIDIDO_PATH);

        File[] files = dir.listFiles(filter);

        if (files != null && files.length > 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    filesList.add(file.getName());
                }
            }
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(SelecionarArquivoDivididoActivity.this)
            											.setTitle(getString(R.string.nao_exist_arquivo_para_carregar))
                                                        .setIcon(R.drawable.erro)
                                                        .setCancelable(false)
                                                        .setNegativeButton(getString(R.string.sair),
                                                                           new DialogInterface.OnClickListener() {
                                                                               @Override
                                                                               public void onClick(
                                                                                       DialogInterface dialog,
                                                                                       int which) {

                                                                            	    Intent it = new Intent(SelecionarArquivoDivididoActivity.this,RoteiroActivity.class);
                                                                                    startActivity(it);
                                                                                    finish();
                                                                               }
                                                                           })
                                                        .show();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {

                    finish();
                }
            });

        }

        fileAdapter = new ArquivoAdapter(this,
                                      filesList);

        listView.setOnItemLongClickListener( new OnItemLongClickListener() {
		

            @Override
            public boolean onItemLongClick(AdapterView<?> parentView, View v, int position, long id) {

                v.setBackgroundColor(android.R.drawable.list_selector_background);

                // Guarda o path do diretorio selecionado
                path = (String) v.getTag();

                try {
					Date dataArquivoCarregado = Fachada.getInstance().pesquisarArquivoDivididoCarregado(path.substring(0, path.length() - 4));
					
					if ( dataArquivoCarregado != null ) {
						
						
						Util.showMessage(SelecionarArquivoDivididoActivity.this, "Arquivo j� Carregado no dia " + Util.convertDateToString(dataArquivoCarregado), Toast.LENGTH_SHORT);
						
						
					} else {
						 mProgressDialog = new ProgressDialog(SelecionarArquivoDivididoActivity.this) {

			                    @Override
			                    public void onBackPressed() {
			                    }

			                    @Override
			                    public boolean onSearchRequested() {
			                        return false;
			                    }

			                };
			                mProgressDialog.setIndeterminate(true);
			                mProgressDialog.setTitle("Carregando Arquivo Dividido");
			                mProgressDialog.setMessage(getString(R.string.carregando));
			                mProgressDialog.setCancelable(false);
			                mProgressDialog.setIcon(R.drawable.transfer);
			                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			                mProgressDialog.show();

			                AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {

			                    @Override
			                    protected Object doInBackground(Object... arg0) {
			                        InputStream istrm = null;
			                        try {
			                        	istrm = new FileInputStream(dir + "/"+ path);

//			                            InputStream istrm = item.getInputStream();
			    						ZipInputStream zipInputStream = new ZipInputStream(istrm);
			    						
			    						ZipEntry entry;
			    						//primeiro carrega o txt
			    			            while ( (entry = zipInputStream.getNextEntry() ) != null ) {
			    			            	ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			    			            	//zip.getInputStream(zip.getEntry("test.txt"));
			    			            	String extensaoArquivo = entry.getName().substring(entry.getName().lastIndexOf("."), entry.getName().length());
			    			            	if( extensaoArquivo.toUpperCase().equals(".TXT") ){
			    			            		
			    			            		int count;
			    			            		byte data[] = new byte[2048];
			    			            	    String filename = entry.getName();
			    			            	    System.out.println("Filename: " + filename);
			    			            	    
			    			            	    while ((count = zipInputStream.read(data, 0, 2048)) != -1) {
			    			            	    	byteOut.write(data, 0, count);
			    			            	    }
			    			            	     
			    			            	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteOut.toByteArray());
			    			            		
			    			            		CarregarArquivoDividido.carregarArquivoFromInputStream(byteArrayInputStream, true);
			    			            	} 
			    			            	
			    			            }
			    			            entry= null;
			    			            
			    			            InputStream istrmf = new FileInputStream(dir + "/" + path);
			    						ZipInputStream zipInputStreamf = new ZipInputStream(istrmf);
			    			            //carrega as imagens
			    			            while ( (entry = zipInputStreamf.getNextEntry() ) != null ) {
			    			            	ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			    			            	
			    			            	String extensaoArquivo = entry.getName().substring(entry.getName().lastIndexOf("."), entry.getName().length());
			    			            	if ( extensaoArquivo.toUpperCase().equals(".JPG") ) {
			    			            		
			    			            		int count;
			    			            		byte data[] = new byte[1024];
			    			            	    String filename = entry.getName();
			    			            	    System.out.println("Filename: " + filename);
			    			            	    while ((count = zipInputStreamf.read(data, 0, 1024)) != -1) {
			    			            	    	byteOut.write(data, 0, count);
			    			            	    }
			    			            	    
			    			            	    data = byteOut.toByteArray();
			    			            	    
			    			            	    File file = new File(ConstantesSistema.SDCARD_GSANAC_PHOTOS_PATH + "/" + filename ); 
			    			            	    // Escreve no arquivo de retorno criado
			    			    	            FileOutputStream fileOut = null;
			    			               	
			    			                    fileOut = new FileOutputStream(file);
			    			                    fileOut.write(data);
			    			            	}
			    			            	
			    			            }
			    			            
			    			            try {
											Fachada.getInstance().inserirArquivoDividido(path);
										} catch (FachadaException e) {
											e.printStackTrace();
										}
			                            
			    			          mProgressDialog.dismiss();
			                          Intent it = new Intent(SelecionarArquivoDivididoActivity.this,RoteiroActivity.class);
			                          startActivity(it);
			                          finish();
			                            
			  
			                        } catch (FileNotFoundException e) {
			                            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
			                        } catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

			                        return null;
			                    }

			                };

			                taskDownloadFile.execute();
					}
				} catch (FachadaException e) {
					e.printStackTrace();
				}
               
				return false;
                
            }
        });

        listView.setAdapter(fileAdapter);
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
}