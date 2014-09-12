package br.com.sgo.controller;

import java.io.File;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.infra.CustomFileUtil;
import br.com.sgo.interceptor.Public;

@Resource
public class ScriptController {

	private final Result result;
	private HttpServletResponse response;
	private ServletOutputStream responseOutputStream;

	public ScriptController(Result result,HttpServletResponse response){		
		this.result = result;
		this.response = response;
	}

	@Get
	@Path("/script/{id}")
	public void script(int id) {

		switch (id) {
			//Script de Vendas
			case 1 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/vendas.jsp"); break;
			//Procedimento de Boletos
			case 2 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/quitadores.jsp"); break;
			case 3 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/operacao.jsp"); break;
			case 4 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/apoiocomercial.jsp"); break;
			case 5 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/operacaoapoiocomercial.jsp"); break;
			case 6 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/bancosrefinanciamento.jsp"); break;
			case 7 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/bancosanalisesuper.jsp"); break;
			case 8 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/bancossuspensos.jsp"); break;
			case 9 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/cartaquitacao.jsp"); break;
			case 10 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/tabela_desconto.jsp"); break;
			case 11 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/politica_banco.jsp"); break;
			case 12 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/coeficientes.jsp"); break;
			case 13 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/descricaoStatus.jsp"); break;
			case 14 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/novasideias.jsp"); break;
			case 15 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/resultados.jsp"); break;
			case 16 : result.use(Results.page()).forwardTo("/WEB-INF/jsp/script/analiseBI.jsp"); break;
			
			default: result.nothing();
		}

	}
	
	@Get
	@Path("/visualizaScript/{caminhoPDF}")
	@Public
	public synchronized void visualiza(String caminhoPDF) {

		String diretorio = "////localhost//sistemas//_repositorio//scripts//";
		String nomeFile = diretorio + caminhoPDF;

		File pdf = new File(nomeFile);

		try {

            byte[] arquivo = null;

            File dir = new File(pdf.getPath());

            try {
                arquivo = CustomFileUtil.fileToByte(dir);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.reset();
            response.setContentType("application/pdf");
            response.setDateHeader("Expires", 0);
            response.setContentLength(arquivo.length);
            response.getOutputStream().write(arquivo, 0, arquivo.length);

            responseOutputStream = response.getOutputStream();
            

            responseOutputStream.flush();
			responseOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

		result.nothing();
	}

}