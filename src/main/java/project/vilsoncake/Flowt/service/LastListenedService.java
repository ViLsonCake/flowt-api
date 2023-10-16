package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;

public interface LastListenedService {
    boolean addSongToLastListenedByUser(UserEntity user, SongEntity song);
    boolean createUserLastListenedEntity(UserEntity user);

    boolean isSongAlreadyInLastListened(SongEntity newSong, UserEntity user);
}
