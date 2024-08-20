/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GaranziaVO {
	
	private String idSoggetto;
	private Long idProgetto;
	private Long idBando;
	private String progrSoggettoProgetto;
	private Long idModalitaAgevolazione;
	private Long idModalitaAgevolazioneRif;
	
	private String codiceVisualizzato;
	private String descrizioneBando;
	private String codiceProgetto;
	private String codiceFiscale;
	private String nag;
	private String partitaIva;
	private String denominazioneCognomeNome;
	private Long idStatoEscussione;
	private String statoEscussione;
	private String codBanca;
	private String denominazioneBanca;
	private Date dataRicevimentoEscussione;
	private Long idTipoEscussione;
	private String tipoEscussione;
	private String statoCredito;
	private Date dataStato;
	private double importoRichiesto;
	private double importoApprovato;
	private String ndg; 
	private BigDecimal idEscussione;
	private String numProtoRichiesta;
	private Date dataNotifica;
	private String numProtoNotifica;
	private String causaleBonifico;
	private String ibanBancaBenef;
	private String note;
	private String descBanca;
	private Long idBanca;
	private Long idSoggProgBancaBen;
	private Boolean esitoInviato;
	private Boolean isFondoMisto;
	
	private List<DettaglioGaranziaVO> listaDettagli;


	public GaranziaVO() {
	}



	public GaranziaVO(String idSoggetto, Long idProgetto, Long idBando, String progrSoggettoProgetto,
			Long idModalitaAgevolazione, Long idModalitaAgevolazioneRif, String codiceVisualizzato,
			String descrizioneBando, String codiceProgetto, String codiceFiscale, String nag, String partitaIva,
			String denominazioneCognomeNome, Long idStatoEscussione, String statoEscussione, String codBanca,
			String denominazioneBanca, Date dataRicevimentoEscussione, Long idTipoEscussione, String tipoEscussione,
			String statoCredito, Date dataStato, double importoRichiesto, double importoApprovato, String ndg,
			BigDecimal idEscussione, String numProtoRichiesta, Date dataNotifica, String numProtoNotifica,
			String causaleBonifico, String ibanBancaBenef, String note, String descBanca, Long idBanca,
			Long idSoggProgBancaBen, Boolean esitoInviato, Boolean isFondoMisto,
			List<DettaglioGaranziaVO> listaDettagli) {
		this.idSoggetto = idSoggetto;
		this.idProgetto = idProgetto;
		this.idBando = idBando;
		this.progrSoggettoProgetto = progrSoggettoProgetto;
		this.idModalitaAgevolazione = idModalitaAgevolazione;
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
		this.codiceVisualizzato = codiceVisualizzato;
		this.descrizioneBando = descrizioneBando;
		this.codiceProgetto = codiceProgetto;
		this.codiceFiscale = codiceFiscale;
		this.nag = nag;
		this.partitaIva = partitaIva;
		this.denominazioneCognomeNome = denominazioneCognomeNome;
		this.idStatoEscussione = idStatoEscussione;
		this.statoEscussione = statoEscussione;
		this.codBanca = codBanca;
		this.denominazioneBanca = denominazioneBanca;
		this.dataRicevimentoEscussione = dataRicevimentoEscussione;
		this.idTipoEscussione = idTipoEscussione;
		this.tipoEscussione = tipoEscussione;
		this.statoCredito = statoCredito;
		this.dataStato = dataStato;
		this.importoRichiesto = importoRichiesto;
		this.importoApprovato = importoApprovato;
		this.ndg = ndg;
		this.idEscussione = idEscussione;
		this.numProtoRichiesta = numProtoRichiesta;
		this.dataNotifica = dataNotifica;
		this.numProtoNotifica = numProtoNotifica;
		this.causaleBonifico = causaleBonifico;
		this.ibanBancaBenef = ibanBancaBenef;
		this.note = note;
		this.descBanca = descBanca;
		this.idBanca = idBanca;
		this.idSoggProgBancaBen = idSoggProgBancaBen;
		this.esitoInviato = esitoInviato;
		this.isFondoMisto = isFondoMisto;
		this.listaDettagli = listaDettagli;
	}



	public String getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public Long getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}


	public String getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}


	public void setProgrSoggettoProgetto(String progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}


	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}


	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}


	public String getDescrizioneBando() {
		return descrizioneBando;
	}


	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}


	public String getCodiceProgetto() {
		return codiceProgetto;
	}


	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getNag() {
		return nag;
	}


	public void setNag(String nag) {
		this.nag = nag;
	}


	public String getPartitaIva() {
		return partitaIva;
	}


	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public String getDenominazioneCognomeNome() {
		return denominazioneCognomeNome;
	}


	public void setDenominazioneCognomeNome(String denominazioneCognomeNome) {
		this.denominazioneCognomeNome = denominazioneCognomeNome;
	}


	public Long getIdStatoEscussione() {
		return idStatoEscussione;
	}


	public void setIdStatoEscussione(Long idStatoEscussione) {
		this.idStatoEscussione = idStatoEscussione;
	}


	public String getStatoEscussione() {
		return statoEscussione;
	}


	public void setStatoEscussione(String statoEscussione) {
		this.statoEscussione = statoEscussione;
	}


	public String getCodBanca() {
		return codBanca;
	}


	public void setCodBanca(String codBanca) {
		this.codBanca = codBanca;
	}


	public String getDenominazioneBanca() {
		return denominazioneBanca;
	}


	public void setDenominazioneBanca(String denominazioneBanca) {
		this.denominazioneBanca = denominazioneBanca;
	}


	public Date getDataRicevimentoEscussione() {
		return dataRicevimentoEscussione;
	}


	public void setDataRicevimentoEscussione(Date dataRicevimentoEscussione) {
		this.dataRicevimentoEscussione = dataRicevimentoEscussione;
	}


	public Long getIdTipoEscussione() {
		return idTipoEscussione;
	}


	public void setIdTipoEscussione(Long idTipoEscussione) {
		this.idTipoEscussione = idTipoEscussione;
	}


	public String getTipoEscussione() {
		return tipoEscussione;
	}


	public void setTipoEscussione(String tipoEscussione) {
		this.tipoEscussione = tipoEscussione;
	}


	public String getStatoCredito() {
		return statoCredito;
	}


	public void setStatoCredito(String statoCredito) {
		this.statoCredito = statoCredito;
	}


	public Date getDataStato() {
		return dataStato;
	}


	public void setDataStato(Date dataStato) {
		this.dataStato = dataStato;
	}


	public double getImportoRichiesto() {
		return importoRichiesto;
	}


	public void setImportoRichiesto(double importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}


	public double getImportoApprovato() {
		return importoApprovato;
	}


	public void setImportoApprovato(double importoApprovato) {
		this.importoApprovato = importoApprovato;
	}


	public String getNdg() {
		return ndg;
	}


	public void setNdg(String ndg) {
		this.ndg = ndg;
	}


	public BigDecimal getIdEscussione() {
		return idEscussione;
	}


	public void setIdEscussione(BigDecimal idEscussione) {
		this.idEscussione = idEscussione;
	}


	public String getNumProtoRichiesta() {
		return numProtoRichiesta;
	}


	public void setNumProtoRichiesta(String numProtoRichiesta) {
		this.numProtoRichiesta = numProtoRichiesta;
	}


	public Date getDataNotifica() {
		return dataNotifica;
	}


	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}


	public String getNumProtoNotifica() {
		return numProtoNotifica;
	}


	public void setNumProtoNotifica(String numProtoNotifica) {
		this.numProtoNotifica = numProtoNotifica;
	}


	public String getCausaleBonifico() {
		return causaleBonifico;
	}


	public void setCausaleBonifico(String causaleBonifico) {
		this.causaleBonifico = causaleBonifico;
	}


	public String getIbanBancaBenef() {
		return ibanBancaBenef;
	}


	public void setIbanBancaBenef(String ibanBancaBenef) {
		this.ibanBancaBenef = ibanBancaBenef;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getDescBanca() {
		return descBanca;
	}


	public void setDescBanca(String descBanca) {
		this.descBanca = descBanca;
	}


	public Long getIdBanca() {
		return idBanca;
	}


	public void setIdBanca(Long idBanca) {
		this.idBanca = idBanca;
	}


	public Long getIdSoggProgBancaBen() {
		return idSoggProgBancaBen;
	}


	public void setIdSoggProgBancaBen(Long idSoggProgBancaBen) {
		this.idSoggProgBancaBen = idSoggProgBancaBen;
	}


	public Boolean getEsitoInviato() {
		return esitoInviato;
	}


	public void setEsitoInviato(Boolean esitoInviato) {
		this.esitoInviato = esitoInviato;
	}


	public List<DettaglioGaranziaVO> getListaDettagli() {
		return listaDettagli;
	}


	public void setListaDettagli(List<DettaglioGaranziaVO> listaDettagli) {
		this.listaDettagli = listaDettagli;
	}

	public Long getIdBando() {
		return idBando;
	}

	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}

	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}

	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}

	public Long getIdModalitaAgevolazioneRif() {
		return idModalitaAgevolazioneRif;
	}

	public void setIdModalitaAgevolazioneRif(Long idModalitaAgevolazioneRif) {
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
	}



	public Boolean getIsFondoMisto() {
		return isFondoMisto;
	}



	public void setIsFondoMisto(Boolean isFondoMisto) {
		this.isFondoMisto = isFondoMisto;
	}



	@Override
	public String toString() {
		return "GaranziaVO [idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto + ", idBando=" + idBando
				+ ", progrSoggettoProgetto=" + progrSoggettoProgetto + ", idModalitaAgevolazione="
				+ idModalitaAgevolazione + ", idModalitaAgevolazioneRif=" + idModalitaAgevolazioneRif
				+ ", codiceVisualizzato=" + codiceVisualizzato + ", descrizioneBando=" + descrizioneBando
				+ ", codiceProgetto=" + codiceProgetto + ", codiceFiscale=" + codiceFiscale + ", nag=" + nag
				+ ", partitaIva=" + partitaIva + ", denominazioneCognomeNome=" + denominazioneCognomeNome
				+ ", idStatoEscussione=" + idStatoEscussione + ", statoEscussione=" + statoEscussione + ", codBanca="
				+ codBanca + ", denominazioneBanca=" + denominazioneBanca + ", dataRicevimentoEscussione="
				+ dataRicevimentoEscussione + ", idTipoEscussione=" + idTipoEscussione + ", tipoEscussione="
				+ tipoEscussione + ", statoCredito=" + statoCredito + ", dataStato=" + dataStato + ", importoRichiesto="
				+ importoRichiesto + ", importoApprovato=" + importoApprovato + ", ndg=" + ndg + ", idEscussione="
				+ idEscussione + ", numProtoRichiesta=" + numProtoRichiesta + ", dataNotifica=" + dataNotifica
				+ ", numProtoNotifica=" + numProtoNotifica + ", causaleBonifico=" + causaleBonifico
				+ ", ibanBancaBenef=" + ibanBancaBenef + ", note=" + note + ", descBanca=" + descBanca + ", idBanca="
				+ idBanca + ", idSoggProgBancaBen=" + idSoggProgBancaBen + ", esitoInviato=" + esitoInviato
				+ ", isFondoMisto=" + isFondoMisto + ", listaDettagli=" + listaDettagli + "]";
	}

	
	
}
