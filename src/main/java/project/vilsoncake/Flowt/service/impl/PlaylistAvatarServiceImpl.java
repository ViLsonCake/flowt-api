package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.PlaylistAvatarEntity;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.repository.PlaylistAvatarRepository;
import project.vilsoncake.Flowt.service.AvatarService;

@Service("playlistAvatarService")
@Slf4j
@RequiredArgsConstructor
public class PlaylistAvatarServiceImpl implements AvatarService {

    private final PlaylistAvatarRepository playlistAvatarRepository;

    @Override
    public boolean deleteAvatar(Object entity) {
        PlaylistAvatarEntity playlistAvatar = playlistAvatarRepository.findByPlaylist((PlaylistEntity) entity);
        playlistAvatarRepository.delete(playlistAvatar);
        return true;
    }
}
