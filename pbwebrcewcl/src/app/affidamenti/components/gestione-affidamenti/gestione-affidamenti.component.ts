/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { EsitoOperazioni } from 'src/app/shared/commons/dto/esito-operazioni';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { RigaTabellaAffidamenti } from '../../commons/dto/affidamento';
import { DocumentoAllegato } from '../../commons/dto/documento-allegato';
import { VerificaAffidamentoRequest } from '../../commons/requests/verifica-affidamento-request';
import { AffidamentiService } from '../../services/affidamenti.service';
import { ConfermaInvioVerificaDialogComponent } from '../conferma-invio-verifica-dialog/conferma-invio-verifica-dialog.component';
import { DocumentiGeneratiDialogComponent } from '../documenti-generati-dialog/documenti-generati-dialog.component';

@Component({
  selector: 'app-gestione-affidamenti',
  templateUrl: './gestione-affidamenti.component.html',
  styleUrls: ['./gestione-affidamenti.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneAffidamentiComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isIstruttore: boolean;
  isBeneficiario: boolean;
  elencoAffidamenti: Array<RigaTabellaAffidamenti>;
  documentiGenerati: Array<DocumentoAllegato>;

  displayedColumns: string[] = ['categoria', 'oggetto', 'fornitori', 'cigCpa', 'importo', 'stato'];
  dataSource: MatTableDataSource<RigaTabellaAffidamenti> = new MatTableDataSource<RigaTabellaAffidamenti>();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedAffidamenti: boolean;
  loadedDocumentiGenerati: boolean = true;
  loadedInvio: boolean = true;
  loadedElimina: boolean = true;
  loadedChiudiAttivita: boolean = true;
  loadedLinea2127: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private affidamentiService: AffidamentiService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private sharedService: SharedService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI) {
            this.displayedColumns.push('esitoint');
            this.displayedColumns.push('esitodef');
            this.isIstruttore = true;
          } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_BEN_MASTER
            || this.user.codiceRuolo === Constants.CODICE_RUOLO_PERSONA_FISICA) {
            this.isBeneficiario = true;
          } else {
            this.displayedColumns.push('esitoint');
            this.displayedColumns.push('esitodef');
          }
          this.displayedColumns.push('azioni');
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.subscribers.codice = this.affidamentiService.getCodiceProgetto(this.idProgetto).subscribe(res => {
              if (res) {
                this.codiceProgetto = res;
              }
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
            });
            if (this.activatedRoute.snapshot.paramMap.get('checklist') != null && this.activatedRoute.snapshot.paramMap.get('checklist') === "SUCCESS") {
              this.showMessageSuccess("Il salvataggio della checklist è avvenuto correttamente.");
            } else if (this.activatedRoute.snapshot.paramMap.get('message')) {
              this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('message'));
            }
            this.loadAffidamenti();
          });
        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_GEST_AFFIDAMENTI;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadAffidamenti(message?: string) {
    this.loadedAffidamenti = false;
    this.subscribers.elenco = this.affidamentiService.getElencoAffidamenti(this.idProgetto).subscribe(data => {
      if (data) {
        data.forEach(d => {
          if ((this.isIstruttore || (!this.isIstruttore && !this.isBeneficiario)) && (d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_IN_VERIFICA
            || d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_RICHIESTA_INTEGRAZIONE
            || d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_VERIFICA_PARZIALE
            || d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_VERIFICA_DEFINITIVA)) {
            d.iconaPdf = 'S';
            d.iconaDettaglio = 'esamina';
          } else if (this.isBeneficiario) {
            if (d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_DA_INVIARE
              || d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_RICHIESTA_INTEGRAZIONE
              || d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_VERIFICA_PARZIALE) {
              d.iconaDettaglio = 'modifica';
              if (d.numAllegatiAssociati > 0 && d.numFornitoriAssociati > 0) {
                d.iconaInviaInVerifica = 'invio';
              } else {
                d.iconaInviaInVerifica = 'completare';
              }
            }
            if (d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_IN_VERIFICA
              || d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_VERIFICA_DEFINITIVA) {
              d.iconaDettaglio = 'esamina';
            }
            if (d.idStatoAffidamento === Constants.ID_STATO_AFFIDAMENTO_DA_INVIARE) {
              d.iconaElimina = 'S';
            }
          }
        });
        if (message) {
          this.resetMessageSuccess();

          this.showMessageSuccess(message);
        }
        this.elencoAffidamenti = data;
        this.dataSource.data = this.elencoAffidamenti;
        this.paginator.length = this.elencoAffidamenti.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
      }
      this.loadedAffidamenti = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  inviaInVerifica(affidamento: RigaTabellaAffidamenti) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ConfermaInvioVerificaDialogComponent, {
      width: "40%",
      data: {
        oggetto: affidamento.oggettoAppalto,
        esisteAllegatoNonInviato: affidamento.esisteAllegatoNonInviato
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.loadedInvio = false;

        this.subscribers.invio = this.affidamentiService.inviaInVerifica(new VerificaAffidamentoRequest(this.idProgetto, affidamento.idAppalto)).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamenti(data.message);
            } else {
              this.showMessageError(data.message);
            }
          }
          this.loadedInvio = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedInvio = true;
          console.log(err)
          if (err.error != null && err.error.msg != null) {
            this.showMessageError(err.error.msg);
          }
          else {
            this.showMessageError("Errore durante l'invio in verifica.");
          }


        });
      }
    });
  }

  openDocumentiGenerati(idAppalto: number) {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.loadedDocumentiGenerati = false;
    this.documentiGenerati = null;
    this.subscribers.documentiGenerati = this.affidamentiService.getChecklistAssociatedAffidamento(idAppalto).subscribe(data => {
      if (data) {
        this.documentiGenerati = data;
        this.dialog.open(DocumentiGeneratiDialogComponent, {
          width: '55%',
          maxHeight: '95%',
          data: {
            documenti: this.documentiGenerati,
            idProgetto: this.idProgetto
          }
        });
      }
      this.loadedDocumentiGenerati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDocumentiGenerati = true;

      this.showMessageError("Errore nel recuperare i documenti generati.");
    });
  }

  eliminaAffidamento(idAppalto: number) {
    this.resetMessageSuccess();
    this.resetMessageError();

    let affidamento = this.elencoAffidamenti.find(a => a.idAppalto === idAppalto);
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi la eliminazione del seguente affidamento?<br>" + affidamento.oggettoAppalto
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.eliminaAffidamento = this.affidamentiService.cancellaAffidamento(idAppalto).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.loadAffidamenti(data.message);
            } else {

              this.showMessageError(data.message);
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;

          this.showMessageError("Errore nell'eliminazione dell'affidamento.");
        });
      }
    });
  }

  isInviaVisible(row: RigaTabellaAffidamenti) {
    if (row.iconaInviaInVerifica === 'invio' && !(row.idStatoAffidamento === 3 && row.esitoIntermedio.includes('NEGATIVO'))) {
      return true;
    }
    return false;
  }

  nuovoAffidamento() {
    this.router.navigate(["drawer/" + this.idOperazione + "/nuovoAffidamento", this.idProgetto]);
  }

  goToModificaAffidamento(idAppalto: number) {
    this.router.navigate(["drawer/" + this.idOperazione + "/modificaAffidamento", idAppalto]);
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

  getDescBreveTask() {
    let descBreveTask: string;
    switch (this.idOperazione) {
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_AFFIDAMENTI:
        descBreveTask = Constants.DESCR_BREVE_TASK_GESTIONE_AFFIDAMENTI;
        break;
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_AFFIDAMENTI_BEN:
        descBreveTask = Constants.DESCR_BREVE_TASK_GESTIONE_AFFIDAMENTI_BEN;
        break;
      default:
        break;
    }
    return descBreveTask;
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, this.getDescBreveTask()).subscribe(data => {
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
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  isLoading() {
    if (!this.loadedAffidamenti || !this.loadedInvio || !this.loadedDocumentiGenerati || !this.loadedElimina
      || !this.loadedChiudiAttivita || !this.loadedLinea2127) {
      return true;
    }
    return false;
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
