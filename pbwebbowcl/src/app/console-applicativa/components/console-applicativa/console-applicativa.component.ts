/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { ConsoleApplicativaService } from '../../services/console-applicativa.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConsoleMonitoraggioFilter } from '../../commons/dto/ConsoleMonitoraggioFilter';
import { PbandiTMonServAmmvoContabDTO } from '../../commons/dto/PbandiTMonServAmmvoContabDTO';
import { ResponseCodeMessage } from 'src/app/shared/commons/dto/response-code-message-dto';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ConsoleMonitoraggioServiziDTO } from '../../commons/dto/MonitoraggioServiziDTO';
import { MatTableDataSource } from '@angular/material/table';
import { PbandiServiziContDTO } from '../../commons/dto/PbandiServiziContDTO';


@Component({
  selector: 'app-console-applicativa',
  templateUrl: './console-applicativa.component.html',
  styleUrls: ['./console-applicativa.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ConsoleApplicativaComponent implements OnInit {


  statusColorMonitoraggio: string = 'giallo'; // default



    //dati filtro ---> eventualmente strutturare un oggetto.
  consoleMonitoraggioFilter: ConsoleMonitoraggioFilter;

    erroreSelezionato : PbandiTMonServAmmvoContabDTO;
    servizioSelezionato : PbandiServiziContDTO;

    erroriList:  Array<PbandiTMonServAmmvoContabDTO>;
    serviziList:  Array<PbandiServiziContDTO>;
    erroriListMonitoraggio:  Array<PbandiTMonServAmmvoContabDTO>;
  //Object subscriber
  subscribers: any = {};


  responseCodeMessage: ResponseCodeMessage;// messaggio di risposta

  // Variabili di errore
  messageError: string;
  isMessageErrorVisible: boolean;
  isResultVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;


 monitoraggioErrorList: Array<ConsoleMonitoraggioServiziDTO>

   //## VARIABILI TABELLA
   displayedColumns: string[] = ['nomeServizio', 'categoria','esito','codiceErrore', 'messaggioErrore', 'dataInizioChiamata', 'dataFineChiamata'];
   @ViewChild("elencoMonitoraggio") paginator: MatPaginator;
   @ViewChild("sort", { static: true }) sort: MatSort;
   dataSource: ConsoleMonitoraggioServiziDTODatasource ;

   constructor( private consoleApplicativaService: ConsoleApplicativaService,
    private handleExceptionService: HandleExceptionService,
    ) { }

  ngOnInit(): void {
    this.consoleMonitoraggioFilter = ConsoleMonitoraggioFilter.createEmptyConsoleMonitoraggioFilter();

    this.preValorizzaFiltroRicercaMonitoraggio();


    this.findTipoErrore();
    this.findServizi();
    this.onTracciaChiamateMonitoraggio();
  }



onTracciaChiamateMonitoraggio() {

  this.resetMessage()
  if ((!this.consoleMonitoraggioFilter.dataInserimentoDal || !this.consoleMonitoraggioFilter.dataInserimentoAl)) {
    this.showMessageError("E' necessario inserire le date di ricerca");
    return;
  }


  console.log("[console-applicativa component -- onTracciaChiamateMonitoraggio] \n");

if(this.consoleMonitoraggioFilter.dataInserimentoDal && this.consoleMonitoraggioFilter.dataInserimentoAl
  && this.consoleMonitoraggioFilter.oraInserimentoDal && this.consoleMonitoraggioFilter.dataInserimentoAl){



  this.consoleMonitoraggioFilter.dataInserimentoDal = this.combinaDataEOra(this.consoleMonitoraggioFilter.dataInserimentoDal, this.consoleMonitoraggioFilter.oraInserimentoDal);
  this.consoleMonitoraggioFilter.dataInserimentoAl =  this.combinaDataEOra(this.consoleMonitoraggioFilter.dataInserimentoAl, this.consoleMonitoraggioFilter.oraInserimentoAl);

}
  //PRIMA DI ESTRARRE I DATI ESEGUO LA CHIAMATA AL SERVIZIO DI VERIFICA DELLO STATUS
  this.gestStatusMonitoraggio();

   this.subscribers.ricerca = this.consoleApplicativaService.getMonitoraggio(this.consoleMonitoraggioFilter).subscribe(data => {
    if (data) {
        this.monitoraggioErrorList = data;
        this.dataSource = new ConsoleMonitoraggioServiziDTODatasource(this.monitoraggioErrorList);
        this.paginator.length = this.monitoraggioErrorList.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }
  }, err => {
    this.showMessageError(this.responseCodeMessage.message);
  });

}


onSelectedTipoErrore() {
  if (this.erroreSelezionato) {
    this.consoleMonitoraggioFilter.codiceErrore = this.erroreSelezionato.codiceErrore;

  } else {
    this.consoleMonitoraggioFilter.codiceErrore = null;
  }
}

onSelectedServizio() {
  if (this.servizioSelezionato) {
    this.consoleMonitoraggioFilter.idServizio = this.servizioSelezionato.idServizio;

  } else {
    this.consoleMonitoraggioFilter.idServizio = null;
  }
}

  findTipoErrore() {
    this.subscribers.ricerca = this.consoleApplicativaService.findTipoErrore().subscribe(data => {
      if (data) {

        if (typeof data == typeof ResponseCodeMessage) {
          //LOG
          console.log("Response code message")
          this.responseCodeMessage = data
        }
        else {
          this.erroriList = data;
        }

      }

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero della tipologia di errore");

    });
}

findServizi() {
  this.subscribers.ricerca = this.consoleApplicativaService.findServizi().subscribe(data => {
    if (data) {

      if (typeof data == typeof ResponseCodeMessage) {
        //LOG
        console.log("Response code message")
        this.responseCodeMessage = data
      }
      else {
        this.serviziList = data;
      }

    }

  }, err => {
    this.handleExceptionService.handleNotBlockingError(err);
    this.showMessageError("Errore in fase di recupero della tipologia di errore");

  });
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
  //  var element = document.getElementById('scrollId')
    //element.scrollIntoView();
  }

    // resetta gli errori
    resetMessage() {
      this.messageError = null;
      this.messageSuccess = null;
      this.isMessageErrorVisible = false;
      this.isMessageSuccessVisible = false;
    }



    gestStatusMonitoraggio() {
      this.subscribers.ricerca = this.consoleApplicativaService.getStatusMonitoraggio(this.consoleMonitoraggioFilter).subscribe(data => {
        if (data) {
          //LOG
          console.log("console-monitoraggio component -- gestStatusMonitoraggio] \nRichiesta tipo anagrafica conclusa con successo\n")

          if (typeof data == typeof ResponseCodeMessage) {
            //LOG
            console.log("Response code message")
            this.responseCodeMessage = data
          }
          else {
            this.erroriListMonitoraggio = data;
          }

        }

      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di recupero della tipologia di errore");

      });


  }

  getStatusServizio(nomeServizio: string) {
    if (this.erroriListMonitoraggio && this.erroriListMonitoraggio.length > 0) {
      const servizioConEsitoKO = this.erroriListMonitoraggio.find(dato => dato.esito === 'KO' && dato.categoriaServizio === nomeServizio);

      if (servizioConEsitoKO) {
        this.statusColorMonitoraggio = 'rosso'; // Se trova un "KO" per il servizio specifico, cambia il colore del semaforo in rosso
      } else {
        const servizioConEsitoOK = this.erroriListMonitoraggio.find(dato => dato.esito === 'OK' && dato.categoriaServizio === nomeServizio);
        if(servizioConEsitoOK){
          this.statusColorMonitoraggio = 'verde';
        }else{
          this.statusColorMonitoraggio = 'giallo';
        }
      }
    } else {
      this.statusColorMonitoraggio = 'giallo';
    }
      let coloreLuce = 'giallo'; // Colore di default
      switch (this.statusColorMonitoraggio.toLowerCase()) {
        case 'rosso':
          coloreLuce = 'red';
          break;
        case 'giallo':
          coloreLuce = 'yellow';
          break;
        case 'verde':
          coloreLuce = 'green';
          break;

      }
      return { 'background-color': coloreLuce };
    }






    combinaDataEOra(data: Date, ora: string): Date {
      const oraSplit = ora.split(':'); // Dividi l'ora in ore e minuti
      data.setHours(parseInt(oraSplit[0])); // Imposta le ore
      data.setMinutes(parseInt(oraSplit[1])); // Imposta i minuti
      return data;
    }


    formatTime(date: Date): string {
      return `${this.padZero(date.getHours())}:${this.padZero(date.getMinutes())}`;
    }
    padZero(num: number): string {
      return (num < 10 ? '0' : '') + num;
    }


    preValorizzaFiltroRicercaMonitoraggio(){
      let adesso = new Date();
      adesso.setMinutes(adesso.getMinutes() - 15);
      this.consoleMonitoraggioFilter.dataInserimentoDal = adesso ;

      adesso = new Date();
      this.consoleMonitoraggioFilter.dataInserimentoAl =  adesso;

      let orarioInizioPredefinito : string = '';
      let orarioFinePredefinito : string = '';


      orarioFinePredefinito = this.formatTime(adesso);
      this.consoleMonitoraggioFilter.oraInserimentoAl = orarioFinePredefinito;

      adesso.setMinutes(adesso.getMinutes() - 15);

      // Formattare l'orario per adattarsi al formato dell'input type="time" (HH:mm)
      orarioInizioPredefinito = this.formatTime(adesso);
      this.consoleMonitoraggioFilter.oraInserimentoDal = orarioInizioPredefinito;

    }
}

export class ConsoleMonitoraggioServiziDTODatasource extends MatTableDataSource<ConsoleMonitoraggioServiziDTO> {

  _orderData(data: ConsoleMonitoraggioServiziDTO[]): ConsoleMonitoraggioServiziDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "nomeServizio";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "esito":
        sortedData = data.sort((permA, permB) => {
          return permA.esito.localeCompare(permB.esito);
        });
        break;

      case "codiceErrore":
        sortedData = data.sort((permA, permB) => {
          if(permA.codiceErrore){
            return permA.codiceErrore.localeCompare(permB.codiceErrore);
          }

        });
        break;

        case "categoria":
          sortedData = data.sort((permA, permB) => {
            return permA.categoriaServizio.localeCompare(permB.codiceErrore);
          });
          break;

      case "messaggioErrore":
        sortedData = data.sort((permA, permB) => {
          if(permA.messaggioErrore){
            return permA.messaggioErrore.localeCompare(permB.messaggioErrore);
          }

        });
        break;

      case "dataInizioChiamata":
        sortedData = data.sort((permA, permB) => {
          return permA.dataInizioChiamata.toString().localeCompare(permB.dataInizioChiamata.toString());
        });
        break;

      case "dataFineChiamata":
        sortedData = data.sort((permA, permB) => {
          return permA.dataFineChiamata.toString().localeCompare(permB.dataFineChiamata.toString());
        });
        break;

        case "nomeServizio":
        sortedData = data.sort((permA, permB) => {
          return permA.nomeServizio.localeCompare(permB.nomeServizio);
        });
        break;

      default:

        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: ConsoleMonitoraggioServiziDTO[]) {
    super(data);
    super.data = data
  }

}
