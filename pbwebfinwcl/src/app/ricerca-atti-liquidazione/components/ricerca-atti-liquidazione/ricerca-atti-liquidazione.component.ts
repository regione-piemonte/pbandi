/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AttoDiLiquidazioneVo } from 'src/app/gestione-disimpegni/commons/atto-di-liquidazione-vo';
import { CodiceDescrizioneDTO } from 'src/app/gestione-disimpegni/commons/codice-descrizione-vo';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { NavigationRicercaAttiService } from '../../services/navigation-ricerca-atti.service';
import { RicercaAttiService } from '../../services/ricerca-atti.service';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-ricerca-atti-liquidazione',
  templateUrl: './ricerca-atti-liquidazione.component.html',
  styleUrls: ['./ricerca-atti-liquidazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaAttiLiquidazioneComponent implements AfterViewInit {

  beneficiari: Array<CodiceDescrizioneDTO>;
  beneficiarioSelected: FormControl = new FormControl();
  beneficiarioGroup: FormGroup = new FormGroup({ beneficiarioControl: new FormControl() });
  filteredOptionsBen: Observable<CodiceDescrizioneDTO[]>;

  progetti: Array<CodiceDescrizioneDTO>;
  progettoSelected: FormControl = new FormControl();
  progettoGroup: FormGroup = new FormGroup({ progettoControl: new FormControl() });
  filteredOptionsProg: Observable<CodiceDescrizioneDTO[]>;

  annoEsercizio: string;
  annoImpegno: string;
  numeroImpegno: string;
  annoAtto: string;
  numeroAtto: string;
  showResults = false;
  criteriRicercaDatiAnagraficiOpened: boolean = true;
  criteriRicercaImpegnoOpened: boolean = true;
  showImpegniButtons: boolean = false;
  displayedColumns: string[] = ['estremiAtto', 'atto', 'progetto', 'beneficiario', 'azioni'];
  dataSource: MatTableDataSource<AttoDiLiquidazioneVo>;

  selection = new SelectionModel<AttoDiLiquidazioneVo>(true, []);
  user: UserInfoSec;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  messageError: string;
  isMessageErrorVisible: boolean;

  // loaaded
  loadedBeneficiari: boolean = true;
  loadedBeneficiariInit: boolean = true;
  loadedProgetti: boolean = true;
  loadedCercaDatiAtti: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};
  subscriptionBen: Subscription;

  constructor(
    private router: Router,
    private navigationService: NavigationRicercaAttiService,
    private userService: UserService,
    private ricercaAttiService: RicercaAttiService,
    private handleExceptionService: HandleExceptionService
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.loadBeneficiari(true);
        this.loadData();
      }
    });
  }

  timeout: any;
  loadBeneficiari(init?: boolean) {
    this.resetMessageError();
    this.loadedBeneficiari = false;
    if (init) {
      this.loadedBeneficiariInit = false;
    }
    if (this.beneficiarioGroup.controls['beneficiarioControl']?.value?.length >= 3 || init) {
      if (this.timeout) {
        clearTimeout(this.timeout);
        this.timeout = null;
      }
      this.timeout = setTimeout(() => {
        if (this.subscriptionBen) {
          this.subscriptionBen.unsubscribe();
        }
        let idBeneficiario = null;
        if (this.navigationService.beneficiarioSelezionata && init) {
          idBeneficiario = +this.navigationService.beneficiarioSelezionata.codice;
        }
        this.subscriptionBen = this.ricercaAttiService.caricaBeneficiariByDenomOrIdBen(this.beneficiarioGroup.controls['beneficiarioControl']?.value || "",
          idBeneficiario).subscribe(data => {
            if (data) {

              this.beneficiari = data;
              this.filteredOptionsBen = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
                .pipe(
                  startWith(''),
                  map(value => typeof value === 'string' || value == null ? value : value.denominazione),
                  map(name => name ? this._filterBen(name) : this.beneficiari.slice())
                );
              if (idBeneficiario) {
                let ben = this.beneficiari.find(b => b.codice == idBeneficiario.toString());
                this.beneficiarioSelected.setValue(ben);
                this.beneficiarioGroup.controls['beneficiarioControl'].setValue(ben);
                this.loadProgetti(true);
              }
            }
            this.loadedBeneficiari = true;
            if (init) {
              this.loadedBeneficiariInit = true;
            }
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fasi di caricamento dei beneficiari.");
            this.loadedBeneficiari = true;
            this.loadedBeneficiariInit = true;
          });
      }, init ? 0 : 1000);
    }
  }

  loadProgetti(isInit?: boolean) {
    this.resetMessageError();
    this.progetti = null;
    this.progettoSelected = new FormControl();
    this.progettoGroup.controls['progettoControl'].setValue(null);
    this.loadedProgetti = false;
    this.ricercaAttiService.caricaProgettiByIdBen(+this.beneficiarioSelected.value.codice).subscribe(data => {
      if (data) {
        this.progetti = data;

        this.filteredOptionsProg = this.progettoGroup.controls['progettoControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descrizione),
            map(name => name ? this._filterProg(name) : (this.progetti ? this.progetti.slice() : null))
          );
        if (this.navigationService.progettoSelezionata && isInit) {
          let prog = this.progetti.find(p => p.codice == this.navigationService.progettoSelezionata.codice);
          this.progettoSelected.setValue(prog);
          this.progettoGroup.controls['progettoControl'].setValue(prog);
        }
        if (isInit) {
          this.cercaDatiAnagrafici();
        }
      }
      this.loadedProgetti = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fasi di caricamento dei progetti.");
      this.loadedProgetti = true;
    });
  }

  check(type: string) {
    setTimeout(() => {
      if (type === 'B') {
        if (!this.beneficiarioSelected || (this.beneficiarioGroup.controls['beneficiarioControl'] && this.beneficiarioSelected.value !== this.beneficiarioGroup.controls['beneficiarioControl'].value)) {
          this.beneficiarioGroup.controls['beneficiarioControl'].setValue(null);
          this.beneficiarioSelected = new FormControl();
          this.progetti = null;
          this.progettoGroup.controls['progettoControl'].setValue(null);
          this.progettoSelected = new FormControl();
        }
      } else if (type === 'P') {
        if (!this.progettoSelected || (this.progettoGroup.controls['progettoControl'] && this.progettoSelected.value !== this.progettoGroup.controls['progettoControl'].value)) {
          this.progettoGroup.controls['progettoControl'].setValue(null);
          this.progettoSelected = new FormControl();
        }
      }
    }, 200);
  }

  click(event: any, type: string) {
    if (type === 'B') {
      this.beneficiarioSelected.setValue(event.option.value);
      this.loadProgetti();
    } else if (type === 'P') {
      this.progettoSelected.setValue(event.option.value);
    }
  }

  private _filterBen(descrizione: string): CodiceDescrizioneDTO[] {
    const filterValue = descrizione.toLowerCase();
    return this.beneficiari.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  private _filterProg(descrizione: string): CodiceDescrizioneDTO[] {
    const filterValue = descrizione.toLowerCase();
    return this.progetti.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  displayFn(element: CodiceDescrizioneDTO): string {
    return element && element.descrizione ? element.descrizione : '';
  }

  // ------------ Inizio gestione dati navigazione ------------
  saveDataForNavigation() {
    this.navigationService.beneficiarioSelezionata = this.beneficiarioSelected?.value;
    this.navigationService.progettoSelezionata = this.progettoSelected?.value;
    this.navigationService.annoEsercizioSelected = this.annoEsercizio;
    this.navigationService.annoImpegnoSelected = this.annoImpegno;
    this.navigationService.nImpegnoSelected = this.numeroImpegno;
    this.navigationService.annoAttoSelected = this.annoAtto;
    this.navigationService.numeroAttoSelected = this.numeroAtto;
    this.salvaPaginator();
  }

  salvaPaginator() {
    if (this.dataSource && this.dataSource.paginator) {
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }

  ripristinaPaginator() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
    }
  }

  loadData() {
    if (this.navigationService.annoEsercizioSelected) {
      this.annoEsercizio = this.navigationService.annoEsercizioSelected;
    }

    if (this.navigationService.annoImpegnoSelected) {
      this.annoImpegno = this.navigationService.annoImpegnoSelected;
    }

    if (this.navigationService.nImpegnoSelected) {
      this.numeroImpegno = this.navigationService.nImpegnoSelected;
    }

    if (this.navigationService.annoAttoSelected) {
      this.annoAtto = this.navigationService.annoAttoSelected;
    }

    if (this.navigationService.numeroAttoSelected) {
      this.numeroAtto = this.navigationService.numeroAttoSelected;
    }

    if (!this.navigationService.beneficiarioSelezionata) {
      if (this.navigationService.annoEsercizioSelected && this.navigationService.annoImpegnoSelected && this.navigationService.nImpegnoSelected) {
        this.cercaImpegni();
      } else {
        if (this.navigationService.annoAttoSelected && this.navigationService.numeroAttoSelected) {
          this.cercaAtto();
        }
      }
    }


  }
  // ------------ Fine gestione dati navigazione ------------


  criteriRicercaDatiAnagraficiOpenClose() {
    this.criteriRicercaDatiAnagraficiOpened = !this.criteriRicercaDatiAnagraficiOpened;
  }

  criteriRicercaImpegnoOpenClose() {
    this.criteriRicercaImpegnoOpened = !this.criteriRicercaImpegnoOpened;
  }

  cercaDatiAnagrafici() {
    this.resetMessageError();
    this.loadedCercaDatiAtti = true;
    this.ricercaAttiService.cercaDatiAtti(this.user, this.beneficiarioSelected?.value ? this.beneficiarioSelected.value.codice.toString() : null,
      this.progettoSelected?.value ? this.progettoSelected.value.codice.toString() : null, undefined, undefined, undefined, undefined, undefined).subscribe(data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.loadedCercaDatiAtti = false;
      }, err => {
        this.loadedCercaDatiAtti = false;
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di cerca degli atti di liquidazione");
      });

    this.showResults = true;
    if (this.navigationService.beneficiarioSelezionata) {
      this.ripristinaPaginator();
    } else {
      if (this.navigationService.progettoSelezionata) {
        this.ripristinaPaginator();
      } else {

      }
    }
  }

  cercaImpegni() {
    this.resetMessageError();
    this.loadedCercaDatiAtti = true;
    this.ricercaAttiService.cercaDatiAtti(this.user, undefined, undefined, this.annoEsercizio, this.annoImpegno, this.numeroImpegno, undefined, undefined).subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.loadedCercaDatiAtti = false;
    }, err => {
      this.loadedCercaDatiAtti = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di cerca degli atti di liquidazione");
    });

    this.showResults = true;
    if (this.navigationService.annoEsercizioSelected && this.navigationService.annoImpegnoSelected && this.navigationService.nImpegnoSelected) {
      this.ripristinaPaginator();
    }
  }

  cercaAtto() {
    this.resetMessageError();
    this.loadedCercaDatiAtti = true;
    this.ricercaAttiService.cercaDatiAtti(this.user, undefined, undefined, undefined, undefined, undefined, this.annoAtto, this.numeroAtto).subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.loadedCercaDatiAtti = false;
    }, err => {
      this.loadedCercaDatiAtti = false;
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di cerca degli atti di liquidazione");
    });

    this.showResults = true;
    if (this.navigationService.annoAttoSelected && this.navigationService.numeroAttoSelected) {
      this.ripristinaPaginator();
    }
  }

  dettaglioAtto(row: AttoDiLiquidazioneVo) {
    this.saveDataForNavigation();
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_RICERCA_ATTI_LIQUIDAZIONE + "/dettaglioAtto?idAttoLiquidazione=" + row.idAttoLiquidazione + "&beneficiario=" + row.denominazioneBeneficiarioBil + "&progetto=" + row.codiceVisualizzatoProgetto) + "&consultazioneAtto=false";
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
    if (!this.loadedBeneficiariInit || !this.loadedProgetti || this.loadedCercaDatiAtti) {
      return true;
    } else {
      return false;
    }
  }
}