package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.FolderDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.integration.vo.ArchivioFileVO;
import it.csi.pbandi.pbservizit.util.FileSqlUtil;
import it.csi.pbandi.pbservizit.util.NumberUtil;
import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;

@RunWith(PbwebJunitClassRunner.class)
public class FolderTest extends TestBaseService  {

	public static final String CLASS_NAME = "FolderTest";
	
	protected FileSqlUtil fileSqlUtil;
	//JdbcTemplate jdbcTemplate;
	
	@Autowired
	GenericDAO genericDAO;
	
	public void setUp() throws Exception {
		final String methodName = "setUp";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		System.out.println(logprefix + " BEGIN-END");
	}
	
	public void tearDown() throws Exception {
		final String methodName = "tearDown";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		System.out.println(logprefix + " BEGIN-END");
	}
	
	@Test
	public void leggiRootTest() {
		long idSoggettoBeneficiario = 3550L;
		// t.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl -> inizializzaArchivioFile
		// Restituisce tutto il contenuto della root del beneficiario.
		try {
			
			System.out.println("\n\n \n [leggiRootTest] genericDAO=" + genericDAO);
			
			List<ArchivioFileVO> listaArchivioFileVO = leggiContenutoRoot (idSoggettoBeneficiario);
			System.out.println("[leggiRootTest]  listaArchivioFileVO.size() = " + listaArchivioFileVO.size());
			
			
			Map<String, FolderDTO> folders = archivioFileToListFolderDTOOrdered(listaArchivioFileVO);
			System.out.println("[leggiRootTest]  folders=" + folders);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		System.out.println("[leggiRootTest]  END \n\n \n");
	}
	
	private List<ArchivioFileVO> leggiContenutoRoot (long idSoggettoBeneficiario) 
			throws InvalidParameterException, Exception {
		String prf = "[ArchivioFileDAOImpl::leggiContenutoRoot] ";
		System.out.println(prf + " BEGIN");
		List<ArchivioFileVO> result = null;
		try {
			
			StringBuffer sb = miaQuery();
//			StringBuffer sb2 = queryOrig();
			
			System.out.println(prf + "\n" + sb.toString());
			long start = System.currentTimeMillis();
			result = genericDAO.getJdbcTemplate().query(sb.toString(), new Object[] {}, new BeanRowMapper(ArchivioFileVO.class));
			long totTime =  System.currentTimeMillis() - start;
			System.out.println(prf + "Record trovati = " + result.size() + " in ["+totTime+"]ms");
			
		} catch (Exception e) {
			System.out.println(prf+" ERRORE in leggiContenutoRoot(): " + e);
			throw new DaoException("Errore durante la ricerca dei file.", e);
		}
		finally {
			System.out.println(prf+" END");
		}
		return result;
	}

	private Map<String,FolderDTO> archivioFileToListFolderDTOOrdered(
			List<ArchivioFileVO> filez) {
		
		String prf = "[archivioFileToListFolderDTOOrdered] ";
		System.out.println(prf + " BEGIN");	
		System.out.println(prf + "lista filez.size=="+filez.size());
		
		//ciclo su tutti i doc ed estraggo le cartelle
		Map<Long,FolderDTO> folders = new HashMap<Long, FolderDTO>();		
		for (ArchivioFileVO archivioFileVO : filez) {
	      FolderDTO folderDTO = createFolderDTO(archivioFileVO);
	      folders.put(folderDTO.getIdFolder(), folderDTO);
		}
		System.out.println(prf +"lista folders size="+folders.size());
		
		//riciclo su tutti i doc che non fanno riferimento ad un doc fisico 
		// e per ogni folder aggiungo il doc fisico alla lista folderDTO.getFiles().add(fileDTO);
		for (ArchivioFileVO archivioFileVO : filez) {		      
		      if(archivioFileVO.getIdDocumentoIndex()!=null){
		    	  FileDTO fileDTO = createFileDTO(archivioFileVO);
		    	  FolderDTO folderDTO =  folders.get(archivioFileVO.getIdFolder().longValue());
		    	  addFileToFolder(folderDTO, fileDTO);
		      }
		}
		System.out.println(prf +"addFileToFolder terminato");
		
		Map<String,FolderDTO> foldersIerarchical=new HashMap<String, FolderDTO>();
		
		int i = 0;
		for ( FolderDTO folderDTO : folders.values()) {
			System.out.println(prf +"["+ i++ +"] IdFolder="+folderDTO.getIdFolder()+", nomeFolder="+folderDTO.getNomeFolder());
			
//			if(folderDTO.getIdFolder()!=51660) { //PK
				if(folderDTO.getIdPadre()==null){
					if(!foldersIerarchical.containsKey(folderDTO.getNomeFolder() )){
						foldersIerarchical.put(folderDTO.getNomeFolder(),folderDTO);
					}
				}else{
					FolderDTO father = folders.get(folderDTO.getIdPadre());
					if(father!=null) {
						addFolderToFolders(father, father.getFolders(), folderDTO);
					}else {
						System.out.println(prf + "padre non trovato , idPadre=" + folderDTO.getIdPadre());
					}
					
				}
//			}
		}
		System.out.println(prf + "foldersIerarchical=" + foldersIerarchical);
		return foldersIerarchical ;
	}
	
	// Modificato rispetto all'originale per il passaggio da [] a List.
	private void addFileToFolder(FolderDTO folderDTO, FileDTO fileDTO) {		
		if (folderDTO.getFiles() == null)
			folderDTO.setFiles(new ArrayList<FileDTO>());
		folderDTO.getFiles().add(fileDTO);
	}
		
	// Modificato rispetto all'originale per il passaggio da [] a List.
	private void addFolderToFolders(FolderDTO father, ArrayList<FolderDTO> children, FolderDTO child) {
		if (children == null) {
			children = new ArrayList<FolderDTO>();
		}
		// mi sembra inutile, lo commento.
		//for (FolderDTO oldChild : children) {
		//	if(oldChild.getNomeFolder().equalsIgnoreCase(child.getNomeFolder()))
		//		return;
		//}
		children.add(child);
		Collections.sort(children, new AlphabeticalFolderComparator());
		father.setFolders(children);		
	}
	
	class AlphabeticalFolderComparator implements Comparator<FolderDTO>{
		public int compare(FolderDTO o1, FolderDTO o2) {
			 return o1.getNomeFolder().toLowerCase().compareTo( o2.getNomeFolder().toLowerCase());
		}
		 
	}
		
	private FolderDTO createFolderDTO(ArchivioFileVO file) {
		FolderDTO folderDTO= new FolderDTO();
		folderDTO.setDtAggiornamento(file.getDtAggiornamentoFolder());
		folderDTO.setDtInserimento(file.getDtInserimentoFolder());
		folderDTO.setIdFolder(file.getIdFolder().longValue());
		if(file.getIdPadre()!=null) folderDTO.setIdPadre(file.getIdPadre().longValue());
		if(file.getIdSoggettoBen()!=null) folderDTO.setIdSoggettoBen(file.getIdSoggettoBen().longValue());
		folderDTO.setNomeFolder(file.getNomeFolder());
		if (file.getIdProgettoFolder() != null) {
			folderDTO.setIdProgettoFolder(file.getIdProgettoFolder().longValue());	
		}		
		return folderDTO;
	}
	
	private FileDTO createFileDTO(ArchivioFileVO file) {
		FileDTO fileDTO=new FileDTO();
		fileDTO.setDtAggiornamento(file.getDtAggiornamentoFile());
		fileDTO.setDtInserimento(file.getDtInserimentoFile());
		if (file.getIdDocumentoIndex() != null)
			fileDTO.setIdDocumentoIndex(file.getIdDocumentoIndex().longValue());
		if (file.getIdFolder() != null)
			fileDTO.setIdFolder(file.getIdFolder().longValue());
		fileDTO.setNomeFile(file.getNomeFile());
		fileDTO.setSizeFile(file.getSizeFile()!=null?file.getSizeFile().longValue():0l);
		if (file.getIdProgetto() != null)
			fileDTO.setIdProgetto(NumberUtil.toLong(file.getIdProgetto()));
		if(file.getEntitiesAssociated()!=null && file.getEntitiesAssociated().longValue()>0){
			fileDTO.setEntityAssociated(file.getEntitiesAssociated().longValue());
		}
		if(file.getIslocked()!=null && file.getIslocked().equalsIgnoreCase("S"))
			fileDTO.setIsLocked(true);
		else
			fileDTO.setIsLocked(false);
		fileDTO.setNumProtocollo(file.getNumProtocollo());
		
		if (file.getIdEntita() != null) {
			fileDTO.setIdEntita(file.getIdEntita().longValue());	
		}
		if (file.getIdTarget() != null) {
			fileDTO.setIdTarget(file.getIdTarget().longValue());	
		}
		fileDTO.setFlagEntita(file.getFlagEntita());	//Jira PBANDI-2815		
		fileDTO.setDescStatoTipoDocIndex(file.getDescStatoTipoDocIndex());		
		return fileDTO;
	}
	
	private StringBuffer miaQuery() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT ENTITIES_ASSOCIATED, ISLOCKED, NOME_FILE, DT_INSERIMENTO_FOLDER, DT_INSERIMENTO_FILE, DT_AGGIORNAMENTO_FOLDER, DT_AGGIORNAMENTO_FILE, ID_FILE, ");
		sb.append(" SIZE_FILE, ID_FOLDER, ID_PADRE, NOME_FOLDER, ID_SOGGETTO_BEN, ID_PROGETTO_FOLDER, ID_DOCUMENTO_INDEX, ID_ENTITA, ID_TARGET, ID_PROGETTO, NUM_PROTOCOLLO,  ");
		sb.append(" ID_FILE_ENTITA, FLAG_ENTITA, DESC_STATO_TIPO_DOC_INDEX ");
		sb.append(" FROM pbandi_t_poke ");
		sb.append(" where ID_SOGGETTO_BEN = 3550 order by  ID_PADRE nulls first, NOME_FOLDER, NOME_FILE ");
		return sb;
	}
	
	private StringBuffer queryOrig() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select ");
		sb.append(" (  select COUNT(ID_ENTITA)  from PBANDI_T_FILE_ENTITA where ID_FILE=FI.ID_FILE ) entities_associated, ");
		sb.append(" case when exists ( select 'x'  ");
		sb.append("          from PBANDI_T_FILE_ENTITA      ");
		sb.append("            join  ");
		sb.append("               PBANDI_T_DOCUMENTO_DI_SPESA ");
		sb.append("                     on PBANDI_T_DOCUMENTO_DI_SPESA.ID_DOCUMENTO_DI_SPESA = PBANDI_T_FILE_ENTITA.ID_TARGET  ");
		sb.append("                        and PBANDI_T_FILE_ENTITA.ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_DOCUMENTO_DI_SPESA') ");
		sb.append("            join ");
		sb.append("               PBANDI_R_DOC_SPESA_PROGETTO using(ID_DOCUMENTO_DI_SPESA)     ");
		sb.append("                  where PBANDI_T_FILE_ENTITA.ID_FILE=FI.ID_FILE   ");
		sb.append("                        and PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA in (select ID_STATO_DOCUMENTO_SPESA  ");
		sb.append("                                                                       from PBANDI_D_STATO_DOCUMENTO_SPESA  ");
		sb.append("                                                                       where  ");
		sb.append("                                                                       DESC_BREVE_STATO_DOC_SPESA not in ('I','R'))) ");
		sb.append("            or ");
		sb.append("            exists ( select 'x'  ");
		sb.append("            from PBANDI_T_FILE_ENTITA      ");
		sb.append("              join  ");
		sb.append("                 PBANDI_R_PAGAMENTO_DOC_SPESA ");
		sb.append("                       on pbandi_r_pagamento_doc_spesa.ID_PAGAMENTO= PBANDI_T_FILE_ENTITA.ID_TARGET  ");
		sb.append("                          and PBANDI_T_FILE_ENTITA.ID_ENTITA=(select ID_ENTITA from PBANDI_C_ENTITA where NOME_ENTITA='PBANDI_T_PAGAMENTO') ");
		sb.append("              join ");
		sb.append("                 PBANDI_R_DOC_SPESA_PROGETTO using(ID_DOCUMENTO_DI_SPESA)     ");
		sb.append("                    where PBANDI_T_FILE_ENTITA.ID_FILE=FI.ID_FILE   ");
		sb.append("                          and PBANDI_R_DOC_SPESA_PROGETTO.ID_STATO_DOCUMENTO_SPESA in (select ID_STATO_DOCUMENTO_SPESA  ");
		sb.append("                                                                         from PBANDI_D_STATO_DOCUMENTO_SPESA  ");
		sb.append("                                                                         where  ");
		sb.append("                                                                         DESC_BREVE_STATO_DOC_SPESA not in ('I','R'))) ");
		sb.append("         OR EXISTS ");
		sb.append("        (SELECT 'x' ");
		sb.append("        FROM PBANDI_T_FILE_ENTITA ");
		sb.append("        WHERE id_progetto is not null  ");
		sb.append("        	  and PBANDI_T_FILE_ENTITA.ID_FILE =FI.ID_FILE  ");
		sb.append("        	  and (ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_COMUNICAZ_FINE_PROG') ");
		sb.append("        		 OR  ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_DICHIARAZIONE_SPESA') ");
		sb.append("        		 OR ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_RICHIESTA_EROGAZIONE') ");
		sb.append("        		 OR ID_ENTITA IN(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA='PBANDI_T_CONTO_ECONOMICO') )       ");
		sb.append("        )                                                           ");
		sb.append("         then 'S' ");
		sb.append("       else ");
		sb.append("        'N' end islocked, ");
		sb.append("fi.nome_file, ");
		sb.append("fo.dt_inserimento as dt_inserimento_folder, ");
		sb.append("fi.dt_inserimento as dt_inserimento_file, ");
		sb.append("FO.DT_AGGIORNAMENTO as DT_AGGIORNAMENTO_FOLDER,  ");
		sb.append("fi.dt_aggiornamento as  dt_aggiornamento_file, ");
		sb.append("fi.id_file, ");
		sb.append("fi.size_file , ");
		sb.append("id_folder , ");
		sb.append("fo.id_padre, ");
		sb.append("fo.nome_folder, ");
		sb.append("fo.id_soggetto_ben, ");
		sb.append("fo.id_progetto as id_progetto_folder, ");
		sb.append("id_documento_index, ");
		sb.append("docindex.id_entita, ");
		sb.append("docindex.id_target, ");
		sb.append("DOCINDEX.ID_PROGETTO,  ");
		sb.append("docindex.num_protocollo, ");
		sb.append("null as id_file_entita, ");
		sb.append("null as flag_entita, ");
		sb.append("null as desc_stato_tipo_doc_index ");
		sb.append("from pbandi_t_folder fo  ");
		sb.append("left join pbandi_t_file fi using (id_folder) ");
		sb.append("left join PBANDI_T_DOCUMENTO_INDEX DOCINDEX using (ID_DOCUMENTO_INDEX) where fo.id_soggetto_ben = 3550 order by  ID_PADRE nulls first, NOME_FOLDER, FI.NOME_FILE ");
		
		return sb;
	}
	
}
