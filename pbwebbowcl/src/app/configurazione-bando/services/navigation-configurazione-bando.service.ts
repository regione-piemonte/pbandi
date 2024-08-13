/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from "@angular/core";
import { NormativaVo } from "../commons/vo/normativa-vo";

@Injectable()
export class NavigationConfigurazioneBandoService {
    private normativaSelected: NormativaVo;
    private nomeBandoLinea: string;
    private nomeBando: string;
    private paginatorTable: Paginator = new Paginator(null, null);

    public set normativaSelezionata(value: NormativaVo) {
        this.normativaSelected = value;
    }

    public get normativaSelezionata(): NormativaVo {
        return this.normativaSelected;
    }

    public set nomeBandoLineaSelected(value: string) {
        this.nomeBandoLinea = value;
    }

    public get nomeBandoLineaSelected(): string {
        return this.nomeBandoLinea;
    }

    public set nomeBandoSelected(value: string) {
        this.nomeBando = value;
    }

    public get nomeBandoSelected(): string {
        return this.nomeBando;
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