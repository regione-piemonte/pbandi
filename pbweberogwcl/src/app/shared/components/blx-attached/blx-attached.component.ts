/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, SimpleChanges, OnInit, ViewChild, Input, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { animate, state, style, transition, trigger } from '@angular/animations';
//import * as EventEmitter from 'events';

@Component({
  selector: 'blx-attached',
  templateUrl: './blx-attached.component.html',
  styleUrls: ['./blx-attached.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush,
  // animations: [
  //   trigger('detailExpand', [
  //     state('collapsed', style({ height: '0px', minHeight: '0' })),
  //     state('expanded', style({ height: '*' })),
  //     transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
  //   ]),
  // ],
})
export class BlxAttachedComponent implements OnInit {

  default: number; // test
  @Input() attachedId: string = 'Test';
  @Input() disabledSfoglia: boolean;
  @Input('disabledAnnulla') disabledAnnullas: boolean;
  @Input('disabledAllegatiSubmit') disabledAllegatiSubmits: boolean;
  @Output() fileList = new EventEmitter<FileList>();
  @Output() arrayFile = new EventEmitter<Array<File>>();
  @Output() onVisualizza = new EventEmitter<any>();
  // @Output() onAssocia = new EventEmitter<any>();
  // @Output() onClona = new EventEmitter<any>();
  @Output() onModifica = new EventEmitter<any>();
  @Output() onElimina = new EventEmitter<any>();
  @Output() onSfoglia = new EventEmitter<any>();
  @Output() onAnnulla = new EventEmitter<any>();
  @Output() onAggiungi = new EventEmitter<any>();

  file: File;
  files: FileList;
  arrayFiles: Array<File>;
  allegati: Array<any>; // DocumentoCertificazioneDTO
  isAllegaFile: boolean = true;
  idAllegatiChecked: Array<number> = new Array<number>();
  allChecked: boolean;
  @ViewChild('uploadFile') attachment: any;

  //MESSAGGI SUCCESS E ERROR
  messageSuccessAllegati: string;
  isMessageSuccessAllegatiVisible: boolean;
  messageAllegatiError: string;
  isMessageErrorAllegatiVisible: boolean;

  defaultValues: any = {
    tooltip: {
      visualizza: 'Visualizza',
      // associa: 'Associa',
      // clona: 'Clona',
      modifica: 'Modifica',
      elimina: 'Elimina'
    }
  }
  
  //dataSource: MatTableDataSource<any>;  // dataSourceS

  constructor(
    private cd: ChangeDetectorRef,
  ) {
    this.default = 200; // test
    console.log('blx-attached - default - constructor', this.default); // test
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log('Blx - ngOnChanges - changes', changes);
    if (changes.collapsed) {
      if (changes.collapsed.currentValue) {
        //
      } else {
        //
      }
    }
  }

  ngOnInit() {
    console.log('blx-attached - default - ngOnInit', this.default); // test
    this.log();
    //this.cd.detectChanges();
  }

  log() {
    console.log('---');
  }

  uploadFiles(e: any) { // file: File // $event.target.files
    this.file = e.target.files[0];
    this.files = e.target.files;
    this.arrayFiles = [];
    for (var i = 0; i <= e.target.files.length - 1; i++) {
      var selectedFile = e.target.files[i];
      //this.files.push(selectedFile);
      this.arrayFiles.push(selectedFile);
    }
    //this.attachment.nativeElement.value = '';
    //(document.getElementById('uploadFile') as HTMLInputElement).value = '';
    this.fileList.emit(this.files);
    this.arrayFile.emit(this.arrayFiles);
  }

  onEliminaFilePreOk(index: number) {
    this.onElimina.emit(index);
  }

  onEliminaFile(index: number) {
    //let index = this.files.indexOf(file);
    //this.files.splice(index, 1);
    // this.files = () => {
    //   let files: Array<File> = [];
    //   this.files.forEach((f, i) => {
    //     // .splice(i, 1)
    //   });
    //   return files;
    // };
    this.arrayFiles.splice(index, 1);
    this.onElimina.emit(index);
  }

  onAnnullaClick() {
    this.file = undefined;
    this.files = undefined;
    this.arrayFiles = undefined;
    this.arrayFiles = [];
    this.fileList.emit(this.files);
    this.arrayFile.emit(this.arrayFiles);
    this.onAnnulla.emit(true);
  }

  aggiungiAllegato() {
    this.resetMessageAllegatiSuccess();
    this.resetMessageAllegatiError();
    // this.loadedAggiungi = false;
    // this.subscribers.allega = this.erogazioneService.allegaFileProposta(this.idProgetto, null, this.file, Constants.DESCR_BREVE_TASK_RICHIESTA_EROGAZIONE_PRIMO_ACCONTO).subscribe(data => {
    //   if (data) {
    //     if (data.esito) {
    //       //this.allChecked = false;
    //       this.file = null;
    //       this.showMessageAllegatiSuccess(data.msg);
    //       this.getAllegati();
    //     } else {
    //       this.showMessageError(data.msg);
    //     }
    //   }
    //   this.loadedAggiungi = true;
    // }, err => {
    //   this.handleExceptionService.handleNotBlockingError(err);
    //   this.showMessageError("Errore nel salvataggio dell'allegato.");
    //   this.loadedAggiungi = true;
    // });
    this.onAggiungi.emit(true);
  }

  getAllegati() {
    // this.loadedAllegati = false;
    // let s = new Array<string>();
    // s.push(Constants.DESCR_BREVE_TASK_RICHIESTA_EROGAZIONE_PRIMO_ACCONTO);
    // this.subscribers.allegati = this.erogazioneService.getAllegatiPropostaCertificazione(new PropostaCertificazioneAllegatiRequest(this.idProgetto, s)).subscribe(data => {
    //   if (data) {
    //     this.allegati = data;
    //     if (this.allegati && this.allegati.length > 0) {
    //       this.isAllegaFile = false;
    //     }
    //     this.allegati.forEach(a => a.checked = false);
    //     this.idAllegatiChecked = new Array<number>();
    //     this.dataSource = new MatTableDataSource<any>(this.allegati); // DocumentoCertificazioneDTO
    //   }
    //   this.loadedAllegati = true;
    // }, err => {
    //   this.handleExceptionService.handleBlockingError(err);
    // });
  }

  disabledAnnulla() {
    let b: boolean;
    if (this.disabledAnnullas !== undefined) {
      b = this.disabledAnnullas;
    } else {
      b = !this.arrayFiles || !(this.arrayFiles.length > 0);
    }
    return b;
  }

  disabledAllegatiSubmit() {
    let b: boolean;
    if (this.disabledAllegatiSubmits !== undefined) {
      b = this.disabledAllegatiSubmits;
    } else {
      b = !this.arrayFiles || !(this.arrayFiles.length > 0);
    }
    return b;
  }

  resetMessageAllegatiSuccess() {
    this.messageSuccessAllegati = null;
    this.isMessageSuccessAllegatiVisible = false;
  }

  resetMessageAllegatiError() {
    this.messageAllegatiError = null;
    this.isMessageErrorAllegatiVisible = false;
  }

}
