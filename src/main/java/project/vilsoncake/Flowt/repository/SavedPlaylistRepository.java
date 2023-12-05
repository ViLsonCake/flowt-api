package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.SavedPlaylistEntity;

public interface SavedPlaylistRepository extends CrudRepository<SavedPlaylistEntity, Long> {
}
