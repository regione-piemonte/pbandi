package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;


public class AttributoEsitoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private int id;
	private String attrib;
	private String attribBreve;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAttrib() {
		return attrib;
	}
	public void setAttrib(String attrib) {
		this.attrib = attrib;
	}
	public String getAttribBreve() {
		return attribBreve;
	}
	public void setAttribBreve(String attribBreve) {
		this.attribBreve = attribBreve;
	}
	
	
	
	
}
