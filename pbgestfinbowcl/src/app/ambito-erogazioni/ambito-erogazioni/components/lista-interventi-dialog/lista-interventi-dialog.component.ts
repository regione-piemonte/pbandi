/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject,OnInit } from '@angular/core';
import { ControlliPreErogazioneResponseService } from '../../services/controlli-pre-erogazione-response.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { InterventoSostitutivoVO } from '../../commons/intervento-sostitutivo-vo';
import { MatTableDataSource } from '@angular/material/table';
import { DestinatarioInterventoSostVO } from '../../commons/destinatario-intervento-sost-vo';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';

@Component({
  selector: 'app-lista-interventi-dialog',
  templateUrl: './lista-interventi-dialog.component.html',
  styleUrls: ['./lista-interventi-dialog.component.scss']
})
export class ListaInterventiDialogComponent implements OnInit {
  listaInterventi: InterventoSostitutivoVO[];
  importoTotale: number;
  importoBeneficiario: number;
  ibanBeneficiario: string;
  distintaCreata: boolean;

  destinatari: DestinatarioInterventoSostVO[] = [];

   //Tabella
   dataSource = new MatTableDataSource<InterventoSostitutivoVO>([]);
   displayedColumns: string[] = ["destinatario", "iban", "importoDaErogare"];

  constructor(
    private resService: ControlliPreErogazioneResponseService,
    private dialogRef: MatDialogRef<ListaInterventiDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      distintaCreata: boolean,
      importoTotale: number,
      ibanBeneficiario: string,
      listaInterventi : InterventoSostitutivoVO[]
    }
  ) { }


  ngOnInit(): void {
    console.log(this.data);
    this.popolaDestinatari();
    this.distintaCreata = this.data.distintaCreata;
    this.importoTotale = this.data.importoTotale;
    this.ibanBeneficiario = this.data.ibanBeneficiario;
    this.listaInterventi = JSON.parse(JSON.stringify(this.data.listaInterventi));
    this.dataSource = new MatTableDataSource(this.listaInterventi);
    this.aggiornaImporti();
  }

  popolaDestinatari() {
    this.resService.getDestinatariIntervento().subscribe(data =>{
      this.destinatari = data;
    })
  }

  displayDestinatario(element: DestinatarioInterventoSostVO): string {
    return element ? element.denominazione : '';
  }

  aggiornaDestinatario(element: InterventoSostitutivoVO, event: MatAutocompleteSelectedEvent) {
    element.idDestinatario = event.option.value.idDestinatario;
    element.denominazione = event.option.value.denominazione;
    element.iban = event.option.value.iban;
  }

  aggiornaImporti() {
    if(this.distintaCreata) {
      this.importoBeneficiario = this.importoTotale
    }else{
      this.importoBeneficiario = this.importoTotale

      this.listaInterventi.forEach(e => this.importoBeneficiario -= e.importoDaErogare);
    }
  }

  importoTotaleProposta() : number {
    let importo: number = this.importoBeneficiario;

    this.listaInterventi.forEach(element => importo = importo + element.importoDaErogare);

    return importo;
  }

  isValid(): boolean {
    return this.importoBeneficiario > 0;
  }

  salva() {
    console.log(this.listaInterventi);

    this.dialogRef.close(this.listaInterventi);
  }

  annulla() {
    this.dialogRef.close();
  }
}
