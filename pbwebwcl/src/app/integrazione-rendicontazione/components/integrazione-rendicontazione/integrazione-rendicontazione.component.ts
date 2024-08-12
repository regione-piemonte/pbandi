import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { VisualizzaTestoDialogComponent } from 'src/app/shared/components/visualizza-testo-dialog/visualizza-testo-dialog.component';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { RigaTabRichiesteIntegrazioniDs } from '../../commons/dto/riga-tab-richieste-integrazioni-ds';
import { IntegrazioneRendicontazioneService } from '../../services/integrazione-rendicontazione.service';
import { saveAs } from 'file-saver-es';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { DocumentoAllegatoDTO } from 'src/app/rendicontazione/commons/dto/documento-allegato-dto';
import { AssociaFilesRequest } from 'src/app/rendicontazione/commons/requests/associa-files-request';
import { ArchivioFileDialogComponent, ArchivioFileService } from '@pbandi/common-lib';

@Component({
  selector: 'app-integrazione-rendicontazione',
  templateUrl: './integrazione-rendicontazione.component.html',
  styleUrls: ['./integrazione-rendicontazione.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class IntegrazioneRendicontazioneComponent implements OnInit {

  idProgetto: number;
  idBandoLinea: number;
  beneficiario: string;
  codiceProgetto: string;
  user: UserInfoSec;
  integrazioni: Array<RigaTabRichiesteIntegrazioniDs>;

  displayedColumns: string[] = ['nota', 'datarichiesta', 'datanumdich', 'allegati', 'datainvio', 'azioni'];
  dataSource: MatTableDataSource<RigaTabRichiesteIntegrazioniDs>;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedIntegrazioni: boolean;
  loadedChiudiAttivita: boolean = true;
  loadedDownload: boolean = true;
  loadedMarca: boolean = true;
  loadedDisassocia: boolean = true;
  loadedAssocia: boolean = true;
  loadedInvia: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private configService: ConfigService,
    private integrazioneRendicontazioneService: IntegrazioneRendicontazioneService,
    private handleExceptionService: HandleExceptionService,
    private archivioFileService: ArchivioFileService,
    private sharedService: SharedService,
    private userService: UserService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        this.beneficiario = this.user.beneficiarioSelezionato.denominazione;

        this.subscribers.router = this.activatedRoute.params.subscribe(params => {
          this.idProgetto = +params['idProgetto'];
          this.idBandoLinea = +params['idBandoLinea'];

          this.loadData();
        });

      }
    });
  }

  get descBrevePagina(): string {
    return Constants.DESC_BREVE_PAGINA_HELP_GEST_INTEGRAZIONI;
  }

  get apiURL(): string {
    return this.configService.getApiURL();
  }


  loadData() {
    this.subscribers.init = this.integrazioneRendicontazioneService.inizializzaIntegrazioneDiSpesa(this.idProgetto).subscribe(dataInizializzazione => {
      this.codiceProgetto = dataInizializzazione.codiceVisualizzatoProgetto;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
    this.loadIntegrazioni();
  }

  loadIntegrazioni() {
    this.loadedIntegrazioni = false;
    this.subscribers.integrazioni = this.integrazioneRendicontazioneService.integrazioniSpesaByIdProgetto(this.idProgetto).subscribe(data => {
      if (data) {
        this.integrazioni = data;
        this.integrazioni.forEach(i => {
          i.descrizione = decodeURIComponent(i.descrizione);
          // if (i.allegati && i.allegati.length > 0) {
          //   let all = i.allegati.find(a => a.flagEntita === "I");
          //   if (all) {
          //     all.checked = true;
          //   }
          // }
        });
        this.dataSource = new MatTableDataSource<RigaTabRichiesteIntegrazioniDs>(this.integrazioni);
        this.paginator.pageIndex = 0;
        this.paginator.length = this.integrazioni.length;
        this.dataSource.paginator = this.paginator;
      }
      this.loadedIntegrazioni = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  visualizzaInDialog(text: string) {
    this.dialog.open(VisualizzaTestoDialogComponent, {
      minWidth: "40%",
      data: {
        text: text
      }
    });
  }

  // changeCheckbox(event: MatCheckboxChange, integrazione: RigaTabRichiesteIntegrazioniDs, idDocumentoIndex: number) {
  //   if (event.checked) {
  //     this.loadedMarca = false;
  //     this.subscribers.marca = this.integrazioneRendicontazioneService.marcaComeDichiarazioneDiIntegrazione(this.idProgetto, idDocumentoIndex, integrazione.idIntegrazioneSpesa)
  //       .subscribe(data => {
  //         if (data) {
  //           if (data.esito) {
  //             this.loadIntegrazioni();
  //           } else {
  //             integrazione.allegati.find(a => a.idDocumentoIndex === idDocumentoIndex.toString()).checked = false;
  //             this.showMessageError(data.messaggio);
  //           }
  //         }
  //         this.loadedMarca = true;
  //       }, err => {
  //         this.handleExceptionService.handleNotBlockingError(err);
  //         this.showMessageError("Errore in fase di marcatura.");
  //         this.loadedMarca = true;
  //       });
  //   }
  // }

  downloadAllegato(idDocumentoIndex: string, nomeDocumento: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedDownload = false;
    this.subscribers.downlaod = this.archivioFileService.leggiFile(this.configService.getApiURL(), idDocumentoIndex).subscribe(res => {
      if (res) {
        saveAs(res, nomeDocumento);
      }
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  disassociaAllegato(idDocumentoIndex: string, idIntegrazioneSpesa: number) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedDisassocia = false;
    this.subscribers.disassocia = this.integrazioneRendicontazioneService.disassociaAllegatoIntegrazioneDiSpesa(this.idProgetto, +idDocumentoIndex, idIntegrazioneSpesa).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess(data.messaggio);
          this.loadIntegrazioni();
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedDisassocia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di disassociazione dell'allegato");
      this.loadedDisassocia = true;
    });
  }

  aggiungiAllegato(integrazione: RigaTabRichiesteIntegrazioniDs) {
    this.resetMessageSuccess();
    this.resetMessageError();
    let dialogRef = this.dialog.open(ArchivioFileDialogComponent, {
      maxWidth: '100%',
      width: window.innerWidth - 100 + "px",
      height: window.innerHeight - 50 + "px",
      data: {
        allegati: integrazione.allegati.map(a => new DocumentoAllegatoDTO(null, null, +a.idDocumentoIndex, null, true, null, this.idProgetto, a.nomeFile, null, null, null, true)),
        apiURL: this.configService.getApiURL(),
        user: this.user,
        drawerExpanded: this.sharedService.getDrawerExpanded()
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res && res.length > 0) {
        this.loadedAssocia = false;
        let request = new AssociaFilesRequest(res.map(all => +all.idDocumentoIndex), integrazione.idIntegrazioneSpesa, this.idProgetto);
        this.subscribers.associa = this.integrazioneRendicontazioneService.associaAllegatiAIntegrazioneDiSpesa(request).subscribe(data => {
          if (data) {
            if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
              && (!data.elencoIdDocIndexFilesNonAssociati || data.elencoIdDocIndexFilesNonAssociati.length === 0)) { //tutti associati
              this.showMessageSuccess("Tutti gli allegati sono stati associati correttamente.");
              this.loadIntegrazioni();
            } else if (data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0
              && (!data.elencoIdDocIndexFilesAssociati || data.elencoIdDocIndexFilesAssociati.length === 0)) { //tutti non associati
              this.showMessageError("Errore nell'associazione degli allegati.");
            } else if (data.elencoIdDocIndexFilesAssociati && data.elencoIdDocIndexFilesAssociati.length > 0
              && data.elencoIdDocIndexFilesNonAssociati && data.elencoIdDocIndexFilesNonAssociati.length > 0) { //alcuni associati e alcuni non associati
              this.loadIntegrazioni();
              let message = "Errore nell'associazione dei seguenti allegati: ";
              data.elencoIdDocIndexFilesNonAssociati.forEach(id => {
                let allegato = res.find(a => a.idDocumentoIndex === id.toString());
                message += allegato.nome + ", ";
              });
              message = message.substr(0, message.length - 2);
              this.showMessageError(message);
            }
          }
          this.loadedAssocia = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedAssocia = true;
          this.showMessageError("Errore nell'associazione degli allegati.")
        });
      }
    });
  }

  isInvio(integrazione: RigaTabRichiesteIntegrazioniDs) {
    if (!integrazione.dataInvio/* && integrazione.allegati && integrazione.allegati.length > 0 && integrazione.allegati.find(a => a.flagEntita === "I")*/) {
      return true;
    }
    return false;
  }

  invia(idIntegrazioneaSpesa: number) {
    this.loadedInvia = true;
    this.subscribers.invia = this.integrazioneRendicontazioneService.inviaIntegrazioneDiSpesaAIstruttore(idIntegrazioneaSpesa).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess(data.messaggio);
          this.loadIntegrazioni();
        } else {
          this.showMessageError(data.messaggio);
        }
      }
      this.loadedInvia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di invio");
      this.loadedInvia = true;
    });
  }

  tornaAAttivitaDaSvolgere() {
    this.resetMessageError();
    this.loadedChiudiAttivita = false;;
    this.subscribers.chiudiAttivita = this.sharedService.chiudiAttivita(this.idProgetto, Constants.DESCR_BREVE_TASK_INTEGRAZIONE_RENDICONTAZIONE).subscribe(data => {
      window.location.assign(this.configService.getPbworkspaceURL() + "/#/drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE + "/attivita");
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedChiudiAttivita = true;
      this.showMessageError("ATTENZIONE: non è stato possibile chiudere l'attività.");
      this.router.navigate(['/errorRouting'], { queryParams: { message: "Sessione scaduta" }, skipLocationChange: true });
    });
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

  isLoading() {
    if (!this.loadedChiudiAttivita || !this.loadedIntegrazioni || !this.loadedDownload || !this.loadedMarca
      || !this.loadedDisassocia || !this.loadedAssocia || !this.loadedInvia) {
      return true;
    }
    return false;
  }
}
