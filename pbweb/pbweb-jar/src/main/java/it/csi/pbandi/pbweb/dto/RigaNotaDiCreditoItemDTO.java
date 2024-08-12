package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class RigaNotaDiCreditoItemDTO implements java.io.Serializable {

	private Boolean rigaNotaDiCredito = false;
	private java.lang.String numeroDocumento = null;
	private java.lang.String dataDocumento = null;
	private java.lang.Double importoDocumento = null;
	private java.lang.String descrizioneVoceDiSpesa = null;
	private java.lang.Double importoVoceDiSpesa = null;
	private java.lang.String statoDocumento = null;
	private java.lang.Long id = null;
	private java.util.ArrayList<DocumentoAllegatoDTO> documentoAllegato = new java.util.ArrayList<DocumentoAllegatoDTO>();

	public Boolean getRigaNotaDiCredito() {
		return rigaNotaDiCredito;
	}

	public void setRigaNotaDiCredito(Boolean rigaNotaDiCredito) {
		this.rigaNotaDiCredito = rigaNotaDiCredito;
	}

	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(java.lang.String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public java.lang.String getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(java.lang.String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public java.lang.Double getImportoDocumento() {
		return importoDocumento;
	}

	public void setImportoDocumento(java.lang.Double importoDocumento) {
		this.importoDocumento = importoDocumento;
	}

	public java.lang.String getDescrizioneVoceDiSpesa() {
		return descrizioneVoceDiSpesa;
	}

	public void setDescrizioneVoceDiSpesa(java.lang.String descrizioneVoceDiSpesa) {
		this.descrizioneVoceDiSpesa = descrizioneVoceDiSpesa;
	}

	public java.lang.Double getImportoVoceDiSpesa() {
		return importoVoceDiSpesa;
	}

	public void setImportoVoceDiSpesa(java.lang.Double importoVoceDiSpesa) {
		this.importoVoceDiSpesa = importoVoceDiSpesa;
	}

	public java.lang.String getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(java.lang.String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.util.ArrayList<DocumentoAllegatoDTO> getDocumentoAllegato() {
		return documentoAllegato;
	}

	public void setDocumentoAllegato(java.util.ArrayList<DocumentoAllegatoDTO> documentoAllegato) {
		this.documentoAllegato = documentoAllegato;
	}

	public RigaNotaDiCreditoItemDTO() {}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nRigaNotaDiCreditoItemDTO: ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				if ("documentoAllegato".equalsIgnoreCase(propName)) {
					ArrayList<DocumentoAllegatoDTO> lista = (ArrayList<DocumentoAllegatoDTO>) BeanUtil.getPropertyValueByName(this, propName);
					if (lista != null) {
						sb.append("\ndocumentoAllegato:");
						for (DocumentoAllegatoDTO item : lista) {
							sb.append("\n   DocumentoAllegatoDTO = "+item.toString());
						}
					} else {
						sb.append("\ndocumentoAllegato = null");
					}
				} else {
					sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));	
				}				
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
