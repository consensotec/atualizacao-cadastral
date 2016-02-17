package com.br.gsanac.conexao;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

import com.br.gsanac.entidades.Foto;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

public class PhotoConnection extends AsyncTask<Object, Integer, Boolean> {

    private static final String UPLOAD_URL  = ConstantesSistema.ACTION;

    
    private Foto               photo       = null;

    /**
     * Prepare activity before upload
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Clean app state after upload is completed
     */
    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        if (result) {
            photo.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
            try {
                Fachada.getInstance().update(photo);
            } catch (FachadaException e) {
                e.printStackTrace();
                Log.e(ConstantesSistema.LOG_TAG, "Erro ao mudar status da photo: " + e.getMessage(), e);
            }
        }
    }

    @Override
    protected Boolean doInBackground(Object... params) {

        photo = (Foto) params[3];

        return doFileUpload((File) params[0], (String) params[1], (Integer) params[2], UPLOAD_URL, photo);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public boolean doFileUpload(File file, String codigo, Integer tipoFoto, String uploadUrl, Foto foto) {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        photo = foto;
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int sentBytes = 0;
        long fileSize = file.length();

        // Verificamos se o servidor está online
        if (this.isServerOnline()) {

            // Send request
            try {
                // Configure connection
                URL url = new URL(uploadUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                // conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
                conn.setRequestProperty("Content-Type", "multipart/form-data");

                publishProgress(0);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeByte(Util.ENVIAR_FOTO);
                dos.writeUTF(codigo);
                dos.writeInt(tipoFoto);
                dos.writeLong(new Long(fileSize));

                // Read file and create buffer
                FileInputStream fileInputStream = new FileInputStream(file);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Send file data
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    // Write buffer to socket
                    dos.write(buffer, 0, bufferSize);

                    // Update progress dialog
                    sentBytes += bufferSize;
                    publishProgress((int) (sentBytes * 100 / fileSize));

                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dos.flush();
                dos.close();
                fileInputStream.close();

            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, "Cannot upload file: " + ioe.getMessage(), ioe);
                return false;
            }

            // Read response
            try {
                int responseCode = conn.getResponseCode();
                return responseCode == 200;
            } catch (IOException ioex) {
                Log.e(ConstantesSistema.LOG_TAG, "Upload file failed: " + ioex.getMessage(), ioex);
                return false;
            } catch (Exception e) {
                Log.e(ConstantesSistema.LOG_TAG, "Upload file failed: " + e.getMessage(), e);
                return false;
            }
        }
        return false;
    }

    public boolean isServerOnline() {

        int status = 0;

        try {
            HttpGet request = new HttpGet(ConstantesSistema.GSAN_HOST);

            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpResponse response = httpClient.execute(request);

            status = response.getStatusLine().getStatusCode();

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
        }

        return (status == HttpStatus.SC_OK);
    }
}
