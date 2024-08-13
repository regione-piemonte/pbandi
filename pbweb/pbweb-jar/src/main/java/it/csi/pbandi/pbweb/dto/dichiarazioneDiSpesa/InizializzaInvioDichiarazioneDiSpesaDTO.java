/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa;

import it.csi.pbandi.pbweb.util.BeanUtil;

import java.beans.IntrospectionException;
import java.util.Set;

public class InizializzaInvioDichiarazioneDiSpesaDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private boolean uploadRelazioneTecnicaAmmesso;
	
	private boolean allegatiAmmessi;
	
	private boolean progettoPiuGreen = false;
	
	private Long idProgettoContributo;
	
	private String codiceVisualizzatoProgetto;
	
	private Boolean taskVisibile;
	
	private Boolean importoRichiestoErogazioneSaldoAmmesso;
	
	private Long dimMaxFileFirmato;
	
	private boolean indicatoriObbligatori;
	
	private boolean cronoprogrammaObbligatorio;

	private boolean regola30attiva;

	private boolean dichiarazioneDiSpesaFinale;

	private boolean allegatiGenerici;

	private boolean allegatiQuietanza;

	private boolean allegatiDocumentoDiSpesa;

	private boolean isPrivate;

	private boolean isRegolaBR60attiva;
	
	private boolean isRegolaBR61attiva;

	private boolean isRegolaBR62attiva;

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean isRegola30attiva() {
		return regola30attiva;
	}
	
	public void setRegola30attiva(boolean regola30attiva) {
		this.regola30attiva = regola30attiva;
	}
	
	public boolean isRegolaBR54attiva() {
		return dichiarazioneDiSpesaFinale;
	}

	public void setRegolaBR54attiva(boolean regolaBR54attiva) {
		this.dichiarazioneDiSpesaFinale = regolaBR54attiva;
	}
	
	public boolean isRegolaBR53attiva() {
		return allegatiGenerici;
	}

	public void setRegolaBR53attiva(boolean regolaBR53attiva) {
		this.allegatiGenerici = regolaBR53attiva;
	}

	public boolean isRegolaBR52attiva() {
		return allegatiQuietanza;
	}

	public void setRegolaBR52attiva(boolean regolaBR52attiva) {
		this.allegatiQuietanza = regolaBR52attiva;
	}

	public boolean isRegolaBR51attiva() {
		return allegatiDocumentoDiSpesa;
	}

	public void setRegolaBR51attiva(boolean regolaBR51attiva) {
		this.allegatiDocumentoDiSpesa = regolaBR51attiva;
	}

	public boolean isIndicatoriObbligatori() {
		return indicatoriObbligatori;
	}

	public void setIndicatoriObbligatori(boolean indicatoriObbligatori) {
		this.indicatoriObbligatori = indicatoriObbligatori;
	}

	public boolean isCronoprogrammaObbligatorio() {
		return cronoprogrammaObbligatorio;
	}

	public void setCronoprogrammaObbligatorio(boolean cronoprogrammaObbligatorio) {
		this.cronoprogrammaObbligatorio = cronoprogrammaObbligatorio;
	}

	public Long getDimMaxFileFirmato() {
		return dimMaxFileFirmato;
	}

	public void setDimMaxFileFirmato(Long dimMaxFileFirmato) {
		this.dimMaxFileFirmato = dimMaxFileFirmato;
	}

	public Boolean getImportoRichiestoErogazioneSaldoAmmesso() {
		return importoRichiestoErogazioneSaldoAmmesso;
	}

	public void setImportoRichiestoErogazioneSaldoAmmesso(Boolean importoRichiestoErogazioneSaldoAmmesso) {
		this.importoRichiestoErogazioneSaldoAmmesso = importoRichiestoErogazioneSaldoAmmesso;
	}

	public Boolean getTaskVisibile() {
		return taskVisibile;
	}

	public void setTaskVisibile(Boolean taskVisibile) {
		this.taskVisibile = taskVisibile;
	}

	public boolean isProgettoPiuGreen() {
		return progettoPiuGreen;
	}

	public void setProgettoPiuGreen(boolean progettoPiuGreen) {
		this.progettoPiuGreen = progettoPiuGreen;
	}

	public Long getIdProgettoContributo() {
		return idProgettoContributo;
	}

	public void setIdProgettoContributo(Long idProgettoContributo) {
		this.idProgettoContributo = idProgettoContributo;
	}

	public boolean isUploadRelazioneTecnicaAmmesso() {
		return uploadRelazioneTecnicaAmmesso;
	}

	public void setUploadRelazioneTecnicaAmmesso(boolean uploadRelazioneTecnicaAmmesso) {
		this.uploadRelazioneTecnicaAmmesso = uploadRelazioneTecnicaAmmesso;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public boolean isAllegatiAmmessi() {
		return allegatiAmmessi;
	}

	public void setAllegatiAmmessi(boolean allegatiAmmessi) {
		this.allegatiAmmessi = allegatiAmmessi;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}

	public boolean isRegolaBR60attiva() {
		return isRegolaBR60attiva;
	}

	public void setRegolaBR60attiva(boolean regolaBR60attiva) {
		isRegolaBR60attiva = regolaBR60attiva;
	}

	public boolean isRegolaBR61attiva() {
		return isRegolaBR61attiva;
	}

	public void setRegolaBR61attiva(boolean regolaBR61attiva) {
		isRegolaBR61attiva = regolaBR61attiva;
	}

	public boolean isRegolaBR62attiva() {
		return isRegolaBR62attiva;
	}

	public void setRegolaBR62attiva(boolean regolaBR62attiva) {
		isRegolaBR62attiva = regolaBR62attiva;
	}
}
