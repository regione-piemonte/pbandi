/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ImportiRevocaVO {

  public idGestioneRevoca: number;     //new
  public idProgetto: number;     //new
  public idBando: number;     //new
  public idDomanda: number;     //new

  public descModalitaAgevolazione: string;
  public importoAmmessoIniziale: number;
  public importoConcesso: number;
  public importoRevocato: number;
  public importoConcessoAlNettoRevocato: number;
  public importoErogato: number;
  public importoRecuperato: number;
  public importoErogatoAlNettoRecuperato: number;
  public importoRimborsato: number;
  public importoErogatoAlNettoRecuperatoRimborsato: number;

  }
