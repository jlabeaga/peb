package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.service.CompanyService;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=CompanyView.NAME, ui=PebUI.class)
public class CompanyView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "CompanyView";

	public static final String WINDOW_TITLE = "Productores";
		
	private static final Logger log = LoggerFactory.getLogger(CompanyView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private CompanyService companyService; 
	
	private Grid<Company> grid;
	private Button newButton;
	
	public CompanyView() {
		log.debug("inside CompanyView creator");
		grid = new Grid<>(Company.class);
		newButton = new Button("Nuevo", event -> newElement() );
	}
	
	private void layout() {
		addComponent(newButton);
		grid.setColumns();
		grid.addColumn(Company::getName).setCaption("Nombre");
		grid.addColumn(Company::getNif).setCaption("NIF/CIF");
		grid.addColumn(company->company.isEnabled()?"Activo":"Inactivo").setCaption("Estado");
		grid.addComponentColumn(company -> new Button("Editar", event -> edit(company.getId())));
		grid.addComponentColumn(company -> new Button("Duplicar", event -> duplicate(company.getId())));
		grid.addComponentColumn(company -> new Button("Borrar", event -> delete(company.getId())));
		grid.setSizeFull();
		addComponent(grid);
	}
	
	private void delete(Long id) {
		companyService.delete(id);
		Notification.show("Elemento eliminado");
		populate();
	}
	
	private void edit(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(CompanyDetailView.NAME, NavigationOperation.EDIT, id) );
	}
	
	private void newElement() {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(CompanyDetailView.NAME, NavigationOperation.NEW, null) );
	}
	
	private void duplicate(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(CompanyDetailView.NAME, NavigationOperation.DUPLICATE, id) );
	}
	
	private void populate() {
		log.debug("inside CompanyView.populate()");
		grid.setItems( companyService.findAll() );
	}
	
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("CompanyView.enter()");
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
//		Notification.show(this.NAME);
//		removeAllComponents();
		layout();
		populate();
		setSizeFull();
	}

}
