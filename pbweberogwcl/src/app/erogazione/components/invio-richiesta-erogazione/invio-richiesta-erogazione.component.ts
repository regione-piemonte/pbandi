/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Erogazione2Service } from '../../services/erogazione2.service';
import { saveAs } from 'file-saver-es';
import { Constants } from 'src/app/core/commons/util/constants';
import { Rinuncia2Service } from 'src/app/rinuncia/services/rinuncia-2.service';

@Component({
  selector: 'app-invio-richiesta-erogazione',
  templateUrl: './invio-richiesta-erogazione.component.html',
  styleUrls: ['./invio-richiesta-erogazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class InvioRichiestaErogazioneComponent implements OnInit, AfterViewChecked {

  idBandoLinea: number;
  idProgetto: number;
  idDocumentoIndex: number;
  codiceProgetto: string;
  user: UserInfoSec;
  nomePdf: string;
  dimMaxFileFirmato: number;
  file: File;
  isConcluded: boolean;
  todayDate: Date = new Date();
  isInviato: boolean;

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

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  @ViewChild('tabs', { static: false }) tabs;

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private erogazione2Service: Erogazione2Service,
    private rinuncia2Service: Rinuncia2Service,
    private sharedService: SharedService,
    private configService: ConfigService,
    private archivioFileService: ArchivioFileService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBando'];
      this.idDocumentoIndex = +params['idDocumentoIndex'];
      if (this.activatedRoute.snapshot.paramMap.get('nomeFile')) {
        this.nomePdf = this.activatedRoute.snapshot.paramMap.get('nomeFile');
      }

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.loadedInizializza = false;
          this.subscribers.initDichiarazione = this.erogazione2Service.inizializzaErogazione(this.idProgetto).subscribe(dataInizializzazione => {
            this.codiceProgetto = dataInizializzazione.codiceVisualizzatoProgetto;
            this.dimMaxFileFirmato = dataInizializzazione.dimMaxFileFirmato;
            this.loadedInizializza = true;
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
        }
      });
      this.showMessageSuccess("La richiesta di erogazione è stata correttamente elaborata e predisposta per essere inviata.");
    });
  }

  ngAfterViewChecked() {
    this.tabs.realignInkBar();
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

  inviaRichiestaErogazione() {
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
    this.subscribers.salvaFile = this.rinuncia2Service.salvaFileFirmato(this.idDocumentoIndex, this.file).subscribe(data => {
      if (data) {
        this.rinuncia2Service.verificaFirmaDigitale(this.idDocumentoIndex);
        this.isInviato = true;
        this.showMessageSuccess("Il documento è stato acquisito e sarà sottoposto a verifiche. L'esito dell'invio sarà consultabile a breve nella sezione Documenti di progetto.");
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
    this.subscribers.concludi = this.rinuncia2Service.salvaInvioCartaceo(this.idDocumentoIndex, "true").subscribe(data => {
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
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_RICHIESTA_EROGAZIONE).subscribe(data => {
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
