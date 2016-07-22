package parsers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResolucaoParserTest {

    private final String resolucaoPath = "src/main/resources//atividadesconsuni32-2013.txt";
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
    public void obtemSuperGrupoDeAtividades() {
        HashMap<String, Map> grupoDeAtividades = resolucaoParser.obtenhaAtividadesResolucao();
        assertEquals(5, grupoDeAtividades.keySet().size());
    }
}
