/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEconomieVO;

public class EconomiaVO extends PbandiTEconomieVO {
	
	public String codiceProgettoCedente;
	public String codiceProgettoRicevente;
	public String codiceVisualizzatoCedente;
	public String codiceVisualizzatoRicevente;	
	
	public String getCodiceProgettoCedente() {
		return codiceProgettoCedente;
	}
	public void setCodiceProgettoCedente(String codiceProgettoCedente) {
		this.codiceProgettoCedente = codiceProgettoCedente;
	}
	public String getCodiceVisualizzatoCedente() {
		return codiceVisualizzatoCedente;
	}
	public void setCodiceVisualizzatoCedente(String codiceVisualizzatoCedente) {
		this.codiceVisualizzatoCedente = codiceVisualizzatoCedente;
	}
	public String getCodiceVisualizzatoRicevente() {
		return codiceVisualizzatoRicevente;
	}
	public void setCodiceVisualizzatoRicevente(String codiceVisualizzatoRicevente) {
		this.codiceVisualizzatoRicevente = codiceVisualizzatoRicevente;
	}
	public String getCodiceProgettoRicevente() {
		return codiceProgettoRicevente;
	}
	public void setCodiceProgettoRicevente(String codiceProgettoRicevente) {
		this.codiceProgettoRicevente = codiceProgettoRicevente;
	}
	
}
