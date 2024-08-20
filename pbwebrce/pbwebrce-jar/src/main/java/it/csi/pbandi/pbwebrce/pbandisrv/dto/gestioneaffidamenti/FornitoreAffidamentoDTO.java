/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti;

import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbwebrce.pbandisrv.dto.gestioneaffidamenti.SubcontrattoAffidDTO;

public class FornitoreAffidamentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idFornitore;
	private Long idAppalto;
	private Long idTipoPercettore;
	private String descTipoPercettore;
	private Date dtInvioVerificaAffidamento;
	private String flgInvioVerificaAffidamento;
	private Long idTipoSoggetto;
	private String codiceFiscaleFornitore;
	private String nomeFornitore;
	private String cognomeFornitore;
	private String partitaIvaFornitore;
	private String denominazioneFornitore;
	private List<SubcontrattoAffidDTO> subcontrattiAffid;

	public Long getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long idAppalto) {
		this.idAppalto = idAppalto;
	}

	public Long getIdTipoPercettore() {
		return idTipoPercettore;
	}

	public void setIdTipoPercettore(Long idTipoPercettore) {
		this.idTipoPercettore = idTipoPercettore;
	}

	public String getDescTipoPercettore() {
		return descTipoPercettore;
	}

	public void setDescTipoPercettore(String descTipoPercettore) {
		this.descTipoPercettore = descTipoPercettore;
	}

	public Date getDtInvioVerificaAffidamento() {
		return dtInvioVerificaAffidamento;
	}

	public void setDtInvioVerificaAffidamento(Date dtInvioVerificaAffidamento) {
		this.dtInvioVerificaAffidamento = dtInvioVerificaAffidamento;
	}

	public String getFlgInvioVerificaAffidamento() {
		return flgInvioVerificaAffidamento;
	}

	public void setFlgInvioVerificaAffidamento(String flgInvioVerificaAffidamento) {
		this.flgInvioVerificaAffidamento = flgInvioVerificaAffidamento;
	}

	public Long getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	public void setIdTipoSoggetto(Long idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public List<SubcontrattoAffidDTO> getSubcontrattiAffid() {
		return subcontrattiAffid;
	}

	public void setSubcontrattiAffid(List<SubcontrattoAffidDTO> subcontrattiAffid) {
		this.subcontrattiAffid = subcontrattiAffid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
