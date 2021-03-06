/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locadora;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author edlon
 */
public class SISLOCA {

    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Midia> midias = new ArrayList<>();
    private static ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private static Funcionario usuarioLogado;
    private static ArrayList<Locacao> locacoes = new ArrayList<>();

    public static void main(String[] args) {
        Administrador root = new Administrador("root", null, "00000000", "root");

        funcionarios.add(root);
        logar();

    }

    public static void logar() {
        do {
            Scanner in = new Scanner(System.in);
            String usuario, senha;
            boolean admin;
            boolean logado = false;
            int idUsuario = -1;
            System.out.println("----------|SISLOCA - Sistema de Locadoras|----------");
            System.out.println("\n\n");
            System.out.println("Você precisa se logar no sistema!");
            System.out.println("\n\n\n\n");

            while (!logado) {
                System.out.print("\t\t\tUsuário:");
                usuario = in.nextLine();
                System.out.print("\n\t\t\tSenha:");
                senha = in.nextLine();
                int i = 0;
                for (Funcionario funcionario : funcionarios) {
                    if (((String) funcionario.getNome()).equals(usuario) && ((String) funcionario.getSenha()).equals(senha)) {
                        logado = true;
                        idUsuario = i;
                        admin = funcionario.isAdmin();
                    } else {
                        logado = false;
                        System.out.println(funcionario.getNome() + funcionario.getSenha());
                        System.out.println("\nDados incorretos! Tente logar novamente.\n\n");
                    }
                    i++;
                }
            }
            Limpa.tela();
            usuarioLogado = funcionarios.get(idUsuario);
            System.out.println("Usuário " + usuarioLogado.getNome() + " logado com sucesso.");
            menuGeral();

        } while (true);
    }

    public static void menuGeral() {
        Scanner in = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("Selecione um menu: ");
            System.out.println("[1]-> Locação");
            System.out.println("[2]-> Usuários");
            System.out.println("[3]-> Clientes");
            System.out.println("[4]-> Mídias");
            System.out.println("[5]-> Sair");
            opcao = in.nextInt();
            Limpa.tela();
        } while (opcao > 5 || opcao < 1);
        if (opcao == 1) {
            menuLocacao();

        } else if (opcao == 2) {

        } else if (opcao == 3) {
            menuClientes();
        } else if (opcao == 4) {
            menuMidias();
        } else if (opcao == 5) {
            int sair;
            do {
                System.out.println("Deseja se deslogar ou sair do sistema?");
                System.out.println("[1]-> Deslogar");
                System.out.println("[2]-> Sair");
                sair = in.nextInt();
                Limpa.tela();
                if (sair == 1) {
                    logar();
                }
                if (sair == 2) {
                    System.exit(0);
                }
            } while (sair < 1 || sair > 2);

        }
    }

    public static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados!");
        } else {
            System.out.println("Clientes cadastrados:");
            int i = 0;
            for (Cliente cliente : clientes) {
                System.out.println("[" + (i + 1) + "]-> " + cliente.getNome());
                i++;
            }
        }
    }

    public static void menuClientes() {
        int opcao;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Selecione uma opção: ");
            System.out.println("[1]-> Listar clientes");
            System.out.println("[2]-> Cadastrar cliente");
            System.out.println("[3]-> Excluir cliente");
            System.out.println("[4]-> Editar propriedade cliente");
            System.out.println("[5]-> Sair");
            opcao = in.nextInt();
            if (opcao == 1) {
                listarClientes();
            } else if (opcao == 2) {
                cadastrarCliente();
            } else if (opcao == 3) {
                excluirCliente();
            } else if (opcao == 4) {
                editarPropriedadeCliente();
            } else if (opcao == 5) {
                menuGeral();
            }
        } while (opcao > 5 || opcao < 1);
        menuClientes();
    }

    public static void menuLocacao() {
        int opcao;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Selecione uma opção: ");
            System.out.println("[1]-> Realizar locação");
            System.out.println("[2]-> Pesquisar emprestimos de cliente");
            System.out.println("[3]-> Sair");
            opcao = in.nextInt();
            if (opcao == 1) {
                realizarLocacao();
            } else if (opcao == 2) {
                pesquisarLocacao();
            } else if (opcao == 3) {
                menuGeral();
            }

        } while (opcao > 3 || opcao < 1);
        menuClientes();
    }

    public static void realizarLocacao() {
        Cliente clienteLoca = null;
        Midia midiaLoca = null;
        ArrayList<Midia> midiasLocadas= new ArrayList<>();
        Scanner in = new Scanner(System.in);
        String cpf;
        String dependente = "";
        int numeroMidia, idCliente = 0;
        boolean encontrou = false;
        System.out.println("-------Realizar locação-------");
        System.out.print("\nDigite o CPF do cliente: ");
        cpf = in.nextLine();
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().contains(cpf)) {
                clienteLoca = cliente;
                encontrou = true;
            } else {
                encontrou = false;
                idCliente++;
            }
        }
        if(!encontrou){
            System.out.println("Cliente não encontrado!");
            realizarLocacao();
        }
        else if (encontrou) {

            int escolha, idDependente = 0;
            if (clienteLoca.getQtdDependentes() > 0) {
                do {
                    System.out.println("\nÉ dependente?");
                    System.out.println("[1]-> Sim");
                    System.out.println("[2]-> Não");
                    escolha = in.nextInt();
                    if (escolha > 2 || escolha < 1) {
                        System.out.println("Opção incorreta!");
                    }
                } while (escolha > 2 || escolha < 1);

                if (escolha == 1) {
                    do {
                        clienteLoca.imprimeDependentes();
                        idDependente = in.nextInt();
                        if (idDependente > clienteLoca.getQtdDependentes() || idDependente < 1) {
                            System.out.println("Opção inválida!");
                        }
                    }while (idDependente > clienteLoca.getQtdDependentes() || idDependente < 1);
                }
                dependente = clienteLoca.getDependenteI(idDependente);
            }
            in.nextLine();

            System.out.print("\nInsira a data de locação: ");
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            int dia, mes, ano;
            dia = in.nextInt();
            mes = in.nextInt();
            ano = in.nextInt();
            Data dataLoca = new Data(dia, mes, ano);
            //Locar midias
            boolean midiaEncontrada = false;
            int adicionarMais;
            do {
                do{
                    System.out.println("\nInsira o número da mídia: ");
                    numeroMidia = in.nextInt();
                    for(Midia midia : midias){
                        if(midia.getNumero()==numeroMidia){
                            midiaEncontrada = true;
                            midiaLoca = midia;
                        }
                    }
                    if(!midiaEncontrada){
                    System.out.println("Mídia não encontrada");
                    }
                }while(!midiaEncontrada);
                midiasLocadas.add(midiaLoca);

                do{
                    System.out.println("\nDeseja adicionar mais mídias?");
                    System.out.println("[1]-> Sim");
                    System.out.println("[2]-> Não");
                    adicionarMais = in.nextInt();
                    if (adicionarMais > 2 || adicionarMais < 1) {
                        System.out.println("Opção incorreta!");
                    }
                } while (adicionarMais > 2 || adicionarMais < 1);
            }while(adicionarMais == 1);
            System.out.println("\nDeseja pagar agora?");
            boolean pagarAgora = false;
            int opcao;
            in.nextLine();
            do {
                System.out.println("\nDeseja pagar agora?");
                System.out.println("[1]-> Sim");
                System.out.println("[2]-> Não");
                opcao = in.nextInt();
                if (opcao > 2 || opcao < 1) {
                    System.out.println("Opção incorreta!");
                } else if (opcao == 1) {
                    pagarAgora = true;
                } else if (opcao == 2) {
                    pagarAgora = false;
                }
            } while (opcao > 2 || opcao < 1);
            Locacao loca = new Locacao(dataLoca, clienteLoca, pagarAgora, dependente, usuarioLogado.getNome(), midiasLocadas);
            locacoes.add(loca);
            clientes.get(idCliente).gravaLocacao(loca);
        }
        menuLocacao();
    }

    public static void pesquisarLocacao() {
        Scanner in = new Scanner(System.in);
        String cpf;
        Cliente clienteBusca = null;
        boolean encontrou = false;
        do{
            System.out.print("\nDigite o CPF do cliente: ");
            cpf = in.nextLine();
            for (Cliente cliente : clientes) {
                if (cliente.getCpf().contains(cpf)) {
                    encontrou = true;
                    cliente.imprimeLocacoes();
                } else {
                    encontrou = false;
                }
            }
            if(!encontrou) System.out.println("Cliente não encontrado!");
        }while(!encontrou);
        menuLocacao();
       
    }

    public static void excluirCliente() {
        Scanner in = new Scanner(System.in);
        int idCliente;

        do {
            System.out.println("Deseja excluir qual cliente?");
            listarClientes();
            idCliente = in.nextInt() - 1;
            if (idCliente < 0 || idCliente > clientes.size()) {
                Limpa.tela();
                System.out.println("Cliente inválido.");
            }
        } while (idCliente < 0 || idCliente > clientes.size());
        clientes.remove(idCliente);
    }

    public static void cadastrarCliente() {
        String nome, cpf, telefone;
        String nomeDependente;
        int opcao;
        Scanner in = new Scanner(System.in);
        System.out.print("Nome: ");
        nome = in.nextLine();
        System.out.print("\nCPF: ");
        cpf = in.nextLine();
        System.out.print("\nTelefone: ");
        telefone = in.nextLine();

        Cliente cliente = new Cliente(nome, cpf, telefone);

        do {
            System.out.println("\nDeseja adicionar dependentes?");
            System.out.println("[1]-> Sim");
            System.out.println("[2]-> Não");
            opcao = in.nextInt();
        } while (opcao < 1 || opcao > 2);
        if (opcao == 1) {
            do {
                if (!(cliente.getDependentes().equals("0"))) {
                    System.out.println("Já estão cadastrados os dependentes: " + cliente.getDependentes());
                }
                in.nextLine();
                System.out.print("\nNome do dependente: ");
                nomeDependente = in.nextLine();
                cliente.setDependente(nomeDependente);
                System.out.println("\nDeseja cadastrar mais um cliente?");
                System.out.println("[1]-> Sim");
                System.out.println("[2]-> Não");
                opcao = in.nextInt();
                while (opcao > 2 || opcao < 1) {
                    System.out.println("Opção inválida!");
                    System.out.println("\nDeseja cadastrar mais um cliente?");
                    System.out.println("[1]-> Sim");
                    System.out.println("[2]-> Não");
                    opcao = in.nextInt();
                }
            } while (opcao == 1);
        }
        clientes.add(cliente);
    }

    public static void editarPropriedadeCliente() {

    }

    public static void menuMidias() {
        int opcao;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Selecione uma opção: ");
            System.out.println("[1]-> Pesquisar mídia");
            System.out.println("[2]-> Cadastrar mídia");
            System.out.println("[3]-> Excluir mídia");
            System.out.println("[4]-> Editar mídia");
            System.out.println("[5]-> Definir preço de mídias");
            System.out.println("[6]-> Sair");
            opcao = in.nextInt();
            if (opcao == 1) {
                pesquisarMidias();
            }
            if (opcao == 2) {
                cadastrarMidia();
            }
            if (opcao == 3) {
                excluirMidia();
            }
            if (opcao == 4) {
                editarMidia();
            }
            if (opcao == 4) {
                precoMidias();
            }
            if (opcao == 6) {
                menuGeral();
            }
        } while (opcao > 5 || opcao < 1);
    }
    
    public static void precoMidias(){
        double valor;
        int catId, tipoMidia;
        Midia.setValorPorCategoriaMidia(valor, catId, tipoMidia);
    }

    public static void pesquisarMidias() {
        int opcao;
        int numeroMidia;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Escolha o tipo de pesquisa:");
            System.out.println("[1]-> MÍDIA POR NÚMERO");
            System.out.println("[2]-> DVD POR GÊNERO");
            System.out.println("[3]-> CD POR GRUPO OU CANTOR");
            System.out.println("[4]-> SAIR");
            opcao = in.nextInt();
        } while (opcao < 1 || opcao > 4);
        if (opcao == 1) {
            if (midias.isEmpty()) {
                System.out.println("Não há mídias cadastradas");
                pesquisarMidias();
            }
            System.out.print("\nDigite o número da mídia: ");
            numeroMidia = in.nextInt();
            in.nextLine();
            boolean encontrou = false;
            for (Midia midia : midias) {
                if (midia.getNumero() == numeroMidia) {
                    System.out.println("Mídia encontrada!");
                    midia.imprimeMidia();
                    encontrou = true;
                }
            }
            if (!encontrou) {
                System.out.println("Mídia não encontrada!");
            }

        } else if (opcao == 2) {
            if (midias.isEmpty()) {
                System.out.println("Não há mídias cadastradas");
                pesquisarMidias();
            }
            String genero;
            System.out.print("\nDigite o gênero do DVD: ");
            genero = in.nextLine();
            boolean encontrou = false;
            for (Midia midia : midias) {
                if (((DVD) midia).getGenero().equals(genero)) {
                    System.out.println("Mídia encontrada!");
                    midia.imprimeMidia();
                    encontrou = true;
                }
            }
            if (!encontrou) {
                System.out.println("Mídia não encontrada!");
            }

        } else if (opcao == 3) {
            int escolha;
            do {
                System.out.println("Deseja fazer a busca por grupo ou cantor?");
                System.out.println("[1]-> Grupo");
                System.out.println("[2]-> Cantor");
                escolha = in.nextInt();
                in.nextLine();
            } while (escolha < 1 || escolha > 2);

            if (midias.isEmpty()) {
                System.out.println("Não há mídias cadastradas");
                pesquisarMidias();
            }
            if (escolha == 1) {
                String grupo;
                System.out.print("\nDigite o grupo: ");
                grupo = in.nextLine();
                boolean encontrou = false;
                for (Midia midia : midias) {
                    if (((CD) midia).getGrupo().equals(grupo)) {
                        System.out.println("Mídia encontrada!");
                        midia.imprimeMidia();
                        encontrou = true;
                    }
                }
                if (!encontrou) {
                    System.out.println("Mídia não encontrada!");
                }
            }
            if (escolha == 2) {
                String cantor;
                System.out.print("\nDigite o cantor: ");
                cantor = in.nextLine();
                boolean encontrou = false;
                for (Midia midia : midias) {
                    if (((CD) midia).getCantor().equals(cantor)) {
                        System.out.println("Mídia encontrada!");
                        midia.imprimeMidia();
                        encontrou = true;
                    }
                }
                if (!encontrou) {
                    System.out.println("Mídia não encontrada!");
                }
            }

        }
    }

    public static void cadastrarMidia() {
        Scanner in = new Scanner(System.in);
        int tipo;
        do {
            System.out.println("Selecione o tipo de mídia:");
            System.out.println("[1]-> DVD");
            System.out.println("[2]-> CD");
            System.out.println("[3]-> GAME");
            tipo = in.nextInt();
            in.nextLine();
            if (tipo < 1 || tipo > 3) {
                System.out.println("Valor inválido!");
            }
        } while (tipo < 1 || tipo > 3);
        if (tipo == 1) {
            String genero, idioma, titulo;
            int qtdDisponivel, catId, numero, censura;
            in.nextLine();
            System.out.print("Título: ");
            titulo = in.nextLine();
            System.out.print("\nNúmero: ");
            numero = in.nextInt();
            System.out.print("\nCensura: ");
            censura = in.nextInt();
            System.out.println("\nCategoria:");
            do {
                System.out.println("[1]-> Lançamento");
                System.out.println("[2]-> Comum");
                System.out.println("[3]-> Acervo");
                catId = in.nextInt();
            } while (catId < 1 || catId > 3);
            System.out.print("\nDisponível: ");
            qtdDisponivel = in.nextInt();
            in.nextLine();
            System.out.print("\nGênero: ");
            genero = in.nextLine();
            System.out.print("\nIdioma: ");
            idioma = in.nextLine();

            DVD dvd = new DVD(genero, idioma, numero, censura, titulo, qtdDisponivel, catId);
            midias.add(dvd);
        } else if (tipo == 2) {
            String nome, grupo, cantor, titulo;
            int nMusicas, numero, censura, qtdDisponivel, catId;
            System.out.print("Título: ");
            titulo = in.nextLine();
            System.out.print("\nNúmero: ");
            numero = in.nextInt();
            System.out.print("\nCensura: ");
            censura = in.nextInt();
            System.out.println("\nCategoria:");
            do {
                System.out.println("[1]-> Lançamento");
                System.out.println("[2]-> Comum");
                System.out.println("[3]-> Acervo");
                catId = in.nextInt();
            } while (catId < 1 || catId > 3);
            System.out.print("\nDisponível: ");
            qtdDisponivel = in.nextInt();
            in.nextLine();
            System.out.print("\nNome: ");
            nome = in.nextLine();
            System.out.print("\nGrupo: ");
            grupo = in.nextLine();
            System.out.print("\nCantor: ");
            cantor = in.nextLine();
            System.out.print("\nNº Músicas: ");
            nMusicas = in.nextInt();
            in.nextLine();

            CD cd = new CD(nome, grupo, cantor, nMusicas, numero, censura, titulo, qtdDisponivel, catId);
            midias.add(cd);
        }
        if (tipo == 3) {
            String console, titulo;
            int numero, censura, qtdDisponivel, catId;
            System.out.print("Título: ");
            titulo = in.nextLine();
            System.out.print("\nNúmero: ");
            numero = in.nextInt();
            System.out.print("\nCensura: ");
            censura = in.nextInt();
            System.out.println("\nCategoria:");
            do {
                System.out.println("[1]-> Lançamento");
                System.out.println("[2]-> Comum");
                System.out.println("[3]-> Acervo");
                catId = in.nextInt();
            } while (catId < 1 || catId > 3);
            System.out.print("\nDisponível: ");
            qtdDisponivel = in.nextInt();
            in.nextLine();
            System.out.print("\nConsole: ");
            console = in.nextLine();

            Game game = new Game(console, numero, censura, titulo, qtdDisponivel, catId);
            midias.add(game);
        }
        menuMidias();
    }

    public static void excluirMidia() {
        int opcao;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Escolha uma mídia: ");
            listarMidias();
            opcao = in.nextInt();
        } while (opcao < 1 || opcao > midias.size());
        midias.remove(opcao - 1);
        menuMidias();
    }

    public static void editarMidia() {

    }

    public static void listarMidias() {
        if (midias.isEmpty()) {
            System.out.println("Não há mídias cadastradas!");
        } else {
            int i = 0;
            for (Midia midia : midias) {
                String tipoMidia = "";
                if (midia instanceof DVD) {
                    tipoMidia = "DVD";
                } else if (midia instanceof CD) {
                    tipoMidia = "CD";
                } else if (midia instanceof Game) {
                    tipoMidia = "GAME";
                }
                System.out.println("[" + (i + 1) + "]-> " + midia.getNumero() + " - " + midia.getTitulo() + " [" + tipoMidia + "]");
                i++;
            }
        }
    }
}
