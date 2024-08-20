/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { StoricoRevocaDTO } from '../../commons/dto/storico-revoca-dto';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-dialog-storico-box',
  templateUrl: './dialog-storico-box.component.html',
  styleUrls: ['./dialog-storico-box.component.scss']
})
export class DialogStoricoBoxComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;

  excelFile: string = 'Excel Note monitoraggio.xlsx';
  idComponentToShow :  number; 
  displayedColumns1: string[] = ['dataInserimento', 'istruttore'];
  displayedColumns2: string[] = ['dataInserimento', 'istruttore', 'Azione'];
  displayedColumns3: string[] = ['dataInserimento', 'istruttore', 'descrizione'];
  displayedColumns: string[] = ['dataInserimento', 'istruttore', 'provenienza', 'nota'];
  ismostra: boolean; 



  constructor(
    public dialogRef: MatDialogRef<DialogStoricoBoxComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
       this.idComponentToShow= this.data.id;
      //  console.log(this.data.storico)  
      //  console.log(this.data.id)  
  }

  closeDialog() { this.dialogRef.close() }

  downloadStorico(){
    let element = document.getElementById('excel');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element,{dateNF:'dd/mm/yyyy',cellDates:true, raw: true});
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Excel');
    XLSX.writeFile(wb, this.excelFile);
  }

}
