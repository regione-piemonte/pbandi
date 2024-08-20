/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DocumentoAllegatoDTO, HandleExceptionService } from '@pbandi/common-lib';
import { ContestazioniService } from '../../services/contestazioni.service';

@Component({
  selector: 'app-conferma-invio-contestazione',
  templateUrl: './conferma-invio-contestazione.component.html',
  styleUrls: ['./conferma-invio-contestazione.component.scss']
})
export class ConfermaInvioContestazioneComponent implements OnInit {
  isLoading = false;
  datiBackend = [];
  showError: boolean = false;
  showSucc: boolean = false;
  allegati;
  subscribers: any = {};
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public contestazioniService : ContestazioniService,
              public dialogRef: MatDialogRef<ConfermaInvioContestazioneComponent>,
              private handleExceptionService: HandleExceptionService,
    ) { }

  ngOnInit(): void {
    console.log(this.data);
    this.getAllegati();
    
    
  }

  getAllegati(){
   // this.loadedAllegati = false;
    this.subscribers.allegati = this.contestazioniService.getAllegati(this.data.idContestazione).subscribe(data => {
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
    this.contestazioniService.inviaContestazione(this.data.idContestazione).subscribe(data => {
      if (data) {
          this.showError = false;
          this.showSucc = true;
          this.dialogRef.close();
      } else {
          this.showError = true;
          this.showSucc = false;
      }

  })
    
  }

  closeDialog() {
    this.dialogRef.close();
}

}
