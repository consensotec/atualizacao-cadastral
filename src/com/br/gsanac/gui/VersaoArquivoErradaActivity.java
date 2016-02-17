package com.br.gsanac.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.br.gsanac.R;

/**
 * @author Arthur Carvalho
 * @since 09/09/2011
 */
public class VersaoArquivoErradaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.versao_errada);
        
        new AlertDialog.Builder(VersaoArquivoErradaActivity.this).setTitle(getString(R.string.str_alert_versao_desatualizada))
        .setMessage(R.string.str_error_aborted)
        .setIcon(R.drawable.ok)
        .setCancelable(false)
        .setNeutralButton(getString(R.string.sim),
                          new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(
                                      DialogInterface dialog,
                                      int which) {
                            	  Intent it = new Intent(VersaoArquivoErradaActivity.this,
                                          LoginActivity.class);
                            	  startActivity(it);
                                  finish();	
                              }
                          })
        .show();
    }

    @Override
    public void onBackPressed() {
    	 Intent it = new Intent(VersaoArquivoErradaActivity.this,
                 LoginActivity.class);
    	 startActivity(it);
        finish();
    }
    
    @Override
    public boolean onSearchRequested() {
        finish();
        return false;
    }
}