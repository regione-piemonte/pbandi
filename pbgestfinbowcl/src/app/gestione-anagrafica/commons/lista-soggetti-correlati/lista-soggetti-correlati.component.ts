/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { SoggettiIndipendentiDomandaService } from '../../services/soggetti-indipendenti-domanda.service';
import { SoggettoCorrelato } from '../dto/soggettoCorrelato';
import { Constants } from 'src/app/core/commons/util/constants';
import { Subscription } from 'rxjs';
import { SoggettoCorrelatoVO } from '../dto/soggetto-correlatoVO';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';

@Component({
  selector: 'app-lista-soggetti-correlati',
  templateUrl: './lista-soggetti-correlati.component.html',
  styleUrls: ['./lista-soggetti-correlati.component.scss']
})
export class ListaSoggettiCorrelatiComponent implements OnInit, OnDestroy {

  displayedColumns: string[] = [
    'tipologia',
    'ndg',
    'cognome',
    'codiceFiscale',
    'ruolo',
    'quotaPartecipazione',
    'action'
  ];

  dataSource: MatTableDataSource<SoggettoCorrelato> = new MatTableDataSource<SoggettoCorrelato>([]);

  numeroDomanda!: string;
  idSoggetto!: string;

  spinner: boolean = false;

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;

  sub!: Subscription;
  idDomanda: string;
  idProgetto: string;
  elenco: SoggettoCorrelato[] = [];
  isTable: boolean;

  constructor(private route: ActivatedRoute, private router: Router,
    private soggettiIndipendentiDomandaService: SoggettiIndipendentiDomandaService) {
  }
  @ViewChild("soggettiCorrelatiIndip") paginator: MatPaginator
  @ViewChild("soggettiCorrelatiIndipSort") sort: MatSort;
  ngOnInit(): void {
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get("idDomanda");
    this.idDomanda = this.route.snapshot.queryParamMap.get("numeroDomanda");
    this.idProgetto = this.route.snapshot.queryParamMap.get("idProgetto");
    console.log(this.idDomanda);
    console.log(this.numeroDomanda);


    //this.idDomanda= 

    this.spinner = true;
    this.sub = this.soggettiIndipendentiDomandaService.getElencoSoggCorrIndipDaDomanda(this.numeroDomanda, this.idSoggetto).subscribe(data => {
      if (data) {
        this.elenco = data;
        this.dataSource = new MatTableDataSource<SoggettoCorrelato>(this.elenco);
        this.paginator.length = this.elenco.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        // this.dataSource.sortingDataAccessor = (item, property) => {
        //   switch (property) {
        //     case 'denominazioneCognomeNome':
        //       return item.cognome;
        //     default: return item[property];
        //   }
        // };
        if(this.elenco.length>0) {
          this.isTable= true; 
        }
        
        this.spinner = false;
      } else {
        this.spinner = false;
      }
    }, err => {
      this.spinner = false;
    })

  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }


  dettagliSoggetto(soggetto: SoggettoCorrelatoVO): void {

    const tiposoggetto: string = soggetto.tipologia.split(' ').join('').toLocaleLowerCase();

    if (tiposoggetto === 'personagiuridica') {
      this.router.navigate([`/drawer/${this.idOp}/anagraficaBeneficiario/personaGiuridicaIndipendenteDomanda`], {
        queryParams:
        {
          idSoggetto: this.idSoggetto,
          numeroDomanda: this.numeroDomanda,
          nag: soggetto.nag,
          tipologiaSoggetto: soggetto.tipologia,
          idSoggCorr: soggetto.idSoggettoCorellato,
          idProgetto: this.idProgetto
        }
      });
    } else {
      this.router.navigate([`/drawer/${this.idOp}/anagraficaBeneficiario/personaFisicaIndipendenteDomanda`], {
        queryParams:
        {
          idSoggetto: this.idSoggetto,
          numeroDomanda: this.numeroDomanda,
          nag: soggetto.nag,
          tipologiaSoggetto: soggetto.tipologia,
          idSoggCorr: soggetto.idSoggettoCorellato,
          idProgetto: this.idProgetto
        }
      });
    }
  }

  // sortData(sort: Sort) {

  //   const data = this.dataSource.data.slice();
  //   if (!sort.active || sort.direction === '') {
  //     this.dataSource.data = data;
  //     return;
  //   }
  //   this.dataSource.data = data.sort((a, b) => {
  //     const isAsc = sort.direction === 'asc';
  //     switch (sort.active) {
  //       case 'tipologia':
  //         return compare(a.tipologia, b.tipologia, isAsc);
  //       case 'nag':
  //         return compare(a.nag, b.nag, isAsc);
  //       case 'cognome':
  //         return compare(a.cognome , b.cognome , isAsc);
  //       case 'codiceFiscale':
  //         return compare(a.codiceFiscale, b.codiceFiscale, isAsc);
  //       case 'ruolo':
  //         return compare(a.ruolo, b.ruolo, isAsc);
  //       case 'quotaPartecipazione':
  //         return compare(a.quotaPartecipazione, b.quotaPartecipazione, isAsc);
  //       default:
  //         return 0;
  //     }
  //   });
  // }
}
// function compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
//   return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
// }
