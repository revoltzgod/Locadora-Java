#!/bin/bash

# Função para compilar
compile() {
    echo "Compilando o projeto..."
    mkdir -p target/classes
    find src -name "*.java" > sources.txt
    javac -d target/classes -cp "lib/*" @sources.txt
    rm sources.txt
}

# Função para limpar
clean() {
    echo "Limpando arquivos compilados..."
    rm -rf target/classes
    find . -name "*.class" -type f -delete
}

# Função para executar GUI
run_gui() {
    echo "Executando a interface gráfica..."
    java -cp "target/classes:lib/*" view.gui.LocadoraGUI
}

# Função para executar Console
run_console() {
    echo "Executando a versão console..."
    java -cp "target/classes:lib/*" view.Sistema
}

# Menu de opções
show_menu() {
    echo "================================"
    echo "    LOCADORA - MENU DE OPÇÕES    "
    echo "================================"
    echo
    echo "1. Executar Interface Gráfica"
    echo "2. Executar Versão Console"
    echo "3. Limpar Arquivos Compilados"
    echo "4. Compilar Projeto"
    echo "5. Limpar, Compilar e Executar GUI"
    echo "6. Limpar, Compilar e Executar Console"
    echo "7. Sair"
    echo
    echo "================================"
    echo
}

# Loop principal
while true; do
    show_menu
    read -p "Escolha uma opção (1-7): " opcao
    echo

    case $opcao in
        1)
            compile && run_gui
            ;;
        2)
            compile && run_console
            ;;
        3)
            clean
            ;;
        4)
            compile
            ;;
        5)
            clean && compile && run_gui
            ;;
        6)
            clean && compile && run_console
            ;;
        7)
            echo "Encerrando..."
            exit 0
            ;;
        *)
            echo "Opção inválida!"
            ;;
    esac
    echo
    read -p "Pressione ENTER para continuar..."
done 