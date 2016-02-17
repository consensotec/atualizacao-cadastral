package com.br.gsanac.gui;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.br.gsanac.R;
import com.br.gsanac.conexao.DBConnection;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

public class ArquivoFinalizadoActivity extends BaseActivity {

   
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Fachada.setContext(this);
        
     // Em caso de sucesso, apagamos o banco e a pasta das fotos/Arquivo/Arquivo Dividido
        File diretorioFoto = new File(ConstantesSistema.SDCARD_GSANAC_PHOTOS_PATH);
        File diretorioArquivoDividido = new File(ConstantesSistema.SDCARD_GSANAC_ARQUIVO_DIVIDIDO_PATH);
        File diretorioArquivo = new File(ConstantesSistema.SDCARD_GSANAC_FILES_PATH);
        
        Util.deletarPastas(diretorioFoto);
        Util.deletarPastas(diretorioArquivo);
        Util.deletarPastas(diretorioArquivoDividido);

        DBConnection.getInstance(ArquivoFinalizadoActivity.this).deleteDatabase();
        Util.removeInstanceRepository();

        // Informamos ao usuário que a transmissão foi concluida com sucesso.
        new AlertDialog.Builder(ArquivoFinalizadoActivity.this).setTitle(getString(R.string.title_route_completed))
                                                               .setMessage(getString(R.string.message_route_completed))
                                                               .setIcon(R.drawable.ok)
                                                               .setCancelable(false)
                                                               .setPositiveButton(getString(R.string.carregar),
                                                                                  new DialogInterface.OnClickListener() {
                                                                                      @Override
                                                                                      public void onClick(
                                                                                              DialogInterface dialog,
                                                                                              int which) {
                                                                                          Intent i = new Intent(ArquivoFinalizadoActivity.this,
                                                                                                                ApkActivity.class);
                                                                                          startActivity(i);
                                                                                          finish();
                                                                                      }
                                                                                  })
                                                               .setNegativeButton(getString(R.string.fechar),
                                                                                  new DialogInterface.OnClickListener() {
                                                                                      @Override
                                                                                      public void onClick(
                                                                                              DialogInterface dialog,
                                                                                              int which) {
                                                                                          finish();
                                                                                      }
                                                                                  })
                                                               .show();
        
	}
}
