package com.entities.sql.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entities.sql.Document;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentDAO {
	@PersistenceContext(unitName="SAPo-dal")
	EntityManager em;
	
	public Document createDocument(Document documentAux){
		em.persist(documentAux);
		em.flush();
		return documentAux;
	}
	
	public Document getOneById(long documentId){
		Query query =  em.createQuery("SELECT d FROM Document d WHERE d.id=:documentId")
		.setParameter("documentId", documentId);
		Document document = (Document) query.getResultList().get(0);
		return document;
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> getByVirtualStorageId(long VSId){
		Query query =  em.createQuery("SELECT d FROM Document d WHERE d.virtualStorageId=:VSId")
		.setParameter("VSId", VSId);
		List<Document> documentList = ((List<Document>) query.getResultList());
		return documentList;
	}
	
	public Document getOneByNameAndVS(String name, long VSId){
		Query query =  em.createQuery("SELECT d FROM Document d WHERE d.name=:name AND d.virtualStorageId=:VSId")
		.setParameter("name", name).setParameter("VSId", VSId);
		Document document = ((Document) query.getResultList().get(0));
		return document;
	}
	
	public Document getOneByName(String name){
		Query query =  em.createQuery("SELECT d FROM Document d WHERE d.name=:name AND d.virtualStorageId is NULL")
		.setParameter("name", name);
		Document document = ((Document) query.getResultList().get(0));
		return document;
	}
	
	public boolean existDocumentNameInVS(String name, long VSId){
		Query query =  em.createQuery("SELECT d FROM Document d WHERE d.name=:name AND d.virtualStorageId=:VSId")
		.setParameter("name", name).setParameter("VSId", VSId);
		return !(query.getResultList().isEmpty());
	}
	
	public boolean existDocumentNameNoVS(String name){
		Query query =  em.createQuery("SELECT d FROM Document d WHERE d.name=:name AND d.virtualStorageId is NULL")
		.setParameter("name", name);
		return !(query.getResultList().isEmpty());
	}
}
