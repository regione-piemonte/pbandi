/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { NumberSymbol } from "@angular/common";

export interface EntrateResponse {
	vociDiEntrataCultura: VociDiEntrata[];
	contoEconomico: DatiContoEconomico;
}

export interface TabellaVociDiSpesa {
	dataTermineDomanda?: string;
	dataTerminePreventivo?: string;
	percentuale?: number;
	totali?: TotaliSpese;
	figli?: tipoSpese[];
	contributoConcesso?: number;
}

export interface TotaliSpese {
	domanda?: number;
	preventivo?: number;
	consuntivo?: number;
	ammesso?: number;
	preventivata?: number;
}

export interface tipoSpese {
	figli: Array<any>;
	idTipologiaVoceDiSpesa: number;
	descTipologiaVoceDiSpesa: string;
	percQuotaContributo: number;
	importoRichiestoInDomanda: number;
	importoRichiestoUltimaProposta: number;
	importoRichiestoNuovaProposta: number;
	importoSpesaAmmessaInDetermina: number;
	importoSpesaAmmessaUltima: number;
	importoSpesaAmmessaRimodulazione: number;
	importoSpesaRendicontata: number;
	importoSpesaQuietanziata: number;
	importoSpesaPreventivata: number;
}

export interface VoceDiEntrataDaSalvareEntrate {
	entrateAmmesseInRimodulazione: Array<EntrateAmmesseInRimodulazione>;
}

export interface EntrateAmmesseInRimodulazione {
	idVoceDiEntrata?: number;
	idRigoContoEconomico?: number;
	importoEntrateAmmesseInRimodulazione?: number; 
	idContoEconomico?: number;
}
export interface VociDiEntrata {
	idBando?: number;
	idContoEconomico?: number;
	idConsuntivoEntrata?: number;
	idDomanda?: number;
	idRigoContoEconomico?: number;
	idStatoContoEconomico?: number;
	idUtenteAgg?: number;
	idUtenteIns?: number;
	idVoceDiEntrata?: number;
	descrizione?: string;
	descrizioneBreve?: string;
	dtFineValidita?: string | null;
	dtInizioValidita?: string;
	dataPresentazioneDomanda?: string;
	dataUltimaProposta?: string;
	dataFineIstruttoria?: string;
	flagEdit?: 'S' | null;
	importoAmmesso?: number;
	importoUltimoAmmesso?: number;
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
	importoEntrateAmmesseInRimodulazione?: number,
	totali?: Totali; 
	rigaRipristina?: boolean;
	differenzaRimodulazione?: number;
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
	totaleRichiesto?: number;
	totaleAmmesso?: number;
	totaleFinanzBancaRichiesto?: number;
	totaleFinanziamentoBanca?: number;
	totaleImpegnoContabile?: number;
	totaleImpegnoVincolante?: number;
	totaleQuietanzato?: number;
	totaleRendicontato?: number;
	totaleValidato?: number;
	totaleConsuntivoPresentato?: number;
	//TOTALI RIMODULAZIONE
	totaleInDomanda?: number;
	totaleAmmesseInIstruttoria?: number;
	totaleUltimeEntrateAmmesse?: number;
	totaleEntrateAmmesseInRimodulazione?: number;
	totaleEntrateRendicontate?: number;
	totaleEntrateValidate?: number;
	totalePercentualeVA?: number;

}

export interface GetVociDiSpesa {
	esitoFindContoEconomicoDTO: EsitoFindContoEconomicoDTO;
	datiContoEconomico: DatiContoEconomico;
	codiceVisualizzatoProgetto: string;
	percSpGenFunz?: number;
	contributoConcesso?: number;
}

export interface EsitoFindContoEconomicoDTO {
	locked: boolean;
	modificabile: boolean;
	copiaModificataPresente: boolean;
	contoEconomico: ContoEconomico;
	rimodulazionePresente: boolean;
	propostaPresente: boolean;
	dataPresentazioneDomanda: Date;
	dataFineIstruttoria: Date;
	inIstruttoria: boolean;
	inStatoRichiesto: boolean;
	isContoMainNew: boolean;
}

export interface ContoEconomico {
	importoRichiestoInDomanda: number;
	importoRichiestoUltimaProposta: number;
	importoSpesaAmmessaUltima: number;
	importoSpesaRendicontata: number;
	importoSpesaQuietanziata: number;
	importoSpesaPreventivata: number;
	percSpesaQuietanziataSuAmmessa: number;
	importoSpesaValidataTotale: number;
	percSpesaValidataSuAmmessa: number;
	idContoEconomico: number;
	figli: ContoEconomicoFigli[];
	voceAssociataARigo: boolean;
	flagRibassoAsta: string;
}

export interface ContoEconomicoFigli {
	importoRichiestoInDomanda: number;
	importoSpesaAmmessaRimodulazione: number;
	importoSpesaAmmessaInDetermina: number;
	importoRichiestoNuovaProposta: number;
	importoRichiestoUltimaProposta: number;
	importoSpesaAmmessaUltima: number;
	importoSpesaRendicontata: number;
	importoSpesaQuietanziata: number;
	importoSpesaPreventivata: number;
	percSpesaQuietanziataSuAmmessa: number;
	importoSpesaValidataTotale: number;
	percSpesaValidataSuAmmessa: number;
	label: string;
	idContoEconomico: number;
	idVoce: number;
	figli: ContoEconomicoFigliFigli[];
	voceAssociataARigo: boolean;
	idTipologiaVoceDiSpesa: number;
	descTipologiaVoceDiSpesa: string;
	percQuotaContributo: number;
}

export interface ContoEconomicoFigliFigli {
	importoSpesaAmmessaRimodulazione: number;
	importoRichiestoInDomanda?: number;
	importoRichiestoUltimaProposta?: number;
	importoSpesaRendicontata?: number;
	importoSpesaAmmessaUltima?: number;
	importoSpesaPreventivata: number;
	label: string;
	idContoEconomico: number;
	idRigoContoEconomico?: number;
	idVoce: number;
	voceAssociataARigo: boolean;
	idTipologiaVoceDiSpesa: number;
	descTipologiaVoceDiSpesa: string;
	percQuotaContributo: number;
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

export function mapToTabellaVociDiSpesa(queryResult: GetVociDiSpesa) {
	let tabellaVociDiSpesa: TabellaVociDiSpesa = {};

	let speseGenerali: tipoSpese = {
		figli: [],
		importoRichiestoInDomanda: 0,
		importoRichiestoUltimaProposta: 0,
		importoRichiestoNuovaProposta: 0,
		importoSpesaAmmessaInDetermina: 0,
		importoSpesaAmmessaUltima: 0,
		importoSpesaAmmessaRimodulazione: 0,
		importoSpesaRendicontata: 0,
		importoSpesaQuietanziata: 0,
		importoSpesaPreventivata: 0,
		idTipologiaVoceDiSpesa: 1,
		descTipologiaVoceDiSpesa: 'Spese generali e di funzionamento',
		percQuotaContributo: 20,
	};

	let speseConnesse: tipoSpese = {
		figli: [],
		importoRichiestoInDomanda: 0,
		importoRichiestoUltimaProposta: 0,
		importoRichiestoNuovaProposta: 0,
		importoSpesaAmmessaInDetermina: 0,
		importoSpesaAmmessaUltima: 0,
		importoSpesaAmmessaRimodulazione: 0,
		importoSpesaRendicontata: 0,
		importoSpesaQuietanziata: 0,
		importoSpesaPreventivata: 0,
		idTipologiaVoceDiSpesa: 2,
		descTipologiaVoceDiSpesa: 'Spese connesse alle attività',
		percQuotaContributo: 100,
	};

	for (const figli of queryResult.esitoFindContoEconomicoDTO.contoEconomico.figli) {
		if (figli.idTipologiaVoceDiSpesa === 1) {
			speseConnesse.figli.push(figli);

			speseConnesse.importoRichiestoInDomanda += figli.importoRichiestoInDomanda ?? 0;
			speseConnesse.importoRichiestoUltimaProposta += figli.importoRichiestoUltimaProposta ?? 0;
			speseConnesse.importoRichiestoNuovaProposta += figli.importoRichiestoNuovaProposta ?? 0;
			speseConnesse.importoSpesaAmmessaInDetermina += figli.importoSpesaAmmessaInDetermina ?? 0;
			speseConnesse.importoSpesaAmmessaUltima += figli.importoSpesaAmmessaUltima ?? 0;
			speseConnesse.importoSpesaAmmessaRimodulazione += figli.importoSpesaAmmessaRimodulazione ?? 0;
			speseConnesse.importoSpesaRendicontata += figli.importoSpesaRendicontata ?? 0;
			speseConnesse.importoSpesaQuietanziata += figli.importoSpesaQuietanziata ?? 0;
			speseConnesse.importoSpesaPreventivata += figli.importoSpesaPreventivata ?? 0;
		} else {
			speseGenerali.figli.push(figli);

			speseGenerali.importoRichiestoInDomanda += figli.importoRichiestoInDomanda ?? 0;
			speseGenerali.importoRichiestoUltimaProposta += figli.importoRichiestoUltimaProposta ?? 0;
			speseGenerali.importoRichiestoNuovaProposta += figli.importoRichiestoNuovaProposta ?? 0;
			speseGenerali.importoSpesaAmmessaInDetermina += figli.importoSpesaAmmessaInDetermina ?? 0;
			speseGenerali.importoSpesaAmmessaUltima += figli.importoSpesaAmmessaUltima ?? 0;
			speseGenerali.importoSpesaAmmessaRimodulazione += figli.importoSpesaAmmessaRimodulazione ?? 0;
			speseGenerali.importoSpesaRendicontata += figli.importoSpesaRendicontata ?? 0;
			speseGenerali.importoSpesaQuietanziata += figli.importoSpesaQuietanziata ?? 0;
			speseGenerali.importoSpesaPreventivata += figli.importoSpesaPreventivata ?? 0;
		}
	}

	tabellaVociDiSpesa.dataTermineDomanda = queryResult.datiContoEconomico.dataFineIstruttoria;
	tabellaVociDiSpesa.dataTerminePreventivo = queryResult.datiContoEconomico.dataPresentazioneDomanda;
	tabellaVociDiSpesa.percentuale = queryResult.percSpGenFunz || 0;
	tabellaVociDiSpesa.contributoConcesso = queryResult.contributoConcesso || 0;
	tabellaVociDiSpesa.totali = {
		domanda: speseConnesse.importoRichiestoInDomanda + speseGenerali.importoRichiestoInDomanda,
		preventivo: speseConnesse.importoSpesaAmmessaInDetermina + speseGenerali.importoSpesaAmmessaInDetermina,
		consuntivo: speseConnesse.importoSpesaRendicontata + speseGenerali.importoSpesaRendicontata,
		ammesso: speseConnesse.importoSpesaAmmessaRimodulazione + speseGenerali.importoSpesaAmmessaRimodulazione,
		preventivata: speseConnesse.importoSpesaPreventivata + speseGenerali.importoSpesaPreventivata,
	};
	tabellaVociDiSpesa.figli = [speseConnesse, speseGenerali];
	let totaleSpeseEffettive: number = tabellaVociDiSpesa.figli[0].importoSpesaRendicontata + tabellaVociDiSpesa.figli[0].importoSpesaPreventivata + tabellaVociDiSpesa.figli[1].importoSpesaPreventivata + ((tabellaVociDiSpesa.figli[1]?.importoSpesaRendicontata * tabellaVociDiSpesa.percentuale) / 100);
	let resultMap: {tabellaVociDiSpesa: TabellaVociDiSpesa, totaleSpeseEffettive: number} = {
		tabellaVociDiSpesa : tabellaVociDiSpesa,
		totaleSpeseEffettive : totaleSpeseEffettive
	}
	return resultMap;
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
			totaleEntrateAmmesseInRimodulazione: 0,
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
			riga.importoConsuntivoPresentato = 0;
			riga.importoEntrateAmmesseInRimodulazione = 0;

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
				riga.importoConsuntivoPresentato += figlio.importoConsuntivoPresentato ?? 0;
				riga.importoEntrateAmmesseInRimodulazione += figlio.importoEntrateAmmesseInRimodulazione ?? 0;
			}
		}

		result.totali.totaleAmmesso += riga.importoAmmesso ?? 0;
		result.totali.totaleFinanzBancaRichiesto += riga.importoFinanzBancaRichiesto ?? 0;
		result.totali.totaleFinanziamentoBanca += riga.importoFinanziamentoBanca ?? 0;
		result.totali.totaleImpegnoContabile += riga.importoImpegnoContabile ?? 0;
		result.totali.totaleImpegnoVincolante += riga.importoImpegnoVincolante ?? 0;
		result.totali.totaleQuietanzato += riga.importoQuietanzato ?? 0;
		result.totali.totaleRendicontato += riga.importoRendicontato ?? 0;
		result.totali.totaleRichiesto += riga.importoRichiesto ?? 0;
		result.totali.totaleValidato += riga.importoValidato ?? 0;
		result.totali.totaleConsuntivoPresentato += riga.importoConsuntivoPresentato ?? 0;
		result.totali.totaleEntrateAmmesseInRimodulazione += riga.importoEntrateAmmesseInRimodulazione ?? 0;
	}

	return result;
}

export function ricalcolaTotaliSpese(spesaVo: TabellaVociDiSpesa) {
	let result: { rossa: tipoSpese[]; totaleVerde } = {
		rossa: spesaVo.figli,
		totaleVerde: 0,
	};

	for (let i = 0; i < result.rossa.length; i++) {
		result.rossa[i].importoSpesaPreventivata = 0;
		let arancione = result.rossa[i].figli[0];
		if (arancione.figli && arancione.figli.length > 0) {
			arancione.importoSpesaPreventivata = 0;
			for (let j = 0; j < arancione.figli.length; j++) {
				let gialla = arancione.figli[j];
				arancione.importoSpesaPreventivata += gialla.importoSpesaPreventivata ?? 0;
			}
		}
		result.rossa[i].importoSpesaPreventivata += arancione.importoSpesaPreventivata ?? 0;
		result.totaleVerde += result.rossa[i].importoSpesaPreventivata ?? 0;
	}

	return result;
}
