/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from '@pbandi/common-lib';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { NoteGeneraliVO } from '../../commons/dto/note-generaliVO';
import { StoricoRevocaDTO } from '../../commons/dto/storico-revoca-dto';
//import { PassaggioPerditaService } from '../../services/passaggio-perdita-services'; // OLD
import * as XLSX from 'xlsx';
import { DialogStoricoBoxComponent } from '../dialog-storico-box/dialog-storico-box.component';
import { MatDialog } from '@angular/material/dialog';
import { AttivitàIstruttoreAreaCreditiService } from '../../services/attivita-istruttore-area-crediti.service';
import { DialogEditNoteGeneraliComponent } from './dialog-edit-note-generali/dialog-edit-note-generali.component';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { saveAs } from 'file-saver-es';
import { AllegatoVO } from "../../commons/dto/allegatoVO";
import { ArchivioFileService } from '@pbandi/common-lib';
import { ConfigService } from "src/app/core/services/config.service";
import { DialogEliminazioneAllegato } from "../dialog-eliminazione-allegato/dialog-eliminazione-allegato.component";
import { DocumentoIndexVO } from 'src/app/gestione-controlli/commons/documento-index-vo';
import { GestioneAllegatiVO } from '../../commons/dto/gestione-allegati-VO';
import { Observable, ReplaySubject } from 'rxjs';


@Component({
  selector: 'app-note-generali',
  templateUrl: './note-generali.component.html',
  styleUrls: ['./note-generali.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NoteGeneraliComponent implements OnInit {
  @Input() item;
  @Input() ambito;
  @Input() nomeBox;
  @Input() idModalitaAgevolazione;
  
  user: UserInfoSec; 
  idProgetto:  number ; 
  note: string; 
  idUtente: number; 
  subscribers: any = {};

  // Loading spinner
  userLoaded: boolean = false; 
  noteGenLoaded: boolean = false;
  noteMonLoaded: boolean = false;
  masterSpinner: boolean = false;

  // Obj
  noteGenerali: Array<NoteGeneraliVO> = new Array<NoteGeneraliVO>();
  noteMonitoraggio: Array<StoricoRevocaDTO> = new Array<StoricoRevocaDTO>();

  displayedColumns: string[] = ['txtNota', 'dataInserimento', 'dataModifica', 'utenteModifica', 'actions'];

  allegati: Array<File> = new Array<File>();

  // If no notes
  noNoteGenerali: boolean = true; 
  noNoteMonitoraggio: boolean = true;   
  
  isSaveSuccess: boolean;
  isError: boolean;
  errorMsg: string = "Errore nel salvataggio della Nota.";
  succMsg: string = "Nota salvata correttamente."

  isModifica: boolean;

  isSalvato:  boolean; 
  isStorico: boolean;
  
  isConferma: boolean;
   
  dimStorico: boolean; 
  excelFile: string = 'storico.xlsx';
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    //private passaggioPerditaService: PassaggioPerditaService, // OLD
    private noteService: AttivitàIstruttoreAreaCreditiService,
    private userService: UserService,
    //private handleExceptionService: HandleExceptionService,
    private archivioFileService: ArchivioFileService,
    private configService: ConfigService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.userLoaded = false;
    this.isModifica = false;

    this.route.queryParams.subscribe(params => {
      this.item = params.idProgetto;
    });

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        console.log("utente", this.user)
        this.idUtente = this.user.idUtente;
        this.userLoaded = true; 
        this.idProgetto = this.item;
        
        this.getNoteGenerali();

        this.getNoteMonitoraggio();
      }
    }, err => {
      //this.handleExceptionService.handleNotBlockingError(err);
    });

  }

  /*recuperaFile(newItem) {
    this.allegati = newItem;
    
  }*/
  
  getNoteGenerali(){
    this.noteGenLoaded = false; 
    //this.isModifica = false;

    this.subscribers.getNota = this.noteService.getNoteGenerali(this.item, this.idModalitaAgevolazione).subscribe(data=>{

      if(data){
        this.noteGenerali = data;
        //this.note = this.noteGeneraliVO.note;

        if (this.noteGenerali.length > 0) {
          this.noNoteGenerali = false; 
          //this.isConferma = false;
        } else {
          //this.isConferma = true;
          this.noNoteGenerali = true;
        }
      }
      
      this.noteGenLoaded = true;
    })
  }

  getNoteMonitoraggio(){
    this.noteMonLoaded = false;
    this.subscribers.storivo =  this.noteService.getNoteMonitoraggio(this.item, this.idModalitaAgevolazione).subscribe(data=>{

      if(data.length > 0){
        this.noteMonitoraggio = data; 
        
        this.noNoteMonitoraggio = false;
      } else{
        this.noNoteMonitoraggio = true;
      }
      
      this.noteMonLoaded = true;
    });
  }


  nuovaNota() {
    this.isSaveSuccess = false;
    this.isError = false
    
    let dialogRef = this.dialog.open(DialogEditNoteGeneraliComponent, {
      width: '40%',
      data: {op: 1}
    });

    dialogRef.afterClosed().subscribe(data => {

      //console.log("Dialog res: ", data);

      if (data?.event == "save") {
        this.noteGenLoaded = false;

        let notaDaSalvare = new NoteGeneraliVO();

        notaDaSalvare.note = data.text; 
        notaDaSalvare.idUtenteIns = this.idUtente;
        notaDaSalvare.idProgetto = this.idProgetto;

        /*let formData = new FormData();
        formData.append("file", data.allegati[0]);
        formData.append("nomeFile", data.allegati[0].name);

        //notaDaSalvare.fileDaSalvare = formData;
        console.log("obj: ", notaDaSalvare);*/

        /*  COMMENTATO MOMENTANEAMENTE
        this.subscribers.salvaNota = this.noteService.salvaNotaGenerale(notaDaSalvare, data.allegati, false, this.idModalitaAgevolazione).subscribe({next:(dataServ)=>{

          //console.log("data from service:", dataServ);
          
          if(dataServ) {
            this.isSaveSuccess = true;
          } else {
            this.isError = true;
            //this.noteGenLoaded = true;
          }
          
          this.getNoteGenerali();
          
        }, error:(e)=>{
          console.log("Print error: ", e);
          this.isError = true
          this.noteGenLoaded = true;
        }});  */

       

        notaDaSalvare.nuoviAllegati = data.nuoviAllegati;
        //console.log("full obj", notaDaSalvare);

        this.subscribers.salvaNota = this.noteService.salvaNota(notaDaSalvare, data.allegati, false, this.idModalitaAgevolazione).subscribe({next:(dataServ)=>{

          //console.log("data from service:", dataServ);
          
          if(dataServ) {
            this.isSaveSuccess = true;
          } else {
            this.isError = true;
            //this.noteGenLoaded = true;
          }
          
          this.getNoteGenerali();
          
        }, error:(e)=>{
          console.log("Print error: ", e);
          this.isError = true
          this.noteGenLoaded = true;
        }});

      }

    })
  }

  

  modificaNota(rowNota: NoteGeneraliVO) {
    this.isSaveSuccess = false;
    this.isError = false

    //this.dialogSaving = false;
    let dialogRef = this.dialog.open(DialogEditNoteGeneraliComponent, {
      width: '40%',
      data: {
        op: 2,
        nota: rowNota.note,
        allegatiPresenti: rowNota.allegatiPresenti
      }
    });

    dialogRef.afterClosed().subscribe(data => {

      //console.log("Dialog res: ", data);

      if (data?.event == "save") {
        this.noteGenLoaded = false;

        let notaDaSalvare = new NoteGeneraliVO();

        notaDaSalvare.note = data.text; 
        notaDaSalvare.idUtenteIns = this.idUtente;
        notaDaSalvare.idProgetto = this.idProgetto;
        notaDaSalvare.idAnnotazione = rowNota.idAnnotazione;
        notaDaSalvare.nuoviAllegati = data.nuoviAllegati;
        notaDaSalvare.allegatiPresenti = rowNota.allegatiPresenti;

        this.subscribers.salvaNota = this.noteService.salvaNota(notaDaSalvare, data.allegati, true, this.idModalitaAgevolazione).subscribe({next:(dataServ)=>{

          //console.log("data from service:", dataServ);
          
          if(dataServ) {
            this.isSaveSuccess = true;
          } else {
            this.isError = true;
            //this.noteGenLoaded = true;
          }
          
          this.getNoteGenerali();
          
        }, error:(e)=>{
          console.log("Print error: ", e);
          this.isError = true
          this.noteGenLoaded = true;
        }});  

      }
    })
  }

  eliminaNota(idAnnotazione: number) {

    this.masterSpinner = true;

    this.subscribers.eliminaNota = this.noteService.eliminaNota(idAnnotazione, this.idModalitaAgevolazione).subscribe({next:(dataServ)=>{

      //console.log("data from service:", dataServ);
      
      if(dataServ) {
        this.masterSpinner = false;
        this.isSaveSuccess = true;
        this.succMsg = "Nota eliminata con successo !";
      } else {
        this.masterSpinner = false;
        this.isError = true;
        //this.noteGenLoaded = true;
      }
      
      this.getNoteGenerali();
      
    }, error:(e)=>{
      this.masterSpinner = false;
      console.log("Print error: ", e);
      this.isError = true
      this.noteGenLoaded = true;
    }});  

  }


  downloadAllegato(file: DocumentoIndexVO) {
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), file.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, file.nomeFile);
      }
    });
  }

  eliminaAllegato(idDocIndex: number): void {
    this.isSaveSuccess = false;
    this.isError = false

    /*let dialogRef = this.dialog.open(DialogEliminazioneAllegato, {
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
      this.gliAllegati = [];
      this.getAllegati();

    })*/

  }


  onModifica() {
    this.isModifica = false;
    this.isSaveSuccess = false; 
    this.noNoteGenerali = false;
    this.isConferma = true;
    this.isStorico = false;
  }
  
  onAnnulla() {
    this.isModifica = false;
    this.isSaveSuccess = false;
    this.noNoteGenerali = true;
    this.isConferma = false;
    this.isStorico = false;
    this.getNoteGenerali(); 
  }
  disattiva() {
    this.isModifica = false;
    this.isConferma = false;
    this.noNoteGenerali = true;
    this.isStorico = false;
  }
  
  downloadStorico(){
    let element = document.getElementById('excel-table');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Foglio');
    XLSX.writeFile(wb, this.excelFile);

  }

  openNoteMonitoraggio() {
    this.isSaveSuccess = false;
    this.isError = false;

    this.dialog.open(DialogStoricoBoxComponent, {
      width: '60%',
      maxWidth: '75%',
      maxHeight: '95%',
      data: {
        id: 9,
        storico: this.noteMonitoraggio, 
        idProgetto: this.item
      }
    });
  }

  isLoading(){
    if (!this.noteGenLoaded || !this.userLoaded || !this.noteMonLoaded) {
      //console.log("spi: true");
      return true;
    }
    //console.log("spi: false");
    return false;
  }

}
