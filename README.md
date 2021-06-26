O presente trabalho é fruto do projeto de Monitoria da disciplina de Programação Modular.
O mesmo tem o objetivo de orientar os primeiros passos para a criação de um projeto modularizado em Java.


Criando o Projeto no GitLab:
    1- Criar/Entrar com uma conta na plataforma GitLab. https://gitlab.com
    2- Com o mouse, localizar e clicar no botão "Novo Projeto"
    3- Selecionar a opção de "Projeto em Branco"
    4- Informar o nome do Projeto e marcá-lo como publico.
    5- Clicar no botão "Criar projeto"

Primeiros passos no projeto:
    1- Clonar o projeto para o computador desejado.
        1.1- Utilize da ferramenta "Git" ou similar. https://git-scm.com/
        1.2- Com a ferramenta instalada, abra um terminal e digite "git clone"
        1.3- Na página do seu projeto no GitLab localize o botão "Clone" e copie o conteúdo de sua      
        preferência, colando após o comando "git clone". 
        Ex: "git clone https://gitlab.com/unirio-pm/projeto-base-programacao-modular-unirio-echo.git"
    2- Configurar o Git para realizar commits
        2.1- No terminal digite os seguintes comandos, alterando os dados dentro das aspas.
        Ex:
            git config --global user.name "Meu nome"
            git config --global user.email "Meu email"
    3- Atualize seu repositório local e remoto com frequência
        3.1- Para atualizar o repositório remoto utilize os comandos "git add", "git commit -m", "git push"
        Ex: 
            git add arquivo_alterado.txt
            git commit -m "Alteração no arquivo"
            git push
        3.2- Para atualizar o repositório local com as alterações do repositório remoto utilize "git pull"
        Ex:
            git pull
