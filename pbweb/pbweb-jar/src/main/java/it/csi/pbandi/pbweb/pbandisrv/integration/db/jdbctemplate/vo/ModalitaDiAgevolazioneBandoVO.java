package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoModalitaAgevolVO;

public class ModalitaDiAgevolazioneBandoVO extends PbandiRBandoModalitaAgevolVO{
	private String modalitaAgevolazioneComposta;
	private String modalita;

	public void setModalitaAgevolazioneComposta(
			String modalitaAgevolazioneComposta) {
		this.modalitaAgevolazioneComposta = modalitaAgevolazioneComposta;
	}
	public String getModalitaAgevolazioneComposta() {
		return modalitaAgevolazioneComposta;
	}
	public void setModalita(String modalita) {
		this.modalita = modalita;
	}
	public String getModalita() {
		return modalita;
	}
}
