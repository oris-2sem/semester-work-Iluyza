package ru.desinfection.site.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SortingUtils {

    @Value("${defaultSortField}")
    private String defaultSortField;

    @Value("${defaultIsSortedASC}")
    private Boolean defaultIsSortedASC;

    public Sort parseSortValue(Optional<String> sortValue) {
        if (sortValue.isEmpty()) {
            return getDefaultSort();
        }

        switch (sortValue.get()) {
            case ("nameASC") -> {
                return Sort.by("name");
            }
            case ("nameDESC") -> {
                return Sort.by("name").descending();
            }
            case ("costASC") -> {
                return Sort.by("cost");
            }
            case ("costDESC") -> {
                return Sort.by("cost").descending();
            }
            default -> {
                return getDefaultSort();
            }
        }
    }

    private Sort getDefaultSort() {
        if (defaultIsSortedASC) {
            return Sort.by(defaultSortField);
        }

        return Sort.by(defaultSortField).descending();
    }
}
