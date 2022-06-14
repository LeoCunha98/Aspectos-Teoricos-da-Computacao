/**
 * TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
 * Professor: GLEIPH GHIOTTO LIMA DE MENEZES
 * Autores: Leonardo Silva da Cunha: 201676019
 *          Juarez de Paula Campos Júnior: 201676022
 *          Luiz Guilherme Almas Araujo: 201676050
 */

package Componentes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class GeraAutomato {
	private static int idEstado = 0;
	
	private static Stack<AFN> pilhaAFN = new Stack<AFN> ();
	private static Stack<Character> operador = new Stack<Character> ();	

	private static Set<Estado> set1 = new HashSet <Estado> ();
	private static Set<Estado> set2 = new HashSet <Estado> ();
	
	// Armazena os símbolos do alfabeto contidos na expressão, vou precisar depois
	private static Set <Character> alfabeto = new HashSet <Character> ();
        static ArrayList<Character> auxAlfabeto = new ArrayList<>();
	
	/**
         * Gera um afn a partir da expressão passada como parâmetro.
         * @param expressao1 - é a expressão contida na TAG informada pelo usuário, via arquivo ou teclado.
         * @return Retorna o AFN gerado.
         */
	public static AFN geraAFN(String expressao1) {
                
                String auxExpressao[] = expressao1.split(":");
                String nome = auxExpressao[0]; //identificador da TAG, futuro nome do AFN
                String expressao = auxExpressao[1];
                expressao.trim();
                
		// prepara a expressao
		expressao = colocaUmaConcatenacao (expressao);
                
		
                for (int i=0; i<expressao.length();i++){
                    if (expressao.charAt(i) != '(' && 
                            expressao.charAt(i) != ')' &&
                            expressao.charAt(i) != '|' &&
                            expressao.charAt(i) != '*' &&
                            expressao.charAt(i) != '.' &&
                            expressao.charAt(i) != '+'){
                        alfabeto.add(expressao.charAt(i));
                        auxAlfabeto.add(expressao.charAt(i));
                    }
                }
                
		
		// Zerando as pilhas.
		pilhaAFN.clear();
		operador.clear();

		for (int i = 0 ; i < expressao.length(); i++) {	

			if (eDoAlfabeto (expressao.charAt(i))) {
				Empilhar(expressao.charAt(i));
				
			} else if (operador.isEmpty()) {
				operador.push(expressao.charAt(i));
				
			} else if (expressao.charAt(i) == '(') {
				operador.push(expressao.charAt(i));
				
			} else if (expressao.charAt(i) == ')') {
				while (operador.get(operador.size()-1) != '(') {
					Operacao();
				}				
				// Desempilha parentesis esquerdo.
				operador.pop();
				
			} else {
				while (!operador.isEmpty() && 
						setPrioridadeOperandos (expressao.charAt(i), operador.get(operador.size() - 1)) ){
					Operacao ();
				}
				operador.push(expressao.charAt(i));
			}		
		}		
		
		// Limpa a pilha, removendo elementos que sobraram
		while (!operador.isEmpty()) {
                    Operacao(); 
                }
		
		// Pegando o AFN todo
		AFN AFNcompleto = pilhaAFN.pop(); //ja testei
		
		// Coloca o estado de aceitação no final do AFN
		AFNcompleto.getAFN().get(AFNcompleto.getAFN().size() - 1).setEestadoAceitacao(true);
		
                // coloca o nome do automato - identificador da TAG
                AFNcompleto.setNome(nome);
		// Retornando o AFN - olhar la
		return AFNcompleto;
	}
	
	/**
         * Lida com a prioridade dos operandos na expressão.
         * @param primeiroOp
         * @param segundoOp
         * @return 
         */
	private static boolean setPrioridadeOperandos (char primeiroOp, Character segundoOp) {//muda pra Character???
		if(primeiroOp == segundoOp) {	return true;	}
		if(primeiroOp == '*') 	{	return false;	}
		if(segundoOp == '*')  	{	return true;	}
		if(primeiroOp == '.') 	{	return false;	}
		if(segundoOp == '.') 	{	return true;	}
		if(primeiroOp == '|') 	{	return false;	} 
                else {	
                    return true;	
                }
	}

	/**
         * Faz a operacção necessária de acordo com o símbolo no topo da pilha.
         */
	private static void Operacao () {
		if (GeraAutomato.operador.size() > 0) {
			char op = operador.pop();

			switch (op) {
				case ('|'):
					OperacaoOu ();
					break;
	
				case ('.'):
					Concatenacao ();
					break;
	
				case ('*'):
					fechoKleene ();
					break;
	
				default :
					System.out.println("Símbolo não acerto!" + op);
					System.exit(1);
					break;			
			}
		}
	} //resolveu
		
	/**
         * Lida com a operação do fecho de kleene.
         */
	private static void fechoKleene() {
		// pega o topo da pilha
		AFN nfa = pilhaAFN.pop();
		
		// Cria os estados para a operação
		Estado inicio = new Estado (idEstado++);
		Estado fim = new Estado (idEstado++);
		
		// Cria transições para os estados inicio e fim
		inicio.addTransicao(fim, 'e');
		inicio.addTransicao(nfa.getAFN().getFirst(), 'e');
		
		nfa.getAFN().getLast().addTransicao(fim, 'e');
		nfa.getAFN().getLast().addTransicao(nfa.getAFN().getFirst(), 'e');
		
		nfa.getAFN().addFirst(inicio);
		nfa.getAFN().addLast(fim);
		
		// coloca o afn no fim da pilha
		pilhaAFN.push(nfa);
	}

	/**
         * Faz a operação de concatenação.
         */
	private static void Concatenacao() {
		// Pega os afns da pilha
		AFN afn2 = pilhaAFN.pop();
		AFN afn1 = pilhaAFN.pop();
		
		// gruda os dois afns, colocando uma transicao do final do afn1 até o inicio do afn2
		afn1.getAFN().getLast().addTransicao(afn2.getAFN().getFirst(), 'e');
		
		// estados de afn2 no final de afn1
		for (Estado s : afn2.getAFN()) {	
                    afn1.getAFN().addLast(s); 
                }

		// coloca o afn de volta na pilha
		pilhaAFN.push (afn1);
	}
	
	/**
         * Faz a operacao OU.
         */
	private static void OperacaoOu() {
		// pega dois afns da pilha
		AFN afn2 = pilhaAFN.pop();
		AFN afn1 = pilhaAFN.pop();
		
		// Cria os estados usados para a operacao
		Estado inicio = new Estado (idEstado++);
		Estado fim = new Estado (idEstado++);
                
                //Coloca uma transicao entre o inicio dos afns 1e2 e uma string vazia
		inicio.addTransicao(afn1.getAFN().getFirst(), 'e');
		inicio.addTransicao(afn2.getAFN().getFirst(), 'e');
                
                //Coloca uma transicao de uma string vazia para o fim de cada afn
		afn1.getAFN().getLast().addTransicao(fim, 'e');
		afn2.getAFN().getLast().addTransicao(fim, 'e');

		
		afn1.getAFN().addFirst(inicio);//coloca o estado inicio como o inicio do afn1
		afn2.getAFN().addLast(fim);//coloca o estado fim como final do afn2
			
		for (Estado s : afn2.getAFN()) { //coloa todos os estados do afn2 no final do afn1, ordenados
			afn1.getAFN().addLast(s);
		}
		// afn de volta na pilha
		pilhaAFN.push(afn1);		
	}
	
	/**
         * Coloca o alfabeto na pilha
         * @param simbolo 
         */
	private static void Empilhar(char simbolo) {
		Estado s0 = new Estado (idEstado++);
		Estado s1 = new Estado (idEstado++);
		
		// cria uma transicao de 0 para 1 com o simbolo passado como parametro
		s0.addTransicao(s1, simbolo);
		
		// cria um afn temporario
		AFN nfa = new AFN ();
		
		// coloca os dois estados nesse afn
		nfa.getAFN().addLast(s0);
		nfa.getAFN().addLast(s1);		
		
		// coloca o afn na pilha
		pilhaAFN.push(nfa);
	}

	/**
         * Coloca um simbolo de concatenação '.' quando existe a concatenação entre dois símbolos
         * @param expressao - é a expressão que vem com a TAG passada pelo usuario.
         * @return a espressão com o simbolo adicionado.
         */
	private static String colocaUmaConcatenacao(String expressao) {
		String nExpressao = new String ("");
                
                expressao = expressao.replaceAll("\\+", "|");
                
		for (int i = 0; i < expressao.length() - 1; i++) {
                        
			if ( eDoAlfabeto(expressao.charAt(i))  && eDoAlfabeto(expressao.charAt(i+1)) ) {
				nExpressao += expressao.charAt(i) + ".";
				
			} else if ( eDoAlfabeto(expressao.charAt(i)) && expressao.charAt(i+1) == '(' ) {
				nExpressao += expressao.charAt(i) + ".";
				
			} else if ( expressao.charAt(i) == ')' && eDoAlfabeto(expressao.charAt(i+1)) ) {
				nExpressao += expressao.charAt(i) + ".";
				
			} else if (expressao.charAt(i) == '*'  && eDoAlfabeto(expressao.charAt(i+1)) ) {
				nExpressao += expressao.charAt(i) + ".";
				
			} else if ( expressao.charAt(i) == '*' && expressao.charAt(i+1) == '(' ) {
				nExpressao += expressao.charAt(i) + ".";
				
			} else if ( expressao.charAt(i) == ')' && expressao.charAt(i+1) == '(') {
				nExpressao += expressao.charAt(i) + ".";			
				
			} else {
				nExpressao += expressao.charAt(i);
			}
		}
		nExpressao += expressao.charAt(expressao.length() - 1);
		return nExpressao;
	}

	/**
         * Método que verifica se o símbolo passado como parametro pertence ao alfabelo do automato.
         * @param simbolo - Simbolo para ser verificado.
         * @return Retorna true se o simbolo pertence ao alfabeto.
         */
	private static boolean eDoAlfabeto(char simbolo) {

            for (int i=0; i<auxAlfabeto.size();i++){
                if (simbolo == auxAlfabeto.get(i)){
                    return true;
                }
            }
            if (simbolo == 'e')	return true;
            return false;
	}

	
	/**
         * Gera um afd, usando o afn.
         * @param afn - um afn passado como parametro. Vai ser usado pra criar um afd.
         * @return Retorna o afd criado.
         */
	public static AFD geraAFD(AFN afn) {
		//cria um novo afd
		AFD afd = new AFD ();

		// as ids dos estados são zeradas
		idEstado = 0;

		//cria uma lista de estados vazios
		LinkedList <Estado> estadosVazios = new LinkedList<Estado> ();
		
		// pra usar hash, são hashs de estados
		set1 = new HashSet <Estado> ();
		set2 = new HashSet <Estado> ();

		// cria primeiro estado de set1
		set1.add(afn.getAFN().getFirst());

		//remove estados com transição lambda
		removeTransicaoLambda ();

		// cria o estado inicial do afd e adiciona o estado na pilha
		Estado inicioAFD = new Estado (set2, idEstado++);
		
		afd.getAFD().addLast(inicioAFD);
		estadosVazios.addLast(inicioAFD);
		
		// equanto ainda tem elementos na lista de estados vazios
		while (!estadosVazios.isEmpty()) {
                    
			// remove o ultimo estado vazio, armazena em e
			Estado e = estadosVazios.removeLast();

			// "enche" os estados vazios, cria as transicoes enquanto tem simbolos mno alfabeto
			for (Character simbolo : alfabeto) {
				set1 = new HashSet<Estado> ();
				set2 = new HashSet<Estado> ();

				organizaEstados (simbolo, e.getEstados(), set1);
				removeTransicaoLambda ();

				boolean found = false;
				Estado e1 = null;

				for (int i = 0 ; i < afd.getAFD().size(); i++) {
					e1 = afd.getAFD().get(i);

					if (e1.getEstados().containsAll(set2)) {
						found = true;
						break;
					}
				}

				// simbolo não esta no afd, cria estado e adiciona
				if (!found) {
					Estado p = new Estado (set2, idEstado++);
					estadosVazios.addLast(p);
					afd.getAFD().addLast(p);
					e.addTransicao(p, simbolo);

				// se já esta no afd
				} else {
					e.addTransicao(e1, simbolo);
				}
			}			
		}
                
                afd.setNome(afn.getNome());
		// retorna o afd
		return afd;
	}

	/**
         * Remove transições vazias
         */
	private static void removeTransicaoLambda() {
		Stack <Estado> pilha = new Stack <Estado> ();
		set2 = set1;

		for (Estado st : set1) { pilha.push(st);	}

		while (!pilha.isEmpty()) {
			Estado st = pilha.pop();

			ArrayList <Estado> estadosLambda = st.getTransicoes ('e');

			for (Estado p : estadosLambda) {
				// Se p não esta entre os estados ele é adicionado
				if (!set2.contains(p)) {
					set2.add(p);
					pilha.push(p);
				}				
			}
		}		
	}

	/**
         * Reorganiza os estados com base no símbolo
         * @param c é o símbolo
         * @param estados hash com estados
         * @param estados1 
         */
	private static void organizaEstados(Character c, Set<Estado> estados,	Set<Estado> estados1) {
		ArrayList <Estado> temp = new ArrayList<Estado> ();

		for (Estado e : estados) {	temp.add(e);	}
		for (Estado e : temp) {			
			ArrayList<Estado> estadost = e.getTransicoes(c);

			for (Estado p : estadost) {	
                            estados1.add(p);	
                        }
		}
	}	
}
