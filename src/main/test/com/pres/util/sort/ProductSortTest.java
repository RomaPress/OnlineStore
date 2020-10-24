package com.pres.util.sort;

import com.pres.model.Product;
import com.pres.model.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductSortTest {
    private List<Product> list;

    @Before
    public void init() {
        list = new ArrayList<>();
        list.add(new Product.Builder().setId(1).setPrice(1).setType(new Type.Builder().setName("Acoustic guitar").build()).build());
        list.add(new Product.Builder().setId(2).setPrice(2).setType(new Type.Builder().setName("Bass-guitar").build()).build());
        list.add(new Product.Builder().setId(3).setPrice(3).setType(new Type.Builder().setName("Classical guitar").build()).build());
        list.add(new Product.Builder().setId(4).setPrice(4).setType(new Type.Builder().setName("Electric guitar").build()).build());
        list.add(new Product.Builder().setId(5).setPrice(5).setType(new Type.Builder().setName("Electro-acoustic guitar").build()).build());
    }

    @Test
    public void sortById() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_ID);
        Assert.assertEquals(1, sortList.get(0).getId());
    }

    @Test
    public void sortByPriceASC() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_PRICE_ASC);
        Assert.assertEquals(1, sortList.get(0).getId());
    }

    @Test
    public void sortByPriceDESC() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_PRICE_DESC);
        Assert.assertEquals(5, sortList.get(0).getId());
    }

    @Test
    public void sortByAcoustic() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_ACOUSTIC);
        Assert.assertEquals("Acoustic guitar", sortList.get(0).getType().getName());
    }

    @Test
    public void sortByBass() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_BASS);
        Assert.assertEquals("Bass-guitar", sortList.get(0).getType().getName());
    }

    @Test
    public void sortByElectric() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_ELECTRIC);
        Assert.assertEquals("Electric guitar", sortList.get(0).getType().getName());
    }

    @Test
    public void sortByClassical() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_CLASSIC);
        Assert.assertEquals("Classical guitar", sortList.get(0).getType().getName());
    }

    @Test
    public void sortByElectroAcoustic() {
        List<Product> sortList = ProductSort.sort(list, ProductSort.SORT_BY_ELECTRO_ACOUSTIC);
        Assert.assertEquals("Electro-acoustic guitar", sortList.get(0).getType().getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        List<Product> sortList = ProductSort.sort(list, 100);
    }
}