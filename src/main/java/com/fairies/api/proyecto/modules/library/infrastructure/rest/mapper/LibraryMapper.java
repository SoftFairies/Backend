package com.fairies.api.proyecto.modules.library.infrastructure.rest.mapper;

import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.domain.model.Work;
import com.fairies.api.proyecto.modules.catalog.domain.models.Format;
import com.fairies.api.proyecto.modules.catalog.domain.models.ReadingStatus;
import com.fairies.api.proyecto.modules.library.domain.model.UserBookCustomization;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.BookCustomizationRequest;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.LibraryEnrollmentRequest;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.UserLibraryDetailResponse;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(mapUser(userId))")
    @Mapping(target = "book", expression = "java(mapBook(request))")
    @Mapping(target = "readingStatus", expression = "java(mapReadingStatus(request.progress().readingStatusId()))")
    @Mapping(target = "currentChapter", source = "request.progress.currentChapter")
    @Mapping(target = "currentPage", source = "request.progress.currentPage")
    @Mapping(target = "isFavorite", source = "request.progress.isFavorite")
    UserLibrary toDomain(LibraryEnrollmentRequest request, UUID userId);

    @Mapping(target = "libraryId", source = "library.id")
    @Mapping(target = "bookId", source = "library.book.id")
    @Mapping(target = "readingStatusName", source = "library.readingStatus.name")
    @Mapping(target = "currentChapter", source = "library.currentChapter")
    @Mapping(target = "currentPage", source = "library.currentPage")
    @Mapping(target = "title", expression = "java(custom != null && custom.getCustomTitle() != null ? custom.getCustomTitle() : library.getBook().getTitle())")
    @Mapping(target = "totalChapters", expression = "java(custom != null && custom.getCustomChapters() != null ? custom.getCustomChapters() : library.getBook().getDefaultChapters())")
    @Mapping(target = "totalPages", expression = "java(custom != null && custom.getCustomPages() != null ? custom.getCustomPages() : library.getBook().getDefaultPages())")
    @Mapping(target = "coverType", expression = "java(custom != null && custom.getCustomCoverType() != null ? custom.getCustomCoverType() : library.getBook().getCoverType())")
    @Mapping(target = "coverValue", expression = "java(custom != null && custom.getCustomCoverValue() != null ? custom.getCustomCoverValue() : library.getBook().getCoverValue())")
    @Mapping(target = "isbn", source = "library.book.isbn")
    @Mapping(target = "origin", source = "library.book.origin")
    @Mapping(target = "isFavorite", source = "library.favorite")
    UserLibraryDetailResponse toDetailResponse(UserLibrary library, UserBookCustomization custom);

    default User mapUser(UUID userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    default ReadingStatus mapReadingStatus(Long statusId) {
        if (statusId == null) return null;
        ReadingStatus status = new ReadingStatus();
        status.setId(statusId);
        return status;
    }

    default Book mapBook(LibraryEnrollmentRequest request) {
        if (request.bookId() != null) {
            Book book = new Book();
            book.setId(request.bookId());
            return book;
        }
        if (request.externalBook() != null) {
            Format format = new Format();
            format.setId(request.externalBook().formatId());

            Work work = new Work();
            work.setOriginalTitle(request.externalBook().originalTitle());

            return Book.builder()
                    .isbn(request.externalBook().isbn())
                    .title(request.externalBook().title())
                    .defaultChapters(request.externalBook().defaultChapters())
                    .defaultPages(request.externalBook().defaultPages())
                    .origin(request.externalBook().origin())
                    .coverType(request.externalBook().coverType())
                    .coverValue(request.externalBook().coverValue())
                    .format(format)
                    .work(work)
                    .build();
        }
        return null;
    }
}