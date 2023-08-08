package IO;

import java.util.List;

public interface FileIO<E> {
    void write(List<E> es, String path);
    List<E> read(String path);
}
