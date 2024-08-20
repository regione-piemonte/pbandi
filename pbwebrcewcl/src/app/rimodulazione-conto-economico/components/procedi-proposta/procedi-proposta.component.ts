/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ArchivioFileDialogComponent, DatiProgettoAttivitaPregresseDialogComponent, DocumentoAllegatoDTO, InfoDocumentoVo } from '@pbandi/common-lib';
import { DocumentoAllegato } from 'src/app/affidamenti/commons/dto/documento-allegato';
import { CodiceDescrizione } from 'src/app/appalti/commons/dto/codice-descrizione';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AssociaFilesRequest } from 'src/app/shared/commons/requests/associa-files-request';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ModalitaAgevolazione } from '../../commons/dto/modalita-agevolazione';
import { ProceduraAggiudicazione } from '../../commons/dto/procedura-aggiudicazione';
import { InviaPropostaRimodulazioneRequest } from '../../commons/request/invia-proposta-rimodulazione-request';
import { RimodulazioneContoEconomicoService } from '../../services/rimodulazione-conto-economico.service';
import { TipoAllegatoDTO } from '../../commons/dto/tipo-allegato-dto';

@Component({
  selector: 'app-procedi-proposta',
  templateUrl: './procedi-proposta.component.html',
  styleUrls: ['./procedi-proposta.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ProcediPropostaComponent implements OnInit {

  idProgetto: number;
  idBando: number;
  idContoEconomico: number;
  codiceProgetto: string;
  user: UserInfoSec;
  note: string;
  rappresentantiLegali: Array<CodiceDescrizione>;
  rappresentanteLegaleSelected: CodiceDescrizione;
  delegati: Array<CodiceDescrizione>;
  delegatoSelected: CodiceDescrizione;
  importoFinanziamento: number;
  importoFinanziamentoFormatted: string;
  totaleRichiestoNuovaProposta: number;
  allegatiAmmessi: boolean;
  modalita: Array<ModalitaAgevolazione>;
  procedure: Array<ProceduraAggiudicazione>;
  allegati: Array<DocumentoAllegato>;
  tipiAllegato: Array<TipoAllegatoDTO>;
  isValidated: boolean;

  displayedColumnsModalita: string[] = ['modalita', 'max', 'perc1', 'ultimoRichiesto', 'erogato', 'ultimoAgevolato', 'perc2'];
  dataSourceModalita: MatTableDataSource<ModalitaAgevolazione> = new MatTableDataSource<ModalitaAgevolazione>([]);

  displayedColumnsProcedure: string[] = ['tipologia', 'cpaCig', 'importo', 'desc'];
  dataSourceProcedure: ProcedureDatasource;

  @ViewChild('paginator', { static: true }) paginator: MatPaginator;
  @ViewChild('sort', { static: true }) sort: MatSort;

  @ViewChild('propostaForm', { static: true }) propostaForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedAllegati: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedAssociaAllegati: boolean = true;
  loadedProcedi: boolean = true;
  loadedTipiDocumento: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private sharedService: SharedService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private rimodulazioneContoEconomicoService: RimodulazioneContoEconomicoService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBando = +params['idBando'];
      this.idContoEconomico = +params['idContoEconomico'];
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.loadedInizializza = false;
          this.subscribers.init = this.rimodulazioneContoEconomicoService.inizializzaConcludiPropostaRimodulazione(this.idProgetto).subscribe(data => {
            if (data) {
              this.codiceProgetto = data.codiceVisualizzatoProgetto;
              this.rappresentantiLegali = data.rappresentantiLegali;
              this.totaleRichiestoNuovaProposta = data.totaleRichiestoNuovaProposta;
              this.delegati = data.delegati;
              this.allegatiAmmessi = data.allegatiAmmessi;
              this.modalita = data.listaModalitaAgevolazione;
              this.dataSourceModalita = new MatTableDataSource(this.modalita);
              this.procedure = data.listaProcedureAggiudicazione;
              this.dataSourceProcedure = new ProcedureDatasource(this.procedure);
              this.paginator.length = this.procedure.length;
              this.paginator.pageIndex = 0;
              this.dataSourceProcedure.paginator = this.paginator;
              this.dataSourceProcedure.sort = this.sort;
              this.loadAllegati();
            }
            this.loadedInizializza = true;
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
        }
      });
    });
  }

  loadAllegati() {
    this.loadedTipiDocumento = false;
    this.rimodulazioneContoEconomicoService.getTipiAllegatiProposta(this.idBando, this.idProgetto).subscribe(data => {
      if (data) {
        this.tipiAllegato = data;
        if (this.tipiAllegato?.length > 0) {
          this.tipiAllegato.forEach(t => {
            if (t.flagAllegato === "S") {
              t.checked = true;
            }
          })
        }
      }
      this.loadedTipiDocumento = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero dei tipi allegati.");
      this.loadedTipiDocumento = true;
    });
    this.loadedAllegati = false;
    this.rimodulazioneContoEconomicoService.allegatiPropostaRimodulazione(this.idProgetto).subscribe(data => {
      if (data) {
        this.allegati = data;
      }
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero degli allegati.");
      this.loadedAllegati = true;
    });
  }

  setImportoFinanziamento() {
    this.importoFinanziamento = this.sharedService.getNumberFromFormattedString(this.importoFinanziamentoFormatted);
    if (this.importoFinanziamento !== null) {
      this.importoFinanziamentoFormatted = this.sharedService.formatValue(this.importoFinanziamento.toString());
    }
  }

  aggiungiAllegato() {
    this.resetMessageError();
    this.resetMessageSuccess();
    let all = new Array<DocumentoAllegatoDTO>();
    this.allegati.forEach(a => {
      all.push(new DocumentoAllegatoDTO(null, null, a.id, null, null, null, this.idProgetto, a.nome, null, null, null))
    });
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegati: all,
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });

    dialogRef.afterClosed().subscribe((res: Array<InfoDocumentoVo>) => {
      if (res && res.length > 0) {
        this.loadedAssociaAllegati = false;
        let request = new AssociaFilesRequest(res.map(all => +all.idDocumentoIndex), this.idProgetto, this.idProgetto);
        this.subscribers.associa = this.rimodulazioneContoEconomicoService.associaAllegatiAPropostaRimodulazione(request).subscribe(data => {
          if (data) {
            if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
              && (!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
              this.showMessageSuccess("Tutti gli allegati sono stati associati correttamente.");
              this.loadAllegati();
            } else if (data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0
              && (!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
              this.showMessageError("Errore nell'associazione degli allegati.");
            } else if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
              && data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
              this.loadAllegati();
              let message = "Errore nell'associazione dei seguenti allegati: ";
              data.elencoIdDocIndexFilesNonAssociati.forEach(id => {
                let allegato = res.find(a => a.idDocumentoIndex === id.toString());
                message += allegato.nome + ", ";
              });
              message = message.substr(0, message.length - 2);
              this.showMessageError(message);
            }
          }
          this.loadedAssociaAllegati = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedAssociaAllegati = true;
          this.showMessageError("Errore nell'associazione degli allegati.")
        });
      }
    });
  }

  disassociaAllegato(id: number) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedAssociaAllegati = false;
    this.subscribers.disassocia = this.rimodulazioneContoEconomicoService.disassociaAllegatoPropostaRimodulazione(id, this.idProgetto).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.loadAllegati();
          this.showMessageSuccess(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedAssociaAllegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedAssociaAllegati = true;
      this.showMessageError("Errore nella disassociazione dell'allegato.");
    });
  }

  procedi() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.isValidated = false;
    let validationError: boolean;
    if (!this.rappresentanteLegaleSelected) {
      this.propostaForm.form.get('rappresentante').setErrors({ error: 'required' });
      this.propostaForm.form.get('rappresentante').markAsTouched();
      validationError = true;
    }
    if (this.importoFinanziamento < 0 || this.importoFinanziamento > 999999999999999.99) {
      this.propostaForm.form.get('importoFinanziamento').setErrors({ error: 'notValid' });
      this.propostaForm.form.get('importoFinanziamento').markAsTouched();
      validationError = true;
    }
    if (validationError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    } else {
      this.showMessageWarning("Tutti gli importi verranno salvati. Continuare?");
      this.isValidated = true;
    }
  }


  continua() {
    this.loadedProcedi = false;
    let mod = new Array<ModalitaAgevolazione>();
    this.modalita.forEach(m => mod.push(Object.assign({}, m)));
    mod.forEach(r => {
      //campi usati in concludi rimodulazione
      delete r['importoAgevolatoFormatted'];
      delete r['percentualeImportoAgevolatoFormatted'];
    });

    let tipi = new Array<TipoAllegatoDTO>();
    this.tipiAllegato.forEach(t => tipi.push(Object.assign({}, t)));
    tipi.forEach(t => {
      t.flagAllegato = t.checked ? "S" : "N";
      delete t['checked'];
    });

    let request = new InviaPropostaRimodulazioneRequest(this.idProgetto, this.idContoEconomico, this.user.beneficiarioSelezionato.idBeneficiario, mod, this.note,
      this.rappresentanteLegaleSelected ? +this.rappresentanteLegaleSelected.codice : null, this.delegatoSelected ? +this.delegatoSelected.codice : null,
      this.importoFinanziamento, tipi?.length ? tipi : null);
    this.subscribers.invia = this.rimodulazioneContoEconomicoService.inviaPropostaRimodulazione(request).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO}/invioProposta/${this.idProgetto}/`
            + `${this.idBando}/${data.idContoEconomico}/${data.idDocumentoIndex}`]);
        } else {
          this.showMessageError("Errore in fase di invio proposta.");
        }
      }
      this.loadedProcedi = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedProcedi = true;
      this.showMessageError("Errore in fase di invio proposta.");
    });
  }

  indietro() {
    if (!this.isValidated) {
      this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO}/rimodulazioneContoEconomico/${this.idProgetto}/${this.idBando}`]);
    } else {
      this.isValidated = false;
      this.resetMessageWarning();
    }
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO).subscribe(data => {
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
    if (!this.loadedChiudiAttivita || !this.loadedInizializza || !this.loadedAssociaAllegati || !this.loadedProcedi || !this.loadedAllegati) {
      return true;
    }
    return false;
  }
}

export class ProcedureDatasource extends MatTableDataSource<ProceduraAggiudicazione> {

  _orderData(data: ProceduraAggiudicazione[]): ProceduraAggiudicazione[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "id";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "tipologia":
        sortedData = data.sort((procA, procB) => {
          return procA.descTipologiaAggiudicazione.localeCompare(procB.descTipologiaAggiudicazione);
        });
        break;
      case "cpaCig":
        sortedData = data.sort((procA, procB) => {
          let a = procA.codProcAgg ? procA.codProcAgg : procA.cigProcAgg;
          let b = procB.codProcAgg ? procB.codProcAgg : procB.cigProcAgg;
          return a ? (b ? a.localeCompare(b) : -1) : 1;
        });
        break;
      case "importo":
        sortedData = data.sort((procA, procB) => {
          return procA.importo - procB.importo;
        });
        break;
      case "desc":
        sortedData = data.sort((procA, procB) => {
          return procA.descProcAgg.localeCompare(procB.descProcAgg);
        });
        break;
      default:
        sortedData = data.sort((procA, procB) => {
          return procA.descTipologiaAggiudicazione.localeCompare(procB.descTipologiaAggiudicazione);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: ProceduraAggiudicazione[]) {
    super(data);
  }
}
