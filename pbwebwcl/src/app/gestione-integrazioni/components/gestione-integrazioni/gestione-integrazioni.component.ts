/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { IntegrazioniRevocaDTO } from '../../commons/dto/integrazioniRevocaDTO';
import { GestioneIntegrazioniService } from '../../services/gestione-integrazioni.service';
import { MatSort } from '@angular/material/sort';
import { ConfermaInvioIntegrazioneComponent } from '../conferma-invio-integrazione/conferma-invio-integrazione.component';
import { IntegrazioniRendicontazioneDTO } from '../../commons/dto/integrazioniRendicontazioneDTO';
import { IntegrazioniControlliDTO } from '../../commons/dto/integrazioniControlliDTO';
import { RichiestaProrogaIntegrazioneComponent } from '../richiesta-proroga-integrazione/richiesta-proroga-integrazione.component';
import { IndicatoriService } from 'src/app/indicatori/services/indicatori.service';
import { DettaglioComponent } from '../dettaglio/dettaglio.component';

@Component({
  selector: 'app-gestione-integrazioni',
  templateUrl: './gestione-integrazioni.component.html',
  styleUrls: ['./gestione-integrazioni.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneIntegrazioniComponent implements OnInit {

  // Startup
  beneficiario: string;
  codiceProgetto: string = "";
  idProgetto: number;
  user: UserInfoSec;
  idOperazione: number;
  progBandoLineaIntervento: number;

  intRendicon: Array<IntegrazioniRendicontazioneDTO>;
  intRendiconDataSource: MatTableDataSource<IntegrazioniRendicontazioneDTO> = new MatTableDataSource<IntegrazioniRendicontazioneDTO>([]);
  nRendicontazioni: number = 0;
  intRevoca: Array<IntegrazioniRevocaDTO>;
  intRevDataSource: MatTableDataSource<IntegrazioniRevocaDTO> = new MatTableDataSource<IntegrazioniRevocaDTO>([]);
  nRevoche: number = 0;
  intControlli: Array<IntegrazioniRevocaDTO>;
  intControlliDataSource: MatTableDataSource<IntegrazioniRevocaDTO> = new MatTableDataSource<IntegrazioniRevocaDTO>([]);
  nControlli: number;

  //displayedColumns: string[] = ['nRevoca', 'dataRichiesta', 'dataNotifica', 'dataScadenza', 'dataInvio', 'statoRichiesta', 'buttons'];
  rendiDisplayedColumns;
  revDisplayedColumns: string[] = ['nRevoca', 'dataRichiesta', 'dataNotifica', 'dataScadenza', 'dataInvio', 'statoRichiesta', 'buttons'];
  contDisplayedColumns: string[] = ['nProtocollo', 'dataRichiesta', 'dataNotifica', 'dataScadenza', 'dataInvio', 'statoRichiesta', 'buttons'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  // Loading spinner
  intRendiconLoaded: boolean;
  intRevocaLoaded: boolean = true;
  intControlliLoaded: boolean = true;
  loadedCodiceProgetto: boolean;
  loadedBandoFP: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  // Panels
  panel1OpenState = false;
  panel2OpenState = false;
  panel3OpenState = false;

  isFP: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private configService: ConfigService,
    private integrazioniService: GestioneIntegrazioniService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private userService: UserService,
    private indicatoriService: IndicatoriService,
    private dialog: MatDialog
  ) { }


  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user?.beneficiarioSelezionato) {
          this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['idProgetto'];
            this.progBandoLineaIntervento = +params['idBandoLinea'];
            this.loadData();
          });
        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_GEST_INTEGRAZIONI;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  get descBreveStatoRichiestaChiusaUfficio() {
    return Constants.DESC_BREVE_STATO_RICHIESTA_INTEG_CHIUSA_UFFICIO;
  }

  loadData() {
    // primo controllare se è 2 e dunque vede poi questo poi vedere se è finpiemonte 
    this.loadedBandoFP = false;
    this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.progBandoLineaIntervento).subscribe(data => {
      this.isFP = data;
      this.getIntRendicontazione();
      if (this.isFP == true) {
        this.rendiDisplayedColumns = ['nDichiarazioneSpesa', 'dataRichiesta', 'dataNotifica', 'dataScadenza', 'dataInvio', 'longStatoRichiesta', 'buttons'];
        this.getIntRevoca();
        this.getIntControlli();
      } else {
        this.rendiDisplayedColumns = ['nDichiarazioneSpesa', 'dataRichiesta', 'dataNotifica', 'dataInvio', 'longStatoRichiesta', 'buttons'];
        this.panel1OpenState = true;
      }
      this.loadedBandoFP = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica bando competenza FP.");
      this.loadedBandoFP = true;
    });

    this.loadedCodiceProgetto = false;
    this.indicatoriService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del codice progetto.");
      this.loadedCodiceProgetto = true;
    });
  }

  getIntRevoca() {
    this.intRevocaLoaded = false;
    this.integrazioniService.getIntegrazioniRevoca(this.idProgetto, this.progBandoLineaIntervento).subscribe(data => {
      this.intRevoca = data;
      this.intRevDataSource = new MatTableDataSource<any>(data);
      this.intRevDataSource.sort = this.sort;
      this.nRevoche = this.intRevoca.length;
      this.intRevocaLoaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricmento delle integrazioni alla revoca.");
      this.intRevocaLoaded = true;
    });
  }

  getIntControlli() {
    this.intControlliLoaded = false;
    this.integrazioniService.getIntegrazioniControlli(this.idProgetto).subscribe(data => {
      this.intControlli = data;
      this.intControlliDataSource = new MatTableDataSource<IntegrazioniControlliDTO>(this.intControlli);
      this.intControlliDataSource.sort = this.sort;
      this.nControlli = this.intControlli.length;
      this.intControlliLoaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricmento delle integrazioni ai controlli.");
      this.intControlliLoaded = true;
    });
  }

  getIntRendicontazione() {
    this.intRendiconLoaded = false;
    this.integrazioniService.getIntegrazioniRendicontazione(this.idProgetto, this.progBandoLineaIntervento).subscribe(data => {
      this.intRendicon = data;
      this.intRendiconDataSource = new MatTableDataSource<IntegrazioniRendicontazioneDTO>(this.intRendicon);
      this.intRendiconDataSource.sort = this.sort;
      this.nRendicontazioni = this.intRendicon.length;
      this.intRendiconLoaded = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricmento delle integrazioni alla rendicontazione.");
      this.intRendiconLoaded = true;
    });
  }

  richiediProroga(element, numero, ambito) {
    const dialogRef = this.dialog.open(RichiestaProrogaIntegrazioneComponent, {
      height: 'auto',
      width: 'auto',
      data: {
        dataKey: element,
        dataKey2: numero,
        dataKey3: this.idProgetto,
        dataKey4: ambito
      },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.loadData();
    });

  }

  dettaglio(element, ambito) {
    const dialogRef = this.dialog.open(DettaglioComponent, {
      height: 'auto',
      width: '70em',
      data: {
        dataKey: element,
        // dataKey2 : tipo,
        dataKey3: this.idProgetto,
        dataKey4: ambito
      },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      this.loadData();
    });
  }

  invia(element, tipo) {
    const dialogRef = this.dialog.open(ConfermaInvioIntegrazioneComponent, {
      height: 'auto',
      width: '50rem',
      data: {
        dataKey: element,
        dataKey2: tipo
      },
    });
    dialogRef.afterClosed().subscribe((dialogResult) => {
      if (dialogResult) {
        this.loadData();
        this.showMessageSuccess("Operazione avvenuta con successo.");
      }
    });
  }

  integrazioneProcRevoca(element) {
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_INTEGRAZIONI + "/integrazione-procedimento-revoca"], { queryParams: { idProgetto: this.idProgetto, idBandoLinea: this.progBandoLineaIntervento, codiceProgetto: this.codiceProgetto, nRevoca: element.nRevoca, idRichIntegrazione: element.idRichIntegrazione } });
  }

  integrazioneAiControlli(element) {
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_INTEGRAZIONI + "/integrazione-ai-controlli"], { queryParams: { idProgetto: this.idProgetto, idBandoLinea: this.progBandoLineaIntervento, codiceProgetto: this.codiceProgetto, idIntegrazione: element.idIntegrazione } });
  }

  integrazioneRendicontazione(element, stato) {
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_INTEGRAZIONI + "/integrazione-rendicontazione"], { queryParams: { idProgetto: this.idProgetto, idBandoLinea: this.progBandoLineaIntervento, codiceProgetto: this.codiceProgetto, idIntegrazione: element.idIntegrazioneSpesa, idDichiarazioneSpesa: element.nDichiarazioneSpesa, stato: stato } });
  }



  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_GESTIONE_INTEGRAZIONI).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
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
    if (!this.intRendiconLoaded || !this.intRevocaLoaded || !this.intControlliLoaded
      || !this.loadedBandoFP || !this.loadedChiudiAttivita || !this.loadedCodiceProgetto) {
      return true;
    }
    return false;
  }

}
