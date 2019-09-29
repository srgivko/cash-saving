package by.sivko.cashsaving;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Ignore;
import org.springframework.core.io.Resource;

import java.sql.Timestamp;

@Ignore
public class DefaultDataSetLoader extends AbstractDataSetLoader {

    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        IDataSet ds = flatXmlDataSetBuilder.build(resource.getInputStream());
        ds = new ReplacementDataSet(ds);
        ((ReplacementDataSet) ds)
                .addReplacementObject("[create_timestamp]", new Timestamp(System.currentTimeMillis()));
        return ds;
    }
}
