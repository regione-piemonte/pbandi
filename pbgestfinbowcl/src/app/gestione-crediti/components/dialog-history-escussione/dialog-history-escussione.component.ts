/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { animate, state, style, transition, trigger } from "@angular/animations";
import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { SchedaClienteMain } from "../../commons/dto/scheda-cliente-main";
import { NumberFormatPipe } from "../number-format/number.pipe";
import { ModGarResponseService } from "../../services/modgar-response-service.service";
import { MatTableDataSource } from "@angular/material/table";

@Component({
    selector: 'dialog-history-escussione',
    templateUrl: './dialog-history-escussione.component.html',
    styleUrls: ['./dialog-history-escussione.component.scss'],
    animations: [
        trigger('detailExpand', [
          state('collapsed', style({ height: '0px', minHeight: '0' })),
          state('expanded', style({ height: '*' })),
          transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ],
})

export class DialogHistoryEscussioneComponent implements OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;

    columns: string[] = ['dtRichEscussione', 'descTipoEscussione', 'descStatoEscussione', 'dtInizioValidita', 'dtNotifica', 'impRichiesto', 'impApprovato', 'visibility'];
    //innerColumns: string[] = ['causaleBonifico', '']
    schedaClienteMain: SchedaClienteMain = new SchedaClienteMain;
    storicoEscussione: Array<SchedaClienteMain> = [];
    informazioniEscussione: any;
    storicoEscussionePresente: boolean;
    isLoading: boolean = false;

    constructor(
        public dialogRef: MatDialogRef<DialogHistoryEscussioneComponent>,
        public modGarResponseService : ModGarResponseService,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {
        this.informazioniEscussione = this.data.info;
    }

    ngOnInit(): void {
        this.isLoading = true;

        this.informazioniEscussione = this.data.info;

        this.modGarResponseService.getVisualizzaStoricoEscussione(this.informazioniEscussione.idProgetto, this.informazioniEscussione.codBanca, this.informazioniEscussione.codiceFiscale).subscribe( data => {

            this.isLoading = false;
            
            if (data.length > 0) {
                this.storicoEscussionePresente = true;
            } else {
                this.storicoEscussionePresente = false;
            }

            if (data) {
                this.storicoEscussione = data;
                //    this.storicoEscussione = new MatTableDataSource<any>(data);
            }

        }, err => {
            this.storicoEscussionePresente = false;
            this.isLoading = false;
        })
    }


    closeDialog() {
        this.dialogRef.close();
    }

    

}