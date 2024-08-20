/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { HandleExceptionService } from '@pbandi/common-lib';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { AltriDatiDsan } from '../../commons/dto/altri-dati-dsan';
import { AnagraficaBeneficiarioService } from '../../services/anagrafica-beneficiario.service';
import { ArchivioFileService } from '@pbandi/common-lib/';
import { ConfigService } from 'src/app/core/services/config.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-dsan-domanda',
  templateUrl: './dsan-domanda.component.html',
  styleUrls: ['./dsan-domanda.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class DsanDomandaComponent implements OnInit {

  idSoggetto: string;
  numeroDomanda: string;

  displayedColumns: string[] = ['numDomanda', "dataEmissione", "dataScadenza", 'nomeDocumento','action'];
  dataSource: MatTableDataSource<AltriDatiDsan> = new MatTableDataSource<AltriDatiDsan>([]);

  messageError: string;
  isMessageErrorVisible: boolean;

  loadedElenco: boolean = true;

  public subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private anagraficaBeneficiarioService: AnagraficaBeneficiarioService,
    private handleExceptionService: HandleExceptionService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
  ) { }

  ngOnInit(): void {
    this.numeroDomanda = this.activatedRoute.snapshot.queryParamMap.get('numeroDomanda');
    this.idSoggetto = this.activatedRoute.snapshot.queryParamMap.get('idSoggetto');
    this.loadedElenco = false;
    this.anagraficaBeneficiarioService.getDsanDomanda(this.idSoggetto, this.numeroDomanda).subscribe(data => {
      if (data && data.length && data[0].dataEmissioneDsan) {
        this.dataSource = new MatTableDataSource<AltriDatiDsan>(data);
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
  downloadAllegato( nomeDocumento, idDocumentoIndex) {
    //this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      //  this.loadedDownload = true;
    });
  }

}
