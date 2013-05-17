package br.com.sgo.infra;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

public class Dao <T>{   

	private final Session session;
    private final Class<T> classe;
    private Transaction tx;

    protected Dao(Session session, Class<T> classe) {
        this.session = session;
        this.classe = classe;
    }

    public void adiciona(T o){

    	this.session.save(o);

    }

    public void adiciona(List<T> o){

    	try {
            for(T item : o){
                this.session.save(item);
            }
        }catch(Exception e){}

    }

    public void adicionar(List<T> o){
        for(T item : o){
            this.session.save(item);
        }
    }

    public void remove(T o){
        this.session.delete(o);
    }

    public void remove(List<T> o){
        try{
            for(T item : o){
                this.session.delete(item);
            }
        }catch(Exception e){}
    }

    public void atualiza(T o){
        try{
        	this.session.beginTransaction();
            this.session.merge(o);
        }
        catch(Exception e){
            try{
                this.session.saveOrUpdate(o);
            }catch(Exception e1){
                this.session.save(o);
            }
        }
    }

    public void atualiza(List<T> o){
        for(T item : o){
            try
            {
                this.session.merge(item);
            }
            catch(Exception e){
                try{
                    this.session.saveOrUpdate(item);
                }catch(Exception e1){
                    this.session.save(item);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T load(Long id){
        return (T) this.session.load(this.classe, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> listaTudo(){
        return this.session.createCriteria(this.classe).list();
    }
    
    @SuppressWarnings("unchecked")
	public List<T> listaTudo(String order, String campo) {
		if (order.equals("ASC"))
			return session.createCriteria(classe).addOrder(Order.asc(campo)).list();
		else if(order.equals("DESC"))
			return session.createCriteria(classe).addOrder(Order.desc(campo)).list();
		else
			return session.createCriteria(classe).list();
	}

    public Session getSession(){
        return this.session;
    }

    public void beginTransaction() {
    	this.tx = this.session.beginTransaction();
    }

    public void commit() {
    	this.tx.commit();
    	this.tx = null;
    }

    public void rollback() {
    	this.tx.rollback();
    	this.tx = null;
    }

    public boolean hasTransaction() {
    	return this.tx != null;
    }

    public void close() {	
    	this.session.close();
    }

    public void clear() {
    	this.session.clear();
    }
    
	public void refresh(T o) {
		session.refresh(o);
	}
}  
