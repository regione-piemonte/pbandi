import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ValidazioneService } from '../../services/validazione.service';
import { saveAs } from 'file-saver-es';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-checklist-chiudi-validazione-conferma',
  templateUrl: './checklist-chiudi-validazione-conferma.component.html',
  styleUrls: ['./checklist-chiudi-validazione-conferma.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class ChecklistChiudiValidazioneConfermaComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  idBandoLinea: number
  idDichiarazioneDiSpesa: number;
  idDocIndexCK: number;
  idReportDettaglioDocSpesa: number;
  nomeFileCK: string;
  nomeFileRD: string;
  user: UserInfoSec;
  beneficiario: string;
  codiceProgetto: string;
  idDocumentoIndex: number;
  nomeFile: string;
  idDocumentoIndexPiuGreen: number;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedChiudiAttivita: boolean = true;
  loadedInizializza: boolean;
  loadedDownload: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};
  isChiusa: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private validazioneService: ValidazioneService,
    private archivioFileService: ArchivioFileService,
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idDichiarazioneDiSpesa = +params['idDichiarazioneDiSpesa'];
      this.isChiusa = this.activatedRoute.snapshot.paramMap.get('isChiusa') === "true" ? true : false;
      console.log(this.isChiusa);
      if (!this.isChiusa) {
        this.idDocIndexCK = +this.activatedRoute.snapshot.paramMap.get('idDocIndexCK');
        let id: string = this.activatedRoute.snapshot.paramMap.get('idReportDettaglioDocSpesa');
        this.idReportDettaglioDocSpesa = id ? +id : null;
        this.nomeFileCK = this.activatedRoute.snapshot.paramMap.get('nomeFileCK');
        this.nomeFileRD = this.activatedRoute.snapshot.paramMap.get('nomeFileRD');
      }

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
          this.loadedInizializza = false;
          this.subscribers.inizializza = this.validazioneService.inizializzaValidazione(this.idDichiarazioneDiSpesa, this.idProgetto, this.idBandoLinea, this.user.codiceRuolo)
            .subscribe(data => {
              console.log(data);

              if (data) {
                if (!this.isChiusa) {
                  this.codiceProgetto = data.codiceVisualizzatoProgetto;
                  this.idDocumentoIndexPiuGreen = data.idDocumentoIndexPiuGreen;
                  this.idDocumentoIndex = data.idDocumentoIndex;
                  this.nomeFile = data.nomeFile;
                  this.showMessageSuccess("La validazione sulla dichiarazione di spesa è stata correttamente chiusa.");
                } else {
                  this.showMessageSuccess("La dichiarazione di spesa è stata correttamente chiusa d'ufficio.");
                }
              }
              this.loadedInizializza = true;
            }, err => {
              this.handleExceptionService.handleBlockingError(err);
            });
        }
      });
    });
  }

  downloadPdfDichiarazione() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaodDS = this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.nomeFile);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file.");
      this.loadedDownload = true;
    });
  }

  exportDettaglioDocSpesa() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaodDoc = this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idReportDettaglioDocSpesa.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.nomeFileRD);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file.");
      this.loadedDownload = true;
    });
  }

  pdfChecklistValidazione() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    this.subscribers.downlaodCK = this.archivioFileService.leggiFile(this.configService.getApiURL(), this.idDocIndexCK.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.nomeFileCK);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file.");
      this.loadedDownload = true;
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

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto,
      this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VALIDAZIONE ?
        Constants.DESCR_BREVE_TASK_VALIDAZIONE : Constants.DESCR_BREVE_TASK_VALIDAZIONE_INTEGRATIVA).subscribe(data => {
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

  isLoading() {
    if (!this.loadedChiudiAttivita || !this.loadedDownload || !this.loadedInizializza) {
      return true;
    }
    return false;
  }

}
