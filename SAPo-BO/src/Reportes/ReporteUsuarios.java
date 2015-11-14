package Reportes;


import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class ReporteUsuarios extends UI {

  	@Override
	protected void init(VaadinRequest request) {
		setContent(new Label(
	            "<h1>Here's some dynamic content</h1>\n" +
	            "<p>This is to be printed.</p>",ContentMode.HTML));
	        
	        // Print automatically when the window opens
	        JavaScript.getCurrent().execute("setTimeout(function() {" + "  print(); self.close();}, 0);");
		
	}
}