/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { DocumentoCertificazioneDTO } from 'src/app/shared/commons/dto/documento-certificazione-dto';
import { LineaInterventoDTO } from 'src/app/shared/commons/dto/linea-intervento-dto';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { PropostaCertifLineaRequest } from 'src/app/shared/commons/requests/proposta-certif-linea-request';
import { ProposteCertificazioneRequest } from 'src/app/shared/commons/requests/proposte-certificazione-request';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { FiltroRicercaDocumentoDTO } from '../../commons/dto/filtro-ricerca-documento-dto';
import { saveAs } from "file-saver-es";

@Component({
  selector: 'app-ricerca-documenti',
  templateUrl: './ricerca-documenti.component.html',
  styleUrls: ['./ricerca-documenti.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaDocumentiComponent implements OnInit {

  user: UserInfoSec;
  oi_ist_master: boolean;
  adg_cert: boolean;
  criteriRicercaOpened: boolean = true;
  stati: Array<string>;
  lineeIntervento: Array<LineaInterventoDTO>;
  lineaDiInterventoSelected: LineaInterventoDTO;
  proposte: Array<PropostaCertificazioneDTO>;
  proposteFiltered: Array<PropostaCertificazioneDTO>;
  propostaSelected: PropostaCertificazioneDTO;

  checkListCertificazione: boolean = true;
  dichiarazioneFinale: boolean = true;
  filePropostaCertificazione: boolean = true;

  elencoDocumenti: Array<DocumentoCertificazioneDTO>;
  dataSource: ElencoDocumentiDatasource;
  displayedColumns: string[] = ['nomefile', 'descrizione', 'proposta', 'progetto', 'tipodoc', 'datadoc', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedProposte: boolean;
  loadedLineeIntervento: boolean;
  loadedCerca: boolean = true;
  loadedDownload: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadedProposte = false;
    this.loadedLineeIntervento = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_ISTRUTTORE) {
          this.oi_ist_master = true;
        } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_CERT) {
          this.adg_cert = true;
        }

        this.stati = new Array<string>();
        if (this.oi_ist_master) {
          this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA);
        } else {
          this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA);
          this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_APERTA);
          this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_RESPINTA);
          this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_ANNULLATA);

          if (this.adg_cert) {
            this.stati.push(Constants.DESC_BREVE_STATO_PROPOSTA_BOZZA);
          }
        }

        let requestProposte = new ProposteCertificazioneRequest(this.user, this.stati);
        this.subscribers.findProposte = this.certificazioneService.propostePerRicercaDocumenti(requestProposte).subscribe(data1 => {
          if (data1) {
            this.proposte = data1;
            this.proposteFiltered = data1.slice();
            let p = new PropostaCertificazioneDTO();
            p.idPropostaCertificaz = 0;
            this.proposte.unshift(p);
            let idLinee = new Array<number>();
            for (let id of this.proposte.map(p => p.idLineaDiIntervento)) {
              if (!idLinee.includes(id)) {
                idLinee.push(id);
              }
            }
            let requestLinee = new PropostaCertifLineaRequest(this.user, idLinee);
            this.subscribers.getLinee = this.certificazioneService.getLineeDiInterventoFromIdLinee(requestLinee).subscribe(data2 => {
              if (data2) {
                this.lineeIntervento = data2;
                this.lineeIntervento.unshift(new LineaInterventoDTO(0, "", ""));
              }
              this.loadedLineeIntervento = true;
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.loadedLineeIntervento = true;
            });
          }
          this.loadedProposte = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
    });
  }

  onSelectLineaIntervento() {
    if (this.lineaDiInterventoSelected.idLineaDiIntervento === 0) {
      this.lineaDiInterventoSelected = null;
      this.proposteFiltered = this.proposte.slice();
    } else {
      this.proposteFiltered = this.proposte.slice().filter(p => p.idLineaDiIntervento === this.lineaDiInterventoSelected.idLineaDiIntervento);
    }
  }

  onSelectProposta() {
    if (this.propostaSelected.idPropostaCertificaz === 0) {
      this.propostaSelected = null;
    }
  }

  ricerca() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedCerca = false;
    if (this.oi_ist_master) {
      this.dichiarazioneFinale = false;
      this.checkListCertificazione = false;
      this.filePropostaCertificazione = true;
    }
    let filtro = new FiltroRicercaDocumentoDTO(this.lineaDiInterventoSelected != null ? this.lineaDiInterventoSelected.idLineaDiIntervento : null, null,
      this.propostaSelected != null ? this.propostaSelected.idPropostaCertificaz : null, null,
      this.dichiarazioneFinale, true, this.checkListCertificazione, false, this.filePropostaCertificazione, this.stati);

    this.subscribers.cerca = this.certificazioneService.ricercaDocumenti(filtro).subscribe(data => {
      if (data) {
        this.elencoDocumenti = data;
        this.dataSource = new ElencoDocumentiDatasource(this.elencoDocumenti);
        setTimeout(() => {
          this.paginator.length = this.elencoDocumenti.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        }, 1000);
        if (data.length > 0) {
          this.noElementsMessageVisible = false;
        } else {
          this.noElementsMessageVisible = true;
        }
        this.criteriRicercaOpened = false;
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella ricerca dei documenti.");
      this.loadedCerca = true;
    });

  }

  downloadAllegato(idDocumentoIndex: number, nomeDocumento: string) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downloadFile = this.certificazioneService.getContenutoDocumentoById(idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
        this.showMessageSuccess("Download avvenuto con successo");
      }
      this.loadedDownload = true;
    }, error => {
      this.handleExceptionService.handleNotBlockingError(error);
      this.loadedDownload = true;
      this.showMessageError("Errore nel download del file.");
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedProposte || !this.loadedLineeIntervento || !this.loadedCerca || !this.loadedDownload) {
      return true;
    }
    return false;
  }

}

export class ElencoDocumentiDatasource extends MatTableDataSource<DocumentoCertificazioneDTO> {


  _orderData(data: DocumentoCertificazioneDTO[]): DocumentoCertificazioneDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "idDocumentoIndex"; //default
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "nomefile":
        sortedData = data.sort((propA, propB) => {
          return propA.nomeDocumento.localeCompare(propB.nomeDocumento);
        });
        break;
      case "proposta":
        sortedData = data.sort((propA, propB) => {
          return propA.idPropostaCertificaz - propB.idPropostaCertificaz;
        });
        break;
      case "progetto":
        sortedData = data.sort((propA, propB) => {
          return propA.idProgetto - propB.idProgetto;
        });
        break;
      case "tipodoc":
        sortedData = data.sort((propA, propB) => {
          return propA.descTipoDocIndexStato .localeCompare(propB.descTipoDocIndexStato );
        });
        break;
      default:
        sortedData = data.sort((propA, propB) => {
          return propA.idDocumentoIndex - propB.idDocumentoIndex;
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: DocumentoCertificazioneDTO[]) {
    super(data);
  }
}
