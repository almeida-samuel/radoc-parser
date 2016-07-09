import parsers.PdfParser;

import java.io.File;

/**
 * Classe que representa o executável do programa.
 */
public class ExtrairAtividades {

	public static void main(String[] args){
		PdfParser parser = new PdfParser(new File(args[0]));
		parser.extraiAtividades(args[1]);
	}
}
