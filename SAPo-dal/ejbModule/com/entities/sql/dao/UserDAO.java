package com.entities.sql.dao;



import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entities.User;




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
//
//
//public void actualizarEncuestador(EncuestadoraUser c){
//em.merge(c);
//}
//
//
//public void baja(Persona p){
//	p.setEnabled(false);
//	em.merge(p);
//}
//
//
//public EncuestadoraUser getPersonaNick(String nick){
//	return em.find(EncuestadoraUser.class, nick);
//}
//
//
//
//	public Boolean login(String nick, String password) {
//	return em.createQuery("SELECT l FROM Persona l WHERE l.nick=:nick and l.password=:password ")
//		.setParameter("nick", nick)
//		.setParameter("password", password)
//		.getResultList().size()>= 1;
//		
//	}
//	
//	
//	
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
//	public Persona findByNick(String nick)
//	{
//		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.nick = :nick");
//		q.setParameter("nick", nick);
//		return (Persona) q.getSingleResult();
//    }
//	
//	
//	public Persona buscar(String nombre)
//	{
//		return em.find(Persona.class, nombre);
//		
//		
//	}
//	
//	public Persona buscarPersona(String nick)
//	{
//		return em.find(Persona.class, nick);
//		
//		
//	}
//	
//	
	public String insert(User u)
	{
		em.persist(u);
		em.flush(); 
		return u.getNick();
	}
//
//	public List<EncuestadoraUser> getEncuestadores() {
//		Query query =  em.createQuery("SELECT l FROM Persona l ");
//		List<EncuestadoraUser>  retorno = new ArrayList<EncuestadoraUser>();
//		List<Persona> lista  =(List<Persona>) query.getResultList();
//		for (Persona persona : lista) {
//			
//			if (persona.getTipo().equalsIgnoreCase("EncuestadoraUser"))
//				retorno.add((EncuestadoraUser) persona);
//		}
//		 return retorno;
//	
//	}
//	public List<EncuestadoraAdmin> getEncuestadoraAdmin() {
//		Query query =  em.createQuery("SELECT l FROM Persona l ");
//		List<EncuestadoraAdmin>  retorno = new ArrayList<EncuestadoraAdmin>();
//		List<Persona> lista  =(List<Persona>) query.getResultList();
//		for (Persona persona : lista) {
//			
//			if (persona.getTipo().equalsIgnoreCase("EncuestadoraAdmin"))
//				retorno.add((EncuestadoraAdmin) persona);
//		}
//		 return retorno;
//	
//	}
//	
//	public List<Administrador> getAdministradores() {
//		Query query =  em.createQuery("SELECT l FROM Persona l ");
//		List<Administrador>  retorno = new ArrayList<Administrador>();
//		List<Persona> lista  =(List<Persona>) query.getResultList();
//		for (Persona persona : lista) {
//			
//			if (persona.getTipo().equalsIgnoreCase("Administrador"))
//				retorno.add((Administrador) persona);
//		}
//		 return retorno;
//	}
//	
//	public List<EncuestadoraUser> getEncuestadoraUser() {
//		Query query =  em.createQuery("SELECT l FROM Persona l ");
//		List<EncuestadoraUser>  retorno = new ArrayList<EncuestadoraUser>();
//		List<Persona> lista  =(List<Persona>) query.getResultList();
//		for (Persona persona : lista) {
//			
//			if (persona.getTipo().equalsIgnoreCase("EncuestadoraUser"))
//				retorno.add((EncuestadoraUser) persona);
//		}
//		 return retorno;
//	}
//
//
//	public List<Encuesta> gethabilitadas(String nick) {
//
//		Query query =  em.createQuery("SELECT l FROM Persona l ");
//		List<EncuestadoraUser>  retorno = new ArrayList<EncuestadoraUser>();
//		List<Persona> lista  =(List<Persona>) query.getResultList();
//		for (Persona persona : lista) {
//			
//			if (persona.getTipo().equalsIgnoreCase("EncuestadoraUser")){
//				if (persona.getNick().equals(nick)){		
//					System.out.println("paso por aca");
//					return ((EncuestadoraUser) persona).getEncuestasHabilitadas();
//				}
//			}		
//		}
//		// return retorno;
//		return null;
//			 
//		//EncuestadoraUser u = (EncuestadoraUser) this.buscarPersona(nick);
//		//EncuestadoraUser u = (EncuestadoraUser) em.find(Persona.class, nick);
//		//return u.getEncuestasHabilitadas();
//	}
//
//
//	


}
