/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { AtlanteVO } from '../../commons/dto/atlante-vo';
import { BeneficiarioSoggCorrPF } from '../../commons/dto/beneficiario-sogg-corr-PF';
import { Nazioni } from '../../commons/dto/nazioni';
import { Regioni } from '../../commons/dto/regioni';
import { VistaStoricoPF } from '../../commons/dto/vista-versione-pf';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { DatiDomandaService } from '../../services/dati-domanda-service';
import { ModificaAnagraficaService } from '../../services/modifica-anagrafica.service';
import { StoricoBenficiarioService } from '../../services/storico-benficiario.service';
import { map, startWith } from 'rxjs/operators';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';

@Component({
  selector: 'app-visualizza-storico-pf',
  templateUrl: './visualizza-storico-pf.component.html',
  styleUrls: ['./visualizza-storico-pf.component.scss']
})
export class VisualizzaStoricoPfComponent implements OnInit {

  public myForm: FormGroup;
  public formSoggetto: FormGroup;
  idVersione: any;
  idSoggetto: any;
  spinner: boolean;
  getConcluse: number = 0;

  storico: VistaStoricoPF;
  idDomanda: any;
  idProgetto: any;
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

  public subscribers: any = {};
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
  //user: UserInfoSec;
  idUtente: number;
  messageError: string;
  isMessageErrorVisible: boolean;
  ndg: string;
  soggObj: BeneficiarioSoggCorrPF = new BeneficiarioSoggCorrPF();
  tipoAnagraficaData: AttivitaDTO[] = [];
  setBackText: string = "Torna allo storico"
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,private anagBeneService: AnagraficaBeneficiarioService,
    private modificaAnagraficaService: ModificaAnagraficaService,
    private handleExceptionService: HandleExceptionService,
    private datiDomandaService: DatiDomandaService,
    private storicoService: StoricoBenficiarioService) { }

  ngOnInit() {
    this.spinner = true;
    this.idVersione = this.route.snapshot.queryParamMap.get('idVersione');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.idDomanda = this.route.snapshot.queryParamMap.get('idDomanda');
    this.idProgetto = this.route.snapshot.queryParamMap.get('idProgetto');

    this.myForm = this.fb.group({
      //DATI ANAGRAFICI:
      cognome: new FormControl({ value: '', disabled: true }),
      nome: new FormControl({ value: '', disabled: true }),
      tipoSoggetto: new FormControl({ value: '', disabled: true }),
      codiceFiscale: new FormControl({ value: '', disabled: true }),
      dataDiNascita: new FormControl({ value: '', disabled: true }),
      comuneDiNascita: new FormControl({ value: '', disabled: true }),
      provinciaDiNascita: new FormControl({ value: '', disabled: true }),
      regioneDiNascita: new FormControl({ value: '', disabled: true }),
      nazioneDiNascita: new FormControl({ value: '', disabled: true }),
      //RESIDENZA:
      indirizzoPF: new FormControl({ value: '', disabled: true }),
      comunePF: new FormControl({ value: '', disabled: true }),
      provinciaPF: new FormControl({ value: '', disabled: true }),
      capPF: new FormControl({ value: '', disabled: true }),
      regionePF: new FormControl({ value: '', disabled: true }),
      nazionePF: new FormControl({ value: '', disabled: true }),
    });

    this.formSoggetto = this.fb.group({
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
      })
    });

    if (this.idVersione.includes(" - idVersioneDomanda")) {

      this.idVersione = this.idVersione.replace(" - idVersioneDomanda", "");

      this.storicoService.getVistaStoricoDomandaPF(this.idSoggetto, this.idVersione).subscribe(data => {
        if (data) {
          this.storico = data;

          this.myForm.setValue({
            //DATI ANAGRAFICI:
            cognome: [this.storico[0].cognome],
            nome: [this.storico[0].nome],
            tipoSoggetto: [this.storico[0].tipoSoggetto],
            codiceFiscale: [this.storico[0].codiceFiscale],
            dataDiNascita: [this.storico[0].dataDiNascita],
            comuneDiNascita: [this.storico[0].comuneDiNascita],
            provinciaDiNascita: [this.storico[0].provinciaDiNascita],
            regioneDiNascita: [this.storico[0].regioneDiNascita],
            nazioneDiNascita: [this.storico[0].nazioneDiNascita],
            //RESIDENZA:
            indirizzoPF: [this.storico[0].indirizzoPF],
            comunePF: [this.storico[0].comunePF],
            provinciaPF: [this.storico[0].provinciaPF],
            capPF: [this.storico[0].capPF],
            regionePF: [this.storico[0].regionePF],
            nazionePF: [this.storico[0].nazionePF],
          });
          this.bloccaSpinner();
        }
      });
    } else {
      this.idVersione = this.idVersione.replace(" - idVersioneProgetto", "");

      this.storicoService.getVistaStoricoProgettoPF(this.idSoggetto, this.idVersione).subscribe(data => {
        if (data) {
          this.storico = data;

          this.myForm.setValue({
            //DATI ANAGRAFICI:
            cognome: [this.storico[0].cognome],
            nome: [this.storico[0].nome],
            tipoSoggetto: [this.storico[0].tipoSoggetto],
            codiceFiscale: [this.storico[0].codiceFiscale],
            dataDiNascita: [this.storico[0].dataDiNascita],
            comuneDiNascita: [this.storico[0].comuneDiNascita],
            provinciaDiNascita: [this.storico[0].provinciaDiNascita],
            regioneDiNascita: [this.storico[0].regioneDiNascita],
            nazioneDiNascita: [this.storico[0].nazioneDiNascita],
            //RESIDENZA:
            indirizzoPF: [this.storico[0].indirizzoPF],
            comunePF: [this.storico[0].comunePF],
            provinciaPF: [this.storico[0].provinciaPF],
            capPF: [this.storico[0].capPF],
            regionePF: [this.storico[0].regionePF],
            nazionePF: [this.storico[0].nazionePF],
          });
          this.bloccaSpinner();
        }
      })
    }


    this.getBenefFisico();
  }



  getBenefFisico() {
    this.subscribers.benefPF = this.anagBeneService.getAnagBeneFisicoNew(this.idSoggetto, this.idDomanda, this.idProgetto).subscribe(data => {

      if (data) {
        this.soggObj = data;
        this.ndg = this.soggObj.ndg;
        this.formSoggetto = this.fb.group({
          'dati-anagrafici': this.fb.group({
            cognome: [{ value: this.soggObj.cognome, disabled: true }],
            nome: [{ value: this.soggObj.nome, disabled: true }],
            ruolo: [{ value: null, disabled: true }],
            codiceFiscale: [{ value: this.soggObj.codiceFiscale, disabled: true }],
            dtNascita: [{ value: this.soggObj.dataDiNascita, disabled: true }],
            comune: [{ value: this.soggObj.comuneDiNascita, disabled: true }],
            provincia: [{ value: this.soggObj.provinciaDiNascita, disabled: true }],
            regione: [{ value: this.soggObj.regioneDiNascita, disabled: true }],
            nazione: [{ value: this.soggObj.nazioneDiNascita, disabled: true }]
          }),
          'residenza': this.fb.group({
            indirizzo: [{ value: this.soggObj.indirizzoPF, disabled: true }],
            comuneResidenza: [{ value: this.soggObj.comunePF, disabled: true }],
            provinciaResidenza: [{ value: this.soggObj.provinciaPF, disabled: true }],
            cap: [{ value: this.soggObj.capPF, disabled: true }],
            regioneResidenza: [{ value: this.soggObj.regionePF, disabled: true }],
            nazioneResidenza: [{ value: this.soggObj.nazionePF, disabled: true }]
          })
        })
        this.loadData();
        //this.regioneNascitaSelected.value.idRegione = this.benefSoggCorrPF.idRegioneDiNascita;

      } else {
        this.spinner = true;
      }
    })

  }


  loadData() {
    this.subscribers.getElencoTipoAnag = this.anagBeneService.getElencoTipoAnag().subscribe(data => {
      if (data) {
        this.tipoAnagraficaData = data;
        if (this.soggObj && this.soggObj.idTipoAnagrafica && this.formSoggetto.get("dati-anagrafici")) {
          this.formSoggetto.get("dati-anagrafici").get("ruolo").setValue(this.tipoAnagraficaData.find(t => t.idAttivita === this.soggObj.idTipoAnagrafica));
        }
      }
      // this.loadedFormeGiuridiche = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento del tipo anagrafica");
      // this.loadedFormeGiuridiche = true;

    });
    this.loadedNazioniNascita = false;
    this.loadedNazioni = false;
    this.subscribers.getNazioni = this.datiDomandaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioneData = data;
        this.filteredOptionsNazioni = this.formSoggetto.get('residenza.nazioneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioni(name) : this.nazioneData.slice())
          );

        if (this.formSoggetto.get("residenza") && this.soggObj.idNazionePF) {
          this.formSoggetto.get("residenza.nazioneResidenza").setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazionePF.toString()));
          this.nazioneSelected.setValue(this.nazioneData.find(f => f.idNazione === this.soggObj.idNazionePF.toString()));
          if (this.soggObj.idNazionePF == 118)
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
        this.filteredOptionsNazioniNascita = this.formSoggetto.get('dati-anagrafici.nazione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descNazione),
            map(name => name ? this._filterNazioniNascita(name) : this.nazioneNascitaData.slice())
          );

        if (this.formSoggetto.get("dati-anagrafici") && this.soggObj.idNazioneDiNascita) {
          this.formSoggetto.get("dati-anagrafici.nazione").setValue(this.nazioneNascitaData.find(f => f.idNazione === this.soggObj.idNazioneDiNascita.toString()));
          this.nazioneNascitaSelected.setValue(this.nazioneNascitaData.find(f => f.idNazione === this.soggObj.idNazioneDiNascita.toString()));
          if (this.soggObj.idNazioneDiNascita == 118)
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
        this.filteredOptionsRegioniNascita = this.formSoggetto.get('dati-anagrafici.regione').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name, 1) : this.regioneNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.nazione") && init) {

          this.formSoggetto.get("dati-anagrafici.regione").setValue(this.regioneNascitaData.find(f => f.idRegione === this.soggObj.idRegioneDiNascita.toString()));
          this.regioneNascitaSelected.setValue(this.regioneNascitaData.find(f => f.idRegione === this.soggObj.idRegioneDiNascita.toString()));
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
        this.filteredOptionsRegioni = this.formSoggetto.get('residenza.regioneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descRegione),
            map(name => name ? this._filterRegioni(name, 0) : this.regioneData.slice())
          );
        if (this.formSoggetto.get("residenza") && init) {
          this.formSoggetto.get("residenza.regioneResidenza").setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegionePF.toString()));
          this.regioneSelected.setValue(this.regioneData.find(f => f.idRegione === this.soggObj.idRegionePF.toString()));
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
        this.filteredOptionsProvinceNascita = this.formSoggetto.get('dati-anagrafici.provincia').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name, 1) : this.provinciaNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.regione") && this.soggObj.idProvinciaDiNascita && init) {
          this.formSoggetto.get("dati-anagrafici.provincia").setValue(this.provinciaNascitaData.find(f => f.idProvincia === this.soggObj.idProvinciaDiNascita.toString()));
          this.provinciaNascitaSelected.setValue(this.provinciaNascitaData.find(f => f.idProvincia === this.soggObj.idProvinciaDiNascita.toString()));
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
        this.filteredOptionsProvince = this.formSoggetto.get('residenza.provinciaResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descProvincia),
            map(name => name ? this._filterProvince(name, 0) : this.provinciaData.slice())
          );
        if (this.formSoggetto.get("residenza.regioneResidenza") && init && this.soggObj) {
          this.formSoggetto.get("residenza.provinciaResidenza").setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvinciaPF.toString()));
          this.provinciaSelected.setValue(this.provinciaData.find(f => f.idProvincia === this.soggObj.idProvinciaPF.toString()));
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
        this.filteredOptionsComuni = this.formSoggetto.get('residenza.comuneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 0) : this.comuneData.slice())
          );
        if (this.formSoggetto.get("residenza.provinciaResidenza") && this.soggObj && init) {
          this.formSoggetto.get("residenza.comuneResidenza").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
          if (this.comuneSelected.value)
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
  loadComuniNascitaItalia(init?: boolean) {
    this.loadedComuniNascita = false;
    this.subscribers.getComuni = this.modificaAnagraficaService.getComuni(this.provinciaNascitaSelected.value.idProvincia).subscribe(data => {
      if (data) {
        this.comuneNascitaData = data;
        this.filteredOptionsComuniNascita = this.formSoggetto.get('dati-anagrafici.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 1) : this.comuneNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.provincia") && init && this.soggObj.idComuneDiNascita > 0) {
          this.formSoggetto.get("dati-anagrafici.comune").setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
          this.comuneNascitaSelected.setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
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
        this.filteredOptionsComuni = this.formSoggetto.get('residenza.comuneResidenza').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 0) : this.comuneData.slice())
          );
        if (this.soggObj && this.soggObj.idComunePF && this.formSoggetto.get("residenza") && init) {
          this.formSoggetto.get("residenza.comuneResidenza").setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
          this.comuneSelected.setValue(this.comuneData.find(f => f.idComune === this.soggObj.idComunePF.toString()));
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
        this.filteredOptionsComuniNascita = this.formSoggetto.get('dati-anagrafici.comune').valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descComune),
            map(name => name ? this._filterComuni(name, 1) : this.comuneNascitaData.slice())
          );
        if (this.formSoggetto.get("dati-anagrafici.comune") && init) {
          this.formSoggetto.get("dati-anagrafici.comune").setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
          this.comuneSelected.setValue(this.comuneNascitaData.find(f => f.idComune === this.soggObj.idComuneDiNascita.toString()));
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

  setCap() {
    this.formSoggetto.get('residenza.cap').setValue(this.comuneSelected.value.cap);
    if (this.comuneSelected.value.cap) {
      this.formSoggetto.get("residenza.cap").disable();
    } else {
      this.formSoggetto.get("residenza.cap").enable();
    }
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
          if (!this.nazioneSelected || (this.formSoggetto.get('residenza.nazioneResidenza') && this.nazioneSelected.value !== this.formSoggetto.get('residenza.nazioneResidenza').value)) {
            this.formSoggetto.get('residenza.nazioneResidenza').setValue(null);
            this.nazioneSelected = new FormControl();
            this.resetRegione();
          }
          break;
        case "R":
          if (!this.regioneSelected || (this.formSoggetto.get('residenza.regioneResidenza') && this.regioneSelected.value !== this.formSoggetto.get('residenza.regioneResidenza').value)) {
            this.formSoggetto.get('residenza.regioneResidenza').setValue(null);
            this.regioneSelected = new FormControl();
            this.resetProvincia()
          }
          break;
        case "P":
          if (!this.provinciaSelected || (this.formSoggetto.get('residenza.provinciaResidenza') && this.provinciaSelected.value !== this.formSoggetto.get('residenza.provinciaResidenza').value)) {
            this.formSoggetto.get('residenza.provinciaResidenza').setValue(null);
            this.provinciaSelected = new FormControl();
            this.resetComune();
          }
          break;
        case "C":
          if (!this.comuneSelected || (this.formSoggetto.get('residenza.comuneResidenza') && this.comuneSelected.value !== this.formSoggetto.get('residenza.comuneResidenza').value)) {
            this.formSoggetto.get('residenza.comuneResidenza').setValue(null);
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
          if (!this.nazioneNascitaSelected || (this.formSoggetto.get('dati-anagrafici.nazione') && this.nazioneNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.nazione').value)) {
            this.formSoggetto.get('dati-anagrafici.nazione').setValue(null);
            this.nazioneNascitaSelected = new FormControl();
            this.resetRegioneNascita();
          }
          break;
        case "R":
          if (!this.regioneNascitaSelected || (this.formSoggetto.get('dati-anagrafici.regione') && this.regioneNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.regione').value)) {
            this.formSoggetto.get('dati-anagrafici.regione').setValue(null);
            this.regioneNascitaSelected = new FormControl();
            this.resetProvinciaNascita()
          }
          break;
        case "P":
          if (!this.provinciaNascitaSelected || (this.formSoggetto.get('dati-anagrafici.provincia') && this.provinciaNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.provincia').value)) {
            this.formSoggetto.get('dati-anagrafici.provincia').setValue(null);
            this.provinciaNascitaSelected = new FormControl();
            this.resetComuneNascita();
          }
          break;
        case "C":
          if (!this.comuneNascitaSelected || (this.formSoggetto.get('dati-anagrafici.comune') && this.comuneNascitaSelected.value !== this.formSoggetto.get('dati-anagrafici.comune').value)) {
            this.formSoggetto.get('dati-anagrafici.comune').setValue(null);
            this.comuneNascitaSelected = new FormControl();
            //this.resetComuneNascita();
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
    this.formSoggetto.get('residenza.regioneResidenza').setValue(null);
    if (this.nazioneSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggetto.get('residenza.regioneResidenza').enable();
    } else {
      this.formSoggetto.get('residenza.regioneResidenza').disable();
    }
    this.resetProvincia();
  }

  resetRegioneNascita() {
    this.regioneNascitaData = [];
    this.regioneNascitaSelected = new FormControl();
    this.formSoggetto.get('dati-anagrafici.regione').setValue(null);
    if (this.nazioneNascitaSelected?.value?.idNazione?.toString() === "118") {
      this.formSoggetto.get('dati-anagrafici.regione').enable();
    } else {
      this.formSoggetto.get('dati-anagrafici.regione').disable();
    }
    this.resetProvinciaNascita();
  }

  resetProvincia() {
    this.provinciaData = [];
    this.provinciaSelected = new FormControl();
    this.formSoggetto.get('residenza.provinciaResidenza').setValue(null);
    if (this.regioneSelected?.value) {
      this.formSoggetto.get('residenza.provinciaResidenza').enable();
    } else {
      this.formSoggetto.get('residenza.provinciaResidenza').disable();
    }
    this.resetComune();
  }
  resetProvinciaNascita() {
    this.provinciaNascitaData = [];
    this.provinciaNascitaSelected = new FormControl();
    this.formSoggetto.get('dati-anagrafici.provincia').setValue(null);
    if (this.regioneNascitaSelected?.value) {
      this.formSoggetto.get('dati-anagrafici.provincia').enable();
    } else {
      this.formSoggetto.get('dati-anagrafici.provincia').disable();
    }
    this.resetComuneNascita();
  }

  resetComune() {
    this.comuneData = [];
    this.comuneSelected = new FormControl();
    this.formSoggetto.get('residenza.comuneResidenza').setValue(null);
    if (this.provinciaSelected?.value || (this.nazioneSelected?.value?.idNazione && this.nazioneSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggetto.get('residenza.comuneResidenza').enable();
    } else {
      this.formSoggetto.get('residenza.comuneResidenza').disable();
    }
    this.resetCap();
  }
  resetComuneNascita() {
    this.comuneNascitaData = [];
    this.comuneSelected = new FormControl();
    this.formSoggetto.get('dati-anagrafici.comune').setValue(null);
    if (this.provinciaNascitaSelected?.value || (this.nazioneNascitaSelected?.value?.idNazione && this.nazioneNascitaSelected.value.idNazione?.toString() !== "118")) {
      this.formSoggetto.get('dati-anagrafici.comune').enable();
    } else {
      this.formSoggetto.get('dati-anagrafici.comune').disable();
    }
    //this.resetCap();
  }

  resetCap() {
    this.formSoggetto.get('residenza.cap').setValue(null);
    this.formSoggetto.get('residenza.cap').disable();
  }
  // resetCapNascita() {
  //   this.formSoggettoCorrelato.get('dati-anagrafici.cap').setValue(null);
  //   this.formSoggettoCorrelato.get('dati-anagrafici.cap').disable();
  // }

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
  //FUNZIONE CHE SI OCCUPA DI INTERROMPERE LO SPINNER
  bloccaSpinner() {
    this.getConcluse += 1;
    if (this.getConcluse > 0) {
      this.spinner = false;
    }
  }

}
