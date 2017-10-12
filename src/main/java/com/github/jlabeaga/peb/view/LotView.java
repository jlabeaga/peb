package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=LotView.NAME, ui=PebUI.class)
public class LotView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "LotView";
	
	public static final String WINDOW_TITLE = "Lotes";

	private static final Logger log = LoggerFactory.getLogger(LotView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 


	public void enter(ViewChangeEvent event) {
		log.debug("LotView.enter()");
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
		//layout();
		//populate();
		setSizeFull();
		
	}

}
