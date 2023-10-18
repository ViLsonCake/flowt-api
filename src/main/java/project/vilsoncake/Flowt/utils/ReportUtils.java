package project.vilsoncake.Flowt.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.enumerated.ReportContentType;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;
import project.vilsoncake.Flowt.exception.IncorrectReportException;

import static project.vilsoncake.Flowt.constant.MessageConst.*;
import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.*;
import static project.vilsoncake.Flowt.entity.enumerated.WhomReportType.*;

@Component
public class ReportUtils {

    public String createReportMessage(ReportEntity report) {
        WhomReportType whomType = report.getWhomType();
        ReportContentType contentType = report.getContentType();

        if ((!whomType.equals(USER) && (contentType.equals(DESCRIPTION) || contentType.equals(PROFILE_HEADER))) ||
                (!whomType.equals(SONG) && contentType.equals(CONTENT))) {
            throw new IncorrectReportException("Incorrect report data");
        }

        if (whomType.equals(USER)) {
            if (contentType.equals(NAME)) {
                return USER_NAME_WARNING_MESSAGE;
            } else if (contentType.equals(AVATAR)) {
                return USER_AVATAR_WARNING_MESSAGE;
            } else if (contentType.equals(PROFILE_HEADER)) {
                return USER_PROFILE_HEADER_MESSAGE;
            } else if (contentType.equals(DESCRIPTION)) {
                return USER_DESCRIPTION_WARNING_MESSAGE;
            }
        } else if (whomType.equals(SONG)) {
            if (contentType.equals(NAME)) {
                return String.format(SONG_NAME_WARNING_MESSAGE, report.getContentTypeName());
            } else if (contentType.equals(AVATAR)) {
                return String.format(SONG_AVATAR_WARNING_MESSAGE, report.getContentTypeName());
            } else if (contentType.equals(CONTENT)) {
                return String.format(SONG_CONTENT_WARNING_MESSAGE, report.getContentTypeName());
            }
        } else if (whomType.equals(PLAYLIST)) {
            if (contentType.equals(NAME)) {
                return String.format(PLAYLIST_NAME_WARNING_MESSAGE, report.getContentTypeName());
            } else if (contentType.equals(AVATAR)) {
                return String.format(PLAYLIST_AVATAR_WARNING_MESSAGE, report.getContentTypeName());
            }
        }

        throw new IncorrectReportException("Incorrect report data");
    }
}
