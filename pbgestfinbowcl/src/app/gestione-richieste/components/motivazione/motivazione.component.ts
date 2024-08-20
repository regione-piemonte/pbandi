/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-motivazione',
  templateUrl: './motivazione.component.html',
  styleUrls: ['./motivazione.component.scss']
})
export class MotivazioneComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Motivazione,
    public dialogRef: MatDialogRef<MotivazioneComponent>,
    private fb: FormBuilder) { }

  public myForm: FormGroup;

  ngOnInit() {
    this.myForm = this.fb.group({
      motivazione: new FormControl({ value: "", disabled: false },  [Validators.required])
    });
  }

  conferma(){
    this.data.motivazione = this.myForm.controls.motivazione.value;
    this.dialogRef.close({
      motivazione : this.data.motivazione,
    });
  }
}

export class Motivazione {
  motivazione: any;
}