select ID_ASSE, li.DESC_LINEA as desc_asse, li.DESC_BREVE_LINEA as DESC_BREVE_LINEA , sum(main_query.IMPORTO_CERTIFICAZIONE_NETTO) as importo_certificazione_netto
from (select pck_pbandi_certificazione.ReturnAsse(bli_sif.ID_LINEA_DI_INTERVENTO) as id_asse, dpc.IMPORTO_CERTIFICAZIONE_NETTO, dpc.CONTRIBUTO_PUBBLICO_CONCESSO
      from PBANDI_T_DETT_PROPOSTA_CERTIF dpc
          join PBANDI_T_PROGETTO p_sif on p_sif.ID_PROGETTO = dpc.ID_PROGETTO
          join PBANDI_T_DOMANDA d_sif on d_sif.ID_DOMANDA = p_sif.ID_DOMANDA
          join PBANDI_R_BANDO_LINEA_INTERVENT bli_sif on bli_sif.PROGR_BANDO_LINEA_INTERVENTO = d_sif.PROGR_BANDO_LINEA_INTERVENTO and bli_sif.FLAG_SIF = 'S'
          join (select dpc1.ID_PROGETTO, min(ID_DETT_PROPOSTA_CERTIF) as id_dett_proposta_certif
                from PBANDI_T_DETT_PROPOSTA_CERTIF dpc1
                    join PBANDI_T_PROPOSTA_CERTIFICAZ pc on pc.ID_PROPOSTA_CERTIFICAZ = dpc1.ID_PROPOSTA_CERTIFICAZ
                    join PBANDI_D_STATO_PROPOSTA_CERTIF spc on spc.ID_STATO_PROPOSTA_CERTIF = pc.ID_STATO_PROPOSTA_CERTIF and DESC_BREVE_STATO_PROPOSTA_CERT = '07appr'
                group by dpc1.ID_PROGETTO) ppc on ppc.id_dett_proposta_certif = dpc.ID_DETT_PROPOSTA_CERTIF and ppc.ID_PROGETTO = dpc.ID_PROGETTO
      where fn_linea_interv_radice(p_sif.ID_PROGETTO) = 'POR-FESR-2021-2027') main_query
    join PBANDI_D_LINEA_DI_INTERVENTO li on li.ID_LINEA_DI_INTERVENTO = main_query.id_asse
group by main_query.id_asse, li.DESC_LINEA, li.DESC_BREVE_LINEA
