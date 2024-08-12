import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SpesaService } from 'src/app/rendicontazione-a20/services/spesa.service';

@Component({
	selector: 'app-aggiungi-entrata',
	templateUrl: './aggiungi-entrata.component.html',
	styleUrls: ['./aggiungi-entrata.component.scss'],
})
export class AggiungiEntrataComponent implements OnInit {
	errore;
	message;
	isLoading = false;
	public formEntrate: FormGroup;
	caricamento: boolean = false;
	constructor(public dialogRef: MatDialogRef<AggiungiEntrataComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder, private spesaService: SpesaService) {
		this.formEntrate = this.fb.group({
			idConsuntivoEntrata: this.data.idConsuntivoEntrata,
			idProgetto: this.data.idProgetto,
			idRigoContoEconomico: this.data.righeSecondoLivello.idRigoContoEconomico,
			idVoceDiEntrata: this.data.righeSecondoLivello.idVoceDiEntrata,
			dtInizioValidita: new Date(),
			completamento: new FormControl('', Validators.required),
			importo: new FormControl('', Validators.required),
		});
	}

	ngOnInit(): void {}

	onConfirmClick() {
		if (!this.formEntrate.valid) {
			return false;
		} else {
			this.caricamento = true;
			const datiScelti = this.formEntrate.value;

			this.data.vociDiEntrataDaSalvare.push({
				idRigoContoEconomico: 0,
				importoAmmesso: datiScelti.importo,
				completamento: datiScelti.completamento,
				flagEdit: null,
				idConsuntivoEntrata: this.data.idConsuntivoEntrata,
				idContoEconomico: this.data.righeSecondoLivello.idContoEconomico,
				idVoceDiEntrata: datiScelti.idVoceDiEntrata,
			});

			if (!this.data.righeSecondoLivello.figli) {
				this.data.righeSecondoLivello.figli = [];
			}
			if (!this.data.righeSecondoLivello.expanded) {
				this.data.righeSecondoLivello.expanded = true;
			}
			this.data.righeSecondoLivello.figli.push({
				idRigoContoEconomico: 0,
				importoConsuntivoPresentato: datiScelti.importo,
				completamento: datiScelti.completamento,
				flagEdit: null,
				idConsuntivoEntrata: this.data.idConsuntivoEntrata,
				idContoEconomico: this.data.righeSecondoLivello.idContoEconomico,
				idVoceDiEntrata: datiScelti.idVoceDiEntrata,
			});
			this.closeDialog();
		}
	}

	closeDialog() {
		this.dialogRef.close();
	}
}
