package parsers;

import interfaces.RadocParser;
import org.junit.*;
import utils.MatcherUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

public class PdfParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void extrairDocumentoRadocValidoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		PdfParser pdfParser = new PdfParser(arquivoRadoc);
		String conteudoArquivo = pdfParser.obtenhaConteudoArquivo();

		assertNotEquals("",conteudoArquivo);
	}

	@Test
	public void obtenhaAtividadesDeEnsinoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeEnsino = radocParser.obtenhaAtividadesDeEnsino();

		assertEquals(7, atividadesDeEnsino.size());
		valideLinhas(atividadesDeEnsino);
	}

	@Test
	public void obtenhaAtividadesDeOrientacaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeOrientacao = radocParser.obtenhaAtividadesDeOrientacao();

		assertEquals(3, atividadesDeOrientacao.size());
		valideLinhas(atividadesDeOrientacao);
	}

	@Test
	public void obtenhaAtividadesEmProjetosRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesEmProjetos = radocParser.obtenhaAtividadesEmProjetos();

		assertEquals(3, atividadesEmProjetos.size());
		valideLinhas(atividadesEmProjetos);
	}

	@Test
	public void obtenhaAtividadesDeExtensaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeExtensao = radocParser.obtenhaAtividadesDeExtensao();

		assertEquals(5, atividadesDeExtensao.size());
		valideLinhas(atividadesDeExtensao);
	}

	@Test
	public void obtenhaAtividadesDeQualificacaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeQualificacao = radocParser.obtenhaAtividadesDeQualificacao();

		assertEquals(3, atividadesDeQualificacao.size());
		valideLinhas(atividadesDeQualificacao);
	}

	@Test
	public void obtenhaAtividadesAcademicasEspeciaisRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAcademicasEspeciais = radocParser.obtenhaAtividadesAcademicasEspeciais();

		assertEquals(8, atividadesAcademicasEspeciais.size());
		valideLinhas(atividadesAcademicasEspeciais);
	}

	@Test
	public void obtenhaAtividadesAdministrativasRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAdministrativas = radocParser.obtenhaAtividadesAdministrativas();

		assertEquals(8, atividadesAdministrativas.size());
		valideLinhas(atividadesAdministrativas);
	}

	@Test
	public void obtenhaProdutosRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> produtos = radocParser.obtenhaProdutos();

		assertEquals(10, produtos.size());
		valideLinhas(produtos);
	}

	@Test
	public void extrairDocumentoRadocValidoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		PdfParser pdfParser = new PdfParser(arquivoRadoc);
		String conteudoArquivo = pdfParser.obtenhaConteudoArquivo();

		assertNotEquals("",conteudoArquivo);
	}

	@Test
	public void obtenhaAtividadesDeEnsinoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeEnsino = radocParser.obtenhaAtividadesDeEnsino();

		assertEquals(5, atividadesDeEnsino.size());
		valideLinhas(atividadesDeEnsino);
	}

	@Test
	public void obtenhaAtividadesDeOrientacaoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeOrientacao = radocParser.obtenhaAtividadesDeOrientacao();

		assertEquals(5, atividadesDeOrientacao.size());
		valideLinhas(atividadesDeOrientacao);
	}

	@Test
	public void obtenhaAtividadesEmProjetosRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesEmProjetos = radocParser.obtenhaAtividadesEmProjetos();

		assertEquals(2, atividadesEmProjetos.size());
		valideLinhas(atividadesEmProjetos);

	}

	@Test
	public void obtenhaAtividadesDeExtensaoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeExtensao = radocParser.obtenhaAtividadesDeExtensao();

		assertEquals(1, atividadesDeExtensao.size());
		valideLinhas(atividadesDeExtensao);

	}

	@Test
	public void obtenhaAtividadesDeQualificacaoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeQualificacao = radocParser.obtenhaAtividadesDeQualificacao();

		assertEquals(2, atividadesDeQualificacao.size());
		valideLinhas(atividadesDeQualificacao);
	}

	@Test
	public void obtenhaAtividadesAcademicasEspeciaisRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAcademicasEspeciais = radocParser.obtenhaAtividadesAcademicasEspeciais();

		assertEquals(6, atividadesAcademicasEspeciais.size());
		valideLinhas(atividadesAcademicasEspeciais);
	}

	@Test
	public void obtenhaAtividadesAdministrativasRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAdministrativas = radocParser.obtenhaAtividadesAdministrativas();

		assertEquals(6, atividadesAdministrativas.size());
		valideLinhas(atividadesAdministrativas);
	}

	@Test
	public void obtenhaProdutosRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> produtos = radocParser.obtenhaProdutos();

		assertEquals(13, produtos.size());
		valideLinhas(produtos);
	}

	@Test
	public void obtenhaAfastamentosRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> afastamentos = radocParser.obtenhaAfastamentos();

		assertEquals(1, afastamentos.size());
		valideLinhas(afastamentos);
	}

	private void valideLinhas(List<String> linhas) {
		String regexLinhaValida = "\\d{12}, \\d{4}, \\d{1,2},\\s.+,\\s*\\d+,\\s+\\d{4,8},\\s+[\\d{8}|\\d{4}]";

		for(String linha: linhas) {
			Matcher matcher = MatcherUtils.obtenhaMatcher(regexLinhaValida, linha);
			assertEquals(matcher.find(), true);
		}
	}
}