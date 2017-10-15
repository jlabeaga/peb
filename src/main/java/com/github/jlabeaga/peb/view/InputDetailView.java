package com.github.jlabeaga.peb.view;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.exception.PebBusinessException;
import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.Lot;
import com.github.jlabeaga.peb.model.User;
import com.github.jlabeaga.peb.repository.InputRepository;
import com.github.jlabeaga.peb.service.CompanyService;
import com.github.jlabeaga.peb.service.InputService;
import com.github.jlabeaga.peb.service.LotService;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=InputDetailView.NAME, ui=PebUI.class)
public class InputDetailView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "InputDetailView";
	
	public static final String WINDOW_TITLE = "Entrada";

	private static final Logger log = LoggerFactory.getLogger(InputDetailView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private InputService inputService; 
	
	@Autowired
	private LotService lotService; 
	
	@Autowired
	private CompanyService companyService; 
	
	private Input input;
	
	private FormLayout formDataLayout = new FormLayout();
	private DateField inputDate = new DateField("Fecha de entrada:");
	private ComboBox<Company> company = new ComboBox<>("Productor:");
	Button newLotButton = new Button("Nuevo lote");

	private Grid<Lot> lots = new Grid<>(Lot.class);

	private CssLayout buttons = new CssLayout();
	Button saveButton = new Button("Guardar");
	Button cancelButton = new Button("Cancelar");
	
	Binder<Input> binder = new Binder<>(Input.class);

	public InputDetailView() {
		log.debug("inside InputDetailView creator");
	}
	
	private void layout() {
		company.setItemCaptionGenerator(Company::getName);
		company.setItems(companyService.findAll());

		inputDate.setWidth("10em");
		company.setWidth("20em");
		formDataLayout.addComponents(inputDate, company);
		addComponent(formDataLayout);
		
		addComponent(newLotButton);
		newLotButton.addClickListener(event->newLot());
		
		addComponent(lots);
		lots.setColumns();
		lots.addColumn(Lot::getCode).setCaption("Codigo");
		lots.addColumn(lot->lot.getVariety().getName()).setCaption("Variedad");
		lots.addColumn(lot->lot.getStatus().getDescription()).setCaption("Estado");
		lots.addColumn(Lot::getPackages).setCaption("Bultos");
		lots.addColumn(Lot::getWeightGross).setCaption("Kg brutos");
		lots.addColumn(Lot::getWeightNet).setCaption("Kg netos");
		lots.addColumn(Lot::getWeightProcessed).setCaption("Kg procesados");
		lots.addComponentColumn(lot -> new Button("Editar", event -> editLot(lot.getId())));
		lots.addComponentColumn(lot -> new Button("Duplicar", event -> duplicateLot(lot.getId())));
		lots.addComponentColumn(lot -> new Button("Borrar", event -> deleteLot(lot.getId())));
		lots.setSizeFull();
		lots.setHeightMode(HeightMode.ROW);

		buttons.addComponents(saveButton, cancelButton);
		addComponents(buttons);
		saveButton.addClickListener(event -> save());
		cancelButton.addClickListener(event -> cancel());
		
		binder.bindInstanceFields(this);
		
	}
	
	public void populate(Long id) {
		input = inputService.findOne(id);
		List<Lot> lotList = lotService.findByInput(id);
		lots.setItems(lotList);
		lots.setHeightByRows(Math.max(1, lotList.size()));
	}

	public void newElement() {
		input = new Input();
		input.setInputDate(LocalDate.now());
		lots.setHeightByRows(1);
	}
	
	public void duplicate(Long id) {
		newElement();
		Input oldInput = inputService.findOne(id);
		input.setCompany(oldInput.getCompany());
	}
	
	private void save() {
		inputService.save(input);
		Notification.show("Elemento guardado");
		back();
	}
	
	
	private void deleteLot(Long lotId) {
		try {
			lotService.delete(lotId);
			Notification.show("Elemento eliminado");
		} catch (PebBusinessException e) {
			Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
		}
		populate(input.getId());
	}
	
	private void editLot(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(LotDetailView.NAME, NavigationOperation.EDIT, id) );
	}
	
	private void newLot() {
		if( input.isNew() ) {
			inputService.save(input);
		}
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(LotDetailView.NAME, NavigationOperation.NEW, null, input.getId()) );
	}
	
	private void duplicateLot(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(LotDetailView.NAME, NavigationOperation.DUPLICATE, id) );
	}
	
	private void cancel() {
		Notification.show("Cambios cancelados");
		back();
	}
		
	private void back() {
		ViewState returnViewState = navigationStack.pop();
		log.debug("returning back to: " + returnViewState);
		navigationUtils.navigateTo( returnViewState );
	}
		
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME, NavigationOperation.EDIT, input.getId()) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("InputDetailView.enter()");
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
			case NEW: newElement(); break;
			case EDIT: populate(id); break;
			case DUPLICATE: duplicate(id); break;			
		}
		binder.setBean(input);

	}

}

