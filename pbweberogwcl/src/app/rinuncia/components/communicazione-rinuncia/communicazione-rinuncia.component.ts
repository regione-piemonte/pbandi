/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BeneficiarioSec } from 'src/app/core/commons/dto/beneficiario';
import { Component, OnInit } from '@angular/core';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { ActivatedRoute, Router } from "@angular/router";
import { FideiussioniService } from "../../../fideiussioni/services/fideiussioni.service";
import { InizializzazioneService } from "../../../shared/services/inizializzazione.service";
import { HandleExceptionService } from "../../../core/services/handle-exception.service";
import { UserService } from "../../../core/services/user.service";
import { DatePipe } from "@angular/common";
import { DateAdapter } from "@angular/material/core";
import { RinunciaService } from "../../services/rinuncia.service";
import { Constants } from "../../../core/commons/util/constants";
import { CodiceDescrizione } from "../../commons/dto/codice-descrizione";
import { DelegatoDTO } from "../../commons/dto/delegato-dto";
import { ConfigService } from "../../../core/services/config.service";
import { CreaComunicazioneRinunciaRequest } from "../../commons/dto/crea-comunicazione-rinuncia-request";
import { DichiarazioneRinuncia } from "../../commons/dto/dichiarazione-rinuncia";
import { ResponseCreaCommunicazioneRinuncia } from "../../commons/dto/response-crea-communicazione-rinuncia";
import { DataService } from "../../../shared/services/data.service";
import { SharedService } from "../../../shared/services/shared.service";
import { ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { Rinuncia2Service } from '../../services/rinuncia-2.service';
import { saveAs } from "file-saver-es";

@Component({
  selector: 'app-communicazione-rinuncia',
  templateUrl: './communicazione-rinuncia.component.html',
  styleUrls: ['./communicazione-rinuncia.component.scss']
})
export class CommunicazioneRinunciaComponent implements OnInit {
  idProgetto: number;
  idBando: number;
  codiceProgetto: string;
  user: UserInfoSec;
  hideAll: boolean;
  beneficiario: BeneficiarioSec;
  isBeneficiarioIsprivatoCittadino: boolean;
  isBR56: boolean;

  noElementsMessage: string = "Non ci sono elementi da visualizzare.";
  noElementsMessageVisible: boolean;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  hasValidationError: boolean;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedImporoDaRestituire: boolean;
  loadedReprLegale: boolean;
  loadedDelegati: boolean;
  loadedCrea: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedDownload: boolean = true;
  loadedBeneficiarioIsPrivatoCittadinio = true;
  loadedRegolaBR56: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  // MODELLI API REQUEST/RESPONSE
  raprLegali: Array<CodiceDescrizione>;
  delegati: Array<DelegatoDTO>;
  dtRinuncia: Date = new Date();
  motivazione: string;
  giorniRestituzione: number = 10;
  rapprLegaleSelected: CodiceDescrizione = { codice: "", descrizione: "" };
  isRappresenteLegaleSelected = false
  importoDaRestituire: number;
  delegatoSelected: DelegatoDTO;

  dichiarazioneRinuncia: DichiarazioneRinuncia;
  requestCreaCommRinuncia: CreaComunicazioneRinunciaRequest;
  responseCreaCommRinuncia: ResponseCreaCommunicazioneRinuncia;

  constructor(private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private rinunciaService: RinunciaService,
    private rinuncia2Service: Rinuncia2Service,
    private inizializzazioneService: InizializzazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private datePipe: DatePipe,
    private archivioFileService: ArchivioFileService,
    private dataService: DataService,
    private dialog: MatDialog,
    private sharedService: SharedService) {
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato;
        if (this.user.codiceRuolo) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['idProgetto'];
            this.idBando = +params['idBando'];
          });
          this.loadData();
        }
      }
    });
  }


  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_COMUN_RINUNCIA;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadData() {
    //Codice Progetto
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

    //Beneficiario is privato cittadino 
    this.loadedBeneficiarioIsPrivatoCittadinio = false;
    this.subscribers.codice = this.rinunciaService.getIsBeneficiarioPrivatoCittadino(this.idProgetto).subscribe(res => {
      if (res) {

        this.isBeneficiarioIsprivatoCittadino = res;

        if (this.isBeneficiarioIsprivatoCittadino) {

          this.rapprLegaleSelected.codice = this.beneficiario.idBeneficiario.toString()
          this.rapprLegaleSelected.descrizione = this.beneficiario.denominazione + " - " + this.beneficiario.codiceFiscale
          this.isRappresenteLegaleSelected = true
        }

      }
      this.loadedBeneficiarioIsPrivatoCittadinio = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBeneficiarioIsPrivatoCittadinio = true;
    });


    //Importo da restiture
    this.loadedImporoDaRestituire = false;
    this.subscribers.codice = this.rinunciaService.getImportoDaRestituire(this.idProgetto).subscribe(res => {
      if (res) {
        this.importoDaRestituire = res;
      }
      this.loadedImporoDaRestituire = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedImporoDaRestituire = true;
    });

    //Rappresentanti Legali
    this.loadedReprLegale = false;
    this.subscribers.codice = this.rinunciaService.getRappresentantiLegali(this.idProgetto).subscribe(res => {
      if (res) {
        this.raprLegali = res;
      }
      this.loadedReprLegale = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedReprLegale = true;
    });

    //Delegati
    this.loadedDelegati = false;
    this.subscribers.codice = this.rinunciaService.getDelegati(this.idProgetto).subscribe(res => {
      if (res) {
        this.delegati = res;
      }
      this.loadedDelegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedDelegati = true;
    });
    this.loadedRegolaBR56 = false;
    this.subscribers.regolBR56 = this.userService.isRegolaApplicabileForBandoLinea(this.idBando, "BR56").subscribe(res => {
      if (res) {
        this.isBR56 = res;
      }
      this.loadedRegolaBR56 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedRegolaBR56 = true;
    });
  }


  creaCommunicazioneRinuncia() {
    this.dichiarazioneRinuncia = {
      dtComunicazioneRinuncia: this.datePipe.transform(this.dtRinuncia, 'dd/MM/yyyy'),
      idRappresentanteLegale: parseInt(this.rapprLegaleSelected.codice),
      giorniRestituzione: this.giorniRestituzione, motivazione: this.motivazione,
      importoDaRestituire: this.importoDaRestituire
    };
    this.requestCreaCommRinuncia = {
      dichiarazioneRinuncia: this.dichiarazioneRinuncia, idProgetto: this.idProgetto,
      idDelegato: this.delegatoSelected ? this.delegatoSelected.idSoggetto : null
    };
    this.loadedCrea = true;
    this.subscribers.save = this.rinuncia2Service.creaComunicazioneRinuncia(this.requestCreaCommRinuncia).subscribe(res => {

      if (res) {
        if ("esito" in res) {
          if (res.esito === false) {
            this.showMessageError(res.msg);
          }
        } else {
          this.responseCreaCommRinuncia = res;
          this.dataService.setResponseCreaCommunicazioneRinuncia(res);
          if (this.responseCreaCommRinuncia.invioDigitale) {
            this.goToInviaDichiarazioneRinuncia();
          } else {
            this.showMessageSuccess(this.responseCreaCommRinuncia.message);
            this.hideAll = true;
          }
        }
      }
      this.loadedCrea = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di creazione della comunicazione.")
      this.loadedCrea = false;
    });
  }

  downloadComunicazioneRinuncia() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), this.responseCreaCommRinuncia.dichiarazioneRinuncia.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, this.responseCreaCommRinuncia.dichiarazioneRinuncia.fileName);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file.");
      this.loadedDownload = true;
    });
  }


  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedImporoDaRestituire || !this.loadedDelegati || this.loadedCrea
      || !this.loadedDownload || !this.loadedChiudiAttivita || !this.loadedRegolaBR56) {
      return true;
    }
    return false;
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

  goToContoEconomico() {
    this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
      width: "90%",
      maxHeight: '90%',
      data: {
        idBandoLinea: this.idBando,
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  goToAttivita() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto,
      Constants.DESCR_BREVE_TASK_COM_RINUNCIA).subscribe(data => {
        window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedChiudiAttivita = true;
        this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
        this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
      });
  }

  goToInviaDichiarazioneRinuncia() {
    this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_COMUNICAZIONE_RINUNCIA}/inviaDichRinuncia/${this.idProgetto}/${this.idBando}`
      + `${this.responseCreaCommRinuncia.dichiarazioneRinuncia.idDocumentoIndex};nomeFile=${this.responseCreaCommRinuncia.dichiarazioneRinuncia.fileName}`);
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg; this.isMessageSuccessVisible = true;
  }
  showMessageError(msg: string) {
    this.messageError = msg; this.isMessageErrorVisible = true;
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


  onSelectrRapLegale() {
    this.isRappresenteLegaleSelected = true

  }

  isButtonDisable() {
    console.log("Entrooooo??")
    return (this.dtRinuncia == undefined || this.motivazione == undefined ||
      this.motivazione == "" || this.giorniRestituzione == undefined ||
      this.importoDaRestituire == undefined || !this.isRappresenteLegaleSelected)
  }



}
