package day8;

import static com.google.common.collect.Lists.*;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Decoder {
  public static int decode(SegmentPattern pattern, List<SevenSegmentNumber> numbers) {
    List<String> signals = Stream.of(pattern.getPattern().split(" ")).collect(Collectors.toList());
    String one = findAndSort(signals, newArrayList(s -> s.length() == 2));
    String seven = findAndSort(signals, newArrayList(s -> s.length() == 3));
    String four = findAndSort(signals, newArrayList(s -> s.length() == 4));
    String eight = findAndSort(signals, newArrayList(s -> s.length() == 7));
    Predicate<String> containsOnlyOnePartOfRightBar =
        s -> s.contains(one.substring(0, 1)) ^ s.contains(one.substring(1, 2));
    Predicate<String> containsRightBar =
        s -> s.contains(one.substring(0, 1)) && s.contains(one.substring(1, 2));

    String six =
        findAndSort(signals, newArrayList(s -> s.length() == 6, containsOnlyOnePartOfRightBar));
    String five =
        findAndSort(
            signals, newArrayList(s -> s.length() == 5, s -> difference(six, s).length() == 1));

    String three = findAndSort(signals, newArrayList(s -> s.length() == 5, containsRightBar));

    String leftBar = difference(eight, three);
    Predicate<String> containsLeftBar =
        s -> s.contains(leftBar.substring(0, 1)) && s.contains(leftBar.substring(1, 2));
    Predicate<String> containsOnlyOnePartOfLeftBar =
        s -> s.contains(leftBar.substring(0, 1)) ^ s.contains(leftBar.substring(1, 2));

    String zero =
        findAndSort(signals, newArrayList(s -> s.length() == 6, containsLeftBar, containsRightBar));

    String nine =
        findAndSort(signals, newArrayList(s -> s.length() == 6, containsOnlyOnePartOfLeftBar));

    String two =
        findAndSort(
            signals,
            newArrayList(
                s -> s.length() == 5,
                containsOnlyOnePartOfLeftBar,
                containsOnlyOnePartOfRightBar,
                s -> !sort(s).equalsIgnoreCase(sort(five))));

    Map<String, String> sortedEncoding =
        ImmutableMap.of(
            zero, "0",
            one, "1",
            two, "2",
            three, "3",
            four, "4",
            five, "5",
            six, "6",
            seven, "7",
            eight, "8",
            nine, "9");

    return Integer.parseInt(
        numbers.stream()
            .map(SevenSegmentNumber::getInput)
            .map(sortedEncoding::get)
            .collect(Collectors.joining("")));
  }

  private static String difference(String a, String b) {
    return String.join("", Sets.difference(asSet(a), asSet(b)));
  }

  private static Set<String> asSet(String a) {
    return Stream.of(a.split("")).collect(Collectors.toSet());
  }

  private static String sort(String s) {
    char[] tempArray = s.toCharArray();
    Arrays.sort(tempArray);
    return new String(tempArray);
  }

  private static String findAndSort(List<String> signals, List<Predicate<String>> predicates) {
    return sort(
        signals.stream().filter(s -> predicates.stream().allMatch(p -> p.test(s))).findAny().get());
  }
}
