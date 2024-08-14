/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;


public class PbandiCCostantiVO {
	private String attributo;
  	
  	private String valore;
  	
  	
	public PbandiCCostantiVO () {
	
	}
  	
	public PbandiCCostantiVO (String attributo, String valore) {
	
		this. attributo =  attributo;
		this. valore =  valore;
	}
  	
  	
	public String getAttributo() {
		return attributo;
	}
	
	public void setAttributo(String attributo) {
		this.attributo = attributo;
	}
	
	public String getValore() {
		return valore;
	}
	
	public void setValore(String valore) {
		this.valore = valore;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && attributo != null && valore != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = true;

   		return isPkValid;
	}

}
