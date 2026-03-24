🎟️ Eventick API
O Eventick é uma API robusta de gerenciamento de eventos e check-ins em tempo real. O sistema foi projetado para lidar com o ciclo completo de um participante: desde a inscrição até a validação na portaria do evento via QR Code, garantindo integridade e performance no processamento de dados.

🏗️ Arquitetura e Fluxo
O projeto segue os princípios de Clean Code e separação de responsabilidades, garantindo que a lógica de negócio seja independente de frameworks externos sempre que possível.

Principais Fluxos:
Inscrição & Validação: Ao se inscrever, o sistema valida a unicidade do e-mail por evento.

Geração de Credencial: Utiliza a biblioteca ZXing para gerar um QR Code único contendo a URI de check-in.

Comunicação: Integração com Mailtrap para envio assíncrono de ingressos digitais, evitando gargalos na requisição HTTP principal.

Check-in: Endpoint otimizado para validar a entrada do participante em milissegundos.

🛠️ Stack Tecnológica
Core: Java 17 & Spring Boot 3.

Persistência: Spring Data JPA com PostgreSQL.

Segurança & Validação: Bean Validation (Hibernate Validator).

Ferramentas: * Maven para gestão de dependências.

ZXing para processamento de imagens (QR Codes).

Spring Mail para integração SMTP.

🚀 Como Executar o Projet

1. Clonar e Configurar
   git clone https://github.com/seu-usuario/eventick.git
cd eventick

2. Variáveis de Ambiente
Configure o seu application.properties ou application.yml com as credenciais do seu banco e servidor de e-mail:
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/eventick_db
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}

# Mail (Mailtrap recomendado para testes)
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525

3. Build e Run
./mvnw clean install
./mvnw spring-boot:run

API Endpoints (Principais)

POST	/events	Registra um novo evento no sistema.
POST	/events/{id}/subscriptions	Inscreve um participante e dispara o e-mail com QR Code.
GET	/events/{id}/attendees	Retorna a lista de participantes e status de check-in.
PATCH	/attendees/{id}/check-in	Realiza a validação de entrada do participante.

🛡️ Diferenciais Implementados
Tratamento de Exceções Global: Uso de @ControllerAdvice para retornos HTTP padronizados em caso de erros de negócio (ex: evento lotado ou e-mail duplicado).
Geração Dinâmica de Assets: O QR Code é gerado em tempo de execução, garantindo que o banco de dados armazene apenas as informações necessárias.

Desenvolvido por Samuel (Samuca) – LinkedIn | Portfólio
