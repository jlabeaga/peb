package com.github.jlabeaga.peb.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jlabeaga.peb.view.CompanyView;
import com.github.jlabeaga.peb.view.UserView;
import com.vaadin.navigator.Navigator;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;

public class AdminMenu extends MenuBar {

	public AdminMenu(SpringNavigator navigator) {
		
		MenuItem adminMenuItem = addItem("Admin", null);
		navigator.addView("", UserView.class);
		
		navigator.addView( UserView.NAME, new UserView() );
		MenuItem userMenuItem = adminMenuItem.addItem("Usuarios",
			(MenuItem selectedItem) -> { navigator.navigateTo(UserView.NAME); }
		);
		navigator.addView( CompanyView.NAME, CompanyView.class );
		MenuItem producerMenuItem = adminMenuItem.addItem("Productores",
			(MenuItem selectedItem) -> { navigator.navigateTo(CompanyView.NAME); }
		);
	}
	

}
