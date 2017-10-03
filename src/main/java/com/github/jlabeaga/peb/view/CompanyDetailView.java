package com.github.jlabeaga.peb.view;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.service.CompanyService;
import com.github.jlabeaga.peb.service.UserService;
import com.github.jlabeaga.peb.ui.AdminUI;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.annotations.Title;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

@SpringView(name=CompanyDetailView.NAME, ui=PebUI.class)
public class CompanyDetailView extends FormLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "CompanyDetailView";
	
	public static final String WINDOW_TITLE = "Productor";

	private static final Logger log = LoggerFactory.getLogger(CompanyDetailView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private CompanyService companyService; 
	
	@Autowired
	private UserService userService; 
	
	private Company company;
	
	private TextField name = new TextField("Nombre:");
	private TextField nif = new TextField("NIF/CIF:");
	private TextField invoiceAddressLine1 = new TextField("Dirección de facturación (linea 1):");
	private TextField invoiceAddressLine2 = new TextField("Dirección de facturación (linea 2):");
	private TextField locationAddressLine1 = new TextField("Dirección de localización (linea 1):");
	private TextField locationAddressLine2 = new TextField("Dirección de localización (linea 2):");
	private CheckBox enabled = new CheckBox("Activo");
	private Grid<User> activeUsers = new Grid<>(User.class);
    
	private CssLayout buttons = new CssLayout();
	Button saveButton = new Button("Guardar");
	Button cancelButton = new Button("Cancelar");
	
	Binder<Company> binder = new Binder<>(Company.class);

	public CompanyDetailView() {
		log.debug("inside CompanyDetailView creator");
	}
	
	private void layout() {
		addComponents(name);
		addComponents(nif);
		addComponents(invoiceAddressLine1);
		addComponents(invoiceAddressLine2);
		addComponents(locationAddressLine1);
		addComponents(locationAddressLine2);
		addComponent(activeUsers);
		addComponents(enabled);
		
		name.setWidth("15em");
		nif.setWidth("10em");
		invoiceAddressLine1.setWidth("20em");
		invoiceAddressLine2.setWidth("20em");
		locationAddressLine1.setWidth("20em");
		locationAddressLine2.setWidth("20em");

		buttons.addComponents(saveButton, cancelButton);
		addComponents(buttons);
		saveButton.addClickListener(event -> save());
		cancelButton.addClickListener(event -> cancel());
		
		activeUsers.setColumns();
		activeUsers.addColumn(User::getNickname).setCaption("Nombre");
		activeUsers.addColumn(User::getEmail).setCaption("Email");
		activeUsers.addColumn(User::getPhone).setCaption("Teléfono");
		activeUsers.setHeightMode(HeightMode.ROW);

		binder.bindInstanceFields(this);
	}
	
	public void populate(Long id) {
		company = companyService.findOne(id);
		List<User> activeUserList = userService.findActiveUsersOfCompany(id);
		activeUsers.setItems(activeUserList);
		activeUsers.setHeightByRows(Math.max(1, activeUserList.size()));
	}
	
	public void newElement() {
		company = new Company();
	}
	
	public void duplicate(Long id) {
		populate(id);
		company.setNew();
	}
	
	private void save() {
		companyService.save(company);
		Notification.show("Elemento guardado");;
		back();
	}
	
	private void refresh() {
		log.debug("inside CompanyDetailView.refresh()");
		populate(company.getId());
	}
	
	private void cancel() {
		Notification.show("Cambios cancelados");;
		back();
	}
		
	private void back() {
		ViewState returnViewState = navigationStack.pop();
		log.debug("returning back to: " + returnViewState);
		navigationUtils.navigateTo( returnViewState );
	}
		
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
		log.debug("CompanyDetailView.enter()");
		log.debug("event.getViewName()="+event.getViewName());
		log.debug("event.getParameters()="+event.getParameters());
		log.debug("event.getParameterMap()="+event.getParameterMap());
		Notification.show(event.getParameters());
		layout();
		setSizeFull();

		NavigationOperation operation = navigationUtils.getNavigationOperation(event);
		Long id = navigationUtils.getId(event);
		switch( operation ) {
			case NEW: newElement(); break;
			case EDIT: populate(id); break;
			case DUPLICATE: duplicate(id); break;			
		}
		binder.setBean(company);

	}

}
