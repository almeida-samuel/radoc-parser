package parsers;
import interfaces.RadocParser;

import java.io.*;
import java.util.*;

import services.Atividades;
import utils.FileUtils;

public class PdfParser implements RadocParser {

	private static final String RESOLUCAO_PATH = "src/main/resources/atividadesconsuni32-2013.txt";

	private File arquivoFonte;
	private String conteudoArquivo;

	public PdfParser(File arquivoFonte) {
		this.arquivoFonte = arquivoFonte;
		this.conteudoArquivo = "";
		this.atividades = new Atividades(conteudoArquivo, arquivoFonte, RESOLUCAO_PATH);
	}

	private Atividades atividades;

	public ArrayList<String> obtenhaAtividadesDeEnsino() {
		return atividades.obtenhaAtividadesDeEnsino();
	}
	public ArrayList<String> obtenhaAtividadesDeOrientacao() {
		return atividades.obtenhaAtividadesDeOrientacao();
	}
	public ArrayList<String> obtenhaAtividadesEmProjetos() {
		return atividades.obtenhaAtividadesEmProjetos();
	}
	public ArrayList<String> obtenhaAtividadesDeExtensao() {
		return atividades.obtenhaAtividadesDeExtensao();
	}
	public ArrayList<String> obtenhaAtividadesDeQualificacao() {
		return atividades.obtenhaAtividadesDeQualificacao();
	}
	public ArrayList<String> obtenhaAtividadesAcademicasEspeciais() {return atividades.obtenhaAtividadesAcademicasEspeciais();}
	public ArrayList<String> obtenhaAtividadesAdministrativas() {
		return atividades.obtenhaAtividadesAdministrativas();
	}
	public ArrayList<String> obtenhaProdutos() {
		return atividades.obtenhaProdutos();
	}
	public ArrayList<String> obtenhaAfastamentos() {
		return atividades.obtenhaAfastamentos();
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
