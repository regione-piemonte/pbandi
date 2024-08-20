/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti;

import java.util.Arrays;
import java.util.Date;

public class AffidamentoDTO implements java.io.Serializable {
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
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO proceduraAggiudicazioneDTO;	
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO[] varianti;
	private it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] fornitori;
	private long numFornitoriAssociati = 0;	
	private long numAllegatiAssociati = 0;	
	private java.lang.Boolean respingibile;
	private String fase1Esito;
	private String fase1Rettifica;
	private String fase2Esito;
	private String fase2Rettifica;
	private boolean esisteAllegatoNonInviato = true;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	@Override
	public String toString() {
		return "AffidamentoDTO [idProgetto=" + idProgetto + ", idAppalto=" + idAppalto + ", oggettoAppalto="
				+ oggettoAppalto + ", idProceduraAggiudicaz=" + idProceduraAggiudicaz + ", idTipologiaAppalto="
				+ idTipologiaAppalto + ", descTipologiaAppalto=" + descTipologiaAppalto + ", idTipoAffidamento="
				+ idTipoAffidamento + ", idTipoPercettore=" + idTipoPercettore + ", idStatoAffidamento="
				+ idStatoAffidamento + ", descStatoAffidamento=" + descStatoAffidamento + ", idNorma=" + idNorma
				+ ", bilancioPreventivo=" + bilancioPreventivo + ", importoContratto=" + importoContratto
				+ ", impRendicontabile=" + impRendicontabile + ", impRibassoAsta=" + impRibassoAsta
				+ ", percRibassoAsta=" + percRibassoAsta + ", dtGuue=" + dtGuue + ", dtGuri=" + dtGuri
				+ ", dtQuotNazionali=" + dtQuotNazionali + ", dtWebStazAppaltante=" + dtWebStazAppaltante
				+ ", dtWebOsservatorio=" + dtWebOsservatorio + ", dtInizioPrevista=" + dtInizioPrevista
				+ ", dtConsegnaLavori=" + dtConsegnaLavori + ", dtFirmaContratto=" + dtFirmaContratto
				+ ", interventoPisu=" + interventoPisu + ", impresaAppaltatrice=" + impresaAppaltatrice
				+ ", sopraSoglia=" + sopraSoglia + ", descProcAgg=" + descProcAgg + ", dtInserimento=" + dtInserimento
				+ ", dtAggiornamento=" + dtAggiornamento + ", idUtenteIns=" + idUtenteIns + ", idUtenteAgg="
				+ idUtenteAgg + ", cigProcAgg=" + cigProcAgg + ", codProcAgg=" + codProcAgg
				+ ", numFornitoriAssociati="
				+ numFornitoriAssociati + ", numAllegatiAssociati=" + numAllegatiAssociati + ", respingibile="
				+ respingibile + ", fase1Esito=" + fase1Esito + ", fase1Rettifica=" + fase1Rettifica + ", fase2Esito="
				+ fase2Esito + ", fase2Rettifica=" + fase2Rettifica + ", esisteAllegatoNonInviato="
				+ esisteAllegatoNonInviato 
//				+ ", proceduraAggiudicazioneDTO=" + proceduraAggiudicazioneDTO + ", varianti="
//				+ Arrays.toString(varianti) + ", fornitori=" + Arrays.toString(fornitori) 
				+ "]";
	}
	public String getOggettoAppalto() {
		return oggettoAppalto;
	}
	public void setOggettoAppalto(String oggettoAppalto) {
		this.oggettoAppalto = oggettoAppalto;
	}
	public Long getIdProceduraAggiudicaz() {
		return idProceduraAggiudicaz;
	}
	public void setIdProceduraAggiudicaz(Long idProceduraAggiudicaz) {
		this.idProceduraAggiudicaz = idProceduraAggiudicaz;
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
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO getProceduraAggiudicazioneDTO() {
		return proceduraAggiudicazioneDTO;
	}
	public void setProceduraAggiudicazioneDTO(
			it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.ProceduraAggiudicazioneDTO proceduraAggiudicazioneDTO) {
		this.proceduraAggiudicazioneDTO = proceduraAggiudicazioneDTO;
	}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO[] getVarianti() {
		return varianti;
	}
	public void setVarianti(it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.VarianteAffidamentoDTO[] varianti) {
		this.varianti = varianti;
	}
	public it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] getFornitori() {
		return fornitori;
	}
	public void setFornitori(it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.FornitoreAffidamentoDTO[] fornitori) {
		this.fornitori = fornitori;
	}
	public long getNumFornitoriAssociati() {
		return numFornitoriAssociati;
	}
	public void setNumFornitoriAssociati(long numFornitoriAssociati) {
		this.numFornitoriAssociati = numFornitoriAssociati;
	}
	public long getNumAllegatiAssociati() {
		return numAllegatiAssociati;
	}
	public void setNumAllegatiAssociati(long numAllegatiAssociati) {
		this.numAllegatiAssociati = numAllegatiAssociati;
	}
	public java.lang.Boolean getRespingibile() {
		return respingibile;
	}
	public void setRespingibile(java.lang.Boolean respingibile) {
		this.respingibile = respingibile;
	}
	public String getFase1Esito() {
		return fase1Esito;
	}
	public void setFase1Esito(String fase1Esito) {
		this.fase1Esito = fase1Esito;
	}
	public String getFase1Rettifica() {
		return fase1Rettifica;
	}
	public void setFase1Rettifica(String fase1Rettifica) {
		this.fase1Rettifica = fase1Rettifica;
	}
	public String getFase2Esito() {
		return fase2Esito;
	}
	public void setFase2Esito(String fase2Esito) {
		this.fase2Esito = fase2Esito;
	}
	public String getFase2Rettifica() {
		return fase2Rettifica;
	}
	public void setFase2Rettifica(String fase2Rettifica) {
		this.fase2Rettifica = fase2Rettifica;
	}
	public boolean isEsisteAllegatoNonInviato() {
		return esisteAllegatoNonInviato;
	}
	public void setEsisteAllegatoNonInviato(boolean esisteAllegatoNonInviato) {
		this.esisteAllegatoNonInviato = esisteAllegatoNonInviato;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
