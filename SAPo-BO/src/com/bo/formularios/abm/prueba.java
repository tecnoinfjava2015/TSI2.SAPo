package com.bo.formularios.abm;

import com.bo.principal.PanelDinamico;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Tree;

public class prueba extends PanelDinamico{
	public prueba(){
		this.addComponent(createChartsTree());
		this.setSizeFull();
	}
	
	private Tree createChartsTree(){
		  final Tree tree=new Tree("Chart Type");
		  //tree.setContainerDataSource(getContainer());
		  tree.setImmediate(true);
		  tree.setItemCaptionPropertyId(1);
		  tree.setItemCaptionMode(Tree.ITEM_CAPTION_MODE_PROPERTY);
		  tree.setNullSelectionAllowed(false);
		  for (  Object id : tree.rootItemIds()) {
		    tree.expandItemsRecursively(id);
		  }
		  tree.addListener(new Tree.ValueChangeListener(){
		    public void valueChange1(    ValueChangeEvent event){
		      try {
		        Object selectedId=event.getProperty().getValue();
		        if (tree.getParent(selectedId) != null) {
		          Object parentId=tree.getParent(selectedId);
		          String demoSeriesTypeName=(String)tree.getContainerProperty(parentId,1).getValue();
		          String seriesInstanceName=(String)tree.getContainerProperty(selectedId,1).getValue();
		          System.out.println("parent : " + demoSeriesTypeName + ", selected : "+ seriesInstanceName);
		          //showChart(demoSeriesTypeName,seriesInstanceName);
		        }
		 else {
		          String demoSeriesTypeName=(String)tree.getContainerProperty(selectedId,1).getValue();
		          System.out.println("Selected " + demoSeriesTypeName);
		         // showChartInstancesForSeriesType(demoSeriesTypeName);
		        }
		      }
		 catch (      Exception e) {
		        e.printStackTrace();
		      }
		    }

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				
			}
		  }
		);
		  return tree;
		}

	
}
