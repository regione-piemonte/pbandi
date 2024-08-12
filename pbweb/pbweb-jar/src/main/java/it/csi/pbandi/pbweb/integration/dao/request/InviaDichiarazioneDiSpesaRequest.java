package it.csi.pbandi.pbweb.integration.dao.request;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class InviaDichiarazioneDiSpesaRequest {
	
	private Long idBandoLinea;
	private Long idProgetto;
	private Long idProgettoContributoPiuGreen;				// non obbligatorio.
	private Long idSoggetto;
	private Long idRappresentanteLegale;
	private Long idDelegato;								// non obbligatorio.
	private Date dataLimiteDocumentiRendicontabili;
	private String codiceTipoDichiarazioneDiSpesa;			// I (intermedia), F (finale), IN (integrativa).		
	private String nomeFileRelazioneTecnica;
	private byte[] bytesRelazioneTecnica;
	
	public String getNomeFileRelazioneTecnica() {
		return nomeFileRelazioneTecnica;
	}

	public void setNomeFileRelazioneTecnica(String nomeFileRelazioneTecnica) {
		this.nomeFileRelazioneTecnica = nomeFileRelazioneTecnica;
	}

	public byte[] getBytesRelazioneTecnica() {
		return bytesRelazioneTecnica;
	}

	public void setBytesRelazioneTecnica(byte[] bytesRelazioneTecnica) {
		this.bytesRelazioneTecnica = bytesRelazioneTecnica;
	}

	public Long getIdBandoLinea() {
		return idBandoLinea;
	}

	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdProgettoContributoPiuGreen() {
		return idProgettoContributoPiuGreen;
	}

	public void setIdProgettoContributoPiuGreen(Long idProgettoContributoPiuGreen) {
		this.idProgettoContributoPiuGreen = idProgettoContributoPiuGreen;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Date getDataLimiteDocumentiRendicontabili() {
		return dataLimiteDocumentiRendicontabili;
	}

	public void setDataLimiteDocumentiRendicontabili(Date dataLimiteDocumentiRendicontabili) {
		this.dataLimiteDocumentiRendicontabili = dataLimiteDocumentiRendicontabili;
	}

	public String getCodiceTipoDichiarazioneDiSpesa() {
		return codiceTipoDichiarazioneDiSpesa;
	}

	public void setCodiceTipoDichiarazioneDiSpesa(String codiceTipoDichiarazioneDiSpesa) {
		this.codiceTipoDichiarazioneDiSpesa = codiceTipoDichiarazioneDiSpesa;
	}

	public Long getIdRappresentanteLegale() {
		return idRappresentanteLegale;
	}

	public void setIdRappresentanteLegale(Long idRappresentanteLegale) {
		this.idRappresentanteLegale = idRappresentanteLegale;
	}

	public Long getIdDelegato() {
		return idDelegato;
	}

	public void setIdDelegato(Long idDelegato) {
		this.idDelegato = idDelegato;
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
