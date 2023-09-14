package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.dto.UserDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.service.SearchService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    @Override
    public List<SongEntity> getSongsBySubstring(SubstringDto substringDto) {
        return null;
    }

    @Override
    public List<PlaylistEntity> getPlaylistsBySubstring(SubstringDto substringDto) {
        return null;
    }

    @Override
    public List<UserDto> getUsersBySubstring(SubstringDto substringDto) {
        return null;
    }
}
