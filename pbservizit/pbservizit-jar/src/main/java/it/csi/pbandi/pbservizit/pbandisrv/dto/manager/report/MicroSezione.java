/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe rappresentativa di un textField del report.
 * Il textField e' composto da un testo contenente eventuali
 * parametri definiti nella forma $P{nomeparametro} che saranno
 *  valorizzati a runtime.
 *
 */
public class MicroSezione {
	
	private String testo;
	private int align = ALIGN_LEFT;
	
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_LEFT = 2;
	public static final int ALIGN_RIGHT = 3;
	public static final int ALIGN_JUSTIFIED = 4;
	private Map<String,Class> textFieldParams = null;
	
	
	public void setTesto(String testo) {
		extractParams(testo);
		this.testo = testo;
	}
	public String getTesto() {
		return testo;
	}
	public void setAlign(int align) {
		this.align = align;
	}
	public int getAlign() {
		return align;
	}
	
	/**
	 * Estrae i parametri della microsezione dalla stringa del testo.
	 * I parametri sono definiti nel seguente modo: $P{nomeparametro}
	 * @param text: testo da inserire nel report
	 */
	private void extractParams(String text) {
		if (text.indexOf("$P")>-1) {
			for (String token : text.split("\\$P")) {
				if (token.trim().length() > 0) {
					if (token.startsWith("{")) {
						int end = token.indexOf("}");
						if (textFieldParams == null)
							textFieldParams = new HashMap<String, Class>();
						/*
						 * Estraggo dalla definizione del parametro il nome, che e'
						 * contenuto tra le parentesi {}
						 */
						textFieldParams.put(token.substring(1,end),java.lang.String.class);
					}
				}
			}
		} 
	}
	
	/**
	 * Restituisce la lista dei parametri del textField
	 * @return
	 */
	public Map<String, Class> getTextFieldParams() {
		return textFieldParams;
	}
	
	
}
