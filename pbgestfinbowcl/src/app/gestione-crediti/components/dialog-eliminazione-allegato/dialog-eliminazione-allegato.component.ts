/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { ActivatedRoute, Router } from "@angular/router";
import { ArchivioFileService, Constants, HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { ConfigService } from "src/app/core/services/config.service";
import { UserService } from "src/app/core/services/user.service";
import { AutoUnsubscribe } from "src/app/shared/decorator/auto-unsubscribe";
import { AllegatoVO } from "../../commons/dto/allegatoVO";
import { ModGarResponseService } from "../../services/modgar-response-service.service";
import { EditDialogComponent } from "../dialog-edit-modben/dialog-edit.component";

@Component({
    selector: 'app-eliminazione-allegato',
    templateUrl: 'dialog-eliminazione-allegato.component.html',
    styleUrls: ['dialog-eliminazione-allegato.component.scss'],
})

@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogEliminazioneAllegato implements OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;
    spinner: boolean;
    idOp = Constants.ID_OPERAZIONE_GESTIONE_GARANZIE;
    public subscribers: any = {};
    isLoading = false;
    user: UserInfoSec;
    idUtente: any;
    idProgetto: any;
    codUtente: string;
    message: string;
    errore: boolean;

    constructor(
        private resService: ModGarResponseService,
        public dialog: MatDialog,
        private route: ActivatedRoute,
        private userService: UserService,
        private router: Router,
        private handleExceptionService: HandleExceptionService,
        private archivioFileService: ArchivioFileService,
        private configService: ConfigService,
        public dialogRef: MatDialogRef<EditDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) { }

    ngOnInit(): void {

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if(data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.codUtente = this.user.codFisc;

                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.idProgetto;
                });

            }

        })

    }

    eliminaAllegato() {
        let idAllegato = new AllegatoVO;
        idAllegato.idDocIndex = this.data.idDocIndex;

        this.resService.deleteAllegato(idAllegato).subscribe(data => {

            if (data  == true) {
                this.message = "Allegato eliminato correttamente";
                this.errore = false;
                setTimeout(() => {
                  this.errore = null;
                  this.closeDialog();
                }, 2000); 
              }
              else if (data === false) {
                this.errore = true;
                this.message = "Non è stato possibile eliminare l'allegato";
              }


        })
        
    }

    closeDialog() {
        this.dialogRef.close();
    }

}