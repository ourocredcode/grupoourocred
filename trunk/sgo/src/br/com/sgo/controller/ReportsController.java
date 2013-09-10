package br.com.sgo.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ReportsDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class ReportsController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ReportsDao reportsDao;
	private final UsuarioDao usuarioDao;

	private HttpServletResponse response;
	private Empresa empresa;
	private Organizacao organizacao;

	public ReportsController(Empresa empresa, Organizacao organizacao,UsuarioInfo usuarioInfo,Result result,HttpServletResponse response,ReportsDao reportsDao,
			UsuarioDao usuarioDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.response = response;
		this.reportsDao = reportsDao;
		this.usuarioDao = usuarioDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();

	}

	@Get
	@Path("/reports/relatorios")
	public void relatorios(){

	}
	
	@Get
	@Path("/reports/filtros/aprovados")
	public void filtrosaprovados(){

		Calendar mesAtual = Calendar.getInstance();

		Collection<Integer> meses = new HashSet<Integer>();

		meses.add(0);
		meses.add(1);
		meses.add(2);
		meses.add(3);
		meses.add(4);
		meses.add(5);
		meses.add(6);
		meses.add(7);
		meses.add(8);
		meses.add(9);
		meses.add(10);
		meses.add(11);

		result.include("meses",meses);
		result.include("mesAtual",mesAtual.get(Calendar.MONTH));

	}
	
	
	@Get
	@Path("/reports/filtros/producaoativa")
	public void filtrosproducaoativa(){
		
		Collection<Usuario> supervisores = this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial");

		//TODO : ALTERAR USUÁRIO SUPERVISOR RETENÇÃO
		supervisores.add(this.usuarioDao.buscaUsuario(2l, 2l, "38316716801"));

		result.include("supervisores",supervisores);

		Calendar mesAtual = Calendar.getInstance();

		Collection<Integer> meses = new HashSet<Integer>();

		meses.add(0);
		meses.add(1);
		meses.add(2);
		meses.add(3);
		meses.add(4);
		meses.add(5);
		meses.add(6);
		meses.add(7);
		meses.add(8);
		meses.add(9);
		meses.add(10);
		meses.add(11);

		result.include("meses",meses);
		result.include("mesAtual",mesAtual.get(Calendar.MONTH));

	}
	
	@Get
	@Path("/reports/filtros/metadiaria")
	public void filtrosmetadiaria(){

		Collection<Usuario> supervisores = this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial");

		//TODO : ALTERAR USUÁRIO SUPERVISOR RETENÇÃO
		supervisores.add(this.usuarioDao.buscaUsuario(2l, 2l, "38316716801"));

		result.include("supervisores",supervisores);

		Calendar mesAtual = Calendar.getInstance();

		Collection<Integer> meses = new HashSet<Integer>();

		meses.add(0);
		meses.add(1);
		meses.add(2);
		meses.add(3);
		meses.add(4);
		meses.add(5);
		meses.add(6);
		meses.add(7);
		meses.add(8);
		meses.add(9);
		meses.add(10);
		meses.add(11);

		result.include("meses",meses);
		result.include("mesAtual",mesAtual.get(Calendar.MONTH));

	}

	@Get
 	@Path("/reports/status")
	public void status() {

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "report_status.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);

		JasperPrint impressao = null;

		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");

			ServletOutputStream responseOutputStream = response.getOutputStream();

			JRDataSource jrRS = new JRResultSetDataSource(reportsDao.statusResultSet());

			impressao = JasperFillManager.fillReport(jasper, parametros , jrRS);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);

			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {

			System.out.println("Erro:" + e);

		} catch(JRException e) {

			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());

		}

		result.nothing();
	}
	
	@Post
 	@Path("/reports/aprovados")
	public void aprovados(Empresa empresa,Organizacao organizacao,Integer ano,Integer mes,Integer concluidoCheck) {

		Calendar calInicio = new GregorianCalendar();
		Calendar calFim = new GregorianCalendar();

		calInicio.set(Calendar.YEAR,ano);
		calInicio.set(Calendar.MONTH, mes);
		calInicio.set(Calendar.DAY_OF_MONTH,calInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
		calInicio.set(Calendar.HOUR_OF_DAY, calInicio.getActualMinimum(Calendar.HOUR_OF_DAY));
		calInicio.set(Calendar.MINUTE, calInicio.getActualMinimum(Calendar.MINUTE));
		calInicio.set(Calendar.SECOND, calInicio.getActualMinimum(Calendar.SECOND));

		calFim.set(Calendar.YEAR,ano);
		calFim.set(Calendar.MONTH, mes);
		calFim.set(Calendar.DAY_OF_MONTH,calFim.getActualMaximum(Calendar.DAY_OF_MONTH));
		calFim.set(Calendar.HOUR_OF_DAY, calFim.getActualMaximum(Calendar.HOUR_OF_DAY));
		calFim.set(Calendar.MINUTE, calFim.getActualMaximum(Calendar.MINUTE));
		calFim.set(Calendar.SECOND, calFim.getActualMaximum(Calendar.SECOND));

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "report_aprovados.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);
		parametros.put("nomeMes",obterNomeMes(mes)+"/"+ano);

		String nomeReport = concluidoCheck == null ? "Aprovados" : "Concluídos";

		parametros.put("nomeReport",nomeReport);

		JasperPrint impressao = null;

		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");

			ServletOutputStream responseOutputStream = response.getOutputStream();

			JRDataSource jrRS = new JRResultSetDataSource(reportsDao.aprovadosResultSet(empresa, organizacao, calInicio, calFim, concluidoCheck));

			impressao = JasperFillManager.fillReport(jasper, parametros , jrRS);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);

			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {

			System.out.println("Erro:" + e);

		} catch(JRException e) {

			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());

		}

		result.nothing();
	}
	
	@Post
 	@Path("/reports/producaoativa")
	public void producaoativa(Empresa empresa,Organizacao organizacao,Usuario usuario ) {

		Usuario u = this.usuarioDao.buscaUsuarioById(usuario.getUsuario_id());

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "report_producaoativa.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);
		parametros.put("nomeEquipe", u.getApelido());
		parametros.put("nomeReport","Produção Ativa");

		JasperPrint impressao = null;

		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");

			ServletOutputStream responseOutputStream = response.getOutputStream();

			JRDataSource jrRS = new JRResultSetDataSource(reportsDao.producaoAtivaResultSet(empresa, organizacao, u));

			impressao = JasperFillManager.fillReport(jasper, parametros , jrRS);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);

			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {

			System.out.println("Erro:" + e);

		} catch(JRException e) {

			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());

		}

		result.nothing();
	}
	
	@Post
 	@Path("/reports/metadiaria")
	public void metadiaria(Empresa empresa,Organizacao organizacao,Integer ano,Integer mes,Usuario usuario) {

		Calendar calInicio = new GregorianCalendar();
		Calendar calFim = new GregorianCalendar();

		calInicio.set(Calendar.YEAR,ano);
		calInicio.set(Calendar.MONTH, mes);
		calInicio.set(Calendar.DAY_OF_MONTH,calInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
		calInicio.set(Calendar.HOUR_OF_DAY, calInicio.getActualMinimum(Calendar.HOUR_OF_DAY));
		calInicio.set(Calendar.MINUTE, calInicio.getActualMinimum(Calendar.MINUTE));
		calInicio.set(Calendar.SECOND, calInicio.getActualMinimum(Calendar.SECOND));

		calFim.set(Calendar.YEAR,ano);
		calFim.set(Calendar.MONTH, mes);
		calFim.set(Calendar.DAY_OF_MONTH,calFim.getActualMaximum(Calendar.DAY_OF_MONTH));
		calFim.set(Calendar.HOUR_OF_DAY, calFim.getActualMaximum(Calendar.HOUR_OF_DAY));
		calFim.set(Calendar.MINUTE, calFim.getActualMaximum(Calendar.MINUTE));
		calFim.set(Calendar.SECOND, calFim.getActualMaximum(Calendar.SECOND));

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "report_metadiaria.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);
		//parametros.put("nomeMes",obterNomeMes(mes)+"/"+ano);

		String nomeReport =  "Relatório Meta Diária";

		parametros.put("nomeReport",nomeReport);

		JasperPrint impressao = null;

		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");

			ServletOutputStream responseOutputStream = response.getOutputStream();

			JRDataSource jrRS = new JRResultSetDataSource(reportsDao.metaDiariaResultSet(empresa, organizacao, calInicio, calFim, usuario));

			impressao = JasperFillManager.fillReport(jasper, parametros , jrRS);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);

			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {

			System.out.println("Erro:" + e);

		} catch(JRException e) {

			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());

		}

		result.nothing();
	}
	
	@Get
 	@Path("/reports/chart-aprovados")
	public void chart_aprovados(Empresa empresa,Organizacao organizacao,Integer ano,Integer mes,Integer concluidoCheck) {

		Calendar calInicio = new GregorianCalendar();
		Calendar calFim = new GregorianCalendar();

		calInicio.set(GregorianCalendar.DAY_OF_MONTH, calInicio.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		calInicio.set(GregorianCalendar.HOUR_OF_DAY,calInicio.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		calInicio.set(GregorianCalendar.MINUTE,calInicio.getActualMinimum(GregorianCalendar.MINUTE));
		calInicio.set(GregorianCalendar.SECOND,calInicio.getActualMinimum(GregorianCalendar.SECOND));
		calFim.set(GregorianCalendar.DAY_OF_MONTH, calFim.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		calFim.set(GregorianCalendar.HOUR_OF_DAY, calFim.getActualMaximum(GregorianCalendar.HOUR));
		calFim.set(GregorianCalendar.MINUTE, calFim.getActualMaximum(GregorianCalendar.MINUTE));
		calFim.set(GregorianCalendar.SECOND, calFim.getActualMaximum(GregorianCalendar.SECOND));

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "chart_aprovados.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);

		JasperPrint impressao = null;

		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");

			ServletOutputStream responseOutputStream = response.getOutputStream();

			JRDataSource jrRS = new JRResultSetDataSource(reportsDao.aprovadosResultSet(empresa,organizacao,calInicio,calFim,concluidoCheck));

			impressao = JasperFillManager.fillReport(jasper, parametros , jrRS);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);

			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {

			System.out.println("Erro:" + e);

		} catch(JRException e) {

			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());

		}

		result.nothing();
	}
	
	public String obterNomeMes(int mes){  
	    String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",   
	                      "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};  
	    return meses[mes];  
	} 
}