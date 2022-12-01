package com.pannawat.weatherforecast.network.factory

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ZonedDateTimeConverterFactory : Converter.Factory() {

    private val zonedDateTimeQueryConverter by lazy { ZonedDateTimeQueryConverter() }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type == ZonedDateTime::class.java) {
            return zonedDateTimeQueryConverter
        }

        return null
    }

    class ZonedDateTimeQueryConverter : Converter<ZonedDateTime, String> {
        override fun convert(value: ZonedDateTime): String? {
            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            return formatter.format(value)
        }
    }
}
