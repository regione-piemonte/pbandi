/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.interfacecsi.archivio;

import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.*;
import it.csi.pbandi.pbweb.pbandisrv.exception.archivio.*;


public interface ArchivioSrv {


	public it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileInfoDTO getFileInfo(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex,

	java.lang.Long idFolderSelected

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbweb.pbandisrv.exception.archivio.ArchivioException;
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.archivio.Esito marcaFlagEntitaFile(

	java.lang.Long idUtente,

	java.lang.String identitaDigitale,

	java.lang.Long idDocumentoIndex,

	java.lang.Long idEntita,

	java.lang.Long idTarget,

	java.lang.Long idProgetto,

	java.lang.String flag

	) throws it.csi.csi.wrapper.CSIException,
			it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, it.csi.pbandi.pbweb.pbandisrv.exception.archivio.ArchivioException;


}
