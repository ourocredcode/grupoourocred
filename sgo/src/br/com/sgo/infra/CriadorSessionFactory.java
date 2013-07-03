package br.com.sgo.infra;

import java.net.InetAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class CriadorSessionFactory implements ComponentFactory<SessionFactory> {

	private SessionFactory factory;

	@PostConstruct
	public void abre() {

		try{
		  String computername=InetAddress.getLocalHost().getHostName();

		  Configuration configuration = new Configuration();

		  if (!computername.equals("SRVOUROSGO"))
			  computername = "SRVOUROHOM";

		  configuration.setProperty("hibernate.connection.url", "jdbc:jtds:sqlserver://" + computername + ":1433;DatabaseName=sgobd");

		  System.out.println("Configuração Connection Hibernate: " + configuration.getProperty("hibernate.connection.url"));

		  configuration.configure();

		  this.factory = configuration.buildSessionFactory();

		  }catch (Exception e){
			  System.out.println("Exception caught ="+e.getMessage());
		  }
	}

	@Override
	public SessionFactory getInstance() {
		return this.factory;
	}
	
	@PreDestroy
	public void fecha() {
		this.factory.close();
	}
}
