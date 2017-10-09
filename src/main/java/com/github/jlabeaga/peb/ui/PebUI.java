package com.github.jlabeaga.peb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.view.ErrorView;
import com.github.jlabeaga.peb.view.NavigationStack;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI(path="/ui")
@Theme("valo")
public class PebUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(PebUI.class);

	@Autowired
	private MenuFactory menuFactory;
	
	private MenuBar menu;
	
	private PebViewDisplay mainContent;

	Label hello;
	VerticalLayout root = new VerticalLayout();

    public PebUI( PebViewDisplay mainContent, SpringNavigator navigator) {
        this.mainContent = mainContent;
        navigator.setErrorView(ErrorView.class);
    }
	
	
	@Override
	protected void init(VaadinRequest request) {
		menu = new MenuBar();
		menu = menuFactory.buildHomeMenu(menu, getNavigator());
		menu = menuFactory.buildUserMenu(menu, getNavigator());
		menu = menuFactory.buildAdminMenu(menu, getNavigator());
		menu = menuFactory.buildOperatorMenu(menu, getNavigator());
		root.addComponent(menu);
		root.addComponent(mainContent);
		setContent(root);

	}

}
