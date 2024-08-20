/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OkDialogComponent } from 'src/app/iter-autorizzativi/components/iter-autorizzativi/ok-dialog/ok-dialog.component';
import { ProcedimentiRevocaResponseService } from 'src/app/revoche/services/procedimenti-revoca-response.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';

@Component({
  selector: 'app-richiesta-proroga-dialog',
  templateUrl: './richiesta-proroga-dialog.component.html',
  styleUrls: ['./richiesta-proroga-dialog.component.scss']
})
export class RichiestaProrogaDialogComponent implements OnInit {

  //PARAMETRI
  error: boolean = false;
  success: boolean = false;
  messageError: string;
  messageSuccess: string;
  myForm: FormGroup;
  numeroDefault: any;

  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog,
    private procedimentiRevocaResponseService: ProcedimentiRevocaResponseService,
    public dialogRef: MatDialogRef<RichiestaProrogaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    console.log(this.data.richiestaProroga.numGiorniRichiestiProroga);
    this.numeroDefault = this.data.richiestaProroga.numGiorniRichiestiProroga;
    
    //creo form
    this.myForm = this.fb.group({
      giorniApprovati: [this.numeroDefault ,[Validators.required],]
    });

    console.log("DATI RICEVUTI DAL COMPONENT PADRE - RICHIESTA PROROGA", this.data)
  }

  approva() {

    if (this.myForm.valid) {

      //apro dialog di conferma
      const dialogConferma = this.dialog.open(OkDialogComponent, {
        //width: '40vw',
        data: {
          "message": "Attenzione! L'operazione completerà l’approvazione della richiesta, continuare?"
        }
      });

      dialogConferma.afterClosed().subscribe(result => {

        console.log("RESULT", result);

        if (result == true) {
          let giorni=this.myForm.get("giorniApprovati")?.value;
          console.log(giorni);
          //aggiorna la richiesta di proroga
       this.procedimentiRevocaResponseService.updateRichiestaProroga(this.data.numeroProcedimentoRevoca, true, giorni).subscribe(data => {

            console.log("Risposta in arrivo dal BE dopo l'updateRichiestaProroga", data);

            if (data.code == 'OK') {
              this.showMessageSuccess(data.message);
              this.success = true;
              setTimeout(() => {
                
                this.closeDialog();
              }, 2000); 
            }
            else if (data.code === 'KO') {
              this.error = true;
              this.showMessageError(data.message);
            }

          }, err => {
            this.showMessageError(err);
          });  

        }
        if (result == "NO") {
          //Non succede nulla
        }

      });
    } else {
      //stampo messaggio errore
    }

  }

  respingi() {

    if (this.myForm.valid) {

      //apro dialog di conferma
      const dialogConferma = this.dialog.open(OkDialogComponent, {
        //width: '40vw',
        data: {
          "message": "Attenzione! Sei sicuro di voler respingere la richiesta?"
        }
      });

      dialogConferma.afterClosed().subscribe(result => {

        console.log("RESULT", result);

        if (result == true) {
          let giorni=this.myForm.get("giorniApprovati")?.value;
          console.log(giorni);
          
          this.procedimentiRevocaResponseService.updateRichiestaProroga(this.data.numeroProcedimentoRevoca, false, giorni).subscribe(data => {

            console.log("Risposta in arrivo dal BE dopo l'updateRichiestaProroga", data);

            if (data.code == 'OK') {
              this.showMessageSuccess(data.message);
              this.success = true;
              setTimeout(() => {
                
                this.closeDialog();
              }, 1000); 
            }
            else if (data.code === 'KO') {
              this.error = true;
              this.showMessageError(data.message);
            }

          }, err => {
            this.showMessageError(err);
          });  
        }
        if (result == "NO") {
          //Non succede nulla
        }

      });
    } else {
      //stampo messaggio errore
    }

  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.error = true;
  }
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.success = true;
  }

  closeDialog() {
    this.dialogRef.close();
}

}
