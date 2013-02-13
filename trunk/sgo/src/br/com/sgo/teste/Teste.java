package br.com.sgo.teste;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import br.com.sgo.modelo.Empresa;

public class Teste {
	
	public static void main(String[] args){
		
		Empresa a1 = new Empresa();

		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		
		a1.setNome("GRUPOOUROCRED");
		a1.setDescricao("Grupo Ourocred Assessoria Financeira");

		Transaction tx = session.beginTransaction();
		session.update(a1);
		
		tx.commit();

	}

}
