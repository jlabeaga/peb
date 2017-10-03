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
		grid.addComponentColumn(company -> new Button("Editar", event -> edit(company)));
		grid.addComponentColumn(company -> new Button("Duplicar", event -> duplicate(company)));
		grid.addComponentColumn(company -> new Button("Borrar", event -> delete(company)));
		grid.setSizeFull();
		addComponent(grid);
	}
	
	private void delete(Company company) {
		companyService.delete(company);
		Notification.show("Elemento eliminado");
		populate();
	}
	
	private void edit(Company company) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(CompanyDetailView.NAME, NavigationOperation.EDIT, company.getId()) );
	}
	
	private void newElement() {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(CompanyDetailView.NAME, NavigationOperation.NEW, null) );
	}
	
	private void duplicate(Company company) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(CompanyDetailView.NAME, NavigationOperation.DUPLICATE, company.getId()) );
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
