import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TabellaVociDiSpesa, VoceDiSpesaDaSalvare, mapToTabellaVociDiSpesa, ricalcolaTotaliSpese } from 'src/app/rendicontazione-a20/commons/conto-economico-vo';
import { SpesaService } from 'src/app/rendicontazione-a20/services/spesa.service';
import { AutoUnsubscribe } from 'src/app/shared/decorator/auto-unsubscribe';
import { SalvaModificheComponent } from '../entrate-nette/salva-modifiche/salva-modifiche.component';
import { MatDialog } from '@angular/material/dialog';
import { CurrencyPipe } from '@angular/common';
@Component({
	selector: 'app-spese',
	templateUrl: './spese.component.html',
	styleUrls: ['./spese.component.scss'],
})
@AutoUnsubscribe({ objectName: 'subscribers' })
export class SpeseComponent implements OnInit {
	@Input() idProgetto;
	@Input() isValidazioneA20;
	@Input() spesaVo: TabellaVociDiSpesa;
	@Input() isIntegrativa: boolean;
	@Input() dataTermineDomanda;
	@Input() dataTerminePreventivo;
	@Input() saldoSpeseConnesseAttivita: number;
	@Output() spesaPreventivata = new EventEmitter<any>();
	@Output() spesaVoEmit = new EventEmitter<any>();
	@Output() updateDati = new EventEmitter<void>();
	spesaSoloVisualizzabile: boolean;
	infoSaldo;
	primoLivQuota;
	public subscribers: any;
	caricamentoSpese: boolean = true;
	vociDiSpesaDaSalvare: VoceDiSpesaDaSalvare = {spesePreventivate: []};
	matInputVisibile: boolean;
	
	constructor(private dialog: MatDialog,
				private spesaService: SpesaService
				) {}

	ngOnInit(): void {
		this.calcolaImporti();
		this.inizializza()
	}
	
	inizializza(){
		this.caricamentoSpese = false;
		this.getSpesa(this.idProgetto).subscribe(data => {
			if (data) {
				this.spesaVo = mapToTabellaVociDiSpesa(data);
				this.isBottoneConsuntivoVisibile();
				this.caricamentoSpese = true;
			}
		});
	}

	getSpesa(idProgetto: any) {
		return this.spesaService.getSpesa(idProgetto);
	}

	pressEye(righeSecondoLivello) {
		righeSecondoLivello.expanded = !righeSecondoLivello.expanded;
	}

	isLoading() {
		if (!this.caricamentoSpese) {
			return true;
		}
		return false;
	}

	calcolaImporti() {
		const calcoli = ricalcolaTotaliSpese(this.spesaVo);
		this.spesaVo.totali.preventivata = calcoli.totaleVerde;
	}

	controlloSpesaPreventivata(riga: any){
		if(riga.importoSpesaAmmessaInDetermina  == null && riga.importoSpesaRendicontata == null){
			return false;
		}
		if(Math.abs(riga.importoSpesaAmmessaInDetermina  - riga.importoSpesaRendicontata) < riga.importoSpesaPreventivata){
			return true;
		}
		return false;
	}

	inizializzaSpesePreventivate(spesaSoloVisualizzazione: boolean){
		this.spesaVo.figli.forEach(arancione => {
			this.controlloInputRigheSecondoLivelloImportoSpesaPreventivata(arancione.figli[0]);
		});

		if(!spesaSoloVisualizzazione || this.isIntegrativa){
			this.spesaVo.figli.forEach(arancione => {
				arancione.figli[0].figli.forEach(gialla => {
						gialla.importoSpesaPreventivata = (new CurrencyPipe('it')).transform(Number(gialla.importoSpesaPreventivata), 'EUR' , 'symbol', '1.2' , 'it')
					});
			});
		}
	}

	controlloInputRigheSecondoLivelloImportoSpesaPreventivata(riga: any) {
		if (!riga.importoSpesaPreventivata || riga.importoSpesaPreventivata < 0) {
			riga.importoSpesaPreventivata = 0;
		}

		this.calcolaImporti();
	}

	controlloCaratteriInput(event){
		event.target.value = event.target.value.replace(/[^0-9,]/g, '');
	}

	aggiungiRigaDaModificare(riga: any) {
		let index;
		if (!riga) {
			console.error('riga non valida');
			return;
		}

		//PER IL MOMENTO SONO STATI RIMOSSI I CONTROLLI 13/11/2023 - VERIFICARE SE MANTENERE LA MODIFICA
		// if(((riga.importoSpesaAmmessaInDetermina  - riga.importoSpesaRendicontata) >= riga.importoSpesaPreventivata) || (riga.importoSpesaAmmessaInDetermina  == null && riga.importoSpesaRendicontata == null)){
		// 	index = this.vociDiSpesaDaSalvare.spesePreventivate.findIndex(x =>x.idVoce === riga.idVoce);

		// 	if (index !== -1) {
		// 			this.vociDiSpesaDaSalvare.spesePreventivate[index].importoSpesaPreventivata = riga.importoSpesaPreventivata;
		// 	}else{
		// 		this.vociDiSpesaDaSalvare.spesePreventivate.push({
		// 			idVoce:riga.idVoce,
		// 			idRigoContoEconomico: riga.idRigoContoEconomico,
		// 			importoSpesaPreventivata: riga.importoSpesaPreventivata,
		// 			idContoEconomico: riga.idContoEconomico
		// 		});
		// 	}
		// }

		index = this.vociDiSpesaDaSalvare.spesePreventivate.findIndex(x =>x.idVoce === riga.idVoce);

		if (index !== -1) {
				this.vociDiSpesaDaSalvare.spesePreventivate[index].importoSpesaPreventivata = riga.importoSpesaPreventivata;
		}else{
			this.vociDiSpesaDaSalvare.spesePreventivate.push({
				idVoce:riga.idVoce,
				idRigoContoEconomico: riga.idRigoContoEconomico,
				importoSpesaPreventivata: riga.importoSpesaPreventivata,
				idContoEconomico: riga.idContoEconomico
			});
		}
	}

	formattedNumber(index, idTipologiaVoceDiSpesa){
		this.spesaVo.figli.forEach(figlio => {
			if(figlio.figli[0].idTipologiaVoceDiSpesa == idTipologiaVoceDiSpesa){
				if(typeof figlio.figli[0].figli[index].importoSpesaPreventivata === "string"){
					figlio.figli[0].figli[index].importoSpesaPreventivata = figlio.figli[0].figli[index].importoSpesaPreventivata.replace(/,/g, '.');
				}
				figlio.figli[0].figli[index].importoSpesaPreventivata = (new CurrencyPipe('it')).transform(Number(figlio.figli[0].figli[index].importoSpesaPreventivata), 'EUR' , 'symbol', '1.2' , 'it');
			}
		});
	}

	deformattaNumber(index, idTipologiaVoceDiSpesa){
		this.spesaVo.figli.forEach(figlio => {
			if(figlio.figli[0].idTipologiaVoceDiSpesa == idTipologiaVoceDiSpesa){
				if(figlio.figli[0].figli[index].importoSpesaPreventivata && typeof figlio.figli[0].figli[index].importoSpesaPreventivata === 'string'){
					figlio.figli[0].figli[index].importoSpesaPreventivata = figlio.figli[0].figli[index].importoSpesaPreventivata.replace(/[^0-9,]/g, '').replace(/,/g, '.').replace(/\./g, ',');
				}
			}
		});
	}

	isBottoneConsuntivoVisibile(){
		this.spesaService.isBottoneConsuntivoVisibile(this.idProgetto).subscribe(data => {
			if (data != null) {
				this.spesaSoloVisualizzabile = data;
				if(this.spesaSoloVisualizzabile && !this.isIntegrativa){
					this.matInputVisibile = false;
				}else{
					this.matInputVisibile = true;
				}
				this.inizializzaSpesePreventivate(this.spesaSoloVisualizzabile);
			}
		});
	}

	aggiornaSaldo() {
		this.updateDati.emit();
		this.spesaVoEmit.emit(this.spesaVo.figli);
	}

	salvaModifiche() {
		this.spesaService.salvaSpesePreventivate({ spesePreventivate: this.vociDiSpesaDaSalvare.spesePreventivate}).subscribe(
			response => {
				let dialogRef = this.dialog.open(SalvaModificheComponent, {
					width: '30%',
					data: {
						success: 'Voci di spesa aggiornate con successo',
						errore : false,
						isSpesa: true,
						isEntrata: false,
						vociDiSpesaDaSalvare: this.vociDiSpesaDaSalvare
					},
				});
				dialogRef.afterClosed().subscribe(result => {
					this.aggiornaSaldo();
					this.vociDiSpesaDaSalvare.spesePreventivate = [];
				});
			},
			err => {
				let dialogRef = this.dialog.open(SalvaModificheComponent, {
					width: '30%',
					data: {
						success: 'Non è stato possibile aggiornare le voci di spesa',
						errore : true 
					},
				});
				dialogRef.afterClosed().subscribe(result => {
					this.aggiornaSaldo();
					this.vociDiSpesaDaSalvare.spesePreventivate = [];
				});
			},
		);
	}
}
