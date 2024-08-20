/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { DocumentoAllegatoDTO, HandleExceptionService } from "@pbandi/common-lib";
import { ControdeduzioniService } from "src/app/gestione-controdeduzioni/services/controdeduzioni.service";
import { ControdeduzioneVO } from "src/app/gestione-crediti/commons/dto/controdeduzioneVO";

@Component({
    selector: 'app-elimina-controdeduzione',
    templateUrl: './elimina-controdeduzione.component.html',
    styleUrls: ['./elimina-controdeduzione.component.scss']
})

export class EliminaControdeduzioneComponent implements OnInit {
  showError: boolean = false;
  showSucc: boolean = false;
  isLoading = false;
  datiBackend = [];
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  allegati;
  subscribers: any = {};
  errore;
  message;

    constructor(
        private controdeduzioniService: ControdeduzioniService,
        private handleExceptionService: HandleExceptionService,
        public dialogRef: MatDialogRef<EliminaControdeduzioneComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) { }

    ngOnInit(): void {
        this.getAllegati();
        
    }

    elimina(){
        this.controdeduzioniService.deleteControdeduz(this.data.dataKey.idControdeduzione).subscribe(data => {
            if (data == true) {
              this.message = "Controdeduzione eliminata con successo";
              this.errore = false;
              setTimeout(() => {
                this.errore = null;
                this.closeDialog();
              }, 2000); 
            } else {
              this.errore = true;
              this.message = "Non è stato possibile eliminare la Controdeduzione";
            }
    
        })
        
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
    
      closeDialog() {
        this.dialogRef.close();
    }

}