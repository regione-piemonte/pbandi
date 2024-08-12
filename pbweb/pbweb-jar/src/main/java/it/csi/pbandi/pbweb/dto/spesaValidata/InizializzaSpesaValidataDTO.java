package it.csi.pbandi.pbweb.dto.spesaValidata;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class InizializzaSpesaValidataDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String codiceVisualizzatoProgetto;

	private Boolean taskVisibile;

	private List<String> elencoTask; // popolato solo se taskVisibile = true.

	private ArrayList<DichiarazioneSpesaValidataComboDTO> comboDichiarazioniDiSpesa;

	private ArrayList<CodiceDescrizioneDTO> comboVociDiSpesa;

	private ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoDiSpesa;

	private ArrayList<CodiceDescrizioneDTO> comboTipiFornitore;

	// Se true, si visualizza il seguente msg:
	// Per il progetto è stata creata almeno una proposta di certificazione.
	// Eventuali rettifiche sulla spesa validata potranno avere effetto sugli
	// importi certificati.
	private Boolean esistePropostaCertificazione;
	private boolean richiestaIntegrazioneAbilitata;

	public ArrayList<CodiceDescrizioneDTO> getComboTipiFornitore() {
		return comboTipiFornitore;
	}

	public void setComboTipiFornitore(ArrayList<CodiceDescrizioneDTO> comboTipiFornitore) {
		this.comboTipiFornitore = comboTipiFornitore;
	}

	public ArrayList<CodiceDescrizioneDTO> getComboTipiDocumentoDiSpesa() {
		return comboTipiDocumentoDiSpesa;
	}

	public void setComboTipiDocumentoDiSpesa(ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoDiSpesa) {
		this.comboTipiDocumentoDiSpesa = comboTipiDocumentoDiSpesa;
	}

	public Boolean getEsistePropostaCertificazione() {
		return esistePropostaCertificazione;
	}

	public void setEsistePropostaCertificazione(Boolean esistePropostaCertificazione) {
		this.esistePropostaCertificazione = esistePropostaCertificazione;
	}

	public ArrayList<DichiarazioneSpesaValidataComboDTO> getComboDichiarazioniDiSpesa() {
		return comboDichiarazioniDiSpesa;
	}

	public void setComboDichiarazioniDiSpesa(ArrayList<DichiarazioneSpesaValidataComboDTO> comboDichiarazioniDiSpesa) {
		this.comboDichiarazioniDiSpesa = comboDichiarazioniDiSpesa;
	}

	public ArrayList<CodiceDescrizioneDTO> getComboVociDiSpesa() {
		return comboVociDiSpesa;
	}

	public void setComboVociDiSpesa(ArrayList<CodiceDescrizioneDTO> comboVociDiSpesa) {
		this.comboVociDiSpesa = comboVociDiSpesa;
	}

	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}

	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}

	public Boolean getTaskVisibile() {
		return taskVisibile;
	}

	public void setTaskVisibile(Boolean taskVisibile) {
		this.taskVisibile = taskVisibile;
	}

	public List<String> getElencoTask() {
		return elencoTask;
	}

	public void setElencoTask(List<String> elencoTask) {
		this.elencoTask = elencoTask;
	}

	public boolean isRichiestaIntegrazioneAbilitata() {
		return richiestaIntegrazioneAbilitata;
	}

	public void setRichiestaIntegrazioneAbilitata(boolean richiestaIntegrazioneAbilitata) {
		this.richiestaIntegrazioneAbilitata = richiestaIntegrazioneAbilitata;
	}

}
