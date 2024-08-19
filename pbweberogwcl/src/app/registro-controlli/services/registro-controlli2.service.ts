/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { map } from 'rxjs/operators';
import { ModificaIrregolaritaDefinitiva } from '../commons/models/modifica-irregolarita-definitiva';
import { EsitoSalvaIrregolaritaDTO } from '../commons/dto/esito-salva-irregolarita-dto';
import { RequestModificaIrreg } from '../commons/models/request-modifica-irreg';
import { RequestInserisciIrreg } from '../commons/models/request-inserisci-irreg';
import { Beneficiario } from '../commons/models/beneficiario';
import { ProgettoDTO } from '../commons/models/progetto-dto';
import { Irregolarita } from '../commons/models/irregolarita';
import { FiltroRilevazione } from 'src/app/monitoraggio-controlli/commons/models/filtro-rilevazione';
import { EsitoOperazioni } from 'src/shared/api/models/esito-operazioni';
import { FormControl, NgForm } from '@angular/forms';
import { SharedService } from 'src/app/shared/services/shared.service';

@Injectable()
export class RegistroControlliService2 {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private sharedService: SharedService
    ) { }

    getIdTipoOperazione(idU: number, idProgetto: number) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/idTipoOperazione';

        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idProgetto", idProgetto.toString())

        return this.http.get<number>(url, { params: params });
    }

    getDocumento(idDocumentoIndex: number) {
        let url = this.configService.getApiURL() + `/restfacade/registroControlli/documenti/${idDocumentoIndex}/contenuto`;

        return this.http.get(url, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
    }

    modificaIrregolarita(body: RequestModificaIrreg) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/modificaIrregolaritaDefinitiva';
        let formData = new FormData();
        formData.append("idU", body.idU.toString());
        formData.append("idIride", body.idIride);
        formData.append("tipoControlli", body.tipoControlli);
        formData.append("idPeriodo", body.idPeriodo.toString());
        formData.append("idCategAnagrafica", body.idCategAnagrafica.toString());
        formData.append("datacampione", body.dataCampione);
        formData.append("dataInizioControlli", body.dataInizioControlli);
        formData.append("dataFineControlli", body.dataFineControlli);
        formData.append("importoIrregolarita", body.importoIrregolarita.toString());
        formData.append("importoAgevolazioneIrreg", body.importoAgevolazioneIrreg.toString());
        formData.append("quotaImpIrregCertificato", body.quotaImpIrregCertificato.toString());
        formData.append("flagProvvedimento", body.flagProvvedimento);
        formData.append("idMotivoRevoca", body.idMotivoRevoca.toString());
        formData.append("notePraticaUsata", body.notePraticaUsata);
        formData.append("soggettoResponsabile", body.soggettoResponsabile);
        formData.append("note", body.note);
        formData.append("modificaDatiAggiuntivi", body.modificaDatiAggiuntivi);
        formData.append("idProgetto", body.idProgetto.toString());
        formData.append("idIrregolarita", body.idIrregolarita.toString());
        formData.append("olaf", body.fileOlaf, body.fileOlaf.name);
        formData.append("idDocumentoIndexOlaf", body.idDocumentoIndexOlaf);
        if (!(body.idDocumentoIndexDatiAggiuntivi == null)) {
            formData.append("idDocumentoIndexDatiAggiuntivi", body.idDocumentoIndexDatiAggiuntivi);
        }
        if (!(body.fileDatiAggiuntivi == undefined)) {
            formData.append("datiAggiuntivi", body.fileDatiAggiuntivi, body.fileDatiAggiuntivi.name);
        }
        return this.http.post<EsitoSalvaIrregolaritaDTO>(url, formData);
    }

    inserisciIrregolaritaDefinitiva(body: RequestModificaIrreg) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/inserimentoIrregolarita';
        let formData = new FormData();
        formData.append("idU", body.idU.toString());
        formData.append("idIride", body.idIride);
        formData.append("tipoControlli", body.tipoControlli);
        formData.append("idPeriodo", body.idPeriodo.toString());
        formData.append("idCategAnagrafica", body.idCategAnagrafica.toString());
        formData.append("datacampione", body.dataCampione);
        formData.append("dataInizioControlli", body.dataInizioControlli);
        formData.append("dataFineControlli", body.dataFineControlli);
        formData.append("importoIrregolarita", body.importoIrregolarita.toString());
        formData.append("importoAgevolazioneIrreg", body.importoAgevolazioneIrreg.toString());
        formData.append("quotaImpIrregCertificato", body.quotaImpIrregCertificato.toString());
        formData.append("flagProvvedimento", body.flagProvvedimento);
        formData.append("idMotivoRevoca", body.idMotivoRevoca.toString());
        formData.append("notePraticaUsata", body.notePraticaUsata);
        formData.append("soggettoResponsabile", body.soggettoResponsabile);
        formData.append("note", body.note);
        formData.append("modificaDatiAggiuntivi", body.modificaDatiAggiuntivi);
        formData.append("idProgetto", body.idProgetto.toString());
        if (body.idIrregolaritaProvv) {
            formData.append("idIrregolaritaProvv", body.idIrregolaritaProvv.toString());
        }
        formData.append("olaf", body.fileOlaf, body.fileOlaf.name);
        if (!(body.fileDatiAggiuntivi == undefined)) {
            formData.append("datiAggiuntivi", body.fileDatiAggiuntivi, body.fileDatiAggiuntivi.name);
        }
        return this.http.post<EsitoSalvaIrregolaritaDTO>(url, formData);
    }

    cercabeneficiari(idU: number, idSoggetto: number, denominazione: string, idBeneficiario: number) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/cercabeneficiari';

        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idSoggetto", idSoggetto.toString())
            .set("denominazione", denominazione);
        if (idBeneficiario) {
            params = params.set("idBeneficiario", idBeneficiario.toString());
        }

        return this.http.get<Array<Beneficiario>>(url, { params: params });
    }

    cercaProgetti(idU: number, idSoggetto: number, idSoggettoBeneficiario: number) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/cercaProgetti';

        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idSoggetto", idSoggetto.toString())
            .set("idSoggettoBeneficiario", idSoggettoBeneficiario.toString())

        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    bloccaSbloccaIrregolarita(idU: number, idIrregolarita: number) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/bloccaSbloccaIrregolarita';

        let params = new HttpParams()
            .set("idU", idU.toString())
            .set("idIrregolarita", idIrregolarita.toString())

        return this.http.get<EsitoSalvaIrregolaritaDTO>(url, { params: params });
    }

    salvaRegistroInvio(body: Irregolarita) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/salvaRegistroInvio';

        return this.http.post<EsitoSalvaIrregolaritaDTO>(url, body);
    }

    generaReportCampionamento(body: FiltroRilevazione) {
        let url = this.configService.getApiURL() + `/restfacade/monitoraggioControlli/reportCampionamento`;

        return this.http.post(url, body, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
    }

    propostaAperta(idProgetto: number, idBeneficario: number) {
        let url = this.configService.getApiURL() + '/restfacade/registroControlli/propostaAperta';

        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idBeneficario", idBeneficario.toString())

        return this.http.get<EsitoOperazioni>(url, { params: params });
    }

    rinuncia(idProgetto: number) {
        let url = this.configService.getApiURL() + '/restfacade/chiusuraProgetto/rinuncia';

        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())

        return this.http.get<EsitoOperazioni>(url, { params: params });
    }

    //validazione

    hasValidationError: boolean;

    checkRequiredForm(form: NgForm, name: string) {
        if (!form.form.get(name) || !form.form.get(name).value) {
            form.form.get(name).setErrors({ error: 'required' });
            this.hasValidationError = true;
        }
    }

    checkPatternError(form: NgForm, name: string) {
        if (form.form.get(name) && form.form.get(name).value
            && form.form.get(name).errors && form.form.get(name).errors.pattern) {
            this.hasValidationError = true;
        }
    }

    validateReg(dataFineControlli: FormControl) {
        if (!dataFineControlli || !dataFineControlli.value) {
            dataFineControlli.setErrors({ error: 'required' });
            this.hasValidationError = true;
        }
    }

    validateProvv(esitoForm: NgForm) {
        this.checkRequiredForm(esitoForm, "importoSpesaIrregolareIrregProvv");
        this.checkRequiredForm(esitoForm, "importoIrregCertIrregProvv");
        this.checkRequiredForm(esitoForm, "motivoIrregIrregProvv");
        this.checkPatternError(esitoForm, "importoSpesaIrregolareIrregProvv");
        this.checkPatternError(esitoForm, "importoIrregCertIrregProvv");
        this.checkPatternError(esitoForm, "importoAgevIrrIrregProvv");
    }

    validateDef(esitoForm: NgForm, dataFineControlli: FormControl, importoSpesaIrregolareIrregDef: string,
        importoAgevIrrIrregDef: string, importoIrregCertIrregDef: string) {
        if (!dataFineControlli || !dataFineControlli.value) {
            dataFineControlli.setErrors({ error: 'required' });
            this.hasValidationError = true;
        }
        this.checkRequiredForm(esitoForm, "importoSpesaIrregolareIrregDef");
        this.checkRequiredForm(esitoForm, "importoAgevIrrIrregDef");
        this.checkRequiredForm(esitoForm, "importoIrregCertIrregDef");
        this.checkRequiredForm(esitoForm, "motivoIrregIrregDef");
        this.checkRequiredForm(esitoForm, "soggContatChiarimIrregDef");
        this.checkRequiredForm(esitoForm, "allegDatiAccessIrreg");
        this.checkPatternError(esitoForm, "importoSpesaIrregolareIrregDef");
        this.checkPatternError(esitoForm, "importoAgevIrrIrregDef");
        this.checkPatternError(esitoForm, "importoIrregCertIrregDef");
        // RIMOSSI CONTROLLI SUGLI IMPORTI
        // let impIrr = this.sharedService.getNumberFromFormattedString(importoSpesaIrregolareIrregDef);
        // let impAgev = this.sharedService.getNumberFromFormattedString(importoAgevIrrIrregDef);
        // let impCert = this.sharedService.getNumberFromFormattedString(importoIrregCertIrregDef);
        // if (impIrr && impAgev && impIrr < impAgev) {
        //     esitoForm.form.get("importoAgevIrrIrregDef").setErrors({ error: "maggiore" })
        //     this.hasValidationError = true;
        // }
        // if (impIrr && impCert && impIrr < impCert) {
        //     esitoForm.form.get("importoIrregCertIrregDef").setErrors({ error: "maggiore" })
        //     this.hasValidationError = true;
        // }
    }

    validate(esitoForm: NgForm, esitoControlloRadio: string, dataInizioControlli: FormControl, dataFineControlli: FormControl, importoSpesaIrregolareIrregDef: string,
        importoAgevIrrIrregDef: string, importoIrregCertIrregDef: string) {
        this.hasValidationError = false;
        this.checkRequiredForm(esitoForm, "annoContabile");
        this.checkRequiredForm(esitoForm, "autoritaControllante");
        if (!dataInizioControlli || !dataInizioControlli.value) {
            dataInizioControlli.setErrors({ error: 'required' });
            this.hasValidationError = true;
        }
        if (dataInizioControlli && dataInizioControlli.value
            && dataFineControlli && dataFineControlli.value
            && dataInizioControlli.value > dataFineControlli.value) {
            dataInizioControlli.setErrors({ error: 'notValid' });
            this.hasValidationError = true;
        }
        if (esitoControlloRadio == "1") {
            this.validateReg(dataFineControlli);
        } else if (esitoControlloRadio == "2") {
            this.validateProvv(esitoForm);
        } else {
            this.validateDef(esitoForm, dataFineControlli, importoSpesaIrregolareIrregDef, importoAgevIrrIrregDef, importoIrregCertIrregDef);
        }
        dataInizioControlli.markAsTouched();
        dataFineControlli.markAsTouched();
        esitoForm.form.markAllAsTouched();
        return this.hasValidationError;
    }
}