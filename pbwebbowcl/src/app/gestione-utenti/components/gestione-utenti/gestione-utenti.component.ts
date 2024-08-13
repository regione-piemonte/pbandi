/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { GestioneBackOfficeEsitoGenerico } from './../../commons/dto/gestione-back-office-esito-generico';
import { ResponseCodeMessage } from '../../../shared/commons/dto/response-code-message-dto';
import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { TipoAnagraficaDTO } from '../../commons/dto/tipo-anagrafica-tdo';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { UtenteRicercatoDTO } from '../../commons/dto/utente-ricercato-dto';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatDialog } from '@angular/material/dialog';
import { CaricaUtentiComponent } from '../carica-utenti-dialog/carica-utenti.component';
import { NuovoUtenteComponent } from '../nuovo-utente-dialog/nuovo-utente.component';
import { Router } from '@angular/router';
import { DettaglioUtenteComponent } from '../dettaglio-utente/dettaglio-utente.component';
import { EsitoSalvaUtente } from '../../commons/dto/esito-salva-utente';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { Constants } from 'src/app/core/commons/util/constants';


@Component({
  selector: 'app-gestione-utenti',
  templateUrl: './gestione-utenti.component.html',
  styleUrls: ['./gestione-utenti.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneUtentiComponent implements OnInit {//}, AfterViewInit{

  //##### VARIABILI ############################################

  //### VARIABILI  DI CLASSE  

  // Variabili di errore 
  messageError: string;
  isMessageErrorVisible: boolean;
  isResultVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  //Loaded
  loadedOnInit: boolean = true;
  loadedTipoAnag: boolean;
  loadedCerca: boolean = true;
  loadedNuovoUtente: boolean = true;
  loadedEliminaUtente: boolean = true;
  loadedFindUtente: boolean = true;


  //Object subscriber
  subscribers: any = {};

  //### VARIABILI LOGICHE 

  user: UserInfoSec;  //Variabile utente

  anagraficaList: Array<TipoAnagraficaDTO> //lista di anagrafiche per ricerca
  anagraficaSelezionata: TipoAnagraficaDTO //anagrafica selezionata

  utenteRicercatoFilter: UtenteRicercatoDTO //filtro usato per la riceraca utente
  utenteRicercatoList: Array<UtenteRicercatoDTO> //lista di utenti trovati con la ricerca usando il filtro
  utenteRicercatoSelected: UtenteRicercatoDTO //utente selezionato nella tabella

  esitoSalvaUtente: EsitoSalvaUtente //esito salva utente
  utenteRicercatoNew: UtenteRicercatoDTO //variabile di supporto usata per passare dal nuovo utente al dettaglio utente

  responseCancellaUtente: GestioneBackOfficeEsitoGenerico

  responseCodeMessage: ResponseCodeMessage // messaggio di risposta
  isTabellaVisible: boolean = false



  //## VARIABILI TABELLA
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild("sort", { static: true }) sort: MatSort;
  dataSource: UtenteRiceracaDTODatasource // = new UtenteRiceracaDTODatasource([])
  displayedColumns: string[] = ['cognome', 'nome', 'cf', 'anagrafica', 'dataDal', 'azioni'];


  constructor(
    private userService: UserService,
    private gestioneUtentiService: GestioneUtentiService,
    private _liveAnnouncer: LiveAnnouncer,
    private caricaUtentiDialog: MatDialog,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
  ) { }

  ngOnInit(): void {

    this.loadedOnInit = false;
    //Pre caricamento inizile dei dati
    this.utenteRicercatoFilter = UtenteRicercatoDTO.createEmptyUtenteRicercato();

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      //LOG
      console.log(this.user)
      if (data) {

        //LOG
        console.log("[gestione utenti component -- onIninit] \nRichiesta utente conclusa con successo\n" + "User: \n")

        this.findTipoAnagrafica()

      }
      this.loadedOnInit = true;
    });

  }


  //################ FUNZIONI ###############


  ///////////Funzionio di default/////////

  //Permette di visualizzare la grefica del caricamento
  isLoading() {
    if (!this.loadedOnInit || !this.loadedTipoAnag || !this.loadedCerca || !this.loadedNuovoUtente
      || !this.loadedEliminaUtente || !this.loadedFindUtente) {
      return true
    }
    return false
  }

  ///////////Funzionio di chiamata ai servizi

  //Carica i dati di tipo anagrafica chiamando il metodo del service
  findTipoAnagrafica() {
    //LOG
    console.log("[gestione utenti component -- cercaTipoAnagrafica]")
    this.loadedTipoAnag = false;
    this.subscribers.ricerca = this.gestioneUtentiService.findTipoAnagrafica(this.user).subscribe(data => {
      //this.anagraficaList = data;
      if (data) {
        //LOG
        console.log("[gestione utenti component -- onInfindTipoAnagraficainit] \nRichiesta tipo anagrafica conclusa con successo\n")

        if (typeof data == typeof ResponseCodeMessage) {
          //LOG
          console.log("Response code message")
          this.responseCodeMessage = data
        }
        else {
          //LOG
          console.log("Array di tipo anagrafica")
          this.anagraficaList = data;
        }

      }
      this.loadedTipoAnag = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei tipi anagrafica");
      this.loadedTipoAnag = true;
    });
  }

  findUtenti(utenteRicercatoFilter: UtenteRicercatoDTO) {

    this.utenteRicercatoList = undefined;
    this.loadedCerca = false

    //LOG
    console.log("[gestione utenti component -- findUtenti] \nInoltro della richiesta al service")

    this.subscribers.ricerca = this.gestioneUtentiService.findUtenti(this.user, utenteRicercatoFilter).subscribe(data => {
      if (data) {
        //LOG
        console.log("[gestione utenti component -- findUtenti] \nRichiesta tipo anagrafica conclusa con successo")

        if (data.code != undefined && data.message != undefined && data.code !== "NOT_FOUND") {
          this.responseCodeMessage = data
          this.utenteRicercatoList = undefined;
          this.showMessageError(data.message)
          this.isTabellaVisible = false

          console.log("Response code message")
          console.log(this.responseCodeMessage)
        } else if (data.code === "NOT_FOUND") {
          this.utenteRicercatoList = [];
          this.dataSource = new UtenteRiceracaDTODatasource([]);
          this.paginator.length = 0;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
          this.isResultVisible = true;
          this.isTabellaVisible = true;
        } else {
          this.responseCodeMessage = undefined
          this.utenteRicercatoList = data;
          //LOG
          console.log("Array di tipo utenteRicercatoDTO")
          console.log(this.utenteRicercatoList)

          this.caricaTabella()
        }
      }
      this.loadedCerca = true
    }, err => {
      this.showMessageError(this.responseCodeMessage.message)
      this.loadedCerca = true
      this.isTabellaVisible = false
    });

  }

  findSingoloUtente(utenteRicercatoFilter: UtenteRicercatoDTO) {

    this.loadedFindUtente = false

    //LOG
    console.log("[gestione utenti component -- findSingoloUtente] \nInoltro della richiesta al service")

    this.subscribers.ricerca = this.gestioneUtentiService.findUtenti(this.user, utenteRicercatoFilter).subscribe(data => {
      this.loadedFindUtente = true
      if (data) {



        //LOG
        console.log("[gestione utenti component -- findSingoloUtente] \nRichiesta tipo anagrafica conclusa con successo")

        if (data.code != undefined && data.message != undefined) {
          this.responseCodeMessage = data
          this.utenteRicercatoList = undefined;

          //LOG
          console.log("[gestione utenti component -- findSingoloUtente] Response code message")
          console.log(this.responseCodeMessage)
        }
        else {
          var utenteRicercatoList: UtenteRicercatoDTO[] = data;
          //this.utenteRicercatoNew = utenteRicercatoList[0]  
          var utenteRicercatoSelected: UtenteRicercatoDTO = utenteRicercatoList[0]
          this.responseCodeMessage = undefined

          //LOG
          console.log("[gestione utenti component -- findSingoloUtente] Array di tipo utenteRicercatoDTO")
          console.log("nuovo utente che viene passato al dettaglio ")
          console.log(this.utenteRicercatoNew)

          this.openDettaglioUtente(utenteRicercatoSelected)

        }

      }
      //this.loadedOnInit = true;
    });

  }

  findUtentiNuovoUtente(utenteRicercatoFilter: UtenteRicercatoDTO) {

    //LOG
    console.log("[gestione utenti component -- findUtenti] \nInoltro della richiesta al service")

    this.subscribers.ricerca = this.gestioneUtentiService.findUtenti(this.user, utenteRicercatoFilter).subscribe(data => {
      if (data) {
        //LOG
        console.log("[gestione utenti component -- findUtenti] \nRichiesta tipo anagrafica conclusa con successo")

        if (data.code != undefined && data.message != undefined) {
          this.responseCodeMessage = data
          this.utenteRicercatoList = undefined;

          //LOG
          console.log("Response code message")
          console.log(this.responseCodeMessage)
        }
        else {
          this.utenteRicercatoList = data;
          this.responseCodeMessage = undefined

          //LOG
          console.log("Array di tipo utenteRicercatoDTO")
          console.log(this.utenteRicercatoList)

        }


      }
      //this.loadedOnInit = true;
    });

  }

  eliminaUtente() {
    const dialogRef = this.caricaUtentiDialog.open(ConfermaDialog, {
      width: '500px',
      data: {
        message: "Confermare l'eliminazione dell'utente?"
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result == 'SI') {
        this.loadedEliminaUtente = false
        //LOG
        console.log("[gestione utenti component -- eliminaUtente] \nInoltro della richiesta al service")

        this.subscribers.ricerca = this.gestioneUtentiService.eliminaUtente(
          this.user.idUtente.toString(),
          this.utenteRicercatoSelected.idSoggetto.toString(),
          this.utenteRicercatoSelected.idTipoAnagrafica.toString(),
          this.utenteRicercatoSelected.idPersonaFisica.toString()

        ).subscribe(data => {
          if (data) {
            //var esito : GestioneBackOfficeEsitoGenerico = data
            //LOG
            console.log("[gestione utenti component -- eliminaUtente] \nRichiesta di eliminazione utente conclusa con successo")
            console.log(data)

            this.responseCancellaUtente = data
            if (this.responseCancellaUtente.esito) {
              this.showMessageSucces("Utente cancellato correttamente");

              this.findUtenti(this.utenteRicercatoFilter)
            }
            else if (!this.responseCancellaUtente.esito) {
              this.showMessageError(this.responseCancellaUtente.message)

            }
            this.loadedEliminaUtente = true
          }

        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di eliminazione");
          this.loadedEliminaUtente = true
        });

      }


    });


  }

  openDettaglioUtente(utenteRicercatoSelected: UtenteRicercatoDTO) {

    const dialogRef = this.caricaUtentiDialog.open(
      DettaglioUtenteComponent, {
      width: utenteRicercatoSelected.descBreveTipoAnagrafica === Constants.CODICE_RUOLO_PERSONA_FISICA ? '70%' : '50%',
      data: {
        utenteRicercatoSelected: utenteRicercatoSelected,
        user: this.user
      }
    }
    );

    dialogRef.afterClosed().subscribe(result => {

      //COMMENTI TEST
      console.log("[gestione utenti component -- openDettaglioUtente]\n")
      console.log("Dialog chiuso. il risultato è: ")
      console.log(result)


    });

  }

  caricaDati(file: File) {
    //LOG
    console.log("[gestione utenti component -- caricaDati] \n Caricamento del file .csv ")
    console.log(file)
  }



  ///////////Funzionio logiche di funzionamento/////

  onSelectedTipoAnagrafica() {
    //LOG
    console.log("[gestione utenti component -- onSelectedTipoAnagrafica] \nTipo anagrafica selezionato")
    if (this.anagraficaSelezionata) {
      this.utenteRicercatoFilter.idTipoAnagrafica = this.anagraficaSelezionata.id;
      this.utenteRicercatoFilter.descTipoAnagrafica = this.anagraficaSelezionata.descEstesa;
      this.utenteRicercatoFilter.descBreveTipoAnagrafica = this.anagraficaSelezionata.descBreve;
    } else {
      this.utenteRicercatoFilter.idTipoAnagrafica = null;
      this.utenteRicercatoFilter.descTipoAnagrafica = null;
      this.utenteRicercatoFilter.descBreveTipoAnagrafica = null;
    }

    console.log(this.utenteRicercatoFilter.toString())
  }

  onCercaUtentiPressed() {
    this.resetMessage()
    if ((!this.utenteRicercatoFilter.nome || !this.utenteRicercatoFilter.nome.length)
      && (!this.utenteRicercatoFilter.cognome || !this.utenteRicercatoFilter.cognome.length)
      && (!this.utenteRicercatoFilter.codiceFiscale || !this.utenteRicercatoFilter.codiceFiscale.length)
      && !this.utenteRicercatoFilter.dataInserimentoDal
      && !this.utenteRicercatoFilter.dataInserimentoAl
      && !this.utenteRicercatoFilter.idTipoAnagrafica) {
      this.showMessageError("E' necessario inserire almeno un campo di ricerca.");
      return;
    }
    //LOG
    console.log("[gestione utenti component -- cercaUtenti] \n")

    this.findUtenti(this.utenteRicercatoFilter)
  }

  onDettaglioPressed(row: UtenteRicercatoDTO) {
    console.log("[gestione utenti component -- onDettaglioPressed] \nApertura del popup per dettaglio uetente")
    this.utenteRicercatoSelected = row;
    this.openDettaglioUtente(this.utenteRicercatoSelected)
  }

  onEliminaPressed(row: UtenteRicercatoDTO) {
    //LOG

    this.utenteRicercatoSelected = row;
    this.eliminaUtente()
  }

  OnCaricaUtentiPressed() {
    //LOG
    console.log("[gestione utenti component --OnCaricaUtentiPressed] \nApertura del popup per il caricamento utenti")


    const dialogRef = this.caricaUtentiDialog.open(CaricaUtentiComponent,
      { width: '600px', data: { user: this.user } });

    dialogRef.afterClosed().subscribe(result => {

      //COMMENTI TEST
      console.log("[gestione utenti component -- modificabeneficiarioDialog]\n")
      console.log("Dialog chiuso. il risultato è: ")
      console.log(result)

      if (result != undefined) {
        var file: File = result
        this.caricaDati(file);

      }

    });

  }

  OnNuovoUtentePressed() {



    //LOG
    console.log("[gestione utenti component -- OnNuovoUtentePressed] \nApertura del popup per inserimento nuovo utente")

    const dialogRef = this.caricaUtentiDialog.open(
      NuovoUtenteComponent,
      { width: '800px', data: { anagraficaList: this.anagraficaList, user: this.user } }
    );

    dialogRef.afterClosed().subscribe(result => {
      this.loadedNuovoUtente = false //start loading

      //LOG
      console.log("[gestione utenti component -- OnNuovoUtentePressed]\n")
      console.log("Dialog chiuso. il risultato è: ")
      console.log(result)

      if (result == true) {

        this.showMessageSucces("Utente inserito correttamente");
        if (this.utenteRicercatoFilter.nome?.length > 0
          || this.utenteRicercatoFilter.cognome?.length > 0
          || this.utenteRicercatoFilter.codiceFiscale?.length > 0
          || this.utenteRicercatoFilter.dataInserimentoDal
          || this.utenteRicercatoFilter.dataInserimentoAl
          || this.utenteRicercatoFilter.idTipoAnagrafica) {
          this.findUtenti(this.utenteRicercatoFilter);
        }
      }

      this.loadedNuovoUtente = true //stop loading 

      //this.isTabellaVisible = false

    });
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
    var element = document.getElementById('scrollId')
    element.scrollIntoView();
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
    var element = document.getElementById('scrollId')
    element.scrollIntoView();

  }


  /////////// Tabella


  caricaTabella() {
    //this.dataSource = new MatTableDataSource<UtenteRicercatoDTO>(this.utenteRicercatoList);
    //this.dataSource = undefined
    //this.dataSource = new UtenteRiceracaDTODatasource(this.utenteRicercatoList)
    if (this.dataSource == undefined)
      this.dataSource = new UtenteRiceracaDTODatasource(this.utenteRicercatoList)
    else
      this.dataSource.data = this.utenteRicercatoList;


    this.paginator.length = this.utenteRicercatoList.length;
    this.paginator.pageIndex = 0;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.isResultVisible = true;
    this.isTabellaVisible = true;

    console.log(!this.loadedOnInit || !this.loadedTipoAnag || !this.loadedCerca || !this.loadedNuovoUtente || !this.loadedEliminaUtente || !this.loadedFindUtente)



  }

  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }


  }


  /////////// End class
}



export class UtenteRiceracaDTODatasource extends MatTableDataSource<UtenteRicercatoDTO> {

  _orderData(data: UtenteRicercatoDTO[]): UtenteRicercatoDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "codice";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "cognome":
        sortedData = data.sort((permA, permB) => {
          return permA.cognome.localeCompare(permB.cognome);
        });
        break;

      case "nome":
        sortedData = data.sort((permA, permB) => {
          return permA.nome.localeCompare(permB.nome);
        });
        break;

      case "cf":
        sortedData = data.sort((permA, permB) => {
          return permA.codiceFiscale.localeCompare(permB.codiceFiscale);
        });
        break;

      case "anagrafica":
        sortedData = data.sort((permA, permB) => {
          return permA.descBreveTipoAnagrafica.localeCompare(permB.descBreveTipoAnagrafica);
        });
        break;

      case "dataDal":
        sortedData = data.sort((permA, permB) => {
          return permA.dataInserimento.toString().localeCompare(permB.dataInserimento.toString());
        });
        break;

      default:
        sortedData = data.sort((permA, permB) => {
          return permA.descBreveTipoAnagrafica.localeCompare(permB.descBreveTipoAnagrafica);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: UtenteRicercatoDTO[]) {
    super(data);
    super.data = data
  }

}