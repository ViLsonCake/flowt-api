package project.vilsoncake.Flowt.constant;

import java.util.List;

public class PatternConst {
    public static final String REGEX_USERNAME_PATTERN = "^[a-zA-Z0-9 ]{3,32}$";
    public static final String REGEX_SONG_NAME_PATTERN = "^[a-zA-Z0-9 -]{3,32}$";
    public static final String REGEX_PLAYLIST_NAME_PATTERN = "^[a-zA-Z0-9 ]{3,32}$";
    public static final String REGEX_ISSUE_DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
    public static final String REGEX_GENRE_PATTERN = "^(Rock|Pop|Hip-Hop|Rap|Classical|Jazz|Electronic|Techno|Metal|Punk|Country|Blues|Reggae|Funk|Soul|Disco|Rhythm and Blues|Indie|Alternative|Latin|Club|Dubstep|Drum and Bass|Trance|Dub|Dabble Rock|Chanson|Rapcore)$";
    public static final String REGEX_REGION_PATTERN = "^(.+? |)(Afghanistan|Albania|Antarctica|Algeria|Andorra|Angola|Antigua and Barbuda|Argentina|Armenia|Australia|Austria|Azerbaijan|The Bahamas|Bahrain|Bangladesh|Barbados|Belarus|Belgium|Belize|Benin|Bhutan|Bolivia|Bosnia and Herzegovina|Botswana|Brazil|Brunei|Bulgaria|Burkina Faso|Burundi|Cabo Verde|Cambodia|Cameroon|Canada|Central African Republic|Chad|Chile|China|Colombia|Comoros|Congo, Democratic Republic|Congo|Costa Rica|Côte d’Ivoire|Croatia|Cuba|Cyprus|Czech Republic|Denmark|Djibouti|\uD83C\uDDE9\uD83C\uDDF2 Dominica|Dominican Republic|Earth|East Timor|Ecuador|Egypt|El Salvador|Equatorial Guinea|Eritrea|Estonia|Eswatini|Ethiopia|Fiji|Finland|France|Gabon|The Gambia|Georgia|Germany|Ghana|Greece|Grenada|Guatemala|Guinea|\uD83C\uDDEC\uD83C\uDDFC Guinea-Bissau|Guyana|Haiti|Honduras|Hungary|Iceland|India|Indonesia|Iran|Iraq|Ireland|Israel|Italy|Jamaica|Japan|Jordan|Kazakhstan|Kenya|Kiribati|North Korea|South Korea|Kosovo|Kuwait|Kyrgyzstan|Laos|Latvia|Lebanon|Lesotho|Liberia|Libya|Liechtenstein|Lithuania|Luxembourg|Madagascar|\uD83C\uDDF2\uD83C\uDDFC Malawi|Malaysia|Maldives|Mali|Malta|\uD83C\uDDF2\uD83C\uDDED Marshall Islands|Mauritania|Mauritius|Mexico|Micronesia|\uD83C\uDDF2\uD83C\uDDE9 Moldova|Monaco|\uD83C\uDDF2\uD83C\uDDF3 Mongolia|Montenegro|Morocco|Mozambique|\uD83C\uDDF2\uD83C\uDDF2 Myanmar|Namibia|Nauru|Nepal|Netherlands|New Zealand|Nicaragua|Niger|Nigeria|North Macedonia|Norway|\uD83C\uDDF4\uD83C\uDDF2 Oman|Pakistan|Palau|Panama|Papua New Guinea|Paraguay|Peru|Philippines|Poland|Portugal|Qatar|Romania|Russia|Rwanda|Saint Kitts and Nevis|Saint Lucia|Saint Vincent and the Grenadines|Samoa|San Marino|Sao Tome and Principe|Saudi Arabia|Senegal|Serbia|Seychelles|Sierra Leone|Singapore|Slovakia|Slovenia|Solomon Islands|Somalia|South Africa|Spain|Sri Lanka|Sudan|Sudan, South|Suriname|Sweden|Switzerland|Syria|Taiwan|Tajikistan|Tanzania|Thailand|Togo|Tonga|Trinidad and Tobago|Tunisia|Turkey|Turkmenistan|Tuvalu|Uganda|Ukraine|United Arab Emirates|United Kingdom|United States|Uruguay|Uzbekistan|Vanuatu|Vatican City|Venezuela|Vietnam|Yemen|Zambia|Zimbabwe)$";
    public static final String REGEX_ENUM_REGION_PATTERN = "^[^ ]* ";
    public static final String REGEX_URL_PATTERN = "^(http://|https://)[/?&=.,a-zA-Z0-9---]+$";
    public static final String REGEX_NAME_AND_SURNAME_PATTERN = "^[A-Z|А-Я][a-zа-я]+$";
    public static final String REGEX_DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$";
    public static final String REGEX_PASSPORT_NUMBER_PATTERN = "^[a-zA-Z0-9]{8,}$";
    public static final List<String> VALID_AUDIO_FILE_EXTENSIONS = List.of("mp3", "wav", "ogg");
}
