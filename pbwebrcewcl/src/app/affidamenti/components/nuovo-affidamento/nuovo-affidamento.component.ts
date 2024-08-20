/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { Affidamento } from '../../commons/dto/affidamento';
import { MotiveAssenzaDTO } from '../../commons/dto/motive-assenza-dto';
import { NormativaAffidamentoDTO } from '../../commons/dto/normativa-affidamento-dto';
import { ProceduraAggiudicazioneAffidamento } from '../../commons/dto/procedura-aggiudicazione-affidamento';
import { TipoAffidamentoDTO } from '../../commons/dto/tipo-affidamento-dt';
import { TipologiaAggiudicazioneDTO } from '../../commons/dto/tipologia-aggiudicazione-dto';
import { TipologiaAppaltoDTO } from '../../commons/dto/tipologia-appalto-dto';
import { SalvaAffidamentoRequest } from '../../commons/requests/salva-affidamento-request';
import { AffidamentiService } from '../../services/affidamenti.service';
import { AffidamentoCheckListDTO } from '../../commons/dto/affidamento-checklist-dto';
import { MatDialog } from '@angular/material/dialog';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { SharedService } from 'src/app/shared/services/shared.service';

@Component({
  selector: 'app-nuovo-affidamento',
  templateUrl: './nuovo-affidamento.component.html',
  styleUrls: ['./nuovo-affidamento.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoAffidamentoComponent implements OnInit {

  idOperazione: number;
  idProgetto: number;
  codiceProgetto: string;
  user: UserInfoSec;

  mappatura: Array<AffidamentoCheckListDTO>;
  normative: Array<NormativaAffidamentoDTO>;
  normativaSelected: NormativaAffidamentoDTO;
  tipologie: Array<TipoAffidamentoDTO>;
  tipologieFiltered: Array<TipoAffidamentoDTO>;
  tipologiaSelected: TipoAffidamentoDTO;
  categorie: Array<TipologiaAppaltoDTO>;
  categorieFiltered: Array<TipologiaAppaltoDTO>;
  categoriaSelected: TipologiaAppaltoDTO;
  importoBaseGara: number;
  importoBaseGaraFormatted: string;
  soglia: string = '0';
  importoRibAsta: number;
  importoRibAstaFormatted: string;
  precentualeRibAsta: number;
  precentualeRibAstaFormatted: string;
  importoAggiudicato: number;
  importoAggiudicatoFormatted: string;
  importoRendicontabile: number;
  importoRendicontabileFormatted: string;
  dataFirmaContratto: FormControl = new FormControl();
  dataInizioLavori: FormControl = new FormControl();
  dataConsegnaLavori: FormControl = new FormControl();
  oggetto: string;
  identificativoIntervento: string;
  cpa: string;
  cig: string;
  nonPrevisto: boolean = false;
  motivi: Array<MotiveAssenzaDTO>;
  motivoSelected: MotiveAssenzaDTO;
  tipologieAgg: Array<TipologiaAggiudicazioneDTO>;
  tipologieAggFiltered: Array<TipologiaAggiudicazioneDTO>;
  tipologiaAggSelected: TipologiaAggiudicazioneDTO;
  descrizione: string;
  dataGUUE: FormControl = new FormControl();
  dataGURI: FormControl = new FormControl();
  dataQuotidianiNazionali: FormControl = new FormControl();
  dataSitoWebAppaltante: FormControl = new FormControl();
  dataSitoWebOsservatorio: FormControl = new FormControl();

  allDisabled: boolean = true;
  isNAdisabled: boolean;
  hasValidationError: boolean;

  @ViewChild("affidamentiForm", { static: true }) affidamentiForm: NgForm;
  @ViewChild("aggiudicazioneForm", { static: true }) aggiudicazioneForm: NgForm;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedMappatura: boolean;
  loadedNormative: boolean;
  loadedTipologie: boolean;
  loadedCategorie: boolean;
  loadedTipologieAggiudicazione: boolean;
  loadedMotiviAssenza: boolean;
  loadedSave: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private affidamentiService: AffidamentiService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private configService: ConfigService,
    private sharedService: SharedService,
    private dialog: MatDialog,
    private _adapter: DateAdapter<any>
  ) {
    this._adapter.setLocale('it');
  }
  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.codiceRuolo && this.user.beneficiarioSelezionato) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.loadMappatura();
            this.loadCombo();
            this.subscribers.codice = this.affidamentiService.getCodiceProgetto(this.idProgetto).subscribe(res => {
              if (res) {
                this.codiceProgetto = res;
              }
            }, err => {
              this.handleExceptionService.handleNotBlockingError(err);
            });
          });

        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_AFFIDAMENTO;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  loadMappatura() {
    this.loadedMappatura = false;
    this.subscribers.mappatura = this.affidamentiService.getAllAffidamentoChecklist().subscribe(data => {
      if (data) {
        this.mappatura = data;
      }
      this.loadedMappatura = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  loadCombo() {
    this.loadedNormative = false;
    this.subscribers.normative = this.affidamentiService.getNormativeAffidamento().subscribe(data => {
      if (data) {
        this.normative = data;
      }
      this.loadedNormative = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedTipologie = false;
    this.subscribers.tipologie = this.affidamentiService.getTipologieAffidamento().subscribe(data => {
      if (data) {
        this.tipologie = data;
      }
      this.loadedTipologie = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedCategorie = false;
    this.subscribers.categorie = this.affidamentiService.getTipologieAppalto().subscribe(data => {
      if (data) {
        this.categorie = data;
      }
      this.loadedCategorie = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedTipologieAggiudicazione = false;
    this.subscribers.tipologieAgg = this.affidamentiService.getTipologieProcedureAggiudicazione(this.idProgetto).subscribe(data => {
      if (data) {
        this.tipologieAgg = data;
      }
      this.loadedTipologieAggiudicazione = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadedMotiviAssenza = false;
    this.subscribers.motivi = this.affidamentiService.getMotiveAssenza().subscribe(data => {
      if (data) {
        this.motivi = data;
      }
      this.loadedMotiviAssenza = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  onSelectNormativa() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma);
    this.tipologieFiltered = new Array<TipoAffidamentoDTO>();
    rows.map(row => row.idTipoAffidamento).forEach(id => {
      if (!this.tipologieFiltered.find(t => t.idTipoAffidamento === id)) {
        this.tipologieFiltered.push(this.tipologie.find(t => t.idTipoAffidamento === id));
      }
    });
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
    this.soglia = "0";
    this.tipologiaSelected = null;
    this.categoriaSelected = null;
  }

  onSelectTipologia() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma && row.idTipoAffidamento === this.tipologiaSelected.idTipoAffidamento);
    this.categorieFiltered = new Array<TipologiaAppaltoDTO>();
    rows.map(row => row.idTipoAppalto).forEach(id => {
      if (!this.categorieFiltered.find(t => t.idTipologiaAppalto === id)) {
        this.categorieFiltered.push(this.categorie.find(t => t.idTipologiaAppalto === id));
      }
    });
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
    this.categoriaSelected = null;
  }

  onSelectCategoria() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma
      && row.idTipoAffidamento === this.tipologiaSelected.idTipoAffidamento
      && row.idTipoAppalto === this.categoriaSelected.idTipologiaAppalto);
    this.tipologieAggFiltered = new Array<TipologiaAggiudicazioneDTO>();
    rows.map(row => row.idTipologiaAggiudicaz).forEach(id => {
      if (!this.tipologieAggFiltered.find(t => t.idTipologiaAggiudicaz === id)) {
        this.tipologieAggFiltered.push(this.tipologieAgg.find(t => t.idTipologiaAggiudicaz === id));
      }
    });
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
    this.tipologiaAggSelected = null;
  }

  onSelectTipologiaAgg() {
    let rows = this.mappatura.filter(row => row.idNorma === this.normativaSelected.idNorma
      && row.idTipoAffidamento === this.tipologiaSelected.idTipoAffidamento
      && row.idTipoAppalto === this.categoriaSelected.idTipologiaAppalto
      && row.idTipologiaAggiudicaz === this.tipologiaAggSelected.idTipologiaAggiudicaz)
      ;
    let sogliaVett = rows.map(row => row.sopraSoglia);
    this.isNAdisabled = sogliaVett.find(n => n !== "S" && n !== "N") != undefined ? false : true;
    this.allDisabled = sogliaVett.find(n => n === "S") != undefined && sogliaVett.find(n => n === "N") != undefined ? false : true;
  }

  setImportoBaseGara() {
    this.importoBaseGara = this.sharedService.getNumberFromFormattedString(this.importoBaseGaraFormatted);
    if (this.importoBaseGara !== null) {
      this.importoBaseGaraFormatted = this.sharedService.formatValue(this.importoBaseGara.toString());
    }
  }

  setImportoRibAsta() {
    this.importoRibAsta = this.sharedService.getNumberFromFormattedString(this.importoRibAstaFormatted);
    if (this.importoRibAsta !== null) {
      this.importoRibAstaFormatted = this.sharedService.formatValue(this.importoRibAsta.toString());
    }
  }

  setPercentualeRibAsta() {
    this.precentualeRibAsta = this.sharedService.getNumberFromFormattedString(this.precentualeRibAstaFormatted);
    if (this.precentualeRibAsta !== null) {
      this.precentualeRibAstaFormatted = this.sharedService.formatValue(this.precentualeRibAsta.toString());
    }
  }

  setImportoAggiudicato() {
    this.importoAggiudicato = this.sharedService.getNumberFromFormattedString(this.importoAggiudicatoFormatted);
    if (this.importoAggiudicato !== null) {
      this.importoAggiudicatoFormatted = this.sharedService.formatValue(this.importoAggiudicato.toString());
    }
  }

  setImportoRendicontabile() {
    this.importoRendicontabile = this.sharedService.getNumberFromFormattedString(this.importoRendicontabileFormatted);
    if (this.importoRendicontabile !== null) {
      this.importoRendicontabileFormatted = this.sharedService.formatValue(this.importoRendicontabile.toString());
    }
  }

  checkRequiredForm(form: NgForm, name: string) {
    if (!form.form.get(name) || !form.form.get(name).value) {
      form.form.get(name).setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
  }

  setAllMarkAsTouched() {
    Object.keys(this.affidamentiForm.form.controls).forEach(key => {
      if (this.affidamentiForm.form.controls[key]) {
        this.affidamentiForm.form.controls[key].markAsTouched();
      }
    });
    Object.keys(this.aggiudicazioneForm.form.controls).forEach(key => {
      if (this.aggiudicazioneForm.form.controls[key]) {
        this.aggiudicazioneForm.form.controls[key].markAsTouched();
      }
    });
    this.dataFirmaContratto.markAsTouched();
    this.dataInizioLavori.markAsTouched();
    this.dataConsegnaLavori.markAsTouched();
    this.dataGUUE.markAsTouched();
    this.dataGURI.markAsTouched();
    this.dataQuotidianiNazionali.markAsTouched();
    this.dataSitoWebAppaltante.markAsTouched();
    this.dataSitoWebOsservatorio.markAsTouched();
  }

  validate() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.hasValidationError = false;
    this.checkRequiredForm(this.affidamentiForm, "normativa");
    this.checkRequiredForm(this.affidamentiForm, "tipologia");
    this.checkRequiredForm(this.affidamentiForm, "categoria");
    this.checkRequiredForm(this.affidamentiForm, "importoAggiudicato");
    this.checkRequiredForm(this.affidamentiForm, "importoRendicontabile");
    this.checkRequiredForm(this.affidamentiForm, "oggetto");
    this.checkRequiredForm(this.aggiudicazioneForm, "cpa");
    this.checkRequiredForm(this.aggiudicazioneForm, "tipologiaAgg");
    this.checkRequiredForm(this.aggiudicazioneForm, "descrizione");
    if (!this.dataFirmaContratto || !this.dataFirmaContratto.value) {
      this.dataFirmaContratto.setErrors({ error: 'required' });
      this.hasValidationError = true;
    }
    if (this.nonPrevisto) {
      this.checkRequiredForm(this.affidamentiForm, "importoBaseGara");
      this.checkRequiredForm(this.aggiudicazioneForm, "motivo");
    } else {
      this.checkRequiredForm(this.aggiudicazioneForm, "cig");
    }
    if (this.affidamentiForm.form.get("precentualeRibAsta") && this.affidamentiForm.form.get("precentualeRibAsta").value) {
      let perc = this.affidamentiForm.form.get("precentualeRibAsta").value;
      if (perc < 0 || perc > 100) {
        this.affidamentiForm.form.get("precentualeRibAsta").setErrors({ error: 'notAPercentage' });
      }
    }
    if (!this.allDisabled && this.isNAdisabled) {
      if (this.soglia === "0") {
        this.affidamentiForm.form.get("soglia").setErrors({ error: 'required' });
        this.hasValidationError = true;
      }
    }

    this.setAllMarkAsTouched();
    if (this.hasValidationError) {
      this.showMessageError("ATTENZIONE! Risultano errati alcuni campi. Correggere o completare i dati contrassegnati prima di proseguire.");
    }
  }

  setAffidamento(): Affidamento {
    let affidamento = new Affidamento();
    affidamento.idProgetto = this.idProgetto;
    affidamento.oggettoAppalto = this.oggetto;
    affidamento.idTipologiaAppalto = this.categoriaSelected.idTipologiaAppalto;
    affidamento.descTipologiaAppalto = this.categoriaSelected.descTipologiaAppalto;
    affidamento.idTipoAffidamento = this.tipologiaSelected.idTipoAffidamento;
    affidamento.idStatoAffidamento = Constants.ID_STATO_AFFIDAMENTO_DA_INVIARE;
    affidamento.descStatoAffidamento = Constants.DESC_STATO_AFFIDAMENTO_DA_INVIARE;
    affidamento.idNorma = this.normativaSelected.idNorma;
    affidamento.bilancioPreventivo = this.importoBaseGara;
    affidamento.importoContratto = this.importoAggiudicato;
    affidamento.impRendicontabile = this.importoRendicontabile;
    affidamento.impRibassoAsta = this.importoRibAsta;
    affidamento.percRibassoAsta = this.precentualeRibAsta;
    affidamento.dtGuue = this.dataGUUE.value;
    affidamento.dtGuri = this.dataGURI.value;
    affidamento.dtQuotNazionali = this.dataQuotidianiNazionali.value;
    affidamento.dtWebStazAppaltante = this.dataSitoWebAppaltante.value;
    affidamento.dtWebOsservatorio = this.dataSitoWebOsservatorio.value;
    affidamento.dtInizioPrevista = this.dataInizioLavori.value;
    affidamento.dtConsegnaLavori = this.dataConsegnaLavori.value;
    affidamento.dtFirmaContratto = this.dataFirmaContratto.value;
    affidamento.interventoPisu = this.identificativoIntervento;
    affidamento.sopraSoglia = this.soglia === "0" ? null : (this.soglia === "1" ? "N" : "S");
    affidamento.descProcAgg = this.descrizione;
    affidamento.dtInserimento = new Date();
    if (!this.nonPrevisto) {
      affidamento.cigProcAgg = this.cig;
    }
    affidamento.codProcAgg = this.cpa;
    return affidamento;
  }

  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();

    this.validate();
    if (!this.hasValidationError) {
      this.loadedSave = false;
      let procAgg = new ProceduraAggiudicazioneAffidamento(null, this.idProgetto, this.tipologiaAggSelected.idTipologiaAggiudicaz, this.motivoSelected && this.nonPrevisto ? this.motivoSelected.idMotivoAssenzaCig : null,
        this.descrizione, this.nonPrevisto ? null : this.cig, this.cpa, this.importoAggiudicato, null, this.dataFirmaContratto.value, new Date(), null, null, null, this.nonPrevisto);
      this.subscribers.save = this.affidamentiService.salvaAffidamento(new SalvaAffidamentoRequest(this.setAffidamento(), procAgg)).subscribe(data => {
        if (data) {
          console.log(data);
          if (data.esito) {
            this.router.navigate(["drawer/" + this.idOperazione + "/modificaAffidamento", data.params[0], { action: "SUCCESS" }]);
          } else {
            this.showMessageError(data.msg);
          }
        }
        this.loadedSave = true;
      }), err => {
        this.handleExceptionService.handleNotBlockingError(err);

        this.showMessageError("Errore nel salvataggio dell'affidamento.")
        this.loadedSave = true;
      };
    }
  }

  goToGestioneAffidamenti() {
    this.router.navigate(["drawer/" + this.idOperazione + "/affidamenti", this.idProgetto]);
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

  isLoading() {
    if (!this.loadedMappatura || !this.loadedNormative || !this.loadedCategorie || !this.loadedTipologie
      || !this.loadedTipologieAggiudicazione || !this.loadedMotiviAssenza || !this.loadedSave) {
      return true;
    }
    return false;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

}
