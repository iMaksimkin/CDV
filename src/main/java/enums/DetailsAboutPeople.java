package enums;

public enum DetailsAboutPeople {
    EMAIL("email"),
    BIRTHDAY("birthday"),
    POSITION("position"),
    LOCATION("location"),
    DEPARTMENT("department"),
    FIRST_NAME("firstName"),
    SECOND_NAME("secondName"),
    START_DATE_AT_COMPANY("startDate");

    private String name;

    DetailsAboutPeople(String name) {
        this.name = name;
    }

    private String get() {
        return this.name;
    }

}
