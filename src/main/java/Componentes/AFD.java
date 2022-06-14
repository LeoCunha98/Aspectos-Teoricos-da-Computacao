/**
 * TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
 * Professor: GLEIPH GHIOTTO LIMA DE MENEZES
 * Autores: Leonardo Silva da Cunha: 201676019
 *          Juarez de Paula Campos Júnior: 201676022
 *          Luiz Guilherme Almas Araujo: 201676050
 */

package Componentes;
import java.util.LinkedList;

public class AFD {
	private LinkedList<Estado> afd;
        private String nome;
	
        /**
         * Construtor da classe.
         */
	public AFD () {
		this.setAFD(new LinkedList<Estado> ());
		this.getAFD().clear();
	}

	public LinkedList<Estado> getAFD() {
		return afd;
	}

	public void setAFD(LinkedList<Estado> afd) {
		this.afd = afd;
	}
        
        public void setNome(String nome){
            this.nome = nome;
        }
        
        public String getNome(){
            return this.nome;
        }
}
//luizalmas
