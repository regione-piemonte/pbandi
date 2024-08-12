package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEconomieVO;

public class EconomieProgettiVO extends PbandiTEconomieVO {
	
	public String codiceVisualizzatoCedente;
	public String codiceVisualizzatoRicevente;
	
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
}
