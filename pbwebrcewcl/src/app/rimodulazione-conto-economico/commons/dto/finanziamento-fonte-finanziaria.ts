/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class FonteFinanziaria {
    public descFonteFinanziaria: string;
    public importoUltimaQuota: number;
    public percentualeUltimaQuota: number;
    public importoQuota: number;
    public percentualeQuota: number;
    public modificabile: boolean;
    public isSubTotale: boolean;
    public isFontePrivata: boolean;
    public idFonteFinanziaria: number;
    public isFonteTotale: boolean;
}

export class FinanziamentoFonteFinanziaria extends FonteFinanziaria {
    public idPeriodo: number;
    public descPeriodo: string;
    public idNorma: number;
    public idDelibera: number;
    public idComune: number;
    public idProvincia: number;
    public idSoggettoPrivato: number;
    public estremiProvvedimento: string;
    public note: string;
    public flagEconomie: boolean;
    public progrProgSoggFinanziat: number;
    public flagLvlprj: boolean;
    public flagCertificazione: boolean;
    public descBreveTipoSoggFinanziat: string;
    public linkDettaglio: string;
    public linkModifica: string;
    public percQuotaDefault: number;
}
