/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { TipoAnagraficaDTO } from '../../commons/dto/tipo-anagrafica-tdo';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { EntiDTO } from '../../commons/dto/enti-dto';
import { EntiDTOSupport } from '../../commons/dto/enti-dto-support';
import { DescBreveDescEstesaDTO } from '../../commons/dto/descbreve-descestesa-dto';
import { UtenteRicercatoDTO } from '../../commons/dto/utente-ricercato-dto';
import { EsitoSalvaUtente } from '../../commons/dto/esito-salva-utente';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { Constants } from 'src/app/core/commons/util/constants';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-nuovo-utente',
  templateUrl: './nuovo-utente.component.html',
  styleUrls: ['./nuovo-utente.component.scss']
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoUtenteComponent implements OnInit {

  user: UserInfoSec;  //variabile utente

  nome: string
  cognome: string
  codiceFiscale: string
  tipoAnagrafica: TipoAnagraficaDTO;
  email: string;

  esitoSalvaUtente: EsitoSalvaUtente

  dataDettaglioUtente: Array<any>

  anagraficaList: Array<TipoAnagraficaDTO>
  anagraficaSelezionata: TipoAnagraficaDTO

  entiAssociabiliSupport: EntiDTOSupport
  entiAssociabili: EntiDTO
  entiAssociatiStr: Array<string> = []
  entiAssociatiDesc: Array<string> = []

  isSaveNuovoUtente: boolean = false
  isEditMode: boolean = false
  isVisibleEntiAssocite: boolean = false
  isAnagraficaSelected: boolean = false

  @ViewChild("inputForm", { static: true }) inputForm: NgForm;

  // Variabili di errore 
  messageError: string;
  isMessageErrorVisible: boolean;
  isResultVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  //LOADED
  loadedEnti: boolean = true;
  loadedNuovo: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<NuovoUtenteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gestioneUtentiService: GestioneUtentiService,
    private handleExceptionService: HandleExceptionService,
  ) {
    this.user = data.user
    this.anagraficaList = data.anagraficaList

    //LOG
    console.log("[gestione utenti component -- costruttore] Parametri passati:")
    console.log(this.user)
    console.log(this.anagraficaList)
  }

  ngOnInit(): void { }

  get descBreveRuoloIstruttoreOI() { return Constants.CODICE_RUOLO_OI_ISTRUTTORE; }
  get descBreveRuoloIstruttoreADG() { return Constants.CODICE_RUOLO_ADG_ISTRUTTORE; }


  ////////// Funzioni dei pulsanti

  OnSalvaPressed() {
    //LOG
    console.log("[gestione utenti component -- OnSalvaPressed] Click salva!!")
    this.resetMessage();
    if (this.nome != undefined && this.cognome != undefined && this.codiceFiscale != undefined) {

      if ((!(this.email?.length > 0) || this.inputForm.form.get("email")?.errors?.pattern) &&
        (this.anagraficaSelezionata?.descBreve === this.descBreveRuoloIstruttoreADG || this.anagraficaSelezionata?.descBreve === this.descBreveRuoloIstruttoreOI)) {
        this.showMessageError("Compilare l'indirizzo e-mail.");
        return;
      }

      this.extractEnti()
      console.log(this.entiAssociabili)
      this.save()
    }
    else {
      this.showMessageError("Completare tutti i campi.")
    }




    //this.saveDiTest() //da rimuovere e sostituire con save()
  }

  onSelectedTipoAnagrafica() {
    //LOG    
    console.log(this.anagraficaSelezionata.descEstesa)

    this.getEnti()
    this.email = null;
  }


  ////////// Funzioni dei pulsanti SPOSTATO IN DETTAGLIO UTENTE 

  /*
  OnModificaPressed(){
    //LOG
    console.log("[gestione utenti component -- OnModificaPressed] Click modifica!! ")
    this.isEditMode = !this.isEditMode
  }  

  OnAnnullaModifichePressed(){
    this.isEditMode = false
  }

  OnChiudiPressed(){
    //LOG
    console.log("[gestione utenti component -- OnChiudiPressed] Click chiudi!! No fa ancora nulla")
    
  }

  OnSalvaModifichePressed(){
    //LOG
    console.log("[gestione utenti component -- OnSalvaModifichePressed] Click slava modifiche!! No fa ancora nulla") 

    this.saveModifiche()
  }

  onCkeckboxSelected(){
    //LOG
    console.log("[gestione utenti component -- onCkeckboxSelected] index: " )
  }
  */

  ////////// Funzionio logiche di funzionamento

  save() {
    //LOG
    console.log("[gestione utenti component -- slava utente] \nInoltro della richiesta al service")
    this.loadedNuovo = false;
    this.subscribers.nuovo = this.gestioneUtentiService.salvaNuovoUtente(
      this.user.idUtente.toString(),
      this.nome, this.cognome,
      this.codiceFiscale,
      this.anagraficaSelezionata.id.toString(),
      this.user.codiceRuolo,
      this.entiAssociatiStr,
      this.email
    ).subscribe(data => {

      console.log("Result\n", data)

      if (data) {
        this.esitoSalvaUtente = data
        //LOG
        console.log("[gestione utenti component -- salva utente] \n Richiesta nuovo urente conclusa:")
        console.log(this.esitoSalvaUtente)


        if (this.esitoSalvaUtente.esito) {
          this.isSaveNuovoUtente = true;

          var utenteRicercatoFilter = UtenteRicercatoDTO.createEmptyUtenteRicercato()
          utenteRicercatoFilter.idPersonaFisica = this.esitoSalvaUtente.idPersonaFisica
          utenteRicercatoFilter.idSoggetto = this.esitoSalvaUtente.idSoggetto
          utenteRicercatoFilter.idTipoAnagrafica = this.esitoSalvaUtente.idTipoAnagrafica
          utenteRicercatoFilter.codiceFiscale = this.codiceFiscale

          //LOG
          console.log("[gestione utenti component -- OnNuovoUtentePressed]\n")
          //console.log("Creato filtro per ricerca: ")
          //console.log(utenteRicercatoFilter)

          this.dialogRef.close(true);
        }
        else {
          this.isSaveNuovoUtente = false;
          this.showMessageError(this.esitoSalvaUtente.message)

        }


      }
      this.loadedNuovo = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca");
      this.loadedNuovo = true;
    });
  }

  getEnti() {
    this.loadedEnti = false;
    //LOG
    console.log("[gestione utenti component -- getEnti] \nInoltro della richiesta al service")

    this.entiAssociatiStr = []

    this.subscribers.ricerca = this.gestioneUtentiService.getEnte(

      this.user.idUtente.toString(),
      this.user.idSoggetto.toString(),
      this.anagraficaSelezionata.id.toString(),
      this.user.ruolo

    ).subscribe(data => {

      if (data) {

        if (data.message == undefined && data.code == undefined) {
          this.entiAssociabiliSupport = data
          //LOG
          console.log("[gestione utenti component -- getEnti] \n Richiesta nuovo urente conclusa:")
          console.log(this.entiAssociabiliSupport)
          this.isVisibleEntiAssocite = true

        }
        else {
          this.isVisibleEntiAssocite = false
          //LOG
          console.log("[gestione utenti component -- getEnti] \n Richiesta nuovo urente conclusa:")
          console.log(data)
        }

        this.isAnagraficaSelected = true
      }
      this.loadedEnti = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca");
      this.loadedEnti = true;
    });

  }

  extractEnti() {

    if (this.entiAssociabiliSupport != undefined && this.entiAssociabiliSupport.entiAssociabiliFiltrati != undefined && this.entiAssociabiliSupport.entiAssociatiList != undefined) {

      var i
      this.entiAssociatiStr = []
      this.entiAssociatiDesc = []

      i = 0
      while (i < this.entiAssociabiliSupport.entiAssociabiliFiltrati.length) {
        var tempDec = this.entiAssociabiliSupport.entiAssociabiliFiltrati[i]
        if (tempDec.checkboxselected) {
          var decodifica = new Decodifica(tempDec.id, tempDec.descrizione, tempDec.descrizioneBreve, tempDec.dataInizioValidita, tempDec.dataFineValidita)
          this.entiAssociatiStr.push(decodifica.id.toString())
          this.entiAssociatiDesc.push(decodifica.descrizione)

        }
        i++
      }

      i = 0
      while (i < this.entiAssociabiliSupport.entiAssociatiList.length) {
        var tempDbde = this.entiAssociabiliSupport.entiAssociatiList[i]
        if (tempDbde.checkboxselected) {
          var descBreveDescEstesa = new DescBreveDescEstesaDTO(tempDbde.seralVersionUID, tempDbde.id, tempDbde.descBreve, tempDbde.descEstesa)
          this.entiAssociatiStr.push(descBreveDescEstesa.id.toString())
          this.entiAssociatiDesc.push(descBreveDescEstesa.descEstesa)

        }
        i++
      }

    }

  }


  //////// Funzioni errori


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

  saveDiTest() {
    //LOG
    console.log("[gestione utenti component -- saveDiTest] \nProva momentanea da sostituire con save")

    this.isSaveNuovoUtente = true;
  }

  close() {
    this.dialogRef.close();
  }

  isLoading() {
    if (!this.loadedEnti || !this.loadedNuovo) {
      return true;
    }
    return false;
  }
}


