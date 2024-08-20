/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ArchivioFileService, DocumentoAllegatoDTO } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { SharedService } from 'src/app/shared/services/shared.service';
import { ControlloLocoVo } from '../../commons/controllo-loco-vo';
import { DocumentoIndexVO } from '../../commons/documento-index-vo';
import { RichiestaIntegrazioneVo } from '../../commons/richiesta-integrazione-vo';
import { RichiestaProrogaVo } from '../../commons/richiesta-proroga-vo';
import { ControlliService } from '../../services/controlli.service';
import { saveAs } from 'file-saver-es';
import { FlexibleConnectedPositionStrategy } from '@angular/cdk/overlay';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';

@Component({
  selector: 'app-dialog-gestione-controlli',
  templateUrl: './dialog-gestione-controlli.component.html',
  styleUrls: ['./dialog-gestione-controlli.component.scss']
})
export class DialogGestioneControlliComponent implements OnInit {
  element: any;
  controllo: ControlloLocoVo = new ControlloLocoVo();
  elencoAllegati: DocumentoIndexVO[] = [];
  idControllo: any;
  subscribers: any = {};
  idLetteraAvvioControllo: number;
  idLetterraEsitoControllo: number;
  file: File;
  fileName: string;
  isFile: boolean;
  idUtente: number
  result: boolean;
  finalResult: boolean;
  giorniScadenza: number;
  motivazione: string;
  richiestaIntegr: RichiestaIntegrazioneVo = new RichiestaIntegrazioneVo();
  isError: boolean;
  idTargetRichiesta: number;
  idRichiestaIntegr: any;
  richProroga: RichiestaProrogaVo = new RichiestaProrogaVo();
  giorniApprovati: number;

  letteraAvvioControllo: DocumentoIndexVO = new DocumentoIndexVO()
  letteraAccompagnatoria: DocumentoIndexVO = new DocumentoIndexVO()
  letteraEsitoControllo: DocumentoIndexVO = new DocumentoIndexVO()
  verbaleControllo: DocumentoIndexVO = new DocumentoIndexVO()
  checkListControllo: DocumentoIndexVO = new DocumentoIndexVO()
  idAttivitaControllo: number;
  fileLetteraAvvioControllo: File = null;
  fileLetteraAccompagnatoria: File = null;
  fileLetteraEsitoControllo: File = null;
  fileVerbaleControllo: File = null;
  fileCheckListControllo: File = null;
  elencoFile: File[] = new Array(5);
  idStatoControllo: any;
  isLetteraAvvio: boolean;
  fileAvvContrName: any;
  fileCheckListName: any;
  isCheckList: boolean;
  fileVerbaleName: any;
  isVerbaleContr: boolean;
  fileEsitoName: any;
  isLetteraEsito: boolean;
  fileLetteraAccompName: any;
  isLetteraAccomp: boolean;
  isMessageErrorVisible: boolean;
  messageError: string;
  idStatoProroga: number;
  displayedColumns = ['dataRichiesta', 'giorniRichiesti', 'motivazione', 'giorniApprov', 'statoRichiesta'];
  elencoProroghe: RichiestaProrogaVo[] = [];
  isShowTable: boolean;
  elencoAllegatiBenef: DocumentoIndexVO[]=[];
  letteraAccompagnatoriaBeneficario: DocumentoIndexVO = new DocumentoIndexVO();
  isAllegatiBenef: boolean;
  idStatoRichiesta: number;
  esitoDTO: EsitoDTO;

  constructor(
    private configService: ConfigService,
    private archivioFileService: ArchivioFileService,
    public dialogRef: MatDialogRef<DialogGestioneControlliComponent>,
    public sharedService: SharedService,
    private controlliService: ControlliService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {

    console.log(this.data);
    this.element = this.data.element;
    this.idControllo = this.data.idControllo;
    this.idUtente = this.data.idUtente;
    if (this.element == 1 || this.element == 3) {
      this.controllo = this.data.controllo;
      this.handleFileInput(this.data.file);
    }
    if (this.element == 2) {
      this.controllo = this.data.controllo;
      this.idAttivitaControllo = this.controllo.idAttivitaContrLoco;
      this.idStatoControllo = this.data.idStatoControllo;
      this.elencoFile = this.data.files;
      this.idRichiestaIntegr = this.data.idRichiestaIntegr;
      this.idStatoRichiesta = this.data.idStatoRichiesta ; 
      this.loadAllegati();
    }
    if (this.element == 4) {
      this.idRichiestaIntegr = this.data.idRichiesta
    }
    // element =4 corrisponde alla chiusura richiesta integrazione;  

    // element =5 corrisponde alla gestione della richiesta di proroga;
    if (this.element == 5) {
      this.richProroga = this.data.proroga
      this.idStatoProroga = this.richProroga.idStatoProroga;
      this.giorniApprovati = this.richProroga.numGiorniApprov;
      this.idRichiestaIntegr = this.data.idRichiestaIntegr;
     
      this.getElencoProroghe();
    }
  }
  getElencoProroghe() {
    this.subscribers.elencoProroghe = this.controlliService.getElencoRichProroga(this.idRichiestaIntegr).subscribe(data => {
      if (data) {
        this.elencoProroghe = data;
        if (this.elencoProroghe.length > 1) {
          this.isShowTable = true;
          this.elencoProroghe = this.elencoProroghe.slice(1);
        }
      }
    })
  }
  loadAllegati() {
    this.subscribers.elencoAllegati = this.controlliService.getElencoAllegati(this.idControllo, 570).subscribe(data => {
      if (data.length > 0) {
        this.elencoAllegati = data;
        this.letteraAvvioControllo = this.elencoAllegati.find(f => f.idTipoDocumentoIndex === 56);
        
        this.verbaleControllo = this.elencoAllegati.find(f => f.idTipoDocumentoIndex === 7)
        this.letteraEsitoControllo = this.elencoAllegati.find(f => f.idTipoDocumentoIndex === 57)
        this.checkListControllo = this.elencoAllegati.find(f => f.idTipoDocumentoIndex === 58)
      }
    });

    if (this.idRichiestaIntegr) {

      this.subscribers.lettera = this.controlliService.getElencoAllegati(this.idRichiestaIntegr, 569).subscribe(data => {
        if (data) {
          this.letteraAccompagnatoria = data.find(f => f.idTipoDocumentoIndex === 62);
        }
      });
      console.log(this.idStatoRichiesta);
      
      // recuperare gli allegati trasmessi dal beneficiario.
      if (this.idStatoRichiesta==2) {
        this.subscribers.lettera = this.controlliService.getElencoAllegatiBeneficiario(this.idRichiestaIntegr, 569).subscribe(data => {
          if (data) {
            this.elencoAllegatiBenef = data;
            this.letteraAccompagnatoriaBeneficario = this.elencoAllegatiBenef.find(f => f.idTipoDocumentoIndex === 23 && f.flagFirmaCartacea=='I');
            if(this.elencoAllegatiBenef.length>0) this.isAllegatiBenef=true; 
          }
        });
      }
    }

    console.log("@@@@@@@@ELENCO FILES");
    
    console.log(this.elencoFile);
    
    if(this.elencoFile){
      this.fileLetteraAvvioControllo = this.elencoFile[0];
      this.fileLetteraAccompagnatoria = this.elencoFile[1];
      this.fileLetteraEsitoControllo = this.elencoFile[2];
      this.fileVerbaleControllo = this.elencoFile[3];
      this.fileCheckListControllo = this.elencoFile[4];
    }


    this.fileLetteraAvvioControllo ? this.handleFileInputAllegati(this.fileLetteraAvvioControllo, 1) : null;
    this.fileLetteraAccompagnatoria ? this.handleFileInputAllegati(this.fileLetteraAccompagnatoria, 2) : null;
    this.fileLetteraEsitoControllo ? this.handleFileInputAllegati(this.fileLetteraEsitoControllo, 3) : null;
    this.fileVerbaleControllo ? this.handleFileInputAllegati(this.fileVerbaleControllo, 4) : null;
    this.fileCheckListControllo ? this.handleFileInputAllegati(this.fileCheckListControllo, 5) : null;

  }

  handleFileInputAllegati(file: File, id: number) {
    console.log("id: " + id);
    console.log(file);


    switch (id) {
      case 1:
        this.fileLetteraAvvioControllo = file;
        this.fileAvvContrName = this.fileLetteraAvvioControllo.name;
        this.isLetteraAvvio = true;
        break;
      case 2:
        this.fileLetteraAccompagnatoria = file;
        this.fileLetteraAccompName = file.name;
        this.isLetteraAccomp = true;
        break;
      case 3:
        this.fileLetteraEsitoControllo = file;
        this.fileEsitoName = file.name;
        this.isLetteraEsito = true;

        break;
      case 4:
        this.fileVerbaleControllo = file;
        this.fileVerbaleName = file.name;
        this.isVerbaleContr = true;

        break;
      case 5:
        this.fileCheckListControllo = file;
        this.fileCheckListName = file.name;
        this.isCheckList = true;

        break;

      default:
        break;
    }
  }
  handleFileInput(file: File) {
    this.resetMessageError()
    if (file != null) {
      this.file = file;
      this.fileName = file.name;
      this.isFile = true;
    }
  }
  setFileNull() {
    this.file = null;
    this.fileName = null;
    this.isFile = false;
  }
  setFileNullAllegati(id: number) {

    this.resetMessageError();
    // this.file = null;
    // this.fileName = null;
    // this.isFile = false;

    switch (id) {
      case 1:
        this.fileLetteraAvvioControllo = null;
        this.fileAvvContrName = null;
        this.isLetteraAvvio = false;
        break;
      case 2:
        this.fileLetteraAccompagnatoria = null;
        this.fileLetteraAccompName = null;
        this.isLetteraAccomp = false;
        break;
      case 3:
        this.fileLetteraEsitoControllo = null;
        this.fileEsitoName = null;
        this.isLetteraEsito = false;
        break;
      case 4:
        this.fileVerbaleControllo = null;
        this.fileVerbaleName = null;
        this.isVerbaleContr = false;
        break;
      case 5:
        this.fileCheckListControllo = null;
        this.fileCheckListName = null;
        this.isCheckList = false;
        break;

      default:
        break;
    }

  }

  avviaIter() {
    // id = 1 corrisponde a avvio controllo in loco
    // id = 2 corrisponde a richiesta integrazione
    // isControlloFinp = 1 corrisponde a un controllo su progetto Finpiemonte.
    // isControlloFinp = 2 corrisponde a un controllo su altri progetto pbandi_t_controllo_altri.
    // se avvio controllo  = 2 si tratta dell'avvio della richiesta di integrazione
    // se avvio controllo = 1 si tratta dell'avvio del controllo in loco. 
    // chiamata al servizio iter _autorizzativi_ quando faccio sia avvio controllo_loco che avvio richiesta integrazione con id_entita = 569 per integrazione
    // id_entita = 570 per avvio controllo_locco
    // avvia iter cdu ITERAUT002 passando i parametri : id_tipo_iter = 6 entita = 570, target = controlloVO.getIdControllo(); id_progetto =  controlloVO.getIdProgetto();
    // e ricevo un esito: successo

    if (this.file.name != null) {
      let isAvvioControllo = 1;
      let isControlloFinp = 1;
      let idEntita = 570;
      this.subscribers.salvaITERAUTO002 = this.controlliService.chiamaIterAuto002(this.controllo, this.controllo.idControllo, this.idUtente, isAvvioControllo, isControlloFinp, idEntita).subscribe(data => {
        if (data == true) {
          this.subscribers.avviaIter = this.controlliService.updateControlloFinp(this.controllo, this.idUtente, 1).subscribe(data => {
            if (data) {
              console.log(data);
              this.esitoDTO = data;
              if (this.esitoDTO.esito == true) {
                const idTipodocumentoIndex = 56 // corriponde all
                const entita = 1 // corrisponde alla tabella t_controllo
                this.subscribers.salvaAllegato = this.controlliService.salvaAllegato(idTipodocumentoIndex, this.idUtente, this.fileName, this.file, this.idControllo, this.controllo.idProgetto, entita).subscribe(data => {
                  if (data) {
                    this.finalResult = data;
                    this.dialogRef.close(this.finalResult);
                  } else {
                    this.dialogRef.close(false);
                  }
                });
              } else {
                this.dialogRef.close(this.finalResult);
              }
            }
          });
        } else {
          this.dialogRef.close(data);
        }
      });
    } else {
      this.showMessageError("controllare la lettera di avvio controllo in loco");
    }
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  controllaCampi() {

    this.richiestaIntegr.idUtente = this.idUtente;
    (this.giorniScadenza != null) ? this.richiestaIntegr.numGiorniScadenza = this.giorniScadenza : this.isError = true;
    this.richiestaIntegr.idTarget = this.idControllo;
    this.richiestaIntegr.idEntita = 570;
    this.richiestaIntegr.idProgetto = this.controllo.idProgetto
    console.log(this.giorniScadenza);

    if (this.isError != true) {
      this.avviaIterIntegrazione();
      console.log(this.isError);

    } else {
      console.log("compilare tutti i campi obbligatori");
      console.log(this.isError);


    }
  }

  avviaIterIntegrazione() {

    // id = 1 corrisponde a avvio controllo in loco
    // id = 2 corrisponde a richiesta integrazione
    // isControlloFinp = 1 corrisponde a un controllo su progetto Finpiemonte.
    // isControlloFinp = 2 corrisponde a un controllo su altri progetto pbandi_t_controllo_altri.
    // se avvio controllo  = 2 si tratta dell'avvio della richiesta di integrazione
    // se avvio controllo = 1 si tratta dell'avvio del controllo in loco. 
    // chiamata al servizio iter _autorizzativi_ quando faccio sia avvio controllo_loco che avvio richiesta integrazione con id_entita = 569 per integrazione
    // id_entita = 570 per avvio controllo_locco
    // avvia iter cdu ITERAUT002 passando i parametri : id_tipo_iter = 6 entita = 570, target = controlloVO.getIdControllo(); id_progetto =  controlloVO.getIdProgetto();
    // e ricevo un esito: successo
    // let isAvvioControllo = 1;
    // let isControlloFinp = 1;
    // let idEntita = 569;
    // this.subscribers.salvaITERAUTO002 = this.controlliService.chiamaIterAuto002(this.controllo, this.idUtente, isAvvioControllo, isControlloFinp, idEntita).subscribe(data => {
    //   if (data == true) {
    //   }
    // });

    if (this.file.name != null) {


      this.subscribers.avviaIterIntegrazione = this.controlliService.avviaIterIntegrazione(this.richiestaIntegr, this.idUtente).subscribe(data => {
        if (data) {
          this.idTargetRichiesta = data;
          if (this.idTargetRichiesta != null) {
            const idTipodocumentoIndex = 62 // corriponde all
            const entita = 2 // corrisponde alla tabella t_richiesta_integrazione
            this.subscribers.salvaAllegato = this.controlliService.salvaAllegato(idTipodocumentoIndex, this.idUtente, this.fileName, this.file, this.idTargetRichiesta, this.controllo.idProgetto, entita).subscribe(data => {
              if (data) {
                this.finalResult = data;
                this.dialogRef.close(this.finalResult);
              }
            });
          }
        } else {
          this.dialogRef.close(false);
        }
      });
    } else {
      this.showMessageError("controllare la lettera accompagnatoria")
    }

  }

  chiudiRichIntegr() {
    this.subscribers.chiudiRichIntegr = this.controlliService.chiudiRichiestaIntegr(this.idRichiestaIntegr, this.idUtente).subscribe(data => {
      if (data) {
        this.finalResult = data;
        this.dialogRef.close(this.finalResult);
      }
    })
  }
  gestisciProroga(id: number) {

    this.richProroga.numGiorniApprov = this.giorniApprovati;
    this.subscribers.gestisciProroga = this.controlliService.gestisciProroga(this.richProroga, this.idUtente, id).subscribe({
      next: (risposta) => {
        this.result = risposta;
        this.dialogRef.close(this.result);
      },
      error: (errore) => console.log(errore),
      complete: () => console.log("completato")
    })

  }
  downloadAllegato(item: DocumentoIndexVO) {
    //this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), item.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, item.nomeDocumento);
      }
      //  this.loadedDownload = true;
    });
  }


  confermaAllegati() {
    
    // this.elencoFile.push(this.fileLetteraAvvioControllo);
    // this.elencoFile.push(this.fileLetteraAccompagnatoria);
    // this.elencoFile.push(this.fileLetteraEsitoControllo);
    // this.elencoFile.push(this.fileVerbaleControllo);
    // this.elencoFile.push(this.fileCheckListControllo);

    this.elencoFile[0]= this.fileLetteraAvvioControllo; 
    this.elencoFile[1]= this.fileLetteraAccompagnatoria; 
    if(this.fileLetteraEsitoControllo){
      this.elencoFile[2]= this.fileLetteraEsitoControllo; 
    } else if(this.letteraEsitoControllo){
        this.archivioFileService.leggiFile(this.configService.getApiURL(), this.letteraEsitoControllo.idDocumentoIndex.toString()).subscribe(res => {
        if (res) {
          this.elencoFile[2]= new File([res], this.letteraEsitoControllo.nomeDocumento);;
         // saveAs(res, this.letteraEsitoControllo.nomeDocumento);
        }
      });
    }
    
    if(this.fileVerbaleControllo){
      this.elencoFile[3]= this.fileVerbaleControllo; 
    } else if(this.verbaleControllo){
      this.archivioFileService.leggiFile(this.configService.getApiURL(), this.verbaleControllo.idDocumentoIndex.toString()).subscribe(res => {
        if (res) {
          this.elencoFile[3]= new File([res], this.verbaleControllo.nomeDocumento);;
         // saveAs(res, this.letteraEsitoControllo.nomeDocumento);
        }
      });
    }

    if(this.fileCheckListControllo){
      this.elencoFile[4]= this.fileCheckListControllo; 
    } else if(this.checkListControllo){
      this.archivioFileService.leggiFile(this.configService.getApiURL(), this.checkListControllo.idDocumentoIndex.toString()).subscribe(res => {
        if (res) {
          this.elencoFile[3]= new File([res], this.checkListControllo.nomeDocumento);;
         // saveAs(res, this.letteraEsitoControllo.nomeDocumento);
        }
      });
    }
  
    this.dialogRef.close(this.elencoFile);
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
