/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material/table';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginator } from '@angular/material/paginator';
import { SchedaClienteMain } from '../../commons/dto/scheda-cliente-main';
import { ModBenResponseService } from '../../services/modben-response-service.service';
import { Constants } from '../../../core/commons/util/constants';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Data, Router } from '@angular/router';
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
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EditDialogComponent } from '../dialog-edit-modben/dialog-edit.component';
import { SchedaClienteDettaglioErogato } from '../../commons/dto/scheda-cliente-dettaglio-erogato';
import { HistoryAIACdialogComponent } from '../dialog-history-attistarecre/dialog-history-attistarecre.component'; 
import { SchedaClienteHistory } from '../../commons/dto/scheda-cliente-history';
import { LiberazioneGaranteDTO } from '../../commons/dto/liberazione-garante-dto';
import { AttivitàIstruttoreAreaCreditiService } from '../../services/attivita-istruttore-area-crediti.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { LiberazioneGaranteDialogComponent } from '../new-edit-liberazione-garante-dialog/libgar-dialog.component';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';






@Component({
  selector: 'app-liberazione-garante',
  templateUrl: './liberazione-garante.component.html',
  styleUrls: ['./liberazione-garante.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})

@AutoUnsubscribe({ objectName: 'subscribers' })
export class LiberazioneGaranteComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  liberazioneGarante: Array<LiberazioneGaranteDTO> = new Array<LiberazioneGaranteDTO>();
  //OBJECT SUBSCRIBER
  public subscribers: any = {};
  displayedColumns: string[] = ['garanteLiberato', 'dataLiberazione', 'utenteModifica', 'options'];
  historyDisplayedColumns: string[] = ['garanteLiberato', 'data', 'istruttore'];
  idUtente: string;
  idProgetto: string; // USED FOR PAGE POPULATION
  isShowing: string = "main";
  noData: boolean = false;
  error: boolean = false;

  isSuccVisible: boolean = false;
  succMsg: string = "Salvato";

  constructor(
    private resService: AttivitàIstruttoreAreaCreditiService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService,
    //private handleExceptionService: HandleExceptionService,
    private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = String(data.idUtente);

        this.idProgetto = this.item;

        this.error = false;
        this.load();

      } else {
        this.isShowing = "error";
        this.error = true
      }
    }, err => {
      this.isShowing = "error"
      this.error = true
      //this.handleExceptionService.handleNotBlockingError(err);
    });
  }





  openEditDialog(operation: string, rowElement?: LiberazioneGaranteDTO): void {
    let dialogRef = this.dialog.open(LiberazioneGaranteDialogComponent, {
      width: '500px',
      data: {
        operation: operation,
        idUtente: this.idUtente,
        idProgetto: this.item,
        LiberazioneGarante: rowElement,
        nomeBox : this.nomeBox,
        idModalitaAgevolazione : this.idModalitaAgevolazione
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result==true){
        this.load();
      }
      
    });
  }

  openHistory(): void {

    this.dialog.open(HistoryAIACdialogComponent, {
      width: '600px',
      data: {
        id: 1,
        liberazioneGarante: this.liberazioneGarante
      }
    });

    this.isSuccVisible = false;
  }

  load() {
    this.isShowing = "loading"
    this.resService.getLiberazioneGarante(this.item, this.idModalitaAgevolazione).subscribe(data => {
      if (data.length > 0) {
        this.liberazioneGarante = data;

        //console.log(this.liberazioneGarante)

        this.noData = false;
        this.error = false;

        this.isShowing = "main";
      } else {
        this.noData = true;
        this.error = false;
        this.isShowing = "nodata"
      }

    }, err => {
      this.error = true;
      this.noData = false;
      this.isShowing = "error";
      //this.snackBar.open("err", "Chiudi", { duration: 3000, });
      //this.handleExceptionService.handleBlockingError(err);
    });
  }

}
