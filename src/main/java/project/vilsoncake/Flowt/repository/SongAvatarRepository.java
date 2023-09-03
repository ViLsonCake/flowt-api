package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.SongAvatarEntity;
import project.vilsoncake.Flowt.entity.SongEntity;

public interface SongAvatarRepository extends CrudRepository<SongAvatarEntity, Long> {
    SongAvatarEntity findByFilename(String filename);
    SongAvatarEntity findBySong(SongEntity song);
    Boolean existsByFilename(String filename);
    Boolean existsBySong(SongEntity song);
}
