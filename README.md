# 📚 Library Management System

Projeto construído com **Clean Architecture** para demonstrar evolução incremental de um CRUD completo.

## 🌿 Branches por Etapa

| Ordem | Branch | Foco |
|-------|--------|------|
| 1️⃣ | `feature/inicio-do-projeto` | Domínio puro + RegisterBookUseCase |
| 2️⃣ | `feature/web-controller` | CRUD HTTP funcional |
| 3️⃣ | `feature/usecases-completos` | DeleteBookUseCase + exceções |
| 4️⃣ | `feature/update-usecase` | UpdateBookUseCase + validações |
| 5️⃣ | `feature/testes-unitarios` | ✨ Testes unitários completos |

## 💡 Por que este projeto?

- ✅ Arquitetura limpa (separação Domínio/Aplicação/Infraestrutura)
- ✅ Testes unitários com JUnit 5 + Mockito
- ✅ Commits significativos seguindo Conventional Commits
- ✅ Evolução incremental em branches focadas

## ▶️ Rodar

```bash
./mvnw spring-boot:run