package project.vilsoncake.Flowt.repository;

import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.UserLinkEntity;

public interface LinksRepository extends CrudRepository<UserLinkEntity, Long> {
}
