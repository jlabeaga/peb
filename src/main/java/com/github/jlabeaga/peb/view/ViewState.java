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

	public String viewName;
	public NavigationOperation navigationOperation;
	public Long id;
	public Long parentId;
	
	public Map<String,String> parameters = new HashMap<String,String>();
	
	public ViewState() {
		super();
	}
	
	public ViewState(String viewName) {
		super();
		this.viewName = viewName;
	}
	
	public ViewState(String viewName, NavigationOperation navigationOperation, Long id) {
		this(viewName);
		this.navigationOperation = navigationOperation;
		this.id = id;
		if( navigationOperation != null ) {
			parameters.put("navigationOperation", navigationOperation.toString());
		}
		if( id != null ) {
			parameters.put("id", id.toString());
		}
	}
	
	public ViewState(String viewName, NavigationOperation operation, Long id, Long parentId) {
		this(viewName, operation, id);
		if( parentId != null ) {
			parameters.put("parentId", parentId.toString());
		}
	}
	
	public static String urlStyleEncode(Map<String,String> map) {
		String urlEncoded = "";
		if( !map.isEmpty() ) {
			urlEncoded = map.keySet().stream().map(key -> key + "=" + map.get(key)).collect(Collectors.joining("&")).toString();

		}
		return urlEncoded;
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

	public static String toQueryString(ViewState viewState) throws UnsupportedEncodingException {
		Map<String,String> result = new HashMap<String,String>();
		result.put("viewName", viewState.viewName);
		if( viewState.navigationOperation != null ) {
			result.put("navigationOperation", viewState.navigationOperation.toString());
		}
		if( viewState.id != null ) {
			result.put("id", viewState.id.toString());
		}
		if( viewState.parentId != null ) {
			result.put("parentId", viewState.parentId.toString());
		}
		result.putAll(viewState.parameters);
		String urlStyleEncoded = urlStyleEncode(result);
		log.debug("urlStyleEncoded="+urlStyleEncoded);
		return urlStyleEncoded;
	}
	
	public static ViewState fromQueryString(String urlStyleEncoded) throws UnsupportedEncodingException {
		ViewState viewState = new ViewState();
		Map<String,String> queryPairs = splitQuery(urlStyleEncoded);
		viewState.viewName = queryPairs.get("viewName");
		if( queryPairs.get("navigationOperation") != null ) {
			viewState.navigationOperation = NavigationOperation.valueOf(queryPairs.get("navigationOperation"));
		}
		viewState.id = queryPairs.get("id")==null?null:Long.parseLong(queryPairs.get("id"));
		viewState.parentId = queryPairs.get("parentId")==null?null:Long.parseLong(queryPairs.get("parentId"));
		viewState.parameters = queryPairs;
		return viewState;
	}
	
	public static String toBase64String(ViewState viewState) throws UnsupportedEncodingException {
		String urlStyleEncoded = toQueryString(viewState);
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
	
	@Override
	public String toString() {
		return "ViewState [viewName=" + viewName + ", navigationOperation=" + navigationOperation + ", id=" + id + ", parentId="
				+ parentId + ", parameters=" + parameters + "]";
	}
	


	
}
