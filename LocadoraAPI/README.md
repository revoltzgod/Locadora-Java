# Sistema de Locadora de Veículos 🚗

Este projeto foi desenvolvido como trabalho final da disciplina de Programação Orientada a Objetos, com o objetivo de criar um sistema de gerenciamento para uma locadora de veículos.

### Professor
- Leandro Cruz

## Equipe de Desenvolvimento 👥

- Andrei Ribeiro Reis dos Santos 
- Caio Neves Passos 
- Felipe Sobral Carneiro 
- Kauã Flaubert Lima Gomes 
- Renan Abreu Prazeres 
- Ryan Gabriel Rodrigues Mendes

## Requisitos do Sistema 💻

- Java JDK 8 ou superior
- MySQL 5.7 ou superior
- Para Linux/macOS: Make (já vem instalado por padrão)
- Para Windows: Nenhuma instalação adicional necessária

## Requisitos do Projeto 📋

O sistema foi desenvolvido atendendo aos seguintes requisitos:

1. **Menu de Operações**
   - Interface gráfica moderna (GUI) e console
   - Opções de CRUD para veículos e clientes
   - Navegação intuitiva entre funcionalidades

2. **Operações CRUD**
   - Create: Cadastro individual de registros
   - Read: Consulta e listagem de dados
   - Update: Alteração de registros existentes
   - Delete: Exclusão de registros

3. **Modelagem de Classes**
   - Hierarquia de veículos (4+ classes):
     - `Veiculo` (classe abstrata)
     - `Carro` (classe concreta)
     - `Moto` (classe concreta)
     - `Cliente` (classe concreta)
     - `Locacao` (classe concreta)

4. **Tecnologias**
   - Java como linguagem principal
   - Swing para interface gráfica
   - MySQL para persistência
   - Padrões de projeto aplicados

## Instalação e Configuração 🔧

1. Clone o repositório > git clone (chave SSH)
2. Configure o banco de dados MySQL
3. Execute o script de criação do banco (`src/sql/locadora.sql`)
4. Ajuste as credenciais do banco em `src/util/ConexaoMySQL.java`
5. Execute o sistema usando os comandos listados no início

## Comandos para Execução 🚀

### Windows
Basta executar o arquivo `run.bat` e escolher a opção desejada no menu:
```bash
run.bat
```

### macOS/Linux
Basta executar o arquivo `run.sh` e escolher a opção desejada no menu:
```bash
# Primeira vez (para tornar o script executável)
chmod +x run.sh

# Para executar
./run.sh
```

O menu apresentará as seguintes opções:
1. Executar Interface Gráfica
2. Executar Versão Console
3. Limpar Arquivos Compilados
4. Compilar Projeto
5. Limpar, Compilar e Executar GUI
6. Limpar, Compilar e Executar Console
7. Sair

## Sobre o Projeto 📋

Este projeto foi desenvolvido como parte da avaliação A3 da disciplina de Programação Orientada a Objetos. O objetivo principal é aplicar os conhecimentos adquiridos durante o semestre em um sistema de cadastro completo.

### Funcionalidades Implementadas ✨

1. **Cadastro (Create)**
   - Inclusão individual de veículos
   - Cadastro de clientes
   - Registro de locações
   - Validações em tempo real

2. **Consulta (Read)**
   - Busca por placa/CPF
   - Listagem de veículos disponíveis
   - Histórico de locações
   - Filtros e ordenação

3. **Atualização (Update)**
   - Modificação de dados de veículos
   - Atualização de cadastros
   - Alteração de locações
   - Validações de integridade

4. **Exclusão (Delete)**
   - Remoção de veículos
   - Exclusão de clientes
   - Cancelamento de locações
   - Controle de dependências

5. **Interface**
   - GUI moderna com Swing
   - Modo console alternativo
   - Feedback visual de operações
   - Mensagens de erro informativas

6. **Persistência**
   - Banco de dados MySQL
   - Transações seguras
   - Backup automático
   - Integridade referencial

## Estrutura do Projeto 🏗️

### Diretórios Principais

```
src/
├── model/          # Classes de modelo do sistema
├── view/           # Interface gráfica e componentes visuais
├── util/           # Classes utilitárias
├── sql/           # Scripts SQL para banco de dados
└── gui/           # Componentes específicos da interface gráfica
```

### Detalhamento das Pastas 📁

#### `src/model/`
- `Veiculo.java` - Classe abstrata base para veículos
- `Carro.java` - Implementação específica para carros (herança)
- `Moto.java` - Implementação específica para motos (herança)
- `Cliente.java` - Gerenciamento de clientes
- `Locacao.java` - Controle de locações

#### `src/view/gui/`
- `LocadoraGUI.java` - Interface gráfica principal
- `CadastroVeiculoGUI.java` - Tela de cadastro de veículos
- `CadastroClienteGUI.java` - Tela de cadastro de clientes
- `RealizarLocacaoGUI.java` - Tela de realização de locações
- Outras interfaces específicas para cada operação

#### `src/view/gui/util/`
- `GUIColors.java` - Definições de cores e estilos
- `ButtonFactory.java` - Fábrica de botões padronizados

#### `src/util/`
- `ConexaoMySQL.java` - Gerenciamento de conexão com banco de dados

#### `src/sql/`
- Scripts de criação e população do banco de dados

## Exemplos de Código 💻

### Herança e Polimorfismo
```java
// Classe abstrata base
public abstract class Veiculo {
    protected String placa;
    protected double valorDiaria;
    
    public abstract String getTipo();
    public abstract double calcularValorLocacao(int dias);
}

// Classe concreta
public class Carro extends Veiculo {
    private int numPortas;
    
    @Override
    public String getTipo() {
        return "Carro";
    }
    
    @Override
    public double calcularValorLocacao(int dias) {
        return valorDiaria * dias;
    }
}
```

### Interface Gráfica
```java
// Exemplo de tela de cadastro
public class CadastroVeiculoGUI extends JFrame {
    private JTextField txtPlaca;
    private JComboBox<String> cmbTipo;
    
    public CadastroVeiculoGUI() {
        setTitle("Cadastro de Veículo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // ... configuração dos componentes
    }
}
```

### Operações CRUD
```java
// Exemplo de operação de salvar
public void salvar() {
    String sql = "INSERT INTO veiculos (placa, tipo, valor_diaria) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, placa);
        stmt.setString(2, getTipo());
        stmt.setDouble(3, valorDiaria);
        stmt.executeUpdate();
    }
}
```

### Conexão com Banco
```java
// Exemplo de conexão MySQL
public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/locadora";
    private static final String USER = "root";
    private static final String PASS = "senha";
    
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
```

## Material de Apoio 📚

Para o desenvolvimento deste projeto, foram utilizados os seguintes materiais e recursos:

### Roteiros da Disciplina
- Roteiro 8
- Roteiro 9
- Roteiro 10

### Ferramentas de IA
- ChatGPT - Auxílio em dúvidas e boas práticas de programação
- Cursor - IDE com recursos de IA para desenvolvimento

### Material do Professor
- Repositório GitHub do Professor: [AulaBDJAVA](https://github.com/profleandrocruz8/AulaBDJAVA)