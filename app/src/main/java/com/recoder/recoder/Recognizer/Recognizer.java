package com.recoder.recoder.Recognizer;

import android.content.Context;

import com.recoder.recoder.App;
import com.recoder.recoder.Helper.PrefsHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**********************************************************************
 * Класс, который отправляет amr-файл и извлекает распознанный текст
 *
 * @author RomanAksenov
 *********************************************************************/
public class Recognizer implements IRecognizer {
    //tells Google to auto-detect the language
    public enum Languages {
        AUTO_DETECT("auto"),
        ARABIC_JORDAN("ar-JO"),
        ARABIC_LEBANON("ar-LB"),
        ARABIC_QATAR("ar-QA"),
        ARABIC_UAE("ar-AE"),
        ARABIC_MOROCCO("ar-MA"),
        ARABIC_IRAQ("ar-IQ"),
        ARABIC_ALGERIA("ar-DZ"),
        ARABIC_BAHRAIN("ar-BH"),
        ARABIC_LYBIA("ar-LY"),
        ARABIC_OMAN("ar-OM"),
        ARABIC_SAUDI_ARABIA("ar-SA"),
        ARABIC_TUNISIA("ar-TN"),
        ARABIC_YEMEN("ar-YE"),
        BASQUE("eu"),
        CATALAN("ca"),
        CZECH("cs"),
        DUTCH("nl-NL"),
        ENGLISH_AUSTRALIA("en-AU"),
        ENGLISH_CANADA("en-CA"),
        ENGLISH_INDIA("en-IN"),
        ENGLISH_NEW_ZEALAND("en-NZ"),
        ENGLISH_SOUTH_AFRICA("en-ZA"),
        ENGLISH_UK("en-GB"),
        ENGLISH_US("en-US"),
        FINNISH("fi"),
        FRENCH("fr-FR"),
        GALICIAN("gl"),
        GERMAN("de-DE"),
        HEBREW("he"),
        HUNGARIAN("hu"),
        ICELANDIC("is"),
        ITALIAN("it-IT"),
        INDONESIAN("id"),
        JAPANESE("ja"),
        KOREAN("ko"),
        LATIN("la"),
        CHINESE_SIMPLIFIED("zh-CN"),
        CHINESE_TRANDITIONAL("zh-TW"),
        CHINESE_HONGKONG("zh-HK"),
        CHINESE_CANTONESE("zh-yue"),
        MALAYSIAN("ms-MY"),
        NORWEGIAN("no-NO"),
        POLISH("pl"),
        PIG_LATIN("xx-piglatin"),
        PORTUGUESE("pt-PT"),
        PORTUGUESE_BRASIL("pt-BR"),
        ROMANIAN("ro-RO"),
        RUSSIAN("ru"),
        SERBIAN("sr-SP"),
        SLOVAK("sk"),
        SPANISH_ARGENTINA("es-AR"),
        SPANISH_BOLIVIA("es-BO"),
        SPANISH_CHILE("es-CL"),
        SPANISH_COLOMBIA("es-CO"),
        SPANISH_COSTA_RICA("es-CR"),
        SPANISH_DOMINICAN_REPUBLIC("es-DO"),
        SPANISH_ECUADOR("es-EC"),
        SPANISH_EL_SALVADOR("es-SV"),
        SPANISH_GUATEMALA("es-GT"),
        SPANISH_HONDURAS("es-HN"),
        SPANISH_MEXICO("es-MX"),
        SPANISH_NICARAGUA("es-NI"),
        SPANISH_PANAMA("es-PA"),
        SPANISH_PARAGUAY("es-PY"),
        SPANISH_PERU("es-PE"),
        SPANISH_PUERTO_RICO("es-PR"),
        SPANISH_SPAIN("es-ES"),
        SPANISH_US("es-US"),
        SPANISH_URUGUAY("es-UY"),
        SPANISH_VENEZUELA("es-VE"),
        SWEDISH("sv-SE"),
        TURKISH("tr"),
        ZULU("zu");

        //TODO Clean Up JavaDoc for Overloaded Methods using @link

        /**
         * Переменная для сохранения языкового кода
         */
        private final String languageCode;

        /**
         * Констуктор
         */
        private Languages(final String languageCode) {
            this.languageCode = languageCode;
        }

        public String toString() {
            return languageCode;
        }

    }

    /**
     * URL для отправки аудио данных POST запросом и получения результатов
     */

    private static final String GOOGLE_RECOGNIZER_URL = "http://www.google.com/speech-api/v2/recognize?client=chromium&output=json";
    public String PREF_API_KEY = "PrefApiKey";
    private boolean profanityFilter = true;
    private String language = null;
    private String apikey = null;
    Context context = App.getContext();
    /**
     * Констуктор
     *
     * @param language  язык запроса
     * @param apikey  уникальный ключ GoogleRecognition
     */
    public Recognizer(String language, String apikey) {
        this.language = language;
        this.apikey = apikey;
        PrefsHelper.writePrefString(context, PREF_API_KEY, apikey);
    }

    /**
     * Констуктор
     *
     * @param language Класс Languages для языка, который вы хотите назначить
     * @param apikey  уникальный ключ GoogleRecognition
     */
    public Recognizer(Languages language, String apikey) {
        this.language = language.languageCode;
        this.apikey = apikey;
        PrefsHelper.writePrefString(context, PREF_API_KEY, apikey);
    }

    /**
     * Констуктор
     *
     * @param profanityFilter флаг включения фильтра нецензурной речи
     * @param apikey - уникальный ключ GoogleRecognition
     */
    public Recognizer(boolean profanityFilter, String apikey) {
        this.profanityFilter = profanityFilter;
        this.apikey = apikey;
        PrefsHelper.writePrefString(context, PREF_API_KEY, apikey);
    }

    /**
     * онстуктор
     *
     * @param language язык запроса
     * @param profanityFilter флаг включения фильтра нецензурной речи
     * @param apikey  уникальный ключ GoogleRecognition
     */
    public Recognizer(String language, boolean profanityFilter, String apikey) {
        this.language = language;
        this.profanityFilter = profanityFilter;
        this.apikey = apikey;
        PrefsHelper.writePrefString(context, PREF_API_KEY, apikey);
    }

    /**
     * Constructor
     *
     * @param language Класс Languages для языка, который вы хотите назначить
     * @param profanityFilter флаг включения фильтра нецензурной речи
     * @param apikey уникальный ключ GoogleRecognition
     */
    public Recognizer(Languages language, boolean profanityFilter, String apikey) {
        this.language = language.languageCode;
        this.profanityFilter = profanityFilter;
        this.apikey = apikey;
        PrefsHelper.writePrefString(context, PREF_API_KEY, apikey);
    }

    /**
     * Language: содержит все поддерживаемые языки для Google Speech to Text.
     *      * Установка этого значения null приведет к тому, что Google будет использовать собственное распознавание языка.
     *      * По умолчанию значение равно нулю.
     *
     * @param language Класс Languages для языка, который вы хотите назначить
     */
    public void setLanguage(Languages language) {
        this.language = language.languageCode;
    }

    /**
     * Код языка.  Этот код языка должен соответствовать языку распознаваемой речи. ex. en-US ru-RU
     * По умолчанию значение равно нулю.
     *
     * @param language  код языка.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Возвращает состояние работы фильтра не нормативной лексики
     * Который включает и выключает фильтрацию ненормативной лексики (Включен по умолчанию).
     *
     * @return profanityFilter флаг включения фильтра нецензурной речи
     */
    public boolean getProfanityFilter() {
        return profanityFilter;
    }

    /**
     * Код языка.  Этот код языка должен соответствовать языку распознаваемой речи. ex. en-US ru-RU
     * По умолчанию значение равно нулю.
     *
     * @return language the Google language
     */
    public String getLanguage() {
        return language;
    }

    public String getApiKey() {
        return apikey;
    }

    public void setApiKey(String apikey) {
        this.apikey = apikey;
    }


    /**
     * Google запрос для отправки на сервер Google Recognition amr-файла
     *
     * @param amrFile - amr файл
     * @return Возвращает ответ на запрос
     * @throws IOException
     */
    public GoogleResponse getRecognizedDataForAmr(File amrFile) throws IOException {
        return getRecognizedDataForAmr(amrFile, 1);
    }

    /**
     * Google запрос для отправки на сервер Google Recognition amr-файла
     * @param amrFile  amr файл
     * @param maxResults количество ответов на Google запрос
     * @return Возвращает ответ на запрос
     * @throws IOException
     */
    public GoogleResponse getRecognizedDataForAmr(File amrFile, int maxResults) throws IOException {
        GoogleResponse googleResponse = getRecognizedDataForAmr(amrFile, maxResults, 8000);
        return googleResponse;
    }

    /**
     * Google запрос для отправки на сервер Google Recognition amr-файла
     * @param amrFile amr файл
     * @param maxResults количество ответов на Google запрос
     * @param sampleRate частота дискретизации
     * @return Возвращает ответ на запрос
     * @throws IOException
     */
    public GoogleResponse getRecognizedDataForAmr(File amrFile, int maxResults, int sampleRate) throws IOException {
        String[] response = rawRequest(amrFile, maxResults, sampleRate);
        GoogleResponse googleResponse = new GoogleResponse();
        parseResponse(response, googleResponse);
        return googleResponse;
    }

    /**
     *
     * @param rawResponse
     * @param googleResponse
     */
    private void parseResponse(String[] rawResponse, GoogleResponse googleResponse) {

        try {
            for (String s : rawResponse) {

                JSONObject jsonResponse = new JSONObject(s);
                JSONArray jsonResultArray = jsonResponse.getJSONArray("result");
                for (int i = 0; i < jsonResultArray.length(); i++) {
                    JSONObject jsonAlternativeObject = jsonResultArray.getJSONObject(i);
                    JSONArray jsonAlternativeArray = jsonAlternativeObject.getJSONArray("alternative");
                    double prevConfidence = 0;
                    for (int j = 0; j < jsonAlternativeArray.length(); j++) {
                        JSONObject jsonTranscriptObject = jsonAlternativeArray.getJSONObject(j);
                        String transcript = jsonTranscriptObject.optString("transcript", "");
                        double confidence = jsonTranscriptObject.optDouble("confidence", 0.0);
                        if (confidence > prevConfidence) {
                            googleResponse.setResponse(transcript);
                            googleResponse.setConfidence(String.valueOf(confidence));
                            prevConfidence = confidence;
                        } else
                            googleResponse.getOtherPossibleResponses().add(transcript);
                    }
                }
            }
        } catch (JSONException f) {

        }
    }

    /**
     * Функция которая получает ответ гугл запроса
     * @param inputFile amr-файл для запроса
     * @param maxResults количество ответов от Gooogle
     * @param sampleRate частота дискретизации
     * @return результат запроса
     * @throws IOException
     */
    private String[] rawRequest(File inputFile, int maxResults, int sampleRate) throws IOException {
        URL url;
        URLConnection urlConn;
        OutputStream outputStream;
        BufferedReader br;

        StringBuilder sb = new StringBuilder(GOOGLE_RECOGNIZER_URL);
        if (language != null) {
            sb.append("&lang=");
            sb.append(language);
        } else {
            sb.append("&lang=auto");
        }
        if (apikey != null) {
            sb.append("&key=");
            sb.append(apikey);
        }

        if (!profanityFilter) {
            sb.append("&pfilter=0");
        }
        sb.append("&maxresults=");
        sb.append(maxResults);

        // URL of Remote Script.
        url = new URL(sb.toString());
        // System.out.println("Recognizer.rawRequest(): url=" + url);

        // Open New URL connection channel.
        urlConn = url.openConnection();

        // we want to do output.
        urlConn.setDoOutput(true);

        // No caching
        urlConn.setUseCaches(false);

        // Specify the header content type.
        urlConn.setRequestProperty("Content-Type", "audio/amr; rate=" + sampleRate);
        urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36");

        // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        // StrictMode.setThreadPolicy(policy);
        // Send POST output.
        outputStream = urlConn.getOutputStream();

        FileInputStream fileInputStream = new FileInputStream(inputFile);

        byte[] buffer = new byte[256];

        while ((fileInputStream.read(buffer, 0, 256)) != -1) {
            outputStream.write(buffer, 0, 256);
        }

        fileInputStream.close();
        outputStream.close();

        // Get response data.
        br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8")));

        List<String> completeResponse = new ArrayList<String>();
        String response = br.readLine();
        while (response != null) {
            completeResponse.add(response);
            response = br.readLine();
        }

        br.close();

        // System.out.println("Recognizer.rawRequest() -> completeResponse = " + completeResponse);
        return completeResponse.toArray(new String[completeResponse.size()]);
    }


}
