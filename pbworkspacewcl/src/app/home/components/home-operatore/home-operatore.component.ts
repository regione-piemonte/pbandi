/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from './../../../core/services/config.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OperazioneVO } from '../../commons/vo/operazione-vo';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { UserInfoSec } from 'src/app/core/commons/vo/user-info';
import { HomeService } from '../../services/home.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConsensoDialogComponent } from '../consenso-dialog/consenso-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { SharedService } from 'src/app/shared/services/shared.service';
import { AvvisoUtenteDTO } from 'src/app/core/commons/vo/avviso-utente-dto';
import { DashboardService } from 'src/app/dashboard/services/dashboard.service';

@Component({
  selector: 'app-home-operatore',
  templateUrl: './home-operatore.component.html',
  styleUrls: ['./home-operatore.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class HomeOperatoreComponent implements OnInit {

  user: UserInfoSec;
  adg_cert: boolean;
  adc_cert: boolean;

  ruoloUser: string;
  operazioni: Array<OperazioneVO>;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedUser: boolean;
  loadedOperazioni: boolean = true;
  loadedTrovaConsenso: boolean;
  loadedIsDashBoardVisible: boolean;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private configService: ConfigService,
    private userService: UserService,
    private homeService: HomeService,
    private handleExceptionService: HandleExceptionService,
    private sharedService: SharedService,
    private dashboardService: DashboardService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadedUser = false;
    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data1 => {
      this.adg_cert = false;
      this.adc_cert = false;
      if (data1) {
        this.user = data1;
        if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADG_CERT) {
          this.adg_cert = true;
        } else if (this.user.codiceRuolo === Constants.CODICE_RUOLO_ADC_CERT) {
          this.adc_cert = true;
        }
        this.loadedUser = true;
        this.resetMessageError();
        if (this.user.beneficiariCount > 0 || this.user.codiceRuolo === Constants.CODICE_RUOLO_CREATOR || this.user.codiceRuolo === Constants.CODICE_RUOLO_CSI_ASSISTENZA) {

          this.loadedIsDashBoardVisible = false;
          this.dashboardService.isDashboardVisible().subscribe(isDashboardVisible => {
            this.homeService.getOperazioni();
            if (isDashboardVisible) {
              this.router.navigate([`/drawer/${Constants.ID_OPERAZIONE_HOME}/dashboardHome`]);
            } else {
              this.loadHome();
            }
            this.loadedIsDashBoardVisible = true;
          }, err => {
            this.handleExceptionService.handleNotBlockingError(err);
            this.loadHome();
            this.loadedIsDashBoardVisible = true;
          });
        } else {
          this.showMessageError("Non è stato rilevato alcun progetto da gestire associato a questa utenza. Contattare l'assistenza.");
        }

        this.loadedTrovaConsenso = false;
        this.subscribers.consenso = this.homeService.trovaConsensoInvioComunicazione().subscribe(data3 => {
          if (data3 && !data3.flagConsensoMail) {
            let dialogRef = this.dialog.open(ConsensoDialogComponent, {
              width: '60%',
              disableClose: true
            });
            dialogRef.afterClosed().subscribe(res => {
              if (res) {
                this.showMessageSuccess("Dati salvati con successo.");
              }
            })
          }
          this.loadedTrovaConsenso = true;
        }, err => {
          this.handleExceptionService.handleBlockingError(err);
        });
      }
    });
  }

  loadHome() {
    this.loadedOperazioni = false;
    this.subscribers.operazioni$ = this.homeService.operazioni$.subscribe(data2 => {
      if (data2) {
        this.operazioni = data2;
        if (this.operazioni.length === 1 && this.operazioni[0].id !== 1
          && this.operazioni[0].id !== 6 && this.operazioni[0].id !== 70) {
          this.goToOperazione(this.operazioni[0]);
        } else {
          for (let op of this.operazioni) {
            switch (op.id) {
              case Constants.ID_OPERAZIONE_ARCHIVIO_FILE:
                op.icona = "assets/archivio_file.png";
                break;
              case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE:
                op.icona = "assets/attivita.png";
                break;
              case Constants.ID_OPERAZIONE_DOCUMENTI_PROGETTO:
                op.icona = "assets/documenti_progetto.png";
                break;
              case Constants.ID_OPERAZIONE_TRASFERIMENTI:
                op.icona = "assets/trasferimenti.png";
                break;
              case Constants.ID_OPERAZIONE_REGISTRO_CONTROLLI:
                op.icona = "assets/registro_controlli.png";
                break;
              case Constants.ID_OPERAZIONE_RILEVAZIONE_CONTROLLI:
                op.icona = "assets/rilevazione_controlli.png";
                break;
              case Constants.ID_OPERAZIONE_CERTIFICAZIONE:
                op.icona = "assets/certificazione.png";
                break;
              case Constants.ID_OPERAZIONE_ASSOCIAZIONE_PERMESSO_RUOLO:
                op.icona = "assets/associazione_permesso_ruolo.png";
                break;
              case Constants.ID_OPERAZIONE_ASSOCIAZIONE_RUOLO_PERMESSO:
                op.icona = "assets/associazione_ruolo_permesso.png";
                break;
              case Constants.ID_OPERAZIONE_BILANCIO:
                op.icona = "assets/bilancio.png";
                break;
              case Constants.ID_OPERAZIONE_CAMBIA_BENEFICIARIO:
                op.icona = "assets/cambia_beneficiario.png";
                break;
              case Constants.ID_OPERAZIONE_CONFIGURAZIONE_BANDO:
                op.icona = "assets/configurazione_bando.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_ASSOCIAZIONE_ISTRUTTORE_PROGETTI:
                op.icona = "assets/gestione_associazione_istruttore_progetti.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_ECONOMIE:
                op.icona = "assets/gestione_economie.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_TEMPLATES:
                op.icona = "assets/gestione_templates.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_UTENTI:
                op.icona = "assets/gestione_utenti.png";
                break;
              case Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO:
                op.icona = "assets/linee_finanziamento.png";
                break;
              case Constants.ID_OPERAZIONE_NOTIFICHE_VIA_MAIL:
                op.icona = "assets/notifiche_via_mail.png";
                break;
              case Constants.ID_OPERAZIONE_VERIFICA_SERVIZI:
                op.icona = "assets/verifica_servizi.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_NEWS:
                op.icona = "assets/gestione_news.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA:
                op.icona = "assets/gestione_anagrafica.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE:
                op.icona = "assets/gestione_richieste.png";
                break;
              case Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI:
                op.icona = "assets/monitoraggio_crediti.png";
                break;
              case Constants.ID_OPERAZIONE_PROPOSTE_VARIAZIONI_STATO_CREDITI:
                op.icona = "assets/proposte_var_stato_credito.png";
                break;
              case Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO:
                op.icona = "assets/area_controlli_loco.png";
                break;
              case Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI:
                op.icona = "assets/gestione_erogazioni.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_GARANZIE:
                op.icona = "assets/gestione_garanzie.png";
                break;
              case Constants.ID_OPERAZIONE_GESTIONE_REVOCHE:
                op.icona = "assets/gestione_revoche.png";
                break;
              case Constants.ID_OPERAZIONE_ITER_AUTORIZZATIVI:
                op.icona = "assets/iter_autorizzativi.png";
                break;
              case Constants.ID_OPERAZIONE_BO_STORAGE:
                op.icona = "assets/bo_storage.png";
                break;
              default:
                break;
            }
          }
        }
      }
      this.loadedOperazioni = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  goToOperazione(op: OperazioneVO) {
    let idOP: number = op.id;
    switch (idOP) {
      case Constants.ID_OPERAZIONE_ARCHIVIO_FILE:
        this.sharedService.navigateToArchivioFile(this.user);
        break;
      case Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE:
        this.router.navigate([`drawer/${idOP}/attivita`]);
        break;
      case Constants.ID_OPERAZIONE_DOCUMENTI_PROGETTO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebURL(), idOP, "documentiProgetto", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_ECONOMIE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebURL(), idOP, "gestioneEconomie", this.user);
        break;
      case Constants.ID_OPERAZIONE_NOTIFICHE_VIA_MAIL:
        this.router.navigate([`drawer/${idOP}/notificheViaMail`]);
        break;
      case Constants.ID_OPERAZIONE_CERTIFICAZIONE:
      case Constants.ID_OPERAZIONE_BILANCIO:
      case Constants.ID_OPERAZIONE_CAMPIONAMENTO:
      case Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI:
      case Constants.ID_OPERAZIONE_GESTIONE_REVOCHE:
      case Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO:
        op.isExpanded = true;
        break;
      case Constants.ID_OPERAZIONE_CONFIGURAZIONE_BANDO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "configurazioneBando", this.user);
        break;
      case Constants.ID_OPERAZIONE_ASSOCIAZIONE_PERMESSO_RUOLO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "associazionePermessoRuolo", this.user);
        break;
      case Constants.ID_OPERAZIONE_ASSOCIAZIONE_RUOLO_PERMESSO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "associazioneRuoloPermesso", this.user);
        break;
      case Constants.ID_OPERAZIONE_CAMBIA_BENEFICIARIO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "cambiaBeneficiario", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_UTENTI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "gestioneUtenti", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_ASSOCIAZIONE_ISTRUTTORE_PROGETTI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "associazioniIstruttoreProgetti", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_NEWS:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "gestioneNews", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_TEMPLATES:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "gestioneTemplates", this.user);
        break;
      case Constants.ID_OPERAZIONE_CONSOLE_APPLICATIVA:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "consoleApplicativa", this.user);
        break;
      case Constants.ID_OPERAZIONE_BO_STORAGE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebboURL(), idOP, "boStorage", this.user);
        break;
      case Constants.ID_OPERAZIONE_TRASFERIMENTI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbweberogURL(), idOP, "trasferimenti", this.user);
        break;
      case Constants.ID_OPERAZIONE_RILEVAZIONE_CONTROLLI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbweberogURL(), idOP, "monitoraggio-controlli", this.user);
        break;
      case Constants.ID_OPERAZIONE_REGISTRO_CONTROLLI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbweberogURL(), idOP, "ricercaEsitiControlli", this.user);
        break;
      case Constants.ID_OPERAZIONE_LINEE_FINANZIAMENTO:
        this.router.navigate([`drawer/${idOP}/lineeDiFinanziamento`]);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_ANAGRAFICA:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "ricercaSoggetti", this.user);
        break;
      case Constants.ID_OPERAZIONE_MONITORAGGIO_CREDITI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "monitoraggioCrediti", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_RICHIESTE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "gestioneRichieste", this.user);
        break;
      case Constants.ID_OPERAZIONE_PROPOSTE_VARIAZIONI_STATO_CREDITI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "proposteVariazioniStatoCrediti", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_GARANZIE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "gestioneGaranzie", this.user);
        break;
      case Constants.ID_OPERAZIONE_ITER_AUTORIZZATIVI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "iterAutorizzativi", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_RIASSICURAZIONI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "gestioneRiassicurazioni", this.user);
        break;
      default:
        break;
    }
  }

  goToCertificazione(idOP: number) {
    switch (idOP) {
      case Constants.ID_OPERAZIONE_CERTIFICAZIONE_CREA_PROPOSTA:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebcertURL(), idOP, "creaAnteprimaProposta", this.user);
        break;
      case Constants.ID_OPERAZIONE_CERTIFICAZIONE_CARICA_CHECKLIST:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebcertURL(), idOP, "caricaChecklist", this.user);
        break;
      case Constants.ID_OPERAZIONE_CERTIFICAZIONE_GESTIONE_PROPOSTE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebcertURL(), idOP, "gestioneProposte", this.user);
        break;
      case Constants.ID_OPERAZIONE_CERTIFICAZIONE_CARICA_DICHIARAZIONE_FINALE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebcertURL(), idOP, "caricaDichiarazioneFinale", this.user);
        break;
      case Constants.ID_OPERAZIONE_CERTIFICAZIONE_RICERCA_DOCUMENTI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebcertURL(), idOP, "ricercaDocumenti", this.user);
        break;
      case Constants.ID_OPERAZIONE_CERTIFICAZIONE_CAMPIONAMENTO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebcertURL(), idOP, "campionamento", this.user);
        break;
      default:
        break;
    }
  }

  goToBilancio(idOP: number) {
    switch (idOP) {
      case Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebfinURL(), idOP, "gestioneImpegni", this.user);
        break;
      case Constants.ID_OPERAZIONE_BILANCIO_RICERCA_ATTI_LIQUIDAZIONE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbwebfinURL(), idOP, "ricercaAttiLiquidazione", this.user);
        break;
      default:
        break;
    }
  }

  goToCampionamento(idOP: number) {
    switch (idOP) {
      case Constants.ID_OPERAZIONE_CAMPIONAMENTO_RICERCA:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "ricercaCampionamenti", this.user);
        break;
      case Constants.ID_OPERAZIONE_CAMPIONAMENTO_NUOVO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "nuovoCampionamento", this.user);
        break;
      default:
        break;
    }
  }

  goToGestioneErogazioni(idOP: number) {
    switch (idOP) {
      case Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_PROPOSTE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "ambitoErogazioni", this.user);
        break;
      case Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_CARICAMENTO_DISTINTE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "caricamentoDistinte", this.user);
        break;
      case Constants.ID_OPERAZIONE_AMBITO_EROGAZIONI_ELENCO_DISTINTE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "elencoDistinte", this.user);
        break;
      default:
        break;
    }
  }

  goToGestioneRevoche(idOP: number) {
    switch (idOP) {
      case Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROPOSTE:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "proposteRevoca", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROCEDIMENTI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "procedimentiRevoca", this.user);
        break;
      case Constants.ID_OPERAZIONE_GESTIONE_REVOCHE_PROVVEDIMENTI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "provvedimentiRevoca", this.user);
        break;
      default:
        break;
    }
  }

  goToAreaControlliLoco(idOP: number) {
    switch (idOP) {
      case Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_ACQUISIZIONE_PROGETTI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "acquisizioneProgettiFinp", this.user);
        break;
      case Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_FIN:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "ricercaControlliProgettiFinp", this.user);
        break;
      case Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_ALTRI:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "ricercaAltriControlli", this.user);
        break;
      case Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_INSERIMENTO:
        this.sharedService.navigateToOperazioneEsterna(this.configService.getPbgestfinboURL(), idOP, "inserimentoAltriControlli", this.user);
        break;
      default:
        break;
    }
  }

  showMessageSuccess(msg: string) {
    this.messageSuccess = msg;
    this.isMessageSuccessVisible = true;
  }

  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }

  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }

  isLoading() {
    if (!this.loadedUser || !this.loadedOperazioni || !this.loadedTrovaConsenso || !this.loadedIsDashBoardVisible) {
      return true;
    }
    return false;
  }
}
