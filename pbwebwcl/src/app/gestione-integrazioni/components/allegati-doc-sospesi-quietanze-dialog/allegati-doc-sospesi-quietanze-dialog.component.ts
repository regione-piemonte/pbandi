import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AllegatiDocSpesaQuietanzeDTO } from '../../commons/dto/allegati-doc-spesa-quietanze-dto';
import { MatTableDataSource } from '@angular/material/table';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { AllegatiQuietanzeDTO } from '../../commons/dto/allegati-quietanze-dto';

@Component({
  selector: 'app-allegati-doc-sospesi-quietanze-dialog',
  templateUrl: './allegati-doc-sospesi-quietanze-dialog.component.html',
  styleUrls: ['./allegati-doc-sospesi-quietanze-dialog.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class AllegatiDocSospesiQuietanzeDialogComponent implements OnInit {

  allegati: Array<AllegatiDocSpesaQuietanzeDTO>;
  dataSourceDocumenti: MatTableDataSource<AllegatiDocSpesaQuietanzeDTO> = new MatTableDataSource<AllegatiDocSpesaQuietanzeDTO>([]);
  dataSourceQuietanze: MatTableDataSource<AllegatiQuietanzeDTO> = new MatTableDataSource<AllegatiQuietanzeDTO>([]);
  displayedColumns: string[] = ['documento', 'fornitore', 'importo', 'notaIstruttore', 'expand'];
  displayedColumnsQ = ['modalita', 'data', 'importo', 'expand2'];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,) { }

  ngOnInit(): void {
    this.allegati = this.data.allegati;
    this.dataSourceDocumenti = new MatTableDataSource<AllegatiDocSpesaQuietanzeDTO>(this.allegati);
  }

  onExpandDocumento(doc: AllegatiDocSpesaQuietanzeDTO) {
    this.dataSourceQuietanze = new MatTableDataSource<AllegatiQuietanzeDTO>(doc.allegatiQuietanze);
  }
}
