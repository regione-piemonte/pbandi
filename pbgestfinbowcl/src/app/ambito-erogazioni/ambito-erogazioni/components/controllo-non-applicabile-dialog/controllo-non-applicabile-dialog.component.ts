/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ControlliPreErogazioneResponseService } from '../../services/controlli-pre-erogazione-response.service';

@Component({
  selector: 'app-controllo-non-applicabile-dialog',
  templateUrl: './controllo-non-applicabile-dialog.component.html',
  styleUrls: ['./controllo-non-applicabile-dialog.component.scss']
})
export class ControlloNonApplicabileDialogComponent implements OnInit {

  public myForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: {
      mot: string,
      idTipoRichiesta: number,
      idProposta: string
    },
    private resService: ControlliPreErogazioneResponseService,
    private dialogRef: MatDialogRef<ControlloNonApplicabileDialogComponent>
  ) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      motivazione: new FormControl(this.data.mot, [Validators.required])
    });
  }

  conferma() {
    let formControls = this.myForm.getRawValue();

    this.myForm.markAllAsTouched();

    if(this.myForm.valid) {
      this.resService.setControlloNonApplicabile(this.data.idProposta, this.data.idTipoRichiesta, formControls.motivazione).subscribe(ris => {
        console.log("chiamata terminata");
        console.log(ris);
        if (ris) {
          let objJson = {
            "res": ris,
            "mot": formControls.motivazione
          }
          this.dialogRef.close(objJson);
        } else {
          console.log("Errore");
        }

      }, err => {
        console.log("Siamo nella merda");
        console.error(err);
      });
    }

  }

}
