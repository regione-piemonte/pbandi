/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { UserService } from 'src/app/core/services/user.service';
import { AcquisizioneCampionamentiService } from '../../services/acquisizione-campionamenti.service';
import { ControlliService } from 'src/app/gestione-controlli/services/controlli.service';
import { AttivitaDTO } from 'src/app/gestione-crediti/commons/dto/attivita-dto';
import { log } from 'console';
import { SearchAcqProgVO } from '../../commons/searchAcqProgVO';
import { format } from 'path';
import { MatTableDataSource } from '@angular/material/table';
import { ProgettoCampioneVO } from '../../commons/progetto-campione-vo';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { SelectionModel } from '@angular/cdk/collections';
import { Constants } from 'src/app/core/commons/util/constants';
import { Router } from '@angular/router';

@Component({
  selector: 'app-acquisizione-progetti-campionati',
  templateUrl: './acquisizione-progetti-campionati.component.html',
  styleUrls: ['./acquisizione-progetti-campionati.component.scss']
})
export class AcquisizioneProgettiCampionatiComponent implements OnInit {

  userloaded: boolean = true;
  subscribers: any = {};
  user: UserInfoSec;
  idUtente: any;
  formAcquisizione!: FormGroup;
  listaAutoritaControllante: AttivitaDTO[] = [];
  loadedStControllo: boolean = true;
  listNormative: any;
  loadedNormative: boolean;
  search: SearchAcqProgVO = new SearchAcqProgVO();
  criteriRicercaOpened: boolean = true;
  progettiAcquisiti: any;
  isMessageErrorVisible: boolean;
  messageError: string;
  sortedData = new MatTableDataSource<ProgettoCampioneVO>([]);
  displayedColumns: string[] = ['select', 'idProgetto', 'codiceVisualizzatoProgetto', "denominazioneBeneficiario"];
  isProgettiCampioni: boolean;
  isAcquisiti: boolean;
  selection = new SelectionModel<any>(true, []);
  numeroSelezionati: number;
  progettiScartati: string = "";
  isAcqLoaded: boolean;
  messageSuccess: any;
  isMessageSuccessVisible: boolean;
  progetti: Array<number>;
  constructor(
    private userService: UserService,
    private handleExceptionService: HandleExceptionService,
    private fb: FormBuilder,
    private acquisizioneCampionamentiService: AcquisizioneCampionamentiService,
    private router: Router
  ) { }
  @ViewChild("campionamento") paginator: MatPaginator;
  @ViewChild("campionamentoSort") sort: MatSort;
  ngOnInit(): void {

    this.userloaded = false;
    this.subscribers.idUtente = this.userService.userInfo$.subscribe(data => {

      if (data) {
        this.user = data;
        this.idUtente = this.user.idUtente;
        this.userloaded = true;
      }
    });

    this.formAcquisizione = this.fb.group({
      normativa: [{ value: '' }],
      numCampionamento: new FormControl(''),
      descrizioneCampionamento: new FormControl(''),
      dataCampione: (''),
      elencoProgettiCampionati: new FormControl('')
    });
    // this.getElencoAutoritaControllante(); 
    this.getElencoNormative();
  }
  // getElencoAutoritaControllante(){
  //   this.subscribers.listaAutoritaControllante = this.controlliService.getAutoritaControlante().subscribe({
  //     next: data => this.listaAutoritaControllante = data,
  //     error: errore => console.log(errore),
  //     complete: () => this.loadedStControllo = true
  //   })
  // }
  getElencoNormative() {
    this.subscribers.listaAutoritaControllante = this.acquisizioneCampionamentiService.getNormative().subscribe({
      next: data => this.listNormative = data,
      error: errore => console.log(errore),
      complete: () => this.loadedNormative = true
    })
  }
  changeElencoProgetti() {
    let elencoProgetti: string = null;
    elencoProgetti = this.formAcquisizione.get("elencoProgettiCampionati").value;
    console.log(elencoProgetti);

    if (elencoProgetti != null) {
      elencoProgetti = elencoProgetti.replace(/\n/g, ";");
      this.formAcquisizione.get("elencoProgettiCampionati").setValue(elencoProgetti);
    }
  }
  prosegui() {
    console.log("prosegui");
    this.isAcqLoaded = false;
    this.resetMessageError();
    if (this.formAcquisizione.get("dataCampione").value && this.formAcquisizione.get("dataCampione")?.value.toString().length > 1) {
      this.search.dataCampione = this.formAcquisizione.get("dataCampione")?.value;
      console.log("its goood date value");
      console.log(this.formAcquisizione.get("dataCampione")?.value.toString());

    }
    this.search.numCampionamento = this.formAcquisizione.get("numCampionamento").value;
    this.search.descCampionamento = this.formAcquisizione.get("descrizioneCampionamento").value;
    this.search.idBandoLineaIntervent = this.formAcquisizione.get('normativa').value.idLineaIntervento
    this.search.progetti = this.formAcquisizione.get("elencoProgettiCampionati").value
    this.search.progettiConfermati = null;
    console.log(this.search);
    this.subscribers.acquisisciProgetti = this.acquisizioneCampionamentiService.acquisisciProgetti(this.search).subscribe(data => {
      if (data) {
        if (data?.esito == false) {
          this.showMessageError(data.messaggio)
          this.isProgettiCampioni = false;
          this.isAcqLoaded = true;
        } else {
          this.isProgettiCampioni = true;
          this.progettiAcquisiti = data;
          this.sortedData = new MatTableDataSource<ProgettoCampioneVO>(this.progettiAcquisiti.progettiDaAggiungere);
          this.paginator.length = this.progettiAcquisiti.progettiDaAggiungere.length;
          this.paginator.pageIndex = 0;
          this.sortedData.paginator = this.paginator;
          this.sortedData.sort = this.sort;
          this.progettiScartati = data.progettiScartati;
          data.progettiDaAggiungere?.length > 0 ? this.isAcquisiti = true : this.isAcquisiti = false;
          this.isAcqLoaded = true;
        }

      }
    })

    this.criteriRicercaOpenClose()
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.sortedData.data?.filter((d: { isConfirmable: boolean; }) => d.isConfirmable != false).length;
    return numSelected === numRows;
  }

  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      this.numeroSelezionati = this.selection.selected.length;
      return;
    }
    this.selection.select(...this.sortedData.data?.filter(d => d.isConfirmable != false));
    this.numeroSelezionati = this.selection.selected.length;
  }

  checkboxLabel(row?: any): string {
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    } this.numeroSelezionati = this.selection.selected.length;
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;

  }
  confermaAcquisizione() {
    console.log(this.selection.selected);
    console.log(this.search);


    this.progetti = this.selection.selected.map(p => p.idProgetto);

    this.subscribers.confermaAcquisizione = this.acquisizioneCampionamentiService.confermaAcquisizione(this.progetti, this.search, this.idUtente).subscribe(data => {
      if (data) {
        console.log(data);
        if (data.esito == true) {
          this.showMessageSuccess(data.messaggio)
        } else {
          this.showMessageError(data.messaggio)
        };

        setTimeout(() => {
          this.goBack();
        }, 3000);
      }
    })

  }
  goBack() {
    this.router.navigate(["/drawer/" + Constants.ID_OPERAZIONE_AREA_CONTROLLI_LOCO_RICERCA_FIN + "/ricercaControlliProgettiFinp"], { queryParams: {} });
  }
  resetMessageSuccess() {
    this.messageSuccess = null;
    this.isMessageSuccessVisible = false;
  }
  showMessageSuccess(msg: string) {
    this.isMessageSuccessVisible = true;
    this.messageSuccess = msg;
  }
  showMessageError(msg: string) {
    this.messageError = msg;
    this.isMessageErrorVisible = true;
  }
  resetMessageError() {
    this.messageError = null;
    this.isMessageErrorVisible = false;
  }
  criteriRicercaOpenClose() {
    this.criteriRicercaOpened = !this.criteriRicercaOpened;
  }
  setDataNull() {
    this.formAcquisizione.get("dataCampione").setValue(null);
  }
  isLoading() {
    if (!this.userloaded && !this.loadedNormative && !this.isAcqLoaded) {
      return true;
    }
    return false;
  }

}
