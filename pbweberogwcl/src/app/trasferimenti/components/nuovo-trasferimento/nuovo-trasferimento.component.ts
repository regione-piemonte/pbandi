/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe } from "@angular/common";
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { BeneficiarioSec } from 'src/app/core/commons/dto/beneficiario';  // select-beneficiario
import { Trasferimento } from "../../commons/dto/trasferimento";
import { TrasferimentiService } from "../../services/trasferimenti.service";
import { SoggettoTrasferimento } from "../../commons/dto/soggetto-trasferimento";
import { CausaleTrasferimento } from "../../commons/dto/causale-trasferimento";

import { NgForm } from "@angular/forms";
import { PbandiDLineaDiInterventoVO } from '../../commons/dto/pbandi-d-linea-di-intervento-vo';
import { Trasferimenti2Service } from '../../services/trasferimenti2.service';

@Component({
  selector: 'app-nuovo-trasferimento',
  templateUrl: './nuovo-trasferimento.component.html',
  styleUrls: ['./nuovo-trasferimento.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovoTrasferimentoComponent implements OnInit {
  test: boolean = false;
  enableSelectBeneficiario: boolean = true;
  enableBasicSelectBeneficiario: boolean = false;
  //
  action: string; // inserisci, modifica, dettaglio
  title: string;
  titleInserisci: string = 'Nuovo Trasferimento';
  titleModifica: string = 'Modifica Trasferimento';
  titleDettaglio: string = 'Dettaglio Trasferimento';
  matchUrlInserisci: string = 'nuovo-trasferimento';
  matchUrlModifica: string = 'modifica-trasferimento';
  matchUrlDettaglio: string = 'dettaglio-trasferimento';
  //
  idTrasferimento: number;  // ex idProgetto
  idDocumentoDiSpesa: number; // Todo: rename into idTrasferimento
  codiceProgetto: string;
  trasferimento: Trasferimento;
  normative: Array<PbandiDLineaDiInterventoVO>;
  normativaSelected: PbandiDLineaDiInterventoVO;
  //
  tipoOperazioneCurrent: string;
  tipoOperazioneFrom: string;
  //documento: DocumentoDiSpesaDTO;
  //
  user: UserInfoSec;
  inputNumberType: string = 'float';

  //LOADED
  loadedBeneficiari: boolean;
  loadedCausali: boolean;
  loadedDocumento: boolean;
  loadedInserisci: boolean;
  loadedModifica: boolean;
  loadedNormative: boolean;

  //MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //SUBSCRIBERS
  subscribers: any = {};

  // Form - start
  //FORM
  @ViewChild("ricercaForm", { static: true }) ricercaForm: NgForm;
  beneficiari: Array<SoggettoTrasferimento>;
  causali: Array<CausaleTrasferimento>;
  criteriRicercaOpened: boolean = true;
  beneficiarioSelected: SoggettoTrasferimento;
  beneficiariS: Array<BeneficiarioSec>;
  beneficiarioS: BeneficiarioSec;
  data: Date;
  causaleSelected: CausaleTrasferimento;
  codice: string = '';
  tipoRadio: string;
  importo: number;

  dataSource: any;
  displayedColumns: string[] = ['attivita', 'beneficiario', 'codiceprogetto', 'contributopubblico', 'cofinanziamento', 'toterogato', 'totpagamenti', 'totfideiussioni', 'spesacertificabile', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  // Tabella - end

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private datePipe: DatePipe,
    private trasferimentiService: TrasferimentiService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private sharedService: SharedService,
    private trasferimenti2Service: Trasferimenti2Service
  ) { }


  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (this.router.url.match('/' + this.matchUrlInserisci + '$')) {
        this.action = 'inserisci';
      } else if (this.router.url.match('/' + this.matchUrlModifica + '/')) {
        this.action = 'modifica';
      } else if (this.router.url.match('/' + this.matchUrlDettaglio + '/')) {
        this.action = 'dettaglio';
      } else {
        console.error('router url not matched', this.router.url);
      }
      if (data) {
        this.user = data;
        if (this.user.idIride && (this.user.codiceRuolo == 'ADG-IST-MASTER' || this.user.codiceRuolo == 'ADG-ISTRUTTORE' || this.user.codiceRuolo == 'ADG-CERT')) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            if (this.action == 'inserisci') {
              this.title = this.titleInserisci;
              this.tipoOperazioneCurrent = 'inserisci'; // Test
              this.idTrasferimento = 0;
              console.log('nuovoTrasferimento', this.tipoOperazioneCurrent, this.idDocumentoDiSpesa);
              this.loadDati();
            } else if (this.action == 'modifica' && params['idDocSpesa']) {
              this.title = this.titleModifica;
              this.tipoOperazioneCurrent = 'modifica'; // Test
              this.idTrasferimento = +params['idDocSpesa'];
              this.idDocumentoDiSpesa = +params['idDocSpesa'];
              console.log('nuovoTrasferimento', this.tipoOperazioneCurrent, this.idDocumentoDiSpesa);
              this.loadTrasferimento();

            } else if (this.action == 'dettaglio' && params['idDocSpesa']) {
              this.title = this.titleDettaglio;
              this.idTrasferimento = +params['idDocSpesa'];
              this.loadTrasferimento();
            } else {

            }

          });
        }
      }
    });
  }

  loadDati() {
    this.loadData();
  }

  loadData() {
    this.loadBeneficiari();
    this.loadCausali();
    this.loadNormative();
  }

  loadNormative() {
    this.loadedNormative = false;
    this.subscribers.normative = this.trasferimenti2Service.getNormativa().subscribe(data => {
      if (data) {
        this.normative = data;
        if (this.trasferimento) {
          this.normativaSelected = this.normative.find(n => n.idLineaDiIntervento === this.trasferimento.idLineaDiIntervento);
        }
      }
      this.loadedNormative = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero delle normative.");
      this.loadedNormative = true;
    });
  }

  loadTrasferimento() {
    this.loadedDocumento = false;
    this.subscribers.doc = this.trasferimentiService.getDettaglioTrasferimento(this.idTrasferimento).subscribe(data => {
      if (data) {
        this.trasferimento = data;
        if (this.enableSelectBeneficiario) {
          this.beneficiarioS = {
            denominazione: this.trasferimento.denominazioneBeneficiario,
            codiceFiscale: this.trasferimento.cfBeneficiario,
            idBeneficiario: this.trasferimento.idSoggettoBeneficiario
          };
        }
        if (this.enableBasicSelectBeneficiario) {
          this.beneficiarioSelected = {
            cfBeneficiario: this.trasferimento.cfBeneficiario,
            denominazioneBeneficiario: this.trasferimento.denominazioneBeneficiario,
            idSoggettoBeneficiario: this.trasferimento.idSoggettoBeneficiario
          };
        }
        this.causaleSelected = {
          descCausaleTrasferimento: this.trasferimento.descCausaleTrasferimento,
          idCausaleTrasferimento: this.trasferimento.idCausaleTrasferimento
        };
        this.data = this.sharedService.getDateFromStrDateIta(this.trasferimento.dtTrasferimento);
        this.codice = this.trasferimento.codiceTrasferimento;
        this.tipoRadio = this.trasferimento.flagPubblicoPrivato;
        this.importo = this.trasferimento.importoTrasferimento;

        this.loadDati();
      }
      this.loadedDocumento = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  loadDataMock() {
    this.loadedBeneficiari = true;
    this.loadedCausali = true;
    this.getBeneficiario();
    this.getCausale();
    this.data = new Date('2021-06-03 00:00:00');
    this.causaleSelected = null;
    this.codice = 'Trasferimento';
    this.tipoRadio = '1';
    this.importo = 100000;
  }

  getBeneficiario() {
    this.beneficiari = new Array<any>();
    this.beneficiari.push({ denominazioneBeneficiario: "1° beneficiario" });
    this.beneficiari.push({ denominazioneBeneficiario: "2° beneficiario" });
  }

  getCausale() {
    this.causali = new Array<any>();
    this.causali.push({ descCausaleTrasferimento: "1° causale" });
    this.causali.push({ descCausaleTrasferimento: "2° causale" });
  }
  // Form - mock - end

  onTorna() {
    this.goToTrasferimenti();
  }

  goToTrasferimenti() {
    this.router.navigate(["drawer/" + Constants.ID_OPERAZIONE_TRASFERIMENTI + "/trasferimenti"]);
  }

  loadBeneficiari() {
    this.loadedBeneficiari = false;
    this.trasferimentiService.getSoggettiTrasferimento().subscribe(res => {
      if (res) {
        if (this.enableBasicSelectBeneficiario) {
          this.beneficiari = res;
        }
        if (this.enableSelectBeneficiario) {
          this.beneficiariS = res.map((res) => {  // select-beneficiario
            return {
              denominazione: res.denominazioneBeneficiario,
              codiceFiscale: res.cfBeneficiario,
              idBeneficiario: res.idSoggettoBeneficiario
            };
          });
        }
      }
      this.loadedBeneficiari = true;
    });
  }

  loadCausali() {
    this.loadedCausali = false;
    this.trasferimentiService.getCausaliTrasferimento().subscribe(params => {
      if (params) {
        this.causali = params;
      }
      this.loadedCausali = true;
    });
  }
  // Form - end

  isLoading() {
    if (
      !this.loadedBeneficiari || !this.loadedCausali || !this.loadedNormative ||
      (
        (
          this.action == 'modifica' ||
          this.action == 'dettaglio'
        ) &&
        !this.loadedDocumento
      )
    ) {
      return true;
    }
    return false;
  }

  nuovoBando() {

  }

  compareWithSelect(option, value, field: string = 'id') {
    return (!value && value !== 0) && (option[field] === value[field]);
  }

  compareWithSelectBeneficiario(option, value) {
    return value && (option.idSoggettoBeneficiario === value.idSoggettoBeneficiario);
  }

  compareWithSelectCausale(option, value) {
    return value && (option.idCausaleTrasferimento === value.idCausaleTrasferimento);
  }

  onSelectBeneficiario() {

  }

  salva() {
    this.resetMessageSuccess();
    this.resetMessageError();
    let request: Trasferimento = {
      cfBeneficiario: ((this.enableSelectBeneficiario) ?
        ((this.beneficiarioS && this.beneficiarioS.codiceFiscale) ? this.beneficiarioS.codiceFiscale : undefined)  // select-beneficiario
        :
        ((this.enableBasicSelectBeneficiario) ?
          ((this.beneficiarioSelected && this.beneficiarioSelected.cfBeneficiario) ? this.beneficiarioSelected.cfBeneficiario : undefined)
          :
          undefined
        )
      ),
      codiceTrasferimento: ((this.codice) ? this.codice : undefined),
      denominazioneBeneficiario: ((this.enableSelectBeneficiario) ?
        ((this.beneficiarioS && this.beneficiarioS.denominazione) ? this.beneficiarioS.denominazione : undefined)  // select-beneficiario
        :
        ((this.enableBasicSelectBeneficiario) ?
          ((this.beneficiarioSelected && this.beneficiarioSelected.denominazioneBeneficiario) ? this.beneficiarioSelected.denominazioneBeneficiario : undefined)
          :
          undefined
        )
      ),
      descCausaleTrasferimento: ((this.causaleSelected && this.causaleSelected.idCausaleTrasferimento) ? this.causaleSelected.descCausaleTrasferimento : undefined),
      descPubblicoPrivato: ((this.tipoRadio) ? ((this.tipoRadio == '0') ? 'tutti' : ((this.tipoRadio == '1') ? 'privati' : ((this.tipoRadio == '2') ? 'pubblici' : undefined))) : undefined),
      dtTrasferimento: ((this.data) ? this.datePipe.transform(this.data, 'dd/MM/yyyy') : undefined),
      flagPubblicoPrivato: ((this.tipoRadio) ? this.tipoRadio : undefined),
      idCausaleTrasferimento: ((this.causaleSelected && this.causaleSelected.idCausaleTrasferimento) ? this.causaleSelected.idCausaleTrasferimento : undefined),
      idLineaDiIntervento: this.normativaSelected ? this.normativaSelected.idLineaDiIntervento : null,
      idSoggettoBeneficiario: ((this.enableSelectBeneficiario) ?
        ((this.beneficiarioS && this.beneficiarioS.idBeneficiario) ? this.beneficiarioS.idBeneficiario : undefined)  // select-beneficiario
        :
        ((this.enableBasicSelectBeneficiario) ?
          ((this.beneficiarioSelected && this.beneficiarioSelected.idSoggettoBeneficiario) ? this.beneficiarioSelected.idSoggettoBeneficiario : undefined)
          :
          undefined
        )
      ),
      idTrasferimento: ((this.action == 'modifica') ? this.idTrasferimento : undefined),
      importoTrasferimento: ((this.importo) ? this.importo : undefined),
      //importoTrasferimento: ((this.importo)?parseFloat(this.sharedService.getNumberFromFormattedString(this.importo.toString()).toString()):undefined), // 123000.2345 -> 123.000,23 -> 123000.23 // formatValue  // old
    };
    if (this.action == 'inserisci') {
      this.loadedInserisci = false;
      this.subscribers.trasferimentiService = this.trasferimentiService.inserisciTrasferimento(request).subscribe(data => {
        if (data) {
          if (data.esito && data.esito) {
            this.showMessageSuccess(data.msgs.map(o => o.msgKey).join("<br>"));
          } else {
            this.showMessageError(data.msgs.map(o => o.msgKey).join("<br>"));
          }
        }
        this.loadedInserisci = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedInserisci = true;
        this.showMessageError("Errore nell'inserimento del trasferimento.");
      });
    } else if (this.action == 'modifica') {
      this.loadedModifica = false;
      this.subscribers.trasferimentiService = this.trasferimentiService.modificaTrasferimento(request).subscribe(data => {
        if (data) {
          if (data.esito && data.esito) {
            this.showMessageSuccess(data.msgs.map(o => o.msgKey).join("<br>"));
          } else {
            this.showMessageError(data.msgs.map(o => o.msgKey).join("<br>"));
          }
        }
        this.loadedModifica = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.loadedModifica = true;
        this.showMessageError("Errore nella modifica del trasferimento.");
      });
    }
  }

  onSelectCausale() {

  }

  disabledSalva(): boolean {
    return !(((this.enableSelectBeneficiario) ? this.beneficiarioS : ((this.enableBasicSelectBeneficiario) ? this.beneficiarioSelected && this.beneficiarioSelected.idSoggettoBeneficiario : false)) && this.normativaSelected && this.causaleSelected && this.data && this.codice && this.tipoRadio && this.importo); // this.attivitaSelected && this.progettoSelected  // -- // this.beneficiarioS // select-beneficiario
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

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  //MESSAGGI SUCCESS E ERROR - end
}
