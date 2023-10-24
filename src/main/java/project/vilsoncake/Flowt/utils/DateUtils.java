package project.vilsoncake.Flowt.utils;

import org.springframework.stereotype.Component;

import java.util.Date;

import static project.vilsoncake.Flowt.constant.NumberConst.WAITING_PERIOD_IN_DAYS;

@Component
public class DateUtils {

    public boolean isWaitingPeriodExpired(Date reportCreationDate) {
        Date currentDate = new Date();
        long differenceInMillis = currentDate.getTime() - reportCreationDate.getTime();
        return differenceInMillis >= getWaitingPeriodInDaysToMillis();
    }

    private long getWaitingPeriodInDaysToMillis() {
        return WAITING_PERIOD_IN_DAYS * 24 * 60 * 60 * 1000;
    }
}
