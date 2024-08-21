/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico;

public class ContoEconomicoRimodulazioneDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private Double importoRichiestoInDomanda = null;
	private Double importoRichiestoUltimaProposta = null;
	private Double importoRichiestoNuovaProposta = null;
	private Double importoSpesaAmmessaInDetermina = null;
	private Double importoSpesaAmmessaUltima = null;
	private Double importoSpesaAmmessaRimodulazione = null;
	private Double importoSpesaRendicontata = null;
	private Double importoSpesaQuietanziata = null;
	private Double percSpesaQuietanziataSuAmmessa = null;
	private Double importoSpesaValidataTotale = null;
	private Double percSpesaValidataSuAmmessa = null;
	private String label = null;
	private Long idContoEconomico = null;
	private Long idRigoContoEconomico = null;
	private Long idVoce = null;
	private ContoEconomicoRimodulazioneDTO[] figli = null;
	private Double importoAmmessoDaBando = null;
	private boolean voceAssociataARigo = true;
	private String flagRibassoAsta = null;
	private Double importoRibassoAsta = null;
	private Double percRibassoAsta = null;
	private Long idTipologiaVoceDiSpesa = null;
	private String descTipologiaVoceDiSpesa = null;
	private Double percQuotaContributo = null;
	private Double importoSpesaPreventivata = null;



	public ContoEconomicoRimodulazioneDTO() {
	}

	public ContoEconomicoRimodulazioneDTO(Double importoRichiestoInDomanda, Double importoRichiestoUltimaProposta, Double importoRichiestoNuovaProposta, Double importoSpesaAmmessaInDetermina, Double importoSpesaAmmessaUltima, Double importoSpesaAmmessaRimodulazione, Double importoSpesaRendicontata, Double importoSpesaQuietanziata, Double percSpesaQuietanziataSuAmmessa, Double importoSpesaValidataTotale, Double percSpesaValidataSuAmmessa, String label, Long idContoEconomico, Long idRigoContoEconomico, Long idVoce, ContoEconomicoRimodulazioneDTO[] figli, Double importoAmmessoDaBando, boolean voceAssociataARigo, String flagRibassoAsta, Double importoRibassoAsta, Double percRibassoAsta, Long idTipologiaVoceDiSpesa, String descTipologiaVoceDiSpesa, Double percQuotaContributo, Double importoSpesaPreventivata) {
		this.importoRichiestoInDomanda = importoRichiestoInDomanda;
		this.importoRichiestoUltimaProposta = importoRichiestoUltimaProposta;
		this.importoRichiestoNuovaProposta = importoRichiestoNuovaProposta;
		this.importoSpesaAmmessaInDetermina = importoSpesaAmmessaInDetermina;
		this.importoSpesaAmmessaUltima = importoSpesaAmmessaUltima;
		this.importoSpesaAmmessaRimodulazione = importoSpesaAmmessaRimodulazione;
		this.importoSpesaRendicontata = importoSpesaRendicontata;
		this.importoSpesaQuietanziata = importoSpesaQuietanziata;
		this.percSpesaQuietanziataSuAmmessa = percSpesaQuietanziataSuAmmessa;
		this.importoSpesaValidataTotale = importoSpesaValidataTotale;
		this.percSpesaValidataSuAmmessa = percSpesaValidataSuAmmessa;
		this.label = label;
		this.idContoEconomico = idContoEconomico;
		this.idRigoContoEconomico = idRigoContoEconomico;
		this.idVoce = idVoce;
		this.figli = figli;
		this.importoAmmessoDaBando = importoAmmessoDaBando;
		this.voceAssociataARigo = voceAssociataARigo;
		this.flagRibassoAsta = flagRibassoAsta;
		this.importoRibassoAsta = importoRibassoAsta;
		this.percRibassoAsta = percRibassoAsta;
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
		this.descTipologiaVoceDiSpesa = descTipologiaVoceDiSpesa;
		this.percQuotaContributo = percQuotaContributo;
		this.importoSpesaPreventivata = importoSpesaPreventivata;
	}


	public Double getImportoRichiestoInDomanda() {
		return importoRichiestoInDomanda;
	}

	public void setImportoRichiestoInDomanda(Double importoRichiestoInDomanda) {
		this.importoRichiestoInDomanda = importoRichiestoInDomanda;
	}

	public Double getImportoRichiestoUltimaProposta() {
		return importoRichiestoUltimaProposta;
	}

	public void setImportoRichiestoUltimaProposta(Double importoRichiestoUltimaProposta) {
		this.importoRichiestoUltimaProposta = importoRichiestoUltimaProposta;
	}

	public Double getImportoRichiestoNuovaProposta() {
		return importoRichiestoNuovaProposta;
	}

	public void setImportoRichiestoNuovaProposta(Double importoRichiestoNuovaProposta) {
		this.importoRichiestoNuovaProposta = importoRichiestoNuovaProposta;
	}

	public Double getImportoSpesaAmmessaInDetermina() {
		return importoSpesaAmmessaInDetermina;
	}

	public void setImportoSpesaAmmessaInDetermina(Double importoSpesaAmmessaInDetermina) {
		this.importoSpesaAmmessaInDetermina = importoSpesaAmmessaInDetermina;
	}

	public Double getImportoSpesaAmmessaUltima() {
		return importoSpesaAmmessaUltima;
	}

	public void setImportoSpesaAmmessaUltima(Double importoSpesaAmmessaUltima) {
		this.importoSpesaAmmessaUltima = importoSpesaAmmessaUltima;
	}

	public Double getImportoSpesaAmmessaRimodulazione() {
		return importoSpesaAmmessaRimodulazione;
	}

	public void setImportoSpesaAmmessaRimodulazione(Double importoSpesaAmmessaRimodulazione) {
		this.importoSpesaAmmessaRimodulazione = importoSpesaAmmessaRimodulazione;
	}

	public Double getImportoSpesaRendicontata() {
		return importoSpesaRendicontata;
	}

	public void setImportoSpesaRendicontata(Double importoSpesaRendicontata) {
		this.importoSpesaRendicontata = importoSpesaRendicontata;
	}

	public Double getImportoSpesaQuietanziata() {
		return importoSpesaQuietanziata;
	}

	public void setImportoSpesaQuietanziata(Double importoSpesaQuietanziata) {
		this.importoSpesaQuietanziata = importoSpesaQuietanziata;
	}

	public Double getPercSpesaQuietanziataSuAmmessa() {
		return percSpesaQuietanziataSuAmmessa;
	}

	public void setPercSpesaQuietanziataSuAmmessa(Double percSpesaQuietanziataSuAmmessa) {
		this.percSpesaQuietanziataSuAmmessa = percSpesaQuietanziataSuAmmessa;
	}

	public Double getImportoSpesaValidataTotale() {
		return importoSpesaValidataTotale;
	}

	public void setImportoSpesaValidataTotale(Double importoSpesaValidataTotale) {
		this.importoSpesaValidataTotale = importoSpesaValidataTotale;
	}

	public Double getPercSpesaValidataSuAmmessa() {
		return percSpesaValidataSuAmmessa;
	}

	public void setPercSpesaValidataSuAmmessa(Double percSpesaValidataSuAmmessa) {
		this.percSpesaValidataSuAmmessa = percSpesaValidataSuAmmessa;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getIdContoEconomico() {
		return idContoEconomico;
	}

	public void setIdContoEconomico(Long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(Long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public Long getIdVoce() {
		return idVoce;
	}

	public void setIdVoce(Long idVoce) {
		this.idVoce = idVoce;
	}


	public Double getImportoAmmessoDaBando() {
		return importoAmmessoDaBando;
	}

	public void setImportoAmmessoDaBando(Double importoAmmessoDaBando) {
		this.importoAmmessoDaBando = importoAmmessoDaBando;
	}

	public boolean isVoceAssociataARigo() {
		return voceAssociataARigo;
	}

	public void setVoceAssociataARigo(boolean voceAssociataARigo) {
		this.voceAssociataARigo = voceAssociataARigo;
	}

	public String getFlagRibassoAsta() {
		return flagRibassoAsta;
	}

	public void setFlagRibassoAsta(String flagRibassoAsta) {
		this.flagRibassoAsta = flagRibassoAsta;
	}

	public Double getImportoRibassoAsta() {
		return importoRibassoAsta;
	}

	public void setImportoRibassoAsta(Double importoRibassoAsta) {
		this.importoRibassoAsta = importoRibassoAsta;
	}

	public Double getPercRibassoAsta() {
		return percRibassoAsta;
	}

	public void setPercRibassoAsta(Double percRibassoAsta) {
		this.percRibassoAsta = percRibassoAsta;
	}

	public Long getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(Long idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

	public String getDescTipologiaVoceDiSpesa() {
		return descTipologiaVoceDiSpesa;
	}

	public void setDescTipologiaVoceDiSpesa(String descTipologiaVoceDiSpesa) {
		this.descTipologiaVoceDiSpesa = descTipologiaVoceDiSpesa;
	}

	public Double getPercQuotaContributo() {
		return percQuotaContributo;
	}

	public void setPercQuotaContributo(Double percQuotaContributo) {
		this.percQuotaContributo = percQuotaContributo;
	}

	public ContoEconomicoRimodulazioneDTO[] getFigli() {
		return figli;
	}

	public void setFigli(ContoEconomicoRimodulazioneDTO[] figli) {
		this.figli = figli;
	}

	public Double getImportoSpesaPreventivata() {
		return importoSpesaPreventivata;
	}

	public void setImportoSpesaPreventivata(Double importoSpesaPreventivata) {
		this.importoSpesaPreventivata = importoSpesaPreventivata;
	}
}