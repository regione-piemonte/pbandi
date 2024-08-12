package it.csi.pbandi.pbweb.dto.archivioFile;

public class UserSpaceDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long total = null;
	private java.lang.Long used = null;
	
	public java.lang.Long getTotal() {
		return total;
	}
	public void setTotal(java.lang.Long total) {
		this.total = total;
	}
	public java.lang.Long getUsed() {
		return used;
	}
	public void setUsed(java.lang.Long used) {
		this.used = used;
	}

}
