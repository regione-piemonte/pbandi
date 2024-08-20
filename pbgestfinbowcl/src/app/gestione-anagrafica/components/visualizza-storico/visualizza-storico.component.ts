/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { AtecoDTO } from '../../commons/dto/atecoDTO';
import { AtlanteVO } from '../../commons/dto/atlante-vo';
import { BeneficiarioSoggCorrEG } from '../../commons/dto/beneficiario-sogg-corr-EG';
import { ElencoFormaGiuridica } from '../../commons/dto/elencoFormaGiuridica';
import { Nazioni } from '../../commons/dto/nazioni';
import { Province } from '../../commons/dto/provincie';
import { Regioni } from '../../commons/dto/regioni';
import { RuoloDTO } from '../../commons/dto/ruoloDTO';
import { SezioneSpecialeDTO } from '../../commons/dto/sezioneSpecialeDTO';
import { VistaStoricoSec } from '../../commons/dto/vista-versione';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { ModificaAnagraficaService } from '../../services/modifica-anagrafica.service';
import { StoricoBenficiarioService } from '../../services/storico-benficiario.service';
import { map, startWith } from 'rxjs/operators';
import { Constants } from 'src/app/core/commons/util/constants';

@Component({
  selector: 'app-visualizza-storico',
  templateUrl: './visualizza-storico.component.html',
  styleUrls: ['./visualizza-storico.component.scss'],
})
export class VisualizzaStoricoComponent implements OnInit {

  public myForm: FormGroup;
  public formSoggetto: FormGroup;
  idVersione: any;
  idSoggetto: any;
  idDomanda: any;
  tipoSoggetto: any;
  spinner: boolean;
  getConcluse: number = 0;
  ndg: any;
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

  codiceAtecoData: Array<AtecoDTO>;
  descrizioneAttivitaData: any[] = [];

  provinciaIscrizioneData: AtlanteVO[] = [];
  public subscribers: any = {};

  codiceAteco: string;
  elencoAteco: Array<AtecoDTO>;
  codiceAtecoIns: any;
  atecoSelected: FormControl = new FormControl();
  statoAttivitaData: AttivitaDTO[];
  loadedSave: boolean = true;
  isSaved: boolean;
  userloaded: boolean;
  idUtente: number;
  soggObj: BeneficiarioSoggCorrEG;
  formaGiuridicaData: ElencoFormaGiuridica[] = [];
  sezioneAppartenanzaData: SezioneSpecialeDTO[] = [];
  ruoloData: RuoloDTO[] = [];
  storico: VistaStoricoSec;
  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  setBackText: string = "Torna allo storico"; 
  //DATEPICKER VALIDATOR
  today: Date;
  myFilter = (d: Date | null): boolean => {
    return d < this.today;
  }
  messageError: string;
  isMessageErrorVisible: boolean;
  loadedSoggetto: boolean = true;
  loadedFormeGiuridiche: boolean = true;
  loadedRuoli: boolean = true;
  loadedNazioni: boolean = true;
  loadedRegioni: boolean = true;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;
  idProgetto: any;
  soggetti: any;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private modificaAnagraficaService: ModificaAnagraficaService,
    private anagBeneService: AnagraficaBeneficiarioService,
    private handleExceptionService: HandleExceptionService,
    private datiDomandaService: DatiDomandaService,
    private storicoService: StoricoBenficiarioService) { }


  ngOnInit() {
    this.spinner = false;
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');
    this.idProgetto = this.route.snapshot.queryParamMap.get('idProgetto');
    this.tipoSoggetto = "Persona Giuridica";

    // this.myForm = this.fb.group({
    //   //DATI ANAGRAFICI
    //   ragioneSoc: new FormControl({ value: '', disabled: true }),
    //   formaGiuridica: new FormControl({ value: '', disabled: true }),
    //   flagPubblicoPrivato: new FormControl({ value: '', disabled: true }),
    //   codiceUniIpa: new FormControl({ value: '', disabled: true }),
    //   codiceFiscale: new FormControl({ value: '', disabled: true }),
    //   dataCostituzione: new FormControl({ value: '', disabled: true }),
    //   pec: new FormControl({ value: '', disabled: true }),
    //   ruolo: new FormControl({ value: 'BENEFICIARIO', disabled: true }),
    //   //SEDE LEGALE
    //   indirizzo: new FormControl({ value: '', disabled: true }),
    //   partitaIva: new FormControl({ value: '', disabled: true }),
    //   comune: new FormControl({ value: '', disabled: true }),
    //   provincia: new FormControl({ value: '', disabled: true }),
    //   cap: new FormControl({ value: '', disabled: true }),
    //   regione: new FormControl({ value: '', disabled: true }),
    //   nazione: new FormControl({ value: '', disabled: true }),
    //   //ATTIVITA' ECONOMICA
    //   codiceAteco: new FormControl({ value: '', disabled: true }),
    //   descAttivitaPrevalente: new FormControl({ value: '', disabled: true }),
    //   ratingDiLegalita: new FormControl({ value: '', disabled: true }),
    //   statoAttivita: new FormControl({ value: '', disabled: true }),
    //   dataInizioAttivita: new FormControl({ value: '', disabled: true }),
    //   periodoChiusuraEsercizio: new FormControl({ value: '', disabled: true }),
    //   dataChiusuraEsercizio: new FormControl({ value: '', disabled: true }),
    //   //DATI ISCRIZIONE:
    //   numeroRea: new FormControl({ value: '', disabled: true }),
    //   dataIscrizione: new FormControl({ value: '', disabled: true }),
    //   provinciaIscrizione: new FormControl({ value: '', disabled: true }),
    //   //SEZIONE DI APPARTENENZA:
    //   sezioneDiAppartenenza: new FormControl({ value: '', disabled: true }),
    // });

    this.loadSoggetto();
    this.formSoggetto = this.fb.group({
      'dati-anagrafici': this.fb.group({
        ragioneSociale: [{ value: '', disabled: true }],
        formaGiuridica: [{ value: '', disabled: true }],
        pubblicoPrivatoRadio: [{ value: '', disabled: true }],
        codiceUniIpa: [{ value: '', disabled: true }],
        ruolo: [{ value: '', disabled: true }],
        codiceFiscale: [{ value: '', disabled: true }],
        dtCostituzione: [{ value: '', disabled: true }],
        pec: [{ value: '', disabled: true }]
      }),
      'sede-legale': this.fb.group({
        indirizzo: [{ value: '', disabled: true }],
        partitaIva: [{ value: '', disabled: true }],
        comune: [{ value: '', disabled: true }],
        provincia: [{ value: '', disabled: true }],
        cap: [{ value: '', disabled: true }],
        regione: [{ value: '', disabled: true }],
        nazione: [{ value: '', disabled: true }]
      }),
      'attivita-economica': this.fb.group({
        codiceAteco: [{ value: '', disabled: true }],
        descrizioneAttivita: [{ value: '', disabled: true }],
        ratingLegalita: [{ value: '', disabled: true }],
        statoAttivita: [{ value: '', disabled: true }],
        dtInizioAttivita: [{ value: '', disabled: true }],
        periodoChiusuraEsercizio: [{ value: '', disabled: true }],
        dtChiusuraEsercizio: [{ value: '', disabled: true }]
      }),
      'dati-iscrizione': this.fb.group({
        numeroRea: [{ value: '', disabled: true }],
        dtIscrizione: [{ value: '', disabled: true }],
        provinciaIscrizione: [{ value: '', disabled: true }]
      }),
      'sezione-appartenenza': this.fb.group({
        sezioneAppartenenza: [{ value: '', disabled: true }]
      })
    });

    // if (this.idVersione.includes(" - idVersioneDomanda")) {

    //   this.idVersione = this.idVersione.replace(" - idVersioneDomanda", "");

    // this.storicoService.getVistaStoricoDomanda(this.idSoggetto, this.idDomanda).subscribe(data => {
    //   if (data) {
    //     this.storico = data;
    //     this.myForm.setValue({
    //       //DATI ANAGRAFICI
    //       ragioneSoc: [this.storico[0].ragioneSoc],
    //       formaGiuridica: [this.storico[0].formaGiuridica],
    //       flagPubblicoPrivato: [this.storico[0].flagPubblicoPrivato],
    //       codiceUniIpa: [this.storico[0].codiceUniIpa],
    //       codiceFiscale: [this.storico[0].codiceFiscale],
    //       dataCostituzione: new Date(this.storico[0].dataCostituzione.toString()),
    //       pec: [this.storico[0].pec],
    //       ruolo: ["BENEFICIARIO"],
    //       //SEDE LEGALE
    //       indirizzo: [this.storico[0].indirizzo],
    //       partitaIva: [this.storico[0].partitaIva],
    //       comune: [this.storico[0].comune],
    //       provincia: [this.storico[0].provincia],
    //       cap: [this.storico[0].cap],
    //       regione: [this.storico[0].regione],
    //       nazione: [this.storico[0].nazione],
    //       //ATTIVITA' ECONOMICA
    //       codiceAteco: [this.storico[0].codiceAteco],
    //       descAttivitaPrevalente: [this.storico[0].descAttivitaPrevalente],
    //       ratingDiLegalita: [this.storico[0].ratingDiLegalita],
    //       statoAttivita: [this.storico[0].statoAttivita],
    //       dataInizioAttivita: this.controlloCampoValido(this.storico[0].dataInizioAttivita),
    //       periodoChiusuraEsercizio: [this.storico[0].periodoChiusuraEsercizio],
    //       dataChiusuraEsercizio: new Date(this.storico[0].dataChiusuraEsercizio.toString()),
    //       //DATI ISCRIZIONE:
    //       numeroRea: [this.storico[0].numeroRea],
    //       dataIscrizione: new Date(this.storico[0].dataIscrizione.toString()),
    //       provinciaIscrizione: [this.storico[0].provinciaIscrizione],
    //       //SEZIONE DI APPARTENENZA:
    //       sezioneDiAppartenenza: [this.storico[0].sezioneDiAppartenenza],
    //     });
    //     this.bloccaSpinner();
    //   }
    // });
  }

  loadSoggetto() {
    this.loadedSoggetto = false;

    this.subscribers.anagBeneficiarioInfo$ = this.anagBeneService.getAnagBeneficiario(this.idSoggetto, this.idProgetto, this.idDomanda).subscribe(data => {
      if (data) {
        this.soggetti = data;
        if (this.soggetti.length > 0) {
          this.soggObj = this.soggetti[0];
          this.ndg = this.soggObj.ndg;
          this.setValueForm();
        }
      }
      this.loadData();
      this.loadedSoggetto = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del dettaglio del soggetto.");
      this.loadedSoggetto = true;
    })
  }
  getElencoCodiceAteco(atecoCod: string, init?: boolean) {


    if (atecoCod)
      this.subscribers.getElencoAteco = this.anagBeneService.getElencoAteco(atecoCod).subscribe(data => {
        if (data) {

          this.codiceAtecoData = data;
          if (init) {
            this.atecoSelected.setValue(this.codiceAtecoData.find(f => f.codAteco == atecoCod));
            this.formSoggetto.get("attivita-economica.codiceAteco").setValue(this.codiceAtecoData.find(f => f.codAteco == atecoCod));
          }
        } else {
          this.elencoAteco = [];
        }
      });
  }
  displayFnAteco(ateco: AtecoDTO) {
    return ateco && ateco.codAteco ? ateco.codAteco : '';
  }

  loadData() {
    this.loadedFormeGiuridiche = false;
    this.subscribers.getElencoFormaGiuridica = this.modificaAnagraficaService.getElencoFormaGiuridica().subscribe(data => {
      if (data) {
        this.formaGiuridicaData = data;
        if (this.soggObj && this.soggObj.idFormaGiuridica && this.formSoggetto.get("dati-anagrafici")) {
          this.formSoggetto.get("dati-anagrafici").get("formaGiuridica").setValue(this.formaGiuridicaData.find(f => f.idFormaGiuridica === this.soggObj.idFormaGiuridica));
        }
      }
      this.loadedFormeGiuridiche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle forme giuridiche.");
      this.loadedFormeGiuridiche = true;
    });
    this.loadedRuoli = false;
    this.subscribers.getRuoli = this.modificaAnagraficaService.getElencoRuolo().subscribe(data => {
      if (data) {
        this.ruoloData = data;
        if (this.soggObj && this.soggObj.idTipoSoggCorr) {
          this.formSoggetto.get("dati-anagrafici").get("ruolo").setValue(this.ruoloData.find(f => f.idTipoSoggCorr.toString() === this.soggObj.idTipoSoggCorr.toString()));
        }
      }
      this.loadedRuoli = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei ruoli.");
      this.loadedRuoli = true;
    });

    this.subscribers.getStatoAttivita = this.anagBeneService.getListaAttivita().subscribe(data => {
      if (data) {
        this.statoAttivitaData = data;
        if (this.soggObj && this.soggObj.idAttAteco)
          this.formSoggetto.get("attivita-economica").get("statoAttivita").setValue(this.statoAttivitaData.find(f => f.idAttivita.toString() === this.soggObj.idStatoAtt.toString()));
      }
    });

    this.subscribers.getProvinceIscrizioni = this.datiDomandaService.getProvince().subscribe(data => {
      if (data) {
        this.provinciaIscrizioneData = data;
        if (this.soggObj && this.soggObj.idProvinciaIscrizione) {
          this.formSoggetto.get("dati-iscrizione.provinciaIscrizione").setValue(this.provinciaIscrizioneData.find(f => f.idProvincia.toString() === this.soggObj.idProvinciaIscrizione.toString()));
        }

      }
    });
    this.subscribers.getElencoSezioneSpeciale = this.anagBeneService.getElencoSezioneSpeciale().subscribe(data => {
      if (data) {
        this.sezioneAppartenanzaData = data;
        if (this.soggObj && this.soggObj.idSezioneSpeciale) {
          this.formSoggetto.get("sezione-appartenanza.sezioneAppartenanza").setValue(
            this.sezioneAppartenanzaData.find(s => s.idSezioneSpeciale = this.soggObj.idSezioneSpeciale));
        }
      }
    })

    this.loadedNazioni = false;
    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.formSoggetto.get('sede-legale.nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.soggObj && this.soggObj.idNazione && this.formSoggetto.get("sede-legale")) {
          this.formSoggetto.get("sede-legale.nazione").setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazione.toString()));
          this.nazioneSelected.setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazione.toString()))
          if (this.soggObj.idNazione == 118)
            this.loadRegioni(true);
          else {
            this.loadComuniEsteri(true);
          }
        }
      }
     this.loadedNazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      //this.loadedNazioni = true;
    });
    console.log(this.soggObj.codAteco);

    this.getElencoCodiceAteco(this.soggObj.codAteco, true);

  }

  loadRegioni(init?: boolean) {
    this.loadedRegioni = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneData = data;
        this.filteredOptionsRegioni = this.formSoggetto.get('sede-legale.regione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name) : this.regioneData.slice())
          );
        if (this.soggObj && this.soggObj.idRegione && init) {
          this.formSoggetto.get("sede-legale.regione").setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegione.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegione.toString()));


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

  loadProvince(init?: boolean) {
    this.loadedProvince = false;
    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaData = data;
        this.filteredOptionsProvince = this.formSoggetto.get('sede-legale.provincia').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name) : this.provinciaData.slice())
          );
        if (this.soggObj && this.soggObj.idProvincia && init) {
          this.formSoggetto.get("sede-legale.provincia").setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvincia.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvincia.toString()));

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

  loadComuniItalia(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggetto.get('sede-legale.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComune && this.formSoggetto.get("sede-legale") && init) {
          this.formSoggetto.get("sede-legale.comune").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));

          if (this.comuneSelected.value != null)
            this.setCap();
        }
      }
      this.loadedComuni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuni = true;
    });
  }

  loadComuniEsteri(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggetto.get('sede-legale.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComune && this.formSoggetto.get("sede-legale") && init) {
          this.formSoggetto.get("sede-legale.comune").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
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

  displayFnNazioni(nazione: Nazioni): string {
    return nazione && nazione.descNazione ? nazione.descNazione : '';
  }

  displayFnRegioni(regione: Regioni): string {
    return regione && regione.descRegione ? regione.descRegione : '';

  }

  displayFnProvince(prov: AtlanteVO): string {
    return prov && prov.siglaProvincia ? prov.siglaProvincia : '';
  }
  displayFnProvinceIscrizione(prov: Province): string {
    return prov && prov.descProvincia ? prov.descProvincia : '';
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
          if (!this.nazioneSelected || (this.formSoggetto.get('sede-legale.nazione') && this.nazioneSelected.value !== this.formSoggetto.get('sede-legale.nazione').value)) {
            this.formSoggetto.get('sede-legale.nazione').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.formSoggetto.get('sede-legale.regione') && this.regioneSelected.value !== this.formSoggetto.get('sede-legale.regione').value)) {
            this.formSoggetto.get('sede-legale.regione').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.formSoggetto.get('sede-legale.provincia') && this.provinciaSelected.value !== this.formSoggetto.get('sede-legale.provincia').value)) {
            this.formSoggetto.get('sede-legale.provincia').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.formSoggetto.get('sede-legale.comune') && this.comuneSelected.value !== this.formSoggetto.get('sede-legale.comune').value)) {
            this.formSoggetto.get('sede-legale.comune').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        case "A":
          if (!this.atecoSelected || (this.formSoggetto.get('attivita-economica.codiceAteco') && this.atecoSelected.value !== this.formSoggetto.get('attivita-economica.codiceAteco').value)) {
            this.formSoggetto.get('attivita-economica.codiceAteco').setValue(null);
            this.atecoSelected = new FormControl();
            this.resetAttivitaPrevalente();
          }
          break;
        default:
          break;
      }
    }, 200);
  }
  resetAttivitaPrevalente() {
    this.formSoggetto.get("attivita-economica.descrizioneAttivita").setValue(null)
  }
  setAttivitaPrevalente() {
    this.formSoggetto.get("attivita-economica.descrizioneAttivita").setValue(this.atecoSelected.value.descAttivitaAteco)
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
      case "A":
        if (event.option.value !== this.atecoSelected.value) {
          this.atecoSelected.setValue(event.option.value);
          this.setAttivitaPrevalente();
          console.log(this.atecoSelected.value.idAttAteco);
        }
        break;
      default:
        break;
    }
  }

  setCap() {
    this.formSoggetto.get('sede-legale.cap').setValue(this.comuneSelected.value.cap);
    if (this.comuneSelected.value.cap) {
      this.formSoggetto.get("sede-legale.cap").disable();
    } else {
      this.formSoggetto.get("sede-legale.cap").enable();
    }
  }

  resetRegione() {
    this.regioneData = [];
    this.regioneSelected = new FormControl();
    this.formSoggetto.get('sede-legale.regione').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggetto.get('sede-legale.regione').enable();
    } else {
      this.formSoggetto.get('sede-legale.regione').disable();
    }
    this.resetProvincia();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.formSoggetto.get('sede-legale.provincia').setValue(null);
    if (this.regioneSelected?.value) {
      this.formSoggetto.get('sede-legale.provincia').enable();
    } else {
      this.formSoggetto.get('sede-legale.provincia').disable();
    }
    this.resetComune();
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.formSoggetto.get('sede-legale.comune').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggetto.get('sede-legale.comune').enable();
    } else {
      this.formSoggetto.get('sede-legale.comune').disable();
    }
    this.resetCap();
  }

  resetCap() {
    this.formSoggetto.get('sede-legale.cap').setValue(null);
    this.formSoggetto.get('sede-legale.cap').disable();
  }

  private setValueForm(): void {
    this.formSoggetto = this.fb.group({
      'dati-anagrafici': this.fb.group({
        ragioneSociale: [{ value: this.soggObj.ragSoc, disabled: true }],
        formaGiuridica: [{ value: null, disabled: true }],
        pubblicoPrivatoRadio: [{ value: this.soggObj.flagPubblicoPrivato, disabled: true }],
        codiceUniIpa: [{ value: this.soggObj.codUniIpa, disabled: true }],
        ruolo: [{ value: this.soggObj.idTipoSoggCorr, disabled: true }],
        codiceFiscale: [{ value: this.soggObj.cfSoggetto, disabled: true }],
        dtCostituzione: [{ value: this.soggObj.dtCostituzione, disabled: true }],
        pec: [{ value: this.soggObj.descPec, disabled: true }]
      }),
      'sede-legale': this.fb.group({
        indirizzo: [{ value: this.soggObj.descIndirizzo, disabled: true }],
        partitaIva: [{ value: this.soggObj.pIva, disabled: true }],
        comune: [{ value: this.soggObj.descComune, disabled: true }],
        provincia: [{ value: this.soggObj.descProvincia, disabled: true }],
        cap: [{ value: this.soggObj.cap, disabled: true }],
        regione: [{ value: this.soggObj.descRegione, disabled: true }],
        nazione: [{ value: this.soggObj.descNazione, disabled: true }]
      }),
      'attivita-economica': this.fb.group({
        codiceAteco: [{ value: null, disabled: true }],
        descrizioneAttivita: [{ value: this.soggObj.descAteco, disabled: true }],
        ratingLegalita: [{ value: this.soggObj.flagRatingLeg, disabled: true }],
        statoAttivita: [{ value: this.soggObj.descStatoAttivita, disabled: true }],
        dtInizioAttivita: [{ value: this.soggObj.dtInizioAttEsito, disabled: true }],
        periodoChiusuraEsercizio: [{ value: this.soggObj.periodoScadEse, disabled: true }],
        dtChiusuraEsercizio: [{ value: this.soggObj.dtUltimoEseChiuso, disabled: true }]
      }),
      'dati-iscrizione': this.fb.group({
        numeroRea: [{ value: this.soggObj.numIscrizione, disabled: true }],
        dtIscrizione: [{ value: this.soggObj.dtIscrizione, disabled: true }],
        provinciaIscrizione: [{ value: this.soggObj.descProvinciaIscriz, disabled: true }]
      }),
      'sezione-appartenenza': this.fb.group({
        sezioneAppartenenza: [{ value: this.soggObj.descSezioneSpeciale, disabled: true }]
      })
    });
    //this.formSoggetto.get("dati-anagrafici.ragioneSociale").setValue(this.soggObj[0].ragSoc);
    console.log("siamo dentro al form");
    console.log(this.soggObj);
    console.log(this.formSoggetto);

  }

  get ragioneSociale(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.ragioneSociale") as FormControl;
  }

  get pubblicoPrivatoRadio(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.pubblicoPrivatoRadio") as FormControl;
  }

  get codiceUniIpa(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.codiceUniIpa") as FormControl;
  }

  get dtCostituzione(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.dtCostituzione") as FormControl;
  }

  get pec(): FormControl {
    return this.formSoggetto.get("dati-anagrafici.pec") as FormControl;
  }

  get indirizzo(): FormControl {
    return this.formSoggetto.get("sede-legale.indirizzo") as FormControl;
  }

  get partitaIva(): FormControl {
    return this.formSoggetto.get("sede-legale.partitaIva") as FormControl;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  //FUNZIONE CHE SI OCCUPA DI INTERROMPERE LO SPINNER
  bloccaSpinner() {
    this.getConcluse += 1;
    if (this.getConcluse > 0) {
      this.spinner = false;
    }
  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null ? valore : "N.D.";
  }

  goBack() {
    this.router.navigate(["/drawer/" + this.idOp + "/storicoBeneficiario"], {
      queryParams: {
        idSoggetto: this.idSoggetto,
        isEnteGiuridico: "Persona Giuridica",
        ndg: this.soggObj.ndg,
        idDomanda: this.idDomanda,
        idProgetto: this.idProgetto
      }
    });
  }
}