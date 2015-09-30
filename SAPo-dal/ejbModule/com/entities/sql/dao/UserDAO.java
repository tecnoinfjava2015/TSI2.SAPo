package com.entities.sql.dao;



import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.sql.Usuario;




@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserDAO {
	
	@PersistenceContext(unitName="SAPo-dal")
	EntityManager em;
	
	
   /* public boolean existe(String nick,String password){
		
    	Persona p =em.find(Persona.class, nick);
		return (p.getPassword()==password);
    	//return true;
    	
	}*/

//
//public boolean existe(String nick){
//		
//    	Persona p =em.find(Persona.class, nick);
//		return !(p==null);
//    	//return true;
//    	
//	}
//	
//public void actualizar(Persona p){
//	em.merge(p);
//}

public Boolean baja(String nick){
	Query query =  em.createQuery("SELECT u FROM Usuario u WHERE u.nick=:nick ")
	.setParameter("nick", nick);
	Usuario usuario = (Usuario) query.getResultList().get(0);
	usuario.setEnabled(false);
	em.merge(usuario);
	return true;
}
	
//	public EncuestadoraAdmin getPersonaAdminNick(String nick){
//		return em.find(EncuestadoraAdmin.class, nick);
//	}
//	public Administrador getPersonaAdministradorNick(String nick){
//		return em.find(Administrador.class, nick);
//	}
//	
//		
//		//System.out.println("ENTRADA a getLogin boolean en dao");
//		
//     /*   String LOGIN = "select u from Usuario u where u.nick = :usuario  and u.password = :password";
//        Query query = em.createQuery(LOGIN);
//		query.setParameter("usuario", nick);
//		query.setParameter("password", password);
//		return ((query.getResultList() != null) && (query.getResultList().size() > 0)); */
//		
//		
//
//	
//	public EncuestadoraAdmin getLoginEA(String nick, String password) {
//		System.out.println("ENTRADA a getLogin USUARIO en dao");
//		
//		Query query =  em.createQuery("SELECT l FROM Usuario l WHERE l.nick=:nick and l.password=:password ")
//		.setParameter("nick", nick)
//		.setParameter("password", password);
//		//.getResultList().get(0);		
//		
//		 return (EncuestadoraAdmin) query.getResultList();
//		/*List results = query.getResultList();
//		Usuario foundEntity = null;
//		if(!results.isEmpty()){
//		// ignores multiple results
//		foundEntity = (Persona) results.get(0);
//		}
//		
//		 return foundEntity;*/
//		
//		
//		
//	}
//	
//	public Persona getLogin(String nick, String password) {
//		System.out.println("ENTRADA a getLogin USUARIO en dao");
//		
//		Query query =  em.createQuery("SELECT l FROM Persona l WHERE l.nick=:nick and l.password=:password ")
//		.setParameter("nick", nick)
//		.setParameter("password", password);
//		//.getResultList().get(0);		
//		
//		
//		List results = query.getResultList();
//		Persona foundEntity = null;
//		if(!results.isEmpty()){
//		// ignores multiple results
//			foundEntity = (Persona) results.get(0);
//		}	
//		 return foundEntity;
//
//		
//	}
//	
//	
	public Usuario buscarUsuario(String nick)
	{
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.nick = :nick");
		q.setParameter("nick", nick);
		return (Usuario) q.getSingleResult();
    }
	
	public Usuario buscar(String nombre)
	{
		return em.find(Usuario.class, nombre);		
	}
	
	public String insert(Usuario u)
	{
		em.persist(u);
		em.flush(); 
		return u.getNick();
	}

	public List<Usuario> listarUsuarios() {
		Query query =  em.createQuery("SELECT u FROM Usuario u ");
		List<Usuario>  retorno = query.getResultList();
		 return retorno;
	}


}
