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
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { saveAs } from 'file-saver-es';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { Constants } from 'src/app/core/commons/util/constants';
import { ChecklistTableComponent } from 'src/app/checklist/components/checklist-table/checklist-table.component';

@Component({
  selector: 'app-gestione-checklist-spesa-validata',
  templateUrl: './gestione-checklist-spesa-validata.component.html',
  styleUrls: ['./gestione-checklist-spesa-validata.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneChecklistSpesaValidataComponent implements OnInit {

  idProgetto: number;
  idBandoLinea: number;
  idDocumentoDiSpesa: number;
  idDichiarazioneDiSpesa: number;
  user: UserInfoSec;
  beneficiario: string;
  codiceProgetto: string;
  modificaChecklistAmmessa: boolean;
  eliminazioneChecklistAmmessa: boolean;
  elencoChecklist: Array<CheckListItem>;

  @ViewChild(ChecklistTableComponent) tableComponent: ChecklistTableComponent;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageSuccess2: string;
  isMessageSuccessVisible2: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedInizializza: boolean;
  loadedRicerca: boolean;
  loadedDownload: boolean = true;
  loadedElimina: boolean = true;
  loadedSalvaLock: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private configService: ConfigService,
    private userService: UserService,
    private checklistService: CheckListService,
    private handleExceptionService: HandleExceptionService,
    private dialog: MatDialog,
    private archivioFileService: ArchivioFileService
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProgetto = +params['idProgetto'];
      this.idBandoLinea = +params['idBandoLinea'];
      this.idDocumentoDiSpesa = +params['idDocumentoDiSpesa'];
      this.idDichiarazioneDiSpesa = +params['idDichiarazioneDiSpesa'];

      if (this.activatedRoute.snapshot.paramMap.get('message') != null) {
        this.showMessageSuccess("Salvataggio avvenuto con successo.");
      }

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

          this.loadedInizializza = false;
          this.subscribers.init = this.checklistService.inizializzaGestioneChecklist(this.idProgetto).subscribe(data => {
            if (data) {
              this.codiceProgetto = data.codiceVisualizzatoProgetto;
              this.modificaChecklistAmmessa = data.modificaChecklistAmmessa;
              this.eliminazioneChecklistAmmessa = data.eliminazioneChecklistAmmessa;
              this.ricerca();
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

  ricerca() {
    this.loadedRicerca = false;
    this.subscribers.checklist = this.checklistService.cercaChecklist(null, null, null,
      [Constants.DESC_BREVE_STATO_CHECKLIST_DA_AGGIORNARE, Constants.DESC_BREVE_STATO_CHECKLIST_INVIATA], "CL", null, null, this.idProgetto).subscribe(data => {
        if (data && data.length > 0) {
          this.elencoChecklist = data;
        } else {
          this.showMessageSuccess2("Nessun'altra checklist da modificare.");
        }
        this.loadedRicerca = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di ricerca delle checklist.");
        this.loadedRicerca = true;
      });
  }

  download(item: CheckListItem) {
    this.resetMessageError();
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
        this.subscribers.eliminaDoc = this.checklistService.eliminaChecklist(item.idDocumentoIndex).subscribe(data => {
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
    // se CL e INVIATA deve comportarsi come DA AGGIORNARE: modifica possibile con modifica versione
    // if (item.codiceTipo === "CL" && item.codiceStato === "I") {
    //   this.showMessageError("Checklist non modificabile perché inviata.");
    //   return;
    // }
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
    this.subscribers.salvaLockLoco = this.checklistService.salvaLockCheckListInLoco(this.idProgetto, item.idDocumentoIndex).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/modificaChecklist/${this.idProgetto}/${this.idBandoLinea}/${item.idDocumentoIndex}/${item.codiceTipo}/${item.idDichiarazione}`);
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
    this.subscribers.salvaLockLoco = this.checklistService.salvaLockCheckListValidazione(this.idProgetto, item.idDichiarazione).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.router.navigateByUrl(`drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/modificaChecklist/${this.idProgetto}/${this.idBandoLinea}/${item.idDocumentoIndex}/${item.codiceTipo}/${item.idDichiarazione}`);
        } else {
          if (data.codiceMessaggio === "E.M10cl") {
            this.showMessageError("Check-list bloccata da un altro utente.");
          } else {
            this.showMessageError(data.codiceMessaggio);
          }
        }
      }
      this.loadedSalvaLock = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSalvaLock = true;
      this.showMessageError("Errore in fase di salvataggio lock.");
    });
  }


  tornaAGestioneSpesaValidata() {
    this.router.navigateByUrl(`/drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_GESTIONE_SPESA_VALIDATA}/gestioneSpesaValidata/${this.idProgetto}/${this.idBandoLinea}`);
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

  contoEconomico() {
    this.dialog.open(VisualizzaContoEconomicoDialogComponent, {
      width: "90%",
      maxHeight: '90%',
      data: {
        idBandoLinea: this.idBandoLinea,
        idProgetto: this.idProgetto,
        apiURL: this.configService.getApiURL()
      }
    });
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
    const element = document.querySelector('#scrollId');
    element.scrollIntoView();
  }

  showMessageSuccess2(msg: string) {
    this.messageSuccess2 = msg;
    this.isMessageSuccessVisible2 = true;
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

  resetMessageSuccess2() {
    this.messageSuccess2 = null;
    this.isMessageSuccessVisible2 = false;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }


  isLoading() {
    if (!this.loadedRicerca || !this.loadedDownload || !this.loadedInizializza 
      || !this.loadedElimina || !this.loadedSalvaLock || !this.tableComponent.loadedAllegati) {
      return true;
    }
    return false;
  }

}
