/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DocumentoAllegatoDTO, HandleExceptionService } from '@pbandi/common-lib';
import { ContestazioniService } from '../../services/contestazioni.service';

@Component({
  selector: 'app-elimina-contestazione',
  templateUrl: './elimina-contestazione.component.html',
  styleUrls: ['./elimina-contestazione.component.scss']
})
export class EliminaContestazioneComponent implements OnInit {
  showError: boolean = false;
  showSucc: boolean = false;
  isLoading = false;
  datiBackend = [];
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  allegati;
  subscribers: any = {};
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public contestazioniService : ContestazioniService,
              public dialogRef: MatDialogRef<EliminaContestazioneComponent>,
              private handleExceptionService: HandleExceptionService,
    ) { }

  ngOnInit(): void {
    this.getAllegati();
}

  elimina(){
    this.contestazioniService.eliminaContestazione(this.data.dataKey).subscribe(data => {
        if (data) {
            this.showError = false;
            this.showSucc = true;
            /* setTimeout(() => {
                this.router.navigate(["/drawer/" + this.idOp + "/contestazioni"], { queryParams: {} });
            }, 4000); */
            this.dialogRef.close()
        } else {
            this.showError = true;
            this.showSucc = false;
        }

    })
    
}

getAllegati(){
  // this.loadedAllegati = false;
   this.subscribers.allegati = this.contestazioniService.getAllegati(this.data.dataKey.idContestazione).subscribe(data => {
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

  closeDialog() {
    this.dialogRef.close();
}

}
