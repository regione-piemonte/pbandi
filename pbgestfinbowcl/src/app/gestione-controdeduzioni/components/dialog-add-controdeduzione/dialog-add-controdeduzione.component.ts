/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { ControdeduzioneVO } from "src/app/gestione-crediti/commons/dto/controdeduzioneVO";
import { EditDialogComponent } from "src/app/gestione-crediti/components/dialog-edit-modben/dialog-edit.component";
import { AutoUnsubscribe } from "src/app/shared/decorator/auto-unsubscribe";
import { ControdeduzioniService } from "../../services/controdeduzioni.service";

@Component({
    selector: 'app-add-controdeduzione',
    templateUrl: 'dialog-add-controdeduzione.component.html',
    styleUrls: ['dialog-add-controdeduzione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })

export class DialogAddControdeduzione implements OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;
    isLoading = false;
    public formControdeduzione: FormGroup;
    revoca: ControdeduzioneVO = undefined;
    info;
    errore;
    message;

    constructor(
        private controdeduzioniService: ControdeduzioniService,
        private fb: FormBuilder,
        private dialog: MatDialog,
        public dialogRef: MatDialogRef<EditDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) { 

        this.formControdeduzione = this.fb.group({
            datiScelti: new FormControl('')
        })
    }

    ngOnInit(): void {
     this.getGestioniRevoca();

    }

    getGestioniRevoca(){
        this.controdeduzioniService.getGestioniRevoca(this.data.idProgetto).subscribe(data => {
        this.info =   data;
        },)
    }

    valorizzaRevoca(element: any) {
        const xxx: ControdeduzioneVO = element.value.numeroRevoca;
        this.revoca = xxx;
    }

    onConfirmClick() {
       // this.isSubmitted = true;
        if (!this.formControdeduzione.valid) {
          return false;
        } else {
        let datiScelti = this.formControdeduzione.get('datiScelti').value;
        this.controdeduzioniService.insertControdeduzione(datiScelti)
            .subscribe((data) => {
                if (data  == true) {
                    this.message = "Controdeduzione inserita con successo";
                    this.errore = false;
                    setTimeout(() => {
                      this.errore = null;
                      this.closeDialog();
                    }, 2000); 
                  }
                  else if (data == false) {
                    this.errore = true;
                    this.message = "Non è stato possibile inserire la Controdeduzione";
                  }
                    },
                    (err) => {
                      if(err.ok == false){
                        this.errore = true;
                        this.message = err.statusText;
                      }
                      }
                    
            ); 
        }
    } 

    closeDialog() {
        this.dialogRef.close();
    }

}