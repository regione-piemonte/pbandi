package it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa;

public class EsitoOperazioneVerificaDichiarazioneSpesa implements java.io.Serializable {

  private static final long serialVersionUID = 69L;

  private Boolean esito = null;
  private it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO[] documentiDiSpesa = null;
  private String message = null;
  private String[] messaggi = null;

  public Boolean getEsito() {
    return esito;
  }

  public void setEsito(Boolean val) {
    esito = val;
  }

  public it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO[] getDocumentiDiSpesa() {
    return documentiDiSpesa;
  }

  public void setDocumentiDiSpesa(it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO[] val) {
    documentiDiSpesa = val;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String val) {
    message = val;
  }

  public String[] getMessaggi() {
    return messaggi;
  }

  public void setMessaggi(String[] val) {
    messaggi = val;
  }

}