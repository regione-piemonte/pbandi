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
import { UserInfoSec, HandleExceptionService, DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ModalitaAgevolazione } from '../../commons/dto/modalita-agevolazione';
import { SalvaRichiestaContoEconomicoRequest } from '../../commons/request/salva-richiesta-conto-economico-request';
import { RimodulazioneContoEconomicoService } from '../../services/rimodulazione-conto-economico.service';

@Component({
  selector: 'app-invia-richiesta',
  templateUrl: './invia-richiesta.component.html',
  styleUrls: ['./invia-richiesta.component.scss']
})
export class InviaRichiestaComponent implements OnInit {

  idProgetto: number;
  idBando: number;
  idContoEconomico: number;
  codiceProgetto: string;
  user: UserInfoSec;
  note: string;
  importoFinanziamento: number;
  importoFinanziamentoFormatted: string;
  totaleRichiestoInDomanda: number;
  modalita: Array<ModalitaAgevolazione>;
  isValidated: boolean;

  displayedColumnsModalita: string[] = ['modalita', 'max', 'perc1'];
  dataSourceModalita: MatTableDataSource<ModalitaAgevolazione> = new MatTableDataSource<ModalitaAgevolazione>([]);

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
  loadedChiudiAttivita: boolean = true;
  loadedProcedi: boolean = true;

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
          this.subscribers.init = this.rimodulazioneContoEconomicoService.inizializzaConcludiRichiestaContoEconomico(this.idProgetto).subscribe(data => {
            if (data) {
              this.codiceProgetto = data.codiceVisualizzatoProgetto;
              this.totaleRichiestoInDomanda = data.totaleRichiestoInDomanda;
              this.modalita = data.listaModalitaAgevolazione;
              this.dataSourceModalita = new MatTableDataSource(this.modalita);
            }
            this.loadedInizializza = true;
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
        }
      });
    });
  }

  setImportoFinanziamento() {
    this.importoFinanziamento = this.sharedService.getNumberFromFormattedString(this.importoFinanziamentoFormatted);
    if (this.importoFinanziamento !== null) {
      this.importoFinanziamentoFormatted = this.sharedService.formatValue(this.importoFinanziamento.toString());
    }
  }

  procedi() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.resetMessageWarning();
    this.isValidated = false;
    let validationError: boolean;
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
    let request = new SalvaRichiestaContoEconomicoRequest(this.idProgetto, this.idContoEconomico, this.user.beneficiarioSelezionato.idBeneficiario,
      mod, this.note, null, this.importoFinanziamento);
    this.subscribers.invia = this.rimodulazioneContoEconomicoService.salvaRichiestaContoEconomico(request).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_CONTO_ECONOMICO_DOMANDA}/rimodulazioneContoEconomico/`
            + `${this.idProgetto}/${this.idBando}/${this.idContoEconomico}`]);
        } else {
          let messaggio: string = '';
          if (data.messaggi && data.messaggi.length > 0) {
            data.messaggi.forEach(m => {
              messaggio += m + "<br/>";
            });
            messaggio = messaggio.substr(0, messaggio.length - 5);
            if (messaggio.length > 0) {
              this.showMessageError(messaggio);
            }
          }
        }
      }
      this.loadedProcedi = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedProcedi = true;
      this.showMessageError("Errore in fase di invio della richiesta.");
    });
  }

  indietro() {
    if (!this.isValidated) {
      this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_CONTO_ECONOMICO_DOMANDA}/rimodulazioneContoEconomico/${this.idProgetto}/${this.idBando}`]);
    } else {
      this.isValidated = false;
      this.resetMessageWarning();
    }
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_RICHIESTA_CONTO_ECONOMICO_DOMANDA).subscribe(data => {
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
    if (!this.loadedChiudiAttivita || !this.loadedInizializza || !this.loadedProcedi) {
      return true;
    }
    return false;
  }
}