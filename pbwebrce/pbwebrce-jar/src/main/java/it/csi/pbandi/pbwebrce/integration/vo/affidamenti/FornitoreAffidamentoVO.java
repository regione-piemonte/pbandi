/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.util.List;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.PbandiRFornitoreAffidamentoVO;

public class FornitoreAffidamentoVO extends PbandiRFornitoreAffidamentoVO {
	private String descTipoPercettore;
	private BigDecimal idTipoSoggetto;
	private String codiceFiscaleFornitore;
	private String nomeFornitore;
	private String cognomeFornitore;
	private String partitaIvaFornitore;
	private String denominazioneFornitore;
	private List<SubcontrattoAffidVO> subcontrattiAffid;

	public String getDescTipoPercettore() {
		return descTipoPercettore;
	}

	public void setDescTipoPercettore(String descTipoPercettore) {
		this.descTipoPercettore = descTipoPercettore;
	}

	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}

	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
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

	public List<SubcontrattoAffidVO> getSubcontrattiAffid() {
		return subcontrattiAffid;
	}

	public void setSubcontrattiAffid(List<SubcontrattoAffidVO> subcontrattiAffid) {
		this.subcontrattiAffid = subcontrattiAffid;
	}

}
