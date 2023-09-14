package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.SubstringDto;
import project.vilsoncake.Flowt.dto.UserDto;
import project.vilsoncake.Flowt.entity.PlaylistEntity;
import project.vilsoncake.Flowt.entity.SongEntity;

import java.util.List;

public interface SearchService {
    List<SongEntity> getSongsBySubstring(SubstringDto substringDto);
    List<PlaylistEntity> getPlaylistsBySubstring(SubstringDto substringDto);
    List<UserDto> getUsersBySubstring(SubstringDto substringDto);
}
