import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { NuovoDocumentoDiSpesaDTO } from '../../commons/dto/nuovo-documento-di-spesa-dto';
import { AnteprimaDialogComponent, ArchivioFileService, InfoDocumentoVo } from '@pbandi/common-lib';
import { MatTableDataSource } from '@angular/material/table';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { MatDialog } from '@angular/material/dialog';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-allegati-dettaglio-doc',
  templateUrl: './allegati-dettaglio-doc.component.html',
  styleUrls: ['./allegati-dettaglio-doc.component.scss']
})
export class AllegatiDettaglioDocComponent implements OnInit, OnChanges {

  @Input("docSpesa") docSpesa: NuovoDocumentoDiSpesaDTO;
  @Input("isVisible") isVisible: boolean;
  @Input("isFP") isFP: boolean;

  allegati: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  quietanze: Array<any>;

  @Output("messageError") messageError = new EventEmitter<string>();
  @Output("resetMessageError") resetMessageError = new EventEmitter<boolean>();


  //LOADED
  loadedDownload: boolean = true;

  constructor(
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private archivioFileService: ArchivioFileService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes?.docSpesa?.currentValue?.idDocumentoDiSpesa) {
      this.quietanze = this.docSpesa.rigaValidazioneItem ? this.docSpesa.rigaValidazioneItem.filter(r => r.rigaPagamento) : null;
      this.quietanze.forEach(q => {
        q.allegati = this.docSpesa.documentoAllegatoPagamento ? this.docSpesa.documentoAllegatoPagamento.filter(d => d.idPagamento === q.idPagamento) : null;
      });
      //salvo in this.allegati l'elenco di allegati per l'anteprima, utilizzo il tipo per distinguere i 3 casi
      //D=allegati doc spesa, Q=allegati quietanze, F=allegati fornitore
      if (this.docSpesa.documentoAllegato && this.docSpesa.documentoAllegato.length) {
        /* for (let i = 0; i < this.docSpesa.length; ++i) {
          this.allegati.push(...this.docSpesa[i].documentoAllegato.map(d => new InfoDocumentoVo("D", d.nome, null, null, null, null, null, d.id.toString(), null)));
        } */
        //   this.allegati.push(...this.docSpesa.documentoAllegato.map(d => new InfoDocumentoVo("D", d.nome, null, null, null, null, null, d.id.toString(), null)));
      }
      // Controllo se esistono file trasmessi in integrazione per le quietanze

      /* if (this.quietanze && this.quietanze.length) {
        this.quietanze.forEach(q => {
          if (q.allegati && q.allegati.length) {
            this.allegati.push(...q.allegati.map(d => new InfoDocumentoVo("Q" + q.idPagamento, d.nomeFile, null, null, null, null, null, d.id.toString(), null)));
          }
        });
      } */
      if (this.docSpesa.documentoAllegatoFornitore && this.docSpesa.documentoAllegatoFornitore.length) {
        this.allegati.push(...this.docSpesa.documentoAllegatoFornitore.map(d => new InfoDocumentoVo("F", d.nome, null, null, null, null, null, d.id.toString(), null)));
      }
    }
  }

  isAnteprimaVisible(nomeFile: string) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
    var splitted = nomeFile.split(".");
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
      return true;
    } else {
      return false;
    }
  }

  anteprimaAllegato(nomeFile: string, idDocumentoIndex: string, tipo: string) {
    this.resetMessages();
    let comodo = new Array<any>();
    comodo.push(nomeFile);
    comodo.push(idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>(this.allegati));
    comodo.push(this.configService.getApiURL());
    comodo.push(tipo);

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });
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
