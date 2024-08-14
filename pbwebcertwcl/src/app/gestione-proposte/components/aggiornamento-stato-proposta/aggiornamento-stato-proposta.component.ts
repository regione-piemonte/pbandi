/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficiarioDTO } from 'src/app/shared/commons/dto/beneficiario-dto';
import { Constants } from 'src/app/core/commons/util/constants';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { CertificazioneService } from 'src/app/shared/services/certificazione.service';
import { StatoPropostaDTO } from '../../commons/dto/stato-proposta-dto';
import { AggiornaStatoRequest } from '../../commons/requests/aggiorna-stato-request';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { BandoLineaDTO } from '../../commons/dto/bando-linea-dto';
import { ProgettoDTO } from 'src/app/shared/commons/dto/progetto-dto';
import { GestisciPropostaRequest } from '../../commons/requests/gestisci-proposta-request';
import { FiltroRicercaProgettiNP } from '../../commons/dto/filtro-ricerca-progetti-np';
import { ProgettoNuovaCertificazioneDTO } from '../../commons/dto/progetto-nuova-certificazione-dto';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DecimalPipe } from '@angular/common';
import { AggiornaImportoNettoRequest } from '../../commons/requests/aggiorna-importo-netto-request';
import { ProgettoNuovaCertificazione } from '../../commons/dto/progetto-nuova-certificazione';
import { GestisciPropostaIntermediaFinaleRequest } from '../../commons/requests/gestisci-proposta-finale-request';
import { ProgettoCertificazioneIntermediaFinaleDTO } from '../../commons/dto/progetto-certificazione-intermedia-finale-dto';
import { AggiornaDatiIntermediaFinaleRequest } from '../../commons/requests/aggiorna-dati-intermedia-finale-request';
import { InviaReportRequest } from '../../commons/requests/invia-report-request';
import { ExcelService } from "../../../shared/services/excel.service";
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ConfermaDialog } from 'src/app/shared/components/conferma-dialog/conferma-dialog';

@Component({
  selector: 'app-aggiornamento-stato-proposta',
  templateUrl: './aggiornamento-stato-proposta.component.html',
  styleUrls: ['./aggiornamento-stato-proposta.component.scss']
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class AggiornamentoStatoPropostaComponent implements OnInit {

  user: UserInfoSec;
  idProposta: number;
  proposta: PropostaCertificazioneDTO;
  nuoviStati: Array<StatoPropostaDTO>;
  nuovoStatoSelected: StatoPropostaDTO;
  descBreveStatoIniziale: string;

  aggiornaDatiClicked: boolean;

  criteriRicercaOpened: boolean = true;
  bandiLinea: Array<BandoLineaDTO>;
  bandoLineaSelected: BandoLineaDTO;
  beneficiari: Array<BeneficiarioDTO>;
  beneficiarioSelected: BeneficiarioDTO;
  progetti: Array<ProgettoDTO>;
  progettoSelected: ProgettoDTO;
  progettiModificati: boolean;
  isTerminated: boolean;
  isInviaReport: boolean;
  isChiusuraConti: boolean;
  bandoLineaSelectedPrevious: BandoLineaDTO;
  beneficiarioSelectedPrevious: BeneficiarioDTO;
  progettoSelectedPrevious: ProgettoDTO;
  progettiModificatiPrevious: boolean;

  elencoProgetti: Array<ProgettoNuovaCertificazioneDTO>;
  elencoProgettiModificati: Array<ProgettoNuovaCertificazioneDTO>;

  dataSource: ElencoProgettiDatasource;
  displayedColumns: string[] = ['codiceprogetto', 'beneficiario', 'nettoADC', 'nettoADG', 'note'];

  elencoProgettiIF: Array<ProgettoCertificazioneIntermediaFinaleDTO>;

  dataSourceIF: ElencoProgettiIFDatasource;
  displayedColumnsIF: string[] = ['asse', 'codiceprogetto', 'beneficiario', 'validaticum', 'erogazionicum', 'revochecum', 'recupericum', 'soppressionicum', 'lordocum', 'nettocum', 'nettoannuale', 'colonnac'];

  @ViewChild("paginator", { static: true }) paginator: MatPaginator;
  @ViewChild("sort", { static: true }) sort: MatSort;
  @ViewChild("paginatorIF", { static: true }) paginatorIF: MatPaginator;
  @ViewChild("sortIF", { static: true }) sortIF: MatSort;

  @ViewChild("progettiForm", { static: true }) progettiForm: NgForm;

  messageSuccess: string;
  isMessageSuccessVisible: boolean;
  messageError: string;
  isMessageErrorVisible: boolean;

  //LOADED
  loadedProposta: boolean;
  loadedStati: boolean = true;
  loadedBandiLinea: boolean = true;
  loadedBeneficiari: boolean = true;
  loadedProgetti: boolean = true;
  loadedSalva: boolean = true;
  loadedCerca: boolean = true;
  loadedChiusuraConti: boolean = true;

  //OBJECT SUBSCRIBER
  subscribers: any = {};

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private certificazioneService: CertificazioneService,
    private handleExceptionService: HandleExceptionService,
    private userService: UserService,
    private excelService: ExcelService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.subscribers.router = this.activatedRoute.params.subscribe(params => {
      this.idProposta = +params['id'];

      this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
        if (data) {
          this.user = data;

          this.loadedProposta = false;
          this.subscribers.proposta = this.certificazioneService.getProposta(this.idProposta).subscribe(data1 => {
            if (data1) {
              this.proposta = data1;
              this.descBreveStatoIniziale = this.proposta.descBreveStatoPropostaCert;
              if (this.proposta.descBreveStatoPropostaCert === Constants.DESC_BREVE_STATO_PROPOSTA_APERTA) {
                this.loadedStati = false;
                this.subscribers.stati = this.certificazioneService.getStatiSelezionabili().subscribe(data2 => {
                  if (data2) {
                    this.nuoviStati = data2;
                  }
                  this.loadedStati = true;
                }, err => {
                  this.handleExceptionService.handleBlockingError(err);
                });
              }
              if (this.proposta.descBreveStatoPropostaCert === Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA) {
                this.loadBandiLinea();
                this.loadBeneficiari();
                this.loadProgetti();
              } else if (this.proposta.descBreveStatoPropostaCert === Constants.DESC_BREVE_STATO_PROPOSTA_INTERMEDIA_FINALE) {
                this.loadBandiLineaIntermediaFinale();
                this.loadBeneficiariIntermediaFinale();
                this.loadProgettiIntermediaFinale();
              }
            }
            this.loadedProposta = true;
          }, err => {
            this.handleExceptionService.handleBlockingError(err);
          });
        }
      });
    });
  }

  loadBandiLinea() {
    this.loadedBandiLinea = false;
    this.subscribers.bandiLinea = this.certificazioneService.getBandoLineaDaProposta(this.idProposta).subscribe(data => {
      if (data) {
        this.bandiLinea = data.sort((a, b) => a.nomeBandoLinea.localeCompare(b.nomeBandoLinea));
        this.bandoLineaSelected = null;
      }
      this.loadedBandiLinea = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei bandi linea.");
      this.loadedBandiLinea = true;
    });
  }

  loadBeneficiari() {
    this.loadedBeneficiari = false;
    this.subscribers.beneficiari = this.certificazioneService.getAllBeneficiari(this.idProposta, !this.bandoLineaSelected ? this.proposta.idLineaDiIntervento : null, this.bandoLineaSelected ? this.bandoLineaSelected.nomeBandoLinea : null).subscribe(data => {
      if (data) {
        this.beneficiari = data;
        this.beneficiarioSelected = null;
      }
      this.loadedBeneficiari = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei beneficiari.");
      this.loadedBeneficiari = true;
    });
  }

  loadProgetti() {
    this.loadedProgetti = false;
    this.subscribers.progetti = this.certificazioneService.getAllProgetti(this.idProposta, this.bandoLineaSelected ? this.bandoLineaSelected.progrBandoLineaIntervento : null,
      this.bandoLineaSelected ? this.bandoLineaSelected.nomeBandoLinea : null, this.beneficiarioSelected ? this.beneficiarioSelected.idSoggetto : null,
      this.beneficiarioSelected ? this.beneficiarioSelected.denominazioneBeneficiario : null).subscribe(data => {
        if (data) {
          this.progetti = data;
          this.progettoSelected = null;
        }
        this.loadedProgetti = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetti.");
        this.loadedProgetti = true;
      });
  }

  loadBandiLineaIntermediaFinale() {
    this.loadedBandiLinea = false;
    this.subscribers.bandiLinea = this.certificazioneService.getAllBandoLineaIntermediaFinale(this.idProposta).subscribe(data => {
      if (data) {
        this.bandiLinea = data.sort((a, b) => a.nomeBandoLinea.localeCompare(b.nomeBandoLinea));
      }
      this.loadedBandiLinea = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di carimento dei bandi linea.");
      this.loadedBandiLinea = true;
    });
  }

  loadBeneficiariIntermediaFinale() {
    this.loadedBeneficiari = false;
    this.subscribers.beneficiari = this.certificazioneService.getAllBeneficiariIntermediaFinale(this.idProposta, this.bandoLineaSelected ? this.bandoLineaSelected.nomeBandoLinea : null).subscribe(data => {
      if (data) {
        this.beneficiari = data;
      }
      this.loadedBeneficiari = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore in fase di caricamento dei beneficiari.");
      this.loadedBeneficiari = true;
    });
  }

  loadProgettiIntermediaFinale() {
    this.loadedProgetti = false;
    this.subscribers.progetti = this.certificazioneService.getAllProgettiIntermediaFinale(this.idProposta, this.bandoLineaSelected ? this.bandoLineaSelected.nomeBandoLinea : null,
      this.beneficiarioSelected ? this.beneficiarioSelected.denominazioneBeneficiario : null).subscribe(data => {
        if (data) {
          this.progetti = data;
        }
        this.loadedProgetti = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore in fase di caricamento dei progetti.");
        this.loadedProgetti = true;
      });
  }

  salvaNuovoStato() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedSalva = false;
    let request = new AggiornaStatoRequest(this.idProposta, this.nuovoStatoSelected.idStatoPropostaCertif, this.user.idUtente);
    this.subscribers.aggiornaStato = this.certificazioneService.aggiornaStatoProposta(request).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.proposta.descStatoPropostaCertif = this.nuovoStatoSelected.descStatoPropostaCertif;
          this.proposta.descBreveStatoPropostaCert = this.nuovoStatoSelected.descBreveStatoPropostaCert;
          this.showMessageSuccess(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedSalva = true;
      this.showMessageError("Errore nel salvataggio dello stato.");
    });
  }

  onSelectBandoLinea() {
    if (this.proposta.descBreveStatoPropostaCert === Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA) {
      this.loadBeneficiari();
    } else {
      this.loadBeneficiariIntermediaFinale();
    }
    this.beneficiarioSelected = null;
    this.progettoSelected = null;
  }

  onSelectBeneficiario() {
    if (this.proposta.descBreveStatoPropostaCert === Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA) {
      this.loadProgetti();
    } else {
      this.loadProgettiIntermediaFinale();
    }
    this.progettoSelected = null;
  }

  formatValue(idProgetto: number, value: string) {
    let p = this.elencoProgetti.find(p => p.idProgetto === idProgetto);
    if (p) {
      if (!value) value = '0';
      value = value.replace(/[.]/g, '');
      value = value.replace(',', '.');
      p.importoModificabile = new DecimalPipe('it').transform(value, '0.2');
    }
  }

  formatValueInit(idProgetto: number, value: string) {
    let p = this.elencoProgetti.find(p => p.idProgetto === idProgetto);
    if (p) {
      p.importoModificabile = new DecimalPipe('it').transform(value, '0.2');
    }
  }

  formatValueIFAnnuale(idProgetto: number, importoCertifNettoAnnual: string) {
    let p = this.elencoProgettiIF.find(p => p.idProgetto === idProgetto);
    if (p) {
      if (!importoCertifNettoAnnual) importoCertifNettoAnnual = '0';
      importoCertifNettoAnnual = importoCertifNettoAnnual.replace(/[.]/g, '');
      importoCertifNettoAnnual = importoCertifNettoAnnual.replace(',', '.');
      p.importoCertifNettoAnnualModificabile = new DecimalPipe('it').transform(importoCertifNettoAnnual, '0.2');
    }
  }

  formatValueIFColonnaC(idProgetto: number, colonnaC: string) {
    let p = this.elencoProgettiIF.find(p => p.idProgetto === idProgetto);
    if (p) {
      if (!colonnaC) colonnaC = '0';
      colonnaC = colonnaC.replace(/[.]/g, '');
      colonnaC = colonnaC.replace(',', '.');
      p.colonnaCModificabile = new DecimalPipe('it').transform(colonnaC, '0.2');
    }
  }

  formatValueInitIF(idProgetto: number, importoCertifNettoAnnual: string, colonnaC: string) {
    let p = this.elencoProgettiIF.find(p => p.idProgetto === idProgetto);
    if (p) {
      p.importoCertifNettoAnnualModificabile = new DecimalPipe('it').transform(importoCertifNettoAnnual, '0.2');
      p.colonnaCModificabile = new DecimalPipe('it').transform(colonnaC, '0.2');
    }
  }

  ricerca() {
    if (this.proposta.descBreveStatoPropostaCert === Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA) {
      this.ricercaApprovata();
    } else {
      this.ricercaIntermediaFinale();
    }
  }

  ricercaApprovata() {
    this.loadedCerca = false;
    let filtro = new FiltroRicercaProgettiNP(this.proposta.idLineaDiIntervento.toString(), this.beneficiarioSelected ? this.beneficiarioSelected.denominazioneBeneficiario : null,
      this.progettoSelected ? this.progettoSelected.codiceProgetto : null, this.bandoLineaSelected ? this.bandoLineaSelected.nomeBandoLinea : null, this.progettiModificati == null ? false : this.progettiModificati)
    this.subscribers.cerca = this.certificazioneService.getGestisciProposta(this.idProposta, new GestisciPropostaRequest(filtro)).subscribe(data => {
      if (data) {
        this.elencoProgetti = data;
        this.elencoProgetti.forEach(p => {
          this.formatValueInit(p.idProgetto, p.importoCertificazioneNetto.toString());
        });
        this.dataSource = new ElencoProgettiDatasource(this.elencoProgetti);
        this.paginator.length = this.elencoProgetti.length;
        this.paginator.pageIndex = 0;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.criteriRicercaOpened = false;
      } else {
        this.elencoProgetti = null;
        this.dataSource = null;
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCerca = true;
    });
  }

  ricercaIntermediaFinale() {
    this.loadedCerca = false;
    let request = new GestisciPropostaIntermediaFinaleRequest(this.bandoLineaSelected ? this.bandoLineaSelected.nomeBandoLinea : null, this.progettoSelected ? this.progettoSelected.codiceProgetto : null,
      this.beneficiarioSelected ? this.beneficiarioSelected.denominazioneBeneficiario : null, this.proposta.idLineaDiIntervento);
    this.subscribers.cerca = this.certificazioneService.getGestisciPropostaIntermediaFinale(this.idProposta, request).subscribe(data => {
      if (data) {
        this.elencoProgettiIF = data;
        this.elencoProgettiIF.forEach(p => this.formatValueInitIF(p.idProgetto, p.importoCertifNettoAnnual.toString(), p.colonnaC.toString()));
        this.dataSourceIF = new ElencoProgettiIFDatasource(this.elencoProgettiIF);
        this.paginatorIF.length = this.elencoProgettiIF.length;
        this.paginatorIF.pageIndex = 0;
        this.dataSourceIF.paginator = this.paginatorIF;
        this.dataSourceIF.sort = this.sortIF;
        this.criteriRicercaOpened = false;
      } else {
        this.elencoProgettiIF = null;
        this.dataSourceIF = null;
      }
      this.loadedCerca = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.loadedCerca = true;
    });
  }

  save() {
    if (this.proposta.descBreveStatoPropostaCert === Constants.DESC_BREVE_STATO_PROPOSTA_APPROVATA) {
      this.saveApprovata();
    } else {
      this.validateIntermediaFinale();
    }
  }

  saveApprovata() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedSalva = false;
    this.elencoProgettiModificati = new Array<ProgettoNuovaCertificazioneDTO>();
    let elencoProgettiModificatiNonCompleti: string = "";
    let i: number = 0;
    for (let p of this.elencoProgetti) {
      let value = p.importoModificabile.replace(/[.]/g, '');
      p.importoCertificazioneNetto = +value.replace(',', '.');
      if (p.importoCertificazioneNetto <= p.impCertifNettoPremodifica && !(p.nota === "" || p.nota === null)) {
        this.elencoProgettiModificati.push(p);
      } else if (p.importoCertificazioneNetto > p.impCertifNettoPremodifica
        || (p.importoCertificazioneNetto < p.impCertifNettoPremodifica && (p.nota === "" || p.nota === null))) {
        elencoProgettiModificatiNonCompleti += p.codiceProgetto + ", ";
        if (p.importoCertificazioneNetto < p.impCertifNettoPremodifica && (p.nota === "" || p.nota === null)) {
          this.progettiForm.form.get('nota' + i).setErrors({ error: 'required' });
          this.progettiForm.form.get('nota' + i).markAsTouched();
        } else {
          this.progettiForm.form.get('importo' + i).setErrors({ error: 'notValid' });
          this.progettiForm.form.get('importo' + i).markAsTouched();
        }
      }
      i++;
    }
    if (elencoProgettiModificatiNonCompleti.length > 0) {
      this.loadedSalva = true;
      this.showMessageError("Errore nel salvataggio dei seguenti progetti: " + elencoProgettiModificatiNonCompleti.substring(0, elencoProgettiModificatiNonCompleti.length - 2) + ".");
    } else if (this.elencoProgettiModificati.length === 0) {
      this.loadedSalva = true;
      this.showMessageError("Nessun progetto è stato modificato.");
    } else {
      let progetti: Array<ProgettoNuovaCertificazione> = this.elencoProgettiModificati.map(p => new ProgettoNuovaCertificazione(p.importoCertificazioneNetto, p.idProgetto, p.idDettPropostaCertif, this.idProposta, p.nota));
      this.subscribers.aggiornaImporto = this.certificazioneService.aggiornaImportoNetto(new AggiornaImportoNettoRequest(progetti)).subscribe(data => {
        if (data) {
          if (data.esito) {
            /*this.subscribers.check = this.certificazioneService.checkProceduraAggiornamentoTerminata().subscribe(data2 => {
              if (data2) {
                this.showMessageSuccess(data.msg);
              }
            });*/
            this.showMessageSuccess(data.msg);
          } else {
            this.showMessageError(data.msg);
          }
        }
        this.loadedSalva = true;
      }, err => {
        this.handleExceptionService.handleNotBlockingError(err);
        this.showMessageError("Errore nel salvataggio.");
        this.loadedSalva = true;
      });
    }
  }

  validateIntermediaFinale() {
    let codiciProgetto: Array<string> = new Array<string>();
    this.elencoProgettiIF.forEach(p => {
      let valueString: string = p.importoCertifNettoAnnualModificabile.replace(/[.]/g, '');
      let valueNumber: number = +valueString.replace(',', '.');
      if (valueNumber > p.importoCertifNettoAnnual) {
        if (!codiciProgetto.find(c => c === p.codiceProgetto)) {
          codiciProgetto.push(p.codiceProgetto);
        }
      }
      valueString = p.colonnaCModificabile.replace(/[.]/g, '');
      valueNumber = +valueString.replace(',', '.');
      if (valueNumber > p.colonnaC) {
        if (!codiciProgetto.find(c => c === p.codiceProgetto)) {
          codiciProgetto.push(p.codiceProgetto);
        }
      }
    });
    if (codiciProgetto.length > 0) {
      let msg: string = "I seguenti progetti sono stati modificati in aumento:<br>[";
      codiciProgetto.forEach((c, i) => {
        msg += c;
        if (i !== codiciProgetto.length - 1) {
          msg += ", ";
        } else {
          msg += "]<br>Si desidera procedere con il salvataggio dei dati inseriti?";
        }
      });

      let dialogRef = this.dialog.open(ConfermaDialog, {
        width: '50%',
        data: {
          message: msg
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result === "SI") {
          this.saveIntermediaFinale();
        }
      });
    } else {
      this.saveIntermediaFinale();
    }
  }

  saveIntermediaFinale() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedSalva = false;
    this.elencoProgettiIF.forEach(p => {
      let value = p.importoCertifNettoAnnualModificabile.replace(/[.]/g, '');
      p.importoCertifNettoAnnual = +value.replace(',', '.');
      value = p.colonnaCModificabile.replace(/[.]/g, '');
      p.colonnaC = +value.replace(',', '.');
    });
    this.subscribers.aggiornaImporto = this.certificazioneService.modificaProgettiIntermediaFinale(new AggiornaDatiIntermediaFinaleRequest(this.elencoProgettiIF.map(p => {
      return new ProgettoCertificazioneIntermediaFinaleDTO(p.idDettPropCertAnnual, p.idDettPropostaCertif, p.idPropostaCertificaz, p.importoRevocheRilevCum, p.importoRecuperiCum,
        p.importoSoppressioniCum, p.importoErogazioniCum, p.importoPagamValidCum, p.importoCertifNettoAnnual, p.dataAgg, p.idUtenteAgg, p.certificatoNettoCumulato,
        p.certificatoLordoCumulato, p.colonnaC, p.descBreveStatoPropostaCert, p.descStatoPropostaCertif, p.descProposta, p.beneficiario, p.codiceProgetto, p.nomeBandoLinea,
        p.idStatoPropostaCertif, p.idLineaDiIntervento, p.idProgetto, p.asse, p.diffCna, p.diffRev, p.diffRec, p.diffSoppr);
    }))).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.showMessageSuccess(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nel salvataggio.");
      this.loadedSalva = true;
    });
  }

  termina() {
    this.isTerminated = true;
    this.bandoLineaSelectedPrevious = this.bandoLineaSelected;
    this.beneficiarioSelectedPrevious = this.beneficiarioSelected;
    this.progettoSelectedPrevious = this.progettoSelected;
    this.progettiModificatiPrevious = this.progettiModificati;
    this.bandoLineaSelected = null;
    this.beneficiarioSelected = null;
    this.progettoSelected = null;
    this.progettiModificati = true;
    this.ricercaApprovata();
  }

  inviaReport() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedSalva = false;
    this.subscribers.inviaReport = this.certificazioneService.inviaReportPostGestione(new InviaReportRequest(this.proposta)).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.isInviaReport = true;
          this.showMessageSuccess(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedSalva = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nell'invio del report.");
      this.loadedSalva = true;
    });
  }

  aggiornaDati() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.certificazioneService.aggiornaDatiIntermediaFinale(this.idProposta);
    this.aggiornaDatiClicked = true;
    this.showMessageSuccess("Aggiornamento dati in elaborazione sarà inviata un'email al termine dell’elaborazione.");
  }

  chiusuraConti() {
    this.resetMessageSuccess();
    this.resetMessageError();
    this.loadedChiusuraConti = false;
    this.subscribers.chiusuraConti = this.certificazioneService.chiusuraContiPropostaIntermediaFinale(this.idProposta).subscribe(data => {
      if (data) {
        if (data.esito) {
          this.isChiusuraConti = true;;
          this.showMessageSuccess(data.msg);
        } else {
          this.showMessageError(data.msg);
        }
      }
      this.loadedChiusuraConti = true;
    }, err => {
      this.handleExceptionService.handleNotBlockingError(err);
      this.showMessageError("Errore nella chiusura conti.");
      this.loadedChiusuraConti = true;
    });
  }

  indietro() {
    this.bandoLineaSelected = this.bandoLineaSelectedPrevious;
    this.beneficiarioSelected = this.beneficiarioSelectedPrevious;
    this.progettoSelected = this.progettoSelectedPrevious;
    this.progettiModificati = this.progettiModificatiPrevious;
    this.isTerminated = false;
    this.ricercaApprovata();
  }

  goToGestioneProposte() {
    this.router.navigateByUrl("/drawer/" + Constants.ID_OPERAZIONE_CERTIFICAZIONE_GESTIONE_PROPOSTE + "/gestioneProposte");
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
    if (!this.loadedProposta || !this.loadedStati || !this.loadedBandiLinea || !this.loadedSalva || !this.loadedBeneficiari || !this.loadedProgetti || !this.loadedCerca || !this.loadedChiusuraConti) {
      return true;
    }
    return false;
  }

  scaricaInExcel(idProposta: number, elencoProgetti: Array<ProgettoNuovaCertificazioneDTO>) {
    alert
    this.excelService.generaExcel(idProposta.toString(), elencoProgetti);
  }

  scaricaInExcelIF(idProposta: number, elencoProgettiIF: Array<ProgettoCertificazioneIntermediaFinaleDTO>) {
    this.excelService.generaExcelIF(idProposta.toString(), elencoProgettiIF);
  }
}

export class ElencoProgettiDatasource extends MatTableDataSource<ProgettoNuovaCertificazioneDTO> {

  _orderData(data: ProgettoNuovaCertificazioneDTO[]): ProgettoNuovaCertificazioneDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "idProgetto"; //default
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "codiceprogetto":
        sortedData = data.sort((propA, propB) => {
          return propA.codiceProgetto.localeCompare(propB.codiceProgetto);
        });
        break;
      case "beneficiario":
        sortedData = data.sort((propA, propB) => {
          return propA.denominazioneBeneficiario.localeCompare(propB.denominazioneBeneficiario);
        });
        break;
      case "nettoADG":
        sortedData = data.sort((propA, propB) => {
          return propA.impCertifNettoPremodifica - propB.impCertifNettoPremodifica;
        });
        break;
      default:
        sortedData = data.sort((propA, propB) => {
          return propA.idProgetto - propB.idProgetto;
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: ProgettoNuovaCertificazioneDTO[]) {
    super(data);
  }
}

export class ElencoProgettiIFDatasource extends MatTableDataSource<ProgettoCertificazioneIntermediaFinaleDTO> {

  _orderData(data: ProgettoCertificazioneIntermediaFinaleDTO[]): ProgettoCertificazioneIntermediaFinaleDTO[] {

    if (!this.sort) return data;
    if (!this.sort.direction || this.sort.direction.length === 0) {
      this.sort.active = "asse"; //default
    }
    let direction = this.sort.direction || "asc";
    let sortedData = null;

    // viene riordinato in ordine ascendente
    switch (this.sort.active) {
      case "codiceprogetto":
        sortedData = data.sort((propA, propB) => {
          return propA.codiceProgetto.localeCompare(propB.codiceProgetto);
        });
        break;
      case "beneficiario":
        sortedData = data.sort((propA, propB) => {
          return propA.beneficiario.localeCompare(propB.beneficiario);
        });
        break;
      case "asse":
        sortedData = data.sort((propA, propB) => {
          return propA.asse.localeCompare(propB.asse);
        });
        break;
      default:
        sortedData = data.sort((propA, propB) => {
          return propA.asse.localeCompare(propB.asse);
        });
        break;
    }
    return (direction === "desc") ? sortedData.reverse() : sortedData;
  }

  constructor(data: ProgettoCertificazioneIntermediaFinaleDTO[]) {
    super(data);
  }
}
