select 'S' flag_nazione_italiana,
       dc.id_comune,
       dc.id_provincia,
       dp.id_regione,
       (select id_nazione from pbandi_d_nazione where desc_nazione = 'ITALIA') id_nazione,
       dc.desc_comune,
	   dp.sigla_provincia,
	   dc.cod_istat_comune 
  from pbandi_d_comune dc,
       pbandi_d_provincia dp,
       pbandi_d_regione dr
 where dc.id_provincia = dp.id_provincia
   and dp.id_regione = dr.id_regione
union
select 'N' flag_nazione_italiana,
       dce.id_comune_estero id_comune,
       null id_provincia,
       null id_regione,
       dce.id_nazione,
       dce.desc_comune_estero desc_comune,
       null sigla_provincia,
       null cod_istat_comune
  from pbandi_d_comune_estero dce,
       pbandi_d_nazione dn
 where dce.id_nazione = dn.id_nazione