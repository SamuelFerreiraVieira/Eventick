Eventick - Back-end Event Management System
O Eventick é uma API robusta para gerenciamento de eventos, focada em automação de inscrições, geração de credenciais via QR Code e comunicação direta com o participante.

⚙️ Funcionalidades Core
Gestão de Inscrições: Cadastro de participantes e controle de vagas por evento.

Geração de QR Code: Emissão automática de códigos únicos para check-in (utilizando a biblioteca ZXing).

Automação de E-mail: Envio de confirmações e ingressos digitais via Mailtrap (ambiente de teste) ou SMTP.

Persistência de Dados: Modelagem relacional para eventos, participantes e check-ins.

🛠️ Stack Tecnológica
Linguagem: Java 17

Framework: Spring Boot 3.x

Banco de Dados: PostgreSQL

Ferramentas de Suporte:

Spring Data JPA (ORM)

Spring Mail (Notificações)

ZXing (Gerador de QR Code)

Maven (Gerenciador de dependências)

🚀 Como Rodar o Projeto
Pré-requisitos
JDK 17 ou superior instalado.

PostgreSQL rodando localmente ou via Docker.

Maven configurado no Path.
