/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { DettaglioAttoDiLiquidazioneVO } from 'src/app/gestione-disimpegni/commons/dettaglio-atto-di-liquidazione-vo';
import { RicercaAttiService } from '../../services/ricerca-atti.service';
import { MatTabGroup } from '@angular/material/tabs';
import { DettaglioLiquidazioneVo } from 'src/app/gestione-disimpegni/commons/dettaglio-liquidazione-vo';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Component({
  selector: 'app-dettaglio-atto-liquidazione',
  templateUrl: './dettaglio-atto-liquidazione.component.html',
  styleUrls: ['./dettaglio-atto-liquidazione.component.scss']
})
export class DettaglioAttoLiquidazioneComponent implements OnInit, AfterViewChecked {

  // Dichiarazioni variabili tab dati generali
  numeroDocumentoSpesa: string;
  descrizioneStatoDocumentoSpesa: string;
  importoAtto: string;
  causale: string;
  note: string;
  stato: string;
  testoRichiestaModifica: string;
  dataInserimentoDatiGenerali: string;
  dataAggiornamentoDatiGenerali: string;
  dataAggiornamentoBilancio: string;
  dataEmissione: string;
  dataScadenza: string;
  dataCompletamento: string;
  dataAnnullamento: string;
  dataRicezione: string;
  dataRifiuto: string;
  dataRichiestaModifica: string;
  // Dichiarazioni variabili tab beneficiario
  codiceBeneficiarioBilancio: string;
  denominazioneNominativo: string;
  ragioneSocialeAggiuntiva: string;
  indirizzo: string;
  comune: string;
  provincia: string;
  codiceFiscale: string;
  partitaIVA: string;
  dataInserimentoBeneficiario: string;
  dataAggiornamentoBeneficiario: string;
  email: string;
  telefono: string;
  fax: string;
  // Dichiarazioni variabili tab dati integrativi
  settoreAppartenenza: string;
  enteCompetenza: string;
  funzionarioLiquidatore: string;
  numeroTelefono: string;
  dirigente: string;
  modalitaAgevolazione: string;
  causaleErogazione: string;
  quietanzante: string;
  codicefiscale: string;
  dataInserimentoPagamento: string;
  dataAggiornamentoPagamento: string;
  modalitaErogazionePagamento: string;
  estremiBancariPagamento: string;
  beneficiarioCedente: string;
  beneficiarioCeduto: string;
  dataInserimentoBenefCedCed: string;
  dataAggiornamentoBenefCedCed: string;
  modalitaErogazioneBenefCedCed: string;
  estremiBancariBenefCedCed: string;
  // Dichiarazioni variabili tab ritenute
  naturaOnereCodiceTributo: string;
  imponibile: string;
  sommeNonSoggette: string;
  // Dichiarazioni variabili tab liquidazioni
  numeroLiquidazione: string;
  dataInserimento: string;
  importoLiquidato: string;
  annoImpegno: string;
  numeroImpegno: string;
  annoEsercizio: string;
  cupImpegno: string;
  cigImpegno: string;
  totaleLiquidato: string;
  totaleQuietanziato: string;
  numeroCapitolo: string;
  tipoFondo: string;
  numeroMandato: string;
  quietanza: string;
  cupMandato: string;
  cigMandato: string;
  importoLordo: string;
  importoRitenute: string;
  importoNetto: string;
  importoQuietanziato: string;
  dataInserimentoMandato: string;
  dataAggiornamentoMandato: string;
  idAttoLiquidazione: string;

  liquidazioneSelected: DettaglioLiquidazioneVo;

  messageError: string;
  isMessageErrorVisible: boolean;

  // Dichiarazioni variabili generali
  subscribers: any = {};
  attoLiquidazione: string;
  beneficiario: string;
  codiceProgetto: string;
  user: UserInfoSec;
  params: URLSearchParams;
  dataDettaglioAtto: DettaglioAttoDiLiquidazioneVO;
  consultazioneAtto: boolean;

  idProgetto: string;
  idBando: string;

  @ViewChild(MatTabGroup, { static: false }) tabs: MatTabGroup;

  // loaaded
  loadedCercaDettaglioAtto: boolean;

  constructor(
    private router: Router,
    private userService: UserService,
    private ricercaAttiService: RicercaAttiService,
    private handleExceptionService: HandleExceptionService
  ) { }

  ngOnInit(): void {

    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;
    this.idAttoLiquidazione = this.params.get('idAttoLiquidazione');
    this.consultazioneAtto = Boolean(this.params.get('consultazioneAtto'));

    this.idProgetto = this.params.get('idProgetto');
    this.idBando = this.params.get('idBando');

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data?.codiceRuolo) {
        this.user = data;

        this.loadedCercaDettaglioAtto = true;
        this.ricercaAttiService.cercaDettaglioAtto(this.user, this.idAttoLiquidazione).subscribe(data => {
          this.loadedCercaDettaglioAtto = false;
          this.dataDettaglioAtto = data;

          this.attoLiquidazione = data.annoAtto + "/" + data.numeroAtto;

          this.beneficiario = this.params.get('beneficiario');
          this.codiceProgetto = this.params.get('progetto');

          // Settaggio variabili tab dati generali
          this.numeroDocumentoSpesa = data.numeroDocumentoSpesa;
          this.descrizioneStatoDocumentoSpesa = data.descStatoDocumento;
          this.importoAtto = data.importoTotaleAtto == null ? undefined : data.importoTotaleAtto.toString();
          this.causale = data.descAtto;
          this.note = data.noteAtto;
          this.stato = data.descStatoAtto;
          this.testoRichiestaModifica = data.testoRichiestaModifica;
          this.dataInserimentoDatiGenerali = data.dtInserimentoAtto == undefined ? undefined : new Date(Number(data.dtInserimentoAtto)).toLocaleDateString();
          this.dataAggiornamentoDatiGenerali = data.dtAggiornamentoAtto == undefined ? undefined : new Date(Number(data.dtAggiornamentoAtto)).toLocaleDateString();
          this.dataAggiornamentoBilancio = data.dtAggiornamentoBilancio == undefined ? undefined : new Date(Number(data.dtAggiornamentoBilancio)).toLocaleDateString();
          this.dataEmissione = data.dtEmissioneAtto == undefined ? undefined : new Date(Number(data.dtEmissioneAtto)).toLocaleDateString();
          this.dataScadenza = data.dtScadenzaAtto == undefined ? undefined : new Date(Number(data.dtScadenzaAtto)).toLocaleDateString();
          this.dataCompletamento = data.dtCompletamentoAtto == undefined ? undefined : new Date(Number(data.dtCompletamentoAtto)).toLocaleDateString();
          this.dataAnnullamento = data.dtAnnullamentoAtto == undefined ? undefined : new Date(Number(data.dtAnnullamentoAtto)).toLocaleDateString();
          this.dataRicezione = data.dtRicezioneAtto == undefined ? undefined : new Date(Number(data.dtRicezioneAtto)).toLocaleDateString();
          this.dataRifiuto = data.dtRifiutoRagioneria == undefined ? undefined : new Date(Number(data.dtRifiutoRagioneria)).toLocaleDateString();
          this.dataRichiestaModifica = data.dtRichiestaModifica == undefined ? undefined : new Date(Number(data.dtRichiestaModifica)).toLocaleDateString();
          // Settaggio variabili tab beneficiario
          this.codiceBeneficiarioBilancio = data.dbcodiceBeneficiarioBilancio;
          this.denominazioneNominativo = data.dbdenominazione;
          this.ragioneSocialeAggiuntiva = data.dbragioneSocialeAgg;
          this.indirizzo = data.dbindirizzo;
          this.comune = data.dbcomune;
          this.provincia = data.dbprovincia;
          this.codiceFiscale = data.dbcodiceFiscale;
          this.partitaIVA = data.dbpartitaIVA;
          this.dataInserimentoBeneficiario = data.didataInserimentoBeneficiario == undefined ? undefined : new Date(Number(data.didataInserimentoBeneficiario)).toLocaleDateString();
          this.dataAggiornamentoBeneficiario = data.didataAggiornamentoBeneficiario == undefined ? undefined : new Date(Number(data.didataAggiornamentoBeneficiario)).toLocaleDateString();
          this.email = data.dbemail;
          this.telefono = data.dbtelefono;
          this.fax = data.dbfax;
          // Settaggio variabili tab dati integrativi
          this.settoreAppartenenza = data.disettoreAppartenenza;
          this.enteCompetenza = data.dienteCompetenza;
          this.funzionarioLiquidatore = data.difunzionarioLiquidatore;
          this.numeroTelefono = data.dinumeroTelefono;
          this.dirigente = data.didirigente;
          this.modalitaAgevolazione = data.dimodalitaAgevolazione;
          this.causaleErogazione = data.dicausaleErogazione;
          this.quietanzante = data.diquietanzante;
          this.codicefiscale = data.dicodiceFiscale;
          this.dataInserimentoPagamento = data.didataInserimento == undefined ? undefined : this.convertDate(data.didataInserimento.toString());
          this.dataAggiornamentoPagamento = data.didataAggiornamento == undefined ? undefined : this.convertDate(data.didataAggiornamento.toString());
          this.modalitaErogazionePagamento = data.dimodalitaErogazione;;
          this.estremiBancariPagamento = data.diestremiBancari;
          this.beneficiarioCedente = data.dibeneficiarioCedente;
          this.beneficiarioCeduto = data.dibeneficiarioCeduto;
          this.dataInserimentoBenefCedCed = data.didataInserimentoBeneficiario == undefined ? undefined : new Date(Number(data.didataInserimentoBeneficiario)).toLocaleDateString();
          this.dataAggiornamentoBenefCedCed = data.didataAggiornamentoBeneficiario == undefined ? undefined : new Date(Number(data.didataAggiornamentoBeneficiario)).toLocaleDateString();
          this.modalitaErogazioneBenefCedCed = data.dimodalitaErogazioneBeneficiario;
          this.estremiBancariBenefCedCed = data.diestremiBancariBeneficiario;
          // Settaggio variabili tab ritenute
          this.naturaOnereCodiceTributo = data.draliquotaRitenuta;
          this.imponibile = data.drimponibile;
          this.sommeNonSoggette = data.drsommeNonSoggette;
          // Settaggio variabili tab liquidazioni

          this.liquidazioneSelected = data.liquidazioni?.length > 0 ? data.liquidazioni[0] : null;
          if (this.liquidazioneSelected?.idLiquidazione) {
            this.dettaglioTabLiquidazioni(this.liquidazioneSelected.idLiquidazione);
          }
        }, err => {
          this.loadedCercaDettaglioAtto = false;
          this.handleExceptionService.handleNotBlockingError(err);
          this.showMessageError("Errore in fase di ottenimento dei dettagli dell'atto");
        });
      }
    });
  }

  ngAfterViewChecked() {
    this.tabs.realignInkBar();
  }

  tornaAllaConsultazione() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_ATTIVITA_DA_SVOLGERE_CONSULTAZIONE_ATTI_LIQUIDAZIONE + "/consultazioneAtti/" + this.idProgetto + "/" + this.idBando);
  }

  tornaARicercaAtti() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_RICERCA_ATTI_LIQUIDAZIONE + "/ricercaAttiLiquidazione");
  }

  dettaglioTabLiquidazioni(idLiquidazione: number) {
    this.dataDettaglioAtto.liquidazioni.forEach(element => {
      if (element.idLiquidazione == idLiquidazione) {
        this.numeroLiquidazione = element.numBilancioLiquidazione;
        this.dataInserimento = new Date(Number(element.dtInserimento)).toLocaleDateString();
        this.importoLiquidato = element.importoLiquidato == null ? undefined : element.importoLiquidato.toString();
        this.annoImpegno = element.annoImpegno;
        this.numeroImpegno = element.numeroImpegno;
        this.annoEsercizio = element.annoEsercizio;
        this.cupImpegno = element.cupImpegno;
        this.cigImpegno = element.cigImpegno;
        this.totaleLiquidato = element.totaleLiquidatoImpegno == null ? undefined : element.totaleLiquidatoImpegno.toString();
        this.totaleQuietanziato = element.totaleQuietanzatoImpegno == null ? undefined : element.totaleQuietanzatoImpegno.toString();
        this.numeroCapitolo = element.numeroCapitolo;
        this.tipoFondo = element.descTipoFondo;
        this.numeroMandato = element.numeroMandato;
        this.quietanza = element.dtQuietanzaMandato == null ? undefined : new Date(Number(element.dtQuietanzaMandato)).toLocaleDateString();
        this.cupMandato = element.cupMandato;
        this.cigMandato = element.cigMandato;
        this.importoLordo = element.importoMandatoLordo == null ? undefined : element.importoMandatoLordo.toString();
        this.importoRitenute = element.importoRitenute == null ? undefined : element.importoRitenute.toString();
        this.importoNetto = element.importoMandatoNetto == null ? undefined : element.importoMandatoNetto.toString();
        this.importoQuietanziato = element.importoQuietanzato == null ? undefined : element.importoQuietanzato.toString();
        this.dataInserimentoMandato = element.dtInserimentoMandato == null ? undefined : new Date(Number(element.dtInserimentoMandato)).toLocaleDateString();
        this.dataAggiornamentoMandato = element.dtAggiornamento == null ? undefined : new Date(Number(element.dtAggiornamento)).toLocaleDateString();
      }
    });
  }

  convertDate(input: string) {
    return input.split("-")[2] + "/" + input.split("-")[1] + "/" + input.split("-")[0];
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
    if (this.loadedCercaDettaglioAtto) {
      return true;
    } else {
      return false;
    }
  }
}
