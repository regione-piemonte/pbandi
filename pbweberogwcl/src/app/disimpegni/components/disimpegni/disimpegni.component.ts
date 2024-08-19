/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import * as _ from "lodash";
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";

import { DisimpegniService } from "../../services/disimpegni.service";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { ModalitaAgevolazioneDTO } from "../../commons/dto/modalita-agevolazione-dto";
import { RigaModificaRevocaItem } from "../../commons/models/riga-modifica-revoca-item";
import { EsitoSalvaRevocaDTO } from "../../commons/dto/esito-salva-revoca-dto";

import { Observable as __Observable } from 'rxjs';  // Todo: remove - tmp
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { AgevolazioneRow } from '../../commons/models/agevolazione-row';
import { Disimpegni2Service } from '../../services/disimpegni-2.service';
@Component({
  selector: 'app-disimpegni',
  templateUrl: './disimpegni.component.html',
  styleUrls: ['./disimpegni.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DisimpegniComponent implements OnInit {

  idProgetto: number;
  isMaster: boolean;
  codiceProgetto: string;

  user: UserInfoSec;

  //LOADED
  loadedCheckProposta: boolean;
  loadedCodiceProgetto: boolean;
  loadedModalitaAgevolazione: boolean;
  loadedDisimpegni: boolean;
  loadedEliminaRevoca: boolean = true;
  loadedChiudiAttivita: boolean = true;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];

  modalitaAgevolazione: ModalitaAgevolazioneDTO[];
  disimpegni: RigaModificaRevocaItem[];

  modalitaAgevErogRows: Array<AgevolazioneRow>;
  displayedColumnsModalitaAgevolazioneErogazione: string[] = ['descBreveModalitaAgevolaz', 'quotaImportoAgevolato', 'importoErogazioni', 'totaleImportoRecuperato', 'data', 'riferimento'];
  dataSourceModalitaAgevolazioneErogazione: MatTableDataSource<AgevolazioneRow> = new MatTableDataSource<AgevolazioneRow>();

  displayedColumnsDisimpegni: string[] = ['causaleDisimpegno', 'motivazione', 'ordineRecupero', 'modalitaRecupero', 'importoDisimpegno', 'data', 'riferimento', 'importoIrregolarita', 'azioni'];
  dataSourceDisimpegni: MatTableDataSource<RigaModificaRevocaItem> = new MatTableDataSource<RigaModificaRevocaItem>();

  constructor(
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private disimpegniService: DisimpegniService,
    private disimpegni2Service: Disimpegni2Service,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService,
  ) {
    ;
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.idIride && this.user.codiceRuolo && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) {  // this.user.idIride
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_OPE_SUP_IST) {
            this.isMaster = true;
          }
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            if (params['id']) {
              this.idProgetto = +params['id'];
              this.loadData();
              this.loadModalitaAgevolazione();
            } else {
              this.showMessageError('Identificativo progetto non inserito');
            }
            if (this.activatedRoute.snapshot.paramMap.get('message')) {
              this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('message'));
            }
          });
        } else {
          console.warn('this.user.idIride', this.user.idIride);
          console.warn('this.user.beneficiarioSelezionato', this.user.beneficiarioSelezionato);
          console.warn('this.user.beneficiarioSelezionato.denominazione', ((this.user.beneficiarioSelezionato) ? this.user.beneficiarioSelezionato.denominazione : ''));
        }
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
    });
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
        this.setupHeaderLabelsButtons();
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del codice del progetto.");
      this.loadedCodiceProgetto = true;
    });
    this.loadedCheckProposta = false;
    this.subscribers.checkProposta = this.disimpegni2Service.checkPropostaCertificazione(this.idProgetto).subscribe(res => {
      if (res && res.esito) {
        this.showMessageWarning(res.msg);
      }
      this.loadedCheckProposta = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica esistenza proposta di certificazione per il progetto.");
      this.loadedCheckProposta = true;
    });
  }

  loadModalitaAgevolazione() {
    this.loadedModalitaAgevolazione = false;
    this.subscribers.modalitaAgevolazione = this.disimpegniService.getModalitaAgevolazione(this.idProgetto).subscribe((res: ModalitaAgevolazioneDTO[]) => {
      if (res) {
        this.modalitaAgevolazione = res;
        this.modalitaAgevErogRows = new Array<AgevolazioneRow>();
        for (let mod of this.modalitaAgevolazione) {
          this.modalitaAgevErogRows.push(new AgevolazioneRow(mod.idModalitaAgevolazione, mod.descModalitaAgevolazione, mod.quotaImportoAgevolato,
            mod.importoErogazioni, mod.totaleImportoRecuperato, null, null, true));
          for (let caus of mod.causaliErogazioni) {
            this.modalitaAgevErogRows.push(new AgevolazioneRow(null, caus.descCausale, null, caus.importoErogazione, null,
              caus.dtContabile, caus.codRiferimentoErogazione, false));
          }
        }
        this.dataSourceModalitaAgevolazioneErogazione = new MatTableDataSource<AgevolazioneRow>(this.modalitaAgevErogRows);
        this.loadDisimpegni();
      }
      this.loadedModalitaAgevolazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del riepilogo erogazioni.");
      this.loadedModalitaAgevolazione = true;
    });
  }

  loadDisimpegni() {
    this.loadedDisimpegni = false;
    this.subscribers.disimpegni = this.disimpegniService.getDisimpegni(this.modalitaAgevolazione).subscribe((res: RigaModificaRevocaItem[]) => {
      if (res) {
        this.disimpegni = res;
        this.dataSourceDisimpegni = new MatTableDataSource<RigaModificaRevocaItem>(this.disimpegni);
      }
      this.loadedDisimpegni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei disimpegni.");
      this.loadedDisimpegni = true;
    });
  }
  loadEliminaRevoca(row) {
    this.loadedEliminaRevoca = false;
    this.resetMessageError();
    this.resetMessageSuccess();
    let request: DisimpegniService.EliminaRevocaParams = {
      idProgetto: this.idProgetto,
      idRevoca: row.idRevoca
    };
    this.disimpegniService.eliminaRevoca(request).subscribe((res: EsitoSalvaRevocaDTO) => {
      if (res) {
        if (res.esito) {
          this.showMessageSuccess(res.msgs.map(o => o.msgKey).join("<br>"));
          this.loadModalitaAgevolazione();
        } else if (res.msgs) {
          this.showMessageError(res.msgs.map(o => o.msgKey).join("<br>"));
        } else {
          this.showMessageError('Nessun messaggio');
        }
      }
      this.loadedEliminaRevoca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedEliminaRevoca = true;
    });
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_GESTIONE_DISIMPEGNI).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedEliminaRevoca || !this.loadedCheckProposta || !this.loadedChiudiAttivita) {
      return true
    }
    return false;
  }

  setupHeaderLabelsButtons() {
    this.headerLabels = [
      {
        codice: 'Beneficiario',
        descrizione: ((this.user && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) ? this.user.beneficiarioSelezionato.denominazione : '')
      },
      {
        codice: 'Codice progetto',
        descrizione: this.codiceProgetto
      }
    ];
    this.headerButtons = [
      {
        codice: 'dati-progetto-e-attivita-pregresse',
        descrizione: 'Dati progetto e attività pregresse'
      }
    ];
  }

  eliminaDisimpegno(row: RigaModificaRevocaItem) {
    this.congermaDialog(row);
  }

  onNuovoDisimpegno() {
    this.goToNuovoDisimpegno(this.idProgetto);
  }

  congermaDialog(row) {
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.congermaDialogSi(row);
      }
    });
  }

  congermaDialogSi(row) {
    this.loadEliminaRevoca(row);
  }

  onHeaderButtonsClick(btn: CodiceDescrizione) {

    switch (btn.codice) {
      case 'dati-progetto-e-attivita-pregresse':
        this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
          width: '90%',
          maxHeight: '95%',
          data: {
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      default:
    }
  }

  goToDettaglioDisimpegno(idRevoca: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_DISIMPEGNI + "/disimpegni/dettaglio-disimpegno", this.idProgetto, idRevoca]);
  }

  goToNuovoDisimpegno(idProgetto: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_DISIMPEGNI + "/disimpegni/nuovo-disimpegno", idProgetto]);
  }

  goToModificaDisimpegno(idRevoca: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_DISIMPEGNI + "/disimpegni/modifica-disimpegno", this.idProgetto, idRevoca]);
  }

  goToRipartisciIrregolarita(idRevoca: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_DISIMPEGNI + "/disimpegni/ripartisci-irregolarita", this.idProgetto, idRevoca]);
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

  showMessageWarning(msg: string) {
    this.messageWarning = msg;
    this.isMessageWarningVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
}