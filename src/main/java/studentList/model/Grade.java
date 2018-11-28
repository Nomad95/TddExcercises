package studentList.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Grade {
    TWO(2f),
    THREE(3f),
    THREE_PLUS(3.5f),
    FOUR(4f),
    FOUR_PLUS(4.5f),
    FIVE(5f);

    @Getter
    private final float value;
}
