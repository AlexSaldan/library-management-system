# Architecture

This project follows Clean Architecture.

Layers:
- Domain: core business rules
- Application: use cases and ports
- Infrastructure: web, persistence, configuration

Rules:
- All business logic must be implemented as Use Cases.
- Controllers are thin adapters.
- Domain does not depend on Spring or JPA.
