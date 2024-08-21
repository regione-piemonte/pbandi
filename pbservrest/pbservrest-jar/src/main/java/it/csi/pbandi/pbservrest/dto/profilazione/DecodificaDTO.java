/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto.profilazione;

import it.csi.pbandi.pbservrest.dto.utils.ILoggable;

public class DecodificaDTO<T> implements ILoggable
{
  /** serialVersionUID */
  private static final long serialVersionUID = 4601339650061084885L;
  private T                 id;
  private String            descrizione;
  private String            codice;
  private String 			codiceDescrizione;
  public DecodificaDTO()
  {
  }
  
  public DecodificaDTO(T id, String descrizione)
  {
    this.id = id;
    this.descrizione = descrizione;
  }
  
  public DecodificaDTO(T id, String codice, String descrizione)
  {
    this.id = id;
    this.descrizione = descrizione;
    this.codice = codice;
  }

  public T getId()
  {
    return id;
  }

  public void setId(T id)
  {
    this.id = id;
  }

  public String getDescrizione()
  {
    return descrizione;
  }

  public void setDescrizione(String descrizione)
  {
    this.descrizione = descrizione;
  }

  public String getCodice()
  {
    return codice;
  }

  public void setCodice(String codice)
  {
    this.codice = codice;
  }

	public String getCodiceDescrizione() {
		return codice+ " - " + descrizione;
	}
	
	public String getCodDescrizione() {
		return codiceDescrizione;
	}
	
	public void setCodiceDescrizione(String codiceDescrizione) {
		this.codiceDescrizione = codiceDescrizione;
	}
}
