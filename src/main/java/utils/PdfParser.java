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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfParser implements RadocParser {

	private final String ENCONDING = "UTF-8";
	private final String REGEX_ATIVIDADES_DE_ENSINO = "Atividades de ensino([\\p{L}\\s-\\d]+)Atividades de orientação";
	private final String REGEX_ATIVIDADES_DE_ORIENTACAO = "Atividades de orientação([\\p{L}\\s-\\d\\W]+)Atividades em projetos";
	private final String REGEX_ATIVIDADES_EM_PROJETOS = "Atividades em projetos([\\p{L}\\s-\\d\\W]+)Atividades de extensão";
	private final String REGEX_ATIVIDADES_DE_EXTENSAO = "Atividades de extensão([\\p{L}\\s-\\d\\W]+)Atividades de qualificação";
	private final String REGEX_ATIVIDADES_DE_QUALIFICACAO = "Atividades de qualificação([\\p{L}\\s-\\d\\W]+)Atividades acadêmicas especiais";
	private final String REGEX_ATIVIDADES_ADMINISTRATIVAS = "Atividades administrativas([\\p{L}\\s-\\d\\W]+)^Produtos\\n";
	private final String REGEX_PRODUTOS = "^Produtos\\n([\\p{L}\\s-\\d\\W]+)";

	private File arquivoFonte;
	private String conteudoArquivo;

	public PdfParser(File arquivoFonte) {
		this.arquivoFonte = arquivoFonte;
		this.conteudoArquivo = "";
	}

	public ArrayList<String> obtenhaAtividadesDeEnsino() {
		ArrayList<String> atividadesDeEnsino = new ArrayList<String>();
		String conteudo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_DE_ENSINO, conteudo);

		if(matcher.find()){
			conteudo = matcher.group();
			conteudo = conteudo.replaceAll("Atividades de ensino","");
			conteudo = conteudo.replaceAll("Atividades de orientação","");
			conteudo = conteudo.replaceAll("\\n+"," ");
			String[] atividades = conteudo.replaceAll("\\r+","").split("\\d+\\s(SIM|NÃO)");
			atividadesDeEnsino = paraArrayList(atividades);
		}

		return atividadesDeEnsino;
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

	public ArrayList<String> obtenhaAtividadesDeQualificacao() {return new ArrayList<String>();}

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
			removaLinhasInuteis();
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

	private void removaLinhasInuteis(){
		conteudoArquivo = conteudoArquivo.replaceAll("Data: \\d+/\\d+/\\d+ \\d+:\\d+:\\d+\\s+[\\w\\s]+Página\\s+\\d+\\s+/\\s+\\d+","");
		conteudoArquivo = conteudoArquivo.replaceAll("EXTRATO DAS ATIVIDADES\\s+-\\s+ANO BASE:\\s+\\d+","");
		conteudoArquivo = conteudoArquivo.replaceAll("UNIVERSIDADE FEDERAL DE GOIÁS\\r\\nSISTEMA DE CADASTRO DE ATIVIDADES DOCENTES","");
		conteudoArquivo = conteudoArquivo.replaceAll("Curso Disciplina CHA Ano Sem Turma Sub Nº alunos Nº sub CHT CHP CHAC Conjug","");
		conteudoArquivo = conteudoArquivo.replaceAll("RGCG - Regime de Graduação Semestral","");
		conteudoArquivo = conteudoArquivo.replaceAll("LEGENDA: CHA - Carga horária da atividade \\| Sem - Semestre \\| Sub - Subturma \\| CHT, CHP e CHAC - Carga horária teórica, prática e acessória \\| Conjug - Disciplina conjugada","");
	}

	private ArrayList<String> paraArrayList(String[] entrada){
		ArrayList array = new ArrayList<String>();
		for(int i = 0; i < entrada.length; i++){
			entrada[i] = entrada[i].trim();
			if(entrada[i] != ""){
				array.add(entrada[i].trim());
			}
		}
		return array;
	}

	private Matcher obtenhaMatcher (String regexConteudo, String conteudo){
		Pattern regex = Pattern.compile(regexConteudo);
		return regex.matcher(conteudo);
	}
}
