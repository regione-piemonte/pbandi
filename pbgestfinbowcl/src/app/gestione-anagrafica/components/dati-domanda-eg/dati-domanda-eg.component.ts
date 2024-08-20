/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Sort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { Observable } from 'rxjs/internal/Observable';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AtlanteVO } from '../../commons/dto/atlante-vo';
import { DatiDomanda } from '../../commons/dto/dati-domanda';
import { DatiDomandaEG } from '../../commons/dto/model-dati-domanda-eg';
import { Nazioni } from '../../commons/dto/nazioni';
import { Regioni } from '../../commons/dto/regioni';
import { SoggettoCorrelatoVO } from '../../commons/dto/soggetto-correlatoVO';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { ModificaAnagraficaService } from '../../services/modifica-anagrafica.service';
import { map, startWith } from 'rxjs/operators';
import { UserService } from 'src/app/core/services/user.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { AnagAltriDati_Main } from '../../commons/dto/anagAltriDati_Main';
import { MatDialog } from '@angular/material/dialog';
import { DialogDettaglioAltreSediEGComponent } from '../dialog-dettaglio-altre-sedi-EG/dialog-dettaglio-altre-sedi-EG.component';
import { ArchivioFileService } from '@pbandi/common-lib/';
import { ConfigService } from 'src/app/core/services/config.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-dati-domanda-eg',
  templateUrl: './dati-domanda-eg.component.html',
  styleUrls: ['./dati-domanda-eg.component.scss']
})
export class DatiDomandaEGComponent implements OnInit, AfterViewChecked {

  public myForm: FormGroup;

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  idVersione: any;
  idSoggetto: number;
  idEnteGiuridico: any;
  numeroDomanda: any;
  spinner: boolean;
  datiEG: DatiDomandaEG;
  altreSediEG: Array<DatiDomandaEG>;
  public subscribers: any = {};
  isPresentata: boolean;
  editForm: boolean;

  nazioneData: Nazioni[] = [];
  filteredOptionsNazioni: Observable<Nazioni[]>;
  nazioneSelected: FormControl = new FormControl();

  regioneData: Regioni[] = [];
  filteredOptionsRegioni: Observable<Regioni[]>;
  regioneSelected: FormControl = new FormControl();

  provinciaData: AtlanteVO[] = [];
  filteredOptionsProvince: Observable<AtlanteVO[]>;
  provinciaSelected: FormControl = new FormControl();

  comuneData: AtlanteVO[] = [];
  filteredOptionsComuni: Observable<AtlanteVO[]>;
  comuneSelected: FormControl = new FormControl();

  loadedNazioni: boolean = true;
  loadedRegioni: boolean = true;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;
  dataSourceAltriDati_AntiMafia: any;
  dataSourceAltriDati_Bdna: any;
  ////sogg corr
  listaSoggettiCorr: Array<SoggettoCorrelatoVO> = new Array<SoggettoCorrelatoVO>();
  isSoggettiCorr: boolean;
  sortedData: SoggettoCorrelatoVO[];
  displayedColumnsSogg: string[] = ['tipologia', 'nag', 'denom', 'codiceFisc', 'ruolo', 'action'];
  displayedColumnsAltreSedi: string[] = ['tipoSede', 'indirizzo', 'comuneSede', 'action'];
  verificaSoggetto: any;
  setBackText: string = "Torna ai dati generali del beneficiario";
  columnsAltriDatiAntiMafia: string[] = ['dataEmissione', 'esito', 'dataScadenza', 'numProto', 'numeroDomanda', 'nomeDocumento', 'action'];
  columnsAltriDatiBdna: string[] = ['dataRicezione', 'numProto', 'numeroDomanda'];
  @ViewChild('tabs', { static: false }) tabs;
  idDomanda: any;
  ndg: any;
  messageError: string;
  isMessageErrorVisible: boolean;
  isProgetto: boolean;
  veroIdDomanda: string;
  idProgetto: string;
  idUtente: any;
  user: UserInfoSec;
  messageSuccess: any;
  isMessageSuccessVisible: boolean;
  altriDati: AnagAltriDati_Main; 
  idAntimafiaTable: boolean;
  isBdnaTable: boolean;
  nazioneSedeAmmData: Nazioni[];
  filteredOptionsNazioniSedeAmm: any;
  _filterNazioniSedeAmm: any;
  nazioneSedeAmmSelected: any;
  loadedNazioniSedeAmm: boolean;
  loadedRegioniSedeAmm: boolean;
  regioneSedeAmmData: Regioni[];
  filteredOptionsRegioniSedeAmm: any;
  regioneSedeAmmSelected: any;
  loadedProvinceSedeAmm: boolean;
  provinciaSedeAmmData: AtlanteVO[];
  filteredOptionsProvinceSedeAmm: any;
  provinciaSedeAmmSelected: any;
  comuneSedeAmmData: any[];
  loadedComuniSedeAmm: boolean;
  filteredOptionsComuniSedeAmm: any;
  comuneSedeAmmSelected: any;
  partitaIva: string;
  denominazione: string;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private datiDomandaService: DatiDomandaService,
    private anagBeneService: AnagraficaBeneficiarioService,
    private handleExceptionService: HandleExceptionService,
    private modificaAnagraficaService: ModificaAnagraficaService,
    private userService: UserService,
    private router: Router,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    public dialog: MatDialog) { }

  ngOnInit() {
    this.spinner = true;
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    // this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get('numeroDomanda');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');
    this.idSoggetto = + this.route.snapshot.queryParamMap.get('idSoggetto');
    this.verificaSoggetto = this.route.snapshot.queryParamMap.get('isEnteGiuridico');
    this.ndg = this.route.snapshot.queryParamMap.get('ndg');
    this.veroIdDomanda = this.route.snapshot.queryParamMap.get('veroIdDomanda');
    this.idProgetto = this.route.snapshot.queryParamMap.get('idProgetto');
    this.denominazione =this.route.snapshot.queryParamMap.get('denominazione');
    this.partitaIva =this.route.snapshot.queryParamMap.get('partitaIva');

    console.log("@@@@ id ente giuridico ");
    
    console.log(this.idEnteGiuridico);

    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
      }
    });

    // idSoggetto: this.idSoggetto,
    // ndg: this.ndg, // server per il goback con nome ndg
    // numeroDomanda: row.numeroDomanda, 
    // idEnteGiuridico: this.idEnteGiuridico,
    // isEnteGiuridico: this.verificaTipoSoggetto, // serve per il goback con il nome tipoSoggetto
    // idDomanda: this.numeroDomanda, // mi serve per il goback con nome idDomanda
    // idProgetto: this.idProgetto,  // mi serve per il goback con nome idProgetto
    // veroIdDomanda: this.veroIdDomanda// mi serve peril goback con nome numeroDomanda


    this.myForm = this.fb.group({
      //STATO DOMANDA
      numeroDomanda: new FormControl({ value: '', disabled: true }),
      statoDomanda: new FormControl({ value: '', disabled: true }),
      dataPresentazioneDomanda: new FormControl({ value: '', disabled: true }),
      //SEDE Intervento
      indirizzo: new FormControl({ value: '', disabled: true }),
      partitaIva: new FormControl({ value: '', disabled: true }),
      comune: new FormControl({ value: '', disabled: true }),
      cap: new FormControl({ value: '', disabled: true }),
      provincia: new FormControl({ value: '', disabled: true }),
      regione: new FormControl({ value: '', disabled: true }),
      nazione: new FormControl({ value: '', disabled: true }),
      
      //SEDE Amministrativa
      indirizzoSedeAmm: new FormControl({ value: '', disabled: true }),
      comuneSedeAmm: new FormControl({ value: '', disabled: true }),
      capSedeAmm: new FormControl({ value: '', disabled: true }),
      provinciaSedeAmm: new FormControl({ value: '', disabled: true }),
      regioneSedeAmm: new FormControl({ value: '', disabled: true }),
      nazioneSedeAmm: new FormControl({ value: '', disabled: true }),

      //RECAPITI
      telefono: new FormControl({ value: '', disabled: true }),
      fax: new FormControl({ value: '', disabled: true }),
      email: new FormControl({ value: '', disabled: true }),
      pec: new FormControl({ value: '', disabled: true }),
      //CONTO CORRENTE
      iban: new FormControl({ value: '', disabled: true }),
      banca: new FormControl({ value: '', disabled: true })
    });

    //SERVIZIO DA IMPLEMENTARE BE
    this.datiDomandaService.getDatiDomandaEG(this.idSoggetto, this.numeroDomanda).subscribe(data => {
      if (data) {
        this.datiEG = data;
        this.myForm.setValue({
          //STATO DOMANDA
          numeroDomanda: this.controlloCampoValido(this.datiEG.numeroDomanda),
          statoDomanda: this.controlloCampoValido(this.datiEG.statoDomanda),
          dataPresentazioneDomanda: this.controlloCampoValido(new Date(Date.parse(this.datiEG.dataPresentazioneDomanda))),
          //SEDE INTERVENTO:
          indirizzo: this.controlloCampoValido(this.datiEG.descIndirizzo),
          partitaIva: this.controlloCampoValido(this.datiEG.partitaIva),
          comune: this.controlloCampoValido(this.datiEG.descComune),
          provincia: this.controlloCampoValido(this.datiEG.descProvincia),
          cap: this.controlloCampoValido(this.datiEG.cap),
          regione: this.controlloCampoValido(this.datiEG.descRegione),
          nazione: this.controlloCampoValido(this.datiEG.descNazione),

          //SEDE AMMINISTRATIVA:
          indirizzoSedeAmm: this.controlloCampoValido(this.datiEG.descIndirizzoSedeAmm),
          comuneSedeAmm: this.controlloCampoValido(this.datiEG.descComuneSedeAmm),
          provinciaSedeAmm: this.controlloCampoValido(this.datiEG.descProvinciaSedeAmm),
          capSedeAmm: this.controlloCampoValido(this.datiEG.capSedeAmm),
          regioneSedeAmm: this.controlloCampoValido(this.datiEG.descRegioneSedeAmm),
          nazioneSedeAmm: this.controlloCampoValido(this.datiEG.descNazioneSedeAmm),

          //RECAPITI:
          telefono: this.controlloCampoValido(this.datiEG.telefono),
          fax: this.controlloCampoValido(this.datiEG.fax),
          email: this.controlloCampoValido(this.datiEG.email),
          pec: this.controlloCampoValido(this.datiEG.pec),

          //CONTO CORRENTE:
          banca: this.controlloCampoValido(this.datiEG.banca),
          iban: this.controlloCampoValido(this.datiEG.iban),
        });
        if (this.datiEG.statoDomanda == "Presentata") {
          this.isPresentata = true;
        }
        if (this.datiEG.idProgetto != null) {
          this.isProgetto = true
        }
        this.spinner = false;
      }
    });


    this.loadData();
    this.getElencoSoggettiCorrelati();
    this.loadAltriDati();
    this.loadAltreSedi();
  }
  loadAltriDati() {

    this.anagBeneService.getAltriDati(this.idSoggetto, this.numeroDomanda, this.idEnteGiuridico).subscribe(data => {
      if (data) {
        console.log(data);
        this.altriDati = data;
        this.dataSourceAltriDati_AntiMafia = this.altriDati.antiMafia;
        this.dataSourceAltriDati_Bdna = this.altriDati.bdna;
        if(this.dataSourceAltriDati_Bdna.length>0){
          this.isBdnaTable= true;
        }
        if(this.dataSourceAltriDati_AntiMafia.length>0){
          this.idAntimafiaTable = true;
        }
      }
    })


  }
  loadData() {

    this.loadedNazioni = false;
    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.myForm.get('nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.datiEG && this.datiEG.idNazione) {
          this.myForm.get("nazione").setValue(this.nazioneData.find(f => f.idNazione === this.datiEG.idNazione.toString()));
          this.nazioneSelected.setValue(this.nazioneData.find(f => f.idNazione === this.datiEG.idNazione.toString()))
          if (this.datiEG.idNazione == 118) {
            this.loadRegioni(true);
          } else {
            this.loadComuniEsteri(true);
          }
        }
      }
      this.loadedNazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      this.loadedNazioni = true;
    });


    this.subscribers.getNazioniSedeAmm = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneSedeAmmData = data;
        this.filteredOptionsNazioniSedeAmm = this.myForm.get('nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioniSedeAmm(name) : this.nazioneSedeAmmData.slice())
          );

        if (this.datiEG && this.datiEG.idNazioneSedeAmm) {
          this.myForm.get("nazioneSedeAmm").setValue(this.nazioneSedeAmmData.find(f => f.idNazione === this.datiEG.idNazioneSedeAmm.toString()));
          this.nazioneSedeAmmSelected.setValue(this.nazioneSedeAmmData.find(f => f.idNazione === this.datiEG.idNazioneSedeAmm.toString()));
          if (this.datiEG.idNazioneSedeAmm == 118)
            this.loadRegioniSedeAmm(true);
          else
            this.loadComuniSedeAmmEsteri(true);
        }
      }
      this.loadedNazioniSedeAmm = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      this.loadedNazioniSedeAmm = true;
    });
  }

  loadAltreSedi() {
    // TODO: impostare uno spinner
    this.datiDomandaService.getAltreSediEG(this.idSoggetto, this.numeroDomanda).subscribe(data => {
      if (data) {

        this.altreSediEG = data;

      }
    });
  }

  //FUNZIONE CHE PERMETTE DI MODIFICARE I CAMPI DELLA DOMANDA/PROGETTO SELEZIONATA DALL'ELENCO
  configuraBandoLinea() {
    this.router.navigate(["/drawer/" + this.idOp + "/modificaDatiDomandaEG"],
      { queryParams: { idSoggetto: this.idSoggetto, idDomanda: this.numeroDomanda, idEnteGiuridico: this.idEnteGiuridico } });
  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null ? valore : "";
  }

  getElencoSoggettiCorrelati() {
    console.log(this.numeroDomanda);

    this.spinner = true;
    this.subscribers.getElencoSoggCorrDipDaDomanda = this.datiDomandaService.getElencoSoggCorrDipDaDomanda(this.numeroDomanda, this.idSoggetto).subscribe(data => {
      if (data.length > 0) {
        this.listaSoggettiCorr = data;
        this.isSoggettiCorr = true
        this.sortedData = data;
        this.spinner = false;
      } else {
        this.isSoggettiCorr = false;
        this.spinner = false;
      }
    })
  }
  visualizzaSoggettoCorr(soggetto: SoggettoCorrelatoVO) {
    // mi porto al componente per vizualizzare i dato del soggetto correlato a ente giuridico con parametro 1 corrispondente a EG 
    if (soggetto.tipologia == "Persona Giuridica") {
      this.router.navigate(["/drawer/" + this.idOp + "/visualizzaSoggCorrDipDom"],
        {
          queryParams: {
            idSoggetto: this.idSoggetto,
            numeroDomanda: this.numeroDomanda,
            idDomanda: soggetto.idDomanda,
            tipoSoggetto: this.verificaSoggetto,
            idEnteGiuridico: this.idEnteGiuridico,
            idSoggettoCorr: soggetto.nag,
            tipoSoggettoCorr: soggetto.tipologia,
            idSoggCorr: soggetto.idSoggettoCorellato
          }
        });
    } else {
      this.router.navigate(["/drawer/" + this.idOp + "/visualizzaSoggCorrDipDomPf"],
        {
          queryParams: {
            idSoggetto: this.idSoggetto,
            numeroDomanda: this.numeroDomanda,
            idDomanda: soggetto.idDomanda,
            tipoSoggetto: this.verificaSoggetto,
            idEnteGiuridico: this.idEnteGiuridico,
            idSoggettoCorr: soggetto.nag,
            tipoSoggettoCorr: soggetto.tipologia,
            idSoggCorr: soggetto.idSoggettoCorellato
          }
        });
    }
  }

  sortData(sort: Sort) {

    const data = this.sortedData.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }
    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'tipologia':
          return compare(a.tipologia, b.tipologia, isAsc);
        case 'nag':
          return compare(a.nag, b.nag, isAsc);
        case 'denom':
          return compare(a.cognome + '' + a.nome, b.cognome + '' + b.nome, isAsc);
        case 'codiceFisc':
          return compare(a.codiceFiscale, b.codiceFiscale, isAsc);
        case 'ruolo':
          return compare(a.ruolo, b.ruolo, isAsc);
        default:
          return 0;
      }
    });
  }

  ngAfterViewChecked() {
    if (this.tabs) {
      this.tabs.realignInkBar();
    }
  }

  goBack() {

    // this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    // this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idTipoSoggetto');
    // this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    // this.numeroDomanda = this.route.snapshot.queryParamMap.get("idDomanda");
    //let tipoSoggetto ="Persona Giuridica"
    console.log(this.idSoggetto, this.numeroDomanda, this.idEnteGiuridico)

    this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"],
      {
        queryParams: {
          idSoggetto: this.idSoggetto,
          idTipoSoggetto: this.idEnteGiuridico,
          tipoSoggetto: this.verificaSoggetto,
          idDomanda: this.idDomanda,
          idProgetto: this.idProgetto,
          numeroDomanda: this.veroIdDomanda,
          ndg: this.ndg
        }
      });

    // idSoggetto: this.idSoggetto,
    // ndg: this.ndg, // server per il goback con nome ndg
    // numeroDomanda: row.numeroDomanda, 
    // idEnteGiuridico: this.idEnteGiuridico,
    // isEnteGiuridico: this.verificaTipoSoggetto, // serve per il goback con il nome tipoSoggetto
    // idDomanda: this.numeroDomanda, // mi serve per il goback con nome idDomanda
    // idProgetto: this.idProgetto,  // mi serve per il goback con nome idProgetto
    // veroIdDomanda: this.veroIdDomanda// mi serve peril goback con nome numeroDomanda
  }

  editDatiDomanda() {
    // inn assenza di progetto si puoò modificare solo la sezione recapiti ein presenza tutto
    if (this.idProgetto != null) {
      this.myForm.enable();
    } else {
      this.myForm.get("telefono").enable();
      this.myForm.get("fax").enable();
      this.myForm.get("email").enable();
      this.myForm.get("pec").enable();
    }
    this.editForm = true;
  }

  isNotEditDatiDomanda() {
    this.myForm.disable();
    this.editForm = false;
    this.resetMessageError();
    this.resetMessageSuccess();
    this.ngOnInit();
  }

  loadRegioni(init?: boolean) {
    this.loadedRegioni = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneData = data;
        this.filteredOptionsRegioni = this.myForm.get('regione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name) : this.regioneData.slice())
          );
        if (this.datiEG && this.datiEG.idRegione && init) {
          this.myForm.get("regione").setValue(this.regioneData.find(f => f.idRegione === this.datiEG.idRegione.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.datiEG.idRegione.toString()));
          if (this.regioneSelected.value != null)
            this.loadProvince(init);
        }
      }
      this.loadedRegioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regioni.");
      this.loadedRegioni = true;
    });
  }

  loadRegioniSedeAmm(init?: boolean) {
    this.loadedRegioniSedeAmm = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneSedeAmmData = data;
        this.filteredOptionsRegioniSedeAmm = this.myForm.get('regioneSedeAmm').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name) : this.regioneSedeAmmData.slice())
          );
        if (this.myForm.get("nazioneSedeAmm") && init) {

          this.myForm.get("regioneSedeAmm").setValue(this.regioneSedeAmmData.find(f => f.idRegione === this.datiEG.idRegioneSedeAmm.toString()));
          this.regioneSedeAmmSelected.setValue(this.regioneSedeAmmData.find(f => f.idRegione === this.datiEG.idRegioneSedeAmm.toString()));
          if (this.regioneSedeAmmSelected.value != null)
            this.loadProvinceSedeAmm(init);
        }
      }
      this.loadedRegioniSedeAmm = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regioni.");
      this.loadedRegioniSedeAmm = true;
    });
  }
 

  loadProvince(init?: boolean) {
    this.loadedProvince = false;
    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaData = data;
        this.filteredOptionsProvince = this.myForm.get('provincia').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name) : this.provinciaData.slice())
          );
        if (this.datiEG && this.datiEG.idProvincia && init) {
          this.myForm.get("provincia").setValue(this.provinciaData.find(f => f.idProvincia === this.datiEG.idProvincia.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.datiEG.idProvincia.toString()));
          console.log(this.provinciaSelected.value);
          if (this.provinciaSelected.value != null)
            this.loadComuniItalia(init);
        }
      }
      this.loadedProvince = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province.");
      this.loadedProvince = true;
    });
  }

  loadProvinceSedeAmm(init?: boolean) {
    this.loadedProvinceSedeAmm = false;

    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneSedeAmmSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaSedeAmmData = data;
        this.filteredOptionsProvinceSedeAmm = this.myForm.get('provinciaSedeAmm').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name) : this.provinciaSedeAmmData.slice())
          );
        if (this.myForm.get("regioneSedeAmm") && this.datiEG.idProvinciaSedeAmm && init) {
          this.myForm.get("provinciaSedeAmm").setValue(this.provinciaSedeAmmData.find(f => f.idProvincia === this.datiEG.idProvinciaSedeAmm.toString()));
          this.provinciaSedeAmmSelected.setValue(this.provinciaSedeAmmData.find(f => f.idProvincia === this.datiEG.idProvinciaSedeAmm.toString()));
          if (this.provinciaSedeAmmSelected.value != null)
            this.loadComuniSedeAmmItalia(init);

        }
      }
      this.loadedProvinceSedeAmm = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province.");
      this.loadedProvinceSedeAmm = true;
    });
  }

  loadComuniItalia(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.myForm.get('comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.datiEG && this.datiEG.idComune && init) {
          this.myForm.get("comune").setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          // if (this.comuneSelected.value != null)
          //   this.setCap();
        }
      }
      this.loadedComuni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuni = true;
    });
  }

  loadComuniSedeAmmItalia(init?: boolean) {
    this.loadedComuniSedeAmm = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaSedeAmmSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneSedeAmmData = data;
        this.filteredOptionsComuniSedeAmm = this.myForm.get('comuneSedeAmm').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneSedeAmmData.slice())
          );
        if (this.myForm.get("provinciaSedeAmm") && init && this.datiEG.idComuneSedeAmm > 0) {
          this.myForm.get("comuneSedeAmm").setValue(this.comuneSedeAmmData.find(f => f.idComune === this.datiEG.idComuneSedeAmm.toString()));
          this.comuneSedeAmmSelected.setValue(this.comuneSedeAmmData.find(f => f.idComune === this.datiEG.idComuneSedeAmm.toString()));
          //this.setCap();
        }
      }
      this.loadedComuniSedeAmm = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuniSedeAmm = true;
    });
  }

  loadComuniEsteri(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.myForm.get('comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.datiEG && this.datiEG.idComune && this.myForm.get("sede-legale") && init) {
          this.myForm.get("comune").setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.datiEG.idComune.toString()));
          //this.setCap();
        }
      }
      this.loadedComuni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuni = true;
    });
  }

  loadComuniSedeAmmEsteri(init?: boolean) {
    this.loadedComuniSedeAmm = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneSedeAmmSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneSedeAmmData = data;
        this.filteredOptionsComuniSedeAmm = this.myForm.get('comuneSedeAmm').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneSedeAmmData.slice())
          );
        if (this.myForm.get("comuneSedeAmm") && init) {
          this.myForm.get("comuneSedeAmm").setValue(this.comuneSedeAmmData.find(f => f.idComune === this.datiEG.idComuneSedeAmm.toString()));
          this.comuneSelected.setValue(this.comuneSedeAmmData.find(f => f.idComune === this.datiEG.idComuneSedeAmm.toString()));
          //this.setCap();
        }
      }
      this.loadedComuniSedeAmm = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuniSedeAmm = true;
    });
  }

  displayFnNazioni(nazione: Nazioni): string {
    return nazione && nazione.descNazione ? nazione.descNazione : '';
  }

  displayFnRegioni(regione: Regioni): string {
    return regione && regione.descRegione ? regione.descRegione : '';

  }

  displayFnProvince(prov: AtlanteVO): string {
    return prov && prov.siglaProvincia ? prov.siglaProvincia : '';
  }

  displayFnComuni(com: AtlanteVO): string {
    return com && com.descComune ? com.descComune : '';
  }

  private _filterNazioni(descrizione: string): Nazioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.nazioneData.filter(option => option.descNazione.toLowerCase().includes(filterValue));
  }

  private _filterRegioni(descrizione: string): Regioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.regioneData.filter(option => option.descRegione.toLowerCase().includes(filterValue));
  }

  private _filterProvince(descrizione: string): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.provinciaData.filter(option => option.siglaProvincia.toLowerCase().includes(filterValue));
  }

  private _filterComuni(descrizione: string): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    return this.comuneData.filter(option => option.descComune.toLowerCase().includes(filterValue));
  }

  check(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneSelected || (this.myForm.get('nazione') && this.nazioneSelected.value !== this.myForm.get('nazione').value)) {
            this.myForm.get('nazione').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.myForm.get('regione') && this.regioneSelected.value !== this.myForm.get('regione').value)) {
            this.myForm.get('regione').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.myForm.get('provincia') && this.provinciaSelected.value !== this.myForm.get('provincia').value)) {
            this.myForm.get('provincia').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.myForm.get('comune') && this.comuneSelected.value !== this.myForm.get('comune').value)) {
            this.myForm.get('comune').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        default:
          break;
      }
    }, 200);
  }

  checkSedeAmm(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneSelected || (this.myForm.get('nazione') && this.nazioneSelected.value !== this.myForm.get('nazione').value)) {
            this.myForm.get('nazione').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.myForm.get('regione') && this.regioneSelected.value !== this.myForm.get('regione').value)) {
            this.myForm.get('regione').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.myForm.get('provincia') && this.provinciaSelected.value !== this.myForm.get('provincia').value)) {
            this.myForm.get('provincia').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.myForm.get('comune') && this.comuneSelected.value !== this.myForm.get('comune').value)) {
            this.myForm.get('comune').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        default:
          break;
      }
    }, 200);
  }

  click(event: any, type: string) {
    switch (type) {
      case "N":
        if (event.option.value !== this.nazioneSelected.value) {
          this.nazioneSelected.setValue(event.option.value);
          this.resetRegione();
          if (this.nazioneSelected.value.idNazione.toString() === "118") { //ITALIA
            this.loadRegioni();
          } else {
            this.loadComuniEsteri();
          }
        }
        break;
      case "R":
        if (event.option.value !== this.regioneSelected.value) {
          this.regioneSelected.setValue(event.option.value);
          this.resetProvincia();
          this.loadProvince();
        }
        break;
      case "P":
        if (event.option.value !== this.provinciaSelected.value) {
          this.provinciaSelected.setValue(event.option.value);
          this.resetComune();
          this.loadComuniItalia();
        }
        break;
      case "C":
        if (event.option.value !== this.comuneSelected.value) {
          this.comuneSelected.setValue(event.option.value);
          this.setCap();
        }
        break;
      default:
        break;
    }
  }

  clickSedeAmm(event: any, type: string) {
    switch (type) {
      case "N":
        if (event.option.value !== this.nazioneSelected.value) {
          this.nazioneSelected.setValue(event.option.value);
          this.resetRegione();
          if (this.nazioneSelected.value.idNazione.toString() === "118") { //ITALIA
            this.loadRegioni();
          } else {
            this.loadComuniEsteri();
          }
        }
        break;
      case "R":
        if (event.option.value !== this.regioneSelected.value) {
          this.regioneSelected.setValue(event.option.value);
          this.resetProvincia();
          this.loadProvince();
        }
        break;
      case "P":
        if (event.option.value !== this.provinciaSelected.value) {
          this.provinciaSelected.setValue(event.option.value);
          this.resetComune();
          this.loadComuniItalia();
        }
        break;
      case "C":
        if (event.option.value !== this.comuneSelected.value) {
          this.comuneSelected.setValue(event.option.value);
          this.setCap();
        }
        break;
      default:
        break;
    }
  }


  openDialogAltreSedi(row: DatiDomandaEG) {
    let dialogRef = this.dialog.open(DialogDettaglioAltreSediEGComponent, {
      //width: '40%',
      data: {
        test: 1,
        row: row
      }
    });

    dialogRef.afterClosed().subscribe(data => {

      //console.log("Dialog res: ", data);

    })
  }

  setCap() {
    this.myForm.get('cap').setValue(this.comuneSelected.value.cap);
    if (this.comuneSelected.value.cap) {
      this.myForm.get("cap").disable();
    } else {
      this.myForm.get("cap").enable();
    }
  }

  resetRegione() {
    this.regioneData = [];
    this.regioneSelected = new FormControl();
    this.myForm.get('regione').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.myForm.get('regione').enable();
    } else {
      this.myForm.get('regione').disable();
    }
    this.resetProvincia();
  }

  resetRegioneSedeAmm() {
    this.regioneSedeAmmData = [];
    this.regioneSedeAmmSelected = new FormControl();
    this.myForm.get('regioneSedeAmm').setValue(null);
    if (this.nazioneSedeAmmSelected?.value?.idNazione?.toString() === "118") {
      this.myForm.get('regioneSedeAmm').enable();
    } else {
      this.myForm.get('regione').disable();
    }
    this.resetProvinciaSedeAmm();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.myForm.get('provincia').setValue(null);
    if (this.regioneSelected?.value) {
      this.myForm.get('provincia').enable();
    } else {
      this.myForm.get('provincia').disable();
    }
    this.resetComune();
  }

  resetProvinciaSedeAmm() {
    this.provinciaSedeAmmData = [];
    this.provinciaSedeAmmSelected = new FormControl();
    this.myForm.get('provinciaSedeAmm').setValue(null);
    if (this.regioneSedeAmmSelected?.value) {
      this.myForm.get('provinciaSedeAmm').enable();
    } else {
      this.myForm.get('provinciaSedeAmm').disable();
    }
    this.resetComuneSedeAmm();
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.myForm.get('comune').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected.value.idNazione?.toString() !== "118")) {
      this.myForm.get('comune').enable();
    } else {
      this.myForm.get('comune').disable();
    }
    this.resetCap();
  }

  resetComuneSedeAmm() {
    this.comuneSedeAmmData = [];
    this.comuneSedeAmmSelected = new FormControl();
    this.myForm.get('comuneSedeAmm').setValue(null);
    if (this.provinciaSedeAmmSelected?.value || (this.nazioneSedeAmmSelected?.value?.idNazione && this.nazioneSedeAmmSelected.value.idNazione?.toString() !== "118")) {
      this.myForm.get('comuneSedeAmm').enable();
    } else {
      this.myForm.get('comuneSedeAmm').disable();
    }
    //this.resetCap();
  }

  resetCap() {
    this.myForm.get('cap').setValue(null);
    this.myForm.get('cap').disable();
  }
  resetCapSedeAmm() {
    this.myForm.get('capSedeAmm').setValue(null);
    this.myForm.get('capSedeAmm').disable();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }
  showMessageSuccess(msg: string) {
    this.isMessageSuccessVisible = true;
    this.messageSuccess = msg;
  }

  salvaModificheDatiDomanda() {
    this.subscribers.updateDatiDomanda = this.datiDomandaService.updateDatiDomandaEG(this.datiEG, this.idUtente).subscribe(data => {
      if (data) {
        this.showMessageSuccess("Salvataggio andato a buon fine!");
        setTimeout(() => {
          this.isNotEditDatiDomanda();
        }, 3000);
      }
    });


  }
  setDatiDomanda() {
    this.resetMessageError();
    let isModifica: boolean;
    // Recapiti
    if (this.myForm.get("telefono").value && (this.myForm.get("telefono").value) != this.datiEG.telefono) {
      this.datiEG.telefono = this.myForm.get("telefono").value;
      isModifica = true;
    } else if (!this.myForm.get("telefono").value) {
      isModifica = true;
      this.datiEG.telefono = null;
    }

    if (this.myForm.get("pec").value && this.myForm.get("pec").value != this.datiEG.pec) {
      this.datiEG.pec = this.myForm.get("pec").value;
      isModifica = true;
    } else if (!this.myForm.get("pec").value) {
      isModifica = true;
      this.datiEG.pec = null;
    }

    if (this.myForm.get("email").value && this.myForm.get("email").value != this.datiEG.email) {
      this.datiEG.email = this.myForm.get("email").value;
      isModifica = true;
    } else if (!this.myForm.get("email").value) {
      this.datiEG.email = null;
      isModifica = true;
    }

    if (this.myForm.get("fax").value && this.myForm.get("fax").value != this.datiEG.fax) {
      this.datiEG.fax = this.myForm.get("fax").value;
      isModifica = true;
    } else if (!this.myForm.get("fax").value) {
      this.datiEG.fax = null;
      isModifica = true;
    }

    if (isModifica) {
      this.salvaModificheDatiDomanda()
    } else {
      this.showMessageError("Modificare almeno un campo");
    }
  }
  downloadAllegato( nomeDocumento, idDocumentoIndex) {
    //this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      //  this.loadedDownload = true;
    });
  }
}
function compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
