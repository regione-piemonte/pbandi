/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.ws.rs.GET;

import com.sun.istack.Nullable;


public class AnagraficaBeneficiarioPfVO {
	
	//Persona Fisica
	
	private String cognome;
	
	private String nome;
	
	private String tipoSoggetto;
	
	private String codiceFiscale;
	
	private Date dataDiNascita;
	
	private Long idComuneDiNascita;
	
	private Long idProvinciaDiNascita;
	
	private Long idRegioneDiNascita;
	
	private Long idNazioneDiNascita;
	
	private String comuneDiNascita;
	
	private String provinciaDiNascita;
	
	private String regioneDiNascita;
	
	private String nazioneDiNascita;
    //RESIDENZA:
	private String indirizzoPF;
	
	private Long idComunePF;
	
	private Long idProvinciaPF;
	
	private Long idRegionePF;
	
	private Long idNazionePF;
	
	private String comunePF;
	
	private String provinciaPF;
	
	private String capPF;
	
	private String regionePF;
	
	private String nazionePF;
	
	private String sesso;
	
	private Long idPersonaFisica;
	
	private Date dtInizioValidita;
	
	private Date dtFineValidita;
	
	private Long idUtenteIns;
	
	private Long idUtenteAgg;
	
	private Long idCittadinanza;
	
	private Long idTitoloStudio;
	
	private Long idOccupazione;
	
	private String cfSoggetto;
	private String descTipoSoggCorr;
	private Long idTipoSoggCorr;
	private String idRuoloPF; 
	private String idIndirizzo;
	private String descStatoDomanda;
	private String ruolo; 
	private BigDecimal quotaPartecipazione; 
	private String idDomanda;
	
	private boolean datiAnagrafici; 
	private boolean sedeLegale;
	private BigDecimal progSoggDomanda; 
	private BigDecimal progSoggProgetto; 
	private BigDecimal idSoggettoEnteGiuridico; 
	private BigDecimal progrSoggCorr;
	private Long idComuneEsteraNascita; 
	private Long idNazioneEsteraNascita; 
	private String comuneNascitaEstero;
	private String nazioneNascitaEstera;
	private boolean quota; 
	private String ndg; 
	private BigDecimal idTipoAnagrafica; 
	private String descTipoAnagrafica; 
	private  List<Integer> campiModificati; 
	private Long idDocumento; 
	private String documentoIdentita;
	private long idTipoDocumentoIdentita; 
	private String numeroDocumento;
	private Date dataRilascio;
	private String enteRilascio;
	private Date scadenzaDocumento;
	
	
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getDocumentoIdentita() {
		return documentoIdentita;
	}
	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}
	public long getIdTipoDocumentoIdentita() {
		return idTipoDocumentoIdentita;
	}
	public void setIdTipoDocumentoIdentita(long idTipoDocumentoIdentita) {
		this.idTipoDocumentoIdentita = idTipoDocumentoIdentita;
	}
	
	public Date getDataRilascio() {
		return dataRilascio;
	}
	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}
	public String getEnteRilascio() {
		return enteRilascio;
	}
	public void setEnteRilascio(String enteRilascio) {
		this.enteRilascio = enteRilascio;
	}
	public Date getScadenzaDocumento() {
		return scadenzaDocumento;
	}
	public void setScadenzaDocumento(Date scadenzaDocumento) {
		this.scadenzaDocumento = scadenzaDocumento;
	}
	public List<Integer> getCampiModificati() {
		return campiModificati;
	}
	public void setCampiModificati(List<Integer> campiModificati) {
		this.campiModificati = campiModificati;
	}
	public BigDecimal getProgSoggProgetto() {
		return progSoggProgetto;
	}
	public void setProgSoggProgetto(BigDecimal progSoggProgetto) {
		this.progSoggProgetto = progSoggProgetto;
	}
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}
	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}
	public String getNdg() {
		return ndg;
	}
	public void setNdg(String ndg) {
		this.ndg = ndg;
	}
	public boolean isQuota() {
		return quota;
	}
	public void setQuota(boolean quota) {
		this.quota = quota;
	}
	public Long getIdNazioneEsteraNascita() {
		return idNazioneEsteraNascita;
	}
	public void setIdNazioneEsteraNascita(Long idNazioneEsteraNascita) {
		this.idNazioneEsteraNascita = idNazioneEsteraNascita;
	}
	public String getComuneNascitaEstero() {
		return comuneNascitaEstero;
	}
	public void setComuneNascitaEstero(String comuneNascitaEstero) {
		this.comuneNascitaEstero = comuneNascitaEstero;
	}
	public String getNazioneNascitaEstera() {
		return nazioneNascitaEstera;
	}
	public void setNazioneNascitaEstera(String nazioneNascitaEstera) {
		this.nazioneNascitaEstera = nazioneNascitaEstera;
	}
	public Long getIdComuneEsteraNascita() {
		return idComuneEsteraNascita;
	}
	public void setIdComuneEsteraNascita(Long idComuneEsteraNascita) {
		this.idComuneEsteraNascita = idComuneEsteraNascita;
	}
	public BigDecimal getProgrSoggCorr() {
		return progrSoggCorr;
	}
	public void setProgrSoggCorr(BigDecimal progrSoggCorr) {
		this.progrSoggCorr = progrSoggCorr;
	}
	public Long getIdTipoSoggCorr() {
		return idTipoSoggCorr;
	}
	public void setIdTipoSoggCorr(Long idTipoSoggCorr) {
		this.idTipoSoggCorr = idTipoSoggCorr;
	}
	public BigDecimal getIdSoggettoEnteGiuridico() {
		return idSoggettoEnteGiuridico;
	}
	public void setIdSoggettoEnteGiuridico(BigDecimal idSoggettoEnteGiuridico) {
		this.idSoggettoEnteGiuridico = idSoggettoEnteGiuridico;
	}
	public BigDecimal getProgSoggDomanda() {
		return progSoggDomanda;
	}
	public void setProgSoggDomanda(BigDecimal progSoggDomanda) {
		this.progSoggDomanda = progSoggDomanda;
	}
	public boolean isDatiAnagrafici() {
		return datiAnagrafici;
	}
	public void setDatiAnagrafici(boolean datiAnagrafici) {
		this.datiAnagrafici = datiAnagrafici;
	}
	public boolean isSedeLegale() {
		return sedeLegale;
	}
	public void setSedeLegale(boolean sedeLegale) {
		this.sedeLegale = sedeLegale;
	}
	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getIdDomanda() {
		return idDomanda;
	}
	public void setQuotaPartecipazione(BigDecimal quotaPartecipazione) {
		this.quotaPartecipazione = quotaPartecipazione;
	}
	public BigDecimal getQuotaPartecipazione() {
		return quotaPartecipazione;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getRuolo() {
		return ruolo;
	}
	
	public String getDescStatoDomanda() {
		return descStatoDomanda;
	}
	public void setDescStatoDomanda(String descStatoDomanda) {
		this.descStatoDomanda = descStatoDomanda;
	}
	public String getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(String idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	
	public String getIdRuoloPF() {
		return idRuoloPF;
	}
	public void setIdRuoloPF(String idRuoloPF) {
		this.idRuoloPF = idRuoloPF;
	}
	
	public void setDescTipoSoggCorr(String descTipoSoggCorr) {
		this.descTipoSoggCorr = descTipoSoggCorr;
	}
	public String getDescTipoSoggCorr() {
		return descTipoSoggCorr;
	}
	
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getComuneDiNascita() {
		return comuneDiNascita;
	}
	public void setComuneDiNascita(String comuneDiNascita) {
		this.comuneDiNascita = comuneDiNascita;
	}
	public String getProvinciaDiNascita() {
		return provinciaDiNascita;
	}
	public void setProvinciaDiNascita(String provinciaDiNascita) {
		this.provinciaDiNascita = provinciaDiNascita;
	}
	public String getRegioneDiNascita() {
		return regioneDiNascita;
	}
	public void setRegioneDiNascita(String regioneDiNascita) {
		this.regioneDiNascita = regioneDiNascita;
	}
	public String getNazioneDiNascita() {
		return nazioneDiNascita;
	}
	public void setNazioneDiNascita(String nazioneDiNascita) {
		this.nazioneDiNascita = nazioneDiNascita;
	}
	public String getIndirizzoPF() {
		return indirizzoPF;
	}
	public void setIndirizzoPF(String indirizzoPF) {
		this.indirizzoPF = indirizzoPF;
	}
	
	public String getComunePF() {
		return comunePF;
	}
	public void setComunePF(String comunePF) {
		this.comunePF = comunePF;
	}
	public String getProvinciaPF() {
		return provinciaPF;
	}
	public void setProvinciaPF(String provinciaPF) {
		this.provinciaPF = provinciaPF;
	}
	public String getCapPF() {
		return capPF;
	}
	public void setCapPF(String capPF) {
		this.capPF = capPF;
	}
	public String getRegionePF() {
		return regionePF;
	}
	public void setRegionePF(String regionePF) {
		this.regionePF = regionePF;
	}
	public String getNazionePF() {
		return nazionePF;
	}
	public void setNazionePF(String nazionePF) {
		this.nazionePF = nazionePF;
	}
	
	
	public Date getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public Long getIdComuneDiNascita() {
		return idComuneDiNascita;
	}
	public void setIdComuneDiNascita(Long idComuneDiNascita) {
		this.idComuneDiNascita = idComuneDiNascita;
	}
	public Long getIdProvinciaDiNascita() {
		return idProvinciaDiNascita;
	}
	public void setIdProvinciaDiNascita(Long idProvinciaDiNascita) {
		this.idProvinciaDiNascita = idProvinciaDiNascita;
	}
	public Long getIdRegioneDiNascita() {
		return idRegioneDiNascita;
	}
	public void setIdRegioneDiNascita(Long idRegioneDiNascita) {
		this.idRegioneDiNascita = idRegioneDiNascita;
	}
	public Long getIdNazioneDiNascita() {
		return idNazioneDiNascita;
	}
	public void setIdNazioneDiNascita(Long idNazioneDiNascita) {
		this.idNazioneDiNascita = idNazioneDiNascita;
	}
	public Long getIdComunePF() {
		return idComunePF;
	}
	public void setIdComunePF(Long idComunePF) {
		this.idComunePF = idComunePF;
	}
	public Long getIdProvinciaPF() {
		return idProvinciaPF;
	}
	public void setIdProvinciaPF(Long idProvinciaPF) {
		this.idProvinciaPF = idProvinciaPF;
	}
	public Long getIdRegionePF() {
		return idRegionePF;
	}
	public void setIdRegionePF(Long idRegionePF) {
		this.idRegionePF = idRegionePF;
	}
	public Long getIdNazionePF() {
		return idNazionePF;
	}
	public void setIdNazionePF(Long idNazionePF) {
		this.idNazionePF = idNazionePF;
	}
	
	
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Long getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdPersonaFisica(Long idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public Long getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public Long getIdCittadinanza() {
		return idCittadinanza;
	}
	public void setIdCittadinanza(Long idCittadinanza) {
		this.idCittadinanza = idCittadinanza;
	}
	public Long getIdTitoloStudio() {
		return idTitoloStudio;
	}
	public void setIdTitoloStudio(Long idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}
	public Long getIdOccupazione() {
		return idOccupazione;
	}
	public void setIdOccupazione(Long idOccupazione) {
		this.idOccupazione = idOccupazione;
	}
	
	public AnagraficaBeneficiarioPfVO() {
		super();
		
	}
	
	@Override
	public String toString() {
		return "AnagraficaBeneficiarioPfVO [cognome=" + cognome + ", nome=" + nome + ", tipoSoggetto=" + tipoSoggetto
				+ ", codiceFiscale=" + codiceFiscale + ", dataDiNascita=" + dataDiNascita + ", idComuneDiNascita="
				+ idComuneDiNascita + ", idProvinciaDiNascita=" + idProvinciaDiNascita + ", idRegioneDiNascita="
				+ idRegioneDiNascita + ", idNazioneDiNascita=" + idNazioneDiNascita + ", comuneDiNascita="
				+ comuneDiNascita + ", provinciaDiNascita=" + provinciaDiNascita + ", regioneDiNascita="
				+ regioneDiNascita + ", nazioneDiNascita=" + nazioneDiNascita + ", indirizzoPF=" + indirizzoPF
				+ ", idComunePF=" + idComunePF + ", idProvinciaPF=" + idProvinciaPF + ", idRegionePF=" + idRegionePF
				+ ", idNazionePF=" + idNazionePF + ", comunePF=" + comunePF + ", provinciaPF=" + provinciaPF
				+ ", capPF=" + capPF + ", regionePF=" + regionePF + ", nazionePF=" + nazionePF + ", sesso=" + sesso
				+ ", idPersonaFisica=" + idPersonaFisica + ", dtInizioValidita=" + dtInizioValidita
				+ ", dtFineValidita=" + dtFineValidita + ", idUtenteIns=" + idUtenteIns + ", idCittadinanza="
				+ idCittadinanza + ", idTitoloStudio=" + idTitoloStudio + ", idOccupazione=" + idOccupazione + "]";
	}
	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getCfSoggetto() {
		return cfSoggetto;
	}
	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}
	
	
   
	
	
    
}
