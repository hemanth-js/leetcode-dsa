class Solution {

    int pos;

    public List<String> braceExpansionII(String expression) {

        pos = 0;

        Set<String> set = parse(expression);

        return new ArrayList<>(set);
    }

    // Handles union: a,b,c
    Set<String> parse(String s) {

        Set<String> result = new TreeSet<>();

        while (pos < s.length() && s.charAt(pos) != '}') {

            Set<String> part = parseTerm(s);

            result.addAll(part);

            // comma means another choice
            if (pos < s.length() && s.charAt(pos) == ',') {
                pos++;
            }
        }

        return result;
    }

    // Handles concatenation: ab{c,d}
    Set<String> parseTerm(String s) {

        Set<String> result = new TreeSet<>();
        result.add("");

        while (pos < s.length()
                && s.charAt(pos) != ','
                && s.charAt(pos) != '}') {

            Set<String> part = parseFactor(s);

            Set<String> next = new TreeSet<>();

            for (String a : result) {
                for (String b : part) {
                    next.add(a + b);
                }
            }

            result = next;
        }

        return result;
    }

    // Handles a single letter or {...}
    Set<String> parseFactor(String s) {

        Set<String> result;

        if (s.charAt(pos) == '{') {

            pos++; // skip {

            result = parse(s);

            pos++; // skip }

        } else {

            result = new TreeSet<>();

            result.add(String.valueOf(s.charAt(pos)));

            pos++;
        }

        return result;
    }
}