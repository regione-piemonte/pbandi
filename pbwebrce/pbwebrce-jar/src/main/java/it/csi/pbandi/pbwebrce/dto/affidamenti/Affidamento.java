/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.affidamenti;

import java.util.Date;

public class Affidamento implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Long idProgetto;
	private Long idAppalto;
	private String oggettoAppalto;
	private Long idProceduraAggiudicaz;
	private Long idTipologiaAppalto;
	private String descTipologiaAppalto;
	private Long idTipoAffidamento;
	private Long idTipoPercettore;
	private Long idStatoAffidamento;
	private String descStatoAffidamento;
	private Long idNorma;
	private Double bilancioPreventivo;
	private Double importoContratto;
	private Double impRendicontabile;
	private Double impRibassoAsta;
	private Double percRibassoAsta;
	private Date dtGuue;
	private Date dtGuri;
	private Date dtQuotNazionali;
	private Date dtWebStazAppaltante;
	private Date dtWebOsservatorio;
	private Date dtInizioPrevista;
	private Date dtConsegnaLavori;
	private Date dtFirmaContratto;
	private String interventoPisu;
	private String impresaAppaltatrice;
	private String sopraSoglia;
	private String descProcAgg;
	private Date dtInserimento;
	private Date dtAggiornamento;
	private Long idUtenteIns;
	private Long idUtenteAgg;
	private String cigProcAgg;
	private String codProcAgg;
	private Long numFornitoriAssociati;
	private Long numAllegatiAssociati;
	private Boolean respingibile;
	private String esitoIntermedio;
	private String esitoDefinitivo;
	private Boolean esisteAllegatoNonInviato;
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] fornitori;

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdAppalto(Long val) {
		idAppalto = val;
	}

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setOggettoAppalto(String val) {
		oggettoAppalto = val;
	}

	public String getOggettoAppalto() {
		return oggettoAppalto;
	}

	public void setIdProceduraAggiudicaz(Long val) {
		idProceduraAggiudicaz = val;
	}

	public Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}

	public Long getIdTipologiaAppalto() {
		return idTipologiaAppalto;
	}

	public void setIdTipologiaAppalto(Long idTipologiaAppalto) {
		this.idTipologiaAppalto = idTipologiaAppalto;
	}

	public String getDescTipologiaAppalto() {
		return descTipologiaAppalto;
	}

	public void setDescTipologiaAppalto(String descTipologiaAppalto) {
		this.descTipologiaAppalto = descTipologiaAppalto;
	}

	public Long getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	public void setIdTipoAffidamento(Long idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	public Long getIdTipoPercettore() {
		return idTipoPercettore;
	}

	public void setIdTipoPercettore(Long idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}

	public Long getIdStatoAffidamento() {
		return idStatoAffidamento;
	}

	public void setIdStatoAffidamento(Long idStatoAffidamento) {
		this.idStatoAffidamento = idStatoAffidamento;
	}

	public String getDescStatoAffidamento() {
		return descStatoAffidamento;
	}

	public void setDescStatoAffidamento(String descStatoAffidamento) {
		this.descStatoAffidamento = descStatoAffidamento;
	}

	public Long getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(Long idNorma) {
		this.idNorma = idNorma;
	}

	public Double getBilancioPreventivo() {
		return bilancioPreventivo;
	}

	public void setBilancioPreventivo(Double bilancioPreventivo) {
		this.bilancioPreventivo = bilancioPreventivo;
	}

	public Double getImportoContratto() {
		return importoContratto;
	}

	public void setImportoContratto(Double importoContratto) {
		this.importoContratto = importoContratto;
	}

	public Double getImpRendicontabile() {
		return impRendicontabile;
	}

	public void setImpRendicontabile(Double impRendicontabile) {
		this.impRendicontabile = impRendicontabile;
	}

	public Double getImpRibassoAsta() {
		return impRibassoAsta;
	}

	public void setImpRibassoAsta(Double impRibassoAsta) {
		this.impRibassoAsta = impRibassoAsta;
	}

	public Double getPercRibassoAsta() {
		return percRibassoAsta;
	}

	public void setPercRibassoAsta(Double percRibassoAsta) {
		this.percRibassoAsta = percRibassoAsta;
	}

	public Date getDtGuue() {
		return dtGuue;
	}

	public void setDtGuue(Date dtGuue) {
		this.dtGuue = dtGuue;
	}

	public Date getDtGuri() {
		return dtGuri;
	}

	public void setDtGuri(Date dtGuri) {
		this.dtGuri = dtGuri;
	}

	public Date getDtQuotNazionali() {
		return dtQuotNazionali;
	}

	public void setDtQuotNazionali(Date dtQuotNazionali) {
		this.dtQuotNazionali = dtQuotNazionali;
	}

	public Date getDtWebStazAppaltante() {
		return dtWebStazAppaltante;
	}

	public void setDtWebStazAppaltante(Date dtWebStazAppaltante) {
		this.dtWebStazAppaltante = dtWebStazAppaltante;
	}

	public Date getDtWebOsservatorio() {
		return dtWebOsservatorio;
	}

	public void setDtWebOsservatorio(Date dtWebOsservatorio) {
		this.dtWebOsservatorio = dtWebOsservatorio;
	}

	public Date getDtInizioPrevista() {
		return dtInizioPrevista;
	}

	public void setDtInizioPrevista(Date dtInizioPrevista) {
		this.dtInizioPrevista = dtInizioPrevista;
	}

	public Date getDtConsegnaLavori() {
		return dtConsegnaLavori;
	}

	public void setDtConsegnaLavori(Date dtConsegnaLavori) {
		this.dtConsegnaLavori = dtConsegnaLavori;
	}

	public Date getDtFirmaContratto() {
		return dtFirmaContratto;
	}

	public void setDtFirmaContratto(Date dtFirmaContratto) {
		this.dtFirmaContratto = dtFirmaContratto;
	}

	public String getInterventoPisu() {
		return interventoPisu;
	}

	public void setInterventoPisu(String interventoPisu) {
		this.interventoPisu = interventoPisu;
	}

	public String getImpresaAppaltatrice() {
		return impresaAppaltatrice;
	}

	public void setImpresaAppaltatrice(String impresaAppaltatrice) {
		this.impresaAppaltatrice = impresaAppaltatrice;
	}

	public String getSopraSoglia() {
		return sopraSoglia;
	}

	public void setSopraSoglia(String sopraSoglia) {
		this.sopraSoglia = sopraSoglia;
	}

	public String getDescProcAgg() {
		return descProcAgg;
	}

	public void setDescProcAgg(String descProcAgg) {
		this.descProcAgg = descProcAgg;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}

	public Long getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public String getCigProcAgg() {
		return cigProcAgg;
	}

	public void setCigProcAgg(String cigProcAgg) {
		this.cigProcAgg = cigProcAgg;
	}

	public String getCodProcAgg() {
		return codProcAgg;
	}

	public void setCodProcAgg(String codProcAgg) {
		this.codProcAgg = codProcAgg;
	}

	public Long getNumFornitoriAssociati() {
		return numFornitoriAssociati;
	}

	public void setNumFornitoriAssociati(Long numFornitoriAssociati) {
		this.numFornitoriAssociati = numFornitoriAssociati;
	}

	public Long getNumAllegatiAssociati() {
		return numAllegatiAssociati;
	}

	public void setNumAllegatiAssociati(Long numAllegatiAssociati) {
		this.numAllegatiAssociati = numAllegatiAssociati;
	}

	public java.lang.Boolean getRespingibile() {
		return respingibile;
	}

	public void setRespingibile(java.lang.Boolean respingibile) {
		this.respingibile = respingibile;
	}

	public String getEsitoIntermedio() {
		return esitoIntermedio;
	}

	public void setEsitoIntermedio(String esitoIntermedio) {
		this.esitoIntermedio = esitoIntermedio;
	}

	public String getEsitoDefinitivo() {
		return esitoDefinitivo;
	}

	public void setEsitoDefinitivo(String esitoDefinitivo) {
		this.esitoDefinitivo = esitoDefinitivo;
	}

	public java.lang.Boolean getEsisteAllegatoNonInviato() {
		return esisteAllegatoNonInviato;
	}

	public void setEsisteAllegatoNonInviato(java.lang.Boolean esisteAllegatoNonInviato) {
		this.esisteAllegatoNonInviato = esisteAllegatoNonInviato;
	}

	public it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] getFornitori() {
		return fornitori;
	}

	public void setFornitori(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] fornitori) {
		this.fornitori = fornitori;
	}

}
