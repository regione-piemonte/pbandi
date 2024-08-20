/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.business.intf;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbwebrce.dto.checklist.EsitoEliminaCheckListDTO;
import it.csi.pbandi.pbwebrce.exception.CheckListException;
import it.csi.pbandi.pbwebrce.pbandisrv.dto.FileDTO;

public interface CheckListSrv {

	public EsitoEliminaCheckListDTO eliminaBozzaCheckListDocumentaleAffidamento(

			java.lang.Long idUtente,

			java.lang.String identitaDigitale,

			java.lang.Long idAffidamento

	) throws it.csi.csi.wrapper.CSIException, it.csi.csi.wrapper.SystemException,
			it.csi.csi.wrapper.UnrecoverableException

			, CheckListException;

	FileDTO[] getFilesAssociatedVerbaleChecklist(Long idUtente, String identitaDigitale, Long idDocIndex)
			throws CSIException, SystemException, UnrecoverableException;

}
