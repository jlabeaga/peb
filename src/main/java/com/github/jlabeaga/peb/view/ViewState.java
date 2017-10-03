package com.github.jlabeaga.peb.view;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewState {

	private static final Logger log = LoggerFactory.getLogger(ViewState.class);

	String viewName;
	Map<String,String> parameters = new HashMap<String,String>();
	
	public ViewState() {
		super();
	}
	
	public ViewState(String viewName) {
		super();
		this.viewName = viewName;
	}
	
	public ViewState(String viewName, NavigationOperation operation, Long id) {
		super();
		this.viewName = viewName;
		if( operation != null ) {
			parameters.put("operation", operation.toString());
		}
		if( id != null ) {
			parameters.put("id", id.toString());
		}
	}
	
	public ViewState(String viewName, Map<String,String> parameters) {
		super();
		this.viewName = viewName;
		this.parameters = parameters;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public Map<String,String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String,String> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}

	private String urlStyleEncode(Map<String,String> map) {
		String urlEncoded = "";
		if( !map.isEmpty() ) {
			urlEncoded = map.keySet().stream().map(key -> key + "=" + map.get(key)).collect(Collectors.joining("&")).toString();

		}
		return urlEncoded;
	}
	
	public String toBase64String() {
		Map<String,String> result = new HashMap<String,String>();
		result.put("viewName", viewName);
		result.putAll(parameters);
		
		String urlStyleEncoded = urlStyleEncode(result);
		log.debug("urlStyleEncoded="+urlStyleEncoded);
		
		String base64UrlEncoded = "";
		try {
			base64UrlEncoded = Base64.getUrlEncoder().encodeToString(urlStyleEncoded.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("base64UrlEncoded="+base64UrlEncoded);
		
		return base64UrlEncoded;
	}

	public static ViewState fromBase64String(String base64UrlEncoded) throws UnsupportedEncodingException {
		String urlStyleEncoded = new String(Base64.getUrlDecoder().decode(base64UrlEncoded), "utf-8");
		log.debug("urlStyleEncoded="+urlStyleEncoded);
		return fromQueryString(urlStyleEncoded);
	}
	
	public static ViewState fromQueryString(String urlStyleEncoded) throws UnsupportedEncodingException {
		Map<String,String> queryPairs = splitQuery(urlStyleEncoded);
		String viewName = queryPairs.get("viewName");
		queryPairs.remove("viewName");
		ViewState viewState = new ViewState(viewName, queryPairs);
		return viewState;
	}
	
	public static Map<String, String> splitQuery(String urlStyleEncoded) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String[] pairs = urlStyleEncoded.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	@Override
	public String toString() {
		return "ViewState [viewName=" + viewName + ", parameters=" + parameters + "]";
	}

	
}
