package com.br.gsanac.gui;

import android.app.Activity;
import android.database.Cursor;

import com.br.gsanac.conexao.DBConnection;
import com.br.gsanac.exception.RepositorioException;

public abstract class BaseTabsActivity extends Activity {

	@Override
    public void onBackPressed() { 
		
		TabsActivity.indicadorExibirMensagemErro = false;
//		Intent intent = new Intent(BaseTabsActivity.this, RoteiroActivity.class);
//        startActivity(intent);
//        finish();

	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    try {
			new DBConnection(this).closeDatabase();
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}
	
	public void fecharCursor(Cursor cursor){
		if(cursor != null){
			cursor.close();
		}
	}
}