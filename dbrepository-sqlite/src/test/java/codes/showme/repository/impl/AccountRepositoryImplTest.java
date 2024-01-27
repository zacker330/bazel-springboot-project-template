package codes.showme.repository.impl;

import codes.showme.domain.Account;
import codes.showme.domain.Category;
import io.ebean.Database;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.UUID;

public class AccountRepositoryImplTest extends AbstractIntegrationTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        // important!!!  this step is import, because it new a database instance for the dbserver provider
        // that DbMigration have to gather.
        Database DATABASE = createDatabase("", Arrays.asList("codes.showme.domain"));
        Mockito.when(instanceProvider.getInstance(Database.class)).thenReturn(DATABASE);
    }

    @Test
    public void testSave() {
        Category category = new Category();
        category.setName("caz");
        new CategoryRepositoryImpl().save(category);
        Assert.assertNotNull(category.getId());


        Account account = new Account();

        account.setName("name");
        account.setCategory(category);
        new AccountRepositoryImpl().save(account);
        Assert.assertNotNull(account.getId());

    }
}
