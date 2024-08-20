/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit, ViewChild } from "@angular/core";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { UserService } from "src/app/core/services/user.service";
import { InfoDettaglioRicercaGaranzieVO } from "../../commons/info-dettaglio-ricerca-garanzie-vo";
import { GestioneGaranzieService } from "../../services/gestione-garanzie.service";
import { animate, state, style, transition, trigger } from "@angular/animations";

@Component({
    selector: 'dialog-dettaglio-ricerca-garanzie',
    templateUrl: './dialog-dettaglio-ricerca-garanzie.component.html',
    styleUrls: ['./dialog-dettaglio-ricerca-garanzie.component.scss'],
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({ height: '0px', minHeight: '0' })),
            state('expanded', style({ height: '*' })),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ]
})

export class DialogDettaglioRicercaGaranzieComponent implements OnInit {
    
    subscribers: any = {};
    idComponent: number = 1
    title: string = "";
    user: UserInfoSec;
    idUtente: number;
    idProgetto: number;
    dettaglioRevoca: InfoDettaglioRicercaGaranzieVO = new InfoDettaglioRicercaGaranzieVO();
    dettaglioAzioni: Array<InfoDettaglioRicercaGaranzieVO> = new Array<InfoDettaglioRicercaGaranzieVO>();
    dettaglioSaldo: Array<InfoDettaglioRicercaGaranzieVO> = new Array<InfoDettaglioRicercaGaranzieVO>();
    speroSiaArrivatoQualcosa: boolean = true;
    azioniRecuperoColumns: Array<string> = ['attivita', 'dataAzione', 'numeroProtocollo', 'note', 'utenteModifica', 'dataModifica'];
    saldoStralcioColumns: string[] = ['esito', 'dataEsito', 'utente', 'actions'];

    isLoading: boolean = true;

    titolo1: string = "Dettaglio Revoca bancaria";
    titolo2: string = "Dettaglio Azioni recupero banca";
    titolo3: string = "Dettaglio Saldo e stralcio";

    constructor(
        public dialogRef: MatDialogRef<DialogDettaglioRicercaGaranzieComponent>,
        private garanzieService: GestioneGaranzieService,
        private userService: UserService,
        private handleExceptionService: HandleExceptionService,
        public dialog: MatDialog,
        @Inject(MAT_DIALOG_DATA) public data: {
            idComponent: number,
            idProgetto: number
        }
    ) {
        //console.log("Dialog data: ", data);
        this.idComponent = data.idComponent;
        this.idProgetto = data.idProgetto;

        switch (this.idComponent) {
            case 1:
                this.title = this.titolo1;
                break;
            case 2:
                this.title = this.titolo2;
                break;
            case 3:
                this.title = this.titolo3;
                break;
            default:
                this.title = this.titolo1;
                break;
        }
    }

    ngOnInit(): void {
        this.isLoading = true;
        this.speroSiaArrivatoQualcosa = true;

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
            }

            switch (this.idComponent) {
                case 1:
                    this.getRevocaBancaria();
                    break;
                case 2:
                    this.getAzioniRecuperoBanca();
                    break;
                case 3:
                    this.getSaldoStralcio();
                    break;
                default:
                    this.getRevocaBancaria();
                    break;
            }
        }, err => {
            this.handleExceptionService.handleBlockingError(err);
        });

    }



    getRevocaBancaria() {
        this.subscribers.revoca = this.garanzieService.getDettaglioRevocaBancaria(this.idProgetto).subscribe(data => {

            if (data) {
                this.dettaglioRevoca = data;
                this.speroSiaArrivatoQualcosa = true;
            } else {
                this.speroSiaArrivatoQualcosa = false;
            }
            this.isLoading = false;
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });
    }

    getAzioniRecuperoBanca() {
        this.subscribers.azione = this.garanzieService.getDettaglioAzioniRecuperoBanca(this.idProgetto).subscribe(data => {

            if (data) {
                this.dettaglioAzioni = data;
                this.speroSiaArrivatoQualcosa = true;
            } else {
                this.speroSiaArrivatoQualcosa = false;
            }
            this.isLoading = false;
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });
    }

    getSaldoStralcio() {
        this.subscribers.saldo = this.garanzieService.getDettaglioSaldoStralcio(this.idProgetto).subscribe(data => {

            if (data) {
                this.dettaglioSaldo = data;
                this.speroSiaArrivatoQualcosa = true;
            } else {
                this.speroSiaArrivatoQualcosa = false;
            }
            this.isLoading = false;
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });
    }

}