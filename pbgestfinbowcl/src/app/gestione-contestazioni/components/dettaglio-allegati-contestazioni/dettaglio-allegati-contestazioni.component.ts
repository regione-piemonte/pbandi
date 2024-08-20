/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { AnteprimaDialogComponent, ArchivioFileService, Constants, DocumentoAllegatoDTO, HandleExceptionService, InfoDocumentoVo, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { AllegatiContestazioniVO } from '../../commons/dto/allegatiContestazione';
import { ContestazioniService } from '../../services/contestazioni.service';
import { saveAs } from 'file-saver-es';

@Component({
  selector: 'app-dettaglio-allegati-contestazioni',
  templateUrl: './dettaglio-allegati-contestazioni.component.html',
  styleUrls: ['./dettaglio-allegati-contestazioni.component.scss']
})
export class DettaglioAllegatiContestazioniComponent implements OnInit {

  subscribers: any = {};
  idProgetto: number;
  idBandoLinea: number;
  user: UserInfoSec;
  isLoading = false;
  messageError: string;
  isMessageErrorVisible: boolean;
  messageSuccess: string;
  isMessageSuccessVisible: boolean;

  
  nomeFile: string;
  nomeFileSecond: string;
  allegatiSecond: Array<File> = new Array<File>();
  file: File;
  fileSecond: File;
  idTarget: number;
  showError: boolean = false;
  showSucc: boolean = false;
  idOp = Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CONTESTAZIONI;
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  allegati;

  constructor(
  @Inject(MAT_DIALOG_DATA) public data: any,
  public contestazioniService : ContestazioniService,
  private activatedRoute: ActivatedRoute,
  private userService: UserService,
  public dialogRef: MatDialogRef<DettaglioAllegatiContestazioniComponent>,
  private handleExceptionService: HandleExceptionService,
  private configService: ConfigService,
  private dialog: MatDialog,
  private archivioFileService: ArchivioFileService,
  private router: Router) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
          this.user = data;
      })

  })
  this.getAllegati();
  }

  getAllegati(){
    // this.loadedAllegati = false;
     this.subscribers.allegati = this.contestazioniService.getAllegati(this.data.dataKey.idContestazione).subscribe(data => {
       if (data) {
         
         for (let a of data) {
           if(a.flagEntita === 'I'){
               this.letteraAccompagnatoria.push(Object.assign({}, a))
           }else{
             this.allegatiGenerici.push(Object.assign({}, a))
           }
           
         }
       } else {
         this.allegati = new Array<DocumentoAllegatoDTO>();
       }
     //  this.loadedAllegati = true;
       },err => {
         this.handleExceptionService.handleBlockingError(err);
       });
   } 

  closeDialog() {
      this.dialogRef.close();
  }

  anteprimaAllegato(lettera : AllegatiContestazioniVO)  {
    this.resetMessageError();
    let comodo = new Array<any>();
    comodo.push(lettera.nomeFile); //nome del file
    comodo.push(lettera.idDocumentoIndex);
    comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(lettera.codTipoDoc, lettera.nomeFile, null, null, null, null, null, lettera.idDocumentoIndex, null)]));
    comodo.push(this.configService.getApiURL());
    comodo.push(lettera.codTipoDoc); //dalla pbandi_t_documenti_index e pbandi_c_tipo_documento_index

    this.dialog.open(AnteprimaDialogComponent, {
      data: comodo,
      panelClass: 'anteprima-dialog-container'
    });

  }

  isAnteprimaVisible(nomeFile: string) { //duplicato di isFileWithPreview dentro AnteprimaDialogComponent
    const splitted = nomeFile.split(".");
    if (splitted[splitted.length - 1] == "pdf" || splitted[splitted.length - 1] == "PDF" || splitted[splitted.length - 1] == "xml" || splitted[splitted.length - 1] == "XML" || splitted[splitted.length - 1] == "p7m" || splitted[splitted.length - 1] == "P7M") {
      return true;
    } else {
      return false;
    }
  }

  scaricaFile(idDocumentoIndex: string) {
    this.archivioFileService.leggiFileConNome(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        let nome = res.headers.get("header-nome-file");
        saveAs(new Blob([res.body]), nome);
      }
   //   this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
   //   this.loadedDownload = true;
    });
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  showMessageError(msg: string) {
    this.messageError = msg;
}

showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
}

resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
}
  


}
