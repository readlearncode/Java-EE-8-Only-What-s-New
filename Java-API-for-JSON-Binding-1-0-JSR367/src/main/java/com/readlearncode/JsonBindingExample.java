package com.readlearncode;

import com.readlearncode.examples.part1.BookletAdapter;
import com.readlearncode.domain.Book;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.BinaryDataStrategy;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.json.bind.config.PropertyVisibilityStrategy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonBindingExample extends JsonData {

    public String serializeBook() {
        return JsonbBuilder.create().toJson(book1);
    }

    public Book deserializeBook() {
        return JsonbBuilder.create().fromJson(bookJson, Book.class);
    }

    public String serializeListOfBooks() {
        return JsonbBuilder.create().toJson(books);
    }

    public List<Book> deserializeListOfBooks() {
        return JsonbBuilder.create().fromJson(bookListJson, new ArrayList<Book>().getClass().getGenericSuperclass());
    }

    public String serializeArrayOfBooks() {
        return JsonbBuilder.create().toJson(arrayBooks);
    }

    public String serializeArrayOfStrings() {
        return JsonbBuilder.create().toJson(new String[]{"Java EE", "Java SE"});
    }

    public String customizedMapping() {

        JsonbConfig jsonbConfig = new JsonbConfig()
                .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_DASHES)
                .withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL)
                .withStrictIJSON(true)
                .withFormatting(true)
                .withNullValues(true);

        Jsonb jsonb = JsonbBuilder.create(jsonbConfig);

        return jsonb.toJson(book1);
    }

    public String annotationPropertiesMapping() {
        return JsonbBuilder.create(getJsonbConfig()).toJson(magazine);
    }

    public String annotationMethodMapping() {
        return JsonbBuilder.create(getJsonbConfig()).toJson(newspaper);
    }

    public String annotationPropertyAndMethodMapping() {
        return JsonbBuilder.create(getJsonbConfig()).toJson(booklet);
    }

    public String bookAdapterToJson() {
        JsonbConfig jsonbConfig = new JsonbConfig().withAdapters(new BookletAdapter());
        Jsonb jsonb = JsonbBuilder.create(jsonbConfig);
        return jsonb.toJson(book1);
    }

    public Book bookAdapterToBook() {
        JsonbConfig jsonbConfig = new JsonbConfig().withAdapters(new BookletAdapter());
        Jsonb jsonb = JsonbBuilder.create(jsonbConfig);
        String json = "{\"isbn\":\"1234567890\",\"bookTitle\":\"Professional Java EE Design Patterns\",\"firstName\":\"Alex\",\"lastName\":\"Theedom\"}";
        return jsonb.fromJson(json, Book.class);
    }


    public void usingAProvider() {
        JsonbBuilder builder = JsonbBuilder.newBuilder("aProvider");
    }


    public String allCustomizedMapping() {

        PropertyVisibilityStrategy vis = new PropertyVisibilityStrategy() {
            @Override
            public boolean isVisible(Field field) {
                return false;
            }

            @Override
            public boolean isVisible(Method method) {
                return false;
            }
        };

        JsonbConfig jsonbConfig = new JsonbConfig()
                .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_DASHES)
                .withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL)
                .withPropertyVisibilityStrategy(vis)
                .withStrictIJSON(true)
                .withFormatting(true)
                .withNullValues(true)
                .withBinaryDataStrategy(BinaryDataStrategy.BASE_64_URL)
                .withDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        Jsonb jsonb = JsonbBuilder.create(jsonbConfig);

        return jsonb.toJson(book1);
    }

    private JsonbConfig getJsonbConfig() {
        return new JsonbConfig().withLocale(Locale.ENGLISH);
    }
}