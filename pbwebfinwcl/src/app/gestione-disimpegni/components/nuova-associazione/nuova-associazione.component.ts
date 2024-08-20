/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { BandolineaImpegnoDTO } from '../../commons/bando-linea-impegno-vo';
import { EnteCompetenzaDTO } from '../../commons/ente-competenza-vo';
import { GenericSelectVo } from '../../commons/generic-select-vo';
import { GestioneImpegnoDTO } from '../../commons/gestione-impegni-vo';
import { ImpegnoDTO } from '../../commons/impegno-vo';
import { ProgettoImpegnoDTO } from '../../commons/progetto-impegno-vo';
import { GestioneImpegniService } from '../../services/gestione-impegni.service';
import { NavigationGestioneImpegniService } from '../../services/navigation-gestione-impegni.service';

@Component({
  selector: 'app-nuova-associazione',
  templateUrl: './nuova-associazione.component.html',
  styleUrls: ['./nuova-associazione.component.scss']
})
export class NuovaAssociazioneComponent implements OnInit {

  // Dichiarazioni variabili tabelle
  displayedColumnsImpegni: string[] = ['impegno', 'impegnoReimp', 'annoEsercizio', 'capitolo', 'codFisc', 'ragSocCUP', 'provvedimento'];
  dataSourceImpegni: MatTableDataSource<ImpegnoDTO>;
  displayedColumnsProgetti: string[] = ['select', 'progetto', 'titoloProgetto', 'bandoLinea', 'beneficiario', 'quotaImportoAgevolato', 'nImpegniAssoc', 'totImportiImpAssociati'];
  dataSourceProgetti: MatTableDataSource<ProgettoImpegnoDTO>;
  displayedColumnsBandoLinea: string[] = ['select', 'bandoLinea', 'dotazioneFinanziaria', 'numImpAsso', 'totImpImpAss'];
  dataSourceBandoLinea: MatTableDataSource<BandolineaImpegnoDTO>;

  // Dichiarazioni variabili generali
  selectionProgetti = new SelectionModel<ProgettoImpegnoDTO>(true, []);
  selectionBandoLinea = new SelectionModel<BandolineaImpegnoDTO>(true, []);
  showAssociaButtonProgetti: boolean = false;
  showAssociaButtonBandoLinea: boolean = false;
  direzioneSelected: EnteCompetenzaDTO;
  direzioni: Array<EnteCompetenzaDTO>;
  bandoLineaSelected: BandolineaImpegnoDTO;
  bandiLinea: Array<BandolineaImpegnoDTO>;
  progettoSelected: ProgettoImpegnoDTO;
  progetti: Array<ProgettoImpegnoDTO>;
  showResults = false;
  criteriRicercaOpened: boolean = true;
  params: URLSearchParams;
  gestAss: number;

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

  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  subscribers: any = {};

  // loaaded
  loadedCercaImpegni: boolean;
  loadedCercaDirezioneConImpegno: boolean;
  loadedAssociaProgettiImpegni: boolean;
  loadedCercaProgettiDaAssociare: boolean;
  loadedAssociaBandoLineaImpegni: boolean;
  loadedCercaBandoLineaDaAssociare: boolean;
  loadedCercaListaProgetti: boolean;
  loadedCercaListaBandoLinea: boolean;

  constructor(
    private router: Router,
    private userService: UserService,
    private gestioneImpegniService: GestioneImpegniService,
    private _snackBar: MatSnackBar
  ) {
    this.dataSourceImpegni = new MatTableDataSource();
    this.dataSourceBandoLinea = new MatTableDataSource();
    this.dataSourceProgetti = new MatTableDataSource();
  }

  ngOnInit(): void {
    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;
    this.gestAss = Number(this.params.get('gestAss'));

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;

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

        this.direzioni = new Array<EnteCompetenzaDTO>();
        this.loadedCercaDirezioneConImpegno = true;
        this.gestioneImpegniService.cercaDirezioneConImpegno(this.user).subscribe(data => {
          this.direzioni = data;
          this.loadedCercaDirezioneConImpegno = false;
        }, err => {
          this.loadedCercaDirezioneConImpegno = false;
          this.openSnackBar("Errore in fase di caricamento delle direzioni");
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
      this.showAssociaButtonProgetti = false;
    } else {
      this.showAssociaButtonProgetti = true;
    }
  }

  masterToggleBandoLinea() {
    this.isAllSelectedBandoLinea() ?
      this.selectionBandoLinea.clear() :
      this.dataSourceBandoLinea.data.forEach(row => this.selectionBandoLinea.select(row));

    if (this.selectionBandoLinea.selected.length == 0) {
      this.showAssociaButtonBandoLinea = false;
    } else {
      this.showAssociaButtonBandoLinea = true;
    }
  }

  changeSingleRowProgetti(row: any) {
    this.selectionProgetti.toggle(row);
    if (this.selectionProgetti.selected.length == 0) {
      this.showAssociaButtonProgetti = false;
    } else {
      this.showAssociaButtonProgetti = true;
    }
  }

  changeSingleRowBandoLinea(row: any) {
    this.selectionBandoLinea.toggle(row);
    if (this.selectionBandoLinea.selected.length == 0) {
      this.showAssociaButtonBandoLinea = false;
    } else {
      this.showAssociaButtonBandoLinea = true;
    }
  }
  // ------------ Fine gestione checkbox tabelle ------------

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  tornaAllaRicercaImpegni() {
    if (this.gestAss == 0) {
      this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/gestioneImpegni?idImpegni=" + this.params.get('idImpegni'));
    }

    if (this.gestAss == 1) {
      this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/associazioni?annoEsercizio=" + this.params.get('annoEsercizio') + "&annoImpegno=" + this.params.get('annoImpegno') + "&annoProvvedimento=" +
        this.params.get('annoProvvedimento') + "&nProvvedimento=" + this.params.get('nProvvedimento') + "&descEnte=" + this.params.get('descEnte') + "&idEnteCompetenza=" + this.params.get('idEnteCompetenza') + "&nCapitolo=" + this.params.get('nCapitolo') + "&ragioneSocialeBeneficiarioImpegno=" + this.params.get('ragioneSocialeBeneficiarioImpegno') + "&nImpegno=" + this.params.get('nImpegno') +
        "&checkedImpegniReimputati=" + this.params.get('checkedImpegniReimputati') + "&idImpegni=" + this.params.get('idImpegni'));
    }

    if (this.gestAss == 2) {
      this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/associazioniBeneficiario?idImpegni=" + this.params.get('idImpegni'));
    }
  }

  associaProgetto() {
    this.resetMessageSuccess();
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
      this.showMessageSuccess("Salvataggio avvenuto con successo.");
      this.loadedAssociaProgettiImpegni = false;
      if (this.progettoSelected == undefined) {
        this.dataSourceBandoLinea = new MatTableDataSource();
      } else {
        this.loadedCercaProgettiDaAssociare = true;
        this.gestioneImpegniService.cercaProgettiDaAssociare(this.user, impegniSelezionati, this.bandoLineaSelected.progrBandolineaIntervento.toString(), this.progettoSelected.idProgetto.toString()).subscribe(data => {
          this.dataSourceProgetti = new MatTableDataSource(data);
          this.loadedCercaProgettiDaAssociare = false;
        }, err => {
          this.loadedCercaProgettiDaAssociare = false;
          this.openSnackBar("Errore in fase di caricamento dei progetti");
        });
      }
    }, err => {
      this.loadedAssociaProgettiImpegni = false;
      this.openSnackBar("Errore in fase di associazione del progetto");
    });
  }

  associaBandoLinea() {
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

    this.loadedAssociaBandoLineaImpegni = true;
    this.gestioneImpegniService.associaBandoLineaImpegni(this.user, this.user.idSoggetto.toString(), oggetto).subscribe(data => {
      this.showMessageSuccess("Salvataggio avvenuto con successo.");
      this.loadedAssociaBandoLineaImpegni = false;
      this.loadedCercaBandoLineaDaAssociare = true;
      this.gestioneImpegniService.cercaBandoLineaDaAssociare(this.user, impegniSelezionati, this.direzioneSelected.idEnteCompetenza.toString(), this.bandoLineaSelected.progrBandolineaIntervento.toString()).subscribe(data => {
        this.dataSourceBandoLinea = new MatTableDataSource(data);
        this.loadedCercaBandoLineaDaAssociare = false;
      }, err => {
        this.loadedCercaBandoLineaDaAssociare = false;
        this.openSnackBar("Errore in fase di caricamento dei bando linea");
      });
    }, err => {
      this.loadedAssociaBandoLineaImpegni = false;
      this.openSnackBar("Errore in fase di associazione del bando linea");
    });
  }

  changeDirezione() {
    this.bandoLineaSelected = undefined;
    this.progettoSelected = undefined;

    var impegniSelezionati = new Array<number>();
    this.idImpegni.forEach(element => {
      impegniSelezionati.push(Number(element));
    });
    this.bandiLinea = new Array<BandolineaImpegnoDTO>();
    this.loadedCercaListaBandoLinea = true;
    this.gestioneImpegniService.cercaListaBandoLinea(this.user, impegniSelezionati, this.direzioneSelected.idEnteCompetenza.toString()).subscribe(data => {
      this.bandiLinea = data;
      this.loadedCercaListaBandoLinea = false;
    }, err => {
      this.loadedCercaListaBandoLinea = false;
      this.openSnackBar("Errore in fase di caricamento delle direzioni");
    });
  }

  changeBandoLinea() {
    this.progettoSelected = undefined;

    var impegniSelezionati = new Array<number>();
    this.idImpegni.forEach(element => {
      impegniSelezionati.push(Number(element));
    });
    this.progetti = new Array<ProgettoImpegnoDTO>();
    this.loadedCercaListaProgetti = true;
    this.gestioneImpegniService.cercaListaProgetti(this.user, impegniSelezionati, this.bandoLineaSelected.progrBandolineaIntervento.toString()).subscribe(data => {
      this.progetti = data;
      this.loadedCercaListaProgetti = false;
    }, err => {
      this.loadedCercaListaProgetti = false;
      this.openSnackBar("Errore in fase di caricamento dei progetti");
    });
  }

  cerca() {
    this.showResults = true;

    var impegniSelezionati = new Array<number>();
    this.idImpegni.forEach(element => {
      impegniSelezionati.push(Number(element));
    });

    this.loadedCercaBandoLineaDaAssociare = true;
    this.gestioneImpegniService.cercaBandoLineaDaAssociare(this.user, impegniSelezionati, this.direzioneSelected.idEnteCompetenza.toString(), this.bandoLineaSelected.progrBandolineaIntervento.toString()).subscribe(data => {
      this.dataSourceBandoLinea = new MatTableDataSource(data);
      this.loadedCercaBandoLineaDaAssociare = false;
    }, err => {
      this.loadedCercaBandoLineaDaAssociare = false;
      this.openSnackBar("Errore in fase di caricamento dei bando linea");
    });

    if (this.progettoSelected == undefined) {
      this.dataSourceBandoLinea = new MatTableDataSource();
    } else {
      this.loadedCercaProgettiDaAssociare = true;
      this.gestioneImpegniService.cercaProgettiDaAssociare(this.user, impegniSelezionati, this.bandoLineaSelected.progrBandolineaIntervento.toString(), this.progettoSelected.idProgetto.toString()).subscribe(data => {
        this.dataSourceProgetti = new MatTableDataSource(data);
        this.loadedCercaProgettiDaAssociare = false;
      }, err => {
        this.loadedCercaProgettiDaAssociare = false;
        this.openSnackBar("Errore in fase di caricamento dei progetti");
      });
    }

  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  calcolaTotale(arr: Array<ImpegnoDTO>) {
    var result = 0;
    arr.forEach(element => {
      result = result + element.disponibilitaLiquidare;
    });
    return result;
  }

  isLoading() {
    if (this.loadedCercaImpegni || this.loadedCercaDirezioneConImpegno || this.loadedAssociaProgettiImpegni || this.loadedCercaProgettiDaAssociare || this.loadedAssociaBandoLineaImpegni ||
      this.loadedCercaBandoLineaDaAssociare || this.loadedCercaListaProgetti || this.loadedCercaListaBandoLinea) {
      return true;
    } else {
      return false;
    }
  }
}