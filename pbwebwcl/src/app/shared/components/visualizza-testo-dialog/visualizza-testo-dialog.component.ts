import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-visualizza-testo-dialog',
  templateUrl: './visualizza-testo-dialog.component.html',
  styleUrls: ['./visualizza-testo-dialog.component.scss']
})
export class VisualizzaTestoDialogComponent implements OnInit {

  title: string;
  text: string;

  constructor(
    public dialogRef: MatDialogRef<VisualizzaTestoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.title = this.data.title;
    this.text = this.data.text;
  }

  close() {
    this.dialogRef.close();
  }

}
