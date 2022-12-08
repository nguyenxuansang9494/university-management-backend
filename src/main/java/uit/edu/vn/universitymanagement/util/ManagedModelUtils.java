package uit.edu.vn.universitymanagement.util;

import uit.edu.vn.universitymanagement.model.ManagedModel;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ManagedModelUtils {

    private ManagedModelUtils(){
        super();
    }

    public static <T extends ManagedModel>List<Long> convertToLongList(Collection<T> tCollection) {
        return tCollection.stream()
                .map(ManagedModel::getId)
                .collect(Collectors.toList());
    }
}
