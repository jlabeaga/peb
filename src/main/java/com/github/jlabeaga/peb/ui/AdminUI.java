package com.github.jlabeaga.peb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jlabeaga.peb.view.CompanyView;
import com.github.jlabeaga.peb.view.ErrorView;
import com.github.jlabeaga.peb.view.UserView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI(path="/admin")
@Theme("valo")
public class AdminUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(AdminUI.class);

	private MenuBar menu;
	
	private PebViewDisplay mainContent;

	Label hello;
	VerticalLayout root = new VerticalLayout();

    public AdminUI( PebViewDisplay mainContent, SpringNavigator navigator) {
        this.mainContent = mainContent;
        navigator.setErrorView(ErrorView.class);
    }
	
	@Override
	protected void init(VaadinRequest request) {
		log.debug("inside AdminUI.init");
//		content = new Panel();
//		content = new AdminViewDisplay();
//		navigator = new SpringNavigator(this, content);
//		menu = new AdminMenu(navigator);
		menu = buildMenu(getNavigator());
		root.addComponent(menu);
		root.addComponent(new Label("This is the admin UI"));
		root.addComponent(mainContent);
		setContent(root);

	}
	
	public MenuBar buildMenu(Navigator navigator) {
		
		MenuBar menu = new MenuBar();
		
		MenuItem adminMenuItem = menu.addItem("Admin", null);
		navigator.addView("", UserView.class);
		
//		navigator.addView( UserView.NAME, new UserView() );
		MenuItem userMenuItem = adminMenuItem.addItem("Usuarios",
			(MenuItem selectedItem) -> { navigator.navigateTo(UserView.NAME); }
		);
//		navigator.addView( CompanyView.NAME, CompanyView.class );
		MenuItem producerMenuItem = adminMenuItem.addItem("Productores",
			(MenuItem selectedItem) -> { navigator.navigateTo(CompanyView.NAME); }
		);
		
		return menu;
	}


}
