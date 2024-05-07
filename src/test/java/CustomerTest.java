import org.example.CustomersEntity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.example.MainHome.getSession;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerTest{
    @Order(1)
    @Test
    void addValue(){
        // given
        CustomersEntity entity = new CustomersEntity();
        entity.setCustomerId((short) 16);
        entity.setFirstName("Victor");
        entity.setLastName("Evans");
        entity.setPhoneNumber("+ 7 927 112 2333");
        entity.setDistrict("Западный");
        entity.setStreet("Бульвар Роз");
        entity.setHouse("11");
        entity.setApartment("100");
        // when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        final Query query = getSession()
                .createSQLQuery("Select * from customers where customer_id=" + 16)
                .addEntity(CustomersEntity.class);
        CustomersEntity customersEntity = (CustomersEntity) query.uniqueResult();
        // then
        Assertions.assertNotNull(customersEntity);
    }

    @Order(2)
    @Test
    void deleteTest(){
        //given
        final Query customerQuery = getSession()
                .createSQLQuery("Select * from customers where customer_id=" + 16)
                .addEntity(CustomersEntity.class);
        Optional<CustomersEntity> customerEntityBeforeDelete =
                customerQuery.uniqueResultOptional();
        Assumptions.assumeTrue(customerEntityBeforeDelete.isPresent());
        // when
        Session session = getSession();
        session.beginTransaction();
        session.delete(customerEntityBeforeDelete.get());
        session.getTransaction().commit();
        // then
        final Query customerQueryAfterDelete = getSession()
                .createSQLQuery("Select * from customers where customer_id=" + 16)
                .addEntity(CustomersEntity.class);
        Optional<CustomersEntity> customerEntityAfterDelete =
                customerQueryAfterDelete.uniqueResultOptional();

        Assertions.assertFalse(customerEntityAfterDelete.isPresent());
    }
}
