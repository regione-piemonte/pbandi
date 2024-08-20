/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { ActivatedRoute } from "@angular/router";
import { HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { UserService } from "src/app/core/services/user.service";
import { SchedaClienteMain } from "../../commons/dto/scheda-cliente-main";
import { ModGarResponseService } from "../../services/modgar-response-service.service";
import { EditDialogComponent } from "../dialog-edit-modben/dialog-edit.component";
import { SaveStatoEscussioneGaranzia } from "../../commons/dto/save-statoescussione-garanzia.all";

@Component({
    selector: 'dialog-editstatoescussione',
    templateUrl: './dialog-edit-statoescussione.component.html',
    styleUrls: ['./dialog-edit-statoescussione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogEditStatoescussioneComponent implements OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;

    schedaCliente: SchedaClienteMain = new SchedaClienteMain;
    subscribers: any = {};

    today = new Date();
    
    public formStatoEscussione: FormGroup;
    idCurrentRecord: string;
    idComponentToShow: number;
    idSoggetto: string;
    idUtente: any;
    descStatoEscussione: any;
    staEsc_descStatoEscussione: string;
    staEsc_dtInizioValidita: any;
    listaStatiEscussione: Array<String> = new Array<String>();
    staEsc_note: string;
    user: UserInfoSec;
    codUtente: string;
    idProgetto: any;
    idEscussione: any;
    idStatoEscussione: any;
    message: String = "";
    errore: boolean = false;
    errorMsg: string = "";
    showError: boolean = false;
    showSuccess: boolean = false;
    isLoading = false;
    infoEscussione: any;

    selezionato: any;

    constructor(
        private escussioneService: ModGarResponseService,
        private responseService: ModGarResponseService,
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private handleExceptionService: HandleExceptionService,
        public dialogRef: MatDialogRef<EditDialogComponent>,
        private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {

        
    }

    ngOnInit(): void {
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.codUtente =  this.user.codFisc; 
                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.idProgetto;
                });
            }
            this.idStatoEscussione = this.data.idStatoEscussione;
            this.idEscussione = this.data.idEscussione;
            this.infoEscussione = this.data.infoEscussione;
            this.selezionato = this.infoEscussione.statoEscussione;

            this.formStatoEscussione = this.fb.group({
                descStatoEscussione: new FormControl(this.infoEscussione.statoEscussione, [Validators.required]),
                dtInizioValidita: new FormControl(this.infoEscussione.dataStato, [Validators.required]),
                dtNotifica: new FormControl(this.infoEscussione.dataNotifica),
                note: new FormControl(this.infoEscussione.note),
                idEscussione : new FormControl(this.data.infoEscussione),
            });

                this.escussioneService.initDialogEscussione(this.idStatoEscussione).subscribe(
                    (data) => {
                        this.listaStatiEscussione = data.statiEscussione;
                          },
                          (err) => {
                 }
               );
        });
    }

    

    closeDialog() {
        this.dialogRef.close();
    }

    onConfirmClick(): void {
        if(this.formStatoEscussione.valid) {
            let formControls = this.formStatoEscussione.getRawValue();
            let temp: SaveStatoEscussioneGaranzia = new SaveStatoEscussioneGaranzia;

            temp.idEscussione = this.idEscussione;
            temp.descStatoEscussione = formControls.descStatoEscussione;
            temp.dtInizioValidita = formControls.dtInizioValidita;
            temp.dtNotifica = formControls.dtNotifica;
            temp.note = formControls.note;
            temp.listaAllegatiPresenti = this.data.listaAllegatiPresenti;
            this.escussioneService.updateStatoEscussione(temp).subscribe(
                (data) => {
                    if (data.esito  == true) {
                        this.showSuccess = true;
                        this.message = "Stato cambiato con successo.";
                        this.showError = false;
                      setTimeout(() => {
                        this.showError = false;
                        this.dialogRef.close({
                            idEscussione: data.id,
                        });
                      }, 2000); 
                    }
                    else if (data.esito == false) {
                        this.showSuccess = false;
                        this.showError = true;
                        this.errorMsg = "Non è stato possibile cambiare lo stato.";
                    }
                      },
                      (err) => {
                        if(err.ok == false){
                            this.showSuccess = false;
                            this.showError = true;
                            this.message = err.statusText;
                        }
                        }
                      ); 
        } else {
            this.showError = true;
            this.errorMsg = "Compila tutti i campi obbligatori!";
        }


    }

}