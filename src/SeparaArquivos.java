import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class SeparaArquivos {

	private static final String OUTPUT_PATH = "src/docs/doc";
	private static final String INPUT_PATH = "src/ptwiki-v2.trec";

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

		File fXmlFile = new File(INPUT_PATH);
		
		// Biblioteca para ler xml
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(fXmlFile);

		document.getDocumentElement().normalize();
		
		// recuperando todos os elementos dentro da tag DOC
		NodeList docs = document.getElementsByTagName("DOC");


		// percorrendo todos os elementos
		for (int i = 0; i < docs.getLength(); i++) {
			
			Node node = docs.item(i);
			Element eElement = (Element) node;
			String docno = eElement.getElementsByTagName("DOCNO").item(0).getTextContent();
			String paragraph = eElement.getElementsByTagName("P").item(0).getTextContent();
			paragraph = clean(paragraph);
			
			PrintWriter writer = new PrintWriter(OUTPUT_PATH + docno + ".txt", "UTF-8");
			
			writer.print(paragraph);
			writer.close();
			
			
		}
	
	}
	
	private static String clean(String str) {
		str = str.toLowerCase();
		str = str.replaceAll("&.{2,4};", " ");
		str = str.replaceAll("\\{\\{!\\}\\}", " ");
		str = str.replaceAll("\\{\\{.*?\\}\\}", "");
		str = str.replaceAll("[^a-z0-9çáéíóúàãõâêô-]", " ");

		return (str);
	}

	
}
