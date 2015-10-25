package com.bo.formularios.abm;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bo.principal.PanelDinamico;
import com.services.UsuarioServiceLocal;
import com.vaadin.ui.ComboBox;

public class FormAdminAV extends PanelDinamico{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ComboBox comboAVs;
	private UsuarioServiceLocal servicio;
	
	private void lookup() {
			InitialContext context = null;
			try {
				context = new InitialContext();					
				servicio = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
			} catch (NamingException e) {
				throw new EJBException(
						"It was not possible to get a reference to one of the required services",
						e);
			} finally {
				try {
					context.close();
				} catch (NamingException e) {
					
				}
			}
	
	}
	
	public FormAdminAV(){    
		lookup();
		
	}	
	
	
}
