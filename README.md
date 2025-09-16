# 🖥️ First Fit Memory Allocation Simulator

Este é um projeto simples em **Java** que simula a estratégia de alocação de memória **First Fit**.

## 🚀 Como funciona
- A memória é representada por um vetor de `boolean`.
  - `true` → slot livre
  - `false` → slot ocupado
- Cada processo tem:
  - **PID** (identificador único)
  - **Tamanho** (1 a 20 slots)
  - **Tempo de vida** (em ciclos)
- A cada ciclo:
  - Novos processos podem aparecer (aleatoriamente)
  - O algoritmo **First Fit** busca o primeiro espaço contíguo disponível
  - Processos finalizados liberam memória
  - É exibido o estado atual da RAM (`L` = livre, `X` = ocupado)

## 📂 Estrutura do projeto
