package com.br.gsanac.util;

import com.br.gsanac.entidades.Bairro;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.Foto.FotoColunasTipo;
import com.br.gsanac.entidades.HidrometroCapacidade;
import com.br.gsanac.entidades.HidrometroCapacidade.HidrometroCapacidadeColunasTipo;
import com.br.gsanac.entidades.HidrometroMarca;
import com.br.gsanac.entidades.HidrometroMarca.HidrometroMarcaColunasTipo;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelOcorrencia.ImovelOcorrenciaColunasTipo;
import com.br.gsanac.entidades.MedicaoTipo;
import com.br.gsanac.entidades.Bairro.BairroTipos;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunasTipo;
import com.br.gsanac.entidades.Categoria;
import com.br.gsanac.entidades.Categoria.CategoriaColunasTipo;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.Cep.CepTipos;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteAtlzCadastral.ClienteAtlzCadastralColunasTipo;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.ClienteFoneAtlzCad.ClienteFoneAtlzCadColunasTipo;
import com.br.gsanac.entidades.ClienteTipo;
import com.br.gsanac.entidades.ClienteTipo.ClienteTipoColunasTipo;
import com.br.gsanac.entidades.EnderecoReferencia;
import com.br.gsanac.entidades.EnderecoReferencia.EnderecoReferenciaTipos;
import com.br.gsanac.entidades.FoneTipo;
import com.br.gsanac.entidades.FoneTipo.FoneTipoColunasTipo;
import com.br.gsanac.entidades.FonteAbastecimento;
import com.br.gsanac.entidades.FonteAbastecimento.FonteAbastecimentoTipos;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad.HidrometroInstHistAtlzCadColunasTipo;
import com.br.gsanac.entidades.HidrometroLocalInst;
import com.br.gsanac.entidades.HidrometroLocalInst.HidrometroLocalInstColunasTipo;
import com.br.gsanac.entidades.HidrometroProtecao;
import com.br.gsanac.entidades.HidrometroProtecao.HidrometroProtecaoColunasTipo;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunasTipo;
import com.br.gsanac.entidades.ImovelPerfil;
import com.br.gsanac.entidades.ImovelPerfil.ImovelPerfilColunasTipo;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad.ImovelSubCategAtlzCadColunasTipo;
import com.br.gsanac.entidades.LigacaoAguaSituacao;
import com.br.gsanac.entidades.LigacaoAguaSituacao.LigacaoAguaSituacaoTipos;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao.LigacaoEsgotoSituacaoTipos;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.Logradouro.LogradouroTipos;
import com.br.gsanac.entidades.LogradouroBairro;
import com.br.gsanac.entidades.LogradouroBairro.LogradouroBairroTipos;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroCep.LogradouroCepTipos;
import com.br.gsanac.entidades.LogradouroTipo;
import com.br.gsanac.entidades.LogradouroTipo.LogradouroTipoColunasTipo;
import com.br.gsanac.entidades.LogradouroTitulo;
import com.br.gsanac.entidades.LogradouroTitulo.LogradouroTituloColunasTipo;
import com.br.gsanac.entidades.MedicaoTipo.MedicaoTipoColunasTipo;
import com.br.gsanac.entidades.Municipio;
import com.br.gsanac.entidades.Municipio.MunicipioColunasTipo;
import com.br.gsanac.entidades.OrgaoExpedidorRg;
import com.br.gsanac.entidades.OrgaoExpedidorRg.OrgaoExpedidorRgColunasTipo;
import com.br.gsanac.entidades.PavimentoCalcada;
import com.br.gsanac.entidades.PavimentoCalcada.PavimentoCalcadaTipos;
import com.br.gsanac.entidades.PavimentoRua;
import com.br.gsanac.entidades.PavimentoRua.PavimentoRuaTipos;
import com.br.gsanac.entidades.PessoaSexo;
import com.br.gsanac.entidades.PessoaSexo.PessoaSexoColunasTipo;
import com.br.gsanac.entidades.Quadra;
import com.br.gsanac.entidades.Quadra.QuadraColunasTipo;
import com.br.gsanac.entidades.SetorComercial;
import com.br.gsanac.entidades.SetorComercial.SetorColunasTipo;
import com.br.gsanac.entidades.SubCategoria;
import com.br.gsanac.entidades.SubCategoria.SubCategoriaColunasTipo;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.entidades.SistemaParametros.SistemaParametrosColunasTipo;
import com.br.gsanac.entidades.UnidadeFederacao;
import com.br.gsanac.entidades.UnidadeFederacao.UnidadeFederacaoColunasTipo;


/**
 * <p>
 * Esta class contem os scripts de CREATE e DROP
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public final class DBScript {

	/**
	 * Retorna script para geracao do banco de dados
	 * 
	 */
	public String[] obterScriptBanco(){
		
		//Inicia variaveis
		PessoaSexoColunasTipo pessoaSexoColunasTipos   	 			  = (new PessoaSexo()).new PessoaSexoColunasTipo();
		FoneTipoColunasTipo foneTipoColunasTipo 					  = (new FoneTipo()).new FoneTipoColunasTipo();
		HidrometroLocalInstColunasTipo hidrometroLocalInstColunasTipo = (new HidrometroLocalInst()).new HidrometroLocalInstColunasTipo();
		HidrometroProtecaoColunasTipo hidrometroProtecaoColunasTipo	  = (new HidrometroProtecao()).new HidrometroProtecaoColunasTipo();
		OrgaoExpedidorRgColunasTipo orgaoExpedidorRgColunasTipo	  	  = (new OrgaoExpedidorRg()).new OrgaoExpedidorRgColunasTipo();
		CategoriaColunasTipo categoriaColunasTipo	  	  			  = (new Categoria()).new CategoriaColunasTipo();
		SubCategoriaColunasTipo subCategoriaColunasTipo	  	  		  = (new SubCategoria()).new SubCategoriaColunasTipo();
		SistemaParametrosColunasTipo parametrosColunasTipo 	  		  = ( new SistemaParametros()).new SistemaParametrosColunasTipo();
		LogradouroTipoColunasTipo logradouroTipoColunasTipo 	  	  = ( new LogradouroTipo()).new LogradouroTipoColunasTipo();
		LogradouroTituloColunasTipo logradouroTituloColunasTipo 	  = ( new LogradouroTitulo()).new LogradouroTituloColunasTipo();
		MunicipioColunasTipo municipioColunasTipo 			  = ( new Municipio()).new MunicipioColunasTipo();
		QuadraColunasTipo quadraColunasTipo					  = ( new Quadra()).new QuadraColunasTipo();
		BairroTipos bairros 								  = ( new Bairro()).new BairroTipos();
		CepTipos ceps 										  = ( new Cep()).new CepTipos();
		LogradouroTipos logradouroTipos 					  = ( new Logradouro()).new LogradouroTipos();
		LogradouroBairroTipos logradouroBairroTipos 		  = ( new LogradouroBairro()).new LogradouroBairroTipos();
		LogradouroCepTipos logradouroCepTipos 				  = ( new LogradouroCep()).new LogradouroCepTipos();
		PavimentoRuaTipos pavimentoRuaTipos 				  = ( new PavimentoRua()).new PavimentoRuaTipos();
		PavimentoCalcadaTipos pavimentoCalcadaTipos 		  = ( new PavimentoCalcada()).new PavimentoCalcadaTipos();
		FonteAbastecimentoTipos fonteAbastecimentoTipos 	  = ( new FonteAbastecimento()).new FonteAbastecimentoTipos();
		LigacaoAguaSituacaoTipos ligacaoAguaSituacaoTipos 	  = ( new LigacaoAguaSituacao()).new LigacaoAguaSituacaoTipos();
		LigacaoEsgotoSituacaoTipos ligacaoEsgotoSituacaoTipos = ( new LigacaoEsgotoSituacao()).new LigacaoEsgotoSituacaoTipos();
		EnderecoReferenciaTipos enderecoReferenciaTipos 	  = ( new EnderecoReferencia()).new EnderecoReferenciaTipos();
		ImovelSubCategAtlzCadColunasTipo imovelSubCategAtlzCadColunasTipo = ( new ImovelSubCategAtlzCad()).new ImovelSubCategAtlzCadColunasTipo();
		ClienteFoneAtlzCadColunasTipo clienteFoneAtlzCadColunasTipo   	  = ( new ClienteFoneAtlzCad()).new ClienteFoneAtlzCadColunasTipo();
		ClienteAtlzCadastralColunasTipo clienteAtlzCadastralColunasTipo   = ( new ClienteAtlzCadastral()).new ClienteAtlzCadastralColunasTipo();
		ImovelAtlzCadastralColunasTipo imovelAtlzCadastralColunasTipo     = ( new ImovelAtlzCadastral()).new ImovelAtlzCadastralColunasTipo();
		HidrometroInstHistAtlzCadColunasTipo hidrometroInstHistAtlzCadColunasTipo = ( new HidrometroInstHistAtlzCad()).new HidrometroInstHistAtlzCadColunasTipo();
		ImovelPerfilColunasTipo imovelPerfilColunasTipo = ( new ImovelPerfil()).new ImovelPerfilColunasTipo();
		CadastroOcorrenciaColunasTipo cadastroOcorrenciaColunasTipo = ( new CadastroOcorrencia()).new CadastroOcorrenciaColunasTipo();
		UnidadeFederacaoColunasTipo unidadeFederacaoColunasTipo = ( new UnidadeFederacao()).new UnidadeFederacaoColunasTipo();
		ClienteTipoColunasTipo clienteTipoColunasTipo = ( new ClienteTipo()).new ClienteTipoColunasTipo();
		MedicaoTipoColunasTipo medicaoTipoColunasTipo = ( new MedicaoTipo()).new MedicaoTipoColunasTipo();
		HidrometroCapacidadeColunasTipo hidrometroCapacidadeColunasTipo = ( new HidrometroCapacidade()).new HidrometroCapacidadeColunasTipo();
		HidrometroMarcaColunasTipo hidrometroMarcaColunasTipo = ( new HidrometroMarca()).new HidrometroMarcaColunasTipo();
		FotoColunasTipo fotoColunasTipo = ( new Foto()).new FotoColunasTipo();
		ImovelOcorrenciaColunasTipo imovelOcorrenciaColunasTipo = ( new ImovelOcorrencia()).new ImovelOcorrenciaColunasTipo();
		SetorColunasTipo setorColunasTipo = ( new SetorComercial()).new SetorColunasTipo();
		
		PessoaSexo pessoaSexo 					= new PessoaSexo();
		FoneTipo foneTipo	  					= new FoneTipo();
		HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
		HidrometroProtecao hidrometroProtecao	= new HidrometroProtecao();
		OrgaoExpedidorRg orgaoExpedidorRg		= new OrgaoExpedidorRg();
		Categoria categoria						= new Categoria();
		SubCategoria subCategoria				= new SubCategoria();
		SistemaParametros sistemaParametros 	= new SistemaParametros();
		LogradouroTipo logradouroTipo			= new LogradouroTipo();
		LogradouroTitulo logradouroTitulo		= new LogradouroTitulo();
		Municipio municipio 					= new Municipio();
		Quadra quadra							= new Quadra();
		Bairro bairro 							= new Bairro();
		Cep cep 								= new Cep();
		LogradouroBairro logradouroBairro 		= new LogradouroBairro();
		LogradouroCep logradouroCep 			= new LogradouroCep();
		Logradouro logradouro 					= new Logradouro();
		PavimentoRua pavimentoRua 				= new PavimentoRua();
		PavimentoCalcada pavimentoCalcada 		= new PavimentoCalcada();
		FonteAbastecimento fonteAbastecimento 	= new FonteAbastecimento();
		LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
		EnderecoReferencia enderecoReferencia 	= new EnderecoReferencia();
		LigacaoEsgotoSituacao ligacaoEsgotoSituacao	= new LigacaoEsgotoSituacao();
		ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
		ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();
		ClienteAtlzCadastral clienteAtlzCadastral   = new ClienteAtlzCadastral();
		ImovelAtlzCadastral imovelAtlzCadastral	    = new ImovelAtlzCadastral();
		HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
		ImovelPerfil imovelPerfil 			  = new ImovelPerfil();
		CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
		UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
		ClienteTipo clienteTipo 		  = new ClienteTipo(); 
		MedicaoTipo medicaoTipo = new MedicaoTipo();
		HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
		HidrometroMarca hidrometroMarca = new HidrometroMarca();
		Foto foto = new Foto();
		ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
		SetorComercial setorComercial = new SetorComercial();
		
		//Usa o metodo createTable para criar o script de cada tabela e junta-os num array
		String[] SCRIPT_CRIAR_BANCO = new String[] {
				                                                            
			createTable(pessoaSexo.getNomeTabela(), pessoaSexo.getColunas(), 
					pessoaSexoColunasTipos.getTipos()).toString(),
					
			createTable(foneTipo.getNomeTabela(), foneTipo.getColunas(), 
					foneTipoColunasTipo.getTipos()).toString(),
					
			createTable(hidrometroLocalInst.getNomeTabela(), hidrometroLocalInst.getColunas(), 
					hidrometroLocalInstColunasTipo.getTipos()).toString(),
					
			createTable(hidrometroProtecao.getNomeTabela(), hidrometroProtecao.getColunas(), 
					hidrometroProtecaoColunasTipo.getTipos()).toString(),
					
			createTable(orgaoExpedidorRg.getNomeTabela(), orgaoExpedidorRg.getColunas(), 
					orgaoExpedidorRgColunasTipo.getTipos()).toString(),
					
			createTable(categoria.getNomeTabela(), categoria.getColunas(), 
					categoriaColunasTipo.getTipos()).toString(),
		
			createTable(subCategoria.getNomeTabela(), subCategoria.getColunas(), 
					subCategoriaColunasTipo.getTipos()).toString(),
					
			createTable(sistemaParametros.getNomeTabela(), sistemaParametros.getColunas(), 
					parametrosColunasTipo.getTipos()).toString(),
					
			createTable(logradouroTipo.getNomeTabela(), logradouroTipo.getColunas(), 
					logradouroTipoColunasTipo.getTipos()).toString(),
					
			createTable(logradouroTitulo.getNomeTabela(), logradouroTitulo.getColunas(), 
					logradouroTituloColunasTipo.getTipos()).toString(),
					
			createTable(municipio.getNomeTabela(), municipio.getColunas(), 
					municipioColunasTipo.getTipos()).toString(),
					
			createTable(quadra.getNomeTabela(), quadra.getColunas(), 
					quadraColunasTipo.getTipos()).toString(),
					
			createTable(bairro.getNomeTabela(), bairro.getColunas(), 
					bairros.getTipos()).toString(),

			createTable(cep.getNomeTabela(), cep.getColunas(), 
					ceps.getTipos()).toString(),

			createTable(logradouro.getNomeTabela(), logradouro.getColunas(), 
					logradouroTipos.getTipos()).toString(),
					
			createTable(logradouroBairro.getNomeTabela(), logradouroBairro.getColunas(), 
					logradouroBairroTipos.getTipos()).toString(),
					
			createTable(logradouroCep.getNomeTabela(), logradouroCep.getColunas(), 
					logradouroCepTipos.getTipos()).toString(),
					
			createTable(pavimentoCalcada.getNomeTabela(), pavimentoCalcada.getColunas(), 
					pavimentoCalcadaTipos.getTipos()).toString(),

			createTable(pavimentoRua.getNomeTabela(), pavimentoRua.getColunas(), 
					pavimentoRuaTipos.getTipos()).toString(),

			createTable(fonteAbastecimento.getNomeTabela(), fonteAbastecimento.getColunas(), 
					fonteAbastecimentoTipos.getTipos()).toString(),

			createTable(ligacaoAguaSituacao.getNomeTabela(), ligacaoAguaSituacao.getColunas(), 
					ligacaoAguaSituacaoTipos.getTipos()).toString(),
					
			createTable(ligacaoEsgotoSituacao.getNomeTabela(), ligacaoEsgotoSituacao.getColunas(), 
					ligacaoEsgotoSituacaoTipos.getTipos()).toString(),

			createTable(enderecoReferencia.getNomeTabela(), enderecoReferencia.getColunas(), 
					enderecoReferenciaTipos.getTipos()).toString(),
					
			createTable(imovelSubCategAtlzCad.getNomeTabela(), imovelSubCategAtlzCad.getColunas(), 
					imovelSubCategAtlzCadColunasTipo.getTipos()).toString(),
					
			createTable(clienteFoneAtlzCad.getNomeTabela(), clienteFoneAtlzCad.getColunas(), 
					clienteFoneAtlzCadColunasTipo.getTipos()).toString(),
			
			createTable(clienteAtlzCadastral.getNomeTabela(), clienteAtlzCadastral.getColunas(), 
					clienteAtlzCadastralColunasTipo.getTipos()).toString(),
					
			createTable(imovelAtlzCadastral.getNomeTabela(), imovelAtlzCadastral.getColunas(), 
					imovelAtlzCadastralColunasTipo.getTipos()).toString(),
			
			createTable(hidrometroInstHistAtlzCad.getNomeTabela(), hidrometroInstHistAtlzCad.getColunas(), 
					hidrometroInstHistAtlzCadColunasTipo.getTipos()).toString(),
					
			createTable(imovelPerfil.getNomeTabela(), imovelPerfil.getColunas(), 
					imovelPerfilColunasTipo.getTipos()).toString(),
					
			createTable(cadastroOcorrencia.getNomeTabela(), cadastroOcorrencia.getColunas(), 
					cadastroOcorrenciaColunasTipo.getTipos()).toString(),
					
			createTable(unidadeFederacao.getNomeTabela(), unidadeFederacao.getColunas(), 
					unidadeFederacaoColunasTipo.getTipos()).toString(),
			
			createTable(clienteTipo.getNomeTabela(), clienteTipo.getColunas(), 
					clienteTipoColunasTipo.getTipos()).toString(),
					
			createTable(medicaoTipo.getNomeTabela(), medicaoTipo.getColunas(), 
					medicaoTipoColunasTipo.getTipos()).toString(),
					
			createTable(hidrometroCapacidade.getNomeTabela(), hidrometroCapacidade.getColunas(), 
					hidrometroCapacidadeColunasTipo.getTipos()).toString(),
					
			createTable(hidrometroMarca.getNomeTabela(), hidrometroMarca.getColunas(), 
					hidrometroMarcaColunasTipo.getTipos()).toString(),
					
			createTable(foto.getNomeTabela(), foto.getColunas(), 
					fotoColunasTipo.getTipos()).toString(),
					
			createTable(imovelOcorrencia.getNomeTabela(), imovelOcorrencia.getColunas(), 
					imovelOcorrenciaColunasTipo.getTipos()).toString(),
					
			createTable(setorComercial.getNomeTabela(), setorComercial.getColunas(), 
					setorColunasTipo.getTipos()).toString(),
			//Tabela responsavel por salvar os arquivos ja carregados no tablet do supervisor. 
			"CREATE TABLE IF NOT EXISTS ARQUIVO_DIVIDIDO ( ARDI_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
			 + "ARDI_DSARQUIVO VARCHAR(50) NOT NULL, ARDI_TMULTIMAALTERACAO TIMESTAMP NOT NULL);",
			
	        "INSERT INTO QUADRA VALUES (999999999, '', 0, 999999999 )",
			"INSERT INTO SETOR_COMERCIAL VALUES (999999999, '', '', 0 )",
			"INSERT INTO BAIRRO VALUES (999999999, ' ', ' ')",
			"INSERT INTO FONE_TIPO VALUES (999999999, '' )",
			"INSERT INTO LIGACAO_AGUA_SITUACAO VALUES (999999999, '' )",
			"INSERT INTO LIGACAO_ESGOTO_SITUACAO VALUES (999999999, '' )",
			"INSERT INTO PAVIMENTO_RUA VALUES (999999999, '' )",
			"INSERT INTO PAVIMENTO_CALCADA VALUES (999999999, '' )",
			"INSERT INTO HIDROMETRO_LOCAL_INST VALUES (999999999, '' )",
			"INSERT INTO HIDROMETRO_PROTECAO VALUES (999999999, '' )",
			"INSERT INTO CADASTRO_OCORRENCIA VALUES (999999999, ' SELECIONE OCORRÊNCIA DE CADASTRO', '')",
			"INSERT INTO CATEGORIA VALUES (999999999, '' )",
			"INSERT INTO FONTE_ABASTECIMENTO VALUES (999999999, '' )",
			"INSERT INTO CLIENTE_TIPO VALUES (999999999, '' , 3)",
			"INSERT INTO ORGAO_EXPEDIDOR_RG VALUES (999999999, '' , '')",
			"INSERT INTO UNIDADE_FEDERACAO VALUES (999999999, '')",
			"INSERT INTO LOGRADOURO VALUES (999999999, 999999999, 999999999, 999999999, '','','', 999999999, 999999999, '')",
			"INSERT INTO ENDERECO_REFERENCIA VALUES (999999999, ' ', '')",
			"INSERT INTO LOGRADOURO_TIPO VALUES (999999999, '', '')",
			"INSERT INTO LOGRADOURO_TITULO VALUES (999999999, '', '')",
			"INSERT INTO SUBCATEGORIA VALUES (999999999, '', '')",
			"INSERT INTO IMOVEL_PERFIL VALUES (999999999, '')"

		};
		
		return SCRIPT_CRIAR_BANCO;
	}
    
    /**
	 * Com base nos array dos nomes de tabela e tipos constantes nas classes basicas
	 * cria a query de criacao da tabela
	 * 
	 * @param String nomeTabela, String[] colunas, String[] tipos
	 * @return StringBuilder
	 */
	private StringBuilder createTable(String nomeTabela, String[] colunas, String[] tipos){
		StringBuilder retorno = new StringBuilder(" CREATE TABLE "+nomeTabela+" ( ");
		for(int i=0;i<colunas.length;i++){
			retorno.append(colunas[i]+tipos[i]);
			if(i!=(colunas.length-1)){
				retorno.append(", ");
			}
		}
		retorno.append(" );");
		return retorno;
	}
}
