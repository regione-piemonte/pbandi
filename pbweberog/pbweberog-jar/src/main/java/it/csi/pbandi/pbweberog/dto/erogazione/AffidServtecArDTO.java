/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class AffidServtecArDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idAffidServtec;
	private Long idRichiestaErogazione;
	private String flagAffidServtec;
	private String fornitore;
	private String servizioAffidato;
	private String documento;
	private String nomeFile;

	public Long getIdAffidServtec() {
		return idAffidServtec;
	}

	public void setIdAffidServtec(Long idAffidServtec) {
		this.idAffidServtec = idAffidServtec;
	}

	public Long getIdRichiestaErogazione() {
		return idRichiestaErogazione;
	}

	public void setIdRichiestaErogazione(Long idRichiestaErogazione) {
		this.idRichiestaErogazione = idRichiestaErogazione;
	}

	public String getFlagAffidServtec() {
		return flagAffidServtec;
	}

	public void setFlagAffidServtec(String flagAffidServtec) {
		this.flagAffidServtec = flagAffidServtec;
	}

	public String getFornitore() {
		return fornitore;
	}

	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}

	public String getServizioAffidato() {
		return servizioAffidato;
	}

	public void setServizioAffidato(String servizioAffidato) {
		this.servizioAffidato = servizioAffidato;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}
