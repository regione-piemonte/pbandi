/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { DatiDimensioneImpresaVO } from '../../commons/dto/data-dimensione-impresa-vo';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';

@Component({
  selector: 'app-dettaglio-dati-dimensione',
  templateUrl: './dettaglio-dati-dimensione.component.html',
  styleUrls: ['./dettaglio-dati-dimensione.component.scss']
})
export class DettaglioDatiDimensioneComponent implements OnInit {
  messageError: string;
  isMessageErrorVisible: boolean;
  idEnteGiuridico: any;
  constructor(public dialogRef: MatDialogRef<DettaglioDatiDimensioneComponent>,
    private route: ActivatedRoute,
    private anagraficaBeneficiarioService: AnagraficaBeneficiarioService,
    private handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }
  displayedColumns: string[] = ['anno', 'ula', 'fatturato', 'bilancio'];
  numeroDomanda: any;
  idSoggetto: any;

  dataSource: MatTableDataSource<DatiDimensioneImpresaVO> = new MatTableDataSource<DatiDimensioneImpresaVO>([]);

  ngOnInit(): void {

    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.numeroDomanda = this.route.snapshot.queryParamMap.get("idDomanda");
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idEnteGiuridico');
    
    console.log("@@@@ id ente giuridico ");
    console.log(this.numeroDomanda + " " + this.idSoggetto, +" "+ this.idEnteGiuridico);

    this.anagraficaBeneficiarioService.getDettaglioDimImpresaSoggetto(this.idEnteGiuridico).subscribe(data => {
      if (data && data.length) {
        this.dataSource = new MatTableDataSource<any>(data);
      }
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dell'elenco.")
      //this.loadedElenco = true;
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }
}

// export class DettaglioImpresa {
//   colonne: string[];
//   dettaglio: any;
// }
