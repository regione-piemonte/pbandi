/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { CodiceDescrizione } from "../../../shared/commons/models/codice-descrizione";
import { MatTableDataSource } from "@angular/material/table";
import { MatPaginator } from "@angular/material/paginator";
import { EsitoSalvaRecuperoDTO } from "../../commons/dto/esito-salva-recupero-dto";
import { RequestSalvaRecuperi } from "../../commons/requests/request-salva-recuperi";
import { ConfigService } from "../../../core/services/config.service";
import { UserService } from "../../../core/services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { SharedService } from "../../../shared/services/shared.service";
import { DatePipe } from "@angular/common";
import { RecuperiService } from "../../services/recuperi.service";
import { EsitoOperazioni } from "../../../shared/api/models/esito-operazioni";
import { DatiProgettoAttivitaPregresseDialogComponent } from "@pbandi/common-lib";
import { ConfermaDialog } from "../../../shared/components/conferma-dialog/conferma-dialog";
import { MatDialog } from "@angular/material/dialog";
import { RigaModificaRecuperoItem } from "../../commons/models/riga-modifica-recupero-item";
import { Constants } from 'src/app/core/commons/util/constants';
import { RevocheService } from 'src/app/revoche/services/revoche.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';

@Component({
  selector: 'app-gestione-recuperi',
  templateUrl: './gestione-recuperi.component.html',
  styleUrls: ['./gestione-recuperi.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneRecuperiComponent implements OnInit {

  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isMaster: boolean;
  esistePropostaCertif: EsitoOperazioni;
  regolaRecuperi: EsitoOperazioni;

  recuperi: Array<RigaModificaRecuperoItem> = new Array<RigaModificaRecuperoItem>();

  displayedColumns: string[] = ['modalitaDiAgevolazione', 'ultimoImportoAgevolato', 'importoGiaErogato', 'importoGiaRevocato', 'importoGiaRecuperato', 'data', 'riferimento'];
  dataSource: MatTableDataSource<RigaModificaRecuperoItem> = new MatTableDataSource<RigaModificaRecuperoItem>();

  isMessageSuccessVisible: any;
  isMessageWarningVisible: any;
  isMessageErrorVisible: any;

  messageSuccess: any;
  messageWarning: any;
  messageError: any;

  noElementsMessageVisible: any;
  noElementsMessage: string = "Non ci sono elementi da visualizzare.";

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedRecuperi: boolean;
  loadedPropostaCertif: boolean;
  loadedisRecuperoAccessibile: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedElimina: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(private configService: ConfigService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private recuperiService: RecuperiService,
    private inizializzazioneService: InizializzazioneService,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private revocheService: RevocheService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
              || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
              || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE) {
              this.isMaster = true;
            }
            this.loadData();
          });

          if (this.activatedRoute.snapshot.paramMap.get('message')) {
            this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('message'));
          }
        }
      }
    });
  }

  loadData() {
    // CODICE PROGETTO
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del codice del progetto.");
      this.loadedCodiceProgetto = true;
    });
    // LOAD PROPOSTA CERTIFICAZIONE PROGETTO
    this.loadedPropostaCertif = false;
    this.subscribers.codice = this.revocheService.checkPropostaCertificazioneProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.esistePropostaCertif = res;
        //non uso res.msg perchè è riferito alle revoche e noai recuperi
      }
      this.loadedPropostaCertif = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedPropostaCertif = true;
    });
    this.loadedisRecuperoAccessibile = false;
    this.subscribers.regolaRecuperi = this.recuperiService.isRecuperoAccessibile(this.idProgetto).subscribe(res => {
      if (res) {
        this.regolaRecuperi = res;
        if (this.isMaster && this.regolaRecuperi.esito) {
          this.displayedColumns.push('azioni');
        }
      }
      this.loadedisRecuperoAccessibile = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedisRecuperoAccessibile = true;
    });
    this.loadRecuperi();
  }

  loadRecuperi() {
    this.loadedRecuperi = false;
    this.subscribers.codice = this.recuperiService.getRecuperiPerModifica(this.idProgetto).subscribe(res => {
      if (res) {
        this.recuperi = res;
        this.dataSource = new MatTableDataSource<RigaModificaRecuperoItem>();
        this.dataSource.data = this.recuperi;
        if (res.length > 0) {
          this.noElementsMessageVisible = false;
        } else {
          this.noElementsMessageVisible = true;
        }
      }
      this.loadedRecuperi = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRecuperi = true;
    });
  }

  confermaDialog(idRecupero) {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        // CHIAMA SERVIZIO ELIMINA REVOCA
        this.eliminaRecupero(idRecupero);
      }
    });
  }

  eliminaRecupero(idRecupero: any) {
    this.loadedElimina = false;
    this.subscribers.elimina = this.recuperiService.cancellaRecupero(idRecupero).subscribe(res => {
      if (res.esito) {
        this.showMessageSuccess(res.msgs[0].msgKey);
        this.loadRecuperi();
      } else {
        this.showMessageError(res.msgs[0].msgKey);
      }
      this.loadedElimina = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedElimina = true;
    });
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_GESTIONE_RECUPERI).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  goToDatiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  goToNuovoRecupero() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_RECUPERI + "/nuovoRecupero", this.idProgetto]);
  }

  goToModificaRecupero(idModalitaAgevolazione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_RECUPERI + "/modificaRecupero", this.idProgetto, idModalitaAgevolazione]);
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
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

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedElimina || !this.loadedChiudiAttivita || !this.loadedRecuperi
      || !this.loadedPropostaCertif || !this.loadedisRecuperoAccessibile) {
      return true;
    }
    return false;
  }

}
