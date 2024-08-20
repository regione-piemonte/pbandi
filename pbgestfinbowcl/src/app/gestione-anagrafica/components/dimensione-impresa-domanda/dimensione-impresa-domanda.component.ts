/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { HandleExceptionService } from '@pbandi/common-lib';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { DatiDimensioneImpresaVO } from '../../commons/dto/data-dimensione-impresa-vo';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';

@Component({
  selector: 'app-dimensione-impresa-domanda',
  templateUrl: './dimensione-impresa-domanda.component.html',
  styleUrls: ['./dimensione-impresa-domanda.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DimensioneImpresaDomandaComponent implements OnInit {

  idSoggetto: string;
  numeroDomanda: string;

  displayedColumns: string[] = ['numDomanda', "data", "esito", "azioni"];
  displayedInnerColumns: string[] = ['anno', 'ula', 'fatturato', 'bilancio'];
  dataSource: MatTableDataSource<DatiDimensioneImpresaVO> = new MatTableDataSource<DatiDimensioneImpresaVO>([]);

  messageError: string;
  isMessageErrorVisible: boolean;

  loadedElenco: boolean = true;

  public subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private anagraficaBeneficiarioService: AnagraficaBeneficiarioService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.numeroDomanda = this.activatedRoute.snapshot.queryParamMap.get('numeroDomanda');
    this.idSoggetto = this.activatedRoute.snapshot.queryParamMap.get('idSoggetto');
    this.loadedElenco = false;
    this.anagraficaBeneficiarioService.getDatiImpresaDomanda(this.idSoggetto, this.numeroDomanda).subscribe(data => {
      if (data && data.length && data[0].dataValutazioneEsito && data[0].descDimImpresa) {
        this.dataSource = new MatTableDataSource<any>(data);
      }
      this.loadedElenco = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dell'elenco.")
      this.loadedElenco = true;
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  isLoading() {
    if (!this.loadedElenco) {
      return true;
    }
    return false;
  }
}
