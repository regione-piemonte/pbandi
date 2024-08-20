/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from "@angular/cdk/collections";
import { Injectable } from "@angular/core";
import { EnteCompetenzaDTO } from "../commons/ente-competenza-vo";
import { GenericSelectVo } from "../commons/generic-select-vo";
import { ImpegnoDTO } from "../commons/impegno-vo";

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
export class NavigationGestioneImpegniService {
    private annoEsercizioSelected: string;
    private direzioneProvvedimentoSelected: EnteCompetenzaDTO;
    private checkedImpegniReimputati: boolean;
    private annoImpegno: string;
    private nImpegno: string;
    private annoProvvedimento: string;
    private nProvvedimento: string;
    private nCapitolo: string;
    private ragioneSocialeBeneficiarioImpegno: string;
    private paginatorTable: Paginator = new Paginator(null, null);
    private selection = new SelectionModel<ImpegnoDTO>(true, []);

    public set selectionSelezionata(value: SelectionModel<ImpegnoDTO>) {
        this.selection = value;
    }

    public get selectionSelezionata(): SelectionModel<ImpegnoDTO> {
        return this.selection;
    }

    public set annoEsercizioSelezionata(value: string) {
        this.annoEsercizioSelected = value;
    }

    public get annoEsercizioSelezionata(): string {
        return this.annoEsercizioSelected;
    }

    public set direzioneProvvedimentoSelezionata(value: EnteCompetenzaDTO) {
        this.direzioneProvvedimentoSelected = value;
    }

    public get direzioneProvvedimentoSelezionata(): EnteCompetenzaDTO {
        return this.direzioneProvvedimentoSelected;
    }

    public set checkedImpegniReimputatiSelected(value: boolean) {
        this.checkedImpegniReimputati = value;
    }

    public get checkedImpegniReimputatiSelected(): boolean {
        return this.checkedImpegniReimputati;
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

    public set annoProvvedimentoSelected(value: string) {
        this.annoProvvedimento = value;
    }

    public get annoProvvedimentoSelected(): string {
        return this.annoProvvedimento;
    }

    public set nProvvedimentoSelected(value: string) {
        this.nProvvedimento = value;
    }

    public get nProvvedimentoSelected(): string {
        return this.nProvvedimento;
    }

    public set nCapitoloSelected(value: string) {
        this.nCapitolo = value;
    }

    public get nCapitoloSelected(): string {
        return this.nCapitolo;
    }

    public set ragioneSocialeBeneficiarioImpegnoSelected(value: string) {
        this.ragioneSocialeBeneficiarioImpegno = value;
    }

    public get ragioneSocialeBeneficiarioImpegnoSelected(): string {
        return this.ragioneSocialeBeneficiarioImpegno;
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