import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SharedService } from 'src/app/shared/services/shared.service';
import { BandoLinea } from '../../commons/dto/bando-linea';
import { Beneficiario } from '../../commons/dto/beneficiario';
import { EconomiaDTO } from '../../commons/dto/economia-dto';
import { ModificaDTO } from '../../commons/dto/modifica-dto';
import { ProgettoDTO } from '../../commons/dto/progetto-dto';
import { QuotaDTO } from '../../commons/dto/quota-dto';
import { GestioneEconomieService } from '../../services/gestione-economie.service';
import { DettaglioProgettoDialogComponent } from '../dettaglio-progetto-dialog/dettaglio-progetto-dialog.component';
import { GestioneQuoteDialogComponent } from '../gestione-quote-dialog/gestione-quote-dialog.component';

@Component({
  selector: 'app-economia',
  templateUrl: './economia.component.html',
  styleUrls: ['./economia.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class EconomiaComponent implements OnInit {

  idEconomia: number; //null se in inserimento, altrimenti modifica
  user: UserInfoSec;
  isFromRimodulazione: boolean;
  idBando: number;
  idProgetto: number;
  economia: EconomiaDTO = new EconomiaDTO();;
  bandi: Array<BandoLinea>;
  bandoCedenteSelected: BandoLinea;
  bandoRiceventeSelected: BandoLinea;
  progettiCedente: Array<ProgettoDTO>;
  progettoCedenteSelected: ProgettoDTO;
  progettiRicevente: Array<ProgettoDTO>;
  progettoRiceventeSelected: ProgettoDTO;
  benCedente: Beneficiario;
  benRicevente: Beneficiario;
  maxCedibile: number;
  economieCedute: number;
  importoCeduto: number;
  importoCedutoFormatted: string;
  noteCessione: string;
  noteRicezione: string;
  dataCessione: FormControl = new FormControl();
  dataRicezione: FormControl = new FormControl();
  dataUtilizzo: FormControl = new FormControl();
  quoteEconomiaCedente: Array<QuotaDTO>;
  quoteEconomiaRicevente: Array<QuotaDTO>;

  @ViewChild('cedenteForm', { static: true }) cedenteForm: NgForm;
  @ViewChild('riceventeForm', { static: true }) riceventeForm: NgForm;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedBandi: boolean = true;
  loadedEconomia: boolean = true;
  loadedProgettiCed: boolean = true;
  loadedProgettiRic: boolean = true;
  loadedSalva: boolean = true;
  loadedInfoProg: boolean = true;

  //SUBSCRIBERS
  subscribers: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private handleExceptionService: HandleExceptionService,
    private gestioneEconomieService: GestioneEconomieService,
    private userService: UserService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idEconomia = params['idEconomia'] ? +params['idEconomia'] : null;

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;
          if (this.idEconomia) {
            this.loadDataModifica();
            if (this.activatedRoute.snapshot.paramMap.get('idProgetto')) {
              this.isFromRimodulazione = true;
              this.idProgetto = +this.activatedRoute.snapshot.paramMap.get('idProgetto');
              this.idBando = +this.activatedRoute.snapshot.paramMap.get('idBando');
            }
          } else {
            this.loadData();
          }
        }
      });
    });
  }

  loadData() {
    this.loadedBandi = false;
    this.subscribers.bandi = this.gestioneEconomieService.getBandi(this.user.idUtente).subscribe(data => {
      this.bandi = data;
      this.loadedBandi = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel recuperare l'elenco dei bandi.");
      this.loadedBandi = true;
    });
  }

  loadDataModifica() {
    this.loadedEconomia = false;
    let eco = new EconomiaDTO();
    eco.idEconomia = this.idEconomia;
    this.subscribers.economia = this.gestioneEconomieService.modifica(this.user.idUtente, eco).subscribe(data => {
      this.bandi = data.bandoLinea;
      if (data.economiaDTO && data.economiaDTO[0]) {
        this.economia = data.economiaDTO[0];
        if (this.economia.idBandoLineaCedente) {
          this.bandoCedenteSelected = this.bandi.find(b => b.progrBandoLineaIntervento === this.economia.idBandoLineaCedente);
          this.changeBandoCedente(this.economia.idProgettoCedente);
        }
        if (this.economia.idBandoLineaRicevente) {
          this.bandoRiceventeSelected = this.bandi.find(b => b.progrBandoLineaIntervento === this.economia.idBandoLineaRicevente);
          this.changeBandoRicevente(this.economia.idProgettoRicevente);
        }
        this.importoCeduto = this.economia.importoCeduto;
        this.importoCedutoFormatted = this.sharedService.formatValue(this.importoCeduto.toString());
        this.noteCessione = this.economia.noteCessione;
        this.noteRicezione = this.economia.noteRicezione;
        this.dataCessione.setValue(this.economia.dataCessione ? new Date(this.economia.dataCessione) : null);
        this.dataRicezione.setValue(this.economia.dataRicezione ? new Date(this.economia.dataRicezione) : null);
        this.dataUtilizzo.setValue(this.economia.dataUtilizzo ? new Date(this.economia.dataUtilizzo) : null);
      }
      this.loadedEconomia = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel recuperare l'economia.");
      this.loadedEconomia = true;
    });
  }

  changeBandoCedente(idProgetto?: number) {
    this.loadedProgettiCed = false;
    this.progettiCedente = null;
    this.progettoCedenteSelected = null;
    this.benCedente = null;
    this.maxCedibile = null;
    this.economieCedute = null;
    this.quoteEconomiaCedente = null;
    this.quoteEconomiaRicevente = null;
    this.subscribers.progCed = this.gestioneEconomieService.getProgettiByBando(this.user.idUtente, this.bandoCedenteSelected.progrBandoLineaIntervento).subscribe(data => {
      this.progettiCedente = data;
      this.loadedProgettiCed = true;
      if (idProgetto) {
        this.progettoCedenteSelected = this.progettiCedente.find(p => p.idProgetto === idProgetto);
        this.changeProgettoCedente();
      }

    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel recuperare i progetti.");
      this.loadedProgettiCed = true;
    });
  }

  changeBandoRicevente(idProgetto?: number) {
    this.progettiRicevente = null;
    this.progettoRiceventeSelected = null;
    this.benRicevente = null;
    this.quoteEconomiaRicevente = null;
    if (this.bandoRiceventeSelected) {
      this.loadedProgettiRic = false;
      this.subscribers.progRic = this.gestioneEconomieService.getProgettiByBando(this.user.idUtente, this.bandoRiceventeSelected.progrBandoLineaIntervento).subscribe(data => {
        this.progettiRicevente = data;
        this.loadedProgettiRic = true;
        if (idProgetto) {
          this.progettoRiceventeSelected = this.progettiRicevente.find(p => p.idProgetto === idProgetto);
          this.changeProgettoRicevente();
        }
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel recuperare i progetti.");
        this.loadedProgettiRic = true;
      });
    }
  }

  changeProgettoCedente(isFromCombo?: boolean) {
    this.loadedProgettiCed = false;
    this.benCedente = null;
    this.maxCedibile = null;
    this.economieCedute = null;
    this.quoteEconomiaCedente = null;
    this.quoteEconomiaRicevente = null;
    this.subscribers.infoCed = this.gestioneEconomieService.cambiaProgetto(this.user.idUtente, this.progettoCedenteSelected.idProgetto, this.idEconomia,
      this.user.beneficiarioSelezionato ? this.user.beneficiarioSelezionato.idBeneficiario : null, "C").subscribe(data => {
        this.benCedente = data.beneficiario ? data.beneficiario[0] : null;
        this.maxCedibile = data.importoMaxCedibile;
        this.economieCedute = data.economieGiaCedute;
        this.quoteEconomiaCedente = data.quotaDTO;
        for (let quota of this.quoteEconomiaCedente) {
          let quotaEconomia = data.quotaEconomiaDTO.find(q => q.idSoggettoFinanziatore === quota.idSoggettoFinanziatore);
          if (quotaEconomia) {
            quota.impQuotaEconSoggFinanziat = quotaEconomia.impQuotaEconSoggFinanziat ? quotaEconomia.impQuotaEconSoggFinanziat : 0;
            quota.idEconomia = quotaEconomia.idEconomia;
          } else {
            quota.impQuotaEconSoggFinanziat = 0;
          }
        }
        if (isFromCombo) {
          this.calcolaQuote(true);
        }
        this.loadedProgettiCed = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel recuperare le informazioni del progetto cedente.");
        this.loadedProgettiCed = true;
      });
  }

  changeProgettoRicevente(isFromCombo?: boolean) {
    this.benRicevente = null;
    this.quoteEconomiaRicevente = null;
    if (this.progettoRiceventeSelected) {
      this.loadedProgettiRic = false;
      this.subscribers.inforic = this.gestioneEconomieService.cambiaProgetto(this.user.idUtente, this.progettoRiceventeSelected.idProgetto, this.idEconomia,
        this.user.beneficiarioSelezionato ? this.user.beneficiarioSelezionato.idBeneficiario : null, "R").subscribe(data => {
          console.log(data);
          this.benRicevente = data.beneficiario ? data.beneficiario[0] : null;
          this.quoteEconomiaRicevente = data.quotaDTO;
          for (let quota of this.quoteEconomiaRicevente) {
            let quotaEconomia = data.quotaEconomiaDTO.find(q => q.idSoggettoFinanziatore === quota.idSoggettoFinanziatore);
            if (quotaEconomia) {
              quota.impQuotaEconSoggFinanziat = quotaEconomia.impQuotaEconSoggFinanziat ? quotaEconomia.impQuotaEconSoggFinanziat : 0;
              quota.idEconomia = quotaEconomia.idEconomia;
            } else {
              quota.impQuotaEconSoggFinanziat = 0;
            }
          }
          if (isFromCombo) {
            this.calcolaQuote(false);
          }
          this.loadedProgettiRic = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore nel recuperare le informazioni del progetto ricevente.");
          this.loadedProgettiRic = true;
        });
    }
  }

  setImportoCeduto() {
    this.importoCeduto = this.sharedService.getNumberFromFormattedString(this.importoCedutoFormatted);
    if (this.importoCeduto !== null) {
      this.importoCedutoFormatted = this.sharedService.formatValue(this.importoCeduto.toString());
    }
    this.calcolaQuote(true);
  }

  /*
  cambio progetto cedente -> ricalcolo quote cedente e ricevente
  modifica importo ceduto -> ricalcolo quote cedente e ricevente
  cambio progetto ricevente -> ricalcolo quote ricevente
  */

  calcolaQuote(calcoloAll: boolean) {
    if (this.quoteEconomiaRicevente) {
      for (let quota of this.quoteEconomiaRicevente) {
        quota.impQuotaEconSoggFinanziat = 0;
      }
    }
    if (this.quoteEconomiaCedente) {
      for (let quotaC of this.quoteEconomiaCedente) {
        if (calcoloAll) {
          quotaC.impQuotaEconSoggFinanziat = +(this.importoCeduto / 100 * quotaC.percQuotaSoggFinanziatore).toFixed(2);
        }
        if (this.quoteEconomiaRicevente) {
          for (let quotaR of this.quoteEconomiaRicevente) {
            if (quotaR.idSoggettoFinanziatore === quotaC.idSoggettoFinanziatore) {
              quotaR.impQuotaEconSoggFinanziat = quotaC.impQuotaEconSoggFinanziat;
            }
          }
        }
      }
    }
  }

  gestioneQuote(type: string) {
    this.resetMessageError();
    this.resetMessageSuccess();
    let dialogRef = this.dialog.open(GestioneQuoteDialogComponent, {
      width: '60%',
      data: {
        type: type,
        quote: type === "C" ? this.quoteEconomiaCedente : this.quoteEconomiaRicevente,
        importoCeduto: this.importoCeduto,
        isFromRimodulazione: this.isFromRimodulazione
      }
    });
    dialogRef.afterClosed().subscribe((res: Array<QuotaDTO>) => {
      if (res) {
        this.quoteEconomiaCedente = res;
        this.calcolaQuote(false);
      }
    });
  }

  openDettaglioProgetto(type: string, idProgetto: number) {
    this.resetMessageError();
    this.resetMessageSuccess();
    this.loadedInfoProg = false;
    this.subscribers.infoProg = this.gestioneEconomieService.infoProgetto(this.user.idUtente, idProgetto).subscribe(data => {
      if (data) {
        let dialogRef = this.dialog.open(DettaglioProgettoDialogComponent, {
          width: '60%',
          data: {
            type: type,
            datiGenerali: data,
            cfBeneficiario: type === "C" ? this.benCedente.codiceFiscale : this.benRicevente.codiceFiscale
          }
        });
        dialogRef.afterClosed().subscribe((res: Array<QuotaDTO>) => {
          if (res) {
            this.quoteEconomiaCedente = res;
            this.calcolaQuote(false);
          }
        });
      }
      this.loadedInfoProg = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di recupero delle informazioni sul progetto.");
      this.loadedInfoProg = true;
    });
  }

  validate() {
    let hasError: boolean;

    if (this.sharedService.checkFormFieldRequired(this.cedenteForm, 'bandoCedente')) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldRequired(this.cedenteForm, 'progettoCedente')) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldRequired(this.cedenteForm, 'importoCeduto')) {
      hasError = true;
    }
    if (this.sharedService.checkFormFieldRequired(this.cedenteForm, 'noteCessione')) {
      hasError = true;
    }
    if (!this.dataCessione || !this.dataCessione.value) {
      this.dataCessione.setErrors({ error: 'required' });
      this.dataCessione.markAsTouched();
    }
    if (this.bandoRiceventeSelected) {
      if (this.sharedService.checkFormFieldRequired(this.riceventeForm, 'progettoRicevente')) {
        hasError = true;
      }
      if (!this.dataRicezione || !this.dataRicezione.value) {
        this.dataRicezione.setErrors({ error: 'required' });
        this.dataRicezione.markAsTouched();
      }
    }
    if (this.importoCeduto && this.maxCedibile && this.importoCeduto > this.maxCedibile) {
      this.cedenteForm.form.get('importoCeduto').setErrors({ error: 'maggioreMax' })
    }
    if (this.importoCeduto && this.importoCeduto < 0) {
      this.cedenteForm.form.get('importoCeduto').setErrors({ error: 'minore' })
    }
    if (this.quoteEconomiaCedente && this.quoteEconomiaRicevente) {
      for (let quotaC of this.quoteEconomiaCedente) {
        if (quotaC.impQuotaEconSoggFinanziat !== null && quotaC.impQuotaEconSoggFinanziat !== 0) {
          let quotaR = this.quoteEconomiaRicevente.find(q => q.idSoggettoFinanziatore === quotaC.idSoggettoFinanziatore);
          if (!quotaR) {
            this.riceventeForm.form.get('progettoRicevente').setErrors({ error: 'fonti' });
            hasError = true;
            break;
          }
        }
      }
    }
    this.cedenteForm.form.markAllAsTouched();
    this.riceventeForm.form.markAllAsTouched();
    if (hasError) {
      this.showMessageError("Errore in fase di validazione.");
    }
    return hasError;
  }

  salva() {
    this.resetMessageError();
    this.resetMessageSuccess();
    if (!this.validate()) {
      //nelle quote devo valorizzare il campo importo e rimuovere il campo impQuotaEconSoggFinanziatFormatted
      let quoteC = new Array<QuotaDTO>();
      this.quoteEconomiaCedente.forEach(q => quoteC.push(Object.assign({}, q)));
      quoteC = quoteC.map(q => {
        if (this.idEconomia) {
          q.idEconomia = this.idEconomia;
        }
        q.importo = q.impQuotaEconSoggFinanziat;
        delete q['impQuotaEconSoggFinanziatFormatted'];
        return q;
      });
      let quoteR = new Array<QuotaDTO>();
      if (this.bandoRiceventeSelected) {
        this.quoteEconomiaRicevente.forEach(q => quoteR.push(Object.assign({}, q)));
        quoteR = quoteR.map(q => {
          if (this.idEconomia) {
            q.idEconomia = this.idEconomia;
          }
          q.importo = q.impQuotaEconSoggFinanziat;
          delete q['impQuotaEconSoggFinanziatFormatted'];
          return q;
        });
      }
      this.economia.idBandoLineaCedente = this.bandoCedenteSelected.progrBandoLineaIntervento;
      this.economia.idBandoLineaRicevente = this.bandoRiceventeSelected ? this.bandoRiceventeSelected.progrBandoLineaIntervento : null;
      this.economia.idProgettoCedente = this.progettoCedenteSelected.idProgetto;
      this.economia.idProgettoRicevente = this.progettoRiceventeSelected ? this.progettoRiceventeSelected.idProgetto : null;
      this.economia.codiceVisualizzatoCedente = this.progettoCedenteSelected.codiceVisualizzato;
      this.economia.codiceVisualizzatoRicevente = this.progettoRiceventeSelected ? this.progettoRiceventeSelected.codiceVisualizzato : null;
      this.economia.importoCeduto = this.importoCeduto;
      this.economia.dataCessione = this.dataCessione && this.dataCessione.value ? this.dataCessione.value : null;
      this.economia.dataRicezione = this.dataRicezione && this.dataRicezione.value ? this.dataRicezione.value : null;
      this.economia.noteRicezione = this.noteRicezione;
      this.economia.noteCessione = this.noteCessione;
      this.economia.dataUtilizzo = this.dataUtilizzo && this.dataUtilizzo.value ? this.dataUtilizzo.value : null;
      this.economia.idBeneficiarioCedente = this.benCedente ? this.benCedente.id : null;
      this.economia.idBeneficiarioRicevente = this.benRicevente ? this.benRicevente.id : null;
      this.economia.denominazioneBeneficiarioCedente = this.benCedente ? this.benCedente.descrizione : null;
      this.economia.denominazioneBeneficiarioRicevente = this.benRicevente ? this.benRicevente.descrizione : null;
      let request = new ModificaDTO(quoteC, quoteC, quoteR, quoteR, null, [this.economia], null, null, null, null, null);
      if (this.idEconomia) {
        this.economia.dataModifica = new Date();
        this.loadedSalva = false;
        this.subscribers.aggiornaEconomia = this.gestioneEconomieService.salvaAggiornamentiEconomia(this.user.idUtente, request).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.showMessageSuccess("Salvataggio avvenuto con successo.");
            } else if (data.msgs) {
              this.showMessageError(data.msgs.msgKey);
            }
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di salvataggio.");
          this.loadedSalva = true;
        });
      } else {
        this.economia.dataInserimento = new Date();
        this.loadedSalva = false;
        this.subscribers.nuovaEconomia = this.gestioneEconomieService.salvaNuovaEconomia(this.user.idUtente, request).subscribe(data => {
          if (data) {
            if (data.esito) {
              this.goToEconomie("Salvataggio avvenuto con successo.");
            } else if (data.msgs) {
              this.showMessageError(data.msgs.msgKey);
            }
          }
          this.loadedSalva = true;
        }, err => {
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di salvataggio.");
          this.loadedSalva = true;
        });
      }
    }
  }

  goToEconomie(message?: string) {
    let url: string = `drawer/${Constants.ID_OPERAZIONE_GESTIONE_ECONOMIE}/gestioneEconomie`;
    if (message) {
      url += `;message=${message}`;
    }
    if (this.isFromRimodulazione) {
      url += `;idProgetto=${this.idProgetto};idBando=${this.idBando}`;
    }
    this.router.navigateByUrl(url);
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
    if (!this.loadedEconomia || !this.loadedProgettiCed || !this.loadedProgettiRic || !this.loadedBandi
      || !this.loadedSalva || !this.loadedInfoProg) {
      return true;
    }
    return false;
  }

}
