package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.ArtistVerifyDto;
import project.vilsoncake.Flowt.dto.ArtistVerifyPageDto;

import java.util.Map;

public interface ArtistVerifyService {
    Map<String, String> saveNewArtistVerifyRequest(String authHeader, ArtistVerifyDto artistVerifyDto);
    Map<String, String> verifyArtistByUsername(String username);
    Map<String, String> cancelVerifyRequestByUsername(String username);
    ArtistVerifyPageDto getArtistVerifyRequestsByLatestDate(int page, int size);
}
