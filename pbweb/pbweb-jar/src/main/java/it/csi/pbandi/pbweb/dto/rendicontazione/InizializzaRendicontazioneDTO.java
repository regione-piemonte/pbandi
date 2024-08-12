package it.csi.pbandi.pbweb.dto.rendicontazione;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoOperazioneDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class InizializzaRendicontazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private boolean taskVisibile;

	private boolean allegatiAmmessi;

	private boolean allegatiAmmessiGenerici;

	private boolean allegatiAmmessiDocumentoSpesa;

	private boolean allegatiAmmessiQuietanze;

	private boolean causalePagamentoVisible;

	private String codiceVisualizzatoProgetto;

	private Long idTipoOperazioneProgetto;

	private Long idProcessoBandoLinea;

	private boolean utenteAbilitatoAdAssociarePagamenti;

	private boolean utenteAbilitatoAdAssociareVociDiSpesa;

	private EsitoOperazioneDTO solaVisualizzazione;

	private Boolean isBR58;

	private Boolean hasDocumentoDichiarabile;

	public boolean isUtenteAbilitatoAdAssociarePagamenti() {
		return utenteAbilitatoAdAssociarePagamenti;
	}

	public void setUtenteAbilitatoAdAssociarePagamenti(boolean utenteAbilitatoAdAssociarePagamenti) {
		this.utenteAbilitatoAdAssociarePagamenti = utenteAbilitatoAdAssociarePagamenti;
	}

	public boolean isUtenteAbilitatoAdAssociareVociDiSpesa() {
		return utenteAbilitatoAdAssociareVociDiSpesa;
	}

	public void setUtenteAbilitatoAdAssociareVociDiSpesa(boolean utenteAbilitatoAdAssociareVociDiSpesa) {
		this.utenteAbilitatoAdAssociareVociDiSpesa = utenteAbilitatoAdAssociareVociDiSpesa;
	}

	public Long getIdProcessoBandoLinea() {
		return idProcessoBandoLinea;
	}

	public void setIdProcessoBandoLinea(Long idProcessoBandoLinea) {
		this.idProcessoBandoLinea = idProcessoBandoLinea;
	}

	public Long getIdTipoOperazioneProgetto() {
		return idTipoOperazioneProgetto;
	}

	public void setIdTipoOperazioneProgetto(Long idTipoOperazioneProgetto) {
		this.idTipoOperazioneProgetto = idTipoOperazioneProgetto;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public boolean isTaskVisibile() {
		return taskVisibile;
	}

	public void setTaskVisibile(boolean taskVisibile) {
		this.taskVisibile = taskVisibile;
	}

	public boolean isAllegatiAmmessiGenerici() {
		return allegatiAmmessiGenerici;
	}

	public void setAllegatiAmmessiGenerici(boolean allegatiAmmessiGenerici) {
		this.allegatiAmmessiGenerici = allegatiAmmessiGenerici;
	}

	public boolean isAllegatiAmmessi() {
		return allegatiAmmessi;
	}

	public boolean isAllegatiAmmessiDocumentoSpesa() {
		return allegatiAmmessiDocumentoSpesa;
	}

	public void setAllegatiAmmessiDocumentoSpesa(boolean allegatiAmmessiDocumentoSpesa) {
		this.allegatiAmmessiDocumentoSpesa = allegatiAmmessiDocumentoSpesa;
	}

	public boolean isAllegatiAmmessiQuietanze() {
		return allegatiAmmessiQuietanze;
	}

	public void setAllegatiAmmessiQuietanze(boolean allegatiAmmessiQuietanze) {
		this.allegatiAmmessiQuietanze = allegatiAmmessiQuietanze;
	}

	public void setAllegatiAmmessi(boolean allegatiAmmessi) {
		this.allegatiAmmessi = allegatiAmmessi;
	}

	public boolean isCausalePagamentoVisible() {
		return causalePagamentoVisible;
	}

	public void setCausalePagamentoVisible(boolean causalePagamentoVisible) {
		this.causalePagamentoVisible = causalePagamentoVisible;
	}

	public EsitoOperazioneDTO getSolaVisualizzazione() {
		return solaVisualizzazione;
	}

	public void setSolaVisualizzazione(EsitoOperazioneDTO solaVisualizzazione) {
		this.solaVisualizzazione = solaVisualizzazione;
	}

	public Boolean getIsBR58() {
		return isBR58;
	}

	public void setIsBR58(Boolean isBR58) {
		this.isBR58 = isBR58;
	}

	public Boolean getHasDocumentoDichiarabile() {
		return hasDocumentoDichiarabile;
	}

	public void setHasDocumentoDichiarabile(Boolean hasDocumentoDichiarabile) {
		this.hasDocumentoDichiarabile = hasDocumentoDichiarabile;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nInizializzaRendicontazioneDTO: ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n" + propName + " = " + BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

}
