package parsers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotEquals;

public class ResolucaoParserTest {

    private final String resolucaoPath = "src/test/resources/Resolucao_CONSUNI_2013_0032.pdf";
    private File resolucaoFile;
    private ResolucaoParser resolucaoParser;

    @Before
    public void setUp() throws Exception {
        this.resolucaoFile = new File(resolucaoPath);
        this.resolucaoParser = new ResolucaoParser(this.resolucaoFile);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void obtemConteudoResolucao() {
        String conteudoResolucao = resolucaoParser.obtenhaConteudoResolucao();
        assertNotEquals("",conteudoResolucao);
    }
}
