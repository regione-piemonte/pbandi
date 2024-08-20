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
//import { Tracing } from 'trace_events';
import { AttivitàIstruttoreAreaCreditiService } from '../../services/attivita-istruttore-area-crediti.service';
import { DatePipe } from '@angular/common';
import { PianoRientroDTO } from '../../commons/dto/piano-rientro-dto';
import { RichiesteService } from 'src/app/gestione-richieste/services/richieste.service';




@Component({
  selector: 'dialog-piano-rientro',
  templateUrl: './dialog-piano-rientro.component.html',
  styleUrls: ['./dialog-piano-rientro.component.scss'],
})



export class DialogPianoRientroComponent implements OnInit {
  today = new Date(); 
  idModalitaAgevolazione = this.data.idModalitaAgevolazione;
  ambito = this.data.ambito;
  nomeBox = this.data.nomeBox;
  idProgetto = this.data.idProgetto;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  public myForm: FormGroup;

  rowPianoRientro: PianoRientroDTO = new PianoRientroDTO;

  esiti: Array<string> = [];
  recuperi: Array<string> = [];

  operation: string;
  idUtente: string
  showError: boolean = false;
  errorMsg: string = "";
  allegati: any;
  showSucc: boolean;
  messageSuccess: any;

  constructor(
    private responceService: AttivitàIstruttoreAreaCreditiService,
    public datepipe: DatePipe,
    private fb: FormBuilder,
    public sharedService: SharedService,
    public dialogRef: MatDialogRef<DialogPianoRientroComponent>,
    private resService: AttivitàIstruttoreAreaCreditiService,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    this.operation = this.data.operation;

    this.rowPianoRientro = this.data.rowPianoRientro;
    this.esiti = this.data.esiti;
    this.recuperi = this.data.recuperi;

    //console.log("row: ", this.data.rowPianoRientro)
    this.idProgetto = this.data.idProgetto;
    this.idUtente = this.data.idUtente;
  }



  ngOnInit(): void {
    this.myForm = this.fb.group({
      dataProposta: new FormControl(null),
      importoCapitale: new FormControl(null),
      importoAgevolazione: new FormControl(null),
      esito: new FormControl("-"),
      dataEsito: new FormControl(null),
      recupero: new FormControl("-"),
      note: new FormControl(null),
    });

    if (this.operation == "edit") {

      if (this.rowPianoRientro.dataProposta != null) {
        this.myForm.patchValue({ dataProposta: new Date(this.rowPianoRientro.dataProposta) });
      }

      if (this.rowPianoRientro.dataEsito != null) {
        this.myForm.patchValue({ dataEsito: new Date(this.rowPianoRientro.dataEsito) });
      }

      if (this.rowPianoRientro.importoCapitale != null) {
        this.myForm.patchValue({ importoCapitale: this.sharedService.formatValue(String(this.rowPianoRientro.importoCapitale))});
      }

      if (this.rowPianoRientro.importoAgevolazione != null) {
        this.myForm.patchValue({importoAgevolazione: this.sharedService.formatValue(String(this.rowPianoRientro.importoAgevolazione))});
      }

      this.myForm.patchValue({
        esito: this.rowPianoRientro.esito,
        recupero: this.rowPianoRientro.recupero,
        note: this.rowPianoRientro.note,
      });
    }

    //console.log(this.myForm)
  }

  recuperaFile(newItem) {
    this.allegati = newItem;
  }

  salva(): void {

    let formControls = this.myForm.getRawValue();

    if (this.myForm.invalid) {

      return;

    }else if ((formControls.dataProposta == null || formControls.dataProposta == "") &&
      (formControls.importoCapitale == null || formControls.importoCapitale == "") &&
      (formControls.importoAgevolazione == null || formControls.importoAgevolazione == "") &&
      (formControls.esito == null || formControls.esito == "-") &&
      (formControls.dataEsito == null || formControls.dataEsito == "") &&
      (formControls.recupero == null || formControls.recupero == "-") &&
      (formControls.note == null || formControls.note == "")) {

      this.errorMsg = "Almeno un campo deve essere valorizzato";
      this.showError = true;
      
    } else if(!this.myForm.dirty) {

      this.errorMsg = "Modificare almeno un campo";
      this.showError = true;

    } else {

      this.showError = false;

      let NewPianoRientro: PianoRientroDTO = new PianoRientroDTO();

      //console.log("row: ", this.rowPianoRientro)


      NewPianoRientro.operation = this.operation;

      NewPianoRientro.idUtente = this.idUtente;
      NewPianoRientro.idProgetto = this.idProgetto;

      if (this.operation == 'edit') {
        NewPianoRientro.idCurrentRecord = this.rowPianoRientro.idCurrentRecord;
      }

      //NewPianoRientro.esito = formControls.esito;
      if (formControls.esito != "-") {
        NewPianoRientro.esito = formControls.esito;
      }

      NewPianoRientro.dataEsito = formControls.dataEsito;
      NewPianoRientro.dataProposta = formControls.dataProposta;

      NewPianoRientro.importoCapitale = this.sharedService.getNumberFromFormattedString(formControls.importoCapitale);
      /*if (formControls.importoCapitale == "") {
        NewPianoRientro.importoCapitale = null;
      } else {
        NewPianoRientro.importoCapitale = formControls.importoCapitale;
      }*/

      NewPianoRientro.importoAgevolazione = this.sharedService.getNumberFromFormattedString(formControls.importoAgevolazione);
      /*if (formControls.importoAgevolazione == "") {
        NewPianoRientro.importoAgevolazione = null;
      } else {
        NewPianoRientro.importoAgevolazione = formControls.importoAgevolazione;
      }*/

      //NewPianoRientro.recupero = formControls.recupero;
      if (formControls.recupero != "-") {
        NewPianoRientro.recupero = formControls.recupero;
      }

      //NewPianoRientro.note = formControls.note;
      if (formControls.note != "" && formControls.note != null) {
        NewPianoRientro.note = formControls.note;
      }
//this.resService.insertPianoRientro(NewPianoRientro,this.allegati,this.idProgetto,this.idModalitaAgevolazione).subscribe(data => {
      this.resService.insertPianoRientro(NewPianoRientro, this.idModalitaAgevolazione).subscribe(data => {
        if (data ==true) {
            this.showError = false;
            this.showSucc = true;
            this.messageSuccess = data.message;
            setTimeout(() => {
              this.dialogRef.close(true);
            }, 1500); 
            
        } else if (data == false) {
            this.showError = true;
            this.showSucc = false;
            this.dialogRef.close(false);
        //    this.showMessageError(data.message);
        } 
  
        //TODOMAR
  
          }, err => {
            
          });
    }
  }
  
  closeDialog() {
    this.dialogRef.close();
  }
}
