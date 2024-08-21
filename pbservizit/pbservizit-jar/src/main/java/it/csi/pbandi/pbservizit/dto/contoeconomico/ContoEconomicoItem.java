/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.contoeconomico;

public class ContoEconomicoItem implements java.io.Serializable {

	private String id = null;
	private String idPadre = null;
	private boolean haFigli = false;
	private String type = null;
	private int level = 0;
	private String label = null;
	private Double importoSpesaAmmessa = null;
	private Double importoSpesaRendicontata = null;
	private Double importoSpesaQuietanziata = null;
	private Double percentualeSpesaQuietanziataSuAmmessa = null;
	private Double importoSpesaValidataTotale = null;
	private Double percentualeSpesaValidataSuAmmessa = null;
	private Long idContoEconomico = null;
	private Long idVoce = null;
	private Long idVocePadre = null;
	private Long idTipologiaVoceDiSpesa = null;
	private String descTipologiaVoceDiSpesa = null;
	private Long percQuotaContributo = null;
	private Double importoSpesaPreventivata = null;

	public ContoEconomicoItem() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public boolean isHaFigli() {
		return haFigli;
	}

	public void setHaFigli(boolean haFigli) {
		this.haFigli = haFigli;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getImportoSpesaAmmessa() {
		return importoSpesaAmmessa;
	}

	public void setImportoSpesaAmmessa(Double importoSpesaAmmessa) {
		this.importoSpesaAmmessa = importoSpesaAmmessa;
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

	public Double getPercentualeSpesaQuietanziataSuAmmessa() {
		return percentualeSpesaQuietanziataSuAmmessa;
	}

	public void setPercentualeSpesaQuietanziataSuAmmessa(Double percentualeSpesaQuietanziataSuAmmessa) {
		this.percentualeSpesaQuietanziataSuAmmessa = percentualeSpesaQuietanziataSuAmmessa;
	}

	public Double getImportoSpesaValidataTotale() {
		return importoSpesaValidataTotale;
	}

	public void setImportoSpesaValidataTotale(Double importoSpesaValidataTotale) {
		this.importoSpesaValidataTotale = importoSpesaValidataTotale;
	}

	public Double getPercentualeSpesaValidataSuAmmessa() {
		return percentualeSpesaValidataSuAmmessa;
	}

	public void setPercentualeSpesaValidataSuAmmessa(Double percentualeSpesaValidataSuAmmessa) {
		this.percentualeSpesaValidataSuAmmessa = percentualeSpesaValidataSuAmmessa;
	}

	public Long getIdContoEconomico() {
		return idContoEconomico;
	}

	public void setIdContoEconomico(Long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}

	public Long getIdVoce() {
		return idVoce;
	}

	public void setIdVoce(Long idVoce) {
		this.idVoce = idVoce;
	}

	public Long getIdVocePadre() {
		return idVocePadre;
	}

	public void setIdVocePadre(Long idVocePadre) {
		this.idVocePadre = idVocePadre;
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

	public Long getPercQuotaContributo() {
		return percQuotaContributo;
	}

	public void setPercQuotaContributo(Long percQuotaContributo) {
		this.percQuotaContributo = percQuotaContributo;
	}

	public Double getImportoSpesaPreventivata() {
		return importoSpesaPreventivata;
	}

	public void setImportoSpesaPreventivata(Double importoSpesaPreventivata) {
		this.importoSpesaPreventivata = importoSpesaPreventivata;
	}

	@Override
	public String toString() {
		return "ContoEconomicoItem [id=" + id + ", idPadre=" + idPadre + ", haFigli=" + haFigli + ", type=" + type
				+ ", level=" + level + ", label=" + label + ", importoSpesaAmmessa=" + importoSpesaAmmessa
				+ ", importoSpesaRendicontata=" + importoSpesaRendicontata + ", importoSpesaQuietanziata="
				+ importoSpesaQuietanziata + ", percentualeSpesaQuietanziataSuAmmessa="
				+ percentualeSpesaQuietanziataSuAmmessa + ", importoSpesaValidataTotale=" + importoSpesaValidataTotale
				+ ", percentualeSpesaValidataSuAmmessa=" + percentualeSpesaValidataSuAmmessa + ", idContoEconomico="
				+ idContoEconomico + ", idVoce=" + idVoce + ", idVocePadre=" + idVocePadre + ", idTipologiaVoceDiSpesa="
				+ idTipologiaVoceDiSpesa + ", descTipologiaVoceDiSpesa=" + descTipologiaVoceDiSpesa
				+ ", percQuotaContributo=" + percQuotaContributo + ", importoSpesaPreventivata="
				+ importoSpesaPreventivata + "]";
	}
}
