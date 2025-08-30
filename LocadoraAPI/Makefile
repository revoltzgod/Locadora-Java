.PHONY: all clean compile run run-gui run-console fresh-gui fresh-console

# Detecta o sistema operacional
ifeq ($(OS),Windows_NT)
	CLASSPATH_SEP := ;
else
	CLASSPATH_SEP := :
endif

# Lista todos os JARs na pasta lib
JARS := $(wildcard lib/*.jar)
CLASSPATH_JARS := $(subst $(space),$(CLASSPATH_SEP),$(JARS))

# Compila o projeto
compile:
	@echo "Compilando o projeto..."
	@mkdir -p target/classes
	@javac -d target/classes -cp "$(CLASSPATH_JARS)" src/model/*.java src/util/*.java src/view/*.java src/view/gui/*.java src/view/gui/util/*.java

# Limpa os arquivos compilados
clean:
	@echo "Limpando arquivos compilados..."
	@rm -rf target/classes
	@find . -name "*.class" -type f -delete

# Executa a versão GUI
run-gui: compile
	@echo "Executando a interface gráfica..."
	@java -cp "target/classes$(CLASSPATH_SEP)$(CLASSPATH_JARS)" view.gui.LocadoraGUI

# Executa a versão console
run-console: compile
	@echo "Executando a versão console..."
	@java -cp "target/classes$(CLASSPATH_SEP)$(CLASSPATH_JARS)" view.console.LocadoraConsole

# Comando padrão (GUI)
run: run-gui

# Compila e prepara tudo
all: clean compile

# Limpa, compila e executa a interface gráfica
fresh-gui: clean compile run-gui
	@echo "Iniciando fresh-gui..."

# Limpa, compila e executa a versão console
fresh-console: clean compile run-console
	@echo "Iniciando fresh-console..." 