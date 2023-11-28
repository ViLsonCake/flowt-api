package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.entity.ListenedEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.Genre;
import project.vilsoncake.Flowt.repository.ListenedRepository;
import project.vilsoncake.Flowt.service.ListenedService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListenedServiceImpl implements ListenedService {

    private final ListenedRepository listenedRepository;

    @Override
    public int getUserListensCount(UserEntity user) {
        return listenedRepository.getListensCount(user.getListenedStatistic());
    }

    @Override
    public boolean existsBySongs(SongEntity song) {
        return listenedRepository.existsByAuthorAndSongName(song.getUser().getUsername(), song.getName());
    }

    @Override
    public List<String> getMostListenedArtists(UserEntity user) {
        return listenedRepository.getMostListenedArtists(user.getListenedStatistic())
                .stream().map(ListenedEntity::getAuthor).toList();
    }

    @Override
    public List<Genre> getMostListenedGenres(UserEntity user) {
        return listenedRepository.getMostListenedGenres(user.getListenedStatistic())
                .stream().map(ListenedEntity::getGenre).toList();
    }
}
