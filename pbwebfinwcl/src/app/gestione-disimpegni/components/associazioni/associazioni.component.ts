/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { BandolineaImpegnoDTO } from '../../commons/bando-linea-impegno-vo';
import { BeneficiarioVo } from '../../commons/beneficiario-vo';
import { GestioneImpegnoDTO } from '../../commons/gestione-impegni-vo';
import { ImpegnoDTO } from '../../commons/impegno-vo';
import { ProgettoImpegnoDTO } from '../../commons/progetto-impegno-vo';
import { GestioneImpegniService } from '../../services/gestione-impegni.service';
import { EliminaDialogComponent } from '../elimina-dialog/elimina-dialog.component';

export interface PeriodicElement {
  impegno: string;
  impegnoReimp: string;
  annoEsercizio: string;
  capitolo: string;
  codFisc: string;
  ragSocCUP: string;
  provvedimento: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { impegno: "impegno1", impegnoReimp: 'impegnoReimp1', annoEsercizio: 'annoEsercizio1', capitolo: 'capitolo1', codFisc: 'codFisc1', ragSocCUP: 'ragSocCUP1', provvedimento: 'provvedimento1' },
  { impegno: "impegno2", impegnoReimp: 'impegnoReimp2', annoEsercizio: 'annoEsercizio2', capitolo: 'capitolo2', codFisc: 'codFisc2', ragSocCUP: 'ragSocCUP2', provvedimento: 'provvedimento2' },
  { impegno: "impegno3", impegnoReimp: 'impegnoReimp3', annoEsercizio: 'annoEsercizio3', capitolo: 'capitolo3', codFisc: 'codFisc3', ragSocCUP: 'ragSocCUP3', provvedimento: 'provvedimento3' },
];

@Component({
  selector: 'app-associazioni',
  templateUrl: './associazioni.component.html',
  styleUrls: ['./associazioni.component.scss']
})
export class AssociazioniComponent implements OnInit {

  // dichiarazione variabili tabelle
  displayedColumns: string[] = ['impegno', 'impegnoReimp', 'annoEsercizio', 'capitolo', 'codFisc', 'ragSocCUP', 'provvedimento'];
  dataSourceImpegni: MatTableDataSource<ImpegnoDTO>;
  displayedColumnsProgetti: string[] = ['select', 'progetto', 'titoloProgetto', 'bandoLinea', 'beneficiario', 'quotaImportoAgevolato', 'nImpegniAssoc', 'totImportiImpAssociati'];
  dataSourceProgetti: MatTableDataSource<ProgettoImpegnoDTO>;
  displayedColumnsBandoLinea: string[] = ['select', 'bandoLinea', 'dotazioneFinanziaria', 'numImpAsso', 'totImpImpAss'];
  dataSourceBandoLinea: MatTableDataSource<BandolineaImpegnoDTO>;

  // dichiarazione variabili generali
  selectionProgetti = new SelectionModel<ProgettoImpegnoDTO>(true, []);
  selectionBandoLinea = new SelectionModel<BandolineaImpegnoDTO>(true, []);
  showEliminaButtonProgetti: boolean = false;
  showEliminaButtonBandoLinea: boolean = false;

  // Dichiarazioni variabili
  annoEsercizioSelected: string;
  checkedImpegniReimputati: string;
  annoImpegno: string;
  nImpegno: string;
  annoProvvedimento: string;
  nProvvedimento: string;
  nCapitolo: string;
  ragioneSocialeBeneficiarioImpegno: string;
  idImpegno: string;
  descEnte: string;
  idEnteCompetenza: string;
  user: UserInfoSec;
  idImpegni: Array<string>;

  params: URLSearchParams;
  subscribers: any = {};

  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  // loaaded
  loadedCercaImpegni: boolean;
  loadedCercaBandoLineaAssociati: boolean;
  loadedCercaProgettiAssociati: boolean;
  loadedDisassociaProgettiGestAss: boolean;
  loadedDisassociaBandoLineaGestAss: boolean;

  constructor(
    private router: Router,
    private userService: UserService,
    private gestioneImpegniService: GestioneImpegniService,
    private _snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.dataSourceImpegni = new MatTableDataSource();
    this.dataSourceProgetti = new MatTableDataSource();
    this.dataSourceBandoLinea = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

        this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

        this.annoEsercizioSelected = this.params.get('annoEsercizio');
        this.annoImpegno = this.params.get('annoImpegno');
        this.annoProvvedimento = this.params.get('annoProvvedimento');
        this.nProvvedimento = this.params.get('nProvvedimento');
        this.descEnte = this.params.get('descEnte');
        this.idEnteCompetenza = this.params.get('idEnteCompetenza');
        this.nCapitolo = this.params.get('nCapitolo');
        this.ragioneSocialeBeneficiarioImpegno = this.params.get('ragioneSocialeBeneficiarioImpegno');
        this.nImpegno = this.params.get('nImpegno');
        this.checkedImpegniReimputati = this.params.get('checkedImpegniReimputati');
        this.idImpegni = new Array<string>();
        this.idImpegni = this.params.get('idImpegni').split("-");

        this.loadedCercaImpegni = true;
        this.gestioneImpegniService.cercaImpegni(this.user, this.annoEsercizioSelected, this.annoImpegno, this.annoProvvedimento, this.nProvvedimento, this.descEnte, this.idEnteCompetenza, this.nCapitolo, this.ragioneSocialeBeneficiarioImpegno, this.nImpegno, this.user.idSoggetto.toString(), this.checkedImpegniReimputati).subscribe(data => {
          var comodo = new Array<ImpegnoDTO>();
          data.forEach(element => {
            this.idImpegni.forEach(element2 => {
              if (element2 == element.idImpegno.toString()) {
                comodo.push(element);
              }
            });
          });
          this.dataSourceImpegni = new MatTableDataSource(comodo);
          this.loadedCercaImpegni = false;
        }, err => {
          this.loadedCercaImpegni = false;
          this.openSnackBar("Errore in fase di cerca degli impegni");
        });

        var impegniSelezionati = new Array<number>();
        this.idImpegni.forEach(element => {
          impegniSelezionati.push(Number(element));
        });

        this.loadedCercaBandoLineaAssociati = true;
        this.gestioneImpegniService.cercaBandoLineaAssociati(this.user, this.user.idSoggetto.toString(), impegniSelezionati).subscribe(data => {
          this.dataSourceBandoLinea = new MatTableDataSource(data);
          this.loadedCercaBandoLineaAssociati = false;
        }, err => {
          this.loadedCercaBandoLineaAssociati = false;
          this.openSnackBar("Errore in fase di cerca dei bando linea");
        });

        this.loadedCercaProgettiAssociati = true;
        this.gestioneImpegniService.cercaProgettiAssociati(this.user, this.user.idSoggetto.toString(), impegniSelezionati, this.annoEsercizioSelected).subscribe(data => {
          this.dataSourceProgetti = new MatTableDataSource(data);
          this.loadedCercaProgettiAssociati = false;
        }, err => {
          this.loadedCercaProgettiAssociati = false;
          this.openSnackBar("Errore in fase di cerca dei progetti");
        });
      }
    });
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

  // ------------ Inizio gestione checkbox tabelle ------------
  isAllSelectedProgetti() {
    const numSelected = this.selectionProgetti.selected.length;
    var numRows;
    if (this.dataSourceProgetti == undefined) {
      numRows = 0;
    } else {
      numRows = this.dataSourceProgetti.data.length;
    }

    return numSelected === numRows;
  }

  isAllSelectedBandoLinea() {
    const numSelected = this.selectionBandoLinea.selected.length;
    var numRows;
    if (this.dataSourceBandoLinea == undefined) {
      numRows = 0;
    } else {
      numRows = this.dataSourceBandoLinea.data.length;
    }

    return numSelected === numRows;
  }

  checkboxLabelProgetti(row?: ProgettoImpegnoDTO): string {
    if (!row) {
      return `${this.isAllSelectedProgetti() ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionProgetti.isSelected(row) ? 'deselect' : 'select'} row ${row.idProgetto + 1}`;
  }

  checkboxLabelBandoLinea(row?: BandolineaImpegnoDTO): string {
    if (!row) {
      return `${this.isAllSelectedBandoLinea() ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionBandoLinea.isSelected(row) ? 'deselect' : 'select'} row ${row.progrBandolineaIntervento + 1}`;
  }

  masterToggleProgetti() {
    this.isAllSelectedProgetti() ?
      this.selectionProgetti.clear() :
      this.dataSourceProgetti.data.forEach(row => this.selectionProgetti.select(row));

    if (this.selectionProgetti.selected.length == 0) {
      this.showEliminaButtonProgetti = false;
    } else {
      this.showEliminaButtonProgetti = true;
    }
  }

  masterToggleBandoLinea() {
    this.isAllSelectedBandoLinea() ?
      this.selectionBandoLinea.clear() :
      this.dataSourceBandoLinea.data.forEach(row => this.selectionBandoLinea.select(row));

    if (this.selectionBandoLinea.selected.length == 0) {
      this.showEliminaButtonBandoLinea = false;
    } else {
      this.showEliminaButtonBandoLinea = true;
    }
  }

  changeSingleRowProgetti(row: any) {
    this.selectionProgetti.toggle(row);
    if (this.selectionProgetti.selected.length == 0) {
      this.showEliminaButtonProgetti = false;
    } else {
      this.showEliminaButtonProgetti = true;
    }
  }

  changeSingleRowBandoLinea(row: any) {
    this.selectionBandoLinea.toggle(row);
    if (this.selectionBandoLinea.selected.length == 0) {
      this.showEliminaButtonBandoLinea = false;
    } else {
      this.showEliminaButtonBandoLinea = true;
    }
  }
  // ------------ Fine gestione checkbox tabelle ------------

  eliminaAssociazioneProgetto() {
    const dialogRef = this.dialog.open(EliminaDialogComponent, {
      data: "Confermi di voler disassociare il/i progetto/i selezionato/i all'impegno?"
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result.data) {
        this.resetMessageSuccess();
        var comodo = new Array<number>();
        this.selectionProgetti.selected.forEach(element => {
          comodo.push(element.idProgetto);
        });

        var comodo2 = new Array<number>();
        this.idImpegni.forEach(element => {
          comodo2.push(Number(element));
        });

        var impegniSelezionati = new Array<number>();
        this.idImpegni.forEach(element => {
          impegniSelezionati.push(Number(element));
        });

        var oggetto = new BeneficiarioVo(comodo2, comodo);
        this.loadedDisassociaProgettiGestAss = true;
        this.gestioneImpegniService.disassociaProgettiGestAss(this.user, oggetto).subscribe(data => {
          this.loadedDisassociaProgettiGestAss = false;
          this.loadedCercaProgettiAssociati = true;
          this.showMessageSuccess("Associazione rimossa con successo");
          this.gestioneImpegniService.cercaProgettiAssociati(this.user, this.user.idSoggetto.toString(), impegniSelezionati, this.annoEsercizioSelected).subscribe(data => {
            this.dataSourceProgetti = new MatTableDataSource(data);
            this.loadedCercaProgettiAssociati = false;
          }, err => {
            this.loadedCercaProgettiAssociati = false;
            this.openSnackBar("Errore in fase di cerca dei progetti");
          });
        }, err => {
          this.loadedDisassociaProgettiGestAss = false;
          this.openSnackBar("Errore in fase di eliminazione dell'associazione sul progetto");
        });
      }
    })
  }

  eliminaAssociazioneBandoLinea() {
    const dialogRef = this.dialog.open(EliminaDialogComponent, {
      data: "Confermi di voler disassociare il/i bando linea selezionato/i all'impegno?"
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result.data) {
        this.resetMessageSuccess();
        var progettiSelezionati = new Array<number>();

        var impegniSelezionati = new Array<number>();
        this.idImpegni.forEach(element => {
          impegniSelezionati.push(Number(element));
        });

        var bandoLineaSelezionati = new Array<number>();
        this.selectionBandoLinea.selected.forEach(element => {
          bandoLineaSelezionati.push(element.progrBandolineaIntervento);
        });

        var oggetto = new GestioneImpegnoDTO(impegniSelezionati, bandoLineaSelezionati, progettiSelezionati);

        this.loadedDisassociaBandoLineaGestAss = true;
        this.gestioneImpegniService.disassociaBandoLineaGestAss(this.user, oggetto).subscribe(data => {
          this.loadedDisassociaBandoLineaGestAss = false;
          this.loadedCercaBandoLineaAssociati = true;
          this.showMessageSuccess("Associazione rimossa con successo");
          this.gestioneImpegniService.cercaBandoLineaAssociati(this.user, this.user.idSoggetto.toString(), impegniSelezionati).subscribe(data => {
            this.dataSourceBandoLinea = new MatTableDataSource(data);
            this.loadedCercaBandoLineaAssociati = false;
          }, err => {
            this.loadedCercaBandoLineaAssociati = false;
            this.openSnackBar("Errore in fase di cerca dei bando linea");
          });
        }, err => {
          this.loadedDisassociaBandoLineaGestAss = false;
          this.openSnackBar("Errore in fase di eliminazione dell'associazione bando linea");
        });
      }
    })
  }

  nuovaAssociazione() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/nuovaAssociazione?gestAss=1&annoEsercizio=" + this.annoEsercizioSelected + "&annoImpegno=" + this.annoImpegno + "&annoProvvedimento=" +
      this.annoProvvedimento + "&nProvvedimento=" + this.nProvvedimento + "&descEnte=" + this.descEnte + "&idEnteCompetenza=" + this.idEnteCompetenza + "&nCapitolo=" + this.nCapitolo + "&ragioneSocialeBeneficiarioImpegno=" + this.ragioneSocialeBeneficiarioImpegno + "&nImpegno=" + this.nImpegno +
      "&checkedImpegniReimputati=" + this.checkedImpegniReimputati + "&idImpegni=" + this.params.get('idImpegni'));
  }

  tornaAllaRicercaImpegni() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/gestioneImpegni");
  }

  calcolaTotale(arr: Array<ImpegnoDTO>) {
    var result = 0;
    arr.forEach(element => {
      result = result + element.disponibilitaLiquidare;
    });
    return result;
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  isLoading() {
    if (this.loadedCercaImpegni || this.loadedCercaBandoLineaAssociati || this.loadedCercaProgettiAssociati || this.loadedDisassociaProgettiGestAss || this.loadedDisassociaBandoLineaGestAss) {
      return true;
    } else {
      return false;
    }
  }
}
