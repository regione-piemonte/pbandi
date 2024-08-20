/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, Inject, OnInit, ViewChild} from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Constants, UserInfoSec } from "@pbandi/common-lib";
import { ActivatedRoute } from "@angular/router";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { UserService } from "src/app/core/services/user.service";
import { GestioneRiassicurazioniService } from "../../../services/gestione-riassicurazioni.service";
import { Riassicurazione_RiassicurazioniVO } from "../../../commons/dto/riassicurazione_RiassicurazioniVO";
import { SharedService } from "src/app/shared/services/shared.service";

@Component({
    selector: 'dialog-dettaglio-riassicurazione',
    templateUrl: 'dialog-dettaglio-riassicurazione.component.html',
    styleUrls: ['dialog-dettaglio-riassicurazione.component.scss'],
})


export class DialogDettaglioRiassicurazione implements OnInit {
    
    subscribers: any = {};

    header: string = "...";

    isSpinnerSpinning: boolean = false;
    isLoading: boolean = true;
    isData: boolean = false;
    
    dettRiass: Riassicurazione_RiassicurazioniVO = new Riassicurazione_RiassicurazioniVO();

    editForm: FormGroup;
    isInEditing: boolean = false;

    showError: boolean = false;
    showSucc: boolean = false;
    errorMsg: string;
    succMsg: string;
    
    constructor(
        private riassicurazioniService: GestioneRiassicurazioniService,
        private fb: FormBuilder,
        public sharedService: SharedService,
        private route: ActivatedRoute,
        public dialogRef: MatDialogRef<DialogDettaglioRiassicurazione>,
        private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: {idProgetto: number, header: string},
    ) {
        this.header = data.header;
    }

    ngOnInit(): void {

        this.editForm = this.fb.group({
            importoEscusso: new FormControl(''),
            dataScarico: new FormControl(''),
            dataEscussione: new FormControl('')
        })

        this.getData();

    }

    closeDialog() {
        this.dialogRef.close();
    }

    getData() {
        this.isLoading = true;

        this.subscribers.riass = this.riassicurazioniService.getDettaglioRiassicurazioni(this.data.idProgetto).subscribe(data => {

            if (data.length > 0) {

                this.isData = true;

                this.dettRiass = data[0];

                this.setFormValues(this.dettRiass);

            } else {
                this.isData = false;
            }

            this.isLoading = false;

        }, err => {
            //console.log("Error: ", err);
            this.isLoading = false;
            //this.showMessageError("Il progetto ricercato non ha agevolazioni di tipo garanzia.")
            //this.handleExceptionService.handleNotBlockingError(err);
        })
    }

    formattaImportoOnBlur() {

        //this.impRichiesto = this.sharedService.getNumberFromFormattedString(this.editForm.get('importoEscusso').value);

        try {
            if (this.editForm.get('importoEscusso').value) {
                this.editForm.get('importoEscusso').setValue(this.sharedService.formatValue(this.editForm.get('importoEscusso').value));

                //console.log(this.sharedService.formatValue(this.impRichiesto.toString()));
            }
        } catch (error) {
            console.log("errore nel formattare importo escusso", error);
            this.editForm.get('importoEscusso').setValue(null);
        }


    }

    numericOnly(event): boolean {
        let patt = /^\d+(?:\.\d{1,2})?$/;
        let result = patt.test(event.key);
        if (!result) {
            // Controlla se il tasto premuto è "backspace" o "delete"
            if (event.keyCode == 8 || event.keyCode == 46) {
                result = true;
            }
        }
        return result;
    }
    

    setFormValues(dati: Riassicurazione_RiassicurazioniVO) {
        this.editForm.patchValue({
            importoEscusso: dati.importoEscusso,
            dataScarico: (dati.dataScarico ? new Date(dati.dataScarico): null),
            dataEscussione: (dati.dataEscussione ? new Date(dati.dataEscussione) : null)
          });

        /*if(dati.importoEscusso) {
            this.editForm.get('importoEscusso').setValue(this.sharedService.formatValue(dati.importoEscusso.toString()))
        }*/
        
    }


    salva() {
        this.resetMessage();

        // Faccio un po' di check
        if(this.editForm.pristine) {
            this.showMessageError("Nessuna modifica effettuata.");
            return;
        }
        
        
        this.isSpinnerSpinning = true;

        let isImportoModified: boolean;
        let isDataEscussioneModified: boolean;
        let isDataScaricoModified: boolean;

        let newImporto: number;
        let newDataEscussione: Date;
        let newDataScarico: Date;

        try {

            if (this.dettRiass.importoEscusso == this.editForm.get('importoEscusso').value) {
                isImportoModified = false;
                newImporto = this.dettRiass.importoEscusso;
            } else {
                isImportoModified = true;
                newImporto = this.sharedService.getNumberFromFormattedString(this.editForm.get('importoEscusso').value);
            }

            if (this.dettRiass.dataEscussione == this.editForm.get('dataEscussione').value) {
                isDataEscussioneModified = false;
                newDataEscussione = this.dettRiass.dataEscussione;
            } else {
                isDataEscussioneModified = true;
                newDataEscussione = this.editForm.get('dataEscussione').value;
            }

            if (this.dettRiass.dataScarico == this.editForm.get('dataScarico').value) {
                isDataScaricoModified = false;
                newDataScarico = this.dettRiass.dataScarico;
            } else {
                isDataScaricoModified = true;
                newDataScarico = this.editForm.get('dataScarico').value;
            }

            this.subscribers.salva = this.riassicurazioniService.modificaRiassicurazione(this.dettRiass.idRiassicurazione, isImportoModified, newImporto, isDataEscussioneModified, newDataEscussione, isDataScaricoModified, newDataScarico).subscribe(data => {

                console.log("modificaRiassicurazione Res: ", data);

                if(data) {
                    this.isInEditing = false;
                    this.getData();
                    this.showMessageSuccess("Salvataggio effettuato.");
                } else {
                    this.showMessageError("Si è verificato un errore durante il salvataggio. Contatta l'assistenza.")
                }

                this.isSpinnerSpinning = false

                //this.dialogRef.close();

            }, err => {
                this.isSpinnerSpinning = false;
                console.log("modificaRiassicurazione Err: ", err);
                this.showMessageError("Si è verificato un errore durante il salvataggio. Contatta l'assistenza.")
            })


        } catch (error) {
            this.showMessageError("Importo o date non valide, controlla i valori inseriti.")
            this.isSpinnerSpinning = false;
            console.log("err: ", error);
        }

        

    }

    modifica() {
        this.isInEditing = true
    }

    reset() {
        this.isInEditing = false;

        this.resetMessage();

        this.setFormValues(this.dettRiass);
    }

    showMessageError(errorMsg: string) {
        this.resetMessage();
        this.errorMsg = errorMsg;
        this.showError = true;
    }
    
    showMessageSuccess(succMsg: string) {
        this.resetMessage();
        this.succMsg = succMsg;
        this.showSucc = true;
    }

    resetMessage() {
        this.errorMsg = "";
        this.succMsg = "";
        this.showError = false;
        this.showSucc = false;
    }

}