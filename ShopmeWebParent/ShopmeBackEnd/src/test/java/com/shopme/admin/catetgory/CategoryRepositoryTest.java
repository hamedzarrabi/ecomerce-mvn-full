package com.shopme.admin.catetgory;


import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category("Electronics");

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory() {

        Category parent = new Category(7);
//        Category cameras = new Category("Cameras", parent);
        Category subCategory = new Category("iPhone", parent);
//        categoryRepository.saveAll(List.of(cameras, smartphones));

        Category save = categoryRepository.save(subCategory);

        assertThat(save.getId()).isGreaterThan(0);


    }

    @Test
    public void testGetCategory() {
        Category category = categoryRepository.findById(2).get();
        System.out.println(category.getName());

        Set<Category> childern = category.getChildern();

        for (Category subCategory : childern) {
            System.out.println(subCategory.getName());
        }

        assertThat(childern.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories() {

        Iterable<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            if (category.getParent() == null) {
                System.out.println(category.getName());

                Set<Category> children = category.getChildern();

                for (Category subCategory : children) {
                    System.out.println("--" + subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }

    private void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildern();

        for (Category subCategory : children) {
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }

            System.out.println(subCategory.getName());
            printChildren(subCategory, newSubLevel);
        }
    }
}