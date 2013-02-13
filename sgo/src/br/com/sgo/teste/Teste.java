package br.com.sgo.teste;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

public class Teste {
	
	public static void main(String[] args){

		Empresa a1;
		Organizacao o1 = new Organizacao();

		Configuration configuration = new Configuration();
		configuration.configure();
		
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();

		EmpresaDao empresaDao = new EmpresaDao(session);
		
		a1 = empresaDao.load(1L);

		o1.setNome("OUROCRED GRU");
		o1.setDescricao("Ourocred Matriz Guarulhos");
		o1.setEmpresa(a1);

		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(o1);
		tx.commit();

	}

}
