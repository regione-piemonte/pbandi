/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AnteprimaDialogComponent, ArchivioFileService, InfoDocumentoVo } from '@pbandi/common-lib';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { CodiceDescrizioneDTO } from 'src/app/associazione-istruttore-progetti/commons/dto/codice-descrizione-dto';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { BoStorageDocumentoIndexDTO } from '../../commons/dto/bo-storage-documento-index-dto';
import { ProgettoSuggestVO } from '../../commons/dto/progetto-suggest-vo';
import { BoStorageService } from '../../services/bo-storage.service';
import { saveAs } from 'file-saver-es';
import { MatDialog } from '@angular/material/dialog';
import { DepFlags } from '@angular/compiler/src/core';

@Component({
  selector: 'app-bo-storage',
  templateUrl: './bo-storage.component.html',
  styleUrls: ['./bo-storage.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class BoStorageComponent implements OnInit {

  user: UserInfoSec;
  nomeFile: string;

  isResultVisible: boolean;

  tipiDocumento: Array<CodiceDescrizioneDTO>;
  tipoDocSelected: FormControl = new FormControl();
  tipiDocGroup: FormGroup = new FormGroup({ tipoDocControl: new FormControl() });
  filteredOptionsTipiDoc$: Observable<CodiceDescrizioneDTO[]>;

  progetti: Array<ProgettoSuggestVO>;
  progSelected: FormControl = new FormControl();
  progGroup: FormGroup = new FormGroup({ progControl: new FormControl() });
  filteredOptionsProg$: Observable<ProgettoSuggestVO[]>;

  criteriRicercaOpened: boolean = true;

  documenti: Array<BoStorageDocumentoIndexDTO>;
  dataSource: MatTableDataSource<BoStorageDocumentoIndexDTO> = new MatTableDataSource<BoStorageDocumentoIndexDTO>([]);
  displayedColumns: string[] = ['info', 'nomeFile', 'descrizioneTipoDocIndex', 'codiceVisualizzatoProgetto', 'azioni'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedTipiDocumento: boolean;
  loadedRicerca: boolean = true;
  loadedDownload: boolean = true;

  subscribers: any = {};

  constructor(
    private boStorageService: BoStorageService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      this.user = data;
      if (data) {
        this.loadedTipiDocumento = false;
        this.boStorageService.getTipiDocIndex().subscribe(data => {
          if (data) {
            this.tipiDocumento = data.filter(t => t.descrizioneBreve !== Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTA);
            this.filteredOptionsTipiDoc$ = this.tipiDocGroup.controls['tipoDocControl'].valueChanges
              .pipe(
                startWith(''),
                map(value => typeof value === 'string' || value == null ? value : value.descrizione),
                map(descrizione => descrizione ? this._filterTipiDoc(descrizione) : this.tipiDocumento?.slice())
              );
          }
          this.loadedTipiDocumento = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di caricamento dei tipi documento.");
          this.loadedTipiDocumento = true;
        });

      }
    });

  }

  getProgettiByDesc(descrizione: string) {
    if (descrizione?.length >= 3) {
      this.progetti = [new ProgettoSuggestVO(null, "Caricamento...")];
      this.boStorageService.getProgettiByDesc(descrizione).subscribe(data => {
        console.log(data);
        this.progetti = data;
        this.filteredOptionsProg$ = this.progGroup.controls['progControl'].valueChanges
          .pipe(
            startWith(''),
            map(value => typeof value === 'string' || value == null ? value : value.codiceVisualizzato),
            map(codiceVisualizzato => codiceVisualizzato ? this._filterProg(codiceVisualizzato) : this.progetti?.slice())
          );
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetti.");
      });
    }
  }

  private _filterTipiDoc(desc: string): CodiceDescrizioneDTO[] {
    const filterValue = desc.toLowerCase();
    return this.tipiDocumento.filter(option => option.descrizione.toLowerCase().includes(filterValue));
  }

  private _filterProg(desc: string): ProgettoSuggestVO[] {
    const filterValue = desc.toLowerCase();
    return this.progetti.filter(option => option.codiceVisualizzato.toLowerCase().includes(filterValue));
  }

  displayFnTipiDoc(element: CodiceDescrizioneDTO): string {
    return element && element.descrizione ? element.descrizione : '';
  }

  displayFnProg(element: ProgettoSuggestVO): string {
    return element && element.codiceVisualizzato ? element.codiceVisualizzato : '';
  }

  check(type: string) {
    setTimeout(() => {
      switch (type) {
        case 'T':
          if (!this.tipoDocSelected || (this.tipiDocGroup.controls['tipoDocControl'] && this.tipoDocSelected.value !== this.tipiDocGroup.controls['tipoDocControl'].value)) {
            this.tipiDocGroup.controls['tipoDocControl'].setValue(null);
            this.tipoDocSelected = new FormControl();
          }
          break;
        case 'P':
          if (!this.progSelected || (this.progGroup.controls['progControl'] && this.progSelected.value !== this.progGroup.controls['progControl'].value)) {
            this.progetti = [];
            this.progGroup.controls['progControl'].setValue(null);
            this.progSelected = new FormControl();
          }
          break;
        default:
          break;
      }
    }, 200);
  }

  click(event: any, type: string) {
    switch (type) {
      case 'T':
        this.tipoDocSelected.setValue(event.option.value);
        break;
      case 'P':
        this.progSelected.setValue(event.option.value);
        break;
      default:
        break;
    }
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  ricerca() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!this.nomeFile?.length && !this.tipoDocSelected?.value?.id && !this.progSelected?.value?.idProgetto) {
      this.showMessageError("Inserire almeno uno dei criteri di ricerca.");
      return;
    }
    this.loadedRicerca = false;
    this.boStorageService.ricercaDocumenti(this.nomeFile, this.tipoDocSelected?.value?.id, this.progSelected?.value?.idProgetto).subscribe(data => {
      this.documenti = data;
      this.dataSource = new MatTableDataSource<BoStorageDocumentoIndexDTO>(data);
      this.paginator.pageIndex = 0;
      this.paginator.length = data?.length || 0;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.isResultVisible = true;
      this.loadedRicerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca.");
      this.loadedRicerca = true;
    });
  }

  downloadAllegato(doc: BoStorageDocumentoIndexDTO) {
    this.resetMessageSuccess();
    this.resetMessageError();
    if (!doc.flagFirmato && !doc.flagMarcato) {
      this.loadedDownload = false;
      this.subscribers.downlaod = this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), doc.idDocumentoIndex.toString()).subscribe(res => {
        if (res) {
          let nome = res.headers.get("header-nome-file");
          saveAs(new Blob([res.body]), nome);
        }
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
    } else if (doc.flagMarcato) {
      this.loadedDownload = false;
      this.subscribers.downlaodFileMarcato = this.archivioFileService.leggiFileMarcato(this.configService.getApiURL(), doc.idDocumentoIndex.toString()).subscribe(res => {
        let nomeFile = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nomeFile);
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
    } else if (doc.flagFirmaAutografa) {
      this.loadedDownload = false;
      this.subscribers.downlaodFileMarcato = this.archivioFileService.leggiFileFirmaAutografa(this.configService.getApiURL(), doc.idDocumentoIndex.toString()).subscribe(res => {
        let nomeFile = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nomeFile);
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
    } else if (doc.flagFirmato) {
      this.loadedDownload = false;
      this.subscribers.downlaodFileMarcato = this.archivioFileService.leggiFileFirmato(this.configService.getApiURL(), doc.idDocumentoIndex.toString()).subscribe(res => {
        let nomeFile = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nomeFile);
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di download del file");
        this.loadedDownload = true;
      });
    }
  }

  anteprimaAllegato(doc: BoStorageDocumentoIndexDTO) {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(doc.nomeFile);
    comodo.push(doc.idDocumentoIndex.toString());
    comodo.push(new MatTableDataSource<InfoDocumentoVo>(this.documenti.map(d => new InfoDocumentoVo(d.descrizioneBreveTipoDocIndex, d.nomeFile, null, null, null, null, null,
      d.idDocumentoIndex.toString(), null))));
    comodo.push(this.configService.getApiURL());
    comodo.push(doc.descrizioneBreveTipoDocIndex);

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
  }

  isAnteprimaVisible(doc: BoStorageDocumentoIndexDTO) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
    // if (doc.descrizioneBreveTipoDocIndex === Constants.DESC_BREVE_TIPO_DOC_INDEX_ACTA) {
    //   return false;
    // }
    if (doc.nomeFile) {
      let splitted = doc.nomeFile.split(".");
      if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
        return true;
      }
    }
    return false;
  }

  onFileSelected(file: File, doc: BoStorageDocumentoIndexDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (file.name !== doc.nomeFile) {
      this.showMessageError("Impossibile caricare il file: il nome deve essere uguale al nome del file che si vuole sostituire.");
      return;
    }
    this.sostituisciAllegato(file, doc);
  }

  sostituisciAllegato(file: File, doc: BoStorageDocumentoIndexDTO) {
    this.loadedDownload = false;
    this.boStorageService.sostituisciDocumento(file, doc.descrizioneBreveTipoDocIndex, doc.idDocumentoIndex, doc.flagFirmato, doc.flagMarcato, doc.flagFirmaAutografa)
      .subscribe(res => {
        if (res) {
          if (res.code === "OK") {
            this.ricerca();
            this.showMessageSuccess(res.message);
          } else {
            this.showMessageError(res.message);
          }
        }
        this.loadedDownload = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di sostituzione del file");
        this.loadedDownload = true;
      });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    let element = document.getElementById('scrollId')
    element.scrollIntoView();
  }

  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    let element = document.getElementById('scrollId')
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedTipiDocumento || !this.loadedRicerca || !this.loadedDownload) {
      return true;
    }
    return false;
  }

}
