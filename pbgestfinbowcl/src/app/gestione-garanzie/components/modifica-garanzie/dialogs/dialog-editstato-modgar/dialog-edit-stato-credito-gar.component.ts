/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ActivatedRoute } from "@angular/router";
import { HandleExceptionService, UserInfoSec } from "@pbandi/common-lib";
import { UserService } from "src/app/core/services/user.service";
import { StatoCreditoVO } from "src/app/gestione-garanzie/commons/stato-credito-vo";
import { AutoUnsubscribe } from "src/app/shared/decorator/auto-unsubscribe";
import { SaveStatocreditoGaranzia } from "../../../../../gestione-crediti/commons/dto/save-statocredito-garanzia.all";
import { SchedaClienteMain } from "../../../../../gestione-crediti/commons/dto/scheda-cliente-main";
import { ModGarResponseService } from "../../../../../gestione-crediti/services/modgar-response-service.service";
import { EditDialogComponent } from "../../../../../gestione-crediti/components/dialog-edit-modben/dialog-edit.component";

@Component({
    selector: 'dialog-edit-stato-credito-gar',
    templateUrl: './dialog-edit-stato-credito-gar.component.html',
    styleUrls: ['./dialog-edit-stato-credito-gar.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogEditstatoModgarComponent implements OnInit {

    schedaCliente: SchedaClienteMain = new SchedaClienteMain;
    subscribers: any = {};
    showError: boolean = false;
    errorMsg: string = "Oh no!"
    isLoading = false;
    public formStatocredito: FormGroup;
    idCurrentRecord: string;
    idComponentToShow: number;
    idSoggetto: string;
    idUtente: any;
    staCred_optionsStatoCredito: Array<string> = [];
    staCred_statoCredito: string;
    staCred_dtModifica: any;
    user: UserInfoSec;
    codUtente: string;
    idProgetto: any;
    listaStatiCred: Array<String> = new Array<String>();
    statoCreditoVO:  StatoCreditoVO = new StatoCreditoVO(); 

    today = new Date();

    constructor(
        private responseService: ModGarResponseService,
        private resService: ModGarResponseService,
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private handleExceptionService: HandleExceptionService,
        public dialogRef: MatDialogRef<EditDialogComponent>,
        private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

    ngOnInit(): void {
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
            
            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.codUtente =  this.user.codFisc; 
                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.idProgetto;
                });

                this.getStatoCredito();

            }

        });

        this.schedaCliente = this.data.schedaCliente;
        

        this.formStatocredito = this.fb.group({
            statoCredito: new FormControl('', [Validators.required]),
            dtModifica: new FormControl('', [Validators.required])
        });

        //this.setValueForm();

        this.resService.getListaStatiCredito().subscribe(data => {

            for(let z = 0; z < 5; z ++) {
                this.listaStatiCred.push(data[z].statoCredito);
            }
            console.log(this.listaStatiCred);
        })

    }
    getStatoCredito(){
        this.subscribers.getStatoCredito = this.responseService.getStatoCredito(this.idProgetto).subscribe(data =>{
            this.statoCreditoVO = data; 
            this.setValueForm();   

        });
    }

    closeDialog() {
        this.dialogRef.close();
    }

    setValueForm() {

        this.formStatocredito.setValue({
            statoCredito: this.statoCreditoVO.descStato,
           dtModifica: this.statoCreditoVO.dtModifica,
        })

    }

    onConfirmClick(): void {
        let newSchedaCliente: SaveStatocreditoGaranzia = new SaveStatocreditoGaranzia();

        if(this.formStatocredito.valid) {
            let formControls = this.formStatocredito.getRawValue();
            //newSchedaCliente.idCurrentRecord = this.schedaCliente.rating_currentId;
            newSchedaCliente.statoCredito = this.formStatocredito.get('statoCredito').value;
            newSchedaCliente.dtModifica = this.formStatocredito.get('dtModifica').value;
            newSchedaCliente.idProgetto = this.idProgetto; 
            newSchedaCliente.idSoggetto = JSON.stringify(this.userService.user.idSoggetto); 
            newSchedaCliente.idUtente = this.idUtente;

            this.dialogRef.close({
                save: "save",
                newSchedaCliente: newSchedaCliente
            });

        } else {
            this.showError = true;
            this.errorMsg = "Compila tutti i campi obbligatori!";
        }

        
    }

}