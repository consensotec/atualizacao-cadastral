package com.br.gsanac.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.util.Log;

import com.br.gsanac.entidades.Bairro;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.Categoria;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.ClienteTipo;
import com.br.gsanac.entidades.EnderecoReferencia;
import com.br.gsanac.entidades.FoneTipo;
import com.br.gsanac.entidades.FonteAbastecimento;
import com.br.gsanac.entidades.HidrometroCapacidade;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroLocalInst;
import com.br.gsanac.entidades.HidrometroMarca;
import com.br.gsanac.entidades.HidrometroProtecao;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelPerfil;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.LigacaoAguaSituacao;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.LogradouroBairro;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroTipo;
import com.br.gsanac.entidades.LogradouroTitulo;
import com.br.gsanac.entidades.MedicaoTipo;
import com.br.gsanac.entidades.Municipio;
import com.br.gsanac.entidades.OrgaoExpedidorRg;
import com.br.gsanac.entidades.PavimentoCalcada;
import com.br.gsanac.entidades.PavimentoRua;
import com.br.gsanac.entidades.PessoaSexo;
import com.br.gsanac.entidades.Quadra;
import com.br.gsanac.entidades.SetorComercial;
import com.br.gsanac.entidades.SubCategoria;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.entidades.UnidadeFederacao;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.fachada.Fachada;
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
import com.br.gsanac.repositorio.RepositorioHidrometroCapacidade;
import com.br.gsanac.repositorio.RepositorioHidrometroInstHistAtlzCad;
import com.br.gsanac.repositorio.RepositorioHidrometroLocalInst;
import com.br.gsanac.repositorio.RepositorioHidrometroMarca;
import com.br.gsanac.repositorio.RepositorioHidrometroProtecao;
import com.br.gsanac.repositorio.RepositorioImovelAtlzCadastral;
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
import com.br.gsanac.repositorio.RepositorioSubCategoria;
import com.br.gsanac.repositorio.RepositorioSistemaParametros;
import com.br.gsanac.repositorio.RepositorioUnidadeFederacao;


/**
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class DBLoader {

    private static DBLoader instance;

    private static int      i = 0;

    public static Integer CONTADOR_IMOVEL = 0;
    
    public static DBLoader getInstance() {
        if (instance == null) {
            instance = new DBLoader();
        }
        return instance;
    }

    public static boolean loadDatabaseFromInputStream(InputStream input, boolean isToDropDB, String cpfLogin) {
    	
    	boolean retorno = false;
    	try {

            InputStreamReader inputReader = new InputStreamReader(input, "ISO-8859-1");
            BufferedReader reader = new BufferedReader(inputReader);
            String line;
            DBLoader.CONTADOR_IMOVEL = 0;
            while ((line = reader.readLine()) != null) {
                carregarBancoDados(line, cpfLogin);
            }
            retorno = true;
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (RepositorioException re) {
            re.printStackTrace();
        }
    	return retorno;
    }

    /**
     * @author Arthur Carvalho
     * @since 09/09/2011
     * @param line
     * @throws RepositorioException
     */
    public static void carregarBancoDados(String line, String cpfLogin) throws RepositorioException {

        List<String> objectArray = Util.split(line);

        String registerType = objectArray.get(0);

        if (registerType.equals(ConstantesSistema.REGISTRO_TIPO_PESSOA_SEXO)) {
            RepositorioPessoaSexo.getInstance().inserir(PessoaSexo.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_FONE_TIPO)){
            RepositorioFoneTipo.getInstance().inserir(FoneTipo.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_HIDROMETRO_LOCAL_INST)){
            RepositorioHidrometroLocalInst.getInstance().inserir(HidrometroLocalInst.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_HIDROMETRO_PROTECAO)){
            RepositorioHidrometroProtecao.getInstance().inserir(HidrometroProtecao.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_ORGAO_EXPEDIDOR_RG)){
            RepositorioOrgaoExpedidorRg.getInstance().inserir(OrgaoExpedidorRg.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_CATEGORIA)){
            RepositorioCategoria.getInstance().inserir(Categoria.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_SUBCATEGORIA)){
            RepositorioSubCategoria.getInstance().inserir(SubCategoria.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_BAIRRO)){
            RepositorioBairro.getInstance().inserir(Bairro.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_CEP)){
            RepositorioCep.getInstance().inserir(Cep.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_LOGRADOURO)){
            RepositorioLogradouro.getInstance().inserir(Logradouro.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_LOGRADOURO_BAIRRO)){
            RepositorioLogradouroBairro.getInstance().inserir(LogradouroBairro.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_LOGRADOURO_CEP)){
            RepositorioLogradouroCep.getInstance().inserir(LogradouroCep.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_PAVIMENTO_RUA)){
            RepositorioPavimentoRua.getInstance().inserir(PavimentoRua.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_PAVIMENTO_CALCADA)){
            RepositorioPavimentoCalcada.getInstance().inserir(PavimentoCalcada.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_FONTE_ABASTECIMENTO)){
            RepositorioFonteAbastecimento.getInstance().inserir(FonteAbastecimento.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_LIGACAO_AGUA_SITUACAO)){
            RepositorioLigacaoAguaSituacao.getInstance().inserir(LigacaoAguaSituacao.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_LIGACAO_ESGOTO_SITUACAO)){
            RepositorioLigacaoEsgotoSituacao.getInstance().inserir(LigacaoEsgotoSituacao.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_ENDERECO_REFERENCIA)){
            RepositorioEnderecoReferencia.getInstance().inserir(EnderecoReferencia.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_SISTEMA_PARAMETRO)){
            RepositorioSistemaParametros.getInstance().inserir(SistemaParametros.inserirDoArquivo(objectArray, cpfLogin));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_IMOVEL_SUBCATG_ATLZ_CAD)){
            RepositorioImovelSubCategAtlzCad.getInstance().inserir(ImovelSubCategAtlzCad.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_CLIENTE_FONE_ATLZ_CADASTRAL)){
            RepositorioClienteFoneAtlzCad.getInstance().inserir(ClienteFoneAtlzCad.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_CLIENTE_ATLZ_CADASTRAL)){
            RepositorioClienteAtlzCadastral.getInstance().inserir(ClienteAtlzCadastral.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_IMOVEL_ATLZ_CADASTRAL)){
        	CONTADOR_IMOVEL ++;
        	RepositorioImovelAtlzCadastral.getInstance().inserir(ImovelAtlzCadastral.inserirDoArquivo(objectArray, CONTADOR_IMOVEL));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_HIDROM_INST_HIST_ATL_CAD)){
            RepositorioHidrometroInstHistAtlzCad.getInstance().inserir(HidrometroInstHistAtlzCad.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_MUNICIPIO)){
            RepositorioMunicipio.getInstance().inserir(Municipio.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_LOGRADOURO_TIPO)){
            RepositorioLogradouroTipo.getInstance().inserir(LogradouroTipo.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_LOGRADOURO_TITULO)){
            RepositorioLogradouroTitulo.getInstance().inserir(LogradouroTitulo.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_QUADRA)){
            RepositorioQuadra.getInstance().inserir(Quadra.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_IMOVEL_PERFIL)){
            RepositorioImovelPerfil.getInstance().inserir(ImovelPerfil.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_CADASTRO_OCORRENCIA)){
            RepositorioCadastroOcorrencia.getInstance().inserir(CadastroOcorrencia.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_UNIDADE_FEDERACAO)){
        	RepositorioUnidadeFederacao.getInstance().inserir(UnidadeFederacao.inserirDoArquivo(objectArray));
        	i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_CLIENTE_TIPO)){
        	RepositorioClienteTipo.getInstance().inserir(ClienteTipo.inserirDoArquivo(objectArray));
        	i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_MEDICAO_TIPO)){
            RepositorioMedicaoTipo.getInstance().inserir(MedicaoTipo.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_HIDROMETRO_CAPACIDADE)){
            RepositorioHidrometroCapacidade.getInstance().inserir(HidrometroCapacidade.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_HIDROMETRO_MARCA)){
            RepositorioHidrometroMarca.getInstance().inserir(HidrometroMarca.inserirDoArquivo(objectArray));
            i++;
        }else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_SETOR_COMERCIAL)){
            RepositorioSetorComercial.getInstance().inserir(SetorComercial.inserirDoArquivo(objectArray));
            i++;
        } else if(registerType.equals(ConstantesSistema.REGISTRO_TIPO_ARQUIVO_CARREGADO_COMPLETO)){

        	//pesquisa sistema parametro e atualiza a coluna informando que todos os registros do arquivo foram carregados corretamente.
        	SistemaParametros sistemaParametros = new SistemaParametros();
        	try{
	    		sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
	    		
	    		if ( sistemaParametros != null ) {
	    			sistemaParametros.setIndicadorArquivoCarregado(Integer.valueOf(1));
	    			Fachada.getInstance().update(sistemaParametros);
	    		}
	    	} catch (FachadaException e) {
				e.printStackTrace();
			}
            i++;
        }
        
        
        Log.i(ConstantesSistema.LOG_TAG, i + " " + line);

        objectArray.clear();
    }
}