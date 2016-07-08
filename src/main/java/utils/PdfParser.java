package utils;
import interfaces.RadocParser;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfParser implements RadocParser {

	private final String ENCONDING = "UTF-8";
	private final String REGEX_TUDO = "[\\s\\d\\p{L}\\/\\-\\.\\_\\:]+";
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

	/**
	 * {@inheritDoc}
     */
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
			//atividadesDeEnsino = paraArrayList(atividades);
		}

		return atividadesDeEnsino;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesDeOrientacao() {
		return new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesEmProjetos() {
		ArrayList<String> atividadesEmProjetos = new ArrayList<String>();
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_EM_PROJETOS, conteudoDoArquivo);

		Map<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades em projetos", "");
		substituicoes.put("Atividades de extensão", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		if(matcher.find()) {
			conteudoDoArquivo = matcher.group();
			conteudoDoArquivo = substituiOcorrencias(conteudoDoArquivo, substituicoes);

			String regexAtividadesIndividuais = "Título do Projeto:.*?Data Término:\\s*[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}";
			Matcher matcherAtividadesIndividuais = obtenhaMatcher(regexAtividadesIndividuais, conteudoDoArquivo);
			int contadorAtividadesIndividuais = 0;

			while(matcherAtividadesIndividuais.find()) {
				Matcher matcherAtividade;
				String regexAtividadeUnica = "Título do Projeto:REGEX_TUDO(REGEX_TUDO)REGEX_TUDOTabela:REGEX_TUDO(REGEX_TUDO)Unidade Responsável:REGEX_TUDOCHA:REGEX_TUDO([\\d]+)REGEX_TUDOData Início:REGEX_TUDO(\\d{2}\\/\\d{2}\\/\\d{4})[\\s\\d\\p{L}\\/\\-\\.\\_\\:]+Data Término:REGEX_TUDO(\\d{2}\\/\\d{2}\\/\\d{4})";
				regexAtividadeUnica = regexAtividadeUnica.replace("REGEX_TUDO", REGEX_TUDO);
				matcherAtividade = obtenhaMatcher(regexAtividadeUnica, matcherAtividadesIndividuais.group());

				while(matcherAtividade.find()) {
					atividadesEmProjetos.add(obtenhaLinhaDeRegistroPadronizado(contadorAtividadesIndividuais, matcherAtividade));
				}

				contadorAtividadesIndividuais++;
			}
		}

		return atividadesEmProjetos;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesDeExtensao() {
		return new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesDeQualificacao() {return new ArrayList<String>();}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesAcademicasEspeciais() {
		return new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesAdministrativas() {
		return new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaProdutos() {
		return new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAfastamentos() {
		return new ArrayList<String>();
	}

	/**
	 * Recupera o conteúdo do arquivo e remove as linhas não utilizadas.
	 * @return O documento parseado.
     */
	public String obtenhaConteudoArquivo(){
		if("".equals(conteudoArquivo)){
			conteudoArquivo = extraiaTexto();
			removaLinhasCabecalhoERodape();
		}

		return conteudoArquivo;
	}

	/**
	 * Recupera as informações textuais de um Radoc utilizndo a ferramenta PDFBox.
	 * @return O conteudo textual do Radoc.
     */
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

	/**
	 * Remove as linhas de cabeçalho e rodapé de um Radoc em sua representação textual.
	 */
	private void removaLinhasCabecalhoERodape(){
		conteudoArquivo = conteudoArquivo.replaceAll("Data: \\d+/\\d+/\\d+ \\d+:\\d+:\\d+\\s+[\\w\\s]+Página\\s+\\d+\\s+/\\s+\\d+","");
		conteudoArquivo = conteudoArquivo.replaceAll("EXTRATO DAS ATIVIDADES\\s+-\\s+ANO BASE:\\s+\\d+","");
		conteudoArquivo = conteudoArquivo.replaceAll("UNIVERSIDADE FEDERAL DE GOIÁS\\r\\nSISTEMA DE CADASTRO DE ATIVIDADES DOCENTES","");
		conteudoArquivo = conteudoArquivo.replaceAll("Curso Disciplina CHA Ano Sem Turma Sub Nº alunos Nº sub CHT CHP CHAC Conjug","");
		conteudoArquivo = conteudoArquivo.replaceAll("RGCG - Regime de Graduação Semestral","");
		conteudoArquivo = conteudoArquivo.replaceAll("LEGENDA: CHA - Carga horária da atividade \\| Sem - Semestre \\| Sub - Subturma \\| CHT, CHP e CHAC - Carga horária teórica, prática e acessória \\| Conjug - Disciplina conjugada","");
	}


	/**
	 * Retorna o Matcher equivalente para um data regex e um dado conteudo.
	 * @param regexConteudo Regex utilizada na configuração do matcher.
	 * @param conteudo Conteudo no qual será feito a análise da regex.
     * @return O matcher configurado para a regex e o conteúdo em análise.
     */
	private Matcher obtenhaMatcher (String regexConteudo, String conteudo){
		Pattern regex = Pattern.compile(regexConteudo);
		return regex.matcher(conteudo);
	}

	/**
	 * Substitui as ocorrências de determinada regex (key) por um dado valor (value)
	 * em um determinado conteúdo alvo.
	 * @param conteudo Conteúdo alvo no qual se deseja substituir as ocorrências.
	 * @param substituicoes Map, em que: key= regex que se deseja substituir, value= valor da substituição.
     * @return O conteúdo alterado, já com as substituições feitas.
     */
	private String substituiOcorrencias(String conteudo, Map<String, String> substituicoes) {
		for(String key : substituicoes.keySet()) {
			conteudo = conteudo.replaceAll(key, substituicoes.get(key));
		}
		return conteudo;
	}

	/**
	 * Obtém a linha padronizada de um registro.
	 * @param sequencial Número sequencial que identifica o registro na atividade de um determinado tipo.
	 * @param matcher Matcher para a atividade.
     * @return Linha padronizada de um registro.
     */
	private String obtenhaLinhaDeRegistroPadronizado(int sequencial, Matcher matcher) {
		String linhaDeRegistroPadrao = "";

		linhaDeRegistroPadrao += (sequencial+1) + ", ";
		linhaDeRegistroPadrao += matcher.group(1) + " " + matcher.group(2) + ", ";
		linhaDeRegistroPadrao += trataCargaHoraria(matcher) + ", ";
		linhaDeRegistroPadrao += trataDatas(matcher);

		return linhaDeRegistroPadrao;
	}

	/**
	 * Recebe o matcher da atividade qualquer e trata a carga horária anual.
	 * @param matcher Matcher para a atividade.
	 * @return A carga horária anual da atividade.
     */
	private String trataCargaHoraria(Matcher matcher) {
		if (matcher.group(3) != "") {
			return matcher.group(3);
		}

		String dtInicio = matcher.group(4).trim();
		String dtFim = matcher.group(5).trim();

		if (dtInicio == "") dtInicio = dtFim;
		if (dtFim == "") dtFim = dtInicio;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dtInicioDate = simpleDateFormat.parse(dtInicio);
			Date dtFimDate = simpleDateFormat.parse(dtFim);

			long diferenca = TimeUnit.DAYS.convert(dtFimDate.getTime() - dtInicioDate.getTime(), TimeUnit.MILLISECONDS);
			return String.valueOf(diferenca * 8);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Recebe o matcher da atividade qualquer e trata suas datas de início e fim.
	 * @param matcher Matcher para a atividade.
	 * @return Concatenação das datas de início e fim.
     */
	private String trataDatas(Matcher matcher) {

		String dtInicio = matcher.group(4).replaceAll("/", "").trim();
		String dtFim = matcher.group(5).replaceAll("/", "").trim();

		if (dtInicio == "") dtInicio = dtFim;
		if (dtFim == "") dtFim = dtInicio;

		return (dtInicio + ", " + dtFim);
	}

}
