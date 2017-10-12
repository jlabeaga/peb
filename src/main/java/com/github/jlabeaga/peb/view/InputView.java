package com.github.jlabeaga.peb.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jlabeaga.peb.model.Company;
import com.github.jlabeaga.peb.model.Input;
import com.github.jlabeaga.peb.model.SearchCriteria;
import com.github.jlabeaga.peb.service.CompanyService;
import com.github.jlabeaga.peb.service.InputService;
import com.github.jlabeaga.peb.service.LotService;
import com.github.jlabeaga.peb.ui.PebUI;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=InputView.NAME, ui=PebUI.class)
public class InputView extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "InputView";
	
	public static final String WINDOW_TITLE = "Entradas";

	private static final Logger log = LoggerFactory.getLogger(InputView.class);

	@Autowired
	private NavigationUtils navigationUtils; 

	@Autowired
	private NavigationStack navigationStack; 

	@Autowired
	private InputService inputService; 
	
	@Autowired
	private CompanyService companyService; 
	
	@Autowired
	private LotService lotService; 
	
	@Autowired
	private SearchCriteria searchCriteria;
	
	Binder<SearchCriteria> binder = new Binder<>(SearchCriteria.class);
	
	private ComboBox<Integer> year;
	private DateField dateFrom;
	private DateField dateTo;
	private ComboBox<Company> company;
	private Button searchButton;
	private Button newButton;
	private GridLayout searchLayout;
	private Grid<Input> grid;
	
	public InputView() {
		log.debug("inside InputView creator");
		grid = new Grid<>(Input.class);
		year = new ComboBox<>("Año");
		year.setValue(SearchCriteria.presentYear);
		dateFrom = new DateField("Desde:");
		dateTo = new DateField("Hasta:");
		company = new ComboBox<>("Productor");
		company.setItemCaptionGenerator(Company::getName);
		searchButton = new Button("Buscar", event -> search() );
		newButton = new Button("Nuevo", event -> newElement() );
		searchLayout = new GridLayout(3, 2);
	}

	private void layout() {
		year.setItems(SearchCriteria.years);
		company.setItems(companyService.findAll());
		searchLayout.addComponents(year, company, searchButton, dateFrom, dateTo, newButton);
		searchLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
		//searchLayout.setSpacing(true);
		addComponent(searchLayout);
		grid.setColumns();
		grid.addColumn(Input::getInputDate).setCaption("Fecha entrada");
		grid.addColumn(input->input.getCompany().getName()).setCaption("Productor");
		grid.addComponentColumn(input -> new Button("Editar", event -> edit(input.getId())));
		grid.addComponentColumn(input -> new Button("Duplicar", event -> duplicate(input.getId())));
		grid.addComponentColumn(input -> new Button("Borrar", event -> delete(input.getId())));
		grid.setSizeFull();
		addComponent(grid);
	}
	
	private void delete(Long id) {
		if( !lotService.findByInput(id).isEmpty() ) {
			Notification.show("Elimine todos los lotes asociados antes de intentar eliminar la Entrada", Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		inputService.delete(id);
		Notification.show("Elemento eliminado");
		search();
	}
	
	private void edit(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(InputDetailView.NAME, NavigationOperation.EDIT, id) );
	}
	
	private void newElement() {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(InputDetailView.NAME, NavigationOperation.NEW, null) );
	}
	
	private void duplicate(Long id) {
		pushReturnViewState();
		navigationUtils.navigateTo( new ViewState(InputDetailView.NAME, NavigationOperation.DUPLICATE, id) );
	}
	
	private void search() {
		log.debug("inside InputView.search()");
		if( inputService == null ) return;
		grid.setItems( inputService.search(searchCriteria) );
	}
	
	private void pushReturnViewState() {
		navigationStack.push( new ViewState(this.NAME) ); // viewState to return to
	}
	
	public void enter(ViewChangeEvent event) {
		log.debug("InputView.enter()");
		Page.getCurrent().setTitle(this.WINDOW_TITLE);
		layout();
		search();
		setSizeFull();
	}

}
