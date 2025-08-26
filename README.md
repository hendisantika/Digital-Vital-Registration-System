# Digital Vital Registration System

A comprehensive Spring Boot application for managing civil registration and vital statistics, including birth
certificates, death certificates, and marriage certificates. The system provides digital signature capabilities,
notification services, and secure document management.

## 🚀 Features

### Core Functionality

- **Birth Certificate Management**: Registration, verification, and certificate generation
- **Death Certificate Processing**: Death registration with proper documentation
- **Marriage Certificate Services**: Marriage registration for both local and foreign nationals
- **Digital Signatures**: RSA-based digital signature system for document authenticity
- **Document Management**: Secure storage and retrieval of citizen documents
- **Notification System**: Multi-channel notifications (Email, WhatsApp) via Kafka messaging
- **User Management**: Citizen registration, staff user management with role-based access
- **Dashboard Analytics**: Statistical analysis and reporting capabilities

### Technical Features

- **Microservices Architecture**: Modular design with separate services
- **Event-Driven Architecture**: Kafka-based messaging for asynchronous processing
- **Caching**: Redis integration for performance optimization
- **Security**: JWT-based authentication and authorization
- **File Upload**: Support for large file uploads (up to 20MB)
- **Email Integration**: SMTP email service with OTP verification
- **WhatsApp Integration**: WhatsApp Cloud API for notifications
- **Report Generation**: JasperReports integration for certificate generation

## 🛠 Technology Stack

### Backend Technologies

- **Java 21**: Latest LTS version of Java
- **Spring Boot 3.5.4**: Modern Spring framework
- **Spring Data JPA**: Database abstraction layer
- **Spring Security**: Authentication and authorization
- **Spring Cache**: Caching abstraction
- **Hibernate**: ORM framework
- **Maven**: Dependency management and build tool

### Databases & Messaging

- **PostgreSQL 17.5**: Primary database
- **Redis 8.2**: Caching and session storage
- **Apache Kafka (Bitnami)**: Message streaming platform

### Additional Libraries

- **JWT (jjwt) 0.11.5**: Token-based authentication
- **JasperReports 6.20.0**: Report generation
- **Testcontainers**: Integration testing
- **Lombok**: Code generation
- **MapStruct**: Bean mapping

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.9+**
- **Docker & Docker Compose** (for running infrastructure services)
- **Git** (for version control)

## 🚦 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Digital-Vital-Registration-System
```

### 2. Start Infrastructure Services

The application requires PostgreSQL, Redis, and Kafka. Start them using Docker Compose:

```bash
docker-compose up -d
```

This will start:

- **PostgreSQL**: Available at `localhost:5432` (credentials: admin/admin123)
- **PgAdmin**: Available at `http://localhost:5050` (credentials: admin@smartmunicipality.com/admin123)
- **Redis**: Available at `localhost:6379`
- **Kafka**: Available at `localhost:9092`

### 3. Configure Application Properties

The application is pre-configured to work with the Docker services. Key configurations:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/municipality
spring.datasource.username=admin
spring.datasource.password=admin123
# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
# Kafka
spring.kafka.bootstrap-servers=localhost:9092
```

### 4. Build and Run the Application

```bash
# Build the application
./mvnw clean compile

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## 🧪 Testing

The application includes comprehensive test suites:

### Running All Tests

```bash
./mvnw test
```

### Test Categories

#### Unit Tests

- **Application Context Tests**: Verify Spring context loading
- **Service Layer Tests**: Business logic validation
- **Repository Tests**: Database operation testing

#### Integration Tests

- **Database Integration**: PostgreSQL container testing
- **Full Stack Integration**: End-to-end testing with all services
- **Notification Service Integration**: Kafka messaging tests

### Test Infrastructure

- **Testcontainers**: Real database testing with PostgreSQL containers
- **H2 Database**: In-memory database for unit tests
- **Mock Beans**: External service mocking for isolated testing

## 🏗 Project Structure

```
src/
├── main/java/id/my/hendisantika/digitalvitalregistrationsystem/
│   ├── certificate/          # Certificate management (Birth, Death)
│   │   ├── controller/        # REST endpoints
│   │   ├── model/            # Entity models
│   │   ├── repository/       # Data access layer
│   │   └── service/          # Business logic
│   ├── citizen/              # Citizen management
│   ├── marriage/             # Marriage certificate services
│   ├── notification/         # Notification system
│   ├── security/             # Security configuration
│   ├── signature/            # Digital signature services
│   ├── staff/               # Staff user management
│   ├── user/                # User management
│   ├── kafka/               # Kafka configuration and services
│   ├── email/               # Email services
│   └── whatsapp/            # WhatsApp integration
└── test/                    # Test suites
    ├── integration/         # Integration tests
    └── config/             # Test configurations
```

## 🔧 Configuration

### Environment Variables

You can override default configurations using environment variables:

```bash
# Database Configuration
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=municipality
export DB_USER=admin
export DB_PASSWORD=admin123

# Redis Configuration
export REDIS_HOST=localhost
export REDIS_PORT=6379

# Kafka Configuration
export KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# JWT Secret
export JWT_SECRET="your-secret-key"
```

### Email Configuration

For email notifications, configure SMTP settings:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### WhatsApp Configuration

For WhatsApp notifications, configure the Cloud API:

```properties
whatsapp.access-token=your-access-token
whatsapp.phone-number-id=your-phone-number-id
```

## 📊 API Documentation

### Main Endpoints

#### Certificate Services

- `POST /api/birth-certificates` - Create birth certificate request
- `GET /api/birth-certificates/{id}` - Get birth certificate
- `POST /api/death-certificates` - Create death certificate request
- `POST /api/marriage-certificates` - Create marriage certificate request

#### Citizen Services

- `POST /api/citizens` - Register citizen
- `GET /api/citizens/{id}` - Get citizen details
- `PUT /api/citizens/{id}` - Update citizen information

#### User Management

- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication
- `POST /api/staff` - Create staff user

## 🔒 Security

### Authentication & Authorization

- **JWT Tokens**: Stateless authentication
- **Role-Based Access Control**: Different permissions for citizens and staff
- **Password Encryption**: BCrypt hashing
- **Secure Endpoints**: Protected API endpoints

### Digital Signatures

- **RSA Encryption**: 2048-bit key generation
- **Document Integrity**: Digital signature verification
- **Certificate Authenticity**: Tamper-proof certificates

## 📈 Monitoring & Logging

### Application Monitoring

- **Health Checks**: Spring Boot Actuator endpoints
- **Metrics**: Application performance metrics
- **Database Connection Monitoring**: HikariCP pool monitoring

### Logging

- **Structured Logging**: JSON-formatted logs
- **Log Levels**: Configurable logging levels
- **Audit Trail**: User action logging

## 🚀 Deployment

### Docker Deployment

Build Docker image:

```bash
./mvnw spring-boot:build-image
```

### Production Configuration

For production deployment:

1. **Database**: Use managed PostgreSQL service
2. **Redis**: Use managed Redis service
3. **Kafka**: Use managed Kafka service
4. **Security**: Update JWT secrets and passwords
5. **SSL/TLS**: Configure HTTPS certificates
6. **Environment Variables**: Set production configurations

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java coding conventions
- Write comprehensive tests
- Update documentation for new features
- Ensure all tests pass before submitting PR

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Hendi Santika**

- Email: hendisantika@yahoo.co.id
- Telegram: @hendisantika34
- GitHub: [hendisantika](https://github.com/hendisantika)

## 🆘 Support

If you encounter any issues or have questions:

1. Check the [Issues](../../issues) section
2. Create a new issue with detailed description
3. Contact the maintainer via email or Telegram

## 📚 Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Redis Documentation](https://redis.io/documentation)
- [JasperReports Documentation](https://community.jaspersoft.com/documentation)

---

**Built with ❤️ using Spring Boot and modern Java technologies**