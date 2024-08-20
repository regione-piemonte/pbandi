/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { EnteCompetenzaDTO } from '../../commons/ente-competenza-vo';
import { GenericSelectVo } from '../../commons/generic-select-vo';
import { ImpegnoDTO } from '../../commons/impegno-vo';
import { GestioneImpegniService } from '../../services/gestione-impegni.service';
import { NavigationGestioneImpegniService } from '../../services/navigation-gestione-impegni.service';
import { ImportaImpegnoDialogComponent } from '../importa-impegno-dialog/importa-impegno-dialog.component';

@Component({
  selector: 'app-gestione-disimpegni',
  templateUrl: './gestione-disimpegni.component.html',
  styleUrls: ['./gestione-disimpegni.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneDisimpegniComponent implements AfterViewInit {

  // Dichiarazioni variabili
  annoEsercizioSelected: string;
  annoEsercizioSelectedCopy: string;
  anniEsercizio: Array<string>;
  direzioneProvvedimentoSelected: EnteCompetenzaDTO;
  direzioneProvvedimentoSelectedCopy: EnteCompetenzaDTO;
  direzioniProvvedimento: Array<EnteCompetenzaDTO>;
  checkedImpegniReimputati: boolean = false;
  checkedImpegniReimputatiCopy: boolean = false;
  annoImpegno: string;
  annoImpegnoCopy: string
  nImpegno: string;
  nImpegnoCopy: string;
  annoProvvedimento: string;
  annoProvvedimentoCopy: string;
  nProvvedimento: string;
  nProvvedimentoCopy: string;
  nCapitolo: string;
  nCapitoloCopy: string;
  ragioneSocialeBeneficiarioImpegno: string;
  ragioneSocialeBeneficiarioImpegnoCopy: string;
  user: UserInfoSec;

  // Dichiarazioni variabili generali
  criteriRicercaOpened: boolean = true;
  showImpegniButtons: boolean = false;
  displayedColumns: string[] = ['select', 'impegno', 'impegnoReimp', 'annoEsercizio', 'capitolo', 'codFisc', 'ragSocCUP', 'provvedimento'];
  dataSource: MatTableDataSource<ImpegnoDTO>;
  showResults = false;
  impegniTableEmpty: boolean;
  subscribers: any = {};
  selection = new SelectionModel<ImpegnoDTO>(true, []);
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  params: URLSearchParams;

  // loaaded
  loadedCercaImpegni: boolean;
  loadedCercaDirezione: boolean;
  loadedCercaAnniEsercizioValidi: boolean;
  loadedCercaDettaglioAtto: boolean;

  messageError: string;
  isMessageErrorVisible: boolean;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private navigationService: NavigationGestioneImpegniService,
    private gestioneImpegniService: GestioneImpegniService,
    private userService: UserService,
    private _snackBar: MatSnackBar
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.loadData();
      }
    });
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

  checkboxLabel(row?: ImpegnoDTO): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.idImpegno + 1}`;
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
    this.selection.toggle(row);
    if (this.selection.selected.length == 0) {
      this.showImpegniButtons = false;
    } else {
      this.showImpegniButtons = true;
    }
  }
  // ------------ Fine gestione checkbox tabelle ------------

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    let top = document.getElementById('scrollId');
    top.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  cerca(fromButton?: boolean) {
    if (fromButton) {
      this.selection.clear();
      this.navigationService.selectionSelezionata = this.selection;
    }
    this.annoEsercizioSelectedCopy = this.annoEsercizioSelected;
    this.annoImpegnoCopy = this.annoImpegno;
    this.annoProvvedimentoCopy = this.annoProvvedimento;
    this.nProvvedimentoCopy = this.nProvvedimento;
    this.direzioneProvvedimentoSelectedCopy = this.direzioneProvvedimentoSelected;
    this.nCapitoloCopy = this.nCapitolo;
    this.ragioneSocialeBeneficiarioImpegnoCopy = this.ragioneSocialeBeneficiarioImpegno;
    this.nImpegnoCopy = this.nImpegno;
    this.checkedImpegniReimputatiCopy = this.checkedImpegniReimputati;

    this.loadedCercaImpegni = true;
    this.gestioneImpegniService.cercaImpegni(this.user, this.annoEsercizioSelected, this.annoImpegno, this.annoProvvedimento, this.nProvvedimento, this.direzioneProvvedimentoSelected == undefined ? undefined : this.direzioneProvvedimentoSelected.descEnte, this.direzioneProvvedimentoSelected == undefined ? undefined : this.direzioneProvvedimentoSelected.idEnteCompetenza.toString(), this.nCapitolo, this.ragioneSocialeBeneficiarioImpegno, this.nImpegno, this.user.idSoggetto.toString(), this.checkedImpegniReimputati.toString()).subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      if (this.dataSource.filteredData.length == 0) {
        this.impegniTableEmpty = true;
      } else {
        this.impegniTableEmpty = false;
      }

      if (this.navigationService.selectionSelezionata) {
        this.navigationService.selectionSelezionata.selected.forEach(elementSelection => {
          data.forEach(elementTable => {
            if (elementSelection.idImpegno == elementTable.idImpegno) {
              this.selection.toggle(elementTable);
            }
          });
        });
      }

      if (this.selection.selected.length == 0) {
        this.showImpegniButtons = false;
      } else {
        this.showImpegniButtons = true;
      }
      this.loadedCercaImpegni = false;
    }, err => {
      this.loadedCercaImpegni = false;
      this.openSnackBar("Errore in fase di cerca degli impegni");
    });

    this.showResults = true;
    if (this.navigationService.annoEsercizioSelezionata) {
      this.ripristinaPaginator();
    } else {

    }
  }

  importaImpegno() {
    var comodo = new Array<any>();
    comodo.push(this.user);
    comodo.push(this.user.idSoggetto.toString());
    const dialogRef = this.dialog.open(ImportaImpegnoDialogComponent, {
      width: '800px',
      data: comodo
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result.data == true) {
        this.resetMessageError();
        this.showMessageSuccess("Importazione dell'impegno avvenuto con successo!");
        this.loadedCercaImpegni = true;
        this.gestioneImpegniService.cercaImpegni(this.user, this.annoEsercizioSelected, this.annoImpegno, this.annoProvvedimento, this.nProvvedimento, this.direzioneProvvedimentoSelected == undefined ? undefined : this.direzioneProvvedimentoSelected.descEnte, this.direzioneProvvedimentoSelected == undefined ? undefined : this.direzioneProvvedimentoSelected.idEnteCompetenza.toString(), this.nCapitolo, this.ragioneSocialeBeneficiarioImpegno, this.nImpegno, this.user.idSoggetto.toString(), this.checkedImpegniReimputati.toString()).subscribe(data => {
          this.dataSource = new MatTableDataSource(data);
          this.dataSource.paginator = this.paginator;
          if (this.dataSource.filteredData.length == 0) {
            this.impegniTableEmpty = true;
          } else {
            this.impegniTableEmpty = false;
          }
          this.loadedCercaImpegni = false;
        }, err => {
          this.openSnackBar("Errore in fase di cerca degli impegni");
          this.loadedCercaImpegni = false;
        });
      } else {
        if (result.data == false) {
          this.resetMessageSuccess();
          this.showMessageError("Impossibile importare i dati dell'impegno indicato!");
        }
      }
    })
  }

  annullaRicerca() {
    this.showResults = false;

    this.annoEsercizioSelected = this.anniEsercizio[0];
    this.checkedImpegniReimputati = false;
    this.annoImpegno = undefined;
    this.nImpegno = undefined;
    this.annoProvvedimento = undefined;
    this.nProvvedimento = undefined;
    this.direzioneProvvedimentoSelected = undefined;
    this.nCapitolo = undefined;
    this.ragioneSocialeBeneficiarioImpegno = undefined;
    this.selection.clear();
    this.saveDataForNavigation();

  }

  dettaglioImpegno(idImpegno: string) {
    this.loadedCercaDettaglioAtto = true;
    this.gestioneImpegniService.cercaDettaglioAtto(this.user, this.user.idSoggetto.toString(), idImpegno).subscribe(data => {
      this.loadedCercaDettaglioAtto = false;

      if (data.responseCodeMessage.message == "Nessun impegno da aggiornare trovato.") {
        this.showMessageError("Nessun impegno da aggiornare trovato.");
      } else {
        this.saveDataForNavigation();

        this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/dettaglioImpegno?idImpegno=" + idImpegno);
      }
    }, err => {
      this.loadedCercaDettaglioAtto = false;
      this.openSnackBar("Errore in fase di ottenimento dei dettagli dell'atto");
    });
  }

  // ------------ Inizio gestione dati navigazione ------------
  saveDataForNavigation() {
    this.navigationService.direzioneProvvedimentoSelezionata = this.direzioneProvvedimentoSelected;
    this.navigationService.annoEsercizioSelezionata = this.annoEsercizioSelected;
    this.navigationService.checkedImpegniReimputatiSelected = this.checkedImpegniReimputati;
    this.navigationService.annoImpegnoSelected = this.annoImpegno;
    this.navigationService.nImpegnoSelected = this.nImpegno;
    this.navigationService.annoProvvedimentoSelected = this.annoProvvedimento;
    this.navigationService.nProvvedimentoSelected = this.nProvvedimento;
    this.navigationService.nCapitoloSelected = this.nCapitolo;
    this.navigationService.ragioneSocialeBeneficiarioImpegnoSelected = this.ragioneSocialeBeneficiarioImpegno;
    this.navigationService.selectionSelezionata = this.selection;
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
    if (this.navigationService.annoImpegnoSelected) {
      this.annoImpegno = this.navigationService.annoImpegnoSelected;
    }

    if (this.navigationService.nImpegnoSelected) {
      this.nImpegno = this.navigationService.nImpegnoSelected;
    }

    if (!(this.navigationService.checkedImpegniReimputatiSelected == undefined)) {
      this.checkedImpegniReimputati = this.navigationService.checkedImpegniReimputatiSelected;
    }

    if (this.navigationService.annoProvvedimentoSelected) {
      this.annoProvvedimento = this.navigationService.annoProvvedimentoSelected;
    }

    if (this.navigationService.nProvvedimentoSelected) {
      this.nProvvedimento = this.navigationService.nProvvedimentoSelected;
    }

    if (this.navigationService.nCapitoloSelected) {
      this.nCapitolo = this.navigationService.nCapitoloSelected;
    }

    if (this.navigationService.ragioneSocialeBeneficiarioImpegnoSelected) {
      this.ragioneSocialeBeneficiarioImpegno = this.navigationService.ragioneSocialeBeneficiarioImpegnoSelected;
    }

    if (this.navigationService.direzioneProvvedimentoSelezionata) {
      this.direzioniProvvedimento = new Array<EnteCompetenzaDTO>();
      this.loadedCercaDirezione = true;
      this.gestioneImpegniService.cercaDirezione(this.user, this.user.idSoggetto.toString()).subscribe(data => {
        this.direzioniProvvedimento = data;
        this.direzioneProvvedimentoSelected = this.direzioniProvvedimento.find(n => n.idEnteCompetenza === this.navigationService.direzioneProvvedimentoSelezionata.idEnteCompetenza);
        this.loadedCercaDirezione = false;
      }, err => {
        this.loadedCercaDirezione = false;
        this.openSnackBar("Errore in fase di caricamento delle direzioni provvedimento");
      });
    } else {
      this.direzioniProvvedimento = new Array<EnteCompetenzaDTO>();
      this.loadedCercaDirezione = true;
      this.gestioneImpegniService.cercaDirezione(this.user, this.user.idSoggetto.toString()).subscribe(data => {
        this.direzioniProvvedimento = data;
        this.loadedCercaDirezione = false;
      }, err => {
        this.loadedCercaDirezione = false;
        this.openSnackBar("Errore in fase di caricamento delle direzioni provvedimento");
      });
    }

    if (this.navigationService.annoEsercizioSelezionata) {

      this.anniEsercizio = new Array<string>();

      this.loadedCercaAnniEsercizioValidi = true;
      this.gestioneImpegniService.cercaAnniEsercizioValidi(this.user).subscribe(data => {
        this.anniEsercizio = data;
        this.annoEsercizioSelected = this.anniEsercizio.find(n => n === this.navigationService.annoEsercizioSelezionata);
        this.cerca();
        this.loadedCercaAnniEsercizioValidi = false;
      }, err => {
        this.loadedCercaAnniEsercizioValidi = false;
        this.openSnackBar("Errore in fase di caricamento degli anni esercizio");
      });
    } else {
      this.anniEsercizio = new Array<string>();
      this.loadedCercaAnniEsercizioValidi = true;
      this.gestioneImpegniService.cercaAnniEsercizioValidi(this.user).subscribe(data => {
        this.anniEsercizio = data;
        this.annoEsercizioSelected = this.anniEsercizio[0];
        this.loadedCercaAnniEsercizioValidi = false;
      }, err => {
        this.loadedCercaAnniEsercizioValidi = false;
        this.openSnackBar("Errore in fase di caricamento degli anni esercizio");
      });
    }
  }
  // ------------ Fine gestione dati navigazione ------------

  gestisciAss() {
    this.saveDataForNavigation();
    var descEnteComodo;
    var idEnteCompComodo;
    if (this.direzioneProvvedimentoSelectedCopy == undefined) {
      descEnteComodo = undefined;
      idEnteCompComodo = undefined;
    } else {
      descEnteComodo = this.direzioneProvvedimentoSelectedCopy.descEnte;
      idEnteCompComodo = this.direzioneProvvedimentoSelectedCopy.idEnteCompetenza.toString();
    }

    var result = "";

    for (var i = 0; i < this.selection.selected.length; i++) {
      if (i == 0) {
        result = this.selection.selected[i].idImpegno.toString();
      } else {
        if (!result.includes(this.selection.selected[i].idImpegno.toString())) {
          result = result + "-" + this.selection.selected[i].idImpegno.toString();
        }
      }
    }

    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/associazioni?annoEsercizio=" + this.annoEsercizioSelectedCopy + "&annoImpegno=" + this.annoImpegnoCopy + "&annoProvvedimento=" +
      this.annoProvvedimentoCopy + "&nProvvedimento=" + this.nProvvedimentoCopy + "&descEnte=" + descEnteComodo + "&idEnteCompetenza=" + idEnteCompComodo + "&nCapitolo=" + this.nCapitoloCopy + "&ragioneSocialeBeneficiarioImpegno=" + this.ragioneSocialeBeneficiarioImpegnoCopy + "&nImpegno=" + this.nImpegno +
      "&checkedImpegniReimputati=" + this.checkedImpegniReimputatiCopy.toString() + "&idImpegni=" + result);
  }

  gestisciAssBen() {

    this.saveDataForNavigation();
    var descEnteComodo;
    var idEnteCompComodo;
    if (this.direzioneProvvedimentoSelectedCopy == undefined) {
      descEnteComodo = undefined;
      idEnteCompComodo = undefined;
    } else {
      descEnteComodo = this.direzioneProvvedimentoSelectedCopy.descEnte;
      idEnteCompComodo = this.direzioneProvvedimentoSelectedCopy.idEnteCompetenza.toString();
    }

    var result = "";

    for (var i = 0; i < this.selection.selected.length; i++) {
      if (i == 0) {
        result = this.selection.selected[i].idImpegno.toString();
      } else {
        if (!result.includes(this.selection.selected[i].idImpegno.toString())) {
          result = result + "-" + this.selection.selected[i].idImpegno.toString();
        }
      }
    }

    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/associazioniBeneficiario?annoEsercizio=" + this.annoEsercizioSelectedCopy + "&annoImpegno=" + this.annoImpegnoCopy + "&annoProvvedimento=" +
      this.annoProvvedimentoCopy + "&nProvvedimento=" + this.nProvvedimentoCopy + "&descEnte=" + descEnteComodo + "&idEnteCompetenza=" + idEnteCompComodo + "&nCapitolo=" + this.nCapitoloCopy + "&ragioneSocialeBeneficiarioImpegno=" + this.ragioneSocialeBeneficiarioImpegnoCopy + "&nImpegno=" + this.nImpegno +
      "&checkedImpegniReimputati=" + this.checkedImpegniReimputatiCopy.toString() + "&idImpegni=" + result);
  }

  nuovaAss() {
    this.saveDataForNavigation();

    var descEnteComodo;
    var idEnteCompComodo;
    if (this.direzioneProvvedimentoSelectedCopy == undefined) {
      descEnteComodo = undefined;
      idEnteCompComodo = undefined;
    } else {
      descEnteComodo = this.direzioneProvvedimentoSelectedCopy.descEnte;
      idEnteCompComodo = this.direzioneProvvedimentoSelectedCopy.idEnteCompetenza.toString();
    }

    var result = "";

    for (var i = 0; i < this.selection.selected.length; i++) {
      if (i == 0) {
        result = this.selection.selected[i].idImpegno.toString();
      } else {
        if (!result.includes(this.selection.selected[i].idImpegno.toString())) {
          result = result + "-" + this.selection.selected[i].idImpegno.toString();
        }
      }
    }

    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/nuovaAssociazione?gestAss=0&annoEsercizio=" + this.annoEsercizioSelectedCopy + "&annoImpegno=" + this.annoImpegnoCopy + "&annoProvvedimento=" +
      this.annoProvvedimentoCopy + "&nProvvedimento=" + this.nProvvedimentoCopy + "&descEnte=" + descEnteComodo + "&idEnteCompetenza=" + idEnteCompComodo + "&nCapitolo=" + this.nCapitoloCopy + "&ragioneSocialeBeneficiarioImpegno=" + this.ragioneSocialeBeneficiarioImpegnoCopy + "&nImpegno=" + this.nImpegno +
      "&checkedImpegniReimputati=" + this.checkedImpegniReimputatiCopy.toString() + "&idImpegni=" + result);
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  isLoading() {
    if (this.loadedCercaImpegni || this.loadedCercaDirezione || this.loadedCercaAnniEsercizioValidi || this.loadedCercaDettaglioAtto) {
      return true;
    } else {
      return false;
    }
  }
}
