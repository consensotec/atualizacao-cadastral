package com.br.gsanac.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;




import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.br.gsanac.R;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.Foto.FotoColunas;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.LogradouroTipo;
import com.br.gsanac.entidades.LogradouroTipo.LogradouroTipoColunas;
import com.br.gsanac.entidades.LogradouroTitulo;
import com.br.gsanac.entidades.LogradouroTitulo.LogradouroTituloColunas;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.gui.TabsActivity;
import com.br.gsanac.repositorio.RepositorioBairro;
import com.br.gsanac.repositorio.RepositorioCadastroOcorrencia;
import com.br.gsanac.repositorio.RepositorioCategoria;
import com.br.gsanac.repositorio.RepositorioCep;
import com.br.gsanac.repositorio.RepositorioClienteAtlzCadastral;
import com.br.gsanac.repositorio.RepositorioClienteFoneAtlzCad;
import com.br.gsanac.repositorio.RepositorioClienteTipo;
import com.br.gsanac.repositorio.RepositorioEnderecoReferencia;
import com.br.gsanac.repositorio.RepositorioFoneTipo;
import com.br.gsanac.repositorio.RepositorioFonteAbastecimento;
import com.br.gsanac.repositorio.RepositorioFoto;
import com.br.gsanac.repositorio.RepositorioHidrometroCapacidade;
import com.br.gsanac.repositorio.RepositorioHidrometroInstHistAtlzCad;
import com.br.gsanac.repositorio.RepositorioHidrometroLocalInst;
import com.br.gsanac.repositorio.RepositorioHidrometroMarca;
import com.br.gsanac.repositorio.RepositorioHidrometroProtecao;
import com.br.gsanac.repositorio.RepositorioImovelAtlzCadastral;
import com.br.gsanac.repositorio.RepositorioImovelOcorrencia;
import com.br.gsanac.repositorio.RepositorioImovelPerfil;
import com.br.gsanac.repositorio.RepositorioImovelSubCategAtlzCad;
import com.br.gsanac.repositorio.RepositorioLigacaoAguaSituacao;
import com.br.gsanac.repositorio.RepositorioLigacaoEsgotoSituacao;
import com.br.gsanac.repositorio.RepositorioLogradouro;
import com.br.gsanac.repositorio.RepositorioLogradouroBairro;
import com.br.gsanac.repositorio.RepositorioLogradouroCep;
import com.br.gsanac.repositorio.RepositorioLogradouroTipo;
import com.br.gsanac.repositorio.RepositorioLogradouroTitulo;
import com.br.gsanac.repositorio.RepositorioMedicaoTipo;
import com.br.gsanac.repositorio.RepositorioMunicipio;
import com.br.gsanac.repositorio.RepositorioOrgaoExpedidorRg;
import com.br.gsanac.repositorio.RepositorioPavimentoCalcada;
import com.br.gsanac.repositorio.RepositorioPavimentoRua;
import com.br.gsanac.repositorio.RepositorioPessoaSexo;
import com.br.gsanac.repositorio.RepositorioQuadra;
import com.br.gsanac.repositorio.RepositorioSetorComercial;
import com.br.gsanac.repositorio.RepositorioSistemaParametros;
import com.br.gsanac.repositorio.RepositorioSubCategoria;
import com.br.gsanac.repositorio.RepositorioUnidadeFederacao;

public class Util {

    public static SimpleDateFormat  dateFormatDB           = new SimpleDateFormat(ConstantesSistema.DATE_FORMAT_DATABASE);

    public static SimpleDateFormat  dateFormatBrazil       = new SimpleDateFormat(ConstantesSistema.DATE_FORMAT_BRAZIL);
    
    public static SimpleDateFormat  dateComHoraFormatBrazil  = new SimpleDateFormat(ConstantesSistema.DATE_COM_HORA_FORMAT_BRAZIL);

    private static SimpleDateFormat dateOnlyFormatFileName = new SimpleDateFormat("ddMMyyyy");

    public static SimpleDateFormat  dateFormatFilename     = new SimpleDateFormat(ConstantesSistema.DATE_FORMAT_FILENAME);

    /**
     * @author Arthur Carvalho
     * @since 15/09/2011
     * @return a data corrente
     */
    public static Date getCurrentDateTime() {
        Calendar gregorianCalendar = Calendar.getInstance();
        return gregorianCalendar.getTime();
    }

//    public static Date convertStringToDate(String dateString) {
//        Date date = null;
//
//        try {
//            if (dateString.length() == 8) {
//                dateString = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
//                        + dateString.substring(6, 8) + " 00:00:00";
//            }
//            date = dateFormatDB.parse(dateString);
//        } catch (ParseException pe) {
//            pe.printStackTrace();
//        }
//
//        return date;
//    }

    /**
     * @author Arthur Carvalho
     * @since 09/09/2011
     * @param context
     * @return o IMEI do aparelho
     */
    public static String getEnderecoMac(Context context) {
    	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    	   String enderecoMac = wifiInfo.getMacAddress();


        return enderecoMac.replace(":", "");
    }
    /**
     * @author Arthur Carvalho
     * @since 09/09/2011
     * @param params
     * @return
     */
    public static byte[] packagingParameters(List<Object> params) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] response = null;

        try {
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    Object param = params.get(i);

                    if (param instanceof Byte) {
                        dos.writeByte(((Byte) param).byteValue());
                    } else if (param instanceof Integer) {
                        dos.writeInt(((Integer) param).intValue());
                    } else if (param instanceof Long) {
                        dos.writeLong(((Long) param).longValue());
                    } else if (param instanceof String) {
                        dos.writeUTF((String) param);
                    } else if (param instanceof byte[]) {
                        dos.write((byte[]) param);
                    }
                }
            }

            response = baos.toByteArray();

            if (dos != null) {
                dos.close();
                dos = null;
            }

            if (baos != null) {
                baos.close();
                baos = null;
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return response;
    }

    /**
     * @author Arthur Carvalho
     * @since 15/09/2011
     * @param line
     *            linha do arquivo que deve ser parseada
     * @return um array com os campos do objeto
     */
    public static List<String> split(String line) {
        List<String> lines = new ArrayList<String>();

        char[] chars = line.toCharArray();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '|') {
                sb.append(chars[i]);
            } else {
                lines.add(sb.toString());
                sb = new StringBuilder();
            }
        }

        return lines;
    }

    /**
     *
     * @author Arthur Carvalho
     * @date 30/01/2013
     *
     * @param intToValidate
     * @return
     */
    public static Integer parseStringToInteger(String intToValidate) {

        Integer value = null;

        if (intToValidate == null || intToValidate.trim().equals("")) {

        } else {
            value = Integer.parseInt(intToValidate.trim());
        }

        return value;
    }

    /**
     * @author Arthur Carvalho
     * @since 16/09/2011
     * @param lastChange
     *            a data recebida do banco de dados no formato yyyy-MM-dd HH:mm:ss
     * @return a data no formato dd/MM/yyyy
     */
    public static String convertDateToString(Date lastChange) {
        return dateFormatBrazil.format(lastChange);
    }
    
    /**
     * @author Arthur Carvalho
     * @since 16/09/2011
     * @param lastChange
     *            a data recebida do banco de dados no formato yyyy-MM-dd HH:mm:ss
     * @return a data no formato dd/MM/yyyy HH:mm:ss
     */
    public static String convertDateToStringComHora(Date lastChange) {
        return dateComHoraFormatBrazil.format(lastChange);
    }

    /**
     * @author Arthur Carvalho
     * @since 19/09/2011
     * @param textToSpinner
     * @return adapterToSpinner - um Adapter para um spinner (Combobox)
     */
    public static SimpleCursorAdapter getAdapter(Cursor cursor) {

        int[] to = new int[] {
            android.R.id.text1
        };

        String[] from = new String[] {
            "description"
        };

        @SuppressWarnings("deprecation")
		SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(Fachada.getContext(),
                                                                          android.R.layout.simple_spinner_item,
                                                                          cursor,
                                                                          from,
                                                                          to);

        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        simpleCursorAdapter.notifyDataSetChanged();

        return simpleCursorAdapter;
    }

    /**
     * @author Anderson Cabral
     * @since 23/01/2013
     * @param textToSpinner
     * @return adapterToAutoComplete - um Adapter para um AutoComplete
     */
    public static SimpleCursorAdapter getAdapterAutoComplete(Cursor cursor) {

        int[] to = new int[] {
                android.R.id.text1
            };

        String[] from = new String[] {
            "description"
        };

		SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(Fachada.getContext(), android.R.layout.simple_dropdown_item_1line, cursor, from, to, 0);

		simpleCursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cursor) {
                final int colIndex = cursor.getColumnIndexOrThrow("description");
                return cursor.getString(colIndex);
            }
        });

		simpleCursorAdapter.notifyDataSetChanged();

        return simpleCursorAdapter;
    }

    private static int calculateDigit(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    /**
     * <p>
     * Funcao de validacao para CPF
     * </p>
     *
     * @author Arthur Carvalho
     * @since 22/09/2011
     * @param cpf
     * @return
     */
    public static boolean validateCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11))
            return false;

        Integer firstDigit = calculateDigit(cpf.substring(0, 9), ConstantesSistema.CPF);
        Integer secondDigit = calculateDigit(cpf.substring(0, 9) + firstDigit, ConstantesSistema.CPF);

		ArrayList<String> cpfsInvalidos = new ArrayList<String>();
		cpfsInvalidos.add("00000000000");
		cpfsInvalidos.add("11111111111");
		cpfsInvalidos.add("22222222222");
		cpfsInvalidos.add("33333333333");
		cpfsInvalidos.add("44444444444");
		cpfsInvalidos.add("55555555555");
		cpfsInvalidos.add("66666666666");
		cpfsInvalidos.add("77777777777");
		cpfsInvalidos.add("88888888888");
		cpfsInvalidos.add("99999999999");

        return cpf.equals(cpf.substring(0, 9) + firstDigit.toString() + secondDigit.toString()) && !cpfsInvalidos.contains(cpf);
    }

    /**
     * <p>
     * Funcao de validacao para CNPJ
     * </p>
     *
     * @author Arthur Carvalho
     * @since 22/09/2011
     * @param cnpjNumber
     * @return
     */
    public static boolean validateCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14))
            return false;

		ArrayList<String> cnpjsInvalidos = new ArrayList<String>();
		cnpjsInvalidos.add("00000000000000");
		cnpjsInvalidos.add("11111111111111");
		cnpjsInvalidos.add("22222222222222");
		cnpjsInvalidos.add("33333333333333");
		cnpjsInvalidos.add("44444444444444");
		cnpjsInvalidos.add("55555555555555");
		cnpjsInvalidos.add("66666666666666");
		cnpjsInvalidos.add("77777777777777");
		cnpjsInvalidos.add("88888888888888");
		cnpjsInvalidos.add("99999999999999");

        Integer firstDigit = calculateDigit(cnpj.substring(0, 12), ConstantesSistema.CNPJ);
        Integer secondDigit = calculateDigit(cnpj.substring(0, 12) + firstDigit, ConstantesSistema.CNPJ);

        return cnpj.equals(cnpj.substring(0, 12) + firstDigit.toString() + secondDigit.toString()) && !cnpjsInvalidos.contains(cnpj);
    }

    /**
     * Adiciona um pipe ao final do objeto passado
     *
     * @param String
     *            a ter o pipe adicionado
     * @return
     */
    public static String stringPipe(Object obj) {
    	if ( obj == null ) {
    		return "|";
    	} else {
    		return obj + "|";
    	}
    }

    /**
     * Fun√ß√£o usada para converter um Date para String em formato de nome de arquivo
     *
     * @param date
     * @return String
     */
    public static String convertDateToDateOnlyStrFile() {
        Date date = new Date();
        String dateStr = dateOnlyFormatFileName.format(date);
        return dateStr;
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como Constantes.NULO_STRING, setando
     * como Constantes.NULO_INT caso verdadadeiro
     *
     * @param valor
     * @return
     */
    public static Integer verificarNuloInt(String valor) {
        if (valor == null || valor.trim().equals(ConstantesSistema.NULO_STRING)) {
            return ConstantesSistema.NULO_INT;
        } else {
            return Integer.parseInt(valor.trim());
        }
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como Constantes.NULO_STRING, setando
     * como Constantes.NULO_INT caso verdadadeiro
     *
     * @param valor
     * @return
     */
    public static Long verificarNuloLong(String valor) {
        if (valor == null || valor.trim().equals(ConstantesSistema.NULO_STRING)) {
            return ConstantesSistema.NULO_LONG;
        } else {
            return Long.parseLong(valor.trim());
        }
    }

//    /**
//     * Adiciona um pipe ao final do objeto passado
//     *
//     * @param String
//     *            a ter o pipe adicionado
//     * @return
//     */
//    public static String stringPipeWithSpace(String parametro) {
//        if (parametro == null || parametro.equals("null") || parametro.equals("0") || parametro.equals("")) {
//            return " |";
//        } else {
//            return parametro + "|";
//        }
//    }

    /**
     * Retorna um Bitmap de modo que nao utilize muita memoria. Previne o memoryOutOfBounds
     */
    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException fnfe) {
            Log.e(ConstantesSistema.LOG_TAG, fnfe.getMessage() + " - " + fnfe.getCause());
        }

        return null;
    }

    public static boolean hasParameter(List<Object> params, byte param) {
        return !params.isEmpty() && params.contains(param) && params.get(0).equals(param);
    }

    /******************************************
     * ********** FOTO ************************
     * ****************************************/

    /**Retorna o caminho completo da foto**/
    public static String completePhotoPath(Integer imovelAtlzCadastralId, Integer photoTypeId) {
    	long timeStamp = new Date().getTime();
        return ConstantesSistema.SDCARD_GSANAC_PHOTOS_PATH + "/" + imovelAtlzCadastralId + "_" + photoTypeId + "_" + timeStamp + ".jpg";
    }

    /**Retorna um File com o caminho completo da foto**/
    public static File getPhotoFile(Integer imovelAtlzCadastralId, Integer photoTypeId) {
        return new File(Util.completePhotoPath(imovelAtlzCadastralId, photoTypeId));
    }

    /**Retorna um File com o caminho completo da foto**/
    public static String getFotoFile(String codigo, Integer photoTypeId) {
        return Util.completeFotoPath(codigo, photoTypeId);
    }

    /**Retorna o caminho completo da foto**/
    public static String completeFotoPath(String codigo, Integer photoTypeId) {
        return ConstantesSistema.SDCARD_GSANAC_PHOTOS_PATH + "/" + codigo + "_" + photoTypeId  + ".jpg";
    }
//    public static Bitmap getBitmap(Integer imovelAtlzCadastralId, Integer photoTypeId) {
//
//        Bitmap bmp = null;
//
//        File photo = getPhotoFile(imovelAtlzCadastralId, photoTypeId);
//
//        if (photo.exists()) {
//            bmp = Util.decodeFile(photo);
//        }
//
//        return bmp;
//    }

//    public static boolean photoExists(Integer imovelAtlzCadastralId, Integer photoTypeId) {
//        return (Util.getPhotoFile(imovelAtlzCadastralId, photoTypeId)).exists();
//    }

	public static Foto pesquisarFotoBanco(Integer idImovel, Integer fotoTipo){
		Foto foto = new Foto();
        String selection = FotoColunas.IMOVELATLZCAD_ID + "=?";
        selection += " AND " + FotoColunas.FOTOTIPO + "=?";

        String[] selectionArgs = new String[] {
            String.valueOf(idImovel),
            String.valueOf(fotoTipo)
        };

		try {
			Fachada fachada = Fachada.getInstance();
			foto = (Foto) fachada.pesquisar(foto, selection, selectionArgs);
		} catch (FachadaException e) {
			Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
		}

		if(foto != null && foto.getId() != null){
			return foto;
		}else{
			return null;
		}
	}

    public static Bitmap getBitmapByPath(String path) {

        Bitmap bmp = null;

        File photo = getPhotoFileByPath(path);

        if (photo.exists()) {
            bmp = Util.decodeFile(photo);
        }

        return bmp;
    }

    /**Retorna um File com o caminho passado como parametro**/
    public static File getPhotoFileByPath(String path) {
        return new File(path);
    }

    public static boolean photoExistsByPath(String path) {
        return (Util.getPhotoFileByPath(path)).exists();
    }

    public static String convertDateToDateStrFile() {
        Date date = new Date();
        String dateStr = dateFormatFilename.format(date);
        return dateStr;
    }

    /**
     * @author Arthur Carvalho
     * @since 28/09/2011
     * @param imovelAtlzCadastralId
     *            Id da ordem de servi√ßo
     * @param photoTypeId
     *            Id do tipo da foto
     * @return se o arquivo foi removido
     */
    public static boolean deletePhotoFileByPath(String path) {
        return (Util.getPhotoFileByPath(path)).delete();
    }


    public static void showMessage(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public static String completeTXTPath(Integer imovelAtlzCadastralId) {
        return ConstantesSistema.SDCARD_GSANAC_RETURN_PATH + "/" + imovelAtlzCadastralId + ".txt";
    }

    public static File getTXTFile(Integer imovelAtlzCadastralId) {
        return new File(Util.completeTXTPath(imovelAtlzCadastralId));
    }

    public static void createSystemDirs() {
        String[] dirs = new String[] {
                ConstantesSistema.SDCARD_GSANAC_FILES_PATH,
                ConstantesSistema.SDCARD_GSANAC_PHOTOS_PATH,
                ConstantesSistema.SDCARD_GSANAC_RETURN_PATH,
                ConstantesSistema.SDCARD_GSANAC_ARQUIVO_DIVIDIDO_PATH,
                ConstantesSistema.SDCARD_GSANAC_VERSION_PATH

        };

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    Log.v(ConstantesSistema.LOG_TAG, "Create directory: " + file.getAbsolutePath());
                } else {
                    Log.e(ConstantesSistema.LOG_TAG, "NOT Created directory: " + file.getAbsolutePath());
                }

            }
        }
    }

    public static void verifyNoMedia() {
        File noMedia = new File(ConstantesSistema.SDCARD_DCIM_CAMERA_PATH + "/.nomedia");

        if (!noMedia.exists()) {
            noMedia.mkdirs();
            try {
                noMedia.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /***********************************************************************************************************************************/

    public static String formatarCEP(String codigo) {

		String retornoCEP = "";
		if(!codigo.equals("0")){
			String parte1 = codigo.substring(0, 2);
			String parte2 = codigo.substring(2, 5);
			String parte3 = codigo.substring(5, 8);

			retornoCEP = parte1 + "." + parte2 + "-" + parte3;
		}

		return retornoCEP;
	}


	public static String retirarFormatacaoCEP(String codigo) {

		String retornoCEP = null;

		String parte1 = codigo.substring(0, 2);
		String parte2 = codigo.substring(3, 6);
		String parte3 = codigo.substring(7, 10);

		retornoCEP =  parte1+parte2+parte3;

		return retornoCEP;
	}

    /**
     * Exibi um Alert com a mensagem passada por parametro
     *
     * @author Anderson Cabral
     * @date 26/12/2012
     *
     * @param Activity
     * @param Titulo
     * @param Mensagem
     */
    public static void exibirMensagemErro(Context activity, String mensagem){
        new AlertDialog.Builder(activity).setTitle("ATEN«√O")
        .setMessage(mensagem)
        .setIcon(R.drawable.warning)
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
    
    /**
     * Exibi um Alert com a mensagem passada por parametro
     *
     * @author Flavio Ferreira
     * @date 29/10/2013
     *
     * @param Activity
     * @param Titulo
     * @param Mensagem
     */
    public static void exibirMensagemOk(Context activity, String mensagem){
        new AlertDialog.Builder(activity).setTitle("")
        .setMessage(mensagem)
        .setIcon(R.drawable.ok)
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

    /**
     * Seleciona um item no combo
     *
     * @author Anderson Cabral
     * @date 27/12/2012
     *
     * @param spinner
     * @param id
     */
//	public static void selecionarItemCombo(Spinner spinner, long id){
//
//		   for (int i = 0; i < spinner.getCount(); i++) {
//			   long itemIdAtPosition2 = spinner.getItemIdAtPosition(i);
//			   if (itemIdAtPosition2 == id && (itemIdAtPosition2 != 0)) {
//				   spinner.setSelection(i);
//				   break;
//			   }
//		   }
//	}

    /**
     * Seleciona um item no combo considerando o id ZERO
     *
     * @author Anderson Cabral
     * @date 01/02/2013
     *
     * @param spinner
     * @param id
     */
	public static void selecionarItemCombo(Spinner spinner, long id){

		   for (int i = 0; i < spinner.getCount(); i++) {
			   long itemIdAtPosition2 = spinner.getItemIdAtPosition(i);
			   if (itemIdAtPosition2 == id) {
				   spinner.setSelection(i);
				   break;
			   }
		   }
	}

    /**
     * Seleciona um item no AutoComplete
     *
     * @author Anderson Cabral
     * @date 23/01/2013
     *
     * @param AutoCompleteTextView
     * @param id
     */
	public static void selecionarItemAutoComplete(AutoCompleteTextView autoCompleteTextView, long id, ArrayList<Logradouro> logradouros){

		try {
			for(Logradouro logradouro : logradouros){
				if(id == logradouro.getId().longValue()){
					String endereco = "";
					LogradouroTipo logradouroTipo = new LogradouroTipo();

					String selectionTipo = LogradouroTipoColunas.ID + "=?";

			        String[] selectionArgsTipo = new String[] {
				            String.valueOf(logradouro.getLogradouroTipo().getId())
				    };

			        logradouroTipo = (LogradouroTipo) Fachada.getInstance().pesquisar(logradouroTipo, selectionTipo, selectionArgsTipo);

			        if ( logradouroTipo != null ) {
			        	endereco = logradouroTipo.getDescricao() + " ";
			        }


			        LogradouroTitulo logradouroTitulo = new LogradouroTitulo();

					String selectionTitulo = LogradouroTituloColunas.ID + "=?";

			        String[] selectionArgsTitulo = new String[] {
				            String.valueOf(logradouro.getLogradouroTitulo().getId())
				    };

			        logradouroTitulo = (LogradouroTitulo) Fachada.getInstance().pesquisar(logradouroTitulo, selectionTitulo, selectionArgsTitulo);

			        if ( logradouroTitulo != null && logradouroTitulo.getDescricao() != null ) {
			        	endereco += logradouroTitulo.getDescricao() + " ";
			        }


			        endereco += logradouro.getNomeLogradouro();


					autoCompleteTextView.setText(endereco);
					break;
				}
			}
		} catch (FachadaException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  REMOVE AS INSTANCIAS DO REPOSITORIO, CASO O BANCO SEJA REMOVIDO … NECESSARIO CRIAR UMA NOVA INSTANCIA.
	 *
	 * @author Arthur Carvalho
	 * @date 30/01/2013
	 *
	 */
    public static void removeInstanceRepository() {

    	RepositorioBairro.removeInstance();
    	RepositorioCadastroOcorrencia.removeInstance();
    	RepositorioCategoria.removeInstance();
    	RepositorioCep.removeInstance();
    	RepositorioClienteAtlzCadastral.removeInstance();
    	RepositorioClienteFoneAtlzCad.removeInstance();
    	RepositorioClienteTipo.removeInstance();
    	RepositorioEnderecoReferencia.removeInstance();
    	RepositorioFoneTipo.removeInstance();
    	RepositorioFonteAbastecimento.removeInstance();
    	RepositorioFoto.removeInstance();
    	RepositorioHidrometroCapacidade.removeInstance();
    	RepositorioHidrometroInstHistAtlzCad.removeInstance();
    	RepositorioHidrometroLocalInst.removeInstance();
    	RepositorioHidrometroMarca.removeInstance();
    	RepositorioHidrometroProtecao.removeInstance();
    	RepositorioImovelAtlzCadastral.removeInstance();
    	RepositorioImovelOcorrencia.removeInstance();
    	RepositorioImovelPerfil.removeInstance();
    	RepositorioImovelSubCategAtlzCad.removeInstance();
    	RepositorioLigacaoAguaSituacao.removeInstance();
    	RepositorioLigacaoEsgotoSituacao.removeInstance();
    	RepositorioLogradouro.removeInstance();
    	RepositorioLogradouroBairro.removeInstance();
    	RepositorioLogradouroCep.removeInstance();
    	RepositorioLogradouroTipo.removeInstance();
    	RepositorioLogradouroTitulo.removeInstance();
    	RepositorioMedicaoTipo.removeInstance();
    	RepositorioMunicipio.removeInstance();
    	RepositorioOrgaoExpedidorRg.removeInstance();
    	RepositorioPavimentoCalcada.removeInstance();
    	RepositorioPavimentoRua.removeInstance();
    	RepositorioPessoaSexo.removeInstance();
    	RepositorioQuadra.removeInstance();
    	RepositorioSistemaParametros.removeInstance();
    	RepositorioSubCategoria.removeInstance();
    	RepositorioUnidadeFederacao.removeInstance();
    	RepositorioSetorComercial.removeInstance();

    }

    /**
     *
     * @author Arthur Carvalho
     * @date 30/01/2013
     *
     * @param diaAnoMesReferencia
     * @return
     */
    public static boolean validarDiaMesAno(String diaAnoMesReferencia) {
		boolean anoMesInvalido = false;

		if (diaAnoMesReferencia.length() == 10) {

			// String mesAnoReferencia = anoMesReferencia.substring(4, 6) + "/"
			// + anoMesReferencia.substring(0, 4);

			SimpleDateFormat dataTxt = new SimpleDateFormat("DD/MM/yyyy");

			try {
				dataTxt.parse(diaAnoMesReferencia);
			} catch (ParseException e) {
				anoMesInvalido = true;
			}
		} else {
			anoMesInvalido = true;
		}

		return anoMesInvalido;
	}


    /**
	 * MÈtodo para retornar o valor do InputStream para o E71 e o E5
	 *
	 * @param input
	 * @return string de retorno com o valor
	 * @throws IOException
	 */
	public static String getValorRespostaInputStream(InputStream input) throws IOException {
		char valor = ' ';
		try {

			// ---INICIO E5
			valor = (char) input.read();
			// ---FIM E5

		} catch (Exception e) {
			// ---INICIO E71
			InputStreamReader isr = new InputStreamReader(input);
			valor = (char) isr.read();
			// ---FIM E71

		}

		return String.valueOf(valor);
	}

	/**
	 * MÈtodo para retornar o valor do InputStream para o E71 e o E5
	 *
	 * @param input
	 * @return char de retorno com o valor
	 * @throws IOException
	 */
	public static char getCharValorRespostaInputStream(InputStream input) throws IOException {
		char valor = ' ';
		try {

			// ---INICIO E5
			valor = (char) input.read();
			// ---FIM E5

		} catch (Exception e) {
			// ---INICIO E71
			InputStreamReader isr = new InputStreamReader(input);
			valor = (char) isr.read();
			// ---FIM E71

		}

		return valor;
	}

	public static String getVersaoSistema(Context context){

    	PackageInfo pinfo = null;

		try {
			pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return pinfo.versionName;

    }

	/**
	 *
	 * Deleta todas as pastas e arquivos filhos de gsanAC.
	 *
	 *
	 * @param  Caminho da pasta gsanAC no SDCARD
	 * @return
	 */
	public static void deletarPastas(File fileOrDirectory){
		// Deleta as pastas diferente de 'carregamento'
			if (fileOrDirectory.isDirectory()){
		        for (File child : fileOrDirectory.listFiles()){
		        	deletarPastas(child);
		        }
			}
		    fileOrDirectory.delete();
	}

	public static byte[] empacotarParametros(ArrayList<Object> parametros) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos =   new DataOutputStream(baos);

		byte[] resposta = null;

		parametros.trimToSize();

		// escreve os dados no OutputStream
		if (parametros != null) {
			int tamanho = parametros.size();
			for (int i = 0; i < tamanho; i++) {
				Object param = parametros.get(i);
				if (param instanceof Byte) {
					dos.writeByte(((Byte) param).byteValue());
				} else if (param instanceof Integer) {
					dos.writeInt(((Integer) param).intValue());
				} else if (param instanceof Short) {
					dos.writeShort(((Short) param).shortValue());
				} else if (param instanceof Long) {
					dos.writeLong(((Long) param).longValue());
				} else if (param instanceof String) {
					dos.writeUTF((String) param);
				} else if (param instanceof byte[]) {
					dos.write((byte[]) param);
				}
			}
		}

		// pega os dados enpacotados
		resposta = baos.toByteArray();

		if (dos != null) {
			dos.close();
			dos = null;
		}
		if (baos != null) {
			baos.close();
			baos = null;
		}

		// retorna o array de bytes
		return resposta;
	}
    /**
	 * MÈtodo que recebe uma data com string no formato dd/MM/yyyy e converte
	 * para o objeto Date.
	 *
	 * @param data
	 * @autor Thiago Toscano
	 * @date 20/05/2005
	 * @return
	 */
	public static Date converteStringParaDate(String data) {
		Date retorno = null;
		try {
			retorno = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).parse(data);
		} catch (Exception e) {
			new IllegalArgumentException(data + " n„o tem o formato dd/MM/yyyy.");
		}
		return retorno;
	}

	/**
	 * Reseta os valores das variaveis globais staticas.
	 *
	 * @author Arthur Carvalho
	 * @date 30/01/2013
	 *
	 */
	public static void removerAtributosTabsActivity(){

	     TabsActivity.imovel = null;
         TabsActivity.cliente = null;
         TabsActivity.hidrometroInstalacaoHist = null;
         TabsActivity.colecaoClienteFone = null;
         TabsActivity.sistemaParametros = null;
         TabsActivity.colecaoImovelOcorrencia = null;
         TabsActivity.colImoveisSubCategoria = null;
         TabsActivity.mensagemErro = null;
         TabsActivity.carregarImovel = false;
         TabsActivity.concluirAtualizacao = false;
         TabsActivity.indicadorExibirMensagemErro = true;
         TabsActivity.indicadorIntegracao = false;
         TabsActivity.primeiraVezAbaLigacao = true;
         TabsActivity.primeiraVezAbaImovel = true;
         TabsActivity.colecaoClienteFoneIncial = null;
         TabsActivity.colecaoImovelOcorrenciaInicial = null;
         TabsActivity.colImoveisSubCategoriaInicial = null;
         TabsActivity.fotoFrenteDaCasa = null;
         TabsActivity.fotoFrenteDaCasaInicial = null;
         TabsActivity.fotoHidrometro = null;
         TabsActivity.fotoHidrometroInicial = null;
	}

	/**
	 * Verifica se a string passada corresponde a uma data v·lida de acordo com o formato que est· sendo passado.
	 *
	 * @author Raphael Rossiter
	 * @date 03/09/2010
	 *
	 * @param data
	 * @param formato
	 * @return boolean
	 */
	public static boolean validarData(String data, String formato) {

		boolean dataInvalida = false;

		try {

			if (data != null && !data.equals("") && data.length() == 10){

				int diaInt = Integer.parseInt(data.substring(0, 2));
				int mesInt = Integer.parseInt(data.substring(3, 5));
				int anoInt = Integer.parseInt(data.substring(6, 10));

				if (mesInt > 12) {
					dataInvalida = true;
				}
				if (diaInt > 31) {
					dataInvalida = true;
				}

				int ultimoDiaMes = Integer.valueOf(Util.obterUltimoDiaMes(mesInt, anoInt));

				if (diaInt > ultimoDiaMes){
					dataInvalida = true;
				}

				SimpleDateFormat formatacaoData = new SimpleDateFormat(formato, new Locale("pt", "BR"));
				formatacaoData.parse(data);

			} else{

				dataInvalida = true;
			}

		} catch (Exception e) {
			dataInvalida = true;
		}

		return dataInvalida;
	}

	/**
	 * Retorna o Ultimo Dia do MÍs informado Auhtor: Rafael CorrÍa Data:
	 * 02/04/2007 Indices de MÍs 1 - Janiero 2 - Fevereiro 3 - MarÁo 4 - Abril 5 -
	 * Maio 6 - Junho 7 - Julho 8 - Agosto 9 - Setembro 10 - Outubro 11 -
	 * Novembro 12 - Dezembro
	 *
	 * @param mes
	 *            Indice do MÍs
	 * @param ano
	 *            Ano
	 * @return Ultimo Dia do Mes
	 */
	public static String obterUltimoDiaMes(int mes, int ano) {
		String ultimoDia = "";

		Calendar calendar = new GregorianCalendar(ano, mes -1, 1);

		// ultima dia do mes
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		ultimoDia = "" + calendar.get(Calendar.DAY_OF_MONTH);

		return ultimoDia;
	}


	/**
	 *
	 * @author Arthur Carvalho
	 * @date 17/01/2013
	 *
	 * @param dateString
	 * @return
	 */
	 public static Date convertStringToDate(String dateString) {
	        Date date = null;

	        try {
	            if (dateString.length() == 8) {
	                dateString = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
	                        + dateString.substring(6, 8) + " 00:00:00";
	            }
	            date = dateFormatDB.parse(dateString);
	        } catch (ParseException pe) {
	            pe.printStackTrace();
	        }

	        return date;
	    }





  /**
	 * Adiciona o arquivo especificado ao zipOutputStream que representa o
	 * arquivo zip
	 *
	 * @author Rodrigo Silveira
	 * @date 19/05/2006
	 *
	 * @param zipFile
	 *            Stream que representa o arquivo zip
	 * @param file
	 *            Arquivo a ser adicionado no arquivo zip
	 * @throws IOException
	 */
	public static void adicionarArquivo(ZipOutputStream zipFile, File file)
			throws IOException {

		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int INPUT_BUFFER_SIZE = 1024;
		byte[] temp = new byte[INPUT_BUFFER_SIZE];
		int numBytesRead = 0;

		while ((numBytesRead = inputStream.read(temp, 0, INPUT_BUFFER_SIZE)) != -1) {
			baos.write(temp, 0, numBytesRead);
		}

		inputStream.close();
		inputStream = null;

		byte[] data = baos.toByteArray();

		ZipEntry zen = new ZipEntry(file.getName());
		zipFile.putNextEntry(zen);
		zipFile.write(data, 0, data.length);
		zipFile.closeEntry();
	}



	/**
	 *  Adiciona arquivo no zip
	 * @author Arthur Carvalho
	 * @date 30/01/2013
	 *
	 * @param path
	 * @param srcFile
	 * @param zip
	 * @throws Exception
	 */
	public static  void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

	    File folder = new File(srcFile);
	    if (folder.isDirectory()) {
	    	addFolderToZip(path, srcFile, zip);
	    } else {
	    	byte[] buf = new byte[1024];
	    	int len;
	    	FileInputStream in = new FileInputStream(srcFile);
	    	zip.putNextEntry(new ZipEntry(path + folder.getName()));
	    	while ((len = in.read(buf)) > 0) {
	    		zip.write(buf, 0, len);
	    	}
	    }
	}

	/**
	 * Adiciona pasta no zip.
	 * @author Arthur Carvalho
	 * @date 30/01/2013
	 *
	 * @param path
	 * @param srcFolder
	 * @param zip
	 * @throws Exception
	 */
	  public static  void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {

	    File folder = new File(srcFolder);

    	for (String fileName : folder.list()) {
    		if (path.equals("")) {
    			addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
    		}	 else {
    			addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
    		}
    	}
	  }

	  /**
	   *
	   * @author Arthur Carvalho
	   * @date 30/01/2013
	   *
	   * @param cursor
	   * @param columnName
	   * @param columnIndex
	   * @return
	   */
	  public static BigDecimal  getDoubleBanco (Cursor cursor, String columnName, int columnIndex){

			if (! cursor.isNull(cursor.getColumnIndex(columnName)) ){
				return BigDecimal.valueOf(cursor.getDouble(columnIndex));
			}else{
				return null;
			}
	  }


	 /**
	  *
	  * @author Arthur Carvalho
	  * @date 25/01/2013
	  *
	  * @param cursor
	  * @return
	  */
	    public static SimpleCursorAdapter getAdapterAutoCompleteLogradouro(Cursor cursor) {

	        int[] to = new int[] {
	                android.R.id.text1
	            };

	        String[] from = new String[] {
	        		ConstantesSistema.COLUMN_DESC_FORMATADA_ALIAS
	        };

			SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(Fachada.getContext(), android.R.layout.simple_dropdown_item_1line, cursor, from, to, 0);

			simpleCursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
	            @Override
	            public CharSequence convertToString(Cursor cursor) {
	                final int colIndexTipo = cursor.getColumnIndexOrThrow(ConstantesSistema.COLUMN_TIPO_ALIAS);
	                final int colIndexTitulo = cursor.getColumnIndexOrThrow(ConstantesSistema.COLUMN_TITULO_ALIAS);
	                final int colIndexLogradouro = cursor.getColumnIndexOrThrow(ConstantesSistema.COLUMN_LOGRADOURO_ALIAS);

	                String formatado = cursor.getString(colIndexTipo) + " ";
	                if ( cursor.getString(colIndexTitulo) != null ) {
	                	formatado += cursor.getString(colIndexTitulo) + " ";
	                }
	                formatado += cursor.getString(colIndexLogradouro);

	                return formatado;
	            }
	        });

			cursor.close();

	        return simpleCursorAdapter;
	    }

	    /**
	     *
	     * @author Arthur Carvalho
	     * @date 29/01/2013
	     *
	     * @param data
	     * @return
	     */
	    public static String obterAAAAMMDDHHMMSS(Date data) {
			StringBuffer dataBD = new StringBuffer();

			if (data != null) {
				Calendar dataCalendar = new GregorianCalendar();

				dataCalendar.setTime(data);

				dataBD.append(dataCalendar.get(Calendar.YEAR));
				if (dataCalendar.get(Calendar.MONTH) >= 9) {
					dataBD.append(dataCalendar.get(Calendar.MONTH)+1);
				}else {
					dataBD.append("0" + (dataCalendar.get(Calendar.MONTH)+1));
				}
				if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
					dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
				}else {
					dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
				}
				if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
					dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
				} else {
					dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
				}

				if (dataCalendar.get(Calendar.MINUTE) > 9) {
					dataBD.append(dataCalendar.get(Calendar.MINUTE));
				} else {
					dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
				}
				if (dataCalendar.get(Calendar.SECOND) > 9) {
					dataBD.append(dataCalendar.get(Calendar.SECOND));
				} else {
					dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
				}
			}

			return dataBD.toString();
		}


	    public static ArrayList<String> split_(String line) {
	    	ArrayList<String> lines = new ArrayList<String>();

	        char[] chars = line.toCharArray();

	        StringBuilder sb = new StringBuilder();

	        for (int i = 0; i < chars.length; i++) {
	            if (chars[i] != '_') {
	                sb.append(chars[i]);
	            } else {
	                lines.add(sb.toString());
	                sb = new StringBuilder();
	            }
	        }

	        return lines;
	    }

	    public static InputFilter filterReplaceCaracteresEspeciais() {
			InputFilter filter = new InputFilter() {

				@Override
				public CharSequence filter(CharSequence source, int start,
						int end, Spanned dest, int dsrart, int dend) {

	                return Util.removerCaracteresEspeciais(source.toString());

				}
			};

			return filter;
	    }
	    
	    public static InputFilter filterReplaceCaracteresEspeciaisEEspaco() {
			InputFilter filter = new InputFilter() {

				@Override
				public CharSequence filter(CharSequence source, int start,
						int end, Spanned dest, int dsrart, int dend) {

	                return Util.removerCaracteresEspeciaisEEspaco(source.toString());

				}
			};

			return filter;
	    }
	    
	    public static InputFilter filtervalidarFiltroNumeroComplemento() {
			InputFilter filter = new InputFilter() {

				@Override
				public CharSequence filter(CharSequence source, int start,
						int end, Spanned dest, int dsrart, int dend) {

	                return Util.validarFiltroNumeroComplemento(source.toString());

				}
			};

			return filter;
	    }	    

		/****
		 * Remove acentos
		 * @author Anderson Cabral
		 * **********/
//		public static String removerAcentos(String acentuada) {
//		    CharSequence cs = new StringBuilder(acentuada);
//		    return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
//		}

		/****
		 * Remove caracteres especiais e substitui caracteres acentuado por um nao acentuado
		 * @author Anderson Cabral
		 * **********/
		public static String removerCaracteresEspeciaisEEspaco(String texto) {
		    CharSequence cs = new StringBuilder(texto);
		    String novoTexto = Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

		    //return  novoTexto.replaceAll("[^[A-Z0-9]]", "");
		    return  novoTexto.replaceAll("[^[A-Z0-9.:;?{}()%@!><,-_=+ ]]", "");
		}
		
		/****
		 * Remove caracteres especiais e substitui caracteres acentuado por um nao acentuado
		 * @author Anderson Cabral
		 * **********/
		public static String removerCaracteresEspeciais(String texto) {
		    CharSequence cs = new StringBuilder(texto);
		    String novoTexto = Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

		    //return  novoTexto.replaceAll("[^[0-9]]", "");
		    return  novoTexto.replaceAll("[^[A-Z0-9.:;?{}()%@!><,-_=+ ]]", "");
		}
		
		public static String validarFiltroNumeroComplemento(String texto) {
		    CharSequence cs = new StringBuilder(texto);
		    String novoTexto = Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

		    return  novoTexto.replaceAll("[^[0-9]]", "");
		}		



	    public static final int BAIXAR_ARQUIVO_TEXTO_LIBERADO = 1;
	    public static final int FINALIZAR_ROTEIRO = 3;
	    public static final byte ENVIAR_FOTO = 4;
	    public static final int ATUALIZAR_SITUACAO_ARQUIVO = 5;
	    public static final int PING = 15;
	    public static final int BAIXAR_NOVA_VERSAO_APK = 11;
	    public static final int VERIFICAR_VERSAO_ANDROID = 12;


	    public static InputStream inflateFile(InputStream is, int tamanhoInput) throws IOException {

			DataInputStream disArquivoCompactado = new DataInputStream(is);
			byte[] arrayArquivoCompactado = new byte[tamanhoInput];
			disArquivoCompactado.readFully(arrayArquivoCompactado);
			arrayArquivoCompactado = GZip.inflate(arrayArquivoCompactado);

			ByteArrayInputStream byteArray = new ByteArrayInputStream(arrayArquivoCompactado);

			disArquivoCompactado.close();
			disArquivoCompactado = null;
			arrayArquivoCompactado = null;

			return byteArray;
		}

		/**
		 * [UC0261] - Obter DÌgito Verificador MÛdulo 11 Author : Pedro Alexandre
		 * Data : 15/02/2006
		 *
		 * Calcula o dÌgito verificador do cÛdigo de barras no mÛdulo 11(onze)
		 *
		 * @param numero
		 *            N˙mero do cÛdigo de barra no formato string para calcular o
		 *            dÌgito veficador
		 * @return digito verificador do mÛdulo 11(onze)
		 */
		public static Integer obterDigitoVerificadorModulo11(String numero) {

			String wnumero = numero;
			int param = 2;
			int soma = 0;

			for (int ind = (wnumero.length() - 1); ind >= 0; ind--) {
				if (param > 9) {
					param = 2;
				}
				soma = soma
						+ (Integer.parseInt(wnumero.substring(ind, ind + 1)) * param);
				param = param + 1;
			}

			int resto = soma % 11;
			int dv;

			if ((resto == 0) || (resto == 1)) {
				dv = 0;
			} else {
				dv = 11 - resto;
			}
			return dv;
		}

		public static Integer obterDigitoVerificadorAntigo(String numero) {

			String wnumero = numero;
			int param = 2;
			int soma = 0;

			for (int ind = (wnumero.length() - 1); ind >= 0; ind--) {
				if (param > 7) {
					param = 2;
				}
				soma = soma
						+ (Integer.parseInt(wnumero.substring(ind, ind + 1)) * param);
				param = param + 1;
			}

			int resto = soma % 11;
			int dv;

			if ((resto == 0) || (resto == 1)) {
				dv = 0;
			} else {
				dv = 11 - resto;
			}
			return dv;
		}
		
		public static String formatarCpf(String cpf) {
			
			String cpfFormatado = cpf;
			String zeros = "";
			
			if (cpfFormatado != null) {
				
				for (int a = 0; a < (11 - cpfFormatado.length()); a++) {
					zeros = zeros.concat("0");
				}
				// concatena os zeros ao numero
				// caso o numero seja diferente de nulo
				cpfFormatado = zeros.concat(cpfFormatado);
				
				cpfFormatado = cpfFormatado.substring(0, 3) + "."
						+ cpfFormatado.substring(3, 6) + "."
						+ cpfFormatado.substring(6, 9) + "-"
						+ cpfFormatado.substring(9, 11);
			}
			
			return cpfFormatado;
		}
		
		/**
		 * Verifica se uma String È um n˙mero Inteiro  
		 * 
		 * @author Raimundo Martins
		 * @date 03/05/2012
		 * */
		public static Boolean isLong(String str){
			Boolean retorno;
			try {  
				Long.parseLong(str);  
		        retorno = Boolean.TRUE;
			} catch (NumberFormatException e) {       
				retorno = Boolean.FALSE;
			}
			return retorno;
		}
		
		public static String completarComZeros( int quantidade, String texto ){
			StringBuffer buf = new StringBuffer( texto );
			
			while (buf.length() < 5) {
			  buf.insert(0, '0');
			}
			
			return buf.toString();			
		}
		
		public static boolean ehNumero( String texto ){
			try{
				if ( texto == null ) return false;
				
				Integer num = Integer.parseInt( texto );
			} catch ( NumberFormatException ex ){
				return false;
			}
			
			return true;
		}
}