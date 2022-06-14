/**
 * TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
 * Professor: GLEIPH GHIOTTO LIMA DE MENEZES
 * Autores: Leonardo Silva da Cunha: 201676019
 *          Juarez de Paula Campos Júnior: 201676022
 *          Luiz Guilherme Almas Araujo: 201676050
 */

package componentes;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Classe com as operacões referentes a validação e leitura de tags e 
 * expressões regulares.
 */
public class ReconheceTag {

	private String expressao;
	private char[] valores;
	private int indiceExpressao;
	private String tagInicial;
	private static Stack<String> tags = new Stack<String>();

	/**
	 * Construtor da classe que recebe a expressão como parâmetro.
	 * @param expressao - Expressao que sera utilizada nos metodos.
	 */
	public ReconheceTag(String expressao) {
		this.expressao = expressao;
	}
	
        /**
         * Construtor vazio da classe.
         */
	public ReconheceTag() {
	}

	/**
	 * Método que transforma uma string em um array de char.
	 */
	private void separaString() {
		this.valores = expressao.toCharArray();
	}

	/**
	 * Método que verifica se a tag inserida antes da expressão regular é válida ou
	 * não. O método percorre o array de caracteres até encontrar o caractere ":".
	 * Caso o array comece com espaço ou direto com ":", a tag é inválida. Caso o
	 * array não contenha ":", a tag é invalida. Caso o array não possua um espaço
	 * após ":", a tag é invalida. Se todos os casos acima não aconteceram a tag é
	 * válida (depois de validar, é claro). 
	 * @return true se a tag eh valida, false se nao.
	 */
	private boolean validaTag() {
		int i = 0;
		int j = i + 1;
		if (this.valores[0] != ':' && this.valores[0] != ' ') {
			while (i < this.valores.length) {
				if (this.valores[i] == ':')
					if (j != this.valores.length && this.valores[j] == ' ') {
						this.tagInicial += String.valueOf(this.valores[i]);
						break;
					} else {
						System.out.println("[ERRO] EXPRESSAO SEM ESPACO ':'");
						return false;
					}
				this.tagInicial += String.valueOf(this.valores[i]);
				i++;
				j++;
			}
			if (!this.tagInicial.contains(":")) {
				System.out.println("[ERRO] TAG NAO INFORMADA.");
				return false;
			}
		} else {
			System.out.println("[ERRO] NOME DE TAG INVALIDO.");
			return false;
		}
		this.tagInicial = this.tagInicial.replaceFirst("null", "");
		indiceExpressao = i;
		return true;

	}

	/**
	 * Verifica se já existe uma tag com o nome inserido.
	 * @param tag Tag inserida.
	 * @return true se nao existe a tag, false se existe a tag.
	 */
	public boolean verificaTag(String tag) {
		if (ReconheceTag.tags.contains(tag)) {
			System.out.println("[WARNING] \"" + tag + "\" JA EXISTE - NAO SERA ADICIONADA A MEMORIA.");
			return false;
		} else {
			ReconheceTag.tags.add(this.tagInicial);
			return true;
		}
	}

	/**
	 * Metodo que verifica se a expressao regular em notacao polonesa reversa
	 * passada no construtor da classe eh valida. O metodo separa a String em
	 * caracteres, percorre cada caracter ate achar um operador (. + ou *). Quando
	 * eh encontrado o operador utiliza uma pilha auxiliar para efetuar a
	 * concatenacao dos elementos com os operadores, apos a concatenacao os
	 * elementos concatenados sao empilhados novamente na pilha principal.
	 * @return true se a expressão foi válida ou false se a expressão é inválida.
	 */
	private boolean validaExpressaoRegular() {
		Stack<String> pilha = new Stack<String>();
		Stack<String> pilhaAux = new Stack<String>();
		String escape;
		String comparacao;
		String fechoKleene;
		String concatenacao;

		if (indiceExpressao + 2 >= this.valores.length) {
			System.out.println("[ERRO] EXPRESSAO REGULAR NAO INFORMADA.");
			return false;
		}
		
		int i = indiceExpressao + 2;
		

		try {
			do {
				if (this.valores[i] == '+') {
					pilhaAux.push(pilha.pop());
					pilhaAux.push(pilha.pop());
					comparacao = pilhaAux.pop() + this.valores[i] + pilhaAux.pop();
					pilha.push(comparacao);
					i++;
				} else if (this.valores[i] == '*') {
					pilhaAux.push(pilha.pop());
					fechoKleene = pilhaAux.pop() + this.valores[i];
					pilha.push(fechoKleene);
					i++;
				} else if (this.valores[i] == '.') {
					pilhaAux.push(pilha.pop());
					pilhaAux.push(pilha.pop());
					concatenacao = "" + pilhaAux.pop() + pilhaAux.pop();
					pilha.push(concatenacao);
					i++;
				} else {
					if(this.valores[i] == '\\') {
						escape = String.valueOf(this.valores[i]) + String.valueOf(this.valores[i+1]);
						pilha.push(escape);
						i = i+2;
					}
					else {
						pilha.push(String.valueOf(this.valores[i]));
						i++;
					}
				}
			} while (i < this.valores.length);

			if (pilha.size() == 1) {
				return true;
			} else {
				System.out.println("[ERRO] EXPRESSAO SEM OPERADORES SUFICIENTES.");
				return false;
			}

		} catch (EmptyStackException e) {
			System.out.println("[ERRO] OPERACOES SEM SIMBOLOS SUFICIENTES.");
			return false;
		}
	}

	/**
	 * Se a tag for válida e não existir outra tag com o mesmo nome e a expressão
	 * regular for válida, então a expressão completa é válida. 
	 * @return true se expressao é valida, false se nao.
	 */
	public boolean validaExpressao() {
		separaString();
		if (validaTag() && validaExpressaoRegular() && verificaTag(this.tagInicial)) {
			System.out.println("[INFO] '" + this.expressao + "' ADICIONADA A MEMORIA.");
			return true;
		}
		else
 			return false;
	}
	
}
