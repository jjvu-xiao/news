//package code;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.pgl.xiao.admin.dao.CatalogMapper;
//import com.pgl.xiao.admin.domain.Catalog;
//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LoggerFactory;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.List;
//
//public class TestDao {
//
//
//    @Test
//    public void dao() {
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-datasource.xml");
//        CatalogMapper mapper = (CatalogMapper) context.getBean("catalogMapper");
//        PageHelper.startPage(1, 5);
//        List<Catalog> catalogs = mapper.selectByExample(null);
//        PageInfo<Catalog> datas = new PageInfo<>(catalogs);
//    }
//}
