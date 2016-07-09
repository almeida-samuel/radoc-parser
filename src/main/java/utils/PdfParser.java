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
	private final String REGEX_TUDO = "[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?";
	private final String REGEX_ATIVIDADES_DE_ENSINO = "Atividades de ensino([\\p{L}\\s-\\d]+)Atividades de orientação";
	private final String REGEX_ATIVIDADES_DE_ORIENTACAO = "Atividades de orientação([\\p{L}\\s-\\d\\W]+)Atividades em projetos";
	private final String REGEX_ATIVIDADES_EM_PROJETOS = "Atividades em projetos([\\p{L}\\s-\\d\\W]+)Atividades de extensão";
	private final String REGEX_ATIVIDADES_DE_EXTENSAO = "Atividades de extensão([\\p{L}\\s-\\d\\W]+)Atividades de qualificação";
	private final String REGEX_ATIVIDADES_DE_QUALIFICACAO = "Atividades de qualificação[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades acadêmicas especiais";
	private final String REGEX_ATIVIDADES_ACADEMICAS_ESPECIAIS = "Atividades acadêmicas especiais[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades administrativas";
	private final String REGEX_ATIVIDADES_ADMINISTRATIVAS = "Atividades administrativas[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Produtos";
	private final String REGEX_PRODUTOS = "Produtos[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+";
	private final String REGEX_AFASTAMENTOS = "Afastamento([\\p{L}\\s-\\d]+)Atividades de ensino";

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
		ArrayList<String> atividadesDeEnsino;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_DE_ENSINO, conteudoDoArquivo);
		String regexAtividadesIndividuais = "(.+?)()(\\d+).+?(\\d{4}).+?()(SIM|NÃO)";
		String regexAtividadeUnica = "(.+?)()(\\d{2,3}).+(\\d{4}).+()(SIM|NÃO)";
		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades de ensino", "");
		substituicoes.put("Atividades de orientação", "");
		substituicoes.put("\n+", "");
		substituicoes.put("\r+", "");

		atividadesDeEnsino = obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

		return atividadesDeEnsino;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesDeOrientacao() {
		ArrayList<String> atividadesDeOrientacao;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_DE_ORIENTACAO, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades de orientação", "");
		substituicoes.put("Atividades em projetos", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexAtividadesIndividuais = "Título do trabalho:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+\\d+\\/\\d+\\/\\d+";
		String regexAtividadeUnica = "Título do trabalho:([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Tabela:([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Orientando:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data início:\\s+(\\d+\\/\\d+\\/\\d+)[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+(\\d+\\/\\d+\\/\\d+)";

		atividadesDeOrientacao = obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

		return atividadesDeOrientacao;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesEmProjetos() {
		ArrayList<String> atividadesEmProjetos;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_EM_PROJETOS, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades em projetos", "");
		substituicoes.put("Atividades de extensão", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexAtividadesIndividuais = "Título do Projeto:.*?Data Término:\\s*[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}";
		String regexAtividadeUnica = "Título do Projeto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)\\s+Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Unidade Responsável:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data Início:\\s+(\\d+\\/\\d+\\/\\d+)\\s+Data Término:\\s+(\\d+\\/\\d+\\/\\d+)";

		atividadesEmProjetos = obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

		return atividadesEmProjetos;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesDeExtensao() {
		ArrayList<String> atividadesDeOrientacao = new ArrayList<String>();
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_DE_EXTENSAO, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades de extensão", "");
		substituicoes.put("Atividades de qualificação", "Tabela");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		if(matcher.find()) {
			conteudoDoArquivo = matcher.group();
			conteudoDoArquivo = substituiOcorrencias(conteudoDoArquivo, substituicoes);

			String regexAtividadesIndividuais = "Tabela:.+?(?=Tabela)";
			Matcher matcherAtividadesIndividuais = obtenhaMatcher(regexAtividadesIndividuais, conteudoDoArquivo);
			int contadorAtividadesIndividuais = 0;

			while(matcherAtividadesIndividuais.find()) {
				Matcher matcherAtividade;
				String regexAtividadeUnica = "Tabela:.+?CHA:.+?(\\d+).+?Data início:.+?(\\d+\\/\\d+\\/\\d+).+?Data término:.+?(\\d+\\/\\d+\\/\\d+).+?Descrição da atividade:.+?(.+).+?Descrição da clientela:.+?(.+)";
				matcherAtividade = obtenhaMatcher(regexAtividadeUnica, matcherAtividadesIndividuais.group());
				while(matcherAtividade.find()) {
					String atividadeTratada = trateAtividadeDeExtensao(matcherAtividade);
					Matcher matcherAtividadeTratada = obtenhaMatcher("(.+?)\\n(.+?)\\n(.+?)\\n(.+)\\n(.+)",atividadeTratada);
					matcherAtividadeTratada.find();
					atividadesDeOrientacao.add(obtenhaLinhaDeRegistroPadronizado(contadorAtividadesIndividuais, matcherAtividadeTratada));
				}

				contadorAtividadesIndividuais++;
			}
		}

		return atividadesDeOrientacao;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesDeQualificacao() {
		ArrayList<String> atividadesDeQualificacao;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_DE_QUALIFICACAO, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades de qualificação", "");
		substituicoes.put("Atividades acadêmicas especiais", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexAtividadesQualificacaoIndividuais = "Tabela:\\s+[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data de término:\\s+\\d{2}\\/\\d{2}\\/\\d{4}";
		String regexAtividadesQualificacaoUnica = "Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Descrição:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)CHA:\\s+(\\d+)\\s+Data de início:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Data de término:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})";

		atividadesDeQualificacao = obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesQualificacaoIndividuais, regexAtividadesQualificacaoUnica, substituicoes);

		return atividadesDeQualificacao;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesAcademicasEspeciais() {
		ArrayList<String> atividadesAcademicasEspeciais = new ArrayList<String>();
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_ACADEMICAS_ESPECIAIS, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades acadêmicas especiais", "");
		substituicoes.put("Atividades administrativas", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		if(matcher.find()) {
			conteudoDoArquivo = matcher.group();
			conteudoDoArquivo = substituiOcorrencias(conteudoDoArquivo, substituicoes);

			String regexAtividadesIndividuais = "Tabela:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?(?=\\bTabela\\b|$)";
			Matcher matcherAtividadesIndividuais = obtenhaMatcher(regexAtividadesIndividuais, conteudoDoArquivo);
			int contadorAtividadesIndividuais = 0;

			while(matcherAtividadesIndividuais.find()) {
				Matcher matcherAtividade;
				String regexAtividadeUnica = "Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)CHA:\\s+(\\d+)\\s+Data início:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Data término:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Descrição Complementar:([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Descrição da Clientela";
				matcherAtividade = obtenhaMatcher(regexAtividadeUnica, matcherAtividadesIndividuais.group());
				while(matcherAtividade.find()) {
					String atividadeTratada = trateAtividadeAcademicaEspecial(matcherAtividade);
					Matcher matcherAtividadeTratada = obtenhaMatcher("(.+?)\\n(.+?)\\n(.+?)\\n(.+)\\n(.+)",atividadeTratada);
					matcherAtividadeTratada.find();
					atividadesAcademicasEspeciais.add(obtenhaLinhaDeRegistroPadronizado(contadorAtividadesIndividuais, matcherAtividadeTratada));
				}

				contadorAtividadesIndividuais++;
			}
		}

		return atividadesAcademicasEspeciais;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAtividadesAdministrativas() {
		ArrayList<String> atividadesAdministrativas;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_ATIVIDADES_ADMINISTRATIVAS, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Atividades administrativas", "");
		substituicoes.put("Produtos", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexAtividadesIndividuais = "\\s+Tabela:\\s+[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+\\d{2}\\/\\d{2}\\/\\d{4}";
		String regexAtividadeUnica = "\\s+Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Descrição:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Órgão emissor[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data início:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Data término:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})";

		atividadesAdministrativas = obtenhaRegistros(conteudoDoArquivo, matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

		return atividadesAdministrativas;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaProdutos() {
		ArrayList<String> produtos;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_PRODUTOS, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Produtos", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexProdutoIndividual = "Descrição do produto:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Editora:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?(?=\\bDescrição\\b|$)";
		String regexProdutoUnico = "Descrição do produto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Título do produto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Autoria[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?()Data:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})()";

		produtos = obtenhaRegistros(conteudoDoArquivo, matcher, regexProdutoIndividual, regexProdutoUnico, substituicoes);

		return produtos;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAfastamentos() {
		ArrayList<String> afastamentos;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = obtenhaMatcher(REGEX_AFASTAMENTOS, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Afastasmentos", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexAfastamentoIndividual = "";
		String regexAfastamentoUnico = "";

		afastamentos = obtenhaRegistros(conteudoDoArquivo, matcher, regexAfastamentoIndividual, regexAfastamentoUnico, substituicoes);

		return afastamentos;
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


	private ArrayList<String> obtenhaRegistros(String conteudoDoArquivo, Matcher matcherGeral, String regexRegistrosIndividuais, String regexRegistroUnico, HashMap substituicoes){
		ArrayList<String> registros = new ArrayList<String>();

		if(matcherGeral.find()) {
			conteudoDoArquivo = matcherGeral.group();
			conteudoDoArquivo = substituiOcorrencias(conteudoDoArquivo, substituicoes);

			Matcher matcherProdutosIndividuais = obtenhaMatcher(regexRegistrosIndividuais, conteudoDoArquivo);
			int contadorProdutosIndividuais = 0;

			while(matcherProdutosIndividuais.find()) {
				Matcher matcherAtividade;
				matcherAtividade = obtenhaMatcher(regexRegistroUnico, matcherProdutosIndividuais.group());

				while(matcherAtividade.find()) {
					registros.add(obtenhaLinhaDeRegistroPadronizado(contadorProdutosIndividuais, matcherAtividade));
				}

				contadorProdutosIndividuais++;
			}
		}

		return registros;
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
	private String substituiOcorrencias(String conteudo, HashMap<String, String> substituicoes) {
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
		if (!"".equals(matcher.group(3))) {
			return matcher.group(3);
		}

		String dtInicio = matcher.group(4).trim();
		String dtFim = matcher.group(5).trim();

		if ("".equals(dtInicio)) dtInicio = dtFim;
		if ("".equals(dtFim)) dtFim = dtInicio;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dtInicioDate = simpleDateFormat.parse(dtInicio);
			Date dtFimDate = simpleDateFormat.parse(dtFim);

			long diferenca = TimeUnit.DAYS.convert(dtFimDate.getTime() - dtInicioDate.getTime(), TimeUnit.MILLISECONDS);
			if(diferenca == 0) return String.valueOf(8);
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

		if ("".equals(dtInicio)) dtInicio = dtFim;
		if ("".equals(dtFim)) dtFim = dtInicio;

		return (dtInicio + ", " + dtFim);
	}
	/**
	 *Trata as atividades de extensão ao padrão de grupos
	 * @param matcher Matcher para a atividade.
	 * @return Atividade tratada
	 */
	private String trateAtividadeDeExtensao(Matcher matcher){
		return matcher.group(4) + "\n" + matcher.group(5) + "\n" + matcher.group(1) + "\n" + matcher.group(2) + "\n" + matcher.group(3);
	}

	/**
	 * Rearranjo das atividadades acadêmicas especiais para a adequação ao formato
	 * definido pelo método obtenhaLinhaDeRegistroPadronizado.
	 * @param matcher Matcher para a atividade.
	 * @return Rearranjo das atividades;
     */
	private String trateAtividadeAcademicaEspecial(Matcher matcher) {
		return matcher.group(1) + "\n" + matcher.group(5) + "\n" + matcher.group(2) + "\n" + matcher.group(3) + "\n" + matcher.group(4);
	}
}
