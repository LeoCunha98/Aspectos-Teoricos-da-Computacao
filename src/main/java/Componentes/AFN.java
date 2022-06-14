/**
 * TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
 * Professor: GLEIPH GHIOTTO LIMA DE MENEZES
 * Autores: Leonardo Silva da Cunha: 201676019
 *          Juarez de Paula Campos Júnior: 201676022
 *          Luiz Guilherme Almas Araujo: 201676050
 */

package Componentes;
import java.util.LinkedList;

public class AFN {
	private LinkedList<Estado> afn;
        private String nome;
        
	/**
         * Contrutor da classe.
         */
	public AFN () {
		this.setAFN(new LinkedList<Estado> ());
		this.getAFN().clear();
	}

	public LinkedList<Estado> getAFN() {
		return afn;
	}

	public void setAFN(LinkedList<Estado> afn) {
		this.afn = afn;
	}
        
        public void setNome(String nome){
            this.nome = nome;
        }
        
        public String getNome(){
            return this.nome;
        }
}
