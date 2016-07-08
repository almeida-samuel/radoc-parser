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
	public void extrairDocumentoRadocValido() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		PdfParser pdfParser = new PdfParser(arquivoRadoc);
		String conteudoArquivo = pdfParser.obtenhaConteudoArquivo();

		assertNotEquals("",conteudoArquivo);
	}

	@Test
	public void obtenhaAtividadesDeEnsino() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeEnsino = radocParser.obtenhaAtividadesDeEnsino();
	}

	@Test
	@Ignore
	public void obtenhaAtividadesDeOrientacao() throws Exception {

	}

	@Test
	public void obtenhaAtividadesEmProjetos() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesEmProjetos = radocParser.obtenhaAtividadesEmProjetos();
	}

	@Test
	@Ignore
	public void obtenhaAtividadesDeExtensao() throws Exception {

	}

	@Test
	@Ignore
	public void obtenhaAtividadesDeQualificacao() throws Exception {

	}

	@Test

	@Ignore
	public void obtenhaAtividadesAcademicasEspeciais() throws Exception {

	}

	@Test
	@Ignore
	public void obtenhaAtividadesAdministrativas() throws Exception {

	}

	@Test
	@Ignore
	public void obtenhaProdutos() throws Exception {

	}

	@Test
	@Ignore
	public void obtenhaAfastamentos() throws Exception {

	}
}