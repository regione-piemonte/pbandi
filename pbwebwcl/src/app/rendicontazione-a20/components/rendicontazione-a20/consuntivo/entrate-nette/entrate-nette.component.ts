/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { HandleExceptionService } from '@pbandi/common-lib';
import { VoceDiEntrataDaSalvare, VociDiEntrata, mapVociDiEntrata, ricalcolaTotali } from 'src/app/rendicontazione-a20/commons/entrate-vo';
import { SpesaService } from 'src/app/rendicontazione-a20/services/spesa.service';
import { AggiungiEntrataComponent } from './aggiungi-entrata/aggiungi-entrata.component';
import { SalvaModificheComponent } from './salva-modifiche/salva-modifiche.component';
import { CurrencyPipe } from '@angular/common';

@Component({
	selector: 'app-entrate-nette',
	templateUrl: './entrate-nette.component.html',
	styleUrls: ['./entrate-nette.component.scss'],
})
export class EntrateNetteComponent implements OnInit {
	@Input() idProgetto;
	@Input() isValidazioneA20;
	@Input() totali;
	@Input() isIntegrativa: boolean;
	@Input() entrateVo;
	@Input() dataTermineDomanda;
	@Input() dataTerminePreventivo;
	@Input() idBandoLinea;

	//ENTRATE
	@Output() entrateVoEmit = new EventEmitter<any>();
	@Output() updateDati = new EventEmitter<void>();
	percImportoAgevolato: any;

	aggiornaSaldo() {
		this.updateDati.emit();
		this.entrateVoEmit.emit(this.entrateVo.vociDiEntrataCultura);
	}

	public subscribers: any;
	primoLivQuota;
	infoSaldo;
	zero = 0;
	caricamentoEntrate: boolean = true;
	vociDiEntrataDaSalvare: VoceDiEntrataDaSalvare[] = [];
	entrateNetteSoloVisualizzabile: boolean;
	matInputVisibile: boolean;
	constructor(private dialog: MatDialog, private spesaService: SpesaService, private handleExceptionService: HandleExceptionService) {}

	ngOnInit() {
		this.isBottoneConsuntivoVisibile();
	}

	// getPercentualeImportoAgevolato(idBandoLinea: number) {
	// 	return this.spesaService.getPercentualeImportoAgevolato(this.entrateVo.vociDiEntrataCultura[0].idBando);
	// }

	inizializzaConsuntivoPresentato(entrateNetteSoloVisualizzabile: boolean){
		this.entrateVo.vociDiEntrataCultura.forEach(arancione => {
			this.controlloInputRigheTerzoLivelloImportoConsuntivoPresentato(arancione);
		});
		
		if(!entrateNetteSoloVisualizzabile || this.isIntegrativa){
			this.entrateVo.vociDiEntrataCultura.forEach(arancione => {
				if(arancione.flagEdit != 'S'){
					if(typeof arancione.importoConsuntivoPresentato === "string"){
						arancione.importoConsuntivoPresentato = arancione.importoConsuntivoPresentato.replace(/,/g, '.');
					}
					arancione.importoConsuntivoPresentato = (new CurrencyPipe('it')).transform(Number(arancione.importoConsuntivoPresentato), 'EUR' , 'symbol', '1.2' , 'it')
				}
				if(arancione.figli){
					arancione.figli.forEach(gialla => {
					if(typeof gialla.importoConsuntivoPresentato === "string"){
						gialla.importoConsuntivoPresentato = gialla.importoConsuntivoPresentato.replace(/,/g, '.');
					}
					gialla.importoConsuntivoPresentato = (new CurrencyPipe('it')).transform(Number(gialla.importoConsuntivoPresentato), 'EUR' , 'symbol', '1.2' , 'it')
					});
				}
			});
		}
	}

	controlloCaratteriInput(event){
		event.target.value = event.target.value.replace(/[^0-9,]/g, '');
	}

	formattedNumber(index, tipoVoceDiSpesa, idVoceDiEntrata, idConsuntivoEntrata){
		if(tipoVoceDiSpesa == 'A'){
			if(typeof this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato === "string"){
				this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato = 
				this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato.replace(/,/g, '.')
			}
			this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato = 
			(new CurrencyPipe('it')).transform(Number(this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato), 'EUR' , 'symbol', '1.2' , 'it');
		}else if(tipoVoceDiSpesa == 'G'){
			if(typeof this.entrateVo.vociDiEntrataCultura.filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].importoConsuntivoPresentato === "string"){
				this.entrateVo.vociDiEntrataCultura.
					filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.
					filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].
					importoConsuntivoPresentato = 
				this.entrateVo.vociDiEntrataCultura.
					filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.
					filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].
					importoConsuntivoPresentato.replace(/,/g, '.')
			}
			this.entrateVo.vociDiEntrataCultura.
			filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.
			filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].
			importoConsuntivoPresentato = (new CurrencyPipe('it')).
											transform(Number(
														this.entrateVo.vociDiEntrataCultura.
														filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.
														filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].
														importoConsuntivoPresentato),
														'EUR' , 'symbol', '1.2' , 'it');
		}
	}

	deformattaNumber(index, tipoVoceDiSpesa, idVoceDiEntrata, idConsuntivoEntrata){
		if(tipoVoceDiSpesa == 'A' && typeof this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato === 'string'){
			this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato = this.entrateVo.vociDiEntrataCultura[index].importoConsuntivoPresentato.replace(/[^0-9,]/g, '').replace(/,/g, '.').replace(/\./g, ',');
		}else if(tipoVoceDiSpesa == 'G' && typeof this.entrateVo.vociDiEntrataCultura.filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].importoConsuntivoPresentato === 'string'){
			this.entrateVo.vociDiEntrataCultura.
			filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.
			filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].
			importoConsuntivoPresentato = this.entrateVo.vociDiEntrataCultura.
												filter(voce => voce.idVoceDiEntrata === idVoceDiEntrata)[0].figli.
												filter(voce => voce.idConsuntivoEntrata === idConsuntivoEntrata)[0].
												importoConsuntivoPresentato.replace(/[^0-9,]/g, '').replace(/,/g, '.').replace(/\./g, ',');
		}
	}

	getEntrateRefresh() {
		this.spesaService.getEntrate(this.idProgetto).subscribe(data => {
			if (data) {
				this.entrateVo = data;
				this.entrateVo.vociDiEntrataCultura = ricalcolaTotali(mapVociDiEntrata(data.vociDiEntrataCultura)).vociDiEntrataCultura;
				this.aggiornaSaldo();
				this.totali = ricalcolaTotali(mapVociDiEntrata(data.vociDiEntrataCultura)).totali;
				this.inizializzaConsuntivoPresentato(this.entrateNetteSoloVisualizzabile);
				// this.getPercentualeImportoAgevolato(this.idBandoLinea).subscribe(
				// 	data => {
				// 		if (data) {
				// 			this.percImportoAgevolato = data;
				// 		}
				// 	},
				// 	err => {
				// 		console.error("Non trovo la percentuale dell'importo agevolato");
				// 	},
				// );
			}
		});
	}

	isBottoneConsuntivoVisibile() {
		this.spesaService.isBottoneConsuntivoVisibile(this.idProgetto).subscribe(data => {
			if (data != null) {
				this.entrateNetteSoloVisualizzabile = data;
				if(this.entrateNetteSoloVisualizzabile && !this.isIntegrativa){
					this.matInputVisibile = false;
				}else{
					this.matInputVisibile = true;
				}
				this.inizializzaConsuntivoPresentato(this.entrateNetteSoloVisualizzabile);
			}
		});
	}

	calcolaImporti() {
		const calcoli = ricalcolaTotali(this.entrateVo.vociDiEntrataCultura);
		this.entrateVo.vociDiEntrataCultura = calcoli.vociDiEntrataCultura;
		this.totali = calcoli.totali;
	}

	controlloInputRigheSecondoLivelloImportoConsuntivoPresentato(righeSecondoLivello: any) {
		if (righeSecondoLivello.importoConsuntivoPresentato < 0) {
			righeSecondoLivello.importoConsuntivoPresentato = 0;
		}
		this.calcolaImporti();
	}

	controlloInputRigheSecondoLivelloImportoValidato(righeSecondoLivello: any) {
		if (righeSecondoLivello.importoValidato < 0) {
			righeSecondoLivello.importoValidato = 0;
		}
		this.calcolaImporti();
	}

	controlloInputRigheTerzoLivelloImportoConsuntivoPresentato(righeTerzoLivello: any) {
		if (!righeTerzoLivello.importoConsuntivoPresentato || righeTerzoLivello.importoConsuntivoPresentato < 0) {
			righeTerzoLivello.importoConsuntivoPresentato = 0;
		}
		this.calcolaImporti();
	}

	controlloInputRigheTerzoLivelloImportoValidato(righeTerzoLivello: any) {
		if (righeTerzoLivello.importoValidato < 0) {
			righeTerzoLivello.importoValidato = 0;
		}
		this.calcolaImporti();
	}

	pressEye(righeSecondoLivello) {
		righeSecondoLivello.expanded = !righeSecondoLivello.expanded;
	}

	aggiungiEntrata(righeSecondoLivello): void {
		let dialogRef = this.dialog.open(AggiungiEntrataComponent, {
			width: '50em',
			data: {
				idProgetto: this.idProgetto,
				righeSecondoLivello: righeSecondoLivello,
				vociDiEntrataDaSalvare: this.vociDiEntrataDaSalvare,
			},
		});

		dialogRef.afterClosed().subscribe(result => {
			this.aggiornaSaldo();
		});
	}

	salvaModifiche() {
		this.spesaService.salvaVociDiEntrataCultura({ vociDiEntrataCultura: this.vociDiEntrataDaSalvare, idProgetto: this.idProgetto }).subscribe(
			response => {
				let dialogRef = this.dialog.open(SalvaModificheComponent, {
					width: '30%',
					data: {
						success: 'Voci di entrata aggiornate con successo',
						errore: false,
						isSpesa: false,
						isEntrata: true,
						vociDiEntrataDaSalvare: this.vociDiEntrataDaSalvare,
					},
				});
				dialogRef.afterClosed().subscribe(result => {
					this.aggiornaSaldo();
					this.getEntrateRefresh();
					this.vociDiEntrataDaSalvare = [];
				});
			},
			err => {
				let dialogRef = this.dialog.open(SalvaModificheComponent, {
					width: '30%',
					data: {
						success: 'Non è stato possibile aggiornare le voci di entrata',
						errore: true,
					},
				});
				dialogRef.afterClosed().subscribe(result => {
					this.aggiornaSaldo();
					this.getEntrateRefresh();
					this.vociDiEntrataDaSalvare = [];
				});
			},
		);
	}

	aggiungiRigaDaModificare(riga: VociDiEntrata) {
		let index;
		if (!riga) {
			console.error('riga non valida');
			return;
		}

		index = this.vociDiEntrataDaSalvare.findIndex(
			x =>
				x.idConsuntivoEntrata === riga.idConsuntivoEntrata &&
				x.idRigoContoEconomico === riga.idRigoContoEconomico &&
				x.idVoceDiEntrata === riga.idVoceDiEntrata &&
				x.completamento === riga.completamento &&
				x.flagEdit === riga.flagEdit &&
				x.idContoEconomico === riga.idContoEconomico,
		);

		if (index !== -1) {
			this.vociDiEntrataDaSalvare[index].importoAmmesso = riga.importoConsuntivoPresentato;
		} else {
			this.vociDiEntrataDaSalvare.push({
				idConsuntivoEntrata: riga.idConsuntivoEntrata,
				idRigoContoEconomico: riga.idRigoContoEconomico,
				importoAmmesso: parseFloat(riga.importoConsuntivoPresentato.toString().replace(/,/g, '.')),
				completamento: riga.completamento,
				flagEdit: riga.flagEdit,
				idContoEconomico: riga.idContoEconomico,
				idVoceDiEntrata: riga.idVoceDiEntrata,
			});
		}
		console.log(this.vociDiEntrataDaSalvare);
	}

	isLoading() {
		if (!this.caricamentoEntrate) {
			return true;
		}
		return false;
	}
}
