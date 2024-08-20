/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentoRevocaVO {

  public numeroRevoca: string;
  public idDocumento: string;
  public nomeFile: string;      //da mostrare come primo

  // public idTipoAnagrafica: string;
  // public descTipoAnagrafica: string;    //da mostrare come secondo

  public originiDocumento: string;

  public dataDocumento: string;     //da mostrare come terzo
  public idTipoDocumento: string;
  //se 47 allora è lett acc per emetti provv
  //se 47 allora è lett acc per
  //se 47 allora è lett acc per

  public bozza: boolean;

  //  private Long numeroRevoca;
  //   private Long idDocumento;
  //   private String nomeFile;

  //   private String originiDocumento;

  //   private Date dataDocumento;
  //   private Long idTipoDocumento;

}
