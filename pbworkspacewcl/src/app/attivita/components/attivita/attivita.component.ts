/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfigService } from 'src/app/core/services/config.service';
import { BeneficiarioSec } from 'src/app/core/commons/vo/beneficiario-vo';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AttivitaVO } from '../../commons/vo/attivita-vo';
import { BandoVO } from '../../commons/vo/bando-vo';
import { ProgettoVO } from '../../commons/vo/progetto-vo';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaService } from '../../services/attivita.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { HomeService } from 'src/app/home/services/home.service';

@Component({
  selector: 'app-attivita',
  templateUrl: './attivita.component.html',
  styleUrls: ['./attivita.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AttivitaComponent implements OnInit {

  idOperazione: number;
  user: UserInfoSec;

  beneficiario: BeneficiarioSec;
  beneficiari: Array<BeneficiarioSec>;
  beneficiarioSelected: FormControl = new FormControl();
  beneficiarioGroup: FormGroup = new FormGroup({ beneficiarioControl: new FormControl() });
  filteredOptions: Observable<BeneficiarioSec[]>;
  isCambiaBeneficiarioVisible: boolean;
  criteriRicercaOpened: boolean = true;
  esisteRicercaPrecedente: boolean;

  bandi: Array<BandoVO>;
  bandoSelected: BandoVO;
  progetti: Array<ProgettoVO>;
  progettoSelected: ProgettoVO;
  attivita: Array<AttivitaVO>;
  descAttivita: string = ""; //inizializzare a stringa vuota altrimenti non funziona la query di ricerca

  progettiSearched: Array<ProgettoVO>;

  nNotificheDaLeggere: number;
  nNotificheLetteOArchiviate: number;
  isBR59: boolean;

  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedBeneficiari: boolean = true;
  loadedBeneficiariInit: boolean = true;
  loadedSaveBeneficiario: boolean = true;
  loadedBandi: boolean = true;
  loadedProgetti: boolean = true;
  loadedAttivita: boolean = true;
  loadedNotifiche: boolean = true;
  loadedStart: boolean = true;
  loadedBR59: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};
  subscriptionBen: Subscription;

  firstTime: boolean = true;
  isCostanteFinpiemonte: boolean = false;
  isBandoFinpiemonte: boolean = false;
  isFinpiemonte: boolean;

  get descBreveTaskComunicazioneRinuncia() {
    return Constants.DESCR_BREVE_TASK_COM_RINUNCIA;
  }

  get descBreveTaskValidazione() {
    return Constants.DESCR_BREVE_TASK_VALIDAZIONE;
  }

  get descBreveTaskValidazioneIntegrativa() {
    return Constants.DESCR_BREVE_TASK_VALIDAZIONE_INTEGRATIVA;
  }

  get descBreveTaskVerificaRichErogazione() {
    return Constants.DESCR_BREVE_TASK_VERIF_RICH_EROGAZIONE;
  }

  constructor(
    private configService: ConfigService,
    private router: Router,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private attivitaService: AttivitaService,
    private handleExceptionService: HandleExceptionService,
    private homeService: HomeService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (this.user && this.firstTime) {
        this.firstTime = false;
        if (this.user.beneficiariCount) {
          if (this.user.beneficiarioSelezionato) {
            this.beneficiario = this.user.beneficiarioSelezionato;
            this.beneficiarioSelected = new FormControl();
            this.beneficiarioSelected.setValue(this.beneficiario);
            this.beneficiarioGroup.controls['beneficiarioControl'].setValue(this.beneficiario);
            if (this.user.beneficiariCount <= 50) {
              this.loadBeneficiari(true);
            } else {
              this.loadBeneficiari(false, this.beneficiario.denominazione);
            }
            this.loadBandi();
          } else {
            this.loadBeneficiari(true);
          }
        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_ATTIVITA_DA_SVOLGERE;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  timeout: any;
  loadBeneficiari(init?: boolean, denomBenefCambia?: string) {
    this.resetBandi();
    this.resetProgetti();
    this.loadedBeneficiari = false;
    if (init || denomBenefCambia?.length) {
      this.loadedBeneficiariInit = false;
    }
    if (this.beneficiarioGroup.controls['beneficiarioControl']?.value?.length >= 3 || init || denomBenefCambia?.length) {
      if (this.timeout) {
        clearTimeout(this.timeout);
        this.timeout = null;
      }
      this.timeout = setTimeout(() => {
        if (this.subscriptionBen) {
          this.subscriptionBen.unsubscribe();
        }
        if (typeof (this.beneficiarioGroup.controls['beneficiarioControl']?.value) == "string" || init || denomBenefCambia?.length) {
          let denominazione: string = typeof (this.beneficiarioGroup.controls['beneficiarioControl']?.value) == "string" ? this.beneficiarioGroup.controls['beneficiarioControl']?.value : "";
          denominazione = denomBenefCambia?.length ? denomBenefCambia : denominazione;
          this.subscriptionBen = this.userService.getBeneficiariByDenom(denominazione).subscribe(data => {
            if (data) {
              console.log(data);
              this.beneficiari = data;
              if (init && this.beneficiari.length == 1) {
                this.user.beneficiarioSelezionato = this.beneficiari[0];
                this.selectBeneficiario(this.user.beneficiarioSelezionato);
                if (this.user.beneficiariCount === 1) {
                  this.beneficiarioGroup.get("beneficiarioControl").disable();
                }
              }
              this.filteredOptions = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
                .pipe(
                  startWith(''),
                  map(value => typeof value === 'string' || value == null ? value : value.denominazione),
                  map(name => name ? this._filter(name) : this.beneficiari.slice())
                );
            }
            this.loadedBeneficiari = true;
            if (init || denomBenefCambia?.length) {
              this.loadedBeneficiariInit = true;
            }
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
        } else {
          this.loadedBeneficiari = true;
          this.loadedBeneficiariInit = true;
        }
      }, init || denomBenefCambia?.length ? 0 : 1000);
    }
  }

  selectBeneficiario(beneficiario?: BeneficiarioSec) {
    this.loadedSaveBeneficiario = false;
    if (!beneficiario) {
      this.beneficiario = this.beneficiarioGroup.controls['beneficiarioControl'].value;
      this.user.beneficiarioSelezionato = this.beneficiarioGroup.controls['beneficiarioControl'].value;
    }
    this.subscribers.salvaBeneficiarioSelezionato = this.userService.salvaBeneficiarioSelezionato(this.beneficiario);
    this.subscribers.salvaBeneficiarioSelezionato$ = this.userService.salvaBeneficiarioSelezionatoSubject.subscribe(data => {
      this.homeService.getOperazioni();
      if (this.idOperazione == Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE) {
        this.router.navigate([`drawer/${this.idOperazione}/attivita`]);
      }
      this.loadedSaveBeneficiario = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

  }

  displayFn(beneficiario: BeneficiarioSec): string {
    return (beneficiario && beneficiario.denominazione ? beneficiario.denominazione : '')
      + (beneficiario && beneficiario.denominazione && beneficiario.codiceFiscale ? ' - ' : '')
      + (beneficiario && beneficiario.codiceFiscale ? beneficiario.codiceFiscale : '');
  }

  private _filter(denominazione: string): BeneficiarioSec[] {
    const filterValue = denominazione.toLowerCase();
    //rimosso punto interrogativo dopo denominazione (Lorenzo)
    return this.beneficiari.filter(option => option.denominazione.toLowerCase().includes(filterValue) || option.codiceFiscale.toLowerCase().includes(filterValue));
  }

  check() {
    setTimeout(() => {
      if (!this.beneficiarioSelected || (this.beneficiarioGroup.controls['beneficiarioControl'] && this.beneficiarioSelected.value !== this.beneficiarioGroup.controls['beneficiarioControl'].value)) {
        this.beneficiarioGroup.controls['beneficiarioControl'].setValue(null);
        this.beneficiarioSelected = new FormControl();
      }
    }, 200);
  }

  click(event: any) {
    this.beneficiarioSelected.setValue(event.option.value);
    this.loadBandi();
  }

  resetBandi() {
    this.bandi = new Array<BandoVO>();
    this.bandoSelected = null;
  }

  resetProgetti() {
    this.progettiSearched = new Array<ProgettoVO>();
    this.progetti = new Array<ProgettoVO>();
    this.progetti.push(new ProgettoVO(0, "", "Tutti", "", null));
    this.progettoSelected = this.progetti[0];
  }

  loadBandi() {
    this.resetProgetti();
    this.loadedBandi = false;
    this.attivitaService.getBandi(this.beneficiarioSelected.value.idBeneficiario).subscribe(data => {
      if (data) {
        this.bandi = data;
        this.ricercaEsistente();
      }
      this.loadedBandi = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  ricercaEsistente() {
    this.loadedAttivita = false;
    this.subscribers.ricercaEsistente = this.attivitaService.ricercaAttivitaPrecedente(this.beneficiarioSelected.value.idBeneficiario).subscribe(data => {
      if (data && data.esisteRicercaPrecedente) {
        this.esisteRicercaPrecedente = data.esisteRicercaPrecedente;
        this.bandoSelected = this.bandi.find(b => b.progrBandoLineaIntervento === data.progrBandoLineaIntervento);
        this.descAttivita = data.descAttivita ? data.descAttivita : '';
        if (this.bandoSelected) {
          this.onSelectBando(data.listaAttivita, data.idProgetto);
          this.checkRegole();
        }
        if (this.esisteRicercaPrecedente) {
          this.selectBeneficiario();
        }
        this.criteriRicercaOpened = false;
      }
      this.loadedAttivita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero di una ricerca precedente.");
      this.loadedAttivita = true;
    });
  }

  checkRegole() {
    this.loadedBR59 = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.bandoSelected.progrBandoLineaIntervento, "BR59").subscribe(res => {
      this.isBR59 = res;
      this.loadedBR59 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedBR59 = true;
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  onSelectBando(listaAttivita?: Array<AttivitaVO>, idProgetto?: number) {
    this.loadedProgetti = false;
    this.progettiSearched = new Array<ProgettoVO>();
    this.progetti.splice(1, this.progetti.length - 1);
    this.progettoSelected = this.progetti[0];
    this.subscribers.progetti = this.attivitaService.getProgetti(this.beneficiarioSelected.value.idBeneficiario, this.bandoSelected.progrBandoLineaIntervento).subscribe(data => {
      if (data) {
        for (let d of data) {
          this.progetti.push(d);
        }
        if (idProgetto) {
          this.progettoSelected = this.progetti.find(p => p.id === idProgetto);
        }
        if (this.esisteRicercaPrecedente && listaAttivita) {
          if (this.progettoSelected.id === 0) {
            this.progettiSearched = this.progetti.slice(1);
          } else {
            this.progettiSearched = new Array<ProgettoVO>();
            this.progettiSearched.push(this.progettoSelected);
          }
          //la procedura getAttivitaBen ritorna le attivita dei primi 20 progetti quando la chiamo con start=1 o in ricerca precedente
          //devono chiamarla N-1 volte con start = 0 per avere le attività dei progetti restanti, con N = numProgetti / 20 arrotondato in eccesso
          let numCycles = this.progettoSelected.id === 0 ? Math.ceil(this.progetti.length / 20) : 1;
          this.setAttivitaOnProgetti(listaAttivita);
          if (numCycles > 1) {
            this.getAttivitaRecursive(numCycles, 1);
          }
          this.loadNotifiche();
        }
      }
      this.loadedProgetti = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });

    this.subscribers.isCostFinp = this.userService.isCostanteFinpiemonte().subscribe(data => {
      if (data) {
        this.isCostanteFinpiemonte = data;
      } else {
        this.isCostanteFinpiemonte = false;
      }
    });

    this.subscribers.isBandoFinp = this.userService.isBandoCompetenzaFinpiemonte(this.bandoSelected.progrBandoLineaIntervento).subscribe(data => {
      if (data) {
        this.isBandoFinpiemonte = data;
      } else {
        this.isBandoFinpiemonte = false;
      }
    });

  }

  onSelectProgetto() {
    this.progettiSearched = new Array<ProgettoVO>();
  }

  setAttivitaOnProgetti(attivitaList: Array<AttivitaVO>) {
    for (let p of this.progettiSearched.filter(pr => attivitaList ? attivitaList.map(a => a.idProgetto).includes(pr.id) : [])) {
      p.attivita = new Array<AttivitaVO>();
      for (let d of attivitaList) {
        if (d.idProgetto == p.id
          && d.descrBreveTask !== "MOD-EROG"
          && d.descrBreveTask !== "MOD-REVOCA"
          && d.descrBreveTask !== "MOD-RECUPERO") {  //attività in && DA RIMUOVERE QUANDO VERRA RIMOSSA L'ATTIVITA' CORRISPONDENTE DALLE ATTIVITA' DI PROCESSO
          if (d.descrTask.includes("primo acconto")) {
            d.descrTask = "Avvio lavori / Richiesta acconto";
          }
          p.attivita.push(d);
        }
      }

      //se compaiono contemporanemante rendicontazione e rendicontazione integrativa, nascondo la rendicontazione
      let rendic = p.attivita.find(a => a.descrBreveTask === Constants.DESCR_BREVE_TASK_RENDICONTAZIONE);
      let rendicInteg = p.attivita.find(a => a.descrBreveTask === Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_INTEGRATIVA);
      if (rendic && rendicInteg) {
        p.attivita = p.attivita.filter(a => a.descrBreveTask !== Constants.DESCR_BREVE_TASK_RENDICONTAZIONE);
      }
      //se compaiono contemporanemante rendicontazione cultura e rendicontazione integrativa cultura, nascondo la rendicontazione cultura
      let rendicCultura = p.attivita.find(a => a.descrBreveTask === Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_CULTURA);
      let rendicIntegCultura = p.attivita.find(a => a.descrBreveTask === Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_INTEGRATIVA_CULTURA);
      if (rendicCultura && rendicIntegCultura) {
        p.attivita = p.attivita.filter(a => a.descrBreveTask !== Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_CULTURA);
      }
    }
  }

  ricerca() {
    this.resetMessageError();
    this.checkRegole();
    this.selectBeneficiario();
    this.loadedAttivita = false;
    if (this.progettoSelected.id === 0) {
      this.progettiSearched = this.progetti.slice(1);
    } else {
      this.progettiSearched = new Array<ProgettoVO>();
      this.progettiSearched.push(this.progettoSelected);
    }
    //la procedura getAttivitaBen ritorna le attivita dei primi 20 progetti quando la chiamo con start=1
    //devono chiamarla N-1 volte con start = 0 per avere le attività dei progetti restanti, con N = numProgetti / 20 arrotondato in eccesso
    let numCycles = this.progettoSelected.id === 0 ? Math.ceil(this.progetti.length / 20) : 1;
    this.subscribers.attivita = this.attivitaService.getAttivita
      (this.beneficiarioSelected.value.idBeneficiario, this.bandoSelected.progrBandoLineaIntervento, this.descAttivita, this.progettoSelected.id, 1).subscribe(data => {
        if (data) {
          this.setAttivitaOnProgetti(data);
          if (numCycles > 1) {
            this.getAttivitaRecursive(numCycles, 1);
          } else {
            this.loadedAttivita = true;
          }
        } else {
          this.loadedAttivita = true;
        }
        this.criteriRicercaOpened = false;

      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
    this.loadNotifiche();

  }

  getAttivitaRecursive(numCycles: number, count: number) {
    this.loadedAttivita = false;
    this.subscribers.attivita = this.attivitaService.getAttivita
      (this.beneficiarioSelected.value.idBeneficiario, this.bandoSelected.progrBandoLineaIntervento, this.descAttivita, this.progettoSelected.id, 0).subscribe(data => {
        if (data) {
          this.setAttivitaOnProgetti(data);
          count++;
          if (count < numCycles) {
            this.getAttivitaRecursive(numCycles, count);
          } else {
            this.loadedAttivita = true;
          }
        }
        this.criteriRicercaOpened = false;
      }, err => {
        this.handleExceptionService.handleBlockingError(err);
      });
  }

  loadNotifiche() {
    this.loadedNotifiche = false;
    this.nNotificheDaLeggere = 0;
    this.nNotificheLetteOArchiviate = 0;
    this.subscribers.notifiche = this.attivitaService.countNotifiche(this.bandoSelected.progrBandoLineaIntervento,
      (this.progettoSelected != null && this.progettoSelected.id != 0) ? this.progettoSelected.id : null, this.progetti.slice(1))
      .subscribe(data => {
        if (data) {
          this.nNotificheDaLeggere = data[0];
          this.nNotificheLetteOArchiviate = data[1];

        }
        this.loadedNotifiche = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
      });
  }

  goToNotifiche() {
    this.router.navigate(["drawer/" + this.idOperazione + "/notifiche", this.bandoSelected.progrBandoLineaIntervento, this.progettoSelected.id]);
  }

  goToAttivita(idProgetto: number, attivita: AttivitaVO) {
    this.resetMessageError();
    this.loadedStart = false;
    this.subscribers.start = this.attivitaService.startAttivita(idProgetto, attivita.descrBreveTask).subscribe(data => {
      switch (attivita.descrBreveTask) {
        case Constants.DESCR_BREVE_TASK_RENDICONTAZIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE, "rendicontazione", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_INTEGRATIVA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE, "rendicontazione/integrativa", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_CULTURA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA, "rendicontazione-a20", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RENDICONTAZIONE_INTEGRATIVA_CULTURA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RENDICONTAZIONE_CULTURA, "rendicontazione-a20/integrativa", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CHECKLIST:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CHECKLIST, "checklist", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_VALIDAZIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VALIDAZIONE, "validazione", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask, attivita.idBusiness);
          break;
        case Constants.DESCR_BREVE_TASK_VALIDAZIONE_INTEGRATIVA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VALIDAZIONE_INTEGRATIVA, "validazione", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask, attivita.idBusiness);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_SPESA_VALIDATA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA, "gestioneSpesaValidata", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_INTEGRAZIONE_RENDICONTAZIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_INTEGRAZIONE_RENDICONTAZIONE, "integrazioneRendicontazione",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_INTEGRAZIONI:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_INTEGRAZIONI, "gestioneIntegrazioni",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_REL_TEC_I:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_REL_TEC_I, "relazioneTecnica",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_REL_TEC_F:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_REL_TEC_F, "relazioneTecnica",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_VAL_REL_TEC_I:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VAL_REL_TEC_I, "validazioneRelazioneTecnica",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_VAL_REL_TEC_F:
          this.navigateToAttivitaIdBando(this.configService.getPbwebURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VAL_REL_TEC_F, "validazioneRelazioneTecnica",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_AFFIDAMENTI:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_AFFIDAMENTI, "affidamenti", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_AFFIDAMENTI_BEN:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_AFFIDAMENTI_BEN, "affidamenti", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CRONOPROGRAMMA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CRONOPROGRAMMA, "cronoprogramma", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CRONOPROGRAMMA_AVVIO:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CRONOPROGRAMMA_AVVIO, "cronoprogrammaAvvio", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RETTIFICA_CRONOPROGRAMMA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RETTIFICA_CRONOPROGRAMMA, "cronoprogramma", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CRONOPROGRAMMA_FASI:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CRONOPROGRAMMA_FASI, "cronoprogrammaFasi", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_INDICATORI:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_INDICATORI, "indicatori", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_INDICATORI_AVVIO:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_INDICATORI_AVVIO, "indicatoriAvvio", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RETTIFICA_INDICATORI:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RETTIFICA_INDICATORI, "indicatori", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_VISUALIZZA_INDICATORI:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VISUALIZZA_INDICATORI, "indicatori", idProgetto,
            attivita.descrBreveTask, attivita.descrTask, "sif");
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_APPALTI:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_APPALTI, "appalti", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_QUADRO_PREVISIONALE:
          this.navigateToAttivita(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_QUADRO_PREVISIONALE, "quadroPrevisionale", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO, "rimodulazioneContoEconomico",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_PROPOSTA_RIMODULAZIONE_CONTO_ECONOMICO, "rimodulazioneContoEconomico",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_ISTRUTTORIA, "rimodulazioneContoEconomico",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RICHIESTA_CONTO_ECONOMICO_DOMANDA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_CONTO_ECONOMICO_DOMANDA, "rimodulazioneContoEconomico",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO_CULTURA:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO_CULTURA, "rimodulazioneContoEconomicoA20",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_QTES:
          this.navigateToAttivitaIdBando(this.configService.getPbwebrceURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_QTES, "gestioneQtes",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_EROGAZIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_EROGAZIONE,
            "erogazione", idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_RICHIESTA_EROGAZIONE:
          this.navigateToAttivitaRichiestaErogazione(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RICHIESTA_EROGAZIONE,
            "avvio-lavori-richiesta-erogazione-acconto", idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_VERIF_RICH_EROGAZIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_VERIF_RICH_EROGAZIONE,
            "verifica-richiesta-erogazione", idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask, attivita.idBusiness);
          break;
        case Constants.DESCR_BREVE_TASK_DATI_PROGETTO:
          this.navigateToAttivitaIdBando(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_DATI_PROGETTO, "dati-progetto", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_FIDEIUSSIONI:
          this.navigateToAttivita(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_FIDEIUSSIONI, "fideiussioni", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_COM_RINUNCIA:
          this.navigateToAttivitaIdBando(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_COMUNICAZIONE_RINUNCIA, "rinuncia", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_DISIMPEGNI:
          this.navigateToAttivita(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_DISIMPEGNI, "disimpegni", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_REGISTRO_CONTROLLI:
          this.navigateToAttivita(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_REGISTRO_CONTROLLI, "controlli", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_REVOCHE:
          this.navigateToAttivitaIdBando(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_REVOCHE, "revoche", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_GESTIONE_RECUPERI:
          this.navigateToAttivita(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_RECUPERI, "recuperi", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CHIUSURA_PROGETTO:
          this.navigateToAttivita(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CHIUSURA_PROGETTO, "chiusura-progetto", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CHIUSURA_UFFICIO_PROGETTO:
          this.navigateToAttivita(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CHIUSURA_UFFICIO_PROGETTO, "chiusura-ufficio-progetto", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CHIUSURA_RINUNCIA_PROGETTO:
          this.navigateToAttivita(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CHIUSURA_RINUNCIA_PROGETTO, "chiusura-progetto-rinuncia", idProgetto,
            attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_SOPPRESSIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbweberogURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_SOPPRESSIONE, "soppressione", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_LIQUIDAZIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbwebfinURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_LIQUIDAZIONE, "liquidazione", idProgetto,
            this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CONSULTAZIONE_ATTI_LIQUIDAZIONE:
          this.navigateToAttivitaIdBando(this.configService.getPbwebfinURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CONSULTAZIONE_ATTI_LIQUIDAZIONE, "consultazioneAtti",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CONTRODEDUZIONI:
          this.navigateToAttivitaIdBando(this.configService.getPbgestfinboURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CONTRODEDUZIONI, "controdeduzioni",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        case Constants.DESCR_BREVE_TASK_CONTESTAZIONI:
          this.navigateToAttivitaIdBando(this.configService.getPbgestfinboURL(), Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CONTESTAZIONI, "contestazioni",
            idProgetto, this.bandoSelected.progrBandoLineaIntervento, attivita.descrBreveTask, attivita.descrTask);
          break;
        default:
          break;
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedStart = true;
      this.showMessageError("ATTENZIONE: non è stato possibile accedere all'attività.");
    });
  }

  navigateToAttivita(configComponentURL: string, idAttivita: number, destComponentPath: string, idProgetto: number, descBreveTask: string, descTask: string, altroParam?: string) {
    let url: string = `${configComponentURL}/#/drawer/${idAttivita}/${destComponentPath}/${idProgetto}` + (altroParam?.length ? `/${altroParam}` : ``) + `?idSg=${this.user.idSoggetto}`
      + `&idSgBen=${this.user.beneficiarioSelezionato.idBeneficiario}&idU=${this.user.idUtente}&role=${this.user.codiceRuolo}`
      + `&idPrj=${idProgetto}&taskIdentity=${descBreveTask}&taskName=${descTask}&wkfAction=StartTaskNeoFlux`;
    window.location.href = url;
  }

  navigateToAttivitaIdBando(configComponentURL: string, idAttivita: number, destComponentPath: string, idProgetto: number, idBando: number, descBreveTask: string, descTask: string, idBusiness?: number) {
    let url: string = `${configComponentURL}/#/drawer/${idAttivita}/${destComponentPath}/${idProgetto}/${idBando}` + (idBusiness ? `/${idBusiness}` : '')
      + `?idSg=${this.user.idSoggetto}&idSgBen=${this.user.beneficiarioSelezionato.idBeneficiario}&idU=${this.user.idUtente}&role=${this.user.codiceRuolo}`
      + `&idPrj=${idProgetto}&taskIdentity=${descBreveTask}&taskName=${descTask}&wkfAction=StartTaskNeoFlux`;
    window.location.href = url;
  }

  navigateToAttivitaRichiestaErogazione(configComponentURL: string, idAttivita: number, destComponentPath: string, idProgetto: number, idBandoLinea: number, descBreveTask: string,
    descTask: string) {
    let codCausale: string;
    if (descTask.includes("Avvio lavori / Richiesta acconto")) {
      codCausale = Constants.DESC_BREVE_CAUSALE_PRIMO_ACCONTO;
    } else if (descTask.includes("saldo")) {
      codCausale = Constants.DESC_BREVE_CAUSALE_SALDO;
    } else if (descTask.includes("secondo acconto")) {
      codCausale = Constants.DESC_BREVE_CAUSALE_SECONDO_ACCONTO;
    } else if (descTask.includes("ulteriore acconto")) {
      codCausale = Constants.DESC_BREVE_CAUSALE_ULTERIORE_ACCONTO;
    }
    let url: string = `${configComponentURL}/#/drawer/${idAttivita}/${destComponentPath}/${idProgetto}/${idBandoLinea}/${codCausale}`
      + `?idSg=${this.user.idSoggetto}&idSgBen=${this.user.beneficiarioSelezionato.idBeneficiario}&idU=${this.user.idUtente}&role=${this.user.codiceRuolo}`
      + `&idPrj=${idProgetto}&taskIdentity=${descBreveTask}&taskName=${descTask}&wkfAction=StartTaskNeoFlux`;
    window.location.href = url;
  }


  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  goToDatiProgettoEAttivitaPregresse(idProgetto: number) {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  isLoading() {
    if (!this.loadedBeneficiariInit || !this.loadedSaveBeneficiario ||
      !this.loadedBandi || !this.loadedProgetti || !this.loadedAttivita ||
      !this.loadedStart) {
      return true;
    }
    return false;
  }
}
