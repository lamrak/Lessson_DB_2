package android.validcat.net.helloworld.interfaces;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oleksii on 12/5/15.
 */
public interface IDataManager<I> {
    long save(I i) throws IOException;
    boolean delete(I i);
    I get(int id);
    List<I> getAll();
}