/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UserInfoSec } from '@pbandi/common-lib';
import { UserService } from 'src/app/core/services/user.service';
import { RegistroControlliService2 } from '../../services/registro-controlli2.service';
import { ProgettoDTO } from '../../commons/models/progetto-dto';
import { Beneficiario } from '../../commons/models/beneficiario';
import { RegistroControlliService } from '../../services/registro-controlli.service';
import { Irregolarita } from '../../commons/models/irregolarita';
import { FiltroRicercaIrregolarita } from '../../commons/models/filtro-ricerca-irregolarita';
import { MatTableDataSource } from '@angular/material/table';
import { RettificaForfettaria } from '../../commons/models/rettifica-forfettaria';
import { Observable, Subscription } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { RegistroControlliTablesComponent } from '../registro-controlli-tables/registro-controlli-tables.component';

@Component({
  selector: 'app-ricerca-esiti-controlli',
  templateUrl: './ricerca-esiti-controlli.component.html',
  styleUrls: ['./ricerca-esiti-controlli.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class RicercaEsitiControlliComponent implements OnInit {

  //beneficiarioSelected: Beneficiario;
  beneficiari: Array<Beneficiario>;
  beneficiario: Beneficiario;
  beneficiarioSelected: FormControl = new FormControl();
  beneficiarioGroup: FormGroup = new FormGroup({ beneficiarioControl: new FormControl() });
  filteredOptions: Observable<Beneficiario[]>;

  user: UserInfoSec;

  progettoSelected: ProgettoDTO;
  progetti: Array<ProgettoDTO>;

  criteriRicercaOpened: boolean = true;

  dataComunicazione: FormControl = new FormControl();

  displayedColumnsRegolari: string[] = ['versione', 'versioneP', 'data', 'progetto', 'beneficiario', 'inizioFineControlli', 'tipo', 'annoContabile', 'autoritaControllante', 'detail'];
  dataSourceRegolari: MatTableDataSource<Irregolarita>;

  displayedColumnsIrregolari: string[] = ['pd', 'versione', 'versioneP', 'data', 'motivoIrregolarita', 'progetto', 'beneficiario', 'inizioFineControlli', 'tipo', 'annoContabile', 'autoritaControl', 'impSpesaIrr', 'impAgevIrreg', 'bloccata', 'riferimentoIms', 'detail'];
  dataSourceIrregolari: MatTableDataSource<Irregolarita>;

  displayedColumnsRetForf: string[] = ['data', 'perc', 'progetto', 'beneficiario', 'autoritaControl', 'rifAffidCPACIG', 'rifChecklist', 'esitoRDCA', 'rifPropostaCert'];
  dataSourceRetForf: MatTableDataSource<RettificaForfettaria>;

  idSoggetto: number;

  showTable = false;

  request: FiltroRicercaIrregolarita;

  @ViewChild(RegistroControlliTablesComponent) tablesComponent: RegistroControlliTablesComponent;

  //loaded
  loadedBeneficiari: boolean = true;
  loadedBeneficiariInit: boolean = true;
  loadedcercaProgetti: boolean;
  loadedgetEsitiRegolari: boolean;
  loadedgetIrregolarita: boolean;
  loadedgetRettifiche: boolean;

  //SUBSCRIBERS
  subscribers: any = {};
  subscriptionBen: Subscription;

  isMessageErrorVisible: any;
  messageError: any;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  constructor(
    private registroControlliService2: RegistroControlliService2,
    private userService: UserService,
    private registroControlliService: RegistroControlliService,
    private handleExceptionService: HandleExceptionService
  ) {
    this.dataSourceRegolari = new MatTableDataSource();
    this.dataSourceIrregolari = new MatTableDataSource();
    this.dataSourceRetForf = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;

        this.idSoggetto = this.user.idSoggetto;

        this.loadData();
      }
    });
  }

  loadData() {
    this.loadBeneficiari(true);
  }

  timeout: any;
  loadBeneficiari(init?: boolean) {
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
        if (this.user?.beneficiarioSelezionato?.idBeneficiario && init) {
          idBeneficiario = this.user?.beneficiarioSelezionato?.idBeneficiario;
        }
        this.subscriptionBen = this.registroControlliService2.cercabeneficiari(this.user.idUtente, this.idSoggetto, this.beneficiarioGroup.controls['beneficiarioControl']?.value || "", idBeneficiario).subscribe(data => {
          if (data) {

            this.beneficiari = data;
            this.filteredOptions = this.beneficiarioGroup.controls['beneficiarioControl'].valueChanges
              .pipe(
                startWith(''),
                map(value => typeof value === 'string' || value == null ? value : value.denominazione),
                map(name => name ? this._filter(name) : this.beneficiari.slice())
              );
            if (this.user?.beneficiarioSelezionato?.idBeneficiario) {
              console.log(this.user);
              console.log("*********");
              let ben = this.beneficiari.find(b => b.id_soggetto === this.user.beneficiarioSelezionato.idBeneficiario);
              this.beneficiarioSelected.setValue(ben);
              this.beneficiarioGroup.controls['beneficiarioControl'].setValue(ben);
              this.loadProgetti();
            }
          }
          this.loadedBeneficiari = true;
          if (init) {
            this.loadedBeneficiariInit = true;
          }
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }, init ? 0 : 1000);
    }
  }

  loadProgetti() {
    this.resetMessageError();
    this.loadedcercaProgetti = true;
    this.subscribers.cercaProgetti = this.registroControlliService2.cercaProgetti(this.user.idUtente, Number(this.idSoggetto), this.beneficiario.id_soggetto).subscribe(data => {
      this.progetti = data;
      this.loadedcercaProgetti = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento dei progetti");
      this.loadedcercaProgetti = false;
    });
  }

  loadEsitiRegolari() {
    this.loadedgetEsitiRegolari = true;
    this.subscribers.getEsitiRegolari = this.registroControlliService.getEsitiRegolari(this.request).subscribe((res: Irregolarita[]) => {
      this.dataSourceRegolari = new MatTableDataSource(res);
      this.loadedgetEsitiRegolari = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento delle regolarità");
      this.loadedgetEsitiRegolari = false;
    });
  }

  loadIrregolarita() {
    this.loadedgetIrregolarita = true;
    this.subscribers.getIrregolarita = this.registroControlliService.getIrregolarita(this.request).subscribe((res: Irregolarita[]) => {
      this.dataSourceIrregolari = new MatTableDataSource(res);
      this.loadedgetIrregolarita = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento delle irregolarità");
      this.loadedgetIrregolarita = false;
    });
  }

  loadRettifiche() {
    this.loadedgetRettifiche = true;
    this.subscribers.getRettifiche = this.registroControlliService.getRettifiche(this.request).subscribe((res: RettificaForfettaria[]) => {
      res.forEach(r => {
        if (r.idPropostaCertificaz && r.dtOraCreazione) {
          r.rifPropostaCertificazione = r.idPropostaCertificaz + " " + r.dtOraCreazione
        }
      })
      this.dataSourceRetForf = new MatTableDataSource(res);
      this.loadedgetRettifiche = false;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ottenimento delle rettifiche");
      this.loadedgetRettifiche = false;
    });
  }

  displayFn(beneficiario: Beneficiario): string {
    return (beneficiario && beneficiario.descrizione ? beneficiario.descrizione : '')
      + (beneficiario && beneficiario.descrizione && beneficiario.codiceFiscale ? ' - ' : '')
      + (beneficiario && beneficiario.codiceFiscale ? beneficiario.codiceFiscale : '');
  }

  private _filter(descrizione: string): Beneficiario[] {
    const filterValue = descrizione.toLowerCase();
    return this.beneficiari.filter(option => option.descrizione.toLowerCase().includes(filterValue) || option.codiceFiscale.toLowerCase().includes(filterValue));
  }

  check() {
    setTimeout(() => {
      if (!this.beneficiarioSelected || (this.beneficiarioGroup.controls['beneficiarioControl'] && this.beneficiarioSelected !== this.beneficiarioGroup.controls['beneficiarioControl'].value)) {
        this.beneficiarioGroup.controls['beneficiarioControl'].setValue(null);
        this.beneficiarioSelected = new FormControl();
      }
    }, 200);
  }

  click(event: any) {
    this.beneficiarioSelected = event.option.value;
    this.beneficiario = this.beneficiarioGroup.controls['beneficiarioControl'].value;
    this.changeBeneficiario();
  }

  changeBeneficiario() {
    if (!this.beneficiario) {
      this.progettoSelected = undefined;
      this.progetti = undefined;
    } else {
      this.loadProgetti();
    }
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  cerca() {
    if (!this.beneficiario && this.progettoSelected == undefined && this.dataComunicazione.value == undefined) {
      this.showMessageError("E’ necessario inserire almeno un campo di ricerca.");
    } else {
      this.showTable = true;
      this.resetMessageError();

      this.request = {
        idBeneficiario: !this.beneficiario ? undefined : this.beneficiario.id_soggetto.toString(),
        idProgetto: this.progettoSelected == undefined ? undefined : this.progettoSelected.idProgetto.toString(),
        dtComunicazioneIrregolarita: this.dataComunicazione.value == undefined ? undefined : new Date(Date.parse(this.dataComunicazione.value)).toLocaleDateString()
      };
      this.loadEsitiRegolari();
      this.loadIrregolarita();
      this.loadRettifiche();
      this.criteriRicercaOpenClose();
    }
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  resetMessages() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.tablesComponent.resetMessageError();
    this.tablesComponent.resetMessageSuccess();
  }

  isLoading() {
    if (this.loadedcercaProgetti || this.loadedgetEsitiRegolari || this.loadedgetIrregolarita
      || this.loadedgetRettifiche || !this.loadedBeneficiariInit) {
      return true;
    } else {
      return false;
    }
  }
}
