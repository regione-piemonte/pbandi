/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { HandleExceptionService } from "@pbandi/common-lib";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SchedaClienteMain } from "../../commons/dto/scheda-cliente-main";
import { ModGarResponseService } from "../../services/modgar-response-service.service";
import { EditDialogComponent } from "../dialog-edit-modben/dialog-edit.component";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { SaveSchedaCliente } from "../../commons/dto/save-scheda-cliente.all";
import { SaveEscussioneGaranzia } from "../../commons/dto/save-escussione-garanzia.all";
import { UserService } from "src/app/core/services/user.service";
import { UserInfoSec } from "src/app/core/commons/dto/user-info";
import { ActivatedRoute } from "@angular/router";
import { NumberPipeInput } from "../number-format-input/number.pipe.input";
import { SharedService } from "src/app/shared/services/shared.service";

@Component({
    selector: 'dialog-edit',
    templateUrl: './dialog-edit-modgar.component.html',
    styleUrls: ['./dialog-edit-modgar.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogEditModgarComponent implements OnInit {
    @Input() name: string;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    schedaCliente: SchedaClienteMain = new SchedaClienteMain;
    subscribers: any = {};
    showError: boolean = false;
    errorMsg: string = "Oh no!"
    isLoading = false;
    public formEscussione: FormGroup;
    idCurrentRecord: string;
    idComponentToShow: number;
    idSoggetto: string;
    idUtente: any;
    esc_optionsEscussione: Array<string> = [];
    esc_nProtocollo: string;
    esc_nProtocolloNotif: string;
    esc_dataRichEscussione: any;
    esc_tipoEscussione: string;
    esc_dataStato: any;
    esc_dataModifica: any;
    esc_importoRich: string;
    esc_importoApp: string;
    esc_causale: string;
    esc_iban: string;
    esc_denominazione: string;
    user: UserInfoSec;
    idProgetto: any;
    codUtente: string;
    price: string;
    value: any;
    impRichiesto: number;
    impApprovato: number;

    constructor(
        private responseService: ModGarResponseService,
        private fb: FormBuilder,
        private route: ActivatedRoute,
        //private snackBar: MatSnackBar,
        private handleExceptionService: HandleExceptionService,
        public dialogRef: MatDialogRef<EditDialogComponent>,
        private userService: UserService,
        public sharedService: SharedService,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) { }

    ngOnInit(): void {

        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

            if (data) {
                this.user = data;
                this.idUtente = this.user.idUtente;
                this.codUtente = this.user.codFisc;

                this.route.queryParams.subscribe(params => {
                    this.idProgetto = params.idProgetto;
                });

            }
            console.log(this.data.schedaCliente.descStatoEscussione);


        });

        this.schedaCliente = this.data.schedaCliente;

        this.formEscussione = this.fb.group({
            dtRichEscussione: new FormControl('', [Validators.required]),
            numProtocolloRich: new FormControl(''),
            numProtocolloNotif: new FormControl(''),
            descTipoEscussione: new FormControl('', [Validators.required]),
            descStatoEscussione: new FormControl(''),
            dtInizioValidita: new FormControl('', [Validators.required]),
            dtNotifica: new FormControl(''),
            impRichiesto: new FormControl('', [Validators.required]),
            impApprovato: new FormControl(''),
            causaleBonifico: new FormControl(''),
            ibanBonifico: new FormControl(''),
            descBanca: new FormControl(''),
            note: new FormControl(''),
        });

        this.setValueForm();
    }

    closeDialog() {
        this.dialogRef.close();
    }

    setImpRichiesto() {
        this.impRichiesto = this.sharedService.getNumberFromFormattedString(this.formEscussione.get('impRichiesto').value);

        if (this.impRichiesto !== null) {
            this.formEscussione.get('impRichiesto').setValue(this.sharedService.formatValue(this.impRichiesto.toString()));
            console.log(this.sharedService.formatValue(this.impRichiesto.toString()));

        }

    }

    setImpApprovato() {
        this.impApprovato = this.sharedService.getNumberFromFormattedString(this.formEscussione.get('impApprovato').value);

        if (this.impApprovato !== null) {
            this.formEscussione.get('impApprovato').setValue(this.sharedService.formatValue(this.impApprovato.toString()));
            console.log(this.sharedService.formatValue(this.impApprovato.toString()));

        }

    }

    setValueForm() {


        if (this.schedaCliente.ibanBonifico === null) {

            if (this.schedaCliente.impApprovato === null) {
                this.formEscussione.setValue({
                    dtRichEscussione: this.schedaCliente.dtRichEscussione,
                    numProtocolloRich: this.schedaCliente.numProtocolloRich,
                    numProtocolloNotif: this.schedaCliente.numProtocolloNotif,
                    descTipoEscussione: this.schedaCliente.descTipoEscussione,
                    descStatoEscussione: this.schedaCliente.descStatoEscussione,
                    dtInizioValidita: this.schedaCliente.dtInizioValidita,
                    dtNotifica: this.schedaCliente.dtNotifica,
                    impRichiesto: this.schedaCliente.impRichiesto.toString(),
                    impApprovato: this.schedaCliente.impApprovato,
                    causaleBonifico: this.schedaCliente.causaleBonifico,
                    ibanBonifico: this.schedaCliente.ibanBonifico,
                    descBanca: null,
                    note: this.schedaCliente.note,
                })
                // this.setImpRichiesto();
                // this.setImpApprovato();
            } else {
                this.formEscussione.setValue({
                    dtRichEscussione: this.schedaCliente.dtRichEscussione,
                    numProtocolloRich: this.schedaCliente.numProtocolloRich,
                    numProtocolloNotif: this.schedaCliente.numProtocolloNotif,
                    descTipoEscussione: this.schedaCliente.descTipoEscussione,
                    descStatoEscussione: this.schedaCliente.descStatoEscussione,
                    dtInizioValidita: this.schedaCliente.dtInizioValidita,
                    dtNotifica: this.schedaCliente.dtNotifica,
                    impRichiesto: this.schedaCliente.impRichiesto.toString(),
                    impApprovato: this.schedaCliente.impApprovato.toString(),
                    causaleBonifico: this.schedaCliente.causaleBonifico,
                    ibanBonifico: this.schedaCliente.ibanBonifico,
                    descBanca: null,
                    note: this.schedaCliente.note,
                })
                // this.setImpRichiesto();
                // this.setImpApprovato();
            }

        } else {

            if (this.schedaCliente.impApprovato === null) {
                this.formEscussione.setValue({
                    dtRichEscussione: this.schedaCliente.dtRichEscussione,
                    numProtocolloRich: this.schedaCliente.numProtocolloRich,
                    numProtocolloNotif: this.schedaCliente.numProtocolloNotif,
                    descTipoEscussione: this.schedaCliente.descTipoEscussione,
                    descStatoEscussione: this.schedaCliente.descStatoEscussione,
                    dtInizioValidita: this.schedaCliente.dtInizioValidita,
                    dtNotifica: this.schedaCliente.dtNotifica,
                    impRichiesto: this.schedaCliente.impRichiesto.toString(),
                    impApprovato: this.schedaCliente.impApprovato,
                    causaleBonifico: this.schedaCliente.causaleBonifico,
                    ibanBonifico: this.schedaCliente.ibanBonifico,
                    descBanca: this.schedaCliente.descBanca,
                    note: this.schedaCliente.note,
                })
                // this.setImpRichiesto();
                // this.setImpApprovato();
            } else {
                this.formEscussione.setValue({
                    dtRichEscussione: this.schedaCliente.dtRichEscussione,
                    numProtocolloRich: this.schedaCliente.numProtocolloRich,
                    numProtocolloNotif: this.schedaCliente.numProtocolloNotif,
                    descTipoEscussione: this.schedaCliente.descTipoEscussione,
                    descStatoEscussione: this.schedaCliente.descStatoEscussione,
                    dtInizioValidita: this.schedaCliente.dtInizioValidita,
                    dtNotifica: this.schedaCliente.dtNotifica,
                    impRichiesto: this.schedaCliente.impRichiesto.toString(),
                    impApprovato: this.schedaCliente.impApprovato.toString(),
                    causaleBonifico: this.schedaCliente.causaleBonifico,
                    ibanBonifico: this.schedaCliente.ibanBonifico,
                    descBanca: this.schedaCliente.descBanca,
                    note: this.schedaCliente.note,
                })
                // this.setImpRichiesto();
                // this.setImpApprovato();
            }

        }

    }

    onConfirmClick(): void {
        let newSchedaCliente: SaveEscussioneGaranzia = new SaveEscussioneGaranzia();

        if (this.formEscussione.valid && this.formEscussione.get('impRichiesto').value > 0) {
            let formControls = this.formEscussione.getRawValue();
            newSchedaCliente.idSoggetto = this.schedaCliente.idSoggetto;
            newSchedaCliente.idCurrentRecord = this.schedaCliente.rating_currentId;
            newSchedaCliente.dtRichEscussione = this.formEscussione.get('dtRichEscussione').value;
            newSchedaCliente.numProtocolloRich = this.formEscussione.get('numProtocolloRich').value;
            newSchedaCliente.numProtocolloNotif = this.formEscussione.get('numProtocolloNotif').value;
            newSchedaCliente.descTipoEscussione = this.formEscussione.get('descTipoEscussione').value;
            newSchedaCliente.dtInizioValidita = this.formEscussione.get('dtInizioValidita').value;
            newSchedaCliente.impRichiesto = this.formEscussione.get('impRichiesto').value;
            newSchedaCliente.note = this.formEscussione.get('note').value;
            newSchedaCliente.idProgetto = this.idProgetto;
            newSchedaCliente.idUtente = JSON.stringify(this.user.idUtente);

            this.dialogRef.close({
                save: "save",
                newSchedaCliente: newSchedaCliente
            });


        } else if (this.formEscussione.valid && this.formEscussione.get('impRichiesto').value < 0) {
            this.showError = true;
            this.errorMsg = "L'importo non può essere negativo!";
        } else {
            this.showError = true;
            this.errorMsg = "Compila tutti i campi obbligatori!";
        }

    }

}