package utils;

import interfaces.RadocParser;
import org.junit.*;
import sun.misc.Resource;

import java.io.File;
import java.util.ArrayList;

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
	}

	@Test
	public void obtenhaAtividadesDeOrientacaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeOrientacao = radocParser.obtenhaAtividadesDeOrientacao();

		assertEquals(3, atividadesDeOrientacao.size());
	}

	@Test
	public void obtenhaAtividadesEmProjetosRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesEmProjetos = radocParser.obtenhaAtividadesEmProjetos();

		assertEquals(3, atividadesEmProjetos.size());
	}

	@Test
	public void obtenhaAtividadesDeExtensaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeExtensao = radocParser.obtenhaAtividadesDeExtensao();

		assertEquals(5, atividadesDeExtensao.size());
	}

	@Test
	public void obtenhaAtividadesDeQualificacaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeQualificacao = radocParser.obtenhaAtividadesDeQualificacao();

		assertEquals(3, atividadesDeQualificacao.size());
	}

	@Test
	public void obtenhaAtividadesAcademicasEspeciaisRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAcademicasEspeciais = radocParser.obtenhaAtividadesAcademicasEspeciais();

		assertEquals(8, atividadesAcademicasEspeciais.size());
	}

	@Test
	public void obtenhaAtividadesAdministrativasRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAdministrativas = radocParser.obtenhaAtividadesAdministrativas();

		assertEquals(8, atividadesAdministrativas.size());
	}

	@Test
	public void obtenhaProdutosRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> produtos = radocParser.obtenhaProdutos();

		assertEquals(10, produtos.size());
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
	}

	@Test
	public void obtenhaAtividadesDeOrientacaoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeOrientacao = radocParser.obtenhaAtividadesDeOrientacao();

		assertEquals(5, atividadesDeOrientacao.size());
	}

	@Test
	public void obtenhaAtividadesEmProjetosRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesEmProjetos = radocParser.obtenhaAtividadesEmProjetos();

		assertEquals(2, atividadesEmProjetos.size());
	}

	@Test
	public void obtenhaAtividadesDeExtensaoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeExtensao = radocParser.obtenhaAtividadesDeExtensao();

		assertEquals(1, atividadesDeExtensao.size());
	}

	@Test
	public void obtenhaAtividadesDeQualificacaoRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeQualificacao = radocParser.obtenhaAtividadesDeQualificacao();

		assertEquals(2, atividadesDeQualificacao.size());
	}

	@Test
	public void obtenhaAtividadesAcademicasEspeciaisRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAcademicasEspeciais = radocParser.obtenhaAtividadesAcademicasEspeciais();

		assertEquals(6, atividadesAcademicasEspeciais.size());
	}

	@Test
	public void obtenhaAtividadesAdministrativasRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAdministrativas = radocParser.obtenhaAtividadesAdministrativas();

		assertEquals(6, atividadesAdministrativas.size());
	}

	@Test
	public void obtenhaProdutosRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> produtos = radocParser.obtenhaProdutos();

		assertEquals(13, produtos.size());
	}

	@Test
	public void obtenhaAfastamentosRadoc2014() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2014.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> afastamentos = radocParser.obtenhaAfastamentos();

		assertEquals(1, afastamentos.size());
	}
}