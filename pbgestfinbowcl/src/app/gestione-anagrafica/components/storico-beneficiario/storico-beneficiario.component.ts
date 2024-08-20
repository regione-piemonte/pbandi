/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';
import { StoricoBenficiarioService } from '../../services/storico-benficiario.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { StoricoBeneficiarioFisico } from '../../commons/dto/storico-beneficiario-fisico';
import { StoricoBeneficiarioSec } from '../../commons/dto/storico-benficiario';
import { VersioniSec } from '../../commons/dto/versioni';

export interface Versioni {
  idVersione: any;
  dataRiferimento: any;
  denom: any,
}

@Component({
  selector: 'app-storico-beneficiario',
  templateUrl: './storico-beneficiario.component.html',
  styleUrls: ['./storico-beneficiario.component.scss']
})
export class StoricoBeneficiarioComponent implements OnInit {

  public myForm: FormGroup;
  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;
  setBackText: string = "Torna all'anagrafica";
  versioni: Array<VersioniSec> = [];
  idSoggetto: any;
  idEnteGiuridico: any;
  idDomandaSnap: any;
  isEnteGiuridico: boolean;
  verificaTipoSoggetto: any;
  storicoGiuridico: StoricoBeneficiarioSec
  storicoFisico: StoricoBeneficiarioFisico
  spinner: boolean;
  getConcluse: number = 0;
  ordinamento: boolean = true;
  ordinamentoString: string = "DESC"

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  // tabella
  displayedColumns: string[] = ['idVersione', 'dataRiferimento', 'denom', 'azioni'];
  dataSource: VersioniSec[] = [];
  emptyTable: boolean;
  veroIdDomanda: any;
  idProgetto: string;

  constructor(
    private fb: FormBuilder,
    private storicoService: StoricoBenficiarioService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }
  // @ViewChild("versioni") paginator: MatPaginator;
  // @ViewChild("versioniSort") sort: MatSort;
  ngOnInit() {
    this.spinner = true;
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('isEnteGiuridico');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    this.veroIdDomanda = this.route.snapshot.queryParamMap.get('idDomanda');
    this.idProgetto = this.route.snapshot.queryParamMap.get('idProgetto');


    //PERSONA GIURIDICA
    if (this.verificaTipoSoggetto == "Persona Giuridica") {
      this.isEnteGiuridico = true;
      this.myForm = this.fb.group({
        denominazione: new FormControl({ value: '', disabled: true }),
        cF: new FormControl({ value: '', disabled: true }),
        idSogg: new FormControl({ value: '', disabled: true }),
        pIva: new FormControl({ value: '', disabled: true }),
        tipoSogg: new FormControl({ value: '', disabled: true }),
      });

      this.storicoService.getStorico(this.idSoggetto);
      this.subscribers.storicoBeneficiarioInfo$ = this.storicoService.storicoBeneficiarioInfo$.subscribe(
        data => {
          if (data) {
            this.storicoGiuridico = data;
            this.myForm.setValue({
              denominazione: [this.storicoGiuridico[0].denominazione],
              cF: [this.storicoGiuridico[0].cF],
              idSogg: (this.storicoGiuridico[0].ndg) ? this.storicoGiuridico[0].ndg : null,
              pIva: [this.storicoGiuridico[0].pIva],
              tipoSogg: [this.storicoGiuridico[0].tipoSogg],
            })
          }
        });
        this.bloccaSpinner();
    } else {
      this.isEnteGiuridico = false;
      //PERSONA FISICA
      this.myForm = this.fb.group({
        cognome: new FormControl({ value: '', disabled: true }),
        nome: new FormControl({ value: '', disabled: true }),
        idSoggetto: new FormControl({ value: '', disabled: true }),
        tipoSoggetto: new FormControl({ value: '', disabled: true }),
        codiceFiscale: new FormControl({ value: '', disabled: true }),
      });

      this.storicoService.getStoricoFisico(this.idSoggetto);
      this.subscribers.storicoBeneficiarioFisicoInfo$ = this.storicoService.storicoBeneficiarioFisicoInfo$.subscribe(
        data => {
          if (data) {
            this.storicoFisico = data;
            this.myForm.setValue({
              cognome: [this.storicoFisico[0].cognome],
              nome: [this.storicoFisico[0].nome],
              idSoggetto: (this.storicoFisico[0].ndg) ? this.storicoFisico[0].ndg : this.idSoggetto,
              tipoSoggetto: this.verificaTipoSoggetto,
              codiceFiscale: [this.storicoFisico[0].codiceFiscale],
            })
            this.bloccaSpinner();
          }
        });
    }

    this.storicoService.getVersioni(this.idSoggetto).subscribe(data => {
      if (data.length > 0) {
        //this.emptyTable = true;
        //this.versioni = this.dataSource;
        this.dataSource = data;
        this.bloccaSpinner();
      } else {
        this.emptyTable = true;
        this.bloccaSpinner();
      }
    });
  }

  // sortColonna() {
  //   this.ordinamento = !this.ordinamento;
  //   if (this.ordinamento) {
  //     this.ordinamentoString = "DESC";
  //   } else {
  //     this.ordinamentoString = "ASC";
  //   }
  //   this.spinner = true;
  //   this.storicoService.getVersioni(this.idSoggetto).subscribe(data => {
  //     if (data) {
  //       let dataSourceTemporaneo: Versioni[] = [];
  //       this.versioni = data;
  //       this.versioni.forEach(val => {
  //         let versione: Versioni;
  //         let idProgetto = val.idVersioneProgetto.toString();
  //         let idDomanda = val.idVersioneDomanda.toString();
  //         this.idDomandaSnap = idDomanda;
  //         if (idProgetto != "") {
  //           versione =
  //           {
  //             idVersione: val.idVersioneProgetto,
  //             dataRiferimento: val.dataRiferimentoDomanda,
  //             denom: val.denom
  //           }
  //           dataSourceTemporaneo.push(versione);
  //         } else if (idDomanda != "") {
  //           versione =
  //           {
  //             idVersione: val.idVersioneDomanda,
  //             dataRiferimento: val.dataRiferimentoDomanda,
  //             denom:val.denom
  //           }
  //           dataSourceTemporaneo.push(versione);
  //         }
  //         this.dataSource = dataSourceTemporaneo;
  //       });
  //       this.bloccaSpinner();
  //     }
  //   });
  // }

  //FUNZIONE CHE SI OCCUPA DI INTERROMPERE LO SPINNER



  bloccaSpinner() {
    this.getConcluse += 1;
    if (this.getConcluse > 1) {
      this.spinner = false;
    }
  }

  visualizzaStoricoDomanda(row) {
    this.router.navigate(["/drawer/" + this.idOp + "/visualizzaStorico"], {
      queryParams:
      {
        idVersione: row.idVersione,
        idSoggetto: this.idSoggetto,
        idDomanda: row.idDomanda, 
        idProgetto: row.idProgetto
      }
    });
  }

  // visualizzaStoricoProgetto(row) {
  //   this.router.navigate(["/drawer/" + this.idOp + "/visualizzaStorico"], {
  //     queryParams:
  //     {
  //       idVersione: row.idVersione,
  //       idSoggetto: this.idSoggetto,
  //       idDomanda: row.idDomanda, 
  //       idProgetto: row.idProgetto
  //     }
  //   });
  // }

  visualizzaStoricoDomandaPF(row) {
    this.router.navigate(["/drawer/" + this.idOp + "/visualizzaStoricoPF"], {
      queryParams:
      {
        idVersione: row.idVersione,
        idSoggetto: this.idSoggetto,
        idDomanda: row.idDomanda, 
        idProgetto: row.idProgetto
      }
    });
  }
  goBack() {
    if (this.isEnteGiuridico == true) {

      this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA + "/anagraficaBeneficiario"], { queryParams: {} });
    } else {

    }
  }
}
