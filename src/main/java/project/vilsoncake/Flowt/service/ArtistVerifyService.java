package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.ArtistVerifyRequestDto;
import project.vilsoncake.Flowt.dto.ArtistVerifyPageDto;

import java.io.IOException;
import java.util.Map;

public interface ArtistVerifyService {
    Map<String, String> saveNewArtistVerifyRequest(String authHeader, ArtistVerifyRequestDto artistVerifyRequestDto);
    Map<String, String> verifyArtistByUsername(String username) throws IOException;
    Map<String, String> cancelVerifyRequestByUsername(String username);
    ArtistVerifyPageDto getArtistVerifyRequestsByLatestDate(int page, int size);
}
