/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class PropostaErogazioneVO {
	
	private Long idProposta;
	private Long idProgetto;
	private String codiceProgetto;
	private String beneficiario;
	
	private BigDecimal importoLordo;
	private BigDecimal importoIres;
	private BigDecimal importoNetto;
	private Date dataConcessione;
	private String controlliPreErogazione;
	private String flagFinistr;

	private String statoRichiestaDurc;
	private String statoRichiestaAntimafia;

	public PropostaErogazioneVO() {
		super();
	}

	public Long getIdProposta() {
		return idProposta;
	}

	public void setIdProposta(Long idProposta) {
		this.idProposta = idProposta;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public BigDecimal getImportoLordo() {
		return importoLordo;
	}

	public void setImportoLordo(BigDecimal importoLordo) {
		this.importoLordo = importoLordo;
	}

	public BigDecimal getImportoIres() {
		return importoIres;
	}

	public void setImportoIres(BigDecimal importoIres) {
		this.importoIres = importoIres;
	}

	public BigDecimal getImportoNetto() {
		return importoNetto;
	}

	public void setImportoNetto(BigDecimal importoNetto) {
		this.importoNetto = importoNetto;
	}

	public Date getDataConcessione() {
		return dataConcessione;
	}

	public void setDataConcessione(Date dataConcessione) {
		this.dataConcessione = dataConcessione;
	}

	public String getControlliPreErogazione() {
		return controlliPreErogazione;
	}

	public void setControlliPreErogazione(String controlliPreErogazione) {
		this.controlliPreErogazione = controlliPreErogazione;
	}

	public String getFlagFinistr() {
		return flagFinistr;
	}

	public void setFlagFinistr(String flagFinistr) {
		this.flagFinistr = flagFinistr;
	}

	public String getStatoRichiestaDurc() {
		return statoRichiestaDurc;
	}

	public void setStatoRichiestaDurc(String statoRichiestaDurc) {
		this.statoRichiestaDurc = statoRichiestaDurc;
	}

	public String getStatoRichiestaAntimafia() {
		return statoRichiestaAntimafia;
	}

	public void setStatoRichiestaAntimafia(String statoRichiestaAntimafia) {
		this.statoRichiestaAntimafia = statoRichiestaAntimafia;
	}
}
