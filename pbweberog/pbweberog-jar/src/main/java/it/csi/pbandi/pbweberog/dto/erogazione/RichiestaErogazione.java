/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

import java.util.Date;

public class RichiestaErogazione implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private String descCausaleErogazione;
	private String numeroRichiestaErogazione;
	private Date dataRichiestaErogazione;
	private Double importoRichiestaErogazione;
	public String getDescCausaleErogazione() {
		return descCausaleErogazione;
	}
	public void setDescCausaleErogazione(String descCausaleErogazione) {
		this.descCausaleErogazione = descCausaleErogazione;
	}
	public String getNumeroRichiestaErogazione() {
		return numeroRichiestaErogazione;
	}
	public void setNumeroRichiestaErogazione(String numeroRichiestaErogazione) {
		this.numeroRichiestaErogazione = numeroRichiestaErogazione;
	}
	public Date getDataRichiestaErogazione() {
		return dataRichiestaErogazione;
	}
	public void setDataRichiestaErogazione(Date dataRichiestaErogazione) {
		this.dataRichiestaErogazione = dataRichiestaErogazione;
	}
	public Double getImportoRichiestaErogazione() {
		return importoRichiestaErogazione;
	}
	public void setImportoRichiestaErogazione(Double importoRichiestaErogazione) {
		this.importoRichiestaErogazione = importoRichiestaErogazione;
	}


	
}
