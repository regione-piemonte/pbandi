/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterContentChecked, ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { abort, addListener } from 'process';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BeneficiarioProgettoAssociatoDTO } from '../../commons/dto/beneficiario-progetto-associato-dto';
import { UtenteRicercatoDTO } from '../../commons/dto/utente-ricercato-dto';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { NuovoModificaAssocBenProgDialogComponent } from '../nuovo-modifica-assoc-ben-prog-dialog/nuovo-modifica-assoc-ben-prog-dialog.component';

@Component({
  selector: 'app-gestione-benef-prog',
  templateUrl: './gestione-benef-prog.component.html',
  styleUrls: ['./gestione-benef-prog.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneBenefProgComponent implements OnInit, OnChanges, AfterContentChecked {

  @Input('utente') utente: UtenteRicercatoDTO;

  associati: Array<BeneficiarioProgettoAssociatoDTO>;
  displayedColumns: string[] = ['cfBenef', 'denomBenef', 'codiceProg', 'titoloProg', 'rapprLegale', 'azioni'];
  dataSource: AssociatiDataSource = new AssociatiDataSource([]);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedAssociati: boolean;
  loadedElimina: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private cdRef: ChangeDetectorRef,
    private gestioneUtentiService: GestioneUtentiService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.utente) {
      this.loadAssociati();
    }
  }

  loadAssociati() {
    this.loadedAssociati = false;
    this.subscribers.associati = this.gestioneUtentiService.gestioneBeneficiarioProgetto(this.utente.idUtente, this.utente.idSoggetto).subscribe(data => {
      if (data) {
        this.associati = data;
        this.dataSource = new AssociatiDataSource(this.associati);
        this.paginator.length = this.associati.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
      this.loadedAssociati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero delle associazioni.");
      this.loadedAssociati = true;
    });
  }

  aggiungi() {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(NuovoModificaAssocBenProgDialogComponent, {
      width: '40%',
      data: {
        isNuovo: true,
        utente: this.utente
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.showMessageSuccess(res.message);
        this.loadAssociati();
      }
    });
  }

  modifica(row: BeneficiarioProgettoAssociatoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(NuovoModificaAssocBenProgDialogComponent, {
      width: '40%',
      data: {
        isNuovo: false,
        row: row,
        utente: this.utente
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.showMessageSuccess(res.message);
        this.loadAssociati();
      }
    });
  }

  elimina(row: BeneficiarioProgettoAssociatoDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "Confermi l'eliminazione dell'associazione?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.elimina = this.gestioneUtentiService.eliminaAssociazioneBeneficiarioProgetto(this.utente.idUtente, this.utente.idSoggetto, row.codiceProgettoVisualizzato).subscribe(data => {
          if (data && data.esito) {
            this.showMessageSuccess("Eliminazione avvenuta con successo.");
            this.loadAssociati();
          } else {
            this.showMessageError("Errore in fase di eliminazione.");
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore in fase di eliminazione.");
        });
      }
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  ngAfterContentChecked() {
    this.cdRef.detectChanges();
  }


  isLoading() {
    if (!this.loadedAssociati || !this.loadedElimina) {
      return true;
    }
    return false;
  }
}

export class AssociatiDataSource extends MatTableDataSource<BeneficiarioProgettoAssociatoDTO> {

  _orderData(data: BeneficiarioProgettoAssociatoDTO[]): BeneficiarioProgettoAssociatoDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "codice";
    }
    let direction = this.sort.direction || this.sort.start;
    let sortedData = null;

    // viene riordinato in ordine ascendente 
    switch (this.sort.active) {
      case "cfBenef":
        sortedData = data.sort((rowA, rowB) => {
          return rowA.codiceFiscaleBeneficiario.localeCompare(rowB.codiceFiscaleBeneficiario);
        });
        break;

      case "denomBenef":
        sortedData = data.sort((rowA, rowB) => {
          return rowA.denominazioneBeneficiario.localeCompare(rowB.denominazioneBeneficiario);
        });
        break;

      case "codiceProg":
        sortedData = data.sort((rowA, rowB) => {
          return rowA.codiceProgettoVisualizzato.localeCompare(rowB.codiceProgettoVisualizzato);
        });
        break;

      case "titoloProg":
        sortedData = data.sort((rowA, rowB) => {
          return rowA.titoloProgetto.localeCompare(rowB.titoloProgetto);
        });
        break;

      case "rapprLegale":
        sortedData = data.sort((rowA, rowB) => {
          let a = rowA.isRappresentanteLegale ? "S" : "N";
          let b = rowB.isRappresentanteLegale ? "S" : "N";
          return a.localeCompare(b);
        });
        break;

      default:
        sortedData = data.sort((rowA, rowB) => {
          return rowA.codiceFiscaleBeneficiario.localeCompare(rowB.codiceFiscaleBeneficiario);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: BeneficiarioProgettoAssociatoDTO[]) {
    super(data);
    super.data = data
  }

}