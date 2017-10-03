package com.github.jlabeaga.peb.view;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NavigationStack {
	
	private static final Logger log = LoggerFactory.getLogger(NavigationStack.class);

	private Stack<ViewState> stack = new Stack<>();
	
	public void reset() {
		log.debug("NavigationStack reset");
		while( !stack.empty() ) {
			stack.pop();
		}
	}
	
	public boolean empty() {
		return stack.empty();
	}
	
	public void push(ViewState viewState) {
		stack.push(viewState);
	}
	
	public ViewState pop() {
		return stack.pop();
	}

}
