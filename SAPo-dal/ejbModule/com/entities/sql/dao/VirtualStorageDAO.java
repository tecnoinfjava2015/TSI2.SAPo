package com.entities.sql.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.entities.sql.Usuario;
import com.entities.sql.VirtualStorage;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class VirtualStorageDAO {	
	@PersistenceContext(unitName="SAPo-dal")
	private EntityManager em;
	
	@EJB
	private UserDAO udao;
	
	public String updateVS(VirtualStorage vs){
		try {
			em.merge(vs);
			return "200-Success-Virtual_Storage_Updated";
		}
		catch (Exception ex) {
			return "500-Error-" + ex.getMessage();
		}
	}

	public Boolean deleteVSByName(String name){
		Query query =  em.createQuery("SELECT v FROM VirtualStorage v WHERE v.nombre=:name")
		.setParameter("nombre", name);
		VirtualStorage virtualStorage = (VirtualStorage) query.getResultList().get(0);
		virtualStorage.setEnabled(false);
		em.merge(virtualStorage);
		return true;
	}
	
	
	public List<VirtualStorage> getVirtualStorageByOwner(int ownerId){
		TypedQuery<VirtualStorage> query =  
				em.createQuery("SELECT vs FROM VirtualStorage vs WHERE vs.owner.id = :ownerId", VirtualStorage.class)
				.setParameter("ownerId", ownerId);
		List<VirtualStorage> virtualStorages = query.getResultList();
		
		return virtualStorages;
	}
	
	public HashMap<String, List<VirtualStorage>> getMyVirtualStorages(int userId){
		//Query owned VSs
		TypedQuery<VirtualStorage> queryOwned =  
				em.createQuery("SELECT vs FROM VirtualStorage vs "
						+ "WHERE vs.owner.id = :ownerId "
						+ "AND vs.enabled = true", VirtualStorage.class)
				.setParameter("ownerId", userId);
		List<VirtualStorage> virtualStoragesOwned = queryOwned.getResultList();
		
		//Query following VSs
		TypedQuery<VirtualStorage> queryFollowing =  
				em.createQuery(
						"SELECT vs FROM VirtualStorage vs join vs.seguidores seg "
						+ "WHERE seg.id = :seguidorId AND vs.enabled = true",
						VirtualStorage.class)
				.setParameter("seguidorId", userId);
		List<VirtualStorage> virtualStoragesFollowing = queryFollowing.getResultList();
		
		HashMap<String, List<VirtualStorage>> result = new HashMap<String, List<VirtualStorage>>();
		result.put("owned", virtualStoragesOwned);
		result.put("following", virtualStoragesFollowing);

		return result;
		//return em.find(VirtualStorage.class, name);		
	}
	
	
	public VirtualStorage searchVSByName(String name){
		return em.find(VirtualStorage.class, name);		
	}
	
	public VirtualStorage buscarVSporID(int id){
		return em.find(VirtualStorage.class, id);		
	}
	
	public Boolean isBlocked(int id){
		return em.find(VirtualStorage.class, id).getBlocked();		
	}
	
	public void bloquear(int id){
		em.find(VirtualStorage.class, id).setBlocked(true);		
	}
	
	public void desbloquear(int id){
		em.find(VirtualStorage.class, id).setBlocked(false);		
	}
	
	public String createVS(VirtualStorage vs, int ownerId){
		try {
			Usuario owner = udao.buscarID(ownerId);
			if (owner != null){
				vs.setOwner(owner);
				vs.setCreatedDate(new Date());
				em.persist(vs);
				em.flush(); 
				return "VSCreado";
			}
			return "500-Error-no_existe_usuario";
		}
		catch (Exception ex) {
			return "500-Error-" + ex.getMessage();
		}
	}

	public List<VirtualStorage> getAllVS() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs ");
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}
	
	public List<VirtualStorage> getDisabledVS() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs WHERE vs.enabled=:enabled ")
		.setParameter("enabled", false);
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}

	public List<VirtualStorage> getEnabledVS() {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs WHERE vs.enabled=:enabled ")
		.setParameter("enabled", true);
		List<VirtualStorage>  retorno = query.getResultList();
		return retorno;
	}

	public String insertVS(VirtualStorage vs, int idCreador){
		Usuario creador = udao.buscarID(idCreador);
		if(creador==null){
			return "500-Error-Sin_Usuario";
		}
		if (searchVSByName(vs.getName()) == null){
			return "501-Error-Ya existe AV";
		}
		else{
			vs.setOwner(creador);
			em.persist(vs);
			em.flush(); 
			return vs.getName();
		}		
	}

	public int AVPorUsuario(String nick) {
		Query query =  em.createQuery("SELECT vs FROM VirtualStorage vs ");
		List<VirtualStorage>  retorno = query.getResultList();
		int total = 0;
		for(VirtualStorage vs : retorno){
			if(vs.getOwner().getNick().equals(nick)){
				total++;
			}
		}
		return total;
	}	
}