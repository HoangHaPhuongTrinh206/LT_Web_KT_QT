package vn.hcmut.de03.dao;

import java.util.List;

import vn.hcmut.de03.entity.Category_24162131;

public interface CategoryDao_24162131 {
    List<Category_24162131> findAll();

    /** Category có Status = true */
    List<Category_24162131> findActive();

    Category_24162131 findById(int categoryId);
}
