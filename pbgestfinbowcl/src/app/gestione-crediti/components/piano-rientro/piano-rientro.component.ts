/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild, Inject, Input, Output, EventEmitter } from '@angular/core';
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
import { HistoryDialogComponent } from '../dialog-history-modben/dialog-history.component';
import { SchedaClienteHistory } from '../../commons/dto/scheda-cliente-history';
import { LiberazioneGaranteDTO } from '../../commons/dto/liberazione-garante-dto';
import { AttivitàIstruttoreAreaCreditiService } from '../../services/attivita-istruttore-area-crediti.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { LiberazioneGaranteDialogComponent } from '../new-edit-liberazione-garante-dialog/libgar-dialog.component';
import { EscussioneConfidiDTO } from '../../commons/dto/escussione-confidi-dto';
import { Progetto } from '@pbandi/common-lib';
import { PianoRientroDTO } from '../../commons/dto/piano-rientro-dto';
import { DialogPianoRientroComponent } from '../dialog-piano-rientro/dialog-piano-rientro.component';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HistoryAIACdialogComponent } from '../dialog-history-attistarecre/dialog-history-attistarecre.component';



@Component({
  selector: 'app-piano-rientro',
  templateUrl: './piano-rientro.component.html',
  styleUrls: ['./piano-rientro.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class PianoRientroComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  pianoRientro: Array<PianoRientroDTO> = new Array<PianoRientroDTO>();

  esiti: string[] = [];
  recuperi: string[] = [];

  public subscribers: any = {};

  mainDisplayedColumns: string[] = ['esito', 'dataEsito', 'utenteModifica', 'options'];
  historyDisplayedColumns: string[] = ['data', 'istruttore'];

  idUtente: string;
  idProgetto: string;

  isShowing: string = "main";
  noData: boolean = false;
  //error: boolean = false;
  errMsg: string = "";

  isSuccVisible: boolean = false;
  succMsg: string = "Salvato";

  constructor(
    private resService: AttivitàIstruttoreAreaCreditiService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService,
    //private handleExceptionService: HandleExceptionService,
  ) { }

  ngOnInit(): void {
    
    this.isShowing = "loading";

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {

        this.idUtente = String(data.idUtente);

        this.idProgetto = this.item;

        this.load();
      }
    }, err => {
      this.isShowing = "error"
      //this.resService.setSpinner(false);
      //this.handleExceptionService.handleNotBlockingError(err);
    });

  }




  openDialog(operation: string, rowElement?: PianoRientroDTO): void {
    this.isSuccVisible= false; 
    let dialogRef = this.dialog.open(DialogPianoRientroComponent, {
      width: '470px',
      data: {
        operation: operation,
        idUtente: this.idUtente,
        idProgetto: this.item,
        rowPianoRientro: rowElement,
        esiti: this.esiti,
        recuperi: this.recuperi,
        nomeBox : this.nomeBox,
        idModalitaAgevolazione : this.idModalitaAgevolazione
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      if(result==true){
        this.isSuccVisible = true; 
        setTimeout(() => {
          this.load(); 
        }, 1500);
      } else {
      
        this.load();
      }
      
    });
  }

  openHistory(): void {

    this.dialog.open(HistoryAIACdialogComponent, {
      width: '500px',
      data: {
        id: 3,
        pianoRientro: this.pianoRientro
      }
    });

    this.isSuccVisible = false;
  }

 
  load() {
    
    this.isShowing = "loading"
    this.isSuccVisible=false;
    this.resService.getPianoRientro(this.item, this.idModalitaAgevolazione).subscribe((data) => {
      if (data.length != 0) {

        if (data[0].idCurrentRecord === null) {
          this.esiti = data[0].esiti;
          this.recuperi = data[0].recuperi;

          this.isShowing = "nodata";
          this.noData = true
        } else {
          this.pianoRientro = data;
          this.esiti = data[0].esiti;
          this.recuperi = data[0].recuperi;

          this.isShowing = "main";
          this.noData = false;
        }

        //console.log("piari: ", data);
      } else {
        this.isShowing = "nodata";
        this.noData = true
      }
    }, err => {
      this.isShowing = "error";
      //this.handleExceptionService.handleBlockingError(err);
    });
  }
}
