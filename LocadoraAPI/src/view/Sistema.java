package view;

import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.*;
import util.ConexaoMySQL;

public class Sistema {
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (opcao) {
                    case 1:
                        cadastrarVeiculo();
                        break;
                    case 2:
                        cadastrarCliente();
                        break;
                    case 3:
                        realizarLocacao();
                        break;
                    case 4:
                        finalizarLocacao();
                        break;
                    case 5:
                        listarVeiculos();
                        break;
                    case 6:
                        listarClientes();
                        break;
                    case 7:
                        listarLocacoes();
                        break;
                    case 8:
                        excluirVeiculo();
                        break;
                    case 9:
                        excluirCliente();
                        break;
                    case 10:
                        excluirLocacao();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
            
        } while (opcao != 0);
        
        scanner.close();
        ConexaoMySQL.fecharConexao();
    }
    
    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE LOCADORA DE VEÍCULOS ===");
        System.out.println("1. Cadastrar Veículo");
        System.out.println("2. Cadastrar Cliente");
        System.out.println("3. Realizar Locação");
        System.out.println("4. Finalizar Locação");
        System.out.println("5. Listar Veículos");
        System.out.println("6. Listar Clientes");
        System.out.println("7. Listar Locações");
        System.out.println("8. Excluir Veículo");
        System.out.println("9. Excluir Cliente");
        System.out.println("10. Excluir Locação");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");
    }
    
    private static void excluirVeiculo() throws Exception {
        System.out.println("\n=== EXCLUIR VEÍCULO ===");
        
        List<Veiculo> veiculos = Veiculo.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("Não há veículos cadastrados!");
            return;
        }
        
        System.out.println("\nVeículos disponíveis:");
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo.getPlaca() + " - " + veiculo.getMarca() + " " + veiculo.getModelo());
        }
        
        System.out.print("\nPlaca do veículo a ser excluído: ");
        String placa = scanner.nextLine();
        
        Veiculo veiculo = Veiculo.buscarPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }
        
        veiculo.excluir();
        System.out.println("Veículo excluído com sucesso!");
    }

    private static void excluirCliente() throws Exception {
        System.out.println("\n=== EXCLUIR CLIENTE ===");
        
        List<Cliente> clientes = Cliente.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados!");
            return;
        }
        
        System.out.println("\nClientes disponíveis:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getCpf() + " - " + cliente.getNome());
        }
        
        System.out.print("\nCPF do cliente a ser excluído: ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = Cliente.buscarPorCPF(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
        
        cliente.excluir();
        System.out.println("Cliente excluído com sucesso!");
    }

    private static void excluirLocacao() throws Exception {
        System.out.println("\n=== EXCLUIR LOCAÇÃO ===");
        
        List<Locacao> locacoes = Locacao.listarTodas();
        if (locacoes.isEmpty()) {
            System.out.println("Não há locações cadastradas!");
            return;
        }
        
        System.out.println("\nLocações disponíveis:");
        for (Locacao locacao : locacoes) {
            System.out.println("ID: " + locacao.getId() + 
                             " - Cliente: " + locacao.getCliente().getNome() + 
                             " - Veículo: " + locacao.getVeiculo().getPlaca());
        }
        
        System.out.print("\nID da locação a ser excluída: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        Locacao locacao = Locacao.buscarPorId(id);
        if (locacao == null) {
            System.out.println("Locação não encontrada!");
            return;
        }
        
        locacao.excluir();
        System.out.println("Locação excluída com sucesso!");
    }
    
    private static void cadastrarVeiculo() throws Exception {
        System.out.println("\n=== CADASTRO DE VEÍCULO ===");
        
        System.out.print("Tipo (1-Carro, 2-Moto): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        
        System.out.print("Valor da Diária: R$ ");
        double valorDiaria = scanner.nextDouble();
        scanner.nextLine();
        
        Veiculo veiculo;
        
        if (tipo == 1) {
            System.out.print("Número de Portas: ");
            int numeroPortas = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Tipo de Câmbio: ");
            String tipoCambio = scanner.nextLine();
            
            System.out.print("Possui Ar Condicionado (S/N)? ");
            boolean arCondicionado = scanner.nextLine().equalsIgnoreCase("S");
            
            veiculo = new Carro(placa, modelo, marca, ano, valorDiaria,
                              numeroPortas, tipoCambio, arCondicionado);
        } else {
            System.out.print("Cilindradas: ");
            int cilindradas = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Tipo de Partida: ");
            String tipoPartida = scanner.nextLine();
            
            veiculo = new Moto(placa, modelo, marca, ano, valorDiaria,
                             cilindradas, tipoPartida);
        }
        
        veiculo.salvar();
        System.out.println("Veículo cadastrado com sucesso!");
    }
    
    private static void cadastrarCliente() throws Exception {
        System.out.println("\n=== CADASTRO DE CLIENTE ===");
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.print("CNH: ");
        String cnh = scanner.nextLine();
        
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        
        Cliente cliente = new Cliente(cpf, nome, telefone, cnh, endereco);
        cliente.salvar();
        
        System.out.println("Cliente cadastrado com sucesso!");
    }
    
    private static void realizarLocacao() throws Exception {
        System.out.println("\n=== REALIZAR LOCAÇÃO ===");
        
        List<Cliente> clientes = Cliente.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados!");
            return;
        }
        
        System.out.println("\nClientes disponíveis:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getCpf() + " - " + cliente.getNome());
        }
        
        System.out.print("\nCPF do Cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = Cliente.buscarPorCPF(cpf);
        
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
        
        List<Veiculo> veiculos = Veiculo.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("Não há veículos cadastrados!");
            return;
        }
        
        System.out.println("\nVeículos disponíveis:");
        for (Veiculo veiculo : veiculos) {
            if (veiculo.isDisponivel()) {
                System.out.println(veiculo.getPlaca() + " - " + veiculo.getMarca() + " " + veiculo.getModelo());
            }
        }
        
        System.out.print("\nPlaca do Veículo: ");
        String placa = scanner.nextLine();
        Veiculo veiculo = Veiculo.buscarPorPlaca(placa);
        
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }
        
        if (!veiculo.isDisponivel()) {
            System.out.println("Veículo não está disponível!");
            return;
        }
        
        System.out.print("Data de Início (dd/mm/aaaa): ");
        LocalDate dataInicio = LocalDate.parse(scanner.nextLine(), dateFormatter);
        
        System.out.print("Data de Fim (dd/mm/aaaa): ");
        LocalDate dataFim = LocalDate.parse(scanner.nextLine(), dateFormatter);
        
        Locacao locacao = new Locacao(cliente, veiculo, dataInicio, dataFim);
        locacao.salvar();
        
        veiculo.setDisponivel(false);
        veiculo.atualizar();
        
        System.out.println("Locação realizada com sucesso!");
        System.out.printf("Valor total: R$ %.2f%n", locacao.calcularValorTotal());
    }
    
    private static void finalizarLocacao() throws Exception {
        System.out.println("\n=== FINALIZAR LOCAÇÃO ===");
        
        List<Locacao> locacoes = Locacao.listarTodas();
        if (locacoes.isEmpty()) {
            System.out.println("Não há locações cadastradas!");
            return;
        }
        
        System.out.println("\nLocações em andamento:");
        for (Locacao locacao : locacoes) {
            if (locacao.isAtiva()) {
                System.out.println(locacao);
                System.out.println();
            }
        }
        
        System.out.print("\nID da Locação: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        Locacao locacao = Locacao.buscarPorId(id);
        
        if (locacao == null) {
            System.out.println("Locação não encontrada!");
            return;
        }
        
        if (!locacao.isAtiva()) {
            System.out.println("Esta locação já foi finalizada!");
            return;
        }
        
        locacao.setAtiva(false);
        locacao.atualizar();
        
        Veiculo veiculo = locacao.getVeiculo();
        veiculo.setDisponivel(true);
        veiculo.atualizar();
        
        System.out.println("Locação finalizada com sucesso!");
        System.out.printf("Valor total: R$ %.2f%n", locacao.calcularValorTotal());
    }
    
    private static void listarVeiculos() throws Exception {
        System.out.println("\n=== LISTA DE VEÍCULOS ===\n");
        
        List<Veiculo> veiculos = Veiculo.listarTodos();
        
        if (veiculos.isEmpty()) {
            System.out.println("Não há veículos cadastrados!");
            return;
        }
        
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
            System.out.println();
        }
    }
    
    private static void listarClientes() throws Exception {
        System.out.println("\n=== LISTA DE CLIENTES ===\n");
        
        List<Cliente> clientes = Cliente.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados!");
            return;
        }
        
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
            System.out.println();
        }
    }
    
    private static void listarLocacoes() throws Exception {
        System.out.println("\n=== LISTA DE LOCAÇÕES ===\n");
        
        List<Locacao> locacoes = Locacao.listarTodas();
        
        if (locacoes.isEmpty()) {
            System.out.println("Não há locações cadastradas!");
            return;
        }
        
        for (Locacao locacao : locacoes) {
            System.out.println(locacao);
            System.out.println();
        }
    }
} 