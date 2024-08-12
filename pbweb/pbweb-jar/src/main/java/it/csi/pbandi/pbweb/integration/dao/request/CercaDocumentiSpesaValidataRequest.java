package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class CercaDocumentiSpesaValidataRequest {
	
	private Long idProgetto;
	
	private Long idDichiarazioneDiSpesa;
	private Long idVoceDiSpesa;
	private Long idTipoDocumentoDiSpesa;
	private String task;
	private String numeroDocumentoDiSpesa;
	private Date dataDocumentoDiSpesa;
	
	private Long idTipoFornitore;
	private String codiceFiscaleFornitore;
	private String partitaIvaFornitore;
	private String denominazioneFornitore;
	private String cognomeFornitore;
	private String nomeFornitore;

	// true = selezionato check "Documenti rettificati"; false = selezionato il check "Tutti i documenti".
	private Boolean rettificato;
	
	// Ancora non è chiaro quando valorizzarlo; forse Spesa Validata viene chiamata da Conto Economico.
	private Long idContoEconomico;
	
	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	}


	public Long getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}


	public Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}


	public void setIdDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}


	public Long getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}


	public void setIdVoceDiSpesa(Long idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}


	public Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}


	public void setIdTipoDocumentoDiSpesa(Long idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}


	public String getNumeroDocumentoDiSpesa() {
		return numeroDocumentoDiSpesa;
	}


	public void setNumeroDocumentoDiSpesa(String numeroDocumentoDiSpesa) {
		this.numeroDocumentoDiSpesa = numeroDocumentoDiSpesa;
	}


	public Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}


	public void setDataDocumentoDiSpesa(Date dataDocumentoDiSpesa) {
		this.dataDocumentoDiSpesa = dataDocumentoDiSpesa;
	}


	public Long getIdTipoFornitore() {
		return idTipoFornitore;
	}


	public void setIdTipoFornitore(Long idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
	}


	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}


	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}


	public String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}


	public void setPartitaIvaFornitore(String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}


	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}


	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}


	public String getCognomeFornitore() {
		return cognomeFornitore;
	}


	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}


	public String getNomeFornitore() {
		return nomeFornitore;
	}


	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}


	public Boolean getRettificato() {
		return rettificato;
	}


	public void setRettificato(Boolean rettificato) {
		this.rettificato = rettificato;
	}


	public Long getIdContoEconomico() {
		return idContoEconomico;
	}


	public void setIdContoEconomico(Long idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
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
	
}
