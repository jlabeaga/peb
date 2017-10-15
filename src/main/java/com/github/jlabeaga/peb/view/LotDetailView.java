package com.github.jlabeaga.peb.view;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.ui.NumberField;

import com.github.jlabeaga.peb.exception.PebBusinessException;
import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.Lot;
import com.github.jlabeaga.peb.model.Part;
import com.github.jlabeaga.peb.model.Variety;
import com.github.jlabeaga.peb.service.CompanyService;
import com.github.jlabeaga.peb.service.InputService;
import com.github.jlabeaga.peb.service.LotService;
import com.github.jlabeaga.peb.service.QueryService;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;

@SpringView(name=LotDetailView.NAME, ui=PebUI.class)
public class LotDetailView extends FormLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "LotDetailView";
	
	public static final String WINDOW_TITLE = "Lote";

	private static final Logger log = LoggerFactory.getLogger(LotDetailView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private CompanyService companyService; 
	
	@Autowired
	private InputService inputService; 
	
	@Autowired
	private LotService lotService; 
	
	@Autowired
	private QueryService queryService; 
	
	private Lot lot;
	
	private TextField code = new TextField("Código:");
	private ComboBox<Input> input = new ComboBox<>("Entrada:");
	private ComboBox<Company> company = new ComboBox<>("Productor:");
	private ComboBox<Variety> variety = new ComboBox<>("Variedad:");
	private ComboBox<Lot.Status> status = new ComboBox<>("Estado:");
	private NumberField packages = new NumberField("Bultos:");
	private NumberField weightGross = new NumberField("Peso bruto:");
	private NumberField weightNet = new NumberField("Peso neto:");
	private NumberField weightProcessed = new NumberField("Peso procesado:");
	private DateField processingDate = new DateField("Fecha de procesado:");
	private Grid<Part> parts = new Grid<>(Part.class);
    
	private CssLayout buttons = new CssLayout();
	Button processLotButton = new Button("Procesar Lote");
	Button saveButton = new Button("Guardar");
	Button cancelButton = new Button("Cancelar");
	
	Binder<Lot> binder = new Binder<>(Lot.class);

	public LotDetailView() {
		log.debug("inside CompanyDetailView creator");
	}
	
	private void layout() {
		addComponent(code);
		addComponent(input);
		addComponent(company);
		addComponent(variety);
		addComponent(status);
		addComponent(packages);
		addComponent(weightGross);
		addComponent(weightNet);
		addComponent(weightProcessed);
		addComponent(processingDate);
		
		code.setWidth("10em");
		
		input.setItems(inputService.findAll());
		input.setItemCaptionGenerator(in->in.getInputDate()+" "+in.getCompany().getName());
		input.setWidth("30em");

		company.setItems(companyService.findAll());
		company.setItemCaptionGenerator(Company::getName);
		company.setWidth("25em");
		company.addBlurListener(event->buildCodeFromData());

		variety.setItems(queryService.listVarieties());
		variety.setItemCaptionGenerator(va->va.getName()+" - "+va.getCode());
		variety.setWidth("20em");
		variety.addBlurListener(event->buildCodeFromData());
		
		status.setItems(Lot.Status.values());
		status.setItemCaptionGenerator(Lot.Status::getDescription);
		status.setWidth("15em");
		
		processingDate.addValueChangeListener(event->buildCodeFromData());
		
		packages.setWidth("5em");
		packages.addStyleName("align-right");
		weightGross.setWidth("5em");
		weightGross.addStyleName("align-right");
		weightNet.setWidth("5em");
		weightNet.addStyleName("align-right");
		weightProcessed.setWidth("5em");
		weightProcessed.addStyleName("align-right");

		buttons.addComponents(processLotButton, saveButton, cancelButton);
		addComponents(buttons);
		processLotButton.addClickListener(event -> processLot());
		saveButton.addClickListener(event -> save());
		cancelButton.addClickListener(event -> cancel());
		
		parts.setColumns();
		parts.addColumn(Part::getCode).setCaption("Código");
		parts.addColumn(Part::getWeight).setCaption("Peso");
		parts.addColumn(part->part.getStatus().getDescription()).setCaption("Estado");
		parts.addColumn(part->part.getPallet()!=null?part.getPallet().getCode():"").setCaption("Palet");
		parts.addColumn(part->part.getPallet()!=null?part.getPallet().getProposedCustomer():"").setCaption("Cliente");
		parts.addComponentColumn(part -> new Button("Editar", event -> editPart(part.getId())));
		parts.addComponentColumn(part -> new Button("Partir", event -> splitPart(part.getId())));
		parts.addComponentColumn(part -> new Button("Borrar", event -> deletePart(part.getId())));
		parts.setHeightMode(HeightMode.ROW);
//		parts.setSizeFull();

		binder.forField(packages)
		  .withConverter(
		    new StringToIntegerConverter(0, "Por favor introduce un número"))
		  .bind(Lot::getPackages, Lot::setPackages);
		  
		binder.forField(weightGross)
		  .withConverter(
		    new StringToIntegerConverter(0, "Por favor introduce un número"))
		  .bind(Lot::getWeightGross, Lot::setWeightGross);
		  
		binder.forField(weightNet)
		  .withConverter(
		    new StringToIntegerConverter(0, "Por favor introduce un número"))
		  .bind(Lot::getWeightNet, Lot::setWeightNet);
		  
		binder.forField(weightProcessed)
		  .withConverter(
		    new StringToIntegerConverter(0, "Por favor introduce un número"))
		  .bind(Lot::getWeightProcessed, Lot::setWeightProcessed);
		
		buildCodeFromData();
		
		binder.bindInstanceFields(this);
		
	}

	public void buildCodeFromData() {
		code.setValue(lotService.buildCodeFromData(lot));
	}

	public void populate(Long id) {
		lot = lotService.findOneWithParts(id);
		int partsSize = 0;
		if( lot.getParts() != null ) {
			parts.setItems(lot.getParts());
			partsSize = lot.getParts().size();
		}
		parts.setHeightByRows(Math.max(1, partsSize));
		checkWeigthBalance();
	}
	
	public void checkWeigthBalance() {
		try {
			lotService.checkWeigthBalance(lot);
		} catch( PebBusinessException e ) {
			Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
		}
	}

	public void newElement(Long inputId) {
		lot = new Lot();
		Input input = inputService.findOne(inputId);
		lot.setInput(input);
		lot.setCompany(input.getCompany());
		lot.setStatus(Lot.Status.UNPROCESSED);
		lot.setProcessingDate(LocalDate.now());
	}
	
	public void duplicate(Long id) {
		Lot newLot = new Lot();
		newLot.setInput(lot.getInput());
		newLot.setCompany(lot.getCompany());
		newLot.setStatus(Lot.Status.UNPROCESSED);
		lot = newLot;
	}
	
	private void processLot() {
		try {
			lotService.processLot(lot);
			Notification.show("Lote procesado");;
			populate(lot.getId());
		} catch (PebBusinessException e) {
			Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
		}
	}
	
	private void save() {
		lotService.save(lot);
		Notification.show("Elemento guardado");;
		populate(lot.getId());
	}
	
	private void cancel() {
		Notification.show("Cambios cancelados");;
		back();
	}
		
	private void deletePart(Long partId) {
		lotService.deletePart(partId);
		Notification.show("Elemento eliminado");
		populate(lot.getId());
	}
	
	private void editPart(Long partId) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(PartDetailView.NAME, NavigationOperation.EDIT, partId) );
	}
	
	private void splitPart(Long partId) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(SplitPartDetailView.NAME, NavigationOperation.EDIT, partId) );
	}
	
	private void back() {
		ViewState returnViewState = navigationStack.pop();
		log.debug("returning back to: " + returnViewState);
		navigationUtils.navigateTo( returnViewState );
	}
		
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME, NavigationOperation.EDIT, lot.getId()) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("LotDetailView.enter()");
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
			case NEW: newElement(navigationUtils.getParentId(event)); break;
			case EDIT: populate(id); break;
			case DUPLICATE: duplicate(id); break;			
		}
		binder.setBean(lot);

	}

}
