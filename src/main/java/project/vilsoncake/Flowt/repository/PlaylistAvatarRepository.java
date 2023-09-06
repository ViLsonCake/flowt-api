package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.PlaylistAvatarEntity;
import project.vilsoncake.Flowt.entity.PlaylistEntity;

public interface PlaylistAvatarRepository extends CrudRepository<PlaylistAvatarEntity, Long> {
    boolean existsByFilename(String filename);

    boolean existsByPlaylist(PlaylistEntity entity);

    PlaylistAvatarEntity findByPlaylist(PlaylistEntity entity);
}
