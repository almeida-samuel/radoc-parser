import parsers.PdfParser;

import java.io.File;

/**
 * Classe que representa o executável do programa.
 */
public class ExtrairAtividades {

	private static final String ARQUIVO_ENTRADA_PATH = "src/test/resources/radoc2015.pdf";
	private static final String ARQUIVO_SAIDA_PATH = "src/test/resources/atividades.txt";

	public static void main(String[] args){
		PdfParser parser = new PdfParser(new File(args[0]));
		parser.extraiAtividades(ARQUIVO_SAIDA_PATH);
	}
}
