import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Query;
import org.apache.lucene.document.TextField;

public class LuceneTeste {

	private IndexWriter index;
	
	public LuceneTeste() throws IOException{
		/* indica o local físico de armazenamento do índice*/
		Directory dir = new RAMDirectory();
		/* indica a versão em uso do Lucene e o Analyzer usado */
		IndexWriterConfig config = new IndexWriterConfig(new BrazilianAnalyzer());
		config.setSimilarity(new BM25Similarity());
		/*Cria um índice no Lucene*/
		index = new IndexWriter(dir, config);
	}

	public void lerDocs(String pasta) throws IOException{
		File arquivos[];
		File diretorio = new File(pasta);
		arquivos = diretorio.listFiles();
		for(int i = 0; i < arquivos.length; i++){
			String texto = new String(Files.readAllBytes(arquivos[i].toPath()));
			Document doc = new Document();
			doc.add(new StringField("nomearq", arquivos[i].getName(), Field.Store.YES));
			doc.add(new TextField("texto", texto, Field.Store.YES));
			index.addDocument(doc);
		}
	}
	
	public void luceneTesteBusca(String busca, int n) throws IOException, ParseException{
		/*recebe a string de consulta e gera um objeto Query do Lucene.*/
		QueryParser qp = new QueryParser("texto", new BrazilianAnalyzer());
		/*Baseado neste objeto e no índice preconstruído, o método IndexSearcher.search() 
		 identifica os documentos que correspondem à pesquisa e retorna um objeto TopDocs.*/
		IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(index)); 
		
		searcher.setSimilarity(new BM25Similarity());
		
		Query query = qp.parse(busca);
		TopDocs topDocs = searcher.search(query, n);
		ScoreDoc[] hits = topDocs.scoreDocs;
		System.out.println("Os 10 melhores documentos são:");
		for (int i = 0; i < hits.length; i++) {
			Document doc = searcher.doc(hits[i].doc);
			System.out.println(doc.get("nomearq"));
		}
	}	
}