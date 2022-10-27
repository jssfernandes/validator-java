package br.com.jssfernandes.validator.validators.strategies;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ValidationStrategies {

    private ValidationStrategies() {}

    protected static final String DEFAULT_NOT_EMPTY_KEY = "notEmpty";
    protected static final String DEFAULT_NOT_NULL_KEY = "notNull";
    protected static final String DEFAULT_NOT_EMPTY_NOR_NULL_KEY = "notEmptyNorNull";
    protected static final String DEFAULT_LESS_THAN_KEY = "lessThan";
    protected static final String DEFAULT_LESS_OR_EQUAL_THAN_KEY = "lessOrEqualThan";
    protected static final String DEFAULT_BIGGER_THAN_KEY = "biggerThan";
    protected static final String DEFAULT_BIGGER_OR_EQUAL_THAN_KEY = "biggerOrEqualThan";
    protected static final String DEFAULT_MATCHES_KEY = "matches";

    public static SimpleValidatorStrategy<Integer> matches(final Integer matchingInteger) {
        return new SimpleValidatorStrategy<Integer>(DEFAULT_MATCHES_KEY, matchingInteger) {
            @Override
            public boolean shouldAddError(Integer integer) {
                return integer == null || !integer.equals(matchingInteger);
            }
        };
    }

    public static SimpleValidatorStrategy<String> matches(final String matchingString) {
        return new SimpleValidatorStrategy<String>(DEFAULT_MATCHES_KEY, matchingString) {
            @Override
            public boolean shouldAddError(String string) {
                return string == null || !string.equals(matchingString);
            }
        };
    }

    public static SimpleValidatorStrategy<Boolean> matches(final Boolean matchingBoolean) {
        return new SimpleValidatorStrategy<Boolean>(DEFAULT_MATCHES_KEY, matchingBoolean) {
            @Override
            public boolean shouldAddError(Boolean bool) {
                return !Objects.equals(bool, matchingBoolean);
            }
        };
    }

    public static SimpleValidatorStrategy<List> notEmpty() {

        return new SimpleValidatorStrategy<List>(DEFAULT_NOT_EMPTY_KEY) {
            @Override
            public boolean shouldAddError(List list) {
                return list == null || list.isEmpty();
            }
        };

    }

    public static SimpleValidatorStrategy<BigDecimal> biggerOrEqualThan(final BigDecimal comparer) {

        return new SimpleValidatorStrategy<BigDecimal>(DEFAULT_BIGGER_OR_EQUAL_THAN_KEY) {

            @Override
            public boolean shouldAddError(BigDecimal value) {
                return value == null || value.compareTo(comparer) >= 0;
            }
        };

    }

    public static SimpleValidatorStrategy<BigDecimal> biggerThan(final BigDecimal comparer) {
        return new SimpleValidatorStrategy<BigDecimal>(DEFAULT_BIGGER_THAN_KEY) {

            @Override
            public boolean shouldAddError(BigDecimal value) {
                return value == null || value.compareTo(comparer) > 0;
            }
        };
    }

    public static SimpleValidatorStrategy<Integer> biggerThan(final Integer comparer) {
        return new SimpleValidatorStrategy<Integer>(DEFAULT_BIGGER_THAN_KEY) {

            @Override
            public boolean shouldAddError(Integer value) {
                return value == null || value.compareTo(comparer) > 0;
            }
        };
    }

    public static SimpleValidatorStrategy lessThan(final BigDecimal comparer) {
        return new SimpleValidatorStrategy<BigDecimal>(DEFAULT_LESS_THAN_KEY) {

            @Override
            public boolean shouldAddError(BigDecimal value) {
                return value == null || value.compareTo(comparer) < 0;
            }
        };
    }

    public static SimpleValidatorStrategy lessThan(final Integer comparer) {
        return new SimpleValidatorStrategy<Integer>(DEFAULT_LESS_THAN_KEY) {

            @Override
            public boolean shouldAddError(Integer value) {
                return value == null || value.compareTo(comparer) < 0;
            }
        };
    }

    public static SimpleValidatorStrategy lessOrEqualThan(final BigDecimal comparer) {
        return new SimpleValidatorStrategy<BigDecimal>(DEFAULT_LESS_OR_EQUAL_THAN_KEY) {

            @Override
            public boolean shouldAddError(BigDecimal value) {
                return value == null || value.compareTo(comparer) <= 0;
            }
        };
    }

    public static SimpleValidatorStrategy lessOrEqualThan(final Integer comparer) {
        return new SimpleValidatorStrategy<Integer>(DEFAULT_LESS_OR_EQUAL_THAN_KEY) {

            @Override
            public boolean shouldAddError(Integer value) {
                return value == null || value.compareTo(comparer) <= 0;
            }
        };
    }

    public static SimpleValidatorStrategy notNull() {
        return new SimpleValidatorStrategy<Object>(DEFAULT_NOT_NULL_KEY) {

            @Override
            public boolean shouldAddError(Object value) {
                return value == null;
            }
        };
    }

    public static SimpleValidatorStrategy mustIsNull() {
        return new SimpleValidatorStrategy<Object>(DEFAULT_NOT_NULL_KEY) {

            @Override
            public boolean shouldAddError(Object value) {
                return value != null;
            }
        };
    }

    public static SimpleValidatorStrategy notNullOrEmpty() {
        return new SimpleValidatorStrategy<String>(DEFAULT_NOT_EMPTY_NOR_NULL_KEY) {

            @Override
            public boolean shouldAddError(String value) {
                return value == null || value.isEmpty();
            }
        };
    }

    public static SimpleValidatorStrategy<Boolean> trueOrFalse(final boolean comparer) {
        return new SimpleValidatorStrategy<Boolean>(DEFAULT_MATCHES_KEY, comparer) {

            @Override
            public boolean shouldAddError(Boolean value) {
                return value == null || value != comparer;
            }
        };
    }
}
