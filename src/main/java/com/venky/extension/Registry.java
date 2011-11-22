package com.venky.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Registry {
	private Registry(){
		
	}
	private static Registry instance = new Registry();
	public static Registry instance(){
		return instance;
	}
	private HashMap<String, List<Extension<?>>> extensionsMap = new HashMap<String, List<Extension<?>>>();
	public void registerExtension(String name,Extension<?> extension){
		List<Extension<?>> extensions = getExtensions(name);
		extensions.add(extension);
	}
	public List<Extension<?>> getExtensions(String extensionPoint){
		List<Extension<?>> extensions = extensionsMap.get(extensionPoint);
		if (extensions == null){
			extensions = new ArrayList<Extension<?>>();
			extensionsMap.put(extensionPoint,extensions);
		}
		return extensions;
	}
	public <C> void callExtensions(String pointName, C context){
		for (Extension<?> extn : getExtensions(pointName)){
			((Extension<C>)extn).invoke(context);
		}
	}
	public static final Object DEFAULT_CONTEXT = new Object();
}
