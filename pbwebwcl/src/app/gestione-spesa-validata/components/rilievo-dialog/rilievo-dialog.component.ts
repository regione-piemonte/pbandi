/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-rilievo-dialog',
  templateUrl: './rilievo-dialog.component.html',
  styleUrls: ['./rilievo-dialog.component.scss']
})
export class RilievoDialogComponent implements OnInit {

  note: string;
  date: Date;
  readOnly: boolean;

  @ViewChild("rilievoForm", { static: true }) rilievoForm: NgForm;

  constructor(public dialogRef: MatDialogRef<RilievoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.note = this.data?.note || null;
    this.date = this.data?.date || null;
    this.readOnly = this.data?.readOnly || null;
  }

  salvaRilievo() {
    if (!(this.note?.length > 0)) {
      this.rilievoForm.form.markAllAsTouched();
      return;
    }
    //TODO: servizio insert/modifica

    this.dialogRef.close(this.note);
  }

  isLoading() {
    return false;
  }
}
