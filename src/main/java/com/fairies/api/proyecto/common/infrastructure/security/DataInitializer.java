//package com.fairies.api.proyecto.common.infrastructure.security;
//
//import com.fairies.api.proyecto.modules.author.application.AddAuthorUseCase;
//import com.fairies.api.proyecto.modules.author.domain.model.Author;
//import com.fairies.api.proyecto.modules.author.infrastructure.persistence.AuthorRepository;
//import com.fairies.api.proyecto.modules.format.application.AddFormatUseCase;
//import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
//import com.fairies.api.proyecto.modules.gender.application.AddGenderUseCase;
//import com.fairies.api.proyecto.modules.gender.domain.model.Gender;
//import com.fairies.api.proyecto.modules.gender.infrastructure.persistence.GenderRepository;
//import com.fairies.api.proyecto.modules.readingStatus.application.AddReadingStatusUseCase;
//import com.fairies.api.proyecto.modules.readingStatus.domain.model.ReadingStatus;
//import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
//import com.fairies.api.proyecto.modules.user.domain.model.Role;
//import com.fairies.api.proyecto.modules.user.infrastructure.persistence.RoleRepository;
//
//import com.fairies.api.proyecto.common.infrastructure.rest.dto.CatalogPlainRequest;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final RoleRepository roleRepository;
//
//    private final GenderRepository genderRepository;
//    private final AuthorRepository authorRepository;
//    private final FormatRepository formatRepository;
//    private final ReadingStatusRepository readingStatusRepository;
//
//    private final AddGenderUseCase addGenderUseCase;
//    private final AddAuthorUseCase addAuthorUseCase;
//    private final AddFormatUseCase addFormatUseCase;
//    private final AddReadingStatusUseCase addReadingStatusUseCase;
//
//    public DataInitializer(
//            RoleRepository roleRepository,
//            GenderRepository genderRepository,
//            AuthorRepository authorRepository,
//            FormatRepository formatRepository,
//            ReadingStatusRepository readingStatusRepository,
//
//            AddGenderUseCase addGenderUseCase,
//            AddAuthorUseCase addAuthorUseCase,
//            AddFormatUseCase addFormatUseCase,
//            AddReadingStatusUseCase addReadingStatusUseCase
//    ) {
//        this.roleRepository = roleRepository;
//        this.genderRepository = genderRepository;
//        this.authorRepository = authorRepository;
//        this.formatRepository = formatRepository;
//        this.readingStatusRepository = readingStatusRepository;
//
//        this.addGenderUseCase = addGenderUseCase;
//        this.addAuthorUseCase = addAuthorUseCase;
//        this.addFormatUseCase = addFormatUseCase;
//        this.addReadingStatusUseCase = addReadingStatusUseCase;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        initializeRoles();
//        initializeGenders();
//        initializeAuthors();
//        initializeFormats();
//        initializeReadingStatuses();
//    }
//
//    private void initializeRoles() {
//        List<String> defaultRoles = List.of("ROLE_ADMIN", "ROLE_USER");
//        for (String roleName : defaultRoles) {
//            if (roleRepository.findByName(roleName).isEmpty()) {
//                Role newRole = Role.builder()
//                        .name(roleName)
//                        .build();
//                roleRepository.save(newRole);
//                System.out.println("-> Rol inicializado con éxito: " + roleName);
//            }
//        }
//    }
//
//    private void initializeGenders() {
//        if (genderRepository.count() == 0) {
//            List<Gender> initialGenders = List.of(
//                    Gender.builder().name("Fantasía").description("Catálogo de género: Fantasía").build(),
//                    Gender.builder().name("Ciencia Ficción").description("Catálogo de género: Ciencia Ficción").build(),
//                    Gender.builder().name("Romance").description("Catálogo de género: Romance").build(),
//                    Gender.builder().name("Misterio").description("Catálogo de género: Misterio").build(),
//                    Gender.builder().name("Drama").description("Catálogo de género: Drama").build(),
//                    Gender.builder().name("Acción").description("Catálogo de género: Acción").build()
//            );
//
//            for (Gender gender : initialGenders) {
//                addGenderUseCase.execute(gender);
//            }
//            System.out.println("-> Catálogo de Géneros inicializado con éxito.");
//        }
//    }
//
//    private void initializeAuthors() {
//        if (authorRepository.count() == 0) {
//            List<Author> initialAuthors = List.of(
//                    Author.builder().name("Eiichiro Oda").description("Escritor/Ilustrador destacado.").build(),
//                    Author.builder().name("Brandon Sanderson").description("Escritor/Ilustrador destacado.").build(),
//                    Author.builder().name("J.K. Rowling").description("Escritor/Ilustrador destacado.").build(),
//                    Author.builder().name("Stephen King").description("Escritor/Ilustrador destacado.").build(),
//                    Author.builder().name("Hajime Isayama").description("Escritor/Ilustrador destacado.").build()
//            );
//
//            for (Author author : initialAuthors) {
//                addAuthorUseCase.execute(author);
//            }
//            System.out.println("-> Catálogo de Autores inicializado con éxito.");
//        }
//    }
//
//    private void initializeFormats() {
//        if (formatRepository.count() == 0) {
//            List<CatalogPlainRequest> initialFormats = List.of(
//                    new CatalogPlainRequest("Manga", "Formato de lectura: Manga"),
//                    new CatalogPlainRequest("Manhwa", "Formato de lectura: Manhwa"),
//                    new CatalogPlainRequest("Manhua", "Formato de lectura: Manhua"),
//                    new CatalogPlainRequest("Novela Ligera", "Formato de lectura: Novela Ligera"),
//                    new CatalogPlainRequest("Libro Tradicional", "Formato de lectura: Libro Tradicional"),
//                    new CatalogPlainRequest("Webtoon", "Formato de lectura: Webtoon")
//            );
//
//            for (CatalogPlainRequest request : initialFormats) {
//                addFormatUseCase.execute(request);
//            }
//            System.out.println("-> Catálogo de Formatos inicializado con éxito.");
//        }
//    }
//
//    private void initializeReadingStatuses() {
//        if (readingStatusRepository.count() == 0) {
//            List<ReadingStatus> initialStatuses = List.of(
//                    ReadingStatus.builder().name("Leyendo").description("Estado de progreso: Leyendo").build(),
//                    ReadingStatus.builder().name("Terminado").description("Estado de progreso: Terminado").build(),
//                    ReadingStatus.builder().name("Pausado").description("Estado de progreso: Pausado").build(),
//                    ReadingStatus.builder().name("Abandonado").description("Estado de progreso: Abandonado").build(),
//                    ReadingStatus.builder().name("Por Leer").description("Estado de progreso: Por Leer").build()
//            );
//
//            for (ReadingStatus status : initialStatuses) {
//                addReadingStatusUseCase.execute(status);
//            }
//            System.out.println("-> Catálogo de Estados de Lectura inicializado con éxito.");
//        }
//    }
//}