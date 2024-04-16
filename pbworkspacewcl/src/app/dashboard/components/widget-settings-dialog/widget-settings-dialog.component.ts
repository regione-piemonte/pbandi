/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { WidgetDTO } from '../../commons/dto/widget-dto';
import { BandoWidgetDTO } from '../../commons/dto/bando-widget-dto';
import { Observable } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { MatTableDataSource } from '@angular/material/table';
import { DashboardService } from '../../services/dashboard.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-widget-settings-dialog',
  templateUrl: './widget-settings-dialog.component.html',
  styleUrls: ['./widget-settings-dialog.component.scss']
})
export class WidgetSettingsDialogComponent implements OnInit {

  widget: WidgetDTO;
  bandiDaAssociare: Array<BandoWidgetDTO>;
  bandiAssociati: Array<BandoWidgetDTO>;
  bandiDaAssociareFiltrati: Array<BandoWidgetDTO>;
  filteredBandiOptions: Observable<Array<BandoWidgetDTO>>;
  bandoGroup: FormGroup = new FormGroup({ bandoControl: new FormControl() });
  bandoSelected: FormControl = new FormControl();
  dataSource: MatTableDataSource<BandoWidgetDTO> = new MatTableDataSource<BandoWidgetDTO>([]);
  displayedColumns: string[] = ["nomeBandoLinea", "azioni"];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedBandiAssociati: boolean;
  loadedAssocia: boolean = true;

  constructor(
    public dialogRef: MatDialogRef<WidgetSettingsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dashboardService: DashboardService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
    this.widget = this.data.widget;
    this.bandiDaAssociare = this.data.bandiDaAssociare;
    this.loadBandiAssociati();

  }

  loadBandiAssociati() {
    this.loadedBandiAssociati = false;
    this.dashboardService.getBandiAssociati(this.widget.idWidget).subscribe(res => {
      if (this.data.bandiAssociati) {
        this.bandiAssociati = this.data.bandiAssociati;
      } else {
        this.bandiAssociati = res;
      }
      this.setBandi();
      this.loadedBandiAssociati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei bandi associati.");
      this.loadedBandiAssociati = true;
    });
  }

  setBandi() {
    this.dataSource = new MatTableDataSource<BandoWidgetDTO>(this.bandiAssociati);
    this.paginator.pageIndex = 0;
    this.paginator.length = this.bandiAssociati?.length || 0;
    this.dataSource.paginator = this.paginator;

    if (this.bandiAssociati?.length > 0) {
      this.bandiDaAssociareFiltrati = this.bandiDaAssociare.filter(b1 => !this.bandiAssociati.find(b2 => b2.progrBandoLineaIntervento === b1.progrBandoLineaIntervento));
    } else {
      this.bandiDaAssociareFiltrati = [...this.bandiDaAssociare];
    }

    this.filteredBandiOptions = this.bandoGroup.controls['bandoControl'].valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' || value == null ? value : value.nomeBandoLinea),
        map(name => name ? this._filterBandi(name) : this.bandiDaAssociareFiltrati.slice())
      );
  }

  private _filterBandi(nomeBandoLinea: string): BandoWidgetDTO[] {
    const filterValue = nomeBandoLinea.toLowerCase();
    return this.bandiDaAssociareFiltrati.filter(option => option.nomeBandoLinea.toLowerCase().includes(filterValue));
  }

  displayFn(bando: BandoWidgetDTO): string {
    return bando?.nomeBandoLinea || '';
  }

  check() {
    setTimeout(() => {
      if (!this.bandoSelected?.value || (this.bandoGroup.controls['bandoControl'] && this.bandoSelected?.value !== this.bandoGroup.controls['bandoControl'].value)) {
        this.bandoGroup.controls['bandoControl'].setValue(null);
        this.bandoSelected = new FormControl();
      }
    }, 200);
  }

  click(event: any) {
    this.bandoSelected.setValue(event.option.value);
  }

  associa() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssocia = false;
    this.dashboardService.associaBandoAWidget(this.widget.idWidget, this.bandoSelected.value.progrBandoLineaIntervento).subscribe(data => {
      if (data?.esito) {
        this.bandoGroup.controls['bandoControl'].setValue(null);
        this.bandoSelected = new FormControl();
        this.loadBandiAssociati();
        this.showMessageSuccess("Bando associato con successo.");
      } else {
        this.showMessageError("Errore in fase di associazione del bando.");
      }
      this.loadedAssocia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di associazione del bando.");
      this.loadedAssocia = true;
    });
  }

  elimina(bando: BandoWidgetDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedAssocia = false;
    this.dashboardService.disassociaBandoAWidget(bando.idBandoLinSoggWidget).subscribe(data => {
      if (data?.esito) {
        this.loadBandiAssociati();
        this.showMessageSuccess("Bando disassociato con successo.");
      } else {
        this.showMessageError("Errore in fase di disassociazione del bando.");
      }
      this.loadedAssocia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di disassociazione del bando.");
      this.loadedAssocia = true;
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedBandiAssociati || !this.loadedAssocia) {
      return true;
    }
    return false;
  }
}
