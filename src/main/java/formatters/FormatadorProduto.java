package formatters;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class FormatadorProduto extends FormatadorPadrao {

    public FormatadorProduto(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "producaoIntelectual";
    }

    @Override
    public String obtenhaCampo(Matcher matcher, String campo) {
        HashMap<String, Map> anexoIIResolucao = getResolucaoParser().obtenhaAtividadesResolucao();
        String key = matcher.group(1).toLowerCase().trim();

        String codGrupoPontuacao = "";
        HashMap<String, Map> mapaAtividade = (HashMap<String, Map>) anexoIIResolucao.get(obtenhaTipoAtividadeResolucao());

        for(String k : mapaAtividade.keySet()) {
            if(k.equals(key) || k.contains(key)) {
                codGrupoPontuacao = String.valueOf(mapaAtividade.get(k).get(campo));
                break;
            }
        }

        return codGrupoPontuacao;
    }
}
