package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.User.Role;
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
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

@SpringView(name=UserDetailView.NAME, ui=PebUI.class)
public class UserDetailView extends FormLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "UserDetailView";

	public static final String WINDOW_TITLE = "Usuario";
	
	private static final Logger log = LoggerFactory.getLogger(UserDetailView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private UserService userService; 
	
	@Autowired
	private CompanyService companyService; 
	
	private User user;
	
	private TextField email = new TextField("Email:");
	private TextField nickname = new TextField("Nombre de pila:");
	private TextField firstname = new TextField("Nombre:");
	private TextField lastname = new TextField("Apellidos:");
	private TextField phone = new TextField("Telefono:");
	private ComboBox<User.Role> role = new ComboBox<>("Rol:");
	private ComboBox<Company> company = new ComboBox<>("Productor:");
	private ComboBox<User.Status> status = new ComboBox<>("Estado:");
    
	private CssLayout buttons = new CssLayout();
	Button saveButton = new Button("Guardar");
	Button cancelButton = new Button("Cancelar");
	
	Binder<User> binder = new Binder<>(User.class);

	public UserDetailView() {
		log.debug("inside CompanyDetailView creator");
	}
	
	private void layout() {
		addComponents(email, nickname, firstname, lastname, phone, role, company, status);
		
		role.setItems(User.Role.USER, User.Role.ADMIN, User.Role.OPERATOR);
		role.setItemCaptionGenerator(role -> role.getDescription());
		company.setItemCaptionGenerator(company -> company.getName());
		company.setItems(companyService.findAll());
		status.setItems(User.Status.values());
		status.setItemCaptionGenerator(status->status.getDescription());
		status.setWidth("15em");
		
		email.setWidth("20em");
		nickname.setWidth("10em");
		firstname.setWidth("10em");
		lastname.setWidth("20em");
		phone.setWidth("10em");
		company.setWidth("20em");

		buttons.addComponents(saveButton, cancelButton);
		addComponents(buttons);
		saveButton.addClickListener(event -> save());
		cancelButton.addClickListener(event -> cancel());
		
		binder.bindInstanceFields(this);
	}
	
	public void populate(Long id) {
		user = userService.findOne(id);
	}
	
	public void newElement() {
		user = new User();
	}
	
	public void duplicate(Long id) {
		populate(id);
		user.setNew();
	}
	
	private void save() {
		userService.save(user);
		Notification.show("Elemento guardado");;
		back();
	}
	
	private void refresh() {
		log.debug("inside UserDetailView.refresh()");
		populate(user.getId());
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
		log.debug("UserDetailView.enter()");
		log.debug("event.getViewName()="+event.getViewName());
		log.debug("event.getParameters()="+event.getParameters());
		log.debug("event.getParameterMap()="+event.getParameterMap());
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
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
		binder.setBean(user);

	}

}
