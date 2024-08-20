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
import { LiberazioneGaranteDTO } from '../../commons/dto/liberazione-garante-dto';
import { DatePipe } from '@angular/common';
import { SaveSchedaCliente } from '../../commons/dto/save-scheda-cliente.all';




@Component({
  selector: 'libgar-dialog',
  templateUrl: './libgar-dialog.component.html',
  styleUrls: ['./libgar-dialog.component.scss'],
})



export class LiberazioneGaranteDialogComponent implements OnInit {
  today = new Date();
  idModalitaAgevolazione = this.data.idModalitaAgevolazione;
  nomeBox = this.data.nomeBox;
  idProgetto = this.data.idProgetto;
  allegati: any;
  public myForm: FormGroup;
  operation: string;
  idUtente: string
  idCurrentRecord: number = 0;
  showError: boolean = false;
  errorMsg: string = "";
  showSucc: boolean;
  messageSuccess: any;

  constructor(
    public datepipe: DatePipe,
    private fb: FormBuilder,
    private resService: AttivitàIstruttoreAreaCreditiService,
    public dialogRef: MatDialogRef<LiberazioneGaranteDialogComponent>,
    public sharedService: SharedService,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.operation = this.data.operation;

    if (this.data.operation == "edit") {
      this.idCurrentRecord = this.data.LiberazioneGarante.idLibGar;
    }

    this.idProgetto = this.data.idProgetto;
    this.idUtente = this.data.idUtente;
  }



  ngOnInit(): void {
    this.myForm = this.fb.group({
      dataLiberazione: new FormControl('', [Validators.required]),
      importoLiberazione: new FormControl('', [Validators.required]),
      nominativoGarante: new FormControl('', [Validators.required]),
      note: new FormControl(''),
    });

    if (this.data.operation == "edit") {

      //console.log(this.data.LiberazioneGarante)

      if (this.data.LiberazioneGarante.importoLiberazione != null && this.data.LiberazioneGarante.importoLiberazione != undefined) {
        this.myForm.patchValue({ importoLiberazione: this.sharedService.formatValue(String(this.data.LiberazioneGarante.importoLiberazione)) });
      }

      this.myForm.patchValue({
        dataLiberazione: new Date(this.data.LiberazioneGarante.dataLiberazione),
        nominativoGarante: this.data.LiberazioneGarante.garanteLiberato,
        note: this.data.LiberazioneGarante.note
      });
    }
  }

 /*  recuperaFile(newItem) {
    this.allegati = newItem;
  } */
  
  salva(): void {
    if (!this.myForm.valid) {

      this.errorMsg = "È necessario valorizzare i campi obbligatori";
      this.showError = true;

    } else if(!this.myForm.dirty) {

      this.errorMsg = "Modificare almeno un campo";
      this.showError = true;

    } else {
      this.showError = false
      let NewLiberazioneGarante: LiberazioneGaranteDTO = new LiberazioneGaranteDTO();
      let formControls = this.myForm.getRawValue();
      NewLiberazioneGarante.operation = this.operation;
      NewLiberazioneGarante.idLibGar = this.idCurrentRecord;
      NewLiberazioneGarante.idUtente = this.idUtente;
      NewLiberazioneGarante.idProgetto = this.idProgetto;
      NewLiberazioneGarante.dataLiberazione = formControls.dataLiberazione;
      NewLiberazioneGarante.importoLiberazione = this.sharedService.getNumberFromFormattedString(formControls.importoLiberazione);
      NewLiberazioneGarante.garanteLiberato = formControls.nominativoGarante;
      NewLiberazioneGarante.note = formControls.note;
//      this.resService.insertLiberazioneGarante(NewLiberazioneGarante,this.allegati,this.idProgetto,this.idModalitaAgevolazione).subscribe(data => {
      this.resService.insertLiberazioneGarante(NewLiberazioneGarante, this.idModalitaAgevolazione).subscribe(data => {
      if (data ==true) {
          this.showError = false;
          this.showSucc = true;
          this.messageSuccess = data.message;
          setTimeout(() => {
            this.dialogRef.close(true);
          }, 1500); 
          
      } else  {
          this.showError = true;
          this.showSucc = false;
      //    this.showMessageError(data.message);
      } 

      //TODOMAR

        }, err => {
          
        });

      
    }
  }


  closeDialog() {
    this.dialogRef.close()
  }
}
