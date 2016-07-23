package formatters;

public class FormatadorAtividadeDeProjeto extends FormatadorPadrao {

    public FormatadorAtividadeDeProjeto(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "pesquisaExtensao";
    }
}
