/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { ActivatedRoute, Router } from '@angular/router';
import { ChecklistHtmlDTO } from '../../../shared/commons/dto/checklisthtml-dto';
import { CheckListService } from '../../services/checkList.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { ViewEncapsulation } from '@angular/core';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { AllegaVerbaleDialogComponent } from '../allega-verbale-dialog/allega-verbale-dialog.component';
import { DatiProgettoAttivitaPregresseDialogComponent } from '@pbandi/common-lib';
import { ConfigService } from 'src/app/core/services/config.service';
import { ValidazioneService } from 'src/app/validazione/services/validazione.service';
import { EsitoValidazioneDichSpesaComponent } from 'src/app/validazione/components/esito-validazione-dich-spesa/esito-validazione-dich-spesa.component';

declare const inizializzaCheckList: any;      //funzione definita in assets/js/checklist2.js
declare const salvaChecklistDefinitivo: any;  //funzione definita in assets/js/checklist2.js
declare const salvaChecklistInBozza: any;  //funzione definita in assets/js/checklist2.js

declare const inizializzaCheckListValidazione: any;      //funzione definita in assets/js/checklistValidazione2.js
declare const wPrint: any;//funzione definita in assets/js/checklistValidazione2.js
declare const updateValuesCLValidazione: any;
declare const verifichePreSalvaChecklistValidazioneDefinitivo;

@Component({
  selector: 'app-nuova-checklist',
  templateUrl: './nuova-checklist.component.html',
  styleUrls: ['./nuova-checklist.component.scss'],
  encapsulation: ViewEncapsulation.None   //PK : da mettere affinche vengano applicate le classi degi stili al parametro "html" 
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class NuovaChecklistComponent implements OnInit, AfterViewInit {

  idOperazione: number;
  codiceProgetto: string;
  idProgetto: number;
  idBandoLinea: number;
  idDichiarazione: number;
  idDocumentoIndex: number; //valorizzato solo in modifica, altrimenti null
  codTipoChecklist: string;  //CLILA, CLA,
  statoCheckList: string;  // //B o D
  htmlModelloCheckList: string = '';  //se non lo inizializzo a video vedo per pochi sec "undefined"
  checklistHtmlDTO: ChecklistHtmlDTO;

  user: UserInfoSec;
  //tipoChecklist: string = "CLIL"; //CLIL Constants.COD_TIPO_CHEKCLIST_IN_LOCO

  viewButtonSalvaChecklistDefinitivo: boolean = true; // per visualizzare o meno il bottone
  viewButtonSalvaChecklistInBozza: boolean = true; // per visualizzare o meno il bottone
  checklistHtml: string;
  statoChecklist: string;
  messageError: string;
  isMessageErrorVisible: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  // Costante Bandi FP
  isFP: boolean = true;

  //visualizzazione modifiche
  isVisible: boolean = false;

  //LOADED
  loadedSalva: boolean = true;
  loadedModulo: boolean;
  loadedInizializza: boolean;
  loadedSave: boolean = true;
  idControllo: number;
  idSatoChecklist: number; 
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private checkListService: CheckListService,
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private configService: ConfigService,
    private dialog: MatDialog,
    private validazioneService: ValidazioneService,
  ) {
    //private router: Router
  };

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];

    });
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = params['idBandoLinea'] ? +params['idBandoLinea'] : null;
      this.idDocumentoIndex = params['idDocumentoIndex'] ? +params['idDocumentoIndex'] : null;
      this.codTipoChecklist = params['codiceTipo'];
      this.idDichiarazione = params['idDichiarazione'] ? +params['idDichiarazione'] : null;
    
    });

    this.activatedRoute.queryParams.subscribe(params => {
      this.idControllo = (params['idControllo']!=undefined)?params['idControllo'] : null;
    });
    
    console.log('this.idBandoLinea');
    console.log(this.idBandoLinea);

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.loadedInizializza = false;
        this.subscribers.init = this.checkListService.inizializzaGestioneChecklist(this.idProgetto).subscribe(data => {
          if (data) {
            this.codiceProgetto = data.codiceVisualizzatoProgetto;
          }
          this.loadedInizializza = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di inizializzazione.");
          this.loadedInizializza = true;
        });

        if (this.idDocumentoIndex != null) {
          // carico la checklist dal DB
          console.log('ANG nuova-checklist carico checklist da DB');
          this.caricaChecklist();
        } else {
          console.log('ANG nuova-checklist carico checklist nuova');

          this.nuovaChecklist();
        }

      }
    }, err => {
      console.log('ANG nuova-checklist err=' + err);
      this.handleExceptionService.handleNotBlockingError(err);
    });

    this.isVisibleFunc();
    this.isFpFunc();
  }


  isVisibleFunc() {
    // Controllo se abilitare le nuove finzioni e disabilitare le vecchie in base all'ambiente
    this.subscribers.costanteFin = this.userService.isCostanteFinpiemonte().subscribe(data => {
      console.log("isCostanteFinpiemonte: ", data)

      this.isVisible = data;
    })
  }

  isFpFunc() {
    console.log("THIS:IDBANDOLINEA", this.idBandoLinea);
    this.subscribers.bandoFin = this.userService.isBandoCompetenzaFinpiemonte(this.idBandoLinea).subscribe(data => {
      this.isFP = data;
      console.log("IS FP: ", this.isFP)
    })
  }


  caricaChecklist() {
    console.log("ANG nuova-checklist  caricaChecklist BEGIN");

    //it.csi.pbandi.pbandiweb.business.CPBEGestioneCheckListHtml.inizializzaCP

    if (this.codTipoChecklist == 'CLIL') {
      this.loadedModulo = false;

      //recupero l'html della checklist dal DB
      this.subscribers.checklistHtmlDTO = this.checkListService.getCheckListInLocoHtml(this.idDocumentoIndex).subscribe(res => {
        console.log('ANG nuova-checklist getCheckListInLocoHtml res=' + res);

        this.htmlModelloCheckList = res.contentHtml;
        this.checklistHtmlDTO = res;

        console.log('ANG nuova-checklist getCheckListInLocoHtml codStatoTipoDocIndex=' + this.checklistHtmlDTO.codStatoTipoDocIndex);
        console.log('ANG nuova-checklist getCheckListInLocoHtml idDocumentoIndex=' + this.checklistHtmlDTO.idDocumentoIndex);

        this.funzInizializzaCheckList();
        this.loadedModulo = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento della checklist.");
        this.loadedModulo = true;
      });

    } else {

      if (this.codTipoChecklist === 'CL') {
        this.viewButtonSalvaChecklistInBozza = false;
      }

      this.loadedModulo = false;
      this.subscribers.checklistHtmlDTO = this.checkListService.getCheckListValidazioneHtml(this.idDocumentoIndex, this.idDichiarazione, this.idProgetto).subscribe(res => {
        console.log('ANG nuova-checklist res=' + res);

        this.htmlModelloCheckList = res.contentHtml;
        this.checklistHtmlDTO = res;

        console.log('ANG nuova-checklist getCheckListValidazioneHtmlcodStatoTipoDocIndex=' + this.checklistHtmlDTO.codStatoTipoDocIndex);
        console.log('ANG nuova-checklist getCheckListValidazioneHtml idDocumentoIndex=' + this.checklistHtmlDTO.idDocumentoIndex);

        this.funzInizializzaCheckList();
        this.loadedModulo = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento della checklist.");
        this.loadedModulo = true;
      });

    }
    console.log("ANG nuova-checklist  caricaChecklist END");
  }

  nuovaChecklist(): any {
    console.log("ANG nuova-checklist  nuovaChecklist BEGIN");
    this.loadedModulo = false;
    this.subscribers.checklistHtmlDTO = this.checkListService.getModuloCheckList(this.idProgetto).subscribe(res => {
      console.log('ANG nuova-checklist res=' + res);

      this.htmlModelloCheckList = res.contentHtml;
      this.checklistHtmlDTO = res;

      this.codTipoChecklist = "CLIL";

      console.log('ANG nuova-checklist codStatoTipoDocIndex=' + this.checklistHtmlDTO.codStatoTipoDocIndex);
      console.log('ANG nuova-checklist idDocumentoIndex=' + this.checklistHtmlDTO.idDocumentoIndex);
      //   console.log('ANG nuova-checklist tipoChecklist=' + this.tipoChecklist);
      console.log('ANG nuova-checklist statoCheckList=' + this.statoCheckList);

      //   if (this.tipoChecklist == 'CL' && this.statoCheckList == 'B') {
      //     this.viewButtonSalvaChecklistDefinitivo = false;
      //   } else {
      this.viewButtonSalvaChecklistDefinitivo = true;
      //   }
      console.log('ANG nuova-checklist viewButtonSalvaChecklistDefinitivo=' + this.viewButtonSalvaChecklistDefinitivo);

      if (this.statoCheckList == 'D') {
        this.viewButtonSalvaChecklistInBozza = false;
      } else {
        this.viewButtonSalvaChecklistInBozza = true;
      }
      console.log('ANG nuova-checklist viewButtonSalvaChecklistInBozza=' + this.viewButtonSalvaChecklistInBozza);

      this.funzInizializzaCheckList();
      this.loadedModulo = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento della checklist.");
      this.loadedModulo = true;
    });
    console.log("ANG nuova-checklist  nuovaChecklist END");
  }

  ngAfterViewInit() {
    console.log("ANG nuova-checklist  ngAfterViewInit BEGIN-END");
    //this.funzInizializzaCheckList();
  }

  funzInizializzaCheckList() {
    console.log("ANG nuova-checklist  funzInizializzaCheckList BEGIN");

    //PK : forse l'html che importo e' troppo grosso, non riesco ad accedere direttamente al suo DOM
    // devo usare setTimeout.... anche solo 10ms bastano
    // chiamo una funziona Angular che chiama la funzione js
    setTimeout(() => { this.inizializzaCheckListANgular(), 2 });
    console.log("ANG nuova-checklist  funzInizializzaCheckList END");
  }

  funzBtSalvaChecklistInBozza() {
    this.resetMessageError();
    this.idSatoChecklist = 1; 
    console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza BEGIN");

    this.loadedSalva = false;
    salvaChecklistInBozza();  // per fare     updateValues(); !!! serve???

    this.statoCheckList = "B";

    if (document.getElementById('fixH') != null) {
      document.getElementById('fixH').remove();
    }

    // console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza tipoChecklist=" + this.tipoChecklist);
    console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza idProgetto=" + this.idProgetto);
    console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza statoCheckList=" + this.statoCheckList);
    console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza idDichiarazione=" + this.idDichiarazione);

    var t = document.getElementById('checklistHtmlDiv').outerHTML;
    //console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza checklistHtmlDiv="+ t);

    var idChecklist = '';
    if (this.idDichiarazione != null) {
      idChecklist = idChecklist + this.idDichiarazione;
    }

    this.subscribers.save = this.checkListService.saveCheckListInLocoHtml(this.statoCheckList, t, idChecklist, this.idProgetto, this.user).subscribe(data => {
      console.log("ANG nuova-checklist funzBtSalvaChecklistInBozza chiamo servizio rest");
      if (data) {
        console.log(data);
        if (data.esito) {
          console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza esito OK");

          this.goToGestioneChecklist("Salvataggio avvenuto con successo.", this.idSatoChecklist);

        } else {
          console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza esito KO");
          this.showMessageError("Errore in fase di salvataggio.");
        }
      }
      this.loadedSalva = true;
    }), err => {
      this.handleExceptionService.handleNotBlockingError(err);
      console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza errore");
      this.showMessageError("Errore in fase di salvataggio.");
      this.loadedSalva = true;
    };

    console.log("ANG nuova-checklist  funzBtSalvaChecklistInBozza END");
  }



  inizializzaCheckListANgular() {
    console.log("ANG nuova-checklist  inizializzaCheckListANgular BEGIN");
    inizializzaCheckList();
    console.log("ANG nuova-checklist  inizializzaCheckListANgular END");
  }
  // tasto conferma nel box giallo è disabilitato finche non c'è l'allegato 


  /* funzSalvaChecklistValidazioneDefinitivo() {
      const idDichiarazioneDiSpesa = this.idDichiarazione;
  if (this.isVisible && this.htmlModelloCheckList.indexOf("TESTO DA CONCORDARE") != -1) {
        this.loadedSave = true;
        let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
          width: '50%',
          data: {
            required: "N",
            codTipoChecklist: "CL"
          }
        });
        dialogRef.afterClosed().subscribe(res => {
          if (res.esito === "S") {
            if (this.isFP) {
              let dialogRef = this.dialog.open(EsitoValidazioneDichSpesaComponent, {
                width: '60%',
                maxHeight: '90%',
                disableClose: true,
                data: {
                  idBandoLinea: this.idBandoLinea,
                  idDichiarazioneDiSpesa: idDichiarazioneDiSpesa,
                  idProgetto: this.idProgetto
                }
              });
              dialogRef.afterClosed().subscribe(data => {
                console.log(data)
                if (data.esito == "N") {
  
                } else {
                  this.loadedSave = false;
                  updateValuesCLValidazione();
                  if (document.getElementById('checklistHtmlDiv') != null) {
                    this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
                  }
                  this.loadedSave = false;
                  updateValuesCLValidazione();
                }
              })
            } else {
              this.loadedSave = false;
              updateValuesCLValidazione();
              if (document.getElementById('checklistHtmlDiv') != null) {
                this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
              }
              this.loadedSave = false;
            }
          }
        });
      } else {
  
        if (!verifichePreSalvaChecklistValidazioneDefinitivo()) {
  
        } else {
          this.loadedSave = true;
          let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
            width: '50%',
            data: {
              required: "N",
              codTipoChecklist: "CL"
            }
          });
          dialogRef.afterClosed().subscribe(res => {
            if (res.esito === "S") {
              if (this.isFP) {
                let dialogRef = this.dialog.open(EsitoValidazioneDichSpesaComponent, {
                  width: '60%',
                  maxHeight: '90%',
                  disableClose: true,
                  data: {
                    idBandoLinea: this.idBandoLinea,
                    idDichiarazioneDiSpesa: idDichiarazioneDiSpesa,
                    idProgetto: this.idProgetto
                  }
                });
  
                dialogRef.afterClosed().subscribe(data => {
                  console.log(data)
                  if (data.esito == "N") {
  
                  } else {
                    this.loadedSave = false;
                    updateValuesCLValidazione();
                    if (document.getElementById('checklistHtmlDiv') != null) {
                      this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
                    }
                    this.loadedSave = false;
                  }
                })
  
              } else {
                this.loadedSave = false;
                updateValuesCLValidazione();
                if (document.getElementById('checklistHtmlDiv') != null) {
                  this.checklistHtml = document.getElementById('checklistHtmlDiv').outerHTML;
                }
                this.loadedSave = false;
              }
            }
          });
        }
      }
    } */




  funzSalvaChecklistDefinitivo() {
    this.resetMessageError();
    this.loadedSalva = false;
    this.statoCheckList = "D";
    this.idSatoChecklist = 2; 
    inizializzaCheckList();
    if (salvaChecklistDefinitivo() == "OK") {

      var t = document.getElementById('checklistHtmlDiv').outerHTML;

      //  CHECK LIST IN LOCO NON CI INTERESSA
      if (this.codTipoChecklist == 'CLIL') {

        this.loadedSalva = true;
        let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
          width: '50%',
          data: {
            required: "S",
            codTipoChecklist: this.codTipoChecklist
          }
        });
        dialogRef.afterClosed().subscribe((res) => {
          if (res.esito === "S" && res.files && res.files.length > 0) {
            this.subscribers.save = this.checkListService.saveCheckListDefinitivo(this.statoCheckList, t,
              this.idDichiarazione, this.idProgetto, this.user, res.files).subscribe(data => {
                if (data) {
                  console.log(data);
                  if (data.esito) {
                    this.goToGestioneChecklist("Salvataggio avvenuto con successo.", this.idSatoChecklist);

                  } else {
                    this.showMessageError("Errore in fase di salvataggio.");
                  }
                }
                this.loadedSalva = true;
              }), err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase salvataggio.");
                this.loadedSalva = true;
              };

          }
        });

      }

      //  CHECKLIST IN VALIDAZIONE CI INTERESSA
      else {

        if (this.codTipoChecklist == 'CL') {
          this.loadedSalva = true;
          let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
            width: '50%',
            data: {
              required: "N",
              codTipoChecklist: this.codTipoChecklist
            }
          });
          dialogRef.afterClosed().subscribe(res => {
            if (this.isVisible === true && this.isFP === true) {
              console.log(this.statoCheckList);
              //METODO DELLA CHECKLIST DILLA INIZIO
              if (res.esito === "S") {
                let dialogRef = this.dialog.open(EsitoValidazioneDichSpesaComponent, {
                  width: '60%',
                  maxHeight: '90%',
                  disableClose: true,
                  data: {
                    idBandoLinea: this.idBandoLinea,
                    idDichiarazioneDiSpesa: this.idDichiarazione,
                    idProgetto: this.idProgetto,
                    statoCheckList: this.statoCheckList,
                    t: t,
                    idDichiarazione: this.idDichiarazione.toString(),
                    user: this.user,
                    files: res.files,
                    isChiudiValidazione: "0"
                  }
                });

                dialogRef.afterClosed().subscribe(data => {
                  console.log(data)
                  if (data.esito == true) {
                    this.goToGestioneChecklist("Salvataggio avvenuto con successo.");

                  } else {
                    this.showMessageError("Errore in fase di salvataggio.");
                  }

                  this.loadedSalva = true;

                })

              }


            }

            else {

              if (res.esito === "S") {

                this.loadedSalva = false;
                this.subscribers.save = this.checkListService.saveCheckListDocumentaleHtml(this.statoCheckList, t,
                  this.idDichiarazione, this.idProgetto, this.user, res.files).subscribe(data => {

                    if (data) {
                      console.log(data);
                      if (data.esito) {
                        this.goToGestioneChecklist("Salvataggio avvenuto con successo.");

                      } else {
                        this.showMessageError("Errore in fase di salvataggio.");
                      }
                    }
                    this.loadedSalva = true;

                  }), err => {
                    this.handleExceptionService.handleNotBlockingError(err);
                    this.showMessageError("Errore in fase di salvataggio.");
                    this.loadedSalva = true;
                  };
              }
            }
          });
        }

        //        CHECKLIST AFFIDAMENT PROBABILMENTE CLILA

        else {

          var idChecklist = ""; //TODO : PK valorizzare ????
          this.subscribers.save = this.checkListService.saveCheckListDocumentaleHtml(this.statoCheckList, t,
            this.idDichiarazione, this.idProgetto, this.user, null).subscribe(data => {
              if (data) {
                console.log(data);
                if (data.esito) {
                  this.goToGestioneChecklist("Salvataggio avvenuto con successo.");

                } else {
                  this.showMessageError("Errore in fase di salvataggio.");
                }
              }
              this.loadedSalva = true;
            }), err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di salvataggio.");
              this.loadedSalva = true;
            };
        }
      }

    } else {
      this.loadedSalva = true;
    }
  }







  /* funzSalvaChecklistDefinitivo() {
    this.resetMessageError();
    this.loadedSalva = false;
    this.statoCheckList = "D";

    inizializzaCheckList();
    if (salvaChecklistDefinitivo() == "OK") {
      var t = document.getElementById('checklistHtmlDiv').outerHTML;
      if (this.codTipoChecklist == 'CLIL') {
        this.loadedSalva = true;
        let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
          width: '50%',
          data: {
            required: "S",
            codTipoChecklist: this.codTipoChecklist
          }
        });
        dialogRef.afterClosed().subscribe((res) => {
          if (res.esito === "S" && res.files && res.files.length > 0) {
            this.subscribers.save = this.checkListService.saveCheckListDefinitivo(this.statoCheckList, t,
              this.idDichiarazione, this.idProgetto, this.user, res.files).subscribe(data => {
                console.log("ANG nuova-checklist saveCheckListDocumentaleHtml chiamo servizio rest");
                if (data) {
                  console.log(data);
                  if (data.esito) {
                    this.goToGestioneChecklist("Salvataggio avvenuto con successo.");

                  } else {
                    this.showMessageError("Errore in fase di salvataggio.");
                  }
                }
                this.loadedSalva = true;
              }), err => {
                this.handleExceptionService.handleNotBlockingError(err);
                this.showMessageError("Errore in fase salvataggio.");
                this.loadedSalva = true;
              };

          }
        });

      } else {
        if (this.codTipoChecklist == 'CL') {
          this.loadedSalva = true;
          let dialogRef = this.dialog.open(AllegaVerbaleDialogComponent, {
            width: '50%',
            data: {
              required: "N",
              codTipoChecklist: this.codTipoChecklist
            }
          });
          dialogRef.afterClosed().subscribe(res => {
            if (res.esito === "S") {
              this.loadedSalva = false;
              this.subscribers.save = this.checkListService.saveCheckListDocumentaleHtml(this.statoCheckList, t,
                this.idDichiarazione, this.idProgetto, this.user, res.files).subscribe(data => {
                  if (data) {
                    console.log(data);
                    if (data.esito) {
                      this.goToGestioneChecklist("Salvataggio avvenuto con successo.");

                    } else {
                      this.showMessageError("Errore in fase di salvataggio.");
                    }
                  }
                  this.loadedSalva = true;
                }), err => {
                  this.handleExceptionService.handleNotBlockingError(err);
                  this.showMessageError("Errore in fase di salvataggio.");
                  this.loadedSalva = true;
                };
            }
          });
        } else {

          var idChecklist = ""; 
          this.subscribers.save = this.checkListService.saveCheckListDocumentaleHtml(this.statoCheckList, t,
            this.idDichiarazione, this.idProgetto, this.user, null).subscribe(data => {
              if (data) {
                console.log(data);
                if (data.esito) {
                  this.goToGestioneChecklist("Salvataggio avvenuto con successo.");

                } else {
                  this.showMessageError("Errore in fase di salvataggio.");
                }
              }
              this.loadedSalva = true;
            }), err => {
              this.handleExceptionService.handleNotBlockingError(err);
              this.showMessageError("Errore in fase di salvataggio.");
              this.loadedSalva = true;
            };
        }
      }

    } else {
      this.loadedSalva = true;
    }
  } */

  goToGestioneChecklist(message?: string, idSatoChecklist?: number) {
    let url: string;
    if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CHECKLIST) {
      url = "drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CHECKLIST + "/checklist/" + this.idProgetto + "/" + this.idBandoLinea;
    } else if (this.idOperazione === Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA) {
      url = "drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA + "/gestioneChecklistSpesaValidata/" + this.idProgetto + "/" + this.idBandoLinea;
    } else if (this.idOperazione === Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI) {
      url = "drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI + "/checklistControlli/" + this.idProgetto + "/" + this.idBandoLinea + "/" + this.idControllo + "/"+ idSatoChecklist;
    }
    if (message) {
      url += ";message=" + message;
    }
    this.router.navigateByUrl(url);
  }

  datiProgettoEAttivitaPregresse() {
    this.dialog.open(DatiProgettoAttivitaPregresseDialogComponent, {
      width: '90%',
      maxHeight: '95%',
      data: {
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedSalva || !this.loadedModulo || !this.loadedInizializza) {
      return true;
    }
    return false;
  }

}