package parsers;
import interfaces.RadocParser;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;

import services.Atividades;
import utils.FileUtils;
import utils.MatcherUtils;
import utils.RegistrosUtils;

public class PdfParser implements RadocParser {
	private final String REGEX_PRODUTOS = "Produtos[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+";
	private final String REGEX_AFASTAMENTOS = "Afastamento[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades de ensino";

	private File arquivoFonte;
	private String conteudoArquivo;

	public PdfParser(File arquivoFonte) {
		this.arquivoFonte = arquivoFonte;
		this.conteudoArquivo = "";
		this.atividades = new Atividades(conteudoArquivo, arquivoFonte);
	}

	private Atividades atividades;

	@Override
	public ArrayList<String> obtenhaAtividadesDeEnsino() {
		return atividades.obtenhaAtividadesDeEnsino();
	}

	@Override
	public ArrayList<String> obtenhaAtividadesDeOrientacao() {
		return atividades.obtenhaAtividadesDeOrientacao();
	}

	@Override
	public ArrayList<String> obtenhaAtividadesEmProjetos() {
		return atividades.obtenhaAtividadesEmProjetos();
	}

	@Override
	public ArrayList<String> obtenhaAtividadesDeExtensao() {
		return atividades.obtenhaAtividadesDeExtensao();
	}

	@Override
	public ArrayList<String> obtenhaAtividadesDeQualificacao() {
		return atividades.obtenhaAtividadesDeQualificacao();
	}

	@Override
	public ArrayList<String> obtenhaAtividadesAcademicasEspeciais() {
		return atividades.obtenhaAtividadesAcademicasEspeciais();
	}

	@Override
	public ArrayList<String> obtenhaAtividadesAdministrativas() {
		return atividades.obtenhaAtividadesAdministrativas();
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaProdutos() {
		ArrayList<String> produtos;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_PRODUTOS, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Produtos", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexProdutoIndividual = "Descrição do produto:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Editora:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?(?=\\bDescrição\\b|$)";
		String regexProdutoUnico = "Descrição do produto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Título do produto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Autoria[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?()Data:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})()";

		produtos = RegistrosUtils.obtenhaRegistros(conteudoDoArquivo, matcher, regexProdutoIndividual, regexProdutoUnico, substituicoes);

		return produtos;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> obtenhaAfastamentos() {
		ArrayList<String> afastamentos;
		String conteudoDoArquivo = obtenhaConteudoArquivo();
		Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_AFASTAMENTOS, conteudoDoArquivo);

		HashMap<String, String> substituicoes = new HashMap<String, String>();
		substituicoes.put("Afastamento", "");
		substituicoes.put("Atividades de ensino", "");
		substituicoes.put("[\\n\\r\\t]+", " ");
		substituicoes.put("[\\n\\r\\t]+", " ");

		String regexAfastamentoIndividual = "Tabela:.+?Data de término:.+?\\d{2}\\/\\d{2}\\/\\d{4}";
		String regexAfastamentoUnico = ".+?Processo:(.+?)Descrição.+?(.+?)Motivo:.+?CHA:(.+?)Data de início:.+?(\\d{2}\\/\\d{2}\\/\\d{4}).+?Data de término:.?+(\\d{2}\\/\\d{2}\\/\\d{4})";

		afastamentos = RegistrosUtils.obtenhaRegistros(conteudoDoArquivo, matcher, regexAfastamentoIndividual, regexAfastamentoUnico, substituicoes);

		return afastamentos;
	}

	/**
	 * Recupera o conteúdo do arquivo e remove as linhas não utilizadas.
	 * @return O documento parseado.
     */
	public String obtenhaConteudoArquivo(){
		return FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
	}

	/**
	 * Extrai as atividades do radoc para o path determinado.
	 * @param path Caminho do arquivo de saída.
     */
	public void extraiAtividades(String path) {
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");

			StringBuffer sb = new StringBuffer();
			sb.append("ATIVIDADES DE ENSINO\n");
			sb.append(String.join("\n", obtenhaAtividadesDeEnsino()));
			sb.append("\n\nATIVIDADES DE ORIENTAÇÃO\n");
			sb.append(String.join("\n", obtenhaAtividadesDeOrientacao()));
			sb.append("\n\nATIVIDADES EM PROJETOS\n");
			sb.append(String.join("\n", obtenhaAtividadesEmProjetos()));
			sb.append("\n\nATIVIDADES DE EXTENSÃO\n");
			sb.append(String.join("\n", obtenhaAtividadesDeExtensao()));
			sb.append("\n\nATIVIDADES DE QUALIFICAÇÃO\n");
			sb.append(String.join("\n", obtenhaAtividadesDeQualificacao()));
			sb.append("\n\nATIVIDADES ACADÊMICAS ESPECIAIS\n");
			sb.append(String.join("\n", obtenhaAtividadesAcademicasEspeciais()));
			sb.append("\n\nATIVIDADES ADMINISTRATIVAS\n");
			sb.append(String.join("\n", obtenhaAtividadesAdministrativas()));
			sb.append("\n\nPRODUTOS\n");
			sb.append(String.join("\n", obtenhaProdutos()));
			sb.append("\n\nAFASTAMENTOS\n");
			sb.append(String.join("\n", obtenhaAfastamentos()));

			writer.write(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
