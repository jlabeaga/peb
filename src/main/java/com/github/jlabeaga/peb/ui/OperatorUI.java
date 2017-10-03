package com.github.jlabeaga.peb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jlabeaga.peb.view.CompanyView;
import com.github.jlabeaga.peb.view.ErrorView;
import com.github.jlabeaga.peb.view.InputView;
import com.github.jlabeaga.peb.view.LotView;
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

@SpringUI(path="/operator")
@Theme("valo")
public class OperatorUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(OperatorUI.class);

	private MenuBar menu;
	
	private PebViewDisplay mainContent;

	Label hello;
	VerticalLayout root = new VerticalLayout();

    public OperatorUI( PebViewDisplay mainContent, SpringNavigator navigator) {
        this.mainContent = mainContent;
        navigator.setErrorView(ErrorView.class);
    }
	
	@Override
	protected void init(VaadinRequest request) {
		log.debug("inside OperatorUI.init");
//		content = new Panel();
//		content = new AdminViewDisplay();
//		navigator = new SpringNavigator(this, content);
//		menu = new AdminMenu(navigator);
		menu = buildMenu(getNavigator());
		root.addComponent(menu);
		root.addComponent(new Label("This is the operator UI"));
		root.addComponent(mainContent);
		setContent(root);

	}
	
	public MenuBar buildMenu(Navigator navigator) {
		
		MenuBar menu = new MenuBar();
		
		MenuItem operatorMenuItem = menu.addItem("Operador", null);
		navigator.addView("", InputView.class);
		
		MenuItem inputMenuItem = operatorMenuItem.addItem("Entradas",
			(MenuItem selectedItem) -> { navigator.navigateTo(InputView.NAME); }
		);
//		navigator.addView( CompanyView.NAME, CompanyView.class );
		MenuItem lotMenuItem = operatorMenuItem.addItem("Lotes",
			(MenuItem selectedItem) -> { navigator.navigateTo(LotView.NAME); }
		);
		
		return menu;
	}


}
