/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { animate, state, style, transition, trigger } from "@angular/animations";
import { Component, Inject, Input, OnInit } from "@angular/core";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { UserService } from "src/app/core/services/user.service";
import { AutoUnsubscribe } from "src/app/shared/decorator/auto-unsubscribe";
import { AttivitaDTO } from "../../commons/dto/attivita-dto";
import { SaldoStralcioVO } from "../../commons/dto/saldo-stralcio-VO";
import { StoricoSaldoStralcioDTO } from "../../commons/dto/storico-saldo-stralcio-dto";
import { SaldoStralcioService } from "../../services/saldo-stralcio-service";

@Component({
    selector: 'dialog-saldo-stralcio',
    templateUrl: './dialog-saldo-stralcio.component.html',
    styleUrls: ['./dialog-saldo-stralcio.component.scss'],
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({ height: '0px', minHeight: '0' })),
            state('expanded', style({ height: '*' })),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ]
})

@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogSaldoStralcio implements OnInit {
    @Input() item ;
    //@Input() idModalitaAgevolazione ;
    user: UserInfoSec;
    idUtente: number;
    saldoStralcioVO: SaldoStralcioVO;
    isStorico: boolean = false;
    isStorico2: boolean = false;
    isAttiva: boolean = true;
    listaAttivitaSaldoStralcio: Array<AttivitaDTO>;
    idProgetto: number;
    storicoSaldi: Array<StoricoSaldoStralcioDTO>;
    storicoSaldoStralcio: Array<StoricoSaldoStralcioDTO>;
    subscribers: any = {};
    displayedColumns: string[] = ['esito', 'dataEsito', 'utente', 'actions'];
    loadedSaldiStralci: boolean;
    loadedUser: boolean;
    loadedStorico: boolean = false;
    isSaveSuccess: boolean;
    isSaveError: boolean;
    disableStorico: boolean;
    isLoading = false;
    
    idModalitaAgevolazione: number = 10;

    constructor(
        public dialogRef: MatDialogRef<DialogSaldoStralcio>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private route: ActivatedRoute,
        private router: Router,
        private saldoStralcioService: SaldoStralcioService,
        private userService: UserService,
        private handleExceptionService: HandleExceptionService,
        public dialog: MatDialog
    ) {console.log("Dialog data: ", data)}

    ngOnInit(): void {
        this.loadedStorico = true;
        this.loadedUser = false;

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.loadedUser = true;

                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.progetto;
                });

                this.loadSaldo();    
            }

        });

    }

    loadSaldo() {
        this.loadedStorico = false;
        this.loadedSaldiStralci = false;
        this.idProgetto = this.data.idProgetto;

        this.subscribers.storico = this.saldoStralcioService.getStoricoSaldoStralcio(this.idUtente, this.idProgetto,this.idModalitaAgevolazione).subscribe(data => {

            if (data.length > 0) {
                this.storicoSaldoStralcio = data;
                this.isStorico = true;
                this.loadedSaldiStralci = true
            } else {
                this.loadedSaldiStralci = true;
            }
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });

    }

    closeDialog() {
        this.dialogRef.close();
    }

}