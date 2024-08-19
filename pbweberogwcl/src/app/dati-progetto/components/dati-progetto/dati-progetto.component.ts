/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { InizializzazioneService } from "src/app/shared/services/inizializzazione.service";
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from "../../../core/commons/dto/user-info";
import { FileDTO } from "../../commons/dto/file-dto";
import { SoggettoProgettoDTO } from "../../commons/dto/soggetto-progetto-dto";
import { CodiceDescrizione } from "../../commons/models/codice-descrizione";
import { DatiProgettoService } from "../../services/dati-progetto.service";
import { DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { DatiGeneraliComponent } from '../dati-generali/dati-generali.component';
import { AllegatiComponent } from '../allegati/allegati.component';
import { DatiBenefSediComponent } from '../dati-benef-sedi/dati-benef-sedi.component';
import { SoggettiComponent } from '../soggetti/soggetti.component';
import { MatTabGroup } from '@angular/material/tabs';
import { BlocchiComponent } from '../blocchi/blocchi.component';
import { DatiAggiuntiviComponent } from '../dati-aggiuntivi/dati-aggiuntivi.component';
import { DatiSif } from '../../commons/dto/dati-sif';
import { DatiSifComponent } from '../dati-sif/dati-sif.component';

@Component({
  selector: 'app-dati-progetto',
  templateUrl: './dati-progetto.component.html',
  styleUrls: ['./dati-progetto.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DatiProgettoComponent implements OnInit, AfterViewChecked {

  title: string = 'Gestione dati del progetto';

  idProgetto: number;
  idBando: number; //progrBandoLineaIntervento
  user: UserInfoSec;
  codiceProgetto: string;
  userBeneficiarioSelezionatoCodiceFiscale: string;
  isBR42: boolean;
  isBR59: boolean;
  isBR64: boolean;
  isHideTabSedi: boolean;
  allegati: Array<FileDTO>;
  soggettiProgetto: SoggettoProgettoDTO[];
  isBenPersonaFisica: boolean;
  bandoCompetenzaFinpiemonte: boolean;
  costanteFinpiemonte: boolean;
  isProgrammazione2127: boolean;
  isBandoSif: boolean;
  datiSif: DatiSif;

  @ViewChild(DatiGeneraliComponent) datiGeneraliComponent: DatiGeneraliComponent;
  @ViewChild(DatiSifComponent) datiSifComponent: DatiSifComponent;
  @ViewChild(AllegatiComponent) allegatiComponent: AllegatiComponent;
  @ViewChild(DatiBenefSediComponent) datiBenefSediComponent: DatiBenefSediComponent;
  @ViewChild(SoggettiComponent) soggettiComponent: SoggettiComponent;
  @ViewChild(BlocchiComponent) blocchiComponent: BlocchiComponent;
  @ViewChild(DatiAggiuntiviComponent) datiAggiuntiviComponent: DatiAggiuntiviComponent;

  // MESSAGGI SUCCESS E ERROR
  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  // LOADED
  loadedChiudiAttivita: boolean = true;
  loadedCodiceProgetto: boolean;
  loadedCostanteFinpiemonte: boolean;
  loadedBandoFinpiemonte: boolean;
  loadedAllegati: boolean;
  loadedSoggettiProgetto: boolean;
  loadedRegolaBR42: boolean;
  loadedRegolaBR59: boolean;
  loadedRegolaBR64: boolean;
  loadedLinea2127: boolean;
  loadedIsSif: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  @ViewChild('tabs', { static: false }) tabs: MatTabGroup;

  constructor(
    private configService: ConfigService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private inizializzazioneService: InizializzazioneService,
    private datiProgettoService: DatiProgettoService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private dialog: MatDialog,
    private sharedService: SharedService,
    private cdRef: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.user.idIride) {
          this.subscribers.router = this.activatedRoute.params.subscribe(params => {
            this.idProgetto = +params['id'];
            this.idBando = +params['idBando'];
            this.loadData();
            this.getAllegati();
          });
        }
      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_GEST_DATI_PROG;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }

  isTabDatiSifVisible(): boolean {
    return this.isProgrammazione2127 && this.isBandoSif;
  }

  isTabDatiAggiuntiviVisible(): boolean {
    return this.isBR59;
  }

  isTabAllegatiVisible(): boolean {
    return (this.isBR42 && this.allegati?.length > 0) || this.isBR64;
  }

  isTabBeneficiarioSediVisible(): boolean {
    return !this.isHideTabSedi;
  }

  isTabBlocchiVisible(): boolean {
    return this.bandoCompetenzaFinpiemonte && this.costanteFinpiemonte;
  }

  //quanti valori a true ci sono nell'array
  countBooleansTrue(array: boolean[]): number {
    let count: number = 0;
    if (array?.length > 0) {
      for (let a of array) {
        if (a) {
          count++;
        }
      }
    }
    return count;
  }

  onTabChange() {
    this.resetMessageError();
    this.resetMessageSuccess();
    switch (this.tabs.selectedIndex) {
      case 0: // dati generali
        //non serve: basta farlo una volta quando si carica la pagina
        //this.datiGeneraliComponent.loadData();
        break;
      case 1: // dati sif / dati aggiuntivi / allegati / beneficiario / soggetti
        if (this.isTabDatiSifVisible()) {
          this.datiSifComponent.loadData()
        } else if (this.isTabDatiAggiuntiviVisible()) {
          this.datiAggiuntiviComponent.loadData(true);
        } else if (this.isTabAllegatiVisible()) {
          //tab allegati non ha bisogno di un caricamento perche' la get viene eseguita nell'ngOnInit
        } else if (this.isTabBeneficiarioSediVisible()) {
          this.datiBenefSediComponent.loadData();
        } else {
          this.soggettiComponent.loadData(true);
        }
        break;
      case 2: // dati aggiuntivi / allegati / beneficiario / soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible()) {
          this.datiAggiuntiviComponent.loadData(true);
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible()]) === 1
          && this.isTabAllegatiVisible()) {
          //tab allegati non ha bisogno di un caricamento perche' la get viene eseguita nell'ngOnInit
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible()]) === 1
          && this.isTabBeneficiarioSediVisible()) {
          this.datiBenefSediComponent.loadData();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible(), this.isTabBeneficiarioSediVisible()]) === 1) {
          this.soggettiComponent.loadData(true);
        } else if (this.isTabBlocchiVisible()) {
          this.blocchiComponent.loadData(true);
        }
        break;
      case 3: // allegati / beneficiario / soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible() && this.isTabAllegatiVisible()) {
          //tab allegati non ha bisogno di un caricamento perche' la get viene eseguita nell'ngOnInit
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible()]) === 2
          && this.isTabBeneficiarioSediVisible()) {
          this.datiBenefSediComponent.loadData();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible()]) === 1) {
          this.soggettiComponent.loadData(true);
        } else if (this.isTabBlocchiVisible()) {
          this.blocchiComponent.loadData(true);
        }
        break;
      case 4: // beneficiario / soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible() && this.isTabAllegatiVisible()
          && this.isTabBeneficiarioSediVisible()) {
          this.datiBenefSediComponent.loadData();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible(), this.isTabBeneficiarioSediVisible()]) === 3) {
          this.soggettiComponent.loadData(true);
        } else if (this.isTabBlocchiVisible()) {
          this.blocchiComponent.loadData(true);
        }

        break;
      case 5: // soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible() && this.isTabAllegatiVisible()
          && this.isTabBeneficiarioSediVisible()) {
          this.soggettiComponent.loadData(true);
        } else if (this.isTabBlocchiVisible()) {
          this.blocchiComponent.loadData(true);
        }
        break;
      case 5: //  blocchi
        if (this.isTabBlocchiVisible()) {
          this.blocchiComponent.loadData(true);
        }
        break;
      default:
        break;
    }
  }

  loadSoggettiProgetto(fromTabSoggetti?: boolean) {
    this.loadedSoggettiProgetto = false;
    this.subscribers.soggettiProgetto = this.datiProgettoService.getSoggettiProgetto(this.user.idUtente, this.idProgetto, this.user.codiceRuolo)
      .subscribe(res => {
        if (res as SoggettoProgettoDTO[]) {
          this.soggettiProgetto = res;
          this.loadedRegolaBR42 = false;
          this.subscribers.regola = this.userService.isRegolaApplicabileForBandoLinea(this.idBando, "BR42").subscribe(res => {
            if (res) {
              this.isBR42 = res;
            }
            if (this.soggettiProgetto.find(s => s.idTipoAnagrafica === 1 && s.idTipoSoggetto === 1)) {//BENEFICIARIO PERSONA FISICA
              this.isHideTabSedi = true;
              this.isBenPersonaFisica = true;
            }

            if (fromTabSoggetti) {
              this.soggettiComponent.loadData();
            }
            if (this.datiProgettoService.tabSoggetti) {
              //se sono nella pagina nuovo/modifica soggetto, quando torno indietro con il navigate devo ritornare sul tab dei soggetti automaticamente
              if (!this.isBR59 && !this.isTabAllegatiVisible() && this.isHideTabSedi) {//non ci sono i dati agg, nè gli allegati, nè il benef 
                this.tabs.selectedIndex = 1;
              } else if ((!this.isBR59 && this.isTabAllegatiVisible() && this.isHideTabSedi)//ci sono gli allegati ma non il benef nè i dati agg
                || (!this.isBR59 && !this.isTabAllegatiVisible() && !this.isHideTabSedi)//non ci sono gli allegati nè i dati agg, ma c'è il benef 
                || (this.isBR59 && !this.isTabAllegatiVisible() && this.isHideTabSedi)) { //non ci sono gli allegati nè il benef, ma ci sono i dati agg
                this.tabs.selectedIndex = 2;
              } else if (this.isBR59 && this.isTabAllegatiVisible() && !this.isHideTabSedi) { //ci sono sia dati agg, sia allegati, sia benef
                this.tabs.selectedIndex = 4;
              } else {
                this.tabs.selectedIndex = 3;
              }
              this.datiProgettoService.tabSoggetti = false;
            }
            this.loadedRegolaBR42 = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadedRegolaBR42 = true;
          });
        }

        this.loadedSoggettiProgetto = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dell'elenco dei soggetti.");
        this.loadedSoggettiProgetto = true;
      });
  }

  loadData() {
    this.loadedCodiceProgetto = false;
    this.subscribers.codiceProgetto = this.inizializzazioneService.getCodiceProgetto(this.idProgetto).subscribe(res => {
      if (res) {
        this.codiceProgetto = res;
        if (this.user && this.user.beneficiarioSelezionato && this.user.beneficiarioSelezionato.codiceFiscale) {
          this.userBeneficiarioSelezionatoCodiceFiscale = this.user.beneficiarioSelezionato.codiceFiscale
        }
      }
      this.loadedCodiceProgetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero del codice progetto.");
      this.loadedCodiceProgetto = true;
    });
    this.loadedRegolaBR59 = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.idBando, "BR59").subscribe(res => {
      if (res) {
        this.isBR59 = res;
      }
      this.loadedRegolaBR59 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero della regola BR59.");
      this.loadedRegolaBR59 = true;
    });
    this.loadedRegolaBR64 = false;
    this.userService.isRegolaApplicabileForBandoLinea(this.idBando, "BR64").subscribe(res => {
      this.isBR64 = res;
      this.loadedRegolaBR64 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero della regola BR64.");
      this.loadedRegolaBR64 = true;
    });
    //load costante finpiemonte
    this.loadedCostanteFinpiemonte = false;
    this.userService.isCostanteFinpiemonte().subscribe(res => {
      this.costanteFinpiemonte = res
      this.loadedCostanteFinpiemonte = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero della costante finpiemonte.");
      this.loadedCostanteFinpiemonte = true;
    });
    //load competenza finpiemonte
    this.loadedBandoFinpiemonte = false;
    this.userService.isBandoCompetenzaFinpiemonte(this.idBando).subscribe(res => {
      this.bandoCompetenzaFinpiemonte = res;
      this.loadedBandoFinpiemonte = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica bando competenza finpiemonte.");
      this.loadedBandoFinpiemonte = true;
    });
    this.loadedLinea2127 = false;
    this.userService.hasProgettoLineaByDescBreve(this.idProgetto, Constants.DESC_BREVE_LINEA_INTERVENTO_POR_FESR_2021_2027).subscribe(res => {
      this.isProgrammazione2127 = res;
      if (this.isProgrammazione2127) {
        this.loadedIsSif = false;
        this.userService.isBandoSif(this.idBando).subscribe(data => {
          this.isBandoSif = data;
          this.loadedIsSif = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di verifica bando SIF.");
          this.loadedIsSif = true;
        });
      }

      this.loadedLinea2127 = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di verifica linea BANDIREGP.");
      this.loadedLinea2127 = true;
    });
  }

  getAllegati() {
    this.loadedAllegati = false;
    this.datiProgettoService.getFilesAssociatedProgetto(this.idProgetto).subscribe(data => {
      if (data) {
        this.allegati = data;
        this.loadSoggettiProgetto();
      }
      this.loadedAllegati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento degli allegati.");
      this.loadedAllegati = true;
    });
  }

  datiSifFromChild(dati: DatiSif) {
    if (this.isProgrammazione2127 && this.isBandoSif) {
      this.datiSif = dati;
    }
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

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_DATI_PROGETTO).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
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

  messageErrorFromChild(msg: string) {
    if (msg === null) {
      this.resetMessageError();
    } else {
      this.showMessageError(msg);
    }
  }

  messageSuccessFromChild(msg: string) {
    if (msg === null) {
      this.resetMessageSuccess();
    } else {
      this.showMessageSuccess(msg);
    }
  }

  messageWarningFromChild(msg: string) {
    if (msg === null) {
      this.resetMessageWarning();
    } else {
      this.showMessageWarning(msg);
    }
  }

  ngAfterViewChecked() {
    this.tabs.realignInkBar();
    this.cdRef.detectChanges();
  }

  isLoading() {
    let r: boolean = false;
    if (!this.loadedRegolaBR42 || !this.loadedSoggettiProgetto || !this.loadedChiudiAttivita || !this.loadedCostanteFinpiemonte
      || !this.loadedBandoFinpiemonte || !this.loadedLinea2127 || !this.loadedIsSif) {
      return true;
    }

    switch (this.tabs.selectedIndex) {
      case 0: // dati generali
        r = this.datiGeneraliComponent.isLoading();
        break;
      case 1: // dati sif / dati aggiuntivi / allegati / beneficiario / soggetti
        if (this.isTabDatiSifVisible()) {
          r = this.datiSifComponent.isLoading();
        } else if (this.isTabDatiAggiuntiviVisible()) {
          r = this.datiAggiuntiviComponent.isLoading();
        } else if (this.isTabAllegatiVisible()) {
          r = !this.loadedAllegati || this.allegatiComponent.isLoading();
        } else if (this.isTabBeneficiarioSediVisible()) {
          r = this.datiBenefSediComponent.isLoading();
        } else {
          r = this.soggettiComponent.isLoading();
        }
        break;
      case 2: // dati aggiuntivi / allegati / beneficiario / soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible()) {
          r = this.datiAggiuntiviComponent.isLoading();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible()]) === 1
          && this.isTabAllegatiVisible()) {
          r = !this.loadedAllegati || this.allegatiComponent.isLoading();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible()]) === 1
          && this.isTabBeneficiarioSediVisible()) {
          r = this.datiBenefSediComponent.isLoading();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible(), this.isTabBeneficiarioSediVisible()]) === 1) {
          r = this.soggettiComponent.isLoading();
        } else if (this.isTabBlocchiVisible()) {
          r = this.blocchiComponent.isLoading();
        }
        break;
      case 3: // allegati / beneficiario / soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible() && this.isTabAllegatiVisible()) {
          r = !this.loadedAllegati || this.allegatiComponent.isLoading();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible()]) === 2
          && this.isTabBeneficiarioSediVisible()) {
          r = this.datiBenefSediComponent.isLoading();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible()]) === 1) {
          r = this.soggettiComponent.isLoading();
        } else if (this.isTabBlocchiVisible()) {
          r = this.blocchiComponent.isLoading();
        }
        break;
      case 4: // beneficiario / soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible() && this.isTabAllegatiVisible()
          && this.isTabBeneficiarioSediVisible()) {
          r = this.datiBenefSediComponent.isLoading();
        } else if (this.countBooleansTrue([this.isTabDatiSifVisible(), this.isTabDatiAggiuntiviVisible(), this.isTabAllegatiVisible(), this.isTabBeneficiarioSediVisible()]) === 3) {
          r = this.soggettiComponent.isLoading();
        } else if (this.isTabBlocchiVisible()) {
          r = this.blocchiComponent.isLoading();
        }

        break;
      case 5: // soggetti / blocchi
        if (this.isTabDatiSifVisible() && this.isTabDatiAggiuntiviVisible() && this.isTabAllegatiVisible()
          && this.isTabBeneficiarioSediVisible()) {
          r = this.soggettiComponent.isLoading();
        } else if (this.isTabBlocchiVisible()) {
          r = this.blocchiComponent.isLoading();
        }
        break;
      case 5: //  blocchi
        if (this.isTabBlocchiVisible()) {
          r = this.blocchiComponent.isLoading();
        }
        break;
      default:
        break;
    }
    return r;
  }
}