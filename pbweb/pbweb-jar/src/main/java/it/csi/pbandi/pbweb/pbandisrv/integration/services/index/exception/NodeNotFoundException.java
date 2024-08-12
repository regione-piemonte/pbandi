package it.csi.pbandi.pbweb.pbandisrv.integration.services.index.exception;


public class NodeNotFoundException extends Exception {

	public NodeNotFoundException(String message) {
		super(message);
	}

	public NodeNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
