/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { animate, state, style, transition, trigger } from "@angular/animations";
import { Component, Inject, OnInit } from "@angular/core";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { UserService } from "src/app/core/services/user.service";
import { AutoUnsubscribe } from "src/app/shared/decorator/auto-unsubscribe";
import { AttivitaDTO } from "../../commons/dto/attivita-dto";
import { AzioneRecuperoBancaVO } from "../../commons/dto/azione-recupero-bancaVO";
import { StoricoAzioneRecuperoBancaDTO } from "../../commons/dto/storico-azione-recupero-banca-dto";
import { AzioniRecuperoBancaService } from "../../services/azioni-recupero-banca-service";

@Component({
    selector: 'dialog-azioni-recupero-banca',
    templateUrl: './dialog-azioni-recupero-banca.component.html',
    styleUrls: ['./dialog-azioni-recupero-banca.component.scss'],
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({ height: '0px', minHeight: '0' })),
            state('expanded', style({ height: '*' })),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ]
})

@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogAzioniRecuperoBanca implements OnInit {
    azioneRecuperoBancaVO: AzioneRecuperoBancaVO;
    subscribers: any = {};
    user: UserInfoSec;
    isStorico: boolean = false;
    isStorico2: boolean = false;
    isAttiva: boolean = true;
    idUtente: number;
    storicoAzioniRecupBanca: Array<StoricoAzioneRecuperoBancaDTO>;
    storicoAzioni: Array<StoricoAzioneRecuperoBancaDTO>;
    idProgetto: number;
    dataAzione: Date;
    numProtocollo: number;
    note: string;
    idAttivitaAzione: number;
    descAttivitaAzione: string;
    attivitaAzione: AttivitaDTO;
    loadedUser: boolean;
    loadedAzioni: boolean;
    isSaveSuccess: boolean;
    displayedColumns: string[] = ['azione', 'dataAzione', 'utente', 'actions'];
    displayedColumns2: string[] = ['dataInserimento', 'istruttore', 'Azione'];
    isStoricoLength: boolean;
    loadedStorico: boolean = true;
    disableStorico: boolean;
    isLoading = false;

    idModalitaAgevolazione: number = 10;

    constructor(
        public dialogRef: MatDialogRef<DialogAzioniRecuperoBanca>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private route: ActivatedRoute,
        private router: Router,
        private azioneRecuperoBancaService: AzioniRecuperoBancaService,
        private userService: UserService,
        private handleExceptionService: HandleExceptionService,
        public dialog: MatDialog

    ) {console.log("Dialog data: ", data)}
    /* MA CHE CA*** TI PASSI L'ID SE SIAMO SU GARANZIE!!! MANNAGGIA ALLA M
    E LA COSA PIU' COMICA E' CHE NEANCHE TE LO PASSI DALL'ORIGINE!!! */
    //idModalitaAgevolazione = this.data.idModalitaAgevolazione;

    ngOnInit(): void {
        this.loadedUser = false
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.loadedUser = true;

                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.progetto;
                });

                this.loadAzioni();
            }
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });

    }

    loadAzioni() {
        this.loadedAzioni = false;
        this.idProgetto = this.data.idProgetto;

        this.subscribers.storico = this.azioneRecuperoBancaService.getStoricoAzioneRecupBanca(this.idUtente, this.idProgetto, this.idModalitaAgevolazione).subscribe(data => {

            if (data) {
                this.storicoAzioniRecupBanca = data;

                if (this.storicoAzioniRecupBanca.length > 0) {
                    this.isStorico = true;
                }

            }

            this.loadedAzioni = true;
        }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
        });

    }

    closeDialog() {
        this.dialogRef.close();
    }

}