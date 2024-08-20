/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { ContestazioniService } from '../../services/contestazioni.service';
import { Location } from '@angular/common';
import { MatPaginator } from '@angular/material/paginator';
import { ControdeduzioneVO } from 'src/app/gestione-crediti/commons/dto/controdeduzioneVO';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { EditDialogComponent } from 'src/app/gestione-crediti/components/dialog-edit-modben/dialog-edit.component';

@Component({
  selector: 'app-inserisci-contestazione',
  templateUrl: './inserisci-contestazione.component.html',
  styleUrls: ['./inserisci-contestazione.component.scss']
})


export class InserisciContestazioneComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
    isLoading = false;
    public formControdeduzione: FormGroup;
    revoca: ControdeduzioneVO = undefined;
    info;
    errore;
    message;
  /* subscribers: any = {};
  idProgetto: any;
  idBandoLinea: any;
  codiceProgetto;
  user: UserInfoSec;
  idUtente:number;
  messageSuccess;
  protocolli;
  revoche;
  public formContestazioni: FormGroup;
  nomeFile: string;
  allegati: Array<File> = new Array<File>();
  file: File;
  loadedChiudiAttivita: boolean = true;
  isSubmitted = false;
  errore = null;
  message;
  isMessageErrorVisible: boolean;
  messageError: string; */
  
  constructor(/* private router: Router, private fb: FormBuilder,
              public contestazioniService : ContestazioniService,
              private route: ActivatedRoute,
              private userService: UserService,
              private location: Location,
              private handleExceptionService: HandleExceptionService, */
              private contestazioniService: ContestazioniService,
              private fb: FormBuilder,
              private dialog: MatDialog,
              public dialogRef: MatDialogRef<EditDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any
              ) 
    { 
      /* this.formContestazioni = this.fb.group({
        numProtocollo: new FormControl(''),
        numRevoca: new FormControl('')
    }) */
    this.formControdeduzione = this.fb.group({
      datiScelti: new FormControl('')
  })
  }

  ngOnInit(): void {/* 
    this.subscribers.router = this.route.params.subscribe(params => {
      this.idProgetto = this.route.snapshot.queryParamMap.get('idProgetto');
      this.idBandoLinea = this.route.snapshot.queryParamMap.get('idBandoLinea');
      this.codiceProgetto = this.route.snapshot.queryParamMap.get('codiceProgetto');
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.idUtente = data.idUtente;
          this.getRecupProvRevoca();
        }
      })
    }); */
 //   this.getGestioniRevoca();
  }


  /* getGestioniRevoca(){
    this.contestazioniService.getRecupProvRevoca(this.data.idProgetto).subscribe(data => {
    this.info =   data;
    },)
} */

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
    this.contestazioniService.inserisciContestazione(datiScelti)
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

  /* getRecupProvRevoca(){
    this.contestazioniService.getRecupProvRevoca(this.idProgetto).subscribe(data => {
      if (data) {
        this.protocolli = data;
      }
    }, err => {
      this.showMessageError("Errore in fase di ricerca.")
      this.handleExceptionService.handleNotBlockingError(err);
    })
}

  get myForms() {
  return this.formContestazioni.value;
} */

 /*  onSubmit() {
    this.isSubmitted = true;
    if (!this.formContestazioni.valid) {
      return false;
    } else {
      let datiBackEnd = {
        numeroProtocollo : this.formContestazioni.get('numProtocollo').value,
        idUtente : this.idUtente
      } 

      this.contestazioniService.inserisciContestazione(datiBackEnd)
        .subscribe((data) => {
          if (data == true) {
            this.messageSuccess = "Contestazione inserita con successo";
            this.errore = false;
            setTimeout(() => {
              this.goBack();
            }, 1500); 
          }
          else if (data == false) {
            this.errore = true;
            this.messageSuccess = "Non è stato possibile inserire la contestazione";
          }
        },
          (err) => {
              this.errore = true;
              this.showMessageError("Non è stato possibile inserire la contestazione.");
          }
        );
    }
}  */
/* 
  controllaForm() {
    this.isSubmitted = true;
}
  goBack() {
    this.location.back();
}

  isLoading() {
    if (!this.loadedChiudiAttivita) {
      return true;
    }
    return false;
}

  showMessageError(msg: string) {
    this.messageError = msg;
}

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
} */

}
