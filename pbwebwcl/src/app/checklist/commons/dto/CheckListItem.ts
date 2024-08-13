/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CheckListItem {
  constructor(
    public idCheckList: number,
    public idDichiarazione: number,
    public codiceTipo: string,
    public codiceStato: string,
    public versione: string,
    public descTipo: string,
    public descStato: string,
    public dataControllo: string,
    public flagRilevazioneIrregolarita: string,
    public soggettoControllore: string,
    public nome: string,
    public idProgetto: number,
    public idDocumentoIndex: number,

    public isModificabile: boolean,
    public isEliminabile:  boolean
  ) { }
}