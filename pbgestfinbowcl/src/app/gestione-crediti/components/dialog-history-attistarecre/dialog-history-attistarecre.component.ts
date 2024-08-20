/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material/table';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Observable } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { startWith, debounceTime, distinctUntilChanged, switchMap, map } from 'rxjs/operators';
import { MatIconModule } from '@angular/material/icon';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatListModule } from '@angular/material/list';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DettaglioFinanziamentoErogato } from 'src/app/gestione-crediti/commons/dto/dettaglio-finanziamento-erogato';
import { CDK_CONNECTED_OVERLAY_SCROLL_STRATEGY_PROVIDER_FACTORY } from '@angular/cdk/overlay/overlay-directives';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { SchedaClienteHistory } from '../../commons/dto/scheda-cliente-history';
//import { Tracing } from 'trace_events';
import { SchedaClienteMain } from '../../commons/dto/scheda-cliente-main';




@Component({
  selector: 'dialog-history-aiac',
  templateUrl: './dialog-history-attistarecre.component.html',
  styleUrls: ['./dialog-history-attistarecre.component.scss'],
})



export class HistoryAIACdialogComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  idComponentToShow: number;

  historyDisplayedColumns1: string[] = ['data', 'istruttore', 'garanteLiberato'];
  historyDisplayedColumns2: string[] = ['data', 'istruttore', 'nominativo'];
  historyDisplayedColumns3: string[] = ['data', 'istruttore'];
  historyDisplayedColumns4: string[] = ['data', 'istruttore'];
  historyDisplayedColumns5: string[] = ['data', 'istruttore'];
  historyDisplayedColumns6: string[] = ['data', 'istruttore'];

  /*staAzColumns: string[] = ['statoAzienda', 'dataInizio', 'dataFine', 'utenteModifica'];
  StaCreFinColumns: string[] = ['statoCredito', 'dataModifica', 'UtenteModifica'];
  RatColumns: string[] = ['rating', 'provider', 'dataClass', 'utenteModifica'];
  BanBenColumns: string[] = ['banca', 'dataModifica', 'motivazione', "soggettoTerzo", 'utenteModifica'];*/

  /*schedaClienteMain: SchedaClienteMain = new SchedaClienteMain;

  storicoStatoAzienda: Array<SchedaClienteHistory>;
  storicoStatoCreditoFin: Array<SchedaClienteHistory>;
  storicoRating: Array<SchedaClienteHistory>;
  storicoBancaBeneficiario: Array<SchedaClienteHistory>;*/


  constructor(
    public dialogRef: MatDialogRef<HistoryAIACdialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    this.idComponentToShow = this.data.id;
    //console.log("id: ", this.idComponentToShow, " data: ", this.data);

    /*this.schedaClienteMain = this.data.schedaClienteMain;

    this.storicoStatoAzienda = this.data.storicoStatoAzienda;
    this.storicoStatoCreditoFin = this.data.storicoStatoCreditoFin;
    this.storicoRating = this.data.storicoRating;
    this.storicoBancaBeneficiario = this.data.storicoBancaBeneficiario;*/
  }


  ngOnInit(): void { }


  closeDialog() { this.dialogRef.close() }

}
