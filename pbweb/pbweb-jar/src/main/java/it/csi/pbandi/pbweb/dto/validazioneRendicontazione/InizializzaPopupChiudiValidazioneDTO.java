package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.appalti.AppaltoProgetto;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class InizializzaPopupChiudiValidazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private String note;
	private Boolean checkDsIntegrativaVisibile;
	private Boolean checkDsIntegrativaSelezionatoENonModificabile;
	private ArrayList<String> messaggi; 
	private Boolean warningImportoMaggioreAmmesso;
	private  ArrayList<AppaltoProgetto> appalti;
	
	public ArrayList<AppaltoProgetto> getAppalti() {
		return appalti;
	}
	public void setAppalti(ArrayList<AppaltoProgetto> appalti) {
		this.appalti = appalti;
	}
	public Boolean getWarningImportoMaggioreAmmesso() {
		return warningImportoMaggioreAmmesso;
	}
	public void setWarningImportoMaggioreAmmesso(Boolean warningImportoMaggioreAmmesso) {
		this.warningImportoMaggioreAmmesso = warningImportoMaggioreAmmesso;
	}
	public Boolean getCheckDsIntegrativaSelezionatoENonModificabile() {
		return checkDsIntegrativaSelezionatoENonModificabile;
	}
	public void setCheckDsIntegrativaSelezionatoENonModificabile(Boolean checkDsIntegrativaSelezionatoENonModificabile) {
		this.checkDsIntegrativaSelezionatoENonModificabile = checkDsIntegrativaSelezionatoENonModificabile;
	}
	public ArrayList<String> getMessaggi() {
		return messaggi;
	}
	public void setMessaggi(ArrayList<String> messaggi) {
		this.messaggi = messaggi;
	}
	public Boolean getCheckDsIntegrativaVisibile() {
		return checkDsIntegrativaVisibile;
	}
	public void setCheckDsIntegrativaVisibile(Boolean checkDsIntegrativaVisibile) {
		this.checkDsIntegrativaVisibile = checkDsIntegrativaVisibile;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
