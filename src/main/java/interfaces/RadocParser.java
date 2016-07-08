package interfaces;

import java.util.ArrayList;

/**
 * <p>Interface que encapsula os métodos padrão esperados para o
 * parse de um Radoc.</p>
 * <p>O retorno de cada um dos métodos definidos nesta interface
 * obedece às regras estabelecidas na disposição do Trabalho 3
 * da disciplina de Manutenção de Software.</p>
 * <p>Cada item dentro do ArrayList de retorno de cada um dos
 * métodos desta interface, representa uma linha do arquivo .txt
 * de saída, que é produto final desta implementação.</p>
 */
public interface RadocParser {

	/**
	 * Recupera as atividades de ensino de um Radoc. Cada item do
	 * ArrayList retornado representa uma linha do arquivo .txt de saída.
	 * @return Lista representando as atividades de ensino do arquivo
	 * de saída.
     */
	ArrayList<String> obtenhaAtividadesDeEnsino();

	/**
	 * Recupera as atividades de orientação de um Radoc. Cada item do
	 * ArrayList retornado representa uma linha do arquivo .txt de saída.
	 * @return Lista representando as atividades de orientação do arquivo
	 * de saída.
	 */
	ArrayList<String> obtenhaAtividadesDeOrientacao();

	/**
	 * Recupera as atividades em projetos de um Radoc. Cada item do
	 * ArrayList retornado representa uma linha do arquivo .txt de saída.
	 * @return Lista representando as atividades em projetos do arquivo
	 * de saída.
	 */
	ArrayList<String> obtenhaAtividadesEmProjetos();

	/**
	 * Recupera as atividades de extensão de um Radoc. Cada item do
	 * ArrayList retornado representa uma linha do arquivo .txt de saída.
	 * @return Lista representando as atividades de extensão do arquivo
	 * de saída.
	 */
	ArrayList<String> obtenhaAtividadesDeExtensao();

	/**
	 * Recupera as atividades de qualificação de um Radoc. Cada item do
	 * ArrayList retornado representa uma linha do arquivo .txt de saída.
	 * @return Lista representando as atividades de qualificação do arquivo
	 * de saída.
	 */
	ArrayList<String> obtenhaAtividadesDeQualificacao();

	/**
	 * Recupera as atividades acadêmicas especiais de um Radoc. Cada item do
	 * ArrayList retornado representa uma linha do arquivo .txt de saída.
	 * @return Lista representando as atividades acadêmicas especiais do arquivo
	 * de saída.
	 */
	ArrayList<String> obtenhaAtividadesAcademicasEspeciais();

	/**
	 * Recupera as atividades administrativas de um Radoc. Cada item do
	 * ArrayList retornado representa uma linha do arquivo .txt de saída.
	 * @return Lista representando as atividades administrativas do arquivo
	 * de saída.
	 */
	ArrayList<String> obtenhaAtividadesAdministrativas();

	/**
	 * Recupera os produtos de um Radoc. Cada item do ArrayList retornado representa
	 * uma linha do arquivo .txt de saída.
	 * @return Lista representando os produtos do arquivo de saída.
	 */
	ArrayList<String> obtenhaProdutos();

	/**
	 * Recupera os afastamentos de um Radoc. Cada item do ArrayList retornado representa
	 * uma linha do arquivo .txt de saída.
	 * @return Lista representando os afastamentos do arquivo de saída.
	 */
	ArrayList<String> obtenhaAfastamentos();
}
