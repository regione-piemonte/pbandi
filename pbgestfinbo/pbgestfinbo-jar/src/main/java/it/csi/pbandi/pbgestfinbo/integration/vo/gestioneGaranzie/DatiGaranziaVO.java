/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.gestioneGaranzie;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DatiGaranziaVO {
	
	private Long idProgetto;
	private Long idSoggetto;
	private Long progrSoggettoProgetto;
	private Long idBando;
	private Long idEscussione;
	
	private String titoloBando;
	private String codiceProgetto;
	private String ndg;
	private String denomBeneficiario;
	private String denomBanca;
	private Date dataRichiestaEscussione;
	private Integer idTipoEscussione;
	private String descTipoEscussione;
	private Integer idStatoEscussione;
	private String descStatoEscussione;
	private Date dataStato; // Corrisponde a DT_INIZIO_VALIDITA
	private BigDecimal importoRichiesto;
	private BigDecimal importoApprovato;
	private Integer idModalitaAgevolazione;
	private Integer idModalitaAgevolazioneRif;
	
	private String codiceFiscale;
	private String partitaIva;
	private Boolean isFondoMisto;
	//private Boolean esitoInviato;
	
	
	private List<DatiDettaglioGaranziaVO> listaDettagli;


	public DatiGaranziaVO() {
	}


	public DatiGaranziaVO(Long idProgetto, Long idSoggetto, Long progrSoggettoProgetto, Long idBando, Long idEscussione,
			String titoloBando, String codiceProgetto, String ndg, String denomBeneficiario, String denomBanca,
			Date dataRichiestaEscussione, Integer idTipoEscussione, String descTipoEscussione,
			Integer idStatoEscussione, String descStatoEscussione, Date dataStato, BigDecimal importoRichiesto,
			BigDecimal importoApprovato, Integer idModalitaAgevolazione, Integer idModalitaAgevolazioneRif,
			String codiceFiscale, String partitaIva, Boolean isFondoMisto,
			List<DatiDettaglioGaranziaVO> listaDettagli) {
		this.idProgetto = idProgetto;
		this.idSoggetto = idSoggetto;
		this.progrSoggettoProgetto = progrSoggettoProgetto;
		this.idBando = idBando;
		this.idEscussione = idEscussione;
		this.titoloBando = titoloBando;
		this.codiceProgetto = codiceProgetto;
		this.ndg = ndg;
		this.denomBeneficiario = denomBeneficiario;
		this.denomBanca = denomBanca;
		this.dataRichiestaEscussione = dataRichiestaEscussione;
		this.idTipoEscussione = idTipoEscussione;
		this.descTipoEscussione = descTipoEscussione;
		this.idStatoEscussione = idStatoEscussione;
		this.descStatoEscussione = descStatoEscussione;
		this.dataStato = dataStato;
		this.importoRichiesto = importoRichiesto;
		this.importoApprovato = importoApprovato;
		this.idModalitaAgevolazione = idModalitaAgevolazione;
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
		this.codiceFiscale = codiceFiscale;
		this.partitaIva = partitaIva;
		this.isFondoMisto = isFondoMisto;
		this.listaDettagli = listaDettagli;
	}





	public Long getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}


	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}


	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}


	public Long getIdBando() {
		return idBando;
	}


	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}


	public Long getIdEscussione() {
		return idEscussione;
	}


	public void setIdEscussione(Long idEscussione) {
		this.idEscussione = idEscussione;
	}


	public String getTitoloBando() {
		return titoloBando;
	}


	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}


	public String getCodiceProgetto() {
		return codiceProgetto;
	}


	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}


	public String getNdg() {
		return ndg;
	}


	public void setNdg(String ndg) {
		this.ndg = ndg;
	}


	public String getDenomBeneficiario() {
		return denomBeneficiario;
	}


	public void setDenomBeneficiario(String denomBeneficiario) {
		this.denomBeneficiario = denomBeneficiario;
	}


	public String getDenomBanca() {
		return denomBanca;
	}


	public void setDenomBanca(String denomBanca) {
		this.denomBanca = denomBanca;
	}


	public Date getDataRichiestaEscussione() {
		return dataRichiestaEscussione;
	}


	public void setDataRichiestaEscussione(Date dataRichiestaEscussione) {
		this.dataRichiestaEscussione = dataRichiestaEscussione;
	}


	public Integer getIdTipoEscussione() {
		return idTipoEscussione;
	}


	public void setIdTipoEscussione(Integer idTipoEscussione) {
		this.idTipoEscussione = idTipoEscussione;
	}


	public String getDescTipoEscussione() {
		return descTipoEscussione;
	}


	public void setDescTipoEscussione(String descTipoEscussione) {
		this.descTipoEscussione = descTipoEscussione;
	}


	public Integer getIdStatoEscussione() {
		return idStatoEscussione;
	}


	public void setIdStatoEscussione(Integer idStatoEscussione) {
		this.idStatoEscussione = idStatoEscussione;
	}


	public String getDescStatoEscussione() {
		return descStatoEscussione;
	}


	public void setDescStatoEscussione(String descStatoEscussione) {
		this.descStatoEscussione = descStatoEscussione;
	}


	public Date getDataStato() {
		return dataStato;
	}


	public void setDataStato(Date dataStato) {
		this.dataStato = dataStato;
	}


	public BigDecimal getImportoRichiesto() {
		return importoRichiesto;
	}


	public void setImportoRichiesto(BigDecimal importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}


	public BigDecimal getImportoApprovato() {
		return importoApprovato;
	}


	public void setImportoApprovato(BigDecimal importoApprovato) {
		this.importoApprovato = importoApprovato;
	}


	public Integer getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}


	public void setIdModalitaAgevolazione(Integer idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}


	public Integer getIdModalitaAgevolazioneRif() {
		return idModalitaAgevolazioneRif;
	}


	public void setIdModalitaAgevolazioneRif(Integer idModalitaAgevolazioneRif) {
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getPartitaIva() {
		return partitaIva;
	}


	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public List<DatiDettaglioGaranziaVO> getListaDettagli() {
		return listaDettagli;
	}


	public void setListaDettagli(List<DatiDettaglioGaranziaVO> listaDettagli) {
		this.listaDettagli = listaDettagli;
	}


	public Boolean getIsFondoMisto() {
		return isFondoMisto;
	}


	public void setIsFondoMisto(Boolean isFondoMisto) {
		this.isFondoMisto = isFondoMisto;
	}


	@Override
	public String toString() {
		return "DatiGaranziaVO [idProgetto=" + idProgetto + ", idSoggetto=" + idSoggetto + ", progrSoggettoProgetto="
				+ progrSoggettoProgetto + ", idBando=" + idBando + ", idEscussione=" + idEscussione + ", titoloBando="
				+ titoloBando + ", codiceProgetto=" + codiceProgetto + ", ndg=" + ndg + ", denomBeneficiario="
				+ denomBeneficiario + ", denomBanca=" + denomBanca + ", dataRichiestaEscussione="
				+ dataRichiestaEscussione + ", idTipoEscussione=" + idTipoEscussione + ", descTipoEscussione="
				+ descTipoEscussione + ", idStatoEscussione=" + idStatoEscussione + ", descStatoEscussione="
				+ descStatoEscussione + ", dataStato=" + dataStato + ", importoRichiesto=" + importoRichiesto
				+ ", importoApprovato=" + importoApprovato + ", idModalitaAgevolazione=" + idModalitaAgevolazione
				+ ", idModalitaAgevolazioneRif=" + idModalitaAgevolazioneRif + ", codiceFiscale=" + codiceFiscale
				+ ", partitaIva=" + partitaIva + ", isFondoMisto=" + isFondoMisto + ", listaDettagli=" + listaDettagli
				+ "]";
	}


}
