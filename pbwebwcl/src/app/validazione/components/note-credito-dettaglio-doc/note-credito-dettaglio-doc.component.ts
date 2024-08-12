import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { RigaNotaDiCreditoItemDTO } from '../../commons/dto/riga-nota-di-credito-item-dto';
import { NuovoDocumentoDiSpesaDTO } from '../../commons/dto/nuovo-documento-di-spesa-dto';
import { ArchivioFileService } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-note-credito-dettaglio-doc',
  templateUrl: './note-credito-dettaglio-doc.component.html',
  styleUrls: ['./note-credito-dettaglio-doc.component.scss']
})
export class NoteCreditoDettaglioDocComponent implements OnInit, OnChanges {

  @Input("docSpesa") docSpesa: NuovoDocumentoDiSpesaDTO;

  noteDiCredito: Array<RigaNotaDiCreditoItemDTO>;

  dataSourceNoteCredito: MatTableDataSource<RigaNotaDiCreditoItemDTO>;
  displayedColumnsNoteCredito: string[] = ['numero', 'importo', 'data'];

  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("resetMessageError") resetMessageError = new EventEmitter<boolean>();

  //LOADED
  loadedDownload: boolean = true;

  constructor(
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes?.docSpesa?.currentValue?.idDocumentoDiSpesa) {
      this.noteDiCredito = this.docSpesa.noteDiCredito ? this.docSpesa.noteDiCredito.filter(n => n.rigaNotaDiCredito) : null;
      this.dataSourceNoteCredito = new MatTableDataSource<RigaNotaDiCreditoItemDTO>(this.noteDiCredito);
      if (this.docSpesa.tipoInvio === 'D') {
        this.displayedColumnsNoteCredito.push('allegati');
      }
    }
  }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessages();
    this.loadedDownload = false;
    this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  showMessageError(msg: string) {
    this.messageError.emit(msg);
  }

  resetMessages() {
    this.resetMessageError.emit(true);
  }

  isLoading() {
    if (!this.loadedDownload) {
      return true;
    }
    return false;
  }

}
