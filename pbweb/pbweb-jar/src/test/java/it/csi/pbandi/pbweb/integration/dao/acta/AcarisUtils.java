package it.csi.pbandi.pbweb.integration.dao.acta;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class AcarisUtils {

	/*
	 private static final SimpleDateFormat ggmmaaaahhmmssSSSFormatter;
	    private static final SimpleDateFormat ggmmaaaahhmmssFormatter;
	    private static final SimpleDateFormat DBFormatter;
	    public static final SimpleDateFormat ggmmaaaaFormatter;
	    private static final SimpleDateFormat formatters[];
	    protected static Logger log;

	    public AcarisUtils()
	    {
	    }

	    public static boolean isCduAbilitato(ContestoUtenteCdto contestoUtenteCdto, String funzione)
	    {
	        return contestoUtenteCdto.getCodiciFunzioniProfiliSelezionati().contains(funzione);
	    }

	    public static final Boolean isTrue(String s)
	    {
	        if(s == null)
	        {
	            return Boolean.FALSE;
	        } else
	        {
	            return Boolean.valueOf("S".equals(s));
	        }
	    }

	    public static PrincipalIdType createGCOPrincipalId(AuthIdentitaCdto authIdentita)
	    {
	        PrincipalIdType gcoPrincipalId = null;
	        if(authIdentita != null && authIdentita.getIdIdentita() != null && StringUtils.isNotEmpty(authIdentita.getIdIdentita().toString()))
	        {
	            String principalId = "";
	            gcoPrincipalId = new PrincipalIdType();
	            principalId = (new StringBuilder()).append(EnumPolicyObjectType.PRINCIPAL_ID.value()).append("_").append(authIdentita.getIdIdentita().toString()).toString();
	            long timestamp = System.currentTimeMillis();
	            principalId = (new StringBuilder()).append(principalId).append("_").append(timestamp).toString();
	            gcoPrincipalId.setValue(pseudoCipher(principalId));
	        }
	        return gcoPrincipalId;
	    }

	    public static PrincipalIdType createGCOPrincipalIdConAppKey(AuthIdentitaCdto authIdentita, String appKey)
	    {
	        PrincipalIdType gcoPrincipalId = null;
	        if(authIdentita != null && authIdentita.getIdIdentita() != null && StringUtils.isNotEmpty(authIdentita.getIdIdentita().toString()))
	        {
	            String principalId = "";
	            gcoPrincipalId = new PrincipalIdType();
	            principalId = (new StringBuilder()).append(EnumPolicyObjectType.PRINCIPAL_ID.value()).append("_").append(authIdentita.getIdIdentita().toString()).toString();
	            long timestamp = System.currentTimeMillis();
	            principalId = (new StringBuilder()).append(principalId).append("_").append(timestamp).toString();
	            principalId = (new StringBuilder()).append(principalId).append("_").append(appKey).toString();
	            gcoPrincipalId.setValue(pseudoCipher(principalId));
	        }
	        return gcoPrincipalId;
	    }

	    public static PrincipalIdType aggiornaTimeStamp(PrincipalIdType principalIdType)
	    {
	        String value = pseudoDecipher(principalIdType.getValue());
	        String p[] = value.split("_");
	        long timestamp = System.currentTimeMillis();
	        p[2] = String.valueOf(timestamp);
	        String nuovoValue = "";
	        nuovoValue = (new StringBuilder()).append(p[0]).append("_").append(p[1]).append("_").append(p[2]).append("_").append(p[3]).toString();
	        principalIdType.setValue(pseudoCipher(nuovoValue));
	        return principalIdType;
	    }

	    public static String pseudoCipher(String in)
	    {
	        if(StringUtils.isBlank(in))
	        {
	            return in;
	        }
	        in = StringUtils.rightPad(in, 128);
	        StringBuilder sb = new StringBuilder(256);
	        int mask = 85;
	        for(int i = 0; i < in.length(); i++)
	        {
	            int ch = in.charAt(i);
	            if(ch > 255)
	            {
	                throw new RuntimeException("unexpected char");
	            }
	            ch ^= mask;
	            mask = mask << 1 & 0xff;
	            if(mask % 4 == 0)
	            {
	                mask |= 1;
	            }
	            sb.append(String.format("%02x", new Object[] {
	                Integer.valueOf(ch)
	            }));
	        }

	        return sb.toString();
	    }

	    public static String encryptingString(String value)
	    {
	        if(StringUtils.isEmpty(value))
	        {
	            return null;
	        }
	        StringBuffer sb;
	        value = value.trim();
	        MessageDigest digest = MessageDigest.getInstance("SHA");
	        String passphrase = "acaris";
	        digest.update(passphrase.getBytes());
	        SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
	        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        aes.init(1, key);
	        byte ciphertext[] = aes.doFinal(value.getBytes());
	        sb = new StringBuffer();
	        for(int i = 0; i < ciphertext.length; i++)
	        {
	            if(i > 0)
	            {
	                sb.append("/");
	            }
	            sb.append((new Long(ciphertext[i])).toString());
	        }

	        return sb.toString();
	        NoSuchAlgorithmException e;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_177;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_177;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_177;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_177;
	        e;
	        e.printStackTrace();
	        return null;
	    }

	    public static String decryptingString(String value)
	    {
	        if(StringUtils.isEmpty(value))
	        {
	            return null;
	        }
	        byte bytes[];
	        Cipher aes;
	        value = value.trim();
	        String valueBytes[] = value.split("/");
	        bytes = new byte[valueBytes.length];
	        for(int i = 0; i < valueBytes.length; i++)
	        {
	            bytes[i] = (new Byte(valueBytes[i])).byteValue();
	        }

	        MessageDigest digest = MessageDigest.getInstance("SHA");
	        String passphrase = "acaris";
	        digest.update(passphrase.getBytes());
	        SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
	        aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        aes.init(2, key);
	        return new String(aes.doFinal(bytes));
	        NoSuchAlgorithmException e;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_159;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_159;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_159;
	        e;
	        e.printStackTrace();
	        break MISSING_BLOCK_LABEL_159;
	        e;
	        e.printStackTrace();
	        return null;
	    }

	    public static void main(String args[])
	    {
	        System.out.println(pseudoDecipher("07cf25c526c321c527d301d325cf0a9b758a758a758a758a758a758a758a758a758a758a758a758a" +
	"758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a" +
	"758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a" +
	"758a758a758a758a"
	));
	        String parolaDaCodificare = "ACARIS-TEAM";
	        String stringEncrypted = encryptingString(parolaDaCodificare);
	        System.out.println((new StringBuilder()).append("valore crittografata: ").append(stringEncrypted).toString());
	        System.out.println((new StringBuilder()).append("valore decifrato: ").append(decryptingString(stringEncrypted)).toString());
	        String nulla = null;
	        System.out.println("S".compareTo(nulla));
	    }
*/
	    public static String pseudoDecipher(String in)
	    {
	        if(StringUtils.isEmpty(in) || StringUtils.length(in) != 256)
	        {
	            return "";
	        }
	        StringBuilder sb = new StringBuilder(128);
	        int mask = 85;
	        for(int i = 0; i < in.length(); i += 2)
	        {
	            int ch = Integer.parseInt(in.substring(i, i + 2), 16);
	            ch ^= mask;
	            mask = mask << 1 & 0xff;
	            if(mask % 4 == 0)
	            {
	                mask |= 1;
	            }
	            char c = (char)ch;
	            sb.append(c);
	        }

	        return StringUtils.stripEnd(sb.toString(), null);
	    }
/*
	    public static String createObjectId(String objType, String classId)
	    {
	        String objId = "";
	        if(StringUtils.isNotEmpty(objType) && StringUtils.isNotEmpty(classId))
	        {
	            objId = (new StringBuilder()).append(objType).append("_").append(classId).toString();
	            objId = pseudoCipher(objId);
	        }
	        return objId;
	    }

	    public static String decodeClassIdFromObjIdDeChiper(String value)
	    {
	        String valueInChiaro = pseudoDecipher(value);
	        String classId = "";
	        if(StringUtils.isNotEmpty(valueInChiaro))
	        {
	            String params[] = valueInChiaro.split("_");
	            if(params.length == 2)
	            {
	                classId = params[1];
	            }
	        }
	        return classId;
	    }
*/
	    public static String decodeTipoTargetIdFromObjIdDeChiper(String value)
	    {
	        String valueInChiaro = pseudoDecipher(value);
	        String tipoTarget = "";
	        if(StringUtils.isNotEmpty(valueInChiaro))
	        {
	            String params[] = valueInChiaro.split("_");
	            if(params.length == 2)
	            {
	                tipoTarget = params[0];
	            }
	        }
	        return tipoTarget;
	    }
/*
	    public static ActaPkDto decodePkDtoFromObjId(ObjectIdType objId)
	        throws AcarisException
	    {
	        ActaPkDto pkDto = null;
	        try
	        {
	            String dbKey = decodeClassIdFromObjId(pseudoDecipher(objId.getValue()));
	            if(StringUtils.isEmpty(dbKey))
	            {
	                throwAcarisException(EnumErrorCodeType.SER_E_001, objId.getValue());
	            }
	            BigDecimal id = new BigDecimal(dbKey);
	            pkDto = new ActaPkDto(id);
	        }
	        catch(Exception e)
	        {
	            throwAcarisException(EnumErrorCodeType.SER_E_001, objId.getValue());
	        }
	        return pkDto;
	    }

	    public static ActaPkDto decodePkDtoFromObjId(ObjectIdType objId, String propertyName)
	        throws AcarisException
	    {
	        ActaPkDto pkDto = null;
	        try
	        {
	            String dbKey = decodeClassIdFromObjId(pseudoDecipher(objId.getValue()));
	            if(StringUtils.isEmpty(dbKey))
	            {
	                throwAcarisException(EnumErrorCodeType.SER_E_001, propertyName);
	            }
	            BigDecimal id = new BigDecimal(dbKey);
	            pkDto = new ActaPkDto(id);
	        }
	        catch(Exception e)
	        {
	            throwAcarisException(EnumErrorCodeType.SER_E_001, propertyName);
	        }
	        return pkDto;
	    }

	    public static String decodeClassIdFromObjId(String objId)
	        throws AcarisException
	    {
	        String classId = "";
	        try
	        {
	            if(StringUtils.isNotEmpty(objId))
	            {
	                String params[] = objId.split("_");
	                classId = params[1];
	            } else
	            {
	                throwAcarisException(EnumErrorCodeType.SER_E_001, objId);
	            }
	        }
	        catch(Exception e)
	        {
	            throwAcarisException(EnumErrorCodeType.SER_E_001, objId);
	        }
	        return classId;
	    }

	    public EnumObjectType decodeObjTypeFromObjId(String objId)
	        throws AcarisException
	    {
	        EnumObjectType objType = null;
	        try
	        {
	            if(StringUtils.isNotEmpty(objId))
	            {
	                String params[] = objId.split("_");
	                objType = EnumObjectType.fromValue(params[0]);
	            } else
	            {
	                throwAcarisException(EnumErrorCodeType.SER_E_001, objId);
	            }
	        }
	        catch(Exception e)
	        {
	            throwAcarisException(EnumErrorCodeType.SER_E_001, objId);
	        }
	        return objType;
	    }

	    public String decodeFromObjId(String objId, String constType)
	        throws AcarisException
	    {
	        String ret = null;
	        try
	        {
	            if(StringUtils.isNotEmpty(objId))
	            {
	                String params[] = objId.split("_");
	                if(params[0].equals(constType))
	                {
	                    ret = constType;
	                }
	            } else
	            {
	                throwAcarisException(EnumErrorCodeType.SER_E_001, objId);
	            }
	        }
	        catch(Exception e)
	        {
	            throwAcarisException(EnumErrorCodeType.SER_E_001, objId);
	        }
	        return ret;
	    }

	    public ActaPkDto decodeIdIdentitaFromPrincipal(PrincipalIdType principalIdType)
	        throws AcarisException
	    {
	        ActaPkDto id = null;
	        try
	        {
	            if(principalIdType != null && StringUtils.isNotEmpty(principalIdType.getValue()))
	            {
	                id = new ActaPkDto();
	                String p[] = principalIdType.getValue().split("_");
	                if(p.length != 4 || !(EnumPolicyObjectType.fromValue(p[0]) instanceof EnumPolicyObjectType) || StringUtils.isBlank(p[1]) || !StringUtils.isNumeric(p[1]) || (new Integer(p[1])).intValue() < 0)
	                {
	                    throwAcarisException(EnumErrorCodeType.SER_E_046, "principalId");
	                } else
	                {
	                    id = pkDtoFromStr(p[1]);
	                }
	            } else
	            {
	                throwAcarisException(EnumErrorCodeType.SER_E_046, "principalId");
	            }
	        }
	        catch(Throwable e)
	        {
	            throwAcarisException(EnumErrorCodeType.SER_E_046, "principalId");
	        }
	        return id;
	    }

	    private ActaPkDto pkDtoFromStr(String s)
	    {
	        ActaPkDto pkDto = null;
	        if(StringUtils.isNotBlank(s))
	        {
	            pkDto = new ActaPkDto(new BigDecimal(s));
	        }
	        return pkDto;
	    }

	    private static AcarisException throwAcarisException(EnumErrorCodeType errorCode, String propertyName)
	        throws AcarisException
	    {
	        AcarisFaultType ft = null;
	        String exMessage = "";
	        if(errorCode != null)
	        {
	            ft = new AcarisFaultType();
	            AcarisProperties properties = new AcarisProperties();
	            ft.setErrorCode(errorCode.value());
	            ft.setExceptionType(properties.returnExcType((new StringBuilder()).append(errorCode.value()).append("_TYPE").toString()));
	            exMessage = properties.returnErrorMessage(errorCode);
	            ft.setTechnicalInfo(exMessage);
	        }
	        ft.setPropertyName(StringUtils.isNotBlank(propertyName) ? propertyName : null);
	        throw new AcarisException(exMessage, ft);
	    }

	    public static int getDizionarioClassiFromEnumObjectType(EnumObjectType tipo)
	    {
	    / * anonymous class not found * /
	    class _anm1 {}

	        switch(_cls1..SwitchMap.it.doqui.acta.actasrv.dto.acaris.type.common.EnumObjectType[tipo.ordinal()])
	        {
	        case 1: // '\001'
	            return 55;

	        case 2: // '\002'
	            return 24;

	        case 3: // '\003'
	            return 9;

	        case 4: // '\004'
	            return 12;

	        case 5: // '\005'
	            return 13;

	        case 6: // '\006'
	            return 6;

	        case 7: // '\007'
	            return 40;

	        case 8: // '\b'
	            return 41;

	        case 9: // '\t'
	            return 5;

	        case 10: // '\n'
	            return 17;

	        case 11: // '\013'
	            return 10;

	        case 12: // '\f'
	            return 36;

	        case 13: // '\r'
	            return 14;

	        case 14: // '\016'
	            return 45;

	        case 15: // '\017'
	            return 46;

	        case 16: // '\020'
	            return 44;

	        case 17: // '\021'
	            return 47;

	        case 18: // '\022'
	            return 33;

	        case 19: // '\023'
	            return 35;

	        case 20: // '\024'
	            return 34;

	        case 21: // '\025'
	            return 21;

	        case 22: // '\026'
	            return 23;

	        case 23: // '\027'
	            return 22;

	        case 24: // '\030'
	            return 19;

	        case 25: // '\031'
	            return 20;

	        case 26: // '\032'
	            return 2;

	        case 27: // '\033'
	            return 107;

	        case 28: // '\034'
	            return 102;

	        case 29: // '\035'
	            return 110;

	        case 30: // '\036'
	            return 122;

	        case 31: // '\037'
	            return 117;

	        case 32: // ' '
	            return 124;

	        case 33: // '!'
	            return 31;

	        case 34: // '"'
	            return 201;
	        }
	        return 0;
	    }

	    public static EnumObjectType getEnumObjectTypeFromDizionarioClassi(int dizionario)
	    {
	        switch(dizionario)
	        {
	        case 24: // '\030'
	            return EnumObjectType.GRUPPO_ALLEGATI_PROPERTIES_TYPE;

	        case 9: // '\t'
	            return EnumObjectType.DOCUMENTO_DB_PROPERTIES_TYPE;

	        case 12: // '\f'
	            return EnumObjectType.DOCUMENTO_REGISTRO_PROPERTIES_TYPE;

	        case 13: // '\r'
	            return EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE;

	        case 6: // '\006'
	            return EnumObjectType.CLIPS_METALLICA_PROPERTIES_TYPE;

	        case 40: // '('
	            return EnumObjectType.TITOLARIO_PROPERTIES_TYPE;

	        case 41: // ')'
	            return EnumObjectType.VOCE_PROPERTIES_TYPE;

	        case 5: // '\005'
	            return EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE;

	        case 17: // '\021'
	            return EnumObjectType.FASCICOLO_TEMPORANEO_PROPERTIES_TYPE;

	        case 10: // '\n'
	            return EnumObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE;

	        case 55: // '7'
	            return EnumObjectType.CONTENUTO_FISICO_PROPERTIES_TYPE;

	        case 36: // '$'
	            return EnumObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE;

	        case 14: // '\016'
	            return EnumObjectType.DOSSIER_PROPERTIES_TYPE;

	        case 45: // '-'
	            return EnumObjectType.VOLUME_SERIE_FASCICOLI_PROPERTIES_TYPE;

	        case 46: // '.'
	            return EnumObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;

	        case 44: // ','
	            return EnumObjectType.VOLUME_FASCICOLI_PROPERTIES_TYPE;

	        case 47: // '/'
	            return EnumObjectType.VOLUME_SOTTOFASCICOLI_PROPERTIES_TYPE;

	        case 33: // '!'
	            return EnumObjectType.SERIE_DOSSIER_PROPERTIES_TYPE;

	        case 35: // '#'
	            return EnumObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;

	        case 34: // '"'
	            return EnumObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE;

	        case 21: // '\025'
	            return EnumObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE;

	        case 23: // '\027'
	            return EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE;

	        case 22: // '\026'
	            return EnumObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE;

	        case 19: // '\023'
	            return EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE;

	        case 20: // '\024'
	            return EnumObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE;

	        case 117: // 'u'
	            return EnumObjectType.NODO_PROPERTIES_TYPE;

	        case 122: // 'z'
	            return EnumObjectType.STRUTTURA_PROPERTIES_TYPE;

	        case 102: // 'f'
	            return EnumObjectType.AOO_PROPERTIES_TYPE;

	        case 124: // '|'
	            return EnumObjectType.UTENTE_PROPERTIES_TYPE;

	        case 107: // 'k'
	            return EnumObjectType.ENTE_PROPERTIES_TYPE;

	        case 2: // '\002'
	            return EnumObjectType.ANNOTAZIONE_PROPERTIES_TYPE;

	        case 120: // 'x'
	            return EnumObjectType.PROFILO_PROPERTIES_TYPE;

	        case 110: // 'n'
	            return EnumObjectType.GRUPPO_AOO_PROPERTIES_TYPE;

	        case 3: // '\003'
	        case 4: // '\004'
	        case 7: // '\007'
	        case 8: // '\b'
	        case 11: // '\013'
	        case 15: // '\017'
	        case 16: // '\020'
	        case 18: // '\022'
	        case 25: // '\031'
	        case 26: // '\032'
	        case 27: // '\033'
	        case 28: // '\034'
	        case 29: // '\035'
	        case 30: // '\036'
	        case 31: // '\037'
	        case 32: // ' '
	        case 37: // '%'
	        case 38: // '&'
	        case 39: // '\''
	        case 42: // '*'
	        case 43: // '+'
	        case 48: // '0'
	        case 49: // '1'
	        case 50: // '2'
	        case 51: // '3'
	        case 52: // '4'
	        case 53: // '5'
	        case 54: // '6'
	        case 56: // '8'
	        case 57: // '9'
	        case 58: // ':'
	        case 59: // ';'
	        case 60: // '<'
	        case 61: // '='
	        case 62: // '>'
	        case 63: // '?'
	        case 64: // '@'
	        case 65: // 'A'
	        case 66: // 'B'
	        case 67: // 'C'
	        case 68: // 'D'
	        case 69: // 'E'
	        case 70: // 'F'
	        case 71: // 'G'
	        case 72: // 'H'
	        case 73: // 'I'
	        case 74: // 'J'
	        case 75: // 'K'
	        case 76: // 'L'
	        case 77: // 'M'
	        case 78: // 'N'
	        case 79: // 'O'
	        case 80: // 'P'
	        case 81: // 'Q'
	        case 82: // 'R'
	        case 83: // 'S'
	        case 84: // 'T'
	        case 85: // 'U'
	        case 86: // 'V'
	        case 87: // 'W'
	        case 88: // 'X'
	        case 89: // 'Y'
	        case 90: // 'Z'
	        case 91: // '['
	        case 92: // '\\'
	        case 93: // ']'
	        case 94: // '^'
	        case 95: // '_'
	        case 96: // '`'
	        case 97: // 'a'
	        case 98: // 'b'
	        case 99: // 'c'
	        case 100: // 'd'
	        case 101: // 'e'
	        case 103: // 'g'
	        case 104: // 'h'
	        case 105: // 'i'
	        case 106: // 'j'
	        case 108: // 'l'
	        case 109: // 'm'
	        case 111: // 'o'
	        case 112: // 'p'
	        case 113: // 'q'
	        case 114: // 'r'
	        case 115: // 's'
	        case 116: // 't'
	        case 118: // 'v'
	        case 119: // 'w'
	        case 121: // 'y'
	        case 123: // '{'
	        default:
	            return null;
	        }
	    }

	    public static EnumTipoDocumentoCreazione getEnumTipoDocumentoCreazioneFromDizionarioClassi(int dizionario)
	    {
	        switch(dizionario)
	        {
	        case 9: // '\t'
	            return EnumTipoDocumentoCreazione.DOCUMENTO_ARCHIVISTICO_DB;

	        case 12: // '\f'
	            return EnumTipoDocumentoCreazione.DOCUMENTO_ARCHIVISTICO_REGISTRO;

	        case 13: // '\r'
	            return EnumTipoDocumentoCreazione.DOCUMENTO_ARCHIVISTICO_SEMPLICE;

	        case 10: // '\n'
	            return EnumTipoDocumentoCreazione.DOCUMENTO_FISICO;

	        case 55: // '7'
	            return EnumTipoDocumentoCreazione.CONTENUTO_FISICO;
	        }
	        return null;
	    }

	    public static String getObjectStatus(BigDecimal pkStato, EnumObjectType oggetto)
	    {
	        int idStato = pkStato.intValue();
	        switch(_cls1..SwitchMap.it.doqui.acta.actasrv.dto.acaris.type.common.EnumObjectType[oggetto.ordinal()])
	        {
	        case 7: // '\007'
	            switch(idStato)
	            {
	            case 3: // '\003'
	                return EnumTitolarioStatoType.ATTIVO.value();

	            case 14: // '\016'
	                return EnumTitolarioStatoType.DISATTIVO.value();

	            case 4: // '\004'
	                return EnumTitolarioStatoType.BOZZA.value();

	            case 16: // '\020'
	                return EnumTitolarioStatoType.PREATTIVO.value();
	            }
	            // fall through

	        case 8: // '\b'
	            switch(idStato)
	            {
	            case 2: // '\002'
	                return EnumVoceStatoType.ATTIVA.value();

	            case 13: // '\r'
	                return EnumVoceStatoType.DISATTIVA.value();

	            case 4: // '\004'
	                return EnumVoceStatoType.BOZZA.value();

	            case 19: // '\023'
	                return EnumVoceStatoType.PREATTIVA.value();
	            }
	            // fall through

	        case 10: // '\n'
	            switch(idStato)
	            {
	            case 3: // '\003'
	                return EnumFascicoloTemporaneoStatoType.ATTIVO.value();

	            case 14: // '\016'
	                return EnumFascicoloTemporaneoStatoType.DISATTIVO.value();

	            case 12: // '\f'
	                return EnumFascicoloTemporaneoStatoType.CONGELATO.value();
	            }
	            // fall through

	        case 21: // '\025'
	        case 22: // '\026'
	        case 23: // '\027'
	        case 24: // '\030'
	        case 25: // '\031'
	            switch(idStato)
	            {
	            case 1: // '\001'
	                return EnumFascicoloRealeStatoType.APERTO.value();

	            case 5: // '\005'
	                return EnumFascicoloRealeStatoType.CANCELLATO.value();

	            case 12: // '\f'
	                return EnumFascicoloRealeStatoType.CONGELATO.value();

	            case 17: // '\021'
	                return EnumFascicoloRealeStatoType.SCARTATO.value();

	            case 18: // '\022'
	                return EnumFascicoloRealeStatoType.SPOSTATO.value();

	            case 15: // '\017'
	                return EnumFascicoloRealeStatoType.ATTESA_DI_CHIUSURA.value();

	            case 9: // '\t'
	                return EnumFascicoloRealeStatoType.CHIUSO_IN_CORRENTE.value();

	            case 10: // '\n'
	                return EnumFascicoloRealeStatoType.CHIUSO_IN_DEPOSITO.value();
	            }
	            // fall through

	        case 12: // '\f'
	            switch(idStato)
	            {
	            case 1: // '\001'
	                return EnumSottofascicoloStatoType.APERTO.value();

	            case 5: // '\005'
	                return EnumSottofascicoloStatoType.CANCELLATO.value();

	            case 12: // '\f'
	                return EnumSottofascicoloStatoType.CONGELATO.value();

	            case 17: // '\021'
	                return EnumSottofascicoloStatoType.SCARTATO.value();

	            case 18: // '\022'
	                return EnumSottofascicoloStatoType.SPOSTATO.value();

	            case 15: // '\017'
	                return EnumSottofascicoloStatoType.ATTESA_DI_CHIUSURA.value();

	            case 9: // '\t'
	                return EnumSottofascicoloStatoType.CHIUSO_IN_CORRENTE.value();

	            case 10: // '\n'
	                return EnumSottofascicoloStatoType.CHIUSO_IN_DEPOSITO.value();
	            }
	            // fall through

	        case 18: // '\022'
	            switch(idStato)
	            {
	            case 2: // '\002'
	                return EnumSerieDossierStatoType.ATTIVA.value();

	            case 20: // '\024'
	                return EnumSerieDossierStatoType.CANCELLATA.value();

	            case 11: // '\013'
	                return EnumSerieDossierStatoType.CONGELATA.value();

	            case 13: // '\r'
	                return EnumSerieDossierStatoType.DISATTIVA.value();

	            case 6: // '\006'
	                return EnumSerieDossierStatoType.CHIUSA_IN_CORRENTE.value();

	            case 7: // '\007'
	                return EnumSerieDossierStatoType.CHIUSA_IN_DEPOSITO.value();
	            }
	            // fall through

	        case 20: // '\024'
	            switch(idStato)
	            {
	            case 2: // '\002'
	                return EnumSerieFascicoliStatoType.ATTIVA.value();

	            case 20: // '\024'
	                return EnumSerieFascicoliStatoType.CANCELLATA.value();

	            case 11: // '\013'
	                return EnumSerieFascicoliStatoType.CONGELATA.value();

	            case 13: // '\r'
	                return EnumSerieFascicoliStatoType.DISATTIVA.value();

	            case 6: // '\006'
	                return EnumSerieFascicoliStatoType.CHIUSA_IN_CORRENTE.value();

	            case 7: // '\007'
	                return EnumSerieFascicoliStatoType.CHIUSA_IN_DEPOSITO.value();
	            }
	            // fall through

	        case 19: // '\023'
	            switch(idStato)
	            {
	            case 2: // '\002'
	                return EnumSerieTipologicaDocumentiStatoType.ATTIVA.value();

	            case 20: // '\024'
	                return EnumSerieTipologicaDocumentiStatoType.CANCELLATA.value();

	            case 11: // '\013'
	                return EnumSerieTipologicaDocumentiStatoType.CONGELATA.value();

	            case 13: // '\r'
	                return EnumSerieTipologicaDocumentiStatoType.DISATTIVA.value();

	            case 6: // '\006'
	                return EnumSerieTipologicaDocumentiStatoType.CHIUSA_IN_CORRENTE.value();

	            case 7: // '\007'
	                return EnumSerieTipologicaDocumentiStatoType.CHIUSA_IN_DEPOSITO.value();
	            }
	            // fall through

	        case 13: // '\r'
	            switch(idStato)
	            {
	            case 1: // '\001'
	                return EnumDossierStatoType.APERTO.value();

	            case 15: // '\017'
	                return EnumDossierStatoType.ATTESA_DI_CHIUSURA.value();

	            case 5: // '\005'
	                return EnumDossierStatoType.CANCELLATO.value();

	            case 12: // '\f'
	                return EnumDossierStatoType.CONGELATO.value();

	            case 18: // '\022'
	                return EnumDossierStatoType.SPOSTATO.value();

	            case 17: // '\021'
	                return EnumDossierStatoType.SCARTATO.value();

	            case 9: // '\t'
	                return EnumDossierStatoType.CHIUSO_IN_CORRENTE.value();

	            case 10: // '\n'
	                return EnumDossierStatoType.CHIUSO_IN_DEPOSITO.value();
	            }
	            // fall through

	        case 14: // '\016'
	        case 15: // '\017'
	        case 16: // '\020'
	        case 17: // '\021'
	            switch(idStato)
	            {
	            case 1: // '\001'
	                return EnumVolumeStatoType.APERTO.value();

	            case 5: // '\005'
	                return EnumVolumeStatoType.CANCELLATO.value();

	            case 17: // '\021'
	                return EnumVolumeStatoType.SCARTATO.value();

	            case 9: // '\t'
	                return EnumVolumeStatoType.CHIUSO_IN_CORRENTE.value();

	            case 10: // '\n'
	                return EnumVolumeStatoType.CHIUSO_IN_DEPOSITO.value();
	            }
	            // fall through

	        case 9: // '\t'
	            switch(idStato)
	            {
	            case 2: // '\002'
	                return EnumClassificazioneStatoType.ATTIVA.value();

	            case 5: // '\005'
	                return EnumClassificazioneStatoType.CANCELLATO.value();

	            case 22: // '\026'
	                return EnumClassificazioneStatoType.RI_CLASSIFICATO.value();
	            }
	            // fall through

	        case 34: // '"'
	            switch(idStato)
	            {
	            case 1: // '\001'
	                return "registrata";

	            case 2: // '\002'
	                return "modificata";

	            case 3: // '\003'
	                return "annullata";

	            case 4: // '\004'
	                return "da inoltrare ad altra AOO";
	            }
	            // fall through

	        case 3: // '\003'
	        case 4: // '\004'
	        case 5: // '\005'
	            switch(idStato)
	            {
	            }
	            // fall through

	        case 2: // '\002'
	        case 6: // '\006'
	        case 11: // '\013'
	            switch(idStato)
	            {
	            }
	            // fall through

	        case 26: // '\032'
	        case 27: // '\033'
	        case 28: // '\034'
	        case 29: // '\035'
	        case 30: // '\036'
	        case 31: // '\037'
	        case 32: // ' '
	        case 33: // '!'
	        default:
	            return null;
	        }
	    }

	    public static PropertyType buildPropertyItem(EnumObjectType tipoOggetto, ItemListaPropertiesCdto itemProperty)
	    {
	        PropertyType itemPropertyType = new PropertyType();
	        QueryNameType queryName = new QueryNameType();
	        queryName.setClassName(tipoOggetto.value());
	        queryName.setPropertyName(itemProperty.getNomeAttributo());
	        itemPropertyType.setQueryName(queryName);
	        ValueType value = new ValueType();
	        String content[] = new String[1];
	        if("stato".equals(itemProperty.getNomeAttributo()))
	        {
	            content[0] = getObjectStatus(new BigDecimal(itemProperty.getValore()), tipoOggetto);
	        } else
	        {
	            content[0] = itemProperty.getValore();
	        }
	        value.setContent(content);
	        itemPropertyType.setValue(value);
	        return itemPropertyType;
	    }

	    public static PropertyType buildPropertyType(EnumObjectType tipoOggetto, String nomeAttributo, Object valore)
	    {
	        PropertyType propertyType = new PropertyType();
	        QueryNameType queryName = new QueryNameType();
	        queryName.setClassName(tipoOggetto.value());
	        queryName.setPropertyName(nomeAttributo);
	        propertyType.setQueryName(queryName);
	        ValueType value = new ValueType();
	        if(valore != null && valore.getClass().isArray())
	        {
	            Object array[] = (Object[])(Object[])valore;
	            if(array != null && array.length > 0)
	            {
	                String elementi[] = new String[array.length];
	                for(int j = 0; j < array.length; j++)
	                {
	                    elementi[j] = (String)array[j];
	                }

	                value.setContent(elementi);
	            }
	        } else
	        {
	            String elementi[] = new String[1];
	            elementi[0] = value != null ? String.valueOf(valore) : null;
	            value.setContent(elementi);
	        }
	        propertyType.setValue(value);
	        return propertyType;
	    }

	    public static ItemListaPropertiesCdto[] propertyTypeToItemLista(PropertyType properties[])
	    {
	        if(properties == null)
	        {
	            return null;
	        }
	        ItemListaPropertiesCdto ilpArray[] = new ItemListaPropertiesCdto[properties.length];
	        for(int i = 0; i < properties.length; i++)
	        {
	            ItemListaPropertiesCdto ilp = new ItemListaPropertiesCdto();
	            ilp.setNomeAttributo(properties[i].getQueryName().getPropertyName());
	            if(properties[i].getValue() != null && properties[i].getValue().getContent() != null && properties[i].getValue().getContent().length > 0)
	            {
	                ilp.setValore(properties[i].getValue().getContent()[0]);
	            }
	            if(properties[i].getValue() != null && properties[i].getValue().getContent() != null)
	            {
	                ilp.setContent(properties[i].getValue().getContent());
	            }
	            ilpArray[i] = ilp;
	        }

	        return ilpArray;
	    }

	    public static String getStringPropertyValue(Object value)
	    {
	        String stringato = null;
	        if(value != null && value.getClass().isArray())
	        {
	            String esteso = "";
	            Object array[] = (Object[])(Object[])value;
	            for(int j = 0; j < array.length; j++)
	            {
	                esteso = (new StringBuilder()).append(esteso).append((String)array[j]).append("; ").toString();
	            }

	            stringato = esteso;
	        } else
	        {
	            stringato = value != null ? String.valueOf(value) : null;
	        }
	        return stringato;
	    }

	    public static final Timestamp convertiDataAllFormat(String ts)
	    {
	        if(ts == null)
	        {
	            return null;
	        }
	        Timestamp dataTimestamp = null;
	        for(int i = 0; i < formatters.length && dataTimestamp == null; i++)
	        {
	            dataTimestamp = convertiDataByFormatter(ts, formatters[i]);
	        }

	        return dataTimestamp;
	    }

	    public static final Timestamp convertiDataByFormatter(String ts, SimpleDateFormat formatter)
	    {
	        if(ts == null || formatter == null)
	        {
	            return null;
	        }
	        return new Timestamp(formatter.parse(ts).getTime());
	        Exception e;
	        e;
	        return null;
	    }

	    public static EnumMimeTypeType getMimeTypeStatus(String mimeType)
	    {
	        if(mimeType != null)
	        {
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_EXCEL.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_EXCEL;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_GNUTAR.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_GNUTAR;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_MSPOWERPOINT.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_MSPOWERPOINT;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_MSWORD.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_MSWORD;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_PDF.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_PDF;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_PKCS_7_MIME.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_PKCS_7_MIME;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_RTF.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_RTF;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_POWERPOINT.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_POWERPOINT;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_TIMESTAMP_REPLY.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_TIMESTAMP_REPLY;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_MS_EXCEL.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_MS_EXCEL;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_MS_POWERPOINT.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_MS_POWERPOINT;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_CHART.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_CHART;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_COMPRESSED.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_COMPRESSED;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_EXCEL.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_EXCEL;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_MSEXCEL.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_MSEXCEL;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_MSPOWERPOINT.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_MSPOWERPOINT;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_RTF.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_RTF;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_TAR.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_TAR;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_ZIP_COMPRESSED.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_ZIP_COMPRESSED;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_XML.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_XML;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_XSL.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_XSL;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_ZIP.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_ZIP;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.IMAGE_JPEG.value()))
	            {
	                return EnumMimeTypeType.IMAGE_JPEG;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.IMAGE_PJPEG.value()))
	            {
	                return EnumMimeTypeType.IMAGE_PJPEG;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.IMAGE_TIFF.value()))
	            {
	                return EnumMimeTypeType.IMAGE_TIFF;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.IMAGE_X_TIFF.value()))
	            {
	                return EnumMimeTypeType.IMAGE_X_TIFF;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.MULTIPART_MIXED.value()))
	            {
	                return EnumMimeTypeType.MULTIPART_MIXED;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.MULTIPART_X_ZIP.value()))
	            {
	                return EnumMimeTypeType.MULTIPART_X_ZIP;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.TEXT_PLAIN.value()))
	            {
	                return EnumMimeTypeType.TEXT_PLAIN;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.TEXT_RICHTEXT.value()))
	            {
	                return EnumMimeTypeType.TEXT_RICHTEXT;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.APPLICATION_X_COMPRESSED.value()))
	            {
	                return EnumMimeTypeType.APPLICATION_X_COMPRESSED;
	            }
	            if(mimeType.equalsIgnoreCase(EnumMimeTypeType.TEXT_HTML.value()))
	            {
	                return EnumMimeTypeType.TEXT_HTML;
	            } else
	            {
	                return null;
	            }
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumObjectType convertiDizionarioClasseInEnumObjectType(BigDecimal idDizionarioClassi)
	    {
	        if(idDizionarioClassi == null)
	        {
	            return null;
	        }
	        switch(idDizionarioClassi.intValue())
	        {
	        case 35: // '#'
	            return EnumObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;

	        case 34: // '"'
	            return EnumObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE;

	        case 33: // '!'
	            return EnumObjectType.SERIE_DOSSIER_PROPERTIES_TYPE;

	        case 19: // '\023'
	            return EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE;

	        case 20: // '\024'
	            return EnumObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE;

	        case 21: // '\025'
	            return EnumObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE;

	        case 22: // '\026'
	            return EnumObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE;

	        case 23: // '\027'
	            return EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE;

	        case 36: // '$'
	            return EnumObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE;

	        case 44: // ','
	            return EnumObjectType.VOLUME_FASCICOLI_PROPERTIES_TYPE;

	        case 45: // '-'
	            return EnumObjectType.VOLUME_SERIE_FASCICOLI_PROPERTIES_TYPE;

	        case 46: // '.'
	            return EnumObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;

	        case 47: // '/'
	            return EnumObjectType.VOLUME_SOTTOFASCICOLI_PROPERTIES_TYPE;

	        case 13: // '\r'
	            return EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE;

	        case 12: // '\f'
	            return EnumObjectType.DOCUMENTO_REGISTRO_PROPERTIES_TYPE;

	        case 9: // '\t'
	            return EnumObjectType.DOCUMENTO_DB_PROPERTIES_TYPE;

	        case 10: // '\n'
	            return EnumObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE;

	        case 11: // '\013'
	        case 14: // '\016'
	        case 15: // '\017'
	        case 16: // '\020'
	        case 17: // '\021'
	        case 18: // '\022'
	        case 24: // '\030'
	        case 25: // '\031'
	        case 26: // '\032'
	        case 27: // '\033'
	        case 28: // '\034'
	        case 29: // '\035'
	        case 30: // '\036'
	        case 31: // '\037'
	        case 32: // ' '
	        case 37: // '%'
	        case 38: // '&'
	        case 39: // '\''
	        case 40: // '('
	        case 41: // ')'
	        case 42: // '*'
	        case 43: // '+'
	        default:
	            return null;
	        }
	    }

	    public static EnumArchiveObjectType convertiDizionarioClasseInEnumArchiveObjectType(BigDecimal idDizionarioClassi)
	    {
	        if(idDizionarioClassi == null)
	        {
	            return null;
	        }
	        switch(idDizionarioClassi.intValue())
	        {
	        case 35: // '#'
	            return EnumArchiveObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;

	        case 34: // '"'
	            return EnumArchiveObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE;

	        case 33: // '!'
	            return EnumArchiveObjectType.SERIE_DOSSIER_PROPERTIES_TYPE;

	        case 19: // '\023'
	            return EnumArchiveObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE;

	        case 20: // '\024'
	            return EnumArchiveObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE;

	        case 21: // '\025'
	            return EnumArchiveObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE;

	        case 22: // '\026'
	            return EnumArchiveObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE;

	        case 23: // '\027'
	            return EnumArchiveObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE;

	        case 36: // '$'
	            return EnumArchiveObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE;

	        case 44: // ','
	            return EnumArchiveObjectType.VOLUME_FASCICOLI_PROPERTIES_TYPE;

	        case 45: // '-'
	            return EnumArchiveObjectType.VOLUME_SERIE_FASCICOLI_PROPERTIES_TYPE;

	        case 46: // '.'
	            return EnumArchiveObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;

	        case 47: // '/'
	            return EnumArchiveObjectType.VOLUME_SOTTOFASCICOLI_PROPERTIES_TYPE;

	        case 13: // '\r'
	            return EnumArchiveObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE;

	        case 12: // '\f'
	            return EnumArchiveObjectType.DOCUMENTO_REGISTRO_PROPERTIES_TYPE;

	        case 9: // '\t'
	            return EnumArchiveObjectType.DOCUMENTO_DB_PROPERTIES_TYPE;

	        case 10: // '\n'
	            return EnumArchiveObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE;

	        case 11: // '\013'
	        case 14: // '\016'
	        case 15: // '\017'
	        case 16: // '\020'
	        case 17: // '\021'
	        case 18: // '\022'
	        case 24: // '\030'
	        case 25: // '\031'
	        case 26: // '\032'
	        case 27: // '\033'
	        case 28: // '\034'
	        case 29: // '\035'
	        case 30: // '\036'
	        case 31: // '\037'
	        case 32: // ' '
	        case 37: // '%'
	        case 38: // '&'
	        case 39: // '\''
	        case 40: // '('
	        case 41: // ')'
	        case 42: // '*'
	        case 43: // '+'
	        default:
	            return null;
	        }
	    }

	    public static ChangeTokenType estraiChangeTokenDataAgg(ObjectResponseType objResType)
	    {
	        ChangeTokenType changeTokenType = new ChangeTokenType();
	        ItemListaPropertiesCdto ilpArray[] = propertyTypeToItemLista(objResType.getProperties());
	        boolean trovato = false;
	        for(int i = 0; i < ilpArray.length && !trovato; i++)
	        {
	            if(ilpArray[i].getNomeAttributo().equalsIgnoreCase("changeToken"))
	            {
	                changeTokenType.setValue(ilpArray[i].getValore());
	                trovato = true;
	            }
	        }

	        return changeTokenType;
	    }

	    public static Exception generaAcarisException(Exception bean, AcarisException ex)
	    {
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.objectservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.objectservice.AcarisException exc = new it.doqui.acta.acaris.objectservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.backofficeservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.backofficeservice.AcarisException exc = new it.doqui.acta.acaris.backofficeservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.managementservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.managementservice.AcarisException exc = new it.doqui.acta.acaris.managementservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.multifilingservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.multifilingservice.AcarisException exc = new it.doqui.acta.acaris.multifilingservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.navigationservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.navigationservice.AcarisException exc = new it.doqui.acta.acaris.navigationservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.relationshipsservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.relationshipsservice.AcarisException exc = new it.doqui.acta.acaris.relationshipsservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.repositoryservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.repositoryservice.AcarisException exc = new it.doqui.acta.acaris.repositoryservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.smsservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.smsservice.AcarisException exc = new it.doqui.acta.acaris.smsservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        }
	        if(bean.getClass().isInstance(new it.doqui.acta.acaris.documentservice.AcarisException()))
	        {
	            it.doqui.acta.acaris.documentservice.AcarisException exc = new it.doqui.acta.acaris.documentservice.AcarisException(ex.getMessage(), ex.getFaultInfo(), ex.getCause());
	            return exc;
	        } else
	        {
	            return ex;
	        }
	    }

	    public static final boolean isLong(String str)
	    {
	        Long.parseLong(str);
	        return true;
	        NumberFormatException nfe;
	        nfe;
	        return false;
	    }

	    public static EnumPropertyFilterOperation convertFromStringToEnumPropertyFilterOperation(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("all"))
	        {
	            return EnumPropertyFilterOperation.ALL;
	        }
	        if(value.equals("getChildren"))
	        {
	            return EnumPropertyFilterOperation.GET_CHILDREN;
	        }
	        if(value.equals("getDescendants"))
	        {
	            return EnumPropertyFilterOperation.GET_DESCENDANTS;
	        }
	        if(value.equals("getFolderParent"))
	        {
	            return EnumPropertyFilterOperation.GET_FOLDER_PARENT;
	        }
	        if(value.equals("getObjectParents"))
	        {
	            return EnumPropertyFilterOperation.GET_OBJECT_PARENTS;
	        }
	        if(value.equals("getProperties"))
	        {
	            return EnumPropertyFilterOperation.GET_PROPERTIES;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumServiceType convertFromStringToEnumServiceType(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("Archive"))
	        {
	            return EnumServiceType.ARCHIVE;
	        }
	        if(value.equals("BackOffice"))
	        {
	            return EnumServiceType.BACK_OFFICE;
	        }
	        if(value.equals("Management"))
	        {
	            return EnumServiceType.MANAGEMENT;
	        }
	        if(value.equals("OfficialBook"))
	        {
	            return EnumServiceType.OFFICIAL_BOOK;
	        }
	        if(value.equals("Sms"))
	        {
	            return EnumServiceType.SMS;
	        }
	        if(value.equals("SubjectRegistry"))
	        {
	            return EnumServiceType.SUBJECT_REGISTRY;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumRelationshipObjectType convertFromStringToEnumRelationshipObjectType(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("DocumentAssociationPropertiesType"))
	        {
	            return EnumRelationshipObjectType.DOCUMENT_ASSOCIATION_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentCompositionPropertiesType"))
	        {
	            return EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE;
	        }
	        if(value.equals("HistoryModificheTecnichePropertiesType"))
	        {
	            return EnumRelationshipObjectType.HISTORY_MODIFICHE_TECNICHE_PROPERTIES_TYPE;
	        }
	        if(value.equals("HistoryVecchieVersioniPropertiesType"))
	        {
	            return EnumRelationshipObjectType.HISTORY_VECCHIE_VERSIONI_PROPERTIES_TYPE;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumRelationshipDirectionType convertFromStringToEnumRelationshipDirection(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("either"))
	        {
	            return EnumRelationshipDirectionType.EITHER;
	        }
	        if(value.equals("source"))
	        {
	            return EnumRelationshipDirectionType.SOURCE;
	        }
	        if(value.equals("target"))
	        {
	            return EnumRelationshipDirectionType.TARGET;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumTipoOperazione convertFromStringToEnumTipoOperazione(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("aggiuntaContenutoFisico"))
	        {
	            return EnumTipoOperazione.AGGIUNTA_CONTENUTO_FISICO;
	        }
	        if(value.equals("aggiuntaDocumentoFisico"))
	        {
	            return EnumTipoOperazione.AGGIUNTA_DOCUMENTO_FISICO;
	        }
	        if(value.equals("elettronico"))
	        {
	            return EnumTipoOperazione.ELETTRONICO;
	        }
	        if(value.equals("soloMetadati"))
	        {
	            return EnumTipoOperazione.SOLO_METADATI;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumTipoDocumentoArchivistico convertFromStringToEnumTipoDocumentoArchivistico(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("DocumentoDB"))
	        {
	            return EnumTipoDocumentoArchivistico.DOCUMENTO_DB;
	        }
	        if(value.equals("DocumentoRegistro"))
	        {
	            return EnumTipoDocumentoArchivistico.DOCUMENTO_REGISTRO;
	        }
	        if(value.equals("DocumentoSemplice"))
	        {
	            return EnumTipoDocumentoArchivistico.DOCUMENTO_SEMPLICE;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumArchiveObjectType convertFromStringToEnumArchiveObjectType(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("ActaACEPropertiesType"))
	        {
	            return EnumArchiveObjectType.ACTA_ACE_PROPERTIES_TYPE;
	        }
	        if(value.equals("ClassificazionePropertiesType"))
	        {
	            return EnumArchiveObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE;
	        }
	        if(value.equals("ClipsMetallicaPropertiesType"))
	        {
	            return EnumArchiveObjectType.CLIPS_METALLICA_PROPERTIES_TYPE;
	        }
	        if(value.equals("ContenutoFisicoPropertiesType"))
	        {
	            return EnumArchiveObjectType.CONTENUTO_FISICO_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentAssociationPropertiesType"))
	        {
	            return EnumArchiveObjectType.DOCUMENT_ASSOCIATION_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentCompositionPropertiesType"))
	        {
	            return EnumArchiveObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoDBPropertiesType"))
	        {
	            return EnumArchiveObjectType.DOCUMENTO_DB_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoFisicoPropertiesType"))
	        {
	            return EnumArchiveObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoRegistroPropertiesType"))
	        {
	            return EnumArchiveObjectType.DOCUMENTO_REGISTRO_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoSemplicePropertiesType"))
	        {
	            return EnumArchiveObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE;
	        }
	        if(value.equals("DossierPropertiesType"))
	        {
	            return EnumArchiveObjectType.DOSSIER_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeAnnualePropertiesType"))
	        {
	            return EnumArchiveObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeContinuoPropertiesType"))
	        {
	            return EnumArchiveObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeEreditatoPropertiesType"))
	        {
	            return EnumArchiveObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeLegislaturaPropertiesType"))
	        {
	            return EnumArchiveObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeLiberoPropertiesType"))
	        {
	            return EnumArchiveObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloTemporaneoPropertiesType"))
	        {
	            return EnumArchiveObjectType.FASCICOLO_TEMPORANEO_PROPERTIES_TYPE;
	        }
	        if(value.equals("GruppoAllegatiPropertiesType"))
	        {
	            return EnumArchiveObjectType.GRUPPO_ALLEGATI_PROPERTIES_TYPE;
	        }
	        if(value.equals("HistoryModificheTecnichePropertiesType"))
	        {
	            return EnumArchiveObjectType.HISTORY_MODIFICHE_TECNICHE_PROPERTIES_TYPE;
	        }
	        if(value.equals("HistoryVecchieVersioniPropertiesType"))
	        {
	            return EnumArchiveObjectType.HISTORY_VECCHIE_VERSIONI_PROPERTIES_TYPE;
	        }
	        if(value.equals("SerieDossierPropertiesType"))
	        {
	            return EnumArchiveObjectType.SERIE_DOSSIER_PROPERTIES_TYPE;
	        }
	        if(value.equals("SerieFascicoliPropertiesType"))
	        {
	            return EnumArchiveObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE;
	        }
	        if(value.equals("SerieTipologicaDocumentiPropertiesType"))
	        {
	            return EnumArchiveObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;
	        }
	        if(value.equals("SottofascicoloPropertiesType"))
	        {
	            return EnumArchiveObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE;
	        }
	        if(value.equals("TitolarioPropertiesType"))
	        {
	            return EnumArchiveObjectType.TITOLARIO_PROPERTIES_TYPE;
	        }
	        if(value.equals("VocePropertiesType"))
	        {
	            return EnumArchiveObjectType.VOCE_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeFascicoliPropertiesType"))
	        {
	            return EnumArchiveObjectType.VOLUME_FASCICOLI_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeSerieFascicoliPropertiesType"))
	        {
	            return EnumArchiveObjectType.VOLUME_SERIE_FASCICOLI_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeSottofascicoliPropertiesType"))
	        {
	            return EnumArchiveObjectType.VOLUME_SOTTOFASCICOLI_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeSerieTipologicaDocumentiPropertiesType"))
	        {
	            return EnumArchiveObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumTipoDocumentoType convertFromStringToEnumTipoDocumentoType(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("Firmato"))
	        {
	            return EnumTipoDocumentoType.FIRMATO;
	        }
	        if(value.equals("Semplice"))
	        {
	            return EnumTipoDocumentoType.SEMPLICE;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumClassificazioneStatoType convertFromStringToEnumClassificazioneStatoType(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("Attiva"))
	        {
	            return EnumClassificazioneStatoType.ATTIVA;
	        }
	        if(value.equals("Cancellato"))
	        {
	            return EnumClassificazioneStatoType.CANCELLATO;
	        }
	        if(value.equals("Ri-classificato"))
	        {
	            return EnumClassificazioneStatoType.RI_CLASSIFICATO;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumObjectType convertFromStringToEnumObjectType(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("ActaACEPropertiesType"))
	        {
	            return EnumObjectType.ACTA_ACE_PROPERTIES_TYPE;
	        }
	        if(value.equals("AnnotazionePropertiesType"))
	        {
	            return EnumObjectType.ANNOTAZIONE_PROPERTIES_TYPE;
	        }
	        if(value.equals("AOOPropertiesType"))
	        {
	            return EnumObjectType.AOO_PROPERTIES_TYPE;
	        }
	        if(value.equals("ClassificazionePropertiesType"))
	        {
	            return EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE;
	        }
	        if(value.equals("ClipsMetallicaPropertiesType"))
	        {
	            return EnumObjectType.CLIPS_METALLICA_PROPERTIES_TYPE;
	        }
	        if(value.equals("ContenutoFisicoPropertiesType"))
	        {
	            return EnumObjectType.CONTENUTO_FISICO_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentAssociationPropertiesType"))
	        {
	            return EnumObjectType.DOCUMENT_ASSOCIATION_PROPERTIES_TYPE;
	        }
	        if(value.equals("CorrispondentePropertiesType"))
	        {
	            return EnumObjectType.CORRISPONDENTE_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentCompositionPropertiesType"))
	        {
	            return EnumObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoDBPropertiesType"))
	        {
	            return EnumObjectType.DOCUMENTO_DB_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoFisicoPropertiesType"))
	        {
	            return EnumObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoRegistroPropertiesType"))
	        {
	            return EnumObjectType.DOCUMENTO_REGISTRO_PROPERTIES_TYPE;
	        }
	        if(value.equals("DocumentoSemplicePropertiesType"))
	        {
	            return EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE;
	        }
	        if(value.equals("DossierPropertiesType"))
	        {
	            return EnumObjectType.DOSSIER_PROPERTIES_TYPE;
	        }
	        if(value.equals("EntePropertiesType"))
	        {
	            return EnumObjectType.ENTE_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeAnnualePropertiesType"))
	        {
	            return EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeContinuoPropertiesType"))
	        {
	            return EnumObjectType.FASCICOLO_REALE_CONTINUO_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeEreditatoPropertiesType"))
	        {
	            return EnumObjectType.FASCICOLO_REALE_EREDITATO_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeLegislaturaPropertiesType"))
	        {
	            return EnumObjectType.FASCICOLO_REALE_LEGISLATURA_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloRealeLiberoPropertiesType"))
	        {
	            return EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloStdPropertiesType"))
	        {
	            return EnumObjectType.FASCICOLO_STD_PROPERTIES_TYPE;
	        }
	        if(value.equals("FascicoloTemporaneoPropertiesType"))
	        {
	            return EnumObjectType.FASCICOLO_TEMPORANEO_PROPERTIES_TYPE;
	        }
	        if(value.equals("FormaDocumentariaPropertiesType"))
	        {
	            return EnumObjectType.FORMA_DOCUMENTARIA_PROPERTIES_TYPE;
	        }
	        if(value.equals("GruppoAllegatiPropertiesType"))
	        {
	            return EnumObjectType.GRUPPO_ALLEGATI_PROPERTIES_TYPE;
	        }
	        if(value.equals("GruppoAOOPropertiesType"))
	        {
	            return EnumObjectType.GRUPPO_AOO_PROPERTIES_TYPE;
	        }
	        if(value.equals("HistoryModificheTecnichePropertiesType"))
	        {
	            return EnumObjectType.HISTORY_MODIFICHE_TECNICHE_PROPERTIES_TYPE;
	        }
	        if(value.equals("NodoConStruttura"))
	        {
	            return EnumObjectType.NODO_CON_STRUTTURA;
	        }
	        if(value.equals("HistoryVecchieVersioniPropertiesType"))
	        {
	            return EnumObjectType.HISTORY_VECCHIE_VERSIONI_PROPERTIES_TYPE;
	        }
	        if(value.equals("NodoPropertiesType"))
	        {
	            return EnumObjectType.NODO_PROPERTIES_TYPE;
	        }
	        if(value.equals("ProfiloPropertiesType"))
	        {
	            return EnumObjectType.PROFILO_PROPERTIES_TYPE;
	        }
	        if(value.equals("RegistrazionePropertiesType"))
	        {
	            return EnumObjectType.REGISTRAZIONE_PROPERTIES_TYPE;
	        }
	        if(value.equals("RegistroPropertiesType"))
	        {
	            return EnumObjectType.REGISTRO_PROPERTIES_TYPE;
	        }
	        if(value.equals("SerieDossierPropertiesType"))
	        {
	            return EnumObjectType.SERIE_DOSSIER_PROPERTIES_TYPE;
	        }
	        if(value.equals("SerieFascicoliPropertiesType"))
	        {
	            return EnumObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE;
	        }
	        if(value.equals("SerieTipologicaDocumentiPropertiesType"))
	        {
	            return EnumObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;
	        }
	        if(value.equals("SistemaPropertiesType"))
	        {
	            return EnumObjectType.SISTEMA_PROPERTIES_TYPE;
	        }
	        if(value.equals("SoggettoPropertiesType"))
	        {
	            return EnumObjectType.SOGGETTO_PROPERTIES_TYPE;
	        }
	        if(value.equals("SottofascicoloPropertiesType"))
	        {
	            return EnumObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE;
	        }
	        if(value.equals("StrutturaPropertiesType"))
	        {
	            return EnumObjectType.STRUTTURA_PROPERTIES_TYPE;
	        }
	        if(value.equals("TitolarioPropertiesType"))
	        {
	            return EnumObjectType.TITOLARIO_PROPERTIES_TYPE;
	        }
	        if(value.equals("UtenteProfilo"))
	        {
	            return EnumObjectType.UTENTE_PROFILO;
	        }
	        if(value.equals("UtentePropertiesType"))
	        {
	            return EnumObjectType.UTENTE_PROPERTIES_TYPE;
	        }
	        if(value.equals("VerifyReportPropertiesType"))
	        {
	            return EnumObjectType.VERIFY_REPORT_PROPERTIES_TYPE;
	        }
	        if(value.equals("VocePropertiesType"))
	        {
	            return EnumObjectType.VOCE_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeFascicoliPropertiesType"))
	        {
	            return EnumObjectType.VOLUME_FASCICOLI_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeSerieFascicoliPropertiesType"))
	        {
	            return EnumObjectType.VOLUME_SERIE_FASCICOLI_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeSerieTipologicaDocumentiPropertiesType"))
	        {
	            return EnumObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE;
	        }
	        if(value.equals("VolumeSottofascicoliPropertiesType"))
	        {
	            return EnumObjectType.VOLUME_SOTTOFASCICOLI_PROPERTIES_TYPE;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumStepErrorAction convertFromStringToEnumStepErrorAction(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("exception"))
	        {
	            return EnumStepErrorAction.EXCEPTION;
	        }
	        if(value.equals("insert"))
	        {
	            return EnumStepErrorAction.INSERT;
	        } else
	        {
	            return null;
	        }
	    }

	    public static EnumStreamId convertFromStringToEnumStreamId(String value)
	    {
	        if(value == null)
	        {
	            return null;
	        }
	        if(value.equals("primary"))
	        {
	            return EnumStreamId.PRIMARY;
	        }
	        if(value.equals("renditionDocument"))
	        {
	            return EnumStreamId.RENDITION_DOCUMENT;
	        }
	        if(value.equals("renditionEngine"))
	        {
	            return EnumStreamId.RENDITION_ENGINE;
	        }
	        if(value.equals("signature"))
	        {
	            return EnumStreamId.SIGNATURE;
	        }
	        if(value.equals("timestamp"))
	        {
	            return EnumStreamId.TIMESTAMP;
	        } else
	        {
	            return null;
	        }
	    }

	    static 
	    {
	        ggmmaaaahhmmssSSSFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	        ggmmaaaahhmmssFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        DBFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        ggmmaaaaFormatter = new SimpleDateFormat("dd/MM/yyyy");
	        formatters = (new SimpleDateFormat[] {
	            ggmmaaaahhmmssSSSFormatter, DBFormatter, ggmmaaaahhmmssFormatter, ggmmaaaaFormatter
	        });
	        log = Logger.getLogger(ActaSrvConstants.ACTA_BUSINESS_LOG_CATEGORY);
	    }
	    
	    */
}
