/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao;

import it.csi.pbandi.pbservwelfare.dto.IbanBeneficiario;
import it.csi.pbandi.pbservwelfare.dto.RequestRicezioneIban;

public interface GestioneBeneficiarioDAO {

    IbanBeneficiario getInfoIbanBeneficiario(String numeroDomanda);

	void censimentoNotificaProcesso(IbanBeneficiario infoIbanBeneficiario, RequestRicezioneIban request);

    void updateEstremiBancari(IbanBeneficiario infoIbanBeneficiario, String iban);
    
}
