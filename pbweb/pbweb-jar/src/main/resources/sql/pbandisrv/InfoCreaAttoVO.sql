SELECT 
    id_atto_liquidazione, 
    importo_atto, 
    desc_settore, 
    erog.desc_causale, 
    ta.note_atto,
    settente.desc_ente
FROM 
    pbandi_t_atto_liquidazione ta, 
    ( 
    SELECT 
        ec.desc_settore, 
        ec.desc_ente, 
        se.id_settore_ente 
    FROM 
        pbandi_d_settore_ente se, 
        pbandi_t_ente_competenza ec 
    WHERE 
        ec.id_ente_competenza (+)                             = se.id_ente_competenza 
        and NVL(TRUNC(se.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) 
        and NVL(TRUNC(ec.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) 
    ) 
    settente, 
    ( 
    SELECT 
        desc_causale, 
        id_causale_erogazione 
    FROM 
        pbandi_d_causale_erogazione ce 
    WHERE 
        NVL(TRUNC(ce.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) 
    ) 
    erog 
WHERE 
    settente.id_settore_ente (+)      = ta.id_settore_ente 
    and erog.id_causale_erogazione (+)= ta.id_causale_erogazione