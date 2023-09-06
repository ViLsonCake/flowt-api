package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.PlaylistAvatarEntity;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.repository.PlaylistAvatarRepository;
import project.vilsoncake.Flowt.service.AvatarService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistAvatarServiceImpl implements AvatarService {

    private final PlaylistAvatarRepository playlistAvatarRepository;

    @Override
    public boolean saveAvatar(MultipartFile avatar, String uuid, Object playlist) {
        PlaylistAvatarEntity playlistAvatar = new PlaylistAvatarEntity(uuid, String.valueOf(avatar.getSize()), (PlaylistEntity) playlist);
        playlistAvatarRepository.save(playlistAvatar);
        return true;
    }

    @Override
    public boolean existsByFilename(String filename) {
        return playlistAvatarRepository.existsByFilename(filename);
    }

    @Override
    public boolean existsByEntity(Object entity) {
        return playlistAvatarRepository.existsByPlaylist((PlaylistEntity) entity);
    }

    @Override
    public PlaylistAvatarEntity getByEntity(Object entity) {
        return playlistAvatarRepository.findByPlaylist((PlaylistEntity) entity);
    }
}
