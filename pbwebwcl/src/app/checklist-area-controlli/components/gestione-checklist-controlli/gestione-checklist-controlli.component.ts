/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ArchivioFileService, DatiProgettoAttivitaPregresseDialogComponent, VisualizzaContoEconomicoDialogComponent } from '@pbandi/common-lib';
import { CheckListItem } from 'src/app/checklist/commons/dto/CheckListItem';
import { CheckListService } from 'src/app/checklist/services/checkList.service';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { saveAs } from 'file-saver-es';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { Constants } from 'src/app/core/commons/util/constants';
import { SharedService } from 'src/app/shared/services/shared.service';
import { DatePipe } from '@angular/common';
import { NavigationChecklistService } from 'src/app/checklist/services/navigation-checklist.service';
import { FiltroRicercaChecklist } from 'src/app/checklist/commons/dto/filtro-ricerca-checklist';
import { CodiceDescrizioneDTO } from 'src/app/gestione-spesa-validata/commons/dto/codice-descrizione-dto';
import { FormControl } from '@angular/forms';
import { ChecklistTableComponent } from 'src/app/checklist/components/checklist-table/checklist-table.component';

@Component({
  selector: 'app-gestione-checklist-controlli',
  templateUrl: './gestione-checklist-controlli.component.html',
  styleUrls: ['./gestione-checklist-controlli.component.scss']
})
export class GestioneChecklistControlliComponent implements OnInit {
  user: UserInfoSec;
  idOperazione: number;
  idProgetto: number;
  idBandoLinea: number;
  codiceProgetto: string;
  modificaChecklistAmmessa: boolean;
  eliminazioneChecklistAmmessa: boolean;
  criteriRicercaOpened: boolean = true;

  isAdgIstMaster: boolean;
  isOiIstMaster: boolean;

  dichiarazioniSpesa: Array<CodiceDescrizioneDTO>;
  dichiarazioneSpesaSelected: CodiceDescrizioneDTO;
  dataControllo: FormControl = new FormControl();
  soggettoControllo: string;
  stati: Array<CodiceDescrizioneDTO>;
  statoSelected: CodiceDescrizioneDTO;
  tipologiaSelected: string = "CLIL";
  rilevazioneSelected: string = "N";
  versione: string;

  elencoChecklist: Array<CheckListItem>;

  @ViewChild(ChecklistTableComponent) tableComponent: ChecklistTableComponent;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;
  //visualizzazione modifiche
  isVisible: boolean = false;
  // Costante Bandi FP
  isFP: boolean = true;

  //LOADED
  loadedInizializza: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedDichiarazioni: boolean;
  loadedStati: boolean;
  loadedRicerca: boolean = true;
  loadedDownload: boolean = true;
  loadedElimina: boolean = true;
  loadedSalvaLock: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};
  idControllo: any;
  idStatoChecklist: number;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private configService: ConfigService,
    private userService: UserService,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    private checkListService: CheckListService,
    private datePipe: DatePipe,
    private archivioFileService: ArchivioFileService,
    private navigationService: NavigationChecklistService,
    private dialog: MatDialog
  ) { }


  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.pathFromRoot[1].params.subscribe(params => {
      this.idOperazione = +params['id'];
    });

    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idControllo = +params['idControllo'];
      this.idStatoChecklist= +params['idStatoChecklist'];
      
      this.isVisibleFunc();
      this.isFpFunc();

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;

          if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER) {
            this.isAdgIstMaster = true;
          } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER) {
            this.isOiIstMaster = true;
          }

          this.loadedInizializza = false;
          this.subscribers.init = this.checkListService.inizializzaGestioneChecklist(this.idProgetto).subscribe(data => {
            if (data) {
              this.codiceProgetto = data.codiceVisualizzatoProgetto;
              this.modificaChecklistAmmessa = data.modificaChecklistAmmessa;
              this.eliminazioneChecklistAmmessa = data.eliminazioneChecklistAmmessa;
              this.loadData();
              this.ricerca();
              if (this.activatedRoute.snapshot.paramMap.get('message')) {
                this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('message'));
              }
            }
            this.loadedInizializza = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.showMessageError("Errore in fase di inizializzazione.");
            this.loadedInizializza = true;
          });
        }
      });
    });


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

  loadData() {
    if (this.navigationService.filtroRicercaChecklistSelezionato) {
      this.dataControllo = this.navigationService.filtroRicercaChecklistSelezionato.dataControllo;
      this.soggettoControllo = this.navigationService.filtroRicercaChecklistSelezionato.soggettoControllo;
      this.tipologiaSelected = this.navigationService.filtroRicercaChecklistSelezionato.tipologiaSelected;
      this.rilevazioneSelected = this.navigationService.filtroRicercaChecklistSelezionato.rilevazioneSelected;
      this.versione = this.navigationService.filtroRicercaChecklistSelezionato.versione;
    }
    this.loadedDichiarazioni = false;
    this.subscribers.dichiarazioni = this.checkListService.caricaDichiarazioneDiSpesa(this.idProgetto, this.isVisible && this.isFP).subscribe(data => {
      console.log('ANG checklist caricaDichiarazioneDiSpesa data=' + data);
      if (data) {
        this.dichiarazioniSpesa = data;
        if (this.navigationService.filtroRicercaChecklistSelezionato && this.navigationService.filtroRicercaChecklistSelezionato.dichiarazioneSpesaSelected) {
          this.dichiarazioneSpesaSelected = this.dichiarazioniSpesa.find(d => d.codice === this.navigationService.filtroRicercaChecklistSelezionato.dichiarazioneSpesaSelected.codice);
        }
      }
      this.loadedDichiarazioni = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      console.log('ANG checklist ERR');
      this.showMessageError("Errore in fase di caicamento delle dichiarazioni di spesa.");
      this.loadedDichiarazioni = true;
    });
    this.loadedStati = false;
    this.subscribers.stati = this.checkListService.caricaStatoSoggetto(this.user.idUtente, this.user.idIride).subscribe(data => {
      console.log('ANG checklist caricaStatoSoggetto data=' + data);
      if (data) {
        this.stati = data;
        if (this.navigationService.filtroRicercaChecklistSelezionato && this.navigationService.filtroRicercaChecklistSelezionato.statoSelected) {
          this.statoSelected = this.stati.find(s => s.codice === this.navigationService.filtroRicercaChecklistSelezionato.statoSelected.codice);
        }
      }
      this.loadedStati = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      console.log('ANG checklist ERR');
      this.showMessageError("Errore in fase di carimento degli stati.");
      this.loadedStati = true;
    });
  }

  ricerca() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedRicerca = false;
    this.subscribers.save = this.checkListService.cercaChecklist(this.dichiarazioneSpesaSelected ? +this.dichiarazioneSpesaSelected.codice : null,
      this.dataControllo && this.dataControllo.value ? this.datePipe.transform(this.dataControllo.value, 'dd/MM/yyyy') : null,
      this.soggettoControllo ? this.soggettoControllo : null,
      this.statoSelected ? [this.statoSelected.codice] : null,
      this.tipologiaSelected, this.rilevazioneSelected,
      this.versione ? this.versione : null, this.idProgetto).subscribe(data => {
        console.log('ANG checklist cercaChecklist data=' + data);
        if (data) {
          this.criteriRicercaOpened = false;
          this.elencoChecklist = data;
        }
        this.loadedRicerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ricerca.");
        console.log('ANG checklist cercaChecklist ERR');
        this.loadedRicerca = true;
      });

  }

  download(item: CheckListItem) {
    this.resetMessageError();
    this.resetMessageSuccess()
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), item.idDocumentoIndex.toString()).subscribe(res => {
      if (res) {
        saveAs(res, item.nome);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  elimina(item: CheckListItem) {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "L’operazione eliminerà definitivamente la check-list. Continuare?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.eliminaDoc = this.checkListService.eliminaChecklist(item.idDocumentoIndex).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.ricerca();
              this.showMessageSuccess(data.messaggio);
            }
            else {
              this.showMessageError(data.messaggio)
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore in fase di eliminazione del file.");
        });
      }
    });
  }

  modifica(item: CheckListItem) {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (item.codiceTipo === "CL" && item.codiceStato === "I") {
      this.showMessageError("Checklist non modificabile perché inviata.");
      return;
    }
    if (item.codiceTipo === "CLIL" && item.codiceStato === "D") {
      this.showMessageError("Checklist non modificabile perché in stato definitivo.");
      return;
    }
    if (item.codiceTipo === "CLIL") {
      this.salvaLockCheckListInLoco(item);
    } else {
      this.salvaLockCheckListValidazione(item);
    }
  }

  salvaLockCheckListInLoco(item: CheckListItem) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedSalvaLock = false;
    this.subscribers.salvaLockLoco = this.checkListService.salvaLockCheckListInLoco(this.idProgetto, item.idDocumentoIndex).subscribe(data => {
      if (data) {
        if (data.esito) {
          //go to modifica
          console.log("checklist.component.ts NAVIGATE TO MODIFICA CHECKLIST");
          this.saveDataForNavigation();
          let url =`drawer/${Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI}/modificaChecklist/${this.idProgetto}/${this.idBandoLinea}/${item.idDocumentoIndex}/${item.codiceTipo}/${item.idDichiarazione}`; 
          url += `?&idControllo=${this.idControllo}`;
          this.router.navigateByUrl(url);
          //PK se passo l'idChecklist (equivale a idDichiarazione) dall'altra parte carico la checklist dal db

        } else {
          this.showMessageError(data.codiceMessaggio);
        }
      }
      this.loadedSalvaLock = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSalvaLock = true;
      this.showMessageError("Errore in fase di salvataggio lock.");
    });
  }

  salvaLockCheckListValidazione(item: CheckListItem) {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedSalvaLock = false;
    this.subscribers.salvaLockLoco = this.checkListService.salvaLockCheckListValidazione(this.idProgetto, item.idDichiarazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          //go to modifica
          console.log("NAVIGATE TO MODIFICA CHECKLIST VALIDAZIONE");
          this.saveDataForNavigation();
          let url =`drawer/${Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI}/modificaChecklist/${this.idProgetto}/${this.idBandoLinea}/${item.idDocumentoIndex}/${item.codiceTipo}/${item.idDichiarazione}`; 
          url += `?&idControllo=${this.idControllo}`;
          this.router.navigateByUrl(url);
          //this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI}/modificaChecklist/${this.idProgetto}/${this.idBandoLinea}/${item.idDocumentoIndex}/${item.codiceTipo}/${item.idDichiarazione}`);
        } else {
          this.showMessageError(data.codiceMessaggio);
        }
      }
      this.loadedSalvaLock = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSalvaLock = true;
      this.showMessageError("Errore in fase di salvataggio lock.");
    });
  }

  setFiltro() {
    return new FiltroRicercaChecklist(this.dichiarazioneSpesaSelected, this.dataControllo, this.soggettoControllo, this.statoSelected, this.tipologiaSelected, this.rilevazioneSelected,
      this.versione);
  }

  saveDataForNavigation() {
    this.navigationService.filtroRicercaChecklistSelezionato = this.setFiltro();
    if (this.tableComponent) {
      this.tableComponent.salvaPaginator();
    }
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

  tornaAllaGestioneControlli() {
    this.resetMessageSuccess();
    this.resetMessageError();
    let destComponentPath = 'gestioneAltriControlli';
    this.loadedChiudiAttivita = false;
    this.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI, destComponentPath, this.user);

  }
  navigateToOperazioneEsterna(configComponentURL: string, idOP: number, destComponentPath: string, user: UserInfoSec) {
    let dettaglio =2;
    let url: string = `${configComponentURL}/#/drawer/${idOP}/${destComponentPath}?&idControllo=${this.idControllo}`
      + `&idProgetto=${this.idProgetto}&dettaglio=${dettaglio}&idStatoChecklist=${this.idStatoChecklist}`;
      // + `&idU=${user.idUtente}&role=${user.codiceRuolo}`;
      // + `&idProgetto=${this.idProgetto}`;

    window.location.href = url;
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
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

  nuovaCheckList() {
    this.saveDataForNavigation();
    let url ="drawer/" + this.idOperazione + "/nuovaChecklist/" + this.idProgetto + "/" + this.idBandoLinea;
    url += `?&idControllo=${this.idControllo}`; 
    this.router.navigateByUrl(url);
  }

  isLoading() {
    if (!this.loadedChiudiAttivita || !this.loadedDichiarazioni || !this.loadedStati || !this.loadedRicerca
      || !this.loadedDownload || !this.loadedInizializza || !this.loadedElimina || !this.loadedSalvaLock) {
      return true;
    }
    return false;
  }

}



