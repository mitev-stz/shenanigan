package de.moviemanager.ui.masterlist.categorizer;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.ToDoubleFunction;

import de.moviemanager.data.Nameable;
import de.moviemanager.ui.masterlist.elements.ContentElement;
import de.moviemanager.ui.masterlist.elements.DividerElement;
import de.moviemanager.ui.masterlist.elements.HeaderElement;
import de.util.StringUtils;

import static java.lang.String.format;
import static java.util.Comparator.comparing;

public class Watched<T extends Nameable> extends Categorizer<String, T> {
    private final Comparator<ContentElement<T>> contentComparator =
            comparing(ContentElement<T>::getMeta)
                    .thenComparing(ContentElement::getTitle, StringUtils::alphabeticalComparison);

    private final String numberName;
    private final ToDoubleFunction<T> getNumber;

    private final static String NOT_LOANED = "Not Loaned";

    public Watched(final String numberName, final ToDoubleFunction<T> getNumber) {
        this.numberName = numberName;
        this.getNumber = getNumber;
    }

    @Override
    public String getCategoryNameFor(final T obj) {
        long temp = (long)getNumber.applyAsDouble(obj);

        if (temp == 0){
            return NOT_LOANED;
        }

        Date date = new Date(temp);
        String pattern = "MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);

    }

    @Override
    public HeaderElement<T> createHeader(final String category) {
        return new HeaderElement<>(category);
    }

    @Override
    protected ContentElement<T> createContent(final T obj) {
        String name = obj.name();
        long number = (long)getNumber.applyAsDouble(obj);

        String temp = "";
        if (number == 0){
            temp = NOT_LOANED;
        }else{
            temp = (new Date(number)).toString();
        }

        return new ContentElement<>(name, numberName + ": " + temp);
    }

    @Override
    public DividerElement createDivider() {
        return new DividerElement();
    }

    @Override
    public int compareCategories(final String cat1, final String cat2) {
        int rank1 = covertCategorieToInteger(cat1);
        int rank2 = covertCategorieToInteger(cat2);
        return Integer.compare(rank1, rank2);
    }

    private int covertCategorieToInteger(final String s){
        if (s == NOT_LOANED){
            return 0;
        }else{
            int temp1 = Integer.parseInt(s.substring(0,2));
            int temp2 = Integer.parseInt(s.substring(3,7));
            return temp1 + (temp2*12);
        }
    }


    @Override
    public int compareContent(final ContentElement<T> element1, final ContentElement<T> element2) {
        return contentComparator.compare(element1, element2);
    }
}

