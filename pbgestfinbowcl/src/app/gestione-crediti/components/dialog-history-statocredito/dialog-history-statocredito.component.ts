/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { tick } from "@angular/core/testing";
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { StatoCreditoVO } from "src/app/gestione-garanzie/commons/stato-credito-vo";
import { SchedaClienteMain } from "../../commons/dto/scheda-cliente-main";
import { ModGarResponseService } from "../../services/modgar-response-service.service";

@Component({
    selector: 'dialog-history-statocredito',
    templateUrl: './dialog-history-statocredito.component.html',
    styleUrls: ['./dialog-history-statocredito.component.scss']
})

export class DialogHistoryStatocreditoComponent implements OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;

    columns: string[] = ['descStato', 'dtModifica','utenteModifica'];
    schedaClienteMain: SchedaClienteMain = new SchedaClienteMain;
    storicoStatocredito: Array<SchedaClienteMain> = [];

    storico: Array<StatoCreditoVO>= [];  
    idProgetto: any;
    idUtente: any;
    subscribers: any = {};
    storicoPresente: boolean = false;
    isLoading: boolean = false;

    constructor(
        public dialogRef: MatDialogRef<DialogHistoryStatocreditoComponent>,
        public modGarResponseService: ModGarResponseService,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {
        // this.schedaClienteMain = this.data.schedaClienteMain;
        // this.storicoStatocredito = this.data.storicoStatocredito[0];
    }

    ngOnInit(): void {
        this.isLoading = true;

        console.log(this.storicoStatocredito);
        this.idProgetto = this.data.idProgetto;
        this.idUtente = this.data.idUtente;

        this.subscribers.storicoStatocredito = this.modGarResponseService.getStoricoStatoCredito(this.idProgetto, this.idUtente).subscribe(data => {

            this.isLoading = false;

            if (data.length > 0) {
                this.storicoPresente = true;
            } else {
                this.storicoPresente = false;
            }

            if (data) {
                this.storico = data;
                //    this.storicoEscussione = new MatTableDataSource<any>(data);
            }
        }, err => {
            this.storicoPresente = false;
            this.isLoading = false;
        });

    }

    closeDialog() {
        this.dialogRef.close();
    }

}