/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RigaValidazioneItemDTO {
    public rigaPagamento: boolean;
    public modalitaPagamento: string;
    public dataPagamento: string;
    public importoTotalePagamento: number;
    public descrizioneVoceDiSpesa: string;
    public importoAssociatoVoceDiSpesa: number;
    public importoValidato: string;
    public importoValidatoVoceDiSpesa: number;
    public idPagamento: number;
    public statoPagamento: string;
    public idQuotaParte: number;
    public idDocumentoDiSpesa: number;
    public rigaModificabile: boolean;
    public dataPagamentoVisible: boolean;
    public dataValutaVisible: boolean;
    public dataValuta: string;
    public idDichiarazioneDiSpesa: number;
    public idRigoContoEconomico: number;
    public progrPagQuotParteDocSp: number;
    public importoTotaleRettifica: number;
    public importoValidatoPrecedenteVoceDiSpesa: number;
    public riferimentoRettifica: string;
    public linkModifica: string;
    public linkNote: string;
    public linkRettifiche: string;
    public note: string;
    public oreLavorate: number;
    public importoResiduoAmmesso: number;
    public importoAmmesso: number;
}

export class RigaValidazioneItemDTOFormatted extends RigaValidazioneItemDTO {
    public importoValidatoVoceDiSpesaFormatted: string;
}