/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AtlanteVO } from 'src/app/gestione-anagrafica/commons/dto/atlante-vo';
import { Nazioni } from 'src/app/gestione-anagrafica/commons/dto/nazioni';
import { Regioni } from 'src/app/gestione-anagrafica/commons/dto/regioni';
import { RuoloDTO } from 'src/app/gestione-anagrafica/commons/dto/ruoloDTO';
import { SoggettoFisicoIndipendenteDomanda } from 'src/app/gestione-anagrafica/commons/dto/SoggettoFisicoIndipendenteDomanda';
import { AnagraficaBeneficiarioService } from 'src/app/gestione-anagrafica/services/anagrafica-beneficiario.service';
import { DatiDomandaService } from 'src/app/gestione-anagrafica/services/dati-domanda-service';
import { ModificaAnagraficaService } from 'src/app/gestione-anagrafica/services/modifica-anagrafica.service';
import { SoggettiIndipendentiDomandaService } from 'src/app/gestione-anagrafica/services/soggetti-indipendenti-domanda.service';
import { map, startWith } from 'rxjs/operators';
import { BeneficiarioSoggCorrPF } from 'src/app/gestione-anagrafica/commons/dto/beneficiario-sogg-corr-PF';
import { Location } from '@angular/common';


@Component({
  selector: 'app-persona-fisica-indipendente-domanda',
  templateUrl: './persona-fisica-indipendente-domanda.component.html',
  styleUrls: ['./persona-fisica-indipendente-domanda.component.scss']
})
export class PersonaFisicaIndipendenteDomandaComponent implements OnInit {

  setBackText: string = "Torna ai dati generali del beneficiario";

  formSoggettoCorrelato!: FormGroup;

  tipologiaSoggettoCorrelato!: string;
  nagSoggettoCorrelato!: string;

  soggObj: SoggettoFisicoIndipendenteDomanda = Object.create(null);

  idSoggetto!: any;
  numeroDomanda!: any;

  spinner: boolean = false;
  sub!: Subscription;
  benefSoggCorrPF: BeneficiarioSoggCorrPF = new BeneficiarioSoggCorrPF();
  benefSoggCorrPFDaSalvare: BeneficiarioSoggCorrPF = new BeneficiarioSoggCorrPF();
  loadedEG: boolean = true;
  loadedPF: boolean = true;
  idEnteGiuridico: any;
  idSoggettoCorr: string;
  tipoSoggettoCorr: string;
  idDomanda: any;
  tipoSoggettoPF: string;
  pec: string;
  descProvinciaIscriz: any;
  descSezioneSpeciale: any;
  ruoloPF: any;
  ruoloData: RuoloDTO[] = [];
  loadedRuoli: boolean;


  nazioneData: Nazioni[] = [];
  filteredOptionsNazioni: Observable<Nazioni[]>;
  nazioneSelected: FormControl = new FormControl();

  nazioneNascitaData: Nazioni[] = [];
  filteredOptionsNazioniNascita: Observable<Nazioni[]>;
  nazioneNascitaSelected: FormControl = new FormControl();

  regioneData: Regioni[] = [];
  filteredOptionsRegioni: Observable<Regioni[]>;
  regioneSelected: FormControl = new FormControl();

  regioneNascitaData: Regioni[] = [];
  filteredOptionsRegioniNascita: Observable<Regioni[]>;
  regioneNascitaSelected: FormControl = new FormControl();

  provinciaData: AtlanteVO[] = [];
  filteredOptionsProvince: Observable<AtlanteVO[]>;
  provinciaSelected: FormControl = new FormControl();

  provinciaNascitaData: AtlanteVO[] = [];
  filteredOptionsProvinceNascita: Observable<AtlanteVO[]>;
  provinciaNascitaSelected: FormControl = new FormControl();

  comuneData: AtlanteVO[] = [];
  filteredOptionsComuni: Observable<AtlanteVO[]>;
  comuneSelected: FormControl = new FormControl();

  comuneNascitaData: AtlanteVO[] = [];
  filteredOptionsComuniNascita: Observable<AtlanteVO[]>;
  comuneNascitaSelected: FormControl = new FormControl();

  loadedNazioni: boolean;
  loadedRegioni: boolean = true;
  loadedProvince: boolean = true;
  loadedComuni: boolean = true;

  loadedNazioniNascita: boolean;
  loadedRegioniNascita: boolean = true;
  loadedProvinceNascita: boolean = true;
  loadedComuniNascita: boolean = true;

  editForm: boolean = false;
  loadedSalva: boolean = true;
  isSave: boolean;
  modificaControl: boolean;
  errorModifica: boolean;
  userloaded: boolean;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: number;
  messageError: string;
  isMessageErrorVisible: boolean;
  ndg: string;

  constructor(private activatedRoute: ActivatedRoute,
    private datiDomandaService: DatiDomandaService,
    private router: Router,
    private userService: UserService,
    private modificaAnagraficaService: ModificaAnagraficaService,
    private fb: FormBuilder,
    private handleExceptionService: HandleExceptionService,
    private anagBeneService: AnagraficaBeneficiarioService,
    private location: Location,) {
  }

  ngOnInit(): void {
    this.tipologiaSoggettoCorrelato = this.activatedRoute.snapshot.queryParamMap.get('tipologiaSoggetto');
    this.nagSoggettoCorrelato = this.activatedRoute.snapshot.queryParamMap.get('nag');

    this.idSoggetto = this.activatedRoute.snapshot.queryParamMap.get('idSoggetto');
    this.numeroDomanda = this.activatedRoute.snapshot.queryParamMap.get('numeroDomanda');

    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
      }
    });
    this.formSoggettoCorrelato = this.fb.group({
      'dati-anagrafici': this.fb.group({
        cognome: [{ value: '', disabled: true }],
        nome: [{ value: '', disabled: true }],
        ruolo: [{ value: '', disabled: true }],
        codiceFiscale: [{ value: '', disabled: true }],
        dtNascita: [{ value: '', disabled: true }],
        comune: [{ value: '', disabled: true }],
        provincia: [{ value: '', disabled: true }],
        regione: [{ value: '', disabled: true }],
        nazione: [{ value: '', disabled: true }]
      }),
      'residenza': this.fb.group({
        indirizzo: [{ value: '', disabled: true }],
        comuneResidenza: [{ value: '', disabled: true }],
        provinciaResidenza: [{ value: '', disabled: true }],
        cap: [{ value: '', disabled: true }],
        regioneResidenza: [{ value: '', disabled: true }],
        nazioneResidenza: [{ value: '', disabled: true }]
      }),
      'quota-partecipazione': this.fb.group({
        quotaPartecipazione: [{ value: '', disabled: true }]
      })
    });

    this.getBeneficiarioSoggCorrPF();

  }

  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null && valore != '';
  }
  getBeneficiarioSoggCorrPF() {

    this.loadedPF = false;

    this.subscribers.benefPF = this.anagBeneService.getBenefSoggCorrPF(this.nagSoggettoCorrelato, this.numeroDomanda).subscribe(data => {
      if (data) {
        this.benefSoggCorrPF = data;
        this.benefSoggCorrPFDaSalvare = data; 
        this.ndg = this.benefSoggCorrPF.ndg;
        this.loadedPF = true;
        this.formSoggettoCorrelato = this.fb.group({
          'dati-anagrafici': this.fb.group({
            cognome: [{ value: this.benefSoggCorrPF.cognome, disabled: true }],
            nome: [{ value: this.benefSoggCorrPF.nome, disabled: true }],
            ruolo: [{ value: null, disabled: true }],
            codiceFiscale: [{ value: this.benefSoggCorrPF.codiceFiscale, disabled: true }],
            dtNascita: [{ value: this.benefSoggCorrPF.dataDiNascita, disabled: true }],
            comune: [{ value: this.benefSoggCorrPF.comuneDiNascita, disabled: true }],
            provincia: [{ value: this.benefSoggCorrPF.provinciaDiNascita, disabled: true }],
            regione: [{ value: this.benefSoggCorrPF.regioneDiNascita, disabled: true }],
            nazione: [{ value: this.benefSoggCorrPF.nazioneDiNascita, disabled: true }]
          }),
          'residenza': this.fb.group({
            indirizzo: [{ value: this.benefSoggCorrPF.indirizzoPF, disabled: true }],
            comuneResidenza: [{ value: this.benefSoggCorrPF.comunePF, disabled: true }],
            provinciaResidenza: [{ value: this.benefSoggCorrPF.provinciaPF, disabled: true }],
            cap: [{ value: this.benefSoggCorrPF.capPF, disabled: true }],
            regioneResidenza: [{ value: this.benefSoggCorrPF.regionePF, disabled: true }],
            nazioneResidenza: [{ value: this.benefSoggCorrPF.nazionePF, disabled: true }]
          }),
          'quota-partecipazione': this.fb.group({
            quotaPartecipazione: [{ value: this.benefSoggCorrPF.quotaPartecipazione, disabled: true }]
          })
        })
        this.loadData();
        //this.regioneNascitaSelected.value.idRegione = this.benefSoggCorrPF.idRegioneDiNascita;

      } else {
        this.loadedPF = true;
      }
    })

  }

  loadData() {

    this.loadedRuoli = false;
    this.subscribers.getRuoli = this.anagBeneService.getElencoRuoloIndipendenti().subscribe(data => {
      if (data) {
        this.ruoloData = data;
        console.log(this.ruoloData);

        if (this.benefSoggCorrPF.idTipoSoggCorr) {
          this.formSoggettoCorrelato.get("dati-anagrafici").get("ruolo").setValue(this.ruoloData.find(f => f.idTipoSoggCorr === this.benefSoggCorrPF.idTipoSoggCorr));
        }

      }
      this.loadedRuoli = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei ruoli.");
      this.loadedRuoli = true;
    });
    this.loadedNazioniNascita = false;
    this.loadedNazioni = false;
    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.formSoggettoCorrelato.get('residenza.nazioneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.formSoggettoCorrelato.get("residenza") && this.benefSoggCorrPF.idNazionePF) {
          this.formSoggettoCorrelato.get("residenza.nazioneResidenza").setValue(this.nazioneData.find(f => f.idNazione === this.benefSoggCorrPF.idNazionePF.toString()));
          this.nazioneSelected.setValue(this.nazioneData.find(f => f.idNazione === this.benefSoggCorrPF.idNazionePF.toString()));
          if (this.benefSoggCorrPF.idNazionePF == 118)
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

    this.subscribers.getNazioniNascita = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneNascitaData = data;
        this.filteredOptionsNazioniNascita = this.formSoggettoCorrelato.get('dati-anagrafici.nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioniNascita(name) : this.nazioneNascitaData.slice())
          );

        if (this.formSoggettoCorrelato.get("dati-anagrafici") && this.benefSoggCorrPF.idNazioneDiNascita) {
          this.formSoggettoCorrelato.get("dati-anagrafici.nazione").setValue(this.nazioneNascitaData.find(f => f.idNazione === this.benefSoggCorrPF.idNazioneDiNascita.toString()));
          this.nazioneNascitaSelected.setValue(this.nazioneNascitaData.find(f => f.idNazione === this.benefSoggCorrPF.idNazioneDiNascita.toString()));
          if (this.benefSoggCorrPF.idNazioneDiNascita == 118)
            this.loadRegioniNascita(true);
          else
            this.loadComuniNascitaEsteri(true);
        }
      }
      this.loadedNazioniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle nazioni.");
      this.loadedNazioniNascita = true;
    });
  }
  loadRegioniNascita(init?: boolean) {
    this.loadedRegioniNascita = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneNascitaData = data;
        this.filteredOptionsRegioniNascita = this.formSoggettoCorrelato.get('dati-anagrafici.regione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name, 1) : this.regioneNascitaData.slice())
          );
        if (this.formSoggettoCorrelato.get("dati-anagrafici.nazione") && init) {

          this.formSoggettoCorrelato.get("dati-anagrafici.regione").setValue(this.regioneNascitaData.find(f => f.idRegione === this.benefSoggCorrPF.idRegioneDiNascita.toString()));
          this.regioneNascitaSelected.setValue(this.regioneNascitaData.find(f => f.idRegione === this.benefSoggCorrPF.idRegioneDiNascita.toString()));
          if (this.regioneNascitaSelected.value != null)
            this.loadProvinceNascita(init);
        }
      }
      this.loadedRegioniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle regioni.");
      this.loadedRegioniNascita = true;
    });
  }
  loadRegioni(init?: boolean) {
    this.loadedRegioni = false;
    this.subscribers.getRegioni = this.datiDomandaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioneData = data;
        this.filteredOptionsRegioni = this.formSoggettoCorrelato.get('residenza.regioneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name, 0) : this.regioneData.slice())
          );
        if (this.formSoggettoCorrelato.get("residenza") && init) {
          this.formSoggettoCorrelato.get("residenza.regioneResidenza").setValue(this.regioneData.find(f => f.idRegione === this.benefSoggCorrPF.idRegionePF.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.benefSoggCorrPF.idRegionePF.toString()));
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
  loadProvinceNascita(init?: boolean) {
    this.loadedProvinceNascita = false;

    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneNascitaSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaNascitaData = data;
        this.filteredOptionsProvinceNascita = this.formSoggettoCorrelato.get('dati-anagrafici.provincia').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name, 1) : this.provinciaNascitaData.slice())
          );
        if (this.formSoggettoCorrelato.get("dati-anagrafici.regione") && this.benefSoggCorrPF.idProvinciaDiNascita && init) {
          this.formSoggettoCorrelato.get("dati-anagrafici.provincia").setValue(this.provinciaNascitaData.find(f => f.idProvincia === this.benefSoggCorrPF.idProvinciaDiNascita.toString()));
          this.provinciaNascitaSelected.setValue(this.provinciaNascitaData.find(f => f.idProvincia === this.benefSoggCorrPF.idProvinciaDiNascita.toString()));
          if (this.provinciaNascitaSelected.value != null)
            this.loadComuniNascitaItalia(init);

        }
      }
      this.loadedProvinceNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento delle province.");
      this.loadedProvinceNascita = true;
    });
  }
  loadProvince(init?: boolean) {
    this.loadedProvince = false;
    this.subscribers.getProvinciaConIdRegione = this.anagBeneService.getProvinciaConIdRegione(this.regioneSelected.value.idRegione).subscribe(data => {
      if (data) {
        this.provinciaData = data;
        this.filteredOptionsProvince = this.formSoggettoCorrelato.get('residenza.provinciaResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name, 0) : this.provinciaData.slice())
          );
        if (this.formSoggettoCorrelato.get("residenza.regioneResidenza") && init && this.benefSoggCorrPF) {
          this.formSoggettoCorrelato.get("residenza.provinciaResidenza").setValue(this.provinciaData.find(f => f.idProvincia === this.benefSoggCorrPF.idProvinciaPF.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.benefSoggCorrPF.idProvinciaPF.toString()));
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
        this.filteredOptionsComuni = this.formSoggettoCorrelato.get('residenza.comuneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 0) : this.comuneData.slice())
          );
        if (this.formSoggettoCorrelato.get("residenza.provinciaResidenza") && this.benefSoggCorrPF && init) {
          this.formSoggettoCorrelato.get("residenza.comuneResidenza").setValue(this.comuneData.find(f => f.idComune === this.benefSoggCorrPF.idComunePF.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.benefSoggCorrPF.idComunePF.toString()));
          //if (this.comuneSelected.value)
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
  loadComuniNascitaItalia(init?: boolean) {
    this.loadedComuniNascita = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaNascitaSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneNascitaData = data;
        this.filteredOptionsComuniNascita = this.formSoggettoCorrelato.get('dati-anagrafici.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 1) : this.comuneNascitaData.slice())
          );
        if (this.formSoggettoCorrelato.get("dati-anagrafici.provincia") && init && this.benefSoggCorrPF.idComuneDiNascita > 0) {
          this.formSoggettoCorrelato.get("dati-anagrafici.comune").setValue(this.comuneNascitaData.find(f => f.idComune === this.benefSoggCorrPF.idComuneDiNascita.toString()));
          this.comuneNascitaSelected.setValue(this.comuneNascitaData.find(f => f.idComune === this.benefSoggCorrPF.idComuneDiNascita.toString()));
          //this.setCap();
        }
      }
      this.loadedComuniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuniNascita = true;
    });
  }

  loadComuniEsteri(init?: boolean) {
    this.loadedComuni = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneData = data;
        this.filteredOptionsComuni = this.formSoggettoCorrelato.get('residenza.comuneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 0) : this.comuneData.slice())
          );
        if (this.benefSoggCorrPF && this.benefSoggCorrPF.idComunePF && this.formSoggettoCorrelato.get("residenza") && init) {
          this.formSoggettoCorrelato.get("residenza.comuneResidenza").setValue(this.comuneData.find(f => f.idComune === this.benefSoggCorrPF.idComunePF.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.benefSoggCorrPF.idComunePF.toString()));
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
  loadComuniNascitaEsteri(init?: boolean) {
    this.loadedComuniNascita = false;
    this.subscribers.getComuneEstero = this.anagBeneService.getComuneEstero(this.nazioneNascitaSelected.value.idNazione).subscribe(data => {
      if (data) {
        this.comuneNascitaData = data;
        this.filteredOptionsComuniNascita = this.formSoggettoCorrelato.get('dati-anagrafici.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 1) : this.comuneNascitaData.slice())
          );
        if (this.formSoggettoCorrelato.get("dati-anagrafici.comune") && init) {
          this.formSoggettoCorrelato.get("dati-anagrafici.comune").setValue(this.comuneNascitaData.find(f => f.idComune === this.benefSoggCorrPF.idComuneDiNascita.toString()));
          this.comuneSelected.setValue(this.comuneNascitaData.find(f => f.idComune === this.benefSoggCorrPF.idComuneDiNascita.toString()));
          //this.setCap();
        }
      }
      this.loadedComuniNascita = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei comuni.");
      this.loadedComuniNascita = true;
    });
  }

  // setCap() {
  //   this.formSoggettoCorrelato.get('residenza.cap').setValue(this.comuneSelected.value.cap);
  //   if (this.comuneSelected.value.cap) {
  //     this.formSoggettoCorrelato.get("residenza.cap").disable();
  //   } else {
  //     this.formSoggettoCorrelato.get("residenza.cap").enable();
  //   }
  // }


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
          //this.setCap();
        }
        break;
      default:
        break;
    }
  }
  clickNascita(event: any, type: string) {
    switch (type) {
      case "N":
        if (event.option.value !== this.nazioneNascitaSelected.value) {
          this.nazioneNascitaSelected.setValue(event.option.value);
          this.resetRegioneNascita();
          if (this.nazioneNascitaSelected.value.idNazione.toString() === "118") { //ITALIA
            this.loadRegioniNascita();
          } else {
            this.loadComuniNascitaEsteri();
          }
        }
        break;
      case "R":
        if (event.option.value !== this.regioneNascitaSelected.value) {
          this.regioneNascitaSelected.setValue(event.option.value);
          this.resetProvinciaNascita();
          this.loadProvinceNascita();
        }
        break;
      case "P":
        if (event.option.value !== this.provinciaNascitaSelected.value) {
          this.provinciaNascitaSelected.setValue(event.option.value);
          this.resetComuneNascita();
          this.loadComuniNascitaItalia();
        }
        break;
      case "C":
        if (event.option.value !== this.comuneNascitaSelected.value) {
          this.comuneNascitaSelected.setValue(event.option.value);
          // this.setCap();
        }
        break;
      default:
        break;
    }
  }


  check(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneSelected || (this.formSoggettoCorrelato.get('residenza.nazioneResidenza') && this.nazioneSelected.value !== this.formSoggettoCorrelato.get('residenza.nazioneResidenza').value)) {
            this.formSoggettoCorrelato.get('residenza.nazioneResidenza').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.formSoggettoCorrelato.get('residenza.regioneResidenza') && this.regioneSelected.value !== this.formSoggettoCorrelato.get('residenza.regioneResidenza').value)) {
            this.formSoggettoCorrelato.get('residenza.regioneResidenza').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.formSoggettoCorrelato.get('residenza.provinciaResidenza') && this.provinciaSelected.value !== this.formSoggettoCorrelato.get('residenza.provinciaResidenza').value)) {
            this.formSoggettoCorrelato.get('residenza.provinciaResidenza').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.formSoggettoCorrelato.get('residenza.comuneResidenza') && this.comuneSelected.value !== this.formSoggettoCorrelato.get('residenza.comuneResidenza').value)) {
            this.formSoggettoCorrelato.get('residenza.comuneResidenza').setValue(null);
            this.comuneSelected = new FormControl();
            this.resetCap();
          }
          break;
        default:
          break;
      }
    }, 200);
  }
  checkNascita(type: string) {
    setTimeout(() => {
      switch (type) {
        case "N":
          if (!this.nazioneNascitaSelected || (this.formSoggettoCorrelato.get('dati-anagrafici.nazione') && this.nazioneNascitaSelected.value !== this.formSoggettoCorrelato.get('dati-anagrafici.nazione').value)) {
            this.formSoggettoCorrelato.get('dati-anagrafici.nazione').setValue(null);
            this.nazioneNascitaSelected = new FormControl();
            this.resetRegioneNascita();
          }
          break;
        case "R":
          if (!this.regioneNascitaSelected || (this.formSoggettoCorrelato.get('dati-anagrafici.regione') && this.regioneNascitaSelected.value !== this.formSoggettoCorrelato.get('dati-anagrafici.regione').value)) {
            this.formSoggettoCorrelato.get('dati-anagrafici.regione').setValue(null);
            this.regioneNascitaSelected = new FormControl();
            this.resetProvinciaNascita()
          }
          break;
        case "P":
          if (!this.provinciaNascitaSelected || (this.formSoggettoCorrelato.get('dati-anagrafici.provincia') && this.provinciaNascitaSelected.value !== this.formSoggettoCorrelato.get('dati-anagrafici.provincia').value)) {
            this.formSoggettoCorrelato.get('dati-anagrafici.provincia').setValue(null);
            this.provinciaNascitaSelected = new FormControl();
            this.resetComuneNascita();
          }
          break;
        default:
          break;
      }
    }, 200);
  }


  resetRegione() {
    this.regioneData = [];
    this.regioneSelected = new FormControl();
    this.formSoggettoCorrelato.get('residenza.regioneResidenza').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggettoCorrelato.get('residenza.regioneResidenza').enable();
    } else {
      this.formSoggettoCorrelato.get('residenza.regioneResidenza').disable();
    }
    this.resetProvincia();
  }

  resetRegioneNascita() {
    this.regioneNascitaData = [];
    this.regioneNascitaSelected = new FormControl();
    this.formSoggettoCorrelato.get('dati-anagrafici.regione').setValue(null);
    if (this.nazioneNascitaSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggettoCorrelato.get('dati-anagrafici.regione').enable();
    } else {
      this.formSoggettoCorrelato.get('dati-anagrafici.regione').disable();
    }
    this.resetProvinciaNascita();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.formSoggettoCorrelato.get('residenza.provinciaResidenza').setValue(null);
    if (this.regioneSelected?.value) {
      this.formSoggettoCorrelato.get('residenza.provinciaResidenza').enable();
    } else {
      this.formSoggettoCorrelato.get('residenza.provinciaResidenza').disable();
    }
    this.resetComune();
  }
  resetProvinciaNascita() {
    this.provinciaNascitaData = [];
    this.provinciaNascitaSelected = new FormControl();
    this.formSoggettoCorrelato.get('dati-anagrafici.provincia').setValue(null);
    if (this.regioneNascitaSelected?.value) {
      this.formSoggettoCorrelato.get('dati-anagrafici.provincia').enable();
    } else {
      this.formSoggettoCorrelato.get('dati-anagrafici.provincia').disable();
    }
    this.resetComuneNascita();
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.formSoggettoCorrelato.get('residenza.comuneResidenza').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggettoCorrelato.get('residenza.comuneResidenza').enable();
    } else {
      this.formSoggettoCorrelato.get('residenza.comuneResidenza').disable();
    }
    this.resetCap();
  }
  resetComuneNascita() {
    this.comuneNascitaData = [];
    this.comuneSelected = new FormControl();
    this.formSoggettoCorrelato.get('dati-anagrafici.comune').setValue(null);
    if (this.provinciaNascitaSelected?.value || (this.nazioneNascitaSelected?.value?.idNazione && this.nazioneNascitaSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggettoCorrelato.get('dati-anagrafici.comune').enable();
    } else {
      this.formSoggettoCorrelato.get('dati-anagrafici.comune').disable();
    }
    //this.resetCap();
  }

  resetCap() {
    this.formSoggettoCorrelato.get('residenza.cap').setValue(null);
   // this.formSoggettoCorrelato.get('residenza.cap').disable();
  }
  // resetCapNascita() {
  //   this.formSoggettoCorrelato.get('dati-anagrafici.cap').setValue(null);
  //   this.formSoggettoCorrelato.get('dati-anagrafici.cap').disable();
  // }

  modificaBSoggettoCorr() {

    this.formSoggettoCorrelato.enable();
    if (this.formSoggettoCorrelato.get("residenza") && this.formSoggettoCorrelato.get("residenza").get("indirizzo")
      && (!this.formSoggettoCorrelato.get("residenza").get("indirizzo").value || !this.formSoggettoCorrelato.get("residenza").get("indirizzo").value.length)) {
      this.formSoggettoCorrelato.get("residenza").get("indirizzo").setErrors({ error: "required" })
    }
    this.formSoggettoCorrelato.markAllAsTouched();

    // this.router.navigate(["/drawer/" + this.idOp + "/modificaSoggCorrDipDom"],
    //   {
    //     queryParams: {
    //       idSoggetto: this.idSoggetto,
    //       numeroDomanda: this.numeroDomanda,
    //       idDomanda: this.idDomanda,
    //       tipoSoggetto: this.tipoSoggetto,
    //       idSoggettoCorr: this.idSoggettoCorr,
    //       tipoSoggettoCorr: this.tipoSoggettoCorr
    //     }
    //   });

  }

  editSoggetto(): void {
    this.editForm = true;
    this.formSoggettoCorrelato.enable();
    this.formSoggettoCorrelato.get("dati-anagrafici.codiceFiscale").disable();
    this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").disable();

    this.formSoggettoCorrelato.get("dati-anagrafici").disable();
    // this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").disable();

    // if (!this.nazioneSelected?.value || this.nazioneSelected.value.idNazione.toString() !== "118") {
    //   this.formSoggettoCorrelato.get("dati-anagrafici.regione").disable();
    //   this.formSoggettoCorrelato.get("dati-anagrafici.provincia").disable();
    // }
    if (!this.nazioneNascitaSelected?.value || this.nazioneNascitaSelected.value.idNazione.toString() !== "118") {
      this.formSoggettoCorrelato.get("residenza.regioneResidenza").disable();
      this.formSoggettoCorrelato.get("residenza.provinciaResidenza").disable();
    }

   // this.formSoggettoCorrelato.enable();
    // if (this.formSoggettoCorrelato.get("residenza") && this.formSoggettoCorrelato.get("residenza").get("indirizzo")
    //   && (!this.formSoggettoCorrelato.get("residenza").get("indirizzo").value || !this.formSoggettoCorrelato.get("residenza").get("indirizzo").value.length)) {
    //   this.formSoggettoCorrelato.get("residenza").get("indirizzo").setErrors({ error: "required" })
    // }
    //this.formSoggettoCorrelato.markAllAsTouched();
  }
  salvaModifiche() {

    this.loadedSalva = false;

    //  if(this.formSoggettoCorrelato.get("dati-anagrafici").markAsTouched){
    //   this.benefSoggCorrPF.datiAnagrafici = true; 
    //   this.benefSoggCorrPF.nome= this.formSoggettoCorrelato.get("dati-anagrafici.nome").value;
    //   this.benefSoggCorrPF.cognome= this.formSoggettoCorrelato.get("dati-anagrafici.cognome").value;
    //   this.benefSoggCorrPF.codiceFiscale = this.formSoggettoCorrelato.get("dati-anagrafici.codiceFiscale").value
    //   //this.benefSoggCorrPF.idTipoSoggCorr = this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").value.idTipoSoggCorr
    //   this.benefSoggCorrPF.dataDiNascita = this.formSoggettoCorrelato.get("dati-anagrafici.dtNascita").value

    //   if(this.nazioneNascitaSelected.value.idNazione == 118){
    //     this.benefSoggCorrPF.idNazioneDiNascita = this.nazioneNascitaSelected.value.idNazione; 
    //     this.benefSoggCorrPF.idRegioneDiNascita = this.regioneNascitaSelected.value.idRegione;
    //     this.benefSoggCorrPF.idProvinciaDiNascita = this.provinciaNascitaSelected.value.idProvincia;
    //     this.benefSoggCorrPF.idComuneDiNascita = this.comuneNascitaSelected.value.idComune 
    //   } else {
    //     this.benefSoggCorrPF.idNazioneDiNascita=this.nazioneNascitaSelected.value.idNazione;
    //     this.benefSoggCorrPF.idComuneDiNascita = this.comuneNascitaSelected.value.idComune; 
    //   } 

    //  }

    if (this.benefSoggCorrPF.nome != this.formSoggettoCorrelato.get("dati-anagrafici.nome").value) {
      this.benefSoggCorrPF.datiAnagrafici = true;
      this.benefSoggCorrPF.nome = this.formSoggettoCorrelato.get("dati-anagrafici.nome").value;
      this.modificaControl = true;
    }
    if (this.benefSoggCorrPF.cognome != this.formSoggettoCorrelato.get("dati-anagrafici.cognome").value) {
      this.benefSoggCorrPF.datiAnagrafici = true;
      this.benefSoggCorrPF.cognome = this.formSoggettoCorrelato.get("dati-anagrafici.cognome").value;
      this.modificaControl = true;
    }
    if (this.benefSoggCorrPF.dataDiNascita != this.formSoggettoCorrelato.get("dati-anagrafici.dtNascita").value) {
      this.benefSoggCorrPF.datiAnagrafici = true;
      this.benefSoggCorrPF.dataDiNascita = this.formSoggettoCorrelato.get("dati-anagrafici.dtNascita").value;
      this.modificaControl = true;
    }
    //  if(this.benefSoggCorrPF.idTipoSoggCorr != this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").value.idTipoSoggCorr){
    //   this.benefSoggCorrPF.datiAnagrafici = true;
    //   this.benefSoggCorrPF.idTipoSoggCorr = this.formSoggettoCorrelato.get("dati-anagrafici.ruolo").value.idTipoSoggCorr;
    //  }
    if (this.nazioneNascitaSelected.value && this.nazioneNascitaSelected.value.idNazione == 118) {
      if (this.benefSoggCorrPF.idComuneDiNascita != this.comuneNascitaSelected.value.idComune) {
        this.benefSoggCorrPF.datiAnagrafici = true;
        this.benefSoggCorrPF.idComuneDiNascita = this.comuneNascitaSelected.value.idComune
        this.benefSoggCorrPF.idNazioneDiNascita = this.nazioneNascitaSelected.value.idNazione;
        this.benefSoggCorrPF.idRegioneDiNascita = this.regioneNascitaSelected.value.idRegione;
        this.benefSoggCorrPF.idProvinciaDiNascita = this.provinciaNascitaSelected.value.idProvincia;
        this.modificaControl = true;
      }
    } else {
      this.benefSoggCorrPF.datiAnagrafici = true;
      this.benefSoggCorrPF.idNazioneDiNascita = this.nazioneNascitaSelected.value.idNazione;
      this.benefSoggCorrPF.idComuneDiNascita = this.comuneNascitaSelected.value.idComune;
      this.modificaControl = true;
    }

    if (this.benefSoggCorrPF.indirizzoPF != this.formSoggettoCorrelato.get("residenza.indirizzo").value) {
      this.benefSoggCorrPF.sedeLegale = true;
      this.benefSoggCorrPF.indirizzoPF = this.formSoggettoCorrelato.get("residenza.indirizzo").value;
      this.modificaControl = true;
    }

    if (this.nazioneSelected.value && this.nazioneSelected.value.idNazione == 118) {
      this.benefSoggCorrPF.idNazionePF = this.nazioneSelected.value.idNazione;

      if(this.regioneSelected.value!=null && this.regioneSelected.value.idRegione!=this.benefSoggCorrPF.idRegionePF){
        this.modificaControl = true;
        this.benefSoggCorrPF.sedeLegale = true;
        this.benefSoggCorrPF.idRegionePF = this.regioneSelected?.value.idRegione;
      }
      if( this.provinciaSelected.value!=null && this.benefSoggCorrPF.idProvinciaPF!= this.provinciaSelected.value.idProvincia){
        this.modificaControl = true;
        this.benefSoggCorrPF.sedeLegale = true;
        this.benefSoggCorrPF.idRegionePF = this.regioneSelected?.value.idRegione;
        this.benefSoggCorrPF.idProvinciaPF = this.provinciaSelected.value.idProvincia;
      }
      if ( this.comuneSelected.value!=null && this.benefSoggCorrPF.idComunePF != this.comuneSelected.value.idComune) {
        this.modificaControl = true;
        this.benefSoggCorrPF.sedeLegale = true;
        this.benefSoggCorrPF.idComunePF= this.comuneSelected.value.idComune
        this.benefSoggCorrPF.idRegionePF = this.regioneSelected.value.idRegione;
        this.benefSoggCorrPF.idProvinciaPF = this.provinciaSelected.value.idProvincia;
        this.benefSoggCorrPF.capPF = this.formSoggettoCorrelato.get("residenza.cap").value;
      } 
    } else {
      if (this.benefSoggCorrPF.idNazionePF > 0 && this.nazioneSelected.value && this.benefSoggCorrPF.idNazionePF != this.nazioneSelected.value?.idNazione) {
      this.benefSoggCorrPF.sedeLegale = true;
      this.benefSoggCorrPF.idNazionePF = this.nazioneSelected.value.idNazione;
      this.benefSoggCorrPF.idComunePF = this.comuneSelected.value.idComune;
      this.modificaControl = true;
      }
    }

    if (this.benefSoggCorrPF.quotaPartecipazione != this.formSoggettoCorrelato.get("quota-partecipazione.quotaPartecipazione").value) {
      this.benefSoggCorrPF.quota = true;
      this.benefSoggCorrPF.quotaPartecipazione = this.formSoggettoCorrelato.get("quota-partecipazione.quotaPartecipazione").value;
      this.modificaControl= true;
    }

    this.benefSoggCorrPF.idUtenteAgg = this.idUtente;
    console.log(this.benefSoggCorrPF);

    if (this.modificaControl == true) {

      this.subscribers.salvaModifiche = this.anagBeneService.modificaSoggCorrIndipDaDomandaPF(this.benefSoggCorrPF, this.nagSoggettoCorrelato, this.numeroDomanda).subscribe(data => {
        if (data == true) {
          this.loadedSalva = true
          this.isSave = data;
          this.isNotEditSoggetto();
        } else {
          this.isSave = data;
        }

      })
    } else {
      this.errorModifica = true;
      this.loadedSalva = true;
      this.messageError = "Modificare almeno un campo!";
    }



  }

  isNotEditSoggetto(): void {
    //this.location.back();
    this.modificaControl = false;
    this.errorModifica = false;
    this.editForm = false;
    this.formSoggettoCorrelato.disable();
    this.getBeneficiarioSoggCorrPF();
    setTimeout(() => {
      this.ngOnInit()
    }, 3000); 
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
  displayFnProvinceNascita(prov: AtlanteVO): string {
    return prov && prov.siglaProvincia ? prov.descProvincia : '';
  }

  displayFnComuni(com: AtlanteVO): string {
    return com && com.descComune ? com.descComune : '';
  }

  private _filterNazioni(descrizione: string): Nazioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.nazioneData.filter(option => option.descNazione.toLowerCase().includes(filterValue));
  }
  private _filterNazioniNascita(descrizione: string): Nazioni[] {
    const filterValue = descrizione.toLowerCase();
    return this.nazioneNascitaData.filter(option => option.descNazione.toLowerCase().includes(filterValue));
  }

  private _filterRegioni(descrizione: string, flagNascita: number): Regioni[] {
    const filterValue = descrizione.toLowerCase();
    if (flagNascita == 0) {
      return this.regioneData.filter(option => option.descRegione.toLowerCase().includes(filterValue));
    } else {
      return this.regioneNascitaData.filter(option => option.descRegione.toLowerCase().includes(filterValue));
    }
  }

  private _filterProvince(descrizione: string, flagNascita: number): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    if (flagNascita == 0) {
      return this.provinciaData.filter(option => option.siglaProvincia.toLowerCase().includes(filterValue));
    } else {
      return this.provinciaNascitaData.filter(option => option.siglaProvincia.toLowerCase().includes(filterValue));

    }
  }

  private _filterComuni(descrizione: string, flasgNascita: number): AtlanteVO[] {
    const filterValue = descrizione.toLowerCase();
    if (flasgNascita == 0) {
      return this.comuneData.filter(option => option.descComune.toLowerCase().includes(filterValue));
    } else {
      return this.comuneNascitaData.filter(option => option.descComune.toLowerCase().includes(filterValue));
    }
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
    if (!this.userloaded || !this.loadedPF || !this.loadedComuni || !this.loadedSalva || !this.loadedComuniNascita
      || !this.loadedNazioni || !this.loadedRegioni || !this.loadedProvince || !this.loadedRuoli || !this.loadedComuniNascita
      || !this.loadedProvinceNascita || !this.loadedRegioniNascita) {
      return true;
    }
    return false;
  }

}