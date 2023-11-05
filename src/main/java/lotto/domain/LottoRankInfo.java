package lotto.domain;

import java.util.Arrays;

public enum LottoRankInfo {
    FIRST(6, 2_000_000_000),
    SECOND(5, 30_000_000),
    THIRD(5, 1_500_000),
    FOURTH(4, 50_000),
    FIFTH(3, 5_000),
    NO_WIN(0, 0);

    private final int matchingCount;
    private final int prizeMoney;

    LottoRankInfo(int matchingCount, int prizeMoney) {
        this.matchingCount = matchingCount;
        this.prizeMoney = prizeMoney;
    }

    public LottoRankInfo geLottoRankInfo(int matchingCount, boolean matchingBonus) {
        return Arrays.stream(LottoRankInfo.values())
                .filter(rankInfo -> rankInfo.findRankInfo(rankInfo, matchingCount, matchingBonus))
                .findAny()
                .orElse(NO_WIN);
    }

    private boolean findRankInfo(LottoRankInfo rankInfo, int matchingCount, boolean matchingBonus) {
        if (isSecondRank(rankInfo) && matchingBonus) {
            return isMatchCountEqual(matchingCount, rankInfo.matchingCount);
        }

        if (isThirdRank(rankInfo) && !matchingBonus) {
            return isMatchCountEqual(matchingCount, rankInfo.matchingCount);
        }

        if (!isSecondRank(rankInfo) && !isThirdRank(rankInfo)) {
            return isMatchCountEqual(matchingCount, rankInfo.matchingCount);
        }

        return false;
    }

    private boolean isSecondRank(LottoRankInfo rankInfo) {
        return rankInfo.equals(SECOND);
    }

    private boolean isThirdRank(LottoRankInfo rankInfo) {
        return rankInfo.equals(THIRD);
    }

    private boolean isMatchCountEqual(int matchingCount, int expectedMatchingCount) {
        return matchingCount == expectedMatchingCount;
    }
}
