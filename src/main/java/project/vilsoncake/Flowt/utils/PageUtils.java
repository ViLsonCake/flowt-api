package project.vilsoncake.Flowt.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.entity.SongEntity;

import java.util.List;

@Component
public class PageUtils {

    public Page<SongEntity> convertToPage(List<SongEntity> entities, Pageable pageable) {
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), entities.size());

        return new PageImpl<>(entities.subList(start, end), pageable, entities.size());
    }
}
