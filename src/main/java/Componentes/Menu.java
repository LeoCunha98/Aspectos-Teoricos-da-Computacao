/**
 * TRABALHO PRÁTICO - DCC146 ASPECTOS TEÓRICOS DA COMPUTAÇÃO 2022/1
 * Professor: GLEIPH GHIOTTO LIMA DE MENEZES
 * Autores: Leonardo Silva da Cunha: 201676019
 *          Juarez de Paula Campos Júnior: 201676022
 *          Luiz Guilherme Almas Araujo: 201676050
 */


package Componentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Menu {
    
    //private String definicao;
    private static Scanner leitura = new Scanner(System.in);
    private ArrayList<String> tagsMemoria = new ArrayList<String>();
    private String cmd = "";
    private String caminho = "";
    private String entradaInformada = "";
    private ArrayList<String> divisoesMemoria = new ArrayList<String>();
    ClassificaString cs = new ClassificaString();
    
    /**
     * Função menu, que dará todo o inicio do programa (logica)
     * onde o comando :d realiza a divisão em tags da string do arquivo informado ex: ":d input.txt"
     * onde o comando :c carrega um arquivo com definições de tags
     * onde o comando :o especifica o caminho do arquivo de saída para a divisão em tags ex: ":o output.txt" 
     * onde o comando :p realiza a divisão em tags da entrada informada
     * onde o comando :a Lista as definições formais dos autômatos em memória
     * onde o comando :l Lista as definições de tag válidas 
     * onde o comando :q sair do programa
     * onde o comando :s salva as tags em um arquivo
     */
    public void menu(){

        boolean flag = true;
        while (flag) {
            entrada();

            switch (this.cmd) {
                
            case ":q":
                System.out.println("[INFO] - Encerrando execucao.");
                flag = false;
                break;
            case ":d": 
                // realiza a divisao em tags da string do arquivo informado
                entradaParaDividirArq(this.caminho);
                //System.out.println("[WARNING] - Função não implementada");
                break;
            case ":c":
                definicoesDeTag(this.caminho);
                break;                
            case ":o":
                salvaDivisaoTags(this.caminho);
                //System.out.println("[WARNING] - Função não implementada");
                break;                
            case ":p":
                //realiza a divisão em tags da entrada informada
                entradaParaDividir();
                //System.out.println("[WARNING] - Função não implementada");
                break;
            case ":a":
                //tagsParaAutomato();
                geraAutomato();
                break;
            case ":l":
                for(int a = 0 ; a<this.tagsMemoria.size() ; a++){
                    System.out.println(this.tagsMemoria.get(a));
                }
                break;
            case ":s":
                salvarTags(this.caminho);
                break;
            
            default:
                adicionaTagMemoria(cmd);
                break;
            }
        }
    }
    
    /**
     * Pega a Sring informada pelo usuário, manda para a classe Automato e lá e feita a divisão.
     * Recebe de volta a divisão em TAGs, apresenta para o usuário e também as prepara para gravação em arquivo.
     */
    private void entradaParaDividir(){
        //automato.divideEntrada(this.entradaInformada);
        ArrayList<String> divisoes = cs.divideString(this.entradaInformada);
        String divs = "";
        for (int i=0;i<divisoes.size();i++){
            divs = divs + divisoes.get(i) + " ";
            
        }
        System.out.println(divs);
        divisoesMemoria.add(divs);
    }
    
    /**
     * Recebe Strings para divisão de um arquivo, cujo caminho foi informado pelo usuário.
     * Salva as expressões em um array, envia cada uma para divisão na classe Automato.
     * Recebe de volta as divisões em TAG, mostra para o usuário e as prepara para gravação em arquivo.
     * @param caminho 
     */
    private void entradaParaDividirArq(String caminho){
        Arquivo arq = new Arquivo();
        ArrayList<String> expressoes = arq.getExpressao(caminho);
        
        for(int i=0; i<expressoes.size();i++){
            if (expressoes.get(i) != null){
                ArrayList<String> divisoes = cs.divideString(expressoes.get(i));
                 String divs = "";
                for (int j=0;j<divisoes.size();j++){
                    divs = divs + divisoes.get(j) + " ";

                }
                System.out.println(divs);
                divisoesMemoria.add(divs);
            }
        }
        
        
    }
    
    public void tagsParaAutomato(){
//        for(int i=0; i<tagsMemoria.size();i++){
//            automato.novoEstado(tagsMemoria.get(i));
//        }
    }
    
    public void geraAutomato(){
        GeraAutomato gera = new GeraAutomato();
        for (int i=0; i< tagsMemoria.size();i++){
            cs.adAFN(gera.geraAFN(tagsMemoria.get(i)));
        }
        }
    
    /**
     * Função com objetivo de validar o comando passado ou se não for comando, será
     * considerado como uma tag.
     * @return 
     */
    private boolean entrada(){ // lê e entrada
        String linha = leitura.nextLine();
        String[] comando = linha.split(" ");
        if(linha.charAt(0) == ' '){
            System.out.println("[ERRO] - Comando começou com espaço");
            return false;
        }else if(comando.length > 2){
            System.out.println("[Erro] - Comando inválido");
        }else if(comando[0].charAt(0) == ':'){
            if(!validadeComando(comando))
                return entrada();
            else
                return true;
        }else{
            this.cmd = linha;
        }
        return true;
    }
    
    /**
     * Função que tem o objetivo de reconhecer o comando e atribuir na variavel
     * 'cmd' e se caso, o comando for algum que é necessário de um caminho, o mesmo
     * atribui o caminho na variavel 'caminho'.
     * @param comando
     * @return 
     */
    private boolean validadeComando(String[] comando) {
        if (!comando[0].equals(":d") // verifica a sintaxe do comando
                && comando[0].equals(":c")
                && comando[0].equals(":o")
                && comando[0].equals(":p")//
                && comando[0].equals(":a")
                && comando[0].equals(":a")
                && comando[0].equals(":l")
                && comando[0].equals(":q")
                && comando[0].equals(":s")) {
            System.out.println("[ERRO] COMANDO INVALIDO");
            return false;
        }else{
            if(comando.length == 1) { // verifica os comandos sem parametro
                if(comando[0].equals(":a")) {
                    this.cmd = comando[0];
                    return true;
                }else if(comando[0].equals(":l")){
                    this.cmd = comando[0];
                    return true;
                }else if(comando[0].equals(":q")){
                    this.cmd = comando[0];
                    return true;
                }
                else{ // se uma comando que deveria ter um parametro veio sem o parametro
                    
                    System.out.println("[ERRO] CAMINHO NAO INFORMADO");
                    return false;
                    }
                
            }
            else { //separa o comando do parametro
                if (comando[0].equals(":p")){
                        this.cmd = comando[0];
                        this.entradaInformada = comando[1];
                        return true;
                } else {
                this.cmd = comando[0];
                this.caminho = comando[1];
                return true;
                }
            }
        }
    }
    
    /**
     * Função que tem como objetivo, ler um arquivo passado pelo usuario e verificar
     * se as tags contidas nele, são tags válidas ou não e se caso for, adicionar 
     * essas tags na memoria de execução do programa (caso alguma tag desse arquivo
     * tiver o mesmo nome de alguma tag já salva na memoria de execução do programa,
     * a mesma será desconsiderada).
     * @param caminhoTag 
     */
    private void definicoesDeTag(String caminhoTag) {
        Arquivo arq = new Arquivo();

        ArrayList<String> tagsArq;
        if ((tagsArq = arq.getExpressao(caminhoTag)) != null) {
            ReconheceTag[] er = new ReconheceTag[tagsArq.size()];

            // A ULTIMA VEZ QUE A TAG FOI INSERIDA NO
            // ARQUIVO EH A QUE SERA VALIDA.
            Collections.reverse(tagsArq);

            for (int i = 0; i < tagsArq.size(); i++) {
                er[i] = new ReconheceTag(tagsArq.get(i));
                if (er[i].validaExpressao())
                    this.tagsMemoria.add(tagsArq.get(i));
            }
        }
    }
    
    /**
     * Salva a divisão da entrada em tags em um arquivo.
     * @param caminhoDiv - Caminho do arquivo.
     */
    private void salvaDivisaoTags(String caminhoDiv){
        Arquivo salvaDiv = new Arquivo();
        salvaDiv.gravaDivisao(divisoesMemoria, caminho);
    }
    
    /**
     * Função que tem como objetivo, salvar as tags válidas que estão na memoria
     * de execução do programa em um arquivo na memoria do computador.
     * @param caminhoTag 
     */
    private void salvarTags(String caminhoTag){
        Arquivo arq = new Arquivo();

        if (!this.tagsMemoria.isEmpty()) {
            if (!arq.existe(caminhoTag)) {
                for (int i = 0; i < this.tagsMemoria.size(); i++)
                    arq.setExpressao(this.tagsMemoria.get(i), caminhoTag);
                System.out.println("[INFO] ARQUIVO CRIADO - TAGS ADICIONADAS.");
            } else {
                for (int i = 0; i < this.tagsMemoria.size(); i++)
                    arq.setExpressao(this.tagsMemoria.get(i), caminhoTag);
                System.out.println("[INFO] ARQUIVO SOBRESCRITO COM NOVAS TAGS.");
            }
        } else {
            System.out.println("[WARNING] NAO HA TAGS A SEREM ADICIONADAS.");
        }
    }
    
    /**
     * Função que tem com objetivo, verificar a tag que foi passada pelo usuario
     * é uma tag válida ou não. Se caso for válido, adiciona na memoria de execução 
     * do programa e se caso não, retorna uma mensagem de erro.
     * @param tag 
     */
    private void adicionaTagMemoria(String tag) {
            ReconheceTag re = new ReconheceTag(tag);
            if (re.validaExpressao())
                    this.tagsMemoria.add(tag);

    }
    
}
