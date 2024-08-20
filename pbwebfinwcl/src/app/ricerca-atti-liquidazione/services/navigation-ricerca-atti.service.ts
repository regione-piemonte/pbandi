/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from "@angular/cdk/collections";
import { Injectable } from "@angular/core";
import { CodiceDescrizioneDTO } from "src/app/gestione-disimpegni/commons/codice-descrizione-vo";
import { GenericSelectVo } from "src/app/gestione-disimpegni/commons/generic-select-vo";

export interface PeriodicElement {
    select: number;
    impegno: string;
    impegnoReimp: string;
    annoEsercizio: string;
    capitolo: string;
    codFisc: string;
    ragSocCUP: string;
    provvedimento: string;
  }

@Injectable()
export class NavigationRicercaAttiService {
    private beneficiarioSelected: CodiceDescrizioneDTO;
    private progettoSelected: CodiceDescrizioneDTO;
    private annoEsercizio: string;
    private annoImpegno: string;
    private nImpegno: string;
    private annoAtto: string;
    private numeroAtto: string;
    private paginatorTable: Paginator = new Paginator(null, null);

    public set beneficiarioSelezionata(value: CodiceDescrizioneDTO) {
        this.beneficiarioSelected = value;
    }

    public get beneficiarioSelezionata(): CodiceDescrizioneDTO {
        return this.beneficiarioSelected;
    }

    public set progettoSelezionata(value: CodiceDescrizioneDTO) {
        this.progettoSelected = value;
    }

    public get progettoSelezionata(): CodiceDescrizioneDTO {
        return this.progettoSelected;
    }
    
    public set annoEsercizioSelected(value: string) {
        this.annoEsercizio = value;
    }

    public get annoEsercizioSelected(): string {
        return this.annoEsercizio;
    }

    public set annoImpegnoSelected(value: string) {
        this.annoImpegno = value;
    }

    public get annoImpegnoSelected(): string {
        return this.annoImpegno;
    }

    public set nImpegnoSelected(value: string) {
        this.nImpegno = value;
    }

    public get nImpegnoSelected(): string {
        return this.nImpegno;
    }

    public set annoAttoSelected(value: string) {
        this.annoAtto = value;
    }

    public get annoAttoSelected(): string {
        return this.annoAtto;
    }

    public set numeroAttoSelected(value: string) {
        this.numeroAtto = value;
    }

    public get numeroAttoSelected(): string {
        return this.numeroAtto;
    }

    public set paginatorPageIndexTable(value: number) {
        this.paginatorTable.pageIndex = value;
    }
    public get paginatorPageIndexTable(): number {
        return this.paginatorTable.pageIndex;
    }
    public set paginatorPageSizeTable(value: number) {
        this.paginatorTable.pageSize = value;
    }
    public get paginatorPageSizeTable(): number {
        return this.paginatorTable.pageSize;
    }
}

//utilità per navigazione
////////////////////////////////////////////////////////////////////////////////////////

export class Paginator {
    constructor(public pageIndex: number, public pageSize: number) { }
}