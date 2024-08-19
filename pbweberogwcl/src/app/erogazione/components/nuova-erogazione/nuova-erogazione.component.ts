/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe } from "@angular/common";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";

import { Erogazione } from "../../commons/models/erogazione";
import { DatiCalcolati } from "../../commons/models/dati-calcolati";
import { SoggettoTrasferimento } from "../../commons/models/soggetto-trasferimento";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { CodiceDescrizioneCausale } from "../../commons/models/codice-descrizione-causale";
import { EsitoErogazioneDTO } from "../../commons/dto/esito-erogazione-dto";
import { ModalitaAgevolazioneErogazioneDTO } from "../../commons/dto/modalita-agevolazione-erogazione-dto";
import { CausaleErogazioneDTO } from "../../commons/dto/causale-erogazione-dto";
import { ControllaDatiRequest } from "../../commons/requests/controlla-dati-request";
import { EsitoControllaDati } from "../../commons/models/esito-controlla-dati";
import { GetDatiCalcolatiRequest } from "../../commons/requests/get-dati-calcolati-request";
import { ModificaErogazioneRequest } from "../../commons/requests/modifica-erogazione-request";
import { SalvaErogazioneRequest } from "../../commons/requests/salva-erogazione-request";
import { ErogazioneService } from "../../services/erogazione.service";

import { NgForm, Validators } from "@angular/forms";
import { EsitoRichiestaErogazioneDTO } from "../../commons/dto/esito-richiesta-erogazione-dto";
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';

@Component({
  selector: 'app-nuova-erogazione',
  templateUrl: './nuova-erogazione.component.html',
  styleUrls: ['./nuova-erogazione.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovaErogazioneComponent implements OnInit {
  //
  action: string; // inserisci, modifica, dettaglio
  title: string;
  titleInserisci: string = 'Nuova Erogazione';
  titleModifica: string = 'Modifica Erogazione';
  titleDettaglio: string = 'Dettaglio Erogazione';
  matchUrlInserisci: string = 'nuova-erogazione';
  matchUrlModifica: string = 'modifica-erogazione';
  matchUrlDettaglio: string = 'dettaglio-erogazione';
  idProgetto: number;
  idBandoLinea: number;
  codiceProgetto: string;
  idErogazione: number;
  hideForm: boolean;
  modalitaAgevolazioni: ModalitaAgevolazioneErogazioneDTO[];
  modalitaAgevolazione: ModalitaAgevolazioneErogazioneDTO;
  causaleErogazione: CausaleErogazioneDTO;
  inputNumberType: string = 'float';
  user: UserInfoSec;

  //LOADED
  loadedCodiceProgetto: boolean;
  //loadedBeneficiari: boolean;
  loadedCausali: boolean;
  loadedModalitaAgevolazioneContoEconomico: boolean;
  loadedModalitaErogazione: boolean;
  loadedCodiciDirezione: boolean;
  loadedDatiRiepilogoRichiestaErogazione: boolean;
  loadedErogazioni: boolean;
  loadedDettaglioErogazioni: boolean;
  loadedEliminaErogazioni: boolean;
  loadedCompileFormDettaglioErogazione: boolean;
  loadedRiepilogoRichiestaErogazione: boolean;
  loadedDatiCalcolati: boolean;
  loadedErogazioniDatiCalcolati: boolean;
  loadedCompileModalitaAgevolazioneErogazione: boolean;
  loadedControllaDatiOnSelectCausaleErogazione: boolean;
  loadedRicerca: boolean;
  //loadedElimina: boolean = true;
  //old
  loadedBeneficiari: boolean;
  //loadedCausali: boolean; // Duplicated
  //loadedErogazioni: boolean; // Duplicated
  loadedInserisci: boolean;
  loadedModifica: boolean;
  loadedDatiRiepilogo: boolean;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  erogazioneMessageWarning: string;
  isErogazioneMessageWarningVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  //HEADER LABELS BUTTONS
  headerLabels: CodiceDescrizione[];
  headerButtons: CodiceDescrizione[];

  data: Date; // .setHours(0, 0, 0, 0);

  @ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;
  @ViewChild("ricercaForm2", { static: true }) ricercaForm2: NgForm;
  criteriRicercaOpened: boolean = true;
  beneficiari: Array<SoggettoTrasferimento>;
  causali: CodiceDescrizioneCausale[];  // CodiceDescrizione[]
  modalitaAgevolazioneContoEconomico: CodiceDescrizione[];
  modalitaErogazione: CodiceDescrizione[];
  codiciDirezione: CodiceDescrizione[];
  datiRiepilogoRichiestaErogazione: EsitoRichiestaErogazioneDTO;
  erogazione: any; // EsitoErogazioneDTO ErogazioneItem?
  dettaglioErogazione: Erogazione;
  datiCalcolati: DatiCalcolati;
  beneficiarioSelected: SoggettoTrasferimento;
  causaleSelected: CodiceDescrizione;
  isCausaleSelectedError: boolean;
  percentualeErogazioneIterFinanziario: number;
  importoErogazioneFinanziario: number;
  modalitaAgevolazioneContoEconomicoSelected: CodiceDescrizione;
  modalitaErogazioneSelected: CodiceDescrizione;
  codiciDirezioneSelected: CodiceDescrizione;
  dataContabile: Date;
  numeroRiferimento: string;
  noteErogazione: string;
  dataDal: Date;  // .setHours(0, 0, 0, 0);
  dataAl: Date;
  codiceTrasferimento: string;
  tipo: string = '0';
  disabilitaNuovaErogazione: boolean;
  // Form - end
  esitoRichiestaErogazione: EsitoRichiestaErogazioneDTO;
  dataRequest: SalvaErogazioneRequest | ModificaErogazioneRequest;

  //TABLE
  dataSource: any;  //ElencoProgettiProposteDatasource;
  displayedColumns: string[] = ['attivita', 'beneficiario', 'codiceprogetto', 'contributopubblico', 'cofinanziamento', 'toterogato', 'totpagamenti', 'totfideiussioni', 'spesacertificabile', 'azioni'];

  cols: number;
  rowHeight: any;

  //FORM FIELDS
  preventCalculation: boolean;
  fieldPercentualeErogazioneEffettiva: number;
  fieldPercentualeErogazioneEffettivaFormatted: string;
  fieldImportoErogazioneEffettivo: number;
  fieldImportoErogazioneEffettivoFormatted: string;
  idModalitaAgevolazione: number = null;
  descModalitaAgevolazione: String;
  loadedModalitaAgevolazione: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private datePipe: DatePipe,
    private erogazioneService: ErogazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private configService: ConfigService
  ) { }

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
        //this.router.navigate(['/error'], { queryParams: { message: MessageRestError.GENERIC }, skipLocationChange: true });
      }
      if (data) {
        this.user = data;
        if (this.user.idIride && this.user.codiceRuolo && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.denominazione) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            if (this.action == 'inserisci' && params['id']) { // this.disabilitaNuovaErogazione
              this.title = this.titleInserisci;
              //  this.tipoOperazioneCurrent = 'inserisci'; // Test
              this.idProgetto = +params['id'];
              this.idBandoLinea = +params['idBando'];
              //  console.log('NuovaErogazione', this.tipoOperazioneCurrent, this.idProgetto);
              this.loadedControllaDatiOnSelectCausaleErogazione = true;
              this.loadDati();
              this.loadErogazione();
              //this.loadErogazioneForm();
            } else if (this.action == 'modifica' && params['id'] && params['id2'] && (this.user.codiceRuolo == 'ADG-IST-MASTER' || this.user.codiceRuolo == 'OI-IST-MASTER')) {
              this.title = this.titleModifica;
              //
              //  this.tipoOperazioneCurrent = 'modifica'; // Test
              this.idProgetto = +params['id'];
              this.idBandoLinea = +params['idBando'];
              this.idErogazione = +params['id2'];
              this.idModalitaAgevolazione = +params['idModalitaAgevolazione']
              this.loadedControllaDatiOnSelectCausaleErogazione = true;
              this.loadDati();
              //this.loadDatiRiepilogoRichiestaErogazione();
              this.loadErogazione();
              this.loadDettaglioErogazione();
              //this.loadErogazioneForm();
            } else if (this.action == 'dettaglio' && params['id'] && params['id2']) {
              this.title = this.titleDettaglio;
              this.idProgetto = +params['id'];
              this.idBandoLinea = +params['idBando'];
              this.idErogazione = +params['id2'];
              this.idModalitaAgevolazione = +params['idModalitaAgevolazione']
              console.log("@@@@ dentro nuova erogazione" + this.idModalitaAgevolazione);

              //  console.log('NuovaErogazione', this.tipoOperazioneCurrent, this.idProgetto);
              this.loadedControllaDatiOnSelectCausaleErogazione = true;
              this.loadDati();
              //this.loadDatiRiepilogoRichiestaErogazione();
              this.loadErogazione();
              this.loadDettaglioErogazione();
              //this.loadErogazioneForm();
            } else {

            }
            //this.loadData();
          });
        }
      }
    });
  }

  loadDati() {
    this.loadData();
  }

  loadData() {
    console.log('this.erogazioneService.rootUrl', this.erogazioneService.rootUrl);
    //LOAD CODICE PROGETTO



    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
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
    //LOAD CAUSALI EROGAZIONI
    this.loadedCausali = false;
    this.subscribers.causali = this.erogazioneService.getCausaliErogazioni(this.idProgetto).subscribe((res: CodiceDescrizioneCausale[]) => {
      if (res) {
        console.log('getCausaliErogazioni', res);
        this.causali = res;
        //this.causaleSelected = this.causali[0];
      }
      this.loadedCausali = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedCausali = true;
    });
    //LOAD ALL MODALITA AGEVOLAZIONE CONTO ECONOMICO
    this.loadedModalitaAgevolazioneContoEconomico = false;
    this.subscribers.modalitaAgevolazioneContoEconomico = this.erogazioneService.getAllModalitaAgevolazioneContoEconomico(this.idProgetto).subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        console.log('getAllModalitaAgevolazioneContoEconomico', res);
        this.modalitaAgevolazioneContoEconomico = res;
        this.disabilitaNuovaErogazione = !res || !res.length;

        // LOAD MODALITA AGEVOLAZIONE DA VISUALIZZARE
        if (this.idModalitaAgevolazione != null) {
          this.loadedModalitaAgevolazione = false
          this.subscribers.getModalitaAgevolazioneDaVisualizzare = this.erogazioneService.getModalitaAgevolazioneDaVisualizzare(this.idModalitaAgevolazione).subscribe(data => {
            if (data) {
              this.descModalitaAgevolazione = data.messaggio;
              this.loadedModalitaAgevolazione = true;
            }
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError(err.message);
            this.loadedModalitaAgevolazione = true;
          })
        }

        if (this.disabilitaNuovaErogazione) {
          this.isMessageWarningVisible = this.disabilitaNuovaErogazione;
          this.messageWarning = "Per il conto economico del progetto {idProgetto} non risultano ancora associate le modalità di agevolazione previste. <br /><span>Effettuare una rimodulazione del conto economico oppure contattare l'assistenza prima di procedere con un'erogazione.".replace('{idProgetto}', this.idProgetto.toString());
        }
      }
      this.loadedModalitaAgevolazioneContoEconomico = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedModalitaAgevolazioneContoEconomico = true;
    });
    //LOAD MODALITA EROGAZIONE
    this.loadedModalitaErogazione = false;
    this.subscribers.modalitaErogazione = this.erogazioneService.getAllModalitaErogazione().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        console.log('getAllModalitaErogazione', res);
        this.modalitaErogazione = res;
      }
      this.loadedModalitaErogazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedModalitaErogazione = true;
    });
    //LOAD CODICI DIREZIONE
    this.loadedCodiciDirezione = false;
    this.subscribers.codiciDirezione = this.erogazioneService.getAllCodiciDirezione().subscribe((res: CodiceDescrizione[]) => {
      if (res) {
        console.log('getAllCodiciDirezione', res);
        this.codiciDirezione = res;
      }
      this.loadedCodiciDirezione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedCodiciDirezione = true;
    });


  }

  loadErogazione() {
    //LOAD EROGAZIONI
    this.loadedErogazioni = false;
    let request: ErogazioneService.GetErogazioneParams = {
      idU: this.user.idUtente,
      idSoggettoBeneficiario: this.user.beneficiarioSelezionato.idBeneficiario,
      idProgetto: this.idProgetto
    };
    this.subscribers.erogazione = this.erogazioneService.getErogazione(request).subscribe((res: EsitoErogazioneDTO) => {
      if (res) {
        console.log('getErogazione', res);
        this.erogazione = res;
        this.loadDatiCalcolati();
      }
      this.loadedErogazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedErogazioni = true;
    });
  }

  loadDatiCalcolati() {
    this.loadedDatiCalcolati = false;
    let datiCalcolatiRequest: GetDatiCalcolatiRequest = {
      erogazioneDTO: this.erogazione.erogazione,
      idProgetto: this.idProgetto
    };
    console.log('datiCalcolatiRequest', datiCalcolatiRequest);  // erogazioneDTO: ErogazioneDTO (riferimento circolare in ErogazioneDTO)
    this.subscribers.datiCalcolati = this.erogazioneService.getDatiCalcolati(datiCalcolatiRequest).subscribe((res: DatiCalcolati) => {
      if (res) {
        console.log('getDatiCalcolati', res);
        this.datiCalcolati = res;
        // this.datiCalcolati.importoTotaleRichiesto
        this.compileModalitaAgevolazioneErogazione();
      }
      this.loadedDatiCalcolati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedDatiCalcolati = true;
    });
  }

  loadDettaglioErogazione() {
    //LOAD DETTAGLIO EROGAZIONE
    this.loadedDettaglioErogazioni = false;
    let dettaglioErogazioneParams: ErogazioneService.GetDettaglioErogazioneParams = {
      idProgetto: this.idProgetto,
      idErogazione: this.idErogazione
    };
    this.subscribers.erogazione = this.erogazioneService.getDettaglioErogazione(dettaglioErogazioneParams).subscribe((res: Erogazione) => {
      if (res) {
        console.log('getDettaglioErogazione', res);
        this.dettaglioErogazione = res;
        this.compileFormDettaglioErogazione();
      }
      this.loadedDettaglioErogazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError(err.message);
      this.loadedDettaglioErogazioni = true;
    });
  }

  compileFormDettaglioErogazione() {
    this.loadedCompileFormDettaglioErogazione = false;
    this.causaleSelected = ((this.dettaglioErogazione && this.dettaglioErogazione.codCausaleErogazione && this.dettaglioErogazione.descCausaleErogazione) ? {
      codice: this.dettaglioErogazione.codCausaleErogazione,
      descrizione: this.dettaglioErogazione.descCausaleErogazione
    } : undefined);
    this.modalitaAgevolazioneContoEconomicoSelected = ((this.dettaglioErogazione && this.dettaglioErogazione.codModalitaAgevolazione && this.dettaglioErogazione.descModalitaAgevolazione) ? {
      codice: this.dettaglioErogazione.codModalitaAgevolazione,
      descrizione: this.dettaglioErogazione.descModalitaAgevolazione
    } : undefined);
    this.modalitaErogazioneSelected = ((this.dettaglioErogazione && this.dettaglioErogazione.codModalitaErogazione && this.dettaglioErogazione.descModalitaErogazione) ? {
      codice: this.dettaglioErogazione.codModalitaErogazione,
      descrizione: this.dettaglioErogazione.descModalitaErogazione
    } : undefined);
    this.codiciDirezioneSelected = this.dettaglioErogazione && this.dettaglioErogazione.codTipoDirezione ?
      this.codiciDirezione.find(c => c.codice === this.dettaglioErogazione.codTipoDirezione) : null;
    this.percentualeErogazioneIterFinanziario = ((this.dettaglioErogazione && this.dettaglioErogazione.percentualeErogazioneIterFinanziario !== undefined) ? this.dettaglioErogazione.percentualeErogazioneIterFinanziario : undefined);
    this.importoErogazioneFinanziario = ((this.dettaglioErogazione && this.dettaglioErogazione.importoErogazioneDaIterFinanziario !== undefined) ? this.dettaglioErogazione.importoErogazioneDaIterFinanziario : undefined);
    this.fieldPercentualeErogazioneEffettiva = ((this.dettaglioErogazione && this.dettaglioErogazione.percentualeErogazioneEffettiva !== undefined) ? this.dettaglioErogazione.percentualeErogazioneEffettiva : undefined);
    this.fieldImportoErogazioneEffettivo = ((this.dettaglioErogazione && this.dettaglioErogazione.importoErogazioneEffettiva !== undefined) ? this.dettaglioErogazione.importoErogazioneEffettiva : undefined);
    this.dataContabile = ((this.dettaglioErogazione && this.dettaglioErogazione.dataContabile !== undefined) ? this.sharedService.parseDate(this.dettaglioErogazione.dataContabile) : undefined);
    this.numeroRiferimento = ((this.dettaglioErogazione && this.dettaglioErogazione.numero !== undefined) ? this.dettaglioErogazione.numero : undefined);
    this.noteErogazione = ((this.dettaglioErogazione && this.dettaglioErogazione.note !== undefined) ? this.dettaglioErogazione.note : undefined);
    if (this.fieldImportoErogazioneEffettivo && !this.fieldPercentualeErogazioneEffettiva) {
      this.onImportoErogazioneEffettivoChange(null);
    }
    this.loadedCompileFormDettaglioErogazione = true;
  }

  compileModalitaAgevolazioneErogazione() {
    this.loadedCompileModalitaAgevolazioneErogazione = false;

    console.log('test f', this.erogazione.erogazione.fideiussioni);
    if (this.action == 'modifica') {
      if (!this.disabilitaNuovaErogazione) {
        this.disabilitaNuovaErogazione = !this.erogazione.isRegolaAttiva;
        if (this.disabilitaNuovaErogazione) {
          this.isMessageWarningVisible = this.disabilitaNuovaErogazione;
          this.messageWarning = this.erogazione.messages;
        }
      }
    }
    this.loadedCompileModalitaAgevolazioneErogazione = true;
  }

  setFormData = () => {
    console.log('setFormData', this.erogazione);

    this.fieldPercentualeErogazioneEffettiva = 0;
    this.fieldPercentualeErogazioneEffettivaFormatted = '0';
    this.fieldImportoErogazioneEffettivo = 0;
    this.fieldImportoErogazioneEffettivoFormatted = '0';
    this.causaleSelected = {
      codice: '',
      descrizione: ''
    };
    this.modalitaAgevolazioneContoEconomicoSelected = {
      codice: '',
      descrizione: ''
    };
    this.modalitaErogazioneSelected = {
      codice: '',
      descrizione: ''
    };
    this.codiciDirezioneSelected = {
      codice: '',
      descrizione: ''
    };
  }

  setFormDataRequired() {
    if (this.erogazione.isRegolaAttiva) {
      this.ricercaForm.form.get('noteErogazione').setValidators([Validators.required])
      this.ricercaForm.form.get('noteErogazione').updateValueAndValidity();
    } else {
      this.ricercaForm.form.get('noteErogazione').setValidators([])
      this.ricercaForm.form.get('noteErogazione').updateValueAndValidity();
    }
  }

  applyFormData() {
    console.log('applyFormData');
    // ttt
    this.modalitaAgevolazione;
    this.causaleErogazione;
    this.causaleSelected;
    this.modalitaAgevolazioneContoEconomicoSelected;
    this.modalitaErogazioneSelected;
    this.codiciDirezioneSelected;
    this.percentualeErogazioneIterFinanziario;
    this.importoErogazioneFinanziario;
    this.fieldPercentualeErogazioneEffettiva;
    this.fieldImportoErogazioneEffettivo;
    this.dataContabile;
    this.noteErogazione;
  }

  disabledFormDataSubmit() {
    return (
      this.isCausaleSelectedError ||
      (!this.fieldImportoErogazioneEffettivo && this.fieldImportoErogazioneEffettivo !== 0) ||
      !this.causaleSelected ||
      !this.modalitaAgevolazioneContoEconomicoSelected ||
      !this.modalitaErogazioneSelected ||
      !this.codiciDirezioneSelected ||
      !this.dataContabile ||
      !this.numeroRiferimento
    );
  }

  // Form - mock - end

  onTorna() {
    this.goToErogazione();
  }

  goToErogazione() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_EROGAZIONE + "/erogazione", this.idProgetto, this.idBandoLinea]);
  }

  isLoading() {
    if (
      !this.loadedControllaDatiOnSelectCausaleErogazione ||
      !this.loadedCodiceProgetto || // !this.loadedBeneficiari || !this.loadedCausali  ||
      (
        (
          this.action == 'inserisci'
        ) &&
        //!this.loadedDatiRiepilogoRichiestaErogazione &&
        !this.loadedErogazioni ||
        !this.loadedDatiCalcolati ||
        !this.loadedCompileModalitaAgevolazioneErogazione
        //!this.loadedRiepilogoRichiestaErogazione
      ) ||
      (
        (
          this.action == 'modifica' ||
          this.action == 'dettaglio'
        ) && (
          //!this.loadedDatiRiepilogoRichiestaErogazione &&
          !this.loadedErogazioni ||
          !this.loadedDatiCalcolati ||
          !this.loadedCompileModalitaAgevolazioneErogazione ||
          !this.loadedDettaglioErogazioni ||
          !this.loadedCompileFormDettaglioErogazione //||
          //!this.loadedRiepilogoRichiestaErogazione
        )
        //!this.loadedRiepilogoRichiestaErogazione
      )
    ) {
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

  extractCodiceDescrizione(row) {
    let value: CodiceDescrizione = undefined;
    if (row.codice !== undefined || row.descrizione !== undefined) {
      value = {
        codice: row.codice,
        descrizione: row.descrizione
      };
    }
    return value;
  }

  getByField(rows: any[], field: any, value: string) {
    return rows.find((obj: any, index: number) => {
      return obj[field] === value;
    });
  }

  onSelectCausale() {
    this.resetMessageError();
    this.loadControllaDatiOnSelectCausaleErogazione();
  }

  loadControllaDatiOnSelectCausaleErogazione() {
    this.ricercaForm.controls['causale'].setErrors(null);
    let e: EsitoErogazioneDTO;
    this.loadedControllaDatiOnSelectCausaleErogazione = false;
    let percErogazione: number;
    let percLimite: number;
    if (this.causaleSelected) {
      let causale: CodiceDescrizioneCausale = this.getByField(this.causali, 'codice', this.causaleSelected.codice);
      if (causale) {
        percErogazione = causale.percErogazione;
        percLimite = causale.percLimite;
      }
    }
    let controllaDatiRequest: ControllaDatiRequest = {
      codCausaleErogSel: this.causaleSelected.codice,
      percErogazione: percErogazione, // this.dettaglioErogazione.percErogazione
      percLimite: percLimite, // this.dettaglioErogazione.percLimite
      importoCalcolato: this.erogazione.erogazione.spesaProgetto.importoAmmessoContributo,
      importoErogazioneEffettiva: this.fieldImportoErogazioneEffettivo, // this.erogazione.importoErogazioneEffettiva
      importoResiduoSpettante: this.erogazione.importoResiduoSpettante  // this.erogazione.importoResiduoSpettante
    };
    this.subscribers.controllaDatiOnSelectCausaleErogazione = this.erogazioneService.controllaDatiOnSelectCausaleErogazione(controllaDatiRequest).subscribe((res: EsitoControllaDati) => {
      if (res) {
        console.log('controllaDatiOnSelectCausaleErogazione', res);
        if (res.esito) {
          this.isCausaleSelectedError = false;
        } else {
          this.ricercaForm.controls['causale'].setErrors({ error: 'isCausaleSelectedError' });
          this.isCausaleSelectedError = true;
          this.resetMessageError();
          this.isMessageErrorVisible = true;
          this.messageError = res.message;
        }
      }
      this.loadedControllaDatiOnSelectCausaleErogazione = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.ricercaForm.controls['causale'].setErrors({ error: 'isCausaleSelectedError' });
      this.isCausaleSelectedError = true;
      this.resetMessageError();
      this.isMessageErrorVisible = true;
      this.messageError = err.message;
      this.loadedControllaDatiOnSelectCausaleErogazione = true;
    });
  }

  onSelectModalitaAgevolazioneContoEconomico() {

  }

  onSelectModalitaErogazione() {

  }

  onSelectCodiciDirezione() {

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
            idBandoLinea: this.idBandoLinea,
            idProgetto: this.idProgetto,
            apiURL: this.configService.getApiURL()
          }
        });
        break;
      default:
    }
  }

  onPercentualeErogazioneEffettivaChange = (e: any) => {
    if (this.erogazione && this.erogazione.erogazione) {
      let tot: number = this.erogazione.erogazione.importoTotaleResiduo + this.erogazione.erogazione.importoTotaleErogato;
      let value: number = +(this.fieldPercentualeErogazioneEffettiva);
      this.fieldImportoErogazioneEffettivo = +(((value * tot) / 100).toFixed(2));
    }
  }

  onPercentualeErogazioneEffettivaChangeOld = (e: any) => {

  }

  onPercentualeErogazioneEffettivaBlur() {
    console.log('onPercentualeErogazioneEffettivaBlur');
    //if (this.fieldPercentualeErogazioneEffettiva) {
    this.fieldPercentualeErogazioneEffettivaFormatted = this.sharedService.formatValue(this.fieldPercentualeErogazioneEffettiva.toString());
    //}
  }

  onImportoErogazioneEffettivoChange = (e: any) => {
    if (this.erogazione && this.erogazione.erogazione) {
      let tot: number = this.erogazione.erogazione.importoTotaleResiduo + this.erogazione.erogazione.importoTotaleErogato;
      let value: number = +(this.fieldImportoErogazioneEffettivo);
      this.fieldPercentualeErogazioneEffettiva = (value * 100) / tot;
    }
  }

  onImportoErogazioneEffettivoChangeOld = (e: any) => {

  }

  onImportoErogazioneEffettivoBlur() {
    console.log('onImportoErogazioneEffettivoBlur');
    //if (this.fieldImportoErogazioneEffettivo) {
    this.fieldImportoErogazioneEffettivoFormatted = this.sharedService.formatValue(this.fieldImportoErogazioneEffettivo.toString());
    //}
  }

  goToDettaglioDocumento(idProgetto: number, idErogazione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_EROGAZIONE + "/dettaglio-erogazione", idProgetto, this.idBandoLinea, idErogazione]);
  }

  goToModificaDocumento(idProgetto: number, idErogazione: number) {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_EROGAZIONE + "/modifica-erogazione", idProgetto, this.idBandoLinea, idErogazione]);
  }

  ricerca() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.loadedRicerca = false;
    if (this.action == 'inserisci') {
      let salvaErogazioneRequest: SalvaErogazioneRequest = {
        datiCalcolati: this.datiCalcolati,
        dettaglioErogazione: {
          codCausaleErogazione: this.causaleSelected.codice,
          codModalitaAgevolazione: this.modalitaAgevolazioneContoEconomicoSelected.codice,
          codModalitaErogazione: this.modalitaErogazioneSelected.codice,
          codTipoDirezione: this.codiciDirezioneSelected.codice,
          dataContabile: ((this.dataContabile) ? this.datePipe.transform(this.dataContabile, 'dd/MM/yyyy') : undefined),  // this.erogazione.erogazione.dataContabile
          descCausaleErogazione: this.causaleSelected.descrizione,
          descModalitaAgevolazione: this.modalitaAgevolazioneContoEconomicoSelected.descrizione,
          descModalitaErogazione: this.modalitaErogazioneSelected.descrizione,
          descTipoDirezione: this.codiciDirezioneSelected.descrizione,
          //idErogazione: undefined,  // 'modifica', 'inserisci'
          importoCalcolato: this.erogazione.erogazione.spesaProgetto.importoAmmessoContributo,  // 'modifica', 'inserisci'
          importoErogazioneDaIterFinanziario: this.importoErogazioneFinanziario,  // this.erogazione.erogazione.importoErogazioneFinanziario
          importoErogazioneEffettiva: this.fieldImportoErogazioneEffettivo,  // this.erogazione.erogazione.importoErogazioneEffettivo
          importoResiduo: this.datiCalcolati.importoResiduoSpettante,  // 'modifica', 'inserisci'
          note: this.noteErogazione,  // this.erogazione.erogazione.noteErogazione
          numero: this.numeroRiferimento,  // this.dettaglioErogazione.numero
          percentualeErogazioneEffettiva: this.fieldPercentualeErogazioneEffettiva,  // this.erogazione.erogazione.percentualeErogazioneEffettiva
          percentualeErogazioneIterFinanziario: this.percentualeErogazioneIterFinanziario // this.erogazione.erogazione.percentualeErogazioneFinanziaria
        },
        idProgetto: this.idProgetto
      };
      this.dataRequest = salvaErogazioneRequest;
      this.subscribers.ricercaErogazione = this.erogazioneService.inserisciErogazione(salvaErogazioneRequest).subscribe(res => {
        if (res.esito) {
          this.showMessageSuccess(res.messages.join('<br>'));
          this.loadErogazione();
          this.ricercaForm.resetForm();
          this.ricercaForm2.resetForm();
        } else if (res.messages) {
          this.showMessageError(res.messages.join('<br>'));
        } else {
          this.showMessageError('Nessun messaggio');
        }
        this.loadedRicerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        if (err.error && !err.error.esito && err.error.msg) {
          this.showMessageError(err.error.msg);
        } else {
          this.showMessageError(err.message);
        }
        this.loadedRicerca = true;
      });
    } else if (this.action == 'modifica') {
      let modificaErogazioneRequest: ModificaErogazioneRequest = {
        datiCalcolati: this.datiCalcolati,
        erogazione: {
          codCausaleErogazione: this.causaleSelected.codice,
          codModalitaAgevolazione: this.modalitaAgevolazioneContoEconomicoSelected.codice,
          codModalitaErogazione: this.modalitaErogazioneSelected.codice,
          codTipoDirezione: this.codiciDirezioneSelected.codice,
          dataContabile: ((this.dataContabile) ? this.datePipe.transform(this.dataContabile, 'dd/MM/yyyy') : undefined),  // this.erogazione.erogazione.dataContabile
          descCausaleErogazione: this.causaleSelected.descrizione,
          descModalitaAgevolazione: this.modalitaAgevolazioneContoEconomicoSelected.descrizione,
          descModalitaErogazione: this.modalitaErogazioneSelected.descrizione,
          descTipoDirezione: this.codiciDirezioneSelected.descrizione,
          idErogazione: this.dettaglioErogazione.idErogazione,  // 'modifica', 'inserisci'
          importoCalcolato: this.dettaglioErogazione.importoCalcolato,  // 'modifica', 'inserisci'
          importoErogazioneDaIterFinanziario: this.importoErogazioneFinanziario,  // this.erogazione.erogazione.importoErogazioneFinanziario
          importoErogazioneEffettiva: this.fieldImportoErogazioneEffettivo,  // this.erogazione.erogazione.importoErogazioneEffettivo
          importoResiduo: this.dettaglioErogazione.importoResiduo,  // 'modifica', 'inserisci'
          note: this.noteErogazione,  // this.erogazione.erogazione.noteErogazione
          numero: this.numeroRiferimento,  // this.dettaglioErogazione.numero
          percentualeErogazioneEffettiva: this.fieldPercentualeErogazioneEffettiva,  // this.erogazione.erogazione.percentualeErogazioneEffettiva
          percentualeErogazioneIterFinanziario: this.percentualeErogazioneIterFinanziario // this.erogazione.erogazione.percentualeErogazioneFinanziaria
        },
        idProgetto: this.idProgetto
      };
      this.dataRequest = modificaErogazioneRequest;
      this.subscribers.ricercaErogazione = this.erogazioneService.modificaErogazione(modificaErogazioneRequest).subscribe(res => {
        //this.setupErogazioneItemDatasource(data);
        if (res.esito) {
          this.showMessageSuccess(res.msg);
          this.loadErogazione();
          this.loadDettaglioErogazione();
        } else if (res.msg) {
          this.showMessageError(res.msg);
        } else {
          this.showMessageError('Nessun messaggio');
        }
        this.loadedRicerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError(err.message);
      });
    }
  }

  //MESSAGGI SUCCESS E ERROR - start
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