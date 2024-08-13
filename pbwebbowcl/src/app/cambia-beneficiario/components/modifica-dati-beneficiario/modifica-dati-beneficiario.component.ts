/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DatiBeneficiarioProgettoDTO } from '../../commons/dto/dati-beneficiario-progetto-dto';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { CambiaBeneficiarioService } from '../../services/cambia-beneficiario.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';

@Component({
  selector: 'app-modifica-dati-beneficiario',
  templateUrl: './modifica-dati-beneficiario.component.html',
  styleUrls: ['./modifica-dati-beneficiario.component.scss']
})

@AutoUnsubscribe({ objectName: 'subscribers' })

export class ModificaDatiBeneficiarioComponent {// implements OnInit {

  datiBeneficiarioProgettoDTO: DatiBeneficiarioProgettoDTO
  user: UserInfoSec;  //variabile utente

  regioniList: Array<Decodifica> = []
  provincieiList: Array<Decodifica> = []
  comuniiList: Array<Decodifica> = []

  idRegioneSelected: number
  idProvinciaSelected: number
  idComuneSelected: number

  regioneSelected: Decodifica
  provinciaSelected: Decodifica
  comuneSelected: Decodifica

  // Variabili di errore 
  messageError: string;
  isMessageErrorVisible: boolean;
  isResultVisible: boolean;

  //Loaded
  loadedOnInit: boolean = true;
  loadedLocazione: boolean = true;
  //loadedFindRegioni : boolean = true;
  //loadedFindProvince : boolean = true;
  //loadedFindComuni : boolean = true;

  //Object subscriber
  subscribers: any = {};



  constructor(
    public dialogRef: MatDialogRef<ModificaDatiBeneficiarioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    //@Inject(MAT_DIALOG_DATA) public dati: DatiBeneficiarioProgettoDTO,
    //@Inject(MAT_DIALOG_DATA) public user: UserInfoSec,
    private cambiaBeneficiarioService: CambiaBeneficiarioService,
    private handleExceptionService: HandleExceptionService,) {

    this.datiBeneficiarioProgettoDTO = this.copyDatiBeneficiarioProgettoDTO(data.dati)
    this.user = data.user

    console.log("Inizio del componente dialog ")
    console.log("Regione  ==> " + this.datiBeneficiarioProgettoDTO.idRegione)

  }

  public onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit(): void {

    this.idRegioneSelected = this.datiBeneficiarioProgettoDTO.idRegione
    this.idProvinciaSelected = this.datiBeneficiarioProgettoDTO.idProvincia
    this.idComuneSelected = this.datiBeneficiarioProgettoDTO.idComune

    this.findRegioniProvinciComuni()


  }

  findRegioniProvinciComuni() {

    this.loadedLocazione = false;
    //Funzione cerca i beneficiari disponibili per un certo utente
    this.subscribers.ricerca = this.cambiaBeneficiarioService.findRegioni(this.user.idUtente.toString()).subscribe(data => {
      if (data) {

        this.regioniList = data;

        //COMMENTI TEST
        console.log("[modifica dati beneficiario -- findRegioniProvinciComuni] \nRichiesta della funzione cerca avvenuta con successo. \n" +
          "regioni list -> " + this.regioniList)

        if (this.idRegioneSelected != null && this.idRegioneSelected != undefined) {
          var i = 0
          var find = false
          while (!find && i < this.regioniList.length) {
            if (this.regioniList[i].id == this.idRegioneSelected) {
              find = true
              this.regioneSelected = this.regioniList[i]
            }
            i++
          }

          this.findProvincieComuni()
        }


      }
      this.loadedLocazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca");
      this.isResultVisible = false;
      this.loadedLocazione = true;
    });

  }


  findProvincieComuni() {

    this.loadedLocazione = false;
    //Funzione cerca i beneficiari disponibili per un certo utente
    this.subscribers.ricerca = this.cambiaBeneficiarioService.findProvince(this.user.idUtente.toString(), this.idRegioneSelected.toString()).subscribe(data => {
      if (data) {

        this.provincieiList = data;

        //COMMENTI TEST
        console.log("[modifica dati beneficiario -- findProvincieComuni] \nRichiesta della funzione cerca avvenuta con successo. \n" +
          "provincie list -> " + this.provincieiList)


        if (this.idProvinciaSelected != null && this.idProvinciaSelected != undefined) {
          var i = 0
          var find = false
          while (!find && i < this.provincieiList.length) {
            if (this.provincieiList[i].id == this.idProvinciaSelected) {
              find = true
              this.provinciaSelected = this.provincieiList[i]
            }
            i++
          }
          this.findComuni()
        }
      }

      this.loadedLocazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca");
      this.isResultVisible = false;
      this.loadedLocazione = true;
    });

  }


  private copyDatiBeneficiarioProgettoDTO(data: DatiBeneficiarioProgettoDTO): DatiBeneficiarioProgettoDTO {
    var newObject = new DatiBeneficiarioProgettoDTO(
      data.serialVersionUID,
      data.cap,
      data.civico,
      data.codiceFiscale,
      data.codiceVisualizzato,
      data.denominazione,
      data.email,
      data.fax,
      data.indirizzo,
      data.idComune,
      data.idEnteGiuridico,
      data.idIndirizzo,
      data.idProgetto,
      data.idProvincia,
      data.idRecapiti,
      data.idRegione,
      data.idSede,
      data.idSoggetto,
      data.partitaIva,
      data.telefono,
      data.message
    )

    return newObject
  }

  // Restetta gli errori
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  // Mostra gli errori
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  // Funzione che fa apparire e scomparire la grafica del caricamento
  isLoading() {
    if (!this.loadedOnInit || !this.loadedLocazione) {
      return true;
    }
    return false;
  }

  // Funzione che cerca tutte le regioni
  findRegioni() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- findRegioni]")
    console.log("Ricerca delle regioni avviata")

    this.resetMessageError();
    this.loadedLocazione = false;

    //Funzione cerca le rgioni
    this.subscribers.ricerca = this.cambiaBeneficiarioService.findRegioni(this.user.idUtente.toString()).subscribe(data => {
      if (data) {

        this.regioniList = data;

        //COMMENTI TEST
        console.log("[cambia beneficiario -- findRegioni] \nRichiesta della funzione cerca avvenuta con successo. \n") +
          ("Regioni trovate")
        console.log(this.regioniList[0].descrizione)

        if (this.idRegioneSelected != null && this.idRegioneSelected != undefined) {
          var i = 0
          var find = false
          while (!find && i < this.regioniList.length) {
            if (this.regioniList[i].id == this.idRegioneSelected) {
              find = true
              this.regioneSelected = this.regioniList[i]
            }
            i++
          }

        }

      }
      this.loadedLocazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di cambiamento delle regioni");
      this.isResultVisible = false;
      this.loadedLocazione = true;
    });

  }

  // Funzione che cerca le province in base alla regione scelta
  findProvince() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- findProvince]")
    console.log("Ricerca delle province avviata")

    this.resetMessageError();
    this.loadedLocazione = false;

    //Funzione cerca le province
    this.subscribers.ricerca = this.cambiaBeneficiarioService.findProvince(this.user.idUtente.toString(), this.regioneSelected.id.toString()).subscribe(data => {
      if (data) {

        this.provincieiList = data;

        //COMMENTI TEST
        console.log("[cambia beneficiario -- findProvince] \nRichiesta della funzione cerca avvenuta con successo. \n") +
          ("Province trovate")

        if (this.idRegioneSelected != null && this.idRegioneSelected != undefined) {
          var i = 0
          var find = false
          while (!find && i < this.regioniList.length) {
            if (this.regioniList[i].id == this.idRegioneSelected) {
              find = true
              this.regioneSelected = this.regioniList[i]
            }
            i++
          }
        }

      }
      this.loadedLocazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di cambiamento delle province");
      this.isResultVisible = false;
      this.loadedLocazione = true;
    });

  }

  // Funzione che cerca i cumuni in base alla provincia scleta
  findComuni() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- findComune]")
    console.log("Ricerca dei comuni avviata")

    this.resetMessageError();
    this.loadedLocazione = false;

    //Funzione cerca le rgioni
    this.subscribers.ricerca = this.cambiaBeneficiarioService.findComune(this.user.idUtente.toString(), this.idProvinciaSelected.toString()).subscribe(data => {
      if (data) {

        this.comuniiList = data;

        //COMMENTI TEST
        console.log("[cambia beneficiario -- findComune] \nRichiesta della funzione cerca avvenuta con successo. \n" +
          "Comuni trovati")

        if (this.idComuneSelected != null && this.idComuneSelected != undefined) {
          var i = 0
          var find = false
          while (!find && i < this.comuniiList.length) {
            if (this.comuniiList[i].id == this.idComuneSelected) {
              find = true
              this.comuneSelected = this.comuniiList[i]
            }
            i++
          }
        }

      }
      this.loadedLocazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di cambiamento delle regioni");
      this.isResultVisible = false;
      this.loadedLocazione = true;
    });

  }

  //Quando viene selezionata una regione si azzera il campo provincia e
  //si ecrcano tutte le province in quella regione
  onSelectedRegione() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- onSelectedRegione] \nRegione selezionata desc. => " + this.regioneSelected.descrizione)
    this.provincieiList = undefined;
    this.comuniiList = undefined;
    this.provinciaSelected = undefined;
    this.comuneSelected = undefined;

    this.datiBeneficiarioProgettoDTO.idRegione = this.regioneSelected.id
    this.idRegioneSelected = this.regioneSelected.id

    this.findProvince();

  }

  onSelectedProvincia() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- onSelectedProvincia] \n " +
      "Provincia selezionata. desc => " + this.provinciaSelected.descrizione)
    this.comuniiList = undefined;
    this.comuneSelected = undefined;

    this.datiBeneficiarioProgettoDTO.idProvincia = this.provinciaSelected.id
    this.idProvinciaSelected = this.provinciaSelected.id

    this.findComuni();


  }

  onSelectedComune() {
    //COMMENTI TEST
    console.log("[cambia beneficiario -- onSelectedProvincia] \n " +
      "Provincia selezionata. desc => " + this.provinciaSelected.descrizione)

    this.datiBeneficiarioProgettoDTO.idComune = this.comuneSelected.id
    this.idComuneSelected = this.comuneSelected.id

  }





}
