/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { MatPaginator } from "@angular/material/paginator";
import { ActivatedRoute, Router } from "@angular/router";
import { ConfigService } from "../../../core/services/config.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { UserService } from "../../../core/services/user.service";
import { MatDialog } from "@angular/material/dialog";
import { FideiussioniService } from "../../services/fideiussioni.service";
import { Constants } from "../../../core/commons/util/constants";
import { DateAdapter } from "@angular/material/core";
import { NgForm } from "@angular/forms";
import { MatTableDataSource } from "@angular/material/table";
import { Fideiussione } from "../../commons/dto/fideiussione";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { CercaFideiussioniRequest } from "../../commons/dto/cerca-fideiussioni-request";
import { FiltroRicercaFideiussione } from "../../commons/dto/filtro-ricerca-fideiussione";
import { DatePipe, formatNumber } from "@angular/common";
import { EsitoOperazioni } from "../../commons/dto/esito-operazioni";
import { ConfermaDialog } from "../../../shared/components/conferma-dialog/conferma-dialog";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { CreaAggiornaFideiussioneRequest } from "../../commons/dto/crea-aggiorna-fideiussione-request";
import { SharedService } from "../../../shared/services/shared.service";
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';

@Component({
  selector: 'app-gestione-fideiussioni',
  templateUrl: './gestione-fideiussioni.component.html',
  styleUrls: ['./gestione-fideiussioni.component.scss']
})
export class GestioneFideiussioniComponent implements OnInit {
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;
  isFideiussioneGestibile: EsitoOperazioni = { msg: "" };

  //STATO DI FORM
  isModifica: boolean;

  @ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;
  //@ViewChild("ngForm", { static: true }) ngForm: NgForm;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  criteriRicercaOpened: boolean;
  hasValidationError: boolean;

  //MODELLI DI RICERCA
  codiceRiferimento: string;
  dataDecorrenza: Date;
  dataScadenza: Date;
  tuttiRisultatiVisualizzati: boolean;

  displayedColumns: string[] = ['codice', 'dataDecorrenza', 'enteEmittente', 'importo', 'dataScadenza', 'descrizione'];
  dataSource: MatTableDataSource<Fideiussione> = new MatTableDataSource<Fideiussione>();

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedRicerca: boolean;
  loadedFideiussioni: boolean;
  loadedElimina: boolean;
  loadedModifica: boolean;
  loadedChiudiAttivita: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  // MODELLI API REQUEST/RESPONSE
  cercaFideiussioniRequest: CercaFideiussioniRequest;
  filtroRicerca: FiltroRicercaFideiussione;
  elencoFideiussioni: Array<Fideiussione> = new Array<Fideiussione>();
  fideiussioneInModifica: Fideiussione;
  tipoTrattamentoSelected: CodiceDescrizione;
  tipiTrattamento: Array<CodiceDescrizione>;
  dtDecorrenza: Date;
  dtScadenza: Date;
  aggiornaFideiussioneRequest: CreaAggiornaFideiussioneRequest;

  constructor(private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router, private fideiussioniService: FideiussioniService,
    private inizializzazioneService: InizializzazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService, private datePipe: DatePipe,
    private dialog: MatDialog, private _adapter: DateAdapter<any>,
    private sharedService: SharedService) {
    this._adapter.setLocale('it');
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo) {
          this.displayedColumns.push('azioni');
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.fideiussioniService.isFideiussioneGestibile(this.idProgetto).subscribe((res: EsitoOperazioni) => {
              if (res) {
                this.isFideiussioneGestibile = res;
                if (this.isFideiussioneGestibile.esito) {
                  this.loadData();
                  this.cerca();
                } else {
                  this.showMessageError(this.isFideiussioneGestibile.msg);
                }
              }
            });

          });
        }
      }
    });
  }


  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_GEST_FIDEIUSSIONI;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codice = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProgetto = true;
    });

    this.fideiussioniService.getTipiDiTrattamento(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.tipiTrattamento = res;
      }
    })
  }

  cerca() {
    this.resetAllMessages();
    if (this.dataScadenza < this.dataDecorrenza) {
      this.showMessageError("La Data scadenza non può essere anteriore alla Data decorrenza.");
      return;
    }

    this.filtroRicerca = {
      codiceRiferimento: this.codiceRiferimento ? this.codiceRiferimento : null,
      dtDecorrenza: this.dataDecorrenza ? this.sharedService.formatDateToString(this.dataDecorrenza) : null,
      dtScadenza: this.dataScadenza ? this.sharedService.formatDateToString(this.dataScadenza) : null
    };
    this.cercaFideiussioniRequest = { idProgetto: this.idProgetto, filtro: this.filtroRicerca };
    this.loadedRicerca = true;
    this.fideiussioniService.getFideiussioni(this.cercaFideiussioniRequest).subscribe(data => {
      if (data) {
        this.elencoFideiussioni = data;
        this.dataSource = new MatTableDataSource<Fideiussione>();
        this.dataSource.data = this.elencoFideiussioni;
        this.paginator.length = this.elencoFideiussioni.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        if (data.length > 0) {
          this.noElementsMessageVisible = false;
          if (this.paginator.hasNextPage())
            this.tuttiRisultatiVisualizzati = false;
          else this.tuttiRisultatiVisualizzati = true;
        } else {
          this.noElementsMessageVisible = true;
        }
      }
      this.loadedRicerca = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRicerca = false;

      this.showMessageError("Errore nella ricerca fideiussioni.");
    })
  }
  showModificaFideiussione(fideiussione: Fideiussione) {
    this.isModifica = true;
    this.fideiussioneInModifica = {
      idFideiussione: fideiussione.idFideiussione, codRiferimentoFideiussione: fideiussione.codRiferimentoFideiussione,
      dtDecorrenza: this.datePipe.transform(fideiussione.dtDecorrenza, 'yyyy-MM-dd'),
      dtScadenza: this.datePipe.transform(fideiussione.dtScadenza, 'yyyy-MM-dd'),
      importo: fideiussione.importo, idProgetto: fideiussione.idProgetto, importoTotaleTipoTrattamento: fideiussione.importoTotaleTipoTrattamento,
      numero: fideiussione.numero,
      importoFideiussione: fideiussione.importoFideiussione,
      idTipoTrattamento: fideiussione.idTipoTrattamento, descrizioneTipoTrattamento: fideiussione.descrizioneTipoTrattamento,
      descEnteEmittente: fideiussione.descEnteEmittente, noteFideiussione: fideiussione.noteFideiussione,
    }
    this.tipiTrattamento.map(t => {
      if (t.codice === fideiussione.idTipoTrattamento.toString()) {
        this.tipoTrattamentoSelected = t;
      }
    })
  }

  modifica() {
    this.resetAllMessages();
    this.validate();
    if (!this.hasValidationError) {
      this.loadedModifica = true;
      this.fideiussioneInModifica.dtDecorrenza = this.datePipe.transform(this.fideiussioneInModifica.dtDecorrenza, 'yyyy-MM-dd'),
        this.fideiussioneInModifica.dtScadenza = this.datePipe.transform(this.fideiussioneInModifica.dtScadenza, 'yyyy-MM-dd'),

        this.aggiornaFideiussioneRequest = { idProgetto: this.idProgetto, fideiussione: this.fideiussioneInModifica };
      this.subscribers.save = this.fideiussioniService.creaAggiornaFideiussione(this.aggiornaFideiussioneRequest).subscribe(res => {
        if (res.esito) {
          this.cerca();
          this.showMessageSuccess(res.message);
          this.isModifica = false;
        } else {
          this.showMessageError(res.message);
        }
        this.loadedModifica = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel'aggiornamento della fideiussione.")
        this.loadedModifica = false;
      })
    }
  }

  eliminaFideiussione(idFideiussione: number) {
    this.resetAllMessages();
    let fideiussione = this.elencoFideiussioni.find(a => a.idFideiussione === idFideiussione);
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi la eliminazione del seguente fideiussione?<br><br>" + " Codice riferimento: " + fideiussione.codRiferimentoFideiussione + " Descrizione: " + fideiussione.noteFideiussione
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.eliminaFideiussione = this.fideiussioniService.eliminaFideiussione(idFideiussione).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.cerca();
              this.showMessageSuccess(data.message);
            } else {
              this.cerca()
              this.showMessageError(data.message);
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore nell'eliminazione della fideiussione.");
        });
      }
    });

  }

  onSelectTipoTrat(value: string) {
    this.tipiTrattamento.map(t => {
      if (t.codice === value) {
        this.tipoTrattamentoSelected = t;
      }
    });
  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.hasValidationError = false;
    this.checkRequiredForm(this.ricercaForm, "enteEmittente");
    this.checkRequiredForm(this.ricercaForm, "dtDecorrenza");
    this.checkRequiredForm(this.ricercaForm, "tipoTrattamento");

    if (this.fideiussioneInModifica.dtScadenza < this.fideiussioneInModifica.dtDecorrenza) {
      this.showMessageError("La Data scadenza non può essere anteriore alla Data decorrenza.");
      this.hasValidationError = true;
      return;
    }

    this.setAllMarkAsTouched();
    if (this.hasValidationError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  checkRequiredForm(form: NgForm, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  setAllMarkAsTouched() {
    Object.keys(this.ricercaForm.form.controls).forEach(key => {
      if (this.ricercaForm.form.controls[key]) {
        this.ricercaForm.form.controls[key].markAsTouched();
      }
    });
  }



  isLoading() {
    if (!this.loadedCodiceProgetto || this.loadedRicerca || this.loadedModifica || !this.loadedChiudiAttivita) {
      return true;
    } return false;
  }
  showMessageSuccess(msg: string) {
    this.messageSuccess = msg; this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  showMessageError(msg: string) {
    this.messageError = msg; this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }
  resetMessageSuccess() {
    this.messageSuccess = null; this.isMessageSuccessVisible = false;
  }
  resetMessageError() {
    this.messageError = null; this.isMessageErrorVisible = false;
  }
  resetAllMessages() {
    this.resetMessageError(); this.resetMessageSuccess();
  }

  /* goToAttivita() {
     window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
   }*/
  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto,
      Constants.DESCR_BREVE_TASK_GESTIONE_FIDEIUSSIONI).subscribe(data => {
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

  goToNuovaFideiussione() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_FIDEIUSSIONI + "/nuovaFideiussione", this.idProgetto]);
  }


  goToDettaglioFideiussione(idFideiussione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_FIDEIUSSIONI + "/dettaglioFideiussione", this.idProgetto, idFideiussione]);
  }
}
