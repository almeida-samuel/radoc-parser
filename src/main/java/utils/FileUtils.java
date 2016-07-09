package utils;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    private static final String ENCONDING = "UTF-8";

    /**
     * Recupera o conteúdo do arquivo e remove as linhas não utilizadas.
     * @return O documento parseado.
     */
    public static String obtenhaConteudoArquivo(String conteudoArquivo, File arquivoFonte){
        if("".equals(conteudoArquivo)){
            conteudoArquivo = extraiaTexto(arquivoFonte);
            conteudoArquivo = removaLinhasCabecalhoERodape(conteudoArquivo);
        }

        return conteudoArquivo;
    }

    /**
     * Recupera as informações textuais de um Radoc utilizndo a ferramenta PDFBox.
     * @return O conteudo textual do Radoc.
     */
    private static String extraiaTexto(File arquivoFonte) {
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


    /**
     * Remove as linhas de cabeçalho e rodapé de um Radoc em sua representação textual.
     */
    private static String removaLinhasCabecalhoERodape(String conteudoArquivo) {
        conteudoArquivo = conteudoArquivo.replaceAll("Data: \\d+/\\d+/\\d+ \\d+:\\d+:\\d+\\s+[\\w\\s]+Página\\s+\\d+\\s+/\\s+\\d+","");
        conteudoArquivo = conteudoArquivo.replaceAll("EXTRATO DAS ATIVIDADES\\s+-\\s+ANO BASE:\\s+\\d+","");
        conteudoArquivo = conteudoArquivo.replaceAll("UNIVERSIDADE FEDERAL DE GOIÁS\\sSISTEMA DE CADASTRO DE ATIVIDADES DOCENTES","");
        conteudoArquivo = conteudoArquivo.replaceAll("Curso Disciplina CHA Ano Sem Turma Sub Nº alunos Nº sub CHT CHP CHAC Conjug","");
        conteudoArquivo = conteudoArquivo.replaceAll("RGCG - Regime de Graduação Semestral","");
        conteudoArquivo = conteudoArquivo.replaceAll("LEGENDA: CHA - Carga horária da atividade \\| Sem - Semestre \\| Sub - Subturma \\| CHT, CHP e CHAC - Carga horária teórica, prática e acessória \\| Conjug - Disciplina conjugada","");
        return conteudoArquivo;
    }
}

