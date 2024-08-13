/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from '@angular/common';
import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { DateAdapter } from '@angular/material/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DichiarazioneDiSpesaService } from '../../services/dichiarazione-di-spesa.service';
import { saveAs } from 'file-saver-es';
import { ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-invio-dichiarazione-di-spesa',
  templateUrl: './invio-dichiarazione-di-spesa.component.html',
  styleUrls: ['./invio-dichiarazione-di-spesa.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class InvioDichiarazioneDiSpesaComponent implements OnInit, AfterViewChecked {

  idProgetto: number;
  idBandoLinea: number;
  idDocumentoIndex: number;
  idDichiarazioneDiSpesa: number;
  codiceProgetto: string;
  isPiuGreen: boolean;
  user: UserInfoSec;
  beneficiario: string;
  nomePdf: string;
  dimMaxFileFirmato: number;
  file: File;
  isConcluded: boolean;
  isIntegrativa: boolean;
  tipoInvioDS: string;
  todayDate: Date = new Date();
  isFinale: boolean;
  isBR50: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedConcludi: boolean = true;
  loadedInvia: boolean = true;
  loadedChiudiAttivita: boolean = true;
  loadedDownload: boolean = true;
  loadedRegola: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  @ViewChild('tabs', { static: false }) tabs;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private dichiarazioneDiSpesaService: DichiarazioneDiSpesaService,
    private sharedService: SharedService,
    private configService: ConfigService,
    private archivioFileService: ArchivioFileService,
    private datePipe: DatePipe,
    private dialog: MatDialog,
    private _adapter: DateAdapter<any>
  ) {
    this._adapter.setLocale('it');
  }

  ngOnInit(): void {

    // Servizio iniziale
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idDocumentoIndex = +params['idDocumentoIndex'];
      this.idDichiarazioneDiSpesa = +params['idDichiarazioneDiSpesa'];

      this.isIntegrativa = this.activatedRoute.snapshot.paramMap.get('integrativa') ? true : false;
      this.tipoInvioDS = this.activatedRoute.snapshot.paramMap.get('tipoInvio') ? this.activatedRoute.snapshot.paramMap.get('tipoInvio') : null;
      this.isFinale = this.activatedRoute.snapshot.paramMap.get('finale') ? true : false;
      this.isPiuGreen = this.activatedRoute.snapshot.paramMap.get('piuGreen') ? true : false;

      /* Tolto perchè forzava questo messaggio 
      if (this.tipoInvioDS === 'C') {
        this.showMessageWarning("Per l'invio, eseguire il download della dichiarazione di spesa, firmare il documento stampato e procedere con l'invio.<br>"
          + "È sempre possibile scaricare il file per poterlo stampare, firmare ed inviare, anche in un secondo momento dalla sezione " +
          "<span class='boldText'>Documenti di progetto</span>.");
      }
      */
      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
          this.loadedInizializza = false;
          this.subscribers.initDichiarazione = this.dichiarazioneDiSpesaService.inizializzaInvioDichiarazioneDiSpesa(this.idProgetto, this.idBandoLinea).subscribe(dataInizializzazione => {
            this.codiceProgetto = dataInizializzazione.codiceVisualizzatoProgetto;
            if (this.isFinale) {
              this.nomePdf = "Dichiarazione_finale_con_CFP_" + this.idDichiarazioneDiSpesa + "_";
              if (this.isPiuGreen) {
                this.nomePdf += this.codiceProgetto + "_";
              }
              this.nomePdf += this.datePipe.transform(this.todayDate, 'ddMMyyyy') + ".pdf";
            } else {
              this.nomePdf = "DichiarazioneDiSpesa_" + this.idDichiarazioneDiSpesa + "_" + this.datePipe.transform(this.todayDate, 'ddMMyyyy') + ".pdf";
            }
            this.dimMaxFileFirmato = dataInizializzazione.dimMaxFileFirmato;
            this.loadedInizializza = true;
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
        }
      });
      if (this.isFinale) {
        this.showMessageSuccess("La Comunicazione di fine progetto, contenente la Dichiarazione di spesa finale, è stata correttamente elaborata e predisposta per essere inviata");
      } else {
        this.showMessageSuccess("La dichiarazione di spesa è stata correttamente elaborata e predisposta per essere inviata.");
      }
    });


    //Servizio per regola br50
    this.loadedRegola = false;
    this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBandoLinea, "BR50").subscribe(res => {
      if (res != undefined && res != null) {
        this.isBR50 = res;
      }
      this.loadedRegola = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegola = true;
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_INVIO_DICH_SPESA;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  ngAfterViewChecked() {
    if (this.tabs) {
      this.tabs.realignInkBar();
    }
  }

  handleFileInput(file: File) {
    this.file = file;
  }

  download() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.nomePdf);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  onTabChange($event) {
    this.file = undefined;
  }

  inviaDichiarazioneSpesaFirmaDigitale() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.file.size > this.dimMaxFileFirmato) {
      this.showMessageError("La dimensione del file :" + this.file.size + " eccede il limite previsto di " + this.dimMaxFileFirmato);
      return;
    }
    if (!this.file.name.endsWith(".p7m") && !this.file.name.endsWith(".P7M")) {
      this.showMessageError("Il file deve avere estensione .p7m");
      return;
    }
    if (!this.file.name.startsWith(this.nomePdf)) {
      this.showMessageError("Il file firmato e da caricare deve essere: " + this.nomePdf + ".p7m");
      return;
    }
    this.loadedInvia = false;
    this.subscribers.salvaFile = this.dichiarazioneDiSpesaService.salvaFileFirmato(this.idDocumentoIndex, this.file).subscribe(data => {
      if (data) {
        this.dichiarazioneDiSpesaService.verificaFirmaDigitale(this.idDocumentoIndex);
        this.showMessageSuccess("Il documento è stato acquisito e sarà sottoposto a verifiche. L'esito dell'invio sarà consultabile a breve nella sezione Documenti di progetto.");
        this.isConcluded = true;
      } else {
        this.showMessageError("Errore nell'invio del documento firmato.");
      }
      this.loadedInvia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nell'invio del documento firmato.");
      this.loadedInvia = true;
    });
  }

  inviaDichiarazioneSpesaFirmaAutografa() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.file.size > this.dimMaxFileFirmato) {
      this.showMessageError("La dimensione del file :" + this.file.size + " eccede il limite previsto di " + this.dimMaxFileFirmato);
      return;
    }
    if (!this.file.name.endsWith(".pdf") && !this.file.name.endsWith(".pdf")) {
      this.showMessageError("Il file deve avere estensione .pdf");
      return;
    }
    if (!this.file.name.startsWith(this.nomePdf)) {
      this.showMessageError("Il file firmato e da caricare deve essere: " + this.nomePdf);
      return;
    }
    this.loadedInvia = false;
    this.subscribers.salvaFile = this.dichiarazioneDiSpesaService.salvaFileFirmaAutografa(this.idDocumentoIndex, this.file).subscribe(data => {
      if (data) {
        this.showMessageSuccess("Il documento è stato acquisito e sarà sottoposto a verifiche. L'esito dell'invio sarà consultabile a breve nella sezione Documenti di progetto.");
        this.isConcluded = true;
      } else {
        this.showMessageError("Errore nell'invio del documento firmato.");
      }
      this.loadedInvia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nell'invio del documento firmato.");
      this.loadedInvia = true;
    });
  }

  concludi() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedConcludi = false;
    this.subscribers.concludi = this.dichiarazioneDiSpesaService.salvaInvioCartaceo(this.idDocumentoIndex, "true").subscribe(data => {
      if (data) {
        this.showMessageSuccess("Attività conclusa correttamente.");
        this.isConcluded = true;
      } else {
        this.showMessageError("Errore nella conclusione dell'attività.");
      }
      this.loadedConcludi = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella conclusione dell'attività.");
      this.loadedConcludi = true;
    });
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_RENDICONTAZIONE).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
  }

  datiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  contoEconomico() {
    this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
      width: "90%",
      maxHeight: '90%',
      data: {
        idBandoLinea: this.idBandoLinea,
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

  resetMessageWarning() {
    this.messageWarning = null;
    this.isMessageWarningVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedInizializza || !this.loadedConcludi || !this.loadedInvia || !this.loadedChiudiAttivita || !this.loadedDownload) {
      return true;
    }
    return false;
  }




}
