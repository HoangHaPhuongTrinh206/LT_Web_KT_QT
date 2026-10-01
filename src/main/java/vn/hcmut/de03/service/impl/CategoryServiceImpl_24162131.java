package vn.hcmut.de03.service.impl;

import java.util.List;

import vn.hcmut.de03.dao.CategoryDao_24162131;
import vn.hcmut.de03.dao.impl.CategoryDaoImpl_24162131;
import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.service.CategoryService_24162131;

public class CategoryServiceImpl_24162131 implements CategoryService_24162131 {
    private final CategoryDao_24162131 categoryDao = new CategoryDaoImpl_24162131();

    @Override
    public List<Category_24162131> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category_24162131> findActive() {
        return categoryDao.findActive();
    }
}
