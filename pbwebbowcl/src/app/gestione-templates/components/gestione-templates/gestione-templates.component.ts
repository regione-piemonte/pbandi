/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { UserInfoSec } from '@pbandi/common-lib';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { TemplateDTO } from 'src/app/configurazione-bando/commons/vo/template-DTO';
import { ModelliDocumentoService } from 'src/app/configurazione-bando/services/modelli-documento.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BandoLinea } from '../../commons/dto/bando-linea';
import { GestioneTemplatesService } from '../../services/gestione-templates.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-gestione-templates',
  templateUrl: './gestione-templates.component.html',
  styleUrls: ['./gestione-templates.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneTemplatesComponent implements OnInit {

  user: UserInfoSec;

  bandi: Array<BandoLinea>;
  bandoSelected: FormControl = new FormControl();
  bandoGroup: FormGroup = new FormGroup({ bandoControl: new FormControl() });
  filteredOptions: Observable<BandoLinea[]>;

  tipiDocumento: Array<Decodifica>;
  tipoDocumentoSelected: Decodifica;

  criteriRicercaOpened: boolean = true;
  showElenco: boolean;

  templates: Array<TemplateDTO>;
  dataSource: MatTableDataSource<TemplateDTO> = new MatTableDataSource<TemplateDTO>([]);
  displayedColumns: string[] = ['bando', 'tipo', 'data', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedBandi: boolean;
  loadedTipiDoc: boolean;
  loadedGetNomeDocumento: boolean = true;
  loadedDownloadDocumento: boolean = true;
  loadedGeneraDocumento: boolean = true;
  loadedCerca: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private gestioneTemplatesService: GestioneTemplatesService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private modelliDocumentoService: ModelliDocumentoService
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.loadData();
      }
    });
  }

  loadData() {
    this.loadedBandi = false;
    this.subscribers.findBandiLinea = this.gestioneTemplatesService.findBandiLinea().subscribe(data => {
      if (data) {
        this.bandi = data;
        this.filteredOptions = this.bandoGroup.controls['bandoControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.descrizione),
            map(name => name ? this._filter(name) : (this.bandi ? this.bandi.slice() : null))
          );
      }
      this.loadedBandi = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei bandolinea.");
      this.loadedBandi = true;
    });
    this.loadedTipiDoc = false;
    this.subscribers.findTipiDocumento = this.gestioneTemplatesService.findTipiDocumento().subscribe(data => {
      if (data) {
        this.tipiDocumento = data;
      }
      this.loadedTipiDoc = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei tipi documento.");
      this.loadedTipiDoc = true;
    });
  }

  private _filter(nomeBandoLinea: string): BandoLinea[] {
    const filterValue = nomeBandoLinea.toLowerCase();
    return this.bandi.filter(option => option.nomeBandoLinea.toLowerCase().includes(filterValue));
  }

  displayFn(element: BandoLinea): string {
    return element && element.nomeBandoLinea ? element.nomeBandoLinea : '';
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  check() {
    setTimeout(() => {
      if (!this.bandoSelected || (this.bandoGroup.controls['bandoControl'] && this.bandoSelected.value !== this.bandoGroup.controls['bandoControl'].value)) {
        this.bandoGroup.controls['bandoControl'].setValue(null);
        this.bandoSelected = new FormControl();
      }

    }, 200);
  }

  click(event: any) {
    // this.bandoSelected.setValue(event.option.value);
    this.bandoSelected.setValue(event.option.value);
    //this.bando = this.bandoGroup.controls['bandoControl'].value;
  }

  ricerca() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if ((!this.bandoSelected || !this.bandoSelected.value) && !this.tipoDocumentoSelected) {
      this.showMessageError("Selezionare almeno un bandolinea o un tipo documento.");
      return;
    }
    this.loadedCerca = false;
    this.subscribers.findModelli = this.gestioneTemplatesService.findModelli(this.bandoSelected && this.bandoSelected.value ? this.bandoSelected.value.progrBandoLineaIntervento : null,
      this.tipoDocumentoSelected ? this.tipoDocumentoSelected.id : null).subscribe(data => {
        if (data) {
          this.templates = data;
          this.dataSource = new MatTableDataSource<TemplateDTO>(this.templates);
          this.paginator.length = this.templates.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
          this.criteriRicercaOpenClose();
        }
        this.showElenco = true;
        this.loadedCerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ricerca.");
        this.loadedCerca = true;
      });
  }

  anteprima() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!this.bandoSelected || !this.bandoSelected.value || !this.tipoDocumentoSelected) {
      this.showMessageError("Selezionare un bandolinea e un tipo documento.");
      return;
    }
    this.loadedDownloadDocumento = false;
    this.subscribers.downlaod = this.gestioneTemplatesService.anteprimaTemplate(this.bandoSelected && this.bandoSelected.value ? this.bandoSelected.value.progrBandoLineaIntervento : null,
      this.tipoDocumentoSelected ? this.tipoDocumentoSelected.id : null).subscribe(res => {
        let nomeFile = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nomeFile);
        this.loadedDownloadDocumento = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download dell'anteprima.");
        this.loadedDownloadDocumento = true;
      });
  }

  downloadModelloDocumentoAssociato(template: TemplateDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedGetNomeDocumento = false;
    this.subscribers.getNomeDocumento = this.modelliDocumentoService.getNomeDocumento(this.user, template.progrBandolineaIntervento.toString(),
      template.idTipoDocumentoIndex.toString()).subscribe(nomeFile => {
        this.loadedDownloadDocumento = false;
        this.subscribers.downloadDocumento = this.modelliDocumentoService.downloadDocumento(this.user, template.idTipoDocumentoIndex.toString(),
          template.progrBandolineaIntervento.toString()).subscribe(data => {
            if (data) {
              saveAs(data, nomeFile);
            }
            this.loadedDownloadDocumento = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di download del modello di documento associato.");
            this.loadedDownloadDocumento = true;
          });
        this.loadedGetNomeDocumento = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del modello di documento associato");
        this.loadedGetNomeDocumento = true;
      });
  }

  generaTemplates() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if ((!this.bandoSelected || !this.bandoSelected.value) && !this.tipoDocumentoSelected) {
      this.showMessageError("Selezionare almeno un bandolinea o un tipo documento.");
      return;
    }
    this.loadedGeneraDocumento = false;
    this.subscribers.generaDocumento = this.modelliDocumentoService.generaDocumento(this.user,
      this.bandoSelected && this.bandoSelected.value ? this.bandoSelected.value.progrBandoLineaIntervento : null,
      this.tipoDocumentoSelected ? this.tipoDocumentoSelected.id.toString() : null).subscribe(data => {
        this.showMessageSuccess("Templates generati con successo.");
        this.loadedGeneraDocumento = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di generazione templates.");
        this.loadedGeneraDocumento = true;
      })
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
    if (!this.loadedCerca || !this.loadedBandi || !this.loadedTipiDoc || !this.loadedDownloadDocumento
      || !this.loadedGeneraDocumento || !this.loadedGetNomeDocumento) {
      return true;
    }
    return false;
  }

}
