/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { HandleExceptionService } from "@pbandi/common-lib";
import { ControdeduzioniService } from "src/app/gestione-controdeduzioni/services/controdeduzioni.service";
import { ControdeduzioneVO } from "src/app/gestione-crediti/commons/dto/controdeduzioneVO";

@Component({
    selector: 'app-accesso-agli-atti',
    templateUrl: './accesso-agli-atti.component.html',
    styleUrls: ['./accesso-agli-atti.component.scss']
})

export class AccessoAgliAttiComponent implements OnInit {
    showError: boolean = false;
    showSucc: boolean = false;
    isLoading = false;
    datiBackend = [];
    subscribers: any = {};
    errore;
    message;

    constructor(@Inject(MAT_DIALOG_DATA) public data: any,
        public controdeduzioniService: ControdeduzioniService,
        public dialogRef: MatDialogRef<AccessoAgliAttiComponent>,
        private handleExceptionService: HandleExceptionService
    ) { }

    ngOnInit(): void {
    }

    accessoAgliAtti(): void {
        this.controdeduzioniService.richiediAccessoAtti(this.data.dataKey.idControdeduzione).subscribe(data => {
            if (data == true) {
                this.message = "Atti inviati con successo";
                this.errore = false;
                setTimeout(() => {
                    this.errore = null;
                    this.closeDialog();
                }, 2000); 
            } else {
                this.errore = true;
              this.message = "Non è stato possibile inviare gli atti";
            }
    
        })
    }

    closeDialog() {
        this.dialogRef.close();
    }

}