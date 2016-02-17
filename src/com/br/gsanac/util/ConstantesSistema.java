package com.br.gsanac.util;

import android.os.Environment;

/**
 * @author Arthur Carvalho
 */
public class ConstantesSistema {

    /**
     * Categoria do LogCat
     */
    public static final String  LOG_TAG                               = "GSANAC";

    public static final String  DATABASE_NAME                         = "gsanac_database-2.dat";

    public static final String  DATABASE_PATH                         = "data/data/com.br.gsanac/databases/";

    /**
     * URL do GSAN
     */
    //Desenvolvedor Pinto
    //public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:8098/gsan";
    
//     Arthur
//     public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:8104/gsan";
    
    
    // Flavio
    //public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:8128/gsan";
    
    //Anderson
    //public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:9193/gsan";

    //Davi
    //public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:8102/gsan";
    
    //Paulo
    //public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:8124/gsan";

    //Servidor Desenvolvimento IPAD 239
    //public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:8116/gsan";

    //Servidor Desenvolvimento IPAD 241
    //public static final String GSAN_HOST = "http://gsan.gsansaneamento.com.br:8087/gsan";
    
    //Servidor Desenvolvimento IPAD 242
    //public static final String  GSAN_HOST = "http://gsan.gsansaneamento.com.br:8081/gsan";
    
    //Servidor Desenvolvimento IPAD 240
   // public static final String  GSAN_HOST = "http://gsan.gsansaneamento.com.br:8122/gsan";
    
    // Producao (COMPESA) OFICIAL
      public static final String GSAN_HOST = "http://200.167.199.198:8083/gsan";
    
    // Servidor Bruno Barros
    //public static final String  GSAN_HOST = "http://200.167.199.198:8083/gsan";


    /**
     * Action do GSAN para o download do arquivo
     */
    public static final String  ACTION    = GSAN_HOST+ "/processarRequisicaoDispositivoMovelAtualizacaoCadastralAction.do";
    /**
     * Indicador SIM
     */
    public static final int  SIM = 1;

    /**
     * Indicador NAO
     */
    public static final int NAO = 2;
    
    /**
     * Indicador SIM
     */
    public static final int FINALIZADO = 1;

    /**
     * Indicador NAO
     */
    public static final int PENDENTE = 2;
 
    /**
     * Formato da data no padrao do SQLite
     */
    public static final String  DATE_FORMAT_DATABASE                  = "yyyy-MM-dd HH:mm:ss";

    /**
     * Formato da data no padrao brasileiro
     */
    public static final String  DATE_FORMAT_BRAZIL                    = "dd/MM/yyyy";
    
    public static final String  DATE_COM_HORA_FORMAT_BRAZIL           = "dd/MM/yyyy HH:mm:ss";

    /**
     * 
     */
    public static final String  DATE_FORMAT_FILENAME                  = "ddMMyyyyHHmmss";
    
    /**
     * 
     */
    public static final String  ALERT_OK                          = "OK";
    
    public static final int     CAMERA_TAKE_PICTURE                   = 8291;
    
    
    public static final int BOTAO_REMOVER_BAIRRO_ID           = 1;
    public static final int BOTAO_REMOVER_CEP_ID              = 2;
    
    public static final int	BOTAO_REMOVER_TELEFONE_ID		  = 1;
    
    public static final int BOTAO_REMOVER_OCORRENCIA_ID       = 1;
    
    //Fone Tipo
    public static final int FOTO_TIPO_FRENTE_DE_CASA = 4;
    public static final int FOTO_TIPO_HIDROMETRO 	 = 5;
    
    
    public static final int		BOTAO_REMOVER_CATEGORIA_ID		  = 1;
    
    public static final Integer ARQUIVO_EM_CAMPO = Integer.valueOf(3);
    public static final Integer ARQUIVO_FINALIZADO = Integer.valueOf(4);
   
    /**
     * Tipos de Registro
     */
    
	public static final String REGISTRO_TIPO_BAIRRO = "01";

	public static final String REGISTRO_TIPO_CEP = "02";

	public static final String REGISTRO_TIPO_LOGRADOURO = "03";

	public static final String REGISTRO_TIPO_LOGRADOURO_BAIRRO = "04";

	public static final String REGISTRO_TIPO_LOGRADOURO_CEP = "05";

	public static final String REGISTRO_TIPO_PAVIMENTO_RUA = "06";

	public static final String REGISTRO_TIPO_PAVIMENTO_CALCADA = "07";

	public static final String REGISTRO_TIPO_FONTE_ABASTECIMENTO = "08";

	public static final String REGISTRO_TIPO_CATEGORIA = "09";

	public static final String REGISTRO_TIPO_SUBCATEGORIA = "10";
	
	public static final String REGISTRO_TIPO_LIGACAO_AGUA_SITUACAO = "11";
	
	public static final String REGISTRO_TIPO_LIGACAO_ESGOTO_SITUACAO = "12";
	
	public static final String REGISTRO_TIPO_HIDROMETRO_LOCAL_INST = "13";
	
	public static final String REGISTRO_TIPO_HIDROMETRO_PROTECAO = "14";
	
	public static final String REGISTRO_TIPO_ORGAO_EXPEDIDOR_RG = "15";
	
	public static final String REGISTRO_TIPO_ENDERECO_REFERENCIA = "16";
	
	public static final String REGISTRO_TIPO_PESSOA_SEXO = "17";

	public static final String REGISTRO_TIPO_FONE_TIPO = "18";
	
	public static final String REGISTRO_TIPO_SISTEMA_PARAMETRO = "19";
	
	public static final String REGISTRO_TIPO_IMOVEL_ATLZ_CADASTRAL = "20";
	
	public static final String REGISTRO_TIPO_CLIENTE_ATLZ_CADASTRAL = "21";
	
	public static final String REGISTRO_TIPO_CLIENTE_FONE_ATLZ_CADASTRAL = "22";
	
	public static final String REGISTRO_TIPO_HIDROM_INST_HIST_ATL_CAD = "23";
	
	public static final String REGISTRO_TIPO_IMOVEL_SUBCATG_ATLZ_CAD = "24";
    
	public static final String REGISTRO_TIPO_LOGRADOURO_TIPO = "25";
	
	public static final String REGISTRO_TIPO_LOGRADOURO_TITULO = "26";
	
	public static final String REGISTRO_TIPO_MUNICIPIO = "27";

	public static final String REGISTRO_TIPO_QUADRA = "29"; 
	
	public static final String REGISTRO_TIPO_IMOVEL_PERFIL = "28"; 
	
	public static final String REGISTRO_TIPO_CADASTRO_OCORRENCIA = "30";
	
	public static final String REGISTRO_TIPO_UNIDADE_FEDERACAO = "31";
	
	public static final String REGISTRO_TIPO_HIDROMETRO_MARCA = "32";
	
	public static final String REGISTRO_TIPO_HIDROMETRO_CAPACIDADE = "33";

	public static final String REGISTRO_TIPO_CLIENTE_TIPO = "34";
	
	public static final String REGISTRO_TIPO_MEDICAO_TIPO = "35";
	
	public static final String REGISTRO_TIPO_SETOR_COMERCIAL = "36";
	
	public static final String REGISTRO_TIPO_ARQUIVO_CARREGADO_COMPLETO = "37";
	
    public static final String  COLUMN_DESCRIPTION_ALIAS = "description";
    
    public static final String  COLUMN_TIPO_ALIAS = "tipo";
    
    public static final String  COLUMN_TITULO_ALIAS = "titulo";
    
    public static final String  COLUMN_LOGRADOURO_ALIAS = "logradouro";
    
    public static final String  COLUMN_DESC_FORMATADA_ALIAS = "descricaoFormatada";

   
    /**
     * SDCard
     */
    public static final String SDCARD_PATH                           = Environment.getExternalStorageDirectory().toString();

    public static final String  SDCARD_GSANAC_PATH                    = SDCARD_PATH + "/gsanAC/";
    
    public static final String SDCARD_BANCO_PATH 					  = SDCARD_GSANAC_PATH + "banco";

    public static final String  SDCARD_GSANAC_RETURN_PATH             = SDCARD_GSANAC_PATH + "retorno";
    
    public static final String  SDCARD_GSANAC_PHOTOS_PATH             = SDCARD_GSANAC_PATH  + "fotos";

    public static final String  SDCARD_GSANAC_FILES_PATH              = SDCARD_GSANAC_PATH + "arquivos";
    
    public static final String  SDCARD_GSANAC_ARQUIVO_DIVIDIDO_PATH   = SDCARD_GSANAC_PATH + "arquivo_dividido";

    public static final String  SDCARD_DCIM_CAMERA_PATH               = SDCARD_PATH + "/DCIM/Camera";

    public static final String  SDCARD_GSANAC_VERSION_PATH            = SDCARD_GSANAC_PATH + "versao";

	
    
    public static final String  NOME_APK = "gsanac.apk";
    
    public static final int     INSTALL_APK = 4741;

    public static final byte    DOWNLOAD_FILE                         = 1;

    public static final byte    UPLOAD_FILE                           = 2;
    
    public static final byte FINALIZAR_ROTEIRO = 3;

    public static final byte    ATUALIZAR_SITUACAO_ARQUIVO = 5;

    public static final Integer NULO_INT                              = null;
    
    public static final Long NULO_LONG                              = null;

    public static final String  NULO_STRING                           = "";
    
	public static final String IMOVEL = "imovel";
	public static final String LOGRADOURO = "logradouro";
	public static final String INTEGRACAO_MATRICULA = "compesa.mobile.mapalocal.activities.exibicaoMapa.MATRICULA";
	public static final String INTEGRACAO_ID_UNICO = "compesa.mobile.mapalocal.activities.exibicaoMapa.IDUNICO";
	public static final String INTEGRACAO_OPERACAO_INTEGRACAO = "compesa.mobile.mapalocal.activities.exibicaoMapa.OPERACAOEFETUADA";
	public static final String INTEGRACAO_CODIGO_TIPO_FINALIZACAO = "compesa.mobile.mapalocal.activities.exibicaoMapa.TIPOFINALIZACAO";
	public static final String INTEGRACAO_ENVIO_MATRICULAS = "ipad.gsan.atualizacaocadastral.LISTAMATRICULAS";
	public static final String INTEGRACAO_ENVIO_LOCALIDADE = "ipad.gsan.atualizacaocadastral.LOCALIDADE";
	public static final String INTEGRACAO_ENVIO_SETORCOMERCIAL = "ipad.gsan.atualizacaocadastral.SETORCOMERCIAL";
	
    
    public static final String  IMOVEL_SUBCATEGORIA_ATLZ_CADASTRAL = "imovelSubCategoriaAtlzCadastral"; 
    
    public static final long ITEM_INVALIDO = 999999999; 
    
    /** Resquest Code ***/
    public static final int ENDERECO_ABA_REQUEST_CODE = 1;
    public static final int IMOVEL_ABA_REQUEST_CODE = 3;

    public static final int[]   CPF                            = {
            11,
            10,
            9,
            8,
            7,
            6,
            5,
            4,
            3,
            2
                                                                      };

    public static final int[]   CNPJ                           = {
            6,
            5,
            4,
            3,
            2,
            9,
            8,
            7,
            6,
            5,
            4,
            3,
            2
                                                                      };

    public static final Integer PESSOA_FISICA = 1;
    
    public static final Integer PESSOA_JURIDICA = 2;
    
 // Erros da classe
 	public static final int ERRO_GENERICO = 0;
 	public static final int ERRO_REQUISICAO_ABORTAR = 1;
 	public static final int ERRO_DOWNLOAD_ARQUIVO = 2;
 	public static final int ERRO_CARREGANDO_ARQUIVO = 3;
 	public static final int ERRO_SERVIDOR_OFF_LINE = 4;
 	public static final int ERRO_SINAL_INICIALIZACAO_ROTEIRO = 5;
 	public static final int ERRO_SEM_ARQUIVO = 6;
 	
 	
 	public static final char RESPOSTA_OK_CHAR = '*';
 	public static final String RESPOSTA_OK = "*";
 	public static final String RESPOSTA_ERRO = "#";
 	public static final byte PING = 15;
 	public static final byte CONSULTAR_ARQUIVO_FINALIZADO = 16;
 	public static final int OK = 100;
 	
 	public static final byte DOWNLOAD_APK = 11;
 	public static final byte VERIFICAR_VERSAO = 12;
 	public static final byte FINALIZAR_IMOVEL = 13;
 	
 	public static final int INDICADOR_TRANSMITIDO = 1;
 	public static final int INDICADOR_NAO_TRANSMITIDO = 2;
 	
	public static final String LOGIN = "LOGIN";
	public static final String SENHA = "SENHA";
	public static final String CPF_LOGIN = "CPF_LOGIN";
	
	public static final String REGISTRO_TIPO_AD_CEP	 = "01";
	public static final String REGISTRO_TIPO_AD_LOGRADOURO = "02";
	public static final String REGISTRO_TIPO_AD_LOGRADOURO_BAIRRO = "03";
	public static final String REGISTRO_TIPO_AD_LOGRADOURO_CEP = "04";
	public static final String REGISTRO_TIPO_AD_IMOVEL_ATLZ_CADASTRAL = "05";
	public static final String REGISTRO_TIPO_AD_CLIENTE_ATLZ_CADASTRAL = "06";
	public static final String REGISTRO_TIPO_AD_CLIENTE_FONE_ATLZ_CADASTRAL = "07";
	public static final String REGISTRO_TIPO_AD_HIDROMETRO_ATLZ_CADASTRAL = "08";
	public static final String REGISTRO_TIPO_AD_SUBCATEGORIA_ATLZ_CADASTRAL = "09";
	public static final String REGISTRO_TIPO_AD_OCORRENCIA_ATLZ_CADASTRAL = "10";
	public static final String REGISTRO_TIPO_AD_IMOVEL_TRANSMITIDO = "11";
	public static final String REGISTRO_TIPO_AD_IMOVEL_FOTO = "12";
	public static final String ARQUIVO_TRANSMITIDO_USUARIO_SEM_LOGIN = "13";
	public static final String ARQUIVO_TRANSMITIDO_USUARIO_COM_LOGIN = "00";
	
	public static final int ARQUIVO_TRANSMITIDO_USUARIO_SEM_LOGIN_INT = 13;
	
	public static final int SEM_NUMERO = 9;
	
	public static final String SEM_NUMERO_ONLINE_OFFLINE = "S/N";

}