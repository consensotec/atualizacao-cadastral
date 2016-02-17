package com.br.gsanac.gui;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.br.gsanac.R;
import com.br.gsanac.adapter.ArquivoAdapter;
import com.br.gsanac.conexao.DBConnection;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.DBLoader;
import com.br.gsanac.util.Util;

/**
 * @author Arthur Carvalho
 * @since 09/09/2011
 */
public class SelecionarArquivoActivity extends Activity {

    private File         dir;

    private List<String> filesList;

    private ArquivoAdapter  fileAdapter;

    private String       path;
    
    private ProgressDialog mProgressDialog;
    
    private String cpfLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.arquivo_selecionar);

        Fachada.setContext(this);

        if ( getIntent().getSerializableExtra(ConstantesSistema.CPF_LOGIN) != null ) {
        	cpfLogin	= (String) getIntent().getSerializableExtra(ConstantesSistema.CPF_LOGIN); 
		}
         
        
        Util.createSystemDirs();

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith(".txt");
            }
        };

        filesList = new ArrayList<String>();

        ListView listView = (ListView) findViewById(R.id.listFiles);

        dir = new File(ConstantesSistema.SDCARD_GSANAC_FILES_PATH);

        File[] files = dir.listFiles(filter);

        if (files != null && files.length > 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    filesList.add(file.getName());
                }
            }
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(SelecionarArquivoActivity.this).setTitle(getString(R.string.nao_exist_arquivo_para_carregar))
                                                                                        .setIcon(R.drawable.erro)
                                                                                        .setCancelable(false)
                                                                                        .setNegativeButton(getString(R.string.sair),
                                                                                                           new DialogInterface.OnClickListener() {
                                                                                                               @Override
                                                                                                               public void onClick(
                                                                                                                       DialogInterface dialog,
                                                                                                                       int which) {

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

                 mProgressDialog = new ProgressDialog(SelecionarArquivoActivity.this) {

                    @Override
                    public void onBackPressed() {
                    }

                    @Override
                    public boolean onSearchRequested() {
                        return false;
                    }

                };
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setTitle(getString(R.string.carregar_offline));
                mProgressDialog.setMessage(getString(R.string.carregando));
                mProgressDialog.setCancelable(false);
                mProgressDialog.setIcon(R.drawable.transfer);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.show();

                AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {

                    @Override
                    protected Object doInBackground(Object... arg0) {
                        InputStream is = null;
                        try {
                            is = new FileInputStream(dir + "/" + path);

                            DBLoader.loadDatabaseFromInputStream(is, true, cpfLogin);
                            
                            String strVersaoAtual = Util.getVersaoSistema(SelecionarArquivoActivity.this);
                            
                            
                            SistemaParametros sistemaParametros = new SistemaParametros();
                            try{
                        		sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
                        	} catch (FachadaException e) {
                    			e.printStackTrace();
                    		}
                            
                        	Integer intVersaoAtual = Integer.valueOf(strVersaoAtual.replace(".", ""));
                        	Integer intNovaVersao = Integer.valueOf(sistemaParametros.getNumeroVersao().replace(".", ""));
                        	
                        	if(intNovaVersao > intVersaoAtual){
                        		mProgressDialog.dismiss();
                        		  //remove o arquivo carregado.
                          	    DBConnection.getInstance(Fachada.getContext()).deleteDatabase();
                                Util.removeInstanceRepository();
                        		 //caso o arquivo tenha sido carregado - envia o usuario para a tela de login
    	                        Intent i = new Intent(SelecionarArquivoActivity.this, VersaoArquivoErradaActivity.class);
    	                        startActivity(i);
    	                        finish();
                    		} else {
	
	                            mProgressDialog.dismiss();
	                            Intent it = new Intent(SelecionarArquivoActivity.this,
	                                                   LoginActivity.class);
	                            startActivity(it);
	                            finish();
                    		}

                        } catch (FileNotFoundException e) {
                            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
                        }

                        return null;
                    }

                };

                taskDownloadFile.execute();
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