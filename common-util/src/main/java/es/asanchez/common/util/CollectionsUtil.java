package es.asanchez.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionsUtil {

    private static CollectionsUtil INSTANCE;

    public static CollectionsUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CollectionsUtil();
        }
        return INSTANCE;
    }

    public <T> List<T> getListFromIterable(Iterable<T> iterable){
        List<T> result = new ArrayList<T>();
        iterable.forEach(result::add);
        return result;
    }
}
