package com.bo.formularios.abm;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bo.principal.PanelDinamico;
import com.entities.sql.Usuario;

import com.services.UsuarioServiceLocal;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
//import com.vaadin.tapio.googlemaps.GoogleMap;
//import com.vaadin.tapio.googlemaps.client.LatLon;
//import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
//import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

//import diva.util.java2d.*;



public class FormUbicacionUsuarios extends PanelDinamico {
    private VerticalLayout UserComp;
    private HorizontalLayout rootLayout,mapComp;
    private ComboBox usuariosCombo;
    private Button recargarMapa;
    private Label  texto2;
    private List<Usuario>  listaUsuarios;
//    protected GoogleMap googleMap;
//    protected LatLon position;
//   
//    protected ArrayList<LatLon> coordenadasZonaLatLon = new ArrayList<LatLon>();
//    protected GoogleMapPolygon poligonoZona = new GoogleMapPolygon();
    private UsuarioServiceLocal servicioU;
   
    private final String apiKey = "";
    protected String nombreUsuario = "";
    
    private void lookup() {
		InitialContext context = null;
		try {
			context = new InitialContext();					
			servicioU = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
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
    
    
    public FormUbicacionUsuarios(){
    	lookup();
        this.addStyleName("outlined");
        this.setSizeFull();
        listaUsuarios = servicioU.getUsuarios();
        UserComp = new VerticalLayout();   
        mapComp = new HorizontalLayout();
        
        rootLayout = new HorizontalLayout(UserComp,mapComp);
        rootLayout.setSizeFull();
        
        UserComp = generarEncuestadoresComp();
        mapComp = generarMapComp();

        rootLayout.addComponent(UserComp);
        rootLayout.addComponent(mapComp);

        rootLayout.setExpandRatio(UserComp, 1);
        rootLayout.setExpandRatio(mapComp, 2);
    
        this.addComponent(rootLayout);
        
        
    }
    
//    protected void reiniciarMapa(GoogleMap googleMap){
//         googleMap.setCenter(new LatLon(-34.860963, -56.15122));
//         googleMap.setZoom(12);
//         googleMap.clearMarkers();
//         for(GoogleMapMarker m : googleMap.getMarkers()){
//            googleMap.removeMarker(m);
//        }
//               
//    } 
       
    
    
    private VerticalLayout generarEncuestadoresComp(){
    	VerticalLayout encComp = new VerticalLayout();
    	      
        texto2 = new Label("Seleccione un usuario para ver su última ubicación");
        texto2.setContentMode(ContentMode.HTML);
        texto2.setWidth("95%");
        encComp.addComponent(texto2);
  
        usuariosCombo = new ComboBox();
        usuariosCombo.setFilteringMode(FilteringMode.CONTAINS);
        usuariosCombo.setWidth("90%");
        usuariosCombo.setNullSelectionAllowed(false);
        usuariosCombo.setTextInputAllowed(false);
        usuariosCombo.addItem("Todos");
        
        for (Usuario usuario : listaUsuarios) {
            usuariosCombo.addItem(usuario.getNick());
        }
        encComp.addComponent(usuariosCombo);

        recargarMapa = new Button("Reiniciar Mapa");
        recargarMapa.addStyleName(ValoTheme.BUTTON_TINY);
        recargarMapa.setWidth("50%");
        encComp.addComponent(recargarMapa);

        encComp.setSizeFull();
        encComp.setMargin(true);
        
        encComp.setComponentAlignment(texto2, Alignment.MIDDLE_CENTER);
        encComp.setComponentAlignment(usuariosCombo, Alignment.TOP_CENTER);
        encComp.setComponentAlignment(recargarMapa, Alignment.BOTTOM_RIGHT);

        
//        usuariosCombo.addValueChangeListener(new ValueChangeListener() {
//            @Override
//            public void valueChange(ValueChangeEvent event) {
//                reiniciarMapa(googleMap);
//                
//               for(GoogleMapMarker m : googleMap.getMarkers()){
//                   googleMap.removeMarker(m);
//               }
//               googleMap.clearMarkers();
//               String valorEncuestadorCombo = usuariosCombo.getValue().toString();
//               if (!valorEncuestadorCombo.contains("Todos")){
//            	   double latitud = 0;
//	               double longitud = 0;
//	               Usuario user = servicioU.getUsuario(usuariosCombo.getValue().toString());
//	               nombreUsuario = user.getNick();
//	               boolean ubicacionOK = false;
//	               if (user.getLatitud() != 0){
//	                   latitud = user.getLatitud();
//	                   if (user.getLongitud()!= 0){
//	                       longitud = user.getLongitud();
//	                       System.out.println("Ubicación "+ nombreUsuario + ": "+ latitud + ", " + longitud);
//	                       ubicacionOK = true;
//	                   }
//	               }
//	               else{
//	                   System.out.println("Ubicación "+ nombreUsuario + " Error, sin Ubicación");
//	               }
//	                       
//	               if(ubicacionOK){
//	                   reiniciarMapa(googleMap);
//	                   GoogleMapMarker encuestadorMarker = new GoogleMapMarker();
//	                   
//	                   LatLon ubiEnc = new LatLon(latitud, longitud);
//	                   encuestadorMarker.setCaption("Usuario: "+nombreUsuario);
//	                   encuestadorMarker.setPosition(ubiEnc);
//	                   encuestadorMarker.setDraggable(false);
//	                   googleMap.addMarker(encuestadorMarker);
//	                   googleMap.setCenter(ubiEnc);
//	                   googleMap.setZoom(15);      
//	                   
//	               }
//	               else {
//	                   googleMap.removePolygonOverlay(poligonoZona);
//	                   Notification notif = new Notification("No hay datos de ubicación \n para este usuario");
//	                   notif.setDelayMsec(2000);
//	                   notif.show(Page.getCurrent());   
//	               }
//               }
//               else{
//            	   for (Usuario user : listaUsuarios) {
//            		   double latitud = 0;
//    	               double longitud = 0;
//            		   nombreUsuario = user.getNick();
//    	               boolean ubicacionOK = false;
//    	               if (user.getLatitud() != 0){
//    	                   latitud = user.getLatitud();
//    	                   if (user.getLongitud()!= 0){
//    	                       longitud = user.getLongitud();
//    	                       ubicacionOK = true;
//    	                   }
//    	               }
//    	                	                       
//    	               if(ubicacionOK){
//    	                   GoogleMapMarker encuestadorMarker = new GoogleMapMarker();
//    	                   
//    	                   LatLon ubiEnc = new LatLon(latitud, longitud);
//    	                   encuestadorMarker.setCaption("Usuario: "+nombreUsuario);
//    	                   encuestadorMarker.setPosition(ubiEnc);
//    	                   encuestadorMarker.setDraggable(false);
//    	                   googleMap.addMarker(encuestadorMarker);  
//    	               }	   
//                   }
//               }
//            }
//           });        
//        
        return encComp;
    }
    
    
    private HorizontalLayout generarMapComp(){
        HorizontalLayout mapaComponente = new HorizontalLayout(); 
//    	googleMap = new GoogleMap(null, null, null);
//        googleMap.setSizeFull();
//        googleMap.setMinZoom(6);
//        reiniciarMapa(googleMap);
//        
//        mapaComponente.addComponent(googleMap);
//        mapaComponente.setExpandRatio(googleMap, 1.0f);
        mapaComponente.setSizeFull();
        return mapaComponente;
    }
    
}