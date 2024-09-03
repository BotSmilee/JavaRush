package NumberSystemConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Solution {
    public static void main(String[] args) {
        Number number = new Number(NumberSystemType._10, "6");
        Number result = convertNumberToOtherNumberSystem(number, NumberSystemType._2);
        System.out.println(result);    //expected 110

        number = new Number(NumberSystemType._10, "6");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._4);
        System.out.println(result);    //expected 12

        number = new Number(NumberSystemType._12, "a12b");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._10);
        System.out.println(result);    //expected 17459

        number = new Number(NumberSystemType._12, "a12b");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._8);
        System.out.println(result);    //expected 42063

        number = new Number(NumberSystemType._16, "6df");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._8);
        System.out.println(result);    //expected 3337

        number = new Number(NumberSystemType._16, "abcdefabcdef");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._16);
        System.out.println(result);    //expected abcdefabcdef

        number = new Number(NumberSystemType._2, "110");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._3);
        System.out.println(result);    //expected 20

        number = new Number(NumberSystemType._3, "22");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._8);
        System.out.println(result);    //expected 10

        number = new Number(NumberSystemType._8, "600");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._10);
        System.out.println(result);    //expected 384

        number = new Number(NumberSystemType._2, "1010101010");
        result = convertNumberToOtherNumberSystem(number, NumberSystemType._16);
        System.out.println(result);    //expected 2aa

    }

    public static Number getLesserSystems(BigDecimal digit, int system, StringBuilder count) {
        while (digit.compareTo(BigDecimal.valueOf(0)) > 0) {
            count.append(digit.remainder(BigDecimal.valueOf(system)).toString().split("\\.")[0]);
            digit = digit.divide(BigDecimal.valueOf(system), 0, RoundingMode.DOWN);
        }
        return new Number(NumberSystemType.valueOf("_" + system), count.reverse().toString());
    }

    public static Number getTwelfthfSystem(BigDecimal digit, StringBuilder count) {
        while (digit.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (digit.remainder(BigDecimal.valueOf(12)).compareTo(BigDecimal.valueOf(10)) == 0) count.append("a");
            else if (digit.remainder(BigDecimal.valueOf(12)).compareTo(BigDecimal.valueOf(11)) == 0) count.append("b");
            else count.append(digit.remainder(BigDecimal.valueOf(12)));
            digit = digit.divide(BigDecimal.valueOf(12), 0, RoundingMode.DOWN);
        }
        return new Number(NumberSystemType.valueOf("_" + 12), count.reverse().toString());
    }

    private static Number getSixteenthSystem(BigDecimal digit, StringBuilder count) {
        while (digit.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (digit.remainder(BigDecimal.valueOf(16)).compareTo(BigDecimal.valueOf(10)) == 0) count.append("a");
            else if (digit.remainder(BigDecimal.valueOf(16)).compareTo(BigDecimal.valueOf(11)) == 0) count.append("b");
            else if (digit.remainder(BigDecimal.valueOf(16)).compareTo(BigDecimal.valueOf(12)) == 0) count.append("c");
            else if (digit.remainder(BigDecimal.valueOf(16)).compareTo(BigDecimal.valueOf(13)) == 0) count.append("d");
            else if (digit.remainder(BigDecimal.valueOf(16)).compareTo(BigDecimal.valueOf(14)) == 0) count.append("e");
            else if (digit.remainder(BigDecimal.valueOf(16)).compareTo(BigDecimal.valueOf(15)) == 0) count.append("f");
            else count.append(digit.remainder(BigDecimal.valueOf(16)));
            digit = digit.divide(BigDecimal.valueOf(16), 0, RoundingMode.DOWN);
        }
        return new Number(NumberSystemType.valueOf("_" + 16), count.reverse().toString());
    }

    public static Number getSystem(int system, BigDecimal digit, StringBuilder count) {
        switch (system) {
            case (10):
                return new Number(NumberSystemType._10, digit.toString().split("\\.")[0]);
            case (2), (3), (4), (5), (6), (7), (8), (9):
                return getLesserSystems(digit, system, count);
            case (12):
                return getTwelfthfSystem(digit, count);
            case (16):
                return getSixteenthSystem(digit, count);
            default:
                throw new RuntimeException();
        }
    }

    public static Number convertNumberToOtherNumberSystem(Number number, NumberSystem expectedNumberSystem) {
        switch (number.getNumberSystem().getNumberSystemIntValue()) {
            case (10):
                BigDecimal digit = new BigDecimal(number.getDigit());
                StringBuilder count = new StringBuilder();
                int system = expectedNumberSystem.getNumberSystemIntValue();
                return getSystem(system, digit, count);
            case (12):
                String numberDigit = number.getDigit();
                digit = BigDecimal.valueOf(0);
                count = new StringBuilder();
                system = expectedNumberSystem.getNumberSystemIntValue();
                for (int i = 0; i < numberDigit.length(); i++) {
                    double pow = Math.pow(12, numberDigit.length() - i - 1);
                    switch (numberDigit.charAt(i)) {
                        case ('a'):
                            digit = digit.add(BigDecimal.valueOf(10 * pow));
                            break;
                        case ('b'):
                            digit = digit.add(BigDecimal.valueOf(11 * pow));
                            break;
                        default:
                            digit = digit.add(BigDecimal.valueOf(Integer.parseInt(numberDigit.substring(i, i + 1)) * pow));
                            break;
                    }
                }
                return getSystem(system, digit, count);
            case (16):
                String numberDigit16 = number.getDigit();
                digit = BigDecimal.valueOf(0);
                count = new StringBuilder();
                system = expectedNumberSystem.getNumberSystemIntValue();
                for (int i = 0; i < numberDigit16.length(); i++) {
                    double pow = Math.pow(16, numberDigit16.length() - i - 1);
                    switch (numberDigit16.charAt(i)) {
                        case ('a'):
                            digit = digit.add(BigDecimal.valueOf(10 * pow));
                            break;
                        case ('b'):
                            digit = digit.add(BigDecimal.valueOf(11 * pow));
                            break;
                        case ('c'):
                            digit = digit.add(BigDecimal.valueOf(12 * pow));
                            break;
                        case ('d'):
                            digit = digit.add(BigDecimal.valueOf(13 * pow));
                            break;
                        case ('e'):
                            digit = digit.add(BigDecimal.valueOf(14 * pow));
                            break;
                        case ('f'):
                            digit = digit.add(BigDecimal.valueOf(15 * pow));
                            break;
                        default:
                            digit = digit.add(BigDecimal.valueOf(Integer.parseInt(numberDigit16.substring(i, i + 1)) * pow));
                            break;
                    }
                }
                return getSystem(system, digit, count);
            case (2), (3), (4), (5), (6), (7), (8), (9):
                String numberDigitX = number.getDigit();
                digit = BigDecimal.valueOf(0);
                count = new StringBuilder();
                int expectedSystem = number.getNumberSystem().getNumberSystemIntValue();
                system = expectedNumberSystem.getNumberSystemIntValue();
                for (int i = 0; i < numberDigitX.length(); i++) {
                    double pow = Math.pow(expectedSystem, numberDigitX.length() - i - 1);
                    digit = digit.add(BigDecimal.valueOf(Integer.parseInt(numberDigitX.substring(i, i + 1)) * pow));
                }
                return getSystem(system, digit, count);
            default:
                return null;
        }
    }
}
