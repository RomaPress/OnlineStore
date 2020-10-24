package com.pres.util.sort;

import com.pres.constants.ErrorMessage;
import com.pres.constants.Sort;
import com.pres.model.Product;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used for performing sorting products.
 *
 * @author Pres Roman
 **/
public class ProductSort {
    public static final Logger LOG = Logger.getLogger(ProductSort.class);

    public static final int SORT_BY_ID = 1;

    public static final int SORT_BY_PRICE_ASC = 2;

    public static final int SORT_BY_PRICE_DESC = 3;

    public static final int SORT_BY_ACOUSTIC = 4;

    public static final int SORT_BY_BASS = 5;

    public static final int SORT_BY_ELECTRIC = 6;

    public static final int SORT_BY_CLASSIC = 7;

    public static final int SORT_BY_ELECTRO_ACOUSTIC = 8;


    public static List<Product> sort(List<Product> products, int sortBy) {
        switch (sortBy) {
            case SORT_BY_ID:
                return sortById(products);
            case SORT_BY_PRICE_ASC:
                return sortByPriceAsc(products);
            case SORT_BY_PRICE_DESC:
                return sortByPriceDesc(products);
            case SORT_BY_ACOUSTIC:
                return sortByAcousticGuitar(products);
            case SORT_BY_BASS:
                return sortByBassGuitar(products);
            case SORT_BY_ELECTRIC:
                return sortByElectricGuitar(products);
            case SORT_BY_CLASSIC:
                return sortByClassicalGuitar(products);
            case SORT_BY_ELECTRO_ACOUSTIC:
                return sortByElectroAcousticGuitar(products);
            default:
                LOG.error(ErrorMessage.ERR_INCORRECT_DATA);
                throw new IllegalArgumentException(ErrorMessage.ERR_INCORRECT_DATA);
        }
    }


    private static List<Product> sortById(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingInt(Product::getId))
                .collect(Collectors.toList());
    }

    private static List<Product> sortByPriceAsc(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    private static List<Product> sortByPriceDesc(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice)
                        .reversed())
                .collect(Collectors.toList());
    }

    private static List<Product> sortByAcousticGuitar(List<Product> products) {
        return products.stream()
                .filter(product -> product.getType().getName().equals(Sort.SORT_ACOUSTIC_GUITAR))
                .collect(Collectors.toList());
    }

    private static List<Product> sortByBassGuitar(List<Product> products) {
        return products.stream()
                .filter(product -> product.getType().getName().equals(Sort.BASS_GUITAR))
                .collect(Collectors.toList());
    }

    private static List<Product> sortByElectricGuitar(List<Product> products) {
        return products.stream()
                .filter(product -> product.getType().getName().equals(Sort.ELECTRIC_GUITAR))
                .collect(Collectors.toList());
    }

    private static List<Product> sortByClassicalGuitar(List<Product> products) {
        return products.stream()
                .filter(product -> product.getType().getName().equals(Sort.CLASSICAL_GUITAR))
                .collect(Collectors.toList());
    }

    private static List<Product> sortByElectroAcousticGuitar(List<Product> products) {
        return products.stream()
                .filter(product -> product.getType().getName().equals(Sort.ELECTRO_ACOUSTIC_GUITAR))
                .collect(Collectors.toList());
    }
}
