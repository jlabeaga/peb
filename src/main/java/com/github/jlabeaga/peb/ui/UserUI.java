package com.github.jlabeaga.peb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI(path="/user")
@Theme("valo")
public class UserUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(UserUI.class);

	private MenuBar menu;
	
	private Navigator navigator;
	
	private Panel content;

	Label hello;
	VerticalLayout root = new VerticalLayout();
	
	@Override
	protected void init(VaadinRequest request) {
		content = new Panel();
		menu = new MenuBar();
		navigator = new Navigator(this, content);
//		menu = MenuFactory.buildUserMenu(menu, navigator);
		root.addComponent(menu);
		root.addComponent(new Label("This is the user UI"));
		setContent(root);

	}

}
