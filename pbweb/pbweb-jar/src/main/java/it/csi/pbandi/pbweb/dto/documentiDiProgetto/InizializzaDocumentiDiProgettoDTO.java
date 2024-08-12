package it.csi.pbandi.pbweb.dto.documentiDiProgetto;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class InizializzaDocumentiDiProgettoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	
	private ArrayList<CodiceDescrizioneDTO> comboBeneficiari;
	
	private ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoIndex;
	
	private ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoIndexUploadable;
	
	private ArrayList<CodiceDescrizioneDTO> categorieAnagrafica;
	
	private Long dimMaxSingoloFile = null;
	
	private ArrayList<String> estensioniConsentite = null;

	public ArrayList<String> getEstensioniConsentite() {
		return estensioniConsentite;
	}

	public void setEstensioniConsentite(ArrayList<String> estensioniConsentite) {
		this.estensioniConsentite = estensioniConsentite;
	}

	public Long getDimMaxSingoloFile() {
		return dimMaxSingoloFile;
	}

	public void setDimMaxSingoloFile(Long dimMaxSingoloFile) {
		this.dimMaxSingoloFile = dimMaxSingoloFile;
	}

	public ArrayList<CodiceDescrizioneDTO> getCategorieAnagrafica() {
		return categorieAnagrafica;
	}

	public void setCategorieAnagrafica(ArrayList<CodiceDescrizioneDTO> categorieAnagrafica) {
		this.categorieAnagrafica = categorieAnagrafica;
	}

	public ArrayList<CodiceDescrizioneDTO> getComboTipiDocumentoIndexUploadable() {
		return comboTipiDocumentoIndexUploadable;
	}

	public void setComboTipiDocumentoIndexUploadable(ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoIndexUploadable) {
		this.comboTipiDocumentoIndexUploadable = comboTipiDocumentoIndexUploadable;
	}

	public ArrayList<CodiceDescrizioneDTO> getComboBeneficiari() {
		return comboBeneficiari;
	}

	public void setComboBeneficiari(ArrayList<CodiceDescrizioneDTO> comboBeneficiari) {
		this.comboBeneficiari = comboBeneficiari;
	}
	
	public ArrayList<CodiceDescrizioneDTO> getComboTipiDocumentoIndex() {
		return comboTipiDocumentoIndex;
	}

	public void setComboTipiDocumentoIndex(ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoIndex) {
		this.comboTipiDocumentoIndex = comboTipiDocumentoIndex;
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
