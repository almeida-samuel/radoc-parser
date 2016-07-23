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
		String primeiraLinha = "000000000000, 0064, 1, DISCIPLINA UNIDADE - INSTITUTO DEINFORMATICAINTEGRAÇÃO DE APLICAÇÕES  , 64, 2015, 2015";
		assertEquals(primeiraLinha, atividadesDeEnsino.get(0));
	}

	@Test
	public void obtenhaAtividadesDeOrientacaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeOrientacao = radocParser.obtenhaAtividadesDeOrientacao();

		assertEquals(3, atividadesDeOrientacao.size());
		valideLinhas(atividadesDeOrientacao);
		String primeiraLinha = "005001013000, 0003, 1,  IMPLANTAÇÃO DE PROCESSOS DO COBIT EM PEQUENA OU MÉDIA EMPRESA   Aluno orientado em projeto de final de curso , 1, 03082015, 31122015";
		assertEquals(primeiraLinha, atividadesDeOrientacao.get(0) );
	}

	@Test
	public void obtenhaAtividadesEmProjetosRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesEmProjetos = radocParser.obtenhaAtividadesEmProjetos();

		assertEquals(3, atividadesEmProjetos.size());
		valideLinhas(atividadesEmProjetos);
		String primeiraLinha = "003002005000, 0003, 1, Simpósio Brasileiro de Sistemas de Informação Participante de projeto de extensão/cultura cadastrado na PROEC , 100, 30062014, 15072015";
		assertEquals(primeiraLinha, atividadesEmProjetos.get(0));
	}

	@Test
	public void obtenhaAtividadesDeExtensaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeExtensao = radocParser.obtenhaAtividadesDeExtensao();

		assertEquals(5, atividadesDeExtensao.size());
		valideLinhas(atividadesDeExtensao);
		String primeiraLinha = "003002008003, 0003, 1,  Palestrante, conferencista ou participante em mesa redonda em evento científico, cultural ou artístico - Evento regional ou local  Palestra ministrada: Uma certificação para a qualidade de produto de software brasileiro, 2, 26112015, 26112015";
		assertEquals(primeiraLinha, atividadesDeExtensao.get(0));
	}

	@Test
	public void obtenhaAtividadesDeQualificacaoRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesDeQualificacao = radocParser.obtenhaAtividadesDeQualificacao();

		assertEquals(3, atividadesDeQualificacao.size());
		valideLinhas(atividadesDeQualificacao);

		String primeiraLinha = "005003006000, 0001, 1, Participação em Congressos, Seminários, Encontros, Jornadas etc.  Jornada Goiana em Engenharia de Software - Goiânia-GO , 4, 26112015, 26112015";
		assertEquals(primeiraLinha, atividadesDeQualificacao.get(0));
	}

	@Test
	public void obtenhaAtividadesAcademicasEspeciaisRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAcademicasEspeciais = radocParser.obtenhaAtividadesAcademicasEspeciais();

		assertEquals(8, atividadesAcademicasEspeciais.size());
		valideLinhas(atividadesAcademicasEspeciais);

		String primeiraLinha = "005002001001, 0004, 1,  Membro de banca de concurso para docente efetivo na instituição  Presidente da Banca , 20, 01092015, 31122015";
		assertEquals(primeiraLinha, atividadesAcademicasEspeciais.get(0));
	}

	@Test
	public void obtenhaAtividadesAdministrativasRadoc2015() throws Exception {
		File arquivoRadoc = new File("src/test/resources/radoc2015.pdf");
		RadocParser radocParser = new PdfParser(arquivoRadoc);
		ArrayList<String> atividadesAdministrativas = radocParser.obtenhaAtividadesAdministrativas();

		assertEquals(8, atividadesAdministrativas.size());
		valideLinhas(atividadesAdministrativas);

		String primeiraLinha = "004002005000, 0010, 1, Atividades acadêmicas e administrativas designadas por portaria do Reitor, Pró-Reitor ou Diretor de Unidade Acadêmica com carga horária >=150 horas  Membro do Núcleo Docente Estruturante (NDE) do curso de Engenharia de Software , 150, 01012015, 31122015";
		assertEquals(primeiraLinha, atividadesAdministrativas.get(0));
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