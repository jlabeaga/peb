package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.service.UserService;
import com.github.jlabeaga.peb.ui.AdminUI;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=UserView.NAME, ui=PebUI.class)
public class UserView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "UserView";
	
	public static final String WINDOW_TITLE = "Usuarios";

	private static final Logger log = LoggerFactory.getLogger(UserView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private UserService userService; 
	
	private Grid<User> grid;
	private Button newButton;
	
	public UserView() {
		log.debug("inside UserView creator");
		grid = new Grid<>(User.class);
		newButton = new Button("Nuevo", event -> newElement() );
	}

	private void layout() {
		addComponent(newButton);
		grid.setColumns();
		grid.addColumn(User::getNickname).setCaption("Nombre");
		grid.addColumn(User::getEmail).setCaption("Email");
		grid.addColumn(User::getPhone).setCaption("Teléfono");
		grid.addColumn(user->user.getStatus().getDescription()).setCaption("Estado");
		grid.addColumn(user->user.getCompany().getName()).setCaption("Productor");
		grid.addComponentColumn(company -> new Button("Editar", event -> edit(company)));
		grid.addComponentColumn(company -> new Button("Duplicar", event -> duplicate(company)));
		grid.addComponentColumn(company -> new Button("Borrar", event -> delete(company)));
		grid.setSizeFull();
		addComponent(grid);
	}
	
	private void delete(User user) {
		userService.delete(user);
		Notification.show("Elemento eliminado");
		populate();
	}
	
	private void edit(User user) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(UserDetailView.NAME, NavigationOperation.EDIT, user.getId()) );
	}
	
	private void newElement() {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(UserDetailView.NAME, NavigationOperation.NEW, null) );
	}
	
	private void duplicate(User user) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(UserDetailView.NAME, NavigationOperation.DUPLICATE, user.getId()) );
	}
	
	private void populate() {
		log.debug("inside UserView.populate()");
		if( userService == null ) return;
		grid.setItems( userService.findAll() );
	}
	
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("UserView.enter()");
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
//		Notification.show(this.NAME);
//		removeAllComponents();
		layout();
		populate();
		setSizeFull();
	}

}
