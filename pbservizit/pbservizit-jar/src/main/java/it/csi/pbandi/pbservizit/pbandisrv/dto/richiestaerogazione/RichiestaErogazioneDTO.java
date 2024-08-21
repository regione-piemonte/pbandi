/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;

import java.util.Date;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;

public class RichiestaErogazioneDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private Long id;
	private String descrizioneCausaleErogazione;
	private Double percentualeErogazione;
	private Double importoRichiesto;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO spesaProgetto;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] fideiussioni;
	private Double percentualeLimite;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO[] fideiussioniTipoTrattamento;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] tipoAllegati;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] tipoAllegatiCompleti;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO[] allegati;
	private it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO estremiBancari;
	private String descBreveCausaleErogazione;
	private Date dtInizioLavori;
	private String direttoreLavori;
	private String residenzaDirettoreLavori;
	private Date dtStipulazioneContratti;
	
	public it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] getTipoAllegatiCompleti() {
		return tipoAllegatiCompleti;
	}
	public void setTipoAllegatiCompleti(
			it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] tipoAllegatiCompleti) {
		this.tipoAllegatiCompleti = tipoAllegatiCompleti;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescrizioneCausaleErogazione() {
		return descrizioneCausaleErogazione;
	}
	public void setDescrizioneCausaleErogazione(String descrizioneCausaleErogazione) {
		this.descrizioneCausaleErogazione = descrizioneCausaleErogazione;
	}
	public Double getPercentualeErogazione() {
		return percentualeErogazione;
	}
	public void setPercentualeErogazione(Double percentualeErogazione) {
		this.percentualeErogazione = percentualeErogazione;
	}
	public Double getImportoRichiesto() {
		return importoRichiesto;
	}
	public void setImportoRichiesto(Double importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO getSpesaProgetto() {
		return spesaProgetto;
	}
	public void setSpesaProgetto(
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.SpesaProgettoDTO spesaProgetto) {
		this.spesaProgetto = spesaProgetto;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] getFideiussioni() {
		return fideiussioni;
	}
	public void setFideiussioni(it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneDTO[] fideiussioni) {
		this.fideiussioni = fideiussioni;
	}
	public Double getPercentualeLimite() {
		return percentualeLimite;
	}
	public void setPercentualeLimite(Double percentualeLimite) {
		this.percentualeLimite = percentualeLimite;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO[] getFideiussioniTipoTrattamento() {
		return fideiussioniTipoTrattamento;
	}
	public void setFideiussioniTipoTrattamento(
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO[] fideiussioniTipoTrattamento) {
		this.fideiussioniTipoTrattamento = fideiussioniTipoTrattamento;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] getTipoAllegati() {
		return tipoAllegati;
	}
	public void setTipoAllegati(it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO[] tipoAllegati) {
		this.tipoAllegati = tipoAllegati;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO getEstremiBancari() {
		return estremiBancari;
	}
	public void setEstremiBancari(
			it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EstremiBancariDTO estremiBancari) {
		this.estremiBancari = estremiBancari;
	}
	public String getDescBreveCausaleErogazione() {
		return descBreveCausaleErogazione;
	}
	public void setDescBreveCausaleErogazione(String descBreveCausaleErogazione) {
		this.descBreveCausaleErogazione = descBreveCausaleErogazione;
	}
	public Date getDtInizioLavori() {
		return dtInizioLavori;
	}
	public void setDtInizioLavori(Date dtInizioLavori) {
		this.dtInizioLavori = dtInizioLavori;
	}
	public String getDirettoreLavori() {
		return direttoreLavori;
	}
	public void setDirettoreLavori(String direttoreLavori) {
		this.direttoreLavori = direttoreLavori;
	}
	public String getResidenzaDirettoreLavori() {
		return residenzaDirettoreLavori;
	}
	public void setResidenzaDirettoreLavori(String residenzaDirettoreLavori) {
		this.residenzaDirettoreLavori = residenzaDirettoreLavori;
	}
	public Date getDtStipulazioneContratti() {
		return dtStipulazioneContratti;
	}
	public void setDtStipulazioneContratti(Date dtStipulazioneContratti) {
		this.dtStipulazioneContratti = dtStipulazioneContratti;
	}
	public it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO[] getAllegati() {
		return allegati;
	}
	public void setAllegati(it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO[] allegati) {
		this.allegati = allegati;
	}

}
