package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.Pallet;
import com.github.jlabeaga.peb.model.Part;
import com.github.jlabeaga.peb.service.PalletService;
import com.github.jlabeaga.peb.service.PartService;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

@SpringView(name=PartDetailView.NAME, ui=PebUI.class)
public class PartDetailView extends FormLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "PartDetailView";
	
	public static final String WINDOW_TITLE = "Partida";

	private static final Logger log = LoggerFactory.getLogger(PartDetailView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private PalletService palletService; 
	
	@Autowired
	private PartService partService; 
	
	private Part part;

	private TextField lotCode = new TextField("Lote:");
	private TextField processingDate = new TextField("Fecha de procesado:");
	private TextField code = new TextField("Código:");
	private ComboBox<Part.Status> status = new ComboBox<>("Estado:");
	private TextField weight = new TextField("Peso:");
	private ComboBox<Pallet> pallet = new ComboBox<>("Palet:");
	
	private CssLayout buttons = new CssLayout();
	Button saveButton = new Button("Guardar");
	Button cancelButton = new Button("Cancelar");
	
	Binder<Part> binder = new Binder<>(Part.class);

	public PartDetailView() {
		log.debug("inside PartDetailView creator");
	}
	
	private void layout() {
		addComponent(lotCode);
		addComponent(processingDate);
		addComponent(code);
		addComponent(status);
		addComponent(weight);
		addComponent(pallet);
		
		lotCode.setWidth("15em");
		lotCode.setEnabled(false);
		
		processingDate.setWidth("15em");
		processingDate.setEnabled(false);
		
		code.setWidth("15em");
		
		status.setItems(Part.Status.values());
		status.setItemCaptionGenerator(Part.Status::getDescription);
		
		weight.setWidth("10em");
		
		pallet.setItems(palletService.findAll());
		pallet.setItemCaptionGenerator(pa->pa.getCode()+" "+(pa.getProposedCustomer()!=null?pa.getProposedCustomer():""));

		buttons.addComponents(saveButton, cancelButton);
		addComponents(buttons);
		saveButton.addClickListener(event -> save());
		cancelButton.addClickListener(event -> cancel());
		
		binder.bindInstanceFields(this);
		
	}
	
	public void populate(Long id) {
		part = partService.findOne(id);
	}
	
	private void save() {
		partService.save(part);
		Notification.show("Elemento guardado");;
		populate(part.getId());
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
		navigationStack.push( new ViewState(this.NAME, NavigationOperation.EDIT, part.getId()) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("PartDetailView.enter()");
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
		binder.setBean(part);

	}

}
