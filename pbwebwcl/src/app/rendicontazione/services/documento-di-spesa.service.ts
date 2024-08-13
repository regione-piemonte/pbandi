/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { EsitoOperazioneDTO } from 'src/app/shared/commons/dto/esito-operazione-dto';
import { AffidamentoRendicontazioneDTO } from '../commons/dto/affidamento-rendicontazione-dto';
import { DecodificaDTO } from '../commons/dto/decodifica-dto';
import { DocumentoAllegatoDTO } from '../commons/dto/documento-allegato-dto';
import { DocumentoDiPagamentoDTO } from '../commons/dto/documento-di-pagamento-dto';
import { DocumentoDiSpesaDTO } from '../commons/dto/documento-di-spesa-dto';
import { EsitoAssociaFilesDTO } from '../commons/dto/esito-associa-files-dto';
import { EsitoLetturaXmlFattElett } from '../commons/dto/esito-lettura-xml-fatt-elett';
import { EsitoRicercaDocumentiDiSpesa } from '../commons/dto/esito-ricerca-documenti-di-spesa';
import { FiltroRicercaRendicontazionePartners } from '../commons/dto/filtro-ricerca-rendicontazione-partners';
import { FormAssociaDocSpesa } from '../commons/dto/form-associa-doc-spesa';
import { VoceDiSpesaPadre } from '../commons/dto/voce-di-spesa';
import { VoceDiSpesaDTO } from '../commons/dto/voce-di-spesa-dto';
import { AssociaFilesRequest } from '../commons/requests/associa-files-request';
import { AssociaPagamentoRequest } from '../commons/requests/associa-pagamento-request';
import { FiltroRicercaDocumentiSpesa } from '../commons/requests/filtro-ricerca-documenti-spesa';
import { PagamentiAssociatiRequest } from '../commons/requests/pagamenti-associati-request';
import { ParametroCompensiDTO } from '../commons/dto/parametro-compensi-dto';
import { EsitoImportoSaldoDTO } from '../commons/dto/esito-importo-dto';
import { QuietanzeAssociateDTO } from '../commons/dto/quietanze-associate';

@Injectable()
export class DocumentoDiSpesaService {
	constructor(private configService: ConfigService, private http: HttpClient) { }

	messageSuccess: string;
	messageError: string;

	getElencoAffidamenti(idProgetto: number, idFornitoreDocSpesa: number, codiceRuolo: string) {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/elencoAffidamenti';
		let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('codiceRuolo', codiceRuolo);
		if (idFornitoreDocSpesa) {
			params = params.set('idFornitoreDocSpesa', idFornitoreDocSpesa.toString());
		}
		return this.http.get<Array<AffidamentoRendicontazioneDTO>>(url, { params: params });
	}

	getAllegatiFornitore(idFornitore: number, idProgetto: number, idUtente: number): Observable<Array<DocumentoAllegatoDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/allegatiFornitore';
		let params = new HttpParams().set('idFornitore', idFornitore.toString()).set('idProgetto', idProgetto.toString()).set('idUtente', idUtente.toString());
		return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
	}

	disassociaAllegatoDocumentoDiSpesa(idDocumentoIndex: number, idDocumentoDiSpesa: number, idProgetto: number, idUtente: number): Observable<EsitoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/disassociaAllegatoDocumentoDiSpesa';
		let params = new HttpParams()
			.set('idDocumentoIndex', idDocumentoIndex.toString())
			.set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString())
			.set('idProgetto', idProgetto.toString())
			.set('idUtente', idUtente.toString());
		return this.http.get<EsitoDTO>(url, { params: params });
	}

	salvaDocumentoDiSpesa(documentoDiSpesaDTO: DocumentoDiSpesaDTO, progrBandoLinea: number, idUtente: number, tipoBeneficiario?: number): Observable<EsitoDTO> {
		if (tipoBeneficiario == 2) {
			let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/salvaDocumentoDiSpesaCultura';
			let params = new HttpParams().set('idUtente', idUtente.toString()).set('progrBandoLinea', progrBandoLinea.toString()).set('tipoBeneficiario', tipoBeneficiario.toString());
			return this.http.post<EsitoDTO>(url, documentoDiSpesaDTO, { params: params });
		} else {
			let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/salvaDocumentoDiSpesa';
			let params = new HttpParams().set('idUtente', idUtente.toString()).set('progrBandoLinea', progrBandoLinea.toString());
			return this.http.post<EsitoDTO>(url, documentoDiSpesaDTO, { params: params });
		}
	}

	salvaDocumentoDiSpesaValidazione(documentoDiSpesaDTO: DocumentoDiSpesaDTO, idUtente: number): Observable<EsitoOperazioneDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/salvaDocumentoDiSpesaValidazione';
		let params = new HttpParams().set('idUtente', idUtente.toString());
		return this.http.post<EsitoOperazioneDTO>(url, documentoDiSpesaDTO, { params: params });
	}

	getMacroVociDiSpesa(idDocumentoDiSpesa: number, idProgetto: number, idUtente: number): Observable<Array<VoceDiSpesaPadre>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/macroVociDiSpesa';
		let params = new HttpParams().set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString()).set('idProgetto', idProgetto.toString()).set('idUtente', idUtente.toString());
		return this.http.get<Array<VoceDiSpesaPadre>>(url, { params: params });
	}

	getVociDiSpesaAssociateRendicontazione(
		idDocumentoDiSpesa: number,
		idProgetto: number,
		idSoggetto: number,
		codiceRuolo: string,
		tipoOperazioneDocSpesa: string,
		descBreveStatoDocSpesa: string,
		idUtente: number,
	): Observable<Array<VoceDiSpesaDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/vociDiSpesaAssociateRendicontazione';
		let params = new HttpParams()
			.set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString())
			.set('idProgetto', idProgetto.toString())
			.set('idSoggetto', idSoggetto.toString())
			.set('codiceRuolo', codiceRuolo)
			.set('tipoOperazioneDocSpesa', tipoOperazioneDocSpesa)
			.set('descBreveStatoDocSpesa', descBreveStatoDocSpesa)
			.set('idUtente', idUtente.toString());
		return this.http.get<Array<VoceDiSpesaDTO>>(url, { params: params });
	}

	getVociDiSpesaAssociateValidazione(
		idDocumentoDiSpesa: number,
		idProgetto: number,
		idSoggetto: number,
		codiceRuolo: string,
		tipoOperazioneDocSpesa: string,
		descBreveStatoDocSpesa: string,
		idUtente: number,
	): Observable<Array<VoceDiSpesaDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/vociDiSpesaAssociateValidazione';
		let params = new HttpParams()
			.set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString())
			.set('idProgetto', idProgetto.toString())
			.set('idSoggetto', idSoggetto.toString())
			.set('codiceRuolo', codiceRuolo)
			.set('tipoOperazioneDocSpesa', tipoOperazioneDocSpesa)
			.set('descBreveStatoDocSpesa', descBreveStatoDocSpesa)
			.set('idUtente', idUtente.toString());
		return this.http.get<Array<VoceDiSpesaDTO>>(url, { params: params });
	}

	associaAllegatiADocSpesa(request: AssociaFilesRequest): Observable<EsitoAssociaFilesDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/associaAllegatiADocSpesa';
		return this.http.post<EsitoAssociaFilesDTO>(url, request);
	}

	getAllegatiDocumentoDiSpesa(idDocumentoDiSpesa: number, idProgetto: number, flagDocElettronico: string, idUtente: number): Observable<Array<DocumentoAllegatoDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/allegatiDocumentoDiSpesa';
		let params = new HttpParams()
			.set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString())
			.set('idProgetto', idProgetto.toString())
			.set('flagDocElettronico', flagDocElettronico)
			.set('idUtente', idUtente.toString());
		return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
	}

	getModalitaQuietanza(idDocumentoDiSpesa: number, idProgetto: number, idUtente: number): Observable<Array<DecodificaDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/modalitaPagamento';
		let params = new HttpParams().set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString()).set('idProgetto', idProgetto.toString()).set('idUtente', idUtente.toString());
		return this.http.get<Array<DecodificaDTO>>(url, { params: params });
	}

	getCausaliQuietanza(): Observable<Array<DecodificaDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/causaliPagamento';
		return this.http.get<Array<DecodificaDTO>>(url);
	}

	getVociDiSpesaRicerca(idProgetto: number): Observable<Array<VoceDiSpesaPadre>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/vociDiSpesaRicerca';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<Array<VoceDiSpesaPadre>>(url, { params: params });
	}

	getPartners(idProgetto: number, idBandoLinea: number): Observable<FiltroRicercaRendicontazionePartners> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/partners';
		let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idBandoLinea', idBandoLinea.toString());
		return this.http.get<FiltroRicercaRendicontazionePartners>(url, { params: params });
	}

	ricercaDocumentiDiSpesa(
		idProgetto: number,
		codiceProgettoCorrente: string,
		idSoggettoBeneficiario: number,
		codiceRuolo: string,
		filtro: FiltroRicercaDocumentiSpesa,
	): Observable<EsitoRicercaDocumentiDiSpesa> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/ricercaDocumentiDiSpesa';
		let params = new HttpParams()
			.set('idProgetto', idProgetto.toString())
			.set('codiceProgettoCorrente', codiceProgettoCorrente)
			.set('idSoggettoBeneficiario', idSoggettoBeneficiario.toString())
			.set('codiceRuolo', codiceRuolo);
		return this.http.post<EsitoRicercaDocumentiDiSpesa>(url, filtro, { params: params });
	}

	getDocumentoDiSpesa(idProgetto: number, idDocumentoDiSpesa: number): Observable<DocumentoDiSpesaDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/documentoDiSpesa';
		let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString());
		return this.http.get<DocumentoDiSpesaDTO>(url, { params: params });
	}

	eliminaDocumentoDiSpesa(idProgetto: number, idDocumentoDiSpesa: number): Observable<EsitoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/eliminaDocumentoDiSpesa';
		let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString());
		return this.http.get<EsitoDTO>(url, { params: params });
	}

	associaVoceDiSpesa(voceDiSpesaDTO: VoceDiSpesaDTO, idUtente: number): Observable<EsitoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/associaVoceDiSpesa';
		let params = new HttpParams().set('idUtente', idUtente.toString());
		return this.http.post<EsitoDTO>(url, voceDiSpesaDTO, { params: params });
	}

	disassociaVoceDiSpesa(idQuotaParteDocSpesa: number, idUtente: number): Observable<EsitoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/disassociaVoceDiSpesa';
		let params = new HttpParams().set('idQuotaParteDocSpesa', idQuotaParteDocSpesa.toString()).set('idUtente', idUtente.toString());
		return this.http.get<EsitoDTO>(url, { params: params });
	}

	associaQuietanza(request: AssociaPagamentoRequest): Observable<EsitoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/associaPagamento';
		return this.http.post<EsitoDTO>(url, request);
	}

	disassociaQuietanza(idPagamento: number): Observable<EsitoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/disassociaPagamento';
		let params = new HttpParams().set('idPagamento', idPagamento.toString());
		return this.http.get<EsitoDTO>(url, { params: params });
	}

	getQuietanzeAssociate(request: PagamentiAssociatiRequest): Observable<Array<DocumentoDiPagamentoDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/pagamentiAssociati';
		return this.http.post<Array<DocumentoDiPagamentoDTO>>(url, request);
	}

	getGgScadenzaQuietanza(idBandoLinea: number) {let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/getGiorniMassimiQuietanzaPerBando';
	let params = new HttpParams().set('idBandoLinea', idBandoLinea.toString());
	return this.http.get<number>(url, { params: params });
	}

	associaAllegatiAQuietanza(request: AssociaFilesRequest): Observable<EsitoAssociaFilesDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/associaAllegatiAPagamento';
		return this.http.post<EsitoAssociaFilesDTO>(url, request);
	}

	disassociaAllegatoQuietanza(idDocumentoIndex: number, idPagamento: number, idProgetto: number): Observable<EsitoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/disassociaAllegatoPagamento';
		let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString()).set('idPagamento', idPagamento.toString()).set('idProgetto', idProgetto.toString());
		return this.http.get<EsitoDTO>(url, { params: params });
	}

	getNoteDiCreditoFattura(idProgetto: number, idDocumentoDiSpesa: number): Observable<Array<DocumentoDiSpesaDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/noteDiCreditoFattura';
		let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString());
		return this.http.get<Array<DocumentoDiSpesaDTO>>(url, { params: params });
	}

	popolaFormAssociaDocSpesa(idDocumentoDiSpesa: number): Observable<FormAssociaDocSpesa> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/popolaFormAssociaDocSpesa';
		let params = new HttpParams().set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString());
		return this.http.get<FormAssociaDocSpesa>(url, { params: params });
	}

	associaDocumentoDiSpesaAProgetto(idDocumentoDiSpesa: number, idProgetto: number, idProgettoDocumento: number, task: string, importoRendicontabile: number): Observable<EsitoOperazioneDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/associaDocumentoDiSpesaAProgetto';
		let params = new HttpParams()
			.set('idDocumentoDiSpesa', idDocumentoDiSpesa.toString())
			.set('idProgetto', idProgetto.toString())
			.set('idProgettoDocumento', idProgettoDocumento.toString())
			.set('task', task)
			.set('importoRendicontabile', importoRendicontabile.toString());
		return this.http.get<EsitoOperazioneDTO>(url, { params: params });
	}

	esitoLetturaXmlFattElett(idDocumentoIndex: number, idSoggettoBeneficiario: number, idTipoOperazioneProgetto: number): Observable<EsitoLetturaXmlFattElett> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/esitoLetturaXmlFattElett';
		let params = new HttpParams()
			.set('idDocumentoIndex', idDocumentoIndex.toString())
			.set('idSoggettoBeneficiario', idSoggettoBeneficiario.toString())
			.set('idTipoOperazioneProgetto', idTipoOperazioneProgetto.toString());
		return this.http.get<EsitoLetturaXmlFattElett>(url, { params: params });
	}

	getTipoBeneficiario(idSoggetto: number, idProgetto: number): Observable<number> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/tipoBeneficiario';
		let params = new HttpParams().set('idSoggetto', idSoggetto.toString()).set('idProgetto', idProgetto.toString());
		return this.http.get<number>(url, { params: params });
	}

	getQuotaImportoAgevolato(idProgetto: number): Observable<number> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/quotaImportoAgevolato';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<number>(url, { params: params });
	}

	getCategorieVociDiSpesa(): any {
		let url = this.configService.getApiURL() + '/restfacade/decodificheCultura/categorie';
		return this.http.get<any>(url, {});
	}

	getDocumentazioneDaAllegare(progrBandoLineaIntervento: number, idTipoDocumentoSpesa: number): Observable<string> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/documentazioneDaAllegare';
		let params = new HttpParams()
			.set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString())
			.set("idTipoDocumentoSpesa", idTipoDocumentoSpesa.toString());
		return this.http.get(url, { params: params, responseType: 'text' });
	}

	getParametriCompensi(): Observable<Array<ParametroCompensiDTO>> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/parametriCompensi';
		return this.http.get<Array<ParametroCompensiDTO>>(url);
	}


	getImportoASaldo(idProgetto: number): Observable<EsitoImportoSaldoDTO> {
		let url = this.configService.getApiURL() + '/restfacade/documentoDiSpesa/importoASaldo';
		let params = new HttpParams().set('idProgetto', idProgetto.toString());
		return this.http.get<EsitoImportoSaldoDTO>(url, { params: params });
	}
}
