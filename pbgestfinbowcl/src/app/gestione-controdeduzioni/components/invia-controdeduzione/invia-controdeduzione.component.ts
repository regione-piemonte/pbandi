/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DocumentoAllegatoDTO, HandleExceptionService } from '@pbandi/common-lib';
import { ControdeduzioniService } from '../../services/controdeduzioni.service';

@Component({
    selector: 'app-invio-controdeduzione',
    templateUrl: './invia-controdeduzione.component.html',
    styleUrls: ['./invia-controdeduzione.component.scss']
})
export class InviaControdeduzione implements OnInit {
  isLoading = false;
  datiBackend = [];
  showError: boolean = false;
  showSucc: boolean = false;
  allegati;
  subscribers: any = {};
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  errore;
  message;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public controdeduzioniService : ControdeduzioniService,
              public dialogRef: MatDialogRef<InviaControdeduzione>,
              private handleExceptionService: HandleExceptionService,
    ) { }

  ngOnInit(): void {
    this.getAllegati()
  }

  getAllegati(){
   // this.loadedAllegati = false;
    this.subscribers.allegati = this.controdeduzioniService.getAllegati(this.data.dataKey.idControdeduzione).subscribe(data => {
      if (data) {
        
        for (let a of data) {
          if(a.flagEntita === 'I'){
              this.letteraAccompagnatoria.push(Object.assign({}, a))
          }else{
            this.allegatiGenerici.push(Object.assign({}, a))
          }
          
        }
      } else {
        this.allegati = new Array<DocumentoAllegatoDTO>();
      }
    //  this.loadedAllegati = true;
      },err => {
        this.handleExceptionService.handleBlockingError(err);
      });
  } 

  invia(){
    this.controdeduzioniService.inviaControdeduz(this.data.dataKey.idControdeduzione).subscribe(data => {
        if (data == true) {
            this.message = "Controdeduzione inviata con successo";
            this.errore = false;
            setTimeout(() => {
              this.errore = null;
              this.closeDialog();
            }, 2000); 
          } else {
            this.errore = true;
            this.message = "Non è stato possibile inviare la Controdeduzione";
          }

  })
    
  }

  closeDialog() {
    this.dialogRef.close();
}

}