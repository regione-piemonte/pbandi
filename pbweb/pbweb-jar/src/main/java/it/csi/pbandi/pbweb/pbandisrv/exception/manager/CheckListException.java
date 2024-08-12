package it.csi.pbandi.pbweb.pbandisrv.exception.manager;

public class CheckListException extends Exception {
	public CheckListException(String string, Throwable e) {
		super(string, e);
	}
	
	public CheckListException(String codiceMessaggio) {
		super(codiceMessaggio);
	}

	private static final long serialVersionUID = 1L;
}
