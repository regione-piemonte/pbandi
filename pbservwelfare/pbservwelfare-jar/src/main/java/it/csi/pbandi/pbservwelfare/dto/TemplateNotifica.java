/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.dto;

public class TemplateNotifica {
    private Integer idTemplateNotifica;
    private String compMessage;
    private String elaboratedCompMessage;

    public Integer getIdTemplateNotifica() {
        return idTemplateNotifica;
    }

    public void setIdTemplateNotifica(Integer idTemplateNotifica) {
        this.idTemplateNotifica = idTemplateNotifica;
    }

    public String getCompMessage() {
        return compMessage;
    }

    public void setCompMessage(String compMessage) {
        this.compMessage = compMessage;
    }

    public String getElaboratedCompMessage() {
        return elaboratedCompMessage;
    }

    public void setElaboratedCompMessage(String elaboratedCompMessage) {
        this.elaboratedCompMessage = elaboratedCompMessage;
    }
}
