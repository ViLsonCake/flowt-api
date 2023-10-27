package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.vilsoncake.Flowt.entity.ProfileHeaderEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.ProfileHeaderRepository;
import project.vilsoncake.Flowt.service.ProfileHeaderService;

@Service
@RequiredArgsConstructor
public class ProfileHeaderServiceImpl implements ProfileHeaderService {

    private final ProfileHeaderRepository profileHeaderRepository;

    @Override
    public boolean removeByUser(UserEntity user) {
        ProfileHeaderEntity profileHeader = profileHeaderRepository.getByUser(user);
        profileHeaderRepository.delete(profileHeader);
        return true;
    }
}
