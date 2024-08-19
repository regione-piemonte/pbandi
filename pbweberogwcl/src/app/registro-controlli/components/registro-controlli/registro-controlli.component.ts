/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent, HandleExceptionService, UserInfoSec } from '@pbandi/common-lib';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { InizializzazioneService } from 'src/app/shared/services/inizializzazione.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { FiltroRicercaIrregolarita } from '../../commons/models/filtro-ricerca-irregolarita';
import { Irregolarita } from '../../commons/models/irregolarita';
import { RettificaForfettaria } from '../../commons/models/rettifica-forfettaria';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';
import { NuovoEsitoControlloComponent } from '../nuovo-esito-controllo/nuovo-esito-controllo.component';
import { RegistroControlliTablesComponent } from '../registro-controlli-tables/registro-controlli-tables.component';

@Component({
  selector: 'app-registro-controlli',
  templateUrl: './registro-controlli.component.html',
  styleUrls: ['./registro-controlli.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RegistroControlliComponent implements OnInit {

  displayedColumnsRegolari: string[] = ['versione', 'versioneP', 'data', 'inizioFineControlli', 'tipo', 'annoContabile', 'autoritaControllante', 'dtCampione', 'detail', 'edit', 'delete'];
  dataSourceRegolari: MatTableDataSource<Irregolarita>;

  displayedColumnsIrregolari: string[] = ['pd', 'versione', 'versioneD', 'data', 'motivoIrregolarita', 'inizioFineControlli', 'tipo', 'annoContabile', 'autoritaControl', 'dataCampione', 'impSpesaIrr', 'impAgevIrreg', 'bloccata', 'riferimentoIms', 'detail', 'edit', 'delete'];
  dataSourceIrregolari: MatTableDataSource<Irregolarita>;
  dataSourceIrregolariComodo: MatTableDataSource<Irregolarita>;

  displayedColumnsRetForf: string[] = ['data', 'perc', 'autoritaControl', 'rifAffidCPACIG', 'rifChecklist', 'esitoRDCA', 'rifPropostaCert', 'delete'];
  dataSourceRetForf: MatTableDataSource<RettificaForfettaria>;

  showTable: boolean = true;

  user: UserInfoSec;
  idProgetto: number;
  codiceProgetto: string;
  beneficiario: string;
  request: FiltroRicercaIrregolarita;

  idOperazione: number;

  @ViewChild(RegistroControlliTablesComponent) tablesComponent: RegistroControlliTablesComponent;

  //SUBSCRIBERS
  subscribers: any = {};

  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  messageError: string;
  isMessageErrorVisible: boolean;

  showRetForfArea: boolean;

  params: URLSearchParams;

  postIns: string;

  loadedgetEsitiRegolari: boolean;
  loadedgetIrregolarita: boolean;
  loadedgetRettifiche: boolean;
  loadedgetIdTipoOperazione: boolean;
  loadedChiudiAttivita: boolean = true;

  constructor(
    private configService: ConfigService,
    private registroControlliService: RegistroControlliService,
    private registroControlliService2: RegistroControlliService2,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private inizializzazioneService: InizializzazioneService,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    private router: Router,
    private dialog: MatDialog
  ) {
    this.dataSourceRegolari = new MatTableDataSource();
    this.dataSourceIrregolari = new MatTableDataSource();
    this.dataSourceRetForf = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    // RECUPERO PARAMETRI DA URL
    this.postIns = this.params.get('postIns');

    if (this.postIns == 'true') {
      this.showMessageSuccess("Rettifica forfettaria salvata con successo.");
    }

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.subscribers.router = this.activatedRoute.params.subscribe(params => {
          this.idProgetto = +params['id'];

          this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
            if (res) {
              this.codiceProgetto = res;
              this.request = {
                idBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario.toString(),
                idProgetto: this.idProgetto.toString()
              };
              this.loadEsitiRegolari();
              this.loadIrregolarita();
              this.loadRettifiche();

              this.loadedgetIdTipoOperazione = true;
              this.subscribers.getIdTipoOperazione = this.registroControlliService2.getIdTipoOperazione(this.user.idUtente, this.idProgetto).subscribe(data => {
                this.loadedgetIdTipoOperazione = false;
                if (data == 1 || data == 2) {
                  this.showRetForfArea = true;
                } else {
                  this.showRetForfArea = false;
                }
              }, err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase di ottenimento dell'idTipoOperazione");
                this.loadedgetIdTipoOperazione = false;
              });
            }
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di ottenimento del codice progetto");
          });
        });
      }
    });
  }

  loadEsitiRegolari(event?: any) {
    this.loadedgetEsitiRegolari = true;
    this.subscribers.getEsitiRegolari = this.registroControlliService.getEsitiRegolari(this.request).subscribe((res: Irregolarita[]) => {
      this.dataSourceRegolari = new MatTableDataSource(res);
      if (event && event.idIrregolarita) {
        this.tablesComponent.dettaglioRegolarita(this.dataSourceRegolari.data.find(d => d.idIrregolaritaCollegata === event.idIrregolarita || d.idIrregolarita === event.idIrregolarita),
          "Salvataggio avvenuto con successo.");
      }
      this.loadedgetEsitiRegolari = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento delle regolarità");
      this.loadedgetEsitiRegolari = false;
    });
  }

  loadIrregolarita(event?: any) {
    this.loadedgetIrregolarita = true;
    this.subscribers.getIrregolarita = this.registroControlliService.getIrregolarita(this.request).subscribe((res: Irregolarita[]) => {
      this.dataSourceIrregolari = new MatTableDataSource(res);
      if (event && event.idIrregolarita) {
        this.tablesComponent.dettaglioIrregolarita(this.dataSourceIrregolari.data.find(d => d.idIrregolaritaProvv === event.idIrregolarita || d.idIrregolarita === event.idIrregolarita),
          "Salvataggio avvenuto con successo.");
      }
      this.loadedgetIrregolarita = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento delle irregolarità");
      this.loadedgetIrregolarita = false;
    });
  }

  loadRettifiche(event?: any) {
    this.loadedgetRettifiche = true;
    this.subscribers.getRettifiche = this.registroControlliService.getRettifiche(this.request).subscribe((res: RettificaForfettaria[]) => {
      res.forEach(r => {
        if (r.idPropostaCertificaz && r.dtOraCreazione) {
          r.rifPropostaCertificazione = r.idPropostaCertificaz + " " + r.dtOraCreazione
        }
      })
      this.dataSourceRetForf = new MatTableDataSource(res);
      this.loadedgetRettifiche = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento delle rettifiche");
      this.loadedgetRettifiche = false;
    });
  }

  nuovoEsito() {
    this.resetMessages();
    let dialogRef = this.dialog.open(NuovoEsitoControlloComponent, {
      width: '76%',
      data: {
        idProgetto: this.idProgetto,
        codiceProgetto: this.codiceProgetto
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.showMessageSuccess(res.message);
        if (res.esitoControllo === "REG") {
          this.loadEsitiRegolari();
        } else {
          this.loadIrregolarita();
        }
      }
    });
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  resetMessages() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.tablesComponent.resetMessageError();
    this.tablesComponent.resetMessageSuccess();
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

  tornaAattivita() {
    this.resetMessages();
    this.loadedChiudiAttivita = false;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_REGISTRO_CONTROLLI).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  rettificaForfettaria() {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/rettifiche-forfettarie?codProg=` + this.codiceProgetto + "&idProgetto=" + this.idProgetto);
  }

  isLoading() {
    if (this.loadedgetEsitiRegolari || this.loadedgetIrregolarita || this.loadedgetRettifiche || this.loadedgetIdTipoOperazione
      || !this.loadedChiudiAttivita) {
      return true;
    }
    return false;
  }
}
