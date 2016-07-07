package utils;
import interfaces.RadocParser;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfParser implements RadocParser {

	private final String ENCONDING = "UTF-8";
	private final String REGEX_ATIVIDADES_DE_ENSINO = "REGEX_AQUI";
	private final String REGEX_ATIVIDADES_DE_ORIENTACAO = "REGEX_AQUI";

	private File arquivoFonte;
	private String conteudoArquivo;

	public PdfParser(File arquivoFonte) {
		this.arquivoFonte = arquivoFonte;
		this.conteudoArquivo = "";
	}

	public ArrayList<String> obtenhaAtividadesDeEnsino() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaAtividadesDeOrientacao() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaAtividadesEmProjetos() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaAtividadesDeExtensao() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaAtividadesDeQualificacao() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaAtividadesAcademicasEspeciais() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaAtividadesAdministrativas() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaProdutos() {
		return new ArrayList<String>();
	}

	public ArrayList<String> obtenhaAfastamentos() {
		return new ArrayList<String>();
	}

	public String obtenhaConteudoArquivo(){

		if("".equals(conteudoArquivo)){
			conteudoArquivo = extraiaTexto();
		}

		return conteudoArquivo;
	}

	private String extraiaTexto() {
		String textoPDF = "";

		try {
			PDFTextStripper pdfTextStripper = new PDFTextStripper(ENCONDING);
			PDFParser pdfParser = new PDFParser(new FileInputStream(arquivoFonte));

			pdfParser.parse();
			PDDocument pdDocument = pdfParser.getPDDocument();
			textoPDF =  pdfTextStripper.getText(pdDocument);
			pdDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return textoPDF;
	}
}
