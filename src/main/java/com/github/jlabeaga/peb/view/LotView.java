package com.github.jlabeaga.peb.view;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Lot;
import com.github.jlabeaga.peb.service.CompanyService;
import com.github.jlabeaga.peb.service.LotService;
import com.github.jlabeaga.peb.service.SearchCriteria;
import com.github.jlabeaga.peb.ui.AdminUI;
import com.github.jlabeaga.peb.ui.OperatorUI;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.annotations.Title;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
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

	@Autowired
	private CompanyService companyService; 
	
	@Autowired
	private LotService lotService; 
	
	@Autowired
	private SearchCriteria searchCriteria;
	
	Binder<SearchCriteria> binder = new Binder<>(SearchCriteria.class);
	
	private TextField year;
	private DateField dateFrom;
	private DateField dateTo;
	private ComboBox<Company> company;
	private Button searchButton;
	private Button newButton;
	private GridLayout searchLayout;
	private Grid<Lot> grid;
	
	public LotView() {
		log.debug("inside LotView creator");
		grid = new Grid<>(Lot.class);
		year = new TextField("Año");
		year.setValue(String.valueOf(LocalDate.now().getYear()));
		dateFrom = new DateField("Desde:");
		dateTo = new DateField("Hasta:");
		company = new ComboBox<>("Productor");
		company.setItemCaptionGenerator(Company::getName);
		searchButton = new Button("Buscar", event -> search() );
		newButton = new Button("Nuevo", event -> newElement() );
		searchLayout = new GridLayout(3, 2);
	}

	private void layout() {
		company.setItems(companyService.findAll());
		searchLayout.addComponents(year, company, searchButton, dateFrom, dateTo, newButton);
		addComponent(searchLayout);
		grid.setColumns();
		grid.addColumn(Lot::getInputDate).setCaption("Fecha entrada");
		grid.addColumn(Lot::getCode).setCaption("Codigo");
		grid.addColumn(lot->lot.getCompany().getName()).setCaption("Productor");
		grid.addColumn(lot->lot.getVariety().getName()).setCaption("Variedad");
		grid.addColumn(Lot::getPackages).setCaption("Bultos");
		grid.addColumn(Lot::getWeightNet).setCaption("Peso neto");
		grid.addColumn(Lot::getStatus).setCaption("Status");
		grid.addColumn(Lot::getWeightProcessed).setCaption("Peso procesado:");
		grid.addComponentColumn(lot -> new Button("Editar", event -> edit(lot)));
		grid.addComponentColumn(lot -> new Button("Duplicar", event -> duplicate(lot)));
		grid.addComponentColumn(lot -> new Button("Borrar", event -> delete(lot)));
		grid.setSizeFull();
		addComponent(grid);
		binder.bindInstanceFields(searchCriteria);
	}
	
	private void delete(Lot lot) {
		if( !lotService.isUnassigned(lot) ) {
			Notification.show("No es posible eliminar un lote con partidas asignadas a algún palet", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		lotService.delete(lot);
		Notification.show("Elemento eliminado");
		search();
	}
	
	private void edit(Lot lot) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(LotDetailView.NAME, NavigationOperation.EDIT, lot.getId()) );
	}
	
	private void newElement() {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(LotDetailView.NAME, NavigationOperation.NEW, null) );
	}
	
	private void duplicate(Lot lot) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(LotDetailView.NAME, NavigationOperation.DUPLICATE, lot.getId()) );
	}
	
	private void search() {
		log.debug("inside LotView.search()");
		if( lotService == null ) return;
		binder.setBean(searchCriteria);
		log.debug("searchCriteria bound="+searchCriteria);
		grid.setItems( lotService.search(searchCriteria) );
	}
	
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("LotView.enter()");
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
		layout();
		search();
		setSizeFull();
		
	}

}
