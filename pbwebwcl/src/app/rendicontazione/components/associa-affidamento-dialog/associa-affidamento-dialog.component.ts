import { AffidamentoRendicontazioneDTO } from '../../commons/dto/affidamento-rendicontazione-dto';
import { HandleExceptionService } from './../../../core/services/handle-exception.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { DocumentoDiSpesaService } from '../../services/documento-di-spesa.service';

export interface PeriodicElement {
  enabled: boolean;
  id: number;
  CPA: string;
  CIG: string;
  Oggetto: string;
  Fornitori: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { enabled: true, id: 1, CPA: "DD_587/2019", CIG: 'Z27272DB02', Oggetto: "INTERVENTI DI RESTAURO DEI DIPINTI MURALI DELL'OTTAVA", Fornitori: 'FONDAZIONE CENTRO PER LA CONSERVAZIONE ED IL RESTAURO DEI BENI CULTURALI LA VENARIA REALE - 97662370010' },
  { enabled: true, id: 2, CPA: "DD1101/2019", CIG: 'Z3A27909B4', Oggetto: "affidamento incarico professionale per assistenza allo scavo archeologico e redazione documentazione", Fornitori: 'GABUTTI DOTT.SA ANTONELLA - 02049610021' },
  { enabled: true, id: 3, CPA: "DD_702/2019", CIG: 'ZCC24C686F', Oggetto: "INSTALLAZIONE PONTEGGIO PER LA REALIZZAZIONE DEGLI IN", Fornitori: 'RVENTI DI RESTAURO DEGLI AFFRESCHI	PINTO FRANCESCO - NOLEGGIO PONTEGGI ITALIA - 02680750011' },
  { enabled: false, id: 4, CPA: "DD_427/2019", CIG: '77311792BD', Oggetto: "INTERVENTI DI RESTAURO E RISANAMENTO CONSERVATIVO", Fornitori: 'MANUTENZIONI SRL - 05641980726' },
  { enabled: true, id: 5, CPA: "DD_3808/2018", CIG: 'ZE525E0A63', Oggetto: "INTERVENTI DIRESTAURO DEI DIPINTI MURALI DELL'OTTAVA", Fornitori: 'FEDERICIANA RESTAURI DI MARIA LUISA DE TOMA - DTMMLS77R51L328Y' },
  { enabled: true, id: 6, CPA: "DD_3735/2019", CIG: 'Z512AA82BC', Oggetto: "AFFIDAMENTO INCARICO PROFESSIONALE PER DIREZIONE L", Fornitori: 'MPATA DELLA NAVATA SUD EX CHIESA DI SAN MARCO. PROSECUZIONE ATTIVITA GIA AVVIATE.	FONDAZIONE CENTRO PER LA CONSERVAZIONE ED IL RESTAURO DEI BENI CULTURALI LA VENARIA REALE - 97662370010' },
  { enabled: false, id: 7, CPA: "aaaaaaa", CIG: '1111111111', Oggetto: "aaaa", Fornitori: 'BESSO MARCHEIS ARCH. ANTONIO - BSSNTN59L21H340N' },
  { enabled: false, id: 8, CPA: "wersrerewrew", CIG: '1111111111', Oggetto: "xzffdx", Fornitori: 'ALLSYSTEM 1 SRL - 01933640029' },
  { enabled: true, id: 9, CPA: "yu6h65y56r", CIG: '5555555555', Oggetto: "hvjhgjg", Fornitori: 'AAA SRL - PBAN_96993000000*' }
];

@Component({
  selector: 'app-associa-affidamento-dialog',
  templateUrl: './associa-affidamento-dialog.component.html',
  styleUrls: ['./associa-affidamento-dialog.component.scss']
})
export class AssociaAffidamentoDialogComponent implements OnInit {

  idProgetto: number;
  idFornitoreDocSpesa: number;
  codiceRuolo: string;

  showErrorMessage = false;
  stateRadio = false;
  radioSelected: number;

  displayedColumns: string[] = ['checked', 'CPA', 'CIG', 'Oggetto', 'Fornitori'];
  elencoAffidamenti: Array<AffidamentoRendicontazioneDTO>;

  //LOADED
  loadedAffidamenti: boolean;

  constructor(
    public dialogRef: MatDialogRef<AssociaAffidamentoDialogComponent>,
    private documentoDiSpesaService: DocumentoDiSpesaService,
    public handleExceptionService: HandleExceptionService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.idProgetto = this.data.idProgetto;
    this.idFornitoreDocSpesa = this.data.idFornitoreDocSpesa;
    this.codiceRuolo = this.data.codiceRuolo;
    this.radioSelected = this.data.idAppaltoSelected;
    this.loadedAffidamenti = false;
    this.documentoDiSpesaService.getElencoAffidamenti(this.idProgetto, this.idFornitoreDocSpesa, this.codiceRuolo).subscribe(data => {
      this.elencoAffidamenti = data;
      this.loadedAffidamenti = true;
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  public clickRadio(event: Event, value: number) {
    event.preventDefault();
    this.radioSelected = value;
    this.stateRadio = true;
  }

  associa() {
    if (this.radioSelected == 0) {
      this.showErrorMessage = true;
    } else {
      var data: AffidamentoRendicontazioneDTO;
      for (var val of this.elencoAffidamenti) {
        if (val.idAppalto == this.radioSelected) {
          data = val;
        }
      }
      this.dialogRef.close({ data: data });
    }
  }

  close() {
    this.dialogRef.close();
  }

  isLoading() {
    if (!this.loadedAffidamenti) {
      return true;
    }
    return false;
  }
}
