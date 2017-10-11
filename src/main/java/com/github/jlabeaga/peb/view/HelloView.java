package com.github.jlabeaga.peb.view;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.service.CompanyService;
import com.github.jlabeaga.peb.ui.AdminUI;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=HelloView.NAME, ui=PebUI.class)
public class HelloView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "HelloView";

	public static final String WINDOW_TITLE = "Hello";
		
	private static final Logger log = LoggerFactory.getLogger(HelloView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private CompanyService companyService; 
	
//	private Grid<Company> grid;
//	private Button newButton;

	private Button helloButton;
	
	public HelloView() {
		log.debug("inside HelloView creator");
//		grid = new Grid<>(Company.class);
//		newButton = new Button("Nuevo", event -> newElement() );
		helloButton = new Button("Hello", event -> Notification.show("Hello") );
	}
	
	private void layout() {
		addComponent(helloButton);
//		addComponent(newButton);
//		grid.setColumns();
//		grid.addColumn(Company::getName).setCaption("Nombre");
//		grid.addColumn(Company::getNif).setCaption("NIF/CIF");
//		grid.addColumn(company->company.isEnabled()?"Activo":"Inactivo").setCaption("Estado");
//		grid.addComponentColumn(company -> new Button("Editar", event -> edit(company)));
//		grid.addComponentColumn(company -> new Button("Duplicar", event -> duplicate(company)));
//		grid.addComponentColumn(company -> new Button("Borrar", event -> delete(company)));
//		grid.setSizeFull();
//		addComponent(grid);
	}
	

	
	private void populate() {
		log.debug("inside HelloView.populate()");
//		grid.setItems( companyService.findAll() );
	}
	
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("HelloView.enter()");
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
//		Notification.show(this.NAME);
//		removeAllComponents();
		layout();
		populate();
		setSizeFull();
		Notification.show("Inside ENTER method");
	}

}
