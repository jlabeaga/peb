package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.UserDTO;
import com.github.jlabeaga.peb.repository.QueryService;
import com.github.jlabeaga.peb.service.UserService;
import com.github.jlabeaga.peb.ui.PebUI;
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
	private QueryService queryService; 
	
	@Autowired
	private UserService userService; 
	
	private Grid<UserDTO> grid;
	private Button newButton;
	
	public UserView() {
		log.debug("inside UserView creator");
		grid = new Grid<>(UserDTO.class);
		newButton = new Button("Nuevo", event -> newElement() );
	}

	private void layout() {
		addComponent(newButton);
		grid.setColumns();
		grid.addColumn(UserDTO::getNickname).setCaption("Nombre");
		grid.addColumn(UserDTO::getEmail).setCaption("Email");
		grid.addColumn(UserDTO::getPhone).setCaption("Teléfono");
		grid.addColumn(userDTO->userDTO.getStatus()).setCaption("Estado");
		grid.addColumn(userDTO->userDTO.getCompanyName()).setCaption("Productor");
		grid.addComponentColumn(userDTO -> new Button("Editar", event -> edit(userDTO.getId())));
		grid.addComponentColumn(userDTO -> new Button("Duplicar", event -> duplicate(userDTO.getId())));
		grid.addComponentColumn(userDTO -> new Button("Borrar", event -> delete(userDTO.getId())));
		grid.setSizeFull();
		addComponent(grid);
	}
	
	private void delete(Long id) {
		userService.delete(id);
		Notification.show("Elemento eliminado");
		populate();
	}
	
	private void edit(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(UserDetailView.NAME, NavigationOperation.EDIT, id) );
	}
	
	private void newElement() {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(UserDetailView.NAME, NavigationOperation.NEW, null) );
	}
	
	private void duplicate(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(UserDetailView.NAME, NavigationOperation.DUPLICATE, id) );
	}
	
	private void populate() {
		log.debug("inside UserView.populate()");
		if( userService == null ) return;
//		grid.setItems( userService.findAll() );
		grid.setItems( queryService.listUsers() );
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
