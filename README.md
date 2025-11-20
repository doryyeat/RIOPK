# **Программное средство управления подарочными сертификатами**

Программное средство управления подарочными сертификатами позволяет автоматизировать процесс создания, продажи и активации подарочных сертификатов с учётом ролей: управляющий, менеджер и клиент. Система обеспечивает удобный и безопасный доступ к функционалу, отвечая требованиям современного бизнеса по контролю, учёту и аналитике.

Приложение построено с акцентом на разграничение прав доступа и удобную навигацию по рабочим областям.

**Цели проекта:**
1. Автоматизировать процесс управления подарочными сертификатами для улучшения операционной эффективности.
2. Обеспечить прозрачность и безопасность обработки сертификатов с разграничением ролей.
3. Создать современный и удобный в использовании интерфейс для разных категорий пользователей.
4. Предоставить инструменты аналитики для оценки продаж и активности клиентов.
5. Упростить процесс покупки и активации сертификатов, повысить удобство клиентов.

**Основные возможности:**
1. Многоуровневый доступ с тремя ролями: управляющий, менеджер, клиент.
2. Управление сертификатами.
3. Управление пользователями.
4. Каталог с карточками сертификатов.
5. Покупка подарочного сертификата с отправкой PDF на email.
6. Просмотр истории купленных сертификатов.
7. Активация сертификатов менеджером по коду, предоставленному клиентом.
8. Визуализация аналитических данных по продажам и активности пользователей.

## **Содержание**

1. [Архитектура](#Архитектура)
	1. [C4-модель](#C4-модель)
	2. [Схема данных](#Схема_данных)
2. [Функциональные возможности](#Функциональные_возможности)
	1. [Диаграмма вариантов использования](#Диаграмма_вариантов_использования)
	2. [User-flow диаграммы](#User-flow_диаграммы)
3. [Пользовательский интерфейс](#Пользовательский_интерфейс)
   	1. [Примеры экранов UI](#Примеры_экранов_UI)
5. [Детали реализации](#Детали_реализации)
	1. [UML-диаграммы](#UML-диаграммы)
	2. [Спецификация API](#Спецификация_API)
	3. [Безопасность](#Безопасность)
	4. [Оценка качества кода](#Оценка_качества_кода)
6. [Тестирование](#Тестирование)
	1. [Unit-тесты](#Unit-тесты)
	2. [Интеграционные тесты](#Интеграционные_тесты)
7. [Установка и  запуск](#installation)
	1. [Манифесты для сборки docker образов](#Манифесты_для_сборки_docker_образов)
	2. [Манифесты для развертывания k8s кластера](#Манифесты_для_развертывания_k8s_кластера)
8. [Лицензия](#Лицензия)
9. [Контакты](#Контакты)

---
## **Архитектура**

### C4-модель

Для описания архитектурных решений программного средства использована нотация C4-model, которая представляет архитектуру на четырех уровнях декомпозиции. Каждый последующий уровень детализирует предыдущий, включая четыре уровня абстракции: контекст, контейнеры, компоненты и код. На уровне контекста показывается обзор системы и ее взаимодействие с окружением. Для разрабатываемого программного средства контекстный уровень представлен ниже.

<img width="579" height="668" alt="image" src="https://github.com/user-attachments/assets/4369164f-9ef9-4d6a-9c42-e84158214582" />

Следующим уровнем представления архитектуры является контейнерный. В нем составные части архитектуры, определенные на контекстном уровне, декомпозируются для предоставления информации о технических блоках высокого уровня. Для разрабатываемого программного средства контейнерный уровень представлен ниже.

<img width="960" height="943" alt="image" src="https://github.com/user-attachments/assets/5cce4405-7a36-4cbe-9ffc-e4f61801a1ea" />

Следующим уровнем представления архитектуры является компонентный. В нем представляются внутренние блоки контейнеров, определенные на контейнерном уровне. Для разрабатываемого программного средства компонентный уровень представлен ниже.

<img width="974" height="719" alt="image" src="https://github.com/user-attachments/assets/748a0a11-6b94-433c-9ec5-f3b47690eb49" />

Самым нижним уровнем представления архитектуры является кодовый. В нем представляется внутренняя организация компонентов, определенных 
на компонентном уровне. Для разрабатываемого программного средства кодовый уровень представлен ниже.

<img width="1280" height="306" alt="image" src="https://github.com/user-attachments/assets/f7c0fe25-886f-47ef-9540-7560b925093a" />

Таким образом описаны архитектурные решения разрабатываемого программного средства при помощи нотации C4-model. Чистая архитектура аналитического решения в Gift Certificates строится на принципе разделения ответственности между слоями, где каждый слой решает строго определённую задачу и не зависит от технических деталей других. Это позволяет системе быть гибкой, масштабируемой и легко объяснимой – как для команды разработки, так и при защите архитектурных решений. В данном программном обеспечении находятся контроллеры, которые представляют основные бизнес-сценарии и сущности. Среди них есть AuthController, отвечающий за аутентификацию, CompanyDashboardController, который занимается созданием, обновлением, удалением и списком сертификатов, CertificateSearchController для поиска и фильтрации сертификатов, CertificateStatusController управляет активацией, деактивацией, сроками и статусом сертификатов, NotificationController отправляет уведомления о покупке, напоминания и обновления статуса, а SupportController обрабатывает создание и ответы на тикеты поддержки. Основные сущности в базе данных – это Certificate, который содержит ключевые поля, такие как идентификатор, код, статус, компания и связанные пользователи, а также User с данными о пользователе, такими как идентификатор, имя и электронная почта. Эти классы описывают логику работы с сертификатами, управлением их статусами, уведомлениями и поддержкой. Каждый контроллер реализует отдельный сценарий, соответствующий прикладной логике. В частности, контроллеры обрабатывают запросы на создание, обновление и удаление сертификатов, их поиск и фильтрацию по параметрам, управление статусом, отправку уведомлений и поддержку пользователей. Каждый такой сценарий принимает входные параметры, взаимодействует с бизнес-сущностями и возвращает результат пользователю. Контроллеры в пакете controller выступают как адаптеры между внешними HTTP-запросами и внутренней бизнес-логикой, преобразуя входящие запросы в действия над бизнес-объектами. Элементы инфраструктуры, хотя и не показаны на диаграмме, обычно включают использование Spring Boot для запуска и настройки приложения, Spring Data JPA и PostgreSQL для работы с базой данных сертификатов и пользователей, JWT для авторизации пользователей. Для взаимодействия с пользователем и визуализации данных применён фронтенд на React или аналогичной SPA технологии.

### Схема данных

<img width="1714" height="1074" alt="db" src="https://github.com/user-attachments/assets/62fa787e-986e-45f3-8d60-8dcbc0394416" />

## **Функциональные возможности**

### Диаграмма вариантов использования

Диаграмма вариантов использования и ее описание

### User-flow диаграммы

Ниже представлен User Flow для роли «Компания».

<img width="3596" height="1621" alt="Роль_Компания" src="https://github.com/user-attachments/assets/f3822320-2edb-477b-9ab3-f72272944826" />

После успешной авторизации пользователю предоставляется возможность работы с сертификатами, аналитикой и технической поддержкой. Выделены крупные процессы, такие как «Авторизация», «Анализ и мониторинг данных», а также целевой бизнес-процесс «Управление сертификатами». Ниже представлен User Flow для роли «Клиент».

<img width="4550" height="1621" alt="Роль_Клиент" src="https://github.com/user-attachments/assets/d0b86f0c-3d00-4cb6-8a6a-526e5d6aca0e" />

После успешной авторизации пользователю предоставляется возможность работы с каталогом сертификатов, сертификатами пользователя, историей покупок и личным профилем.
Выделены крупные процессы, такие как «Авторизация», «Управление учетной записью», а также целевые бизнес-процессы «Выбор подарочных сертификатов» и «Управление подарочными сертификатами».

## **Пользовательский интерфейс**

### Примеры экранов UI






---

## **Детали реализации**

### UML-диаграммы

**Поведенческие UML-диаграммы**

- Use Case
<img width="1335" height="1184" alt="Use_Case drawio" src="https://github.com/user-attachments/assets/9f230009-1dcb-4a1e-bd45-d4e2cc43d7d0" />

- Sequence Diagram
<img width="1195" height="1140" alt="Sequence_Diagram" src="https://github.com/user-attachments/assets/4fa096ce-5e16-49de-98ec-daf95644c28b" />

- Activity Diagram
<img width="760" height="877" alt="Activity_Diagram" src="https://github.com/user-attachments/assets/c7f75e42-44aa-41e7-86ac-a422da789614" />

**Структурные UML-диаграммы**

- Components Diagram
<img width="1491" height="2898" alt="Components_diagram" src="https://github.com/user-attachments/assets/eab1c8d1-f500-4c10-af4a-11d1c7eb4047" />

- Deployment Diagram
<img width="1579" height="655" alt="Deployment_Diagram drawio" src="https://github.com/user-attachments/assets/219856d2-42e9-487a-a601-a259128e90c7" />

### Спецификация API

Представить описание реализованных функциональных возможностей ПС с использованием Open API (можно представить либо полный файл спецификации, либо ссылку на него)

### Безопасность

**Регистрация пользователей через POST-запрос.** Контроллер обрабатывает POST-запрос на создание нового пользователя. Используется валидация данных через @Valid. Данные пользователя принимаются из тела запроса и передаются в сервис для сохранения. Результат преобразуется в DTO и возвращается клиенту.

```@PostMapping
public Result save(@Valid @RequestBody AppUser newUser) {
    return new Result(
            true,
            StatusCode.SUCCESS,
            "Success Save",
            toDtoConverter.convert(service.save(newUser))
    );
}
```

**Обработка аутентификации в контроллере.** Контроллер принимает объект аутентификации после успешного входа. В лог записывается имя пользователя. Создаётся ответ с JWT токеном и информацией о пользователе, возвращаемый клиенту.

```@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public Result getLoginIndo(Authentication authentication) {
        LOGGER.debug("Authenticated user: '{}'", authentication.getName());
        return new Result(
                true,
                StatusCode.SUCCESS,
                "User Info and JSON Web Token",
                authService.createLoginInfo(authentication)
        );
    }
}
```

**Сервис формирования данных пользователя и JWT токена.** В сервисе из объекта аутентификации извлекается пользователь. Он конвертируется в DTO для передачи клиенту. Параллельно генерируется JWT токен. Итог – карта с данными пользователя и токеном.

```@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserToUserDtoConverter userToUserDtoConverter;

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();

        AppUser user = principal.user();
        UserDto userDto = userToUserDtoConverter.convert(user);

        String token = jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();

        loginResultMap.put("user", userDto);
        loginResultMap.put("token", token);

        return loginResultMap;
    }
}
```

**Генерация JWT токена с помощью RSA.** Токен создаётся с текущим временем, временем истечения, субъектом (логином) и ролями пользователя. Все эти данные подписываются приватным ключом для безопасности.

```@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;

    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiresIn = 2;

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expiresIn, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
```

**Конфигурация безопасности Spring Security.** Настраивается защита API, политика без сессий, CORS, обработчики ошибок аутентификации и авторизации. Определяются механизмы кодирования пароля и генерации/проверки JWT.

```@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    public final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;
    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    private final CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint;
    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;

    public SecurityConfiguration(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint,
                                 CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint,
                                 CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler) throws NoSuchAlgorithmException {
        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
        this.customBearerTokenAuthenticationEntryPoint = customBearerTokenAuthenticationEntryPoint;
        this.customBearerTokenAccessDeniedHandler = customBearerTokenAccessDeniedHandler;

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                }))
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(customBasicAuthenticationEntryPoint))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(customBearerTokenAuthenticationEntryPoint)
                        .accessDeniedHandler(customBearerTokenAccessDeniedHandler))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
```

### Оценка качества кода

Используя показатели качества и метрики кода, оценить его качество

---

## **Тестирование**

### Unit-тесты

Представить код тестов для пяти методов и его пояснение

### Интеграционные тесты

Представить код тестов и его пояснение

---

## **Установка и  запуск**

### Манифесты для сборки docker образов

Представить весь код манифестов или ссылки на файлы с ними (при необходимости снабдить комментариями)

### Манифесты для развертывания k8s кластера

Представить весь код манифестов или ссылки на файлы с ними (при необходимости снабдить комментариями)

---

## **Лицензия**

Этот проект лицензирован по лицензии MIT - подробности представлены в файле [[License.md|LICENSE.md]]

---

## **Контакты**

Автор: zholudarya@gmail.com.
