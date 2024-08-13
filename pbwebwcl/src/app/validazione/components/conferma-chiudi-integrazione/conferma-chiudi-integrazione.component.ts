/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { ValidazioneService } from '../../services/validazione.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { HandleExceptionService } from '@pbandi/common-lib';

@Component({
  selector: 'app-conferma-chiudi-integrazione',
  templateUrl: './conferma-chiudi-integrazione.component.html',
  styleUrls: ['./conferma-chiudi-integrazione.component.css']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ConfermaChiudiIntegrazioneComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    private validazioneService: ValidazioneService,
    public dialogRef: MatDialogRef<ConfermaChiudiIntegrazioneComponent>,
    private handleExceptionService: HandleExceptionService
  ) { }

  message: string = "";
  showError: boolean = false;
  showSucc: boolean = false;
  
  //SUBSCRIBERS
  subscribers: any = {};

  //LOADED
  isLoading = false;

  ngOnInit(): void {
  }

  chiudiRichiesta(){
    this.isLoading = true;
    this.subscribers.chiudiRichiesta = this.validazioneService.chiudiRichiestaIntegrazione(this.data.dataKey).subscribe(data => {
      if (data.esito == true) {
        this.showError = false;
        this.showSucc = true;
        this.dialogRef.close(data.messaggio)
      } else {
        this.showError = true;
        this.showSucc = false;
        this.message = data.messaggio;
      }
      this.isLoading = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showError = false;
      this.showSucc = true;
      this.message = "Errore in fase di chiusura della richiesta";
      this.isLoading = false;
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
