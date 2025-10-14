package shared.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryUtil {

    public static List<String> getAllCountries() {
        Locale.setDefault(new Locale("ES"));

        List<String> countries = new ArrayList<>();
        String[] isoCountries = Locale.getISOCountries();
        for (String countryCode : isoCountries) {
            Locale locale = new Locale("", countryCode); // Create a Locale with no language, only country
            String countryName = locale.getDisplayCountry(); // Get the display name in the default locale
            countries.add(countryName);
        }

        return countries;
    }
}
