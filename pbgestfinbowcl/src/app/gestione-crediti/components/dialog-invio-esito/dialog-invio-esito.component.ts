/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, Inject, OnInit, ViewChild } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { Constants, UserInfoSec } from "@pbandi/common-lib";
import { UserService } from "src/app/core/services/user.service";
import { ModGarResponseService } from "../../services/modgar-response-service.service";

@Component({
    selector: 'dialog-invio-esito',
    templateUrl: 'dialog-invio-esito.component.html',
    styleUrls: ['dialog-invio-esito.component.scss'],
})

export class DialogInvioEsito implements OnInit {
    @ViewChild('uploadFileEscussione')myFileEscussione: ElementRef;

    subscribers: any = {};
    idOp = Constants.ID_OPERAZIONE_GESTIONE_GARANZIE;
    showError: boolean = false;
    showSucc: boolean = false;
    errorMsg: string = "";
    succMsg: string = "";
    isLoading: boolean = false;
    
    // Importanti
    idUtente: number;
    idEscussione: number;
    idStatoEscussione: number;
    idTipoEscussione: number;
    idProgetto: number;

    idSoggetto: string;
    //idUtente: any;
    user: UserInfoSec;
    codUtente: string;
    idTarget: number;
    //idEscussione: number;
    nomeFile: string;
    file: File;
    allegati = [];
    messageAllegato: string;
    erroreAllegato: boolean;
    messageError: string;
    error: boolean;
    allegatiInseriti: boolean = false;
    //idStatoEscussione: any;

    constructor(
        private escussioneService: ModGarResponseService,
        private route: ActivatedRoute,
        public dialogRef: MatDialogRef<DialogInvioEsito>,
        private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: any,
    ) {
        console.log("dialog data:", data);
        this.idUtente = data.idUtente,
        this.idEscussione = data.idEscussione,
        this.idStatoEscussione = data.idStatoEscussione,
        this.idTipoEscussione = data.idTipoEscussione,
        this.idProgetto = data.idProgetto
    }

    ngOnInit(): void {

        //this.isLoading = true;

        /*this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.codUtente = this.user.codFisc;
                this.idEscussione = this.data.idEscussione;
                this.idStatoEscussione = this.data.idStatoEscussione;
                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.idProgetto;
                });

            }

        });*/

    }

    closeDialog() {
        this.dialogRef.close();
    }

       /* downloadAllegato(files: AllegatoVO) {

        this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), files.idDocIndex.toString()).subscribe(res => {

            if (res) {
                saveAs(res, files.nomeFile);
            }

        });

    } */

    


    handleFileEscussione(files: Array<File>) {
        console.log(files[0]);
        this.resetMessage();
        if (!files[0].name.endsWith(".pdf") && !files[0].name.endsWith(".PDF") && !files[0].name.endsWith(".DOC") && !files[0].name.endsWith(".doc") && !files[0].name.endsWith(".zip") && !files[0].name.endsWith(".ZIP")) {
          this.showMessageError("Il file deve avere un'estensione .pdf, .doc oppure .zip");
        } 
        else {
          this.allegati.push(files[0]);
          this.allegatiInseriti = true;
        }
       
    }

    
    eliminaFileIntegrazione(i) {
        this.myFileEscussione.nativeElement.value = '';
        this.allegati = [];
        this.allegatiInseriti = false;
    }

    inviaEsito() {
        this.showError = false;
        this.showSucc = false;


        if (this.idStatoEscussione != 5 && this.allegati.length <= 0) {
            this.errorMsg = "È necessario inserire una lettera accompagnatoria prima di procedere."
            this.showError = true;
            return;

        } else {

            //this.showError = false;
            this.isLoading = true;

            //console.log("Allegati caricai:", this.allegati);

            this.escussioneService.inserisciAllegatoEsito(this.allegati, this.idProgetto, this.idEscussione, this.idStatoEscussione, this.idTipoEscussione).subscribe(data => {

                this.isLoading = false;

                if (data.esito) {
                    this.succMsg = "Esito inviato correttamente.";
                    this.showError = false;
                    this.showSucc = true;
                    setTimeout(() => {
                        this.erroreAllegato = null;
                        this.allegati = [];
                        this.closeDialog();
                    }, 2000);
                } else {
                    this.showError = true;
                    this.showSucc = false;
                    this.errorMsg = "Non è stato possibile inviare l'esito.";
                }
            }, err => {
                this.showError = true;
                this.errorMsg = "Non è stato possibile inviare l'esito.";
                this.isLoading = false;
            })
        }
    }

    

    showMessageError(errorMsg: string) {
        this.resetMessage();
        this.errorMsg = errorMsg;
        this.showError = true;
    }

    resetMessage() {
        this.showError = false;
        this.showSucc = false;
        // this.success = false;
    }


}