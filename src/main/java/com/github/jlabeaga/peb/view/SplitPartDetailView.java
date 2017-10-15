package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.exception.PebBusinessException;
import com.github.jlabeaga.peb.model.Part;
import com.github.jlabeaga.peb.service.LotService;
import com.github.jlabeaga.peb.service.PartService;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

@SpringView(name=SplitPartDetailView.NAME, ui=PebUI.class)
public class SplitPartDetailView extends FormLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "SplitPartDetailView";
	
	public static final String WINDOW_TITLE = "Partida";

	private static final Logger log = LoggerFactory.getLogger(SplitPartDetailView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private LotService lotService; 
	
	@Autowired
	private PartService partService; 
	
	private Part originalPart;

	private TextField lotCode = new TextField("Lote:");
	private TextField code = new TextField("Código de la Partida de origen:");
	private TextField weight = new TextField("Peso de la Partida de origen:");
	private TextField newPartWeight = new TextField("Peso de la Partida nueva:");
	
	private CssLayout buttons = new CssLayout();
	Button splitButton = new Button("Partir");
	Button cancelButton = new Button("Cancelar");
	
	Binder<Part> binder = new Binder<>(Part.class);

	public SplitPartDetailView() {
		log.debug("inside PartDetailView creator");
	}
	
	private void layout() {
		addComponent(lotCode);
		addComponent(code);
		addComponent(weight);
		addComponent(newPartWeight);
		
		lotCode.setWidth("15em");
		lotCode.setEnabled(false);
		
		code.setWidth("15em");
		
		weight.setWidth("10em");
		
		newPartWeight.setWidth("10em");
		
		buttons.addComponents(splitButton, cancelButton);
		addComponents(buttons);
		splitButton.addClickListener(event -> split());
		cancelButton.addClickListener(event -> cancel());
		
		binder.bindInstanceFields(this);
		
	}
	
	public void populate(Long id) {
		originalPart = partService.findOne(id);
	}
	
	private void split() {
		try {
			lotService.splitPart(originalPart, Integer.parseInt(weight.getValue()), Integer.parseInt(newPartWeight.getValue()));
			Notification.show("Nueva partida generada");
			back();
		} catch (NumberFormatException e) {
			Notification.show("Error: no se entiende el peso", Type.ERROR_MESSAGE);
		} catch (PebBusinessException e) {
			Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
		}
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
		
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME, NavigationOperation.EDIT, originalPart.getId()) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("SplitPartDetailView.enter()");
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
			case EDIT: populate(id); break;
		}
		binder.setBean(originalPart);

	}

}
