/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AnagraficaBeneficiarioSec } from '../../commons/dto/anagrafica-beneficiario';
import { AnagraficaBeneFisico } from '../../commons/dto/anagraficaBeneFisico';
import { AtecoDTO } from '../../commons/dto/atecoDTO';
import { AtlanteVO } from '../../commons/dto/atlante-vo';
import { BeneficiarioSoggCorrEG } from '../../commons/dto/beneficiario-sogg-corr-EG';
import { BeneficiarioSoggCorrPF } from '../../commons/dto/beneficiario-sogg-corr-PF';
import { ElencoFormaGiuridica } from '../../commons/dto/elencoFormaGiuridica';
import { Nazioni } from '../../commons/dto/nazioni';
import { Regioni } from '../../commons/dto/regioni';
import { RuoloDTO } from '../../commons/dto/ruoloDTO';
import { SezioneSpecialeDTO } from '../../commons/dto/sezioneSpecialeDTO';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { ModificaAnagraficaService } from '../../services/modifica-anagrafica.service';
import { SoggettiIndipendentiDomandaService } from '../../services/soggetti-indipendenti-domanda.service';
import { map, startWith } from 'rxjs/operators';
import { Province } from '../../commons/dto/provincie';


@Component({
  selector: 'app-visualizza-sogg-corr-dip-dom',
  templateUrl: './visualizza-sogg-corr-dip-dom.component.html',
  styleUrls: ['./visualizza-sogg-corr-dip-dom.component.scss']
})
export class VisualizzaSoggCorrDipDomComponent implements OnInit {
  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  formSoggettoCorrelato!: FormGroup;
  setBackText: string = "Torna ai dati beneficiario domanda";
  tipoSoggetto: any;
  idVersione: any;
  numeroDomanda: any;
  idSoggetto: any;
  messageError: string;
  isMessageErrorVisible: boolean;
  userloaded: any;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: number;
  beneficiario: BeneficiarioSoggCorrEG;
  beneficiarioFisico: AnagraficaBeneFisico;

  myForm: FormGroup;
  isEnteGiuridico: boolean;
  getConcluse: number = 0;
  spinner: boolean;

  ruoloEG: any;


  benefSoggCorrEG: BeneficiarioSoggCorrEG = new BeneficiarioSoggCorrEG();
  benefSoggCorrPF: BeneficiarioSoggCorrPF = new BeneficiarioSoggCorrPF();
  loadedEG: boolean = true;
  loadedPF: boolean = true;
  idEnteGiuridico: any;
  idSoggettoCorr: string;
  tipoSoggettoCorr: string;

  ruoloPF: any;

  editForm: boolean = false;

  tipologiaSoggettoCorrelato!: string;
  nagSoggettoCorrelato!: string;

  soggObj: BeneficiarioSoggCorrEG = new BeneficiarioSoggCorrEG();




  formaGiuridicaData: ElencoFormaGiuridica[] = [];
  ruoloData: RuoloDTO[] = [];

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

  isMessageError: boolean = false;
  isPecValid: boolean = false;
  isPartitaIvaValid: boolean = false;
  sezioneAppartenanzaData: SezioneSpecialeDTO[] = [];



  // da togliere
  maxData = new Date();


  //LOADED
  loadedSoggetto: boolean = true;
  loadedFormeGiuridiche: boolean = true;
  loadedRuoli: boolean = true;
  loadedNazioni: boolean = true;
  loadedRegioni: boolean = true;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;

  codiceAteco: string;
  elencoAteco: Array<AtecoDTO>;
  codiceAtecoIns: any;
  atecoSelected: FormControl = new FormControl();
  statoAttivitaData: AttivitaDTO[];
  loadedSave: boolean = true;
  isSaved: boolean;

  modificaControl: boolean = false;
  errorModifica: boolean = false;
  idDomanda: string;
  ndg: String;
  idSoggCorr: string;



  constructor(private route: ActivatedRoute,
    private datiDomandaService: DatiDomandaService,
    private router: Router,
    private userService: UserService,
    private fb: FormBuilder,
    private anagBeneService: AnagraficaBeneficiarioService,
    private modificaAnagraficaService: ModificaAnagraficaService,
    private anagraficaBeneficiarioService: AnagraficaBeneficiarioService,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    private soggettiIndipendentiDomandaService: SoggettiIndipendentiDomandaService,) { }

  ngOnInit(): void {
    this.tipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get('numeroDomanda');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    this.idSoggettoCorr = this.route.snapshot.queryParamMap.get('idSoggettoCorr');;
    this.tipoSoggettoCorr = this.route.snapshot.queryParamMap.get('tipoSoggettoCorr');
    this.idSoggCorr = this.route.snapshot.queryParamMap.get('idSoggCorr');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');

    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
      }
    });
    if (this.tipoSoggettoCorr == "Persona Giuridica") {
      this.isEnteGiuridico = true;
      this.formSoggettoCorrelato = this.fb.group({
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
          dtChiusuraEsercizio: [{ value: '', disabled: true }],
          quotaPartecipazione: [{ value: '', disabled: true }]
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
      this.getBeneficiarioSoggCorrEG();

    }

  }




  getBeneficiarioSoggCorrEG() {
    this.loadedEG = false;
    this.subscribers.benfEG = this.datiDomandaService.getBenefSoggCorrEG(this.idSoggettoCorr, this.idDomanda,this.idSoggCorr).subscribe(data => {
      if (data) {
        this.benefSoggCorrEG = data;
        this.soggObj = data;
        this.ndg = this.soggObj.ndg; 
        //SETTING DEL FORM PER PERSONA GIURIDICA
        this.beneficiario = data;

        console.log(this.benefSoggCorrEG);
        this.loadedPF = false;

        this.formSoggettoCorrelato = this.fb.group({
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
            dtChiusuraEsercizio: [{ value: this.soggObj.dtUltimoEseChiuso, disabled: true }],
            quotaPartecipazione: [{ value: this.soggObj.quotaPartecipazione, disabled: true }]
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
        this.loadedEG = true;
        this.loadData();
      }
      else {
        this.loadedEG = true;
      }

    });

  }
  loadData() {
    this.loadedFormeGiuridiche = false;
    this.subscribers.getElencoFormaGiuridica = this.modificaAnagraficaService.getElencoFormaGiuridica().subscribe(data => {
      if (data) {
        this.formaGiuridicaData = data;
        if (this.soggObj && this.soggObj.idFormaGiuridica && this.formSoggettoCorrelato.get("dati-anagrafici")) {
          this.formSoggettoCorrelato.get("dati-anagrafici").get("formaGiuridica").setValue(this.formaGiuridicaData.find(f => f.idFormaGiuridica === this.soggObj.idFormaGiuridica));
        }
      }
      this.loadedFormeGiuridiche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle forme giuridiche.");
      this.loadedFormeGiuridiche = true;
    });
    this.loadedRuoli = false;
    this.subscribers.getRuoli = this.anagBeneService.getElencoRuoloDipendenti().subscribe(data => {
      if (data) {
        this.ruoloData = data;
        if (this.soggObj && this.soggObj.idTipoSoggCorr) {
          this.formSoggettoCorrelato.get("dati-anagrafici").get("ruolo").setValue(this.ruoloData.find(f => f.idTipoSoggCorr.toString() === this.soggObj.idTipoSoggCorr.toString()));
          
        }
      }
      this.loadedRuoli = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei ruoli.");
      this.loadedRuoli = true;
    });

    this.subscribers.getStatoAttivita = this.anagraficaBeneficiarioService.getListaAttivita().subscribe(data => {
      if (data) {
        this.statoAttivitaData = data;
        if (this.soggObj && this.soggObj.idAttAteco)
          this.formSoggettoCorrelato.get("attivita-economica").get("statoAttivita").setValue(this.statoAttivitaData.find(f => f.idAttivita.toString() === this.soggObj.idStatoAtt.toString()));
      }
    });

    this.subscribers.getProvinceIscrizioni = this.datiDomandaService.getProvince().subscribe(data => {
      if (data) {
        this.provinciaIscrizioneData = data;
        if (this.soggObj && this.soggObj.idProvinciaIscrizione) {
          this.formSoggettoCorrelato.get("dati-iscrizione.provinciaIscrizione").setValue(this.provinciaIscrizioneData.find(f => f.idProvincia.toString() === this.soggObj.idProvinciaIscrizione.toString()));
        }

      }
    });
    this.subscribers.getElencoSezioneSpeciale = this.anagraficaBeneficiarioService.getElencoSezioneSpeciale().subscribe(data => {
      if (data) {
        this.sezioneAppartenanzaData = data;
        if (this.soggObj && this.soggObj.idSezioneSpeciale) {
          this.formSoggettoCorrelato.get("sezione-appartenanza.sezioneAppartenanza").setValue(
            this.sezioneAppartenanzaData.find(s => s.idSezioneSpeciale = this.soggObj.idSezioneSpeciale));
        }
      }
    })

    this.loadedNazioni = false;
    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.formSoggettoCorrelato.get('sede-legale.nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.soggObj && this.soggObj.idNazione && this.formSoggettoCorrelato.get("sede-legale")) {
          this.formSoggettoCorrelato.get("sede-legale.nazione").setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazione.toString()));
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
      this.loadedNazioni = true;
    });
    this.getElencoCodiceAteco(this.soggObj.codAteco, true);

  }

  getElencoCodiceAteco(atecoCod: string, init?: boolean) {


    if (atecoCod)
      this.subscribers.getElencoAteco = this.anagraficaBeneficiarioService.getElencoAteco(atecoCod).subscribe(data => {
        if (data) {

          this.codiceAtecoData = data;
          if (init) {
            this.atecoSelected.setValue(this.codiceAtecoData.find(f => f.codAteco == atecoCod));
            this.formSoggettoCorrelato.get("attivita-economica.codiceAteco").setValue(this.codiceAtecoData.find(f => f.codAteco == atecoCod));
          }
        } else {
          this.elencoAteco = [];
        }
      });
  }
  displayFnAteco(ateco: AtecoDTO) {
    return ateco && ateco.codAteco ? ateco.codAteco : '';
  }
  loadRegioni(init?: boolean) {
    this.loadedRegioni = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneData = data;
        this.filteredOptionsRegioni = this.formSoggettoCorrelato.get('sede-legale.regione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name) : this.regioneData.slice())
          );
        if (this.soggObj && this.soggObj.idRegione && init) {
          this.formSoggettoCorrelato.get("sede-legale.regione").setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegione.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegione.toString()));
          console.log(this.regioneSelected.value.idRegione);

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
    this.subscribers.getProvinciaConIdRegione = this.anagraficaBeneficiarioService.getProvinciaConIdRegione(this.regioneSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaData = data;
        this.filteredOptionsProvince = this.formSoggettoCorrelato.get('sede-legale.provincia').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name) : this.provinciaData.slice())
          );
        if (this.soggObj && this.soggObj.idProvincia && init) {
          this.formSoggettoCorrelato.get("sede-legale.provincia").setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvincia.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvincia.toString()));

          if (this.provinciaSelected.value != null)
            console.log(this.provinciaSelected.value.idProvincia);
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
        this.filteredOptionsComuni = this.formSoggettoCorrelato.get('sede-legale.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComune && this.formSoggettoCorrelato.get("sede-legale") && init) {
          this.formSoggettoCorrelato.get("sede-legale.comune").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));

          if (this.comuneSelected.value != null)
            console.log(this.comuneSelected.value.idComune);
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
    this.subscribers.getComuneEstero = this.anagraficaBeneficiarioService.getComuneEstero(this.nazioneSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggettoCorrelato.get('sede-legale.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComune && this.formSoggettoCorrelato.get("sede-legale") && init) {
          this.formSoggettoCorrelato.get("sede-legale.comune").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComune.toString()));
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
          if (!this.nazioneSelected || (this.formSoggettoCorrelato.get('sede-legale.nazione') && this.nazioneSelected.value !== this.formSoggettoCorrelato.get('sede-legale.nazione').value)) {
            this.formSoggettoCorrelato.get('sede-legale.nazione').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.formSoggettoCorrelato.get('sede-legale.regione') && this.regioneSelected.value !== this.formSoggettoCorrelato.get('sede-legale.regione').value)) {
            this.formSoggettoCorrelato.get('sede-legale.regione').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.formSoggettoCorrelato.get('sede-legale.provincia') && this.provinciaSelected.value !== this.formSoggettoCorrelato.get('sede-legale.provincia').value)) {
            this.formSoggettoCorrelato.get('sede-legale.provincia').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.formSoggettoCorrelato.get('sede-legale.comune') && this.comuneSelected.value !== this.formSoggettoCorrelato.get('sede-legale.comune').value)) {
            this.formSoggettoCorrelato.get('sede-legale.comune').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        case "A":
          if (!this.atecoSelected || (this.formSoggettoCorrelato.get('attivita-economica.codiceAteco') && this.atecoSelected.value !== this.formSoggettoCorrelato.get('attivita-economica.codiceAteco').value)) {
            this.formSoggettoCorrelato.get('attivita-economica.codiceAteco').setValue(null);
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
    this.formSoggettoCorrelato.get("attivita-economica.descrizioneAttivita").setValue(null)
  }
  setAttivitaPrevalente() {
    this.formSoggettoCorrelato.get("attivita-economica.descrizioneAttivita").setValue(this.atecoSelected.value.descAttivitaAteco)
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
          console.log(this.comuneSelected.value.idComune);

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
    this.formSoggettoCorrelato.get('sede-legale.cap').setValue(this.comuneSelected.value.cap);
    if (this.comuneSelected.value.cap) {
      this.formSoggettoCorrelato.get("sede-legale.cap").disable();
    } else {
      this.formSoggettoCorrelato.get("sede-legale.cap").enable();
    }
  }

  resetRegione() {
    this.regioneData = [];
    this.regioneSelected = new FormControl();
    this.formSoggettoCorrelato.get('sede-legale.regione').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggettoCorrelato.get('sede-legale.regione').enable();
    } else {
      this.formSoggettoCorrelato.get('sede-legale.regione').disable();
    }
    this.resetProvincia();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.formSoggettoCorrelato.get('sede-legale.provincia').setValue(null);
    if (this.regioneSelected?.value) {
      this.formSoggettoCorrelato.get('sede-legale.provincia').enable();
    } else {
      this.formSoggettoCorrelato.get('sede-legale.provincia').disable();
    }
    this.resetComune();
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.formSoggettoCorrelato.get('sede-legale.comune').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggettoCorrelato.get('sede-legale.comune').enable();
    } else {
      this.formSoggettoCorrelato.get('sede-legale.comune').disable();
    }
    this.resetCap();
  }

  resetCap() {
    this.formSoggettoCorrelato.get('sede-legale.cap').setValue(null);
    this.formSoggettoCorrelato.get('sede-legale.cap').disable();
  }


  editSoggetto(): void {
    this.editForm = true;
    this.isSaved = false;
    this.formSoggettoCorrelato.enable();
    this.formSoggettoCorrelato.get("dati-anagrafici.codiceFiscale").disable();
    this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").disable();
    this.formSoggettoCorrelato.get("attivita-economica.descrizioneAttivita").disable();
    this.formSoggettoCorrelato.get("attivita-economica.quotaPartecipazione").disable();
    //this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").disable();

    this.formSoggettoCorrelato.get("dati-anagrafici.pec").disable();

    if (!this.nazioneSelected?.value || this.nazioneSelected.value.idNazione.toString() !== "118") {
      this.formSoggettoCorrelato.get("sede-legale.regione").disable();
    }
    if (!this.nazioneSelected?.value || this.nazioneSelected.value.idNazione.toString() !== "118"
      || !this.regioneSelected?.value) {
      this.formSoggettoCorrelato.get("sede-legale.provincia").disable();
    }
    if (!this.nazioneSelected?.value || (this.nazioneSelected.value.idNazione.toString() === "118" &&
      (!this.regioneSelected?.value || !this.provinciaSelected?.value))) {
      this.formSoggettoCorrelato.get("sede-legale.comune").disable();
    }
    if (!this.comuneSelected?.value || this.comuneSelected?.value?.cap) {
      this.formSoggettoCorrelato.get("sede-legale.cap").disable();
    }
    // console.log(this.formSoggettoCorrelato);
  }

  isNotEditSoggetto(): void {
    this.modificaControl = false;
    this.errorModifica = false;
    this.editForm = false;
    this.formSoggettoCorrelato.disable();
    //this.location.back();
  }

  salvaModifiche(){
    
    this.loadedSave = false; 
    this.soggObj.idUtenteAgg = this.idUtente; 
    this.modificaControl = false; 
    this.errorModifica = false;

    if(this.soggObj.ragSoc !=this.formSoggettoCorrelato.get("dati-anagrafici.ragioneSociale").value ){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.ragSoc = this.formSoggettoCorrelato.get("dati-anagrafici.ragioneSociale").value; 
      this.modificaControl = true; 
    }
    if(this.soggObj.cfSoggetto != this.formSoggettoCorrelato.get("dati-anagrafici.codiceFiscale").value){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.cfSoggetto = this.formSoggettoCorrelato.get("dati-anagrafici.codiceFiscale").value;
      this.modificaControl = true; 
    }
    if( this.soggObj.idFormaGiuridica != this.formSoggettoCorrelato.get("dati-anagrafici.formaGiuridica").value.idFormaGiuridica){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.idFormaGiuridica = this.formSoggettoCorrelato.get("dati-anagrafici.formaGiuridica").value.idFormaGiuridica;
      this.modificaControl = true; 
    }
    if(this.soggObj.flagPubblicoPrivato != this.formSoggettoCorrelato.get('dati-anagrafici.pubblicoPrivatoRadio').value){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.flagPubblicoPrivato = this.formSoggettoCorrelato.get('dati-anagrafici.pubblicoPrivatoRadio').value;
      this.modificaControl = true; 
    }
    if(this.formSoggettoCorrelato.get('dati-anagrafici.codiceUniIpa').value!=null && this.soggObj.codUniIpa != this.formSoggettoCorrelato.get('dati-anagrafici.codiceUniIpa').value ){
      this.soggObj.codUniIpa = this.formSoggettoCorrelato.get('dati-anagrafici.codiceUniIpa').value; 
      this.soggObj.datiAnagrafici = true; 
      this.modificaControl = true; 
  }
    if(this.soggObj.idTipoSoggCorr != this.formSoggettoCorrelato.get('dati-anagrafici.ruolo').value.idTipoSoggCorr){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.idTipoSoggCorr = this.formSoggettoCorrelato.get('dati-anagrafici.ruolo').value.idTipoSoggCorr;
      this.modificaControl = true; 
    }
    if(this.soggObj.descPec != this.formSoggettoCorrelato.get('dati-anagrafici.pec').value){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.descPec = this.formSoggettoCorrelato.get('dati-anagrafici.pec').value;
      this.modificaControl = true; 
    }
    if( this.soggObj.dtCostituzione !=  this.formSoggettoCorrelato.get('dati-anagrafici.dtCostituzione').value){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.dtCostituzione =  this.formSoggettoCorrelato.get('dati-anagrafici.dtCostituzione').value;
      this.modificaControl = true; 
    }
    if( this.soggObj.dtCostituzione !=  this.formSoggettoCorrelato.get('dati-anagrafici.dtCostituzione').value){
      this.soggObj.datiAnagrafici = true;
      this.soggObj.dtCostituzione =  this.formSoggettoCorrelato.get('dati-anagrafici.dtCostituzione').value;
      this.modificaControl = true; 
    }

    
    if(this.soggObj.descIndirizzo != this.formSoggettoCorrelato.get('sede-legale.indirizzo').value){
      this.soggObj.sedeLegale = true; 
      this.soggObj.descIndirizzo = this.formSoggettoCorrelato.get('sede-legale.indirizzo').value;
      this.modificaControl = true; 
    }
    if( this.soggObj.pIva != this.formSoggettoCorrelato.get('sede-legale.partitaIva').value){
      this.soggObj.sedeLegale = true;
      this.soggObj.pIva = this.formSoggettoCorrelato.get('sede-legale.partitaIva').value;
      this.modificaControl = true; 
    }
    if(this.nazioneSelected.value.idNazione ==118){
      this.soggObj.idNazione =118; 
      
      if(this.soggObj.idComune != this.comuneSelected.value.idComune){
        this.soggObj.sedeLegale = true;
        this.soggObj.idRegione = this.regioneSelected.value.idRegione; 
        this.soggObj.idProvincia = this.provinciaSelected.value.idProvincia; 
        this.soggObj.idComune = this.comuneSelected.value.idComune; 
        this.soggObj.cap = this.formSoggettoCorrelato.get('sede-legale.cap').value;
        this.modificaControl = true;  
      }
     } else {
       if(this.soggObj.idNazione != this.nazioneSelected.value.idNazione){
         this.soggObj.sedeLegale = true;
         this.soggObj.idNazione = this.nazioneSelected.value.idNazione; 
         this.soggObj.idComune = this.comuneSelected.value.idComune; 
         this.modificaControl = true; 
       }
     }

    if( this.soggObj.idAttAteco != this.atecoSelected.value.idAttAteco){
      this.soggObj.attivitaEconomica = true; 
      this.soggObj.idAttAteco = this.atecoSelected.value.idAttAteco;
      this.modificaControl = true; 
    }
    if(this.soggObj.flagRatingLeg != this.formSoggettoCorrelato.get('attivita-economica.ratingLegalita').value){
      this.soggObj.attivitaEconomica = true;
      this.soggObj.flagRatingLeg = this.formSoggettoCorrelato.get('attivita-economica.ratingLegalita').value;
      this.modificaControl = true; 
    }
    if((this.formSoggettoCorrelato.get('attivita-economica.statoAttivita').value)?this.soggObj.idStatoAtt !=  this.formSoggettoCorrelato.get('attivita-economica.statoAttivita').value.idAttivita:this.soggObj.idStatoAtt= null){
      this.soggObj.attivitaEconomica = true;
      (this.formSoggettoCorrelato.get('attivita-economica.statoAttivita').value)?this.soggObj.idStatoAtt 
      = this.formSoggettoCorrelato.get('attivita-economica.statoAttivita').value.idAttivita:this.soggObj.idStatoAtt= null;
      this.modificaControl = true; 
    }
    if(this.soggObj.dtInizioAttEsito != this.formSoggettoCorrelato.get('attivita-economica.dtInizioAttivita').value){
      this.soggObj.attivitaEconomica = true;
      this.soggObj.dtInizioAttEsito = this.formSoggettoCorrelato.get('attivita-economica.dtInizioAttivita').value;
      this.modificaControl = true; 
    }
    if(this.soggObj.periodoScadEse != this.formSoggettoCorrelato.get('attivita-economica.periodoChiusuraEsercizio').value){
      this.soggObj.attivitaEconomica = true;
      this.soggObj.periodoScadEse = this.formSoggettoCorrelato.get('attivita-economica.periodoChiusuraEsercizio').value;
      this.modificaControl = true; 
    }
    if( this.soggObj.dtUltimoEseChiuso != this.formSoggettoCorrelato.get('attivita-economica.dtChiusuraEsercizio').value){
      this.soggObj.attivitaEconomica = true;
      this.soggObj.dtUltimoEseChiuso = this.formSoggettoCorrelato.get('attivita-economica.dtChiusuraEsercizio').value;
      this.modificaControl = true; 
    }
    if(this.soggObj.quotaPartecipazione != this.formSoggettoCorrelato.get('attivita-economica.quotaPartecipazione').value){
      this.soggObj.attivitaEconomica = true;
      this.soggObj.quotaPartecipazione = this.formSoggettoCorrelato.get('attivita-economica.quotaPartecipazione').value;
      this.modificaControl = true; 
    }

    if(this.soggObj.numIscrizione != this.formSoggettoCorrelato.get('dati-iscrizione.numeroRea').value){
      this.soggObj.datiIscrizione = true;
      this.soggObj.numIscrizione = this.formSoggettoCorrelato.get('dati-iscrizione.numeroRea').value;
      this.modificaControl = true; 
    }
    if(this.soggObj.dtIscrizione != this.formSoggettoCorrelato.get('dati-iscrizione.dtIscrizione').value){
      this.soggObj.datiIscrizione = true;
      this.soggObj.dtIscrizione = this.formSoggettoCorrelato.get('dati-iscrizione.dtIscrizione').value;
      this.modificaControl = true; 
    }
    if(this.soggObj.idProvinciaIscrizione != this.formSoggettoCorrelato.get('dati-iscrizione.provinciaIscrizione').value.idProvincia){
      this.soggObj.datiIscrizione = true;
      this.soggObj.idProvinciaIscrizione = this.formSoggettoCorrelato.get('dati-iscrizione.provinciaIscrizione').value.idProvincia;
      this.modificaControl = true; 
    }

    if((this.formSoggettoCorrelato.get('sezione-appartenenza.sezioneAppartenenza').value)?this.soggObj.idSezioneSpeciale !=this.formSoggettoCorrelato.get('sezione-appartenenza.sezioneAppartenenza').value.idSezioneSpeciale: this.soggObj.idSezioneSpeciale = null){
      this.soggObj.sezioneAppartenenza = true;
      (this.formSoggettoCorrelato.get('sezione-appartenenza.sezioneAppartenenza').value)?this.soggObj.idSezioneSpeciale =this.formSoggettoCorrelato.get('sezione-appartenenza.sezioneAppartenenza').value.idSezioneSpeciale: this.soggObj.idSezioneSpeciale = null; 
      this.modificaControl = true; 
    }
    console.log(this.soggObj);

    if(this.modificaControl== true){
      this.subscribers.salvaModifiche = this.datiDomandaService.modificaEG(this.soggObj, this.idSoggetto, this.numeroDomanda).subscribe(data=>{
        if(data==true){
          this.loadedSave = true; 
          this.isSaved= true; 
          //this.editForm = false; 
          this.isNotEditSoggetto();
          
        } else {
          this.isSaved = false; 
          this.loadedSave = true; 
        }
      })
    }else {
      this.errorModifica= true; 
      this.loadedSave = true;
      this.messageError="Modificare almeno un campo!"
    }
    // if(this.modificaControl){    
    // }else {
    //   this.errorModifica= true; 
    //   this.loadedSave = true;
    //   this.messageError="Modificare almeno un campo!"
    // }

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

  isLoading() {
    if (!this.userloaded || !this.loadedEG) {
      return true;
    }
    return false;
  }


}
