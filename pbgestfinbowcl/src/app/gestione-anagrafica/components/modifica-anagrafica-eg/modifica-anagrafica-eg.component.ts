/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { AnagraficaBeneficiarioSec } from 'src/app/gestione-anagrafica/commons/dto/anagrafica-beneficiario';
import { AnagraficaBeneficiarioService } from 'src/app/gestione-anagrafica/services/anagrafica-beneficiario.service';
import { ModificaAnagraficaService } from 'src/app/gestione-anagrafica/services/modifica-anagrafica.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Comuni } from '../../commons/dto/comuni';
import { ModificaEnteGiuridico } from '../../commons/dto/modificaEnteGiuridico';
import { Nazioni } from '../../commons/dto/nazioni';
import { Province } from '../../commons/dto/provincie';
import { Regioni } from '../../commons/dto/regioni';
import { statoDomanda } from '../../commons/dto/statoDomanda';

@Component({
  selector: 'app-modifica-anagrafica-eg',
  templateUrl: './modifica-anagrafica-eg.component.html',
  styleUrls: ['./modifica-anagrafica-eg.component.scss']
})
export class ModificaAnagraficaEGComponent implements OnInit {
  public myForm: FormGroup;

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;

  beneficiario: AnagraficaBeneficiarioSec;
  modifiche: ModificaEnteGiuridico;
  statoUltimaDomanda: statoDomanda;
  idSoggetto: any;
  idEnteGiuridico: any;
  spinner: boolean;
  getConcluse: number = 0;

  nazioni: Array<Nazioni>;
  regioni: Array<Regioni>;
  provincie: Array<Province>;
  comuni: Array<Comuni>;
  descComuni: Array<string> = [];
  filteredOptions: Observable<string[]>;

  public subscribers: any = {};
  dataSource: any;
  handleExceptionService: any;
  idProgetto:any; 
  numeroDomanda: string;

  constructor(
    private modificaAnagraficaService: ModificaAnagraficaService,
    private anagBeneService: AnagraficaBeneficiarioService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar,
    private router: Router,
    private sharedService: SharedService,) { }

  ngOnInit() {
    this.spinner = true;
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');  
    this.idProgetto =this.route.snapshot.queryParamMap.get("idProgetto");
    this.numeroDomanda = this.route.snapshot.queryParamMap.get("idDomanda");

    this.myForm = this.fb.group({
      // //DATI ANAGRAFICI:
      ragSociale: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      formaGiuridica: new FormControl('', [Validators.required]),
      iva: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      descTipoSoggetto: new FormControl({ value: '', disabled: true }),
      codiceFiscale: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      dataCostituzione: new FormControl({ value: '', disabled: true }),
      pec: new FormControl({ value: '', disabled: true }),
      //SEDE LEGALE:
      indirizzoSede: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      ivaSede: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      descComuneSede: new FormControl('', [Validators.required, Validators.maxLength(50)]),
       idComuneSede: new FormControl({ value: '', disabled: false }),
      descProvinciaSede: new FormControl({ value: '', disabled: false }),
      idProvinciaSede: new FormControl({ value: '', disabled: false }),
      capSede: new FormControl('', [Validators.required, Validators.maxLength(50)]),
       idRegioneSede: new FormControl({ value: '', disabled: false }),
      descRegioneSede: new FormControl({ value: '', disabled: false }),
       idNazioneSede: new FormControl({ value: '', disabled: false }),
      descNazioneSede: new FormControl({ value: '', disabled: false }),
      ateco: new FormControl('', [Validators.required, Validators.maxLength(50)]),
      descAttivita: new FormControl({ value: '', disabled: false }),
      //DATI ISCRIZIONE:
      numIscrizione: new FormControl({ value: '', disabled: true }),
      dataIscrizione: new FormControl({ value: '', disabled: false }),
      descProvinciaIscrizione: new FormControl({ value: '', disabled: true }),
      idProvinciaIscrizione: new FormControl({ value: '', disabled: true }),
      //ATTIVITA':
      stato: new FormControl({ value: '', disabled: true }),
      dataCessazione: new FormControl({ value: '', disabled: true }),
      causaleCessazione: new FormControl({ value: '', disabled: true })
    });
    ;
    this.subscribers.anagBeneficiarioInfo$ = this.anagBeneService.getAnagBeneficiario(this.idSoggetto, this.idProgetto,  this.numeroDomanda).subscribe(data => {
      if (data) {
        this.beneficiario = data;
        this.myForm.setValue({
           //DATI ANAGRAFICI
           ragSociale: this.beneficiario[0].ragSoc,
           formaGiuridica: this.beneficiario[0].descFormaGiur,
           descTipoAnagrafica: this.beneficiario[0].descTipoAnagrafica,
           flagPubblicoPrivato: this.beneficiario[0].flagPubblicoPrivato,
           codUniIpa: this.beneficiario[0].codUniIpa,
           iva: this.beneficiario[0].pIva,
           descTipoSoggetto: this.beneficiario[0].descTipoSoggetto,
           codiceFiscale: this.beneficiario[0].cfSoggetto,
           dataCostituzione: new Date(Date.parse(this.beneficiario[0].dtCostituzione)),
           //dataCostituzione: 'Da caricare sul D.B.',
           pec: this.controlloCampoValido(this.beneficiario[0].pec) ? this.beneficiario[0].pec : 'nehom.pec@aruba.it',
           //SEDE LEGALE:
           indirizzoSede: this.beneficiario[0].descIndirizzo,
           ivaSede: this.beneficiario[0].pIva,
           descComuneSede: this.beneficiario[0].descComune,
           idComuneSede: this.beneficiario[0].idComune,
           descProvinciaSede: this.beneficiario[0].descProvincia,
           idProvinciaSede: this.beneficiario[0].idProvincia,
           capSede: this.beneficiario[0].cap,
           idRegioneSede: this.beneficiario[0].idRegione,
           descRegioneSede: this.beneficiario[0].descRegione,
           idNazioneSede: this.beneficiario[0].idNazione,
           descNazioneSede: this.beneficiario[0].descNazione,
           //DATI ISCRIZIONE:
           numIscrizione: this.controlloCampoValido(this.beneficiario[0].numIscrizione) ? this.beneficiario[0].numIscrizione : '1254262',
           sezSpec: this.controlloCampoValido(this.beneficiario[0].descSezioneSpeciale) ? this.beneficiario[0].descSezioneSpeciale : 'società semplice',
           dataIscrizione: new Date(Date.parse(this.beneficiario[0].dtIscrizione)),
           descProvinciaIscrizione: this.controlloCampoValido(this.beneficiario[0].descProvinciaIscrizione) ? this.beneficiario[0].descProvinciaIscrizione : 'TORINO',
           idProvinciaIscrizione: this.beneficiario[0].idProvinciaIscrizione,
           //ATTIVITA':
           //stato: this.controlloCampoValido(this.beneficiario[0].stato) ? this.beneficiario[0].stato : 'N.D.',
           //dataCessazione: 'N.D.',
           //causaleCessazione: this.controlloCampoValido(this.beneficiario[0].causaleCessazione) ? this.beneficiario[0].causaleCessazione : 'N.D.',
           ateco: this.beneficiario[0].codAteco,
           descAttivita: this.controlloCampoValido(this.beneficiario[0].descAteco) ? this.beneficiario[0].descAteco : 'N.D.',
           flagRatingLeg:(this.beneficiario[0].flagRatingLeg)?this.beneficiario[0].flagRatingLeg:1,
           descStatoAttivita: this.controlloCampoValido(this.beneficiario[0].descStatoAttivita) ? this.beneficiario[0].descStatoAttivita : 'ATTIVA',
           dtInizioAttEsito: this.controlloCampoValido(this.beneficiario[0].dtInizioAttEsito) ? this.beneficiario[0].dtInizioAttEsito : '18/12/1982',
           periodoScadEse: this.controlloCampoValido(this.beneficiario[0].periodoScadEse) ? this.beneficiario[0].periodoScadEse : 'N.D.',
           dtUltimoEseChiuso: this.controlloCampoValido(this.beneficiario[0].dtUltimoEseChiuso) ? this.beneficiario[0].dtUltimoEseChiuso : 'N.D.'
         });

        let formControls = this.myForm.getRawValue()
        console.log(formControls)

        //SERVIZIO CHE MI PRENDE TUTTI I COMUNI DELLA PROVINCIA DI RESIDENZA
        // this.modificaAnagraficaService.getComuni(this.myForm.controls.idProvinciaSede.value);
        this.subscribers.comuniInfo$ = this.modificaAnagraficaService.comuniInfo$.subscribe(data => {
          if (data) {
            this.comuni = data;
            //QUI SVUOTO L'ARRAY DI DESC_COMUNI_RES PERCHE' NON CAPISCO COME MAI VIENE POPOLATO 2 VOLTE QUINDI SI CREEREBBERO COPIE
            this.descComuni = [];
            this.comuni.forEach(val => {
              if (val) {
                this.descComuni.push(val.descComune);
              }
            });
            this.bloccaSpinner();
          }
        });
        this.bloccaSpinner();
      }
    });

    this.anagBeneService.getStatoDomanda(this.idSoggetto, this.numeroDomanda);
    this.subscribers.statoUltimaDomandaInfo$ = this.anagBeneService.statoUltimaDomandaInfo$.subscribe(data => {
      if (data) {
        this.statoUltimaDomanda = data;
        this.bloccaSpinner();
      }
    });

    this.getProvincieMeth();
    this.getRegioniMeth();
    this.getNazioniMeth();
   

    //OBSERVABLE PER L'AUTOCOMPLETE
    this.filteredOptions = this.myForm.controls.descComuneSede.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value)),
    );

  }


  //FUNZIONE CHE MI CONTROLLA CHE UN CAMPO SIA DIVERSO DA NULL E STRINGA VUOTA
  controlloCampoValido(valore) {
    return valore != null && valore != '';
  }

  //FUNZIONE CHE SI OCCUPA DI INTERROMPERE LO SPINNER
  bloccaSpinner() {
    this.getConcluse += 1;
    if (this.getConcluse > 2) {
      this.spinner = false;
    }
  }


  //FUNZIONE PER LA VALIDAZIONE
  public hasError = (controlName: string, errorName: string) => {
    return this.myForm.controls[controlName].hasError(errorName);
  }


  //FILTRO PER AUTOCOMPLETE
  private _filter(value: string): string[] {
    if (value.length < 3) {
      return [];
    }
    const filterValue = value.toLowerCase();
    return this.descComuni.filter(option => option.toLowerCase().includes(filterValue));
  }

  //METODO PER PRENDERMI I COMUNI LEGATI ALLA PROVINCIA DI RESIDENZA
  getComuni(provincia: HTMLSelectElement) {
    this.modificaAnagraficaService.getComuni(provincia.value);
    this.subscribers.comuniInfo$ = this.modificaAnagraficaService.comuniInfo$.subscribe(data => {
      if (data) {
        this.comuni = data;
        //QUI SVUOTO L'ARRAY DI DESC_COMUNI_RES PERCHE' NON CAPISCO COME MAI VIENE POPOLATO 2 VOLTE QUINDI SI CREEREBBERO COPIE
        this.descComuni = [];
        this.comuni.forEach(val => {
          if (val) {
            this.descComuni.push(val.descComune);
          }
        });
      }
    });
    this.myForm.controls.comune.setValue("")
  }

  //METODO PER PRENDERMI LE PROVINCIE LEGATE ALLA REGIONE
  getProvincieMeth() {
    this.modificaAnagraficaService.getProvincie();
    this.subscribers.provincieInfo$ = this.modificaAnagraficaService.provincieInfo$.subscribe(data => {
      if (data) {
        this.provincie = data;
      }
    });
  }

  //METODO PER PRENDERMI LE REGIONI LEGATE ALLA NAZIONE
  getRegioniMeth() {
    this.modificaAnagraficaService.getRegioni();
    this.subscribers.regioniInfo$ = this.modificaAnagraficaService.regioniInfo$.subscribe(data => {
      if (data) {
        this.regioni = data;
      }
    });
  }

  //METODO PER PRENDERMI LE NAZIONI
  getNazioniMeth() {
    this.modificaAnagraficaService.getNazioni();
    this.subscribers.nazioniInfo$ = this.modificaAnagraficaService.nazioniInfo$.subscribe(data => {
      if (data) {
        this.nazioni = data;
      }
    });
  }


  //METODO PER FARE L'UPDATE
  salvaModifiche() {
    let beneficiarioModificato: AnagraficaBeneficiarioSec;
    let update = false;
    let formControls = this.myForm.getRawValue();

    beneficiarioModificato = Object.assign({}, this.beneficiario[0])
    this.modificaAnagraficaService.aggiornaBeneficiario(beneficiarioModificato, formControls);

    update = this.modificaAnagraficaService.comparazione(beneficiarioModificato, this.beneficiario[0]);
    this.modifiche = new ModificaEnteGiuridico(this.idSoggetto, beneficiarioModificato, update);

    // //SERVIZIO DI POST:
  //   this.modificaAnagraficaService.updateAnagraficaEG(this.modifiche).subscribe(data => {
  //     if (data) {
  //       return true;
  //     }
  //   }, err => {
  //     this.handleExceptionService.handleBlockingError(err);
  //   });
  //   //ROUTE VERSO VISUALIZZA ENTE GIURIDICO
  //   this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"], { queryParams: { idSoggetto: this.idSoggetto, idEnteGiuridico: this.idEnteGiuridico } });
  }
}
