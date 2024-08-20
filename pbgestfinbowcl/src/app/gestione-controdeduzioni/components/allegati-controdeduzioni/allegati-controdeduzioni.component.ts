/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { AnteprimaDialogComponent, ArchivioFileDialogComponent, ArchivioFileService, Constants, DocumentoAllegatoDTO, HandleExceptionService, InfoDocumentoVo, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AllegatiControdeduzioniVO } from '../../common/dto/allegati-controdeduzioni.vo';
import { ControdeduzioniService } from '../../services/controdeduzioni.service';
import { saveAs } from 'file-saver-es';
@Component({
    selector: 'app-allegati-controdeduzione',
    templateUrl: './allegati-controdeduzioni.component.html',
    styleUrls: ['./allegati-controdeduzioni.component.scss']
})
export class AllegatiControdeduzioneComponent implements OnInit {
  @Input() tipoDichiarazione: string;
  subscribers: any = {};
  idProgetto: number;
  idBandoLinea: number;
  user: UserInfoSec;
  isLoading = false;
  
  idTarget: number;
  showError: boolean = false;
  showSucc: boolean = false;
  messageError: string;
  idOp = Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CONTESTAZIONI;
  messageSuccess = 'Allegato inserito correttamente'
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  isMessageErrorVisible: boolean;
  

//allegati
  allegati: Array<DocumentoAllegatoDTO>;
  loadedAllegati: boolean = true;
  allegatiDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegatoDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegatiDaInviare : Array<AllegatiControdeduzioniVO> = new Array<AllegatiControdeduzioniVO>();
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  loadedAssociaAllegati: boolean = true;
  messaggioLettera = false;
  errore;
  message;

  constructor(
  @Inject(MAT_DIALOG_DATA) public data: any,
  public controdeduzioniService : ControdeduzioniService,
  private activatedRoute: ActivatedRoute,
  private userService: UserService,
  public dialogRef: MatDialogRef<AllegatiControdeduzioneComponent>,
  private dialog: MatDialog,
  private configService: ConfigService,
  private handleExceptionService: HandleExceptionService,
  private archivioFileService: ArchivioFileService,
  private sharedService: SharedService,) { }

  ngOnInit(): void {
    this.getDati();
    this.getAllegati();
    
    
  }

  getDati(){
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
        this.idProgetto = +params['idProgetto'];
        this.idBandoLinea = +params['idBandoLinea'];
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
            this.user = data;
        })
  
    })
}

  getAllegati(){
    this.allegatiGenerici = [];
    this.letteraAccompagnatoria = [];
     this.subscribers.allegati = this.controdeduzioniService.getAllegati(this.data.dataKey.idControdeduzione).subscribe(data => {
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
       },err => {
         this.handleExceptionService.handleBlockingError(err);
       });
} 
 
  confermaAllegati() {
    if(this.letteraAccompagnatoria.length<=0){
      if(this.allegatoDaSalvare.length<=0 ){
        this.messaggioLettera = true;
        return;
      }
    else {
    this.messaggioLettera = false;
    this.allegatiDaInviare = [];
    this.allegatoDaSalvare.forEach(i =>this.allegatiDaInviare.push
      (new AllegatiControdeduzioniVO(i.nome,i.idDocumentoIndex,'I','AF')));
    this.allegatiDaSalvare.forEach(i =>this.allegatiDaInviare.push
      (new AllegatiControdeduzioniVO(i.nome,i.idDocumentoIndex,null,'AF'))); 
    this.controdeduzioniService.uploadFile(this.allegatiDaInviare,this.data.dataKey.idControdeduzione).subscribe(data => {
        if (data == true) {
            this.message = "Allegati inseriti con successo";
              this.errore = false;
              setTimeout(() => {
                this.errore = null;
                this.closeDialog();
              }, 2000);  
            
        } else {
            this.errore = true;
            this.message = "Non è stato possibile inserire gli allegati";
        }

    }) 
  }
   }else {
    this.messaggioLettera = false;
    this.allegatiDaInviare = [];
    this.allegatoDaSalvare.forEach(i =>this.allegatiDaInviare.push
      (new AllegatiControdeduzioniVO(i.nome,i.idDocumentoIndex,'I','AF')));

    this.allegatiDaSalvare.forEach(i =>this.allegatiDaInviare.push
      (new AllegatiControdeduzioniVO(i.nome,i.idDocumentoIndex,null,'AF')));
    this.controdeduzioniService.uploadFile(this.allegatiDaInviare,this.data.dataKey.idControdeduzione).subscribe(data => {
        if (data.code === "OK") {
            this.message = "Allegati inseriti con successo";
            this.errore = false;
            setTimeout(() => {
              this.errore = null;
              this.closeDialog();
            }, 2000);  
            
        } else if (data.code === "KO") {
            this.errore = true;
              this.message = "Non è stato possibile inserire gli allegati";
        }
    }) 
   }
}

  aggiungiAllegati() {
  this.resetMessageSuccess();
  this.resetMessageError();
  let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
    maxWidth: '100%',
    width: window.innerWidth - 100 + "px",
    height: window.innerHeight - 50 + "px",
    data: {
      allegatiDaSalvare: this.allegatiDaSalvare,
      allegati: this.allegati,
      apiURL: this.configService.getApiURL(),
      user: this.user,
      drawerExpanded: this.sharedService.getDrawerExpanded()
    }
  });
  dialogRef.afterClosed().subscribe(res => {
    if (res && res.length > 0) {
      for (let a of res) {
        if (this.allegatiDaSalvare.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
          this.allegatiDaSalvare.push(a);
        }
      }
    }
    
  });
} 
  rimuoviAllegatiDaSalvare(idDocumentoIndex: string) {
  this.allegatiDaSalvare.splice(this.allegatiDaSalvare.findIndex(a => a.idDocumentoIndex === idDocumentoIndex), 1);
}

  aggiungiAllegato() {
  this.resetMessageError();
  this.resetMessageSuccess();
  let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
    maxWidth: '100%',
    width: window.innerWidth - 100 + "px",
    height: window.innerHeight - 50 + "px",
    data: {
      allegatoDaSalvare: this.allegatoDaSalvare,
      allegati: this.allegati,
      apiURL: this.configService.getApiURL(),
      user: this.user,
      drawerExpanded: this.sharedService.getDrawerExpanded(),
      onlyOneFile: true
    }
  });
  dialogRef.afterClosed().subscribe(res => {
    this.messaggioLettera = false;
    if (res && res.length > 0) {
      for (let a of res) {
        if (this.allegatoDaSalvare.find(all => all.idDocumentoIndex === a.idDocumentoIndex) === undefined) {
          this.allegatoDaSalvare.push(a);
        }
      }
    }
    
  });
}

  rimuoviAllegatoDaSalvare(idDocumentoIndex: string) {
  this.allegatoDaSalvare.splice(this.allegatoDaSalvare.findIndex(a => a.idDocumentoIndex === idDocumentoIndex), 1);
}

  deleteAllegato(idFileEntita){
  this.controdeduzioniService.deleteAllegati(idFileEntita).subscribe(data => {
    if (data) {
        this.ngOnInit();
        
    } else {
        this.showError = true;
        this.showSucc = false;
        this.showMessageError("non è stato possibile eliminare l'allegato'.");
    }

})  
}

anteprimaFile(lettera)  {
  console.log(lettera);
  
  this.resetMessageError();
  let comodo = new Array<any>();
  comodo.push(lettera.nome); //nome del file
  comodo.push(lettera.idDocumentoIndex);
  comodo.push(new MatTableDataSource<InfoDocumentoVo>([new InfoDocumentoVo(lettera.codTipoDoc, lettera.nome, null, null, null, null, null, lettera.idDocumentoIndex, null)]));
  comodo.push(this.configService.getApiURL());
  comodo.push(lettera.codTipoDoc); //dalla pbandi_t_documenti_index e pbandi_c_tipo_documento_index

  this.dialog.open(AnteprimaDialogComponent, {
    data: comodo,
    panelClass: 'anteprima-dialog-container'
  });

}

anteprimaFileMetod(lettera)  {
  console.log(lettera);
  
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



resetMessageSuccess() {
  this.messageSuccess = null;
  this.isMessageSuccessVisible = false;
}

  resetMessageError() {
  this.messageError = null;
  this.isMessageErrorVisible = false;
}

  closeDialog() {
      this.dialogRef.close();
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

}
