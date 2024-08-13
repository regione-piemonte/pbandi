/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { AnteprimaDialogComponent, ArchivioFileDialogComponent, DocumentoAllegatoDTO, HandleExceptionService, InfoDocumentoVo, UserInfoSec } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/shared/services/shared.service';
import { Location } from '@angular/common';
import { GestioneIntegrazioniService } from '../../services/gestione-integrazioni.service';
import { AllegatiIntegrazioniVO } from '../../commons/dto/allegatiIntegrazioniDTO';
import { MatTableDataSource } from '@angular/material/table';
import { AllegatiPrecedentementeVO } from '../../commons/allegatiPrecedentementeVO';

@Component({
  selector: 'app-integrazione-procedimento-revoca',
  templateUrl: './integrazione-procedimento-revoca.component.html',
  styleUrls: ['./integrazione-procedimento-revoca.component.css']
})
export class IntegrazioneProcedimentoRevocaComponent implements OnInit {
  subscribers: any = {};
  idProgetto: number;
  idBandoLinea: number;
  user: UserInfoSec;
  isLoading : boolean = true;
  codiceProgetto: number;
  nRevoca : number;
  idRichIntegrazione;
  

  //allegati
  allegati: Array<DocumentoAllegatoDTO>;
  loadedAllegati: boolean = true;
  allegatiDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegatoDaSalvare: Array<InfoDocumentoVo> = new Array<InfoDocumentoVo>();
  allegatiDaInviare : Array<AllegatiIntegrazioniVO> = new Array<AllegatiIntegrazioniVO>();
  letteraAccompagnatoria: any = [];
  allegatiGenerici: any = [];
  loadedAssociaAllegati: boolean = true;

 // Messaggi 
  messaggioLettera: boolean  = false;
  messageSuccess = 'Allegato inserito correttamente'
  isMessageSuccessVisible: boolean;
  messageWarning: string;
  isMessageWarningVisible: boolean;
  isMessageErrorVisible: boolean;
  showError: boolean = false;
  showSucc: boolean = false;
  messageError: string;
  errore;
  lettaraIstruttore: any[][];
  allegatiIstruttoreRevoche: AllegatiPrecedentementeVO[];

  constructor(private dialog: MatDialog,
    private configService: ConfigService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private integrazioniService: GestioneIntegrazioniService,
    private location: Location,) { }

  ngOnInit(): void {
    this.getDati();
    this.getAllegati();
    this.getAllegatiIstruttoreRevoche();
  }

  getDati(){

    this.activatedRoute.queryParams.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.codiceProgetto = params['codiceProgetto'];
      this.nRevoca = params['nRevoca'];
      this.idRichIntegrazione = params['idRichIntegrazione'];
      
    });
        this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
            this.user = data;
            this.isLoading = false;
        }) 
  
}

getLetteraIstruttore(){
  this.integrazioniService.getLetteraIstruttore(this.idRichIntegrazione).subscribe(data => { 
    if (data) {
      this.lettaraIstruttore = [data];
      console.log(data);
      
    } else {
      
    }
     },err => {
      this.handleExceptionService.handleBlockingError(err);
    });
}

  getAllegati(){
    this.allegatiGenerici = [];
    this.letteraAccompagnatoria = [];
     this.subscribers.allegati = this.integrazioniService.getAllegatiIntegrazione(this.idRichIntegrazione).subscribe(data => { 
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
      this.loadedAllegati = true;
        },err => {
         this.handleExceptionService.handleBlockingError(err);
       }); 
    } 


    getAllegatiIstruttoreRevoche(){
       this.integrazioniService.getAllegatiIstruttoreRevoche(this.idRichIntegrazione).subscribe(data => { 
        this.allegatiIstruttoreRevoche = data;
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
    this.allegatoDaSalvare.forEach(i =>this.allegatiDaInviare.push(new AllegatiIntegrazioniVO(i.nome,i.idDocumentoIndex,'I')));
    this.allegatiDaSalvare.forEach(i =>this.allegatiDaInviare.push(new AllegatiIntegrazioniVO(i.nome,i.idDocumentoIndex,null)));
    this.integrazioniService.uploadFileIntegrazioni(this.allegatiDaInviare,this.idRichIntegrazione).subscribe(data => {
        if (data) {
            this.showError = false;
            this.showSucc = true;
            setTimeout(() => {
              this.errore = null;
              this.goBack();
            }, 2000);
            
        } else {
            this.showError = true;
            this.showSucc = false;
            this.showMessageError("non è stato possibile inserire l'allegato'.");
        }
      }) 
    }
   }
   
   else {
    this.messaggioLettera = false;
    this.allegatiDaInviare = [];
    this.allegatoDaSalvare.forEach(i =>this.allegatiDaInviare.push(new AllegatiIntegrazioniVO(i.nome,i.idDocumentoIndex,'I')));
    this.allegatiDaSalvare.forEach(i =>this.allegatiDaInviare.push(new AllegatiIntegrazioniVO(i.nome,i.idDocumentoIndex,null)));
    this.integrazioniService.uploadFileIntegrazioni(this.allegatiDaInviare,this.idRichIntegrazione).subscribe(data => {
        if (data) {
            this.showError = false;
            this.showSucc = true;
            setTimeout(() => {
              this.errore = null;
              this.goBack();
            }, 2000); 
        } else {
            this.showError = true;
            this.showSucc = false;
            this.showMessageError("non è stato possibile inserire l'allegato'.");
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
  let datiBackend = {
    idFileEntita:idFileEntita
  }
  this.integrazioniService.deleteAllegati(datiBackend).subscribe(data => {
    if (data) {
        this.ngOnInit();
        
    } else {
        this.showError = true;
        this.showSucc = false;
        this.showMessageError("non è stato possibile eliminare l'allegato'.");
    }

})  
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
  
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  goBack() {
    this.location.back();
  }

  anteprimaAllegato(lettera)  {
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

  closeDialog(){

  }
}
