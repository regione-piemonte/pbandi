/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ResponseCodeMessage } from './../../../shared/commons/dto/response-code-message-dto';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { EntiDTOSupport } from '../../commons/dto/enti-dto-support';
import { EntiDTO } from '../../commons/dto/enti-dto';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NuovoUtenteComponent } from '../nuovo-utente-dialog/nuovo-utente.component';
import { DescBreveDescEstesaDTO } from '../../commons/dto/descbreve-descestesa-dto';
import { UtenteRicercatoDTO } from '../../commons/dto/utente-ricercato-dto';
import { DecodificaSupport } from '../../commons/dto/decodifica-support';
import { DescBreveDescEstesaDTOSupport } from '../../commons/dto/descbreve-descestesa-dto-support';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Constants } from 'src/app/core/commons/util/constants';
import { GestioneBenefProgComponent } from '../gestione-benef-prog/gestione-benef-prog.component';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { NgForm } from '@angular/forms';



@Component({
  selector: 'app-dettaglio-utente',
  templateUrl: './dettaglio-utente.component.html',
  styleUrls: ['./dettaglio-utente.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DettaglioUtenteComponent implements OnInit {

  user: UserInfoSec;  //variabile utente

  utenteRicercatoOld: UtenteRicercatoDTO;
  utenteRicercatoSelected: UtenteRicercatoDTO;
  isIstruttoreOrganismoIntermedio: boolean = false;
  isGestioneBenProgVisible: boolean;
  isGestioneBenProgButtonVisible: boolean;

  dataDettaglioUtente: Array<any>


  entiAssociabiliSupport: EntiDTOSupport
  entiAssociabili: EntiDTO
  entiAssociatiStr: Array<string> = []
  entiAssociatiDesc: Array<string> = []

  isEditMode: boolean = false
  isVisibleEntiAssocite: boolean = false
  isAnagraficaSelected: boolean = false
  isSaveModifiche: boolean = false

  entiModificati: boolean;

  @ViewChild(GestioneBenefProgComponent) gestioneBenefProgComponent: GestioneBenefProgComponent;
  @ViewChild("inputForm", { static: true }) inputForm: NgForm;

  //Message error e succes 
  isMessageSuccessVisible: boolean = false
  isMessageErrorVisible: boolean = false
  messageSuccess: string
  messageError: string

  //Loaded
  loadedEntiAssociati: boolean = true;
  loadedEnti: boolean = true;
  loadedSalva: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    public dialogRef: MatDialogRef<NuovoUtenteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gestioneUtentiService: GestioneUtentiService,
    private handleExceptionService: HandleExceptionService,
  ) {
    this.user = data.user;
    this.utenteRicercatoSelected = data.utenteRicercatoSelected;
    this.utenteRicercatoOld = Object.assign({}, this.utenteRicercatoSelected);
    this.isGestioneBenProgButtonVisible = this.utenteRicercatoSelected.descBreveTipoAnagrafica === Constants.CODICE_RUOLO_PERSONA_FISICA ? true : false;

  }

  ngOnInit(): void {
    //LOG
    console.log("[gestione utenti component -- costruttore] Parametri passati:")
    console.log(this.user)
    console.log(this.utenteRicercatoSelected)

    this.getEnti()
  }

  get descBreveRuoloIstruttoreOI() { return Constants.CODICE_RUOLO_OI_ISTRUTTORE; }
  get descBreveRuoloIstruttoreADG() { return Constants.CODICE_RUOLO_ADG_ISTRUTTORE; }

  ////////// Funzioni dei pulsanti


  close() {
    this.dialogRef.close();
  }

  OnModificaPressed() {
    //LOG
    console.log("[gestione utenti component -- OnModificaPressed] Click modifica!! ")
    this.getEntiAssociateChecked()
    this.isEditMode = !this.isEditMode
  }

  OnAnnullaModifichePressed() {
    this.isEditMode = false
  }

  OnChiudiPressed() {
    //LOG
    console.log("[gestione utenti component -- OnChiudiPressed] Click chiudi!! No fa ancora nulla")

  }

  OnSalvaModifichePressed() {
    //LOG
    console.log("[gestione utenti component -- OnSalvaModifichePressed] Click slava modifiche!! No fa ancora nulla")

    this.saveModifiche()
  }

  onCheckboxChange(enti: DecodificaSupport) {
    //LOG
    console.log("[gestione utenti component -- entiAssociabiliFiltrati]")

    if (this.utenteRicercatoSelected.idTipoAnagrafica == 5) {
      for (let i = 0; i < this.entiAssociabiliSupport.entiAssociabiliFiltrati.length; i++) {
        this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselected = false
        this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselectedChanged = false
        console.log(this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselected)
      }

      for (let i = 0; i < this.entiAssociabiliSupport.entiAssociatiList.length; i++) {

        this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselected = false
        this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselectedChanged = false
        console.log(this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselected)
      }
    }

    enti.checkboxselectedChanged = !enti.checkboxselectedChanged
    this.entiModificati = true;
  }

  onCheckboxChange2(enti: DescBreveDescEstesaDTOSupport) {
    //LOG
    console.log("[gestione utenti component -- onCheckboxChangeentiAssociatiList]")

    if (this.utenteRicercatoSelected.idTipoAnagrafica == 5) {
      for (let i = 0; i < this.entiAssociabiliSupport.entiAssociabiliFiltrati.length; i++) {
        this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselected = false
        this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselectedChanged = false
        console.log(this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselected)
      }

      for (let i = 0; i < this.entiAssociabiliSupport.entiAssociatiList.length; i++) {

        this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselected = false
        this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselectedChanged = false
        console.log(this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselected)
      }
    }

    enti.checkboxselectedChanged = !enti.checkboxselectedChanged
    this.entiModificati = true;
  }

  OnOkPressed() {
    this.dialogRef.close()
  }


  ////////// Funzionio logiche di funzionamento

  //Chiamo il servizio che mi ritona gli enti disponibili per un certo utente
  //questi vengono messi all' intermo di un oggetto di supporto  entiAssociabiliSupport
  //che possiede campi aggiuntivi
  getEnti() {
    this.loadedEnti = false;
    //LOG
    console.log("[gestione utenti component -- getEnti] \nInoltro della richiesta al service")

    this.entiAssociatiStr = []

    this.subscribers.ente = this.gestioneUtentiService.getEnte(

      this.user.idUtente.toString(),
      this.user.idSoggetto.toString(),
      this.utenteRicercatoSelected.idTipoAnagrafica.toString(),
      this.user.ruolo

    ).subscribe(data => {
      //LOG
      console.log("print data")
      console.log(data)

      if (data) {

        if (data.message == undefined && data.code == undefined) {
          this.entiAssociabiliSupport = data
          //LOG
          console.log("[gestione utenti component -- getEnti] \n Richiesta nuovo urente conclusa:")
          console.log(this.entiAssociabiliSupport)
          this.isVisibleEntiAssocite = true
          this.getEntiAssociati()
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
      this.showMessageError("Errore in fase di recupero degli enti");
      this.loadedEnti = true;
    });

  }

  //Questa funzione estrae tutti gli enti associati con le modifiche apportate 
  extractEnti() {
    this.entiAssociatiStr = [];
    this.entiAssociatiDesc = [];

    if (this.entiAssociabiliSupport != undefined && this.entiAssociabiliSupport.entiAssociabiliFiltrati != undefined && this.entiAssociabiliSupport.entiAssociatiList != undefined) {

      var i

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

  //Questa funzione chiama il servizio che salva i dati che la funzione extractEnti ha trovato
  saveModifiche() {
    this.resetMessage();
    if (this.utenteRicercatoSelected.nome === this.utenteRicercatoOld.nome
      && this.utenteRicercatoSelected.cognome === this.utenteRicercatoOld.cognome
      && this.utenteRicercatoSelected.codiceFiscale === this.utenteRicercatoOld.codiceFiscale
      && this.utenteRicercatoSelected.email === this.utenteRicercatoOld.email
      && !this.entiModificati) {
      this.showMessageError("Non sono state effettuate modifiche.");
      //controllare enti
      return;
    }

    if ((!(this.utenteRicercatoSelected?.email?.length > 0) || this.inputForm.form.get("email")?.errors?.pattern) &&
      (this.utenteRicercatoSelected?.descBreveTipoAnagrafica === this.descBreveRuoloIstruttoreADG
        || this.utenteRicercatoSelected?.descBreveTipoAnagrafica === this.descBreveRuoloIstruttoreOI)) {
      this.showMessageError("Compilare l'indirizzo e-mail.");
      return;
    }

    this.extractEnti()

    //LOG
    console.log("[gestione utenti component -- saveModifiche] \nInoltro della richiesta al service")
    this.loadedSalva = false;
    this.subscribers.salva = this.gestioneUtentiService.salvaModificaUtente(
      this.user.idUtente.toString(),
      this.utenteRicercatoSelected.nome,
      this.utenteRicercatoSelected.cognome,
      this.utenteRicercatoSelected.codiceFiscale,
      this.utenteRicercatoSelected.idTipoAnagrafica.toString(),
      this.entiAssociatiStr,
      this.utenteRicercatoSelected.email
    ).subscribe(data => {
      if (data) {
        var ris: ResponseCodeMessage = data
        //LOG
        console.log("[gestione utenti component -- saveModifiche] \n Richiesta nuovo urente conclusa:")
        console.log(ris)

        if (ris.code == "OK") {
          this.isSaveModifiche = true;
          this.isEditMode = false;
          this.showMessageSucces(ris.message)
        }
        else {
          this.isSaveModifiche = false;
          this.showMessageError(ris.message)
        }

      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    });
  }

  //chiama il servizio che ritorna gli enti associati degli utenti se disponibili 
  // mette i dati nella variabile dataDettaglioUtente
  getEntiAssociati() {
    this.loadedEntiAssociati = false;
    //LOG
    console.log("[DettaglioUtenteComponent -- getEntiAssociati] \nInoltro della richiesta al service")

    this.subscribers.dettaglio = this.gestioneUtentiService.dettaglioUtente(
      this.user.idUtente.toString(),
      this.utenteRicercatoSelected.idPersonaFisica.toString(),
      this.utenteRicercatoSelected.idTipoAnagrafica.toString(),
      this.utenteRicercatoSelected.descBreveTipoAnagrafica,
      this.utenteRicercatoSelected.idSoggetto.toString()
    ).subscribe(data => {
      if (data) {

        //LOG
        console.log("DettaglioUtenteComponent -- getEntiAssociati] \nRichiesta dettaglio utente conclusa con successo")
        console.log(data)

        if (data.message != undefined && data.code != undefined) {
          if (data.code != 'KO') {
            this.showMessageError(data.code)
          }
          this.dataDettaglioUtente = undefined
        }
        else {
          this.dataDettaglioUtente = data;
        }

      }
      this.loadedEntiAssociati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero degli enti associabili");
      this.loadedEntiAssociati = true;
    });

  }

  //Questa funzione assoccia (con un boolean) l'oggetto di supporto con tutti gli enti 
  //alla specifica persona 
  getEntiAssociateChecked() {
    //LOG
    console.log("[DettaglioUtenteComponent -- getEntiNonAssociate] \nModifico array di supporto per enti associati")


    if (this.dataDettaglioUtente != undefined && this.entiAssociabiliSupport != undefined && this.entiAssociabiliSupport.entiAssociabiliFiltrati != undefined) {
      var i = 0
      while (i < this.entiAssociabiliSupport.entiAssociabiliFiltrati.length) {
        var find = false
        var j = 0
        while (!find && j < this.dataDettaglioUtente.length) {
          if (this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].descrizione == this.dataDettaglioUtente[j].descEstesa) {
            find = true
          }

          j++
        }
        this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselected = find
        this.entiAssociabiliSupport.entiAssociabiliFiltrati[i].checkboxselectedChanged = false
        i++
      }
    }


    if (this.dataDettaglioUtente != undefined && this.entiAssociabiliSupport != undefined && this.entiAssociabiliSupport.entiAssociatiList != undefined) {
      var i = 0
      while (i < this.entiAssociabiliSupport.entiAssociatiList.length) {
        var find = false
        var j = 0
        while (!find && j < this.dataDettaglioUtente.length) {
          if (this.entiAssociabiliSupport.entiAssociatiList[i].descEstesa == this.dataDettaglioUtente[j].descEstesa) {
            find = true
          }

          j++
        }
        this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselected = find
        this.entiAssociabiliSupport.entiAssociatiList[i].checkboxselectedChanged = false
        i++
      }
    }


    if (this.utenteRicercatoSelected.idTipoAnagrafica == 5) {
      this.isIstruttoreOrganismoIntermedio = true
    }
    else {
      this.isIstruttoreOrganismoIntermedio = false
    }

    console.log(this.entiAssociabiliSupport)


  }

  gestioneBeneficiarioProgetto() {
    this.isGestioneBenProgVisible = true;
    this.isGestioneBenProgButtonVisible = false;
  }

  goToDettaglio() {
    this.isGestioneBenProgVisible = false;
    this.isGestioneBenProgButtonVisible = true;
  }

  indietro() {
    this.isEditMode = !this.isEditMode
  }

  ////////// Funzioni mesaggi errore

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

  // resetta gli errori
  resetMessage() {
    this.messageError = null;
    this.messageSuccess = null;
    this.isMessageErrorVisible = false;
    this.isMessageSuccessVisible = false;
  }

  isLoading() {
    if (!this.loadedEnti || !this.loadedEntiAssociati || !this.loadedSalva) {
      return true;
    }
    return false;
  }


}





