package com.github.jlabeaga.peb.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jlabeaga.peb.view.CompanyView;
import com.github.jlabeaga.peb.view.HelloView;
import com.github.jlabeaga.peb.view.InputView;
import com.github.jlabeaga.peb.view.LotView;
import com.github.jlabeaga.peb.view.NavigationStack;
import com.github.jlabeaga.peb.view.UserView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.EmptyView;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;

@Component
public class MenuFactory {

	@Autowired
	private NavigationStack navigationStack; 

	public MenuBar buildHomeMenu(MenuBar menu, Navigator navigator) {
		MenuItem userMenuItem = menu.addItem("Home", 
				(MenuItem selectedItem) -> { navigationStack.reset(); navigator.navigateTo(""); }
		);
		return menu;
	}

	public MenuBar buildUserMenu(MenuBar menu, Navigator navigator) {
		MenuItem userMenuItem = menu.addItem("Usuario", null);
		navigator.addView("", EmptyView.class);

		MenuItem helloMenuItem = userMenuItem.addItem("Hello",
				(MenuItem selectedItem) -> { navigationStack.reset(); navigator.navigateTo(HelloView.NAME); }
		);

		return menu;
	}

	public MenuBar buildAdminMenu(MenuBar menu, Navigator navigator) {
		
		MenuItem adminMenuItem = menu.addItem("Admin", null);
		navigator.addView("", EmptyView.class);
		
//		navigator.addView( UserView.NAME, new UserView() );
		MenuItem userMenuItem = adminMenuItem.addItem("Usuarios",
			(MenuItem selectedItem) -> { navigationStack.reset(); navigator.navigateTo(UserView.NAME); }
		);
//		navigator.addView( CompanyView.NAME, CompanyView.class );
		MenuItem producerMenuItem = adminMenuItem.addItem("Productores",
			(MenuItem selectedItem) -> { navigationStack.reset(); navigator.navigateTo(CompanyView.NAME); }
		);
		
		return menu;
	}
	
	public MenuBar buildOperatorMenu(MenuBar menu, Navigator navigator) {
		
		MenuItem operatorMenuItem = menu.addItem("Operador", null);
		navigator.addView("", EmptyView.class);
		
		MenuItem inputMenuItem = operatorMenuItem.addItem("Entradas",
			(MenuItem selectedItem) -> { navigationStack.reset(); navigator.navigateTo(InputView.NAME); }
		);
//		navigator.addView( CompanyView.NAME, CompanyView.class );
		MenuItem lotMenuItem = operatorMenuItem.addItem("Lotes",
			(MenuItem selectedItem) -> { navigationStack.reset(); navigator.navigateTo(LotView.NAME); }
		);
		
		return menu;
	}
	
	
	
}
