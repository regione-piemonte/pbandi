/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { RelazioneTecnicaService } from '../../services/relazione-tecnica.service';
import { DocumentoIndexDTO } from 'src/app/documenti-progetto/commons/dto/documento-index-dto';
import { RelazioneTecnicaDTO } from '../../commons/relazione-tecnica-dto';

@Component({
  selector: 'app-relazione-tecnica',
  templateUrl: './relazione-tecnica.component.html',
  styleUrls: ['./relazione-tecnica.component.scss']
})
export class RelazioneTecnicaComponent implements OnInit {

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
  codiceProgetto: String;
  isFile: boolean = false;
  fileRelazione: File;
  fileRelazioneName: string;
  loadedCodiceProj: boolean = true;
  loadedSalvaRelazioneTec: boolean = true;
  idTipoRelazioneTecnica: number;

  relazioneTecnica: RelazioneTecnicaDTO;
  loadedRelTec: boolean = true;
  isMandaRelazione: boolean = true;
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private relazioneTecnicaService: RelazioneTecnicaService
  ) { }

  ngOnInit(): void {
    // Costanti da usare per distinguere i casi:
    // Constants.DESCR_BREVE_TASK_REL_TEC_I
    // Constants.DESCR_BREVE_TASK_REL_TEC_F
    // Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_REL_TEC_I
    // Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_REL_TEC_F
    // Distiguere i casi anche nel servizio del pulsante Torna alle attivita da svolgere


    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;
        //this.codiceProgetto = this.user.codFisc
        this.subscribers.router = this.activatedRoute.parent.params.subscribe(params => {
          this.idRouting = params.id;
          if (params.id == Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_REL_TEC_I) {
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
        if (this.relazioneTecnica.flagValidato == 'S') {
          this.showMessageSuccess("la precedente relazione tecnica è stata validata con successo!")
          this.isMandaRelazione = false;
        } else if (this.relazioneTecnica.flagValidato == 'N') {
          this.showMessageError("la precedente relazione tecnica è stata rifiutata con nota:" + this.relazioneTecnica.note);
          this.isMandaRelazione = true;
        } else {
          this.showMessageError("la precedente relazione tecnica non è ancora stata  controllata!");
          this.isMandaRelazione = true;
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


  handleFileInput(file: File) {

    this.fileRelazione = file;
    this.fileRelazioneName = this.fileRelazione.name;
    this.isFile = true;

  }
  setFileNullAllegati() {
    this.fileRelazione = null;
    this.fileRelazioneName = null;
    this.isFile = false;
  }

  conferma() {

    this.loadedSalvaRelazioneTec = false;
    this.subscribers.salvaRelazioneTecnica = this.relazioneTecnicaService.salvaRelazioneTecnica(this.fileRelazione, this.fileRelazioneName,
      this.idProgetto, this.idTipoRelazioneTecnica).subscribe(data => {
        if (data) {
          this.showMessageSuccess("Salvataggio avvenuto con successo!");
          this.loadedSalvaRelazioneTec = true;
          setTimeout(() => {
            this.tornaAAttivitaDaSvolgere();
          }, 3000);
        } else {
          this.loadedSalvaRelazioneTec = true;
          this.showMessageError("Errore durante il salvataggio della relazione tecnica!");
        }
      },
        err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedSalvaRelazioneTec = true;
          this.showMessageError("Errore durante il salvataggio della relazione tecnica!");
          setTimeout(() => {
            this.tornaAAttivitaDaSvolgere();
          }, 3000);
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
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, (this.isRelTecIntermedia == true) ? Constants.DESCR_BREVE_TASK_REL_TEC_I :
      Constants.DESCR_BREVE_TASK_REL_TEC_F).subscribe(data => {
        window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedChiudiAttivita = true;
        this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
        this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
      });
  }

  isLoading() {
    if (!this.loadedInizializza || !this.loadedCodiceProj || !this.loadedSalvaRelazioneTec || !this.loadedRelTec) {
      return true;
    }
    return false;

  }

}
