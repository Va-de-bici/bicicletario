O presente trabalho é fruto do projeto de Monitoria da disciplina de Programação Modular.
O mesmo tem o objetivo de orientar os primeiros passos para a criação de um projeto modularizado em Java.


## Criando o Projeto no GitLab:
    1. Criar/Entrar com uma conta na plataforma GitLab. https://gitlab.com
    2. Com o mouse, localizar e clicar no botão "Novo Projeto"
    3. Selecionar a opção de "Projeto em Branco"
    4. Informar o nome do Projeto e marcá-lo como publico.
    5. Clicar no botão "Criar projeto"

## Primeiros passos no projeto:
    1. Clonar o projeto para o computador desejado.
        1.1. Utilize da ferramenta "Git" ou similar. https://git-scm.com/
        1.2 Com a ferramenta instalada, abra um terminal e digite "git clone"
        1.3 Na página do seu projeto no GitLab localize o botão "Clone" e copie o conteúdo de sua      
        preferência, colando após o comando "git clone". 
        Ex: "git clone https://gitlab.com/unirio-pm/projeto-base-programacao-modular-unirio-echo.git"
    2. Configurar o Git para realizar commits
        2.1 No terminal digite os seguintes comandos, alterando os dados dentro das aspas.
        Ex:
            git config --global user.name "Meu nome"
            git config --global user.email "Meu email"
    3. Atualize seu repositório local e remoto com frequência
        3.1 Para atualizar o repositório remoto utilize os comandos "git add", "git commit -m", "git push"
        Ex: 
            git add arquivo_alterado.txt
            git commit -m "Alteração no arquivo"
            git push
        3.2 Para atualizar o repositório local com as alterações do repositório remoto utilize "git pull"
        Ex:
            git pull

## Criando o projeto com o Maven
    1. Escolha uma plataforma de desenvolvimento do seu interesse.
    2. Crie um projeto Java por meio da plataforma, ou utilize o Maven para criar o projeto. https://maven.apache.org/
        Obs.: Caso utilize Windows, será necessário incluir o caminho nas variáveis do ambiente.
    3. É esperado que o seu projeto tenha uma pasta 'src' com duas subpastas 'main' e 'test', alem de um arquivo pom.xml




## Utilizando o site sonarcloud.io
    1. Com a conta utilizada no GitLab, realize login
    2. Localize um sinal de "+" onde deve-se mostrar ao clicar "Analize novo projeto"
    3. Selecione a opção de Analizar novo projeto.
    4. Informe a sua chave API do gitLab
        4.1 Acesse suas configurações pessoais em seguida chaves de acesso https://gitlab.com/-/profile/personal_access_tokens
        4.2 Marque a opção API, dê um nome para a chave e clique em criar.
        4.3 Copie o código abaixo de "Seu novo token de acesso pessoal"
    5. Prossiga com a criação da organização e selecione o projeto criado no GitLab
    6. Escolha a opção recomendada "Com CI/CD do GitLab"
    7. Defina as variáveis de ambiente como informado na tela. DICA: Os links mostrados levam direto ao local de configuraçãodo projeto.
    8. Ao clicar em continuar, selecione na proxima tela a opção maven.
    9. Crie um arquivo de configuração do sonar com o nome de ".gitlab-ci.yml" e salve o conteúdo abaixo.
        ```
        variables:
        SONAR_USER_HOME: "${CI_PROJECT_DIR}/.sonar"  # Defines the location of the analysis task cache
        GIT_DEPTH: "0"  # Tells git to fetch all the branches of the project, required by the analysis task
        sonarcloud-check:
        image: maven:3.6.3-jdk-11
        cache:
            key: "${CI_JOB_NAME}"
            paths:
            - .sonar/cache
        script:
            - mvn verify sonar:sonar
        only:
            - merge_requests
            - master
            - develop
        ```
    10. Caso já tenha criado o projeto Maven, insira o properties do sonar no arquivo "pom.xml", caso contrário guarde a informação para o uso futuro.
    ```xml
    <properties>
        <sonar.projectKey>nome do projeto no GitLab</sonar.projectKey>
        <sonar.organization> nome da organização </sonar.organization>
    </properties>
    ```
        
