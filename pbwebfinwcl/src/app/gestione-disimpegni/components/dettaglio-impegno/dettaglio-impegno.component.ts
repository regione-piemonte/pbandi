/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Constants } from '@pbandi/common-lib';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { UserService } from 'src/app/core/services/user.service';
import { GestioneImpegniService } from '../../services/gestione-impegni.service';

@Component({
  selector: 'app-dettaglio-impegno',
  templateUrl: './dettaglio-impegno.component.html',
  styleUrls: ['./dettaglio-impegno.component.scss']
})
export class DettaglioImpegnoComponent implements OnInit {

  // Dichiarazione variabili generali
  annoEsercizio: string;
  annoImpegno: string;
  numeroImpegno: string;
  // Dichiarazione variabili sezione impegno
  descrizioneImpegno: string;
  stato: string;
  importoIniziale: string;
  importoAttuale: string;
  totaleLiquidato: string;
  disponibilitaLiquidare: string;
  totaleQuietanziato: string;
  dataInserimento: string;
  dataUltimaModifica: string;
  cup: string;
  cig: string;
  nCapitoloOrigine: string;
  // Dichiarazione variabili sezione provvedimento
  direzioneProvvedimento: string;
  annoProvvedimento: string;
  tipo: string;
  numeroProvvedimento: string;
  // Dichiarazione variabili sezione capitolo di spesa
  descrizioneCapitoloSpesa: string;
  tipoFondo: string;
  numeroCapitoloSpesa: string;
  numeroArticolo: string;
  direzioneCapitoloSpesa: string;
  // Dichiarazione variabili sezione reimputato
  annoReimputato: string;
  numero: string;

  subscribers: any = {};
  params: URLSearchParams;
  user: UserInfoSec;

  // loaaded
  loadedCercaDettaglioAtto: boolean;

  constructor(private router: Router,
    private userService: UserService,
    private gestioneImpegniService: GestioneImpegniService,
    private _snackBar: MatSnackBar) { }

  ngOnInit(): void {

    this.subscribers.userInfo$ = this.userService.userInfo$.subscribe(data => {
      if (data) {
        this.user = data;
      }
    });

    this.params = (new URL('https://www.example.com?' + window.location.href.split("?").pop())).searchParams;

    this.loadedCercaDettaglioAtto = true;
    this.gestioneImpegniService.cercaDettaglioAtto(this.user, this.user.idSoggetto.toString(), this.params.get('idImpegno')).subscribe(data => {
      this.loadedCercaDettaglioAtto = false;
      // Settaggio variabili generali
      this.annoEsercizio = data.impegnoDTO.annoEsercizio == undefined ? undefined : data.impegnoDTO.annoEsercizio.toString();
      this.annoImpegno = data.impegnoDTO.annoImpegno == undefined ? undefined : data.impegnoDTO.annoImpegno.toString();
      this.numeroImpegno = data.impegnoDTO.numeroImpegno == undefined ? undefined : data.impegnoDTO.numeroImpegno.toString();
      // Settaggio variabili sezione impegno
      this.descrizioneImpegno = data.impegnoDTO.descImpegno;
      this.stato = data.impegnoDTO.statoImpegno.descStatoImpegno;
      this.importoIniziale = data.impegnoDTO.importoInizialeImpegno == undefined ? undefined : data.impegnoDTO.importoInizialeImpegno.toString();
      this.importoAttuale = data.impegnoDTO.importoAttualeImpegno == undefined ? undefined : data.impegnoDTO.importoAttualeImpegno.toString();
      this.totaleLiquidato = data.impegnoDTO.totaleLiquidatoImpegno == undefined ? undefined : data.impegnoDTO.totaleLiquidatoImpegno.toString();
      this.disponibilitaLiquidare = data.impegnoDTO.disponibilitaLiquidare == undefined ? undefined : data.impegnoDTO.disponibilitaLiquidare.toString();
      this.totaleQuietanziato = data.impegnoDTO.totaleQuietanzatoImpegno == undefined ? undefined : data.impegnoDTO.totaleQuietanzatoImpegno.toString();
      this.dataInserimento = data.impegnoDTO.dtInserimento == undefined ? undefined : new Date(Number(data.impegnoDTO.dtInserimento)).toLocaleDateString();
      this.dataUltimaModifica = data.impegnoDTO.dtAggiornamento == undefined ? undefined : new Date(Number(data.impegnoDTO.dtAggiornamento)).toLocaleDateString();
      this.cup = data.impegnoDTO.cupImpegno;
      this.cig = data.impegnoDTO.cigImpegno;
      this.nCapitoloOrigine = data.impegnoDTO.numeroCapitoloOrigine == undefined ? undefined : data.impegnoDTO.numeroCapitoloOrigine.toString();
      // Settaggio variabili sezione provvedimento
      this.direzioneProvvedimento = data.impegnoDTO.provvedimento.enteCompetenza.descEnte;
      this.annoProvvedimento = data.impegnoDTO.provvedimento.annoProvvedimento == undefined ? undefined : data.impegnoDTO.provvedimento.annoProvvedimento.toString();
      this.tipo = data.impegnoDTO.provvedimento.tipoProvvedimento.descTipoProvvedimento;
      this.numeroProvvedimento = data.impegnoDTO.provvedimento.numeroProvvedimento;
      // Settaggio variabili sezione capitolo di spesa
      this.descrizioneCapitoloSpesa = data.impegnoDTO.capitolo.descCapitolo;
      this.tipoFondo = data.impegnoDTO.capitolo.tipoFondo.descTipoFondo;
      this.numeroCapitoloSpesa = data.impegnoDTO.capitolo.numeroCapitolo == undefined ? undefined : data.impegnoDTO.capitolo.numeroCapitolo.toString();
      this.numeroArticolo = data.impegnoDTO.capitolo.numeroArticolo == undefined ? undefined : data.impegnoDTO.capitolo.numeroArticolo.toString();
      this.direzioneCapitoloSpesa = data.impegnoDTO.capitolo.enteCompetenza.descEnte;
      // Settaggio variabili sezione reimputato
      this.annoReimputato = data.impegnoDTO.annoPerente == undefined ? undefined : data.impegnoDTO.annoPerente.toString();
      this.numero = data.impegnoDTO.numeroPerente == undefined ? undefined : data.impegnoDTO.numeroPerente.toString();
    }, err => {
      this.loadedCercaDettaglioAtto = false;
      this.openSnackBar("Errore in fase di caricamento degli anni esercizio");
    });
  }

  tornaAllaRicercaImpegni() {
    this.router.navigateByUrl("drawer/" + Constants.ID_OPERAZIONE_BILANCIO_GESTIONE_IMPEGNI + "/gestioneImpegni");
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "", {
      duration: 2000,
    });
  }

  isLoading() {
    if (this.loadedCercaDettaglioAtto) {
      return true;
    } else {
      return false;
    }
  }
}
