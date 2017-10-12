package com.github.jlabeaga.peb.view;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

@Component
public class NavigationUtils {

//	public String urlEncode(Map<String,String> parameters) {
//		String urlEncoded = "";
//		if( !parameters.isEmpty() ) {
//			urlEncoded = parameters.keySet().stream().map(key -> key + "=" + parameters.get(key)).collect(Collectors.joining("&")).toString();
//
//		}
//		return urlEncoded;
//	}
//	
	public void navigateTo(ViewState viewState) {
		navigateTo(viewState.viewName, viewState.parameters);
	}
	
	public void navigateTo(String viewName, Map<String,String> parameters) {
		navigateTo(viewName, ViewState.urlStyleEncode(parameters));
	}
	
	public void navigateTo(String viewName, String parameters) {
		String viewState = viewName;
		if( parameters != null && !parameters.isEmpty() ) {
			viewState = viewName + "/" + parameters;
		}
		UI.getCurrent().getNavigator().navigateTo(viewState);
	}
	
	public String getParameter(ViewChangeEvent event, String paramName) {
		String value = null;
		if( event.getParameterMap() != null ) {
			value = event.getParameterMap().get(paramName);
		}
		return value;
	}
	
	public NavigationOperation getNavigationOperation(ViewChangeEvent event) {
		NavigationOperation value = null;
		if( event.getParameterMap() != null && !event.getParameterMap().isEmpty()) {
			value = NavigationOperation.valueOf(event.getParameterMap().get("navigationOperation"));
		}
		return value;		
	}
	
	public Long getId(ViewChangeEvent event) {
		Long id = null;
		try {
			id = Long.valueOf(event.getParameterMap().get("id"));
		} catch( NullPointerException | NumberFormatException e) {
			// do nothing, just return null
		}
		return id;		
	}
	
	public Long getParentId(ViewChangeEvent event) {
		Long parentId = null;
		try {
			parentId = Long.valueOf(event.getParameterMap().get("parentId"));
		} catch( NullPointerException | NumberFormatException e) {
			// do nothing, just return null
		}
		return parentId;		
	}
	
//	public Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
//	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
//	    String query = url.getQuery();
//	    String[] pairs = query.split("&");
//	    for (String pair : pairs) {
//	        int idx = pair.indexOf("=");
//	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
//	    }
//	    return query_pairs;
//	}
}
