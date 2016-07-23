package formatters;

public class FormatadorAtividadeAdministrativa extends FormatadorPadrao {

    public FormatadorAtividadeAdministrativa(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "administrativas";
    }
}
