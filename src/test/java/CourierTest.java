import org.example.CourierInfoEntity;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourierTest extends AbstractTest{
    @Test
    void getCourierCount() throws SQLException {
        // given
        String sql = "SELECT * FROM courier_info";
        Statement statement = getConnection().createStatement();
        int count = 0;
        //when
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            count++;
        }
        //then
        Assertions.assertEquals(4, count);
    }
    @Test
    void getCourierCountORM(){
        //given
        //when
        final Query query = getSession().createSQLQuery("SELECT * FROM courier_info")
                .addEntity(CourierInfoEntity.class);
        //then
        Assertions.assertEquals(4, query.getResultList().size());
    }
    @ParameterizedTest
    @CsvSource({"1, John", "2, Kate", "3, Bob"})
    void testName(int id, String name) throws SQLException {
        // given
        String sql = "Select * from courier_info where courier_id=" + id;
        Statement statement = getConnection().createStatement();
        String nameResult = "";
        // when
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            nameResult = rs.getString(2);
        }
        // then
        Assertions.assertEquals(name, nameResult);
    }
}
