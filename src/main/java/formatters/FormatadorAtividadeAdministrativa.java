package formatters;

public class FormatadorAtividadeAdministrativa extends FormatadorPadrao {

    public FormatadorAtividadeAdministrativa(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividade() {
        return "atividadeAdministrativa";
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "administrativas";
    }
}
