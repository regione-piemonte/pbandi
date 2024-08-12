import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { DocumentoIndexDTO } from 'src/app/documenti-progetto/commons/dto/documento-index-dto';
import { RelazioneTecnicaService } from 'src/app/relazione-tecnica/services/relazione-tecnica.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { saveAs } from 'file-saver-es';
import { NotaRelTecDialogComponent } from '../nota-rel-tec-dialog/nota-rel-tec-dialog.component';
import { RelazioneTecnicaDTO } from 'src/app/relazione-tecnica/commons/relazione-tecnica-dto';

@Component({
  selector: 'app-validazione-relazione-tecnica',
  templateUrl: './validazione-relazione-tecnica.component.html',
  styleUrls: ['./validazione-relazione-tecnica.component.scss']
})
export class ValidazioneRelazioneTecnicaComponent implements OnInit {

  subscribers: any = {};
  loadedInizializza: boolean = true;
  idRouting: any;
  isRelTecIntermedia: boolean = false;
  title: string;
  idProgetto: number;
  idBandoLinea: number;
  loadedChiudiAttivita: boolean = false;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  user: UserInfoSec;
  beneficiario: string;
  idTipoRelazioneTecnica: number;
  loadedCodiceProj: boolean = true;
  codiceProgetto: string;
  relazioneTecnica: RelazioneTecnicaDTO;
  loadedRelTec: boolean = true;
  isDocumentoValidato: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private relazioneTecnicaService: RelazioneTecnicaService,
    private archivioFileService: ArchivioFileService
    ) { }

  ngOnInit(): void {
    // Costanti da usare per distinguere i casi:
    // Constants.DESCR_BREVE_TASK_VAL_REL_TEC_I
    // Constants.DESCR_BREVE_TASK_VAL_REL_TEC_F
    // Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VAL_REL_TEC_I
    // Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VAL_REL_TEC_F
    // Distiguere i casi anche nel servizio del pulsante Torna alle attivita da svolgere

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
        //this.codiceProgetto = this.user.codFisc
        this.subscribers.router = this.activatedRoute.parent.params.subscribe(params => {
          this.idRouting = params.id;
          if (params.id == Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VAL_REL_TEC_I) {
            this.isRelTecIntermedia = true;
            this.title = "Intermedia"
            this.idTipoRelazioneTecnica = 1;
          } else {
            this.title = "Finale";
            this.idTipoRelazioneTecnica = 2;
          }

          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['idProgetto'];
            this.idBandoLinea = +params['idBandoLinea'];
            this.getCodiceProgetto();
            this.getRelazioneTecnicaBenef();
          });
        });
      }
    });
  }

  

  getCodiceProgetto() {

    this.loadedCodiceProj = false;
    this.subscribers.getCodiceProgetto = this.relazioneTecnicaService.getCodiceProgetto(this.idProgetto).subscribe(data => {
      if (data) {
        this.codiceProgetto = data;
        this.loadedCodiceProj = true;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCodiceProj = true;
      this.showMessageError("Errore recupero codice progetto.");
    });

  }

  getRelazioneTecnicaBenef() {

    this.loadedRelTec = false;
    this.subscribers.getRelazioneTecnicaBenef = this.relazioneTecnicaService.getRelazioneTecnica(this.idProgetto, this.idTipoRelazioneTecnica).subscribe(data => {
      if (data) {
        this.loadedRelTec = true;
        this.relazioneTecnica = data;
        if(this.relazioneTecnica.flagValidato!=null){
          this.isDocumentoValidato = true;
        }
      }
    },
      err => {
        this.loadedRelTec = true;
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedChiudiAttivita = true;
        this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      });
  }


  downloadAllegato(item:string , idDocumentoIndex: string) {
    //this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, item);
      }
      //  this.loadedDownload = true;
    });
  }

  conferma(flag: string){

    console.log("sono dentro il conferma documento di relazione tecnica!");

    console.log(flag);
    
    let dialogRef = this.dialog.open(NotaRelTecDialogComponent, {
      width: '40%',
      data: {
        idRelazioneTecnica: this.relazioneTecnica.idRelazioneTecnica, 
        idProgetto: this.idProgetto,
        flagConferma: flag, 
        tipoRelazioneTecnica: this.title
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.showMessageSuccess("Validazione confermata");
        setTimeout(() => {
          this.tornaAAttivitaDaSvolgere();
        }, 3000);
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

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
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

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, (this.isRelTecIntermedia == true) ? Constants.DESCR_BREVE_TASK_VAL_REL_TEC_I :
      Constants.DESCR_BREVE_TASK_VAL_REL_TEC_F).subscribe(data => {
        window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedChiudiAttivita = true;
        this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
        this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
      });
  }
  isLoading() {
    if (!this.loadedInizializza  || !this.loadedCodiceProj || !this.loadedRelTec) {
      return true;
    }
    return false;

  }

}
