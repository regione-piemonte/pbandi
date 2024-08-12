export interface EntrateResponse {
	vociDiEntrataCultura: VociDiEntrata[];
	contoEconomico: DatiContoEconomico;
}
export interface VociDiEntrata {
	idBando: number;
	idContoEconomico: number;
	idConsuntivoEntrata?: number;
	idDomanda: number;
	idRigoContoEconomico?: number;
	idStatoContoEconomico?: number;
	idUtenteAgg?: number;
	idUtenteIns?: number;
	idVoceDiEntrata: number;
	descrizione: string;
	descrizioneBreve?: string;
	dtFineValidita?: string | null;
	dtInizioValidita?: string;
	flagEdit: 'S' | null;
	importoAmmesso?: number;
	importoFinanzBancaRichiesto?: number;
	importoFinanziamentoBanca?: number;
	importoImpegnoContabile?: number;
	importoImpegnoVincolante?: number;
	importoQuietanzato?: number;
	importoRendicontato?: number;
	importoRichiesto?: number;
	importoValidato?: number;
	noteContoEconomico?: string | null;
	percSpGenFunz?: string | null;
	riferimento?: string | null;
	contrattiDaStipulare?: string | null;
	completamento?: string | null;
	importoConsuntivoPresentato?: number;
	figli?: VociDiEntrata[];
	expanded?: boolean;
}
export interface DatiContoEconomico {
	hasCopiaPresente?: boolean | null;
	dataPresentazioneDomanda: string | null;
	dataFineIstruttoria: string | null;
	nascondiColonnaImportoRichiesto?: boolean | null;
	nascondiColonnaSpesaAmmessa?: boolean | null;
	dataUltimaProposta?: string | null;
	dataUltimaRimodulazione?: string | null;
}
export interface Totali {
	totaleRichiesto: number;
	totaleAmmesso: number;
	totaleFinanzBancaRichiesto: number;
	totaleFinanziamentoBanca: number;
	totaleImpegnoContabile: number;
	totaleImpegnoVincolante: number;
	totaleQuietanzato: number;
	totaleRendicontato: number;
	totaleValidato: number;
	totaleConsuntivoPresentato: number;
}

export interface VoceDiEntrataDaSalvare {
	idContoEconomico?: number;
	idRigoContoEconomico?: number;
	idVoceDiEntrata?: number;
	idConsuntivoEntrata: number;
	importoAmmesso: number;
	completamento?: string;
	flagEdit: 'S' | null;
}

export function mapVociDiEntrata(data: VociDiEntrata[]) {
	const tabella: VociDiEntrata[] = JSON.parse(JSON.stringify(data));
	tabella.sort((a, b) => {
		if (a.flagEdit === 'S' && b.flagEdit !== 'S') {
			return 1; // Move 's' to the bottom
		} else if (a.flagEdit !== 'S' && b.flagEdit === 'S') {
			return -1; // Move 's' to the bottom
		} else {
			return 0; // No change in order
		}
	});

	tabella.map(row => {
		row.expanded = true;
		if (row.idRigoContoEconomico && !row.idConsuntivoEntrata) {
			if (row.completamento) {
				//ci troviamo nel caso in cui la riga è stata inserita nel conto economico master
				const rowCopy = JSON.parse(JSON.stringify(row));
				const mainRow = tabella.findIndex(
					x => x.idVoceDiEntrata == row.idVoceDiEntrata && x.flagEdit == row.flagEdit && x.flagEdit && x.idRigoContoEconomico != row.idRigoContoEconomico && !x.completamento,
				);

				const figlio = tabella.findIndex(x => x.idRigoContoEconomico == row.idRigoContoEconomico && x.idConsuntivoEntrata);
				if (figlio > -1) {
					rowCopy.importoConsuntivoPresentato = tabella[figlio].importoConsuntivoPresentato;
					rowCopy.idConsuntivoEntrata = tabella[figlio].idConsuntivoEntrata;
					tabella.splice(figlio, 1);
				}
				if (mainRow > -1) {
					tabella[mainRow].figli.push(rowCopy);
					tabella.splice(
						tabella.findIndex(x => x.idRigoContoEconomico == row.idRigoContoEconomico),
						1,
					);
				} else {
					row.figli = [rowCopy];
					row.completamento = null;
					row.importoConsuntivoPresentato = 0;
				}
			} else {
				let fine = false;
				while (!fine) {
					const index = tabella.findIndex(x => x.idRigoContoEconomico === row.idRigoContoEconomico && x.idConsuntivoEntrata);
					if (index > -1) {
						if (row.completamento) {
							console.log(row);
							row.importoConsuntivoPresentato = tabella[index].importoConsuntivoPresentato;
							row.idConsuntivoEntrata = tabella[index].idConsuntivoEntrata;
							fine = true;
						} else if (!row.flagEdit) {
							row.importoConsuntivoPresentato = tabella[index].importoConsuntivoPresentato;
							row.idConsuntivoEntrata = tabella[index].idConsuntivoEntrata;
							fine = true;
						} else if (row.figli) {
							row.figli.push(tabella[index]);
						} else {
							row.figli = [tabella[index]];
						}
						tabella.splice(index, 1);
					} else fine = true;
				}
			}
		}
	});

	//gestisco le rimanenze
	tabella
		.filter(x => x.idConsuntivoEntrata && x.completamento && x.idRigoContoEconomico)
		.map(row => {
			const index = tabella.findIndex(x => x.idVoceDiEntrata === row.idVoceDiEntrata && !x.idConsuntivoEntrata);
			if (index > -1 && tabella[index].flagEdit) {
				if (tabella[index].figli) {
					tabella[index].figli.push(row);
				} else {
					tabella[index].figli = [row];
				}
				tabella.splice(tabella.findIndex(x => x.idConsuntivoEntrata === row.idConsuntivoEntrata), 1);
			}
		});

	//sort by name Descrizione
	return tabella.sort((a, b) => {
		if (a.descrizione < b.descrizione) {
			return -1;
		}
		if (a.descrizione > b.descrizione) {
			return 1;
		}
		return 0;
	});
}

export function ricalcolaTotali(vociDiEntrataCultura: VociDiEntrata[]) {
	let result: { vociDiEntrataCultura: VociDiEntrata[]; totali: Totali } = {
		vociDiEntrataCultura: vociDiEntrataCultura,
		totali: {
			totaleRichiesto: 0,
			totaleAmmesso: 0,
			totaleFinanzBancaRichiesto: 0,
			totaleFinanziamentoBanca: 0,
			totaleImpegnoContabile: 0,
			totaleImpegnoVincolante: 0,
			totaleQuietanzato: 0,
			totaleRendicontato: 0,
			totaleValidato: 0,
			totaleConsuntivoPresentato: 0,
		},
	};

	for (let i = 0; i < result.vociDiEntrataCultura.length; i++) {
		let riga = result.vociDiEntrataCultura[i];

		if (riga.figli && riga.figli.length > 0) {
			riga.importoAmmesso = 0;
			riga.importoFinanzBancaRichiesto = 0;
			riga.importoFinanziamentoBanca = 0;
			riga.importoImpegnoContabile = 0;
			riga.importoImpegnoVincolante = 0;
			riga.importoQuietanzato = 0;
			riga.importoRendicontato = 0;
			riga.importoRichiesto = 0;
			riga.importoValidato = 0;
			riga.importoConsuntivoPresentato = parseFloat(riga.importoConsuntivoPresentato.toString().replace(/[^0-9,]/g, '').replace(/,/g, '.')) ?? 0;
			riga.importoConsuntivoPresentato = 0;

			for (let j = 0; j < riga.figli.length; j++) {
				let figlio = riga.figli[j];
				riga.importoAmmesso += figlio.importoAmmesso ?? 0;
				riga.importoFinanzBancaRichiesto += figlio.importoFinanzBancaRichiesto ?? 0;
				riga.importoFinanziamentoBanca += figlio.importoFinanziamentoBanca ?? 0;
				riga.importoImpegnoContabile += figlio.importoImpegnoContabile ?? 0;
				riga.importoImpegnoVincolante += figlio.importoImpegnoVincolante ?? 0;
				riga.importoQuietanzato += figlio.importoQuietanzato ?? 0;
				riga.importoRendicontato += figlio.importoRendicontato ?? 0;
				riga.importoRichiesto += figlio.importoRichiesto ?? 0;
				riga.importoValidato += figlio.importoValidato ?? 0;
				riga.importoConsuntivoPresentato +=  parseFloat(figlio.importoConsuntivoPresentato.toString().replace(/[^0-9,]/g, '').replace(/,/g, '.')) ?? 0;
			}
		}

		result.totali.totaleAmmesso += riga.importoAmmesso;
		result.totali.totaleFinanzBancaRichiesto += riga.importoFinanzBancaRichiesto ?? 0;
		result.totali.totaleFinanziamentoBanca += riga.importoFinanziamentoBanca ?? 0;
		result.totali.totaleImpegnoContabile += riga.importoImpegnoContabile ?? 0;
		result.totali.totaleImpegnoVincolante += riga.importoImpegnoVincolante ?? 0;
		result.totali.totaleQuietanzato += riga.importoQuietanzato ?? 0;
		result.totali.totaleRendicontato += riga.importoRendicontato ?? 0;
		result.totali.totaleRichiesto += riga.importoRichiesto ?? 0;
		result.totali.totaleValidato += riga.importoValidato ?? 0;
		result.totali.totaleConsuntivoPresentato += parseFloat(riga.importoConsuntivoPresentato.toString().replace(/[^0-9,]/g, '').replace(/,/g, '.')) ?? 0;
	}

	return result;
}
