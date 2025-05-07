package bookstore.dto.book;

import bookstore.validation.Path;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank
    @Size(max = 25, message = "Title must be up to 25 characters")
    private String title;

    @NotBlank
    @Size(max = 25, message = "Author name must be up to 25 characters")
    private String author;

    @NotBlank
    @Pattern(regexp = "^(978|979)[0-9]{10}$", message = "Invalid ISBN format")
    private String isbn;

    @NotNull
    @Positive
    private BigDecimal price;

    private String description;

    @Path
    private String coverImage;

    @NotEmpty
    private Set<Long> categoryIds;
}
