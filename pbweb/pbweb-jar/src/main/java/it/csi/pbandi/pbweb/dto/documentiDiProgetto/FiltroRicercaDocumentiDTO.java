package it.csi.pbandi.pbweb.dto.documentiDiProgetto;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class FiltroRicercaDocumentiDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private Long idSoggettoBeneficiario = null;
	private Long idProgetto = null;
	private Long idTipoDocumentoIndex = null;
	private Date dataDal = null;
	private Date dataAl = null;
	private Boolean docInFirma = null;
	private Boolean docInviati = null;
	
	private java.lang.Long idSoggetto = null;
	private java.lang.String codiceRuolo = null;
	private java.lang.Long idBando = null;
	
	
	

	public Long getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}




	public void setIdSoggettoBeneficiario(Long idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}




	public Long getIdProgetto() {
		return idProgetto;
	}




	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}




	public Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}




	public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}




	public Date getDataDal() {
		return dataDal;
	}




	public void setDataDal(Date dataDal) {
		this.dataDal = dataDal;
	}




	public Date getDataAl() {
		return dataAl;
	}




	public void setDataAl(Date dataAl) {
		this.dataAl = dataAl;
	}




	public Boolean getDocInFirma() {
		return docInFirma;
	}




	public void setDocInFirma(Boolean docInFirma) {
		this.docInFirma = docInFirma;
	}




	public Boolean getDocInviati() {
		return docInviati;
	}




	public void setDocInviati(Boolean docInviati) {
		this.docInviati = docInviati;
	}




	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}




	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}




	public java.lang.String getCodiceRuolo() {
		return codiceRuolo;
	}




	public void setCodiceRuolo(java.lang.String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}




	public java.lang.Long getIdBando() {
		return idBando;
	}




	public void setIdBando(java.lang.Long idBando) {
		this.idBando = idBando;
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
