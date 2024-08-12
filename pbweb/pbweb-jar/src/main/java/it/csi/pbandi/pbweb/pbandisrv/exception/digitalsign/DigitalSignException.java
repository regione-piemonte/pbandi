package it.csi.pbandi.pbweb.pbandisrv.exception.digitalsign;

import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UserException;
import it.csi.csi.wrapper.UnrecoverableException;

public class DigitalSignException extends UserException

{

	static final long serialVersionUID = 1;

	public DigitalSignException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}

	public DigitalSignException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}

	public DigitalSignException(String msg, Throwable nested) {
		super(msg, nested);
	}

	public DigitalSignException(String msg) {
		super(msg);
	}
}
