/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { InfoAssociazioneFileVo } from './../commons/vo/info-associazione-file-vo';
import { SalvaFileVo } from './../commons/vo/salva-file-vo';
import { FoldersVo } from './../commons/vo/folders-vo';
import { LeftTreeVo } from '../commons/vo/left-tree-vo';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { UserInfoSec } from '../../../public-api';

@Injectable()
export class ArchivioFileService {

    constructor(
        private http: HttpClient,
    ) { }

    inizializzaArchivioFile(apiURL: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/inizializzaArchivioFile';

        let params = new HttpParams()
            .set("idSoggetto", userInfo.idSoggetto.toString())
            .set("idSoggettoBeneficiario", userInfo.beneficiarioSelezionato.idBeneficiario.toString())
            .set("codiceRuolo", userInfo.codiceRuolo)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get<LeftTreeVo>(url, { params: params });
    }

    leggiFolder(apiURL: string, idFolder: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/leggiFolder';

        let params = new HttpParams()
            .set("idFolder", idFolder)
            .set("idSoggetto", userInfo.idSoggetto.toString())
            .set("codiceRuolo", userInfo.codiceRuolo);

        return this.http.get<Array<FoldersVo>>(url, { params: params });
    }

    salvaFiles(apiURL: string, idFolder: string, file: Array<File>, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/salvaFiles';

        let formData = new FormData()
        for (var i = 0; i < file.length; i++) {
            formData.append("file", file[i], file[i].name);
        }
        formData.append("idFolder", idFolder);
        formData.append("idUtente", userInfo.idUtente.toString());
        formData.append("idSoggettoBeneficiariO", userInfo.beneficiarioSelezionato.idBeneficiario.toString());

        return this.http.post<Array<SalvaFileVo>>(url, formData);
    }

    creaFolder(apiURL: string, nomeFolder: string, idFolderPadre: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/creaFolder';

        let params = new HttpParams()
            .set("nomeFolder", nomeFolder)
            .set("idFolderPadre", idFolderPadre)
            .set("idSoggettoBeneficiario", userInfo.beneficiarioSelezionato.idBeneficiario.toString())
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get(url, { params: params });
    }

    leggiRoot(apiURL: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/leggiRoot';

        let params = new HttpParams()
            .set("idSoggetto", userInfo.idSoggetto.toString())
            .set("idSoggettoBeneficiario", userInfo.beneficiarioSelezionato.idBeneficiario.toString())
            .set("codiceRuolo", userInfo.codiceRuolo)

        return this.http.get<Array<FoldersVo>>(url, { params: params });
    }

    cancellaFolder(apiURL: string, idFolder: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/cancellaFolder';

        let params = new HttpParams()
            .set("idFolder", idFolder)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get(url, { params: params });
    }

    cancellaFile(apiURL: string, idDocumentoIndex: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/cancellaFile';

        let params = new HttpParams()
            .set("idDocumentoIndex", idDocumentoIndex)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get(url, { params: params });
    }

    rinominaFolder(apiURL: string, idFolder: string, nomeFolder: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/rinominaFolder';

        let params = new HttpParams()
            .set("nomeFolder", nomeFolder)
            .set("idFolder", idFolder)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get(url, { params: params });
    }

    rinominaFile(apiURL: string, idDocumentoIndex: string, nomeFile: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/rinominaFile';

        let params = new HttpParams()
            .set("nomeFile", nomeFile)
            .set("idDocumentoIndex", idDocumentoIndex)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get(url, { params: params });
    }

    spostaFolder(apiURL: string, idFolder: string, idFolderDestinazione: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/spostaFolder';

        let params = new HttpParams()
            .set("idFolder", idFolder)
            .set("idFolderDestinazione", idFolderDestinazione)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get(url, { params: params });
    }

    spostaFile(apiURL: string, idDocumentoIndex: string, idFolder: string, idFolderDestinazione: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/spostaFile';

        let params = new HttpParams()
            .set("idDocumentoIndex", idDocumentoIndex)
            .set("idFolder", idFolder)
            .set("idFolderDestinazione", idFolderDestinazione)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get(url, { params: params });
    }

    leggiFile(apiURL: string, idDocumentoIndex: string) {
        let url = apiURL + '/restfacade/archivioFile/leggiFile';

        let params = new HttpParams()
            .set("idDocumentoIndex", idDocumentoIndex);

        return this.http.get(url, { params: params, responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
    }

    leggiFileConNome(apiURL: string, idDocumentoIndex: string) {
        let url = apiURL + '/restfacade/archivioFile/leggiFile';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex);
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    leggiFileFirmato(apiURL: string, idDocumentoIndex: string) {
        let url = apiURL + '/restfacade/archivioFile/leggiFileFirmato';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex);
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    leggiFileMarcato(apiURL: string, idDocumentoIndex: string) {
        let url = apiURL + '/restfacade/archivioFile/leggiFileMarcato';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex);
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    leggiFileFirmaAutografa(apiURL: string, idDocumentoIndex: string) {
        let url = apiURL + '/restfacade/archivioFile/leggiFileFirmaAutografa';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex);
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    infoFile(apiURL: string, idDocumentoIndex: string, idFolder: string, userInfo: UserInfoSec) {
        let url = apiURL + '/restfacade/archivioFile/infoFile';

        let params = new HttpParams()
            .set("idDocumentoIndex", idDocumentoIndex)
            .set("idFolder", idFolder)
            .set("idUtente", userInfo.idUtente.toString());

        return this.http.get<InfoAssociazioneFileVo>(url, { params: params });
    }
}