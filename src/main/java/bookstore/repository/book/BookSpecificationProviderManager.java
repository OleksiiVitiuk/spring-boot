package bookstore.repository.book;

import bookstore.entity.Book;
import bookstore.exception.DataProcessingException;
import bookstore.repository.SpecificationProvider;
import bookstore.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new DataProcessingException(
                        "Can`t find correct specification provider for key: " + key));
    }
}
