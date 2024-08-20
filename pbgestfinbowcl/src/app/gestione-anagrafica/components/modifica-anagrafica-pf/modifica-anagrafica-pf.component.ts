/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AnagraficaBeneficiarioSec } from 'src/app/gestione-anagrafica/commons/dto/anagrafica-beneficiario';
import { AnagraficaBeneficiarioService } from 'src/app/gestione-anagrafica/services/anagrafica-beneficiario.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ModificaAnagraficaService } from 'src/app/gestione-anagrafica/services/modifica-anagrafica.service';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { AnagraficaBeneFisico } from '../../commons/dto/anagraficaBeneFisico';
import { Comuni } from '../../commons/dto/comuni';
import { ModificaPersonaFisica } from '../../commons/dto/modificaPersonaFisica';
import { Nazioni } from '../../commons/dto/nazioni';
import { Province } from '../../commons/dto/provincie';
import { Regioni } from '../../commons/dto/regioni';
import { statoDomanda } from '../../commons/dto/statoDomanda';

@Component({
  selector: 'app-modifica-anagrafica-pf',
  templateUrl: './modifica-anagrafica-pf.component.html',
  styleUrls: ['./modifica-anagrafica-pf.component.scss']
})
export class ModificaAnagraficaPFComponent implements OnInit {
  myForm: FormGroup;
  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  beneficiario: AnagraficaBeneficiarioSec;
  beneficiarioFisico: AnagraficaBeneFisico;
  dataUpdate: AnagraficaBeneFisico;
  modifiche: ModificaPersonaFisica;
  statoUltimaDomanda: statoDomanda;

  nazioni: Array<Nazioni>;
  regioni: Array<Regioni>;
  provincie: Array<Province>;
  comuni: Array<Comuni>;
  descComuni: Array<string> = [];
  descComuniRes: Array<string> = [];
  filteredOptions: Observable<string[]>;
  filteredOptions2: Observable<string[]>;
  spinner: boolean;
  getConcluse: number = 0;


  idSoggetto: any;
  verificaTipoSoggetto: any;

  public subscribers: any = {};
  dataSource: any;
  handleExceptionService: any;
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
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('verificaTipoSoggetto');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get("numeroDomanda");

    this.myForm = this.fb.group({
      //DATI ANAGRAFICI:
      cognome: new FormControl('', [Validators.required, Validators.maxLength(20)]),
      nome: new FormControl('', [Validators.required, Validators.maxLength(20)]),
      tipoSoggetto: new FormControl({ value: '', disabled: true }),
      codiceFiscale: new FormControl('', [Validators.required, Validators.maxLength(17)]),
      dataDiNascita: new FormControl({ value: '', disabled: false }),
      idComuneDiNascita: new FormControl({ value: '', disabled: false }),
      comuneDiNascita: new FormControl('', [Validators.required]),
      provinciaDiNascita: new FormControl({ value: '', disabled: false }),
      idProvinciaDiNascita: new FormControl({ value: '', disabled: false }),
      regioneDiNascita: new FormControl({ value: '', disabled: false }),
      idRegioneDiNascita: new FormControl({ value: '', disabled: false }),
      nazioneDiNascita: new FormControl({ value: '', disabled: false }),
      idNazioneDiNascita: new FormControl({ value: '', disabled: false }),
      //RESIDENZA:
      indirizzoPF: new FormControl('', [Validators.required]),
      idComunePF: new FormControl({ value: '', disabled: false }),
      comunePF: new FormControl('', [Validators.required]),
      provinciaPF: new FormControl({ value: '', disabled: false }),
      idProvinciaPF: new FormControl({ value: '', disabled: false }),
      capPF: new FormControl('', [Validators.required, Validators.maxLength(6)]),
      regionePF: new FormControl({ value: '', disabled: false }),
      idRegionePF: new FormControl({ value: '', disabled: false }),
      nazionePF: new FormControl({ value: '', disabled: false }),
      idNazionePF: new FormControl({ value: '', disabled: false }),
    })

    this.anagBeneService.getAnagBeneFisico(this.idSoggetto, this.numeroDomanda);
    this.subscribers.anagBeneFisicoInfo$ = this.anagBeneService.anagBeneFisicoInfo$.subscribe(data => {
      if (data) {
        this.beneficiarioFisico = data;
        console.log(this.beneficiarioFisico);
        this.myForm.setValue({
          //DATI ANAGRAFICI:
          cognome: this.beneficiarioFisico.cognome,
          nome: this.beneficiarioFisico.nome,
          tipoSoggetto: "BENEFICIARIO",
          codiceFiscale: this.beneficiarioFisico.codiceFiscale,
          dataDiNascita: new Date(Date.parse(this.beneficiarioFisico.dataDiNascita)),
          idComuneDiNascita: this.beneficiarioFisico.idComuneDiNascita.toString(),
          comuneDiNascita: this.beneficiarioFisico.comuneDiNascita,
          provinciaDiNascita: this.beneficiarioFisico.provinciaDiNascita,
          idProvinciaDiNascita: this.beneficiarioFisico.idProvinciaDiNascita.toString(),
          regioneDiNascita: this.beneficiarioFisico.regioneDiNascita,
          idRegioneDiNascita: this.beneficiarioFisico.idRegioneDiNascita.toString(),
          nazioneDiNascita: this.beneficiarioFisico.nazioneDiNascita,
          idNazioneDiNascita: this.beneficiarioFisico.idNazioneDiNascita.toString(),
          //RESIDENZA:
          indirizzoPF: this.beneficiarioFisico.indirizzoPF,
          idComunePF: this.beneficiarioFisico.idComunePF.toString(),
          comunePF: this.beneficiarioFisico.comunePF,
          provinciaPF: this.beneficiarioFisico.provinciaPF,
          idProvinciaPF: this.beneficiarioFisico.idProvinciaPF.toString(),
          capPF: this.beneficiarioFisico.capPF,
          regionePF: this.beneficiarioFisico.regionePF,
          idRegionePF: this.beneficiarioFisico.idRegionePF.toString(),
          nazionePF: this.beneficiarioFisico.nazionePF,
          idNazionePF: this.beneficiarioFisico.idNazionePF.toString(),
        });
        //SERVIZIO CHE MI PRENDE TUTTI I COMUNI DELLA PROVINCIA DI NASCITA
        this.modificaAnagraficaService.getComuni(this.myForm.controls.idProvinciaDiNascita.value).subscribe(data => {
          if (data) {
            this.comuni = data;
            //QUI SVUOTO L'ARRAY DI DESC_COMUNI PERCHE' NON CAPISCO COME MAI VIENE POPOLATO 2 VOLTE QUINDI SI CREEREBBERO COPIE
            this.descComuni = [];
            this.comuni.forEach(val => {
              if (val) {
                this.descComuni.push(val.descComune);
              }
            });
            this.bloccaSpinner();
          }
        });

        //SERVIZIO CHE MI PRENDE TUTTI I COMUNI DELLA PROVINCIA DI RESIDENZA
        this.modificaAnagraficaService.getComuni(this.myForm.controls.idProvinciaPF.value).subscribe(data => {
          if (data) {
            this.comuni = data;
            //QUI SVUOTO L'ARRAY DI DESC_COMUNI_RES PERCHE' NON CAPISCO COME MAI VIENE POPOLATO 2 VOLTE QUINDI SI CREEREBBERO COPIE
            this.descComuniRes = [];
            this.comuni.forEach(val => {
              if (val) {
                this.descComuniRes.push(val.descComune);
              }
            });
            this.bloccaSpinner();
          }
        });

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

    //OBSERVABLE PER IL PRIMO L'AUTOCOMPLETE
    this.filteredOptions = this.myForm.controls.comuneDiNascita.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value)),
    );
    //OBSERVABLE PER IL SECONDO AUTOCOMPLETE
    this.filteredOptions2 = this.myForm.controls.comunePF.valueChanges.pipe(
      startWith(''),
      map(value => this._filterRes(value)),
    );
  }


  //FUNZIONE CHE SI OCCUPA DI INTERROMPERE LO SPINNER
  bloccaSpinner() {
    this.getConcluse += 1;
    if (this.getConcluse > 3) {
      this.spinner = false;
    }
  }


  //FUNZIONE PER LA VALIDAZIONE
  public hasError = (controlName: string, errorName: string) => {
    return this.myForm.controls[controlName].hasError(errorName);
  }

  //FILTRO PER IL PRIMO AUTOCOMPLETE
  private _filter(value: string): string[] {
    if (value.length < 3) {
      return [];
    }
    const filterValue = value.toLowerCase();
    return this.descComuni.filter(option => option.toLowerCase().includes(filterValue));
  }

  //FILTRO PER IL SECONDO AUTOCOMPLETE
  private _filterRes(value: string): string[] {
    if (value.length < 3) {
      return [];
    }
    const filterValue = value.toLowerCase();
    return this.descComuniRes.filter(option => option.toLowerCase().includes(filterValue));
  }

  //METODO PER PRENDERMI I COMUNI LEGATI ALLA PROVINCIA DI NASCITA
  getComuniNascita(provinciaNascita: HTMLSelectElement) {
    this.modificaAnagraficaService.getComuni(provinciaNascita.value).subscribe(data => {
      if (data) {
        this.comuni = data;
        //QUI SVUOTO L'ARRAY DI DESC_COMUNI PERCHE' NON CAPISCO COME MAI VIENE POPOLATO 2 VOLTE QUINDI SI CREEREBBERO COPIE
        this.descComuni = [];
        this.comuni.forEach(val => {
          if (val) {
            this.descComuni.push(val.descComune);
          }
        });
      }
    });
    this.myForm.controls.comuneDiNascita.setValue("")
  }

  //METODO PER PRENDERMI I COMUNI LEGATI ALLA PROVINCIA DI RESIDENZA
  getComuniResidenza(provinciaResidenza: HTMLSelectElement) {
    this.modificaAnagraficaService.getComuni(provinciaResidenza.value).subscribe(data => {
      if (data) {
        this.comuni = data;
        //QUI SVUOTO L'ARRAY DI DESC_COMUNI_RES PERCHE' NON CAPISCO COME MAI VIENE POPOLATO 2 VOLTE QUINDI SI CREEREBBERO COPIE
        this.descComuniRes = [];
        this.comuni.forEach(val => {
          if (val) {
            this.descComuniRes.push(val.descComune);
          }
        });
      }
    });
    this.myForm.controls.comunePF.setValue("")
  }

  //METODO PER PRENDERMI LE PROVINCIE LEGATE ALLA REGIONE 
  getProvincieMeth() {
    this.modificaAnagraficaService.getProvincie().subscribe(data => {
      if (data) {
        this.provincie = data;
      }
    });
  }

  //METODO PER PRENDERMI LE REGIONI LEGATE ALLA NAZIONE
  getRegioniMeth() {
    this.modificaAnagraficaService.getRegioni().subscribe(data => {
      if (data) {
        this.regioni = data;
      }
    });
  }

  //METODO PER PRENDERMI LE NAZIONI
  getNazioniMeth() {
    this.modificaAnagraficaService.getNazioni().subscribe(data => {
      if (data) {
        this.nazioni = data;
      }
    });
  }

  //METODO CHE RITORNA TRUE SE LA REGIONE APPARTIENE ALLA NAZIONE
  checkProvincia(regioneNasc: string) {
    if (regioneNasc == this.myForm.controls.regioneDiNascita.value) {
      return true;
    }
    else {
      return false
    }
  }

  salvaModifiche() {
    this.modifiche = new ModificaPersonaFisica(this.idSoggetto, this.myForm.getRawValue());
    // //SERVIZIO DI POST:
    // this.modificaAnagraficaService.updateAnagraficaPF(this.modifiche).subscribe(data => {
    //   if (data) {
    //     return true;
    //   }
    // }, err => {
    //   this.handleExceptionService.handleBlockingError(err);
    // });
    // this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"], { queryParams: { idSoggetto: this.idSoggetto } });
  }

  annullaModifiche(){
    this.router.navigate(["/drawer/" + this.idOp + "/anagraficaBeneficiario"], { queryParams: { idSoggetto: this.idSoggetto, tipoSoggetto: "Persona Fisica" } });
  }

}
