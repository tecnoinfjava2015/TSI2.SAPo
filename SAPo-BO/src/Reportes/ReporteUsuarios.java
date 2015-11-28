package Reportes;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import utils.Encrypter;

import com.bl.ProductBL;
//import com.bl.ProductMovementBL;
import com.bo.principal.PanelDinamico;
import com.bo.principal.PiePagina;
import com.entities.sql.ProductMovement;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.shared.ui.label.ContentMode;
//import com.vaadin.ui.JavaScript;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.UI;
import com.entities.sql.Usuario;
import com.services.ProductMovementServiceLocal;
import com.services.UsuarioServiceLocal;
import com.services.interfaces.IProductBL;
import com.utilities.IntervalDates;
//import com.services.interfaces.IProductMovementBL;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.themes.ValoTheme;

public class ReporteUsuarios extends PanelDinamico{
	

private static final long serialVersionUID = 1L;
private List<Usuario>  listaUsuarios;
private List<ProductMovement>  listaMovimientos;
private VerticalLayout panelIzquierda, panelDerecha;
private HorizontalLayout rootLayout;
private Table tableUsuario, tableMovimientos; 
private VerticalLayout mainLayout = new VerticalLayout();
private UsuarioServiceLocal servicio;

private static final long serialVersionUID1 = 1L;


private ProductMovementServiceLocal servicioPM;
//private IProductMovementBL servicioProductoMov = new ProductMovementBL();

private void lookup() {
	InitialContext context = null;
	try {
		context = new InitialContext();					
		servicio = (UsuarioServiceLocal) context.lookup("java:app/SAPo-BO/UsuarioServiceBean");
		servicioPM =(ProductMovementServiceLocal) context.lookup("java:app/SAPo-BO/ProductMovementServiceBean");
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
	
		public ReporteUsuarios(){    
			lookup();
			this.addStyleName("outlined");
		    this.setSizeFull();
		    listaUsuarios = servicio.getUsuariosHabilitados();
		    panelIzquierda = new VerticalLayout();
		    panelDerecha = new VerticalLayout();
		    rootLayout = new HorizontalLayout(panelIzquierda,panelDerecha);
		    rootLayout.setSizeFull();
		    panelIzquierda = generarPanelIzquierda();
		    panelDerecha = generarPanelDerecha();
		    rootLayout.addComponent(panelIzquierda);
		    rootLayout.addComponent(panelDerecha);
		    rootLayout.setExpandRatio(panelIzquierda, 1);
		    rootLayout.setExpandRatio(panelDerecha, 2);
		    this.addComponent(rootLayout);
		    
			tableUsuario.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					if(event.getProperty().getValue() != null){
						actualizarTabla();
					}
				}
			});
			
		      
		}
		
		@SuppressWarnings("deprecation")
		public VerticalLayout generarPanelDerecha() {
			VerticalLayout panDer = new VerticalLayout();
		     tableMovimientos = new Table("Movimientos Usuario");
		     tableMovimientos.addContainerProperty("Producto", String.class, null);
		     listaMovimientos = servicioPM.getMovementByUser(listaUsuarios.get(((int)tableUsuario.getValue()-1)).getId());
		     tableMovimientos.addContainerProperty("Fecha", String.class, null);
		     tableMovimientos.addContainerProperty("C�digo de Barras", String.class, null);
		     tableMovimientos.addContainerProperty("Usuario", String.class, null);
		     tableMovimientos.addContainerProperty("Stock al movimiento", Long.class, null);
		     tableMovimientos.addContainerProperty("Cambio de Stock", Long.class, null);
		     tableMovimientos.addContainerProperty("Precio Anterior", Double.class, null);
		     tableMovimientos.addContainerProperty("Precio Actual", Double.class, null);
			 
		     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    for (ProductMovement mov : listaMovimientos) {
			    	IntervalDates iDates = new IntervalDates();
			    	iDates.setDate1(servicioPM.getoFechaCreadoProduto(mov.getVirtualStorageId(), mov.getBarCode()));
			    	iDates.setDate2(mov.getDateMov());		    	
			    	Object newItemId = tableMovimientos.addItem();
			    	Item row1 = tableMovimientos.getItem(newItemId);
			    	row1.getItemProperty("Producto").setValue(mov.getProductName());
			    	String date = sdf.format(mov.getDateMov().getTime());
			    	row1.getItemProperty("Fecha").setValue(date);
			    	row1.getItemProperty("C�digo de Barras").setValue(mov.getBarCode());
			    	row1.getItemProperty("Usuario").setValue(servicio.getUsuario(mov.getUserID()).getNick());
			    	row1.getItemProperty("Stock al movimiento").setValue(servicioPM.getovimientosStockProduto(mov.getVirtualStorageId(), mov.getBarCode(),iDates));
			    	row1.getItemProperty("Cambio de Stock").setValue(mov.getStock());
			    	row1.getItemProperty("Precio Anterior").setValue(mov.getInitialPrice());
			    	row1.getItemProperty("Precio Actual").setValue(mov.getFinalPrice());
			    }
			     
			        //para ordenar tabla por nombre
			    tableMovimientos.addColumnResizeListener(new Table.ColumnResizeListener(){
			    	  public void columnResize(ColumnResizeEvent event) {
			    	        int width = event.getCurrentWidth();
			    	        String column = (String) event.getPropertyId();
			    	        tableMovimientos.setColumnFooter(column, String.valueOf(width) + "Producto");
			    	  }
				 }); 
			    tableMovimientos.setImmediate(true);
			    tableMovimientos.setSelectable(true);
			    tableMovimientos.select(1);
			    tableMovimientos.setNullSelectionItemId(false);
			    tableMovimientos.setPageLength(tableMovimientos.size());
				tableMovimientos.setWidth("80%");
			    panDer.setMargin(true);
			    panDer.addComponent(tableMovimientos);
			    panDer.setComponentAlignment(tableMovimientos, Alignment.TOP_CENTER);
		     
		     return panDer;
		}	
		
		public void actualizarTabla(){
			
			VerticalLayout nuevaTabla = new VerticalLayout();
		    nuevaTabla = generarPanelDerecha();
		    rootLayout.replaceComponent(panelDerecha, nuevaTabla);
		    panelDerecha = nuevaTabla;
		    
		    tableUsuario.addValueChangeListener(new Property.ValueChangeListener() {
				@Override
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					if(event.getProperty().getValue() != null){
						
					}
				}
			});
		    
		}
		
		private VerticalLayout generarPanelIzquierda(){
			VerticalLayout panIzq = new VerticalLayout();
			tableUsuario = new Table("Usuarios cargados");
		    tableUsuario.addContainerProperty("Nombre", String.class, null);
		    tableUsuario.addContainerProperty("Tipo", String.class, null);
		    tableUsuario.addContainerProperty("Mail", String.class, null);
		    ArrayList<String> nombreEncuestador = new ArrayList<String>();
		
		    for (Usuario usuario : listaUsuarios) {
		    	Object newItemId = tableUsuario.addItem();
		    	Item row1 = tableUsuario.getItem(newItemId);
		    	row1.getItemProperty("Nombre").setValue(usuario.getNick());
		    	row1.getItemProperty("Tipo").setValue(usuario.getType());
		    	row1.getItemProperty("Mail").setValue(usuario.getMail());
		    }
		     
		        //para ordenar tabla por nombre
			 tableUsuario.addColumnResizeListener(new Table.ColumnResizeListener(){
		    	  public void columnResize(ColumnResizeEvent event) {
		    	        int width = event.getCurrentWidth();
		    	        String column = (String) event.getPropertyId();
		    	        tableUsuario.setColumnFooter(column, String.valueOf(width) + "Nombre");
		    	  }
			 }); 
			 tableUsuario.setImmediate(true);
			 tableUsuario.setSelectable(true);
			 tableUsuario.select(1);
			 tableUsuario.setNullSelectionItemId(false);
			 tableUsuario.setPageLength(tableUsuario.size());
			 tableUsuario.setWidth("80%");
			 panIzq.setMargin(true);
			 panIzq.addComponent(tableUsuario);
			 panIzq.setComponentAlignment(tableUsuario, Alignment.MIDDLE_CENTER);
		     panIzq.setSizeFull();
		     panIzq.setMargin(true);
		     MarginInfo mi = new MarginInfo(false, true, false, true);
		     panIzq.setMargin(mi);
		     return panIzq;
		}
		
	//}

		
}
