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
import { ActivatedRoute, Data, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { MatSort } from '@angular/material/sort';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
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
import { LiberazioneBancaDTO } from '../../commons/dto/liberazione-banca-dto';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HistoryAIACdialogComponent } from '../dialog-history-attistarecre/dialog-history-attistarecre.component';



@Component({
  selector: 'app-liberazione-banca',
  templateUrl: './liberazione-banca.component.html',
  styleUrls: ['./liberazione-banca.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class LiberazioneBancaComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  today =  new Date();
  allegati: any;
  public myForm: FormGroup;

  liberazione: Array<LiberazioneBancaDTO> = new Array<LiberazioneBancaDTO>();

  motivazioni: string[] = [];

  garanzie: string[] = [];

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  displayedColumns: string[] = ['garanteLiberato', 'dataLiberazione', 'utenteModifica', 'options'];
  historyDisplayedColumns: string[] = ['data', 'istruttore'];


  idUtente: string;

  idProgetto: string;

  isShowing: string = "main";
  noData: boolean = false;

  showError: boolean = false;
  errorMsg: string = "";

  isSuccVisible: boolean = false;
  succMsg: string = "Salvato";

  constructor(
    private resService: AttivitàIstruttoreAreaCreditiService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private userService: UserService,
    private fb: FormBuilder,
    //private handleExceptionService: HandleExceptionService,
  ) { }

  ngOnInit(): void {
    //this.spinner = true;

    this.myForm = this.fb.group({
      dataLiberazione: new FormControl(null),
      motivazione: new FormControl('-'),
      bancaLib: new FormControl(null),
      note: new FormControl(null),
    });


    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.idUtente = String(data.idUtente);

        // Necessario, non prende l'id da item
        this.route.queryParams.subscribe(params => {
          this.item = params.idProgetto;
        });
        
        //console.log("item lib ban:", this.item)
        this.idProgetto = this.item;
        this.getData()

      }
    }, err => {
      this.isShowing = "error"
      //this.handleExceptionService.handleNotBlockingError(err);
    });

  }


/* recuperaFile(newItem) {
    this.allegati = newItem;
    
  } */


  action(act: string): void {
    if (act == "save") {

      let formControls = this.myForm.getRawValue();
      if (formControls.dataLiberazione == null &&
        (formControls.motivazione == "-" || formControls.motivazione === null)
        && (formControls.bancaLib == "" || formControls.bancaLib === null)
        && (formControls.note == "" || formControls.note === null)) {

        this.errorMsg = "Almeno un campo deve essere valorizzato";
        this.showError = true;

        return;

      } else if (!this.myForm.dirty) {

        this.errorMsg = "Modificare almeno un campo";
        this.showError = true;

        return;
      }

      this.isShowing = "loading";

      this.showError = false;
      this.isSuccVisible = false;

      let newLiberazione: LiberazioneBancaDTO = new LiberazioneBancaDTO();


      if (formControls.dataLiberazione != "" && formControls.dataLiberazione != null) {
        newLiberazione.dataLiberazione = formControls.dataLiberazione;
      }

      if (formControls.motivazione == "-" || formControls.motivazione == null) {
        newLiberazione.motivazione = null;
      } else {
        newLiberazione.motivazione = formControls.motivazione;
      }

      if (formControls.bancaLib != "" && formControls.bancaLib != null) {
        newLiberazione.bancaLiberata = formControls.bancaLib;
      }

      if (formControls.note != "" && formControls.note != null) {
        newLiberazione.note = formControls.note;
      }

      if (this.liberazione[0]?.idCurrentRecord != undefined && this.liberazione[0].idCurrentRecord != null) {
        newLiberazione.idCurrentRecord = this.liberazione[0].idCurrentRecord;
      }
      
      newLiberazione.idUtente = this.idUtente;
      newLiberazione.idProgetto = this.item;
//this.resService.setLiberazioneBanca(newLiberazione,this.allegati,this.idModalitaAgevolazione).subscribe(data => {
      this.resService.inserisciLiberazioneBanca(newLiberazione, this.idModalitaAgevolazione).subscribe(data => {
        if (data) {
          this.isSuccVisible = true;

          this.getData();
        }
      }, err => {
        this.getData();
      });

      
      
    } else if (act == "cancel") {
      
      this.setForm()

      this.myForm.markAsPristine();

      this.isSuccVisible = false;
      this.showError = false;

      if (!this.noData) { this.isShowing = "main" }
    }
  }

  openHistory(): void {

    this.dialog.open(HistoryAIACdialogComponent, {
      width: '500px',
      data: {
        id: 4,
        liberazione: this.liberazione
      }
    });

    this.showError = false;
    this.isSuccVisible = false;
  }

  getData() {
  
    this.isShowing = "loading"

    this.resService.getNewLiberazione(this.item, this.idModalitaAgevolazione).subscribe((data) => {
      if (data.length != 0) {

        //console.log("newSer: ", data);

        if (data[0].idCurrentRecord === null) {
          this.motivazioni = data[0].motivazioni;

          this.isShowing = "edit";
          this.noData = true
        } else {
          this.liberazione = data;

          this.motivazioni = data[0].motivazioni;

          this.isShowing = "main";
          this.noData = false;
          this.setForm()
        }      

      } else {
        this.isShowing = "edit";
        this.noData = true
        //this.opsnack("C'è stato un errore nella lettura dati");
      }

    });

  }



  setForm() {

    if (!this.noData) {

      if (this.liberazione[0].dataLiberazione != null) {
        this.myForm.patchValue({
          dataLiberazione: new Date(this.liberazione[0].dataLiberazione)
        });
      } else {
        this.myForm.patchValue({
          dataLiberazione: null
        });
      }

      this.myForm.patchValue({
        motivazione: this.liberazione[0].motivazione,
        bancaLib: this.liberazione[0].bancaLiberata,
        note: this.liberazione[0].note
      });
    } else {

      this.myForm.patchValue({
        dataLiberazione: null,
        motivazione: "-",
        bancaLib: null,
        note: null,
      });
    }
  }


}
