package avto;

public abstract class CarImp implements Car {
    private String nameFabricator;
    private String mark;
    private int yearOfIssue;

    public CarImp(String nameFabricator, String mark, int yearOfIssue) {
        this.nameFabricator = nameFabricator;
        this.mark = mark;
        this.yearOfIssue = yearOfIssue;
    }

    public String getNameFabricator() {
        return nameFabricator;
    }

    public void setNameFabricator(String nameFabricator) {
        this.nameFabricator = nameFabricator;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    @Override
    public void admission() {
        System.out.println("Новый автомобиль от " + getNameFabricator());
    }

    @Override
    public String toString() {
        return "Авто: " + nameFabricator + " " + mark +
                " " + yearOfIssue + " год выпуска";
    }
}
