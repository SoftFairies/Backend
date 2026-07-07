package com.fairies.api.proyecto.modules.library.application;

import com.fairies.api.proyecto.modules.book.application.AddBookUseCase;
import com.fairies.api.proyecto.modules.book.domain.model.Book;
import com.fairies.api.proyecto.modules.book.infrastructure.persistence.BookRepository;
import com.fairies.api.proyecto.modules.book.infrastructure.rest.mapper.BookMapper;
import com.fairies.api.proyecto.modules.format.infrastructure.persistence.FormatRepository;
import com.fairies.api.proyecto.modules.gamification.application.AwardBadgeUseCase;
import com.fairies.api.proyecto.modules.library.domain.model.UserLibrary;
import com.fairies.api.proyecto.modules.library.infrastructure.persistence.LibraryRepository;
import com.fairies.api.proyecto.modules.library.infrastructure.rest.dto.AddLibraryEntryRequest;
import com.fairies.api.proyecto.modules.readingStatus.infrastructure.persistence.ReadingStatusRepository;
import com.fairies.api.proyecto.modules.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final AddBookUseCase addBookUseCase;
    private final ReadingStatusRepository readingStatusRepository;
    private final FormatRepository formatRepository;
    private final BookMapper bookMapper;
    private final AwardBadgeUseCase awardBadgeUseCase;

    @Transactional
    public UserLibrary execute(User user, AddLibraryEntryRequest request) {
        Book book = (request.bookId() != null)
                ? bookRepository.findById(request.bookId()).orElseThrow()
                : addBookUseCase.execute(bookMapper.toDomain(request.newBook()));

        UserLibrary entry = libraryRepository.save(UserLibrary.builder()
                .user(user)
                .book(book)
                .readingStatus(readingStatusRepository.findById(request.readingStatusId()).orElseThrow())
                .format(formatRepository.findById(request.formatId()).orElseThrow())
                .currentChapter(0)
                .currentPage(0)
                .totalChapter(request.totalChapter() != null ? request.totalChapter() : 0)
                .totalPage(request.totalPage() != null ? request.totalPage() : 0)
                .isFavorite(request.isFavorite())
                .build());

        libraryRepository.flush();

        long bookCount = libraryRepository.countByUserId(user.getId());
        if (bookCount == 3) {
            awardBadgeUseCase.execute(user.getId(), 4L);
        }

        return entry;
    }
}