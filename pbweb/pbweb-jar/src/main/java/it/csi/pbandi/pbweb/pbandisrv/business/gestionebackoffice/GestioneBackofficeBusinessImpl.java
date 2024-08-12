package it.csi.pbandi.pbweb.pbandisrv.business.gestionebackoffice;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.BilancioManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ComuniManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ConfigurationManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.VoceDiSpesaManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.AllegatoAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.AreaScientificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.AtecoAmmessoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.AtecoInclusoEsclusoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.BandoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.BandoLineaAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.BeneficiarioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.BeneficiarioProgettoAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.BeneficiarioProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.CausaleDiErogazioneAssociataDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.CheckListBandoLineaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.CostantiBandoLineaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DatiBandoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DatiBeneficiarioProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DefinizioneProcessoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DettTipolAiutiAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DettTipolInterventoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DettaglioBandoLinea;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DettaglioUtenteDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DocPagamBandoLineaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EnteDiCompetenzaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EnteDiCompetenzaRuoloAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoGeneraParolaChiaveActa;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoOperazioneAssociaProgettiAIstruttore;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoOperazioneDisassociaProgettiAIstruttore;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoSalvaDatiBando;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoSalvaUtente;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.IdDescBreveDescEstesa2DTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.IdDescBreveDescEstesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.InterventoAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.IstruttoreDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaAssociareDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.LineaDiInterventiDaModificareDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.LineaDiInterventoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.PbandiDMateriaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.PermessoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.PremialitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.ProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.RegolaAssociataDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.SettoreEnteDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.SezioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.SoggettoFinanziatoreAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.SportelloBandoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.TipoAnagraficaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.TipoDiAiutoAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.TipoDiTrattamentoAssociatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.TipolInterventoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.TipologiaBeneficiarioDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.UtenteRicercatoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.VoceDiEntrataDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.VoceDiSpesaAssociataDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionebackoffice.GestioneBackOfficeException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiBackOfficeDAOImpl;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AllegatoAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AtecoEsclusoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.AtecoInclusoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoCausaleDiErogazioneAssociataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoIndicatoreAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaEnteCompAttiviVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaIndicatoreQSNAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaIndicatoriRisultatoProgrammaAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTemaPrioritarioAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaTipoAiutoAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoSifNonAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoSoggettoFinanziatoreAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BandoTipoTrattamentoAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.BeneficiarioProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettTipolAiutiAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettTipolAiutiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioAllSoggettiConTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioSoggettoConTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.DettaglioSoggettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaAssociataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaProgSoggVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EnteDiCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.EsisteProgettoBandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.IndicatoriCodIgrueVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.InterventoAssociatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.MaxProgrOrdinamentoVoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ModalitaDiAgevolazioneBandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PermessoTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PersonaFisicaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.PremialitaAssociataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.ProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RegolaAssociataAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.RegolaAssociataBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SettoreEnteBandoRuoloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SezioneContenutoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SezioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoEnteCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoPermessoTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.SoggettoProgettoRuoloProcessoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiEntrataAssociataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaAssociataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VoceDiSpesaInterventoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VociDiSpesaAssociateABandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.VociDiSpesaConIdVoceDiSpesaPadreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BandoVO;
//import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioEnteGiuridico;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.BeneficiarioVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.DatiBeneficiarioProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.IstruttoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionebackoffice.SoggettoEnteGiuridicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.BetweenCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.FilterIgnoreCaseCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.LikeStartsWithCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.util.OrCondition;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCDefinizioneProcessoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCRegolaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAllegatiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDAreaScientificaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCampiInterventoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDCausaleErogazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDComuneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDettTipolAiutiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDDettTipolInterventiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDIndRisultatoProgramVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMateriaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDMicroSezioneModuloVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDModalitaPagamentoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDNaturaCipeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoGenQsnVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDObiettivoSpecifQsnVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDPermessoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDPremialitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDProvinciaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSettoreEnteVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSoggettoFinanziatoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDSottoSettoreCipeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAccessoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoDocumentoSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoLineaInterventoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipoSoggCorrelatoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipolBeneficiariVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDTipolInterventiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiEntrataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiSpesaMonitVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiDVoceDiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBanLineaIntTemPriVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandiAllegatiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandiAtecoEsclVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandiAtecoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandiDettTipAiutiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandiPremialitaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandiTipolIntervVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoCausErTipAllVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoCausaleErogazVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoIndicatoriVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLinIndRisProVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLinIndicatQsnVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLinTipoAiutoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLinTipoPeriodVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaEnteCompVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaPeriodoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaSettoreVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoModalitaAgevolVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoSoggFinanziatVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoTipoTrattamentVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoVoceEntrataVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoVoceSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBlTipoDocMicroSezVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBlTipoDocSezModVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDettTipolIntVocSpVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiREccezBanLinDocPagVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiREnteCompetenzaSoggVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRObtemClassraVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRPermessoTipoAnagrafVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRRegolaBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRRegolaTipoAnagVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggProgSoggCorrelVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggTipoAnagraficaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggettiCorrelatiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRSoggettoProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRTipoDocModalitaPagVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRTipolBenBandiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRTipolIntervVociSpeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRTpDocIndBanLiIntVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTBandoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEnteCompetenzaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTEnteGiuridicoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTFlussiUploadVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTIndirizzoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTLogProtocollazioneVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTPersonaFisicaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTRecapitiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTSedeVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTSportelliBandiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiVAtecoAmmessiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWCostantiVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiWCspCostantiVO;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionebackoffice.GestioneBackofficeSrv;
import it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbweb.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.pbandiutil.common.Constants;
import it.csi.pbandi.pbweb.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbweb.pbandiutil.common.messages.MessaggiConstants;

public class GestioneBackofficeBusinessImpl extends BusinessImpl implements
		GestioneBackofficeSrv {

	
	static String METODO_VERIFICA_LOG_PROTOCOLLAZIONE = "VERIFICA";
	
	private ComuniManager comuniManager;
	@Autowired
	private GenericDAO genericDAO;
	private PbandiBackOfficeDAOImpl pbandiBackOfficeDAO = null;
	private ProgettoManager progettoManager;
	private VoceDiSpesaManager voceDiSpesaManager;
	private BilancioManager bilancioManager;
	private ConfigurationManager configurationManager;
	
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public void setComuniManager(ComuniManager comuniManager) {
		this.comuniManager = comuniManager;
	}

	public ComuniManager getComuniManager() {
		return comuniManager;
	}
	public PbandiBackOfficeDAOImpl getPbandiBackOfficeDAO() {
		return pbandiBackOfficeDAO;
	}

	public void setPbandiBackOfficeDAO(
			PbandiBackOfficeDAOImpl pbandiBackOfficeDAO) {
		this.pbandiBackOfficeDAO = pbandiBackOfficeDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	private String leggiCostanteActa(String attributo, Long idUtente) {
		String valore = null;
		logger.info("[GestioneBackofficeBusinessImpl::leggiCostanteActa] BEGIN");
		logger.info("[GestioneBackofficeBusinessImpl::leggiCostanteActa] attributo="+attributo);
		try {
			PbandiCCostantiVO c = new PbandiCCostantiVO();
			c.setAttributo(attributo);
			c = genericDAO.findSingleWhere(c);
			valore = c.getValore();
			logger.info("Valore della costante "+attributo+" = "+valore);
		} catch (Exception e) {
			e.printStackTrace();
			valore = null;
			this.inserisciLogProtocollazione("ATTENZIONE! Non è stato possibile completare l'operazione", METODO_VERIFICA_LOG_PROTOCOLLAZIONE, "N", idUtente);
		}  		
		return valore;
	}

	// Metodo non piu utilizzato: la chiave acta viene creata in associaEnteDiCompetenza().
	@Deprecated
	public EsitoGeneraParolaChiaveActa generaParolaChiaveActa(Long idUtente, String identitaDigitale, Long progrBandoLineaIntervento)
		throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		
		logger.info("generaParolaChiaveActa(): progrBandoLineaIntervento = "+progrBandoLineaIntervento);
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaIntervento);

		EsitoGeneraParolaChiaveActa esito = new EsitoGeneraParolaChiaveActa();
		try {
			
			esito.setEsito(true);

			// Verifica se il bando linea deve avere una parola chiave Acta.
			BandoLineaEnteCompAttiviVO ente = new BandoLineaEnteCompAttiviVO();
			ente.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
			ente.setDescBreveRuoloEnte("DESTINATARIO");
			ente.setDescBreveTipoEnteCompetenz("ADG");
			List<BandoLineaEnteCompAttiviVO> lista = genericDAO.findListWhere(ente);
			
			// Caso in cui il bando linea non prevede l'integrazione con Acta.
			if (lista.isEmpty()) {
				logger.info("generaParolaChiaveActa(): nessuna integrazione con Acta");
				esito.setChiaveAmmessa(false);
				esito.setChiaveGiaEsistente(false);
				esito.setParolaChiaveActa("");
				esito.setEnteSettore("");
				logger.info("generaParolaChiaveActa(): esito restituito:"+esito.toString());
				return esito;
			} 
			
			// Cerca il bando linea per verificare se ha già la parola chiave acta.
			PbandiRBandoLineaInterventVO bl = new PbandiRBandoLineaInterventVO();
			bl.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
			bl = genericDAO.findSingleWhere(bl);
			
			// Caso in cui il bando linea ha già una parola chiave verificata.
			/*
			if (!StringUtil.isEmpty(bl.getParolaChiave())) {
				esito.setChiaveAmmessa(true);
				esito.setChiaveGiaEsistente(true);
				esito.setParolaChiaveActa(bl.getParolaChiave());
				esito.setEnteSettore("");
				logger.info("generaParolaChiaveActa(): esito restituito:"+esito.toString());
				return esito;
			}*/
			
			// Genero la parola chiave da visualizzare a video.
			String enteSettore = lista.get(0).getDescEnteSettore();
			String chiave = "PBANDIPBL"+progrBandoLineaIntervento+enteSettore;
			esito.setChiaveAmmessa(true);
			esito.setChiaveGiaEsistente(false);
			esito.setParolaChiaveActa(chiave);
			esito.setEnteSettore(enteSettore);
			logger.info("generaParolaChiaveActa(): esito restituito:"+esito.toString());

		} catch (Exception e) {
			logger.error("Errore durante la generazione della parola chiave Acta per il progr. bando linea "+progrBandoLineaIntervento, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
			logger.info("generaParolaChiaveActa(): esito restituito:"+esito.toString());
		}  
		
		return esito;
	}
	
	private void inserisciLogProtocollazione(String msg, String metodo, String flagEsito, Long idUtente) {
		PbandiTLogProtocollazioneVO l = new PbandiTLogProtocollazioneVO();
		l.setMessaggio(msg);
		l.setMetodo(metodo);
		l.setFlagEsito(flagEsito);
		l.setIdUtente(new BigDecimal(idUtente));
		java.util.Date d = new java.util.Date();
		java.sql.Date oggi = new java.sql.Date(d.getTime());
		l.setDtLog(oggi);
		logger.info("Inserisco il seguente record in PBANDI_T_LOG_PROTOCOLLAZIONE: "+l.toString());
		try {
			genericDAO.insert(l);
		} catch (Exception e1) {
			logger.error("Errore nella insert in PBANDI_T_LOG_PROTOCOLLAZIONE.");
		}
	}

	public BandoDTO[] findBandi(Long idUtente, String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

			PbandiTBandoVO[] bandiVO = genericDAO.findAll(PbandiTBandoVO.class);

			return beanUtil.transform(bandiVO, BandoDTO.class);
	}

	public IstruttoreDTO findDettaglioIstruttore(Long idUtente,
			String identitaDigitale, Long idSoggettoIstruttore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {

			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggettoIstruttore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggettoIstruttore);

			/*
			 * Se ho piu' di un istruttore restituisco il primo
			 */
			List<IstruttoreVO> istruttori = pbandiBackOfficeDAO
					.findDettaglioIstruttore(idSoggettoIstruttore);
			IstruttoreDTO dto = null;

			if (istruttori != null && istruttori.size() > 0) {
				dto = new IstruttoreDTO();
				beanUtil.valueCopy(istruttori.get(0), dto);
			}

			return dto;

	}

	public IstruttoreDTO[] findIstruttoriSemplici(Long idUtente,
			String identitaDigitale, Long idSoggettoIstruttoreMaster,
			IstruttoreDTO filtro) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggettoIstruttoreMaster", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggettoIstruttoreMaster, filtro);

			List<IstruttoreVO> istruttoriVO = pbandiBackOfficeDAO
					.findIstruttoriSempliciForIstruttoreMaster(
							idSoggettoIstruttoreMaster, filtro);
			List<IstruttoreDTO> istruttoriDTO = new ArrayList<IstruttoreDTO>();

			for (IstruttoreVO istruttoreVO : istruttoriVO) {
				IstruttoreDTO istruttore = new IstruttoreDTO();
				beanUtil.valueCopy(istruttoreVO, istruttore);

				
				/*
				 * ricerco i progetti associati ad ogni istruttore
				 */
				List<ProgettoVO> progettiAssociatiVO = pbandiBackOfficeDAO
						.findProgettiAssociatiIstruttore(istruttore
								.getIdSoggetto(), true);
				ProgettoDTO[] progettiAssociatiDTO = new ProgettoDTO[progettiAssociatiVO
						.size()];
				beanUtil.valueCopy(progettiAssociatiVO.toArray(),
						progettiAssociatiDTO);
				istruttore.setProgettiAssociati(progettiAssociatiDTO);
				istruttore.setTotaleProgettiAssociati(new Long(
						progettiAssociatiVO.size()));
				istruttoriDTO.add(istruttore);
			}
			return istruttoriDTO.toArray(new IstruttoreDTO[] {});

	}

	public ProgettoDTO[] findProgettiAssociatiAIstruttore(Long idUtente,
			String identitaDigitale, Long idSoggettoIstruttore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggettoIstruttore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggettoIstruttore);

			List<ProgettoVO> progettiVO = pbandiBackOfficeDAO
					.findProgettiAssociatiIstruttore(idSoggettoIstruttore, true);
			ProgettoDTO[] progetti = null;
			if (progettiVO != null) {
				progetti = new ProgettoDTO[progettiVO.size()];
				beanUtil.valueCopy(progettiVO.toArray(), progetti);
			}
			return progetti;
	}

	public ProgettoDTO[] findDettaglioProgettiAssociatiAIstruttore(
			Long idUtente, String identitaDigitale, Long idSoggettoIstruttore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggettoIstruttore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggettoIstruttore);

			List<ProgettoVO> progettiVO = pbandiBackOfficeDAO
					.findDettaglioProgettiAssociatiIstruttore(idSoggettoIstruttore);
			ProgettoDTO[] progetti = null;
			if (progettiVO != null) {
				progetti = new ProgettoDTO[progettiVO.size()];
				beanUtil.valueCopy(progettiVO.toArray(), progetti);
			}
			return progetti;
	}

	public ProgettoDTO[] findProgettiByBando(Long idUtente,
			String identitaDigitale, Long idBando) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			List<ProgettoVO> progettiVO = pbandiBackOfficeDAO
					.findProgettiByBando(idBando);
			ProgettoDTO[] progettiDTO = new ProgettoDTO[progettiVO.size()];
			beanUtil.valueCopy(progettiVO.toArray(), progettiDTO);
			return progettiDTO;

	}

	public BeneficiarioProgettoDTO[] findBeneficiari(Long idUtente,
			String identitaDigitale, Long idBando, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);
			ProgettoDTO filtro = new ProgettoDTO();
			filtro.setIdBando(idBando);
			filtro.setIdProgetto(idProgetto);
			List<BeneficiarioVO> beneficiariVO = pbandiBackOfficeDAO
					.findBeneficiariProgetto(filtro);
			BeneficiarioProgettoDTO[] beneficiariDTO = new BeneficiarioProgettoDTO[beneficiariVO
					.size()];
			beanUtil.valueCopy(beneficiariVO.toArray(), beneficiariDTO);
			return beneficiariDTO;
	}

	public ProgettoDTO[] findProgettiDaAssociare(Long idUtente,
			String identitaDigitale, Long idBando, Long idProgetto,
			Long idSoggettoBeneficiario, Long idSoggettoMaster,
			boolean isIstruttoriAssociati) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando", "isIstruttoriAssociati" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando, isIstruttoriAssociati);
			/*
			 * Cerco i progetti da associare
			 */
			List<ProgettoVO> progettiDaAssociareVO = pbandiBackOfficeDAO
					.findProgettiDaAssociare(idBando, idProgetto,
							isIstruttoriAssociati);
			List<ProgettoDTO> progetti = new ArrayList<ProgettoDTO>();

			for (ProgettoVO progettoVO : progettiDaAssociareVO) {
				ProgettoDTO progetto = new ProgettoDTO();
				beanUtil.valueCopy(progettoVO, progetto);
				boolean progettoDaAssociare = true;
				/*
				 * Se isIstruttoriAssociati e' true allora ricerco anche il
				 * numero di istruttori associati ad ogni progetto Se il
				 * progetto non ha istruttori associati non lo considero
				 */
				Long numeroIstruttoriAssociati = new Long(0);
				if (isIstruttoriAssociati) {
					/*
					 * FIX PBandi-698
					 */
					List<IstruttoreVO> istruttoriAssociatiVO = pbandiBackOfficeDAO
							.findIstruttoriAssociatiAProgetto(idSoggettoMaster,
									idBando, progettoVO.getIdProgetto());
					if (istruttoriAssociatiVO != null
							&& istruttoriAssociatiVO.size() > 0) {
						numeroIstruttoriAssociati = new Long(
								istruttoriAssociatiVO.size());
						IstruttoreDTO[] istruttoriAssociati = new IstruttoreDTO[istruttoriAssociatiVO
								.size()];
						beanUtil.valueCopy(istruttoriAssociatiVO.toArray(),
								istruttoriAssociati);
						progetto
								.setIstruttoriSempliciAssociati(istruttoriAssociati);

					} else {
						progettoDaAssociare = false;
					}

				}
				if (progettoDaAssociare) {
					progetto
							.setNumeroIstruttoriAssociati(numeroIstruttoriAssociati);
					/*
					 * Cerco il beneficiario del progetto corrente
					 */
					ProgettoDTO filtro = new ProgettoDTO();
					filtro.setIdBando(idBando);
					filtro.setIdProgetto(progetto.getIdProgetto());
					filtro.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
					List<BeneficiarioVO> beneficiari = pbandiBackOfficeDAO
							.findBeneficiariProgetto(filtro);
					/*
					 * Se e' impostato il filtro sul beneficiario (cioe'
					 * idSoggettoBeneficiario e' diverso da null) allora
					 * considero validi solo i progetti pre i quali esiste il
					 * beneficiario
					 */
					if (beneficiari != null && beneficiari.size() > 0) {
						BeneficiarioVO beneficiario = beneficiari.get(0);
						progetto
								.setBeneficiario(beneficiario.getBeneficiario());
						progetto
								.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
						progetti.add(progetto);
					}

				}
			}
			return progetti.toArray(new ProgettoDTO[] {});

	}

	public ProgettoDTO[] findProgettiBeneficiarioBando(Long idUtente,
			String identitaDigitale, Long idBando, Long idSoggettoBeneficiario)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando", "idSoggettoBeneficiario" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando, idSoggettoBeneficiario);

			List<ProgettoVO> progettiVO = pbandiBackOfficeDAO
					.findProgettiByBeneficiarioBando(idSoggettoBeneficiario,
							idBando);
			ProgettoDTO[] progetti = null;
			if (progettiVO != null) {
				progetti = new ProgettoDTO[progettiVO.size()];
				beanUtil.valueCopy(progettiVO.toArray(), progetti);
			}
			return progetti;

	}

	public EsitoOperazioneAssociaProgettiAIstruttore associaProgettiAIstruttore(
			Long idUtente, String identitaDigitale, String[] progetti,
			Long idIstruttore, Long idIstruttoreMaster, String codRuolo)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			EsitoOperazioneAssociaProgettiAIstruttore esito = null;
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idIstruttore", "codRuolo" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idIstruttore, codRuolo);
			/*
			 * Il ruolo che dovra' avere l'istruttore semplice dipende dal ruolo
			 * dell' istruttore master: - se istruttore master ha OI-ISTR-MASTER
			 * allora l' istruttore semplice avra' OI-ISTRUTTORE - se istruttore
			 * master ha ADG-ISTR-MASTER allora l' istruttore semplice avra'
			 * ADG-ISTRUTTORE
			 */
			DecodificaDTO decodifica = createRuoloIstruttoreSemplice(
					idIstruttoreMaster, codRuolo);

			/*
			 * Per i progetti che sono stati gia' associati ed annullati allora
			 * imposto a null la data fine validita' di
			 * PBANDI_R_SOGGETTO_PROGETTO per gli altri eseguo la insert
			 */
			if (progetti != null && progetti.length > 0) {

				List<String> associati = new ArrayList<String>();
				List<String> scartati = new ArrayList<String>();
				List<ProgettoVO> progettiFlux = new ArrayList<ProgettoVO>();
				/*
				 * Verifico quali dei progetti selezionati sono stati gia'
				 * associati in precedenza
				 */

				List<ProgettoVO> progettiAssociati = pbandiBackOfficeDAO
						.verificaProgettiAssociatiIstruttore(idIstruttore,
								Arrays.asList(progetti), false);

				/*
				 * Per i progetti gia' associati eseguo l'update
				 */

				for (ProgettoVO progettoAssociato : progettiAssociati) {
					try {
						PbandiRSoggettoProgettoVO soggettoProgettoVO = new PbandiRSoggettoProgettoVO();
						soggettoProgettoVO.setProgrSoggettoProgetto(beanUtil.transform(progettoAssociato
								.getProgrSoggettoProgetto(), BigDecimal.class));
						
						soggettoProgettoVO = genericDAO.where(soggettoProgettoVO).selectSingle();
						
						soggettoProgettoVO.setIdTipoAnagrafica(beanUtil.transform(decodifica
								.getId(), BigDecimal.class));
						soggettoProgettoVO.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
						soggettoProgettoVO.setDtFineValidita(null);
						soggettoProgettoVO.setIdUtenteAgg(new BigDecimal(idUtente));
						logger.warn("\n\n\n\n\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ aggiorno flagAggiornatoFlux PbandiRSoggettoProgetto("+progettoAssociato
								.getProgrSoggettoProgetto()+")  a false");
						genericDAO.updateNullables(soggettoProgettoVO);
						
						progettiFlux.add(progettoAssociato);
					} catch (Exception dae) {
						scartati.add(progettoAssociato.getCodiceVisualizzato());
						logger.error("Progetto ["
								+ progettoAssociato.getIdProgetto() + "] "
								+ progettoAssociato.getCodiceVisualizzato()
								+ " non associato.", dae);
					}
				}

				/*
				 * Per i progetti non associati in precedenza, eseguo la insert.
				 */
				for (String idProgetto : progetti) {
					ProgettoVO progettoVO = pbandiBackOfficeDAO
							.findProgetto(new Long(idProgetto));
					if (!progettiAssociati.contains(progettoVO)) {
						/*
						 * Ricerco i dati dell' istruttore semplice
						 */
						List<IstruttoreVO> dettagliIstruttore = pbandiBackOfficeDAO
								.findDettaglioIstruttore(idIstruttore);
						Long idPersonaFisica = null;
						if (dettagliIstruttore != null
								&& dettagliIstruttore.size() > 0) {
							IstruttoreVO istruttore = dettagliIstruttore.get(0);
							idPersonaFisica = istruttore.getIdPersonaFisica();
						}
						try {
							PbandiRSoggettoProgettoVO soggettoProgettoVO = new PbandiRSoggettoProgettoVO();
							soggettoProgettoVO.setIdSoggetto(beanUtil.transform(idIstruttore, BigDecimal.class));
							soggettoProgettoVO.setIdPersonaFisica(beanUtil.transform(idPersonaFisica, BigDecimal.class));
							soggettoProgettoVO.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
							soggettoProgettoVO.setIdTipoAnagrafica(beanUtil.transform(decodifica
									.getId(), BigDecimal.class));
							
							soggettoProgettoVO.setIdUtenteIns(beanUtil.transform(idUtente, BigDecimal.class));
							soggettoProgettoVO = genericDAO.insert(soggettoProgettoVO);
							
							progettoVO.setProgrSoggettoProgetto(soggettoProgettoVO.getProgrSoggettoProgetto().longValue());
							progettiFlux.add(progettoVO);
						} catch (Exception dae) {
							scartati.add(progettoVO.getCodiceVisualizzato());
							logger.error("Progetto ["
									+ progettoVO.getIdProgetto() + "] "
									+ progettoVO.getCodiceVisualizzato()
									+ " non associato.", dae);
						}
					}
				}

				/*
				 * Aggiorno la mappatura su flux
				 */
				for (ProgettoVO progetto : progettiFlux) {
					associati.add(progetto.getCodiceVisualizzato());
				}

				esito = new EsitoOperazioneAssociaProgettiAIstruttore();
				esito.setProgettiAssociati(associati.toArray(new String[] {}));
				esito.setProgettiScartati(scartati.toArray(new String[] {}));
				if (scartati.size() > 0)
					esito.setEsito(Boolean.FALSE);
				else
					esito.setEsito(Boolean.TRUE);
			}
			return esito;

	}

	public EsitoOperazioneDisassociaProgettiAIstruttore disassociaProgettiAIstruttore(
			Long idUtente, String identitaDigitale,
			String[] progressiviProgettoSoggetto, Long idIstruttoreMaster,
			String codRuolo) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
			EsitoOperazioneDisassociaProgettiAIstruttore esito = null;
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progressiviProgettoSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progressiviProgettoSoggetto);
			if (progressiviProgettoSoggetto != null
					&& progressiviProgettoSoggetto.length > 0) {
				/*
				 * Il ruolo che dovra' avere l'istruttore semplice dipende dal
				 * ruolo dell' istruttore master: - se istruttore master ha
				 * OI-ISTR-MASTER allora l' istruttore semplice avra'
				 * OI-ISTRUTTORE - se istruttore master ha ADG-ISTR-MASTER
				 * allora l' istruttore semplice avra' ADG-ISTRUTTORE
				 */
				DecodificaDTO decodifica = createRuoloIstruttoreSemplice(
						idIstruttoreMaster, codRuolo);

				List<String> disassociati = new ArrayList<String>();
				List<String> scartati = new ArrayList<String>();
				for (String progrSoggettoProgetto : progressiviProgettoSoggetto) {
					boolean checkDataFine = false;
					ProgettoVO progetto = pbandiBackOfficeDAO
							.findProgettoByProgressivoSoggettoProgetto(
									new Long(progrSoggettoProgetto),
									checkDataFine);

					try {
						PbandiRSoggettoProgettoVO soggettoProgettoVO = new PbandiRSoggettoProgettoVO();
						soggettoProgettoVO.setProgrSoggettoProgetto(beanUtil.transform(progrSoggettoProgetto, BigDecimal.class));
						
						soggettoProgettoVO = genericDAO.where(soggettoProgettoVO).selectSingle();
						
						soggettoProgettoVO.setIdTipoAnagrafica(beanUtil.transform(decodifica
								.getId(), BigDecimal.class));
						logger.warn("\n\n\n\n\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ aggiorno flagAggiornatoFlux PbandiRSoggettoProgetto ("+progrSoggettoProgetto
								+")  a false");
						soggettoProgettoVO.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
						soggettoProgettoVO.setDtFineValidita(DateUtil.getSysdate());
						soggettoProgettoVO.setIdUtenteAgg(new BigDecimal(idUtente));
						genericDAO.updateNullables(soggettoProgettoVO);
						

						disassociati.add(progetto.getCodiceVisualizzato());
					} catch (Exception dae) {
						logger.error("Progetto "
								+ progetto.getCodiceVisualizzato()
								+ " non associato.", dae);
						scartati.add(progetto.getCodiceVisualizzato());
					}
				}
				esito = new EsitoOperazioneDisassociaProgettiAIstruttore();
				esito.setProgettiDisassociati(disassociati
						.toArray(new String[] {}));
				esito.setProgettiScartati(scartati.toArray(new String[] {}));

				if (scartati.size() > 0)
					esito.setEsito(Boolean.FALSE);
				else
					esito.setEsito(Boolean.TRUE);
			}
			return esito;

	}

	public IstruttoreDTO[] findIstruttoriSempliciAssociatiProgetto(
			Long idUtente, String identitaDigitale,
			Long idSoggettoIstruttoreMaster, Long idBando, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggettoIstruttoreMaster" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggettoIstruttoreMaster);

			List<IstruttoreVO> istruttoriVO = pbandiBackOfficeDAO
					.findIstruttoriAssociatiAProgetto(
							idSoggettoIstruttoreMaster, idBando, idProgetto);
			IstruttoreDTO istruttoriDTO[] = new IstruttoreDTO[istruttoriVO
					.size()];
			beanUtil.valueCopy(istruttoriVO.toArray(), istruttoriDTO);
			return istruttoriDTO;
	}

	private DecodificaDTO createRuoloIstruttoreSemplice(
			Long idIstruttoreMaster, String codRuolo) {
			DecodificaDTO ruoloIstruttoreSemplice = new DecodificaDTO();
			String ruoloIstrMaster = pbandiBackOfficeDAO
					.findRuoloIstruttoreMaster(idIstruttoreMaster, codRuolo,
							true);
			if (ruoloIstrMaster != null) {
				if (ruoloIstrMaster.equals(Constants.RUOLO_ADG_IST_MASTER))
					ruoloIstruttoreSemplice = decodificheManager
							.findDecodifica(
									GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
									Constants.RUOLO_ADG_ISTRUTTORE);
				else if (ruoloIstrMaster.equals(Constants.RUOLO_OI_IST_MASTER))
					ruoloIstruttoreSemplice = decodificheManager
							.findDecodifica(
									GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
									Constants.RUOLO_OI_ISTRUTTORE);
			}

			return ruoloIstruttoreSemplice;

	}

	public BandoDTO[] findBandiConfigurabili(Long idUtente,
			String identitaDigitale, String titoloBando, String nomeBandoLinea,
			Long idMateria, Long idModalitaAttivazione, Long idLineaDiIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

			BandoVO filter = new BandoVO();
			boolean almenoUnoNonNull = false;

			if (titoloBando != null && !titoloBando.equals("")) {
				filter.setTitoloBando(titoloBando.toUpperCase());
				almenoUnoNonNull = true;
			}
			if (nomeBandoLinea != null && !nomeBandoLinea.equals("")) {
				filter.setNomeBandoLinea(nomeBandoLinea.toUpperCase());
				almenoUnoNonNull = true;
			}
			if (idMateria != null && !idMateria.equals("")) {
				filter.setIdMateria(idMateria);
				almenoUnoNonNull = true;
			}
			if (idLineaDiIntervento != null && !idLineaDiIntervento.equals("")) {
				filter.setIdLineaDiIntervento(idLineaDiIntervento);
				almenoUnoNonNull = true;
			}
			if (!almenoUnoNonNull)
				throw new GestioneBackOfficeException(ERRORE_CAMPI_OBBLIGATORI);

			List<BandoVO> bandiVO = pbandiBackOfficeDAO
					.findBandiConfigurabili(filter);
			BandoDTO[] bandiDTO = null;
			if (bandiVO != null && !(bandiVO.size() == 0)) {
				bandiDTO = new BandoDTO[bandiVO.size()];
				beanUtil.valueCopy(bandiVO.toArray(), bandiDTO);
			}

			return bandiDTO;
	}

	public PbandiDMateriaDTO[] findMaterieDiRiferimento(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

			PbandiDMateriaVO[] pbandiDMateriaVO = genericDAO
					.findAll(PbandiDMateriaVO.class);
			PbandiDMateriaDTO[] pbandiDMateriaDTO = new PbandiDMateriaDTO[pbandiDMateriaVO.length];

			beanUtil.valueCopy(pbandiDMateriaVO, pbandiDMateriaDTO);

			return pbandiDMateriaDTO;
	}

	public DatiBandoDTO findDatiBando(Long idUtente, String identitaDigitale,
			DatiBandoDTO bandoDaTrovare) throws CSIException, SystemException,
			UnrecoverableException {
			DatiBandoDTO resultDTO = new DatiBandoDTO();

			PbandiTBandoVO queryVO = new PbandiTBandoVO();

			if (bandoDaTrovare != null && bandoDaTrovare.getIdBando() != null
					&& !bandoDaTrovare.getIdBando().equals("")) {
				queryVO.setIdBando(new BigDecimal(bandoDaTrovare.getIdBando()));

				queryVO = genericDAO.findSingleWhere(queryVO);
				try {
					beanUtil.valueCopy(queryVO, resultDTO,
							mapPbandiTBandoToDatiBandoDTO());

					if (queryVO.getFlagAllegato() != null) {
						if (queryVO.getFlagAllegato().equals(
								Constants.FLAG_TRUE))
							resultDTO.setIsPrevistoAllegato(true);
						else
							resultDTO.setIsPrevistoAllegato(false);
					}

					if (queryVO.getFlagGraduatoria() != null) {
						if (queryVO.getFlagGraduatoria().equals(
								Constants.FLAG_TRUE))
							resultDTO.setIsPrevistaGraduatoria(true);
						else
							resultDTO.setIsPrevistaGraduatoria(false);
					}

					if (queryVO.getFlagQuietanza() != null) {
						if (queryVO.getFlagQuietanza().equals(
								Constants.FLAG_TRUE))
							resultDTO.setIsQuietanza(true);
						else
							resultDTO.setIsQuietanza(false);
					}

					// recupera il settore CIPE padre
					if (resultDTO.getIdSottoSettoreCIPE() != null) {
						PbandiDSottoSettoreCipeVO sottoSettoreVO = new PbandiDSottoSettoreCipeVO(
								new BigDecimal(resultDTO
										.getIdSottoSettoreCIPE()));
						sottoSettoreVO = genericDAO
								.findSingleWhere(sottoSettoreVO);
						resultDTO.setIdSettoreCIPE(NumberUtil
								.toLong(sottoSettoreVO.getIdSettoreCipe()));
					}
					
					// Cultura.
					if (queryVO.getIdLineaDiIntervento() != null) {
						int idProcessoCultura = it.csi.pbandi.pbweb.pbandisrv.util.Constants.ID_PROCESSO_CULTURA;
						PbandiDLineaDiInterventoVO linea = new PbandiDLineaDiInterventoVO(); 
						linea.setIdLineaDiIntervento(queryVO.getIdLineaDiIntervento());
						linea = genericDAO.findSingleWhere(linea);
						boolean isCultura = (linea.getIdProcesso() != null && linea.getIdProcesso().intValue() == idProcessoCultura);
						resultDTO.setIsBandoCultura(isCultura);	
					} else {
						resultDTO.setIsBandoCultura(false);
					} 

				} catch (Exception e) {
					throw new UnrecoverableException(
							"Impossibile mappare il risultato ottenuto.", e);
				}
			}

			return resultDTO;
	}

	private Map<String, String> mapPbandiTBandoToDatiBandoDTO() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("titoloBando", "titoloBando");
		map.put("dtInizioPresentazDomande", "dataPresentazioneDomande");
		map.put("idMateria", "idMateriaRiferimento");
		map.put("importoPremialita", "importoPremialita");
		map.put("dtScadenzaBando", "dataScadenza");
		map.put("idBando", "idBando");
		map.put("percentualePremialita", "percentualePremialita");
		map.put("idModalitaAttivazione", "idModalitaAttivazione");
		map.put("dotazioneFinanziaria", "dotazioneFinanziaria");
		map.put("dtPubblicazioneBando", "dataPubblicazione");
		map.put("punteggioPremialita", "punteggioPremialita");
		map.put("idUtenteAgg", "idUtenteAgg");
		map.put("idUtenteIns", "idUtenteIns");
		map.put("costoTotaleMinAmmissibile", "costoTotaleMinimoAmmissibile");
		map.put("idIntesa", "idCodiceIntesaIstituzionale");
		map.put("idTipoOperazione", "idTipoOperazione");
		map.put("idSottoSettoreCipe", "idSottoSettoreCIPE");
		map.put("idNaturaCipe", "idNaturaCIPE");
		map.put("idSettoreCpt", "idSettoreCPT");
		map.put("idTipologiaAttivazione", "idTipologiaAttivazione");
		// CDU-13 V07: inizio.
		/* commentato causa sospensione collegamento con findom.
		map.put("numMaxDomande", "numMaxDomande");
		map.put("flagFindom", "flagFindom");
		*/		
		// CDU-13 V07: fine	
		// CDU-13 V08: inizio
		map.put("idLineaDiIntervento", "idLineaDiIntervento");
		map.put("dataDeterminaApprovazione", "dataDeterminaApprovazione");
		map.put("determinaApprovazione", "determinaApprovazione");
		// CDU-13 V08: fine
		
		return map;
	}

	public EsitoSalvaDatiBando salvaDatiBando(Long idUtente,
			String identitaDigitale, DatiBandoDTO dto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "fornitore",
				"dto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, dto);

		EsitoSalvaDatiBando esito = new EsitoSalvaDatiBando();
		logger.info("\n\n\n\n\nsalva dati bando");
		
			PbandiTBandoVO vo = new PbandiTBandoVO();
			try {
				beanUtil.valueCopy(dto, vo, mapDatiBandoDTOToPbandiTBando());
				if (dto.getIsPrevistaGraduatoria())
					vo.setFlagGraduatoria(Constants.FLAG_TRUE);
				else
					vo.setFlagGraduatoria(Constants.FLAG_FALSE);
				if (dto.getIsPrevistoAllegato())
					vo.setFlagAllegato(Constants.FLAG_TRUE);
				else
					vo.setFlagAllegato(Constants.FLAG_FALSE);
				if (dto.getIsQuietanza())
					vo.setFlagQuietanza(Constants.FLAG_TRUE);
				else
					vo.setFlagQuietanza(Constants.FLAG_FALSE);
				
			} catch (Exception e) {
				throw new UnrecoverableException(
						"Impossibile mappare i dati inseriti.", e);
			}

			if (dto.getIdBando() == null
					|| dto.getIdBando().compareTo(new Long(0)) < 0) {
				// nuovo bando
				try {
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					vo = genericDAO.insert(vo);
				} catch (Exception e) {
					throw new GestioneBackOfficeException(
							"Errore durante l'inserimento di un nuovo bando.",
							e);
				}
			} else {
				// modifica bando
				try {
					vo.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.updateNullables(vo);
				} catch (Exception e) {
					throw new GestioneBackOfficeException(
							"Errore durante la modifica del bando (ID="
									+ vo.getIdBando() + ")", e);
				}
			}

			esito.setEsito(true);
			esito.setIdBando(NumberUtil.toLong(vo.getIdBando()));
			esito
					.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			return esito;
	}

	private Map<String, String> mapDatiBandoDTOToPbandiTBando() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("titoloBando", "titoloBando");
		map.put("dataPresentazioneDomande", "dtInizioPresentazDomande");
		map.put("idMateriaRiferimento", "idMateria");
		map.put("importoPremialita", "importoPremialita");
		map.put("dataScadenza", "dtScadenzaBando");
		map.put("idBando", "idBando");
		map.put("percentualePremialita", "percentualePremialita");
		map.put("idModalitaAttivazione", "idModalitaAttivazione");
		map.put("dotazioneFinanziaria", "dotazioneFinanziaria");
		map.put("dataPubblicazione", "dtPubblicazioneBando");
		map.put("punteggioPremialita", "punteggioPremialita");
		map.put("idUtenteAgg", "idUtenteAgg");
		map.put("idUtenteIns", "idUtenteIns");
		map.put("costoTotaleMinimoAmmissibile", "costoTotaleMinAmmissibile");
		map.put("idCodiceIntesaIstituzionale", "idIntesa");
		map.put("idTipoOperazione", "idTipoOperazione");
		map.put("idSottoSettoreCIPE", "idSottoSettoreCipe");
		map.put("idNaturaCIPE", "idNaturaCipe");
		map.put("idSettoreCPT", "idSettoreCpt");
		map.put("idTipologiaAttivazione", "idTipologiaAttivazione");
		// CDU-13 V07: inizio.
		/* commentato causa sospensione collegamento con findom.
		map.put("numMaxDomande", "numMaxDomande");
		map.put("flagFindom", "flagFindom");
		*/
		// CDU-13 V07: fine
		// CDU-13 V08: inizio
		map.put("idLineaDiIntervento", "idLineaDiIntervento");
		map.put("dataDeterminaApprovazione", "dataDeterminaApprovazione");
		map.put("determinaApprovazione", "determinaApprovazione");
		// CDU-13 V08: fine
		return map;
	}

	// Il parametro "tipoProgrammazione" serve solo quando si cercano le normative (idLineaPadre = null).
	public LineaDiInterventoDTO[] findLineeDiIntervento(Long idUtente,
			String identitaDigitale, Long idLineaPadre, String tipoProgrammazione) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			
			logger.info("findLineeDiIntervento(): idLineaPadre = "+idLineaPadre+"; tipoProgrammazione = "+tipoProgrammazione);
			
			PbandiDLineaDiInterventoVO[] pbandiDLineaDiInterventoVO;
			if (idLineaPadre == null) {
				
				/* codice precedente
				pbandiDLineaDiInterventoVO = genericDAO
						.findWhere(new NullCondition<PbandiDLineaDiInterventoVO>(
								PbandiDLineaDiInterventoVO.class,
								"idLineaDiInterventoPadre"));
				*/
				
				// Si cercano Normative relative ad una data programmazione (pre-2016 o post-2016).
				
				// Cerca le linee di intervento su db.
				PbandiDLineaDiInterventoVO query = new PbandiDLineaDiInterventoVO();
				query.setIdTipoLineaIntervento(new BigDecimal(1));
				List<PbandiDLineaDiInterventoVO> listaVO = genericDAO.findListWhere(query);
				
				// Considera le linee di intervento relative alla programmazione in input.
				java.sql.Date data2016 = DateUtil.utilToSqlDate(DateUtil.getDate("01/01/2016"));
				ArrayList<PbandiDLineaDiInterventoVO> listaVORidotta = new ArrayList<PbandiDLineaDiInterventoVO>(); 
				if (it.csi.pbandi.pbweb.pbandisrv.util.Constants.PROGRAMMAZIONE_PRE_2016.equalsIgnoreCase(tipoProgrammazione))
				{
					// Considera le linee con data < 2016
					for (PbandiDLineaDiInterventoVO vo : listaVO) {
						if (vo.getDtInizioValidita().before(data2016))
							listaVORidotta.add(vo);
					}
				} else if (it.csi.pbandi.pbweb.pbandisrv.util.Constants.PROGRAMMAZIONE_2016.equalsIgnoreCase(tipoProgrammazione)) {
					// Considera le linee con data > = 2016
					for (PbandiDLineaDiInterventoVO vo : listaVO) {
						if (!(vo.getDtInizioValidita().before(data2016)))
							listaVORidotta.add(vo);
					}
				} else {
					// Considera tutte le linee trovate.
					for (PbandiDLineaDiInterventoVO vo : listaVO) {						
							listaVORidotta.add(vo);
					}
				}

				pbandiDLineaDiInterventoVO = listaVORidotta.toArray(new PbandiDLineaDiInterventoVO[]{});
				
			} else {
				// Si cercano Assi, Misure o Linee.
				PbandiDLineaDiInterventoVO query = new PbandiDLineaDiInterventoVO();
				query.setIdLineaDiInterventoPadre(new BigDecimal(idLineaPadre));
				
				ArrayList<String> orderArray = new ArrayList<String>() {
					{
						add("descBreveLinea");
						add("descLinea");
					}
				};
				query.setOrderPropertyNamesList(orderArray);
				query.setAscendentOrder(true);
				
				pbandiDLineaDiInterventoVO = genericDAO.findWhere(query);
			}

			LineaDiInterventoDTO[] dto = new LineaDiInterventoDTO[pbandiDLineaDiInterventoVO.length];

			Map<String, String> beanMap = new HashMap<String, String>();
			beanMap.put("descBreveLinea", "descBreve");
			beanMap.put("descLinea", "descEstesa");
			beanMap.put("idLineaDiIntervento", "idLinea");

			beanUtil.valueCopy(pbandiDLineaDiInterventoVO, dto, beanMap);
			for (int i = 0; i < dto.length; i++) {
				logger.info("Linea di intervento: "+dto[i].getIdLinea()+" - "+dto[i].getDescBreve());
			}
			return dto;

		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}
	}

	public EsitoAssociazione associaLineaDiIntervento(Long idUtente,
			String identitaDigitale,
			LineaDiInterventiDaAssociareDTO dettaglioLineaDiIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando", "idLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, dettaglioLineaDiIntervento.getIdBando(),
					dettaglioLineaDiIntervento.getIdLineaDiIntervento());

			// check di esistenza di un bando-linea con lo stesso nome
			PbandiRBandoLineaInterventVO vo = beanUtil.transform(
					dettaglioLineaDiIntervento,
					PbandiRBandoLineaInterventVO.class,
					new HashMap<String, String>() {
						{
							put("nomeBandoLinea", "nomeBandoLinea");
						}
					});

			PbandiRBandoLineaInterventVO[] lineeVO = genericDAO
					.findWhere(new FilterIgnoreCaseCondition<PbandiRBandoLineaInterventVO>(
							vo));
			if (lineeVO != null && lineeVO.length > 0) {
				esito.setEsito(false);
				esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				return esito;
			}

			beanUtil.valueCopy(dettaglioLineaDiIntervento, vo);
			beanUtil.valueCopy(dettaglioLineaDiIntervento, vo,
					new HashMap<String, String>() {
						{
							put("mesiDurataDtConcessione",
									"mesiDurataDaDtConcessione");
						}
					});
			
			// CDU-013-V08: inizio
			// Su db flag_sif vale solo null o 'S'.
			if ("N".equals(vo.getFlagSif()))
				vo.setFlagSif(null);
			// Se livello istituzionale <> da 2 e da 3, flag_fondo_di_fondi non va valorizzato.
			if (vo.getIdLivelloIstituzione() == null ||
				(vo.getIdLivelloIstituzione().intValue() != 2 &&
				 vo.getIdLivelloIstituzione().intValue() != 3))
				vo.setFlagFondoDiFondi(null);
			// CDU-013-V08: fine

			try {
				vo.setIdUtenteIns(new BigDecimal(idUtente));
				vo = genericDAO.insert(vo);
				
				esito.setEsito(true);
				esito.setIdAssociazione(NumberUtil.toLong(vo
						.getProgrBandoLineaIntervento()));
				esito
						.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}
			
			// CDU-13 V05: 2 insert su PBANDI_W_CSP_COSTANTI.
			PbandiWCspCostantiVO vo1 = new PbandiWCspCostantiVO(vo.getProgrBandoLineaIntervento(),"STATO_PROGETTO","1");
			PbandiWCspCostantiVO vo2 = new PbandiWCspCostantiVO(vo.getProgrBandoLineaIntervento(),"STATO_DOMANDA","2");
			try {
				genericDAO.insert(vo1);
				genericDAO.insert(vo2);
			} catch (Exception e) {
				logger.error("Errore durante la insert su PBANDI_W_CSP_COSTANTI. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}
			// CDU-13 V05 fine
			
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}

	public BandoLineaAssociatoDTO[] findLineeDiInterventoAssociate(
			Long idUtente, String identitaDigitale, Long idBando)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			BandoLineaVO vo = new BandoLineaVO();
			vo.setIdBando(new BigDecimal(idBando));
			List<BandoLineaVO> lineeVO = genericDAO.findListWhere(vo);
			BandoLineaAssociatoDTO[] lineeDTO = new BandoLineaAssociatoDTO[lineeVO
					.size()];
			beanUtil.valueCopy(lineeVO.toArray(), lineeDTO);
			return lineeDTO;
		 
	}

	public GestioneBackOfficeEsitoGenerico eliminaBandoLineaAssociato(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
			PbandiRBandoLineaInterventVO vo = new PbandiRBandoLineaInterventVO(
					new BigDecimal(progrBandoLineaIntervento));
			try {
				
				// CDU-13 V05: delete su PBANDI_W_CSP_COSTANTI dei 2 record associati ad un progrBandoLineaIntervento.
				PbandiWCspCostantiVO vo1 = new PbandiWCspCostantiVO(vo.getProgrBandoLineaIntervento(),"STATO_PROGETTO","1");				
				if (!genericDAO.delete(vo1))
					throw new Exception();				
				// CDU-13 V05 fine
				
				esito.setEsito(genericDAO.delete(vo));
				if (!esito.getEsito()) {
					throw new Exception();
				}				
				
				esito
						.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error(
						"Errore durante la cancellazione del bando-linea (ID = "
								+ progrBandoLineaIntervento + ")", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}
		return esito;
	}

	// CDU-014-V08: aggiunto tipoProgrammazione
	public EsitoAssociazione associaEnteDiCompetenza(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long idEnte, Long idRuoloEnte,Long idSettoreEnte, String pec, String tipoProgrammazione) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idEnte", "idRuoloEnte", "tipoProgrammazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento, idEnte,
					idRuoloEnte, tipoProgrammazione);

			logger.info("associaEnteDiCompetenza(): progrBandoLineaIntervento = "+progrBandoLineaIntervento+"; idEnte = "+idEnte+"; idRuoloEnte = "+idRuoloEnte+"; tipoProgrammazione = "+tipoProgrammazione);						 
			
			PbandiRBandoLineaEnteCompVO vo = new PbandiRBandoLineaEnteCompVO();
			vo.setIdEnteCompetenza(BigDecimal.valueOf(idEnte));
			vo.setIdRuoloEnteCompetenza(BigDecimal.valueOf(idRuoloEnte));
			vo.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			vo.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			vo.setIndirizzoMailPec(pec);

			try {
				// Inserisce in PbandiRBandoLineaEnteComp
				vo = genericDAO.insert(vo);
				
				if(idSettoreEnte!=null){
					logger.info("\n\n\n\n\n\n\ninserting PbandiRBandoLineaSettoreVO for idSettoreEnte: "+idSettoreEnte
							+" , vo.getProgrBandoLineaEnteComp() "+vo.getProgrBandoLineaEnteComp());
					PbandiRBandoLineaSettoreVO bandoLineaSettore = new PbandiRBandoLineaSettoreVO();
					bandoLineaSettore.setIdSettoreEnte(BigDecimal.valueOf(idSettoreEnte));
					bandoLineaSettore.setIdRuoloEnteCompetenza(BigDecimal.valueOf(idRuoloEnte));
					bandoLineaSettore.setProgrBandoLineaIntervento(vo.getProgrBandoLineaIntervento());
					bandoLineaSettore.setIdUtenteIns(BigDecimal.valueOf(idUtente));
					bandoLineaSettore = genericDAO.insert(bandoLineaSettore);
					logger.info("just inserted bandoLineaSettore.getProgrBandoLineaSettore() : "+bandoLineaSettore.getProgrBandoLineaSettore());
				}
				
				// Verifica se bisogna generare la parola chiave Acta.
				String parolaChiaveActa = null;
				Long idEnteCompetenzaFindom = new Long(2);
				Long idRuoloEnteCompetenzaDestinatarioComunicaz = new Long(1);
				Long idRuoloEnteCompetenzaGestoreDomanda = new Long(12);
				if (!idEnteCompetenzaFindom.equals(idEnte) &&
					idRuoloEnteCompetenzaDestinatarioComunicaz.equals(idRuoloEnte)) {
					
					// Leggo da BandoLineaEnteCompAttivi il campo entesettore.
					BandoLineaEnteCompAttiviVO ente = new BandoLineaEnteCompAttiviVO();
					ente.setProgrBandoLineaEnteComp(vo.getProgrBandoLineaEnteComp());
					List<BandoLineaEnteCompAttiviVO> lista = genericDAO.findListWhere(ente);	
					
					if (lista.isEmpty() || lista.get(0) == null) {
						String msg = "Record in PBANDI_V_BANDO_LINEA_ENTE_COMP non trovato";
						logger.error(msg);
						throw new Exception(msg);
					} else {					
						// Genero la parola chiave da visualizzare a video.
						String enteSettore = lista.get(0).getDescEnteSettore();
						parolaChiaveActa = "PBANDIPBL"+progrBandoLineaIntervento+enteSettore;					
						logger.info("associaEnteDiCompetenza(): generata la parola chiave "+parolaChiaveActa);
					}
					
				} 
				
				// CDU-014-V08 inizio
				if (!idEnteCompetenzaFindom.equals(idEnte) &&
						   idRuoloEnteCompetenzaGestoreDomanda.equals(idRuoloEnte) &&
						   it.csi.pbandi.pbweb.pbandisrv.util.Constants.PROGRAMMAZIONE_2016.equals(tipoProgrammazione)) {
					
					// Leggo da BandoLineaEnteCompAttivi il campo entesettore.
					BandoLineaEnteCompAttiviVO ente = new BandoLineaEnteCompAttiviVO();
					ente.setProgrBandoLineaEnteComp(vo.getProgrBandoLineaEnteComp());
					List<BandoLineaEnteCompAttiviVO> lista = genericDAO.findListWhere(ente);	
					
					if (lista.isEmpty() || lista.get(0) == null) {
						String msg = "Record in PBANDI_V_BANDO_LINEA_ENTE_COMP non trovato";
						logger.error(msg);
						throw new Exception(msg);
					} else {					
						// Genero la parola chiave da visualizzare a video.
						String enteSettore = lista.get(0).getDescEnteSettore();
						parolaChiaveActa = "FINDOMPBL"+progrBandoLineaIntervento+enteSettore;					
						logger.info("associaEnteDiCompetenza(): generata la parola chiave "+parolaChiaveActa);						
					}
				}
				// CDU-014-V08 fine
				
				// Salva l'eventuale parola chiave acta.
				if (parolaChiaveActa == null) {
					logger.info("associaEnteDiCompetenza(): il bando linea NON prevede la parola chiave Acta.");
				} else {
					PbandiRBandoLineaEnteCompVO vo1 = new PbandiRBandoLineaEnteCompVO();
					vo1.setProgrBandoLineaEnteComp(vo.getProgrBandoLineaEnteComp());
					vo1.setIdUtenteAgg(vo.getIdUtenteIns());
					vo1.setParolaChiave(parolaChiaveActa);
					genericDAO.update(vo1);
					logger.info("associaEnteDiCompetenza(): parola chiave inserita in PbandiRBandoLineaEnteComp"); 
				}
				
				esito.setEsito(true);
				esito.setIdAssociazione(NumberUtil.toLong(vo.getProgrBandoLineaEnteComp()));
				esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaEnteDiCompetenzaAssociato(
			Long idUtente, String identitaDigitale, Long progrBandoLineaEnteComp)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		ArrayList<String> ruoliEnteCompetenzaObbligatori = new ArrayList<String>() {
			{
				add(Constants.RUOLO_ENTE_COMPETENZA_DESTINATARIO);
				add(Constants.RUOLO_ENTE_COMPETENZA_PROGRAMMATORE);
				add(Constants.RUOLO_ENTE_COMPETENZA_RESPONSABILE);
			}
		};

			PbandiRBandoLineaEnteCompVO pbandiRBandoLineaEnteCompVO = new PbandiRBandoLineaEnteCompVO(
					new BigDecimal(progrBandoLineaEnteComp));
			try {
				pbandiRBandoLineaEnteCompVO = genericDAO.where(pbandiRBandoLineaEnteCompVO).selectSingle();
				
				SettoreEnteBandoRuoloVO settoreEnteBandoRuoloVO = new SettoreEnteBandoRuoloVO();
				settoreEnteBandoRuoloVO.setProgrBandoLineaEnteComp(pbandiRBandoLineaEnteCompVO.getProgrBandoLineaEnteComp());
				settoreEnteBandoRuoloVO = genericDAO.findSingleOrNoneWhere(settoreEnteBandoRuoloVO);
				if(settoreEnteBandoRuoloVO!=null){
					PbandiRBandoLineaSettoreVO pbandiRBandoLineaSettoreVO=new PbandiRBandoLineaSettoreVO(settoreEnteBandoRuoloVO.getProgrBandoLineaSettore());
					genericDAO.delete(pbandiRBandoLineaSettoreVO);
				}
				DecodificaDTO ruoloEnte = decodificheManager.findDecodifica(
						GestioneDatiDiDominioSrv.RUOLO_ENTE_COMPETENZA,
						beanUtil.transform(pbandiRBandoLineaEnteCompVO.getIdRuoloEnteCompetenza(),
								Long.class));
				esito.setEsito(genericDAO.delete(pbandiRBandoLineaEnteCompVO));
				
				
				
				/*
				PbandiRBandoLineaSettoreVO pbandiRBandoLineaSettoreVO = new PbandiRBandoLineaSettoreVO();
				pbandiRBandoLineaSettoreVO.
				pbandiRBandoLineaSettoreVO.setIdSettoreEnte(null);
				genericDAO.delete(pbandiRBandoLineaSettoreVO);
				*/
				if (!esito.getEsito()) {
					throw new Exception();
				}
				if (ruoliEnteCompetenzaObbligatori.contains(ruoloEnte
						.getDescrizioneBreve())) {
				esito
							.setMessage(MessaggiConstants.BKO_RIMOSSO_ENTE_COMPETENZA_OBBLIGATORIO);
				} else {
					esito
						.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
				}
			} catch (Exception e) {
				logger.error("Errore durante la cancellazione dell'ente (ID = "
						+ progrBandoLineaEnteComp + ")", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}
		return esito;
	}

	public EnteDiCompetenzaRuoloAssociatoDTO[] findEntiDiCompetenzaAssociati(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento);

			EnteDiCompetenzaAssociataVO vo = new EnteDiCompetenzaAssociataVO();
			vo.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			vo.setAscendentOrder("descEnte");
			List<EnteDiCompetenzaAssociataVO> entiVO = genericDAO.findListWhere(vo);
			EnteDiCompetenzaRuoloAssociatoDTO[] entiDTO = beanUtil.transform(entiVO, EnteDiCompetenzaRuoloAssociatoDTO.class);
			return entiDTO;
	}

	public DettaglioBandoLinea findDettaglioBandoLinea(Long idUtente,
			String identitaDigitale, Long idBando, Long idBandoLinea)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
			BandoLineaVO vo = new BandoLineaVO();
			vo.setIdBando(new BigDecimal(idBando));
			vo.setProgrBandoLineaIntervento(new BigDecimal(idBandoLinea));

			return beanUtil.transform(genericDAO.findSingleWhere(vo),
					DettaglioBandoLinea.class);
	}

	public EnteDiCompetenzaDTO[] findEntiDiCompetenza(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			/*EnteDiCompetenzaVO[] entiVO = genericDAO
					.findAll(EnteDiCompetenzaVO.class);*/
          
			EnteDiCompetenzaVO filter=new EnteDiCompetenzaVO();
			filter.setAscendentOrder("descEnteDirezione");
			List <EnteDiCompetenzaVO>entiVO = genericDAO.findListWhere(Condition.filterBy(filter));
			
			//EnteDiCompetenzaDTO[] entiDTO = new EnteDiCompetenzaDTO[entiVO.size()];

			Map<String, String> map = new HashMap<String, String>();
			map.put("descEnteDirezione", "descEnte");
			map.put("idEnteCompetenza", "idEnteCompetenza");

			EnteDiCompetenzaDTO[] entiDTO  = beanUtil.transform(entiVO, EnteDiCompetenzaDTO.class, map);
			return entiDTO;
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile caricare gli enti di competenza.", e);
		} 
	}

	public EsitoAssociazione associaAreaScientifica(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			AreaScientificaDTO areaScientifica) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			EsitoAssociazione esito = new EsitoAssociazione();
			PbandiDAreaScientificaVO vo = new PbandiDAreaScientificaVO(
					new BigDecimal(areaScientifica.getId()));
			List<PbandiDAreaScientificaVO> esistente = genericDAO
					.findListWhere(new AndCondition<PbandiDAreaScientificaVO>(
							new FilterCondition<PbandiDAreaScientificaVO>(vo),
							new NullCondition<PbandiDAreaScientificaVO>(
									PbandiDAreaScientificaVO.class,
									"dtFineValidita")));
			if (esistente.isEmpty()) {
				// l'area scientifica è stata creata ora
				vo = new PbandiDAreaScientificaVO();
				Map<String, String> map = new HashMap<String, String>();
				map.put("descBreve", "codAreaScientifica");
				map.put("descEstesa", "descAreaScientifica");
				try {
					beanUtil.valueCopy(areaScientifica, vo, map);
					vo = genericDAO.insert(vo);
				} catch (Exception e) {
					throw new GestioneBackOfficeException(
							"Errore durante la creazione dell'area scientifica (id="
									+ areaScientifica.getId() + "descBreve="
									+ areaScientifica.getDescBreve()
									+ "descEstesa="
									+ areaScientifica.getDescEstesa() + ").", e);
				}
			} else {
				// esiste ed è unica l'area scientifica
				vo = esistente.get(0);
			}
			if (vo.getIdAreaScientifica().intValue() < 0) {
				esito.setEsito(false);
				esito.setIdAssociazione(NumberUtil.toLong(vo
						.getIdAreaScientifica()));
				esito.setMessage(ERRORE_SERVER);
			} else {
				PbandiRBandoLineaInterventVO bandoLineaVO = new PbandiRBandoLineaInterventVO(
						new BigDecimal(progrBandoLineaIntervento));
				bandoLineaVO.setIdAreaScientifica(vo.getIdAreaScientifica());
				bandoLineaVO.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(bandoLineaVO);
				esito.setEsito(true);
				esito.setIdAssociazione(NumberUtil.toLong(vo
						.getIdAreaScientifica()));
				esito
						.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			}

			return esito;
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'update.", e);
		}  
	}

	public AreaScientificaDTO[] findAreeScientificheAssociate(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento);

		BandoLineaVO vo = new BandoLineaVO();
		vo.setProgrBandoLineaIntervento(new BigDecimal(
				progrBandoLineaIntervento));
		List<BandoLineaVO> areeVO = genericDAO.findListWhere(vo);
		List<AreaScientificaDTO> areeDTO = new ArrayList<AreaScientificaDTO>();
		for (BandoLineaVO area : areeVO) {
			if (area.getIdAreaScientifica() != null) {
				AreaScientificaDTO temp = new AreaScientificaDTO();
				temp.setId(NumberUtil.toLong(area.getIdAreaScientifica()));
				temp.setDescEstesa(area.getDescAreaScientifica());
				temp.setDescBreve("Area " + area.getCodAreaScientifica());
				areeDTO.add(temp);
			}
		}
		if (areeDTO.size() > 1) {
			throw new UnrecoverableException(
					"Si aspettava un risultato unico, trovati "
							+ areeDTO.size() + " elementi.");
		}

		AreaScientificaDTO[] result = new AreaScientificaDTO[areeDTO.size()];
		return areeDTO.toArray(result);
	}

	public IdDescBreveDescEstesaDTO[] findRegole(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			PbandiCRegolaVO[] vo = genericDAO.findAll(PbandiCRegolaVO.class);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo.length];
			Map<String, String> map = new HashMap<String, String>();
			map.put("descBreveRegola", "descBreve");
			map.put("descRegola", "descEstesa");
			map.put("idRegola", "id");
			beanUtil.valueCopy(vo, dto, map);
			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		} 
	}

	public RegolaAssociataDTO[] findRegoleAssociate(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento);

			RegolaAssociataAnagraficaVO regolaAssociataAnagraficaVO = new RegolaAssociataAnagraficaVO();
			regolaAssociataAnagraficaVO
					.setProgrBandoLineaIntervento(new BigDecimal(
							progrBandoLineaIntervento));
			RegolaAssociataAnagraficaVO[] regoleAnagrafica = genericDAO
					.findWhere(regolaAssociataAnagraficaVO);
			RegolaAssociataBandoLineaVO vo = new RegolaAssociataBandoLineaVO();
			vo.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			List<RegolaAssociataBandoLineaVO> regoleAssociate = genericDAO
					.findListWhere(vo);

			RegolaAssociataDTO[] dtoAssociate = new RegolaAssociataDTO[regoleAssociate
					.size()];
			beanUtil.valueCopy(regoleAssociate.toArray(), dtoAssociate);
			RegolaAssociataDTO[] dtoAnagrafica = new RegolaAssociataDTO[regoleAnagrafica.length];
			beanUtil.valueCopy(regoleAnagrafica, dtoAnagrafica);

			List<RegolaAssociataDTO> temp = new ArrayList<RegolaAssociataDTO>(
					dtoAssociate.length + dtoAnagrafica.length);
			temp.addAll(Arrays.asList(dtoAssociate));
			temp.addAll(Arrays.asList(dtoAnagrafica));

			RegolaAssociataDTO[] dtoResult = (RegolaAssociataDTO[]) temp
					.toArray(new RegolaAssociataDTO[temp.size()]);
			return dtoResult;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public EsitoAssociazione associaRegola(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long idRegola, Long idTipoAnagrafica, Long idTipoBeneficiario)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento", "idRegola" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento, idRegola);
		try {
			EsitoAssociazione esito = new EsitoAssociazione();

			esito.setEsito(false);

			// controllo che non esista già l'associazione regola-bandolinea
			PbandiRRegolaBandoLineaVO regolaBandoLineaVO = new PbandiRRegolaBandoLineaVO();
			regolaBandoLineaVO.setIdRegola(new BigDecimal(idRegola));
			regolaBandoLineaVO.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			if (genericDAO.findListWhere(regolaBandoLineaVO).isEmpty()) {
				// non esiste ancora l'associazione
				genericDAO.insert(regolaBandoLineaVO);
				esito.setEsito(true);
			}
			// CDU-14 V04: commento l'inserimento tipo anagrafica
			/*
			// verifico se sto associando la regola a un tipo anagrafica
			if (idTipoAnagrafica != null) {
				// controllo che non esista già l'associazione
				// regola-anagrafica-bandolinea
				PbandiRRegolaTipoAnagVO regolaTipoAnagVO = new PbandiRRegolaTipoAnagVO();
				regolaTipoAnagVO.setIdRegola(new BigDecimal(idRegola));
				regolaTipoAnagVO.setProgrBandoLineaIntervento(new BigDecimal(
						progrBandoLineaIntervento));
				regolaTipoAnagVO.setIdTipoAnagrafica(new BigDecimal(
						idTipoAnagrafica));
				if (idTipoBeneficiario != null) {
					regolaTipoAnagVO.setIdTipoBeneficiario(new BigDecimal(
							idTipoBeneficiario));
				}
				if (genericDAO.findListWhere(regolaTipoAnagVO).isEmpty()) {
					// non esiste ancora l'associazione
					genericDAO.insert(regolaTipoAnagVO);
					esito.setEsito(true);
				}
			}
			*/
			// CDU-14 V04: fine
			esito.setIdAssociazione(idRegola);
			if (esito.getEsito()) {
				// almeno un inserimento è stato fatto
				esito
						.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} else {
				// non si è passati attraverso nessun controllo di non esistenza
				// di associazione
				esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
			}
			return esito;
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'update.", e);
		} 
	}

	public IdDescBreveDescEstesaDTO[] findAreeScientifiche(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			PbandiDAreaScientificaVO[] vo = genericDAO
					.findAll(PbandiDAreaScientificaVO.class);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo.length];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descAreaScientifica", "descEstesa");
			map.put("codAreaScientifica", "descBreve");
			map.put("idAreaScientifica", "id");

			beanUtil.valueCopy(vo, dto, map);

			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesaDTO[] findModalitaDiAgevolazioneAssociate(
			Long idUtente, String identitaDigitale, Long idBando)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			ModalitaDiAgevolazioneBandoVO query = new ModalitaDiAgevolazioneBandoVO();
			query.setIdBando(new BigDecimal(idBando));
			List<ModalitaDiAgevolazioneBandoVO> modalita = genericDAO
					.findListWhere(query);
			IdDescBreveDescEstesaDTO[] result = new IdDescBreveDescEstesaDTO[modalita
					.size()];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("modalitaAgevolazioneComposta", "descEstesa");
			map.put("modalita", "descBreve");
			map.put("idModalitaAgevolazione", "id");
			beanUtil.valueCopy(modalita.toArray(), result, map);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public VoceDiSpesaAssociataDTO[] findVociDiSpesa(Long idUtente,
			String identitaDigitale, Long idVoceDiSpesaPadre, Long idBando)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			logger.info("Parametri: idVoceDiSpesaPadre = "+idVoceDiSpesaPadre+"; idBando = "+idBando);
			VoceDiSpesaAssociataDTO[] result = null;	
			if (idBando == null) {
				// Caso normale.
				List<PbandiDVoceDiSpesaVO> voci = null;
				Condition<PbandiDVoceDiSpesaVO> condition;
				ArrayList<String> orderArray = new ArrayList<String>() {
					{
						add("descVoceDiSpesa");
					}
				};
				PbandiDVoceDiSpesaVO vo = new PbandiDVoceDiSpesaVO();
				vo.setOrderPropertyNamesList(orderArray);
				vo.setAscendentOrder(true);
				if (idVoceDiSpesaPadre == null) {
					condition = new AndCondition<PbandiDVoceDiSpesaVO>(Condition
							.filterBy(vo), Condition.isFieldNull(
							PbandiDVoceDiSpesaVO.class, "idVoceDiSpesaPadre"));
				} else {
					vo.setIdVoceDiSpesaPadre(new BigDecimal(idVoceDiSpesaPadre));
					condition = Condition.filterBy(vo);
				}
				voci = genericDAO.where(
						condition.and(Condition
								.validOnly(PbandiDVoceDiSpesaVO.class))).select();

				result =  beanUtil.transform(voci, VoceDiSpesaAssociataDTO.class);
			} else {
				// CDU-13 V07: inizio
				// Vanno estratte solo le voci di spesa associate ad interventi a loro volta associati ad un bando.
				VoceDiSpesaInterventoVO vo = new VoceDiSpesaInterventoVO();
				vo.setIdBando(new BigDecimal(idBando));
				List<VoceDiSpesaInterventoVO> voci = genericDAO.findListWhere(vo);
				result = beanUtil.transform(voci, VoceDiSpesaAssociataDTO.class);
				// CDU-13 V07: fine
			}
			
			return result;
			
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesa2DTO[] findVociDiSpesaAssociate(Long idUtente,
			String identitaDigitale, Long idBando) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			VoceDiSpesaAssociataVO query = new VoceDiSpesaAssociataVO();
			query.setIdBando(new BigDecimal(idBando));
			query.setAscendentOrder("descVoceDiSpesaComposta");

			// List<VoceDiSpesaAssociataVO> voci =
			// genericDAO.findListWhere(query);
			List<VoceDiSpesaAssociataVO> voci = voceDiSpesaManager
					.findVociDiSpesaAssociate(query);
			IdDescBreveDescEstesa2DTO[] result = new IdDescBreveDescEstesa2DTO[voci
					.size()];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descVoceDiSpesaComposta", "descEstesa");
			map.put("codTipoVoceDiSpesa", "descBreve");
			map.put("idVoceDiSpesa", "id");
			map.put("livello", "info");
			// Cultura.
			map.put("descTipologiaVoceDiSpesa", "info2");
			map.put("flagEdit", "info3");
			beanUtil.valueCopy(voci.toArray(), result, map);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	// Cultura: aggiunto minimoImportoAgevolato.
	public EsitoAssociazione associaModalitaDiAgevolazione(Long idUtente,
			String identitaDigitale, Long idBando,
			Long idModalitaDiAgevolazione, Double importoAgevolato,
			Double massimoImportoAgevolato, String flagLiquidazione, Double minimoImportoAgevolato) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando", "idModalitaDiAgevolazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando, idModalitaDiAgevolazione);

			PbandiRBandoModalitaAgevolVO vo = new PbandiRBandoModalitaAgevolVO();

			try {
				vo.setIdModalitaAgevolazione(new BigDecimal(
						idModalitaDiAgevolazione));
				vo.setIdBando(new BigDecimal(idBando));
				List<PbandiRBandoModalitaAgevolVO> associazioneEsistente = genericDAO
						.findListWhere(vo);
				if (associazioneEsistente.isEmpty()) {
					

					// Se flagLiquidazione = S, metto a N tutte le altre modalit� del bando.
					if ("S".equalsIgnoreCase(flagLiquidazione)) {
						PbandiRBandoModalitaAgevolVO filtro = new PbandiRBandoModalitaAgevolVO();
						filtro.setIdBando(new BigDecimal(idBando));
						List<PbandiRBandoModalitaAgevolVO> lista = genericDAO.findListWhere(filtro);
						for (PbandiRBandoModalitaAgevolVO item : lista) {
							item.setFlagLiquidazione("N");
							genericDAO.update(item);
						}
					}
					
					
					vo = new PbandiRBandoModalitaAgevolVO();
					vo.setIdModalitaAgevolazione(new BigDecimal(
							idModalitaDiAgevolazione));
					vo.setIdBando(new BigDecimal(idBando));
					vo
							.setMassimoImportoAgevolato(massimoImportoAgevolato == null ? null
									: new BigDecimal(massimoImportoAgevolato));
					vo
							.setPercentualeImportoAgevolato(importoAgevolato == null ? null
									: new BigDecimal(importoAgevolato));
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					vo.setFlagLiquidazione(flagLiquidazione);
					vo.setMinimoImportoAgevolato(minimoImportoAgevolato == null ? null
							: new BigDecimal(minimoImportoAgevolato));

					vo = genericDAO.insert(vo);
					esito.setEsito(true);
					esito
							.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setIdAssociazione(NumberUtil.toLong(vo
							.getIdModalitaAgevolazione()));
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaAreaScientificaAssociata(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoLineaInterventVO vo = new PbandiRBandoLineaInterventVO(
					new BigDecimal(progrBandoLineaIntervento));
			vo = genericDAO.findSingleWhere(vo);
			vo.setIdAreaScientifica(null);
			vo.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.updateNullables(vo);
			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la modifica del bando-linea (ID = "
					+ progrBandoLineaIntervento + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaModalitaDiAgevolazioneAssociata(
			Long idUtente, String identitaDigitale, Long idBando,
			Long idModalitaDiAgevolazione) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
			PbandiRBandoModalitaAgevolVO vo = new PbandiRBandoModalitaAgevolVO(
					new BigDecimal(idModalitaDiAgevolazione), new BigDecimal(
							idBando));
			try {
				esito.setEsito(genericDAO.delete(vo));
				if (!esito.getEsito()) {
					throw new Exception();
				}
				esito
						.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error(
						"Errore durante la cancellazione della modalità (ID = "
								+ idModalitaDiAgevolazione
								+ ") per il bando (ID = " + idBando + ")", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaRegolaAssociata(
			Long idUtente, String identitaDigitale, Long idRegola,
			Long progrBandoLineaIntervento, Long idTipoAnagrafica,
			Long idTipoBeneficiario) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idRegola", "progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idRegola, progrBandoLineaIntervento);

			if (idTipoAnagrafica == null) {
				// devo rimuovere un associazione con un bando-linea
				// la regola non deve essere ancora associata a tipo anagrafica
				PbandiRRegolaTipoAnagVO check = new PbandiRRegolaTipoAnagVO();
				check.setIdRegola(new BigDecimal(idRegola));
				check.setProgrBandoLineaIntervento(new BigDecimal(
						progrBandoLineaIntervento));
				if (genericDAO.findListWhere(check).isEmpty()) {
					PbandiRRegolaBandoLineaVO vo = new PbandiRRegolaBandoLineaVO();
					vo.setProgrBandoLineaIntervento(new BigDecimal(
							progrBandoLineaIntervento));
					vo.setIdRegola(new BigDecimal(idRegola));
					esito.setEsito(genericDAO.delete(vo));
					if (esito.getEsito()) {
						esito
								.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
					} else {
						esito.setMessage(ERRORE_SERVER);
					}
				} else {
					esito.setEsito(false);
					esito
							.setMessage(ERRORE_REGOLA_ANCORA_ASSOCIATA_A_TIPO_ANAGRAFICA);
				}
			} else {
				// devo rimuovere un associazione con un tipo anagrafica
				PbandiRRegolaTipoAnagVO vo = new PbandiRRegolaTipoAnagVO();
				vo.setIdRegola(new BigDecimal(idRegola));
				vo.setIdTipoAnagrafica(new BigDecimal(idTipoAnagrafica));
				if (idTipoBeneficiario != null) {
					vo
							.setIdTipoBeneficiario(new BigDecimal(
									idTipoBeneficiario));
				}
				vo.setProgrBandoLineaIntervento(new BigDecimal(
						progrBandoLineaIntervento));
				esito.setEsito(genericDAO.delete(vo));
				if (esito.getEsito()) {
					esito
							.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
				} else {
					esito.setMessage(ERRORE_SERVER);
				}
			}
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la modifica.", e);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaVoceDiSpesaAssociata(
			Long idUtente, String identitaDigitale, Long idBando,
			Long idVoceDiSpesa) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			// primissima cosa da fare: controllare che la vds non sia presente
			// su un rigo di conto
			if (isVoceDiSpesaAssociataAContoEconomico(idVoceDiSpesa, idBando)) {
				logger
						.warn("Qualcuno ha cercato di disassociare la voce di spesa "
								+ idVoceDiSpesa
								+ ", ma è già associato a un conto economico per il bando "
								+ idBando + ".");
				esito.setEsito(false);
				esito
						.setMessage(ERRORE_VOCE_DI_SPESA_GIA_PRESENTE_SU_RIGO_CONTO_ECONOMICO);
			} else {
				// Verifico che non stia cercando di eliminare un padre con
				// figli
				VociDiSpesaConIdVoceDiSpesaPadreVO checkPadreVO = new VociDiSpesaConIdVoceDiSpesaPadreVO();
				checkPadreVO.setIdBando(new BigDecimal(idBando));
				checkPadreVO
						.setIdVoceDiSpesaPadre(new BigDecimal(idVoceDiSpesa));
				if (genericDAO.findListWhere(checkPadreVO).isEmpty()) {
					PbandiRBandoVoceSpesaVO vo = new PbandiRBandoVoceSpesaVO(
							new BigDecimal(idVoceDiSpesa), new BigDecimal(
									idBando));
					esito.setEsito(genericDAO.delete(vo));
					if (!esito.getEsito()) {
						throw new Exception();
					}
					esito
							.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ERRORE_MACRO_VOCE_CON_MICRO_VOCI);
				}

			}
		} catch (Exception e) {
			logger.error(
					"Errore durante la cancellazione della voce di spesa (ID = "
							+ idVoceDiSpesa + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public IdDescBreveDescEstesaDTO[] findTipiAnagrafica(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			PbandiDTipoAnagraficaVO[] vo = genericDAO
					.findAll(PbandiDTipoAnagraficaVO.class);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo.length];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descTipoAnagrafica", "descEstesa");
			map.put("descBreveTipoAnagrafica", "descBreve");
			map.put("idTipoAnagrafica", "id");

			beanUtil.valueCopy(vo, dto, map);

			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public EsitoAssociazione associaVoceDiSpesa(Long idUtente,
			String identitaDigitale, Long idBando,
			VoceDiSpesaAssociataDTO voceDiSpesa,
			VoceDiSpesaAssociataDTO voceDiSpesaPadre) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBando",
				"voceDiSpesa" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idBando, voceDiSpesa);
		try {
			
			// Nota: 
			//  - se a video è stata selezionata solo la voce di spesa, voceDiSpesaPadre è nullo.
			//  - se è stata selezionata anche la sottovoce, voceDiSpesa contiene la sottovoce, voceDiSpesaPadre la voce.
			//    - se la sottovoce è stata appena inserita a video (e non ancora su db), 
			//      in input voceDiSpesa contiene idVoceDiSpesa=-1 e idVoceDiSpesaPadre=null
			
			if (voceDiSpesa == null)
				logger.info("Param input voceDiSpesa nullo");
			else
				logger.info("Param input voceDiSpesa: "+voceDiSpesa.toString());
			if (voceDiSpesaPadre == null)
				logger.info("Param input voceDiSpesaPadre nullo");
			else
				logger.info("Param input voceDiSpesaPadre: "+voceDiSpesaPadre.toString());
			
			BigDecimal idVoceDiSpesaPadre = null;
			if (voceDiSpesaPadre != null
					&& voceDiSpesaPadre.getIdVoceDiSpesa() != null) {
				// controllo che non sia la prima sottovoce di spesa di una voce
				// già usata
				// e che la vds padre non sia già presente su un conto economico
				VociDiSpesaConIdVoceDiSpesaPadreVO checkVO = new VociDiSpesaConIdVoceDiSpesaPadreVO();
				checkVO.setIdBando(new BigDecimal(idBando));
				checkVO.setIdVoceDiSpesaPadre(new BigDecimal(voceDiSpesaPadre
						.getIdVoceDiSpesa()));
				if (genericDAO.findListWhere(checkVO).isEmpty()
						&& isVoceDiSpesaAssociataAContoEconomico(
								voceDiSpesaPadre.getIdVoceDiSpesa(), idBando)) {
					esito.setEsito(false);
					esito
							.setIdAssociazione(voceDiSpesaPadre
									.getIdVoceDiSpesa());
					esito.setMessage(ERRORE_MACRO_VOCE_GIA_SU_CONTO_ECONOMICO);
					return esito;
				}
				PbandiDVoceDiSpesaVO voPadre = new PbandiDVoceDiSpesaVO(
						new BigDecimal(voceDiSpesaPadre.getIdVoceDiSpesa()));
				List<PbandiDVoceDiSpesaVO> esistente = genericDAO
						.findListWhere(voPadre);
				if (esistente.isEmpty()) {
					// il padre è stato creato ora
					voPadre = new PbandiDVoceDiSpesaVO();
					try {
						beanUtil.valueCopy(voceDiSpesaPadre, voPadre);
						voPadre.setIdVoceDiSpesa(null);
						voPadre.setIdVoceDiSpesaPadre(null);
						voPadre = genericDAO.insert(voPadre);
					} catch (Exception e) {
						throw new GestioneBackOfficeException(
								"Errore durante la creazione della voce di spesa padre(id="
										+ voceDiSpesaPadre.getIdVoceDiSpesa()
										+ ", descrizione="
										+ voceDiSpesaPadre.getDescVoceDiSpesa()
										+ ").", e);
					}
				} else {
					voPadre = esistente.get(0);
				}
				// in ogni caso ora ho un vo esistente
				idVoceDiSpesaPadre = voPadre.getIdVoceDiSpesa();
			}
			// ora controllo la voce di spesa
			PbandiDVoceDiSpesaVO vo = new PbandiDVoceDiSpesaVO(new BigDecimal(
					voceDiSpesa.getIdVoceDiSpesa()));
			List<PbandiDVoceDiSpesaVO> esistente = genericDAO.findListWhere(vo);
			if (esistente.isEmpty()) {
				// la voce di spesa è stata creata ora
				vo = new PbandiDVoceDiSpesaVO();
				try {
					beanUtil.valueCopy(voceDiSpesa, vo);
					vo.setIdVoceDiSpesa(null);
					vo.setIdVoceDiSpesaPadre(idVoceDiSpesaPadre); // chiunque
					// esso sia,
					// anche
					// nullo
					vo = genericDAO.insert(vo);
				} catch (Exception e) {
					throw new GestioneBackOfficeException(
							"Errore durante la creazione della voce di spesa (id="
									+ voceDiSpesa.getIdVoceDiSpesa()
									+ ", descrizione="
									+ voceDiSpesa.getDescVoceDiSpesa()
									+ ", idPadre=" + idVoceDiSpesaPadre + ").",
							e);
				}
			} else {
				vo = esistente.get(0);
			}
			if (vo.getIdVoceDiSpesa().intValue() < 0) {
				esito.setEsito(false);
				esito.setIdAssociazione(NumberUtil
						.toLong(vo.getIdVoceDiSpesa()));
				esito.setMessage(ERRORE_SERVER);
			} else {
				PbandiRBandoVoceSpesaVO associazione = new PbandiRBandoVoceSpesaVO();
				associazione.setIdBando(new BigDecimal(idBando));
				associazione.setIdVoceDiSpesa(vo.getIdVoceDiSpesa());
				// verifico che l'associazione non esista già
				List<PbandiRBandoVoceSpesaVO> associazioneEsistente = genericDAO
						.findListWhere(associazione);
				if (associazioneEsistente.isEmpty()) {
					// verifico che il padre sia già stato associato
					if (idVoceDiSpesaPadre != null) {
						PbandiRBandoVoceSpesaVO padreVO = new PbandiRBandoVoceSpesaVO();
						padreVO.setIdVoceDiSpesa(idVoceDiSpesaPadre);
						padreVO.setIdBando(new BigDecimal(idBando));
						if (genericDAO.findListWhere(padreVO).isEmpty()) {
							// devo prima associare la macro-voce
							padreVO.setIdUtenteIns(new BigDecimal(idUtente));
							List<MaxProgrOrdinamentoVoceDiSpesaVO> maxProgrOrdinamentoVO = genericDAO
									.findListWhere(new MaxProgrOrdinamentoVoceDiSpesaVO(
											idBando));
							if (maxProgrOrdinamentoVO.size() == 0)
								padreVO.setProgrOrdinamento(new BigDecimal(1L));
							else
								padreVO.setProgrOrdinamento(new BigDecimal(
										maxProgrOrdinamentoVO.get(0)
												.getMaxProgrOrdinamento() + 1));
							genericDAO.insert(padreVO);
						}
					}
					associazione.setIdUtenteIns(new BigDecimal(idUtente));
					List<MaxProgrOrdinamentoVoceDiSpesaVO> maxProgrOrdinamentoVO = genericDAO
							.findListWhere(new MaxProgrOrdinamentoVoceDiSpesaVO(
									idBando));
					if (maxProgrOrdinamentoVO.size() == 0)
						associazione.setProgrOrdinamento(new BigDecimal(1L));
					else
						associazione.setProgrOrdinamento(new BigDecimal(
								maxProgrOrdinamentoVO.get(0)
										.getMaxProgrOrdinamento() + 1));
					genericDAO.insert(associazione);
					esito.setEsito(true);
					esito.setIdAssociazione(NumberUtil.toLong(vo
							.getIdVoceDiSpesa()));
					esito
							.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
					
					// CDU-13 V07: inizio
					/* commentato causa sospensione collegamento con findom.
					// Verifica che sia stata selezionata la sottovoce.
					if (idVoceDiSpesaPadre != null) {
					
						// voceDiSpesaPadre contiene la voce, voceDiSpesa la sottovoce
						BigDecimal idVoce = idVoceDiSpesaPadre;
						BigDecimal idSottovoce = vo.getIdVoceDiSpesa();
						logger.info("\nGestione sottovoce per FINDOM: idVoce = "+idVoce+"; idSottovoce = "+idSottovoce);
						
						// Verifica se l'idVoce è presente in PbandiDTipolInterventi.IdMacroVoce
						PbandiDTipolInterventiVO voTipol = new PbandiDTipolInterventiVO();
						voTipol.setIdMacroVoce(idVoce);
						List<PbandiDTipolInterventiVO> listaTipol = genericDAO.findListWhere(voTipol);
						if (listaTipol.size() > 0) {
							logger.info("\nVoce di Spesa "+idVoce+" presente in PbandiDTipolInterventi: inserisco la sottovoce in PbandiRTipolIntervVociSpe.");
							PbandiRTipolIntervVociSpeVO rTipol = new PbandiRTipolIntervVociSpeVO();
							rTipol.setIdVoceSpesa(idSottovoce);
							rTipol.setIdTipolIntervento(listaTipol.get(0).getIdTipolIntervento());
							genericDAO.insert(rTipol);
						} else {
							logger.info("\nVoce di Spesa "+idVoce+" NON presente in PbandiDTipolInterventi.");
							PbandiDDettTipolInterventiVO voDettTipol = new PbandiDDettTipolInterventiVO();
							voDettTipol.setIdMacroVoce(idVoce);
							List<PbandiDDettTipolInterventiVO> listaDettTipol = genericDAO.findListWhere(voDettTipol);
							if (listaDettTipol.size() > 0) {
								logger.info("\nVoce di Spesa "+idVoce+" presente in PbandiDDettTipolInterventi: inserisco la sottovoce in PbandiRDettTipolIntVocSp.");
								PbandiRDettTipolIntVocSpVO rDettTipol = new PbandiRDettTipolIntVocSpVO();
								rDettTipol.setIdVoceSpesa(idSottovoce);
								rDettTipol.setIdDettTipolIntervento(listaDettTipol.get(0).getIdDettTipolIntervento());
								genericDAO.insert(rDettTipol);
							} else {
								logger.info("\nVoce di Spesa "+idVoce+" NON presente in PbandiDDettTipolInterventi.");
							}
						}
					} 
					*/
					// CDU-13 V07: fine
					
				} else {
					esito.setEsito(false);
					esito.setIdAssociazione(NumberUtil.toLong(vo
							.getIdVoceDiSpesa()));
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			}
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'update.", e);
		}  
		return esito;
	}

	public EsitoAssociazione associaTipiDiAiuto(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long[] idTipiDiAiuto) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idTipiDiAiuto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento, idTipiDiAiuto);

			PbandiRBandoLinTipoAiutoVO vo = new PbandiRBandoLinTipoAiutoVO();
			vo.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));

			try {
				for (Long idTipoDiAiuto : idTipiDiAiuto) {
					vo.setIdTipoAiuto(new BigDecimal(idTipoDiAiuto));
					vo = genericDAO.insert(vo);
				}
				esito.setEsito(true);
				esito
						.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaTipiDiAiuto(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long[] idTipiDiAiuto) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idTipiDiAiuto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento, idTipiDiAiuto);

			PbandiRBandoLinTipoAiutoVO vo = new PbandiRBandoLinTipoAiutoVO();
			vo.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));

			for (Long idTipoDiAiuto : idTipiDiAiuto) {
				vo.setIdTipoAiuto(new BigDecimal(idTipoDiAiuto));
				Boolean check = genericDAO.delete(vo);
				if (!check) {
					throw new Exception();
				}
			}

			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
			return esito;

		} catch (Exception e) {
			logger.error("Errore durante la cancellazione. ", e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
	}

	public TipoDiAiutoAssociatoDTO[] findTipiDiAiutoAssociati(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento);

			BandoLineaTipoAiutoAssociatoVO query = new BandoLineaTipoAiutoAssociatoVO();
			query.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			List<BandoLineaTipoAiutoAssociatoVO> tipi = genericDAO
					.findListWhere(query);
			TipoDiAiutoAssociatoDTO[] result = new TipoDiAiutoAssociatoDTO[tipi
					.size()];

			beanUtil.valueCopy(tipi.toArray(), result);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesaDTO[] findSottoSettoriCIPE(Long idUtente,
			String identitaDigitale, Long idSettoreCIPEPadre)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			PbandiDSottoSettoreCipeVO query = new PbandiDSottoSettoreCipeVO();
			query.setIdSettoreCipe(new BigDecimal(idSettoreCIPEPadre));
			List<PbandiDSottoSettoreCipeVO> vo = genericDAO
					.findListWhere(query);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo
					.size()];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descSottoSettoreCipe", "descEstesa");
			map.put("codSottoSettoreCipe", "descBreve");
			map.put("idSottoSettoreCipe", "id");

			beanUtil.valueCopy(vo.toArray(), dto, map);

			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	// CDU-13-V08: aggiunto idNaturaCipe
	// La progr pre-2016 usa l'idTipoOperazione, la progr 2016: usa idNaturaCipe. 
	public IdDescBreveDescEstesaDTO[] findVociDiSpesaMonitoraggio(
			Long idUtente, String identitaDigitale, Long idTipoOperazione, Long idNaturaCipe)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			PbandiDVoceDiSpesaMonitVO query = new PbandiDVoceDiSpesaMonitVO();
			if (idTipoOperazione != null)
				query.setIdTipoOperazione(new BigDecimal(idTipoOperazione));
			if (idNaturaCipe != null)
				query.setIdNaturaCipe(new BigDecimal(idNaturaCipe));
			List<PbandiDVoceDiSpesaMonitVO> vo = genericDAO
					.findListWhere(query);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo
					.size()];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descVoceSpesaMonit", "descEstesa");
			map.put("codIgrueT28", "descBreve");
			map.put("idVoceDiSpesaMonit", "id");

			beanUtil.valueCopy(vo.toArray(), dto, map);

			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		} 
	}

	public EsitoAssociazione associaCausaleDiErogazione(Long idUtente,
			String identitaDigitale,
			CausaleDiErogazioneAssociataDTO causaleDiErogazione)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"causaleDiErogazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, causaleDiErogazione);

			PbandiRBandoCausaleErogazVO rbce = new PbandiRBandoCausaleErogazVO();
			beanUtil.valueCopy(causaleDiErogazione, rbce);
			rbce.setIdUtenteIns(new BigDecimal(idUtente));
			rbce = genericDAO.insert(rbce);
			if ((causaleDiErogazione.getIdTipologieDiAllegato() != null)
					&& (causaleDiErogazione.getIdTipologieDiAllegato().length > 0)) {
				List<PbandiRBandoCausErTipAllVO> rbceta = new ArrayList<PbandiRBandoCausErTipAllVO>();
				for (Long idTipologiaDiAllegato : causaleDiErogazione
						.getIdTipologieDiAllegato()) {
					PbandiRBandoCausErTipAllVO temp = new PbandiRBandoCausErTipAllVO();
					temp.setIdTipoAllegato(beanUtil.transform(
							idTipologiaDiAllegato, BigDecimal.class));
					temp.setIdCausaleErogazione(rbce.getIdCausaleErogazione());
					temp.setIdBando(rbce.getIdBando());
					temp.setIdUtenteIns(new BigDecimal(idUtente));
					rbceta.add(temp);
				}
				genericDAO.insert(rbceta);
			}

			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}

	public EsitoAssociazione associaSoggettoFinanziatore(Long idUtente,
			String identitaDigitale,
			SoggettoFinanziatoreAssociatoDTO soggettoFinanziatore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"soggettoFinanziatore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, soggettoFinanziatore);

			PbandiRBandoSoggFinanziatVO vo = new PbandiRBandoSoggFinanziatVO();

			try {
				beanUtil.valueCopy(soggettoFinanziatore, vo);
				vo.setPercentualeQuotaSoggFinanz(null);

				if (genericDAO.findListWhere(vo).isEmpty()) {
					beanUtil.valueCopy(soggettoFinanziatore, vo);
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					vo = genericDAO.insert(vo);
					esito.setEsito(true);
					esito
							.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}

	public EsitoAssociazione associaTipiDiTrattamento(Long idUtente,
			String identitaDigitale, Long idBando, Long[] idTipiDiTrattamento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idTipiDiTrattamento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idTipiDiTrattamento);

			try {
				// verifico se esiste almeno un tipo già associato
				PbandiRBandoTipoTrattamentVO vo = new PbandiRBandoTipoTrattamentVO();
				vo.setIdBando(new BigDecimal(idBando));
				List<PbandiRBandoTipoTrattamentVO> tipi = genericDAO
						.findListWhere(vo);
				boolean daImpostarePredefinito = false;
				if (tipi.isEmpty()) {
					daImpostarePredefinito = true;
				} else {
					// controllo che non esistano già associazioni (prima di
					// inserirne)
					for (Long id : idTipiDiTrattamento) {
						vo.setIdTipoTrattamento(new BigDecimal(id));
						if (!genericDAO.findListWhere(vo).isEmpty()) {
							esito.setEsito(false);
							esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
							return esito;
						}
					}
				}

				// sono certo che da qui non ci sono doppioni

				for (Long idDaAssociare : idTipiDiTrattamento) {
					vo.setIdTipoTrattamento(new BigDecimal(idDaAssociare));
					if (daImpostarePredefinito) {
						vo.setFlagAssociazioneDefault(Constants.FLAG_TRUE);
						daImpostarePredefinito = false;
					} else {
						vo.setFlagAssociazioneDefault(Constants.FLAG_FALSE);
					}
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					vo = genericDAO.insert(vo);
				}

				esito.setEsito(true);
				esito
						.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaCausaleDiErogazione(
			Long idUtente, String identitaDigitale, Long progrBandoCausaleErogaz)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoCausaleErogazVO rbce = new PbandiRBandoCausaleErogazVO(
					new BigDecimal(progrBandoCausaleErogaz));
			rbce = genericDAO.findSingleWhere(rbce);
			esito.setEsito(genericDAO.delete(rbce));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			PbandiRBandoCausErTipAllVO rbceta = beanUtil.transform(rbce,
					PbandiRBandoCausErTipAllVO.class,
					new HashMap<String, String>() {
						{
							put("idBando", "idBando");
							put("idCausaleErogazione", "idCausaleErogazione");
						}
					});
			genericDAO
					.deleteWhere(new FilterCondition<PbandiRBandoCausErTipAllVO>(
							rbceta));
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione della causale (ID = "
					+ progrBandoCausaleErogaz + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaSoggettoFinanziatore(
			Long idUtente, String identitaDigitale, Long idBando,
			Long idSoggetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoSoggFinanziatVO vo = new PbandiRBandoSoggFinanziatVO(
					new BigDecimal(idSoggetto), new BigDecimal(idBando));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione del soggetto (ID = "
					+ idSoggetto + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaTipoDiTrattamento(
			Long idUtente, String identitaDigitale, Long idBando,
			Long idTipoTrattamento) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoTipoTrattamentVO vo = new PbandiRBandoTipoTrattamentVO(
					new BigDecimal(idBando), new BigDecimal(idTipoTrattamento));
			if (genericDAO.findSingleWhere(vo).getFlagAssociazioneDefault()
					.equals(Constants.FLAG_TRUE)) {
				esito.setEsito(genericDAO.delete(vo));
				if (!esito.getEsito()) {
					throw new Exception();
				}
				// ho cancellato un tipo impostato come predefinito
				vo = new PbandiRBandoTipoTrattamentVO();
				vo.setIdBando(new BigDecimal(idBando));
				List<PbandiRBandoTipoTrattamentVO> tempList = genericDAO
						.findListWhere(vo);
				if (!tempList.isEmpty()) {
					vo = tempList.get(0);
					vo.setFlagAssociazioneDefault(Constants.FLAG_TRUE);
					vo.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.update(vo);
				}
			} else {
				esito.setEsito(genericDAO.delete(vo));
				if (!esito.getEsito()) {
					throw new Exception();
				}
			}

			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error(
					"Errore durante la cancellazione del tipo di trattamento (ID = "
							+ idTipoTrattamento + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public CausaleDiErogazioneAssociataDTO[] findCausaliDiErogazioneAssociati(
			Long idUtente, String identitaDigitale, Long idBando)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			BandoCausaleDiErogazioneAssociataVO query = new BandoCausaleDiErogazioneAssociataVO();
			query.setIdBando(new BigDecimal(idBando));
			List<BandoCausaleDiErogazioneAssociataVO> tipi = genericDAO
					.findListWhere(query);
			CausaleDiErogazioneAssociataDTO[] result = new CausaleDiErogazioneAssociataDTO[tipi
					.size()];

			beanUtil.valueCopy(tipi.toArray(), result);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesaDTO[] findComune(Long idUtente,
			String identitaDigitale, Long idProvincia) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idProvincia" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idProvincia);
		 
			PbandiDComuneVO[] comuni=comuniManager.findComune(idProvincia);
			
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[comuni.length];

			Map<String, String> beanMap = new HashMap<String, String>();
			beanMap.put("cap", "descBreve");
			beanMap.put("descComune", "descEstesa");
			beanMap.put("idComune", "id");

			beanUtil.valueCopy(comuni, dto, beanMap);

			return dto;

		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public SoggettoFinanziatoreAssociatoDTO[] findSoggettiFinanziatoriAssociati(
			Long idUtente, String identitaDigitale, Long idBando)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			BandoSoggettoFinanziatoreAssociatoVO query = new BandoSoggettoFinanziatoreAssociatoVO();
			query.setIdBando(new BigDecimal(idBando));
			List<BandoSoggettoFinanziatoreAssociatoVO> soggetti = genericDAO
					.findListWhere(query);
			SoggettoFinanziatoreAssociatoDTO[] result = new SoggettoFinanziatoreAssociatoDTO[soggetti
					.size()];

			beanUtil.valueCopy(soggetti.toArray(), result);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public TipoDiTrattamentoAssociatoDTO[] findTipiDiTrattamentoAssociati(
			Long idUtente, String identitaDigitale, Long idBando)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			BandoTipoTrattamentoAssociatoVO query = new BandoTipoTrattamentoAssociatoVO();
			query.setIdBando(new BigDecimal(idBando));
			List<BandoTipoTrattamentoAssociatoVO> tipi = genericDAO
					.findListWhere(query);
			TipoDiTrattamentoAssociatoDTO[] result = new TipoDiTrattamentoAssociatoDTO[tipi
					.size()];

			beanUtil.valueCopy(tipi.toArray(), result);

			Map<String, String> beanMap = new HashMap<String, String>();
			beanMap.put("flagAssociazioneDefault", "isPredefinito");
			beanUtil.valueCopy(tipi.toArray(), result, beanMap);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public GestioneBackOfficeEsitoGenerico rendiTipoDiTrattamentoPredefinito(
			Long idUtente, String identitaDigitale, Long idBando,
			Long idTipoTrattamento) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoTipoTrattamentVO vo = new PbandiRBandoTipoTrattamentVO();
			vo.setIdBando(new BigDecimal(idBando));
			List<PbandiRBandoTipoTrattamentVO> tipi = genericDAO
					.findListWhere(vo);

			for (PbandiRBandoTipoTrattamentVO tipo : tipi) {
				Long idTipoEstratto = NumberUtil.toLong(tipo
						.getIdTipoTrattamento());
				if (idTipoEstratto.equals(idTipoTrattamento)) {
					tipo.setFlagAssociazioneDefault(Constants.FLAG_TRUE);
					tipo.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.update(tipo);
				} else if (tipo.getFlagAssociazioneDefault().equals(
						Constants.FLAG_TRUE)) {
					tipo.setFlagAssociazioneDefault(Constants.FLAG_FALSE);
					tipo.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.update(tipo);
				}
			}

			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la modifica.", e);
		}  
		return esito;
	}

	public IdDescBreveDescEstesaDTO[] findProvince(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			
			PbandiDProvinciaVO[] vo =comuniManager.findProvince();
			//PbandiDProvinciaVO[] vo = genericDAO
			//.findAll(PbandiDProvinciaVO.class);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo.length];
			Map<String, String> map = new HashMap<String, String>();
			map.put("siglaProvincia", "descBreve");
			map.put("descProvincia", "descEstesa");
			map.put("idProvincia", "id");
			beanUtil.valueCopy(vo, dto, map);
			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		} 
	}

	public IdDescBreveDescEstesaDTO[] findSoggettiFinanziatori(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			PbandiDSoggettoFinanziatoreVO[] vo = genericDAO
					.findAll(PbandiDSoggettoFinanziatoreVO.class);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo.length];
			Map<String, String> map = new HashMap<String, String>();
			map.put("codIgrueT25", "descBreve");
			map.put("descSoggFinanziatore", "descEstesa");
			map.put("idSoggettoFinanziatore", "id");
			beanUtil.valueCopy(vo, dto, map);
			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		} 
	}

	public IdDescBreveDescEstesaDTO[] findCausaliDiErogazione(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			PbandiDCausaleErogazioneVO pbandiDCausaleErogazioneVO = new PbandiDCausaleErogazioneVO();
			pbandiDCausaleErogazioneVO.setFlagIterStandard(Constants.FLAG_TRUE);
			List<PbandiDCausaleErogazioneVO> vo = genericDAO
					.findListWhere(pbandiDCausaleErogazioneVO);
			PbandiDCausaleErogazioneVO[] array = vo
					.toArray(new PbandiDCausaleErogazioneVO[vo.size()]);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo
					.size()];
			Map<String, String> map = new HashMap<String, String>();
			map.put("descBreveCausale", "descBreve");
			map.put("descCausale", "descEstesa");
			map.put("idCausaleErogazione", "id");
			beanUtil.valueCopy(array, dto, map);
			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public EsitoAssociazione associaIndicatore(Long idUtente,
			String identitaDigitale, Long idBando, Long idIndicatore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando", "idIndicatore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando, idIndicatore);

			try {
				PbandiRBandoIndicatoriVO vo = new PbandiRBandoIndicatoriVO();
				vo.setIdBando(new BigDecimal(idBando));
				vo.setIdIndicatori(new BigDecimal(idIndicatore));
				if (genericDAO.findListWhere(vo).isEmpty()) {
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					vo = genericDAO.insert(vo);
					esito.setEsito(true);
					esito
							.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaIndicatoreAssociato(
			Long idUtente, String identitaDigitale, Long idBando,
			Long idIndicatore) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBando",
				"idIndicatore" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idBando, idIndicatore);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoIndicatoriVO vo = new PbandiRBandoIndicatoriVO();
			vo.setIdBando(new BigDecimal(idBando));
			vo.setIdIndicatori(new BigDecimal(idIndicatore));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error(
					"Errore durante la cancellazione dell'associazione (idBando = "
							+ idBando + ", idIndicatori = " + idIndicatore
							+ ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public DefinizioneProcessoDTO[] findDefinizioniProcesso(Long idUtente,
			String identitaDigitale) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale);

			PbandiCDefinizioneProcessoVO[] vo = genericDAO
					.findAll(PbandiCDefinizioneProcessoVO.class);
			DefinizioneProcessoDTO[] dto = new DefinizioneProcessoDTO[vo.length];
			beanUtil.valueCopy(vo, dto);
			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesaDTO[] findIndicatori(Long idUtente,
			String identitaDigitale, Long idTipoIndicatore)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idTipoIndicatore" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idTipoIndicatore);

			IndicatoriCodIgrueVO query = new IndicatoriCodIgrueVO();
			query.setIdTipoIndicatore(new BigDecimal(idTipoIndicatore));
			query.setAscendentOrder("codIgrue");

			IndicatoriCodIgrueVO[] vo = genericDAO.findWhere(Condition
					.filterBy(query));

			Map<String, String> map = new HashMap<String, String>();
			map.put("codIgrue", "descBreve");
			map.put("descIndicatoreLinea", "descEstesa");
			map.put("idIndicatori", "id");

			return beanUtil.transform(vo, IdDescBreveDescEstesaDTO.class, map);
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesaDTO[] findIndicatoriAssociati(Long idUtente,
			String identitaDigitale, Long idBando) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idBando);

			BandoIndicatoreAssociatoVO query = new BandoIndicatoreAssociatoVO();
			query.setIdBando(new BigDecimal(idBando));
			List<BandoIndicatoreAssociatoVO> vo = genericDAO
					.findListWhere(query);
			IdDescBreveDescEstesaDTO[] dto = new IdDescBreveDescEstesaDTO[vo
					.size()];
			Map<String, String> map = new HashMap<String, String>();
			map.put("codIgrue", "descBreve");
			map.put("descIndicatoreLinea", "descEstesa");
			map.put("idIndicatori", "id");
			beanUtil.valueCopy(vo.toArray(), dto, map);
			return dto;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public EsitoAssociazione associaIndicatoreQSN(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long idIndicatoreQSN) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idIndicatoreQSN" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento,
					idIndicatoreQSN);

			try {
				PbandiRBandoLinIndicatQsnVO vo = new PbandiRBandoLinIndicatQsnVO();
				vo.setProgrBandoLineaIntervento(new BigDecimal(
						progrBandoLineaIntervento));
				vo.setIdIndicatoreQsn(new BigDecimal(idIndicatoreQSN));
				if (genericDAO.findListWhere(vo).isEmpty()) {
					vo = genericDAO.insert(vo);
					esito.setEsito(true);
					esito
							.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}

	public EsitoAssociazione associaIndicatoreRisultatoProgramma(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long idIndicatoreRisultatoProgramma) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento",
					"idIndicatoreRisultatoProgramma" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento,
					idIndicatoreRisultatoProgramma);

			try {
				PbandiRBandoLinIndRisProVO vo = new PbandiRBandoLinIndRisProVO();
				vo.setProgrBandoLineaIntervento(new BigDecimal(
						progrBandoLineaIntervento));
				vo.setIdIndRisultatoProgram(new BigDecimal(
						idIndicatoreRisultatoProgramma));
				if (genericDAO.findListWhere(vo).isEmpty()) {
					vo = genericDAO.insert(vo);
					esito.setEsito(true);
					esito
							.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}

	public EsitoAssociazione associaTemaPrioritario(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long idTemaPrioritario) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idTemaPrioritario" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento,
					idTemaPrioritario);

			try {
				PbandiRBanLineaIntTemPriVO vo = new PbandiRBanLineaIntTemPriVO();
				vo.setProgrBandoLineaIntervento(new BigDecimal(
						progrBandoLineaIntervento));
				vo.setIdTemaPrioritario(new BigDecimal(idTemaPrioritario));
				if (genericDAO.findListWhere(vo).isEmpty()) {
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					vo = genericDAO.insert(vo);
					esito.setEsito(true);
					esito
							.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaIndicatoreQSNAssociato(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento, Long idIndicatoreQSN)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento", "idIndicatoreQSN" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento, idIndicatoreQSN);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoLinIndicatQsnVO vo = new PbandiRBandoLinIndicatQsnVO();
			vo.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			vo.setIdIndicatoreQsn(new BigDecimal(idIndicatoreQSN));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione (progrBandoLineaIntervento = "
									+ progrBandoLineaIntervento
									+ ", idIndicatoreQSN = "
									+ idIndicatoreQSN
									+ ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaIndicatoreRisultatoProgrammaAssociato(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento, Long idIndicatoreRisultatoProgramma)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento", "idIndicatoreRisultatoProgramma" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento,
				idIndicatoreRisultatoProgramma);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoLinIndRisProVO vo = new PbandiRBandoLinIndRisProVO();
			vo.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			vo.setIdIndRisultatoProgram(new BigDecimal(
					idIndicatoreRisultatoProgramma));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione (progrBandoLineaIntervento = "
									+ progrBandoLineaIntervento
									+ ", idIndicatoreRisultatoProgramma = "
									+ idIndicatoreRisultatoProgramma + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public GestioneBackOfficeEsitoGenerico eliminaTemaPrioritarioAssociato(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento, Long idTemaPrioritario)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento", "idTemaPrioritario" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento, idTemaPrioritario);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBanLineaIntTemPriVO vo = new PbandiRBanLineaIntTemPriVO();
			vo.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			vo.setIdTemaPrioritario(new BigDecimal(idTemaPrioritario));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione (progrBandoLineaIntervento = "
									+ progrBandoLineaIntervento
									+ ", idIndicatoreRisultatoProgramma = "
									+ idTemaPrioritario + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

	public IdDescBreveDescEstesaDTO[] findIndicatoriQSNAssociati(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento);

			BandoLineaIndicatoreQSNAssociatoVO query = new BandoLineaIndicatoreQSNAssociatoVO();
			query.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			List<BandoLineaIndicatoreQSNAssociatoVO> tipi = genericDAO
					.findListWhere(query);
			IdDescBreveDescEstesaDTO[] result = new IdDescBreveDescEstesaDTO[tipi
					.size()];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descIndicatoreQsn", "descEstesa");
			map.put("codIgrueT12", "descBreve");
			map.put("idIndicatoreQsn", "id");
			beanUtil.valueCopy(tipi.toArray(), result, map);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesaDTO[] findIndicatoriRisultatoProgramma(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento);

			PbandiDIndRisultatoProgramVO query = new PbandiDIndRisultatoProgramVO();
			query.setIdLineaDiIntervento(progettoManager
					.getLineaDiInterventoNormativa(
							new BigDecimal(progrBandoLineaIntervento))
					.getIdLineaDiIntervento());
			List<PbandiDIndRisultatoProgramVO> indicatori = genericDAO
					.findListWhere(Condition
							.filterBy(query)
							.and(
									Condition
											.validOnly(PbandiDIndRisultatoProgramVO.class)));
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descIndRisultatoProgramma", "descEstesa");
			map.put("codIgrueT20", "descBreve");
			map.put("idIndRisultatoProgram", "id");

			return beanUtil.transform(indicatori,
					IdDescBreveDescEstesaDTO.class, map);
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public IdDescBreveDescEstesaDTO[] findIndicatoriRisultatoProgrammaAssociati(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento);

			BandoLineaIndicatoriRisultatoProgrammaAssociatoVO query = new BandoLineaIndicatoriRisultatoProgrammaAssociatoVO();
			query.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			List<BandoLineaIndicatoriRisultatoProgrammaAssociatoVO> tipi = genericDAO
					.findListWhere(query);
			IdDescBreveDescEstesaDTO[] result = new IdDescBreveDescEstesaDTO[tipi
					.size()];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descIndRisultatoProgramma", "descEstesa");
			map.put("codIgrueT20", "descBreve");
			map.put("idIndRisultatoProgram", "id");
			beanUtil.valueCopy(tipi.toArray(), result, map);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	/*
	 * Usare la find decodifiche al posto (non-Javadoc)
	 * 
	 * @see
	 * it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionebackoffice.GestioneBackofficeSrv
	 * #findTemiPrioritariAssociati(java.lang.Long, java.lang.String,
	 * java.lang.Long)
	 */
	// }L{ XXX FATTO!
	@Deprecated
	public IdDescBreveDescEstesaDTO[] findTemiPrioritariAssociati(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento);

			BandoLineaTemaPrioritarioAssociatoVO query = new BandoLineaTemaPrioritarioAssociatoVO();
			query.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			List<BandoLineaTemaPrioritarioAssociatoVO> tipi = genericDAO
					.findListWhere(query);
			IdDescBreveDescEstesaDTO[] result = new IdDescBreveDescEstesaDTO[tipi
					.size()];

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("descTemaPrioritario", "descEstesa");
			map.put("codIgrueT4", "descBreve");
			map.put("idTemaPrioritario", "id");
			beanUtil.valueCopy(tipi.toArray(), result, map);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	private Boolean isVoceDiSpesaAssociataAContoEconomico(Long idVoceDiSpesa,
			Long idBando) {
		VociDiSpesaAssociateABandoVO check = new VociDiSpesaAssociateABandoVO();
		check.setIdVoceDiSpesa(new BigDecimal(idVoceDiSpesa));
		check.setIdBando(new BigDecimal(idBando));
		List<VociDiSpesaAssociateABandoVO> checkList = genericDAO
				.findListWhere(check);
		return !checkList.isEmpty();
	}

	public GestioneBackOfficeEsitoGenerico eliminaUtente(Long idUtente,
			String identitaDigitale, Long idSoggetto,
			Long idTipoAnagraficaSoggetto,
			String descBreveTipoAnagraficaSoggetto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto", "idTipoAnagraficaSoggetto",
					"descBreveTipoAnagraficaSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto, idTipoAnagraficaSoggetto,
					descBreveTipoAnagraficaSoggetto);

			ArrayList<String> soggettoProgetto = new ArrayList<String>() {
				{
					add(Constants.RUOLO_ADG_ISTRUTTORE);
					add(Constants.RUOLO_OI_ISTRUTTORE);
					add(Constants.RUOLO_PERSONA_FISICA);
				}
			};
			ArrayList<String> enteCompetenzaSogg = new ArrayList<String>() {
				{
					add(Constants.RUOLO_OI_IST_MASTER);
					add(Constants.RUOLO_OI_ISTRUTTORE);
					add(Constants.RUOLO_ADG_IST_MASTER);
					add(Constants.RUOLO_ADG_ISTRUTTORE);
				}
			};

			GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
			esito.setEsito(false);

			PbandiRSoggTipoAnagraficaVO rsta = new PbandiRSoggTipoAnagraficaVO();
			rsta.setIdSoggetto(new BigDecimal(idSoggetto));
			rsta.setIdTipoAnagrafica(new BigDecimal(idTipoAnagraficaSoggetto));
			rsta.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil
					.getDataOdierna()));
			rsta.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
			rsta.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(rsta);

			if (enteCompetenzaSogg.contains(descBreveTipoAnagraficaSoggetto)) {
				PbandiREnteCompetenzaSoggVO recs = new PbandiREnteCompetenzaSoggVO();
				recs.setIdSoggetto(new BigDecimal(idSoggetto));
				PbandiTEnteCompetenzaVO tec = new PbandiTEnteCompetenzaVO();
				PbandiREnteCompetenzaSoggVO finpiemonte = new PbandiREnteCompetenzaSoggVO();
				tec
						.setDescBreveEnte(it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESCRIZIONE_BREVE_ENTE_FINPIEMONTE);
				finpiemonte.setIdEnteCompetenza(genericDAO.findSingleWhere(tec)
						.getIdEnteCompetenza());
				Condition<PbandiREnteCompetenzaSoggVO> finpiemonteCondition;
				if (isRuoloOI(descBreveTipoAnagraficaSoggetto)) {
					// FINPIEMONTE
					finpiemonteCondition = new FilterCondition<PbandiREnteCompetenzaSoggVO>(
							finpiemonte);
				} else {
					// NON-FINPIEMONTE
					finpiemonteCondition = new FilterCondition<PbandiREnteCompetenzaSoggVO>(
							finpiemonte).negate();
				}
				if (isRuoloCreator(descBreveTipoAnagraficaSoggetto)) {
					// cancella tutto
					genericDAO
							.deleteWhere(new FilterCondition<PbandiREnteCompetenzaSoggVO>(
									recs));
				} else {
					genericDAO
							.deleteWhere(new AndCondition<PbandiREnteCompetenzaSoggVO>(
									new FilterCondition<PbandiREnteCompetenzaSoggVO>(
											recs), finpiemonteCondition));
				}
				// se non trovo nulla da cancellare, non è un problema
			} 
			if (soggettoProgetto.contains(descBreveTipoAnagraficaSoggetto)) {
				PbandiRSoggettoProgettoVO filter = new PbandiRSoggettoProgettoVO();
				filter.setIdSoggetto(new BigDecimal(idSoggetto));
				filter.setIdTipoAnagrafica(new BigDecimal(
						idTipoAnagraficaSoggetto));

				PbandiRSoggettoProgettoVO newValue = new PbandiRSoggettoProgettoVO();
				newValue.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil
						.getDataOdierna()));
				newValue.setFlagAggiornatoFlux(Constants.FLAG_FALSE);

				newValue.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.update(filter, newValue);
			}

			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);

			return esito;
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione dell'utente "
					+ descBreveTipoAnagraficaSoggetto + " (idSoggetto = "
					+ idSoggetto + ", idTipoAnagrafica = "
					+ idTipoAnagraficaSoggetto + ")", e);

			// è necessaria l'eccezione per la rollback
			throw new GestioneBackOfficeException(ERRORE_SERVER);
		}  
	}

	private Boolean isRuoloOI(String codiceRuolo) {
		return (Constants.RUOLO_OI_IST_MASTER.equals(codiceRuolo))
				|| (Constants.RUOLO_OI_ISTRUTTORE.equals(codiceRuolo));
	}

	private Boolean isRuoloADG(String codiceRuolo) {
		return (Constants.RUOLO_ADG_IST_MASTER.equals(codiceRuolo))
				|| (Constants.RUOLO_ADG_ISTRUTTORE.equals(codiceRuolo));
	}

	private Boolean isRuoloCreator(String codiceRuolo) {
		return (Constants.RUOLO_CREATOR.equals(codiceRuolo));
	}

	

	public UtenteRicercatoDTO findDettaglioUtente(Long idUtente,
			String identitaDigitale, Long idPersonaFisica, Long idTipoAnagrafica)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idPersonaFisica", "idTipoAnagrafica" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idPersonaFisica, idTipoAnagrafica);

			UtenteRicercatoDTO dettaglio = getDettaglioUtente(idPersonaFisica,
					idTipoAnagrafica);

			return dettaglio;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	/*idSoggetto
	 * cognome
	 * nome
	 * codiceFiscale*/
	private UtenteRicercatoDTO getDettaglioUtente(Long idPersonaFisica,
			Long idTipoAnagrafica) {
		UtenteRicercatoDTO dettaglio = new UtenteRicercatoDTO();
		DettaglioSoggettoConTipoAnagraficaVO vo = new DettaglioSoggettoConTipoAnagraficaVO();
		vo.setIdPersonaFisica(new BigDecimal(idPersonaFisica));
		vo.setIdTipoAnagrafica(new BigDecimal(idTipoAnagrafica));
		List<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO> result = genericDAO
				.where(
						new FilterCondition<DettaglioSoggettoConTipoAnagraficaVO>(
								vo))
				.groupBy(
						DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO.class)
				.select();
		// TNT commentato perchè modificando la query  DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO.sql si spacca
		//if (result.size() == 1) {
		if(!isEmpty(result)){
			DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO voGroupBy = result
					.get(0);
			dettaglio = beanUtil.transform(voGroupBy, UtenteRicercatoDTO.class);
		}
		else{
			// non trovo + nulla perchè non ci sono progetti ... da rifare la query?
		}
		// else c'e' qualcosa che non va...
		return dettaglio;
	}

	public IdDescBreveDescEstesaDTO[] findEntiAssociatiSoggetto(Long idUtente,
			String identitaDigitale, Long idSoggetto,
			String descBreveTipoAnagraficaSoggetto) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto", "descBreveTipoAnagraficaSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto,
					descBreveTipoAnagraficaSoggetto);

			List<EnteDiCompetenzaVO> entiCompetentiVO = getEntiAssociatiSoggetto(
					new BigDecimal(idSoggetto), descBreveTipoAnagraficaSoggetto);

			return beanUtil.transform(entiCompetentiVO,
					IdDescBreveDescEstesaDTO.class,
					new HashMap<String, String>() {
						{
							put("descEnteDirezione", "descEstesa");
							put("descBreveEnte", "descBreve");
							put("idEnteCompetenza", "id");
						}
					});
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	private List<EnteDiCompetenzaVO> getEntiAssociatiSoggetto(
			BigDecimal idSoggetto, String descBreveTipoAnagrafica) {
		final String tipoEnteCompetenza = descBreveTipoAnagrafica.contains("-") ? descBreveTipoAnagrafica
				.substring(0, descBreveTipoAnagrafica.indexOf('-'))
				: null; // NB:
		// CREATOR
		// ->
		// tipoEnteCompetenza
		// ==
		// null

		List<EnteDiCompetenzaVO> entiCompetentiVO = new ArrayList<EnteDiCompetenzaVO>();
		PbandiREnteCompetenzaSoggVO entiVO = new PbandiREnteCompetenzaSoggVO();
		entiVO.setIdSoggetto(idSoggetto);
		List<PbandiREnteCompetenzaSoggVO> tuttiEntiAssociati = genericDAO
				.findListWhere(Condition.filterBy(entiVO).and(
						Condition.validOnly(PbandiREnteCompetenzaSoggVO.class)));

		if (tuttiEntiAssociati.size() > 0) {
			// Altrimenti non c'è proprio nulla! Se prosegue FATAL
			entiCompetentiVO = beanUtil.transformList(tuttiEntiAssociati,
					EnteDiCompetenzaVO.class, new HashMap<String, String>() {
						{
							put("idEnteCompetenza", "idEnteCompetenza");
						}
					});
			EnteDiCompetenzaVO tipoEnteVO = new EnteDiCompetenzaVO();
			tipoEnteVO.setDescBreveTipoEnteCompetenz(tipoEnteCompetenza);

			entiCompetentiVO = genericDAO
					.findListWhere(new AndCondition<EnteDiCompetenzaVO>(
							new FilterCondition<EnteDiCompetenzaVO>(
									entiCompetentiVO),
							new FilterCondition<EnteDiCompetenzaVO>(tipoEnteVO)));
		}

		return entiCompetentiVO;
	}

	public BeneficiarioProgettoAssociatoDTO[] findBeneficiarioProgettoAssociati(
			Long idUtente, String identitaDigitale, Long idSoggetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto);

			SoggettoProgettoRuoloProcessoVO query = new SoggettoProgettoRuoloProcessoVO();
			query.setIdSoggetto(new BigDecimal(idSoggetto));
			query.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);

			/*
			 * List<BeneficiarioProgettoAssociatoVO> vo = genericDAO.where( new
			 * FilterCondition<SoggettoProgettoRuoloProcessoVO>(query)).groupBy(
			 * BeneficiarioProgettoAssociatoVO.class).select();
			 */

			List<BeneficiarioProgettoAssociatoVO> vo = genericDAO
					.where(
							Condition
									.filterBy(query)
									.and(
											Condition
													.validOnly(SoggettoProgettoRuoloProcessoVO.class)))
					.groupBy(BeneficiarioProgettoAssociatoVO.class).select();

			BeneficiarioProgettoAssociatoDTO[] result = beanUtil.transform(vo,
					BeneficiarioProgettoAssociatoDTO.class,
					new HashMap<String, String>() {
						{
							put("idProgetto", "idProgetto");
							put("idSoggettoBeneficiario", "idBeneficiario");
							put("codiceFiscaleBeneficiario",
									"codiceFiscaleBeneficiario");
							put("denominazioneBeneficiario",
									"denominazioneBeneficiario");
							put("codiceVisualizzatoProgetto",
									"codiceProgettoVisualizzato");
							put("titoloProgetto", "titoloProgetto");
							put("flagRappresentanteLegale",
									"isRappresentanteLegale");
						}
					});

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	public GestioneBackOfficeEsitoGenerico eliminaAssociazioneBeneficiarioProgetto(
			Long idUtente, String identitaDigitale, Long idSoggetto,
			Long idProgetto) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto, idProgetto);

			return disassociaBeneficiarioProgetto(idUtente, idSoggetto,
					idProgetto);

		} catch (Exception e) {
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	private GestioneBackOfficeEsitoGenerico disassociaBeneficiarioProgetto(
			Long idUtente, Long idSoggetto, Long idProgetto) throws Exception {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		esito.setEsito(true);
		esito.setMessage(CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);

		PbandiRSoggettoProgettoVO condition = new PbandiRSoggettoProgettoVO();
		PbandiRSoggettoProgettoVO newValue = new PbandiRSoggettoProgettoVO();

		condition.setIdSoggetto(new BigDecimal(idSoggetto));
		condition.setIdProgetto(new BigDecimal(idProgetto));
		newValue.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil
				.getDataOdierna()));
		newValue.setFlagAggiornatoFlux(Constants.FLAG_FALSE);

		newValue.setIdUtenteAgg(new BigDecimal(idUtente));
		genericDAO.update(new FilterCondition<PbandiRSoggettoProgettoVO>(
				condition), newValue);

		return esito;
	}

	public GestioneBackOfficeEsitoGenerico inserisciAssociazioneBeneficiarioProgetto(
			Long idUtente, String identitaDigitale, Long idSoggetto,
			String codiceFiscaleBeneficiario,
			String codiceVisualizzatoProgetto, Boolean isRappresentanteLegale,String ruolo,Long idSoggettoOperante)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto", "codiceFiscaleBeneficiario",
					"codiceVisualizzatoProgetto", "isRappresentanteLegale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto, codiceFiscaleBeneficiario,
					codiceVisualizzatoProgetto, isRappresentanteLegale);

			return associaBeneficiarioProgetto(idUtente, idSoggetto,
					codiceFiscaleBeneficiario, codiceVisualizzatoProgetto,
					isRappresentanteLegale, ruolo, idSoggettoOperante);
		} catch (Exception e) {
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	private GestioneBackOfficeEsitoGenerico associaBeneficiarioProgetto(
			Long idUtente, Long idSoggetto, String codiceFiscaleBeneficiario,
			String codiceVisualizzatoProgetto, Boolean isRappresentanteLegale,
			String ruolo,Long idSoggettoOperante)
			throws Exception {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		esito.setEsito(false);
		esito.setMessage(ERRORE_SERVER);

		BigDecimal progrSoggettoProgetto;
		BigDecimal progrSoggettiCorrelati;

		// Verifico relazione beneficiario-progetto
		// se il beneficiario è beneficiario per il progetto
		BeneficiarioProgettoVO bp = new BeneficiarioProgettoVO();
		bp.setCodiceFiscaleSoggetto(codiceFiscaleBeneficiario);
		bp.setCodiceVisualizzatoProgetto(codiceVisualizzatoProgetto);

		List<BeneficiarioProgettoVO> beneficiarioProgetto = genericDAO
				.findListWhere(Condition.filterBy(bp).and(
						Condition.validOnly(BeneficiarioProgettoVO.class)));

		if (beneficiarioProgetto.isEmpty()) {
			throw new GestioneBackOfficeException(
					ERRORE_BENEFICIARIO_PROGETTO_INCOERENTI);
		}
		
	
		if (ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER) ||
				ruolo.equalsIgnoreCase(Constants.RUOLO_OI_IST_MASTER)){
			EnteDiCompetenzaProgSoggVO vo=new EnteDiCompetenzaProgSoggVO();
			vo.setCodice_visualizzato(codiceVisualizzatoProgetto);
			vo.setIdsoggoperante(new BigDecimal(idSoggettoOperante));
			if (ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER))
				vo.setDesc_breve_tipo_ente_competenz("ADG");
			else
				vo.setDesc_breve_tipo_ente_competenz("OI");
			List<EnteDiCompetenzaProgSoggVO> ret = genericDAO.findListWhere(vo);
			if(isEmpty(ret))
				throw new GestioneBackOfficeException(ERRORE_PROGETTO_SU_ENTE_NON_COLLEGATO_SOGG_OPERANTE);
		}
		//if (nonVerificatoEnteAssociatoAlSoggettoLoggato){
		  // throw new GestioneBackOfficeException(   ERRORE_PROGETTO_SU_ENTE_NON_COLLEGATO_SOGG_OPERANTE);
		   //
	    // }
		
		BigDecimal idSoggettoEnteGiuridico = beneficiarioProgetto.get(0)
				.getIdSoggetto();
		BigDecimal idProgetto = beneficiarioProgetto.get(0).getIdProgetto();

		// trovo tutti i dettagli del soggetto
		DettaglioSoggettoVO dett = new DettaglioSoggettoVO();
		dett.setIdSoggetto(new BigDecimal(idSoggetto));
		dett = genericDAO.findSingleWhere(dett);

		// Trovo corrispondenza già esistente
		SoggettoProgettoRuoloProcessoVO sprp = new SoggettoProgettoRuoloProcessoVO();
		sprp.setCodiceFiscaleBeneficiario(codiceFiscaleBeneficiario);
		sprp.setCodiceVisualizzatoProgetto(codiceVisualizzatoProgetto);
		sprp.setIdSoggetto(new BigDecimal(idSoggetto));
		sprp.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);

		List<BeneficiarioProgettoAssociatoVO> benProg = genericDAO
				.where(
						new FilterCondition<SoggettoProgettoRuoloProcessoVO>(
								sprp)
								.and(
										new NullCondition<SoggettoProgettoRuoloProcessoVO>(
												SoggettoProgettoRuoloProcessoVO.class,
												"progrSoggettoProgetto")
												.negate())
								.and(
										Condition
												.validOnly(SoggettoProgettoRuoloProcessoVO.class)))
				.groupBy(BeneficiarioProgettoAssociatoVO.class).select();

		if (benProg.size() > 0) {
			// Trovato qualcosa
			throw new GestioneBackOfficeException(
					ERRORE_UTENTE_GIA_ASSOCIATO_BENEFICIARIO_PROGETTO);
			/**
			 * In questo caso si gestisce la possibilita' di inserire ugualmente
			 * l'associazione se il tipo di associazione per rappresentante
			 * legale è diversa // Conto di prendere l'unico risultato boolean
			 * flagRL =
			 * (benProg.get(0).getFlagRappresentanteLegale().equals(Constants
			 * .FLAG_TRUE)) ? true : false; // E' solo una corrispondenza e per
			 * flagRappresentante legale diverso? if (flagRL ==
			 * isRappresentanteLegale || benProg.size() == 2) { // no -> STOP
			 * (c'è già) esito.setEsito(false); esito
			 * .setMessage(ERRORE_UTENTE_GIA_ASSOCIATO_BENEFICIARIO_PROGETTO);
			 * return esito; } else { // mi va bene: ho già l'associazione
			 * SOGGETTO_PROGETTO fatta progrSoggettoProgetto =
			 * benProg.get(0).getProgrSoggettoProgetto(); }
			 */
		} else {
			// Non trov -> inserisco e mi salvo il progr
			// }L{ PBANDI-1165 oppure utilizzo una relazione già esistente
			PbandiRSoggettoProgettoVO rsp = new PbandiRSoggettoProgettoVO();

			// controllo se non esiste già per id_tipo_anagrafica, id_soggetto e
			// id_progetto
			PbandiDTipoAnagraficaVO tipoAnagraficaVO = new PbandiDTipoAnagraficaVO();
			tipoAnagraficaVO
					.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);

			rsp.setIdTipoAnagrafica(genericDAO
					.findSingleWhere(tipoAnagraficaVO).getIdTipoAnagrafica());
			rsp.setIdSoggetto(dett.getIdSoggetto());
			rsp.setIdProgetto(idProgetto);

			List<PbandiRSoggettoProgettoVO> rspFound = genericDAO.where(
					new FilterCondition<PbandiRSoggettoProgettoVO>(rsp))
					.select();

			if (rspFound != null && rspFound.size() > 0) {
				if (rspFound.size() > 1) {
					logger
							.warn("E' stato trovato su R_SOGGETTO_PROGETTO piu' di un risultato per"
									+ " ID_SOGGETTO="
									+ rsp.getIdSoggetto()
									+ " ID_PROGETTO="
									+ rsp.getIdProgetto()
									+ " ID_TIPO_ANAGRAFICA="
									+ rsp.getIdTipoAnagrafica()
									+ ". Per il nuovo inserimento verra' modificato PROGR_SOGGETTO_PROGETTO="
									+ rspFound.get(0)
											.getProgrSoggettoProgetto());
				}
				rsp = rspFound.get(0);
				rsp.setIdPersonaFisica(dett.getIdPersonaFisica());
				rsp.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
				rsp.setDtInizioValidita(DateUtil.utilToSqlDate(DateUtil
						.getDataOdierna()));
				rsp.setDtFineValidita(null);

				rsp.setIdUtenteAgg(new BigDecimal(idUtente));
				genericDAO.updateNullables(rsp);
			} else {
				rsp.setIdPersonaFisica(dett.getIdPersonaFisica());
				rsp.setDtInizioValidita(DateUtil.utilToSqlDate(DateUtil
						.getDataOdierna()));
				rsp.setIdUtenteIns(new BigDecimal(idUtente));
				rsp.setFlagAggiornatoFlux(Constants.FLAG_FALSE);

				rsp = genericDAO.insert(rsp);
			}

			progrSoggettoProgetto = rsp.getProgrSoggettoProgetto();
		}

		// aggiorno la SOGGETTI_CORRELATI
		PbandiRSoggettiCorrelatiVO rsc = new PbandiRSoggettiCorrelatiVO();
		rsc.setIdSoggetto(dett.getIdSoggetto());
		rsc.setIdSoggettoEnteGiuridico(idSoggettoEnteGiuridico);
		final String soggettoCorrelato = isRappresentanteLegale ? it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_RAPPRESENTANTE_LEGALE
				: it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESCRIZIONE_BREVE_TIPO_SOGG_CORRELATO_ND;

		// prima annullo eventuali vecchi soggetti correlati
		PbandiRSoggettiCorrelatiVO rscDataFine = new PbandiRSoggettiCorrelatiVO();
		rscDataFine.setDtFineValidita(DateUtil.getSysdate());
		genericDAO.update(rsc,rscDataFine);
		
		PbandiDTipoSoggCorrelatoVO soggettoCorrelatoVO = new PbandiDTipoSoggCorrelatoVO();
		soggettoCorrelatoVO.setDescBreveTipoSoggCorrelato(soggettoCorrelato);
		rsc.setIdTipoSoggettoCorrelato(genericDAO.findSingleWhere(
				soggettoCorrelatoVO).getIdTipoSoggettoCorrelato());

		PbandiRSoggettiCorrelatiVO nuovoSoggettoCorrelato = genericDAO
				.findSingleOrNoneWhere(rsc);
		if (nuovoSoggettoCorrelato == null) {
			// da inserire
			rsc.setIdUtenteIns(new BigDecimal(idUtente));
			rsc.setDtInizioValidita(DateUtil.utilToSqlDate(DateUtil
					.getDataOdierna()));
			rsc = genericDAO.insert(rsc);
		} else {
			// anche in questo caso si presume sia unico
			nuovoSoggettoCorrelato.setDtFineValidita(null);
			genericDAO.updateNullables(nuovoSoggettoCorrelato);
			rsc = nuovoSoggettoCorrelato;
		}

		// un progressivo ce l'ho in ogni caso
		progrSoggettiCorrelati = rsc.getProgrSoggettiCorrelati();

		// Ultimo passo: creare la relazione tra SOGGETTO_PROGETTO e
		// SOGGETTO_CORRELATO
		// Se non esiste gia'
		PbandiRSoggProgSoggCorrelVO rspsc = new PbandiRSoggProgSoggCorrelVO();
		rspsc.setProgrSoggettiCorrelati(progrSoggettiCorrelati);
		rspsc.setProgrSoggettoProgetto(progrSoggettoProgetto);

		List<PbandiRSoggProgSoggCorrelVO> rspscFound = genericDAO
				.findListWhere(rspsc);
		if (rspscFound != null && rspscFound.size() > 0) {
			rspsc.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(rspscFound.get(0), rspsc);
		} else {
			rspsc.setIdUtenteIns(new BigDecimal(idUtente));
			genericDAO.insert(rspsc);
		}

		PbandiRSoggTipoAnagraficaVO rSoggTipoAnag = new PbandiRSoggTipoAnagraficaVO();
		rSoggTipoAnag.setIdSoggetto(new BigDecimal(idSoggetto));
		rSoggTipoAnag.setIdTipoAnagrafica(new BigDecimal(16));
		List<PbandiRSoggTipoAnagraficaVO> listRSoggTipoAnag = genericDAO
				.findListWhere(rSoggTipoAnag);
		if (isEmpty(listRSoggTipoAnag)) {
			rSoggTipoAnag.setFlagAggiornatoFlux("N");
			rSoggTipoAnag.setIdUtenteIns(new BigDecimal(idUtente));
			genericDAO.insert(rSoggTipoAnag);
		} 
		
		esito.setEsito(true);
		esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		return esito;
	}

	public EsitoSalvaUtente salvaUtente(Long idUtente, String identitaDigitale,
			DettaglioUtenteDTO dettaglio) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"dettaglio", "dettaglio.nome", "dettaglio.cognome",
					"dettaglio.codiceFiscale", "dettaglio.idTipoAnagrafica" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, dettaglio, dettaglio.getNome(), dettaglio
							.getCognome(), dettaglio.getCodiceFiscale(),
					dettaglio.getIdTipoAnagrafica());

			EsitoSalvaUtente esito = new EsitoSalvaUtente();

			// VARIABILI DI UTILITA
			final String tipoAnagrafica = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
					dettaglio.getIdTipoAnagrafica()).getDescrizioneBreve();
			final BigDecimal idUtenteAgg = new BigDecimal(idUtente);
			final BigDecimal idUtenteIns = idUtenteAgg;
			final BigDecimal idTipoSoggettoPersonaFisica = new BigDecimal(
					decodificheManager
							.findDecodifica(
									GestioneDatiDiDominioSrv.TIPO_SOGGETTO,
									it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_FISICA)
							.getId());
			final Date dtInizioValidita = DateUtil.utilToSqlDate(DateUtil
					.getDataOdierna());
			final Date dtFineValidita = dtInizioValidita;

			dettaglio.setCodiceFiscale(dettaglio.getCodiceFiscale()
					.toUpperCase());

			final BigDecimal idSoggetto;
			final BigDecimal idPersonaFisica;

			List<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO> soggettoEsistente = null;
			try {
				soggettoEsistente = findSoggettoEsistente(dettaglio,
						tipoAnagrafica);
			} catch (GestioneBackOfficeException e) {
				esito.setEsito(false);
				esito.setMessage(e.getMessage());
				return esito;
			}

			Boolean datiAnagraficiInvariati = false;

			if (soggettoEsistente != null && soggettoEsistente.size() != 0) {
				// stessi dati anagrafici
				datiAnagraficiInvariati = true;
				if (!isRuoloADG(tipoAnagrafica) && !isRuoloOI(tipoAnagrafica)
						&& !isRuoloCreator(tipoAnagrafica)) {
					// se fosse stato OI_IST_MASTER ,ADG_IST_MASTER o CREATOR
					// poteva
					// variare per associazioni

					esito.setEsito(false);
					esito
							.setMessage(ErrorConstants.ERRORE_UTENTE_GIA_ASSOCIATO_RUOLO);
					return esito;
				}
			}

			if (dettaglio.getIdSoggetto() == null) {
				// CREA NUOVO
				datiAnagraficiInvariati = false;
				PbandiTSoggettoVO oldSoggetto = new PbandiTSoggettoVO();
				oldSoggetto.setCodiceFiscaleSoggetto(dettaglio
						.getCodiceFiscale());
				oldSoggetto.setIdTipoSoggetto(idTipoSoggettoPersonaFisica);
				PbandiTSoggettoVO[] soggettiEsistenti = genericDAO
						.findWhere(oldSoggetto);
				final boolean isNuovoUtente;
				if (soggettiEsistenti.length == 0) {
					// devo crearlo da 0
					isNuovoUtente = true;
					// T_SOGGETTO
					PbandiTSoggettoVO newSoggetto = new PbandiTSoggettoVO();
					newSoggetto.setCodiceFiscaleSoggetto(dettaglio
							.getCodiceFiscale());
					newSoggetto.setIdTipoSoggetto(idTipoSoggettoPersonaFisica);
					newSoggetto.setIdUtenteIns(idUtenteIns);
					newSoggetto = genericDAO.insert(newSoggetto);
					idSoggetto = newSoggetto.getIdSoggetto();
					// T_UTENTE
					PbandiDTipoAccessoVO tipoAccessoCertificato = new PbandiDTipoAccessoVO();
					tipoAccessoCertificato
							.setCodTipoAccesso(it.csi.pbandi.pbweb.pbandisrv.util.Constants.CODICE_TIPO_ACCESSO_CERTIFICATO);
					tipoAccessoCertificato = genericDAO
							.findWhere(new AndCondition<PbandiDTipoAccessoVO>(
									new FilterCondition<PbandiDTipoAccessoVO>(
											tipoAccessoCertificato),
									new NullCondition<PbandiDTipoAccessoVO>(
											PbandiDTipoAccessoVO.class,
											"dtFineValidita")))[0];
					PbandiTUtenteVO newUtente = new PbandiTUtenteVO();
					newUtente.setIdSoggetto(idSoggetto);
					newUtente.setCodiceUtente(dettaglio.getCodiceFiscale());
					newUtente.setIdTipoAccesso(tipoAccessoCertificato
							.getIdTipoAccesso());
					newUtente = genericDAO.insert(newUtente);
				} else {
					isNuovoUtente = false;
					idSoggetto = soggettiEsistenti[0].getIdSoggetto();
				}
				if (isNuovoUtente) {
					PbandiTPersonaFisicaVO newPersonaFisica = new PbandiTPersonaFisicaVO();
					newPersonaFisica.setIdSoggetto(idSoggetto);
					newPersonaFisica.setDtInizioValidita(dtInizioValidita);
					newPersonaFisica.setCognome(dettaglio.getCognome());
					newPersonaFisica.setIdUtenteIns(idUtenteIns);
					newPersonaFisica.setNome(dettaglio.getNome());
					newPersonaFisica = genericDAO.insert(newPersonaFisica);
					idPersonaFisica = newPersonaFisica.getIdPersonaFisica();
				} else {
					PbandiTPersonaFisicaVO personaFisica = new PbandiTPersonaFisicaVO();
					personaFisica.setIdSoggetto(idSoggetto);
					personaFisica.setNome(dettaglio.getNome());
					personaFisica.setCognome(dettaglio.getCognome());
					List<PbandiTPersonaFisicaVO> personeFisicheInserite = genericDAO
							.findListWhere(personaFisica);
					if (personeFisicheInserite.size() == 0) {
						// è stato modificato nome e/o cognome, inserisco nuovo
						personaFisica.setDtInizioValidita(dtInizioValidita);
						personaFisica.setIdUtenteIns(idUtenteIns);
						personaFisica = genericDAO.insert(personaFisica);
						idPersonaFisica = personaFisica.getIdPersonaFisica();
					} else {
						// esisteva e va aggiornato a oggi
						personaFisica.setIdPersonaFisica(personeFisicheInserite
								.get(0).getIdPersonaFisica());
						personaFisica.setDtInizioValidita(dtInizioValidita);
						personaFisica.setIdUtenteAgg(idUtenteAgg);
						genericDAO.update(personaFisica);
						idPersonaFisica = personaFisica.getIdPersonaFisica();
					}
				}
			} else {
				// MODIFICA
				String[] modificaParameter = { "dettaglio.idPersonaFisica" };
				ValidatorInput.verifyNullValue(modificaParameter, dettaglio
						.getIdPersonaFisica());
				idPersonaFisica = new BigDecimal(dettaglio.getIdPersonaFisica());
				idSoggetto = new BigDecimal(dettaglio.getIdSoggetto());

				/*final UtenteRicercatoDTO dettaglioOriginale = getDettaglioUtente(
						dettaglio.getIdPersonaFisica(), dettaglio
								.getIdTipoAnagrafica());*/
				/* mi servono solo cf, nome,cognome*/
				PersonaFisicaVO dettaglioOriginale = new PersonaFisicaVO();
				dettaglioOriginale.setIdPersonaFisica(idPersonaFisica);
						
				dettaglioOriginale = genericDAO.findSingleWhere(dettaglioOriginale);
				
				
				// CODICE FISCALE
				if (!dettaglio.getCodiceFiscale().equals(
						dettaglioOriginale.getCodiceFiscale())) {
					// si sta cercando di modificare il CF
					PbandiTUtenteVO newUtente = new PbandiTUtenteVO();
					newUtente.setCodiceUtente(dettaglio.getCodiceFiscale());
					if (genericDAO.findWhere(newUtente).length > 0) {
						// collisione di CF, non se ne fa nulla
						esito.setEsito(false);
						// TODO messaggio
						esito.setMessage(ERRORE_SERVER);
						return esito;
					}
					// si può modificare
					// T_UTENTE
					PbandiTUtenteVO oldUtente = new PbandiTUtenteVO();
					oldUtente.setCodiceUtente(dettaglioOriginale
							.getCodiceFiscale());
					oldUtente = genericDAO.findSingleWhere(oldUtente);
					genericDAO.update(oldUtente, newUtente);

					// T_SOGGETTO
					PbandiTSoggettoVO oldSoggetto = new PbandiTSoggettoVO();
					oldSoggetto.setCodiceFiscaleSoggetto(dettaglioOriginale
							.getCodiceFiscale());
					oldSoggetto.setIdTipoSoggetto(idTipoSoggettoPersonaFisica);
					PbandiTSoggettoVO newSoggetto = new PbandiTSoggettoVO();
					newSoggetto.setCodiceFiscaleSoggetto(dettaglio
							.getCodiceFiscale());
					newSoggetto.setIdUtenteAgg(idUtenteAgg);
					genericDAO.update(oldSoggetto, newSoggetto);
				}
				// NOME e/o COGNOME
				if (!dettaglio.getCognome().equals(
						dettaglioOriginale.getCognome())
						|| !dettaglio.getNome().equals(
								dettaglioOriginale.getNome())) {
					// T_PERSONA_FISICA
					PbandiTPersonaFisicaVO personaFisica = new PbandiTPersonaFisicaVO();
					personaFisica.setIdSoggetto(idSoggetto);
					personaFisica.setIdPersonaFisica(idPersonaFisica);
					PbandiTPersonaFisicaVO newPersonaFisica = new PbandiTPersonaFisicaVO();
					newPersonaFisica.setIdUtenteAgg(idUtenteAgg);
					newPersonaFisica.setDtInizioValidita(dtInizioValidita);
					newPersonaFisica.setNome(dettaglio.getNome());
					newPersonaFisica.setCognome(dettaglio.getCognome());

					genericDAO.update(personaFisica, newPersonaFisica);
				}
			}
			// idSoggetto e idPersonaFisica valorizzati per soggetto esistente

			PbandiRSoggTipoAnagraficaVO rsta = new PbandiRSoggTipoAnagraficaVO();
			rsta.setIdSoggetto(idSoggetto);
			rsta.setIdTipoAnagrafica(new BigDecimal(dettaglio
					.getIdTipoAnagrafica()));
			List<PbandiRSoggTipoAnagraficaVO> rstaFound = genericDAO
					.findListWhere(rsta);

			if (rstaFound.isEmpty()) {
				// manca l'associazione
				rsta.setIdUtenteIns(idUtenteIns);
				rsta.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
				rsta.setDtInizioValidita(dtInizioValidita);
				genericDAO.insert(rsta);
			} else {
				// controllo se in realtà dovrebbe essere "eliminato"
				if (genericDAO
						.findListWhere(
								Condition
										.filterBy(rsta)
										.and(
												Condition
														.validOnly(PbandiRSoggTipoAnagraficaVO.class)))
						.isEmpty()) {
					// è stato "eliminato" in precedenza
					rsta = rstaFound.get(0);
					rsta.setDtFineValidita(null);
					rsta.setIdUtenteAgg(idUtenteAgg);
					rsta.setFlagAggiornatoFlux(Constants.FLAG_FALSE);
					genericDAO.updateNullables(rsta);
				}
			}

			// Associazione a enti di competenza
			if (isRuoloADG(tipoAnagrafica) || isRuoloOI(tipoAnagrafica)
					|| isRuoloCreator(tipoAnagrafica)) {
				if (!associaEntiDiCompetenza(idUtenteIns, idSoggetto,
						tipoAnagrafica, dettaglio.getIdEntiAssociati())
						&& datiAnagraficiInvariati) {
					// non sono cambiati i dati, non sono cambiati gli enti
					esito.setEsito(false);
					esito
							.setMessage(ErrorConstants.ERRORE_UTENTE_GIA_ASSOCIATO_RUOLO);
					return esito;
				}
			}

			esito.setEsito(true);
			esito.setIdSoggetto(NumberUtil.toLong(idSoggetto));
			esito.setIdPersonaFisica(NumberUtil.toLong(idPersonaFisica));
			esito.setIdTipoAnagrafica(dettaglio.getIdTipoAnagrafica());
			esito.setMessage(MessaggiConstants.BKO_AGGIORNARE_IRIDE);
			return esito;

		} catch (Exception e) {
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	private List<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO> findSoggettoEsistente(
			DettaglioUtenteDTO dettaglio, String tipoAnagrafica)
			throws GestioneBackOfficeException {
		final BigDecimal idMaster;
		final BigDecimal idNonMaster;

		DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO queryExist = beanUtil
				.transform(dettaglio,
						DettaglioSoggettoConTipoAnagraficaVO.class);

		if (!isRuoloOI(tipoAnagrafica) && !isRuoloADG(tipoAnagrafica)) {
			return genericDAO
					.where(
							new FilterIgnoreCaseCondition<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO>(
									queryExist)).select();
		} else if (isRuoloOI(tipoAnagrafica)) {
			idMaster = new BigDecimal(decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
					Constants.RUOLO_OI_IST_MASTER).getId());
			idNonMaster = new BigDecimal(decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
					Constants.RUOLO_OI_ISTRUTTORE).getId());
		} else {
			// isRuoloADG(tipoAnagrafica) == true
			idMaster = new BigDecimal(decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
					Constants.RUOLO_ADG_IST_MASTER).getId());
			idNonMaster = new BigDecimal(decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.TIPO_ANAGRAFICA,
					Constants.RUOLO_ADG_ISTRUTTORE).getId());
		}

		if (isCollisioneMasterNonMaster(tipoAnagrafica, queryExist
				.getCodiceFiscale(), idMaster, idNonMaster)) {
			throw new GestioneBackOfficeException(
					ErrorConstants.ERRORE_UTENTE_GIA_ASSOCIATO_RUOLO);
		}
		// verifico sia per MASTER che NON-MASTER
		DettaglioSoggettoConTipoAnagraficaVO master = beanUtil.transform(
				queryExist, DettaglioSoggettoConTipoAnagraficaVO.class);
		master.setIdTipoAnagrafica(idMaster);
		DettaglioSoggettoConTipoAnagraficaVO nonMaster = beanUtil.transform(
				queryExist, DettaglioSoggettoConTipoAnagraficaVO.class);
		nonMaster.setIdTipoAnagrafica(idNonMaster);

		return genericDAO
				.where(
						new OrCondition<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO>(
								new FilterIgnoreCaseCondition<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO>(
										master),
								new FilterIgnoreCaseCondition<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO>(
										nonMaster))).select();
	}

	private boolean associaEntiDiCompetenza(BigDecimal idUtenteIns,
			BigDecimal idSoggetto, String descBreveTipoAnagrafica,
			Long[] entiDaAssociare) throws Exception {
		// trovo gli enti già associati
		List<EnteDiCompetenzaVO> entiAssociatiVO = getEntiAssociatiSoggetto(
				idSoggetto, descBreveTipoAnagrafica);

		if (entiAssociatiVO.size() == entiDaAssociare.length) {
			// POTREBBERO essere uguali
			boolean entiDaAssociareDifferenti = false;
			Arrays.sort(entiDaAssociare);
			for (EnteDiCompetenzaVO enteAssociato : entiAssociatiVO) {
				if (Arrays.binarySearch(entiDaAssociare, beanUtil.transform(
						enteAssociato.getIdEnteCompetenza(), Long.class)) < 0) {
					// almeno uno da associare è differente
					entiDaAssociareDifferenti = true;
					break;
				}
			}
			if (!entiDaAssociareDifferenti) {
				// non va fatto nulla
				return false;
			}
		}

		// eliminazione delle vecchie associazioni (tipo anagrafica corrente)
		PbandiREnteCompetenzaSoggVO recsSoggetto = new PbandiREnteCompetenzaSoggVO();
		recsSoggetto.setIdSoggetto(idSoggetto);
		PbandiREnteCompetenzaSoggVO[] recsEnti = beanUtil.transform(
				entiAssociatiVO, PbandiREnteCompetenzaSoggVO.class,
				new HashMap<String, String>() {
					{
						put("idEnteCompetenza", "idEnteCompetenza");
					}
				});
		genericDAO.deleteWhere(new AndCondition<PbandiREnteCompetenzaSoggVO>(
				new FilterCondition<PbandiREnteCompetenzaSoggVO>(recsSoggetto),
				new FilterCondition<PbandiREnteCompetenzaSoggVO>(Arrays
						.asList(recsEnti))));

		// inserimento delle nuove associazioni (tipo anagrafica corrente)
		recsSoggetto.setIdUtenteIns(idUtenteIns);
		for (Long idEnteCompetenza : entiDaAssociare) {
			recsSoggetto.setIdEnteCompetenza(new BigDecimal(idEnteCompetenza));
			genericDAO.insert(recsSoggetto);
		}

		return true;

	}

	private boolean isCollisioneMasterNonMaster(final String tipoAnagrafica,
			String codiceFiscale, final BigDecimal idMaster,
			final BigDecimal idNonMaster) {
		List<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO> soggettoEsistente;
		DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO asMaster = new DettaglioSoggettoConTipoAnagraficaVO();
		asMaster.setCodiceFiscale(codiceFiscale);
		asMaster.setIdTipoAnagrafica(idMaster);
		DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO asNotMaster = new DettaglioSoggettoConTipoAnagraficaVO();
		asNotMaster.setCodiceFiscale(codiceFiscale);
		asNotMaster.setIdTipoAnagrafica(idNonMaster);
		soggettoEsistente = genericDAO
				.where(
						new OrCondition<DettaglioSoggettoConTipoAnagraficaSenzaBeneficiarioVO>(
								Condition.filterBy(asMaster), Condition
										.filterBy(asNotMaster))).select();
		return (soggettoEsistente.size() > 0 && !soggettoEsistente.get(0)
				.getDescBreveTipoAnagrafica().equals(tipoAnagrafica));
	}

	public GestioneBackOfficeEsitoGenerico cambiaAssociazioneBeneficiarioProgetto(
			Long idUtente, String identitaDigitale, Long idSoggetto,
			Long idProgetto, String codiceFiscaleBeneficiario,
			String codiceVisualizzatoProgetto, Boolean isRappresentanteLegale,String ruolo,Long idSoggettoOperante)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idSoggetto", "idProgetto", "codiceFiscaleBeneficiario",
					"codiceVisualizzatoProgetto", "isRappresentanteLegale" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idSoggetto, idProgetto,
					codiceFiscaleBeneficiario, codiceVisualizzatoProgetto,
					isRappresentanteLegale);

//------------- PBANDI-2831 - Abilitazione utenti con flag rappresentante legale ---------
			
//			GestioneBackOfficeEsitoGenerico esito = disassociaBeneficiarioProgetto(
//					idUtente, idSoggetto, idProgetto);

//			if (esito.getEsito()) {
				return associaBeneficiarioProgetto(idUtente, idSoggetto,
						codiceFiscaleBeneficiario, codiceVisualizzatoProgetto,
						isRappresentanteLegale,ruolo,idSoggettoOperante);
//			} else {
//				return esito;
//			}
//   --------------------------------------------------------------------------------------

		} catch (Exception e) {
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public void associaPermessiATipoAnagrafica(Long idUtente,
			String identitaDigitale, String descBreveTipoAnagrafica,
			PermessoDTO[] descBreviPermessi) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBreveTipoAnagrafica", "descBreviPermessi" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBreveTipoAnagrafica, descBreviPermessi);

		try {
			PbandiDTipoAnagraficaVO tipoAnagrafica = getTipoAnagraficaFromDescBreve(descBreveTipoAnagrafica);
			Date dataOdierna = DateUtil
					.utilToSqlDate(DateUtil.getDataOdierna());
			for (int i = 0; i < descBreviPermessi.length; i++) {
				PbandiDPermessoVO permesso = getPermessoFromDescBreve(descBreviPermessi[i]
						.getDescBrevePermesso());
				insertPermessoTipoAnagrafica(tipoAnagrafica
						.getIdTipoAnagrafica(), permesso.getIdPermesso(),
						dataOdierna);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public void associaTipiAnagraficaAPermesso(Long idUtente,
			String identitaDigitale, String descBrevePermesso,
			TipoAnagraficaDTO[] descBreviTipiAnagrafica) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBrevePermesso", "descBreviTipiAnagrafica" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBrevePermesso, descBreviTipiAnagrafica);
		try {
			PbandiDPermessoVO permesso = getPermessoFromDescBreve(descBrevePermesso);
			Date dataOdierna = DateUtil
					.utilToSqlDate(DateUtil.getDataOdierna());
			for (int i = 0; i < descBreviTipiAnagrafica.length; i++) {
				PbandiDTipoAnagraficaVO tipoAnagrafica = getTipoAnagraficaFromDescBreve(descBreviTipiAnagrafica[i]
						.getDescBreveTipoAnagrafica());
				insertPermessoTipoAnagrafica(tipoAnagrafica
						.getIdTipoAnagrafica(), permesso.getIdPermesso(),
						dataOdierna);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public void disassociaPermessiDaTipoAnagrafica(Long idUtente,
			String identitaDigitale, String descBreveTipoAnagrafica,
			PermessoDTO[] descBreviPermessi) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBreveTipoAnagrafica", "descBreviPermessi" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBreveTipoAnagrafica, descBreviPermessi);
		try {
			List<PbandiDPermessoVO> permessiVO = new ArrayList<PbandiDPermessoVO>();
			for (PermessoDTO permessoDTO : descBreviPermessi) {
				PbandiDPermessoVO permessoVO = new PbandiDPermessoVO();
				permessoVO.setDescBrevePermesso(permessoDTO
						.getDescBrevePermesso());
				permessiVO.add(permessoVO);
			}
			FilterCondition<PbandiDPermessoVO> inConditionPermessi = new FilterCondition<PbandiDPermessoVO>(
					permessiVO);
			List<PbandiDPermessoVO> permessiDaCancellare = genericDAO
					.findListWhere(inConditionPermessi);
			ArrayList<PbandiRPermessoTipoAnagrafVO> permessiTipoAnagrafVO = new ArrayList<PbandiRPermessoTipoAnagrafVO>();
			BigDecimal idTipoAnagrafica = getTipoAnagraficaFromDescBreve(
					descBreveTipoAnagrafica).getIdTipoAnagrafica();
			for (PbandiDPermessoVO permVO : permessiDaCancellare) {
				PbandiRPermessoTipoAnagrafVO permessoTipoAnagrafVO = new PbandiRPermessoTipoAnagrafVO();
				permessoTipoAnagrafVO.setIdPermesso(permVO.getIdPermesso());
				permessiTipoAnagrafVO.add(permessoTipoAnagrafVO);
			}
			PbandiRPermessoTipoAnagrafVO condizioneTipoAnagrafica = new PbandiRPermessoTipoAnagrafVO();
			condizioneTipoAnagrafica.setIdTipoAnagrafica(idTipoAnagrafica);
			FilterCondition<PbandiRPermessoTipoAnagrafVO> filtroTipoAnagrafica = new FilterCondition<PbandiRPermessoTipoAnagrafVO>(
					condizioneTipoAnagrafica);
			FilterCondition<PbandiRPermessoTipoAnagrafVO> inConditionPermessiDaCancellare = new FilterCondition<PbandiRPermessoTipoAnagrafVO>(
					permessiTipoAnagrafVO);
			genericDAO
					.deleteWhere(new AndCondition<PbandiRPermessoTipoAnagrafVO>(
							filtroTipoAnagrafica,
							inConditionPermessiDaCancellare));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public void disassociaTipiAnagraficaDaPermesso(Long idUtente,
			String identitaDigitale, String descBrevePermesso,
			TipoAnagraficaDTO[] descBreviTipiAnagrafica) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBrevePermesso", "descBreviTipiAnagrafica" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBrevePermesso, descBreviTipiAnagrafica);
		try {
			List<PbandiDTipoAnagraficaVO> tipiAnagraficaVO = new ArrayList<PbandiDTipoAnagraficaVO>();
			for (TipoAnagraficaDTO tipoAnagraficaDTO : descBreviTipiAnagrafica) {
				PbandiDTipoAnagraficaVO tipoAnagraficaVO = new PbandiDTipoAnagraficaVO();
				tipoAnagraficaVO.setDescBreveTipoAnagrafica(tipoAnagraficaDTO
						.getDescBreveTipoAnagrafica());
				tipiAnagraficaVO.add(tipoAnagraficaVO);
			}
			FilterCondition<PbandiDTipoAnagraficaVO> inConditionTipiAnagrafica = new FilterCondition<PbandiDTipoAnagraficaVO>(
					tipiAnagraficaVO);
			List<PbandiDTipoAnagraficaVO> tipiAnagraficaDaCancellare = genericDAO
					.findListWhere(inConditionTipiAnagrafica);
			ArrayList<PbandiRPermessoTipoAnagrafVO> permessiTipoAnagrafVO = new ArrayList<PbandiRPermessoTipoAnagrafVO>();
			BigDecimal idPermesso = getPermessoFromDescBreve(descBrevePermesso)
					.getIdPermesso();
			for (PbandiDTipoAnagraficaVO tipoAnagVO : tipiAnagraficaDaCancellare) {
				PbandiRPermessoTipoAnagrafVO permessoTipoAnagrafVO = new PbandiRPermessoTipoAnagrafVO();
				permessoTipoAnagrafVO.setIdTipoAnagrafica(tipoAnagVO
						.getIdTipoAnagrafica());
				permessiTipoAnagrafVO.add(permessoTipoAnagrafVO);
			}
			PbandiRPermessoTipoAnagrafVO condizionePermesso = new PbandiRPermessoTipoAnagrafVO();
			condizionePermesso.setIdPermesso(idPermesso);
			FilterCondition<PbandiRPermessoTipoAnagrafVO> filtroPermesso = new FilterCondition<PbandiRPermessoTipoAnagrafVO>(
					condizionePermesso);
			FilterCondition<PbandiRPermessoTipoAnagrafVO> inConditionTipiAnagraficaDaCancellare = new FilterCondition<PbandiRPermessoTipoAnagrafVO>(
					permessiTipoAnagrafVO);
			genericDAO
					.deleteWhere(new AndCondition<PbandiRPermessoTipoAnagrafVO>(
							filtroPermesso,
							inConditionTipiAnagraficaDaCancellare));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public PermessoDTO[] findPermessi(Long idUtente, String identitaDigitale,
			PermessoDTO permesso) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "permesso" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, permesso);
		PermessoDTO[] permessiTrovati = null;
		try {
			PbandiDPermessoVO permessoVO = new PbandiDPermessoVO();
			permessoVO.setDescBrevePermesso(permesso.getDescBrevePermesso());
			permessoVO.setDescPermesso(permesso.getDescPermesso());
			permessoVO.setAscendentOrder("descBrevePermesso");
			List<PbandiDPermessoVO> permessi = genericDAO
					.findListWhere((new LikeStartsWithCondition(permessoVO))
							.and(Condition.validOnly(PbandiDPermessoVO.class)));
			if (permessi != null) {
				permessiTrovati = new PermessoDTO[permessi.size()];
				beanUtil.valueCopy(permessi.toArray(), permessiTrovati);
			}
			return permessiTrovati;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public PermessoDTO[] findPermessiAssociatiATipoAnagrafica(Long idUtente,
			String identitaDigitale, String descBreveTipoAnagrafica)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBreveTipoAnagrafica" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBreveTipoAnagrafica);
		PermessoDTO[] permessiTrovati = null;
		try {
			List<PermessoTipoAnagraficaVO> permessiTipiAnagrafica = doFindPermessiAssociatiATipoAnagrafica(descBreveTipoAnagrafica);
			if (permessiTipiAnagrafica != null) {
				permessiTrovati = new PermessoDTO[permessiTipiAnagrafica.size()];
				beanUtil.valueCopy(permessiTipiAnagrafica.toArray(),
						permessiTrovati);
			}
			return permessiTrovati;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public PermessoDTO[] findPermessiNonAssociatiATipoAnagrafica(Long idUtente,
			String identitaDigitale, String descBreveTipoAnagrafica)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBreveTipoAnagrafica" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBreveTipoAnagrafica);
		PermessoDTO[] permessiNonAssociatiDTO = null;
		try {
			List<PermessoTipoAnagraficaVO> permessiTipiAnagrafica = doFindPermessiAssociatiATipoAnagrafica(descBreveTipoAnagrafica);
			ArrayList<PbandiDPermessoVO> permessiVO = new ArrayList<PbandiDPermessoVO>();
			for (PermessoTipoAnagraficaVO permessoTipoAnagraficaVO : permessiTipiAnagrafica) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("descBrevePermesso", "descBrevePermesso");
				permessiVO.add(beanUtil.transform(permessoTipoAnagraficaVO,
						PbandiDPermessoVO.class, map));
			}
			List<PbandiDPermessoVO> permessiNonAssociatiVO = new ArrayList<PbandiDPermessoVO>();
			Condition condizione = null;
			if (permessiTipiAnagrafica.size() > 0) {
				condizione = new AndCondition(
						new NotCondition<PbandiDPermessoVO>(
								new FilterCondition<PbandiDPermessoVO>(
										permessiVO)), Condition
								.validOnly(PbandiDPermessoVO.class));
			} else {
				condizione = Condition.validOnly(PbandiDPermessoVO.class);
			}
			List<Pair<GenericVO, PbandiDPermessoVO, PbandiDPermessoVO>> list = genericDAO
					.join(condizione,
							Condition.filterBy(new PbandiDPermessoVO())).by(
							"idPermesso").orderBy(
							GenericDAO.Order.ascendent("descBrevePermesso", 0))
					.select();

			for (Pair<GenericVO, PbandiDPermessoVO, PbandiDPermessoVO> pair : list) {
				permessiNonAssociatiVO.add(pair.getFirst());
			}

			if (permessiNonAssociatiVO != null
					&& permessiNonAssociatiVO.size() > 0) {
				permessiNonAssociatiDTO = new PermessoDTO[permessiNonAssociatiVO
						.size()];
				beanUtil.valueCopy(permessiNonAssociatiVO.toArray(),
						permessiNonAssociatiDTO);
			}
			return permessiNonAssociatiDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public TipoAnagraficaDTO[] findTipiAnagraficaAssociatiAPermesso(
			Long idUtente, String identitaDigitale, String descBrevePermesso)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBrevePermesso" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBrevePermesso);
		TipoAnagraficaDTO[] tipiAnagraficaTrovati = null;
		try {
			List<PermessoTipoAnagraficaVO> permessiTipiAnagrafica = doFindTipiAnagraficaAssociatiAPermesso(descBrevePermesso);
			if (permessiTipiAnagrafica != null) {
				tipiAnagraficaTrovati = new TipoAnagraficaDTO[permessiTipiAnagrafica
						.size()];
				beanUtil.valueCopy(permessiTipiAnagrafica.toArray(),
						tipiAnagraficaTrovati);
			}
			return tipiAnagraficaTrovati;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	public TipoAnagraficaDTO[] findTipiAnagraficaFiltrato(Long idUtente,
			String identitaDigitale, TipoAnagraficaDTO tipoAnagrafica)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"tipoAnagrafica" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, tipoAnagrafica);
		TipoAnagraficaDTO[] tipiAnagraficaTrovati = null;
		try {
			PbandiDTipoAnagraficaVO tipoAnagraficaVO = new PbandiDTipoAnagraficaVO();
			tipoAnagraficaVO.setDescBreveTipoAnagrafica(tipoAnagrafica
					.getDescBreveTipoAnagrafica());
			tipoAnagraficaVO.setDescTipoAnagrafica(tipoAnagrafica
					.getDescTipoAnagrafica());
			tipoAnagraficaVO.setAscendentOrder("descBreveTipoAnagrafica");
			List<PbandiDTipoAnagraficaVO> tipiAnagrafica = genericDAO
					.findListWhere((new LikeStartsWithCondition(
							tipoAnagraficaVO)).and(Condition
							.validOnly(PbandiDTipoAnagraficaVO.class)));
			if (tipiAnagrafica != null) {
				tipiAnagraficaTrovati = new TipoAnagraficaDTO[tipiAnagrafica
						.size()];
				beanUtil.valueCopy(tipiAnagrafica.toArray(),
						tipiAnagraficaTrovati);
			}
			return tipiAnagraficaTrovati;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		} 
	}

	public TipoAnagraficaDTO[] findTipiAnagraficaNonAssociatiAPermesso(
			Long idUtente, String identitaDigitale, String descBrevePermesso)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descBrevePermesso" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descBrevePermesso);
		TipoAnagraficaDTO[] tipiAnagraficaNonAssociatiDTO = null;
		try {
			List<PermessoTipoAnagraficaVO> permessiTipiAnagrafica = doFindTipiAnagraficaAssociatiAPermesso(descBrevePermesso);
			ArrayList<PbandiDTipoAnagraficaVO> tipoAnagraficaVO = new ArrayList<PbandiDTipoAnagraficaVO>();
			for (PermessoTipoAnagraficaVO permessoTipoAnagraficaVO : permessiTipiAnagrafica) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("descBreveTipoAnagrafica", "descBreveTipoAnagrafica");
				tipoAnagraficaVO.add(beanUtil.transform(
						permessoTipoAnagraficaVO,
						PbandiDTipoAnagraficaVO.class, map));
			}
			PbandiDTipoAnagraficaVO anagraficaVO = new PbandiDTipoAnagraficaVO();
			List<PbandiDTipoAnagraficaVO> tipiAnagraficaNonAssociatiVO = new ArrayList<PbandiDTipoAnagraficaVO>();
			Condition condition = null;
			if (permessiTipiAnagrafica.size() > 0) {
				condition = new AndCondition(
						new NotCondition<PbandiDTipoAnagraficaVO>(
								new FilterCondition<PbandiDTipoAnagraficaVO>(
										tipoAnagraficaVO)), Condition
								.validOnly(PbandiDTipoAnagraficaVO.class));

			} else {
				condition = Condition.validOnly(PbandiDTipoAnagraficaVO.class);
			}

			List<Pair<GenericVO, PbandiDTipoAnagraficaVO, PbandiDTipoAnagraficaVO>> list = genericDAO
					.join(condition, Condition.filterBy(anagraficaVO)).by(
							"idTipoAnagrafica").orderBy(
							GenericDAO.Order.ascendent(
									"descBreveTipoAnagrafica", 0)).select();

			for (Pair<GenericVO, PbandiDTipoAnagraficaVO, PbandiDTipoAnagraficaVO> pair : list) {
				tipiAnagraficaNonAssociatiVO.add(pair.getFirst());
			}

			if (tipiAnagraficaNonAssociatiVO != null
					&& tipiAnagraficaNonAssociatiVO.size() > 0) {
				tipiAnagraficaNonAssociatiDTO = new TipoAnagraficaDTO[tipiAnagraficaNonAssociatiVO
						.size()];

				beanUtil.valueCopy(tipiAnagraficaNonAssociatiVO.toArray(),
						tipiAnagraficaNonAssociatiDTO);
			}
			return tipiAnagraficaNonAssociatiDTO;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
	}

	private PbandiDTipoAnagraficaVO getTipoAnagraficaFromDescBreve(
			String descBreveTipoAnagrafica) {
		PbandiDTipoAnagraficaVO tipoAnagrafica = new PbandiDTipoAnagraficaVO();
		tipoAnagrafica.setDescBreveTipoAnagrafica(descBreveTipoAnagrafica);
		tipoAnagrafica.setDescendentOrder("idTipoAnagrafica");
		List<PbandiDTipoAnagraficaVO> tipiAnagrafica = genericDAO
				.findListWhere((new FilterCondition(tipoAnagrafica))
						.and(Condition.validOnly(PbandiDTipoAnagraficaVO.class)));
		if (tipiAnagrafica.size() > 0) {
			tipoAnagrafica = tipiAnagrafica.get(0);
		}
		return tipoAnagrafica;
	}

	private PbandiDPermessoVO getPermessoFromDescBreve(String descBrevePermesso) {
		PbandiDPermessoVO permesso = new PbandiDPermessoVO();
		permesso.setDescBrevePermesso(descBrevePermesso);
		permesso.setDescendentOrder("idPermesso");
		List<PbandiDPermessoVO> permessi = genericDAO
				.findListWhere((new FilterCondition(permesso)).and(Condition
						.validOnly(PbandiDPermessoVO.class)));
		if (permessi.size() > 0) {
			permesso = permessi.get(0);
		}
		return permesso;
	}

	private void insertPermessoTipoAnagrafica(BigDecimal idTipoAnagrafica,
			BigDecimal idPermesso, Date dataOdierna) throws Exception {
		PbandiRPermessoTipoAnagrafVO permessoTipoAnagrafica = new PbandiRPermessoTipoAnagrafVO();
		permessoTipoAnagrafica.setIdTipoAnagrafica(idTipoAnagrafica);
		permessoTipoAnagrafica.setIdPermesso(idPermesso);
		permessoTipoAnagrafica.setDtInizioValidita(dataOdierna);
		genericDAO.insert(permessoTipoAnagrafica);
	}

	private List<PermessoTipoAnagraficaVO> doFindTipiAnagraficaAssociatiAPermesso(
			String descBrevePermesso) {
		PermessoTipoAnagraficaVO permessoTipoAnagraficaVO = new PermessoTipoAnagraficaVO();
		permessoTipoAnagraficaVO.setDescBrevePermesso(descBrevePermesso);
		permessoTipoAnagraficaVO.setAscendentOrder("descBreveTipoAnagrafica");
		List<PermessoTipoAnagraficaVO> permessiTipiAnagrafica = genericDAO
				.findListWhere((new FilterCondition(permessoTipoAnagraficaVO))
						.and(Condition
								.validOnly(SoggettoPermessoTipoAnagraficaVO.class)));

		return permessiTipiAnagrafica;
	}

	private List<PermessoTipoAnagraficaVO> doFindPermessiAssociatiATipoAnagrafica(
			String descBreveTipoAnagrafica) {
		PermessoTipoAnagraficaVO permessoTipoAnagraficaVO = new PermessoTipoAnagraficaVO();
		permessoTipoAnagraficaVO.setAscendentOrder("descBrevePermesso");
		permessoTipoAnagraficaVO
				.setDescBreveTipoAnagrafica(descBreveTipoAnagrafica);
		List<PermessoTipoAnagraficaVO> permessiTipiAnagrafica = genericDAO
				.findListWhere((new FilterCondition(permessoTipoAnagraficaVO))
						.and(Condition
								.validOnly(PermessoTipoAnagraficaVO.class)));
		return permessiTipiAnagrafica;
	}

	public void setVoceDiSpesaManager(VoceDiSpesaManager voceDiSpesaManager) {
		this.voceDiSpesaManager = voceDiSpesaManager;
	}

	public VoceDiSpesaManager getVoceDiSpesaManager() {
		return voceDiSpesaManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public BilancioManager getBilancioManager() {
		return bilancioManager;
	}

	public void setBilancioManager(BilancioManager bilancioManager) {
		this.bilancioManager = bilancioManager;
	}

	public Long insertFlussoUpload(Long idUtente, String identitaDigitale, String fileName) throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		PbandiTFlussiUploadVO ret = null;
		
		try {
			PbandiTFlussiUploadVO flusso = new PbandiTFlussiUploadVO();
			flusso.setNomeFlusso(fileName);
			flusso.setDtAcquisizione(DateUtil.getSysdate());
			flusso.setIdUtenteUpload(new BigDecimal(idUtente));
			ret = genericDAO.insert(flusso);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(e.getMessage(), e);
		}  
		
		if (ret == null)
			return null;
		else
			return NumberUtil.toLong(ret.getIdFlusso());
	}
	
	
	
	public UtenteRicercatoDTO[] findUtenti(Long idUtente,
			String identitaDigitale, Long idSoggetto, String ruolo, UtenteRicercatoDTO filtro)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, filtro);
			
			if (!(ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER) ||
					ruolo.equalsIgnoreCase(Constants.RUOLO_OI_IST_MASTER))){
				return findUtentiPerUtenteCSI( ruolo, filtro);
			}
			
			
			DettaglioSoggettoConTipoAnagraficaVO query = new DettaglioSoggettoConTipoAnagraficaVO();
			if (filtro.getIdTipoAnagrafica() != null) {
				query.setIdTipoAnagrafica(new BigDecimal(filtro
						.getIdTipoAnagrafica()));
			}
			UtenteRicercatoDTO[] trovati = null;

			DettaglioSoggettoConTipoAnagraficaVO queryLike = beanUtil
					.transform(filtro,
							DettaglioSoggettoConTipoAnagraficaVO.class);
			// zozzeria : la query like aggiunge la condizione su id_relazione_beneficiario
			queryLike.setIdRelazioneBeneficiario(null);
			queryLike.setOrderPropertyNamesList(new ArrayList<String>() {
				{
					add("cognome");
					add("nome");
					add("codiceFiscale");
					add("descTipoAnagrafica");
				}
			});
			
			/* FILTRO PER RUOLI DI ISTRUTTORE */
			Condition<DettaglioSoggettoConTipoAnagraficaVO> conditionRuolo = null;
			/*Condition<DettaglioSoggettoConTipoAnagraficaVO> conditionEntiOperatoreIn = null;
			Condition<DettaglioSoggettoConTipoAnagraficaVO> conditionEntiProgettoIn = null;
			OrCondition<DettaglioSoggettoConTipoAnagraficaVO> entiOperatoreCondition =null;
			OrCondition<DettaglioSoggettoConTipoAnagraficaVO> entiProgettoCondition =null;*/
			Set <BigDecimal>idEntiOperatore=new HashSet<BigDecimal>();
			if (ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER) ||
					ruolo.equalsIgnoreCase(Constants.RUOLO_OI_IST_MASTER)){
				
				List<SoggettoEnteCompetenzaVO> entiVO;
				
				if (ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER))
					entiVO = bilancioManager.findEntiCompetenzaSoggetto(idSoggetto,"ADG","idEnte");
				else
					entiVO = bilancioManager.findEntiCompetenzaSoggetto(idSoggetto,"OI","idEnte");
				
				
				if (entiVO != null) {
					for (SoggettoEnteCompetenzaVO enteVO : entiVO) {
						idEntiOperatore.add(enteVO.getIdEnteCompetenza());
						}
				}
				/*List<DettaglioSoggettoConTipoAnagraficaVO> filtroEntiOperatore = new ArrayList<DettaglioSoggettoConTipoAnagraficaVO>();
				List<DettaglioSoggettoConTipoAnagraficaVO> filtroEntiProgetto= new ArrayList<DettaglioSoggettoConTipoAnagraficaVO>();
				if (entiVO != null) {
					for (SoggettoEnteCompetenzaVO enteVO : entiVO) {
						idEntiOperatore.add(enteVO.getIdEnteCompetenza());
						DettaglioSoggettoConTipoAnagraficaVO enteOperatore = new DettaglioSoggettoConTipoAnagraficaVO();
						enteOperatore.setIdEnteOperatore(enteVO.getIdEnteCompetenza());
						filtroEntiOperatore.add(enteOperatore);
						DettaglioSoggettoConTipoAnagraficaVO enteProgetto= new DettaglioSoggettoConTipoAnagraficaVO();
						enteProgetto.setIdEnteProgetto(enteVO.getIdEnteCompetenza());
						filtroEntiProgetto.add(enteProgetto);
					}
					
					conditionEntiOperatoreIn = Condition.filterBy(filtroEntiOperatore);
					conditionEntiProgettoIn = Condition.filterBy(filtroEntiProgetto);
					NullCondition<DettaglioSoggettoConTipoAnagraficaVO> nullEnteOperatoreCondition = 
								new NullCondition<DettaglioSoggettoConTipoAnagraficaVO>(
										DettaglioSoggettoConTipoAnagraficaVO.class,
										"idEnteOperatore");
					NullCondition<DettaglioSoggettoConTipoAnagraficaVO> nullEnteProgettoCondition =
								new NullCondition<DettaglioSoggettoConTipoAnagraficaVO>(
										DettaglioSoggettoConTipoAnagraficaVO.class,
										"idEnteProgetto");
					entiOperatoreCondition = new OrCondition<DettaglioSoggettoConTipoAnagraficaVO>(conditionEntiOperatoreIn, nullEnteOperatoreCondition);
					entiProgettoCondition = new OrCondition<DettaglioSoggettoConTipoAnagraficaVO>(conditionEntiProgettoIn, nullEnteProgettoCondition);
				}
			*/
				List<DettaglioSoggettoConTipoAnagraficaVO> filtroRuolo = new ArrayList<DettaglioSoggettoConTipoAnagraficaVO>();
				
				
				if (filtro.getIdTipoAnagrafica() == null && 
						ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER)) {
					DettaglioSoggettoConTipoAnagraficaVO istrADGMaster = new DettaglioSoggettoConTipoAnagraficaVO();
					istrADGMaster.setDescBreveTipoAnagrafica(Constants.RUOLO_ADG_IST_MASTER);
					
					DettaglioSoggettoConTipoAnagraficaVO istrADGr = new DettaglioSoggettoConTipoAnagraficaVO();
					istrADGr.setDescBreveTipoAnagrafica(Constants.RUOLO_ADG_ISTRUTTORE);
					
					DettaglioSoggettoConTipoAnagraficaVO opBeneficiario = new DettaglioSoggettoConTipoAnagraficaVO();
					opBeneficiario.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);
					
					filtroRuolo.add(istrADGMaster);
					filtroRuolo.add(istrADGr);
					filtroRuolo.add(opBeneficiario);
					conditionRuolo = Condition.filterBy(filtroRuolo);
				}
				
				if (filtro.getIdTipoAnagrafica() == null && 
						ruolo.equalsIgnoreCase(Constants.RUOLO_OI_IST_MASTER)) {
					DettaglioSoggettoConTipoAnagraficaVO istrOIMaster = new DettaglioSoggettoConTipoAnagraficaVO();
					istrOIMaster.setDescBreveTipoAnagrafica(Constants.RUOLO_OI_IST_MASTER);
					
					DettaglioSoggettoConTipoAnagraficaVO istrOI = new DettaglioSoggettoConTipoAnagraficaVO();
					istrOI.setDescBreveTipoAnagrafica(Constants.RUOLO_OI_ISTRUTTORE);
					
					DettaglioSoggettoConTipoAnagraficaVO opBeneficiario = new DettaglioSoggettoConTipoAnagraficaVO();
					opBeneficiario.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);
					
					filtroRuolo.add(istrOIMaster);
					filtroRuolo.add(istrOI);
					filtroRuolo.add(opBeneficiario);
					conditionRuolo = Condition.filterBy(filtroRuolo);
				}
			}
			/* FILTRO PER RUOLI DI ISTRUTTORE FINE */
			
			List<DettaglioSoggettoConTipoAnagraficaVO> filtroRelBen = new ArrayList<DettaglioSoggettoConTipoAnagraficaVO>();
			Condition<DettaglioSoggettoConTipoAnagraficaVO> conditionRelBen = null;
			
			DettaglioSoggettoConTipoAnagraficaVO rapprLegale = new DettaglioSoggettoConTipoAnagraficaVO();
			rapprLegale.setIdRelazioneBeneficiario(new BigDecimal(1));
			
			DettaglioSoggettoConTipoAnagraficaVO nonDefinito = new DettaglioSoggettoConTipoAnagraficaVO();
			nonDefinito.setIdRelazioneBeneficiario(new BigDecimal(6));

			if (filtro.getIdRelazioneBeneficiario() != null) {
				switch (filtro.getIdRelazioneBeneficiario().intValue()) {
				case -1:	// Altro
					filtroRelBen.add(rapprLegale);
					filtroRelBen.add(nonDefinito);
					conditionRelBen = Condition.filterBy(filtroRelBen);
					conditionRelBen = Condition.not(conditionRelBen);
					break;
				case 0:		// Tutti
					filtroRelBen.add(rapprLegale);
					filtroRelBen.add(nonDefinito);
					conditionRelBen = Condition.filterBy(filtroRelBen);
					break;
				case 1:		// Rappresentante Legale
					conditionRelBen = Condition.filterBy(rapprLegale);
					break;
				case 6:		// Non definito
					
					conditionRelBen = Condition.filterBy(nonDefinito);
					break;
					
				default:
					break;
				}
			}
			
			BetweenCondition<DettaglioSoggettoConTipoAnagraficaVO> conditionDate = null;
			
			DettaglioSoggettoConTipoAnagraficaVO dataDal = new DettaglioSoggettoConTipoAnagraficaVO();
			dataDal.setDataInserimento(DateUtil.utilToSqlDate(filtro.getDataInserimentoDal())); // todo da completare !!
			
			DettaglioSoggettoConTipoAnagraficaVO dataAl = new DettaglioSoggettoConTipoAnagraficaVO();
			dataAl.setDataInserimento(DateUtil.utilToSqlDate(filtro.getDataInserimentoAl()));
			
			conditionDate = new BetweenCondition<DettaglioSoggettoConTipoAnagraficaVO>(dataDal, dataAl);
			
		
			List<DettaglioSoggettoConTipoAnagraficaVO> soggetti = null;
			
			soggetti = genericDAO
					.findListWhere(
							new AndCondition<DettaglioSoggettoConTipoAnagraficaVO>(
									new LikeStartsWithCondition<DettaglioSoggettoConTipoAnagraficaVO>(queryLike),
									new FilterCondition<DettaglioSoggettoConTipoAnagraficaVO>(query),
									conditionRelBen,
									conditionDate,
									conditionRuolo
									//,entiOperatoreCondition	,
									//entiProgettoCondition
									));
			// ZOZZERIA: dovendo eventualmente filtrare per idEnti e id_relazioneBeneficiario,
			// per il generic dao son costretto a metterli nella select,list,mi ritorna quindi + record per lo stesso soggetto
			// visibile nel web,quindi devo fare il remove via sw
			
			
			
			if(idEntiOperatore!=null && idEntiOperatore.size()>0){
				logger.warn("\n\n\n\n\nidEntiOperatore: "+idEntiOperatore);
				rimuoviSoggConEntiInvalidi(soggetti,idEntiOperatore);
			}
			
			rimuoviDuplicati(soggetti);
			
			trovati = beanUtil.transform(soggetti, UtenteRicercatoDTO.class);

			return trovati;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}
	
	
	private UtenteRicercatoDTO[] findUtentiPerUtenteCSI(
			String ruolo, UtenteRicercatoDTO filtro)
			throws Exception {
	

			DettaglioAllSoggettiConTipoAnagraficaVO query = new DettaglioAllSoggettiConTipoAnagraficaVO();
			if (filtro.getIdTipoAnagrafica() != null) {
				query.setIdTipoAnagrafica(new BigDecimal(filtro
						.getIdTipoAnagrafica()));
			}
			UtenteRicercatoDTO[] trovati = null;

			DettaglioAllSoggettiConTipoAnagraficaVO queryLike = beanUtil
					.transform(filtro,
							DettaglioAllSoggettiConTipoAnagraficaVO.class);
			// zozzeria : la query like aggiunge la condizione su id_relazione_beneficiario
			queryLike.setIdRelazioneBeneficiario(null);
			
			queryLike.setOrderPropertyNamesList(new ArrayList<String>() {
				{
					add("cognome");
					add("nome");
					add("codiceFiscale");
					add("descTipoAnagrafica");
				}
			});
			
			/* FILTRO PER RUOLI DI ISTRUTTORE */
			Condition<DettaglioAllSoggettiConTipoAnagraficaVO> conditionRuolo = null;
		
			
				List<DettaglioAllSoggettiConTipoAnagraficaVO> filtroRuolo = new ArrayList<DettaglioAllSoggettiConTipoAnagraficaVO>();
				
				
				if (filtro.getIdTipoAnagrafica() == null && 
						ruolo.equalsIgnoreCase(Constants.RUOLO_ADG_IST_MASTER)) {
					DettaglioAllSoggettiConTipoAnagraficaVO istrADGMaster = new DettaglioAllSoggettiConTipoAnagraficaVO();
					istrADGMaster.setDescBreveTipoAnagrafica(Constants.RUOLO_ADG_IST_MASTER);
					
					DettaglioAllSoggettiConTipoAnagraficaVO istrADGr = new DettaglioAllSoggettiConTipoAnagraficaVO();
					istrADGr.setDescBreveTipoAnagrafica(Constants.RUOLO_ADG_ISTRUTTORE);
					
					DettaglioAllSoggettiConTipoAnagraficaVO opBeneficiario = new DettaglioAllSoggettiConTipoAnagraficaVO();
					opBeneficiario.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);
					
					filtroRuolo.add(istrADGMaster);
					filtroRuolo.add(istrADGr);
					filtroRuolo.add(opBeneficiario);
					conditionRuolo = Condition.filterBy(filtroRuolo);
				}
				
				if (filtro.getIdTipoAnagrafica() == null && 
						ruolo.equalsIgnoreCase(Constants.RUOLO_OI_IST_MASTER)) {
					DettaglioAllSoggettiConTipoAnagraficaVO istrOIMaster = new DettaglioAllSoggettiConTipoAnagraficaVO();
					istrOIMaster.setDescBreveTipoAnagrafica(Constants.RUOLO_OI_IST_MASTER);
					
					DettaglioAllSoggettiConTipoAnagraficaVO istrOI = new DettaglioAllSoggettiConTipoAnagraficaVO();
					istrOI.setDescBreveTipoAnagrafica(Constants.RUOLO_OI_ISTRUTTORE);
					
					DettaglioAllSoggettiConTipoAnagraficaVO opBeneficiario = new DettaglioAllSoggettiConTipoAnagraficaVO();
					opBeneficiario.setDescBreveTipoAnagrafica(Constants.RUOLO_PERSONA_FISICA);
					
					filtroRuolo.add(istrOIMaster);
					filtroRuolo.add(istrOI);
					filtroRuolo.add(opBeneficiario);
					conditionRuolo = Condition.filterBy(filtroRuolo);
				}
			
			/* FILTRO PER RUOLI DI ISTRUTTORE FINE */
			
			List<DettaglioAllSoggettiConTipoAnagraficaVO> filtroRelBen = new ArrayList<DettaglioAllSoggettiConTipoAnagraficaVO>();
			Condition<DettaglioAllSoggettiConTipoAnagraficaVO> conditionRelBen = null;
			
			DettaglioAllSoggettiConTipoAnagraficaVO rapprLegale = new DettaglioAllSoggettiConTipoAnagraficaVO();
			rapprLegale.setIdRelazioneBeneficiario(new BigDecimal(1));
			
			DettaglioAllSoggettiConTipoAnagraficaVO nonDefinito = new DettaglioAllSoggettiConTipoAnagraficaVO();
			nonDefinito.setIdRelazioneBeneficiario(new BigDecimal(6));

			if (filtro.getIdRelazioneBeneficiario() != null) {
				switch (filtro.getIdRelazioneBeneficiario().intValue()) {
				case -1:	// Altro
					filtroRelBen.add(rapprLegale);
					filtroRelBen.add(nonDefinito);
					conditionRelBen = Condition.filterBy(filtroRelBen);
					conditionRelBen = Condition.not(conditionRelBen);
					break;
				case 0:		// Tutti
					filtroRelBen.add(rapprLegale);
					filtroRelBen.add(nonDefinito);
					conditionRelBen = Condition.filterBy(filtroRelBen);
					break;
				case 1:		// Rappresentante Legale
					conditionRelBen = Condition.filterBy(rapprLegale);
					break;
				case 6:		// Non definito
					
					conditionRelBen = Condition.filterBy(nonDefinito);
					break;
					
				default:
					break;
				}
			}
			
			BetweenCondition<DettaglioAllSoggettiConTipoAnagraficaVO> conditionDate = null;
			
			DettaglioAllSoggettiConTipoAnagraficaVO dataDal = new DettaglioAllSoggettiConTipoAnagraficaVO();
			dataDal.setDataInserimento(DateUtil.utilToSqlDate(filtro.getDataInserimentoDal())); // todo da completare !!
			
			DettaglioAllSoggettiConTipoAnagraficaVO dataAl = new DettaglioAllSoggettiConTipoAnagraficaVO();
			dataAl.setDataInserimento(DateUtil.utilToSqlDate(filtro.getDataInserimentoAl()));
			
			conditionDate = new BetweenCondition<DettaglioAllSoggettiConTipoAnagraficaVO>(dataDal, dataAl);
			
		
			List<DettaglioAllSoggettiConTipoAnagraficaVO> soggetti = null;
			
			soggetti = genericDAO
					.findListWhere(
							new AndCondition<DettaglioAllSoggettiConTipoAnagraficaVO>(
									new LikeStartsWithCondition<DettaglioAllSoggettiConTipoAnagraficaVO>(queryLike),
									new FilterCondition<DettaglioAllSoggettiConTipoAnagraficaVO>(query),
									conditionRelBen,
									conditionDate,
									conditionRuolo
									));
			// ZOZZERIA: dovendo eventualmente filtrare per idEnti e id_relazioneBeneficiario,
			// per il generic dao son costretto a metterli nella select,list,mi ritorna quindi + record per lo stesso soggetto
			// visibile nel web,quindi devo fare il remove via sw
			Iterator <DettaglioAllSoggettiConTipoAnagraficaVO> iter=soggetti.iterator();
			DettaglioAllSoggettiConTipoAnagraficaVO oldSogg=new  DettaglioAllSoggettiConTipoAnagraficaVO();
		
			while (iter.hasNext()){
				DettaglioAllSoggettiConTipoAnagraficaVO newSogg = iter.next();
				if(newSogg.getCodiceFiscale().equalsIgnoreCase(oldSogg.getCodiceFiscale() )&& 
						newSogg.getDescBreveTipoAnagrafica().equalsIgnoreCase(oldSogg.getDescBreveTipoAnagrafica())	)
					iter.remove();
				oldSogg=newSogg;
			}
					
			trovati = beanUtil.transform(soggetti, UtenteRicercatoDTO.class);

			return trovati;
		
	}
	
	private void rimuoviSoggConEntiInvalidi(
			List<DettaglioSoggettoConTipoAnagraficaVO>  soggetti,Set<BigDecimal> idEntiOperatore) {
		long start =System.currentTimeMillis();
		Iterator <DettaglioSoggettoConTipoAnagraficaVO> iter=soggetti.iterator();
		HashMap<BigDecimal,Set<BigDecimal>> cacheSoggetti=new HashMap();
	// con questo ciclo popolo mappa con soggetti e enti correlati tramite progetti
		logger.info("soggetti da controllare: "+soggetti.size());
		while (iter.hasNext()){
			Set<BigDecimal> idEnti =null;
			DettaglioSoggettoConTipoAnagraficaVO soggetto = iter.next();
			if(cacheSoggetti.containsKey(soggetto.getIdSoggetto())){
				idEnti = cacheSoggetti.get(soggetto.getIdSoggetto());
			}else{				
				idEnti =new HashSet<BigDecimal>();
			}
			if(soggetto.getIdEnteProgetto()!=null)
				idEnti.add(soggetto.getIdEnteProgetto());			
			cacheSoggetti.put(soggetto.getIdSoggetto(), idEnti);
		}
		
		logger.info("soggetti con id soggetto diverso : "+cacheSoggetti.size());
		
		Set<Entry<BigDecimal, Set<BigDecimal>>> entrySet = cacheSoggetti.entrySet();
		
		// con questo secondo ciclo rimuovo dalla mappa i sogg con enti non compresi tra quelli dell'operatore
		Iterator iterSogg =entrySet.iterator();
		while ( iterSogg.hasNext()) {
			 Map.Entry entry = (Map.Entry) iterSogg.next();
			 BigDecimal idSogg = (BigDecimal) entry.getKey();
			 Set<BigDecimal> entiProgetto=(Set<BigDecimal>)entry.getValue();
			// logger.warn("\n\n\nl'id sogg "+idSogg+" è legato ai seguenti idEntiProgetti :"+entiProgetto);
			 for (BigDecimal idEntePr : entiProgetto) {
				if(!idEntiOperatore.contains(idEntePr)){
					iterSogg.remove();
					break;
				}
			} 
		}
		Set<BigDecimal> idSoggettiConEntiValidi = cacheSoggetti.keySet();
		
		Set<BigDecimal> idSoggettiConProgettiValidi=new HashSet<BigDecimal>();
		for (DettaglioSoggettoConTipoAnagraficaVO sogg : soggetti) {
			if(sogg.getProgettiValidi()!=null && sogg.getProgettiValidi().longValue()>0){
				for (BigDecimal idEntePr : idEntiOperatore) {
					if(sogg.getIdEnteProgetto()==null || idEntiOperatore.contains(sogg.getIdEnteProgetto())){
						idSoggettiConProgettiValidi.add(sogg.getIdSoggetto());
					}
				} 
			}
		}
		
		
		logger.info("soggetti con id soggetto diverso dopo la scrematura : "+idSoggettiConEntiValidi.size());
		Iterator <DettaglioSoggettoConTipoAnagraficaVO> newiter=soggetti.iterator();
	// da qui rimuovo dalla lista principale i soggetti non contenuti nella mappa con enti non validi
		while (newiter.hasNext()){
			DettaglioSoggettoConTipoAnagraficaVO soggetto = newiter.next();
			if(soggetto.getIdEnteProgetto()!=null
					&& 
					!idSoggettiConEntiValidi.contains(soggetto.getIdSoggetto())
					&&				
					!idSoggettiConProgettiValidi.contains(soggetto.getIdSoggetto())){
				newiter.remove();
			}
		}
		logger.warn("soggetti rimasti ("+soggetti.size()+") ");
		long end =System.currentTimeMillis()-start;
		logger.warn("rimuoviSoggConEntiInvalidi eseguito in ms: "+end);
	}
	
	private void rimuoviDuplicati(
			List<DettaglioSoggettoConTipoAnagraficaVO>  soggetti) {
		Iterator <DettaglioSoggettoConTipoAnagraficaVO> iter=soggetti.iterator();
		DettaglioSoggettoConTipoAnagraficaVO oldSogg=new  DettaglioSoggettoConTipoAnagraficaVO();
		while (iter.hasNext()){
			DettaglioSoggettoConTipoAnagraficaVO newSogg = iter.next();
			if(oldSogg.getIdSoggetto()!=null &&
					newSogg.getIdSoggetto().longValue()== oldSogg.getIdSoggetto().longValue() &&
					newSogg.getCodiceFiscale().equalsIgnoreCase(oldSogg.getCodiceFiscale() )&& 
					newSogg.getDescBreveTipoAnagrafica().equalsIgnoreCase(oldSogg.getDescBreveTipoAnagrafica())	)
				iter.remove();
			oldSogg=newSogg;
		}
	}

	public SettoreEnteDTO[] getSettori(Long idUtente, String identitaDigitale,
			Long idEnte) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"idEnte"  };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, idEnte);
		SettoreEnteDTO[]settori=null;
		
		logger.info("getSettori by idEnte: "+idEnte);
		PbandiDSettoreEnteVO pbandiDSettoreEnteVO=new PbandiDSettoreEnteVO();
		pbandiDSettoreEnteVO.setIdEnteCompetenza(BigDecimal.valueOf(idEnte));
		pbandiDSettoreEnteVO.setAscendentOrder("descSettore");
		List<PbandiDSettoreEnteVO> settoriDb = genericDAO.findListWhere(pbandiDSettoreEnteVO);
		if(settoriDb!=null && !settoriDb.isEmpty()){
			
			// Considero solo i settori attivi.
			List<PbandiDSettoreEnteVO> settoriAttivi = new ArrayList<PbandiDSettoreEnteVO>(); 
			for (PbandiDSettoreEnteVO s : settoriDb) {
				if (s.getDtFineValidita() == null)
					settoriAttivi.add(s);
			}
			
			logger.info("found  settori : "+settoriAttivi.size());
			settori=beanUtil.transform(settoriAttivi,SettoreEnteDTO.class);
		}
		return settori;
	}
	
	// CDU-14 V04
	
	public EsitoAssociazione associaTipoPeriodo(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento,
			Long idTipoPeriodo, Long idPeriodo) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idTipoPeriodo" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento,
					idTipoPeriodo);

			try {
				PbandiRBandoLinTipoPeriodVO vo = new PbandiRBandoLinTipoPeriodVO();
				
				vo.setProgrBandoLineaIntervento(new BigDecimal(
						progrBandoLineaIntervento));
				vo.setIdTipoPeriodo(new BigDecimal(idTipoPeriodo));

				if (genericDAO.findListWhere(vo).isEmpty()) {
					vo.setIdUtenteIns(new BigDecimal(idUtente));
					if(idPeriodo != null && idPeriodo.compareTo(ID_PERIODO_POR_FESR_14_20.longValue()) == 0){
						vo.setIdPeriodo(beanUtil.transform(idPeriodo, BigDecimal.class));
					}
					
					vo = genericDAO.insert(vo);
					
					if(idPeriodo != null && idPeriodo.compareTo(ID_PERIODO_VECCHIA_PROGRAMMAZIONE.longValue()) == 0){
						
						try{
							PbandiRBandoLineaPeriodoVO periodoVO = new PbandiRBandoLineaPeriodoVO();
							
							periodoVO.setIdPeriodo(new BigDecimal(idPeriodo));
							periodoVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
							
							periodoVO = genericDAO.insert(periodoVO);
						}catch(Exception ex){
							throw new Exception(ex);
						}
					}
					
					esito.setEsito(true);
					esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				}
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}
	
	public GestioneBackOfficeEsitoGenerico eliminaTipoPeriodoAssociato(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento, Long idTipoPeriodo, Long idPeriodo)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento", "idTipoPeriodo" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento, idTipoPeriodo);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandoLinTipoPeriodVO vo = new PbandiRBandoLinTipoPeriodVO();
			vo.setProgrBandoLineaIntervento(new BigDecimal(
					progrBandoLineaIntervento));
			vo.setIdTipoPeriodo(new BigDecimal(idTipoPeriodo));
			
			if(idPeriodo != null && idPeriodo.compareTo(ID_PERIODO_POR_FESR_14_20.longValue()) == 0){
				vo.setIdPeriodo(beanUtil.transform(idPeriodo, BigDecimal.class));
			}
			
			Boolean esitoElimina = genericDAO.delete(vo);
			if(esitoElimina && null != idPeriodo && idPeriodo.compareTo(ID_PERIODO_VECCHIA_PROGRAMMAZIONE.longValue()) == 0){
				PbandiRBandoLineaPeriodoVO periodoVO = new PbandiRBandoLineaPeriodoVO();
				periodoVO.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
				periodoVO.setIdPeriodo(new BigDecimal(idPeriodo));
				genericDAO.delete(periodoVO);
			}
			
			esito.setEsito(esitoElimina);
			
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione (progrBandoLineaIntervento = "
									+ progrBandoLineaIntervento
									+ ", idTipoPeriodo = "
									+ idTipoPeriodo + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}
	
	public EsitoAssociazione associaCostantiBandoLinea(Long idUtente, String identitaDigitale, CostantiBandoLineaDTO costantiBandoLinea) 
			throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			if (costantiBandoLinea == null)
				throw new FormalParameterException("Parametro costantiBandoLinea nullo");
			
			String[] nameParameter = { "normaIncentivazione", "codiceNormativa",
					"idTipoOperazione", "idStatoDomanda", "progrBandoLineaIntervento",
					"statoInvioDomanda", "statoProgetto", "statoContoEconomico" };
			ValidatorInput.verifyNullValue(nameParameter,
					costantiBandoLinea.getNormaIncentivazione(),
					costantiBandoLinea.getCodiceNormativa(),
					costantiBandoLinea.getIdTipoOperazione(),
					costantiBandoLinea.getIdStatoDomanda(),
					costantiBandoLinea.getProgrBandoLineaIntervento(),
					costantiBandoLinea.getStatoInvioDomanda(),
					costantiBandoLinea.getStatoProgetto(),
					costantiBandoLinea.getStatoContoEconomico()
					);
				
			// Verifica se per il bando linea esistono già delle costanti
			// (sono tutte obbligatorie, basta verificare che ne esista 1).
			PbandiWCostantiVO voSearch = new PbandiWCostantiVO();
			voSearch.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_PROGR_BANDO_LINEA_INTERVENTO);
			voSearch.setValore(costantiBandoLinea.getProgrBandoLineaIntervento().toString());
			List<PbandiWCostantiVO> costantiDb = genericDAO.findListWhere(voSearch);
			
			// Se il bando-linea ha già delle costanti su db, le cancello.
			if (!costantiDb.isEmpty()) {
				// Recupero il valore del campo NORMA_INCENTIVAZIONE del record trovato
				// (è comune a tutte le costanti del bando-linea, 
				// quindi lo uso come chiave della delete).
				PbandiWCostantiVO costante = costantiDb.get(0);
				String normaIncentivazioneDaCancellare = costante.getNormaIncentivazione(); 
				// Cancello le costanti aventi NORMA_INCENTIVAZIONE=normaIncentivazioneDaCancellare.
				PbandiWCostantiVO filtro = new PbandiWCostantiVO();
				filtro.setNormaIncentivazione(normaIncentivazioneDaCancellare);
				genericDAO.deleteWhere(new FilterCondition<PbandiWCostantiVO>(filtro));					
			}
			
			// Inserisco le costanti per il bando-linea (7 record in PBANDI_W_COSTANTI).
			PbandiWCostantiVO vo = new PbandiWCostantiVO();
			vo.setNormaIncentivazione(costantiBandoLinea.getNormaIncentivazione());
			
			// Inserisco la costante con attributo CODICE_PROGETTO_NORMATIVA
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_CODICE_PROGETTO_NORMATIVA);
			vo.setValore(costantiBandoLinea.getCodiceNormativa());
			genericDAO.insert(vo);
			
			// Inserisco la costante con attributo PROGR_BANDO_LINEA_INTERVENTO
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_PROGR_BANDO_LINEA_INTERVENTO);
			vo.setValore(costantiBandoLinea.getProgrBandoLineaIntervento().toString());
			genericDAO.insert(vo);
							
			// Inserisco la costante con attributo STATO_DOMANDA
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_DOMANDA);
			vo.setValore(costantiBandoLinea.getIdStatoDomanda().toString());
			genericDAO.insert(vo);

			// Inserisco la costante con attributo STATO_INVIO_DOMANDA
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_INVIO_DOMANDA);
			vo.setValore(costantiBandoLinea.getStatoInvioDomanda().toString());
			genericDAO.insert(vo);

			// Inserisco la costante con attributo STATO_PROGETTO
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_PROGETTO);
			vo.setValore(costantiBandoLinea.getStatoProgetto().toString());
			genericDAO.insert(vo);
			
			// Inserisco la costante con attributo TIPO_OPERAZIONE
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_TIPO_OPERAZIONE);
			vo.setValore(costantiBandoLinea.getIdTipoOperazione().toString());
			genericDAO.insert(vo);
			
			// Inserisco la costante con attributo STATO_CONTO_ECONOMICO
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_CONTO_ECONOMICO);
			vo.setValore(costantiBandoLinea.getStatoContoEconomico().toString());
			genericDAO.insert(vo);	
			
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);				

		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}
	
	public CostantiBandoLineaDTO findCostantiBandoLinea(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		String[] nameParameter = { "progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, progrBandoLineaIntervento);
		
		CostantiBandoLineaDTO formCostantiBandoLinea = null;
		try {
			
			// Verifica se per il bando linea esiste già la costante PBANDI_W_COSTANTI_ATTRIBUTO_PROGR_BANDO_LINEA_INTERVENTO
			// in modo da recuperare la Norma Incentivazione che fa da chiave tra tutte le costanti.
			PbandiWCostantiVO voSearch = new PbandiWCostantiVO();
			voSearch.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_PROGR_BANDO_LINEA_INTERVENTO);
			voSearch.setValore(progrBandoLineaIntervento.toString());
			List<PbandiWCostantiVO> costantiDb = genericDAO.findListWhere(voSearch);
			
			// Se non è stato trovato nulla, restituisco null.
			if (costantiDb == null || costantiDb.isEmpty()) return null;
				
			// Recupero il valore del campo NORMA_INCENTIVAZIONE del record trovato.
			// è comune a tutte le costanti del bando-linea, quindi lo uso come chiave per la successiva select).
			String normaIncentivazione = costantiDb.get(0).getNormaIncentivazione();
			
			// Legge le costanti del bando linea.
			PbandiWCostantiVO vo = new PbandiWCostantiVO();
			vo.setNormaIncentivazione(normaIncentivazione);
			List<PbandiWCostantiVO> elencoCostantiBandoLinea = genericDAO.findListWhere(vo);
			if (elencoCostantiBandoLinea == null || elencoCostantiBandoLinea.isEmpty())
				return null;
			
			String codProgettoNormativa = it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_CODICE_PROGETTO_NORMATIVA;
			String statoDomanda = it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_DOMANDA;			
			String tipoOperazione = it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_TIPO_OPERAZIONE;			
			String statoInvioDomanda = it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_INVIO_DOMANDA;
			String statoProgetto = it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_PROGETTO;
			String statoContoEconomico = it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_STATO_CONTO_ECONOMICO;
			
			// Recupero i valori da visualizzare a video in Costanti\Modelli -> Costanti.
			formCostantiBandoLinea = new CostantiBandoLineaDTO();
			formCostantiBandoLinea.setProgrBandoLineaIntervento(progrBandoLineaIntervento);
			formCostantiBandoLinea.setNormaIncentivazione(normaIncentivazione);
			for (PbandiWCostantiVO costante : elencoCostantiBandoLinea) {
				if(codProgettoNormativa.equals(costante.getAttributo()))
					formCostantiBandoLinea.setCodiceNormativa(costante.getValore());
				if(statoDomanda.equals(costante.getAttributo()))
					formCostantiBandoLinea.setIdStatoDomanda(new Long(costante.getValore()));
				if(tipoOperazione.equals(costante.getAttributo()))
					formCostantiBandoLinea.setIdTipoOperazione(new Long(costante.getValore()));				
				if(statoInvioDomanda.equals(costante.getAttributo()))
					formCostantiBandoLinea.setStatoInvioDomanda(new Integer(costante.getValore()));
				if(statoProgetto.equals(costante.getAttributo()))
					formCostantiBandoLinea.setStatoProgetto(new Integer(costante.getValore()));
				if(statoContoEconomico.equals(costante.getAttributo()))
					formCostantiBandoLinea.setStatoContoEconomico(new Integer(costante.getValore()));
			}
			
			return formCostantiBandoLinea;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	// Verifica se esiste un bando linea diverso da quello in input,
	// a cui sia stata associata la norma incentivazione in input.
	public Boolean verificaUnicitaNormaIncentivazione(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaIntervento, 
			String normaIncentivazione)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		String[] nameParameter = { "progrBandoLineaIntervento", "normaIncentivazione" };
		ValidatorInput.verifyNullValue(nameParameter, progrBandoLineaIntervento, normaIncentivazione);
		
		try {
			
			// Legge da db le costanti aventi
			// ATTRIBUTO = "PROGR_BANDO_LINEA_INTERVENTO" e 
			// NORMA_INCENTIVAZIONE = valore in input.
			PbandiWCostantiVO vo = new PbandiWCostantiVO();
			vo.setAttributo(it.csi.pbandi.pbweb.pbandisrv.util.Constants.PBANDI_W_COSTANTI_ATTRIBUTO_PROGR_BANDO_LINEA_INTERVENTO);
			vo.setNormaIncentivazione(normaIncentivazione);
			List<PbandiWCostantiVO> costanti = genericDAO.findListWhere(vo);
			
			boolean normaUnivoca = false;
			if (costanti == null || costanti.isEmpty()) {
				// Se non è stato trovato nulla, la norma incentivazione è univoca.
				normaUnivoca = true;
			} else if (costanti.size() == 1) {
				// Se è stato trovato 1 risultato, verifico che il bando linea sia quello in input.
				String progBandoLinea = costanti.get(0).getValore();
				normaUnivoca = progBandoLinea.equals(progrBandoLineaIntervento.toString());
			} else  {
				// Se è stato trovato più di 1 record, la norma incentivazione non è univoca.
				normaUnivoca = false;
			}
			
			return normaUnivoca;
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}
	
	// Associa una serie di modelli al bando-linea 'new' copiandoli da un bando-linea 'old'.
	public EsitoAssociazione associaModelli(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaInterventoOld,
			Long progrBandoLineaInterventoNew,
			Long[] elencoIdTipoDocumentoIndex
			) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaInterventoOld", "progrBandoLineaInterventoNew", "elencoIdTipoDocumentoIndex" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaInterventoOld,
					progrBandoLineaInterventoNew, elencoIdTipoDocumentoIndex);
			
			for (Long idTipoDocumentoIndex : elencoIdTipoDocumentoIndex) {
				// Associo il singolo modello.
				esito = this.associaModello(progrBandoLineaInterventoOld, progrBandoLineaInterventoNew, idTipoDocumentoIndex);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}
	
	// Usato da associaModelli().
	private EsitoAssociazione associaModello(
			Long progrBandoLineaInterventoOld,
			Long progrBandoLineaInterventoNew,
			Long idTipoDocumentoIndex
			) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			
			String[] nameParameter = { "progrBandoLineaInterventoOld", "progrBandoLineaInterventoNew", "idTipoDocumentoIndex" };
			ValidatorInput.verifyNullValue(nameParameter, progrBandoLineaInterventoOld,
					progrBandoLineaInterventoNew, idTipoDocumentoIndex);

			// Delete di eventuale record pre-esistente e insert su PBANDI_R_TP_DOC_IND_BAN_LI_INT.
			PbandiRTpDocIndBanLiIntVO vo1 = new PbandiRTpDocIndBanLiIntVO();
			vo1.setIdTipoDocumentoIndex(BigDecimal.valueOf(idTipoDocumentoIndex));
			vo1.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaInterventoNew));
			genericDAO.delete(vo1);
			vo1.setIdUtenteIns(BigDecimal.valueOf(0));
			genericDAO.insert(vo1);
			
			// Select su PBANDI_R_BL_TIPO_DOC_SEZ_MOD.
			PbandiRBlTipoDocSezModVO vo2 = new PbandiRBlTipoDocSezModVO();
			vo2.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaInterventoOld));
			vo2.setIdTipoDocumentoIndex(BigDecimal.valueOf(idTipoDocumentoIndex));
			List<PbandiRBlTipoDocSezModVO> sezioni = genericDAO.findListWhere(vo2);
			
			// Scorre le sezioni associate al bando-linea 'old' per associarle a quello 'new'.
			for(PbandiRBlTipoDocSezModVO sez : sezioni) {				
				
				// Insert su PBANDI_R_BL_TIPO_DOC_SEZ_MOD.
				PbandiRBlTipoDocSezModVO vo3 = new PbandiRBlTipoDocSezModVO();
				vo3.setIdMacroSezioneModulo(sez.getIdMacroSezioneModulo());
				vo3.setIdTipoDocumentoIndex(sez.getIdTipoDocumentoIndex());
				vo3.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaInterventoNew));
				vo3.setNumOrdinamentoMacroSezione(sez.getNumOrdinamentoMacroSezione());
				//vo3.setProgrBlTipoDocSezMod()   popolato con sequence SEQ_PBANDI_R_BL_TIPO_DOC_SEZ_M (vedi SequenceManager.java)
				vo3.setReportJrxml(sez.getReportJrxml());
				vo3.setTemplateJrxml(sez.getTemplateJrxml());
				vo3 = genericDAO.insert(vo3);
				
				BigDecimal progrBlTipoDocSezMod = vo3.getProgrBlTipoDocSezMod();
				
				// Select su PBANDI_R_BL_TIPO_DOC_MICRO_SEZ.
				PbandiRBlTipoDocMicroSezVO vo4 = new PbandiRBlTipoDocMicroSezVO();
				vo4.setProgrBlTipoDocSezMod(sez.getProgrBlTipoDocSezMod());
				List<PbandiRBlTipoDocMicroSezVO> microSezioni = genericDAO.findListWhere(vo4);
				
				// Scorre le micro sezioni del bando-linea 'old' per associarle al 'new'.
				for(PbandiRBlTipoDocMicroSezVO microSez : microSezioni) {
				
					// Insert su PBANDI_R_BL_TIPO_DOC_MICRO_SEZ.
					PbandiRBlTipoDocMicroSezVO vo5 = new PbandiRBlTipoDocMicroSezVO();
					vo5.setProgrBlTipoDocSezMod(progrBlTipoDocSezMod);
					vo5.setIdMicroSezioneModulo(microSez.getIdMicroSezioneModulo());
					vo5.setNumOrdinamentoMicroSezione(microSez.getNumOrdinamentoMicroSezione());
					genericDAO.insert(vo5);
					
				}
				
			}
			
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento", e);
		} 
		return esito;
	}
	
	public GestioneBackOfficeEsitoGenerico eliminaModelloAssociato(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento, Long idTipoDocumentoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento", "idTipoDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento, idTipoDocumentoIndex);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {			
			pbandiBackOfficeDAO.eliminaModelloAssociato(progrBandoLineaIntervento, idTipoDocumentoIndex);
			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione (progrBandoLineaIntervento = "
									+ progrBandoLineaIntervento
									+ ", idTipoDocumentoIndex = "
									+ idTipoDocumentoIndex + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}
	
	public IdDescBreveDescEstesaDTO[] findModelliAssociati(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaIntervento);
		
		CostantiBandoLineaDTO formCostantiBandoLinea = null;
		try {
			 
			List<PbandiCTipoDocumentoIndexVO> modelli = pbandiBackOfficeDAO.findModelliAssociati(progrBandoLineaIntervento);
			
			int n = modelli.size();
			int i = 0;
			IdDescBreveDescEstesaDTO[] elencoIdDesc = new IdDescBreveDescEstesaDTO[n];
			for(PbandiCTipoDocumentoIndexVO modello : modelli) {
				IdDescBreveDescEstesaDTO item = new IdDescBreveDescEstesaDTO();
				item.setId(modello.getIdTipoDocumentoIndex().longValue());				
				item.setDescBreve(modello.getDescBreveTipoDocIndex());
				item.setDescEstesa(modello.getDescTipoDocIndex());
				elencoIdDesc[i] = item;
				i = i + 1;
			}
			
			return elencoIdDesc;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}
	
	public CheckListBandoLineaDTO[] findCheckListAssociabili(
			Long idUtente,
			String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		
		try {
			
			return pbandiBackOfficeDAO.findCheckListAssociabili();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}
	
	public CheckListBandoLineaDTO[] findCheckListAssociate(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaIntervento);
		
		try {
			
			return pbandiBackOfficeDAO.findCheckListAssociate(progrBandoLineaIntervento);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}
	
	public EsitoAssociazione associaCheckList(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaIntervento,
			Long idTipoDocumentoIndex,
			Long idModello
			) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idTipoDocumentoIndex", "idModello" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento,
					idTipoDocumentoIndex, idModello);
			
			// Delete di eventuale record pre-esistente e insert su PBANDI_R_TP_DOC_IND_BAN_LI_INT.
			PbandiRTpDocIndBanLiIntVO vo = new PbandiRTpDocIndBanLiIntVO();
			vo.setIdTipoDocumentoIndex(BigDecimal.valueOf(idTipoDocumentoIndex));
			vo.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			genericDAO.delete(vo);
			vo.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			vo.setIdModello(BigDecimal.valueOf(idModello));
			genericDAO.insert(vo);
			
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		} 
		return esito;
	}
	
	public GestioneBackOfficeEsitoGenerico eliminaCheckListAssociata(
			Long idUtente, String identitaDigitale,
			Long progrBandoLineaIntervento, Long idTipoDocumentoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLineaIntervento", "idTipoDocumentoIndex" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLineaIntervento, idTipoDocumentoIndex);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {			
			PbandiRTpDocIndBanLiIntVO vo = new PbandiRTpDocIndBanLiIntVO();
			vo.setIdTipoDocumentoIndex(BigDecimal.valueOf(idTipoDocumentoIndex));
			vo.setProgrBandoLineaIntervento(BigDecimal.valueOf(progrBandoLineaIntervento));
			genericDAO.delete(vo);
			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione (progrBandoLineaIntervento = "
									+ progrBandoLineaIntervento
									+ ", idTipoDocumentoIndex = "
									+ idTipoDocumentoIndex + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}
	
	public DocPagamBandoLineaDTO[] findDocPagamAssociati(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaIntervento);
		
		try {
			
			return pbandiBackOfficeDAO.findDocPagamAssociati(progrBandoLineaIntervento);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}
	
	public EsitoAssociazione associaDocPagam(
			Long idUtente,
			String identitaDigitale,
			Long progrBandoLineaIntervento,
			Long idTipoDocumento,
			Long idModalitaPagamento
			) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLineaIntervento", "idTipoDocumento", "idModalitaPagamento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLineaIntervento,
					idTipoDocumento, idModalitaPagamento);

			java.util.Date d = new java.util.Date();
			java.sql.Date oggi = new java.sql.Date(d.getTime());
			
			BigDecimal progrBL = BigDecimal.valueOf(progrBandoLineaIntervento);
			BigDecimal idDoc = BigDecimal.valueOf(idTipoDocumento);
			BigDecimal idPagam = BigDecimal.valueOf(idModalitaPagamento);
			
			// Legge da PBANDI_R_ECCEZ_BAN_LIN_DOC_PAG i record con 
			// progrBandoLineaIntervento e idTipoDocumento pari ai valori in input.
			PbandiREccezBanLinDocPagVO vo = new PbandiREccezBanLinDocPagVO();
			vo.setProgrBandoLineaIntervento(progrBL);
			vo.setIdTipoDocumentoSpesa(idDoc);
			List<PbandiREccezBanLinDocPagVO> elenco = genericDAO.findListWhere(vo);
			
			// Alcune verifiche sui dati appena letti.
			boolean associazioneGiaPresente = false;		// record con progrBL, idDoc e idPagam in input.
			boolean recordConPagamNulloPresente = false;	// record con progrBL e idDoc in input e idPagam nullo.
			for(PbandiREccezBanLinDocPagVO rec : elenco) {				
				if(rec.getIdModalitaPagamento() == null) {
					recordConPagamNulloPresente = true;
				} else if(rec.getIdModalitaPagamento().equals(idPagam)) { 
					associazioneGiaPresente = true;
				}
			}

			// Se l'associazione esiste già su db, lo segnala.
			if (associazioneGiaPresente) {
				esito.setEsito(false);
				esito.setMessage("Associazione Documenti e Modalit&agrave; di pagamento gi&agrave; esistente.");
				return esito;					
			}
			
			// Inserisce l'associazione.
			PbandiREccezBanLinDocPagVO voInsert = new PbandiREccezBanLinDocPagVO();
			voInsert.setProgrBandoLineaIntervento(progrBL);
			voInsert.setIdTipoDocumentoSpesa(idDoc);
			voInsert.setIdModalitaPagamento(idPagam);
			voInsert.setFlagAggiunta("S");
			voInsert.setIdUtenteIns(BigDecimal.ZERO);
			// progrEccezBanLinDocPag	 popolato con sequence SEQ_PBANDI_R_ECC_BAN_LIN_DOC_P (vedi SequenceManager.java)					
			voInsert.setDtInserimento(oggi);
			genericDAO.insert(voInsert);
			
			// Se manca il record con pagam nullo, lo inserisce.
			if (!recordConPagamNulloPresente) {
				voInsert.setIdModalitaPagamento(null);
				voInsert.setProgrEccezBanLinDocPag(null);
				genericDAO.insert(voInsert);
			}
	
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			
		} catch (Exception e) {
			logger.error("Errore durante inserimento: ", e);
			// è necessaria l'eccezione per la rollback
			throw new GestioneBackOfficeException(ERRORE_SERVER);
		} 
		return esito;
	}
	
	public EsitoAssociazione associaDocPagamATuttiBL(
			Long idUtente,
			String identitaDigitale,
			Long idTipoDocumento,
			Long idModalitaPagamento
			) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idTipoDocumento", "idModalitaPagamento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, idTipoDocumento, idModalitaPagamento);

			BigDecimal idDoc = BigDecimal.valueOf(idTipoDocumento);
			BigDecimal idPagam = BigDecimal.valueOf(idModalitaPagamento);
			
			// Verifica se doc e pagam esistono già in PBANDI_R_TIPO_DOC_MODALITA_PAG.
			PbandiRTipoDocModalitaPagVO vo = new PbandiRTipoDocModalitaPagVO();
			vo.setIdTipoDocumentoSpesa(idDoc);
			vo.setIdModalitaPagamento(idPagam);
			List<PbandiRTipoDocModalitaPagVO> elenco = genericDAO.findListWhere(vo);

			// Se l'associazione esiste già su db, lo segnala.
			if (elenco.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Associazione Documenti e Modalit&agrave; di pagamento gi&agrave; assegnata a tutti i bando-linea.");
				return esito;					
			}
			
			// Inserisce l'associazione in PBANDI_R_TIPO_DOC_MODALITA_PAG.
			vo.setIdUtenteIns(BigDecimal.ZERO);
			genericDAO.insert(vo);
	
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			
		} catch (Exception e) {
			logger.error("Errore durante inserimento: ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		} 
		return esito;
	}
	
	public GestioneBackOfficeEsitoGenerico eliminaDocPagamAssociato(
			Long idUtente, String identitaDigitale,
			java.lang.Long progrEccezBanLinDocPag)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrEccezBanLinDocPag" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrEccezBanLinDocPag);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {			

			pbandiBackOfficeDAO.eliminaDocPagamAssociato(progrEccezBanLinDocPag);
			
			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione con progrEccezBanLinDocPag = "+progrEccezBanLinDocPag, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}
	
	public EsitoAssociazione inserisciTipoDocumentoSpesa(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.String descrizione,
			java.lang.String descrizioneBreve) 
		throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descrizione", "descrizioneBreve" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descrizione, descrizioneBreve);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			
			// Verifica che non esista già un record con le stesse descrizione o descrizione breve.
			PbandiDTipoDocumentoSpesaVO vo1 = new PbandiDTipoDocumentoSpesaVO();
			vo1.setDescTipoDocumentoSpesa(descrizione);
			List<PbandiDTipoDocumentoSpesaVO> elenco1 = genericDAO.findListWhere(vo1);
			if (elenco1.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Descrizione gia' assegnata ad un altro tipo di documento");
				return esito;
			}
			PbandiDTipoDocumentoSpesaVO vo2 = new PbandiDTipoDocumentoSpesaVO();
			vo2.setDescBreveTipoDocSpesa(descrizioneBreve);
			List<PbandiDTipoDocumentoSpesaVO> elenco2 = genericDAO.findListWhere(vo2);
			if (elenco2.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Descrizione breve gia' assegnata ad un altro tipo di documento");
				return esito;
			}
			
			boolean esitoInsert = pbandiBackOfficeDAO.inserisciTipoDocumentoSpesa(descrizione, descrizioneBreve);
			
			if (esitoInsert) {
				esito.setEsito(true);
				esito.setMessage(MessaggiConstants.OPERAZIONE_ESEGUITA_CON_SUCCESSO);	
			} else {
				throw new Exception("Errore in pbandiBackOfficeDAO.inserisciTipoDocumentoSpesa().");
			} 
			
						
		} catch (Exception e) {
			logger.error("Errore durante inserimento: ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
		
	}
	
	public EsitoAssociazione inserisciModalitaPagamento(
			java.lang.Long idUtente,
			java.lang.String identitaDigitale,
			java.lang.String descrizione,
			java.lang.String descrizioneBreve) 
		throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"descrizione", "descrizioneBreve" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, descrizione, descrizioneBreve);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			
			// Verifica che non esista già un record con le stesse descrizione o descrizione breve.
			PbandiDModalitaPagamentoVO vo1 = new PbandiDModalitaPagamentoVO();
			vo1.setDescModalitaPagamento(descrizione);
			List<PbandiDModalitaPagamentoVO> elenco1 = genericDAO.findListWhere(vo1);
			if (elenco1.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Descrizione gia' assegnata ad un'altra modalita' di pagamento");
				return esito;
			}
			PbandiDModalitaPagamentoVO vo2 = new PbandiDModalitaPagamentoVO();
			vo2.setDescBreveModalitaPagamento(descrizioneBreve);
			List<PbandiDModalitaPagamentoVO> elenco2 = genericDAO.findListWhere(vo2);
			if (elenco2.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Descrizione breve gia' assegnata ad un'altra modalita' di pagamento");
				return esito;
			}
			
			boolean esitoInsert = pbandiBackOfficeDAO.inserisciModalitaPagamento(descrizione, descrizioneBreve);
			
			if (esitoInsert) {
				esito.setEsito(true);
				esito.setMessage(MessaggiConstants.OPERAZIONE_ESEGUITA_CON_SUCCESSO);	
			} else {
				throw new Exception("Errore in pbandiBackOfficeDAO.inserisciModalitaPagamento().");
			}		
						
		} catch (Exception e) {
			logger.error("Errore durante inserimento: ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
		
	}
	
	public DocPagamBandoLineaDTO[] findDocPagamAssociatiATuttiBL(
			Long idUtente,
			String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		
		try {
			
			DocPagamBandoLineaDTO[] elenco = pbandiBackOfficeDAO.findDocPagamAssociatiATuttiBL();
						
			// PBANDI_R_TIPO_DOC_MODALITA_PAG non ha un id progressivo univoco,
			// quindi ne creo uno nel campo DocPagamBandoLineaDTO.progrBandoLineaIntervento.
			long i = 1;
			if (elenco != null) {
				for (DocPagamBandoLineaDTO dto : elenco) {
					dto.setProgrEccezBanLinDocPag(new Long(i));
					i = i + 1;
				}
			}
			
			return elenco;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}
	
	public GestioneBackOfficeEsitoGenerico eliminaDocPagamAssociatoATuttiBL(
			Long idUtente, String identitaDigitale,
			java.lang.Long idTipoDocumento,
			java.lang.Long idModalitaPagamento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale", "idTipoDocumento", "idModalitaPagamento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idTipoDocumento, idModalitaPagamento);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {			

			pbandiBackOfficeDAO.eliminaDocPagamAssociatoATuttiBL(idTipoDocumento, idModalitaPagamento);
			
			esito.setEsito(true);
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione con idTipoDocumento = "+idTipoDocumento+" e idModalitaPagamento = "+idModalitaPagamento, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}
	
	// CDU-14 V04 fine
	
	// JIRA PBANDI-1390
	
	public LineaDiInterventiDaModificareDTO findLineaDiIntervento(Long idUtente,
			String identitaDigitale, Long progrBandoLineaIntervento) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			
			String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaIntervento);

			// Accede a Pbandi_R_Bando_Linea_Intervent per chiave;
			PbandiRBandoLineaInterventVO pbandiRBandoLineaInterventVO = new PbandiRBandoLineaInterventVO(); 			
			pbandiRBandoLineaInterventVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
			pbandiRBandoLineaInterventVO = genericDAO.findSingleOrNoneWhere(pbandiRBandoLineaInterventVO);
			if (pbandiRBandoLineaInterventVO == null)
				return null;
			logger.info("PBANDI_R_BANDO_LINEA_INTERVENT letto da DB:");
			logger.info(pbandiRBandoLineaInterventVO.toString());

			// Copia i dati da pbandiRBandoLineaInterventVO a LineaDiInterventiDaModificareDTO.
			Map<String, String> beanMap = new HashMap<String, String>();
			beanMap.put("idCategoriaCipe", "idCategoriaCIPE");
			beanMap.put("idObiettivoSpecifQsn", "idObiettivoSpecifQSN");			
			beanMap.put("idTipologiaCipe", "idTipologiaCIPE");
			LineaDiInterventiDaModificareDTO lineaDiInterventiDaModificareDTO = new LineaDiInterventiDaModificareDTO();
			beanUtil.valueCopy(pbandiRBandoLineaInterventVO, lineaDiInterventiDaModificareDTO);
			if (pbandiRBandoLineaInterventVO.getMesiDurataDaDtConcessione() != null)
				lineaDiInterventiDaModificareDTO.setMesiDurataDtConcessione(pbandiRBandoLineaInterventVO.getMesiDurataDaDtConcessione().doubleValue());
			lineaDiInterventiDaModificareDTO.setFlagSchedin(pbandiRBandoLineaInterventVO.getFlagSchedin());
			if (pbandiRBandoLineaInterventVO.getIdUnitaOrganizzativa() != null) {
				lineaDiInterventiDaModificareDTO.setIdUnitaOrganizzativa(pbandiRBandoLineaInterventVO.getIdUnitaOrganizzativa().longValue());
			}
			if (pbandiRBandoLineaInterventVO.getIdAreaScientifica() != null) {
				lineaDiInterventiDaModificareDTO.setIdAreaScientifica(pbandiRBandoLineaInterventVO.getIdAreaScientifica().longValue());
			}
			if (pbandiRBandoLineaInterventVO.getIdUtenteIns() != null) {
				lineaDiInterventiDaModificareDTO.setIdUtenteIns(pbandiRBandoLineaInterventVO.getIdUtenteIns().longValue());
			}
			if (pbandiRBandoLineaInterventVO.getIdUtenteAgg() != null) {
				lineaDiInterventiDaModificareDTO.setIdUtenteAgg(pbandiRBandoLineaInterventVO.getIdUtenteAgg().longValue());
			}
			
			// Ricava Normativa, Asse, Misura, Linea partendo da LineaDiIntervento.			
			if (pbandiRBandoLineaInterventVO.getIdLineaDiIntervento() != null) {
				
				BigDecimal idLineaDiIntervento = pbandiRBandoLineaInterventVO.getIdLineaDiIntervento();
				logger.info("pbandiRBandoLineaIntervent.IdLineaDiIntervento = "+idLineaDiIntervento);

				// Legge il dettaglio del bando linea per capirne il tipo (normativa, asse, etc).
				PbandiDLineaDiInterventoVO pbandiDLineaDiInterventoVO;
				pbandiDLineaDiInterventoVO = this.findPbandiDLineaDiInterventoVO(idLineaDiIntervento);
				if (pbandiDLineaDiInterventoVO == null) {
					logger.error("Dettaglio del bando linea nullo.");
					return null;
				}
				logger.info("pbandiDLineaDiIntervento.IdTipoLineaIntervento = "+pbandiDLineaDiInterventoVO.getIdTipoLineaIntervento());
				
				// Legge il tipo di linea
				PbandiDTipoLineaInterventoVO pbandiDTipoLineaInterventoVO = new PbandiDTipoLineaInterventoVO();
				pbandiDTipoLineaInterventoVO.setIdTipoLineaIntervento(pbandiDLineaDiInterventoVO.getIdTipoLineaIntervento());
				pbandiDTipoLineaInterventoVO = genericDAO.findSingleOrNoneWhere(pbandiDTipoLineaInterventoVO);
				if (pbandiDTipoLineaInterventoVO == null) {
					logger.error("Tipo Linea Intervento nullo.");
					return null;
				}				
				
				String descTipoLinea = pbandiDTipoLineaInterventoVO.getDescTipoLinea();
				logger.info("pbandiDTipoLineaIntervento.getDescTipoLinea = "+descTipoLinea);
												
				String descNormativa = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_NORMATIVA;
				String descAsse = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_ASSE;
				String descMisura = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_MISURA;
				String descLinea = it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESC_TIPO_LINEA_LINEA;
				
				PbandiDLineaDiInterventoVO normativa = null, asse = null, misura = null, linea = null;		
												
				if (descLinea.equals(descTipoLinea)) {					
					// Recupera misura, asse e normativa.
					linea = pbandiDLineaDiInterventoVO;
					misura = this.findPbandiDLineaDiInterventoVO(pbandiDLineaDiInterventoVO.getIdLineaDiInterventoPadre());
					asse = this.findPbandiDLineaDiInterventoVO(misura.getIdLineaDiInterventoPadre());
					normativa = this.findPbandiDLineaDiInterventoVO(asse.getIdLineaDiInterventoPadre());
				} else if (descMisura.equals(descTipoLinea)) {					
					// Recupera asse e normativa.
					misura = pbandiDLineaDiInterventoVO;
					asse = this.findPbandiDLineaDiInterventoVO(misura.getIdLineaDiInterventoPadre());
					normativa = this.findPbandiDLineaDiInterventoVO(asse.getIdLineaDiInterventoPadre());
				} else if (descAsse.equals(descTipoLinea)) {					
					// Recupera la normativa.
					asse = pbandiDLineaDiInterventoVO;
					normativa = this.findPbandiDLineaDiInterventoVO(asse.getIdLineaDiInterventoPadre());
				} else {
					normativa = pbandiDLineaDiInterventoVO;
				}
				
				// Assegna linea, misura, asse e normativa all'output.
				if (linea != null) {
					lineaDiInterventiDaModificareDTO.setIdLinea(linea.getIdLineaDiIntervento().longValue());
					logger.info("Id Linea = "+lineaDiInterventiDaModificareDTO.getIdLinea());
				}
				if (misura != null) {
					lineaDiInterventiDaModificareDTO.setIdMisura(misura.getIdLineaDiIntervento().longValue());
					logger.info("Id Misura = "+lineaDiInterventiDaModificareDTO.getIdMisura());
				}
				if (asse != null) {
					lineaDiInterventiDaModificareDTO.setIdAsse(asse.getIdLineaDiIntervento().longValue());
					logger.info("Id Asse = "+lineaDiInterventiDaModificareDTO.getIdAsse());
				}
				if (normativa != null) {
					lineaDiInterventiDaModificareDTO.setIdNormativa(normativa.getIdLineaDiIntervento().longValue());
					logger.info("Id Normativa = "+lineaDiInterventiDaModificareDTO.getIdNormativa());
				}
			} else {
				logger.info("pbandiRBandoLineaIntervent.IdLineaDiIntervento nullo.");
			}
			
			// Ricava priorita e obiettivo generale, partendo dall'obiettivo specifico.
			if (pbandiRBandoLineaInterventVO.getIdObiettivoSpecifQsn() != null) {
				
				BigDecimal idObiettivoSpecifQsn = pbandiRBandoLineaInterventVO.getIdObiettivoSpecifQsn();
				logger.info("pbandiRBandoLineaIntervent.IdObiettivoSpecifQsn = "+idObiettivoSpecifQsn);
				
				// Legge il dettaglio dell'obiettivo specifico per ricavare quello generale.
				PbandiDObiettivoSpecifQsnVO vo1 = new PbandiDObiettivoSpecifQsnVO();
				vo1.setIdObiettivoSpecifQsn(idObiettivoSpecifQsn);
				vo1 = genericDAO.findSingleWhere(vo1);

				BigDecimal idObiettivoGenQsn = vo1.getIdObiettivoGenQsn();
				logger.info("PbandiDObiettivoSpecifQsnVO.IdObiettivoSpecifQsn = "+idObiettivoGenQsn);
				
				// Legge il dettaglio dell'obiettivo generale per ricavare la priorita.
				PbandiDObiettivoGenQsnVO vo2 = new PbandiDObiettivoGenQsnVO();
				vo2.setIdObiettivoGenQsn(idObiettivoGenQsn);
				vo2 = genericDAO.findSingleWhere(vo2);
				
				BigDecimal idPrioritaQsn = vo2.getIdPrioritaQsn();
				logger.info("PbandiDObiettivoGenQsnVO.IdPrioritaQsn = "+idPrioritaQsn);
				
				// Assegna priorita, obiettivo generale e oviettivo specifico all'output.
				lineaDiInterventiDaModificareDTO.setIdObiettivoSpecifQSN(idObiettivoSpecifQsn.longValue());				
				lineaDiInterventiDaModificareDTO.setIdObiettivoGeneraleQSN(idObiettivoGenQsn.longValue());
				lineaDiInterventiDaModificareDTO.setIdPrioritaQSN(idPrioritaQsn.longValue());
				
			} else {
				logger.info("pbandiRBandoLineaIntervent.IdObiettivoSpecifQsn nullo.");
			}
			
			// CDU-13 V08: inizio
			// Ricava l'obiettivo tematico dalla classificazione ra.
			Long classificazioneRa = lineaDiInterventiDaModificareDTO.getIdClassificazioneRa();
			if (classificazioneRa != null) {
				PbandiRObtemClassraVO pbandiRObtemClassraVO = new PbandiRObtemClassraVO();
				pbandiRObtemClassraVO.setIdClassificazioneRa(new BigDecimal(classificazioneRa));
				pbandiRObtemClassraVO = genericDAO.findSingleOrNoneWhere(pbandiRObtemClassraVO);
				if (pbandiRObtemClassraVO != null &&
					pbandiRObtemClassraVO.getIdObiettivoTem() != null) {
					lineaDiInterventiDaModificareDTO.setIdObiettivoTem(pbandiRObtemClassraVO.getIdObiettivoTem().longValue());
				}
			}
			// CDU-13 V08: fine
			
			logger.info("lineaDiInterventiDaModificareDTO restituito in output:");
			logger.info(lineaDiInterventiDaModificareDTO.toString());
			
 
			
			return lineaDiInterventiDaModificareDTO;

		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}
	}
	
	private PbandiDLineaDiInterventoVO findPbandiDLineaDiInterventoVO(BigDecimal idLinea) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {

			PbandiDLineaDiInterventoVO vo = new PbandiDLineaDiInterventoVO();
			vo.setIdLineaDiIntervento(idLinea);
			vo = genericDAO.findSingleOrNoneWhere(vo);

			return vo;

	}
	
	// Nota: Preso spunto da associaLineaDiIntervento().
	public GestioneBackOfficeEsitoGenerico modificaLineaDiIntervento(Long idUtente,
			String identitaDigitale,
			LineaDiInterventiDaModificareDTO dettaglioLineaDiIntervento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		esito.setEsito(false);
		try {
			
			logger.info("LineaDiInterventiDaModificareDTO ricevuto in input:");
			logger.info(dettaglioLineaDiIntervento.toString());
			
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idBando", "idLineaIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, dettaglioLineaDiIntervento.getIdBando(),
					dettaglioLineaDiIntervento.getIdLineaDiIntervento());

			PbandiRBandoLineaInterventVO vo = beanUtil.transform(
					dettaglioLineaDiIntervento,
					PbandiRBandoLineaInterventVO.class,
					new HashMap<String, String>() {
						{
							put("nomeBandoLinea", "nomeBandoLinea");
						}
					});

			beanUtil.valueCopy(dettaglioLineaDiIntervento, vo);
			beanUtil.valueCopy(dettaglioLineaDiIntervento, vo,
					new HashMap<String, String>() {
						{
							put("mesiDurataDtConcessione","mesiDurataDaDtConcessione");						
						}
					});
			
			// CDU-013-V08: inizio
			// Su db flag_sif vale solo null o 'S'.
			if ("N".equals(vo.getFlagSif()))
				vo.setFlagSif(null);
			// Se livello istituzionale <> da 2 e da 3, flag_fondo_di_fondi non va valorizzato.
			if (vo.getIdLivelloIstituzione() == null ||
				(vo.getIdLivelloIstituzione().intValue() != 2 &&
				 vo.getIdLivelloIstituzione().intValue() != 3))
				vo.setFlagFondoDiFondi(null);
			// CDU-013-V08: fine
			
			logger.info("RECORD PBANDI_R_BANDO_LINEA_INTERVENT DA MODIFICARE:");
			logger.info(vo.toString());
			
			try {
				vo.setIdUtenteAgg(new BigDecimal(idUtente));
				//genericDAO.update(vo);
				genericDAO.updateNullables(vo);
				
				esito.setEsito(true);
				esito
						.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}
		
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare la modifica.", e);
		}  
		return esito;
	}
	
	// JIRA PBANDI-1390 fine
	
	// Legge le sezioni di un modello (verranno visualizzate nella popup di 
	// Configurazione Bando-Linea -> tab Modelli di documento 
	public SezioneDTO[] getSezioni(Long idUtente, String identitaDigitale,
			Long progrBandoLinea, Long idTipoDocumento)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		logger.info("Parametri in input: progrBandoLinea = "+progrBandoLinea+"; idTipoDocumento = "+idTipoDocumento);
			
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idSoggetto", "descBreveTipoAnagraficaSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, progrBandoLinea, idTipoDocumento);

			SezioneVO filtro = new SezioneVO();
			filtro.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
			filtro.setIdTipoDocumentoIndex(new BigDecimal(idTipoDocumento));
			
			List<SezioneVO> sezioniVO = genericDAO.findListWhere(filtro);
			logger.info("Num sezioni VO = "+sezioniVO.size());

			SezioneDTO[] sezioniDTO = new SezioneDTO[sezioniVO.size()];
			beanUtil.valueCopy(sezioniVO.toArray(), sezioniDTO);
			logger.info("Num sezioni DTO = "+sezioniDTO.length);
					
			return sezioniDTO;
			
		} catch (Exception e) {
			throw new UnrecoverableException("Impossibile effettuare la ricerca.", e);
		}  

	}
	
	// Salva su db le sezioni visualizzate nella popup di 
	// Configurazione Bando-Linea -> tab Modelli di documento .
	public EsitoAssociazione salvaSezioni(Long idUtente, String identitaDigitale, SezioneDTO[] sezioni)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		logger.info("\n\n\n  ************************  INIZIO salvaSezioni(): sezioni in input:"+sezioni.length);
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idSoggetto", "descBreveTipoAnagraficaSoggetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, sezioni);
			
			if (sezioni.length == 0) {
				logger.info("Elenco sezioni in input vuoto.");
				esito.setEsito(false);
				return esito;
			}
			
			// Progressivo e tipo documento sono uguali per tutte le sezioni.
			BigDecimal progrBandoLineaIntervento = new BigDecimal(sezioni[0].getProgrBandoLineaIntervento());
			BigDecimal idTipoDocumentoIndex = new BigDecimal(sezioni[0].getIdTipoDocumentoIndex());
			ValidatorInput.verifyNullValue(nameParameter, progrBandoLineaIntervento, idTipoDocumentoIndex);
			logger.info("progrBandoLineaIntervento = "+progrBandoLineaIntervento+"; idTipoDocumentoIndex = "+idTipoDocumentoIndex);
			
			// Legge le macro sezioni associate al progressivo\tipoDoc.			
			PbandiRBlTipoDocSezModVO filtroMacro = new PbandiRBlTipoDocSezModVO();
			filtroMacro.setProgrBandoLineaIntervento(progrBandoLineaIntervento);
			filtroMacro.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			List<PbandiRBlTipoDocSezModVO> macro = genericDAO.findListWhere(filtroMacro);
			logger.info("Numero macro sezioni trovate = "+macro.size()+"\n");
			
			// Cancella le associazioni tra ciascuna macro sezione e le sue micro sezioni.
			logger.info("Cancello le associazioni tra le macro sezioni e le loro micro sezioni.");
			for (PbandiRBlTipoDocSezModVO m : macro) {								
				PbandiRBlTipoDocMicroSezVO filtro = new PbandiRBlTipoDocMicroSezVO();
				filtro.setProgrBlTipoDocSezMod(m.getProgrBlTipoDocSezMod());
				if (filtro.getProgrBlTipoDocSezMod() == null)
					throw new Exception("ProgrBlTipoDocSezMod NULLO.");				
				genericDAO.deleteWhere(new FilterCondition<PbandiRBlTipoDocMicroSezVO>(filtro));
			}
			logger.info("Fine cancellazione.\n");
			
			// Scorro le micro sezioni ricevute in input.
			logger.info("Scorro le micro sezioni ricevute in input.\n");
			for (int i = 0; i < sezioni.length; i++) {
				
				SezioneDTO sezione = sezioni[i];
				logger.info("Sezione corrente: "+sezione.toString());
				
				if (sezione.getProgrBlTipoDocSezMod() == null || sezione.getProgrBlTipoDocSezMod() == 0)
					throw new Exception("ProgrBlTipoDocSezMod non valorizzato");
				if (sezione.getNumOrdinamentoMicroSezione() == null || sezione.getNumOrdinamentoMicroSezione() == 0)
					throw new Exception("NumOrdinamentoMicroSezione non valorizzato");
				
				BigDecimal progrBlTipoDocSezMod = new BigDecimal(sezione.getProgrBlTipoDocSezMod());
				BigDecimal ordinamentoMicroSez = new BigDecimal(sezione.getNumOrdinamentoMicroSezione());
				String contenuto = sezione.getContenutoMicroSezione();
				String descrizione = sezione.getDescMicroSezione();
				
				BigDecimal idMicroSez = null;
				if (sezioni[i].getIdMicroSezioneModulo() != null &&
					sezioni[i].getIdMicroSezioneModulo() > 0)
					idMicroSez = new BigDecimal(sezioni[i].getIdMicroSezioneModulo());
								
				if (idMicroSez == null) {
					// Si tratta di una micro sezione aggiunta ex-novo dall'utente.
					
					// Inserisco la micro sezione (PBANDI_D_MICRO_SEZIONE_MODULO).
					// ID_MICRO_SEZIONE_MODULO	 popolato con sequence SEQ_PBANDI_D_MICRO_SEZ_MODULO (vedi SequenceManager.java)
					logger.info("idMicroSez non valorizzato: inserisco una nuova microsezione.");
					java.util.Date d = new java.util.Date();
					java.sql.Date oggi = new java.sql.Date(d.getTime());
					PbandiDMicroSezioneModuloVO microSez = new PbandiDMicroSezioneModuloVO();
					microSez.setContenutoMicroSezione(contenuto);
					microSez.setDescMicroSezione(descrizione);
					microSez.setDtInizioValidita(oggi);
					microSez = genericDAO.insert(microSez);
					
					if (microSez == null || microSez.getIdMicroSezioneModulo() == null)
						throw new Exception("Fallita insert su PBANDI_D_MICRO_SEZIONE_MODULO");
					
					idMicroSez = microSez.getIdMicroSezioneModulo();
					logger.info("idMicroSez del record inserito: "+idMicroSez);
									
				} else {
					// Si tratta di una micro sezione già esistente.
					
					// Aggiorno il contenuto della micro sezione.
					logger.info("idMicroSez = "+idMicroSez+": ne modifico il contenuto.");
					PbandiDMicroSezioneModuloVO microSez = new PbandiDMicroSezioneModuloVO();
					microSez.setIdMicroSezioneModulo(idMicroSez);
					microSez.setContenutoMicroSezione(contenuto);
					genericDAO.update(microSez);
					
				}
				
				// Inserisco l'associazione tra macro sezione e micro sezione.
				logger.info("Inserisco una nuova associazione tra macro sezione e micro sezione.");
				PbandiRBlTipoDocMicroSezVO associazione = new PbandiRBlTipoDocMicroSezVO();
				associazione.setProgrBlTipoDocSezMod(progrBlTipoDocSezMod);
				associazione.setIdMicroSezioneModulo(idMicroSez);
				associazione.setNumOrdinamentoMicroSezione(ordinamentoMicroSez);
				associazione = genericDAO.insert(associazione);
				
				logger.info("Fine elaborazione sezione corrente.\n");
				
			}	// fine ciclo for settori			
			
			esito.setEsito(true);
			
			logger.info("\n\n\n  ************************  FINE salvaSezioni()\n\n");
			
		} catch (Exception e) {
			logger.error("ERRORE in salvaSezioni(): "+e);
			throw new GestioneBackOfficeException("Errore durante il salvataggio delle sezioni.", e);
		}
		
		return esito;

	}
	
	public String[] findPlaceholder(Long idUtente, String identitaDigitale, Long idTipoDocumento)
		throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
				
		String prefisso = "$P{";
		String suffisso = "}";
		
		// Cerco tutti i contenuti delle micro sezioni associate al tipo doc in input.
		SezioneContenutoVO vo = new SezioneContenutoVO();
		vo.setIdTipoDocumentoIndex(idTipoDocumento);			
		List<SezioneContenutoVO> contenuti = genericDAO.findListWhere(vo);
		
		// Scorro i contenuti delle micro sezioni per estrarre i placeholder contenuti in essi.
		// Un placeholder è una stringa del tipo $P{stringa} 
		ArrayList<String> listaPlaceholder = new ArrayList<String>(); 
		String contenuto = "";
		int posizionePrefisso = -1, posizioneSuffisso = -1;
		for (SezioneContenutoVO c : contenuti) {
			contenuto = c.getContenutoMicroSezione();
			posizionePrefisso = contenuto.indexOf(prefisso);
			int numeroIterazioni = 1;
			while (posizionePrefisso != -1) {
				posizioneSuffisso = contenuto.indexOf(suffisso, posizionePrefisso);
				listaPlaceholder.add(contenuto.substring(posizionePrefisso, posizioneSuffisso+suffisso.length()));
				posizionePrefisso = contenuto.indexOf(prefisso, posizioneSuffisso+1);
				if (numeroIterazioni++ > 100) {
					// Il ciclo while è andato in loop, lo interrompo.
					logger.error("Raggiunte le 100 iterazioni del ciclo while: interrompo.");
					break;
				}
			}
		}
		
		// Elimino doppioni.
		java.util.Set mySet = new HashSet(listaPlaceholder);
		listaPlaceholder = new ArrayList(mySet);
		
		// Metto in ordine alfabetico.
		Collections.sort(listaPlaceholder);
		
		logger.info("Lista placeholder:");
		for (String s : listaPlaceholder)
			logger.info("Placeholder = "+s);

		return listaPlaceholder.toArray(new String[listaPlaceholder.size()]);
	}
	
	// CDU-14 V07: inizio
	
	public TipologiaBeneficiarioDTO[] findTipolBeneficiario(Long idUtente, String identitaDigitale)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		
		PbandiDTipolBeneficiariVO filtro = new PbandiDTipolBeneficiariVO();
		filtro.setAscendentOrder("descrizione");
		List<PbandiDTipolBeneficiariVO> tipolBeneficiariVO = genericDAO.findListWhere(filtro);

		TipologiaBeneficiarioDTO[] tipolBeneficiariDTO = new TipologiaBeneficiarioDTO[tipolBeneficiariVO.size()];
		beanUtil.valueCopy(tipolBeneficiariVO.toArray(), tipolBeneficiariDTO);

		return tipolBeneficiariDTO;
	}
	
	public EsitoAssociazione associaTipolBeneficiario(Long idUtente,
			String identitaDigitale, Long progrBandoLinea, Long idTipolBeneficiario)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale",
					"progrBandoLinea", "idTipolBeneficiario" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					identitaDigitale, progrBandoLinea, idTipolBeneficiario);

			try {
				PbandiRTipolBenBandiVO vo = new PbandiRTipolBenBandiVO();
				vo.setIdTipolBeneficiario(new BigDecimal(idTipolBeneficiario));
				vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));				
				vo = genericDAO.insert(vo);
				esito.setEsito(true);
				esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}
	
	public GestioneBackOfficeEsitoGenerico eliminaTipolBeneficiarioAssociato(
			Long idUtente, String identitaDigitale,
			Long progrBandoLinea, Long idTipolBeneficiario)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"progrBandoLinea", "idTipolBeneficiario" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, progrBandoLinea, idTipolBeneficiario);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRTipolBenBandiVO vo = new PbandiRTipolBenBandiVO();
			vo.setIdTipolBeneficiario(new BigDecimal(idTipolBeneficiario));
			vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));	
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger
					.error(
							"Errore durante la cancellazione dell'associazione (progrBandoLinea = "
									+ progrBandoLinea
									+ ", idTipolBeneficiario = "
									+ idTipolBeneficiario + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}
	
	public TipologiaBeneficiarioDTO[] findTipolBeneficiariAssociate(Long idUtente, String identitaDigitale, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLinea);
		
		// Trova le tipol beneficiario associate al bando.
		PbandiRTipolBenBandiVO vo1 = new PbandiRTipolBenBandiVO();
		vo1.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
		List<PbandiRTipolBenBandiVO> listaVO = genericDAO.findListWhere(vo1);
		
		// Trova i dettagli delle tipologie beneficiario trovate.
		TipologiaBeneficiarioDTO[] listaDTO = new TipologiaBeneficiarioDTO[listaVO.size()];
		int i = 0;
		for (PbandiRTipolBenBandiVO tipol : listaVO) {
			PbandiDTipolBeneficiariVO vo2 = new PbandiDTipolBeneficiariVO();
			vo2.setIdTipolBeneficiario(tipol.getIdTipolBeneficiario());
			vo2 = genericDAO.findSingleWhere(vo2);
						
			TipologiaBeneficiarioDTO dto = new TipologiaBeneficiarioDTO();
			beanUtil.valueCopy(vo2, dto);
			listaDTO[i++] = dto;
			}

		return listaDTO;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione associaSportelloBando(
			Long idUtente, String identitaDigitale, SportelloBandoDTO sportelloBando)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		esito.setEsito(false);
		try {
			
			String[] nameParameter = { "idUtente", "identitaDigitale", "sportelloBando" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, sportelloBando);

			try {
				PbandiTSportelliBandiVO vo = new PbandiTSportelliBandiVO();
				beanUtil.valueCopy(sportelloBando, vo);
				// idSportelloBando popolato con sequence SEQ_PBANDI_T_SPORTELLI_BANDI (vedi SequenceManager.java)
				vo = genericDAO.insert(vo);
				esito.setEsito(true);
				esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			} catch (Exception e) {
				logger.error("Errore durante la insert. ", e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
			}

		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"Impossibile effettuare l'inserimento.", e);
		}  
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaSportelloBandoAssociato(
			Long idUtente, String identitaDigitale, Long idSportelloBando)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idSportelloBando" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idSportelloBando);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiTSportelliBandiVO vo = new PbandiTSportelliBandiVO();
			vo.setIdSportelloBando(new BigDecimal(idSportelloBando));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione dell'associazione (idSportelloBando = "+ idSportelloBando, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.SportelloBandoDTO[] findSportelliBandoAssociati(
			Long idUtente, String identitaDigitale, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
	
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLinea);
		
		// Trova le tipol beneficiario associate al bando.
		PbandiTSportelliBandiVO filtro = new PbandiTSportelliBandiVO();
		filtro.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
		List<PbandiTSportelliBandiVO> sportelliVO = genericDAO.findListWhere(filtro);
		
		SportelloBandoDTO[] sportelliDTO = new SportelloBandoDTO[sportelliVO.size()];
		beanUtil.valueCopy(sportelliVO.toArray(), sportelliDTO);

		return sportelliDTO;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.PremialitaDTO[] findPremialita(
			Long idUtente, String identitaDigitale)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		
		// Leggo la tabella PBANDI_D_PREMIALITA.
		PbandiDPremialitaVO filtro = new PbandiDPremialitaVO();
		filtro.setAscendentOrder("descrizione");
		List<PbandiDPremialitaVO> premialitaVO = genericDAO.findListWhere(filtro);

		PremialitaDTO[] premialitaDTO = new PremialitaDTO[premialitaVO.size()];
		beanUtil.valueCopy(premialitaVO.toArray(), premialitaDTO);

		return premialitaDTO;

	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione inserisciPremialita(
			Long idUtente, String identitaDigitale, PremialitaDTO premialita)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "premialita" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, premialita);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiDPremialitaVO vo = new PbandiDPremialitaVO();
			beanUtil.valueCopy(premialita, vo);
			// idPremialita popolato con sequence SEQ_PBANDI_D_PREMIALITA (vedi SequenceManager.java)
			vo.setIdPremialita(null);
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setIdAssociazione(vo.getIdPremialita().longValue());
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaPremialita(
			Long idUtente, String identitaDigitale, Long idPremialita)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idPremialita" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idPremialita);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();		
		try {
			
			// Verifica che la premialita non sia già associata a qualche bando.
			PbandiRBandiPremialitaVO filtro = new PbandiRBandiPremialitaVO();
			filtro.setIdPremialita(new BigDecimal(idPremialita));
			List<PbandiRBandiPremialitaVO> lista = genericDAO.findListWhere(filtro);
			if (lista.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("La premialit&agrave; non pu&ograve; essere cancellata poich&egrave; gi&agrave; associata ad un bando.");
				return esito;
			}
			
			PbandiDPremialitaVO vo = new PbandiDPremialitaVO();
			vo.setIdPremialita(new BigDecimal(idPremialita));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione della premialita con id = "+idPremialita, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.PremialitaDTO[] findPremialitaAssociate(
			Long idUtente, String identitaDigitale, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLinea);
		
		PremialitaAssociataVO vo = new PremialitaAssociataVO();
		vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
		List<PremialitaAssociataVO> listaVO = genericDAO.findListWhere(vo);
		
		PremialitaDTO[] listaDTO = new PremialitaDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		
		return listaDTO;
		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione associaPremialita(
			Long idUtente, String identitaDigitale, Long idPremialita, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idPremialita", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idPremialita, progrBandoLinea);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiRBandiPremialitaVO vo = new PbandiRBandiPremialitaVO();
			vo.setIdPremialita(new BigDecimal(idPremialita));
			vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;

	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaPremialitaAssociata(
			Long idUtente, String identitaDigitale, Long idPremialita, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idPremialita", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idPremialita, progrBandoLinea);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandiPremialitaVO vo = new PbandiRBandiPremialitaVO();
			vo.setIdPremialita(new BigDecimal(idPremialita));
			vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione della premialita associata con idPremialita = "+idPremialita+" e progrBandoLinea = "+progrBandoLinea, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione inserisciAllegato(
			Long idUtente, String identitaDigitale, String descrizione)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "descrizione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, descrizione);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiDAllegatiVO vo = new PbandiDAllegatiVO();
			vo.setDescrizione(descrizione);
			vo.setAscendentOrder("descrizione");
			// idAllegato popolato con sequence SEQ_PBANDI_D_ALLEGATI (vedi SequenceManager.java)
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setIdAssociazione(vo.getIdAllegato().longValue());
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaAllegato(
			Long idUtente, String identitaDigitale, Long idAllegato)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idAllegato" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idAllegato);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			
			// Verifica che l'allegato non sia già associata a qualche bando.
			PbandiRBandiAllegatiVO filtro = new PbandiRBandiAllegatiVO();
			filtro.setIdAllegato(new BigDecimal(idAllegato));
			List<PbandiRBandiAllegatiVO> lista = genericDAO.findListWhere(filtro);
			if (lista.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("L&rsquo;allegato non pu&ograve; essere cancellato poich&egrave; gi&agrave; associato ad un bando.");
				return esito;
			}
			
			PbandiDAllegatiVO vo = new PbandiDAllegatiVO();
			vo.setIdAllegato(new BigDecimal(idAllegato));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione dell'allegato con id = "+idAllegato, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.AllegatoAssociatoDTO[] findAllegatiAssociati(
			Long idUtente, String identitaDigitale, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLinea);
		
		// Trova gli allegati associati al bando.
		AllegatoAssociatoVO vo = new AllegatoAssociatoVO();
		vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
		List<AllegatoAssociatoVO> listaVO = genericDAO.findListWhere(vo);

		AllegatoAssociatoDTO[] listaDTO = new AllegatoAssociatoDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		
		return listaDTO;
		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione associaAllegato(
			Long idUtente, String identitaDigitale, AllegatoAssociatoDTO allegato)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "allegato" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, allegato);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiRBandiAllegatiVO vo = new PbandiRBandiAllegatiVO();
			beanUtil.valueCopy(allegato, vo);
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaAllegatoAssociato(
			Long idUtente, String identitaDigitale, Long idAllegato, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idAllegato", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idAllegato, progrBandoLinea);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandiAllegatiVO vo = new PbandiRBandiAllegatiVO();
			vo.setIdAllegato(new BigDecimal(idAllegato));
			vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione dell'allegato associato con idAllegato = "+idAllegato+" e progrBandoLinea = "+progrBandoLinea, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione inserisciDettTipolAiuti(
			Long idUtente, String identitaDigitale, DettTipolAiutiAssociatoDTO dettTipolAiuti)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "dettTipolAiuti" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, dettTipolAiuti);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			
			// Verifica che non esista gi� un dettaglio con codice e id_tipo_aiuto uguali.
			PbandiDDettTipolAiutiVO filtro = new PbandiDDettTipolAiutiVO();
			filtro.setCodice(dettTipolAiuti.getCodice());
			filtro.setIdTipoAiuto(new BigDecimal(dettTipolAiuti.getIdTipoAiuto()));
			List<PbandiDDettTipolAiutiVO> lista = genericDAO.findListWhere(filtro);
			if (lista.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Il dettaglio tipologia di aiuto non pu&ograve; essere inserito poich&egrave; ne esiste gi&agrave; un altro con gli stessi campi Codice e Tipo di Aiuto.");
				return esito;
			}
			
			PbandiDDettTipolAiutiVO vo = new PbandiDDettTipolAiutiVO();
			beanUtil.valueCopy(dettTipolAiuti, vo);
			// IdDettTipolAiuti popolato con sequence SEQ_PBANDI_D_DETT_TIPOL_AIUTI (vedi SequenceManager.java)
			vo.setIdDettTipolAiuti(null);
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setIdAssociazione(vo.getIdDettTipolAiuti().longValue());
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaDettTipolAiuti(
			Long idUtente, String identitaDigitale, Long idDettTipolAiuti)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDettTipolAiuti" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDettTipolAiuti);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			
			// Verifica che il dettaglio non sia già associato a qualche bando.
			PbandiRBandiDettTipAiutiVO filtro = new PbandiRBandiDettTipAiutiVO();
			filtro.setIdDettTipolAiuti(new BigDecimal(idDettTipolAiuti));
			List<PbandiRBandiDettTipAiutiVO> lista = genericDAO.findListWhere(filtro);
			if (lista.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Il dettaglio tipologia di aiuto non pu&ograve; essere cancellato poich&egrave; gi&agrave; associato ad un bando.");
				return esito;
			}
			
			PbandiDDettTipolAiutiVO vo = new PbandiDDettTipolAiutiVO();
			vo.setIdDettTipolAiuti(new BigDecimal(idDettTipolAiuti));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione dell'allegato con id = "+idDettTipolAiuti, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DettTipolAiutiAssociatoDTO[] findDettTipolAiutiAssociati(
			Long idUtente, String identitaDigitale, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLinea);
		
		// Trova i dettagli associati al bando.
		DettTipolAiutiAssociatoVO vo = new DettTipolAiutiAssociatoVO();
		vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
		List<DettTipolAiutiAssociatoVO> listaVO = genericDAO.findListWhere(vo);

		DettTipolAiutiAssociatoDTO[] listaDTO = new DettTipolAiutiAssociatoDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		
		return listaDTO;
		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione associaDettTipolAiuti(
			Long idUtente, String identitaDigitale, Long idDettTipolAiuti, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idDettTipolAiuti", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDettTipolAiuti, progrBandoLinea);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiRBandiDettTipAiutiVO vo = new PbandiRBandiDettTipAiutiVO();
			vo.setIdDettTipolAiuti(new BigDecimal(idDettTipolAiuti));
			vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaDettTipolAiutiAssociato(
			Long idUtente, String identitaDigitale, Long idDettTipolAiuti, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDettTipolAiuti", "progrBandoLinea" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDettTipolAiuti, progrBandoLinea);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandiDettTipAiutiVO vo = new PbandiRBandiDettTipAiutiVO();
			vo.setIdDettTipolAiuti(new BigDecimal(idDettTipolAiuti));
			vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito
					.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione del dettaglio tipologia aiuti associato con idDettTipolAiuti = "+idDettTipolAiuti+" e progrBandoLinea = "+progrBandoLinea, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DettTipolAiutiAssociatoDTO[] findDettTipolAiutiByProgrBandoLinea(
			Long idUtente, String identitaDigitale, Long progrBandoLinea)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLinea"};
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLinea);
		
		DettTipolAiutiVO vo = new DettTipolAiutiVO();
		vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
		List<DettTipolAiutiVO> listaVO = genericDAO.findListWhere(vo);

		DettTipolAiutiAssociatoDTO[] listaDTO = new DettTipolAiutiAssociatoDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		
		return listaDTO;
		
	}
	
	// Restituisce la PEC
	//  - tabella: 1 = PBANDI_T_ENTE_COMPETENZA; 2 = PBANDI_D_SETTORE_ENTE
	public String findPecById(Long idUtente, String identitaDigitale, Long id, Integer tabella)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "id", "tabella" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, id, tabella);
		
		String pec = "";
		if (tabella.intValue() == 1) {
			// Leggo la pec da PBANDI_T_ENTE_COMPETENZA.
			PbandiTEnteCompetenzaVO vo = new PbandiTEnteCompetenzaVO();
			vo.setIdEnteCompetenza(new BigDecimal(id));
			vo = genericDAO.findSingleWhere(vo);
			if (vo != null)
				pec = vo.getIndirizzoMailPec();
		} 
		if (tabella.intValue() == 2) {
			// Leggo la pec da PBANDI_D_SETTORE_ENTE.
			PbandiDSettoreEnteVO vo = new PbandiDSettoreEnteVO();
			vo.setIdSettoreEnte(new BigDecimal(id));
			vo = genericDAO.findSingleWhere(vo);
			if (vo != null)
				pec = vo.getIndirizzoMailPec();
		}
				
		return pec;
	}
	
	// Verifica se esistono progetti associati ad un bando o ad un bando-linea.
	public Boolean esisteProgettoBando(Long idUtente, String identitaDigitale, Long idBando, Long progrBandoLineaIntervento)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		EsisteProgettoBandoVO vo = new EsisteProgettoBandoVO();
		
		if (idBando != null)
			vo.setIdBando(new BigDecimal(idBando));
		if (progrBandoLineaIntervento != null)
			vo.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLineaIntervento));
		
		List<EsisteProgettoBandoVO> lista = genericDAO.findListWhere(vo); 
				
		return (lista.size() > 0);
	}
	
	// CDU-14 V07: fine
	
	
	// CDU-13 V07: inizio
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione inserisciCampoIntervento(
			Long idUtente, String identitaDigitale, String codice, String descrizione)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "codice", "descrizione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, codice, descrizione);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiDCampiInterventoVO vo = new PbandiDCampiInterventoVO();
			vo.setCodCampoIntervento(codice);
			vo.setDescrizione(descrizione);
			// idCampoIntervento popolato con sequence SEQ_PBANDI_D_CAMPI_INTERVENTO (vedi SequenceManager.java)
			vo.setIdCampoIntervento(null);
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setIdAssociazione(vo.getIdCampoIntervento().longValue());
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaCampoIntervento(
			Long idUtente, String identitaDigitale, Long idCampoIntervento)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idCampoIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idCampoIntervento);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			
			// Verifica che il campo intervento non sia gi� associato.
			PbandiDTipolInterventiVO filtro = new PbandiDTipolInterventiVO();
			filtro.setIdCampoIntervento(new BigDecimal(idCampoIntervento));
			List<PbandiDTipolInterventiVO> lista = genericDAO.findListWhere(filtro);
			if (lista.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Il campo di intervento non pu&ograve; essere cancellato poich&egrave; gi&agrave; associato ad una tipologia di intervento.");
				return esito;
			}
			PbandiDDettTipolInterventiVO filtroDett = new PbandiDDettTipolInterventiVO();
			filtroDett.setIdCampoIntervento(new BigDecimal(idCampoIntervento));
			List<PbandiDDettTipolInterventiVO> listaDett = genericDAO.findListWhere(filtroDett);
			if (listaDett.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Il campo di intervento non pu&ograve; essere cancellato poich&egrave; gi&agrave; associato ad un dettaglio tipologia di intervento.");
				return esito;
			}
		
			PbandiDCampiInterventoVO vo = new PbandiDCampiInterventoVO();
			vo.setIdCampoIntervento(new BigDecimal(idCampoIntervento));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione del campo intervento con id = "+idCampoIntervento, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.TipolInterventoDTO[] findTipolInterventi(Long idUtente,String identitaDigitale)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
	
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		
		PbandiDTipolInterventiVO filtro = new PbandiDTipolInterventiVO();
		filtro.setAscendentOrder("descrizione");
		List<PbandiDTipolInterventiVO> listaVO = genericDAO.findListWhere(filtro);
	
		TipolInterventoDTO[] listaDTO = new TipolInterventoDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
	
		return listaDTO;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione inserisciTipolInterventi(
			Long idUtente, String identitaDigitale, TipolInterventoDTO tipolIntervento)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "tipolIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, tipolIntervento);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiDTipolInterventiVO vo = new PbandiDTipolInterventiVO();
			beanUtil.valueCopy(tipolIntervento, vo);
			// idTipolIntervento popolato con sequence SEQ_PBANDI_D_TIPOL_INTERVENTI (vedi SequenceManager.java)
			vo.setIdTipolIntervento(null);
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setIdAssociazione(vo.getIdTipolIntervento().longValue());
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaTipolInterventi(
			Long idUtente, String identitaDigitale, Long idTipolIntervento)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idTipolIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idTipolIntervento);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
		
			// Verifica che il tipo intervento non sia gi� associato.
			PbandiDDettTipolInterventiVO filtro = new PbandiDDettTipolInterventiVO();
			filtro.setIdTipolIntervento(new BigDecimal(idTipolIntervento));
			List<PbandiDDettTipolInterventiVO> lista = genericDAO.findListWhere(filtro);
			if (lista.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("La tipologia di intervento non pu&ograve; essere cancellata poich&egrave; gi&agrave; associata ad un dettaglio tipologia di intervento.");
				return esito;
			}
			PbandiRBandiTipolIntervVO filtro1 = new PbandiRBandiTipolIntervVO();
			filtro1.setIdTipolIntervento(new BigDecimal(idTipolIntervento));
			List<PbandiRBandiTipolIntervVO> lista1 = genericDAO.findListWhere(filtro1);
			if (lista1.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("La tipologia di intervento non pu&ograve; essere cancellata poich&egrave; gi&agrave; associata ad un bando.");
				return esito;
			}
			PbandiRTipolIntervVociSpeVO filtro2 = new PbandiRTipolIntervVociSpeVO();
			filtro2.setIdTipolIntervento(new BigDecimal(idTipolIntervento));
			List<PbandiRTipolIntervVociSpeVO> lista2 = genericDAO.findListWhere(filtro2);
			if (lista2.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("La tipologia di intervento non pu&ograve; essere cancellata poich&egrave; gi&agrave; associata ad una voce di spesa.");
				return esito;
			}
		
			PbandiDTipolInterventiVO vo = new PbandiDTipolInterventiVO();
			vo.setIdTipolIntervento(new BigDecimal(idTipolIntervento));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);

		} catch (Exception e) {
			logger.error("Errore durante la cancellazione della tipologia intervento con id = "+idTipolIntervento, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
	
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaDettTipolInterventi(
			Long idUtente, String identitaDigitale, Long idDettTipolIntervento)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idDettTipolIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDettTipolIntervento);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			
			// Verifica che il campo intervento non sia gi� associato.
			PbandiRDettTipolIntVocSpVO filtro = new PbandiRDettTipolIntVocSpVO();
			filtro.setIdDettTipolIntervento(new BigDecimal(idDettTipolIntervento));
			List<PbandiRDettTipolIntVocSpVO> lista = genericDAO.findListWhere(filtro);
			if (lista.size() > 0) {
				esito.setEsito(false);
				esito.setMessage("Il dettaglio di intervento non pu&ograve; essere cancellato poich&egrave; gi&agrave; associato ad una voce di spesa.");
				return esito;
			}
		
			PbandiDDettTipolInterventiVO vo = new PbandiDDettTipolInterventiVO();
			vo.setIdDettTipolIntervento(new BigDecimal(idDettTipolIntervento));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);

		} catch (Exception e) {
			logger.error("Errore durante la cancellazione del dettaglio intervento con id = "+idDettTipolIntervento, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.DettTipolInterventoDTO[] findDettTipolInterventi(Long idUtente,String identitaDigitale)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
	
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		
		PbandiDDettTipolInterventiVO filtro = new PbandiDDettTipolInterventiVO();
		filtro.setAscendentOrder("descrizione");
		List<PbandiDDettTipolInterventiVO> listaVO = genericDAO.findListWhere(filtro);
	
		DettTipolInterventoDTO[] listaDTO = new DettTipolInterventoDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
	
		return listaDTO;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione inserisciDettTipolInterventi(
			Long idUtente, String identitaDigitale, DettTipolInterventoDTO dettTipolIntervento)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "dettTipolIntervento" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, dettTipolIntervento);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiDDettTipolInterventiVO vo = new PbandiDDettTipolInterventiVO();
			beanUtil.valueCopy(dettTipolIntervento, vo);
			// idDettTipolIntervento popolato con sequence SEQ_PBANDI_D_DETT_TIPOL_INTERV (vedi SequenceManager.java)
			vo.setIdDettTipolIntervento(null);
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setIdAssociazione(vo.getIdDettTipolIntervento().longValue());
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.InterventoAssociatoDTO[] findInterventiAssociati(
			Long idUtente, String identitaDigitale, Long idBando)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBando" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idBando);
		
		// Trova gli interventi associati al bando.
		InterventoAssociatoVO vo = new InterventoAssociatoVO();
		vo.setIdBando(new BigDecimal(idBando));
		List<InterventoAssociatoVO> listaVO = genericDAO.findListWhere(vo);

		InterventoAssociatoDTO[] listaDTO = new InterventoAssociatoDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		
		return listaDTO;	
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione associaIntervento(
			Long idUtente, String identitaDigitale, Long idTipolIntervento, Long idBando)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idTipolIntervento", "idBando" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idTipolIntervento, idBando);
		
		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			PbandiRBandiTipolIntervVO vo = new PbandiRBandiTipolIntervVO();
			vo.setIdTipolIntervento(new BigDecimal(idTipolIntervento));
			vo.setIdBando(new BigDecimal(idBando));
			vo = genericDAO.insert(vo);
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;		
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico eliminaInterventoAssociato(
			Long idUtente, String identitaDigitale,  Long idTipolIntervento, Long idBando)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idTipolIntervento", "idBando" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idTipolIntervento, idBando);
		
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			PbandiRBandiTipolIntervVO vo = new PbandiRBandiTipolIntervVO();
			vo.setIdTipolIntervento(new BigDecimal(idTipolIntervento));
			vo.setIdBando(new BigDecimal(idBando));
			esito.setEsito(genericDAO.delete(vo));
			if (!esito.getEsito()) {
				throw new Exception();
			}
			esito.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la cancellazione dell'associazione con idTipolIntervento = "+idTipolIntervento+" e idBando = "+idBando, e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		
		return esito;
	}
	
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.EsitoAssociazione creaMacroVoceDiSpesa(
			Long idUtente, String identitaDigitale, InterventoAssociatoDTO interventoAssociato)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "interventoAssociato" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, interventoAssociato);

		EsitoAssociazione esito = new EsitoAssociazione();
		try {
			
			java.util.Date d = new java.util.Date();
			java.sql.Date oggi = new java.sql.Date(d.getTime());
			
			boolean dettaglioInterventoPresente = (interventoAssociato.getIdDettTipolIntervento() != null);
			
			// Crea una nuova voce di spesa (la macro voce di spesa).
			PbandiDVoceDiSpesaVO vo = new PbandiDVoceDiSpesaVO();
			// idVoceDiSpesa popolato con sequence SEQ_PBANDI_D_VOCE_DI_SPESA (vedi SequenceManager.java)
			vo.setDtInizioValidita(oggi);
			if (dettaglioInterventoPresente) {
				vo.setDescVoceDiSpesa(interventoAssociato.getDescTipolIntervento()+"-"+interventoAssociato.getDescDettTipolIntervento());
				vo.setCodTipoVoceDiSpesa(interventoAssociato.getCodiceDettTipolIntervento());				
			} else {
				vo.setDescVoceDiSpesa(interventoAssociato.getDescTipolIntervento());
				vo.setCodTipoVoceDiSpesa(interventoAssociato.getCodiceTipolIntervento());
			}
			logger.info("Inserisco una nuova voce di spesa.");
			vo = genericDAO.insert(vo);
			
			// Id del record appena creato
			BigDecimal idVoceDiSpesa = vo.getIdVoceDiSpesa();
			logger.info("Id della nuova voce di spesa: "+idVoceDiSpesa);
			
			esito.setEsito(true);
			esito.setIdAssociazione(idVoceDiSpesa.longValue());
			
			// Assegna la macro voce di spesa appena creata alla tipologia o al dettaglio intervento.
			if (dettaglioInterventoPresente) {
				Long idDett = interventoAssociato.getIdDettTipolIntervento();
				logger.info("Assegno l'id della nuova voce di spesa al dettaglio tipologia intervento con id = "+idDett);
				PbandiDDettTipolInterventiVO dett = new PbandiDDettTipolInterventiVO();
				dett.setIdDettTipolIntervento(new BigDecimal(idDett));
				dett.setIdMacroVoce(idVoceDiSpesa);
				genericDAO.update(dett);
			} else {
				Long idTipol = interventoAssociato.getIdTipolIntervento();
				logger.info("Assegno l'id della nuova voce di spesa alla tipologia intervento con id = "+idTipol);
				PbandiDTipolInterventiVO tipol = new PbandiDTipolInterventiVO();
				tipol.setIdTipolIntervento(new BigDecimal(idTipol));
				tipol.setIdMacroVoce(idVoceDiSpesa);
				genericDAO.update(tipol);
			}
			
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante la insert. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	// Gestisce l'inserimento\cancellazione di ateco in PBANDI_R_BANDI_ATECO e PBANDI_R_BANDI_ATECO_ESCL.
	//  - atecoDaInserire: true = eseguiren insert; false = eseguire delete.
	//  - atecoDaIncludere: true = far riferimento a PBANDI_R_BANDI_ATECO; false = far riferimento a PBANDI_R_BANDI_ATECO_ESCL. 
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico gestisciAtecoInclusiEsclusi(
			Long idUtente, String identitaDigitale, Long idBando,
			Long[] listaIdAttivitaAteco, Boolean atecoDaInserire, Boolean atecoDaIncludere)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBando", "listaIdAttivitaAteco", "atecoDaInserire", "atecoDaIncludere" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idBando, listaIdAttivitaAteco, atecoDaInserire, atecoDaIncludere);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			
			// Eseguo insert\delete
			if (atecoDaIncludere) {			
				PbandiRBandiAtecoVO vo = new PbandiRBandiAtecoVO();
				vo.setIdBando(new BigDecimal(idBando));
				if (atecoDaInserire) {
					for(Long id : listaIdAttivitaAteco) {
						vo.setIdAteco(new BigDecimal(id));
						// Inserisce solo se non esiste già.
						if (genericDAO.findSingleOrNoneWhere(vo) == null)
							genericDAO.insert(vo);
					}
				} else {
					for(Long id : listaIdAttivitaAteco) {
						vo.setIdAteco(new BigDecimal(id));
						genericDAO.delete(vo);
					}
				}
			} else {			
				PbandiRBandiAtecoEsclVO vo = new PbandiRBandiAtecoEsclVO();
				vo.setIdBando(new BigDecimal(idBando));
				if (atecoDaInserire) {
					for(Long id : listaIdAttivitaAteco) {
						vo.setIdAteco(new BigDecimal(id));
						if (genericDAO.findSingleOrNoneWhere(vo) == null)
							genericDAO.insert(vo);
					}
				} else {
					for(Long id : listaIdAttivitaAteco) {
						vo.setIdAteco(new BigDecimal(id));
						genericDAO.delete(vo);
					}
				}
			}
			
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante l'aggiornamento. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	
	/*
	public it.csi.pbandi.pbweb.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico aggiornaAttivitaAtecoEscluse(
			Long idUtente, String identitaDigitale, Long idBando,
			Long[] listaIdAttivitaAteco, String operazione)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBando", "listaIdAttivitaAteco", "operazione" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idBando, listaIdAttivitaAteco, operazione);

		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			
			// Inserisce o cancella gli id ateco in input dalla tabella PBANDI_R_BANDI_ATECO_ESCL.
			// operazione: "I" = insert; "D" = delete.
			PbandiRBandiAtecoEsclVO vo = new PbandiRBandiAtecoEsclVO();
			vo.setIdBando(new BigDecimal(idBando));
			if ("I".equals(operazione)) {
				for(Long id : listaIdAttivitaAteco) {
					vo.setIdAttivitaAteco(new BigDecimal(id));
					// Inserisce solo se non esiste già.
					if (genericDAO.findSingleOrNoneWhere(vo) == null)
						genericDAO.insert(vo);
				}
			} else if ("D".equals(operazione)) {
				for(Long id : listaIdAttivitaAteco) {
					vo.setIdAttivitaAteco(new BigDecimal(id));
					genericDAO.delete(vo);
				}
			} else {
				throw new Exception("Parametro operazione "+operazione+" non valido");
			}
			
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error("Errore durante l'aggiornamento. ", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}
		
		return esito;
	}
	*/
	public AtecoAmmessoDTO[] findAtecoAmmessi(
			Long idUtente, String identitaDigitale, Long idBando)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBando" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idBando);
		
		// Trova gli ateco ammessi al bando attraverso la vista PBANDI_V_ATECO_AMMESSI.
		PbandiVAtecoAmmessiVO vo = new PbandiVAtecoAmmessiVO();
		vo.setIdBando(new BigDecimal(idBando));
		vo.setAscendentOrder("codAteco");
		List<PbandiVAtecoAmmessiVO> listaVO = genericDAO.findListWhere(vo);

		AtecoAmmessoDTO[] listaDTO = new AtecoAmmessoDTO[listaVO.size()];
		beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		
		return listaDTO;
	}
	
	// Restituisce gli ateco inseriti tra quelli inclusi o esclusi.
	//  - atecoInclusi: true = leggo da PBANDI_R_BANDI_ATECO; false: leggo da PBANDI_R_BANDI_ATECO_ESCL.
	public AtecoInclusoEsclusoDTO[] findAtecoInclusiEsclusi(
			Long idUtente, String identitaDigitale, Long idBando, Boolean atecoInclusi)
	throws CSIException, SystemException, UnrecoverableException,GestioneBackOfficeException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idBando", "atecoInclusi" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idBando, atecoInclusi);
		
		AtecoInclusoEsclusoDTO[] listaDTO = null;
		if (atecoInclusi) {
			AtecoInclusoVO vo = new AtecoInclusoVO();
			vo.setIdBando(new BigDecimal(idBando));
			vo.setAscendentOrder("codAteco");
			List<AtecoInclusoVO> listaVO = genericDAO.findListWhere(vo);
			listaDTO = new AtecoInclusoEsclusoDTO[listaVO.size()];
			beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		} else {
			AtecoEsclusoVO vo = new AtecoEsclusoVO();
			vo.setIdBando(new BigDecimal(idBando));
			vo.setAscendentOrder("codAteco");
			List<AtecoEsclusoVO> listaVO = genericDAO.findListWhere(vo);
			listaDTO = new AtecoInclusoEsclusoDTO[listaVO.size()];
			beanUtil.valueCopy(listaVO.toArray(), listaDTO);
		}
				
		return listaDTO;
	}
	

	public BeneficiarioDTO findCurrentBeneficiario(Long idUtente,
			String identitaDigitale, String codiceVisualizzato)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "codiceVisualizzato" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, codiceVisualizzato);
		logger.info("\n\n\n\nfindCurrentBeneficiario for codiceVisualizzato "+codiceVisualizzato);
		BeneficiarioProgettoVO vo=new BeneficiarioProgettoVO();
		vo.setCodiceVisualizzatoProgetto(codiceVisualizzato);
		try{
			 List<BeneficiarioProgettoVO> vos = genericDAO.findListWhere(vo);
			 if(!isEmpty(vos)){
				 logger.info(vos.get(0).getCodiceFiscaleSoggetto());
				 logger.info(vos.get(0).getDenominazioneBeneficiario()+"\n\n\n");
				 vos.get(0).getIdProgetto();
				 BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO();
			     beanUtil.valueCopy(vos.get(0), beneficiarioDTO);
			     beneficiarioDTO.setAnagraficaAggiornabile(!progettoManager.isGestitoGEFO(vos.get(0).getIdProgetto())); 
			     return beneficiarioDTO;
			 }
			 else return null;
		 }catch(Exception ex){
			 logger.error("Errore nella findCurrentBeneficiario per codiceVisualizzato "+codiceVisualizzato+" : "+ex.getMessage(),ex);
			 throw new GestioneBackOfficeException(ex.getMessage());
		 }
	}

	public DatiBeneficiarioProgettoDTO findBeneficiarioSubentrante(Long idUtente,
			String identitaDigitale, String cfPIVA ) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
	 	 String[] nameParameter = { "idUtente", "identitaDigitale", "cfPIVA" ,"idProgetto" };
		 ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, cfPIVA);
		 logger.info("\n\n\n\nfindBeneficiarioSubentrante for cfPIVA "+cfPIVA );
		DatiBeneficiarioProgettoVO datiBeneficiario=new DatiBeneficiarioProgettoVO();
		datiBeneficiario.setCodiceFiscale(cfPIVA);
		 try{
			 
			 DatiBeneficiarioProgettoDTO beneficiarioDTO = new DatiBeneficiarioProgettoDTO();
			 SoggettoEnteGiuridicoVO soggettoEnteGiuridico=new SoggettoEnteGiuridicoVO();
			 soggettoEnteGiuridico.setCodiceFiscale(cfPIVA);
			 List<SoggettoEnteGiuridicoVO> soggettiEnteGiuridico = genericDAO.findListWhere(soggettoEnteGiuridico);
			 if(!isEmpty(soggettiEnteGiuridico)){
				 List<DatiBeneficiarioProgettoVO> vos = genericDAO.findListWhere(datiBeneficiario);
				 if(!isEmpty(vos)){
					 logger.info(vos.get(0).getCodiceFiscale());
					 logger.info(vos.get(0).getDenominazione()+"\n\n\n");
				     beanUtil.valueCopy(vos.get(0), beneficiarioDTO);
				    
				 }else{
					  beanUtil.valueCopy(soggettiEnteGiuridico.get(0),beneficiarioDTO);
				 }
				 return beneficiarioDTO;
			 }
			 else return null;
		 }catch(Exception ex){
			 logger.error("Errore nella findBeneficiarioSubentrante per cfPIVA "+cfPIVA+" : "+ex.getMessage(),ex);
			 throw new GestioneBackOfficeException(ex.getMessage());
		 }
	}

	
 
	public Boolean cambiaBeneficiario(Long idUtente, String identitaDigitale,
			DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto, String caso)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 String[] nameParameter = { "idUtente", "identitaDigitale", "codiceFiscaleNuovoBen" ,"idProgetto","caso"  };
		 ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, datiBeneficiarioProgetto.getCodiceFiscale(),
				 datiBeneficiarioProgetto.getIdProgetto(),caso);
		 logger.info("\n\n\n\n\ncambiaBeneficiario  for caso: "+caso);
		 logger.shallowDump(datiBeneficiarioProgetto, "info");
		 boolean result = false;
		 
		  try{
			  result = genericDAO.callProcedure().cambiaBeneficiario(datiBeneficiarioProgetto.getCodiceFiscale(),
				 BigDecimal.valueOf(datiBeneficiarioProgetto.getIdProgetto()),
				 (datiBeneficiarioProgetto.getIdEnteGiuridico()!=null?BigDecimal.valueOf(datiBeneficiarioProgetto.getIdEnteGiuridico()):null),
				 (datiBeneficiarioProgetto.getIdIndirizzo()!=null?BigDecimal.valueOf(datiBeneficiarioProgetto.getIdIndirizzo()):null),
				 (datiBeneficiarioProgetto.getIdSede()!=null?BigDecimal.valueOf(datiBeneficiarioProgetto.getIdSede()):null),
				 (datiBeneficiarioProgetto.getIdRecapiti()!=null?BigDecimal.valueOf(datiBeneficiarioProgetto.getIdRecapiti()):null),
				 caso);
		  }catch(Exception ex){
				 logger.error("Errore cambiaBeneficiario  : "+ex.getMessage(),ex);
				 throw new GestioneBackOfficeException(ex.getMessage());
			 }
		
		 return result;
	}

	public DatiBeneficiarioProgettoDTO   saveDatiBeneficiario(Long idUtente, String identitaDigitale,
			DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		 String[] nameParameter = { "idUtente", "identitaDigitale", "datiBeneficiarioProgetto"  };
		 ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, datiBeneficiarioProgetto );
		 logger.info("\n\n\n\n\nsaveDatiBeneficiario");
		 logger.shallowDump(datiBeneficiarioProgetto, "info");
		 boolean result = false;
		 
   	     try{
   	    	 Date currentDate = DateUtil.utilToSqlDate(DateUtil.getDataOdierna());
   	    	
   	    	 insertOrUpdateSoggetto(idUtente,datiBeneficiarioProgetto,currentDate);
   	    
   	    	 insertOrUpdateEnteGiuridico(idUtente,datiBeneficiarioProgetto,currentDate);
   	    	
   	    	 insertOrUpdateSede(idUtente,datiBeneficiarioProgetto,currentDate);
  
   	    	 insertOrUpdateIndirizzo(idUtente,datiBeneficiarioProgetto,currentDate);
   	      	
   	    	 insertOrUpdateRecapiti(idUtente,datiBeneficiarioProgetto,currentDate);
			  /* PBANDI_T_SOGGETTO,
			   * PBANDI_T_ENTE_GIURIDICO,
			   * PBANDI_T_SEDE,
			   * PBANDI_T_INDIRIZZO, 
			   * PBANDI_T_RECAPITI e ricavare gli identificati da passare alla procedura di cambio beneficiario: IdEnteGiuridicoNew ,
			   *  IdIndirizzoNew, 
			   *  IdSedeNew ,
			   *  IdRecapitiNew*/
			  
		  }catch(Exception ex){
				 logger.error("Errore cambiaBeneficiario  : "+ex.getMessage(),ex);
				 throw new GestioneBackOfficeException(ex.getMessage());
			 }
		
		 return datiBeneficiarioProgetto;
	}

	
	
	private void insertOrUpdateRecapiti(Long idUtente,
			DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto,
			Date currentDate) throws Exception {
	  	 PbandiTRecapitiVO pbandiTRecapitiVO=new PbandiTRecapitiVO();
	  	 pbandiTRecapitiVO.setEmail(datiBeneficiarioProgetto.getEmail());
	  	 pbandiTRecapitiVO.setFax(datiBeneficiarioProgetto.getFax());
	  	 pbandiTRecapitiVO.setTelefono(datiBeneficiarioProgetto.getTelefono());
		 if(datiBeneficiarioProgetto.getIdRecapiti()==null){
			 logger.info("\n\n\ninserisco recapiti");
			 pbandiTRecapitiVO.setDtInizioValidita(currentDate);
			 pbandiTRecapitiVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			 pbandiTRecapitiVO = genericDAO.insert(pbandiTRecapitiVO);
			 datiBeneficiarioProgetto.setIdRecapiti(pbandiTRecapitiVO.getIdRecapiti().longValue());
		 }  else{
			 logger.info("\n\n\naggiorno recapiti");
			 pbandiTRecapitiVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
			 pbandiTRecapitiVO.setIdRecapiti(BigDecimal.valueOf(datiBeneficiarioProgetto.getIdRecapiti()));
			 genericDAO.updateNullables(pbandiTRecapitiVO);
		 }
	}
	
	
	private void insertOrUpdateIndirizzo(Long idUtente,
			DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto,
			Date currentDate) throws Exception {
	  	 PbandiTIndirizzoVO pbandiTIndirizzoVO=new PbandiTIndirizzoVO();
	  	 pbandiTIndirizzoVO.setCap(datiBeneficiarioProgetto.getCap());
	  	 pbandiTIndirizzoVO.setCivico(datiBeneficiarioProgetto.getCivico());
	  	 pbandiTIndirizzoVO.setDescIndirizzo(datiBeneficiarioProgetto.getIndirizzo());
	  	 pbandiTIndirizzoVO.setIdComune(datiBeneficiarioProgetto.getIdComune()!=null?BigDecimal.valueOf(datiBeneficiarioProgetto.getIdComune()):null);
	  	 pbandiTIndirizzoVO.setIdNazione(BigDecimal.valueOf(ID_NAZIONE_ITALIA));
	  	 pbandiTIndirizzoVO.setIdProvincia(datiBeneficiarioProgetto.getIdProvincia()!=null?BigDecimal.valueOf(datiBeneficiarioProgetto.getIdProvincia()):null);
	  	 pbandiTIndirizzoVO.setIdRegione(datiBeneficiarioProgetto.getIdRegione()!=null?BigDecimal.valueOf(datiBeneficiarioProgetto.getIdRegione()):null);
		 if(datiBeneficiarioProgetto.getIdIndirizzo()==null){
 	    	 logger.info("\n\n\ninserisco indirizzo");
			 pbandiTIndirizzoVO.setDtInizioValidita(currentDate);
			 pbandiTIndirizzoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
		 	 pbandiTIndirizzoVO = genericDAO.insert(pbandiTIndirizzoVO);
			 datiBeneficiarioProgetto.setIdIndirizzo(pbandiTIndirizzoVO.getIdIndirizzo().longValue());
		 }  else{
 	    	 logger.info("\n\n\naggiorno indirizzo");
			 pbandiTIndirizzoVO.setIdIndirizzo(BigDecimal.valueOf(datiBeneficiarioProgetto.getIdIndirizzo()));
			 pbandiTIndirizzoVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
			 genericDAO.updateNullables(pbandiTIndirizzoVO);
		 }
	}
	
	
	
	private void insertOrUpdateSede(Long idUtente,
			DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto,
			Date currentDate) throws Exception {
	  	 PbandiTSedeVO pbandiTSedeVO=new PbandiTSedeVO();
		 if(datiBeneficiarioProgetto.getIdSede()==null){
			logger.info("\n\n\ninserisco sede");
			pbandiTSedeVO.setDtInizioValidita(currentDate);
			pbandiTSedeVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			pbandiTSedeVO.setPartitaIva(datiBeneficiarioProgetto.getPartitaIva());
			pbandiTSedeVO = genericDAO.insert(pbandiTSedeVO);
			datiBeneficiarioProgetto.setIdSede(pbandiTSedeVO.getIdSede().longValue());
		 }  else{
			 pbandiTSedeVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
			 pbandiTSedeVO.setPartitaIva(datiBeneficiarioProgetto.getPartitaIva());
			 pbandiTSedeVO.setIdSede(BigDecimal.valueOf(datiBeneficiarioProgetto.getIdSede()));
			 genericDAO.update(pbandiTSedeVO);
		 }
		 // in caso di sede esistente non c'e' niente da aggiornare
	}

	private void insertOrUpdateSoggetto(Long idUtente,
			DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto,
			Date currentDate) throws Exception {
		
		PbandiTSoggettoVO pbandiTSoggettoVO=new PbandiTSoggettoVO();
		if(datiBeneficiarioProgetto.getIdSoggetto()==null){
			 logger.info("\n\n\ninserisco sogg");
			 BigDecimal idTipoSoggettoPersonaGiuridica= new BigDecimal(
						decodificheManager.findDecodifica(
										GestioneDatiDiDominioSrv.TIPO_SOGGETTO,
										it.csi.pbandi.pbweb.pbandisrv.util.Constants.DESCRIZIONE_BREVE_TIPO_SOGG_PERSONA_GIURIDICA)
								.getId());
			 
			 pbandiTSoggettoVO.setCodiceFiscaleSoggetto(datiBeneficiarioProgetto.getCodiceFiscale());
			 pbandiTSoggettoVO.setDtInserimento(currentDate);
			 pbandiTSoggettoVO.setIdTipoSoggetto(idTipoSoggettoPersonaGiuridica);
			 pbandiTSoggettoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			 pbandiTSoggettoVO = genericDAO.insert(pbandiTSoggettoVO);
			 datiBeneficiarioProgetto.setIdSoggetto(pbandiTSoggettoVO.getIdSoggetto().longValue());
		 }else{
			 logger.info("\n\n\naggiorno sogg");
			 pbandiTSoggettoVO.setDtAggiornamento(currentDate);
			 pbandiTSoggettoVO.setIdSoggetto(BigDecimal.valueOf(datiBeneficiarioProgetto.getIdSoggetto()));
			 pbandiTSoggettoVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
			 genericDAO.update(pbandiTSoggettoVO);
		 }
	}

	private void insertOrUpdateEnteGiuridico(Long idUtente,
			DatiBeneficiarioProgettoDTO datiBeneficiarioProgetto,
			Date currentDate) throws Exception {
	  	 PbandiTEnteGiuridicoVO pbandiTEnteGiuridicoVO=new PbandiTEnteGiuridicoVO();
	     pbandiTEnteGiuridicoVO.setIdSoggetto(BigDecimal.valueOf(datiBeneficiarioProgetto.getIdSoggetto()));		
	    logger.info("insertOrUpdateEnteGiuridico ");
	    logger.shallowDump(pbandiTEnteGiuridicoVO, "info");
 
		if(datiBeneficiarioProgetto.getIdEnteGiuridico()==null){
			logger.info("\n\n\ninserisco ente");
			pbandiTEnteGiuridicoVO.setDenominazioneEnteGiuridico(datiBeneficiarioProgetto.getDenominazione());
			pbandiTEnteGiuridicoVO.setDtInizioValidita(currentDate);
			pbandiTEnteGiuridicoVO.setIdSoggetto(BigDecimal.valueOf(datiBeneficiarioProgetto.getIdSoggetto()));
			pbandiTEnteGiuridicoVO.setIdUtenteIns(BigDecimal.valueOf(idUtente));
			pbandiTEnteGiuridicoVO = genericDAO.insert(pbandiTEnteGiuridicoVO);
			datiBeneficiarioProgetto.setIdEnteGiuridico(pbandiTEnteGiuridicoVO.getIdEnteGiuridico().longValue());
		 }else{
			logger.info("\n\n\naggiorno ente");
			pbandiTEnteGiuridicoVO.setIdEnteGiuridico(BigDecimal.valueOf(datiBeneficiarioProgetto.getIdEnteGiuridico()));
			pbandiTEnteGiuridicoVO.setDenominazioneEnteGiuridico(datiBeneficiarioProgetto.getDenominazione());
			pbandiTEnteGiuridicoVO.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
			genericDAO.update(pbandiTEnteGiuridicoVO);
		 }
	}
	
	// CDU-13 V08: inizio
	
	public BandoLineaAssociatoDTO[] findBandiSifNonAssociati(Long idUtente, String identitaDigitale, Long progrBandoLinea)
		throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
	
		String[] nameParameter = { "idUtente", "identitaDigitale" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale);
		
		BandoSifNonAssociatoVO vo = new BandoSifNonAssociatoVO();
		//vo.setAscendentOrder("nomeBandoLinea");
		List<BandoSifNonAssociatoVO> lista = genericDAO.findListWhere(vo);

		// Alla lista trovata prima, aggiunge il BL in input.
		if (progrBandoLinea != null) {
			PbandiRBandoLineaInterventVO blVO = new PbandiRBandoLineaInterventVO();
			blVO.setProgrBandoLineaIntervento(new BigDecimal(progrBandoLinea));
			blVO = genericDAO.findSingleOrNoneWhere(blVO);
			BandoSifNonAssociatoVO newItem = new BandoSifNonAssociatoVO();
			newItem.setProgrBandoLineaIntervento(blVO.getProgrBandoLineaIntervento());
			newItem.setNomeBandoLinea(blVO.getNomeBandoLinea());
			lista.add(newItem);
		}
		
		// Ordina la lista per "nomeBandoLinea".
		Collections.sort(lista, 
						new Comparator<BandoSifNonAssociatoVO>() {
						  public int compare(BandoSifNonAssociatoVO p1, BandoSifNonAssociatoVO p2) {
						    return p1.getNomeBandoLinea().compareTo(p2.getNomeBandoLinea());
						  }	});

		BandoLineaAssociatoDTO[] result = beanUtil.transform(lista, BandoLineaAssociatoDTO.class);
		return result;
	}
	
	public Long findIdTipoOperazioneByIdNaturaCipe(Long idUtente, String identitaDigitale, Long idNaturaCipe)
		throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {

		Long idTipoOperazione = null;
		
		if (idNaturaCipe != null) {
			PbandiDNaturaCipeVO vo = new PbandiDNaturaCipeVO();
			vo.setIdNaturaCipe(new BigDecimal(idNaturaCipe));
			vo = genericDAO.findSingleOrNoneWhere(vo);
			if (vo != null && vo.getIdTipoOperazione() != null)
				idTipoOperazione = vo.getIdTipoOperazione().longValue();
		}
		
		return idTipoOperazione;
	}

	// CDU-13 V08: fine
	
	// Jira PBANDI-2626
	public boolean testResourcesActa() {
		boolean esito = true;
		try {
			logger.info("Leggo costante conf.actaRepositoryName");
			Long idUtente = -1L;
			String actaRepositoryName = this.leggiCostanteActa("conf.actaRepositoryName",idUtente);
			logger.info("actaRepositoryName="+ actaRepositoryName);
			
			if (StringUtil.isEmpty(actaRepositoryName))
				throw new Exception("Costante conf.actaRepositoryName non valorizzata.");
			
			logger.info("Inizio inizializzazione servizi Acta.");
//			this.initializeRepositoryServicePort();
//			
//			this.initializeObjectServicePort();
//			logger.info("Inizio initializeObjectServicePort: OK.");
//			
//			//PK uso il repository per test PART228 - Collocazione3
//			actaRepositoryName = "RP201209 Regione Piemonte - Agg. 09/2012";
//			
//			logger.info("Leggo l'idRepository.");
//			String idRepository = this.getRepositoryId(actaRepositoryName, idUtente);
//			if (StringUtil.isEmpty(idRepository))
//				esito = false;	
			
		} catch(Exception e) {
			logger.error("Errore nell'invocare Acta : "+e.getMessage(),e);
			esito = false;
		}
		return esito;
	}

	public EsitoSalvaUtente inserisciTabellaUtente(Long idUtente,
			String identitaDigitale, DettaglioUtenteDTO dettaglio)
			throws CSIException, SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		
		try{
		String[] nameParameter = { "idUtente", "identitaDigitale",
				"dettaglio", "dettaglio.idSoggetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente,
				identitaDigitale, dettaglio, dettaglio.getIdSoggetto());

		EsitoSalvaUtente esito = new EsitoSalvaUtente();

		BigDecimal idSoggetto = new BigDecimal(dettaglio.getIdSoggetto());
		
		//Ottieni Codice Fiscale
		PbandiTSoggettoVO soggettoVO = new PbandiTSoggettoVO();
		soggettoVO.setIdSoggetto(idSoggetto);
		
		soggettoVO = genericDAO.findListWhere(Condition.filterBy(soggettoVO)).get(0);

		// T_UTENTE
		PbandiDTipoAccessoVO tipoAccessoCertificato = new PbandiDTipoAccessoVO();
		tipoAccessoCertificato
				.setCodTipoAccesso(it.csi.pbandi.pbweb.pbandisrv.util.Constants.CODICE_TIPO_ACCESSO_CERTIFICATO);
		tipoAccessoCertificato = genericDAO
				.findWhere(new AndCondition<PbandiDTipoAccessoVO>(
						new FilterCondition<PbandiDTipoAccessoVO>(
								tipoAccessoCertificato),
						new NullCondition<PbandiDTipoAccessoVO>(
								PbandiDTipoAccessoVO.class,
								"dtFineValidita")))[0];
		PbandiTUtenteVO newUtente = new PbandiTUtenteVO();
		newUtente.setIdSoggetto(idSoggetto);
		newUtente.setCodiceUtente(soggettoVO != null ? soggettoVO.getCodiceFiscaleSoggetto() : "");
		newUtente.setIdTipoAccesso(tipoAccessoCertificato
				.getIdTipoAccesso());
		newUtente = genericDAO.insert(newUtente);
		
		esito.setEsito(Boolean.TRUE);
		
		return esito;
		
		}catch (Exception ex){
			throw new GestioneBackOfficeException("errore durante l'inserimento dell'utente: ", ex);
		}
	}
	
	public VoceDiEntrataDTO[] findVociDiEntrataAssociate(Long idUtente,
			String identitaDigitale, Long idBando) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		try {
			String[] nameParameter = { "idUtente","identitaDigitale","idBando" };
			ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,idBando);

			VoceDiEntrataAssociataVO query = new VoceDiEntrataAssociataVO();
			query.setIdBando(new BigDecimal(idBando));
			query.setAscendentOrder("descrizione");
			List<VoceDiEntrataAssociataVO> voci = genericDAO.findListWhere(query);
			
			VoceDiEntrataDTO[] result = new VoceDiEntrataDTO[voci.size()];
			beanUtil.valueCopy(voci.toArray(), result);

			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"findVociDiEntrataAssociate(): Impossibile effettuare la ricerca.", e);
		}  
	}
	
	public EsitoAssociazione associaVoceDiEntrata(Long idUtente,
			String identitaDigitale, VoceDiEntrataDTO voceDiEntrata) throws CSIException,
			SystemException, UnrecoverableException,
			GestioneBackOfficeException {
		EsitoAssociazione esito = new EsitoAssociazione();
		String[] nameParameter = { "idUtente","identitaDigitale","voceDiEntrata" };
		ValidatorInput.verifyNullValue(nameParameter,idUtente,identitaDigitale,voceDiEntrata);
		try {
			logger.info("associaVoceDiEntrata(): idVoceDiEntrata = "+voceDiEntrata.getIdVoceDiEntrata()+"; idbando = "+voceDiEntrata.getIdBando());
			
			if (voceDiEntrata.getIdBando() == null) {
				throw new GestioneBackOfficeException("associaVoceDiEntrata(): id bando non valorizzato.");
			}
			
			// Eventuale inserimento della voce di entrata in PBANDI_D_VOCE_DI_ENTRATA.
			BigDecimal idVoceDiEntrata = null;
			if (voceDiEntrata.getIdVoceDiEntrata() == null || voceDiEntrata.getIdVoceDiEntrata().intValue() < 1) {
				// Voce di entrata nuova.
				try {
					PbandiDVoceDiEntrataVO voce = new PbandiDVoceDiEntrataVO();
					voce.setDescrizione(voceDiEntrata.getDescrizione());
					voce.setDescrizioneBreve(voceDiEntrata.getDescrizioneBreve());
					voce.setIndicazioni(voceDiEntrata.getIndicazioni());
					voce.setFlagEdit(voceDiEntrata.getFlagEdit());
					if ("1".equalsIgnoreCase(voceDiEntrata.getFlagRisorseProprie()))
						voce.setFlagRisorseProprie(1L);
					else
						voce.setFlagRisorseProprie(0L);										
					voce.setDtInizioValidita(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
					logger.info("associaVoceDiEntrata(): voce da inserire:"+voce.toString());
					voce = genericDAO.insert(voce);
					
					idVoceDiEntrata = voce.getIdVoceDiEntrata();
				} catch (Exception e) {
					throw new GestioneBackOfficeException("associaVoceDiEntrata(): Errore durante la creazione della voce di entrata.",e);
				}
			} else {
				// Voce di entrata gi� esistente.
				idVoceDiEntrata = new BigDecimal(voceDiEntrata.getIdVoceDiEntrata());
			}
			
			if (idVoceDiEntrata == null) {
				throw new GestioneBackOfficeException("associaVoceDiEntrata(): Errore durante la creazione della voce di entrata (id non valorizzato).");
			}
			
			// Se non esiste gi�, inserisco l'associazione in PBANDI_R_BANDO_VOCE_ENTRATA.
			PbandiRBandoVoceEntrataVO associazione = new PbandiRBandoVoceEntrataVO(idVoceDiEntrata, new BigDecimal(voceDiEntrata.getIdBando())); 
			List<PbandiRBandoVoceEntrataVO> esistente = genericDAO.findListWhere(associazione);
			if (esistente.size() > 0)  {
				// La voce di entrata � gi� assegnata al bando.
				esito.setEsito(false);
				esito.setMessage(ERRORE_OCCORRENZA_GIA_PRESENTE);
				return esito;
			}			
			associazione.setIdUtenteIns(new BigDecimal(idUtente));
			genericDAO.insert(associazione);
			
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
					
		} catch (Exception e) {
			throw new GestioneBackOfficeException(
					"associaVoceDiEntrata(): Impossibile effettuare l'update.", e);
		}  
		return esito;
	}
	
	public GestioneBackOfficeEsitoGenerico eliminaVoceDiEntrataAssociata(
			Long idUtente, String identitaDigitale, Long idBando,
			Long idVoceDiEntrata) throws CSIException, SystemException,
			UnrecoverableException, GestioneBackOfficeException {
		GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
		try {
			logger.info("eliminaVoceDiEntrataAssociata(): idVoceDiEntrata = "+idVoceDiEntrata+"; idbando = "+idBando);
			
			/* il cdu non dice di fare questo conrollo come per le voci di spesa,
			   ma lo tengo nel caso venisse fuori che serve.
			//Verifico se la voce � gi� presente su un rigo di conto economico.
			if (isVoceDiSpesaAssociataAContoEconomico(idVoceDiSpesa, idBando)) {
				logger.warn("Qualcuno ha cercato di disassociare la voce di spesa "
								+ idVoceDiSpesa
								+ ", ma è già associato a un conto economico per il bando "
								+ idBando + ".");
				esito.setEsito(false);
				esito.setMessage(ERRORE_VOCE_DI_SPESA_GIA_PRESENTE_SU_RIGO_CONTO_ECONOMICO);
				return esito;
			} 
			*/

			PbandiRBandoVoceEntrataVO vo = new PbandiRBandoVoceEntrataVO(
				new BigDecimal(idVoceDiEntrata), 
				new BigDecimal(idBando));
			genericDAO.delete(vo);
			esito.setEsito(true);
			esito.setMessage(MessaggiConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
		} catch (Exception e) {
			logger.error(
					"eliminaVoceDiEntrataAssociata(): Errore durante la cancellazione della voce di spesa (ID = "
							+ idVoceDiEntrata + ")", e);
			esito.setEsito(false);
			esito.setMessage(ERRORE_SERVER);
		}  
		return esito;
	}

}
