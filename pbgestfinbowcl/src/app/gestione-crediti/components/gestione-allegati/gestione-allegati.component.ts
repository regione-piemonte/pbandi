/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
//import { AllegatiService } from '../../services/allegati.service';
import { AllegatoVO } from '../../commons/dto/allegatoVO';
import { ConfigService } from 'src/app/core/services/config.service';
import { ArchivioFileService } from '@pbandi/common-lib';
import { MatDialog } from '@angular/material/dialog';
import { saveAs } from 'file-saver-es';
import { Output, EventEmitter } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { GestioneAllegatiVO } from '../../commons/dto/gestione-allegati-VO';

@Component({
  selector: 'app-gestione-allegati',
  templateUrl: './gestione-allegati.component.html',
  styleUrls: ['./gestione-allegati.component.scss']
})
export class GestioneAllegatiComponent implements OnInit {

  /* L'id del componente da cui viene chiamato, corrisponde all'id dal DB */
  @Input() idComponente: number = 0;

  /* [L - S] Modalità di visualizzazione: lettura degli allegati già presenti o caricamento di nuovi.
    (N.B.: in modalità di scrittura [S] posso passare comunque gli allegati già caricati per visualizzarli, cambierà solo l'interfaccia) */
  @Input() mod: "S"|"L" = "L"

  /* L'array degli allegati già presenti, visualizzabili in ogni modalità */
  @Input() allegatiPresenti: Array<GestioneAllegatiVO> = [];

  /* Evento di tipo Array<GestioneAllegatiVO> che viene emesso ogni volta che si seleziona un nuovo allegato,
    contiene l'array degli allegati selezionati fin'ora */
  @Output() newFileEvent = new EventEmitter;
  
  /* Evento che viene emesso quando si effettua un aggiornamento sugli allegati,
    il componente chiamante dovrà ricaricare i dati */
  @Output() needsUpdateEvent = new EventEmitter;

  @ViewChild('uploadFile') myFile: ElementRef;
  
  
  //allegatiPresenti: Array<GestioneAllegatiVO> = new Array<GestioneAllegatiVO>();
  nuoviAllegati: Array<GestioneAllegatiVO> = new Array<GestioneAllegatiVO>();


  allegati = [];
  //allegatiBase64: string[] = [];
  
  allegatiGenerici: Array<File> = new Array<File>();
  idProgetto: any;
  //idModalitaAgevolazione: any;
  
  // Messaggi
  showSucc: boolean = false;
  showError: boolean = false;
  messageError: string = "";
  messageSucc: string = "";

  constructor(
    //public allegatiService : AllegatiService,
    public dialog: MatDialog,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
  ) { }

  ngOnInit(): void {
    //console.log(this.mod);
  }

  /*getAllegati(){
    this.allegatiService.getAllegati(this.idProgetto,this.idModalitaAgevolazione,this.nomeBox).subscribe(data => {

      /* Chiamava questa roba 
      getAllegati(idProgetto,idModalitaAgevolazione,nomeBox) {
      let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getAllegati';
      let params = new HttpParams()
        .set('idProgetto', idProgetto.toString())
        .set('idProgetto', idModalitaAgevolazione.toString())
        .set('idProgetto', nomeBox);
      return this.http.get<any>(url, { params: params });
      }*/
      

      /*for (let x = 0; x < data.length; x++) {
        this.allegatiGenerici.push(data[x]);
      }
    })
  }*/

  // OLD
  /*handleFile(files: Array<File>) {
    console.log("files[0]");
    this.resetMessage();
    if (!files[0].name.endsWith(".xlsx") && !files[0].name.endsWith(".XLSX") && !files[0].name.endsWith(".pdf") && !files[0].name.endsWith(".PDF") && !files[0].name.endsWith(".DOC") && !files[0].name.endsWith(".doc") && !files[0].name.endsWith(".zip") && !files[0].name.endsWith(".ZIP")) {
      this.showMessageError("Il file deve avere estensione .pdf oppure .doc oppure .zip oppure .xlsx");
    } 
    else {
        this.allegati.push(files[0]);
        console.log(this.allegati);
        this.newFileEvent.emit(this.allegati);
        
    }
    //console.log(this.allegati);
  }*/



  onFileSelected(event) {
    const file = event.target.files[0];

    if (file) {
      let TEMPnuovoAllegato: GestioneAllegatiVO = new GestioneAllegatiVO();

      this.convertFileToBase64(file).subscribe(base64 => {

        TEMPnuovoAllegato.nomeNuovoAllegato = file.name;
        TEMPnuovoAllegato.nuovoAllegatoFileSize = file.size;
        //TEMPnuovoAllegato.nuovoAllegato = file; // Non mi serve, verrebbe trasmesso al BE
        TEMPnuovoAllegato.nuovoAllegatoBase64 = base64;
        this.nuoviAllegati.push(TEMPnuovoAllegato);

        this.newFileEvent.emit(this.nuoviAllegati);
      });
    }
  }

  convertFileToBase64(file: File): Observable<string> {
    // Un singolo file
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = (event) => result.next(btoa(event.target.result.toString()));
    //console.log("res 64:", result);
    return result.asObservable();

    // Più file
    /*const result = new ReplaySubject<string[]>(1);
    const base64Strings: string[] = [];
    for (let i = 0; i < files.length; i++) {
      const reader = new FileReader();
      reader.readAsBinaryString(files[i]);
      reader.onload = () => {
        base64Strings.push(btoa(reader.result.toString()));
        if (base64Strings.length === files.length) {
          result.next(base64Strings);
        }
      };
    }
    return result.asObservable();*/
  }

  formatFileSize(bytes: number, decimalPoint = 0): string {
    if (bytes === 0) {
      return '0 Bytes';
    }
  
    const k = 1024;
    const dm = decimalPoint < 0 ? 0 : decimalPoint;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']; // Meglio esagerare
    const i = Math.floor(Math.log(bytes) / Math.log(k));
  
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }
  


  clearInputElement() {
    this.myFile.nativeElement.value = ''
  }

  rimuoviAllegatoLocale(index: number) {
    this.nuoviAllegati.splice(index, 1);
    this.clearInputElement();
 }


  rimuoviAllegatoSalvato(idDocIndex: number): void {
    /* this.showSucc = false;
    this.showError = false;

    let dialogRef = this.dialog.open(DialogEliminazioneAllegato, {
        width: '480px',
        data: {
            idDocIndex: idDocIndex,
            idUtente: this.idUtente,
            idEscussione: this.idEscussione,
            schedaCliente: this.schedaClienteMain2[0]
        }

    });

    dialogRef.afterClosed().subscribe(result => {
        this.allegati = [];
        this.allegatiGenerici = [];
        this.getAllegati();

    }) */

 }

  scaricaAllegatoSalvato(file: GestioneAllegatiVO) {
    this.archivioFileService.leggiFile(this.configService.getApiURL(), file.idDocumentoIndex.toString()).subscribe(res => {

      if (res) {
        saveAs(res, file.nomeFile);
      }
    });
  }

  showMessageError(msg: string) {
    this.resetMessage();
    this.messageError = msg;
    this.showError = true;
    //const element = document.querySelector('#scrollId');
    //element.scrollIntoView();
  }

  showMessageSucc(msg: string) {
    this.resetMessage();
    this.messageSucc = msg;
    this.showSucc = true;
    //const element = document.querySelector('#scrollId');
    //element.scrollIntoView();
  }

  resetMessage() {
    this.showError = false;
    this.showSucc = false;
    this.messageError = "";
    this.messageSucc = "";
  }

}
