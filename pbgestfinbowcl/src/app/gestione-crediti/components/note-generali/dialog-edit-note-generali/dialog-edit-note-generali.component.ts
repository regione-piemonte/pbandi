/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit, Optional } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AbbattimentoGaranzieVO } from '../../../commons/dto/abbattimento-garanzieVO';
import { AttivitaDTO } from '../../../commons/dto/attivita-dto';
import { StoricoAbbattimentoGaranziaDTO } from '../../../commons/dto/storico-abbattimento-garanzia-dto';
import { AbbattimentoGaranzieService } from '../../../services/abbattimento-garanzie.service';
import { Observable, ReplaySubject } from 'rxjs';
import { GestioneAllegatiVO } from 'src/app/gestione-crediti/commons/dto/gestione-allegati-VO';


@Component({
  selector: 'app-dialog-note-generali',
  templateUrl: './dialog-edit-note-generali.component.html',
  styleUrls: ['./dialog-edit-note-generali.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DialogEditNoteGeneraliComponent implements OnInit {

  op: number = 1; // 1 = New, 2 = Edit

  textNota: string = ""; // Il testo della nota

  nuoviAllegati: Array<GestioneAllegatiVO>;

  modalita: string = "S";

  subscribers: any = {};

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    public dialogRef: MatDialogRef<DialogEditNoteGeneraliComponent>,
    public sharedService: SharedService,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: {
      op: number,
      nota: string,
      allegatiPresenti: Array<GestioneAllegatiVO>
    }
  ) {
    console.log("Dialog Data: ", this.data);
    
    this.op = this.data.op; // 1 = New, 2 = Edit

    if (this.op == 2) { // Se è in edit modifica la nota
      this.textNota = this.data.nota;
    }
  }

  ngOnInit(): void { }


  salvaNota() {
    this.dialogRef.close({
      event: 'save',
      text: this.textNota,
      nuoviAllegati: this.nuoviAllegati,
      allegatiPresenti: this.data.allegatiPresenti
    });
  }


  

  recuperaFile(newItem) {
    //console.log("event from gestione allegati: ", newItem);

    console.log("dialog event:", newItem);
    this.nuoviAllegati = newItem;
  }

  update(event) {
    console.log("reload event from gestione allegati: ", event);
  }
  
  /* Non necessario
  closeDialog() {
    this.dialogRef.close();
  }*/


}
