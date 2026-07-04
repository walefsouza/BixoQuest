# BixoQuest 🎓

O **BixoQuest** é um RPG de simulação acadêmica desenvolvido em Java com interface gráfica em JavaFX. O jogador assume o papel de um universitário e deve gerenciar seu tempo, energia e saúde ao longo de 6 semestres letivos, lidando com tarefas, provas, eventos aleatórios e a rotina do campus.

> O projeto foi desenvolvido como atividade de **Problema Baseado em Aprendizagem (PBL)** na Universidade Estadual de Feira de Santana (UEFS), ao longo de 3 fases evolutivas: modelagem, persistência de dados e interface gráfica.

---
## Tecnologias

* **Linguagem:** Java (Orientação a Objetos)
* **Interface Gráfica:** JavaFX e FXML (construído via Scene Builder)
* **Persistência de Dados:** JSON (via biblioteca Google Gson)

---

## Funcionalidades

* **Ciclo Temporal:** O jogo é estruturado em semanas e semestres. As ações do jogador consomem energia e fazem o tempo avançar.
* **Exploração:** Um mapa central *Point and Click* utilizando áreas poligonais invisíveis para transição de ambientes (Cantina, Ponto de Ônibus, Praça, etc).
* **Partidas:** Sistema de saves, permitindo criar, continuar ou excluir diferentes perfis de jogadores.
* **Eventos:** Ocorrência de eventos aleatórios e eventos globais (ex: Greve Geral) que alteram dinamicamente as tarefas e até mesmo o visual do mapa.

---

## Arquitetura de Software

Este projeto não foca apenas na jogabilidade, mas em manter um alto rigor técnico e arquitetural. 

### Padrão MVC
A estrutura de pastas e a organização do código respeitam estritamente o padrão **Model-View-Controller**, isolando completamente a lógica de negócios da camada de apresentação visual.

### Padrões de Projeto (GoF) Implementados:
* **Command:** Isolamento da lógica de ações da interface (ex: `PassarSemanaCommand`, `ViajarLocalCommand`). Permite o encapsulamento e injeção de dependências sem inflar os Controllers.
* **Singleton:** Controle de instâncias globais únicas para evitar sobrecarga, aplicado no `SessaoManager`, `AudioManager` e no sistema de cache.
* **Facade:** Encapsulamento da complexidade do framework JavaFX através de classes de serviço visual (ex: `SceneManager`, `Utilitarios`).
* **Repository:** Abstração total do ecossistema de arquivos físicos. A conversão de tipos genéricos foi contornada utilizando o `TypeToken` e `JsonDeserializer` (Gson).

---
