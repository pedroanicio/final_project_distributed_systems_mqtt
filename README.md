# Relatório do trabalho final de Sistemas Distribuídos

## 1. Introdução
Este relatóro busca descrever o desenvolvimento e funcionamento de um middleware para intermediação entre sensores, atuadores e controladores utilizando MQTT e Cassandra.
O sistema foi desenvolvido para um ambiente de automação residencial, onde sensores monitoram condições de máquinas e atuadores realizam comandos automáticos.
## 2. Arquitetura do Sistema
A solução foi projetada para intermediar a comunicação entre diferentes componenestes do sistema de orma eficiente e segura. O middleware utiliza o broker MQTT para transmissão de mensagens e o Cassandra para armazenamento de eventos e comandos.
### 2.1 Intermediação com Sensores, Atuadores e Controladores
* Sensores: Publicam eventos nos tópicos MQTT;
* Atuadores: Assinam os tópicos MQTT para receber comandos e executar ações;
* Controladores: Gerenciam as informações recebidas dos sensores, armazenam os eventos no banco de dados e tomam decisões baseadas nesses dados.
### 2.2 Publicação de Tópicos pelos Sensores
Os sensores capturam eventos do ambiente e publicam no broker MQTT. O middleware processa e encaminha essas mensagens para os componentes responśaveis, garantindo o fluxo contínuo de dados.
### 2.3 Assinatura dos Tópicos pelos Atuadores
Os atuadores assinam os tópicos MQTT para receber comandos enviados pelos controladores. Dessa forma, é possível automatizar processos com base nas condições registradas pelos sensores.
### 2.4 Gerenciamento de Processos pelos Controladores
Os controladores recebem os dados dos sensores e processam as informações para tomar as decisões corretas, acionando os atuadores específicos para aquela situação.
### 2.5 Visualização e Controle do Sistema pelos Clientes
Os eventos e comandos realizados pelos sensores e atuadores ficam armazenados num banco de dados compartilhado, permitindo o monitoramento em tempo real do funcionamento do sistema.
### 2.6 Middleware como Intermediador da Comunicação
O middleware atua como um intermediador entre os clientes e os processos do sistema. Ele garante a comunicação baseada em objetos, permitindo que os clientes acompanhem eventos em tempo real.
## 3. Disponibilidade e Tolerância a Falhas
### 3.1 Replicas para os Dados Armazenados
O cassandra foi escolhido para garantir a alta disponibilidade dos dados. Ele distribui informações replicadas entre nós, prevenindo a perda de dados em caso de falha de um nó.
### 3.2 Continuidade da Execução em Caso de Falha do Controlador
O middleware garante que mesmo quando um controlador caia, o cliente continue recebendo respostas. Isso ocorre pois várias instâncias de um controller são executadas ao mesmo tempo, e se uma cair, a outra assume a função. Essa funcinalidade também assegura que nenhum controlador fique sobrecarregado.
## 4. Segurança do Sistema
Para evitar respostas incorretas por falha ou invasão, o middleware implementa verificações de consistência, que analisam padrões dos dados recebidos. Caso existam valores incoerentes, os dados são ignorados.
## 5. Conclusão
O middleware desenvolvido atende aos requisitos de um sistema distribuído de alta disponibilidade e segurança, garantindo comunicação confiável entre sensores, atuadores e controladores. Com a utilização do MQTT e Cassandra, o sistema é escalável e robusto, sendo adequado para aplicações de automação residencial.
