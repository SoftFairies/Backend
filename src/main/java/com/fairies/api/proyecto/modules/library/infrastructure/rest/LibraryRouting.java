//package com.fairies.api.proyecto.modules.library.infrastructure.rest;
//
//import com.fairies.api.proyecto.common.application.security.JwtService;
//import com.fairies.api.proyecto.modules.library.application.*;
//import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
//import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.*;
//import com.fairies.api.proyecto.modules.library.infrastructure.rest.mapper.LibraryMapper;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("api/v1/library")
//public class LibraryRouting {
//
//    private final AddLibraryUseCase addLibraryUseCase;
//    private final GetAllLibraryUseCase getAllUseCase;
//    private final GetByIdLibraryUseCase getByIdUseCase;
//    private final SaveCustomizationUseCase saveCustomizationUseCase;
//    private final DeleteLibraryUseCase deleteUseCase;
//    private final JwtService jwtService;
//    private final LibraryMapper libraryMapper;
//
//    public LibraryRouting(
//            AddLibraryUseCase addUseCase,
//            GetAllLibraryUseCase getAllUseCase,
//            GetByIdLibraryUseCase getByIdUseCase,
//            SaveCustomizationUseCase saveCustomizationUseCase,
//            DeleteLibraryUseCase deleteUseCase,
//            JwtService jwtService,
//            LibraryMapper mapper
//    ) {
//        this.addLibraryUseCase = addUseCase;
//        this.getAllUseCase = getAllUseCase;
//        this.getByIdUseCase = getByIdUseCase;
//        this.saveCustomizationUseCase = saveCustomizationUseCase;
//        this.deleteUseCase = deleteUseCase;
//        this.jwtService = jwtService;
//        this.libraryMapper = mapper;
//    }
//
//    @PostMapping("/enroll")
//    public ResponseEntity<UserLibraryDetailResponse> enroll(
//            @Valid @RequestBody LibraryEnrollmentRequest request,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        UUID authenticatedUserId = jwtService.getUserIdFromToken(authHeader);
//        UserLibrary domain = libraryMapper.toDomain(request, authenticatedUserId);
//        UserLibrary saved = addLibraryUseCase.execute(domain);
//        return ResponseEntity.ok(libraryMapper.toDetailResponse(saved, null));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserLibraryDetailResponse> getById(
//            @PathVariable UUID id,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        UUID authenticatedUserId = jwtService.getUserIdFromToken(authHeader);
//
//        // 1. El UseCase hace la orquestación segura y nos devuelve las entidades del dominio
//        GetByIdLibraryUseCase.LibraryDetailResult result = getByIdUseCase.execute(id, authenticatedUserId);
//
//        // 2. El Mapper ejecuta el "Left Join" lógico:
//        // Si 'result.customization()' es null, MapStruct usará los datos globales del catálogo.
//        // Si trae datos, los inyectará sobreescribiendo el título/páginas original.
//        UserLibraryDetailResponse response = libraryMapper.toDetailResponse(result.library(), result.customization());
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<UserLibraryDetailResponse>> getAll(
//            @PageableDefault(size = 10, page = 0) Pageable pageable,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        UUID authenticatedUserId = jwtService.getUserIdFromToken(authHeader);
//
//        // 1. El UseCase resuelve la paginación y el cruce de datos optimizado
//        Page<GetAllLibraryUseCase.LibraryDetailResult> resultPage = getAllUseCase.execute(authenticatedUserId, pageable);
//
//        // 2. Mapeamos la página de resultados del dominio hacia los DTOs de respuesta con MapStruct
//        Page<UserLibraryDetailResponse> responsePage = resultPage.map(result ->
//                libraryMapper.toDetailResponse(result.library(), result.customization())
//        );
//
//        return ResponseEntity.ok(responsePage);
//    }
//
//    @PutMapping("/{id}/customization")
//    @Operation(summary = "Personalizar datos de un libro en la biblioteca")
//    public ResponseEntity<Void> customize(
//            @PathVariable UUID id,
//            @RequestBody BookCustomizationRequest request,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        UUID userId = jwtService.getUserIdFromToken(authHeader);
//        saveCustomizationUseCase.execute(userId, id, request);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(
//            @PathVariable UUID id,
//            @RequestHeader("Authorization") String authHeader
//    ) {
//        UUID userId = jwtService.getUserIdFromToken(authHeader);
//        deleteUseCase.execute(userId, id);
//        return ResponseEntity.noContent().build();
//    }
//
//}
