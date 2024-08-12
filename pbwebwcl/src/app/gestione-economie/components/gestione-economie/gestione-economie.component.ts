import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { EconomiaDTO } from '../../commons/dto/economia-dto';
import { ParametriRicercaEconomieDTO } from '../../commons/dto/parametri-ricerca-economia-dto';
import { GestioneEconomieService } from '../../services/gestione-economie.service';
import { NavigationGestioneEconomieService } from '../../services/navigation-gestione-economie.service';
import { saveAs } from 'file-saver-es';
import { ConfigService } from 'src/app/core/services/config.service';

@Component({
  selector: 'app-gestione-economie',
  templateUrl: './gestione-economie.component.html',
  styleUrls: ['./gestione-economie.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class GestioneEconomieComponent implements OnInit {

  user: UserInfoSec;
  isFromRimodulazione: boolean;
  idBando: number;
  idProgetto: number;
  filtroSelected: string = "1";
  criteriRicercaOpened: boolean = true;
  filtroBenCedente: string;
  filtroCodCedente: string;
  filtroBenRicevente: string;
  filtroCodRicevente: string;
  economie: Array<EconomiaDTO>;

  displayedColumns: string[] = ['cedente', 'ceduto', 'noteCessione', 'dataCessione', 'ricevente', 'dataRicezione', 'noteRicezione', 'dataUtilizzo', 'azioni'];
  dataSource: MatTableDataSource<EconomiaDTO> = new MatTableDataSource<EconomiaDTO>([]);

  @ViewChild('paginator', { static: true }) paginator: MatPaginator;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedCerca: boolean = true;
  loadedElimina: boolean = true;
  loadedDownload: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private handleExceptionService: HandleExceptionService,
    private gestioneEconomieService: GestioneEconomieService,
    private userService: UserService,
    private navigationService: NavigationGestioneEconomieService,
    private dialog: MatDialog,
    private configService: ConfigService
  ) { }
  // Si arriva da Rimodulazione: i filtri di ricerca  e il pulsante aggiungi vanno nascosti.

  ngOnInit(): void {
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
        if (this.activatedRoute.snapshot.paramMap.get('idProgetto')) {
          this.isFromRimodulazione = true;
          this.idProgetto = +this.activatedRoute.snapshot.paramMap.get('idProgetto');
          this.idBando = +this.activatedRoute.snapshot.paramMap.get('idBando');
          this.filtroSelected = "99";
          this.filtroCodRicevente = this.idProgetto.toString();
        } else if (this.navigationService.filtroRicercaSelezionato) {
          this.filtroSelected = this.navigationService.filtroRicercaSelezionato.filtro;
          this.filtroBenCedente = this.navigationService.filtroRicercaSelezionato.beneficiario;
          this.filtroCodCedente = this.navigationService.filtroRicercaSelezionato.progetto;
          this.filtroBenRicevente = this.navigationService.filtroRicercaSelezionato.beneficiarioRicevente;
          this.filtroCodRicevente = this.navigationService.filtroRicercaSelezionato.progettoRicevente;
        }
        this.ricerca();
        if (this.activatedRoute.snapshot.paramMap.get('message')) {
          this.showMessageSuccess(this.activatedRoute.snapshot.paramMap.get('message'));
        }
      }
    });
  }

  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }

  ricerca() {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedCerca = false;
    let request = new ParametriRicercaEconomieDTO(this.filtroSelected, this.filtroBenCedente, this.filtroCodCedente, this.filtroBenRicevente, this.filtroCodRicevente);
    this.subscribers.cerca = this.gestioneEconomieService.getEconomie(this.user.idUtente, request).subscribe(data => {
      if (data) {
        this.economie = data;
        this.dataSource = new MatTableDataSource<EconomiaDTO>(this.economie);
        if (this.navigationService.filtroRicercaSelezionato) {
          this.ripristinaPaginator();
        } else {
          this.paginator.length = this.economie.length;
          this.paginator.pageIndex = 0;
          this.dataSource.paginator = this.paginator;
        }
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di ricerca.");
      this.loadedCerca = true;
    });
  }

  goToModifica(idEconomia: number) {
    this.saveDataForNavigation();
    let url: string = `drawer/${Constants.ID_OPERAZIONE_GESTIONE_ECONOMIE}/economia/${idEconomia}`;
    if (this.isFromRimodulazione) {
      url += `;idProgetto=${this.idProgetto};idBando=${this.idBando}`;
    }
    this.router.navigateByUrl(url);
  }

  goToRimodulazione() {
    let url: string = `${this.configService.getPbwebrceURL()}/#/drawer/${Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_RIMODULAZIONE_CONTO_ECONOMICO}/rimodulazioneContoEconomico/`
      + `${this.idProgetto}/${this.idBando}?idSg=${this.user.idSoggetto}&idSgBen=${this.user.beneficiarioSelezionato.idBeneficiario}&idU=${this.user.idUtente}`
      + `&role=${this.user.codiceRuolo}&idPrj=${this.idProgetto}&taskIdentity=${Constants.DESCR_BREVE_TASK_RIMODULAZIONE_CONTO_ECONOMICO}`
      + `&taskName=Rimodulazione%20del%20conto%20economico%20&wkfAction=StartTaskNeoFlux`;
    window.location.href = url;
  }

  elimina(economia: EconomiaDTO) {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(ConfermaDialog, {
      width: '40%',
      data: {
        message: "L'economia " + economia.codiceVisualizzatoCedente + " verrà eliminata definitivamente. Confermi?"
      }
    });
    dialogRef.afterClosed().subscribe(res => {
      if (res === "SI") {
        this.loadedElimina = false;
        this.subscribers.eliminaDoc = this.gestioneEconomieService.deleteEconomia(this.user.idUtente, economia.idEconomia).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.ricerca();
              this.showMessageSuccess("Eliminazione avvenuta con successo.");//data.msgs.msgKey -> data.msgs=null
            } else {
              this.showMessageError("Errore in fase di eliminazione dell'economia."); //data.msgs.msgKey -> data.msgs=null 
            }
          }
          this.loadedElimina = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.loadedElimina = true;
          this.showMessageError("Errore in fase di eliminazione dell'economia.");
        });
      }
    });
  }

  reportDettaglio() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedDownload = false;
    let request = new ParametriRicercaEconomieDTO(this.filtroSelected, this.filtroBenCedente, this.filtroCodCedente, this.filtroBenRicevente, this.filtroCodRicevente);
    this.subscribers.downlaod = this.gestioneEconomieService.reportDettaglioEconomia(this.user.idUtente, request).subscribe(res => {
      let nomeFile = res.headers.get("header-nome-file");
      saveAs(new Blob([res.body]), nomeFile);
      this.loadedDownload = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di download del file");
      this.loadedDownload = true;
    });
  }

  aggiungiEconomia() {
    this.saveDataForNavigation();
    this.router.navigate([`drawer/${Constants.ID_OPERAZIONE_GESTIONE_ECONOMIE}/economia`]);
  }

  saveDataForNavigation() {
    this.navigationService.filtroRicercaSelezionato = new ParametriRicercaEconomieDTO(this.filtroSelected, this.filtroBenCedente, this.filtroCodCedente,
      this.filtroBenRicevente, this.filtroCodRicevente);
    this.salvaPaginator();
  }

  salvaPaginator() {
    if (this.dataSource) {
      this.navigationService.paginatorPageIndexTable = this.dataSource.paginator.pageIndex;
      this.navigationService.paginatorPageSizeTable = this.dataSource.paginator.pageSize;
    }
  }

  ripristinaPaginator() {
    if (this.navigationService.paginatorPageIndexTable != null && this.navigationService.paginatorPageIndexTable != undefined) {
      this.paginator.pageIndex = this.navigationService.paginatorPageIndexTable;
      this.paginator.pageSize = this.navigationService.paginatorPageSizeTable;
      this.dataSource.paginator = this.paginator;
    }
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
    if (!this.loadedCerca || !this.loadedElimina || !this.loadedDownload) {
      return true;
    }
    return false;
  }


}
