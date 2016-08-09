import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		Scanner ler = new Scanner(System.in);
		String consulta;
		LuceneTeste teste = new LuceneTeste();
		
		teste.lerDocs("C:\\Users\\Dandara\\workspace\\lab1-checkpoint3\\src\\docs");
		
		System.out.printf("O que você está procurando: ");
		consulta = ler.nextLine();
		
		teste.luceneTesteBusca(consulta, 10);
		

	}

}
