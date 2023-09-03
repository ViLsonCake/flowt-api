package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.AudioFileEntity;
import project.vilsoncake.Flowt.entity.SongEntity;

public interface AudioFileRepository extends CrudRepository<AudioFileEntity, Long> {
    AudioFileEntity findBySong(SongEntity song);
    Boolean existsBySong(SongEntity song);
}
