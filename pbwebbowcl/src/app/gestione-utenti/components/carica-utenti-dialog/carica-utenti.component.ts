/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ResponseCodeMessage } from './../../../shared/commons/dto/response-code-message-dto';
import { GestioneUtentiService } from './../../services/gestione-utenti.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';

@Component({
  selector: 'app-carica-utenti',
  templateUrl: './carica-utenti.component.html',
  styleUrls: ['./carica-utenti.component.scss']
})
export class CaricaUtentiComponent implements OnInit {

  user: UserInfoSec;  //variabile utente

  nameFileSelected: string
  isLoadFile: boolean = false //se il file e' stato scelto da file system
  isSelectedFile: boolean = false
  file: File
  responseCodeMessage: ResponseCodeMessage //risposta del servizio


  //Object subscriber
  subscribers: any = {};

  // Variabili di errore 
  messageError: string;
  isMessageErrorVisible: boolean;
  isResultVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;


  constructor(
    public dialogRef: MatDialogRef<CaricaUtentiComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gestioneUtentiService: GestioneUtentiService,
  ) {
    this.user = data.user
  }

  ngOnInit(): void { }

  /*
  OnSfogliaPressed(){
     //LOG
     console.log("[gestione utenti component -- OnSfogliaPressed] Click sfoglia!! No fa ancora nulla")
  }*/

  OnCaricaPressed(file: File) {
    this.file = file
    //LOG
    console.log("[CaricaUtentiComponent -- OnCaricaPressed] Click carica!! ")
    console.log(this.file)

    this.subscribers.caricaFile = this.gestioneUtentiService.caricaFile(
      this.file
    ).subscribe(data => {
      if (data) {
        //LOG
        console.log("[gestione utenti component -- OnCaricaPressed] \nRichiesta carica file conclusa con successo")
        console.log(data)

        this.responseCodeMessage = data

        if (this.responseCodeMessage != undefined && this.responseCodeMessage.code == 'OK') {
          this.showMessageSucces(this.responseCodeMessage.message)
        }
        else {
          this.showMessageError(this.responseCodeMessage.message)
        }

      }

    }, err => {
      this.showMessageError(this.responseCodeMessage.message)
    });

    //this.dialogRef.close();
  }

  OnAnnullaPressed() {
    //LOG
    console.log("[gestione utenti component -- OnAnnullaPressed] Click annulla!!")
    this.dialogRef.close();
  }

  onFileSelected(event: Event) {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if (fileList && fileList.length > 0) {

      if (this.checkFileExtension(fileList[fileList.length - 1])) {
        this.resetMessage()
        this.file = fileList[fileList.length - 1]
        console.log("FileUpload -> files", this.file);
        this.isLoadFile = true
        this.isSelectedFile = true

        //this.showMessageSucces("File csv caricato con successo")   
      }
      else {
        this.showMessageError("Il file selezionato non è dell' estensione corretta")

      }



    }



  }

  checkFileExtension(file: File): boolean {
    var name = file.name
    if (name.length > 4) {
      if (name.split(".")[1] == "csv") {
        return true
      }

    }
    return false
  }

  OnsSuccesPressed() {
    this.dialogRef.close()
  }


  //////////////Errori

  // resetta gli errori
  resetMessage() {
    this.messageError = null;
    this.messageSuccess = null;
    this.isMessageErrorVisible = false;
    this.isMessageSuccessVisible = false;
  }

  // resetta gli errori e motra il messaggio di errore 
  showMessageError(msg: string) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageError = msg;
    }
    else {
      this.messageError = "Success";
    }
    this.isMessageErrorVisible = true;

    //this.vps.scrollToAnchor("sclrollId"); // non funziona

    //const element = document.querySelector('#scrollId'); // non funziona
    //element.scrollIntoView();
  }

  // resetta gli errori e motra il messaggio di successo
  showMessageSucces(msg: string) {
    this.resetMessage()
    if (msg != undefined && msg != null) {
      this.messageSuccess = msg;
    }
    else {
      this.messageSuccess = "Error";
    }
    this.isMessageSuccessVisible = true;

    //this.vps.scrollToAnchor("sclrollId"); // non funziona

    //const element = document.querySelector('#scrollId'); // non funziona
    //element.scrollIntoView();

  }


}
