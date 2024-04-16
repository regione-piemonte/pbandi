/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbworkspace.dto.schedaProgetto.IdDescBreveDescEstesa;
import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class InizializzaComboDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	ArrayList<CodiceDescrizione> comboStrumentoAttuativo;
	ArrayList<CodiceDescrizione> comboSettoreCpt;
	ArrayList<CodiceDescrizione> comboTemaPrioritario;
	ArrayList<CodiceDescrizione> comboIndicatoreRisultatoProgramma;
	ArrayList<CodiceDescrizione> comboIndicatoreQsn;
	ArrayList<CodiceDescrizione> comboTipoAiuto;
	ArrayList<CodiceDescrizione> comboStrumentoProgrammazione;
	ArrayList<CodiceDescrizione> comboDimensioneTerritoriale;
	ArrayList<CodiceDescrizione> comboProgettoComplesso;
	ArrayList<CodiceDescrizione> comboSettoreCipe;
	ArrayList<CodiceDescrizione> comboNaturaCipe;
	ArrayList<CodiceDescrizione> comboRuoli;
	ArrayList<CodiceDescrizione> comboAteneo;
	ArrayList<CodiceDescrizione> comboSettoreAttivita;
	ArrayList<CodiceDescrizione> comboDenominazioneEnteDipReg;
	ArrayList<CodiceDescrizione> comboDenominazioneEntePA;
	ArrayList<CodiceDescrizione> comboFormaGiuridica;
	ArrayList<IdDescBreveDescEstesa> comboNazione;
	ArrayList<IdDescBreveDescEstesa> comboNazioneNascita;
	ArrayList<CodiceDescrizione> comboObiettivoTematico;
	ArrayList<CodiceDescrizione> comboGrandeProgetto;
	ArrayList<CodiceDescrizione> comboTipoOperazione;

	
	ArrayList<CodiceDescrizione> comboDimensioneImpresa;
	ArrayList<CodiceDescrizione> comboRelazioneColBeneficiario;
	public ArrayList<CodiceDescrizione> getComboDimensioneImpresa() {
		return comboDimensioneImpresa;
	}
	public void setComboDimensioneImpresa(ArrayList<CodiceDescrizione> comboDimensioneImpresa) {
		this.comboDimensioneImpresa = comboDimensioneImpresa;
	}
	public ArrayList<CodiceDescrizione> getComboRelazioneColBeneficiario() {
		return comboRelazioneColBeneficiario;
	}
	public void setComboRelazioneColBeneficiario(ArrayList<CodiceDescrizione> comboRelazioneColBeneficiario) {
		this.comboRelazioneColBeneficiario = comboRelazioneColBeneficiario;
	}
	
	
	public ArrayList<CodiceDescrizione> getComboTipoOperazione() {
		return comboTipoOperazione;
	}
	public void setComboTipoOperazione(ArrayList<CodiceDescrizione> comboTipoOperazione) {
		this.comboTipoOperazione = comboTipoOperazione;
	}
	public ArrayList<CodiceDescrizione> getComboStrumentoAttuativo() {
		return comboStrumentoAttuativo;
	}
	public void setComboStrumentoAttuativo(ArrayList<CodiceDescrizione> comboStrumentoAttuativo) {
		this.comboStrumentoAttuativo = comboStrumentoAttuativo;
	}
	public ArrayList<CodiceDescrizione> getComboSettoreCpt() {
		return comboSettoreCpt;
	}
	public void setComboSettoreCpt(ArrayList<CodiceDescrizione> comboSettoreCpt) {
		this.comboSettoreCpt = comboSettoreCpt;
	}
	public ArrayList<CodiceDescrizione> getComboTemaPrioritario() {
		return comboTemaPrioritario;
	}
	public void setComboTemaPrioritario(ArrayList<CodiceDescrizione> comboTemaPrioritario) {
		this.comboTemaPrioritario = comboTemaPrioritario;
	}
	public ArrayList<CodiceDescrizione> getComboIndicatoreRisultatoProgramma() {
		return comboIndicatoreRisultatoProgramma;
	}
	public void setComboIndicatoreRisultatoProgramma(ArrayList<CodiceDescrizione> comboIndicatoreRisultatoProgramma) {
		this.comboIndicatoreRisultatoProgramma = comboIndicatoreRisultatoProgramma;
	}
	public ArrayList<CodiceDescrizione> getComboIndicatoreQsn() {
		return comboIndicatoreQsn;
	}
	public void setComboIndicatoreQsn(ArrayList<CodiceDescrizione> comboIndicatoreQsn) {
		this.comboIndicatoreQsn = comboIndicatoreQsn;
	}
	public ArrayList<CodiceDescrizione> getComboTipoAiuto() {
		return comboTipoAiuto;
	}
	public void setComboTipoAiuto(ArrayList<CodiceDescrizione> comboTipoAiuto) {
		this.comboTipoAiuto = comboTipoAiuto;
	}
	public ArrayList<CodiceDescrizione> getComboStrumentoProgrammazione() {
		return comboStrumentoProgrammazione;
	}
	public void setComboStrumentoProgrammazione(ArrayList<CodiceDescrizione> comboStrumentoProgrammazione) {
		this.comboStrumentoProgrammazione = comboStrumentoProgrammazione;
	}
	public ArrayList<CodiceDescrizione> getComboDimensioneTerritoriale() {
		return comboDimensioneTerritoriale;
	}
	public void setComboDimensioneTerritoriale(ArrayList<CodiceDescrizione> comboDimensioneTerritoriale) {
		this.comboDimensioneTerritoriale = comboDimensioneTerritoriale;
	}
	public ArrayList<CodiceDescrizione> getComboProgettoComplesso() {
		return comboProgettoComplesso;
	}
	public void setComboProgettoComplesso(ArrayList<CodiceDescrizione> comboProgettoComplesso) {
		this.comboProgettoComplesso = comboProgettoComplesso;
	}
	public ArrayList<CodiceDescrizione> getComboSettoreCipe() {
		return comboSettoreCipe;
	}
	public void setComboSettoreCipe(ArrayList<CodiceDescrizione> comboSettoreCipe) {
		this.comboSettoreCipe = comboSettoreCipe;
	}
	public ArrayList<CodiceDescrizione> getComboNaturaCipe() {
		return comboNaturaCipe;
	}
	public void setComboNaturaCipe(ArrayList<CodiceDescrizione> comboNaturaCipe) {
		this.comboNaturaCipe = comboNaturaCipe;
	}
	public ArrayList<CodiceDescrizione> getComboRuoli() {
		return comboRuoli;
	}
	public void setComboRuoli(ArrayList<CodiceDescrizione> comboRuoli) {
		this.comboRuoli = comboRuoli;
	}
	public ArrayList<CodiceDescrizione> getComboAteneo() {
		return comboAteneo;
	}
	public void setComboAteneo(ArrayList<CodiceDescrizione> comboAteneo) {
		this.comboAteneo = comboAteneo;
	}
	public ArrayList<CodiceDescrizione> getComboSettoreAttivita() {
		return comboSettoreAttivita;
	}
	public void setComboSettoreAttivita(ArrayList<CodiceDescrizione> comboSettoreAttivita) {
		this.comboSettoreAttivita = comboSettoreAttivita;
	}
	public ArrayList<CodiceDescrizione> getComboDenominazioneEnteDipReg() {
		return comboDenominazioneEnteDipReg;
	}
	public void setComboDenominazioneEnteDipReg(ArrayList<CodiceDescrizione> comboDenominazioneEnteDipReg) {
		this.comboDenominazioneEnteDipReg = comboDenominazioneEnteDipReg;
	}
	public ArrayList<CodiceDescrizione> getComboDenominazioneEntePA() {
		return comboDenominazioneEntePA;
	}
	public void setComboDenominazioneEntePA(ArrayList<CodiceDescrizione> comboDenominazioneEntePA) {
		this.comboDenominazioneEntePA = comboDenominazioneEntePA;
	}
	public ArrayList<CodiceDescrizione> getComboFormaGiuridica() {
		return comboFormaGiuridica;
	}
	public void setComboFormaGiuridica(ArrayList<CodiceDescrizione> comboFormaGiuridica) {
		this.comboFormaGiuridica = comboFormaGiuridica;
	}
	public ArrayList<IdDescBreveDescEstesa> getComboNazione() {
		return comboNazione;
	}
	public void setComboNazione(ArrayList<IdDescBreveDescEstesa> comboNazione) {
		this.comboNazione = comboNazione;
	}
	public ArrayList<IdDescBreveDescEstesa> getComboNazioneNascita() {
		return comboNazioneNascita;
	}
	public void setComboNazioneNascita(ArrayList<IdDescBreveDescEstesa> comboNazioneNascita) {
		this.comboNazioneNascita = comboNazioneNascita;
	}
	public ArrayList<CodiceDescrizione> getComboObiettivoTematico() {
		return comboObiettivoTematico;
	}
	public void setComboObiettivoTematico(ArrayList<CodiceDescrizione> comboObiettivoTematico) {
		this.comboObiettivoTematico = comboObiettivoTematico;
	}
	public ArrayList<CodiceDescrizione> getComboGrandeProgetto() {
		return comboGrandeProgetto;
	}
	public void setComboGrandeProgetto(ArrayList<CodiceDescrizione> comboGrandeProgetto) {
		this.comboGrandeProgetto = comboGrandeProgetto;
	}
	
}
