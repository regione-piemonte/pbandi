/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { BandoLineaDaAssociareAIstruttoreVO } from '../../commons/dto/bando-linea-da-associare-istruttore-dto';
import { BeneficiarioProgettoDTO } from '../../commons/dto/beneficiario-progetto-dto';
import { ProgettoDTO } from '../../commons/dto/progetto-dto';
import { ProgettoShowDTO } from '../../commons/dto/progetto-show-dto';
import { AssociazioneIstruttoreProgettiService } from '../../services/associazione-istruttore-progetti.service';
import { Constants } from 'src/app/core/commons/util/constants';

@Component({
  selector: 'app-associa-nuovo-progetto',
  templateUrl: './associa-nuovo-progetto.component.html',
  styleUrls: ['./associa-nuovo-progetto.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AssociaNuovoProgettoComponent implements OnInit {

  cognomeIstruttore: string;
  nomeIstruttore: string;
  codiceFiscale: string;
  user: UserInfoSec;

  idOperazione: number;

  params: URLSearchParams;

  form: FormGroup;

  bandilinea: Array<BandoLineaDaAssociareAIstruttoreVO>;
  filteredOptions$: Observable<Array<BandoLineaDaAssociareAIstruttoreVO>>;

  codiciProgetto: Array<ProgettoDTO>;
  codiceProgettoSelected: ProgettoDTO;

  beneficiari: Array<BeneficiarioProgettoDTO>;
  beneficiarioSelected: BeneficiarioProgettoDTO;

  LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY = 'ricercaIstruttore_anagraficaIstruttoreSelected';
  anagraficaIstruttoreSelected: string;

  istruttoriRadio: string = '1';

  messageError: string;
  isMessageErrorVisible: boolean;

  showImpegniButtons: boolean = false;

  isIstruttoreAffidamenti: boolean;

  // tabelle
  displayedColumns: string[] = ['select', 'codiceProgetto', 'bando', 'beneficiario', 'numIstrAssoc'];
  dataSource: MatTableDataSource<ProgettoShowDTO>;

  showResults = false;

  selection = new SelectionModel<ProgettoShowDTO>(true, []);

  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  //LOADED
  loadedCercaBandi: boolean;
  loadedCercaProgettiByBando: boolean;
  loadedCercaBeneficiari: boolean;
  loadedCercaProgettiByBeneficiario: boolean;
  loadedFindProgettiDaAssociare: boolean;
  loadedAssociaProgettiAIstruttore: boolean;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private associazioneIstruttoreProgettiService: AssociazioneIstruttoreProgettiService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private fBuilder: FormBuilder
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.form = this.fBuilder.group({
      bandoLinea: ['', [Validators.required]]
    });
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    this.cognomeIstruttore = this.params.get('cognome');
    this.nomeIstruttore = this.params.get('nome');
    this.codiceFiscale = this.params.get('codFisc');
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.getTipoIstruttoreFromLocalStorage();

        this.loadedCercaBandi = true;
        this.subscribers.cercaBandi = this.associazioneIstruttoreProgettiService.getBandoLineaDaAssociare(this.user, this.user.idSoggetto.toString(),
          this.params.get('idSoggettoIstruttore'), this.isIstruttoreAffidamenti ? Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI : this.user.codiceRuolo).subscribe(data => {
            this.loadedCercaBandi = false;
            this.bandilinea = data;
            this.filteredOptions$ = this.form.get("bandoLinea").valueChanges.pipe(
              startWith(""),
              map(value => {
                const desc = typeof value === 'string' ? value : value?.descrizione;
                return desc ? this._filter(desc as string) : this.bandilinea.slice();
              })
            );
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di cerca dei bandi.");
            this.loadedCercaBandi = false;
          });
      }
    });

  }

  getTipoIstruttoreFromLocalStorage() {

    this.anagraficaIstruttoreSelected = localStorage.getItem(this.LS_ANAGRAFICA_ITRUTTORE_SELECTED_KEY);
    this.isIstruttoreAffidamenti = this.anagraficaIstruttoreSelected === Constants.CODICE_RUOLO_ISTR_AFFIDAMENTI;

  }

  onBlurAutoComplete() {
    setTimeout(() => {
      this.sharedService.onBlurAutoComplete(this.form, ["bandoLinea"]);
    }, 300);
  }

  displayFn(element: BandoLineaDaAssociareAIstruttoreVO): string {
    return element && element.nomeBandolinea ? element.nomeBandolinea : '';
  }

  _filter(value: string): Array<BandoLineaDaAssociareAIstruttoreVO> {
    const filterValue = value.toLowerCase();
    return this.bandilinea.filter(option => option.nomeBandolinea.toLowerCase().includes(filterValue));
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }


  // ------------ Inizio gestione checkbox tabelle ------------
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    var numRows;
    if (this.dataSource == undefined) {
      numRows = 0;
    } else {
      numRows = this.dataSource.data.length;
    }

    return numSelected === numRows;
  }

  checkboxLabel(row?: ProgettoShowDTO): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idProgetto + 1}`;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));

    if (this.selection.selected.length == 0) {
      this.showImpegniButtons = false;
    } else {
      this.showImpegniButtons = true;
    }
  }

  changeSingleRow(row: any) {
    if (this.selection.selected.length == 1) {
      this.selection.clear();
    }

    this.selection.toggle(row);

    if (this.selection.selected.length == 0) {
      this.showImpegniButtons = false;
    } else {
      this.showImpegniButtons = true;
    }
  }
  // ------------ Fine gestione checkbox tabelle ------------

  tornaAllaGestioneAssociazioni() {
    this.router.navigateByUrl(`drawer/` + this.idOperazione + `/gestioneAssociazione?idSoggettoIstruttore=` + this.params.get('idSoggettoIstruttore') + `&cognome=` + this.params.get('cognome') + `&nome=` + this.params.get('nome') + `&codFisc=` + this.params.get('codFisc') + `&idBando=` + this.params.get('idBando'));
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  changeBando() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!this.form.get("bandoLinea")?.value) {
      this.codiceProgettoSelected = undefined;
      this.codiciProgetto = undefined;

      this.beneficiarioSelected = undefined;
      this.beneficiari = undefined;
    } else {
      this.cercaProgettiByBandoLinea();
      this.cercaBeneficiari();

    }
  }

  cercaProgettiByBandoLinea() {
    this.loadedCercaProgettiByBando = true;
    this.subscribers.cercaProgettiByBando = this.associazioneIstruttoreProgettiService.cercaProgettiByBandoLinea(this.user, this.form.get("bandoLinea").value.progBandoLinaIntervento.toString()).subscribe(data => {
      this.loadedCercaProgettiByBando = false;
      this.codiciProgetto = data;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di cerca dei progetti.");
      this.loadedCercaProgettiByBando = false;
    });
  }

  cercaBeneficiari() {
    this.loadedCercaBeneficiari = true;
    this.subscribers.cercaBeneficiari = this.associazioneIstruttoreProgettiService.cercaBeneficiari(this.user, this.form.get("bandoLinea").value.progBandoLinaIntervento.toString(),
      this.codiceProgettoSelected == null ? null : this.codiceProgettoSelected.idProgetto.toString()).subscribe(data => {
        this.loadedCercaBeneficiari = false;
        this.beneficiari = data;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di cerca dei beneficiari.");
        this.loadedCercaBeneficiari = false;
      });
  }

  changeProgetto() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.codiceProgettoSelected == undefined) {
      if (this.beneficiarioSelected == undefined) {
        this.cercaBeneficiari();
      }
    } else {
      if (this.beneficiarioSelected == undefined) {
        this.cercaBeneficiari();
      } else {
        this.cercaBeneficiari();
        this.beneficiarioSelected = undefined;
      }
    }
  }

  changeBeneficiario() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (this.beneficiarioSelected == undefined) {
      if (this.codiceProgettoSelected == undefined) {
        this.cercaProgettiByBandoLinea();
      }
    } else {
      if (this.codiceProgettoSelected == undefined) {
        this.loadedCercaProgettiByBeneficiario = true;
        this.subscribers.cercaProgettiByBeneficiario = this.associazioneIstruttoreProgettiService.cercaProgettiByBeneficiario(this.user,
          this.form.get("bandoLinea").value.progBandoLinaIntervento.toString(), this.beneficiarioSelected.idSoggettoBeneficiario.toString()).subscribe(data => {
            this.codiciProgetto = data;
            this.loadedCercaProgettiByBeneficiario = false;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di cerca dei progetti.");
            this.loadedCercaProgettiByBeneficiario = false;
          });
      }
    }
  }

  cerca() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!this.form.get("bandoLinea")?.value) {
      this.showMessageError("Si prega di selezionare i campi obbligatori.");
    } else {
      this.loadedFindProgettiDaAssociare = true;
      this.subscribers.findProgettiDaAssociare = this.associazioneIstruttoreProgettiService.findProgettiDaAssociare(this.user,
        this.form.get("bandoLinea").value.progBandoLinaIntervento.toString(), this.codiceProgettoSelected == undefined ? null : this.codiceProgettoSelected.idProgetto.toString(),
        this.beneficiarioSelected == undefined ? null : this.beneficiarioSelected.idSoggettoBeneficiario.toString(), this.user.idSoggetto.toString(),
        this.istruttoriRadio == "1" ? "true" : "false", this.isIstruttoreAffidamenti).subscribe(data => {
          var comodo = new Array<ProgettoShowDTO>();
          data.forEach(element => {
            comodo.push(new ProgettoShowDTO(element.idProgetto, element.idSoggettoBeneficiario, element.idBando, element.numeroIstruttoriAssociati, element.titoloBando, element.codiceVisualizzato, element.beneficiario, element.istruttoriSempliciAssociati, element.progrSoggettoProgetto, element.cup, false));
          });
          this.dataSource = new MatTableDataSource(comodo);
          this.dataSource.paginator = this.paginator;
          this.showResults = true;
          this.loadedFindProgettiDaAssociare = false;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di cerca dei progetti da associare.");
          this.loadedFindProgettiDaAssociare = false;
        });
    }
  }

  associaAIstruttore() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssociaProgettiAIstruttore = true;
    this.associazioneIstruttoreProgettiService.associaProgettiAIstruttore(this.user, this.selection.selected[0].idProgetto.toString(), this.params.get('idSoggettoIstruttore'),
      this.user.idSoggetto.toString(), this.user.codiceRuolo, this.isIstruttoreAffidamenti).subscribe(data => {
        if (data) {
          if (data.esito) {
            this.showResults = false;
            this.showMessageSuccess("Associazione effettuata con successo.");
          } else {
            this.showMessageError("Errore in fase di associazione del progetto all'istruttore.");
          }
        }
        this.loadedAssociaProgettiAIstruttore = false;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di associazione del progetto all'istruttore.");
        this.loadedAssociaProgettiAIstruttore = false;
      });
  }

  isLoading() {
    if (this.loadedCercaBandi || this.loadedCercaProgettiByBando || this.loadedCercaBeneficiari || this.loadedCercaProgettiByBeneficiario || this.loadedFindProgettiDaAssociare || this.loadedAssociaProgettiAIstruttore) {
      return true;
    }
    return false;
  }
}
