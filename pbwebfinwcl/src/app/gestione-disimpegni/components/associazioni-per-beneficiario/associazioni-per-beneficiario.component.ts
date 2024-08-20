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
import { BeneficiarioVo } from '../../commons/beneficiario-vo';
import { EnteCompetenzaDTO } from '../../commons/ente-competenza-vo';
import { GestioneImpegnoDTO } from '../../commons/gestione-impegni-vo';
import { ImpegnoDTO } from '../../commons/impegno-vo';
import { ProgettoImpegnoDTO } from '../../commons/progetto-impegno-vo';
import { GestioneImpegniService } from '../../services/gestione-impegni.service';
import { EliminaDialogComponent } from '../elimina-dialog/elimina-dialog.component';

@Component({
  selector: 'app-associazioni-per-beneficiario',
  templateUrl: './associazioni-per-beneficiario.component.html',
  styleUrls: ['./associazioni-per-beneficiario.component.scss']
})
export class AssociazioniPerBeneficiarioComponent implements OnInit {

  // dichiarazione variabili tabelle
  displayedColumnsImpegni: string[] = ['impegno', 'impegnoReimp', 'annoEsercizio', 'capitolo', 'codFisc', 'ragSocCUP', 'provvedimento'];
  dataSourceImpegni: MatTableDataSource<ImpegnoDTO>;
  displayedColumnsProgetti: string[] = ['select', 'progetto', 'titoloProgetto', 'bandoLinea', 'beneficiario', 'quotaImportoAgevolato', 'nImpegniAssoc', 'totImportiImpAssociati'];
  dataSourceProgetti: MatTableDataSource<ProgettoImpegnoDTO>;

  // dichiarazioni variabili generali
  selectionProgetti = new SelectionModel<ProgettoImpegnoDTO>(true, []);
  showEliminaAssociazioneProgettiButton: boolean = false;

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

  // loaaded
  loadedCercaImpegni: boolean;
  loadedCercaProgettiAssociatiPerBeneficiario: boolean;
  loadedDisassociaProgettiGestAss: boolean;
  loadedAssociaProgettiImpegni: boolean;

  constructor(
    private router: Router,
    private gestioneImpegniService: GestioneImpegniService,
    private userService: UserService,
    private _snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.dataSourceImpegni = new MatTableDataSource();
    this.dataSourceProgetti = new MatTableDataSource();
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
          this.openSnackBar("Errore in fase di cerca degli impegni");
          this.loadedCercaImpegni = false;
        });

        var comodoResult = new Array<number>();
        this.idImpegni.forEach(element => {
          comodoResult.push(Number(element));
        });

        this.loadedCercaProgettiAssociatiPerBeneficiario = true;
        this.gestioneImpegniService.cercaProgettiAssociatiPerBeneficiario(this.user, this.user.idSoggetto.toString(), comodoResult, this.annoEsercizioSelected).subscribe(data => {
          this.dataSourceProgetti = new MatTableDataSource(data);
          this.loadedCercaProgettiAssociatiPerBeneficiario = false;
        }, err => {
          this.loadedCercaProgettiAssociatiPerBeneficiario = false;
          this.openSnackBar("Errore in fase di cerca dei progetti");
        });
      }
    });
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

  checkboxLabelProgetti(row?: ProgettoImpegnoDTO): string {
    if (!row) {
      return `${this.isAllSelectedProgetti() ? 'select' : 'deselect'} all`;
    }
    return `${this.selectionProgetti.isSelected(row) ? 'deselect' : 'select'} row ${row.idProgetto + 1}`;
  }

  masterToggleProgetti() {
    this.isAllSelectedProgetti() ?
      this.selectionProgetti.clear() :
      this.dataSourceProgetti.data.forEach(row => this.selectionProgetti.select(row));

    if (this.selectionProgetti.selected.length == 0) {
      this.showEliminaAssociazioneProgettiButton = false;
    } else {
      this.showEliminaAssociazioneProgettiButton = true;
    }
  }

  changeSingleRowProgetti(row: any) {
    this.selectionProgetti.toggle(row);
    if (this.selectionProgetti.selected.length == 0) {
      this.showEliminaAssociazioneProgettiButton = false;
    } else {
      this.showEliminaAssociazioneProgettiButton = true;
    }
  }
  // ------------ Fine gestione checkbox tabelle ------------

  eliminaAssociazioneProgetti() {
    const dialogRef = this.dialog.open(EliminaDialogComponent, {
      data: "Confermi di voler disassociare il/i progetto/i selezionato/i all'impegno?"
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result.data) {
        var comodo = new Array<number>();
        this.selectionProgetti.selected.forEach(element => {
          comodo.push(element.idProgetto);
        });

        var comodo2 = new Array<number>();
        this.idImpegni.forEach(element => {
          comodo2.push(Number(element));
        });

        var oggetto = new BeneficiarioVo(comodo2, comodo);

        this.loadedDisassociaProgettiGestAss = true;
        this.gestioneImpegniService.disassociaProgettiGestAss(this.user, oggetto).subscribe(data => {
          this.loadedDisassociaProgettiGestAss = false;
          var comodoResult = new Array<number>();
          this.idImpegni.forEach(element => {
            comodoResult.push(Number(element));
          });

          this.loadedCercaImpegni = true;
          this.gestioneImpegniService.cercaProgettiAssociatiPerBeneficiario(this.user, this.user.idSoggetto.toString(), comodoResult, this.annoEsercizioSelected).subscribe(data => {
            this.dataSourceProgetti = new MatTableDataSource(data);
            this.loadedCercaImpegni = false;
          }, err => {
            this.openSnackBar("Errore in fase di cerca dei progetti");
            this.loadedCercaImpegni = false;
          });
        }, err => {
          this.loadedDisassociaProgettiGestAss = false;
          this.openSnackBar("Errore in fase di eliminazione dell'associazione");
        });
      }
    })
  }

  calcolaTotale(arr: Array<ImpegnoDTO>) {
    if (arr == null) {
      return undefined;
    } else {
      var result = 0;
      arr.forEach(element => {
        result = result + element.disponibilitaLiquidare;
      });
      return result;
    }
  }

  associa() {
    var progettiSelezionati = new Array<number>();
    this.selectionProgetti.selected.forEach(element => {
      progettiSelezionati.push(element.idProgetto);
    });

    var impegniSelezionati = new Array<number>();
    this.idImpegni.forEach(element => {
      impegniSelezionati.push(Number(element));
    });

    var bandoLineaSelezionati = new Array<number>();

    var oggetto = new GestioneImpegnoDTO(impegniSelezionati, bandoLineaSelezionati, progettiSelezionati);

    this.loadedAssociaProgettiImpegni = true;
    this.gestioneImpegniService.associaProgettiImpegni(this.user, oggetto).subscribe(data => {
      this.loadedAssociaProgettiImpegni = false;
      var comodoResult = new Array<number>();
      this.idImpegni.forEach(element => {
        comodoResult.push(Number(element));
      });

      this.loadedCercaProgettiAssociatiPerBeneficiario = true;
      this.gestioneImpegniService.cercaProgettiAssociatiPerBeneficiario(this.user, this.user.idSoggetto.toString(), comodoResult, this.annoEsercizioSelected).subscribe(data => {
        this.dataSourceProgetti = new MatTableDataSource(data);
        this.loadedCercaProgettiAssociatiPerBeneficiario = false;
      }, err => {
        this.loadedCercaProgettiAssociatiPerBeneficiario = false;
        this.openSnackBar("Errore in fase di cerca dei progetti");
      });
    }, err => {
      this.loadedAssociaProgettiImpegni = false;
      this.openSnackBar("Errore in fase di associazione");
    });
  }

  tornaAllaRicercaImpegni() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/gestioneImpegni");
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  isLoading() {
    if (this.loadedCercaImpegni || this.loadedCercaProgettiAssociatiPerBeneficiario || this.loadedDisassociaProgettiGestAss || this.loadedAssociaProgettiImpegni) {
      return true;
    } else {
      return false;
    }
  }
}
