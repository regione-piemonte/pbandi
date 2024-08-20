/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AnagraficaBeneficiarioSec } from '../../commons/dto/anagrafica-beneficiario';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { Constants } from '../../../core/commons/util/constants';
import { AnagraficaBeneFisico } from '../../commons/dto/anagraficaBeneFisico';
import { ElencoDomandeProgettiSec } from '../../commons/dto/elenco-domande-progetti';
import { BloccoVO } from '../../commons/dto/bloccoVO';
import { statoDomanda } from '../../commons/dto/statoDomanda';
//import { AltriDati_DimensioneImpresa } from '../../commons/dto/altriDati_dimensioneImpresa';
import { AnagAltriDati_Main } from '../../commons/dto/anagAltriDati_Main';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DettaglioImpresa } from '../../commons/dettaglioImpresa';
import { ConditionalExpr } from '@angular/compiler';

@Component({
  selector: 'app-storico-blocchi-attivi',
  templateUrl: './storico-blocchi-attivi.component.html',
  styleUrls: ['./storico-blocchi-attivi.component.scss']
})
export class StoricoBlocchiAttiviComponent implements OnInit {

  public myForm: FormGroup;

  idOp = Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA;

  beneficiario: AnagraficaBeneficiarioSec;
  beneficiarioFisico: AnagraficaBeneFisico;
  elenco: ElencoDomandeProgettiSec;
  elenco2: BloccoVO;
  altriDati: AnagAltriDati_Main;
  statoUltimaDomanda: statoDomanda;
  idSoggetto: any;
  idEnteGiuridico: any;
  isEnteGiuridico: boolean;
  verificaTipoSoggetto: any;
  spinner: boolean;
  getConcluse: number = 0;
  numeroDomanda: any;

  dataSource: any;
  dataSourceAltriDati_DimImpr: any;
  dataSourceAltriDati_Durc: any;
  dataSourceAltriDati_Bdna: any;
  dataSourceAltriDati_AntiMafia: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumnsBlocchi: string[] = ['dataBlocco', 'causale', 'dataSblocco', 'istruttore'];

  public subscribers: any = {};

  listaStoricoBlcocchi: Array<BloccoVO> = new Array<BloccoVO>();
  isBlocchi: boolean;

  constructor(
    private anagBeneService: AnagraficaBeneficiarioService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private router: Router,
    private sharedService: SharedService,
    public dialogRef: MatDialogRef<StoricoBlocchiAttiviComponent>,
  ) { }

  ngOnInit(): void {
    //this.spinner = true;
    this.verificaTipoSoggetto = this.route.snapshot.queryParamMap.get('tipoSoggetto');
    this.idEnteGiuridico = this.route.snapshot.queryParamMap.get('idTipoSoggetto');
    this.idSoggetto = this.route.snapshot.queryParamMap.get('idSoggetto');
    this.getStoricoBlocchi();
  }

  getStoricoBlocchi() {
    this.subscribers.getStoricoBlocchi = this.anagBeneService.getStoricoBlocchi(this.idSoggetto).subscribe(data => {
      if (data.length > 0) {
        this.listaStoricoBlcocchi = data;
        this.isBlocchi = true;
        console.log(this.listaStoricoBlcocchi)
      }
    }, err => {
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }

}