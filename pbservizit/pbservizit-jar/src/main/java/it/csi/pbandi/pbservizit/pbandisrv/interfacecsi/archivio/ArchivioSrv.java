/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.archivio;

import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.*;
import it.csi.pbandi.pbservizit.pbandisrv.exception.archivio.*;


public interface ArchivioSrv {


	public it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileInfoDTO getFileInfo(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex,

	java.lang.Long idFolderSelected

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.archivio.ArchivioException;
	
	public it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.Esito associateFile(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex,

	java.lang.Long idTarget,

	java.lang.Long idEntita,

	java.lang.Long idProgetto

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.archivio.ArchivioException;
	

	public java.lang.Boolean isFileAssociated(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex,

	java.lang.Long idTarget,

	java.lang.Long idEntita

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbservizit.pbandisrv.exception.archivio.ArchivioException;

}
