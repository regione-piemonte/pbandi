package it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa;

import java.math.BigDecimal;
import java.util.Date;

public class DocumentoDiSpesaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 7L;

	private String codiceFiscaleFornitore = null;
	private String codiceProgetto = null;
	private String cognomeFornitore = null;
	private String descrizioneDocumentoDiSpesa = null;
	private String destinazioneTrasferta = null;
	private String denominazioneFornitore = null;
	private String descStatoDocumentoSpesa = null;
	private String descTipologiaDocumentoDiSpesa = null;
	private String descBreveTipoDocumentoDiSpesa = null;
	private Date dataDocumentoDiSpesa = null;
	private Date dataDocumentoDiSpesaDiRiferimento = null;
	private String descTipologiaFornitore = null;
	private Double durataTrasferta = null;
	private Long idBeneficiario = null;
	private Long idDocRiferimento = null;
	private Long idDocumentoDiSpesa = null;
	private Long idFornitore = null;
	private Long idProgetto = null;
	private Long idSoggetto = null;
	private Long idSoggettoPartner = null;
	private Long idTipoDocumentoDiSpesa = null;
	private Long idTipoFornitore = null;
	private Long idTipoOggettoAttivita = null;
	private Double imponibile = null;
	private Double importoIva = null;
	private Double importoIvaACosto = null;
	private Double importoRendicontabile = null;
	private Double importoTotaleDocumentoIvato = null;
	private Double importoRitenutaDAcconto = null;
	private Boolean isGestitiNelProgetto = null;
	private Boolean isRicercaPerCapofila = null;
	private Boolean isRicercaPerTutti = null;
	private Boolean isRicercaPerPartner = null;
	private String nomeFornitore = null;
	private String numeroDocumento = null;
	private String numeroDocumentoDiSpesa = null;
	private String numeroDocumentoDiSpesaDiRiferimento = null;
	private String partitaIvaFornitore = null;
	private String partner = null;
	private Double costoOrario = null;
	private String task = null;
	private Double importoTotaleValidato = null;
	private Double importoTotaleRendicontato = null;
	private Long progrFornitoreQualifica = null;
	private Double importoTotaleQuietanzato = null;
	private Long idStatoDocumentoSpesa = null;
	private Double rendicontabileQuietanzato = null;
	private String noteValidazione = null;
	private String descBreveStatoDocumentoSpesa = null;
	private String tipoInvio = null;
	private Boolean flagElettronico = null;
	private Long idAppalto = null;
	private String descrizioneAppalto = null;
	private String flagElettXml = null;


	public Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}

	public void setDataDocumentoDiSpesa(Date val) {
		dataDocumentoDiSpesa = val;
	}

	public Date getDataDocumentoDiSpesaDiRiferimento() {
		return dataDocumentoDiSpesaDiRiferimento;
	}

	public void setDataDocumentoDiSpesaDiRiferimento(Date val) {
		dataDocumentoDiSpesaDiRiferimento = val;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String val) {
		codiceFiscaleFornitore = val;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String val) {
		codiceProgetto = val;
	}

	public String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(String val) {
		cognomeFornitore = val;
	}

	public String getDescrizioneDocumentoDiSpesa() {
		return descrizioneDocumentoDiSpesa;
	}

	public void setDescrizioneDocumentoDiSpesa(String val) {
		descrizioneDocumentoDiSpesa = val;
	}

	public String getDestinazioneTrasferta() {
		return destinazioneTrasferta;
	}

	public void setDestinazioneTrasferta(String val) {
		destinazioneTrasferta = val;
	}

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String val) {
		denominazioneFornitore = val;
	}

	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}

	public void setDescStatoDocumentoSpesa(String val) {
		descStatoDocumentoSpesa = val;
	}

	public String getDescTipologiaDocumentoDiSpesa() {
		return descTipologiaDocumentoDiSpesa;
	}

	public void setDescTipologiaDocumentoDiSpesa(String val) {
		descTipologiaDocumentoDiSpesa = val;
	}

	public String getDescBreveTipoDocumentoDiSpesa() {
		return descBreveTipoDocumentoDiSpesa;
	}

	public void setDescBreveTipoDocumentoDiSpesa(String val) {
		descBreveTipoDocumentoDiSpesa = val;
	}

	public String getDescTipologiaFornitore() {
		return descTipologiaFornitore;
	}

	public void setDescTipologiaFornitore(String val) {
		descTipologiaFornitore = val;
	}

	public Double getDurataTrasferta() {
		return durataTrasferta;
	}

	public void setDurataTrasferta(Double val) {
		durataTrasferta = val;
	}

	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(Long val) {
		idBeneficiario = val;
	}

	public Long getIdDocRiferimento() {
		return idDocRiferimento;
	}

	public void setIdDocRiferimento(Long val) {
		idDocRiferimento = val;
	}

	public Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(Long val) {
		idDocumentoDiSpesa = val;
	}

	public Long getIdFornitore() {
		return idFornitore;
	}

	public void setIdFornitore(Long val) {
		idFornitore = val;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long val) {
		idProgetto = val;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long val) {
		idSoggetto = val;
	}

	public Long getIdSoggettoPartner() {
		return idSoggettoPartner;
	}

	public void setIdSoggettoPartner(Long val) {
		idSoggettoPartner = val;
	}

	public Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}

	public void setIdTipoDocumentoDiSpesa(Long val) {
		idTipoDocumentoDiSpesa = val;
	}

	public Long getIdTipoFornitore() {
		return idTipoFornitore;
	}

	public void setIdTipoFornitore(Long val) {
		idTipoFornitore = val;
	}

	public Long getIdTipoOggettoAttivita() {
		return idTipoOggettoAttivita;
	}

	public void setIdTipoOggettoAttivita(Long val) {
		idTipoOggettoAttivita = val;
	}

	public Double getImponibile() {
		return imponibile;
	}

	public void setImponibile(Double val) {
		imponibile = val;
	}

	public Double getImportoIva() {
		return importoIva;
	}

	public void setImportoIva(Double val) {
		importoIva = val;
	}

	public Double getImportoIvaACosto() {
		return importoIvaACosto;
	}

	public void setImportoIvaACosto(Double val) {
		importoIvaACosto = val;
	}

	public Double getImportoRendicontabile() {
		return importoRendicontabile;
	}

	public void setImportoRendicontabile(Double val) {
		importoRendicontabile = val;
	}

	public Double getImportoTotaleDocumentoIvato() {
		return importoTotaleDocumentoIvato;
	}

	public void setImportoTotaleDocumentoIvato(Double val) {
		importoTotaleDocumentoIvato = val;
	}

	public Double getImportoRitenutaDAcconto() {
		return importoRitenutaDAcconto;
	}

	public void setImportoRitenutaDAcconto(Double val) {
		importoRitenutaDAcconto = val;
	}

	public Boolean getIsGestitiNelProgetto() {
		return isGestitiNelProgetto;
	}

	public void setIsGestitiNelProgetto(Boolean val) {
		isGestitiNelProgetto = val;
	}

	public Boolean getIsRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}

	public void setIsRicercaPerCapofila(Boolean val) {
		isRicercaPerCapofila = val;
	}

	public Boolean getIsRicercaPerTutti() {
		return isRicercaPerTutti;
	}

	public void setIsRicercaPerTutti(Boolean val) {
		isRicercaPerTutti = val;
	}

	public Boolean getIsRicercaPerPartner() {
		return isRicercaPerPartner;
	}

	public void setIsRicercaPerPartner(Boolean val) {
		isRicercaPerPartner = val;
	}

	public String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(String val) {
		nomeFornitore = val;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String val) {
		numeroDocumento = val;
	}

	public String getNumeroDocumentoDiSpesa() {
		return numeroDocumentoDiSpesa;
	}

	public void setNumeroDocumentoDiSpesa(String val) {
		numeroDocumentoDiSpesa = val;
	}

	public String getNumeroDocumentoDiSpesaDiRiferimento() {
		return numeroDocumentoDiSpesaDiRiferimento;
	}

	public void setNumeroDocumentoDiSpesaDiRiferimento(String val) {
		numeroDocumentoDiSpesaDiRiferimento = val;
	}

	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}

	public void setPartitaIvaFornitore(String val) {
		partitaIvaFornitore = val;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String val) {
		partner = val;
	}

	public Double getCostoOrario() {
		return costoOrario;
	}

	public void setCostoOrario(Double val) {
		costoOrario = val;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String val) {
		task = val;
	}

	public Double getImportoTotaleValidato() {
		return importoTotaleValidato;
	}

	public void setImportoTotaleValidato(Double val) {
		importoTotaleValidato = val;
	}

	public Double getImportoTotaleRendicontato() {
		return importoTotaleRendicontato;
	}

	public void setImportoTotaleRendicontato(Double val) {
		importoTotaleRendicontato = val;
	}

	public Long getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}

	public void setProgrFornitoreQualifica(Long val) {
		progrFornitoreQualifica = val;
	}

	public Double getImportoTotaleQuietanzato() {
		return importoTotaleQuietanzato;
	}

	public void setImportoTotaleQuietanzato(Double val) {
		importoTotaleQuietanzato = val;
	}

	public Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(Long val) {
		idStatoDocumentoSpesa = val;
	}

	public Double getRendicontabileQuietanzato() {
		return rendicontabileQuietanzato;
	}

	public void setRendicontabileQuietanzato(Double val) {
		rendicontabileQuietanzato = val;
	}

	public String getNoteValidazione() {
		return noteValidazione;
	}

	public void setNoteValidazione(String val) {
		noteValidazione = val;
	}

	public String getDescBreveStatoDocumentoSpesa() {
		return descBreveStatoDocumentoSpesa;
	}

	public void setDescBreveStatoDocumentoSpesa(String val) {
		descBreveStatoDocumentoSpesa = val;
	}

	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String val) {
		tipoInvio = val;
	}

	public Boolean getFlagElettronico() {
		return flagElettronico;
	}

	public void setFlagElettronico(Boolean val) {
		flagElettronico = val;
	}

	public Long getIdAppalto() {
		return idAppalto;
	}

	public void setIdAppalto(Long val) {
		idAppalto = val;
	}

	public String getDescrizioneAppalto() {
		return descrizioneAppalto;
	}

	public void setDescrizioneAppalto(String val) {
		descrizioneAppalto = val;
	}

	public String getFlagElettXml() {
		return flagElettXml;
	}

	public void setFlagElettXml(String val) {
		flagElettXml = val;
	}

	private BigDecimal idParametroCompenso;
	private BigDecimal ggLavorabiliMese;
	private BigDecimal sospBrevi;
	private BigDecimal sospLungheGgTot;
	private BigDecimal sospLungheGgLav;
	private BigDecimal oreMeseLavorate;
	private BigDecimal mese;
	private BigDecimal anno;

	public BigDecimal getIdParametroCompenso() {
		return idParametroCompenso;
	}

	public void setIdParametroCompenso(BigDecimal idParametroCompenso) {
		this.idParametroCompenso = idParametroCompenso;
	}

	public BigDecimal getGgLavorabiliMese() {
		return ggLavorabiliMese;
	}

	public void setGgLavorabiliMese(BigDecimal ggLavorabiliMese) {
		this.ggLavorabiliMese = ggLavorabiliMese;
	}

	public BigDecimal getSospBrevi() {
		return sospBrevi;
	}

	public void setSospBrevi(BigDecimal sospBrevi) {
		this.sospBrevi = sospBrevi;
	}

	public BigDecimal getSospLungheGgTot() {
		return sospLungheGgTot;
	}

	public void setSospLungheGgTot(BigDecimal sospLungheGgTot) {
		this.sospLungheGgTot = sospLungheGgTot;
	}

	public BigDecimal getSospLungheGgLav() {
		return sospLungheGgLav;
	}

	public void setSospLungheGgLav(BigDecimal sospLungheGgLav) {
		this.sospLungheGgLav = sospLungheGgLav;
	}

	public BigDecimal getOreMeseLavorate() {
		return oreMeseLavorate;
	}

	public void setOreMeseLavorate(BigDecimal oreMeseLavorate) {
		this.oreMeseLavorate = oreMeseLavorate;
	}

	public BigDecimal getMese() {
		return mese;
	}

	public void setMese(BigDecimal mese) {
		this.mese = mese;
	}

	public BigDecimal getAnno() {
		return anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public Boolean getGestitiNelProgetto() {
		return isGestitiNelProgetto;
	}

	public void setGestitiNelProgetto(Boolean gestitiNelProgetto) {
		isGestitiNelProgetto = gestitiNelProgetto;
	}

	public Boolean getRicercaPerCapofila() {
		return isRicercaPerCapofila;
	}

	public void setRicercaPerCapofila(Boolean ricercaPerCapofila) {
		isRicercaPerCapofila = ricercaPerCapofila;
	}

	public Boolean getRicercaPerTutti() {
		return isRicercaPerTutti;
	}

	public void setRicercaPerTutti(Boolean ricercaPerTutti) {
		isRicercaPerTutti = ricercaPerTutti;
	}

	public Boolean getRicercaPerPartner() {
		return isRicercaPerPartner;
	}

	public void setRicercaPerPartner(Boolean ricercaPerPartner) {
		isRicercaPerPartner = ricercaPerPartner;
	}

	@Override
	public String toString() {
		return "DocumentoDiSpesaDTO{" +
				"codiceFiscaleFornitore='" + codiceFiscaleFornitore + '\'' +
				", codiceProgetto='" + codiceProgetto + '\'' +
				", cognomeFornitore='" + cognomeFornitore + '\'' +
				", descrizioneDocumentoDiSpesa='" + descrizioneDocumentoDiSpesa + '\'' +
				", destinazioneTrasferta='" + destinazioneTrasferta + '\'' +
				", denominazioneFornitore='" + denominazioneFornitore + '\'' +
				", descStatoDocumentoSpesa='" + descStatoDocumentoSpesa + '\'' +
				", descTipologiaDocumentoDiSpesa='" + descTipologiaDocumentoDiSpesa + '\'' +
				", descBreveTipoDocumentoDiSpesa='" + descBreveTipoDocumentoDiSpesa + '\'' +
				", dataDocumentoDiSpesa=" + dataDocumentoDiSpesa +
				", dataDocumentoDiSpesaDiRiferimento=" + dataDocumentoDiSpesaDiRiferimento +
				", descTipologiaFornitore='" + descTipologiaFornitore + '\'' +
				", durataTrasferta=" + durataTrasferta +
				", idBeneficiario=" + idBeneficiario +
				", idDocRiferimento=" + idDocRiferimento +
				", idDocumentoDiSpesa=" + idDocumentoDiSpesa +
				", idFornitore=" + idFornitore +
				", idProgetto=" + idProgetto +
				", idSoggetto=" + idSoggetto +
				", idSoggettoPartner=" + idSoggettoPartner +
				", idTipoDocumentoDiSpesa=" + idTipoDocumentoDiSpesa +
				", idTipoFornitore=" + idTipoFornitore +
				", idTipoOggettoAttivita=" + idTipoOggettoAttivita +
				", imponibile=" + imponibile +
				", importoIva=" + importoIva +
				", importoIvaACosto=" + importoIvaACosto +
				", importoRendicontabile=" + importoRendicontabile +
				", importoTotaleDocumentoIvato=" + importoTotaleDocumentoIvato +
				", importoRitenutaDAcconto=" + importoRitenutaDAcconto +
				", isGestitiNelProgetto=" + isGestitiNelProgetto +
				", isRicercaPerCapofila=" + isRicercaPerCapofila +
				", isRicercaPerTutti=" + isRicercaPerTutti +
				", isRicercaPerPartner=" + isRicercaPerPartner +
				", nomeFornitore='" + nomeFornitore + '\'' +
				", numeroDocumento='" + numeroDocumento + '\'' +
				", numeroDocumentoDiSpesa='" + numeroDocumentoDiSpesa + '\'' +
				", numeroDocumentoDiSpesaDiRiferimento='" + numeroDocumentoDiSpesaDiRiferimento + '\'' +
				", partitaIvaFornitore='" + partitaIvaFornitore + '\'' +
				", partner='" + partner + '\'' +
				", costoOrario=" + costoOrario +
				", task='" + task + '\'' +
				", importoTotaleValidato=" + importoTotaleValidato +
				", importoTotaleRendicontato=" + importoTotaleRendicontato +
				", progrFornitoreQualifica=" + progrFornitoreQualifica +
				", importoTotaleQuietanzato=" + importoTotaleQuietanzato +
				", idStatoDocumentoSpesa=" + idStatoDocumentoSpesa +
				", rendicontabileQuietanzato=" + rendicontabileQuietanzato +
				", noteValidazione='" + noteValidazione + '\'' +
				", descBreveStatoDocumentoSpesa='" + descBreveStatoDocumentoSpesa + '\'' +
				", tipoInvio='" + tipoInvio + '\'' +
				", flagElettronico=" + flagElettronico +
				", idAppalto=" + idAppalto +
				", descrizioneAppalto='" + descrizioneAppalto + '\'' +
				", flagElettXml='" + flagElettXml + '\'' +
				", idParametroCompenso=" + idParametroCompenso +
				", ggLavorabiliMese=" + ggLavorabiliMese +
				", sospBrevi=" + sospBrevi +
				", sospLungheGgTot=" + sospLungheGgTot +
				", sospLungheGgLav=" + sospLungheGgLav +
				", oreMeseLavorate=" + oreMeseLavorate +
				", mese=" + mese +
				", anno=" + anno +
				'}';
	}
}