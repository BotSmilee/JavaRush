package NumberSystemConverter;

public class Number {
    private NumberSystem numberSystem;
    private String digit;

    public Number(NumberSystem numberSystem, String digit) {
        int numberSystemIntValue = numberSystem.getNumberSystemIntValue();
        if (numberSystemIntValue == 10) {
            if (!digit.matches("^\\d+$")) throw new NumberFormatException(); //Цифры десятичной системы счисления
        } else if (numberSystemIntValue == 12) {
            if (!digit.matches("^[^\\sc-zA-Z]+$")) throw new NumberFormatException(); //Цифры двенадцатеричной системы счисления
        } else if (numberSystemIntValue == 16) {
            if (!digit.matches("^[^\\sg-zA-Z]+$")) throw new NumberFormatException(); //Цифры десятичной системы счисления
        } else if (numberSystemIntValue > 1 && numberSystemIntValue < 10)
            if (!digit.matches("^[0-" + (numberSystemIntValue-1) + "\\d]+$")) throw new NumberFormatException(); //Цифры других систем счисления
        this.numberSystem = numberSystem;
        this.digit = digit;
    }

    public NumberSystem getNumberSystem() {
        return numberSystem;
    }

    public String getDigit() {
        return digit;
    }

    @Override
    public String toString() {
        return "Number{" +
                "numberSystem=" + numberSystem +
                ", digit='" + digit + '\'' +
                '}';
    }
}
