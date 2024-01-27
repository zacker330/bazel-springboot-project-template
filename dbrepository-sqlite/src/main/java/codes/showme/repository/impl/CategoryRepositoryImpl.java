package codes.showme.repository.impl;

import codes.showme.domain.Category;
import codes.showme.domain.CategoryRepository;
import codes.showme.techlib.ioc.InstanceFactory;
import io.ebean.Database;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public void save(Category category) {
        Database instance = InstanceFactory.getInstance(Database.class);
        instance.save(category);
    }
}
