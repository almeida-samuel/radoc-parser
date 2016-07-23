package services;

import formatters.*;
import utils.FileUtils;
import utils.MatcherUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class Atividades {
    private final String REGEX_ATIVIDADES_DE_ENSINO = "Atividades de ensino([\\p{L}\\s-\\d]+)Atividades de orientação";
    private final String REGEX_ATIVIDADES_DE_ORIENTACAO = "Atividades de orientação([\\p{L}\\s-\\d\\W]+)Atividades em projetos";
    private final String REGEX_ATIVIDADES_EM_PROJETOS = "Atividades em projetos([\\p{L}\\s-\\d\\W]+)Atividades de extensão";
    private final String REGEX_ATIVIDADES_DE_EXTENSAO = "Atividades de extensão([\\p{L}\\s-\\d\\W]+)Atividades de qualificação";
    private final String REGEX_ATIVIDADES_DE_QUALIFICACAO = "Atividades de qualificação[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades acadêmicas especiais";
    private final String REGEX_ATIVIDADES_ACADEMICAS_ESPECIAIS = "Atividades acadêmicas especiais[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades administrativas";
    private final String REGEX_ATIVIDADES_ADMINISTRATIVAS = "Atividades administrativas[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Produtos";
    private final String REGEX_PRODUTOS = "Produtos[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+";
    private final String REGEX_AFASTAMENTOS = "Afastamento[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Atividades de ensino";

    private String conteudoArquivo;
    private File arquivoFonte;
    private String resolucaoPath;

    public Atividades(String conteudoArquivo, File arquivoFonte, String resolucaoPath) {
        this.conteudoArquivo = conteudoArquivo;
        this.arquivoFonte = arquivoFonte;
        this.resolucaoPath = resolucaoPath;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeEnsino() {
        ArrayList<String> atividadesDeEnsino;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_ENSINO, conteudoDoArquivo);
        String regexAtividadesIndividuais = "(.+?)()(\\d+).+?(\\d{4}).+?()(SIM|NÃO)";
        String regexAtividadeUnica = "(.+?)()(\\d{2,3}).+(\\d{4}).+()(SIM|NÃO)";
        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de ensino", "");
        substituicoes.put("Atividades de orientação", "");
        substituicoes.put("\n+", "");
        substituicoes.put("\r+", "");

        FormatadorAtividadeDeEnsino formatadorAtividadeDeEnsino = new FormatadorAtividadeDeEnsino(this.resolucaoPath);
        atividadesDeEnsino = formatadorAtividadeDeEnsino.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesDeEnsino;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeOrientacao() {
        ArrayList<String> atividadesDeOrientacao;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_ORIENTACAO, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de orientação", "");
        substituicoes.put("Atividades em projetos", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Título do trabalho:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+\\d+\\/\\d+\\/\\d+";
        String regexAtividadeUnica = "Título do trabalho:([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Tabela:([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Orientando:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data início:\\s+(\\d+\\/\\d+\\/\\d+)[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+(\\d+\\/\\d+\\/\\d+)";

        FormatadorAtividadeDeOrientacao formatadorAtividadesDeOrientacao = new FormatadorAtividadeDeOrientacao(this.resolucaoPath);
        atividadesDeOrientacao = formatadorAtividadesDeOrientacao.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesDeOrientacao;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesEmProjetos() {
        ArrayList<String> atividadesDeProjeto;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_EM_PROJETOS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades em projetos", "");
        substituicoes.put("Atividades de extensão", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Título do Projeto:.*?Data Término:\\s*[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}";
        String regexAtividadeUnica = "Título do Projeto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)\\s+Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Unidade Responsável:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data Início:\\s+(\\d+\\/\\d+\\/\\d+)\\s+Data Término:\\s+(\\d+\\/\\d+\\/\\d+)";


        FormatadorAtividadeDeProjeto formatadorAtividadesDeProjeto = new FormatadorAtividadeDeProjeto(this.resolucaoPath);
        atividadesDeProjeto = formatadorAtividadesDeProjeto.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesDeProjeto;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeExtensao() {
        ArrayList<String> atividadesDeExtensao;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_EXTENSAO, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de extensão", "");
        substituicoes.put("Atividades de qualificação", "Tabela");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Tabela:.+?(?=Tabela)";
        String regexAtividadeUnica = "Tabela:.+?CHA:.+?(\\d+).+?Data início:.+?(\\d+\\/\\d+\\/\\d+).+?Data término:.+?(\\d+\\/\\d+\\/\\d+).+?Descrição da atividade:.+?(.+).+?Descrição da clientela:.+?(.+)";

        FormatadorAtividadeDeExtensao formatadorAtividadesDeExtensao = new FormatadorAtividadeDeExtensao(this.resolucaoPath);
        atividadesDeExtensao = formatadorAtividadesDeExtensao.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);


        return atividadesDeExtensao;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesDeQualificacao() {
        ArrayList<String> atividadesDeQualificacao;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_DE_QUALIFICACAO, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades de qualificação", "");
        substituicoes.put("Atividades acadêmicas especiais", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Tabela:\\s+[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data de término:\\s+\\d{2}\\/\\d{2}\\/\\d{4}";
        String regexAtividadeUnica = "Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Descrição:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)CHA:\\s+(\\d+)\\s+Data de início:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Data de término:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})";

        FormatadorAtividadeDeQualificacao formatadorAtividadesDeQualificacao = new FormatadorAtividadeDeQualificacao(this.resolucaoPath);
        atividadesDeQualificacao = formatadorAtividadesDeQualificacao.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesDeQualificacao;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesAcademicasEspeciais() {
        ArrayList<String> atividadesAcademicasEspeciais;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_ACADEMICAS_ESPECIAIS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades acadêmicas especiais", "");
        substituicoes.put("Atividades administrativas", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Tabela:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?(?=\\bTabela\\b|$)";
        String regexAtividadeUnica = "Tabela:(.+?)CHA:.+?(\\d+).+?Data início:.+?(\\d{2}\\/\\d{2}\\/\\d{4}).+?Data término:.+?(\\d{2}\\/\\d{2}\\/\\d{4}).+?Descrição Complementar:.+?(.+?)Descrição da Clientela:.+";

        FormatadorAtividadeAcademicaEspecial formatadorAtividadesAcademicasEspeciais = new FormatadorAtividadeAcademicaEspecial(this.resolucaoPath);
        atividadesAcademicasEspeciais = formatadorAtividadesAcademicasEspeciais.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadesAcademicasEspeciais;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAtividadesAdministrativas() {
        ArrayList<String> atividadeAdministrativa;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_ATIVIDADES_ADMINISTRATIVAS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Atividades administrativas", "");
        substituicoes.put("Produtos", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "\\s+Tabela:\\s+[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Data término:\\s+\\d{2}\\/\\d{2}\\/\\d{4}";
        String regexAtividadeUnica = "\\s+Tabela:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Descrição:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Órgão emissor[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?CHA:\\s+(\\d+)\\s+Data início:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})\\s+Data término:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})";

        FormatadorAtividadeAdministrativa formatadorAtividadesAdministrativa = new FormatadorAtividadeAdministrativa(this.resolucaoPath);
        atividadeAdministrativa = formatadorAtividadesAdministrativa.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return atividadeAdministrativa;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaProdutos() {
        ArrayList<String> produtos;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_PRODUTOS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Produtos", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Descrição do produto:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?Editora:[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?(?=\\bDescrição\\b|$)";
        String regexAtividadeUnica = "Descrição do produto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Título do produto:\\s+([\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?)Autoria[\\s\\d\\p{L}\\/\\-\\.\\_\\:\\,\\>\\=\\<\\(\\'\\\"\\@\\!\\)]+?()Data:\\s+(\\d{2}\\/\\d{2}\\/\\d{4})()";

        FormatadorProduto formatadorProduto = new FormatadorProduto(this.resolucaoPath);
        produtos = formatadorProduto.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return produtos;
    }

    /**
     * {@inheritDoc}
     */
    public ArrayList<String> obtenhaAfastamentos() {
        ArrayList<String> afastamentos;
        String conteudoDoArquivo = FileUtils.obtenhaConteudoArquivo(conteudoArquivo, arquivoFonte);
        Matcher matcher = MatcherUtils.obtenhaMatcher(REGEX_AFASTAMENTOS, conteudoDoArquivo);

        HashMap<String, String> substituicoes = new HashMap<String, String>();
        substituicoes.put("Afastamento", "");
        substituicoes.put("Atividades de ensino", "");
        substituicoes.put("[\\n\\r\\t]+", " ");
        substituicoes.put("[\\n\\r\\t]+", " ");

        String regexAtividadesIndividuais = "Tabela:.+?Data de término:.+?\\d{2}\\/\\d{2}\\/\\d{4}";
        String regexAtividadeUnica = ".+?Processo:.+?Descrição.+?(.+?)Motivo:(.+)?CHA:(.+?)Data de início:.+?(\\d{2}\\/\\d{2}\\/\\d{4}).+?Data de término:.?+(\\d{2}\\/\\d{2}\\/\\d{4})";

        FormatadorAfastamento formatadorAfastamento = new FormatadorAfastamento(this.resolucaoPath);
        afastamentos = formatadorAfastamento.obtenhaRegistros(matcher, regexAtividadesIndividuais, regexAtividadeUnica, substituicoes);

        return afastamentos;
    }




}
