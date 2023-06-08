package util;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dadosProcesso.dto.ficha_DTO;

public class ScrappingUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(ScrappingUtil.class);
	private static final String LINK_STRING = "http://esaj.tjba.jus.br/cpo/sg/search.do;jsessionid=12D5EFF1C1A33340B46089C881262E9F.cposg3?paginaConsulta=1&cbPesquisa=NUMPROC&tipoNuProcesso=UNIFICADO&numeroDigitoAnoUnificado=0809979-67.2015&foroNumeroUnificado=0080&dePesquisaNuUnificado=0809979-67.2015.8.05.0080&dePesquisa=";

	public static void main(String[] args) {
		String link = LINK_STRING;
		ScrappingUtil scrapping = new ScrappingUtil();
		scrapping.getInforProc(link);
		

	}

	public ficha_DTO getInforProc(String link) {
		ficha_DTO ficha = new ficha_DTO();
		Document document = null;

		try {
			document = Jsoup.connect(link).get();
			String title = document.title();
			LOGGER.info("Titulo da pagina: {}", title);

			// DADOS DO PROCESSO:
			// RECUPERAR NUMERO DO PROCESSO:
			String numProc = document
					.selectXpath("/html/body/table[4]//tr/td/table[2]//tr[1]/td[2]/table//tr/td/span[1]").first()
					.text();
			LOGGER.info("Processo: {}", numProc);
			// RECUPERAR CLASSE DO PROCESSO:
			String classProc = document
					.selectXpath("/html/body/table[4]//tr/td/table[2]//tr[2]/td[2]/table//tr/td/span/span").text();
			LOGGER.info("Classe: {}", classProc);
			// AREA:
			String areaProc = document
					.selectXpath("/html/body/table[4]/tbody/tr/td/table[2]/tbody/tr[3]/td[2]/table/tbody/tr/td").text();
			LOGGER.info(areaProc);
			// ASSUNTO:
			String assuntoProc = document.selectXpath("/html/body/table[4]//tr/td/table[2]//tr[4]/td[2]/span").text();
			LOGGER.info("Assunto do processo: {}", assuntoProc);
			// ORIGEM:
			String origProc = document.selectXpath("/html/body/table[4]//tr/td/table[2]//tr[5]/td[2]/span").first()
					.text();
			LOGGER.info("Origem do chamado: {}", origProc);
			// DISTRIBUIÇÃO:
			String distProc = document.selectXpath("/html/body/table[4]//tr/td/table[2]//tr[7]/td[2]/span").first()
					.text();
			LOGGER.info("Distribuição: {}", distProc);
			// RELATOR:
			String relatProc = document.selectXpath("/html/body/table[4]//tr/td/table[2]//tr[8]/td[2]/span").first()
					.text();
			LOGGER.info("Relator: {}", relatProc);
			
			System.out.println();
			System.out.println("Movimentações: ");
			System.out.println();

			String getMovsFromProc = getMovs(document);
			LOGGER.info(getMovsFromProc);

		} catch (Exception e) {
			LOGGER.error("Erro ao conectar com o google JSOUP -> {}", e.getMessage());

		}

		return ficha;

	}
	
	
	 public String getMovs(Document document) { 
	 String movimenta = null;
	 List<String>movsList = new ArrayList<>();
	 int listElement = 1; 
	 String linkString = "//*[@id=\"tabelaTodasMovimentacoes\"]/tbody/tr[";
	  
	  while (true) { 
	  String listMov = document.selectXpath(linkString+listElement+"]").text(); 
	  String recebe = document.selectXpath(linkString+(listElement+1)+"]").text();
	  movsList.add(listMov);
	  System.out.println(listMov); 
	  if( recebe.isEmpty()) {
		break;
	   
	  }
	  listElement++;
	  }
	  return movimenta; 
	  }
	 
	
	

}
