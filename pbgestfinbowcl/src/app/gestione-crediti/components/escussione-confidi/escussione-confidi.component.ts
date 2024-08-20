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
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { HistoryAIACdialogComponent } from '../dialog-history-attistarecre/dialog-history-attistarecre.component';


@Component({
  selector: 'app-escussione-confidi',
  templateUrl: './escussione-confidi.component.html',
  styleUrls: ['./escussione-confidi.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class EscussioneConfidiComponent implements OnInit {
  @Input() item ;
  @Input() nomeBox ;
  @Input() idModalitaAgevolazione ;
  allegati: any;
  public myForm: FormGroup;

  escussione: Array<EscussioneConfidiDTO> = new Array<EscussioneConfidiDTO>();

  garanzie: string[] = [];

  //OBJECT SUBSCRIBER
  public subscribers: any = {};

  displayedColumns: string[] = ['garanteLiberato', 'dataLiberazione', 'utenteModifica', 'options'];
  historyDisplayedColumns: string[] = ['nominativo', 'data', 'istruttore'];

  idUtente: string;

  idProgetto: string;

  isShowing: string = "main";
  noData: boolean = false;
  error: boolean = false;
  errMsg: string = "";

  showError: boolean = false;
  errorMsg: string = "";

  isSuccVisible: boolean = false;
  succMsg: string = "Salvato";
  today = new Date(); 

  constructor(
    private resService: AttivitàIstruttoreAreaCreditiService,
    private route: ActivatedRoute,
    private userService: UserService,
    private fb: FormBuilder,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {

    this.isShowing = "loading";

    this.myForm = this.fb.group({
      nominativo: new FormControl(),
      dataEscussione: new FormControl(null),
      dataPagamento: new FormControl(null),
      garanzia: new FormControl("-"),
      percGaranzia: new FormControl(),
      note: new FormControl(),
    });


    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {

        this.idUtente = String(data.idUtente);

        this.idProgetto = this.item;

        this.load();
      }
    }, err => {
      this.isShowing = "error"
      this.errMsg = "Si è verificato un problema nell'identificazione dell'utente"
      //this.handleExceptionService.handleNotBlockingError(err);
    });




  }


  /* recuperaFile(newItem) {
    this.allegati = newItem;
    
  } */


  action(action: string): void {

    if (action == "save") {

      let formControls = this.myForm.getRawValue();

      if (this.emptyControls(formControls)) {

        this.errorMsg = "Almeno un campo deve essere valorizzato";
        this.showError = true;
        
      } else if (!this.myForm.dirty) {

        this.errorMsg = "Modificare almeno un campo"
        this.showError = true;

      } else {

        this.isShowing = "loading";

        this.showError = false;
        this.isSuccVisible = false;

        let newEscussione: EscussioneConfidiDTO = new EscussioneConfidiDTO();

        if (!this.noData) {
          newEscussione.idCurrentRecord = this.escussione[0].idCurrentRecord;
        }

        newEscussione.idUtente = this.idUtente;
        newEscussione.idProgetto = this.item;

        newEscussione.nominativo = formControls.nominativo;
        newEscussione.dataEscussione = formControls.dataEscussione;
        newEscussione.dataPagamento = formControls.dataPagamento;

        if (formControls.garanzia != "-") {
          newEscussione.garanzia = formControls.garanzia;
        }

        newEscussione.percGaranzia = formControls.percGaranzia;
        newEscussione.note = formControls.note;
 //this.resService.inserisciEscussioneConfidi(newEscussione,this.allegati,this.idModalitaAgevolazione).subscribe(data => {
        this.resService.inserisciEscussioneConfidi(newEscussione, this.idModalitaAgevolazione).subscribe(data => {
          if (data) {
            
            this.isSuccVisible = true;
          }
          
          this.load();
        }, err => {
          this.load();
          
        });

      }

    } else {
      this.setForm();

      this.showError = false;

      if (!this.noData) { this.isShowing = "main" }
    }
  }

  openHistory(): void {
    //console.log(this.myForm.dirty);

    this.dialog.open(HistoryAIACdialogComponent, {
      width: '600px',
      data: {
        id: 2,
        escussione: this.escussione
      }
    });

    this.isSuccVisible = false;
  }


  load() {

    this.isShowing = "loading";

    this.resService.getEscussioneConfidi(this.item, this.idModalitaAgevolazione).subscribe((data) => {
      if (data.length != 0) {

        //console.log("esc: ", data);
        
        if (data[0].idCurrentRecord == null) {
          this.garanzie = data[0].listGaranzie;

          this.isShowing = "edit";
          this.noData = true
        } else {
          this.escussione = data;
          this.garanzie = data[0].listGaranzie;

          this.isShowing = "main";
          this.noData = false;
          this.setForm();
        }
        
      } else {
        this.isShowing = "edit";
        this.noData = true
      }
      //console.log("noData: ", this.noData, "show: ", this.isShowing);
    }, err => {
      this.isShowing = "error";
      //this.handleExceptionService.handleBlockingError(err);
    });
    
  }


  setForm() {

    if (!this.noData) {

      if (this.escussione[0].dataEscussione != null) {
        this.myForm.patchValue({ dataEscussione: new Date(this.escussione[0].dataEscussione) });
      }

      if (this.escussione[0].dataPagamento != null) {
        this.myForm.patchValue({ dataPagamento: new Date(this.escussione[0].dataPagamento) });
      }

      this.myForm.patchValue({
        nominativo: this.escussione[0].nominativo,
        percGaranzia: this.escussione[0].percGaranzia,
        note: this.escussione[0].note
      });

      if (this.escussione[0].garanzia === null) {
        this.myForm.patchValue({garanzia: "-"});
      } else {
        this.myForm.patchValue({garanzia: this.escussione[0].garanzia});
      }
      
    } else {

      this.myForm.patchValue({
        dataEscussione: null,
        dataPagamento: null,
        nominativo: null,
        garanzia: "-",
        percGaranzia: null,
        note: null
      });
    }
  }


  emptyControls(formControls: any): boolean {

    if((formControls.nominativo == null || formControls.nominativo == "") &&
    (formControls.dataEscussione == null || formControls.dataEscussione == "") &&
    (formControls.dataPagamento == null || formControls.dataPagamento == "") &&
    (formControls.garanzia == null || formControls.garanzia == "-") &&
    (formControls.percGaranzia == null || formControls.percGaranzia == "") &&
    (formControls.note == null || formControls.note == "")) { return true} else { return false }
  }



  wasEdited(formControls: any): boolean {
    
    console.log("FC: ", formControls);
    console.log("ESC: ", this.escussione[0]);

    if (this.noData) {
      return true;

    } else {

      let dataEscussione = new Date(this.escussione[0].dataEscussione);
      let dataPagamento = new Date(this.escussione[0].dataPagamento)
      console.log("dataEscussione: ", dataEscussione, " - dataPagamento: ", dataPagamento);

      if (formControls.dataEscussione == dataEscussione) {
        console.log("dataEsc UGUALE");
      } else {
        console.log("dataEsc NON UGUALE");
      }

      if (formControls.dataPagamento == dataPagamento) {
        console.log("dataPag UGUALE");
      } else {
        console.log("dataPag NON UGUALE");
      }

   
    }
  }



}
//Lilì