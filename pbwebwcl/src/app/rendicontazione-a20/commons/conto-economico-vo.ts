/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface GetVociDiSpesa {
	esitoFindContoEconomicoDTO: EsitoFindContoEconomicoDTO;
	datiContoEconomico: DatiContoEconomico;
	codiceVisualizzatoProgetto: string;
	percSpGenFunz?: number;
	contributoConcesso?: number;
}

export interface DatiContoEconomico {
	hasCopiaPresente: boolean;
	dataPresentazioneDomanda: Date;
	dataFineIstruttoria: Date;
	nascondiColonnaImportoRichiesto: boolean;
	nascondiColonnaSpesaAmmessa: boolean;
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

export interface TabellaVociDiSpesa {
	dataTermineDomanda?: Date;
	dataTerminePreventivo?: Date;
	percentuale?: number;
	totali?: Totali;
	figli?: tipoSpese[];
	contributoConcesso?: number;
}

export interface Totali {
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

export interface VoceDiSpesaDaSalvare {
	spesePreventivate: Array<SpesaPreventivata>;
}

export interface SpesaPreventivata {
	idVoce?: number;
	idRigoContoEconomico?: number;
	importoSpesaPreventivata?: number;
	idContoEconomico?: number;
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

			speseConnesse.importoRichiestoInDomanda += figli.importoRichiestoInDomanda || 0;
			speseConnesse.importoRichiestoUltimaProposta += figli.importoRichiestoUltimaProposta || 0;
			speseConnesse.importoRichiestoNuovaProposta += figli.importoRichiestoNuovaProposta || 0;
			speseConnesse.importoSpesaAmmessaInDetermina += figli.importoSpesaAmmessaInDetermina || 0;
			speseConnesse.importoSpesaAmmessaUltima += figli.importoSpesaAmmessaUltima || 0;
			speseConnesse.importoSpesaAmmessaRimodulazione += figli.importoSpesaAmmessaRimodulazione || 0;
			speseConnesse.importoSpesaRendicontata += figli.importoSpesaRendicontata || 0;
			speseConnesse.importoSpesaQuietanziata += figli.importoSpesaQuietanziata || 0;
			speseConnesse.importoSpesaPreventivata += figli.importoSpesaPreventivata || 0;
		} else {
			speseGenerali.figli.push(figli);

			speseGenerali.importoRichiestoInDomanda += figli.importoRichiestoInDomanda || 0;
			speseGenerali.importoRichiestoUltimaProposta += figli.importoRichiestoUltimaProposta || 0;
			speseGenerali.importoRichiestoNuovaProposta += figli.importoRichiestoNuovaProposta || 0;
			speseGenerali.importoSpesaAmmessaInDetermina += figli.importoSpesaAmmessaInDetermina || 0;
			speseGenerali.importoSpesaAmmessaUltima += figli.importoSpesaAmmessaUltima || 0;
			speseGenerali.importoSpesaAmmessaRimodulazione += figli.importoSpesaAmmessaRimodulazione || 0;
			speseGenerali.importoSpesaRendicontata += figli.importoSpesaRendicontata || 0;
			speseGenerali.importoSpesaQuietanziata += figli.importoSpesaQuietanziata || 0;
			speseGenerali.importoSpesaPreventivata += figli.importoSpesaPreventivata || 0;
		}
	}

	tabellaVociDiSpesa.dataTermineDomanda = queryResult.datiContoEconomico.dataFineIstruttoria;
	tabellaVociDiSpesa.dataTerminePreventivo = queryResult.datiContoEconomico.dataPresentazioneDomanda;
	tabellaVociDiSpesa.percentuale = queryResult.percSpGenFunz;
	tabellaVociDiSpesa.contributoConcesso = queryResult.contributoConcesso;
	tabellaVociDiSpesa.totali = {
		domanda: speseConnesse.importoRichiestoInDomanda + speseGenerali.importoRichiestoInDomanda,
		preventivo: speseConnesse.importoSpesaAmmessaInDetermina + speseGenerali.importoSpesaAmmessaInDetermina,
		consuntivo: speseConnesse.importoSpesaRendicontata + speseGenerali.importoSpesaRendicontata,
		ammesso: speseConnesse.importoSpesaAmmessaRimodulazione + speseGenerali.importoSpesaAmmessaRimodulazione,
		preventivata: speseConnesse.importoSpesaPreventivata + speseGenerali.importoSpesaPreventivata,
	};
	tabellaVociDiSpesa.figli = [speseConnesse, speseGenerali];
	return tabellaVociDiSpesa;
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
				if(gialla.importoSpesaPreventivata){
					arancione.importoSpesaPreventivata += parseFloat(gialla.importoSpesaPreventivata.toString().replace(/[^0-9,]/g, '').replace(/,/g, '.'));
				}
			}
		}
		if(arancione.importoSpesaPreventivata){
			result.rossa[i].importoSpesaPreventivata += arancione.importoSpesaPreventivata;
		}
		result.totaleVerde += result.rossa[i].importoSpesaPreventivata;
	}

	return result;
}


