package it.csi.pbandi.pbweb.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TraduttoreMessaggiPbandisrv {
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf/messages");
	
	public static String traduci(String key) {
		if (key == null || key.length() == 0)
			return null;
		return RESOURCE_BUNDLE.getString(key);
	}
	
	public static String traduci(String key, String[] params) {
		if (key == null || key.length() == 0)
			return null;
		//return RESOURCE_BUNDLE.getString(key);
		String msg = RESOURCE_BUNDLE.getString(key);
		return MessageFormat.format(msg, params);
	}
	
	public static ArrayList<String> traduci(String[] keys) {
		ArrayList<String> lista = new ArrayList<String>();
		if (keys == null || keys.length == 0)
			return lista;
		for (String key : keys) {
			lista.add(RESOURCE_BUNDLE.getString(key));
		}
		return lista;
	}
	
	public static ArrayList<String> traduci(ArrayList<String> keys) {
		ArrayList<String> lista = new ArrayList<String>();
		if (keys == null || keys.size() == 0)
			return lista;
		for (String key : keys) {
			lista.add(RESOURCE_BUNDLE.getString(key));
		}
		return lista;
	}

}
