/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.exception;

import it.csi.csi.wrapper.UserException;

public class ContoEconomicoException extends UserException {
  static final long serialVersionUID = 1;

  public ContoEconomicoException(String msg, String nestedExcClassName, String nestedExcMessage, String nestedExcStackTrace) {
    super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
  }

  public ContoEconomicoException(String msg, String nestedExcClassName, String nestedExcMessage) {
    super(msg, nestedExcClassName, nestedExcMessage);
  }

  public ContoEconomicoException(String msg, Throwable nested) {
    super(msg, nested);
  }

  public ContoEconomicoException(String msg) {
    super(msg);
  }
}