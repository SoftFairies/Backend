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
@Transactional
@RequiredArgsConstructor
public class AddLibraryUseCase {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final AddBookUseCase addBookUseCase;
    private final ReadingStatusRepository readingStatusRepository;
    private final FormatRepository formatRepository;
    private final BookMapper bookMapper;

    private final AwardBadgeUseCase awardBadgeUseCase;

    public UserLibrary execute(User user, AddLibraryEntryRequest request) {
        Book book = (request.bookId() != null)
                ? bookRepository.findById(request.bookId()).orElseThrow()
                : addBookUseCase.execute(bookMapper.toDomain(request.newBook()));

        UserLibrary entry = UserLibrary.builder()
                .user(user)
                .book(book)
                .readingStatus(readingStatusRepository.findById(request.readingStatusId()).orElseThrow())
                .format(formatRepository.findById(request.formatId()).orElseThrow())
                .currentChapter(request.currentChapter() != null ? request.currentChapter() : 0)
                .currentPage(request.currentPage() != null ? request.currentPage() : 0)
                .isFavorite(request.isFavorite())
                .build();

        long bookCount = libraryRepository.countByUserId(user.getId());
        if (bookCount == 3) {
            awardBadgeUseCase.execute(user.getId(), 4L);
        }

        return libraryRepository.save(entry);
    }
}