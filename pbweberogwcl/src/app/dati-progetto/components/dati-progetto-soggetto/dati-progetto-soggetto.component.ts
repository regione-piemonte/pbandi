/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

// getDettaglioSedeProgetto
// modificaSedeIntervento
// inserisciSedeInterventoProgetto
import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe, Location } from "@angular/common";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DateAdapter } from '@angular/material/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatRadioChange } from '@angular/material/radio';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { GenericSelectVo } from "../../../core/commons/vo/generic-select-vo";

import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";

import { DettaglioSoggettoProgettoDTO } from "../../commons/dto/dettaglio-soggetto-progetto-dto";
import { SedeProgettoDTO } from "../../commons/dto/sede-progetto-dto";
import { DatiProgettoService } from "../../services/dati-progetto.service";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { Comune } from "../../commons/models/comune";

import { NgForm, FormControl } from "@angular/forms";
import { EsitoOperazioni } from '../../commons/models/esito-operazioni';
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';

export const patternCap = /^([0-9]){5}$/g;

@Component({
  //selector: 'app-dati-progetto-soggetto',
  templateUrl: './dati-progetto-soggetto.component.html',
  styleUrls: ['./dati-progetto-soggetto.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DatiProgettoSoggettoComponent implements OnInit {
  //
  action: string; // inserisci, modifica, dettaglio
  title: string = 'Gestione dati del progetto';
  titleInserisci: string = 'Nuovo Soggetto';
  titleModifica: string = 'Modifica Soggetto';
  titleDettaglio: string = 'Dati Soggetto';
  matchUrlInserisci: string = 'nuovo-soggetto';
  matchUrlModifica: string = 'modifica-soggetto';
  matchUrlDettaglio: string = 'dettaglio-soggetto';
  //
  idProgetto: number;
  idBando: number;
  progrSoggettoProgetto: number;
  progrSoggettiCorrelati: number;
  idTipoSoggettoCorrelato: number;
  codiceFiscaleSoggetto: string;
  //idTipoSoggetto: number;
  codiceProgetto: string;
  dettaglioSoggettoProgetto: DettaglioSoggettoProgettoDTO;
  dettaglioSedeProgetto: SedeProgettoDTO;
  ruoli: CodiceDescrizione[];
  nazioni: CodiceDescrizione[];
  province: CodiceDescrizione[];
  comuni: CodiceDescrizione[];
  residenzaProvince: CodiceDescrizione[];
  residenzaComuni: CodiceDescrizione[];
  regioni: CodiceDescrizione[];
  //
  tipoOperazioneCurrent: string;
  tipoOperazioneFrom: string;
  isBenefPersonaFisica: boolean;

  user: UserInfoSec;

  isAbilitatoAccesso: boolean = true;

  //LOADED
  loadedCodiceProgetto: boolean;
  loadedDettaglioSoggettoProgetto: boolean = true;
  loadedDettaglioSedeProgetto: boolean = true;
  loadedRuoli: boolean;
  loadedNazioni: boolean;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;
  loadedComuniEsterni: boolean = true;
  loadedResidenzaProvince: boolean = true;
  loadedResidenzaComuni: boolean = true;
  loadedResidenzaComuniEsterni: boolean = true;
  loadedRegioni: boolean = true;
  loadedInserisci: boolean = true;
  loadedModifica: boolean = true;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];

  // Form - start
  //FORM

  fieldNome: string;
  fieldSesso: string = "M";
  fieldCognome: string;
  fielddtNascita: Date;
  fieldCodiceFiscale: string;
  fieldRuoloSelected: CodiceDescrizione;
  fieldNazioneSelected: CodiceDescrizione;
  fieldRegioneSelected: CodiceDescrizione;
  fieldProvinciaSelected: CodiceDescrizione;
  fieldComuneSelected: CodiceDescrizione;
  fieldResidenzaIndirizzo: string;
  fieldResidenzaNumeroIndirizzo: string;
  fieldResidenzaCap: string;
  fieldResidenzaNazioneSelected: CodiceDescrizione;
  fieldResidenzaRegioneSelected: CodiceDescrizione;
  fieldResidenzaProvinciaSelected: CodiceDescrizione;
  fieldResidenzaComuneSelected: CodiceDescrizione;
  //data: FormControl = new FormControl(); 
  data: Date; // .setHours(0, 0, 0, 0);
  codice: string = '';
  tipoRadio: string = '0';
  importo: number;

  @ViewChild('nascitaForm', { static: true }) nascitaForm: NgForm;
  @ViewChild("residenzaForm", { static: true }) residenzaForm: NgForm;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private datePipe: DatePipe,
    private datiProgettoService: DatiProgettoService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private configService: ConfigService
  ) {
  }


  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (this.router.url.match('/' + this.matchUrlInserisci + '/')) {
        this.action = 'inserisci';
      } else if (this.router.url.match('/' + this.matchUrlModifica + '/')) {
        this.action = 'modifica';
      } else if (this.router.url.match('/' + this.matchUrlDettaglio + '/')) {
        this.action = 'dettaglio';
      } else {
        console.error('router url not matched', this.router.url);
      }
      if (this.activatedRoute.snapshot.paramMap.get('benefPersonaFisica')) {
        this.isBenefPersonaFisica = true;
      }
      if (data) {
        this.user = data;
        this.subscribers.router = this.activatedRoute.params.subscribe(params => {
          this.idProgetto = +params['idProgetto'];
          this.idBando = +params['idBando'];
          if (this.action == 'inserisci' && params['idProgetto'] && (this.isIstruttore() || this.isBeneficiario())) {
            this.title = this.titleInserisci;
            this.loadData();
          } else if (this.action == 'modifica' && params['idProgetto']
            && ((params['progrSoggettoProgetto'] && params['progrSoggettiCorrelati'] && params['idTipoSoggettoCorrelato']) || params['progrSoggettoProgetto'])
            && (this.isIstruttore() || this.isBeneficiario())) {
            this.title = this.titleModifica;
            this.codiceFiscaleSoggetto = params['codiceFiscaleSoggetto'];
            if (params['progrSoggettoProgetto'] && params['progrSoggettiCorrelati'] && params['idTipoSoggettoCorrelato']) {
              this.progrSoggettoProgetto = +params['progrSoggettoProgetto'];
              this.progrSoggettiCorrelati = +params['progrSoggettiCorrelati'];
              this.idTipoSoggettoCorrelato = +params['idTipoSoggettoCorrelato'];
            } else if (params['progrSoggettoProgetto']) {
              this.progrSoggettoProgetto = +params['progrSoggettoProgetto'];
            }
            this.loadDettaglioSoggettoProgetto();
          } else if (this.action == 'dettaglio' && params['idProgetto']
            && ((params['progrSoggettoProgetto'] && params['progrSoggettiCorrelati'] && params['idTipoSoggettoCorrelato']) || params['progrSoggettoProgetto'])) {
            this.title = this.titleDettaglio;
            this.codiceFiscaleSoggetto = params['codiceFiscaleSoggetto'];
            if (params['progrSoggettoProgetto'] && params['progrSoggettiCorrelati'] && params['idTipoSoggettoCorrelato']) {
              this.progrSoggettoProgetto = +params['progrSoggettoProgetto'];
              this.progrSoggettiCorrelati = +params['progrSoggettiCorrelati'];
              this.idTipoSoggettoCorrelato = +params['idTipoSoggettoCorrelato'];
            } else if (params['progrSoggettoProgetto']) {
              this.progrSoggettoProgetto = +params['progrSoggettoProgetto'];
            }
            this.loadDettaglioSoggettoProgetto();
          } else {
            console.error("Non si dispongono dei permessi per visualizzare la pagina");
          }
        });
      }
    });
  }

  loadData() {
    this.loadCodiceProgetto();
    this.loadRuoli();
    this.loadNazioni();
  }

  loadDettaglioSoggettoProgetto() {
    this.loadedDettaglioSoggettoProgetto = false;
    this.subscribers.dettaglioSoggettoProgetto = this.datiProgettoService.getDettaglioSoggettoProgetto(this.user.idUtente, this.progrSoggettoProgetto, this.idTipoSoggettoCorrelato,
      this.progrSoggettiCorrelati, this.codiceFiscaleSoggetto).subscribe((res: DettaglioSoggettoProgettoDTO) => {
        if (res) {
          console.log('getDettaglioSoggettoProgetto', res);
          this.dettaglioSoggettoProgetto = res;
          this.postFormFields();
        }
        this.loadData();
        this.loadedDettaglioSoggettoProgetto = true;
        //this.updateFormFields(true);
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase del dettaglio del soggetto.");
        this.loadedDettaglioSoggettoProgetto = true;
      });
  }

  loadDettaglioSedeProgetto() {
    this.loadedDettaglioSedeProgetto = false;
    this.subscribers.dettaglioSedeProgetto = this.datiProgettoService.getDettaglioSedeProgetto(0, this.idProgetto).subscribe((res: SedeProgettoDTO) => {
      if (res) {
        console.log('getDettaglioSedeProgetto', res);
        this.dettaglioSedeProgetto = res;
        /** /
        //this.data.setValue(new Date('2021-06-03 00:00:00'));  // patchValue
        this.data = new Date('2021-06-03 00:00:00');
        /**/
      }
      this.loadedDettaglioSedeProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedDettaglioSedeProgetto = true;
    });
  }

  loadRuoli() {
    this.loadedRuoli = false;
    this.subscribers.tipiSoggettiCorrelati = this.datiProgettoService.getTipiSoggettiCorrelati().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.ruoli = res;
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idTipoSoggettoCorrelato) {
          this.fieldRuoloSelected = this.ruoli.find(r => r.codice === this.dettaglioSoggettoProgetto.idTipoSoggettoCorrelato);
          this.isAbilitatoAccesso = this.dettaglioSoggettoProgetto.accessoSistema;
          if ((this.isAbilitatoAccesso && this.fieldRuoloSelected) || this.action === "dettaglio") {
            this.nascitaForm.form.get('isAbilitatoAccesso').disable();
          }
        } else if (!this.isBenefPersonaFisica) {
          this.fieldRuoloSelected = this.ruoli.find(r => r.codice === "1");
        }
        //se in modifica il ruolo è 'delegato alla firma' oppure 'rappresentante legale' non è possibile modificarlo
        //se in modifica di un beneficiario persona fisica, il ruolo non è valorizzato e deve essre disabilitato
        if (this.action === 'modifica' && (this.isBenefPersonaFisica || (this.fieldRuoloSelected && (this.fieldRuoloSelected.codice === '1' || this.fieldRuoloSelected.codice === '21')))) {
          this.nascitaForm.form.get('fieldRuoloSelected').disable();
        }
        //se in modifica il ruolo è diverso da 'delegato alla firma' oppure 'rappresentante legale' è possibile modificarlo, 
        //ma non è possibile modificarlo nei ruoli 'delegato alla firma' oppure 'rappresentante legale'
        if (this.action === "modifica" && !this.isBenefPersonaFisica && this.fieldRuoloSelected && this.fieldRuoloSelected.codice !== '1' && this.fieldRuoloSelected.codice !== '21') {
          this.ruoli = this.ruoli.filter(r => r.codice !== '1' && r.codice !== '21');
        }
      }
      this.loadedRuoli = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedRuoli = true;
    });
  }

  loadNazioni() {
    this.loadedNazioni = false;
    this.subscribers.nazioni = this.datiProgettoService.getNazioni().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.nazioni = res;
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idNazione) {
          this.fieldNazioneSelected = this.nazioni.find(n => n.codice === this.dettaglioSoggettoProgetto.idNazione);

        } else {
          this.fieldNazioneSelected = this.nazioni.find(n => n.codice === "118");
        }
        if (this.fieldNazioneSelected) {
          this.onSelectFieldNazione();
        }
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idNazioneRes) {
          this.fieldResidenzaNazioneSelected = this.nazioni.find(n => n.codice === this.dettaglioSoggettoProgetto.idNazioneRes);
        } else {
          this.fieldResidenzaNazioneSelected = this.nazioni.find(n => n.codice === "118");
        }
        if (this.fieldResidenzaNazioneSelected) {
          this.onSelectFieldResidenzaNazione();
        }
      }
      this.loadedNazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      this.loadedNazioni = true;
    });
  }

  loadProvince() {
    this.loadedProvince = false;
    this.subscribers.province = this.datiProgettoService.getProvince().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.province = res;
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idProvincia) {
          this.fieldProvinciaSelected = this.province.find(p => p.codice === this.dettaglioSoggettoProgetto.idProvincia);
          if (this.fieldProvinciaSelected) {
            this.onSelectFieldProvincia();
          }
        }
      }
      this.loadedProvince = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province");
      this.loadedProvince = true;
    });
  }

  loadResidenzaProvince() {
    this.loadedResidenzaProvince = false;
    this.subscribers.residenzaProvince = this.datiProgettoService.getProvince(+this.fieldResidenzaRegioneSelected.codice).subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        this.residenzaProvince = res;
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idProvinciaRes) {
          this.fieldResidenzaProvinciaSelected = this.residenzaProvince.find(p => p.codice === this.dettaglioSoggettoProgetto.idProvinciaRes);
          if (this.fieldResidenzaProvinciaSelected) {
            this.onSelectFieldResidenzaProvincia();
          }
        }
      }
      this.loadedResidenzaProvince = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province di residenza.");
      this.loadedResidenzaProvince = true;
    });
  }

  loadComuni() {
    this.comuni = [] as CodiceDescrizione[];
    this.loadedComuni = false;
    if (this.fieldProvinciaSelected !== undefined) {
      this.subscribers.comuni = this.datiProgettoService.getComuni(+this.fieldProvinciaSelected.codice).subscribe((res: Comune[]) => {
        if (res) {
          this.comuni = res.map((row: Comune) => { return { codice: row.idComune.toString(), descrizione: row.descrizione } }) as CodiceDescrizione[];
          if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idComune) {
            this.fieldComuneSelected = this.comuni.find(c => c.codice === this.dettaglioSoggettoProgetto.idComune);
          }
        }
        this.loadedComuni = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei comuni.");
        this.loadedComuni = true;
      });
    }
  }

  loadComuniEsterni() {
    this.loadedComuniEsterni = false;
    this.subscribers.comuniEsterni = this.datiProgettoService.getComuniEsterni(+this.fieldNazioneSelected.codice).subscribe((data: CodiceDescrizione[]) => {
      if (data) {
        this.comuni = data;
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idComune) {
          this.fieldComuneSelected = this.comuni.find(c => c.codice === this.dettaglioSoggettoProgetto.idComune);
        }
      }
      this.loadedComuniEsterni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di carimcamento dei comuni.");
      this.loadedComuniEsterni = true;
    });
  }

  loadResidenzaComuni() {
    this.residenzaComuni = [] as CodiceDescrizione[];
    this.loadedResidenzaComuni = false;
    if (this.fieldResidenzaProvinciaSelected !== undefined) {
      this.subscribers.residenzaComuni = this.datiProgettoService.getComuni(+this.fieldResidenzaProvinciaSelected.codice).subscribe((res: Comune[]) => {
        if (res) {
          this.residenzaComuni = res.map((row: Comune) => { return { codice: row.idComune.toString(), descrizione: row.descrizione } }) as CodiceDescrizione[];
          if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idComuneRes) {
            this.fieldResidenzaComuneSelected = this.residenzaComuni.find(c => c.codice === this.dettaglioSoggettoProgetto.idComuneRes);
          }
        }
        this.loadedResidenzaComuni = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei comuni di residenza.");
        this.loadedResidenzaComuni = true;
      });
    }
  }

  loadResidenzaComuniEsterni() {
    this.loadedResidenzaComuniEsterni = false;
    this.subscribers.residenzaComuniEsterni = this.datiProgettoService.getComuniEsterni(+this.fieldResidenzaNazioneSelected.codice).subscribe((data: CodiceDescrizione[]) => {
      if (data) {
        this.residenzaComuni = data;
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idComuneRes) {
          this.fieldResidenzaComuneSelected = this.residenzaComuni.find(c => c.codice === this.dettaglioSoggettoProgetto.idComuneRes);
        }
      }
      this.loadedResidenzaComuniEsterni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni di residenza.");
      this.loadedResidenzaComuniEsterni = true;
    });
  }

  loadResidenzaRegioni() {
    this.loadedRegioni = false;
    this.subscribers.regioni = this.datiProgettoService.getRegioni().subscribe((data: CodiceDescrizione[]) => {
      if (data) {
        this.regioni = data;
        if (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idRegioneRes) {
          this.fieldResidenzaRegioneSelected = this.regioni.find(r => r.codice === this.dettaglioSoggettoProgetto.idRegioneRes);
        } else {
          this.fieldResidenzaRegioneSelected = this.regioni.find(r => r.codice === "1");
        }
        if (this.fieldResidenzaRegioneSelected) {
          this.onSelectFieldResidenzaRegione();
        }
      }
      this.loadedRegioni = true;
      //this.updateFormFields(preserveValue);
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regioni.");
      // this.onSelectFieldRegione(preserveValue);
      this.loadedRegioni = true;
    });
  }

  loadCodiceProgetto() {
    //LOAD CODICE PROGETTO
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe((res: string) => {
      if (res) {
        console.log('getCodiceProgetto', res);
        this.codiceProgetto = res;
        this.setupHeaderLabelsButtons();
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedCodiceProgetto = true;
    });
  }

  postFormFields() {
    //this.isPost = true;
    this.fieldNome = this.dettaglioSoggettoProgetto.nome;
    this.fieldSesso = this.dettaglioSoggettoProgetto.sesso;
    this.fieldCognome = this.dettaglioSoggettoProgetto.cognome;
    this.fielddtNascita = this.sharedService.parseDate(this.dettaglioSoggettoProgetto.dataNascita);
    this.fieldCodiceFiscale = this.dettaglioSoggettoProgetto.codiceFiscale;

    this.fieldResidenzaIndirizzo = this.dettaglioSoggettoProgetto.indirizzoRes;
    this.fieldResidenzaNumeroIndirizzo = this.dettaglioSoggettoProgetto.numCivicoRes;
    this.fieldResidenzaCap = this.dettaglioSoggettoProgetto.capRes;
  }

  getFormFields() {
    let response: DettaglioSoggettoProgettoDTO = {
      nome: this.fieldNome,
      sesso: this.fieldSesso,
      cognome: this.fieldCognome,
      dataNascita: ((this.fielddtNascita) ? this.datePipe.transform(this.fielddtNascita, 'dd/MM/yyyy') : undefined),
      codiceFiscale: this.fieldCodiceFiscale,
      idTipoSoggettoCorrelato: ((this.fieldRuoloSelected && this.fieldRuoloSelected.codice) ? this.fieldRuoloSelected.codice : undefined),
      idNazione: ((this.fieldNazioneSelected && this.fieldNazioneSelected.codice) ? this.fieldNazioneSelected.codice : undefined),
      idProvincia: ((this.fieldProvinciaSelected && this.fieldProvinciaSelected.codice) ? this.fieldProvinciaSelected.codice : undefined),
      idComune: ((this.fieldComuneSelected && this.fieldComuneSelected.codice) ? this.fieldComuneSelected.codice : undefined),
      indirizzoRes: this.fieldResidenzaIndirizzo,
      numCivicoRes: this.fieldResidenzaNumeroIndirizzo,
      capRes: this.fieldResidenzaCap,
      idNazioneRes: ((this.fieldResidenzaNazioneSelected && this.fieldResidenzaNazioneSelected.codice) ? this.fieldResidenzaNazioneSelected.codice : undefined),
      idRegioneRes: ((this.fieldResidenzaRegioneSelected && this.fieldResidenzaRegioneSelected.codice) ? this.fieldResidenzaRegioneSelected.codice : undefined),
      idProvinciaRes: ((this.fieldResidenzaProvinciaSelected && this.fieldResidenzaProvinciaSelected.codice) ? this.fieldResidenzaProvinciaSelected.codice : undefined),
      idComuneRes: ((this.fieldResidenzaComuneSelected && this.fieldResidenzaComuneSelected.codice) ? this.fieldResidenzaComuneSelected.codice : undefined),
      idIndirizzo: ((this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idIndirizzo) ? this.dettaglioSoggettoProgetto.idIndirizzo : undefined),
      idPersonaFisica: ((this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idPersonaFisica) ? this.dettaglioSoggettoProgetto.idPersonaFisica : undefined),
      idProgetto: this.idProgetto.toString(),
      idSoggetto: ((this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idSoggetto) ? this.dettaglioSoggettoProgetto.idSoggetto : undefined),
      //idTipoSoggettoCorrelatoAttuale = ruolo vecchio -> se il ruolo non è cambiato passo quello, altrimenti passo quello salvato in precedenza. in inserimento passo null 
      idTipoSoggettoCorrelatoAttuale: (this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.idTipoSoggettoCorrelato) ? this.dettaglioSoggettoProgetto.idTipoSoggettoCorrelato : undefined,
      dataFineValidita: ((this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.dataFineValidita) ? this.dettaglioSoggettoProgetto.dataFineValidita : undefined),
      progrSoggettiCorrelati: ((this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.progrSoggettiCorrelati) ? this.dettaglioSoggettoProgetto.progrSoggettiCorrelati : undefined),
      progrSoggettoProgetto: ((this.dettaglioSoggettoProgetto && this.dettaglioSoggettoProgetto.progrSoggettoProgetto) ? this.dettaglioSoggettoProgetto.progrSoggettoProgetto : undefined),
      accessoSistema: this.isAbilitatoAccesso,
      codiceRuolo: this.user.codiceRuolo
    };
    return response;
  }

  goToDatiProgettoSoggetto(isSuccess?: boolean) {
    let url = `drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_DATI_PROGETTO}/dati-progetto/${this.idProgetto}/${this.idBando}`;
    url += isSuccess ? ';success=true' : '';
    this.router.navigateByUrl(url);
  }

  onHeaderButtonsClick(btn: CodiceDescrizione) {
    switch (btn.codice) {
      case 'dati-progetto-e-attivita-pregresse':
        this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
          width: '90%',
          maxHeight: '95%',
          data: {
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      case 'conto-economico':
        this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
          width: "90%",
          maxHeight: '90%',
          data: {
            idBandoLinea: this.idBando,
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      default:
    }
  }

  onFlagSessoChange = (e: MatRadioChange) => {
    if (e.value === "1") {
      // ...
    } else {
      // ...
    }
  }

  onSelectFieldRuolo() {
    if (this.fieldRuoloSelected && this.fieldRuoloSelected.codice === "1") {
      this.isAbilitatoAccesso = true;
    } else if (this.action !== 'modifica') {
      this.isAbilitatoAccesso = false;
    }
  }

  onSelectFieldNazione = () => {
    this.fieldProvinciaSelected = null;
    this.fieldComuneSelected = null;
    this.province = null;
    this.comuni = null;
    if (this.fieldNazioneSelected && this.fieldNazioneSelected.codice !== "118") {
      this.loadComuniEsterni();
    } else {
      this.loadProvince();
    }
  }

  onSelectFieldProvincia = () => {
    this.fieldComuneSelected = null;
    this.comuni = null;
    this.loadComuni();
  }

  onSelectFieldResidenzaNazione = () => {
    this.fieldResidenzaRegioneSelected = null;
    this.fieldResidenzaProvinciaSelected = null;
    this.fieldResidenzaComuneSelected = null;
    this.regioni = null;
    this.residenzaProvince = null;
    this.residenzaComuni = null;
    if (this.fieldResidenzaNazioneSelected && this.fieldResidenzaNazioneSelected.codice !== "118") {
      this.loadResidenzaComuniEsterni();
    } else {
      this.loadResidenzaRegioni();
    }
  }

  onSelectFieldResidenzaRegione = () => {
    this.fieldResidenzaProvinciaSelected = null;
    this.fieldResidenzaComuneSelected = null;
    this.residenzaProvince = null;
    this.residenzaComuni = null;
    this.loadResidenzaProvince();

  }

  onSelectFieldResidenzaProvincia = () => {
    this.fieldResidenzaComuneSelected = null;
    this.residenzaComuni = null;
    this.loadResidenzaComuni();
  }

  isSalvaDisabled() {
    if (!this.fieldNome || this.fieldNome.length === 0
      || !this.fieldCognome || this.fieldCognome.length === 0
      || !this.fieldCodiceFiscale || this.fieldCodiceFiscale.length === 0
      || (!this.fieldRuoloSelected && !this.isBenefPersonaFisica)) {
      return true;
    }
    if (this.fieldRuoloSelected && (this.fieldRuoloSelected.codice === '1' || this.fieldRuoloSelected.codice === '21')) {
      if (!this.fieldSesso || this.fieldSesso.length === 0 || !this.fielddtNascita || !this.fieldNazioneSelected
        || (!this.fieldProvinciaSelected && this.fieldNazioneSelected.codice === '118')
        || !this.fieldComuneSelected || !this.fieldResidenzaIndirizzo
        || this.fieldResidenzaIndirizzo.length === 0 || !this.fieldResidenzaNumeroIndirizzo
        || this.fieldResidenzaNumeroIndirizzo.length === 0 || !this.fieldResidenzaCap
        || this.fieldResidenzaCap.length === 0 || !this.fieldResidenzaNazioneSelected
        || (!this.fieldResidenzaRegioneSelected && this.fieldResidenzaNazioneSelected.codice === '118')
        || (!this.fieldResidenzaProvinciaSelected && this.fieldResidenzaNazioneSelected.codice === '118')
        || !this.fieldResidenzaComuneSelected) {
        return true;
      }
    }
    return false;
  }

  isLoading() {
    if (!this.loadedCodiceProgetto || !this.loadedComuni || !this.loadedComuniEsterni || !this.loadedDettaglioSedeProgetto || !this.loadedDettaglioSoggettoProgetto
      || !this.loadedInserisci || !this.loadedModifica || !this.loadedNazioni || !this.loadedProvince || !this.loadedRegioni || !this.loadedResidenzaComuni
      || !this.loadedResidenzaComuniEsterni || !this.loadedResidenzaProvince || !this.loadedRuoli) {
      return true;
    }
    return false;
  }

  setupHeaderLabelsButtons() {
    this.headerLabels = [
      {
        codice: 'Beneficiario',
        descrizione: ((this.user && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) ? this.user.beneficiarioSelezionato.denominazione : '')
      },
      {
        codice: 'Codice progetto',
        descrizione: this.codiceProgetto
      }
    ];
    this.headerButtons = [
      {
        codice: 'dati-progetto-e-attivita-pregresse',
        descrizione: 'Dati progetto e attività pregresse'
      },
      {
        codice: 'conto-economico',
        descrizione: 'Conto economico'
      }
    ];
  }

  compareWithCodiceDescrizione(option: CodiceDescrizione, value: CodiceDescrizione) {
    return value && (option.codice === value.codice);
  }

  loadSalvaDatiProgettoDettaglio() {
    this.resetMessageSuccess();
    this.resetMessageError();
    if (this.fieldResidenzaCap && this.fieldResidenzaCap.length > 0) {
      if (this.sharedService.checkFieldFormPattern(this.residenzaForm, this.fieldResidenzaCap, 'fieldResidenzaCap', patternCap)) {
        this.showMessageError("Parametro non valido: CAP.");
        return;
      }
    }
    let request: DettaglioSoggettoProgettoDTO = this.getFormFields();
    if (this.action == 'inserisci') {
      this.loadedInserisci = false;
      request.idSoggetto = undefined;
      request.progrSoggettoProgetto = undefined;
      this.subscribers.salvaDettaglioSoggettoProgetto = this.datiProgettoService.salvaDettaglioSoggettoProgetto(this.user.idUtente, request).subscribe((data: EsitoOperazioni) => {
        if (data) {
          if (data.esito && data.esito) {
            this.goToDatiProgettoSoggetto(true);
          } else {
            this.showMessageError(data.msg);
          }
        }
        this.loadedInserisci = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err.error && err.error.msg ? err.error.msg : "Errore in fase di salvataggio.");
        this.loadedInserisci = true;
      });
    } else if (this.action == 'modifica') {
      this.loadedModifica = false;
      this.subscribers.salvaDettaglioSoggettoProgetto = this.datiProgettoService.salvaDettaglioSoggettoProgetto(this.user.idUtente, request).subscribe((data: EsitoOperazioni) => {
        if (data) {
          if (data.esito && data.esito) {
            this.goToDatiProgettoSoggetto(true);
          } else {
            this.showMessageError(data.msg);
          }
        }
        this.loadedModifica = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err.error && err.error.msg ? err.error.msg : "Errore in fase di salvataggio.");
        this.loadedModifica = true;
      });
    }
  }

  isBeneficiario() {
    return this.sharedService.isBeneficiario(this.user);
  }

  isIstruttore() {
    return this.sharedService.isIstruttore(this.user);
  }

  //MESSAGGI SUCCESS E ERROR - start
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
  //MESSAGGI SUCCESS E ERROR - end
}
