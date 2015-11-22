package com.bo.formularios.abm;




import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import utils.Encrypter;

import com.bo.principal.SapoBackofficeUI;
import com.entities.sql.Usuario;
import com.services.UsuarioServiceLocal;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public class Login extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private final VerticalLayout loginPanel = new VerticalLayout();
    private VerticalLayout errores = new VerticalLayout();
    private TextField username = new TextField();
    private PasswordField clave = new PasswordField();
    private Label error;
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
    
    public Login() {
    	lookup();
    	setSizeFull();

        Component loginForm = buildLoginForm();
        loginForm.setSizeUndefined();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
        

    }

    private Component buildLoginForm() {
        loginPanel.setSpacing(true);
        loginPanel.setMargin(true);
        final Panel panel = new Panel();
        Responsive.makeResponsive(panel);
        loginPanel.addComponent(buildLabels());
        errores.setVisible(false);
        loginPanel.addComponent(errores);
        loginPanel.addComponent(buildFields());
        panel.setContent(loginPanel);
        return panel;
    }

    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        username = new TextField("Usuario");
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        username.focus();

        clave = new PasswordField("Contraseña");
        clave.setIcon(FontAwesome.LOCK);
        clave.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Entrar");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);

        fields.addComponents(username, clave, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(final ClickEvent event) {
            
                doLogin();
            }
        });
        return fields;
    }

    protected void doLogin() {
        if (error != null) {
            errores.removeComponent(error);
        }
        username.setComponentError(null);
        clave.setComponentError(null);

        if (username.getValue() == null || username.getValue().trim().equalsIgnoreCase("")) {
            username.setComponentError(new UserError("Ingrese su usuario"));
            return;
        }
        if (clave.getValue() == null || clave.getValue().trim().equalsIgnoreCase("")) {
            clave.setComponentError(new UserError("Ingrese su clave"));
            return;
        }
    
        Usuario usuario;
        try {   
           	String md5 = new Encrypter().MD5(clave.getValue());
	    	usuario = servicio.getLogin(username.getValue(),md5);
            if (!(usuario == null)) {
                if (usuario.getType().equals("Administrador")){
                	
                		((SapoBackofficeUI) UI.getCurrent()).login(usuario);
                }
                else cargarError("Usuario no habilitado para este sistema");
          } else {
                cargarError("Usuario/Clave incorrectos o no esta registrado");
            }
        } catch (Exception e) {
              cargarError(e.getMessage());
        }
    }

    protected void cargarError(String message) {
        errores.removeAllComponents();
        error = new Label(message);
        error.addStyleName(ValoTheme.LABEL_FAILURE);
        errores.setVisible(true);
        errores.addComponent(error);
    }

    private Component buildLabels() {
        HorizontalLayout labels = new HorizontalLayout();
        labels.setSpacing(true);
        labels.setWidth(100, Unit.PERCENTAGE);
        ThemeResource resource = new ThemeResource("images/logo.jpg");
        Image image = new Image("", resource);
        labels.addComponent(image);
        Label title = new Label("SAPo");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        labels.addComponent(title);
        labels.setComponentAlignment(image, Alignment.TOP_RIGHT);
        labels.setComponentAlignment(title, Alignment.BOTTOM_LEFT); 
        return labels;
    }

}
